/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.NhaTroDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import model.NhaTro;
import model.Phong;

/**
 *
 * @author Admin
 */
public class filterRoomsHome extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet filterRoomsHome</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet filterRoomsHome at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        // Lấy giá trị từ request
        String[] price = request.getParameterValues("price");
        String[] area = request.getParameterValues("area");
        String status = request.getParameter("status");
        String sortBy = request.getParameter("sortBy"); // Thêm biến để lấy giá trị sắp xếp
        String sortOrder = request.getParameter("order"); // Thêm biến để lấy hướng sắp xếp

        HttpSession session = request.getSession();
        ArrayList<Phong> rooms = (ArrayList<Phong>) session.getAttribute("allrooms");
        ArrayList<Phong> filteredRooms = new ArrayList<>(rooms); // Khởi tạo bằng tất cả phòng

        // Lọc theo giá
        if (price != null) {
            filteredRooms = filterByPrice(rooms, price);
        }

        // Lọc theo diện tích
        if (area != null) {
            filteredRooms = filterByArea(filteredRooms, area);
        }

        // Lọc theo trạng thái
        if (status != null) {
            filteredRooms = filterByStatus(filteredRooms, status);
        }

        // Sắp xếp theo tiêu chí được chọn
        if (sortBy != null) {
            sortRooms(filteredRooms, sortBy, sortOrder);
        }

        // Gán danh sách phòng đã lọc vào request
        request.setAttribute("rooms", filteredRooms);

        // Chuyển tiếp đến JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("allrooms.jsp");
        dispatcher.include(request, response);
    }

    private void sortRooms(ArrayList<Phong> filteredRooms, String sortBy, String sortOrder) {
        Comparator<Phong> comparator = null;

        switch (sortBy) {
            case "price":
                comparator = Comparator.comparingDouble(Phong::getGia);
                break;
            case "area":
                comparator = Comparator.comparingDouble(Phong::getDien_tich);
                break;
            default:
                break;
        }

        if (comparator != null) {
            if ("asc".equals(sortOrder)) {
                filteredRooms.sort(comparator);
            } else if ("desc".equals(sortOrder)) {
                filteredRooms.sort(comparator.reversed());
            }
        }
    }

    private ArrayList<Phong> filterByPrice(ArrayList<Phong> rooms, String[] price) {
        List<int[]> priceRanges = new ArrayList<>();
        ArrayList<Phong> filteredRooms = new ArrayList<>();

        for (String item : price) {
            String[] splitArray = item.split("-");
            int[] pair = new int[2];
            pair[0] = Integer.parseInt(splitArray[0]);
            pair[1] = Integer.parseInt(splitArray[1]);
            priceRanges.add(pair);
        }

        // Lọc các phòng theo từng khoảng giá
        for (int[] priceRange : priceRanges) {
            ArrayList<Phong> roomsInRange = filterRoomsByPrice(rooms, priceRange[0], priceRange[1]);
            for (Phong room : roomsInRange) {
                if (!filteredRooms.contains(room)) {
                    filteredRooms.add(room);
                }
            }
        }
        return filteredRooms;
    }

    private ArrayList<Phong> filterByArea(ArrayList<Phong> filteredRooms, String[] area) {
        ArrayList<Phong> areaFilteredRooms = new ArrayList<>();

        for (String areaRange : area) {
            String[] splitArray = areaRange.split("-");
            int minArea = Integer.parseInt(splitArray[0]);
            int maxArea = Integer.parseInt(splitArray[1]);

            for (Phong room : filteredRooms) {
                if (room.getDien_tich() >= minArea && room.getDien_tich() <= maxArea) {
                    areaFilteredRooms.add(room);
                }
            }
        }
        return areaFilteredRooms;
    }

    private ArrayList<Phong> filterByStatus(ArrayList<Phong> filteredRooms, String status) {
        ArrayList<Phong> statusFilteredRooms = new ArrayList<>();

        if ("ALL".equals(status)) {
            return filteredRooms; // Trả về danh sách đã lọc nếu trạng thái là ALL
        } else if ("T".equals(status)) {
            for (Phong room : filteredRooms) {
                if ("T".equals(room.getTrang_thai())) {
                    statusFilteredRooms.add(room);
                }
            }
        }
        return statusFilteredRooms;
    }

    private void sortRoomsByPrice(ArrayList<Phong> filteredRooms, String sortOrder) {
        if ("asc".equals(sortOrder)) {
            filteredRooms.sort(Comparator.comparingInt(Phong::getGia));
        } else if ("desc".equals(sortOrder)) {
            filteredRooms.sort(Comparator.comparingInt(Phong::getGia).reversed());
        }
    }

    //filter room by price in range min-max
    public ArrayList<Phong> filterRoomsByPrice(ArrayList<Phong> phongList, double min, double max) {
        ArrayList<Phong> filteredList = new ArrayList<>();
        max *= 1000000;
        min *= 1000000;
        for (Phong phong : phongList) {
            if (phong.getGia() >= min && phong.getGia() <= max) {
                filteredList.add(phong);
            }
        }
        return filteredList;
    }

    //filter room by area in range min-max
    public ArrayList<Phong> filterRoomsByArea(ArrayList<Phong> phongList, double minArea, double maxArea) {
        ArrayList<Phong> filteredList = new ArrayList<>();
        for (Phong phong : phongList) {
            if (phong.getDien_tich() >= minArea && phong.getDien_tich() <= maxArea) {
                filteredList.add(phong);
            }
        }
        return filteredList;
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
