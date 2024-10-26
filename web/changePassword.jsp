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

    boolean isTenant = "tenant".equalsIgnoreCase(account.getRole());
    
boolean isManager = "manager".equalsIgnoreCase(account.getRole());
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đổi Mật Khẩu</title>
        <script src="https://kit.fontawesome.com/aab0c35bef.js" crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css" />
        <link rel="stylesheet" href="https://unpkg.com/aos@next/dist/aos.css" />
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

            .menu-item:last-child {
                border-bottom: none;
            }

            .breadcrumb {
                background-color: transparent;
                padding: 0;
                margin-left: 320px; /* Giữ nguyên để breadcrumb không bị che khuất */
                font-size: 1.2rem;
            }

            .breadcrumb-item a {
                color: #6E00FF;
                text-decoration: none;
            }

            .breadcrumb-item.active {
                color: #333;
            }

            .container {
                margin-left: 320px; /* Giữ khoảng cách với sidebar */
                margin-right: 20px;
                padding: 30px;
                background-color: white;
                height: calc(100vh - 80px);
                border-radius: 10px;
                overflow-y: auto; /* Thêm để cho phép cuộn nếu nội dung vượt quá chiều cao */
            }

            h1 {
                font-size: 2rem;
                color: #6E00FF;
                margin-bottom: 30px;
            }

            .form-container {
                display: flex;
                flex-direction: column;
                gap: 20px;
                max-width: 600px;
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
                margin-bottom: 5px;
            }

            .form-container button {
                padding: 15px;
                background: #A78BFA;
                color: white;
                border: none;
                border-radius: 25px;
                cursor: pointer;
                font-size: 16px;
                transition: background 0.3s ease;
            }

            .form-container button:hover {
                background: #6E00FF;
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
        <nav aria-label="breadcrumb" class="main-breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="home.jsp">Trang chủ</a></li>
                <li class="breadcrumb-item active" aria-current="page">Đổi mật khẩu</li>
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

                <% if (isManager) { %>
                <a href="adminMaintainanceServlet" class="menu-item">Xem yêu cầu bảo trì</a>
                <% } %>
                <a href="home.jsp" class="menu-item">Về trang chủ</a>
            </nav>
        </div>

        <!-- Change Password Container -->
        <div class="container">
            <h2>Đổi Mật Khẩu</h2>

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

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-whLSQoQJWlg5dmjXp1Av7kwix0f2QlnL+udt3zSK+XOo+l3qO3LeCcCNiP8Aj+gJ" crossorigin="anonymous"></script>
    </body>
</html>
