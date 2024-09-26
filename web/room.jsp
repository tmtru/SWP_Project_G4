<%-- 
    Document   : room
    Created on : Sep 22, 2024, 1:50:44 AM
    Author     : hihihihaha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@page import= "java.util.List, java.util.ArrayList, model.Phong, model.NhaTro, model.LoaiPhong, model.AnhPhongTro, dal.PhongDAO, dal.NhaTroDAO, dal.LoaiPhongDAO"%>
<!DOCTYPE html>
<!--
Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Html.html to edit this template
-->
<!DOCTYPE html>

<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <!----======== CSS ======== -->
        <link rel="stylesheet" href="css/styleRoom.css">
        <!-- Them font awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">


        <!----===== Boxicons CSS ===== -->
        <link rel="stylesheet" href="css/open-iconic-bootstrap.min.css">
        <link href='https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css' rel='stylesheet'>

        <!-- Bootstrap JS and dependencies -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <style>
            .img-preview {
                width: 50px;
                border: 1px solid #ddd;
                border-radius: 5px;
                margin-right: 10px;
                margin-bottom: 10px;
            }
        </style>
    </head>
    <body>
        <!--Room manegement dash board-->

        <nav class="sidebar">
            <header>
                <div class="image-text">
                    <a href="homer">
                        <span class="image">
                            <img src="assets/img/Logo_nhatro.png" alt="alt" style="margin-top: 15px; width: 100%; margin-left:10px"/>
                            <!--<img src="logo.png" alt="">-->
                        </span>
                    </a>


                </div>

                <i class='bx bx-chevron-right toggle'></i>
            </header>

            <div class="menu-bar">
                <div class="menu">
                    <!-- 
                    <li class="search-box">
                        <i class='bx bx-search icon'></i>
                        <input type="text" placeholder="Search...">
                    </li>
                    -->

                    <ul class="menu-links">
                        <li class="">
                            <a href="#">
                                <i class='bx bx-home-alt icon' ></i>
                                <span class="text nav-text">Trang chủ</span>
                            </a>
                        </li>
                        <li class="">
                            <a href="nhatro">
                                <i class='bx bxs-home icon' ></i>
                                <span class="text nav-text">Nhà trọ</span>
                            </a>
                        </li>

                        <li class="">
                            <a href="room" class="active">
                                <i class='bx bx-bar-chart-alt-2 icon active' ></i>
                                <span class="text nav-text">Phòng trọ</span>
                            </a>
                        </li>

                        <li class="">
                            <a href="accountController">
                                <i class='bx bx-face icon' ></i>
                                <span class="text nav-text">Người dùng</span>
                            </a>
                        </li>

                        <li class="s">
                            <a href="loaddichvu">
                                <i class='bx bx-bell icon'></i>
                                <span class="text nav-text">Dịch vụ</span>
                            </a>
                        </li>

                        <li class="">
                            <a href="#">
                                <i class='bx bx-id-card icon' ></i>
                                <span class="text nav-text">Hợp đồng</span>
                            </a>
                        </li>

                        <li class="">
                            <a href="#">
                                <i class='bx bx-wallet icon' ></i>
                                <span class="text nav-text">Hóa đơn</span>
                            </a>
                        </li>

                        <li class="">
                            <a href="#">
                                <i class='bx bx-devices icon' ></i>
                                <span class="text nav-text">Thiết bị</span>
                            </a>
                        </li>


                    </ul>
                </div>

                <div class="bottom-content">
                    <li class="">
                        <a href="#">
                            <i class='bx bx-log-out icon' ></i>
                            <span class="text nav-text">Logout</span>
                        </a>
                    </li>

                    <li class="mode">
                        <div class="sun-moon">
                            <i class='bx bx-moon icon moon'></i>
                            <i class='bx bx-sun icon sun'></i>
                        </div>
                        <span class="mode-text text">Dark mode</span>

                        <div class="toggle-switch">
                            <span class="switch"></span>
                        </div>
                    </li>

                </div>
            </div>

        </nav>

        <section class="home">
            <c:if test="${param.deleteSuccess eq 'true'}">
                <div class="alert alert-success" role="alert">
                    Room deleted successfully.
                </div>
            </c:if>
            <c:if test="${param.deleteError eq 'true'}">
                <div class="alert alert-danger" role="alert">
                    An error occurred while deleting the room.
                </div>
            </c:if>
            <section class="property-management">
                <div class="header">
                    <h2>Danh sách phòng trọ của</h2>
                    <div class="property-selector">
                        <select name="nhaTro" id="nhaTro" onchange="filterRoomsByNhaTro(this)">
                            <option value="">Chọn nhà trọ</option> <!-- Placeholder option -->
                            <c:forEach var="nhaTro" items="${nhaTroList}">
                                <option value="${nhaTro.ID_NhaTro}" <c:if test="${param.nhaTro == nhaTro.ID_NhaTro}">selected</c:if>>${nhaTro.tenNhaTro}</option>
                            </c:forEach>
                        </select>


                        <span>Còn trống 2 | Đã thuê 6 | Chưa thu phí 0</span>
                    </div>
                </div>

                <div class="action-buttons">
                    <button class="btn add-property">+ Thêm nhà trọ</button>
                    <button class="btn edit-property">Sửa thông tin nhà</button>
                    <button class="btn delete-property">Xóa nhà</button>
                </div>

                <div class="room-actions">
                    <button class="btn add-room" data-toggle="modal" data-target="#addRoomModal">+ Thêm phòng trọ</button>
                    <button class="btn quick-add-room">+ Thêm phòng trọ nhanh</button>
                </div>



                <!-- Add Room Modal -->
                <div class="modal fade" id="addRoomModal" tabindex="-1" role="dialog" aria-labelledby="addRoomModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="addRoomModalLabel">Thêm phòng trọ</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <form id="addRoomForm" action="insertRoom" method="post" enctype="multipart/form-data">
                                    <div class="form-group">
                                        <label for="tenNhaTro">Nhà trọ: </label>
                                        <select name="tenNhaTro" id="tenNhaTro" required>
                                            <c:forEach var="nhaTro" items="${nhaTroList}">
                                                <option value="${nhaTro.ID_NhaTro}">${nhaTro.tenNhaTro}</option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <div class="form-group">
                                        <label for="tenLoaiPhong">Loại phòng:</label>
                                        <select name="tenLoaiPhong" id="tenLoaiPhong" required>
                                            <c:forEach var="loaiPhong" items="${loaiPhongList}">
                                                <option value="${loaiPhong.ID_LoaiPhong}">${loaiPhong.tenLoaiPhong}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="tenPhongTro">Tên Phòng:</label>
                                        <input type="text" class="form-control" id="tenPhongTro" name="tenPhongTro" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="tang">Tầng:</label>
                                        <input type="number" class="form-control" id="tang" name="tang" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="dienTich">Diện tích:</label>
                                        <input type="text" class="form-control"id="dienTich" name="dienTich" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="gia">Giá tiền:</label>
                                        <input type="number" class="form-control" id="gia" name="gia" required>
                                    </div>

                                    <div class="form-group">
                                        <label for="urlPhongTro">Ảnh</label>
                                        <input type="file" class="form-control" name="urlPhongTro" 
                                               onchange="previewImage(this)" id="urlPhongTro" required multiple/>
                                        <div id="image-previews" class="img-previews">
                                            <!-- Preview images will be appended here -->
                                        </div>
                                    </div>
                                    <button type="submit" class="btn btn-primary">Thêm</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Edit Room Modal -->
                <div class="modal fade" id="editRoomModal" tabindex="-1" role="dialog" aria-labelledby="editRoomModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="editRoomModalLabel">Sửa phòng trọ</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <form id="addRoomForm" action="editRoom" method="post" enctype="multipart/form-data">
                                    <input type="hidden" class="form-control" id="modalIdPhong" name="phongId">
                                    <div class="form-group">
                                        <label for="modalIDNhaTro">Nhà trọ: </label>
                                        <select name="tenNhaTro" id="modalIDNhaTro" required>
                                            <c:forEach var="nhaTro" items="${nhaTroList}">
                                                <option value="${nhaTro.ID_NhaTro}">${nhaTro.tenNhaTro}</option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <div class="form-group">
                                        <label for="modalIDLoaiPhong">Loại phòng:</label>
                                        <select name="tenLoaiPhong" id="modalIDLoaiPhong" required>
                                            <c:forEach var="loaiPhong" items="${loaiPhongList}">
                                                <option value="${loaiPhong.ID_LoaiPhong}">${loaiPhong.tenLoaiPhong}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="tenPhongTro">Tên Phòng:</label>
                                        <input type="text" class="form-control" id="modalTenPhongTro" name="tenPhongTro" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="tang">Tầng:</label>
                                        <input type="number" class="form-control" id="modalTang" name="tang" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="modalDienTich">Diện tích:</label>
                                        <input type="text" class="form-control"id="modalDienTich" name="dienTich" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="modalGia">Giá tiền:</label>
                                        <input type="number" class="form-control" id="modalGia" name="gia" required>
                                    </div>

                                    <div class="form-group">
                                        <label for="modalUrlPhongTro">Ảnh</label>
                                        <input type="file" class="form-control" name="urlPhongTro" 
                                               onchange="previewImageOnEditModal(this)" id="modalUrlPhongTro" multiple/>
                                        <div id="modalImageContainer" class="img-previews">
                                            <!-- Preview images will be appended here -->
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="modalTrangThai">Trạng thái:</label>
                                        <select name="trangThai" id="modalTrangThai" required>
                                            <option value="T">Trống</option>
                                            <option value="D">Đang thuê</option>
                                            <option value="DS">Đang sửa</option>
                                        </select>
                                    </div>
                                    <button type="submit" class="btn btn-primary">Lưu</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="filters">
                    <select name="tang" id="tang" onchange="filterRoomsByFloor(this)">
                        <option value="">Chọn tầng</option> <!-- Placeholder option -->
                        <c:forEach var="tang" items="${tangList}">
                            <option value="${tang}" <c:if test="${param.tang == tang}">selected</c:if>>Tầng ${tang}</option>
                        </c:forEach>
                    </select>
                    <select>
                        <option value="room-status">Trạng thái phòng</option>
                    </select>
                    <select>
                        <option value="payment-status">Trạng thái trả phí</option>
                    </select>

                    <form action="searchRoomByName" method="get">
                        <input type="text" name="tenPhongTro" placeholder="Search.." required>
                        <button type="submit"><i class='bx bx-search icon' ></i></button>
                    </form>
                </div>
            </section>
            <section class="ftco-section">
                <div class="container">
                    <div class="row mt-4">

                    </div>
                    <div class="row">
                        <c:forEach var="room" items="${rooms}">
                            <div class="col-md-3 mb-4">
                                <div class="card h-100">

                                    <div class="card-body">
                                        <h5 class="card-title">${room.tenPhongTro}</h5>
                                        <p class="card-text">
                                            <strong>Floor:</strong> ${room.tang}<br>
                                            <strong>Area:</strong> ${room.dien_tich} m²<br>
                                            <strong>Price:</strong> <fmt:formatNumber value="${room.gia}" type="currency" currencyCode="VND"/><br>
                                            <strong>Status:</strong> 
                                            <span class="badge
                                                  ${room.trang_thai == 'T' ? 'badge-success' : 'badge-danger'}">
                                                ${room.trang_thai == 'T' ? 'Trống' : 'Đang thuê'}
                                            </span>

                                        </p>
                                    </div>
                                    <div class="card-footer bg-transparent">
                                        <div class="d-flex justify-content-between">
                                            <a href="detailRoom?id=${room.ID_Phong}" class="btn btn-outline-secondary btn-sm custom-btn" style="color: black;">
                                                <i class="bx bx-info-circle"></i> Details
                                            </a>
                                            <a href="#" class="btn btn-outline-secondary btn-sm custom-btn" style="color: black;"
                                               onclick='openEditModal(
                                                               "${room.ID_Phong}",
                                                               "${room.tenPhongTro}",
                                                               "${room.tang}",
                                                               "${room.dien_tich}",
                                                               "${room.gia}",
                                                               "${room.trang_thai}",
                                                               "${room.ID_NhaTro}",
                                                               "${room.ID_LoaiPhong}",
                                                       [<c:forEach var="image" items="${room.images}" varStatus="status">
                                                       "${fn:replace(image, '\\', '/')}"
                                                   <c:if test="${!status.last}">,</c:if>
                                               </c:forEach>]
                                                               );
                                                       return false;'>
                                                <i class="bx bx-edit"></i> Edit
                                            </a>
                                            <a href="#" class="btn btn-outline-secondary btn-sm custom-btn" style="color: black;" 
                                               onclick="confirmDelete(${room.ID_Phong});
                                                       return false;">
                                                <i class="bx bx-minus-circle"></i> Delete
                                            </a>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <div class="pagination">
                        <c:if test="${currentPage > 1}">
                            <a href="?page=${currentPage - 1}" class="prev-next"><i class="fas fa-chevron-left"></i></a> <!-- Sử dụng biểu tượng mũi tên trái -->
                            </c:if>

                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <c:choose>
                                <c:when test="${currentPage eq i}">
                                    <span class="current-page">${i}</span>
                                </c:when>
                                <c:otherwise>
                                    <a href="?page=${i}" class="page-number">${i}</a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>

                        <c:if test="${currentPage < totalPages}">
                            <a href="?page=${currentPage + 1}" class="prev-next"><i class="fas fa-chevron-right"></i></a> <!-- Sử dụng biểu tượng mũi tên phải -->
                            </c:if>
                    </div>


                </div>
            </section>

        </section>

        <script>
            const body = document.querySelector('body'),
                    sidebar = body.querySelector('nav'),
                    toggle = body.querySelector(".toggle"),
                    modeSwitch = body.querySelector(".toggle-switch"),
                    modeText = body.querySelector(".mode-text");
            // Check if dark mode is enabled on page load
            if (localStorage.getItem("darkMode") === "disabled") {
                body.classList.add("light");
                modeText.innerText = "Light mode";
            }

            // Sidebar toggle functionality
            toggle.addEventListener("click", () => {
                sidebar.classList.toggle("close");
            });
            // Dark mode toggle functionality
            modeSwitch.addEventListener("click", () => {
                body.classList.toggle("dark");
                // Update the text for dark mode
                if (body.classList.contains("dark")) {
                    modeText.innerText = "Light mode";
                    localStorage.setItem("darkMode", "enabled");
                } else {
                    modeText.innerText = "Dark mode";
                    localStorage.setItem("darkMode", "disabled");
                }
            });
            //filter room by floor ko can submit
            function filterRoomsByFloor(select) {
                var selectedFloor = select.value;
                window.location.href = 'room?tang=' + selectedFloor;
            }
            function filterRoomsByNhaTro(select) {
                var selectedNhaTro = select.value;
                window.location.href = 'room?nhaTro=' + selectedNhaTro; // Chú ý sử dụng 'nhaTro'
            }


            //delete room confirm
            function confirmDelete(roomId) {
                if (confirm('Are you sure you want to delete this room?')) {
                    window.location.href = 'deleteRoom?id=' + roomId;
                }
            }
        </script>
        <script type="text/javascript">
            function previewImage(input) {
                var previewContainer = document.getElementById('image-previews');
                previewContainer.innerHTML = ''; // Xóa nội dung cũ khi chọn ảnh mới

                if (input.files) {
                    Array.from(input.files).forEach(file => {
                        var reader = new FileReader();

                        reader.onload = function (e) {
                            var img = document.createElement('img');
                            img.src = e.target.result;
                            img.className = 'img-preview';
                            previewContainer.appendChild(img);
                        }

                        reader.readAsDataURL(file);
                    });
                }
            }

        </script>

        <script type="text/javascript">
            function openEditModal(idPhong, tenPhongTro, tang, dienTich, gia, trangThai, idNhaTro, idLoaiPhong, images) {
                // Hiển thị thông tin trong modal
                document.getElementById('modalIdPhong').value = idPhong;
                document.getElementById('modalTenPhongTro').value = tenPhongTro;
                document.getElementById('modalTang').value = tang;
                document.getElementById('modalDienTich').value = dienTich;
                document.getElementById('modalGia').value = gia;
                document.getElementById('modalTrangThai').value = trangThai;
                document.getElementById('modalIDNhaTro').value = idNhaTro;
                document.getElementById('modalIDLoaiPhong').value = idLoaiPhong;


                // Hiển thị danh sách hình ảnh
                const imageContainer = document.getElementById('modalImageContainer');
                imageContainer.innerHTML = ''; // Xóa nội dung trước đó

                images.forEach(image => {
                    const imgElement = document.createElement('img');
                    imgElement.src = image; // Đường dẫn tới hình ảnh
                    imgElement.alt = 'Room Image';
                    imgElement.style.width = '50px'; // Kích thước hình ảnh
                    imgElement.style.marginRight = '10px';
                    imageContainer.appendChild(imgElement);
                });

                // Mở modal
                $('#editRoomModal').modal('show'); // Sử dụng jQuery để mở modal
            }
            function previewImageOnEditModal(input) {
                var previewContainer = document.getElementById('modalImageContainer');
                previewContainer.innerHTML = ''; // Xóa nội dung cũ khi chọn ảnh mới

                if (input.files) {
                    Array.from(input.files).forEach(file => {
                        var reader = new FileReader();

                        reader.onload = function (e) {
                            var img = document.createElement('img');
                            img.src = e.target.result;
                            img.className = 'img-preview';
                            previewContainer.appendChild(img);
                        }

                        reader.readAsDataURL(file);
                    });
                }
            }
        </script>

    </body>
</html>