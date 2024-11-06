/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dal.NhaTroDAO;
import dal.PhongDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import model.NhaTro;
import model.Phong;

/**
 *
 * @author Admin
 */
public class homeCategory extends HttpServlet {

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
            out.println("<title>Servlet homeCategory</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet homeCategory at " + request.getContextPath() + "</h1>");
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
        PhongDAO daor = new PhongDAO();
        String lat = request.getParameter("lat");
        String lon = request.getParameter("lon");
        HashMap<NhaTro, String> distanceMap = new HashMap<>();
        NhaTroDAO nhaTroDAO = new NhaTroDAO();
        HttpSession session = request.getSession();
        ArrayList<NhaTro> danhSachNhaTro = nhaTroDAO.getAll();

        if (lat != null && lon != null) {
            for (NhaTro nhaTro : danhSachNhaTro) {
                double nhaTroLat = nhaTro.getLat();
                double nhaTroLon = nhaTro.getLon();
                String distance = getDistance(lat, lon, String.valueOf(nhaTroLat), String.valueOf(nhaTroLon));
                distanceMap.put(nhaTro, distance);
            }

            danhSachNhaTro.sort((n1, n2) -> {
                String distance1 = distanceMap.get(n1);
                String distance2 = distanceMap.get(n2);

                if (distance1 == null || distance2 == null) {
                    return distance1 == null ? (distance2 == null ? 0 : 1) : -1;
                }

                double d1 = Double.parseDouble(distance1);
                double d2 = Double.parseDouble(distance2);
                return Double.compare(d1, d2);
            });

            request.setAttribute("distanceMap", distanceMap);
        }

        session.setAttribute("lat", lat);
        session.setAttribute("lon", lon);
        session.setAttribute("nhatros", danhSachNhaTro);
        request.getRequestDispatcher("category.jsp").forward(request, response);

    }

    public static String getDistance(String userLat, String userLon, String nhaTroLat, String nhaTroLon) {
        try {
            String urlStr = "http://router.project-osrm.org/route/v1/driving/"
                    + userLon + "," + userLat + ";" + nhaTroLon + "," + nhaTroLat
                    + "?overview=false";

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            conn.disconnect();

            // Parse JSON with Jackson Databind
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(content.toString());
            double distanceInMeters = rootNode.path("routes").get(0).path("distance").asDouble();

            // Convert meters to kilometers
            double distanceInKilometers = distanceInMeters / 1000;

            // Format the distance to two decimal places if needed
            DecimalFormat df = new DecimalFormat("#.##");
            return df.format(distanceInKilometers); // Returning in kilometers

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
