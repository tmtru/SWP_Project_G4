<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.DichVu" %> <!-- Đảm bảo bạn nhập đúng gói -->
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dịch vụ thuê phòng</title>
    <link rel="stylesheet" href="css/styledichvuthuephong.css">
    <style>
        .container {
            max-width: 1255px !important;
            margin-right: 0px !important;
            margin-left: 250px !important;
        }
        .people-section h2 {
            font-size: 18px !important;
            margin: 0 !important;
        }
        .alert {
            color: red; /* Màu cho thông báo lỗi */
        }
        .success {
            color: green; /* Màu cho thông báo thành công */
        }
    </style>
</head>
<body>
    <jsp:include page="adminUserManagement/sidebar.jsp"></jsp:include>
    <div class="container">
        <div class="header">
            <h1>Dịch vụ thuê phòng</h1>
            <div class="buttons">
                <button class="btn return" onclick="window.history.back();">Quay về</button>
            </div>
        </div>

        <form action="DangKyDichVuPhongTro" method="post" onsubmit="return validateCheckbox()">
            <div class="tabs">
                <a href="DichVuThuePhong.jsp" class="tab active">Dịch vụ</a>
                <% 
                    String errorMessage = (String) request.getAttribute("errorMessage");
                    String successMessage = (String) request.getAttribute("Message");
                    
                    if (successMessage != null && errorMessage == null) {
                %>
                    <a href="#" class="tab" onclick="redirectToHopDong()">Hợp đồng</a>
                <% 
                    }
                %>
            </div>

            <div class="alert">
                <p><strong>Lưu ý:</strong> Vui lòng chọn dịch vụ cho khách thuê. Nếu khách không chọn dịch vụ thì phần mềm sẽ tự tính các khoản phí vào hóa đơn; ngược lại nếu không chọn phần mềm sẽ bỏ qua.</p>
                <p>Đối với dịch vụ là loại điện/nước thì sẽ tính theo chỉ số điện/nước. Đối với các dịch vụ khác sẽ tính theo số lượng.</p>
            </div>

            <div class="people-section">
                <h2>Số lượng người:</h2>
                <div class="input-group">
                    <input type="number" id="people-count" name="people-count" placeholder="Số lượng" min="1" required
                    value="<%= request.getAttribute("peopleCount") != null ? request.getAttribute("peopleCount") : "" %>">
                </div>
            </div>

            <!-- Thông báo từ servlet -->
            <div class="messages">
                <%
                    if (errorMessage != null) {
                %>
                <div class="alert" style="color: red; font-weight: bold; margin-top: 10px;"><%= errorMessage %></div>
                <%
                    }
                    if (successMessage != null) {
                %>
                <div class="success" style="color: green; font-weight: bold; margin-top: 10px;"><%= successMessage %></div>
                <%
                    }
                %>
            </div>

            <div class="people-section">
                <div class="input-group">
                    <input type="hidden" id="hopDongId" name="hopDongId" value="${hopDongId}" required>
                </div>
            </div>

            <div class="service-section">
                <table class="service-table">
                    <thead>
                        <tr>
                            <th>Chọn</th>
                            <th>Dịch vụ sử dụng</th>
                            <th>Đơn giá (VND)</th>
                            <th>Đơn vị</th>
                            <th>Mô tả</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<DichVu> dichVuList = (List<DichVu>) request.getAttribute("dichVuList");
                            String[] selectedServices = (String[]) request.getAttribute("selectedServices");

                            if (dichVuList != null && !dichVuList.isEmpty()) {
                                for (DichVu dichVu : dichVuList) {
                                    boolean isChecked = false;
                                    if (selectedServices != null) {
                                        for (String selectedService : selectedServices) {
                                            if (selectedService.equals(String.valueOf(dichVu.getID_DichVu()))) {
                                                isChecked = true;
                                                break;
                                            }
                                        }
                                    }
                        %>
                        <tr>
                            <td>
                                <input type="checkbox" name="service-selected" value="<%= dichVu.getID_DichVu() %>" <%= isChecked ? "checked" : "" %>>
                            </td>
                            <td><%= dichVu.getTenDichVu() %></td>
                            <td><%= dichVu.getDon_gia() %></td>
                            <td><%= dichVu.getDon_vi() %></td>
                            <td><%= dichVu.getMo_ta() %></td>
                        </tr>
                        <%
                                }
                            } else {
                        %>
                        <tr>
                            <td colspan="5">Không có dịch vụ nào.</td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>

            <div class="buttons">
                <button type="submit" class="btn save">Lưu</button>
            </div>
        </form>
    </div>

    <script>
        function validateCheckbox() {
            const checkboxes = document.querySelectorAll('input[name="service-selected"]');
            let isChecked = false;
            
            for (const checkbox of checkboxes) {
                if (checkbox.checked) {
                    isChecked = true;
                    break;
                }
            }
            
            if (!isChecked) {
                alert("Vui lòng chọn ít nhất một dịch vụ.");
                return false; // Ngăn form submit nếu không có checkbox nào được chọn
            }
            return true; // Cho phép submit nếu đã chọn ít nhất một checkbox
        }

        function redirectToHopDong() {
            var hopDongId = document.getElementById('hopDongId').value;
            window.location.href = 'HopDongThueTro?hopDongId=' + hopDongId;
        }
    </script>
</body>
</html>
