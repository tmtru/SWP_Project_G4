<%@ page import="java.util.List" %>  
<%@ page import="model.HoaDon" %>
<%@ page import="model.KhachThue" %>
<%@ page import="model.Account" %>
<%@ page import="model.DichVu" %>
<%@ page import="dal.AccountDAO" %>
<%@ page import="dal.KhachThueDAO" %>
<%@ page import="dal.HoaDonDAO" %>
<%@ page import="java.text.DecimalFormat" %>
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
    
DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Danh sách Hóa Đơn</title>
        <script src="https://kit.fontawesome.com/aab0c35bef.js" crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <style>
            body {
                background-color: lightgray;
                color: #333;
                margin: 0;
                padding: 0;
                font-size: 1rem; /* Giảm kích thước phông chữ */
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
                font-size: 1rem; /* Giảm kích thước phông chữ trong nội dung hóa đơn */
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
            .invoice-header {
                padding: 10px;
                border-radius: 5px;
                margin-bottom: 10px;
            }
            .status-header {
                padding: 5px;
                border-radius: 5px;
                font-weight: bold;
                display: inline-block; /* Thu gọn div trạng thái */
            }
            .payment-button {
                position: absolute; /* Đặt nút thanh toán ở góc dưới cùng bên trái */
                bottom: 10px;
                right: 10px;
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
                    String tenPhongTro = hoaDonDAO.getRoomOfHoaDon(hoaDon.getID_HoaDon()).getTenPhongTro();
                    int trangThai = hoaDon.getTrang_thai();
                    String statusClass = (trangThai == 0) ? "bg-danger text-white" : "bg-success text-white"; // Màu nền cho trạng thái
                %>
                <div class="card mb-3 position-relative"> <!-- Thêm class position-relative cho card -->
                    <div class="invoice-header <%= statusClass %>">
                        <h5>Mã Hóa Đơn: <%= hoaDon.getID_HoaDon() %> | Ngày tạo: <%= hoaDon.getNgay() %></h5>
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-4">
                                <p><strong>Tổng tiền:</strong> <%= decimalFormat.format(hoaDon.getTong_gia_tien()) %> VND</p>
                                <p><strong>Người tạo:</strong> <%= hoaDon.getNguoiTao() %></p>
                                <p><strong>Phòng:</strong> <%= tenPhongTro %></p>
                            </div>
                            <div class="col-md-4">
                                <p><strong>Dịch vụ sử dụng:</strong> 
                                    <%
                                        List<DichVu> dichVus = hoaDon.getDichVus();
                                        if (dichVus != null && !dichVus.isEmpty()) {
                                            for (int i = 0; i < dichVus.size(); i++) {
                                                DichVu dichVu = dichVus.get(i);
                                                out.print(dichVu.getTenDichVu());
                                                if (i < dichVus.size() - 1) {
                                                    out.print(", "); 
                                                }
                                            }
                                        } else {
                                            out.print("Không có dịch vụ nào");
                                        }
                                    %>
                                </p>
                            </div>
                            <div class="col-md-4">
                                <div class="status-header <%= statusClass %>">
                                    <strong>Trạng thái:</strong> 
                                    <%
                                        if (trangThai == 0) {
                                            out.print("Chưa thanh toán");
                                        } else if (trangThai == 1) {
                                            out.print("Đã thanh toán");
                                        } else {
                                            out.print("Trạng thái không xác định");
                                        }
                                    %>
                                </div>
                            </div>
                        </div>
                        <button class="btn btn-primary payment-button" data-bs-toggle="modal" data-bs-target="#paymentModal" data-qr="assets/img/QR.jpg" data-amount="<%= decimalFormat.format(hoaDon.getTong_gia_tien()) %>">Thanh toán</button>
                    </div>
                </div>
                <% } %>
            </div>
        </div>

        <!-- Modal -->
        <div class="modal fade" id="paymentModal" tabindex="-1" aria-labelledby="paymentModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="paymentModalLabel">Thanh toán Hóa Đơn</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <img id="qrCodeImage" src="" alt="QR Code" class="img-fluid" />
                        <p><strong>Số tiền cần phải trả:</strong> <span id="amountToPay"></span></p>
                        <p><strong>Nội dung chuyển khoản:</strong> [Tên phòng + Tháng]</p>
                        <p class="text-danger"><strong>Cảnh báo:</strong> Yêu cầu điền nội dung và chuyển khoản đúng quy định</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    </div>
                </div>
            </div>
        </div>


        <script>
            $(document).ready(function () {
                $('#paymentModal').on('show.bs.modal', function (event) {
                    var button = $(event.relatedTarget); // Nút kích hoạt modal
                    var qrCodeSrc = button.data('qr'); // Lấy giá trị QR code từ thuộc tính data-qr
                    var amountToPay = button.data('amount'); // Lấy số tiền từ thuộc tính data-amount
                    var modal = $(this);
                    modal.find('#qrCodeImage').attr('src', qrCodeSrc); // Cập nhật đường dẫn hình ảnh QR code
                    modal.find('#amountToPay').text(amountToPay + " VND"); // Cập nhật số tiền cần phải trả
                });
            });
        </script>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
