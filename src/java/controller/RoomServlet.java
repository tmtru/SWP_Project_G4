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
import model.LoaiPhong;
import model.NhaTro;
import model.Phong;

public class RoomServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PhongDAO roomDAO = new PhongDAO();
        NhaTroDAO nhaTroDAO = new NhaTroDAO();
        LoaiPhongDAO loaiPhongDAO = new LoaiPhongDAO();

        
        int page = 1;
        int pageSize = 8; // Số trag mỗi page

        String pageStr = request.getParameter("page");
        if (pageStr != null && !pageStr.isEmpty()) {
            page = Integer.parseInt(pageStr);
        }

        // filter room floor x house
        List<Phong> allFilteredRooms = getFilteredRooms(request, roomDAO);

        // Apply pagination to filtered rooms
        int totalRooms = allFilteredRooms.size();
        int totalPages = (int) Math.ceil((double) totalRooms / pageSize);
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalRooms);
        List<Phong> paginatedRooms = allFilteredRooms.subList(startIndex, endIndex);

        // set trang
        request.setAttribute("rooms", paginatedRooms);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        //get avaible tang
        List<Integer> tangList = roomDAO.getAvailableTang();
        request.setAttribute("tangList", tangList);
        //avaible nha tro
        List<NhaTro> nhaTroList = nhaTroDAO.getAll();
        request.setAttribute("nhaTroList", nhaTroList);
        //avaible loai phong
        List<LoaiPhong> loaiPhongList = loaiPhongDAO.getAllLoaiPhong();
        request.setAttribute("loaiPhongList", loaiPhongList);

        request.getRequestDispatcher("room.jsp").forward(request, response);
    }

    private List<Phong> getFilteredRooms(HttpServletRequest request, PhongDAO roomDAO) {
        List<Phong> rooms;

        String nhaTroId = request.getParameter("nhaTro");
        String tang = request.getParameter("tang");

        try {
            if (nhaTroId != null && !nhaTroId.isEmpty() && tang != null && !tang.isEmpty()) {
                int nhaTroNumber = Integer.parseInt(nhaTroId);
                int tangNumber = Integer.parseInt(tang);
                rooms = roomDAO.getRoomsByNhaTroAndTang(nhaTroNumber, tangNumber);
                System.out.println( nhaTroNumber + tangNumber);
            } else if (nhaTroId != null && !nhaTroId.isEmpty()) {
                int nhaTroNumber = Integer.parseInt(nhaTroId);
                rooms = roomDAO.getRoomsByNhaTro(nhaTroNumber);
                System.out.println(nhaTroNumber);
            } else if (tang != null && !tang.isEmpty()) {
                int tangNumber = Integer.parseInt(tang);
                rooms = roomDAO.getRoomsByTang(tangNumber);
                System.out.println(tangNumber);
            } else {
                rooms = roomDAO.getAllRooms();
                System.out.println("Getting all rooms");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error parsing filter parameters: " + e.getMessage());
            request.setAttribute("error", "invalid");
            rooms = roomDAO.getAllRooms();
        }

        System.out.println(rooms.size());
        return rooms;
    }
}