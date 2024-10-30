<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Thông tin người thuê phòng</title>
        <link rel="stylesheet" href="css/stylehopdong.css">
        <style>
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
                    <h1>Thông tin người thuê phòng</h1>
                    <div class="buttons">
                        <button class="btn return" onclick="window.history.back();">Quay về</button>
                        <button class="btn save">Lưu</button>
                    </div>
                </div>
                <div class="form">
                    <div class="tabs">
                        <a style="text-decoration: none;" href="ThongTinKhachThuePhong" class="tab active">Thông tin người thuê phòng</a>
                    <% 
                                String message = (String) request.getAttribute("message");
                                if (message != null) {
                    %>
                    <a href="#" class="tab" onclick="redirectToHopDong()">Hợp đồng</a>

                    <% 
                        }
                    %>
                </div>
                <form action="ThongTinKhachThuePhong" method="get">
                    <% 
    int hopDongId = Integer.parseInt(request.getParameter("hopDongId")); 
                    %>

                    <% 
    if (message != null) {
                    %>
                    <div class="alert alert-info" style="color: green; font-weight: bold; margin-top: 10px;">
                        <%= message %>
                    </div>

                    <%
                        }
                    %>
                    <input type="hidden" id="hopDongId" name="hopDongId" value="<%= hopDongId %>">

                    <div class="form-row">
                        <div class="form-group">
                            <label for="name">Họ và tên *</label>
                            <input type="text" name="name" id="name" placeholder="Họ và tên" 
                                   value="${Hovaten}" required>
                        </div>
                        <div class="form-group">
                            <label for="cmnd">CMND/CCCD</label>
                            <input type="text" name="cmnd" id="cmnd" placeholder="CMND/CCCD" 
                                   value="${Cmnd}" required 
                                   pattern="\d{10,13}" 
                                   minlength="10" 
                                   maxlength="13" 
                                   title="Vui lòng nhập từ 10 đến 13 chữ số">
                        </div>

                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label>Giới tính:</label></br>
                            <div class="radio-group">
                                <input type="radio" id="male" name="gender" value="male" ${Gioitinh == 'male' ? 'checked' : ''} checked>
                                <label for="male">Nam</label>
                                <input type="radio" id="female" name="gender" value="female" ${Gioitinh == 'female' ? 'checked' : ''}>
                                <label for="female">Nữ</label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="dob">Ngày sinh (dd/MM/yyyy)</label>
                            <input type="date" id="dob" name="dob" value="${NgaySinh}" required>
                            <span id="dob-error" style="color:red; display:none;">Ngày sinh không được sau ngày hiện tại.</span>
                        </div>

                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label for="phone1">Điện thoại</label>
                            <input type="text" name="phone1" id="phone1" placeholder="Điện thoại" 
                                   value="${Dienthoai}" required pattern="^0\d{9}$"
                                   title="Số điện thoại phải bắt đầu bằng 0 và có 10 chữ số.">
                        </div>
                        <div class="form-group">
                            <label for="email">Email:</label>
                            <input type="email" name="email" id="email" placeholder="Email" value="${Email}" required> 
                        </div>

                    </div>



                    <div class="form-row">
                        <div class="form-group">
                            <label for="address">Địa chỉ thường trú</label>
                            <input type="text" name="address" id="address" placeholder="Địa chỉ thường trú" 
                                   value="${DiaChi}" required>
                        </div>

                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <button type="submit" class="submit-btn">Lưu</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </body>

    <script>
        document.addEventListener('DOMContentLoaded', function () {
            const issueDateInput = document.getElementById('issue-date');
            const birthDateInput = document.getElementById('dob');
            const issueDateError = document.getElementById('issue-date-error');
            const dobError = document.getElementById('dob-error');

            // Kiểm tra ngày sinh không được sau ngày hiện tại
            birthDateInput.addEventListener('change', function () {
                const birthDate = new Date(birthDateInput.value);
                const today = new Date();

                // Kiểm tra ngày sinh
                if (birthDate > today) {
                    dobError.style.display = 'inline'; // Hiện thông báo lỗi
                } else {
                    dobError.style.display = 'none'; // Ẩn thông báo lỗi
                }
            });

            // Kiểm tra ngày cấp
            issueDateInput.addEventListener('change', function () {
                const issueDate = new Date(issueDateInput.value);
                const birthDate = new Date(birthDateInput.value);
                const today = new Date();

                // Kiểm tra ngày cấp
                if (issueDate <= birthDate || issueDate > today) {
                    issueDateError.style.display = 'inline'; // Hiện thông báo lỗi
                } else {
                    issueDateError.style.display = 'none'; // Ẩn thông báo lỗi
                }
            });
        });
        function redirectToHopDong() {
            // Lấy giá trị của hopDongId từ input ẩn
            var hopDongId = document.getElementById('hopDongId').value;
            // Chuyển hướng tới trang hợp đồng với tham số hopDongId
            window.location.href = 'HopDongThueTro?hopDongId=' + hopDongId;
        }
    </script>


</html>