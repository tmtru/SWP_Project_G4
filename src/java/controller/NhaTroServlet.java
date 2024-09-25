package controller;

import dal.NhaTroDAO;
import dal.PhongDAO;
import model.NhaTro;
import model.Phong;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class NhaTroServlet extends HttpServlet {
    private final NhaTroDAO nhaTroDAO = new NhaTroDAO();
    private final PhongDAO phongTroDAO = new PhongDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            // Fetch and display all NhaTro
            List<NhaTro> nhaTroList = nhaTroDAO.getAll();
            request.setAttribute("nhaTroList", nhaTroList);
            request.getRequestDispatcher("NhaTro.jsp").forward(request, response);
        } else if (action.equals("add")) {
            // Forward to add new NhaTro page
            request.getRequestDispatcher("addNhaTro.jsp").forward(request, response);
        } else if (action.equals("edit")) {
            // Edit existing NhaTro
            int idNhaTro = Integer.parseInt(request.getParameter("id"));
            NhaTro nhaTro = nhaTroDAO.getNhaTroById(idNhaTro);
            request.setAttribute("nhaTro", nhaTro);
            request.getRequestDispatcher("editNhaTro.jsp").forward(request, response);
        } else if (action.equals("delete")) {
            response.sendRedirect("nhatro");
        } else if (action.equals("viewRooms")) { // View rooms for a specific NhaTro
            int idNhaTro = Integer.parseInt(request.getParameter("id"));
            NhaTro nhaTro = nhaTroDAO.getNhaTroById(idNhaTro);
            List<Phong> phongTroList = phongTroDAO.getRoomsByNhaTro(idNhaTro); // Fetch associated rooms
            request.setAttribute("phongTroList", phongTroList);
            request.setAttribute("nhaTro", nhaTro); // Pass the NhaTro object to the JSP
            request.getRequestDispatcher("PhongTro.jsp").forward(request, response); // Forward to the details page
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            return; // Do nothing if no action is specified
        } else if (action.equals("add")) {
            // Add a new NhaTro
            String tenNhaTro = request.getParameter("tenNhaTro");
            String diaChi = request.getParameter("diaChi");
            String moTa = request.getParameter("moTa");
            int idChuNha = Integer.parseInt(request.getParameter("idChuTro"));
            NhaTro nhaTro = new NhaTro();
            nhaTro.setTenNhaTro(tenNhaTro);
            nhaTro.setDia_chi(diaChi);
            nhaTro.setMo_ta(moTa);
            nhaTro.setID_ChuTro(idChuNha);
            nhaTroDAO.insertNhaTro(nhaTro);
            response.sendRedirect("nhatro");
        } else if (action.equals("editNhaTro")) {
            // Edit an existing NhaTro
            int idNhaTro = Integer.parseInt(request.getParameter("idNhaTro"));
            String tenNhaTro = request.getParameter("tenNhaTro");
            String diaChi = request.getParameter("diaChi");
            String moTa = request.getParameter("moTa");
            int idChuNha = Integer.parseInt(request.getParameter("idChuTro"));
            NhaTro nhaTro = new NhaTro();
            nhaTro.setTenNhaTro(tenNhaTro);
            nhaTro.setDia_chi(diaChi);
            nhaTro.setMo_ta(moTa);
            nhaTro.setID_ChuTro(idChuNha);
            nhaTroDAO.updateNhaTro(nhaTro);
            response.sendRedirect("nhatro");
        } else if (action.equals("addPhongTro")) {
            // Add a new PhongTro
            String tenPhongTro = request.getParameter("tenPhongTro");
            int idNhaTro = Integer.parseInt(request.getParameter("idNhaTro"));
            String trangThai = request.getParameter("trangThai");
            int gia = Integer.parseInt(request.getParameter("gia"));
            int tang = Integer.parseInt(request.getParameter("tang"));
            float dienTich = Float.parseFloat(request.getParameter("dienTich"));
            int idLoaiPhong = Integer.parseInt(request.getParameter("idLoaiPhong"));
            Phong phongTro = new Phong(idLoaiPhong, idLoaiPhong, tenPhongTro, idNhaTro, tang, trangThai, dienTich, gia);
            phongTroDAO.insertRoom(phongTro);
            response.sendRedirect("nhatro"); // Redirect to the main Nha Tro page
        }
    }
}


