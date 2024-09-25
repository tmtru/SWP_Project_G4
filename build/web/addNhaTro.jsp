<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Thêm Nhà Trọ</title>
        <style>

            form {
                background-color: #fff;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                max-width: 400px;

            }
            label {
                display: block;
                margin-bottom: 8px;
                font-weight: bold;
                color: #555;
            }
            input[type="text"] {
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
        <jsp:include page="sidebarNhaTroManagement.jsp"></jsp:include>

        <section class="home mx-2 mt-1">
            <div class="container" style="margin-left:50px; margin-top:20px">
                <h1>Thêm Nhà Trọ</h1>
                <form action="nhatro?action=add" method="post">
                    <label for="tenNhaTro">Tên Nhà Trọ:</label>
                    <input type="text" name="tenNhaTro" id="tenNhaTro" required>

                    <label for="diaChi">Địa Chỉ:</label>
                    <input type="text" name="diaChi" id="diaChi" required>

                    <label for="moTa">Mô Tả:</label>
                    <input type="text" name="moTa" id="moTa" required>

                    <label for="idChuTro">ID Chủ Trọ:</label>
                    <input type="text" name="idChuTro" id="idChuTro" required>

                    <div class="button-container">
                        <input type="submit" value="Thêm">
                        <a href="NhaTro.jsp" class="back-btn">Quay về </a>
                    </div>
                </form>
            </div>
        </section>
    </body>
</html>
