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
import model.LoaiPhong;
import model.NhaTro;
import model.Phong;

public class RoomServlet extends HttpServlet {
    private static final Map<String, String> STATUS_DESCRIPTIONS = new HashMap<>();
    private static final int DEFAULT_HOUSE_ID = 1;
    
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

        // Handle house selection
        int currentHouseId = DEFAULT_HOUSE_ID;
        if (idHouseStr != null && !idHouseStr.isEmpty()) {
            try {
                currentHouseId = Integer.parseInt(idHouseStr);
                request.getSession().setAttribute("currentHouse", currentHouseId);
            } catch (NumberFormatException e) {
                // Use default house ID
            }
        } else if (nhaTroId != null && !nhaTroId.isEmpty()) {
            try {
                currentHouseId = Integer.parseInt(nhaTroId);
                request.getSession().setAttribute("currentHouse", currentHouseId);
            } catch (NumberFormatException e) {
                // Use default house ID
            }
        } else {
            Object sessionHouse = request.getSession().getAttribute("currentHouse");
            if (sessionHouse != null) {
                currentHouseId = (Integer) sessionHouse;
            }
        }

        // Apply filters and get rooms
        List<Phong> filteredRooms;
        if (tangParam != null && !tangParam.isEmpty()) {
            try {
                int tang = Integer.parseInt(tangParam);
                // Get rooms filtered by both house and floor
                filteredRooms = roomDAO.getRoomsByNhaTroAndTang(currentHouseId, tang);
            } catch (NumberFormatException e) {
                filteredRooms = roomDAO.getPhongsByNhaTroId(currentHouseId);
            }
        } else {
            filteredRooms = roomDAO.getPhongsByNhaTroId(currentHouseId);
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