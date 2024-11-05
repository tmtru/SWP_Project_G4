<%@ page contentType="text/html;charset=UTF-8" %>
<%@page import="model.ThietBiPhong"%>
<%@page import="model.Phong"%>
<%@page import="dal.ThietBiPhongDAO"%>
<%@page import="dal.PhongDAO"%>
<%@page session="true" %>
<%
    // Lấy ID từ parameters
    String idThietBiPhongStr = request.getParameter("idThietBiPhong");
    String idPhongStr = request.getParameter("idPhong");
    
    // Khởi tạo DAOs
    ThietBiPhongDAO thietBiPhongDAO = new ThietBiPhongDAO();
    PhongDAO phongDAO = new PhongDAO();
    
    // Lấy thông tin thiết bị và phòng
    ThietBiPhong thietBi = null;
    Phong phong = null;
    
    if(idThietBiPhongStr != null && idPhongStr != null) {
        int idThietBiPhong = Integer.parseInt(idThietBiPhongStr);
        int idPhong = Integer.parseInt(idPhongStr);
        thietBi = thietBiPhongDAO.getThietBiPhongById(idThietBiPhong);
        phong = phongDAO.getPhongById(idPhong);
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Yêu cầu bảo trì</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4">Yêu cầu bảo trì thiết bị</h2>
        
        <% if(thietBi != null && phong != null) { %>
            <div class="card mb-4">
                <div class="card-header">
                    <h5 class="mb-0">Thông tin thiết bị</h5>
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-4">
                            <p><strong>Tên thiết bị:</strong> <%= thietBi.getTenThietBi() %></p>
                        </div>
                        <div class="col-md-4">
                            <p><strong>Mô tả thiết bị:</strong> <%= thietBi.getMo_ta() %></p>
                        </div>
                        <div class="col-md-4">
                            <p><strong>Phòng:</strong> <%= phong.getTenPhongTro() %></p>
                        </div>
                    </div>
                </div>
            </div>
        <% } %>

        <div class="card">
            <div class="card-body">
                <form action="saveMaintainanceRequestServlet" method="post">
                    <input type="hidden" name="idThietBiPhong" value="<%= idThietBiPhongStr %>">
                    <input type="hidden" name="idPhong" value="<%= idPhongStr %>">
                    <div class="mb-3">
                        <label for="moTa" class="form-label">Mô tả vấn đề cần bảo trì</label>
                        <textarea class="form-control" id="moTa" name="moTa" rows="4" required 
                                placeholder="Vui lòng mô tả chi tiết vấn đề của thiết bị..."></textarea>
                    </div>
                    <div class="d-flex gap-2">
                        <button type="submit" class="btn btn-primary">Gửi yêu cầu</button>
                        <a href="maintainanceServlet?selectedRoom=<%= idPhongStr %>" 
                           class="btn btn-secondary">Quay lại</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>