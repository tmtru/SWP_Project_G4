<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="dal.MaintainanceDAO"%>
<%@ page import="model.Maintainance"%>
<%@page import="model.ThietBiPhong"%>
<%@page import="dal.ThietBiPhongDAO"%>
<%@page import="model.Phong"%>
<%@page import="dal.PhongDAO"%>
<%@ page session="true" %>

<%
    String idThietBiPhongStr = request.getParameter("idThietBiPhong");
    String idPhongStr = request.getParameter("idPhong");
    
    // Khởi tạo DAOs
    ThietBiPhongDAO thietBiPhongDAO = new ThietBiPhongDAO();
    PhongDAO phongDAO = new PhongDAO();
    
    // Lấy thông tin thiết bị và phòng
    ThietBiPhong thietbi = null;
    Phong phong = null;
    
    if(idThietBiPhongStr != null && idPhongStr != null) {
        int idThietBiPhong = Integer.parseInt(idThietBiPhongStr);
        int idPhong = Integer.parseInt(idPhongStr);
        thietbi = thietBiPhongDAO.getThietBiPhongById(idThietBiPhong);
        phong = phongDAO.getPhongById(idPhong);
    }
    
    String maintainanceIdStr = request.getParameter("id");
    Maintainance maintainance = null;

    if (maintainanceIdStr != null) {
        int maintainanceId = Integer.parseInt(maintainanceIdStr);
        MaintainanceDAO maintainanceDAO = new MaintainanceDAO();
        maintainance = maintainanceDAO.getMaintainanceById(maintainanceId);
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Chỉnh sửa yêu cầu bảo trì</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container mt-5">
            <h2 class="mb-4">Chỉnh sửa yêu cầu bảo trì</h2>

            <% if (maintainance != null) { %>
            <div class="card mb-4">
                <div class="card-header">
                    <h5 class="mb-0">Thông tin yêu cầu bảo trì</h5>
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-4">
                            <p><strong>ID Yêu cầu:</strong> <%= maintainance.getId_bao_tri() %></p>
                        </div>
                        <div class="col-md-4">
                            <p><strong>Tên thiết bị:</strong> <%= thietbi.getTenThietBi() %></p>
                        </div>
                        <div class="col-md-4">
                            <p><strong>Phòng:</strong> <%= phong.getTenPhongTro() %></p>
                        </div>
                    </div>
                </div>
            </div>

            <div class="card">
                <div class="card-body">
                    <form name="editForm" action="editMaintainanceRequestServlet" method="post" onsubmit="return validateForm()">
                        <input type="hidden" name="id" value="<%= maintainance.getId_bao_tri() %>">
                        <div class="mb-3">
                            <label for="moTa" class="form-label">Mô tả vấn đề cần bảo trì</label>
                            <textarea class="form-control" id="moTa" name="moTa" rows="4" required><%= maintainance.getMo_ta() %></textarea>
                            <div id="error-mota" class="text-danger" style="display:none;">Mô tả không được để trống.</div>
                        </div>
                        
                        <div class="d-flex gap-2">
                            <button type="submit" class="btn btn-primary">Cập nhật</button>
                            <a href="maintainanceServlet" class="btn btn-secondary">Quay lại</a>
                        </div>
                    </form>
                </div>
            </div>
            <% } else { %>
            <div class="alert alert-danger">Không tìm thấy yêu cầu bảo trì.</div>
            <% } %>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            function validateForm() {
                var moTa = document.forms["editForm"]["moTa"].value;
                var errorMessage = document.getElementById("error-mota");

                // Kiểm tra xem mô tả có rỗng không
                if (moTa.trim() == "") {
                    errorMessage.style.display = "block";
                    return false; // Ngừng submit nếu không hợp lệ
                } else {
                    errorMessage.style.display = "none";
                    return true; // Cho phép submit nếu hợp lệ
                }
            }
        </script>
    </body>
</html>
