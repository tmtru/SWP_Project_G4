/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import dal.HopDongDAO;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;
import model.HopDong;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import dal.ChuTroDAO;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.ChuTro;

@WebServlet(name = "HopDongServlet", urlPatterns = {"/hop-dong"})
public class HopDongServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null && action.equals("add")) {
            // Hiển thị form thêm mới
            request.getRequestDispatcher("addHopDong.jsp").forward(request, response);
        } else if ("generatePDF".equals(action)) {
            try {
                generatePDF(request, response);
                System.out.println("generate thanh cong");
            } catch (DocumentException ex) {
                Logger.getLogger(HopDongServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            // Xử lý phân trang như cũ
            HopDongDAO hopDongDAO = new HopDongDAO();

            int page = 1;
            int recordsPerPage = 10;
            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }

            List<HopDong> listHopDong = hopDongDAO.findAll((page - 1) * recordsPerPage, recordsPerPage);
            int noOfRecords = hopDongDAO.getNoOfRecords();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

            request.setAttribute("listHopDong", listHopDong);
            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", page);
            request.setAttribute("active", "hopdong");
            request.getRequestDispatcher("hopDongDashboard.jsp").forward(request, response);
        }
    }

    private void generatePDF(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        Document document = null;
        PdfWriter writer = null;
        ChuTroDAO ctdao = new ChuTroDAO();
        ChuTro chuTro = ctdao.getAllChuTro().get(0);

        try {
            response.setContentType("application/pdf");
            response.setCharacterEncoding("UTF-8"); // Đặt mã hóa UTF-8 cho response
            response.setHeader("Content-Disposition", "attachment; filename=HopDongThuePhongTro.pdf");

            // Đọc file HTML và CSS
            String htmlContent = readResourceHTML();
            String cssContent = readResourceCSS();

            // Thay thế các placeholder bằng dữ liệu thực
            String ngay = "14";
            String thang = "10";
            String nam = "2024";
            String diachi = "123 Đường ABC, Phường XYZ, Thành phố ABC";

            String ngayCapCmndBenA = "02/02/2000";
            String noiCapCmndBenA = "TP.HCM";

            String tenBenA = chuTro.getName();
            String ngaySinhBenA = chuTro.getDob() != null ? chuTro.getDob().toString() : "";  // Assuming Date is in a usable format
            String diachiBenA = "456 Đường DEF, Phường GHI, Thành phố JKL";  // Modify as needed
            String cmndBenA = chuTro.getCccd();
            String soDienThoaiBenA = chuTro.getPhone();

            String tenBenB = "Trần Văn B";
            String ngaySinhBenB = "05/05/1990";
            String diachiBenB = "789 Đường XYZ, Phường PQR, Thành phố STU";
            String cmndBenB = "987654321";
            String ngayCapCmndBenB = "03/03/2005";
            String noiCapCmndBenB = "Hà Nội";
            String soDienThoaiBenB = "0987654321";

            String diaChiThue = "101 Đường LMN, Phường OPQ, Thành phố ABC";
            String giaThue = "5,000,000";
            String hinhThucThanhToan = "Chuyển khoản";
            String tienDien = "3,500";
            String tienNuoc = "100,000";
            String tienDatCoc = "10,000,000";
            String ngayBatDau = "01";
            String thangBatDau = "11";
            String namBatDau = "2024";
            String ngayKetThuc = "01";
            String thangKetThuc = "11";
            String namKetThuc = "2025";

            // Thay thế các placeholder bằng dữ liệu thực
            htmlContent = htmlContent.replace("{{ngay}}", ngay)
                    .replace("{{thang}}", thang)
                    .replace("{{nam}}", nam)
                    .replace("{{diachi}}", diachi)
                    .replace("{{ten_ben_a}}", tenBenA)
                    .replace("{{ngay_sinh_ben_a}}", ngaySinhBenA)
                    .replace("{{diachi_ben_a}}", diachiBenA)
                    .replace("{{cmnd_ben_a}}", cmndBenA)
                    .replace("{{ngay_cap_cmnd_ben_a}}", ngayCapCmndBenA)
                    .replace("{{noi_cap_cmnd_ben_a}}", noiCapCmndBenA)
                    .replace("{{so_dien_thoai_ben_a}}", soDienThoaiBenA
                    )
                    .replace("{{ten_ben_b}}", tenBenB)
                    .replace("{{ngay_sinh_ben_b}}", ngaySinhBenB)
                    .replace("{{diachi_ben_b}}", diachiBenB)
                    .replace("{{cmnd_ben_b}}", cmndBenB)
                    .replace("{{ngay_cap_cmnd_ben_b}}", ngayCapCmndBenB)
                    .replace("{{noi_cap_cmnd_ben_b}}", noiCapCmndBenB)
                    .replace("{{so_dien_thoai_ben_b}}", soDienThoaiBenB)
                    .replace("{{dia_chi_thue}}", diaChiThue)
                    .replace("{{gia_thue}}", giaThue)
                    .replace("{{hinh_thuc_thanh_toan}}", hinhThucThanhToan)
                    .replace("{{tien_dien}}", tienDien)
                    .replace("{{tien_nuoc}}", tienNuoc)
                    .replace("{{tien_dat_coc}}", tienDatCoc)
                    .replace("{{ngay_bat_dau}}", ngayBatDau)
                    .replace("{{thang_bat_dau}}", thangBatDau)
                    .replace("{{nam_bat_dau}}", namBatDau)
                    .replace("{{ngay_ket_thuc}}", ngayKetThuc)
                    .replace("{{thang_ket_thuc}}", thangKetThuc)
                    .replace("{{nam_ket_thuc}}", namKetThuc);

            // Kết hợp HTML và CSS
            String fullHtmlContent = "<html><head><meta charset=\"UTF-8\"/><style>" + cssContent + "</style></head><body>" + htmlContent + "</body></html>";

            // Tạo PDF và ghi vào HttpServletResponse để trả về client
            document = new Document();
            writer = PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // Parse HTML với font hỗ trợ Unicode
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(fullHtmlContent.getBytes(StandardCharsets.UTF_8)),
                    null, StandardCharsets.UTF_8);

            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();  // Log lỗi để kiểm tra
        } finally {
            if (document != null) {
                document.close();  // Đảm bảo document được đóng
            }
        }
    }

    public static void generatePdfFromHtml(String html, String outputFile) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputFile));
        document.open();

        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8)));

        document.close();
    }

    private static String readResourceHTML() throws IOException {
        StringBuilder htmlContent = new StringBuilder();

        htmlContent.append("<div class=\"container\">")
                .append("<header>")
                .append("<h1>CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM</h1>")
                .append("<p class=\"motto\">Độc lập - Tự do - Hạnh phúc</p>")
                .append("</header>")
                .append("<main>")
                .append("<h2>HỢP ĐỒNG THUÊ PHÒNG TRỌ</h2>")
                .append("<p>Hôm nay ngày {{ngay}} tháng {{thang}} năm {{nam}}; tại địa chỉ: {{diachi}}</p>")
                .append("<h3>Chúng tôi gồm:</h3>")
                .append("<div class=\"party\">")
                .append("<h4>1. Đại diện bên cho thuê phòng trọ (Bên A):</h4>")
                .append("<p>Ông/bà: {{ten_ben_a}} Sinh ngày: {{ngay_sinh_ben_a}}</p>")
                .append("<p>Nơi đăng ký HK: {{diachi_ben_a}}</p>")
                .append("<p>CMND số: {{cmnd_ben_a}} cấp ngày {{ngay_cap_cmnd_ben_a}} tại: {{noi_cap_cmnd_ben_a}}</p>")
                .append("<p>Số điện thoại: {{so_dien_thoai_ben_a}}</p>")
                .append("</div>")
                .append("<div class=\"party\">")
                .append("<h4>2. Bên thuê phòng trọ (Bên B):</h4>")
                .append("<p>Ông/bà: {{ten_ben_b}} Sinh ngày: {{ngay_sinh_ben_b}}</p>")
                .append("<p>Nơi đăng ký HK thường trú: {{diachi_ben_b}}</p>")
                .append("<p>CMND số: {{cmnd_ben_b}} cấp ngày {{ngay_cap_cmnd_ben_b}} tại: {{noi_cap_cmnd_ben_b}}</p>")
                .append("<p>Số điện thoại: {{so_dien_thoai_ben_b}}</p>")
                .append("</div>")
                .append("<div class=\"agreement\">")
                .append("<h3>Sau khi bàn bạc trên tinh thần dân chủ, hai bên cùng có lợi, cùng thống nhất như sau:</h3>")
                .append("<p>Bên A đồng ý cho bên B thuê 01 phòng ở tại địa chỉ: {{dia_chi_thue}}</p>")
                .append("<p>Giá thuê: {{gia_thue}} đ/tháng</p>")
                .append("<p>Hình thức thanh toán: {{hinh_thuc_thanh_toan}}</p>")
                .append("<p>Tiền điện: {{tien_dien}} đ/kwh tính theo chỉ số công tơ, thanh toán vào cuối các tháng.</p>")
                .append("<p>Tiền nước: {{tien_nuoc}} đ/người thanh toán vào đầu các tháng.</p>")
                .append("<p>Tiền đặt cọc: {{tien_dat_coc}}</p>")
                .append("<p>Hợp đồng có giá trị kể từ ngày {{ngay_bat_dau}} tháng {{thang_bat_dau}} năm {{nam_bat_dau}} đến ngày {{ngay_ket_thuc}} tháng {{thang_ket_thuc}} năm {{nam_ket_thuc}}</p>")
                .append("</div>")
                .append("<div class=\"responsibilities\">")
                .append("<h3>TRÁCH NHIỆM CỦA CÁC BÊN</h3>")
                .append("<h4>* Trách nhiệm của bên A:</h4>")
                .append("<ul>")
                .append("<li>Tạo mọi điều kiện thuận lợi để bên B thực hiện theo hợp đồng.</li>")
                .append("<li>Cung cấp nguồn điện, nước, wifi cho bên B sử dụng.</li>")
                .append("</ul>")
                .append("<h4>* Trách nhiệm của bên B:</h4>")
                .append("<ul>")
                .append("<li>Thanh toán đầy đủ các khoản tiền theo đúng thỏa thuận.</li>")
                .append("<li>Bảo quản các trang thiết bị và cơ sở vật chất của bên A trang bị cho ban đầu (làm hỏng phải sửa, mất phải đền).</li>")
                .append("<li>Không được tự ý sửa chữa, cải tạo cơ sở vật chất khi chưa được sự đồng ý của bên A.</li>")
                .append("<li>Giữ gìn vệ sinh trong và ngoài khuôn viên của phòng trọ.</li>")
                .append("<li>Bên B phải chấp hành mọi quy định của pháp luật Nhà nước và quy định của địa phương.</li>")
                .append("<li>Nếu bên B cho khách ở qua đêm thì phải báo và được sự đồng ý của chủ nhà đồng thời phải chịu trách nhiệm về các hành vi vi phạm pháp luật của khách trong thời gian ở lại.</li>")
                .append("</ul>")
                .append("</div>")
                .append("<div class=\"common-responsibilities\">")
                .append("<h3>TRÁCH NHIỆM CHUNG</h3>")
                .append("<ul>")
                .append("<li>Hai bên phải tạo điều kiện cho nhau thực hiện hợp đồng.</li>")
                .append("<li>Trong thời gian hợp đồng còn hiệu lực nếu bên nào vi phạm các điều khoản đã thỏa thuận thì bên còn lại có quyền đơn phương chấm dứt hợp đồng; nếu sự vi phạm hợp đồng đó gây tổn thất cho bên bị vi phạm hợp đồng thì bên vi phạm hợp đồng phải bồi thường thiệt hại.</li>")
                .append("<li>Một trong hai bên muốn chấm dứt hợp đồng trước thời hạn thì phải báo trước cho bên kia ít nhất 30 ngày và hai bên phải có sự thống nhất.</li>")
                .append("<li>Bên A phải trả lại tiền đặt cọc cho bên B.</li>")
                .append("<li>Bên nào vi phạm điều khoản chung thì phải chịu trách nhiệm trước pháp luật.</li>")
                .append("<li>Hợp đồng được lập thành 02 bản có giá trị pháp lý như nhau, mỗi bên giữ một bản.</li>")
                .append("</ul>")
                .append("</div>")
                .append("<div class=\"signatures\">")
                .append("<div class=\"signature\">")
                .append("<p>ĐẠI DIỆN BÊN B</p>")
                .append("<div class=\"signature-line\"></div>")
                .append("</div>")
                .append("<div class=\"signature\">")
                .append("<p>ĐẠI DIỆN BÊN A</p>")
                .append("<div class=\"signature-line\"></div>")
                .append("</div>")
                .append("</div>")
                .append("</main>")
                .append("</div>");
        return htmlContent.toString();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null && action.equals("add")) {
            // Xử lý thêm mới hợp đồng
            addHopDong(request, response);
        } else {
            // Xử lý các action khác nếu cần
            response.sendRedirect("hop-dong");
        }
    }

    private void prepareAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        if (account == null) {
            response.sendRedirect("login");  // Redirect to login page if not logged in
            return;
        }

        int accountId = account.getID_Account();
        ChuTroDAO chuTroDAO = new ChuTroDAO();
        ChuTro landlord = chuTroDAO.getChuTroByAccountId(accountId);

        if (landlord == null) {
            request.setAttribute("error", "Không tìm thấy thông tin chủ trọ.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        // Set attributes for JSP
        request.setAttribute("landlord", landlord);
        request.setAttribute("tenBenA", landlord.getName());
        request.setAttribute("ngaySinhBenA", landlord.getDob());
        request.setAttribute("cmndBenA", landlord.getCccd());
        request.setAttribute("soDienThoaiBenA", landlord.getPhone());

        // Forward to the add form JSP
        request.getRequestDispatcher("addHopDong.jsp").forward(request, response);
    }

    private void listHopDong(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HopDongDAO hopDongDAO = new HopDongDAO();

        int page = 1;
        int recordsPerPage = 10;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        List<HopDong> listHopDong = hopDongDAO.findAll((page - 1) * recordsPerPage, recordsPerPage);
        int noOfRecords = hopDongDAO.getNoOfRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        request.setAttribute("listHopDong", listHopDong);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("active", "hopdong");
        request.getRequestDispatcher("hopDongDashboard.jsp").forward(request, response);
    }

    private void addHopDong(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int ID_KhachThue = Integer.parseInt(request.getParameter("ID_KhachThue"));
            int ID_PhongTro = Integer.parseInt(request.getParameter("ID_PhongTro"));

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            // Chuyển đổi từ String sang java.util.Date, sau đó sang java.sql.Date
            java.util.Date utilNgayGiaTri = sdf.parse(request.getParameter("Ngay_gia_tri"));
            java.util.Date utilNgayHetHan = sdf.parse(request.getParameter("Ngay_het_han"));

            Date Ngay_gia_tri = new Date(utilNgayGiaTri.getTime());
            Date Ngay_het_han = new Date(utilNgayHetHan.getTime());

            int Tien_Coc = Integer.parseInt(request.getParameter("Tien_Coc"));

            HopDong newHopDong = new HopDong(0, ID_KhachThue, ID_PhongTro, Ngay_gia_tri, Ngay_het_han, Tien_Coc);
            HopDongDAO hopDongDAO = new HopDongDAO();

            boolean success = hopDongDAO.addHopDong(newHopDong);
            if (success) {
                response.sendRedirect("hop-dong?addSuccess=true");
            } else {
                request.setAttribute("error", "Không thể thêm hợp đồng. Vui lòng thử lại.");
                request.getRequestDispatcher("addHopDong.jsp").forward(request, response);
            }
        } catch (NumberFormatException | ParseException e) {
            request.setAttribute("error", "Dữ liệu không hợp lệ. Vui lòng kiểm tra lại.");
            request.getRequestDispatcher("addHopDong.jsp").forward(request, response);
        }
    }

    private String readResourceCSS() {
        StringBuilder cssContent = new StringBuilder();

        cssContent.append("body {")
                .append("    font-family: Arial, sans-serif;")
                .append("    line-height: 1.6;")
                .append("    color: #333;")
                .append("    max-width: 800px;")
                .append("    margin: 0 auto;")
                .append("    padding: 20px;")
                .append("}")
                .append(".container {")
                .append("    border: 1px solid #ccc;")
                .append("    padding: 20px;")
                .append("    background-color: #f9f9f9;")
                .append("}")
                .append("header {")
                .append("    text-align: center;")
                .append("    margin-bottom: 20px;")
                .append("}")
                .append("h1 {")
                .append("    font-size: 18px;")
                .append("    text-transform: uppercase;")
                .append("    margin-bottom: 5px;")
                .append("}")
                .append(".motto {")
                .append("    font-style: italic;")
                .append("    margin-top: 0;")
                .append("}")
                .append("h2 {")
                .append("    font-size: 16px;")
                .append("    text-align: center;")
                .append("    text-transform: uppercase;")
                .append("    margin-top: 30px;")
                .append("}")
                .append("h3 {")
                .append("    font-size: 14px;")
                .append("    margin-top: 25px;")
                .append("    text-transform: uppercase;")
                .append("}")
                .append("h4 {")
                .append("    font-size: 14px;")
                .append("    margin-top: 20px;")
                .append("}")
                .append("p {")
                .append("    margin-bottom: 10px;")
                .append("}")
                .append("ul {")
                .append("    padding-left: 20px;")
                .append("}")
                .append("li {")
                .append("    margin-bottom: 10px;")
                .append("}")
                .append(".party, .agreement, .responsibilities, .common-responsibilities {")
                .append("    margin-bottom: 30px;")
                .append("}")
                .append(".signatures {")
                .append("    display: flex;")
                .append("    justify-content: space-between;")
                .append("    margin-top: 50px;")
                .append("}")
                .append(".signature {")
                .append("    text-align: center;")
                .append("}")
                .append(".signature-line {")
                .append("    width: 200px;")
                .append("    height: 1px;")
                .append("    background-color: #333;")
                .append("    margin: 10px auto;")
                .append("}");
        return cssContent.toString();
    }
}
