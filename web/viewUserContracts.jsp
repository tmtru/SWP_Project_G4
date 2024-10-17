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
        }
        th {
            background-color: #a18cd1;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
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

            <!-- Các menu item trong sidebar -->
            <a href="profile.jsp" class="menu-item" style="color: white; text-decoration: none;">Thông tin <%= account.getRole() %></a>
            <a href="changePassword.jsp" class="menu-item" style="color: white; text-decoration: none;">Đổi mật khẩu</a>
            <a href="profileServlet?action=viewContracts" class="menu-item" style="color: white; text-decoration: none;">Xem hợp đồng</a>
            <div class="menu-item">Xem hóa đơn</div>
            <div class="menu-item">Yêu cầu bảo trì</div>
            <a href="home.jsp" class="menu-item" style="color: white; text-decoration: none;">Về trang chủ</a>
        </div> 

        <!-- Container cho hợp đồng -->
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
    </div> 
</body> 
</html>
