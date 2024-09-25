<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thêm Phòng Trọ</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f8f8;
            margin: 0;
            padding: 20px;
        }
        h2 {
            color: #4CAF50; /* Màu xanh lá */
            font-size: 28px; /* Kích thước chữ */
            text-align: center; /* Canh giữa */
            text-transform: uppercase; /* Viết hoa tất cả */
            letter-spacing: 1px; /* Khoảng cách giữa các chữ cái */
            margin-bottom: 20px; /* Khoảng cách phía dưới */
            border-bottom: 2px solid #ddd; /* Đường gạch dưới */
            padding-bottom: 10px; /* Khoảng cách giữa đường gạch dưới và chữ */
        }
        form {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            max-width: 400px;
            margin: 0 auto;
        }
        label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
            color: #555;
        }
        input[type="text"],
        input[type="number"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .button-container {
            display: flex;
            justify-content: space-between; /* Đặt khoảng cách giữa các nút */
        }
        input[type="submit"], .back-btn {
            background-color: #4CAF50; /* Màu xanh lá */
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
            flex: 1; /* Đảm bảo nút chiếm không gian đồng đều */
            margin: 5px; /* Thêm khoảng cách giữa các nút */
        }
        input[type="submit"]:hover {
            background-color: #45a049; /* Màu xanh lá đậm hơn khi hover */
        }
        .back-btn {
            background-color: #008CBA; /* Màu xanh dương */
        }
        .back-btn:hover {
            background-color: #007B9E; /* Màu xanh dương đậm hơn khi hover */
        }
    </style>
</head>
<body>
    <h2>Thêm Phòng Trọ cho Nhà Trọ</h2>
    <form action="NhaTroServlet" method="post">
        <input type="hidden" name="action" value="addPhong">
        <input type="hidden" name="idNhaTro" value="${idNhaTro}">
        
        <label for="tenPhongTro">Tên Phòng Trọ:</label>
        <input type="text" name="tenPhongTro" id="tenPhongTro" required>
        
        <label for="trangThai">Trạng Thái:</label>
        <input type="text" name="trangThai" id="trangThai" required>
        
        <label for="gia">Giá:</label>
        <input type="number" name="gia" id="gia" required>
        
        <label for="tang">Tầng:</label>
        <input type="number" name="tang" id="tang" required>
        
        <label for="dienTich">Diện Tích:</label>
        <input type="number" step="0.01" name="dienTich" id="dienTich" required>
        
        <label for="idLoaiPhong">ID Loại Phòng:</label>
        <input type="number" name="idLoaiPhong" id="idLoaiPhong" required>
        
        <div class="button-container">
            <input type="submit" value="Thêm">
            <a href="index.html" class="back-btn">Quay về Trang Chủ</a>
        </div>
    </form>
</body>
</html>
