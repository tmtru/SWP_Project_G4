<%@ page import="java.util.List" %> 
<%@ page import="model.HopDong" %>
<%@ page import="model.KhachThue" %>
<%@ page import="model.Account" %>
<%@ page import="model.ChuTro" %>
<%@ page import="model.Phong" %>
<%@ page import="model.KhachThuePhu" %>
<%@ page import="dal.AccountDAO" %>
<%@ page import="dal.KhachThueDAO" %>
<%@ page import="dal.HopDongDAO" %>
<%@ page import="dal.ChuTroDAO" %>
<%@ page import="dal.PhongDAO" %>
<%@ page import="dal.KhachThuePhuDAO" %>
<%@ page session="true" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%
    Integer accountId = (Integer) session.getAttribute("ID_Account");
    if (accountId == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    AccountDAO accountDAO = new AccountDAO();
    KhachThueDAO khachThueDAO = new KhachThueDAO();
    ChuTroDAO chutroDAO = new ChuTroDAO();
    HopDongDAO hopDongDAO = new HopDongDAO();
    PhongDAO phongDAO = new PhongDAO();
    KhachThuePhuDAO ktpDAO = new KhachThuePhuDAO();

    Account account = accountDAO.getAccountById(accountId);

    KhachThue khachThue = khachThueDAO.getKhachThueByAccountId(accountId);
    
    List<HopDong> hopDongList = hopDongDAO.getHopDongsByKhachThueID(khachThue.getId());

    ChuTro chutro = null;
    chutro = chutroDAO.getChuTroById(1); 
    
    List<Phong> danhSachPhong = phongDAO.getRoomsByKhachThueId(khachThue.getId()); 

    List<KhachThuePhu> ktps = ktpDAO.getKhachThuePhuByKhachThueId(khachThue.getId());

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
            .contract-content {
                font-size: 1.2rem;
                line-height: 1.5;
                margin-top: 20px;
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
    </head>
    <body>
        <nav aria-label="breadcrumb" class="main-breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="home.jsp">Trang chủ</a></li>
                <li class="breadcrumb-item active" aria-current="page">Xem hợp đồng</li>
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
            <h2>Hợp Đồng Thuê Phòng</h2>
            <div class="contract-content">
                <p style="text-align: center; font-size: 33px;"><strong>CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM</strong></p>
                <p style="text-align: center; font-size: 23px;"><strong>Độc lập - Tự do - Hạnh phúc</strong></p>
                <p style="text-align: center; font-size: 33px;"><strong>HỢP ĐỒNG THUÊ PHÒNG TRỌ</strong></p>
                <p style="font-size: 25px;"><strong>Chúng tôi gồm:</strong></p>
                <p style="font-size: 25px;"><strong>1. Đại diện bên cho thuê phòng trọ (Bên A):</strong></p>
                <p style="font-size: 23px;">Ông/bà: <%= chutro != null ? chutro.getName() : "Chưa có thông tin" %><br>
                    Sinh ngày: <%= chutro != null ? chutro.getDob() : "Chưa có thông tin" %><br>
                    CMND số: <%= chutro != null ? chutro.getCccd() : "Chưa có thông tin" %><br>
                    Số điện thoại: <%= chutro != null ? chutro.getPhone() : "Chưa có thông tin" %></p>
                <p style="font-size: 25px;"><strong>2. Bên thuê phòng trọ (Bên B):</strong></p>
                <p style="font-size: 23px;">Ông/bà: <%= khachThue.getName() %><br>
                    Sinh ngày: <%= khachThue.getDob() %><br>
                    HK thường trú: <%= khachThue.getHk_thuong_tru() %><br>
                    CMND số: <%= khachThue.getCccd() %> <br>
                    Ngày cấp CMND: <%= khachThue.getNgay_cap() %> <br>
                    Nơi cấp CMND: <%= khachThue.getNoi_cap() %><br>
                    Số điện thoại: <%= khachThue.getPhone() %> <br>
                    <p style="font-size: 25px;"><strong>Thành viên khác: </strong></p>
                    <% 
                        int index = 1;
                        for (KhachThuePhu ktp : ktps) { 
                    %>
                            Thành viên <%= index %>: <br>
                            Tên thành viên: <%= ktp.getTenKhach() %><br>
                            Sinh ngày: <%= ktp.getNgaySinh() %><br>
                            Nơi đăng ký HK thường trú: <%= ktp.getHkThuongTru() %><br>
                            CMND số: <%= ktp.getCccd() %><br>
                            Số điện thoại: <%= ktp.getSdt() %><br> 
                            <br>
                    <%
                            index++; // Tăng biến đếm lên 1 sau mỗi lần lặp
                        } 
                    %>
                    </p>
                <p style="font-size: 25px;"><strong>Bên A đồng ý cho bên B thuê 01 phòng ở tại địa chỉ: ${diaChiPhongTro}</strong></p>

                <p style="font-size: 23px;">Giá thuê:</p>
                <ul>
                    <% for (Phong phong : danhSachPhong) { %>
                    <li> Phòng: <%= phong.getTenPhongTro() %>: <%= phong.getGia() %> đ/tháng</li>
                        <% } %>
                </ul>


                <p style="font-size: 23px;">Hình thức thanh toán: Chuyển khoản<br></p>

                <% for (HopDong hd : hopDongList) { %>
                <p>Tiền đặt cọc: <%= hd.getTien_Coc() %> đ</p>
                <p>Hợp đồng có hiệu lực từ ngày <%= hd.getNgay_gia_tri() %> đến ngày <%= hd.getNgay_het_han() %></p>
                <% } %>

                <p style="font-size: 25px;"><strong>TRÁCH NHIỆM CỦA CÁC BÊN</strong></p>
                <p style="font-size: 24px;"><strong>* Trách nhiệm của bên A:</strong></p>
                <ul style="font-size: 23px;">
                    <li>Tạo mọi điều kiện thuận lợi để bên B thực hiện theo hợp đồng.</li>
                    <li>Cung cấp nguồn điện, nước, wifi cho bên B sử dụng.</li>
                </ul>
                <p style="font-size: 24px;"><strong>* Trách nhiệm của bên B:</strong></p>
                <ul style="font-size: 23px;">
                    <li>Thanh toán đầy đủ các khoản tiền theo đúng thỏa thuận.</li>
                    <li>Bảo quản các trang thiết bị và cơ sở vật chất của bên A trang bị cho ban đầu (làm hỏng phải sửa, mất phải đền).</li>
                    <li>Không được tự ý sửa chữa, cải tạo cơ sở vật chất khi chưa được sự đồng ý của bên A</li>
                    <li>Giữ gìn vệ sinh trong và ngoài khuôn viên của phòng trọ.</li>
                    <li>Bên B phải chấp hành mọi quy định của pháp luật Nhà nước và quy định của địa phương.</li>
                    <li>Nếu bên B cho khách ở qua đêm thì phải báo và được sự đồng ý của chủ nhà đồng thời phải chịu trách nhiệm về các hành vi vi phạm pháp luật của khách trong thời gian ở lại.</li>
                </ul>
                <p style="font-size: 25px;"><strong><strong>TRÁCH NHIỆM CHUNG</strong></p>
                <ul style="font-size: 23px;">
                    <li>Hai bên phải tạo điều kiện cho nhau thực hiện hợp đồng.</li>
                    <li>Trong thời gian hợp đồng còn hiệu lực nếu bên nào vi phạm các điều khoản đã thỏa thuận thì bên còn lại có quyền đơn phương chấm dứt hợp đồng; nếu sự vi phạm hợp đồng đó gây tổn thất cho bên bị vi phạm hợp đồng thì bên vi phạm hợp đồng phải bồi thường thiệt hại.</li>
                    <li>Một trong hai bên muốn chấm dứt hợp đồng trước thời hạn thì phải báo trước cho bên kia ít nhất 30 ngày và hai bên phải có sự thống nhất.</li>
                    <li>Bên A phải trả lại tiền đặt cọc cho bên B.</li>
                    <li>Bên nào vi phạm điều khoản chung thì phải chịu trách nhiệm trước pháp luật.</li>
                    <li>Hợp đồng được lập thành 02 bản có giá trị pháp lý như nhau, mỗi bên giữ một bản.</li>
                </ul>
                <p style="font-size: 25px;"><strong>CHỮ KÝ CỦA CÁC BÊN</strong></p>
                <div style="display: flex; justify-content: space-between; width: 100%;">
                    <div style="text-align: center; margin-left: 40px;">
                        <p style="font-size: 23px;">Bên A (Chủ nhà):</p>
                        <p style="font-size: 23px;"><%= chutro != null ? chutro.getName() : "Chưa có thông tin" %></p>
                    </div>
                    <div style="text-align: center; margin-right: 40px;">
                        <p style="font-size: 23px;">Bên B (Khách thuê):</p>
                        <p style="font-size: 23px;"><%= khachThue.getName() %></p>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
