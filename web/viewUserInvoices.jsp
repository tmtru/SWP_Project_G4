<%@ page import="java.util.List" %>
<%@ page import="model.HoaDon" %>
<%@ page import="model.KhachThue" %>
<%@ page import="model.Account" %>
<%@ page import="dal.AccountDAO" %>
<%@ page import="dal.KhachThueDAO" %>
<%@ page import="dal.HoaDonDAO" %>
<%@ page session="true" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%
    // Lấy ID_Account từ session
    Integer accountId = (Integer) session.getAttribute("ID_Account");
    if (accountId == null) {
        // Nếu không có session, chuyển hướng tới trang đăng nhập
        response.sendRedirect("login.jsp");
        return;
    }

    // Khởi tạo DAO để lấy thông tin từ database
    AccountDAO accountDAO = new AccountDAO();
    KhachThueDAO khachThueDAO = new KhachThueDAO();
    HoaDonDAO hoaDonDAO = new HoaDonDAO();

    // Lấy thông tin account từ DAO
    Account account = accountDAO.getAccountById(accountId);

    // Lấy thông tin KhachThue
    KhachThue khachThue = khachThueDAO.getKhachThueByAccountId(accountId);

    // Lấy danh sách hóa đơn của khách thuê
    List<HoaDon> hoaDonList = hoaDonDAO.getHoaDonByKhachThueId(khachThue.getId());
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Danh sách Hóa Đơn</title>
        <script src="https://kit.fontawesome.com/aab0c35bef.js" crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background-color: lightgray;
                color: #333;
                margin: 0;
                padding: 0;
                font-size: 1.2rem;
            }
            .sidebar {
                background-color: #A78BFA;
                color: white;
                position: fixed;
                top: 0;
                left: 0;
                height: 100%;
                width: 300px;
                padding: 30px 10px;
                z-index: 100;
                font-size: 1.3rem;
            }
            .sidebar img.avatar {
                width: 150px;
                height: 150px;
                border-radius: 50%;
                border: 5px solid white;
                margin-bottom: 20px;
            }
            .menu-item {
                color: white;
                text-decoration: none;
                padding: 10px 0;
                border-bottom: 1px solid rgba(255, 255, 255, 0.5);
                display: block;
                text-align: left;
            }
            .container {
                margin-left: 320px;
                margin-right: 20px;
                padding: 30px;
                background-color: white;
                border-radius: 10px;
                min-height: 100vh;
            }
            h2 {
                font-size: 28px;
                color: #6E00FF;
                margin-bottom: 30px;
                border-bottom: 2px solid #A78BFA;
                padding-bottom: 10px;
            }
            .invoice-content {
                font-size: 1.2rem;
                line-height: 1.5;
                margin-top: 20px;
            }
            .breadcrumb {
                background-color: transparent;
                padding: 0;
                margin-left: 320px;
                font-size: 1.2rem;
            }
            .breadcrumb-item a {
                color: #6E00FF;
                text-decoration: none;
            }
        </style>
    </head>
    <body>
        <nav aria-label="breadcrumb" class="main-breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="home.jsp">Trang chủ</a></li>
                <li class="breadcrumb-item active" aria-current="page">Xem hóa đơn</li>
            </ol>
        </nav>
        <div class="sidebar">
            <div class="d-flex flex-column align-items-center text-center">
                <img src="assets/img/Avatar.jpg" alt="Avatar" class="avatar">
                <h4><%= account.getUsername() %></h4>
                <p>Role: <%= account.getRole() %></p>
            </div>
            <nav class="nav flex-column nav-pills nav-gap-y-1">
                <a href="profile.jsp" class="menu-item">Thông tin <%= account.getRole() %></a>
                <a href="changePassword.jsp" class="menu-item">Đổi mật khẩu</a>
                <% if ("tenant".equalsIgnoreCase(account.getRole())) { %>
                <a href="viewUserContracts.jsp" class="menu-item">Xem hợp đồng</a>
                <a href="viewUserInvoices.jsp" class="menu-item">Xem hóa đơn</a>
                <a href="maintainanceServlet" class="menu-item">Yêu cầu bảo trì</a>
                <% } %>
                <a href="home.jsp" class="menu-item">Về trang chủ</a>
            </nav>
        </div>

        <div class="container">
            <h2>Danh sách Hóa Đơn</h2>
            <div class="invoice-content">
                <% for (HoaDon hoaDon : hoaDonList) { 
                    // Lấy tên phòng cho từng hóa đơn
                    String tenPhongTro = hoaDonDAO.getRoomOfHoaDon(hoaDon.getID_HoaDon()).getTenPhongTro();
                %>
                <div class="card mb-3">
                    <div class="card-header">
                        <h5>ID Hóa Đơn: <%= hoaDon.getID_HoaDon() %> | Ngày: <%= hoaDon.getNgay() %></h5>
                    </div>
                    <div class="card-body">
                        <p><strong>Số tiền:</strong> <%= hoaDon.getTong_gia_tien() %></p>
                        <p><strong>Trạng thái:</strong> <%= hoaDon.getTrang_thai() %></p>
                        <p><strong>Tên phòng:</strong> <%= tenPhongTro %></p>
                    </div>
                </div>
                <% } %>
            </div>
        </div>

    </body>
</html>
