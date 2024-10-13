<%@page import="model.KhachThue"%> 
<%@page import="model.Account"%>
<%@page session="true" %> 
<%@page contentType="text/html" pageEncoding="UTF-8"%> <!-- Chỉ cần dòng này -->

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
%>

<!DOCTYPE html> 
<html lang="en"> 
    <head> 
        <meta charset="UTF-8"> 
        <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
        <title>Profile</title> 
        <style>
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background: linear-gradient(to bottom, #F6F5FF, #FFFFFF);
                color: #333;
                margin: 0;
                padding: 0;
                display: flex;
                justify-content: center;
                align-items: center;
                min-height: 100vh;
            }
            .layout {
                display: flex;
                width: 1200px;
                background-color: white;
                border-radius: 15px;
                box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
                overflow: hidden;
            }
            .sidebar {
                width: 300px;
                background-color: #a18cd1;
                color: white;
                padding: 30px;
                display: flex;
                flex-direction: column;
            }
            .sidebar img.avatar {
                width: 150px;
                height: 150px;
                border-radius: 50%;
                margin: 0 auto 20px;
                border: 5px solid white;
            }
            .sidebar h2 {
                font-size: 24px;
                margin-bottom: 5px;
                text-align: center;
            }
            .sidebar p {
                font-size: 18px;
                margin-bottom: 30px;
                text-align: center;
            }
            .menu-item {
                font-size: 18px;
                margin-bottom: 15px;
                padding: 10px 0;
                border-bottom: 1px solid rgba(255, 255, 255, 0.5);
            }
            .menu-item:last-child {
                border-bottom: none;
            }
            .container {
                flex-grow: 1;
                padding: 30px;
            }
            h1 {
                font-size: 28px;
                color: #333;
                margin-bottom: 30px;
                border-bottom: 2px solid #a18cd1;
                padding-bottom: 10px;
            }
            .profile-info {
                display: grid;
                grid-template-columns: repeat(2, 1fr);
                gap: 20px;
            }
            .profile-info > div {
                background-color: #f8f9fa;
                padding: 15px;
                border-radius: 10px;
                box-shadow: 0 2px 5px rgba(0,0,0,0.05);
            }
            .profile-info label {
                font-weight: bold;
                display: block;
                margin-bottom: 5px;
            }
            .profile-info span {
                display: block;
                color: #555;
            }
            .stats {
                display: flex;
                justify-content: space-around;
                margin-top: 30px;
            }

            .stat-item {
                text-align: center;
            }

            .stat-item .value {
                font-size: 24px;
                font-weight: bold;
                color: #a18cd1;
            }

            .stat-item .label {
                font-size: 14px;
                color: #666;
            }
        </style> 
    </head> 
    <body> 
        <div class="layout"> 
            <!-- Sidebar -->
            <div class="sidebar"> 
                <img src="assets/img/Avatar.jpg" alt="Avatar" class="avatar"> 
                <h2><%= account.getUsername() %></h2> 
                <p>Role: <%= account.getRole() %></p> 

                <!-- Form cho các lựa chọn trong sidebar -->
                <a href="profile.jsp" class="menu-item" style="color: white; text-decoration: none;">Thông tin <%= account.getRole() %></a>
                <a href="changePassword.jsp" class="menu-item" style="color: white; text-decoration: none;">Đổi mật khẩu</a>
                <a href="profileServlet?action=viewContracts" class="menu-item" style="color: white; text-decoration: none;">Xem hợp đồng</a>
                <div class="menu-item">Xem hóa đơn</div>
                <div class="menu-item">Yêu cầu bảo trì</div>
                <a href="home.jsp" class="menu-item" style="color: white; text-decoration: none;">Về trang chủ</a>
            </div> 

            <!-- Profile Container -->
            <div class="container"> 
                <h1>Thông tin tài khoản</h1> 

                <div class="profile-info"> 
                    <div> 
                        <label>Email:</label> 
                        <span><%= account.getEmail() %></span> 
                    </div> 
                    <div> 
                        <label>Ngày sinh:</label> 
                        <span><%= (khachThue != null) ? khachThue.getDob() : "Chưa có thông tin" %></span> 
                    </div> 
                    <div> 
                        <label>Số CCCD:</label> 
                        <span><%= (khachThue != null) ? khachThue.getCccd() : "Chưa có thông tin" %></span> 
                    </div> 
                    <div> 
                        <label>Nghề nghiệp:</label> 
                        <span><%= (khachThue != null) ? khachThue.getJob() : "Chưa có thông tin" %></span> 
                    </div> 
                    <div> 
                        <label>Số điện thoại:</label> 
                        <span><%= (khachThue != null) ? khachThue.getPhone() : "Chưa có thông tin" %></span> 
                    </div> 
                    <div> 
                        <label>Hộ khẩu thường trú:</label> 
                        <span><%= (khachThue != null) ? khachThue.getAddress() : "Chưa có thông tin" %></span> 
                    </div> 
                </div>
                <div class="stats">
                    <div class="stat-item">
                        <div class="value">3</div>
                        <div class="label">Active Contracts</div>
                    </div>
                    <div class="stat-item">
                        <div class="value">12</div>
                        <div class="label">Completed Payments</div>
                    </div>
                </div>
            </div> 
        </div> 
    </body> 
</html>
