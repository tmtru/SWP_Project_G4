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
    <link href="${pageContext.request.contextPath}/css/css-account-management.css" rel="stylesheet" />
    <style>
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
        }
        .form-group input, .form-group select {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .form-group button {
            background-color: #5a67d8;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 4px;
            cursor: pointer;
        }
        .form-group button:hover {
            background-color: #4c51bf;
        }
    </style>
</head>
<body>
    <div class="container">
        <jsp:include page="adminUserManagement/sidebar.jsp"></jsp:include>
        <section class="home mx-5">
            <div class="main-content">
                <h2>Thêm Hợp Đồng Mới</h2>
                <form action="hop-dong?action=add" method="post" style="max-width: 500px">
                    <input type="hidden" name="action" value="add">
                    <div class="form-group">
                        <label for="ID_KhachThue">Mã Khách Thuê:</label>
                        <input type="number" id="ID_KhachThue" name="ID_KhachThue" required>
                    </div>
                    <div class="form-group">
                        <label for="ID_PhongTro">Mã Phòng Trọ:</label>
                        <input type="number" id="ID_PhongTro" name="ID_PhongTro" required>
                    </div>
                    <div class="form-group">
                        <label for="Ngay_gia_tri">Ngày hợp đồng có giá trị:</label>
                        <input type="date" id="Ngay_gia_tri" name="Ngay_gia_tri" required>
                    </div>
                    <div class="form-group">
                        <label for="Ngay_het_han">Ngày Hết Hạn:</label>
                        <input type="date" id="Ngay_het_han" name="Ngay_het_han" required>
                    </div>
                    <div class="form-group">
                        <label for="Tien_Coc">Tiền Cọc:</label>
                        <input type="number" id="Tien_Coc" name="Tien_Coc" required>
                    </div>
                    <div class="form-group">
                        <button type="submit">Thêm Hợp Đồng</button>
                    </div>
                </form>
            </div>
        </section>
    </div>
</body>
</html>