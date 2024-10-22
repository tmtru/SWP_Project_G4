package controller;

import dal.LoaiPhongDAO;
import dal.NhaTroDAO;
import dal.PhongDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import model.LoaiPhong;
import model.NhaTro;
import model.Phong;

public class RoomServlet extends HttpServlet {
    private static final Map<String, String> STATUS_DESCRIPTIONS = new HashMap<>();
    
    static {
        STATUS_DESCRIPTIONS.put("D", "Đang thuê");
        STATUS_DESCRIPTIONS.put("T", "Trống");
        STATUS_DESCRIPTIONS.put("DS", "Đang sửa");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PhongDAO roomDAO = new PhongDAO();
        NhaTroDAO nhaTroDAO = new NhaTroDAO();
        LoaiPhongDAO loaiPhongDAO = new LoaiPhongDAO();

        // Get filter parameters
        String nhaTroId = request.getParameter("nhaTro");
        String tangParam = request.getParameter("tang");
        String status = request.getParameter("status");
        String idHouseStr = request.getParameter("idHouse");

        // Pagination setup
        int page = 1;
        int pageSize = 8;
        String pageStr = request.getParameter("page");
        if (pageStr != null && !pageStr.isEmpty()) {
            try {
                page = Integer.parseInt(pageStr);
            } catch (NumberFormatException e) {
                // Keep default page value
            }
        }

        // Get houses by role from session
        @SuppressWarnings("unchecked")
        List<NhaTro> housesByRole = (List<NhaTro>) request.getSession().getAttribute("housesByRole");
        
        // Handle house selection with automatic selection of first house
        int currentHouseId = -1;
        
        if (idHouseStr != null && !idHouseStr.isEmpty()) {
            try {
                currentHouseId = Integer.parseInt(idHouseStr);
            } catch (NumberFormatException e) {
                // Invalid ID, will fall back to first house
            }
        } else if (nhaTroId != null && !nhaTroId.isEmpty()) {
            try {
                currentHouseId = Integer.parseInt(nhaTroId);
            } catch (NumberFormatException e) {
                // Invalid ID, will fall back to first house
            }
        } else {
            Object sessionHouse = request.getSession().getAttribute("currentHouse");
            if (sessionHouse != null) {
                currentHouseId = (Integer) sessionHouse;
            }
        }

        // If no house is selected and there are available houses, select the first one
        if (currentHouseId == -1 && housesByRole != null && !housesByRole.isEmpty()) {
            currentHouseId = housesByRole.get(0).getID_NhaTro();
        }

        // Store the selected house ID in session
        request.getSession().setAttribute("currentHouse", currentHouseId);

        // Apply filters and get rooms
        List<Phong> filteredRooms;
        if (currentHouseId != -1) {
            if (tangParam != null && !tangParam.isEmpty()) {
                try {
                    int tang = Integer.parseInt(tangParam);
                    filteredRooms = roomDAO.getRoomsByNhaTroAndTang(currentHouseId, tang);
                } catch (NumberFormatException e) {
                    filteredRooms = roomDAO.getPhongsByNhaTroId(currentHouseId);
                }
            } else {
                filteredRooms = roomDAO.getPhongsByNhaTroId(currentHouseId);
            }
        } else {
            filteredRooms = new ArrayList<>(); // Empty list if no house is selected
        }

        // Apply status filter if present
        if (status != null && !status.isEmpty()) {
            filteredRooms = filteredRooms.stream()
                .filter(room -> status.equals(room.getTrang_thai()))
                .collect(java.util.stream.Collectors.toList());
        }

        // Calculate pagination
        int totalRooms = filteredRooms.size();
        int totalPages = (int) Math.ceil((double) totalRooms / pageSize);
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalRooms);

        // Get paginated rooms
        List<Phong> paginatedRooms = filteredRooms.subList(startIndex, endIndex);

        // Get all necessary lists for dropdowns
        List<Integer> tangList = roomDAO.getAvailableTang();
        List<NhaTro> nhaTroList = nhaTroDAO.getAll();
        List<LoaiPhong> loaiPhongList = loaiPhongDAO.getAllLoaiPhong();
        Map<String, String> statusMap = getStatusDescriptions(roomDAO.getAvailableStatuses());

        // Set attributes for JSP
        request.setAttribute("rooms", paginatedRooms);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("tangList", tangList);
        request.setAttribute("nhaTroList", nhaTroList);
        request.setAttribute("loaiPhongList", loaiPhongList);
        request.setAttribute("statusMap", statusMap);
        request.setAttribute("selectedNhaTro", currentHouseId);
        request.setAttribute("selectedTang", tangParam);
        request.setAttribute("selectedStatus", status);

        // Forward to JSP
        request.getRequestDispatcher("room.jsp").forward(request, response);
    }

    private Map<String, String> getStatusDescriptions(List<String> statuses) {
        Map<String, String> statusMap = new HashMap<>();
        for (String status : statuses) {
            statusMap.put(status, STATUS_DESCRIPTIONS.getOrDefault(status, status));
        }
        return statusMap;
    }
}