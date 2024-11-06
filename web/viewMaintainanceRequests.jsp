<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %> 
<%@page import="model.KhachThue"%>
<%@page import="model.Account"%>
<%@page import="model.Phong"%>
<%@page import="model.ThietBiPhong"%>
<%@page import="dal.MaintainanceDAO" %>
<%@page import="dal.PhongDAO" %>
<%@page import="model.Maintainance" %>
<%@page import="dal.ThietBiPhongDAO" %>
<%@page session="true" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Danh sách yêu cầu bảo trì</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background-color: lightgray;
                color: #333;
                margin: 0;
                padding: 0;
                font-size: 1.2rem;
                padding-bottom: 20px; /* Giảm khoảng cách dưới cho body */
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
            .table-container {
                margin-left: 320px;
                margin-right: 20px;
                margin-top: 30px;
                background-color: white;
                border-radius: 10px;
                padding: 20px;
                height: calc(100vh - 120px); /* Chiều cao cố định với kích thước màn hình */
                overflow-y: auto; /* Thanh cuộn theo chiều dọc */
            }
            h2 {
                font-size: 28px;
                color: #6E00FF;
                margin-bottom: 20px;
                border-bottom: 2px solid #A78BFA;
                padding-bottom: 10px;
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
        </style>
        <script>
            function confirmAction(action, id) {
                var confirmation = confirm("Bạn có chắc chắn muốn " + (action === 'approve' ? "chấp thuận" : "từ chối") + " yêu cầu này?");
                if (confirmation) {
                    hideOtherButton(action, id);  // Ẩn nút khác khi xác nhận hành động
                    document.getElementById(action + '-' + id).submit();  // Gửi form khi đã xác nhận
                } else {
                    return false;  // Ngăn không gửi form khi hủy xác nhận
                }
            }

            function hideOtherButton(action, id) {
                var otherAction = action === 'approve' ? 'deny' : 'approve';
                document.getElementById(otherAction + '-' + id).style.display = 'none';  // Ẩn nút khác
            }

        </script>
    </head>
    <body>
        <%
            Account account = (Account) session.getAttribute("account");
        
        %>
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="home.jsp">Trang chủ</a></li>
                <li class="breadcrumb-item active" aria-current="page">Yêu cầu bảo trì</li>
            </ol>
        </nav>

        <div class="sidebar">
            <div class="d-flex flex-column align-items-center text-center">
                <img src="assets/img/Avatar.jpg" alt="Avatar" class="avatar mb-3">
                <h4><%= account.getUsername() %></h4>
                <p>Role: <%= account.getRole() %></p>
            </div>
            <nav class="nav flex-column nav-pills nav-gap-y-1">
                <a href="profile.jsp" class="menu-item">Thông tin <%= account.getRole() %></a>
                <a href="changePassword.jsp" class="menu-item">Đổi mật khẩu</a>
                <a href="adminMaintainanceServlet" class="menu-item">Xem yêu cầu bảo trì</a>
                <a href="home.jsp" class="menu-item">Về trang chủ</a>
            </nav>
        </div>

        <div class="table-container">
            <h2>Danh sách yêu cầu bảo trì</h2>
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Phòng</th>
                        <th>Tên thiết bị</th>
                        <th>Mô tả bảo trì</th>
                        <th>Trạng thái yêu cầu</th>
                        <th>Trạng thái chấp thuận</th>
                        <th>Tác vụ</th>
                    </tr>
                </thead>
                <tbody>
                    <%
    // Khai báo các đối tượng
    MaintainanceDAO maintainanceDAO = new MaintainanceDAO();
    PhongDAO phongDAO = new PhongDAO();
    ThietBiPhongDAO tbpDAO = new ThietBiPhongDAO();

    List<Maintainance> maintainanceList = (List<Maintainance>) request.getAttribute("maintainanceList");
    if (maintainanceList != null && !maintainanceList.isEmpty()) {
        for (Maintainance maintainance : maintainanceList) {
            Phong phong = phongDAO.getPhongById(maintainance.getID_Phong());
            ThietBiPhong tbp = tbpDAO.getThietBiPhongById(maintainance.getID_ThietBiPhong());
                    %>
                    <tr>
                        <td><%= phong != null ? phong.getTenPhongTro() : "Không tìm thấy" %></td>
                        <td><%= tbp != null ? tbp.getTenThietBi() : "Không tìm thấy" %></td>
                        <td><%= maintainance.getMo_ta() %></td>
                        <td>
                            <%
                                int trangThaiYeuCau = maintainance.getTrang_thai_yeu_cau();
                                String trangThaiYC;

                                switch (trangThaiYeuCau) {
                                    case 0:
                                        trangThaiYC = "Đang chờ";
                                        break;
                                    case 1:
                                        trangThaiYC = "Đã xem";
                                        break;
                                    case 2:
                                        trangThaiYC = "Đã hủy";
                                        break;
                                    default:
                                        trangThaiYC = "Không xác định"; 
                                        break;
                                }
                            %>
                            <%= trangThaiYC %>
                        </td>
                        <td>
                            <%
                                int trangThaiChapThuan = maintainance.getTrang_thai_chap_thuan();
                                String trangThaiCT;

                                switch (trangThaiChapThuan) {
                                    case 0:
                                        trangThaiCT = "Đang chờ";
                                        break;
                                    case 1:
                                        trangThaiCT = "Chấp thuận";
                                        break;
                                    case 2:
                                        trangThaiCT = "Từ chối";
                                        break;
                                    default:
                                        trangThaiCT = "Không xác định"; 
                                        break;
                                }
                            %>
                            <%= trangThaiCT %>

                        </td>
                        <td>
                            <% if (trangThaiChapThuan == 0) { %>
                            <form action="adminMaintainanceServlet" method="post" style="display:inline;" id="approve-<%= maintainance.getId_bao_tri() %>">
                                <input type="hidden" name="action" value="approve">
                                <input type="hidden" name="id" value="<%= maintainance.getId_bao_tri() %>">
                                <button type="submit" class="btn btn-success" onclick="return confirmAction('approve', <%= maintainance.getId_bao_tri() %>)">Chấp thuận</button>
                            </form>
                            <form action="adminMaintainanceServlet" method="post" style="display:inline;" id="deny-<%= maintainance.getId_bao_tri() %>">
                                <input type="hidden" name="action" value="deny">
                                <input type="hidden" name="id" value="<%= maintainance.getId_bao_tri() %>">
                                <button type="submit" class="btn btn-danger" onclick="return confirmAction('deny', <%= maintainance.getId_bao_tri() %>)">Từ chối</button>
                            </form>
                            <% } else if (trangThaiChapThuan == 1) { %>
                            <!-- Trạng thái đã được chấp thuận, chỉ hiển thị nút 'Chấp thuận' -->
                            <button class="btn btn-success" disabled>Đã chấp thuận</button>
                            <% } else if (trangThaiChapThuan == 2) { %>
                            <!-- Trạng thái đã bị từ chối, chỉ hiển thị nút 'Từ chối' -->
                            <button class="btn btn-danger" disabled>Đã từ chối</button>
                            <% } %>
                        </td>


                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="4" class="text-center">Không có yêu cầu bảo trì nào.</td>
                    </tr>
                    <%
                        }
                    %>


                </tbody>
            </table>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>