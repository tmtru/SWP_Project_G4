<%@page import="java.util.List"%>
<%@page import="java.util.Date"%> <!-- Added this import for Date -->
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

        .star-rating i {
            font-size: 1.5rem;
            cursor: pointer;
            margin: 0 2px;
            color: #FFD700;
        }

        #feedbackForm textarea {
            width: 100%;
            height: 60px;
            margin-top: 5px;
        }

        .feedback-section {
            margin-top: 10px;
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

            <a href="#" class="menu-item">Về trang chủ</a>
        </nav>
    </div> 

    <div class="container"> 
        <h1>Danh sách Hợp Đồng</h1>
          <%
            // Display the message if it exists
            String message = (String) request.getAttribute("message");
            if (message != null && !message.isEmpty()) {
        %>
            <div class="alert alert-info">
                <%= message %>
            </div>
        <%
            }
        %>

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
                <th>Feedback</th>
            </tr>
            <%
            for (HopDong hopDong : hopDongs) {
                String feedbackContent = hopDong.getNoi_dung();
                int rating = (hopDong.getDanh_gia() != null) ? Integer.parseInt(hopDong.getDanh_gia()) : 0;
                Date currentDate = new Date(); // Current date
                Date expirationDate = hopDong.getNgay_het_han(); // Expiration date
                boolean canAddFeedback = currentDate.compareTo(expirationDate) >= 0; // Check if current date >= expiration date
            %>
            <tr>
                <td><%= hopDong.getID_HopDong() %></td>
                <td><%= hopDong.getKhach_thue() %></td>
                <td><%= hopDong.getTen_phong() %></td>
                <td><%= hopDong.getNgay_gia_tri() %></td>
                <td><%= hopDong.getNgay_het_han() %></td>
                <td><%= hopDong.getTien_Coc() %></td>
                <td>
                    <%
                    if (feedbackContent != null && !feedbackContent.isEmpty()) {
                    %>
                        <div>
                            <strong>Feedback:</strong> <%= feedbackContent %> <br/>
                            <strong>Rating:</strong>
                            <div class="star-rating">
                                <% for (int i = 1; i <= 5; i++) { %>
                                    <i class="<%= (i <= rating) ? "fas" : "far" %> fa-star"></i>
                                <% } %>
                            </div>
                        </div>
                    <%
                    } else {
                        if (canAddFeedback) {
                    %>
                        <button onclick="toggleFeedbackForm(<%= hopDong.getID_HopDong() %>)" class="btn btn-primary">Add Feedback</button>
                        <div id="feedbackForm_<%= hopDong.getID_HopDong() %>" class="feedback-section" style="display: none;">
                            <form action="feedback" method="post">
                               
    
    <input type="hidden" name="idKhachThue" value="<%= hopDong.getID_KhachThue() %>">
    <input type="hidden" name="idPhong" value="<%= hopDong.getID_Phongtro() %>">
    <textarea name="noiDung" required placeholder="Enter your feedback"></textarea>
    <div class="star-rating" onclick="setRating(<%= hopDong.getID_HopDong() %>, event)">
        <i class="far fa-star" data-value="1"></i>
        <i class="far fa-star" data-value="2"></i>
        <i class="far fa-star" data-value="3"></i>
        <i class="far fa-star" data-value="4"></i>
        <i class="far fa-star" data-value="5"></i>
    </div>
    <input type="hidden" id="ratingInput_<%= hopDong.getID_HopDong() %>" name="danhGia" value="0">
    <button type="submit" class="btn btn-success mt-2">Submit Feedback</button>
</form>

                            </form>
                        </div>
                    <%
                        } else {
                            out.println("Feedback not available.");
                        }
                    }
                    %>
                </td>
            </tr>
            <%
                }
            %>
            </table>
        <%
            }
        %>
    </div>

    <script>
        function toggleFeedbackForm(hopDongId) {
            var form = document.getElementById('feedbackForm_' + hopDongId);
            if (form.style.display === 'none' || form.style.display === '') {
                form.style.display = 'block';
            } else {
                form.style.display = 'none';
            }
        }

        function setRating(hopDongId, event) {
            var stars = event.currentTarget.querySelectorAll('i');
            var ratingInput = document.getElementById('ratingInput_' + hopDongId);
            var rating = event.target.getAttribute('data-value');
            ratingInput.value = rating;

            stars.forEach((star, index) => {
                star.classList.remove('fas');
                star.classList.add('far');
                if (index < rating) {
                    star.classList.remove('far');
                    star.classList.add('fas');
                }
            });
        }
    </script>
</body> 
</html>
