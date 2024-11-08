package controller;

import dal.AccountDAO;
import dal.ChuTroDAO;
import dal.NhaTroDAO;
import dal.QuanLiDAO;
import dal.QuanLyDAO;
import model.NhaTro;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.ChuTro;
import model.QuanLi;
import model.QuanLy;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 15)    // 15 MB
public class NhaTroServlet extends HttpServlet {

    private final NhaTroDAO nhaTroDAO = new NhaTroDAO();
    private final AccountDAO accountDAO = new AccountDAO();
    private final ChuTroDAO chuTroDAO = new ChuTroDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        if (account != null) {

            Account currentAccount = accountDAO.getAccountById2(account.getID_Account());
            boolean isOwwer = false;

            isOwwer = currentAccount != null && currentAccount.getRole().equals("landlord");

            String pageParam = request.getParameter("page");
            String searchParam = request.getParameter("search");
            int page = 1; // Default to the first page
            int pageSize = 6; // Set the desired page size
            if (pageParam != null && !pageParam.isEmpty()) {
                page = Integer.parseInt(pageParam);
            }
            List<NhaTro> listNhaTro;
            if (isOwwer) {
                ChuTro chuTro = chuTroDAO.getChuTroByAccountId(currentAccount.getID_Account());
                listNhaTro = nhaTroDAO.getAllNhaTroWithParam(searchParam, chuTro.getId());
            }else{
                QuanLyDAO qldao = new QuanLyDAO();
                QuanLy quanLy = qldao.getChuTroByAccountId(currentAccount.getID_Account());
                 listNhaTro = nhaTroDAO.getAllNhaTroForManager(quanLy.getId());
            }
            List<NhaTro> pagingNhaTro = nhaTroDAO.Paging(listNhaTro, page, pageSize);

            request.setAttribute("lesson", pagingNhaTro);
            request.setAttribute("totalPages", listNhaTro.size() % pageSize == 0 ? (listNhaTro.size() / pageSize) : (listNhaTro.size() / pageSize + 1));
            request.setAttribute("currentPage", page);
            request.setAttribute("searchParam", searchParam);
            request.setAttribute("listS", pagingNhaTro);
            request.setAttribute("isOwwer", isOwwer);

            request.getRequestDispatcher("NhaTro.jsp").forward(request, response);
        } else {
            session.setAttribute("errorMessage", "You must login first!");
            response.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        if (account != null) {
            if ("add".equals(action)) {
                String tenNhaTro = request.getParameter("tenNhaTro");
                String diaChi = request.getParameter("diaChi");
                String moTa = request.getParameter("moTa");
                ChuTro chuTro = chuTroDAO.getChuTroByAccountId(account.getID_Account());
                // Create new NhaTro object
                NhaTro newNhaTro = new NhaTro();
                newNhaTro.setTenNhaTro(tenNhaTro);
                newNhaTro.setDia_chi(diaChi);
                newNhaTro.setMo_ta(moTa);
                newNhaTro.setID_ChuTro(chuTro.getId());
                // Save the NhaTro to the database and get the generated ID
                int nhaTroId = nhaTroDAO.saveNhaTro(newNhaTro);

                // Define the directory to upload the files to
                String uploadDir = getServletContext().getRealPath("/uploads");
                File uploadDirectory = new File(uploadDir);

                // Check if the directory exists, if not, create it
                if (!uploadDirectory.exists()) {
                    uploadDirectory.mkdir();
                }

                // Process images
                List<String> imageUrls = new ArrayList<>();
                for (Part part : request.getParts()) {
                    if (part.getName().equals("images") && part.getSize() > 0) {
                        // Save the file and get the URL
                        String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                        String uploadPath = uploadDir + File.separator + fileName;

                        // Write the file to disk
                        try {
                            part.write(uploadPath);
                            String imageUrl = "uploads/" + fileName;
                            imageUrls.add(imageUrl);
                        } catch (IOException e) {
                            e.printStackTrace();
                            // Handle the error here, you can either log it or send a response error
                            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error writing file: " + fileName);
                            return;
                        }
                    }
                }

                // Insert image URLs into the database
                nhaTroDAO.saveImages(nhaTroId, imageUrls);
                session.setAttribute("notification", "Add successfully!");

        Account acc = (Account) session.getAttribute("account");
        NhaTroDAO housesDao = new NhaTroDAO();
        //list nha tro ma account duoc quyen truy cap
        List<NhaTro> houses = null;

            String role = acc.getRole(); // Lưu giá trị role vào biến
            if (role != null) { // Kiểm tra xem role có phải là null không
                if (role.equals("landlord")) {
                    houses = housesDao.getAll();
                } else if (role.equals("manager")) {
                    QuanLiDAO qlDao = new QuanLiDAO();
                    QuanLi ql = qlDao.getQuanLiByIDAccount(acc.getID_Account());
                    if (ql != null) { // Kiểm tra ql có phải là null không
                        houses = qlDao.getNhaTroByManagerId(ql.getID_QuanLy());
                    }
                }
            } else {
                // Xử lý trường hợp role là null
                session.setAttribute("errorMessByRole", true);
            }

            if (houses == null) {
                session.setAttribute("errorMessByRole", true);
            } else {
                // Nha tro ma dc phep truy cap bang role
                session.setAttribute("housesByRole", houses);
            }
            // Role cua tai khoan dg truy cap
            session.setAttribute("role", role); // Sử dụng biến role đã lưu

                // Redirect to NhaTro list
                response.sendRedirect("nhatro-detail?id="+nhaTroId);
            } else if ("edit".equals(action)) {
                // Get NhaTro ID
                int nhaTroId = Integer.parseInt(request.getParameter("nhaTroId"));
                String tenNhaTro = request.getParameter("tenNhaTro");
                String diaChi = request.getParameter("diaChi");
                String moTa = request.getParameter("moTa");

                // Update NhaTro details
                NhaTro updatedNhaTro = nhaTroDAO.getNhaTroById(nhaTroId); // Assuming this method exists
                updatedNhaTro.setTenNhaTro(tenNhaTro);
                updatedNhaTro.setDia_chi(diaChi);
                updatedNhaTro.setMo_ta(moTa);
                nhaTroDAO.updateNhaTro2(updatedNhaTro);

                // Handle existing images removal
                String[] deleteImages = request.getParameterValues("deleteImages");
                if (deleteImages != null) {
                    for (String imageUrl : deleteImages) {
                        nhaTroDAO.deleteImage(nhaTroId, imageUrl); // Assuming deleteImage method exists
                    }
                }

                // Handle new image uploads
                String uploadDir = getServletContext().getRealPath("/uploads");
                File uploadDirectory = new File(uploadDir);

                if (!uploadDirectory.exists()) {
                    uploadDirectory.mkdir();
                }

                List<String> imageUrls = nhaTroDAO.getImagesForNhaTro(nhaTroId); // Assuming this method exists
                if (imageUrls.size() < 5) {
                    for (Part part : request.getParts()) {
                        if (part.getName().equals("images") && part.getSize() > 0) {
                            String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                            String uploadPath = uploadDir + File.separator + fileName;

                            if (imageUrls.size() < 5) { // Limit to 5 images
                                try {
                                    part.write(uploadPath);
                                    String imageUrl = "uploads/" + fileName;
                                    nhaTroDAO.saveImage(nhaTroId, imageUrl); // Assuming saveImage method exists
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error uploading file.");
                                    return;
                                }
                            }
                        }
                    }
                }
                
                session.setAttribute("notification", "Update successfully!");
               response.sendRedirect("nhatro-detail?id="+nhaTroId);
            } else if ("delete".equals(action)) {
                // Get NhaTro ID to delete
                int nhaTroId = Integer.parseInt(request.getParameter("nhaTroId"));

                try {
                    // Check if there are any associated rooms
                    if (nhaTroDAO.hasPhongTro(nhaTroId)) {
                        session.setAttribute("notificationErr", "Cannot delete: NhaTro has associated rooms!");
                    } else {
                        // First, delete any associated images
                        nhaTroDAO.deleteAnhNhaTro(nhaTroId);

                        // Then, delete the NhaTro
                        nhaTroDAO.deleteNhaTro(nhaTroId);
                        session.setAttribute("notification", "Delete successfully!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    session.setAttribute("notificationErr", "Error occurred during deletion!");
                }

                // Redirect back to NhaTro list
                response.sendRedirect("nhatro");
            }
        }
    }

}
