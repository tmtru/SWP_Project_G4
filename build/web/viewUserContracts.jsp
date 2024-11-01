<%@page import="java.util.List"%>
<%@page import="model.HopDong"%>
<%@page import="model.KhachThue"%> 
<%@page import="model.Account"%>
<%@page session="true" %> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
%>

<!DOCTYPE html> 
<html lang="en"> 
<head> 
    <meta charset="UTF-8"> 
    <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
    <title>Danh sách Hợp Đồng</title> 
    <script src="https://kit.fontawesome.com/aab0c35bef.js" crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Plus Jakarta Sans', sans-serif;
            background-color: #f4f7f6;
            background-image: url('assets/img/decor-phong-ngu-9.jpg');
            color: #333;
            margin: 0;
            padding: 0;
            font-size: 1.2rem; /* Đặt cỡ chữ cho toàn bộ trang */
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
            font-size: 1.3rem; /* Cỡ chữ cho sidebar */
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
            text-align: center;
        }

        .menu-item:last-child {
            border-bottom: none;
        }

        .container {
            margin-left: 320px;
            padding: 30px;
            background-color: white;
            border-radius: 10px;
            min-height: 100vh;
            font-size: 1.2rem; /* Đặt cỡ chữ trong nội dung chính */
        }

        h1 {
            font-size: 28px;
            color: #6E00FF;
            margin-bottom: 30px;
            border-bottom: 2px solid #A78BFA;
            padding-bottom: 10px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        table, th, td {
            border: 1px solid #ddd;
        }

        th, td {
            padding: 10px;
            text-align: left;
            font-size: 1.2rem; /* Cỡ chữ trong bảng */
        }

        th {
            background-color: #A78BFA;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
    </style> 
</head> 
<body> 
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
                <a href="profileServlet?action=viewContracts" class="menu-item">Xem hợp đồng</a>
                <a href="#" class="menu-item">Xem hóa đơn</a>
                <a href="#" class="menu-item">Yêu cầu bảo trì</a>
            <% } %>

            <a href="home.jsp" class="menu-item">Về trang chủ</a>
        </nav>
    </div> 

    <div class="container"> 
        <h1>Danh sách Hợp Đồng</h1>

        <%
            List<HopDong> hopDongs = (List<HopDong>) request.getAttribute("hopDongs");

            if (hopDongs == null || hopDongs.isEmpty()) {
        %>
            <p>Không có hợp đồng nào.</p>
        <%
            } else {
        %>
            <table>
                <tr>
                    <th>ID Hợp Đồng</th>
                    <th>ID Khách Thuê</th>
                    <th>ID Phòng Trọ</th>
                    <th>Ngày Giá Trị</th>
                    <th>Ngày Hết Hạn</th>
                    <th>Tiền Cọc</th>
                </tr>
                <%
                    for (HopDong hopDong : hopDongs) {
                %>
                <tr>
                    <td><%= hopDong.getID_HopDong() %></td>
                    <td><%= hopDong.getID_KhachThue() %></td>
                    <td><%= hopDong.getID_Phongtro() %></td>
                    <td><%= hopDong.getNgay_gia_tri() %></td>
                    <td><%= hopDong.getNgay_het_han() %></td>
                    <td><%= hopDong.getTien_Coc() %></td>
                </tr>
                <%
                    }
                %>
            </table>
        <%
            }
        %>
    </div> 

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body> 
</html>
