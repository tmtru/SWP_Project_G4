<%@page import="model.Account"%>
<%@page session="true" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    Integer accountId = (Integer) session.getAttribute("ID_Account");
    if (accountId == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    dal.AccountDAO accountDAO = new dal.AccountDAO();
    model.Account account = accountDAO.getAccountById(accountId);

    if (account == null) {
        out.println("<p>Error loading account data.</p>");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đổi Mật Khẩu</title>
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

            .sidebar .menu-item {
                padding: 15px;
                border-bottom: 1px solid rgba(255, 255, 255, 0.1);
                cursor: pointer;
                color: white;
                text-align: left;
                font-size: 18px;
                transition: background 0.3s ease;
                text-decoration: none;
                display: block;
            }

            .sidebar .menu-item:hover {
                background-color: #764ba2;
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

            .form-container {
                display: grid;
                gap: 20px;
            }

            .form-container input {
                width: 100%;
                padding: 15px;
                border-radius: 10px;
                border: 1px solid #ddd;
                font-size: 16px;
            }

            .form-container label {
                font-weight: bold;
                color: #555;
                display: block;
                margin-bottom: 5px;
            }

            .form-container button {
                padding: 15px;
                background: #a18cd1;
                color: white;
                border: none;
                border-radius: 25px;
                cursor: pointer;
                font-size: 16px;
                transition: background 0.3s ease;
            }

            .form-container button:hover {
                background: #764ba2;
            }

            .error, .success {
                font-size: 16px;
                margin-top: 10px;
            }

            .error {
                color: red;
            }

            .success {
                color: green;
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
                
                <a href="profile.jsp" class="menu-item">Thông tin <%= account.getRole() %></a>
                <a href="changePassword.jsp" class="menu-item">Đổi mật khẩu</a>
                <a href="profileServlet?action=viewContracts" class="menu-item" style="color: white; text-decoration: none;">Xem hợp đồng</a>
                <div class="menu-item">Xem hóa đơn</div>
                <div class="menu-item">Yêu cầu bảo trì</div>
                <a href="home.jsp" class="menu-item" style="color: white; text-decoration: none;">Về trang chủ</a>
            </div>

            <!-- Change Password Container -->
            <div class="container">
                <h1>Đổi Mật Khẩu</h1>

                <form action="changepassword" method="post" class="form-container">
                    <div>
                        <label for="currentPassword">Mật khẩu cũ:</label>
                        <input type="password" id="currentPassword" name="currentPassword" required>
                    </div>

                    <div>
                        <label for="newPassword">Mật khẩu mới:</label>
                        <input type="password" id="newPassword" name="newPassword" required>
                    </div>

                    <div>
                        <label for="confirmNewPassword">Xác nhận mật khẩu mới:</label>
                        <input type="password" id="confirmNewPassword" name="confirmNewPassword" required>
                    </div>

                    <button type="submit">Đổi mật khẩu</button>

                    <!-- Error or Success messages -->
                    <div class="error">
                        <% 
                            String error = (String) request.getAttribute("error");
                            if (error != null) {
                                out.println(error);
                            }
                        %>
                    </div>
                    <div class="success">
                        <% 
                            String success = (String) request.getAttribute("success");
                            if (success != null) {
                                out.println(success);
                            }
                        %>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
