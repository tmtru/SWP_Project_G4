<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dal.NhaTroDAO" %>
<%@ page import="model.NhaTro" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Danh Sách Nhà Trọ</title>
        <!-- Bootstrap CSS -->

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <!----======== CSS ======== -->
        <link rel="stylesheet" href="css/styleRoom.css">



        <!----===== Boxicons CSS ===== -->
        <link rel="stylesheet" href="css/open-iconic-bootstrap.min.css">
        <link href='https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css' rel='stylesheet'>

        <style>

            h1 {
                color: #333;
            }
            table {
                border-collapse: collapse;
                width: 100%;
                margin-top: 20px;
            }
            th, td {
                border: 1px solid #ddd;
                padding: 12px;
                text-align: left;
            }
            th {
                background-color: #4CAF50;
                color: white;
            }
            tr:hover {
                background-color: #f1f1f1;
            }
            a {
                text-decoration: none;
                color: #4CAF50;
            }
            a:hover {
                text-decoration: underline;
            }
            form {
                margin: 20px 0;
            }
            label {
                display: block;
                margin: 10px 0 5px;
            }
            input[type="text"], input[type="number"], select {
                padding: 10px;
                margin-bottom: 15px;
                width: calc(100% - 22px);
                border: 1px solid #ccc;
                border-radius: 4px;
            }
            button {
                background-color: #4CAF50;
                color: white;
                border: none;
                padding: 10px 15px;
                cursor: pointer;
                border-radius: 4px;
                transition: background-color 0.3s;
            }
            button:hover {
                background-color: #45a049;
            }

            /* Style for hidden form */
            #addRoomForm {
                display: none; /* Hidden by default */
                margin-top: 20px;
            }

            /* New CSS for button positioning */
            .addRoomButton {
                margin-top: 30px;  /* Adjust the top margin to create space */
                margin-bottom: 20px; /* Space between the button and the form */
                display: block; /* Ensure it's on its own line */
            }

            /* Style for back buttons */
            .backButton {
                background-color: #f44336;
                color: white;
                border: none;
                padding: 10px 20px;
                cursor: pointer;
                border-radius: 4px;
                margin-top: 20px;
                display: inline-block;
                text-decoration: none;
                font-size: 16px;
            }
            .backButton:hover {
                background-color: #d32f2f;
            }
        </style>

        <script>
            function toggleForm() {
                var form = document.getElementById("addRoomForm");
                if (form.style.display === "none") {
                    form.style.display = "block";
                } else {
                    form.style.display = "none";
                }
            }
        </script>
    </head>
    <body>
        <jsp:include page="sidebarNhaTroManagement.jsp"></jsp:include>

            <section class="home mx-2 mt-1">
                <div class="container" style="margin-left:50px; margin-top:20px">
                    <h1>Danh Sách Nhà Trọ</h1>

                    <!-- Add "Quay lại trang chủ" button -->
                    <a href="index.html" class="backButton">Quay lại trang chủ</a>

                    <a href="addNhaTro.jsp"><button>Thêm Nhà Trọ</button></a>

                    <h2>Tìm Kiếm Nhà Trọ</h2>
                    <form action="nhatro" method="get">
                        <input type="text" name="search" placeholder="Nhập tên hoặc địa chỉ nhà trọ" required>
                        <button type="submit">Tìm kiếm</button>
                    </form>

                    <table>
                        <tr>
                            <th>ID</th>
                            <th>Tên Nhà Trọ</th>
                            <th>Địa Chỉ</th>
                            <th>Mô Tả</th>
                            <th>Chi tiết</th>
                            <th>Sửa</th>
                            <th>Xóa</th>
                        </tr>
                    <%
                        String searchQuery = request.getParameter("search");
                        NhaTroDAO nhaTroDAO = new NhaTroDAO();
                        List<NhaTro> list;

                        if (searchQuery != null && !searchQuery.isEmpty()) {
                            list = nhaTroDAO.searchNhaTro(searchQuery);
                        } else {
                            list = nhaTroDAO.getAll();
                        }

                        for (NhaTro nhaTro : list) {
                    %>
                    <tr>
                        <td><%= nhaTro.getID_NhaTro() %></td>
                        <td><%= nhaTro.getTenNhaTro() %></td>
                        <td><%= nhaTro.getDia_chi() %></td>
                        <td><%= nhaTro.getMo_ta() %></td>
                        <td><a href="nhatro?action=viewRooms&id=<%= nhaTro.getID_NhaTro() %>">Xem chi tiết</a></td>
                        <td><a href="editNhaTro.jsp?id=<%= nhaTro.getID_NhaTro() %>">Sửa</a></td>
                        <td>
                            <form action="deleteNhaTro" method="post" onsubmit="return confirm('Bạn có chắc chắn muốn xóa nhà trọ này?');">
                                <input type="hidden" name="id" value="<%= nhaTro.getID_NhaTro() %>">
                                <input type="submit" value="Xóa" style="background-color: red; color: white; border: none; padding: 5px 10px; cursor: pointer;">
                            </form>
                        </td>
                    </tr>
                    <%
                        }
                    %>
                </table>

                <!-- Add "Quay lại danh sách nhà trọ" button after search results -->
                <a href="NhaTro.jsp" class="backButton">Quay lại danh sách nhà trọ</a>

                <button class="addRoomButton" onclick="toggleForm()">Thêm Phòng Trọ cho Nhà Trọ</button>

                <div id="addRoomForm">
                    <h2>Thêm Phòng Trọ cho Nhà Trọ</h2>
                    <form action="nhatro" method="post">
                        <input type="hidden" name="action" value="addPhongTro">

                        <label for="idNhaTro">Chọn Nhà Trọ:</label>
                        <select name="idNhaTro" id="idNhaTro" required>
                            <%
                                for (NhaTro nhaTro : list) {
                            %>
                            <option value="<%= nhaTro.getID_NhaTro() %>"><%= nhaTro.getTenNhaTro() %></option>
                            <%
                                }
                            %>
                        </select>

                        <label for="tenPhongTro">Tên Phòng Trọ:</label>
                        <input type="text" name="tenPhongTro" id="tenPhongTro" required>

                        <label for="trangThai">Trạng Thái:</label>
                        <input type="text" name="trangThai" id="trangThai" required>

                        <label for="gia">Giá:</label>
                        <input type="number" name="gia" id="gia" required>

                        <label for="tang">Tầng:</label>
                        <input type="number" name="tang" id="tang" required>

                        <label for="dienTich">Diện Tích (m²):</label>
                        <input type="number" step="0.01" name="dienTich" id="dienTich" required>

                        <label for="idLoaiPhong">ID Loại Phòng:</label>
                        <input type="number" name="idLoaiPhong" id="idLoaiPhong" required>

                        <button type="submit">Thêm Phòng Trọ</button>
                    </form>
                </div>
            </div>
        </section>
    </body>
</html>
