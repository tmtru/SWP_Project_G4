<%@ page import="java.util.List" %> 
<%@page import="model.KhachThue"%>
<%@page import="model.Account"%>
<%@page import="model.Phong"%>
<%@page import="model.ThietBiPhong"%>
<%@page import="dal.MaintainanceDAO" %>
<%@page import="model.Maintainance" %>
<%@page import="dal.ThietBiPhongDAO" %>
<%@page session="true" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Profile</title>
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
            .breadcrumb-item.active {
                color: #333;
            }
            .table-container {
                max-height: 200px; /* Thay ??i chi?u cao t?i ?a n?u c?n */
                overflow-y: auto;  /* Th�m thanh cu?n khi n?i dung v??t qu� chi?u cao t?i ?a */
            }
            .table-fixed {
                table-layout: fixed; /* C? ??nh chi?u r?ng c�c c?t */
                width: 100%; /* Chi?u r?ng b?ng s? t? ??ng t??ng th�ch */
            }
            .table-fixed th, .table-fixed td {
                overflow: hidden; /* ?n ph?n n?i dung tr�n ra ngo�i */
                text-overflow: ellipsis; /* Th�m d?u ba ch?m (...) n?u n?i dung qu� d�i */
                white-space: nowrap; /* Kh�ng cho ph�p xu?ng d�ng trong c�c � */
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
            Account account = (Account) session.getAttribute("account");
            List<Phong> listPhong = (List<Phong>) request.getAttribute("listPhong");
            List<ThietBiPhong> listThietBiPhong = (List<ThietBiPhong>) request.getAttribute("listThietBiPhong");
            String selectedRoom = (String) request.getAttribute("selectedRoom");
            boolean isTenant = session.getAttribute("ID_KhachThue") != null;
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
                <% if (isTenant) { %>
                <a href="viewUserContracts.jsp" class="menu-item">Xem hợp đồng</a>
                <a href="viewUserInvoices.jsp" class="menu-item">Xem hóa đơn</a>
                <a href="maintainanceServlet" class="menu-item" onclick="document.getElementById('roomForm').submit();">Yêu cầu bảo trì</a>
                <% } %>
                <a href="home.jsp" class="menu-item">Về trang chủ</a>
            </nav>
        </div>

        <div class="profile-info">
            <h2>Yêu cầu bảo trì thiết bị phòng</h2>
            <h3>Chọn phòng:</h3>
            <form id="roomForm" action="maintainanceServlet" method="post">
                <select class="form-select" name="selectedRoom" onchange="this.form.submit()">
                    <option value="">-- Chọn phòng --</option>
                    <% if (listPhong != null) {
                    for (Phong phong : listPhong) { %>
                    <option value="<%= phong.getID_Phong() %>" <%= String.valueOf(phong.getID_Phong()).equals(selectedRoom) ? "selected" : "" %>>
                        <%= phong.getTenPhongTro() %> - Tầng <%= phong.getTang() %>
                    </option>
                    <% }} %>
                </select>
            </form>

            <h3>Danh sách thiết bị trong phòng:</h3>
            <table class="table table-bordered table-container table-fixed">
                <thead>
                    <tr>
                        <th>Tên thiết bị</th>
                        <th>Mô tả</th>
                        <th>Số lượng</th>
                        <th>Trạng thái</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <% if (listThietBiPhong != null && !listThietBiPhong.isEmpty()) {
                    for (ThietBiPhong device : listThietBiPhong) { %>
                    <tr>
                        <td><%= device.getTenThietBi() %></td>
                        <td><%= device.getMo_ta() %></td>
                        <td><%= device.getSo_luong() %></td>
                        <td><%= device.getTrang_thai() %></td>
                        <td>
                            <% if (!"Abnormal".equals(device.getTrang_thai())) { %>
                            <form action="requestMaintenance.jsp" method="get">
                                <input type="hidden" name="idThietBiPhong" value="<%= device.getID_ThietBiPhong() %>">
                                <input type="hidden" name="idPhong" value="<%= device.getID_Phong() %>">
                                <button type="submit" class="btn btn-warning">Yêu cầu bảo trì</button>
                            </form>
                            <% } else { %>
                            <span>Đã yêu cầu</span>
                            <% } %>
                        </td>
                    </tr>
                    <% }} else { %>
                    <tr>
                        <td colspan="4" class="text-center">Không có thiết bị nào trong phòng này.</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>

            <h3>Danh sách yêu cầu bảo trì:</h3>
            <table class="table table-bordered table-container table-fixed">
                <thead>
                    <tr>
                        <th>Tên thiết bị</th>
                        <th>Mô tả bảo trì</th>
                        <th>Ngày gửi</th>
                        <th>Trạng thái yêu cầu</th>
                        <th>Chấp thuận</th>
                        <th>Tác vụ</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                    MaintainanceDAO maintainanceDAO = new MaintainanceDAO();
                    List<Maintainance> maintainanceList = maintainanceDAO.getMaintainanceRequests();
                    ThietBiPhongDAO thietBiPhongDAO = new ThietBiPhongDAO();

                    for (Maintainance mt : maintainanceList) { 
                        ThietBiPhong device = thietBiPhongDAO.getThietBiPhongById(mt.getID_ThietBiPhong());
                    %>
                    <tr>
                        <td><%= device != null ? device.getTenThietBi() : "Không xác định" %></td> 
                        <td><%= mt.getMo_ta() %></td>
                        <td><%= mt.getThoi_gian() %></td>
                        <td>
                            <%
                                int trangThaiYeuCau = mt.getTrang_thai_yeu_cau();
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
                                int trangThaiChapThuan = mt.getTrang_thai_chap_thuan();
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
                            <form action="editMaintainance.jsp" method="get" style="display:inline;">
                                <input type="hidden" name="id" value="<%= mt.getId_bao_tri() %>">
                                <input type="hidden" name="idThietBiPhong" value="<%= device.getID_ThietBiPhong() %>">
                                <input type="hidden" name="idPhong" value="<%= device.getID_Phong() %>">
                                <button type="submit" class="btn btn-primary">Sửa</button>
                            </form>
                            <form action="cancelMaintainanceRequestServlet" method="post" style="display:inline;">
                                <input type="hidden" name="id" value="<%= mt.getId_bao_tri() %>">
                                <button type="submit" class="btn btn-danger" value="cancel">Hủy</button>
                            </form>
                            <% } else if (trangThaiChapThuan == 1) { %>
                            <button class="btn btn-success" disabled>Đã chấp thuận</button>
                            <% } else if (trangThaiChapThuan == 2) { %>
                            <button class="btn btn-danger" disabled>Đã từ chối</button>
                            <% } %>
                        </td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>