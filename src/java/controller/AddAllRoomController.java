package controller;

import dal.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.sql.SQLException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Account;
import model.Device;
import model.Phong;
import model.RoomType;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 15) // 15 MB
@WebServlet(name = "AddAllRoomController", urlPatterns = {"/add-all-room"})
public class AddAllRoomController extends HttpServlet {

    final RoomTypeDAO roomTypeDAO = new RoomTypeDAO();
    final DeviceDAO deviceDAO = new DeviceDAO();
    final PhongDAO phongDAO = new PhongDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        if (account != null) {
            if (account.getRole().equals("landlord")) {
                int nhaTroID = Integer.parseInt(request.getParameter("id"));

                List<RoomType> listRoomTypes = roomTypeDAO.getRopmTypes();
                List<Device> listDevices = deviceDAO.getDevices();

                request.setAttribute("r", nhaTroID);
                request.setAttribute("roomtypes", listRoomTypes);
                request.setAttribute("devices", listDevices);
                request.getRequestDispatcher("add-all-room.jsp").forward(request, response);
            } else {
                session.setAttribute("errorMessage", "Access denied!");
                response.sendRedirect("login.jsp");
            }

        } else {
            session.setAttribute("errorMessage", "You must login first!");
            response.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        if (account != null) {
            if (account.getRole().equals("landlord")) {

                int tuPhong = Integer.parseInt(request.getParameter("tuPhong"));  // Starting room number
                int denPhong = Integer.parseInt(request.getParameter("denPhong")); // Ending room number
                int floor = Integer.parseInt(request.getParameter("floor"));  // Floor number
                int loaiPhongId = Integer.parseInt(request.getParameter("loaiPhong"));  // Room type ID
                int nhaTroId = Integer.parseInt(request.getParameter("nhatroId")); // Building ID
                int dienTich = Integer.parseInt(request.getParameter("dai"));      // Room area
                int gia = Integer.parseInt(request.getParameter("donGia"));        // Room price
                String trangThai = "T";

                // Generate room names dynamically based on the tuPhong and denPhong range
                List<String> roomNames = new ArrayList<>();
                for (int i = tuPhong; i <= denPhong; i++) {
                    String roomName = "A" + floor + String.format("%02d", i);  // Example: A201, A202, ..., A206
                    roomNames.add(roomName);
                }

                // Define the directory to upload the files to
                String uploadDir = getServletContext().getRealPath("/uploads");
                File uploadDirectory = new File(uploadDir);

                // Check if the directory exists, if not, create it
                if (!uploadDirectory.exists()) {
                    uploadDirectory.mkdir();
                }

                // Get selected devices from the form
                String[] selectedDevices = request.getParameterValues("thietBi[]");

                // Loop through each room and save room, images, and devices
                for (String roomName : roomNames) {

                    String modifiedRoomName = roomName;
                    int counter = 1;

                    while (phongDAO.roomNameExists(modifiedRoomName, nhaTroId)) {
                        // Modify the room name if it already exists
                        modifiedRoomName = "A" + floor + String.format("%02d", counter++);  // Change A to another character if needed
                    }
                    // Step 1: Insert the room and get its ID
                    Phong phong = new Phong();
                    phong.setTenPhongTro(roomName);
                    phong.setTang(floor);
                    phong.setID_LoaiPhong(loaiPhongId);
                    phong.setID_NhaTro(nhaTroId);
                    phong.setDien_tich(dienTich);
                    phong.setTrang_thai(trangThai);
                    phong.setGia(gia);

                    int roomId = phongDAO.insertRooms(phong);  // Save room and get room ID

                    // Step 2: Process uploaded images for this specific room
                    List<String> imagePaths = new ArrayList<>();
                    for (Part part : request.getParts()) {
                        if (part.getName().equals("roomImages") && part.getSize() > 0) {
                            String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                            String uploadPath = uploadDir + File.separator + fileName;

                            // Write the file to disk
                            try {
                                part.write(uploadPath);
                                imagePaths.add("uploads/" + fileName);  // Add the image path to the list for this room
                            } catch (IOException e) {
                                e.printStackTrace();
                                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error writing file: " + fileName);
                                return;
                            }
                        }
                    }

                    // Insert images for the current room
                    for (String imagePath : imagePaths) {
                        try {
                            phongDAO.insertRoomImage(roomId, imagePath);  // Save image path linked to the room
                        } catch (SQLException ex) {
                            Logger.getLogger(AddAllRoomController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    // Step 3: Insert associated devices for the room
                    if (selectedDevices != null) {
                        for (String deviceId : selectedDevices) {
                            int id = phongDAO.getThietBiID(deviceId);
                            int deviceQuantity = Integer.parseInt(request.getParameter("soLuong" + deviceId)); // Get the quantity for the device
                            String deviceStatus = "New"; // Default device status
                            String deviceDescription = "Default description"; // Update based on form data

                            // Insert into thietBiPhong table
                            phongDAO.insertThietBiPhong(roomId, id, deviceStatus, deviceDescription, deviceQuantity);
                        }
                    }
                }
                session.setAttribute("notification", "Rooms and images added successfully!");
                response.sendRedirect("room?nhaTro=" + nhaTroId);

            } else {
                session.setAttribute("errorMessage", "Access denied!");
                response.sendRedirect("login.jsp");
            }

        } else {
            session.setAttribute("errorMessage", "You must login first!");
            response.sendRedirect("login.jsp");
        }
    }

}
