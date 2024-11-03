<%-- 
    Document   : HopDongThuePhong
    Created on : Oct 16, 2024, 9:42:03 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.DichVu" %>
<%@ page import="java.util.List" %>
<%@ page import="model.HopDong" %>
<%@ page import="model.KhachThuePhu" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cấu Hình Hợp Đồng</title>
        <link rel="stylesheet" href="css/stylehopdong.css">
        <script src="https://cdn.tiny.cloud/1/jjsh1p2pgb4pw6bs3sl42td5qrf31a3pitnpf52bqixncyt9/tinymce/6/tinymce.min.js" referrerpolicy="origin"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.17.0/xlsx.full.min.js"></script> <!-- Thư viện SheetJS -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.10.1/html2pdf.bundle.min.js"></script>
        <% 
            Integer giaPhong = (Integer) request.getAttribute("giaPhong");
            String diaChiPhongTro = (String) request.getAttribute("diaChiPhongTro");
        %>
        <script>
            // Khởi tạo nội dung hợp đồng
            let initialContent = `
        <p style="text-align: center; font-size: 33px;"><strong>CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM</strong></p>
        <p style="text-align: center; font-size: 23px;"><strong>Độc lập - Tự do - Hạnh phúc</strong></p>
        <p style="text-align: center; font-size: 33px;"><strong>HỢP ĐỒNG THUÊ PHÒNG TRỌ</strong></p>
        <p style="font-size: 25px;"><strong>Chúng tôi gồm:</strong></p>
        <p style="font-size: 25px;"><strong>1. Đại diện bên cho thuê phòng trọ (Bên A):</strong></p>
        <p style="font-size: 23px;">Ông/bà: ${chuTro.name}<br>
        Sinh ngày: ${chuTro.dob}<br>
        CMND số: ${chuTro.cccd}<br>
        Số điện thoại: ${chuTro.phone}</p>
        <p style="font-size: 25px;"><strong>2. Bên thuê phòng trọ (Bên B):</strong></p>
        <p style="font-size: 23px;">Người đại diện thuê phòng: ${khachThue.name}<br>
        Sinh ngày: ${khachThue.dob}<br>
        Nơi đăng ký HK thường trú: ${khachThue.hk_thuong_tru}<br>
        CMND số: ${khachThue.cccd} cấp ngày ${khachThue.ngay_cap} tại: ${khachThue.noi_cap}<br>
        Số điện thoại: ${khachThue.phone}</p>
    
        <p style="font-size: 25px;"><strong>Thành viên khác:</strong></p>
    `;
            // Lấy danh sách thành viên khác từ request attribute khachThuePhuList
            <% 
    List<KhachThuePhu> khachThuePhuList = (List<KhachThuePhu>) request.getAttribute("khachThuePhuList");
            %>
            <% if (khachThuePhuList != null && !khachThuePhuList.isEmpty()) { %>
            // Thêm thông tin từng thành viên trong khachThuePhuList vào initialContent
            <% 
        int index = 1; // Khởi tạo biến đếm bắt đầu từ 1
        for (KhachThuePhu khachThuePhu : khachThuePhuList) { 
            %>
            initialContent += `<p style="font-size: 23px;">
        Thành viên <%= index %>: <br>
        Tên thành viên: <%= khachThuePhu.getTenKhach() %><br>
        Sinh ngày: <%= khachThuePhu.getNgaySinh() %><br>
        Nơi đăng ký HK thường trú: <%= khachThuePhu.getHkThuongTru() %><br>
        CMND số: <%= khachThuePhu.getCccd() %><br>
        Số điện thoại: <%= khachThuePhu.getSdt() %>
    </p>`;
            <%
        index++; // Tăng biến đếm lên 1 sau mỗi lần lặp
        } 
            %>
            <% } else { %>
            initialContent += `<p style="font-size: 23px;">Không có thành viên khác.</p>`;
            <% } %>


            initialContent += `
        <p style="font-size: 25px;"><strong>Bên A đồng ý cho bên B thuê 01 phòng ở tại địa chỉ: ${diaChiPhongTro}</strong></p>
        <p style="font-size: 23px;">Giá thuê: ${giaPhong} đ/tháng<br>
        Hình thức thanh toán: Chuyển khoản<br>
    `;
            <% 
                // Lấy dichVuList từ request
                List<DichVu> dichVuList = (List<DichVu>) request.getAttribute("dichVuList");

                if (dichVuList != null && !dichVuList.isEmpty()) {
            %>
            // Thêm các dịch vụ vào hợp đồng (thực hiện trong đoạn JavaScript)
            initialContent += `
                <p style="font-size: 25px;"><strong>Các dịch vụ đi kèm:</strong></p>`;
            <% for (DichVu dichVu : dichVuList) { %>
            initialContent += `<p style="font-size: 23px;"><%= dichVu.getTenDichVu() %>: <%= dichVu.getDon_gia() %> đ/<%= dichVu.getDon_vi() %><br></p>`;
            <% } %>
            <% } %>

            initialContent += `
                <p style="font-size: 25px;">Tiền đặt cọc: ${hopDong.getTien_Coc()} đ<br>
                Hợp đồng có giá trị kể từ ngày ${hopDong.getNgay_gia_tri()} đến ngày ${hopDong.getNgay_het_han()}</p>
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
            `;
            // Khởi tạo TinyMCE
            function initializeEditor() {
            tinymce.init({
            selector: '#contractContent',
                    height: 800,
                    menubar: false,
                    plugins: [
                            'advlist autolink lists link image charmap print preview anchor',
                            'searchreplace visualblocks code fullscreen',
                            'insertdatetime media table paste code help wordcount'
                    ],
                    toolbar: 'undo redo | formatselect | bold italic backcolor | ' +
                    'alignleft aligncenter alignright alignjustify | ' +
                    'bullist numlist outdent indent | removeformat | help',
                    readonly: true,
                    setup: function (editor) {
                    editor.on('init', function () {
                    editor.setContent(initialContent); // Đặt nội dung ban đầu khi khởi tạo
                    });
                    }
            });
            }

            // Function để reset lại nội dung khi bấm nút "Nhập lại"
            function resetContent() {
            if (tinymce.get('contractContent')) {
            tinymce.get('contractContent').remove(); // Xóa editor hiện tại
            }
            initializeEditor(); // Tạo lại editor với nội dung ban đầu
            }

            function exportToPdf() {
            // Lấy nội dung từ TinyMCE
            const content = tinymce.get('contractContent').getContent();
            // Tạo một phần tử HTML tạm thời để chứa nội dung
            const tempElement = document.createElement('div');
            tempElement.innerHTML = content;
            // Thiết lập tùy chọn cho html2pdf
            const options = {
            margin: 1,
                    filename: 'hop_dong_thue_phong.pdf',
                    image: { type: 'jpeg', quality: 0.98 },
                    html2canvas: { scale: 2 },
                    jsPDF: { unit: 'in', format: 'letter', orientation: 'portrait' }
            };
            // Sử dụng html2pdf để xuất PDF
            html2pdf().from(tempElement).set(options).save();
            }



            document.addEventListener('DOMContentLoaded', function() {
            initializeEditor(); // Khởi tạo editor khi trang load
            });
        </script>


        <style>
            .contract{
                max-width: 1800px;
                margin-right: 0px;
                margin-left: 250px
            }
            .container {
                max-width: 1255px !important;
                margin-right: 0px !important;
                margin-left: 240px !important;
                margin-top: -20px !important;
            }
        </style>
    </head>
    <body>
    <jsp:include page="adminUserManagement/sidebar.jsp"></jsp:include>
    <div class="container">
        <div class="header">
            <h1>HỢP ĐỒNG THUÊ PHÒNG TRỌ</h1>
        </div>
        <% 
            String message = (String) request.getAttribute("message");
            if (message != null) {
        %>
        <div class="alert alert-info" style="color: green; font-weight: bold; margin-top: 10px;">
            <%= message %>
        </div>
        <% 
            }
        %>
        <%
            String hopDongId = request.getParameter("hopDongId");
        %>

        <form action="GuiHopDongChoChuTro" method="POST">
            <!-- Truyền hopDongId qua một hidden input -->
            <input type="hidden" name="hopDongId" value="<%= hopDongId %>">

            <label for="contractContent">Nội dung hợp đồng:</label>
            <textarea id="contractContent" name="contractContent"></textarea>

            <div style="margin-top: 20px;">
                <!-- Button Gửi sẽ submit form -->
                <% if (message == null) { %>
                    <button type="submit" style="padding: 10px 20px; background-color: green; color: white; border: none;">Gửi cho chủ trọ</button>
                <% } %>

                <!-- Button Xuất PDF sẽ không submit form -->
                <button type="button" style="padding: 10px 20px; background-color: blue; color: white; border: none;" onclick="exportToPdf()">Xuất PDF</button>
            </div>
        </form>
    </div>
</body>

</html>
