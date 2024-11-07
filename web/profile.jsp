<%@page import="model.KhachThue"%> 
<%@page import="model.Account"%> 
<%@page session="true" %> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html> 
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profile</title>
    <script src="https://kit.fontawesome.com/aab0c35bef.js" crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:ital,wght@0,200..800;1,200..800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css" />
    <link rel="stylesheet" href="https://unpkg.com/aos@next/dist/aos.css" />
    <style>
        body {
            background-color: lightgray;
            color: #333;
            margin: 0;
            padding: 0;
            font-size: 1.2rem; /* Tăng cỡ chữ tổng thể */
        }

        .sidebar {
            background-color: #A78BFA;
            color: white;
            position: fixed;
            top: 0;
            left: 0;
            height: 100%;
            width: 300px; /* Đặt chiều ngang cố định */
            padding: 30px 10px;
            z-index: 100;
            font-size: 1.3rem; /* Tăng cỡ chữ trong sidebar */
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

        .menu-item:last-child {
            border-bottom: none;
        }

        .profile-info {
            background-color: white;
            border-radius: 10px;
            padding: 30px;
            margin-left: 320px; 
            margin-right: 20px;
            height: calc(100vh - 80px); 
            display: flex;
            flex-direction: column;
            justify-content: space-around; 
            font-size: 1.4rem; 
        }

        .info-item h4 {
            margin-bottom: 5px;
            font-weight: bold;
            color: #333;
        }

        .info-item p {
            color: #666;
        }

        .breadcrumb {
            background-color: transparent;
            padding: 0;
            margin-left: 320px; /* Đặt khoảng cách bên trái để tránh sidebar */
            font-size: 1.2rem; /* Tăng cỡ chữ cho breadcrumb */
        }

        .breadcrumb-item a {
            color: #6E00FF;
            text-decoration: none;
        }

        .breadcrumb-item.active {
            color: #333;
        }
        h2 {
                font-size: 28px;
                color: #6E00FF;
                margin-bottom: 30px;
                border-bottom: 2px solid #A78BFA;
                padding-bottom: 10px;
            }
    </style>
</head>
<body>
    <%
        // Lấy ID_Account từ session
        Integer accountId = (Integer) session.getAttribute("ID_Account");
        if (accountId == null) {
            // Nếu không có session, chuyển hướng tới trang đăng nhập
            response.sendRedirect("login.jsp");
            return;
        }

        // Khởi tạo DAO để lấy thông tin từ database
        dal.AccountDAO accountDAO = new dal.AccountDAO();
        dal.KhachThueDAO khachThueDAO = new dal.KhachThueDAO();

        // Lấy thông tin account từ DAO
        model.Account account = accountDAO.getAccountById(accountId);

        // Nếu không tìm thấy account, báo lỗi
        if (account == null) {
            out.println("<p>Error loading account data.</p>");
            return;
        }

        // Lấy thông tin KhachThue nếu có
        model.KhachThue khachThue = khachThueDAO.getKhachThueByAccountId(accountId);
        
        if (khachThue != null) {
            session.setAttribute("ID_KhachThue", khachThue.getId());
        }

        // Kiểm tra role của người dùng
        boolean isTenant = "tenant".equalsIgnoreCase(account.getRole());
        
        boolean isManager = "manager".equalsIgnoreCase(account.getRole());
    %>

    <nav aria-label="breadcrumb" class="main-breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="home.jsp">Trang chủ</a></li>
            <li class="breadcrumb-item active" aria-current="page">Thông tin cá nhân</li>
        </ol>
    </nav>

    <!-- Sidebar -->
    <div class="sidebar">
        <div class="d-flex flex-column align-items-center text-center">
            <img src="assets/img/Avatar.jpg" alt="Avatar" class="avatar mb-3">
            <h4><%= account.getUsername() %></h4>
            <p>Role: <%= account.getRole() %></p>
        </div>
        <nav class="nav flex-column nav-pills nav-gap-y-1">
            <a href="profile.jsp" class="menu-item">Thông tin <%= account.getRole() %></a>
            <a href="changePassword.jsp" class="menu-item">Đổi mật khẩu</a>

            <% if (isTenant) { %>
            <a href="viewUserContracts.jsp" class="menu-item">Xem hợp đồng</a>
            <a href="viewUserInvoices.jsp" class="menu-item">Xem hóa đơn</a>
            <a href="maintainanceServlet" class="menu-item">Yêu cầu bảo trì</a>
            <% } %>

            <a href="home.jsp" class="menu-item">Về trang chủ</a>
        </nav>
    </div>

    <!-- Main Content -->
    <div class="profile-info">
        <h2 style="color: #6E00FF">Thông tin tài khoản</h2>
        <div class="info-item">
            <h4 class="mb-0">Email</h4>
            <p class="text-muted"><%= account.getEmail() %></p>
        </div>
        <div class="info-item">
            <h4 class="mb-0">Ngày sinh</h4>
            <p class="text-muted"><%= (khachThue != null) ? khachThue.getDob() : "Chưa có thông tin hiển thị" %></p>
        </div>
        <div class="info-item">
            <h4 class="mb-0">Số CCCD</h4>
            <p class="text-muted"><%= (khachThue != null) ? khachThue.getCccd() : "Chưa có thông tin hiển thị" %></p>
        </div>
        <div class="info-item">
            <h4 class="mb-0">Nghề nghiệp</h4>
            <p class="text-muted"><%= (khachThue != null) ? khachThue.getJob() : "Chưa có thông tin hiển thị" %></p>
        </div>
        <div class="info-item">
            <h4 class="mb-0">Số điện thoại</h4>
            <p class="text-muted"><%= (khachThue != null) ? khachThue.getPhone() : "Chưa có thông tin hiển thị" %></p>
        </div>
        <div class="info-item">
            <h4 class="mb-0">Địa chỉ thường trú</h4>
            <p class="text-muted"><%= (khachThue != null) ? khachThue.getHk_thuong_tru() : "Chưa có thông tin hiển thị" %></p>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-whLSQoQJWlg5dmjXp1Av7kwix0f2QlnL+udt3zSK+XOo+l3qO3LeCcCNiP8Aj+gJ" crossorigin="anonymous"></script>
</body>
</html>