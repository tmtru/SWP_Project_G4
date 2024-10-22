<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <title>Thêm Hợp Đồng Mới</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet"/>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet"/>
    <link rel="stylesheet" href="css/stylehopdong.css">
    <link href="${pageContext.request.contextPath}/css/css-account-management.css" rel="stylesheet" />
</head>
<body>
    <div class="container">
        <jsp:include page="adminUserManagement/sidebar.jsp"></jsp:include>
        <section class="home mx-5">
            <div class="container">
                <!-- Header -->
                <div class="header">
                    <h1>Thêm khách thuê phòng</h1>
                    <div class="buttons">
                        <button class="btn return" onclick="window.history.back();">Quay về</button>
                        <button type="submit" class="btn save">Lưu</button>
                    </div>
                </div>
                <div class="form">
                    <div class="tabs">
                        <a style="text-decoration: none;" href="addContract" class="tab active">Thông tin khách thuê</a>
                        <a style="text-decoration: none;" href="DichVuThuePhong.jsp" class="tab">Dịch vụ</a>
                        <a style="text-decoration: none;" href="#hop-dong" class="tab">Hợp đồng</a>
                    </div>
                    <form action="submitContract" method="POST" enctype="multipart/form-data">
                        <div class="form-row">
                            <div class="form-group">
                                <label for="name">Họ và tên *</label>
                                <input type="text" name="name" id="name" placeholder="Họ và tên" required>
                            </div>
                            <div class="form-group">
                                <label for="cmnd">CMND/CCCD</label>
                                <input type="text" name="cmnd" id="cmnd" placeholder="CMND/CCCD">
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label>Giới tính:</label></br>
                                <div class="radio-group">
                                    <input type="radio" id="male" name="gender" value="male" checked>
                                    <label for="male">Nam</label>
                                    <input type="radio" id="female" name="gender" value="female">
                                    <label for="female">Nữ</label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="issue-date">Ngày cấp</label>
                                <input type="date" name="issuedate" id="issue-date" placeholder="Ngày cấp">
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label for="phone1">Điện thoại 1</label>
                                <input type="text" name="phone1" id="phone1" placeholder="Điện thoại 1">
                            </div>
                            <div class="form-group">
                                <label for="phone2">Điện thoại 2</label>
                                <input type="text" name="phone2" id="phone2" placeholder="Điện thoại 2">
                            </div>
                        </div>
                        
                        <div class="form-row">
                            <div class="form-group">
                                <label for="thuephong">Thuê Phòng Số:</label>
                                <input type="text" name="thuephong" id="thuephong" placeholder="phòng thuê" readonly>
                            </div>
                            <div class="form-group">
                                <label for="email">Email:</label>
                                <input type="email" name="email" id="email" placeholder="Email">
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label for="address">Địa chỉ thường trú</label>
                                <input type="text" name="address" id="address" placeholder="Địa chỉ thường trú">
                            </div>
                            <div class="form-group">
                                <label for="city">Tỉnh/Thành phố</label>
                                <select id="city" name="city">
                                    <option value="">Chọn tỉnh/thành phố</option>
                                    <!-- Add options dynamically -->
                                </select>
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label for="dob">Ngày sinh (dd/MM/yyyy)</label>
                                <input type="date" id="dob" name="dob" placeholder="dd/MM/yyyy">
                            </div>
                            <div class="form-group">
                                <label for="birthplace">Nơi sinh</label>
                                <select id="birthplace" name="birthplace">
                                    <option value="">Chọn nơi sinh</option>
                                    <!-- Add options dynamically -->
                                </select>
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label for="other-notes">Ghi chú khác</label>
                                <input type="text" name="note" id="other-notes" placeholder="Ghi chú khác">
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label for="image-upload">Hình ảnh</label>
                                <input type="file" name="img" id="image-upload" class="file-input">
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
        </section>
    </div>
</body>
</html>
