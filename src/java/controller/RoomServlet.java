package controller;

import dal.LoaiPhongDAO;
import dal.NhaTroDAO;
import dal.PhongDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import model.LoaiPhong;
import model.NhaTro;
import model.Phong;

public class RoomServlet extends HttpServlet {
    private static final Map<String, String> STATUS_DESCRIPTIONS = new HashMap<>();
    static {
        STATUS_DESCRIPTIONS.put("D", "Đang thuê");
        STATUS_DESCRIPTIONS.put("T", "Trống");
        STATUS_DESCRIPTIONS.put("DS","Đang sửa");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PhongDAO roomDAO = new PhongDAO();
        NhaTroDAO nhaTroDAO = new NhaTroDAO();
        LoaiPhongDAO loaiPhongDAO = new LoaiPhongDAO();

        int page = 1;
        int pageSize = 8; // Number of rooms per page
        String pageStr = request.getParameter("page");
        if (pageStr != null && !pageStr.isEmpty()) {
            page = Integer.parseInt(pageStr);
        }

        // Filter rooms
        List<Phong> allFilteredRooms = getFilteredRooms(request, roomDAO);

        // Pagination logic
        int totalRooms = allFilteredRooms.size();
        int totalPages = (int) Math.ceil((double) totalRooms / pageSize);
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalRooms);
        List<Phong> paginatedRooms = allFilteredRooms.subList(startIndex, endIndex);

        // Set attributes for JSP
        request.setAttribute("rooms", paginatedRooms);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        // Get available floors, houses, and statuses
        List<Integer> tangList = roomDAO.getAvailableTang();
        request.setAttribute("tangList", tangList);

        List<NhaTro> nhaTroList = nhaTroDAO.getAll();
        request.setAttribute("nhaTroList", nhaTroList);

        List<LoaiPhong> loaiPhongList = loaiPhongDAO.getAllLoaiPhong();
        request.setAttribute("loaiPhongList", loaiPhongList);

        // Get available statuses with descriptions
        Map<String, String> statusMap = getStatusDescriptions(roomDAO.getAvailableStatuses());
        request.setAttribute("statusMap", statusMap);

        // Store selected values for filtering
        String nhaTroId = request.getParameter("nhaTro");
        String tang = request.getParameter("tang");
        String status = request.getParameter("status");
        request.setAttribute("selectedNhaTro", nhaTroId);
        request.setAttribute("selectedTang", tang);
        request.setAttribute("selectedStatus", status);

        request.getRequestDispatcher("room.jsp").forward(request, response);
    }

    private List<Phong> getFilteredRooms(HttpServletRequest request, PhongDAO roomDAO) {
        List<Phong> rooms;
        String nhaTroId = request.getParameter("nhaTro");
        String tang = request.getParameter("tang");
        String status = request.getParameter("status");

        try {
            if (nhaTroId != null && !nhaTroId.isEmpty() && tang != null && !tang.isEmpty()) {
                int nhaTroNumber = Integer.parseInt(nhaTroId);
                int tangNumber = Integer.parseInt(tang);
                rooms = roomDAO.getRoomsByNhaTroAndTang(nhaTroNumber, tangNumber);
            } else if (nhaTroId != null && !nhaTroId.isEmpty()) {
                int nhaTroNumber = Integer.parseInt(nhaTroId);
                rooms = roomDAO.getRoomsByNhaTro(nhaTroNumber);
            } else if (tang != null && !tang.isEmpty()) {
                int tangNumber = Integer.parseInt(tang);
                rooms = roomDAO.getRoomsByTang(tangNumber);
            } else {
                rooms = roomDAO.getAllRooms();
            }

            // Filter by status if selected
            if (status != null && !status.isEmpty()) {
                rooms = rooms.stream()
                    .filter(room -> status.equals(room.getTrang_thai()))
                    .collect(java.util.stream.Collectors.toList());
            }
        } catch (NumberFormatException e) {
            rooms = roomDAO.getAllRooms(); // default to all rooms on error
        }
        return rooms;
    }

    private Map<String, String> getStatusDescriptions(List<String> statuses) {
        Map<String, String> statusMap = new HashMap<>();
        for (String status : statuses) {
            statusMap.put(status, STATUS_DESCRIPTIONS.getOrDefault(status, status));
        }
        return statusMap;
    }
}