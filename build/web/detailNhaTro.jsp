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
        <title >Chi tiết nhà trọ</title>
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
                    <ul class="menu-links">
                        <li class="">
                            <a href="#">
                                <i class='bx bx-home-alt icon' ></i>
                                <span class="text nav-text">Trang chủ</span>
                            </a>
                        </li>
                        <li class="">
                            <a href="nhatro" class="active">
                                <i class='bx bxs-home icon active' ></i>
                                <span class="text nav-text">Nhà trọ</span>
                            </a>
                        </li>

                        <li class="">
                            <a href="room">
                                <i class='bx bx-bar-chart-alt-2 icon ' ></i>
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
            <div class="container mt-5">
                <h2>NhaTro Details</h2>
                <c:if test="${not empty sessionScope.notification}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        ${sessionScope.notification}
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <%
                        session.removeAttribute("notification");
                    %>
                </c:if>

                <c:if test="${not empty sessionScope.notificationErr}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        ${sessionScope.notificationErr}
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <%
                        session.removeAttribute("notificationErr");
                    %>
                </c:if>
                <div class="card">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-6">
                                <!-- Image Carousel -->
                                <div id="roomImageCarousel" class="carousel slide" data-ride="carousel">
                                    <div class="carousel-inner">
                                        <c:forEach items="${listImage}" var="image" varStatus="status">
                                            <div class="carousel-item ${status.index == 0 ? 'active' : ''}">
                                                <img src="${image}" class="d-block w-100" alt="${s.getTenNhaTro()}">
                                            </div>
                                        </c:forEach>
                                    </div>
                                    <a class="carousel-control-prev" href="#roomImageCarousel" role="button" data-slide="prev">
                                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                        <span class="sr-only">Previous</span>
                                    </a>
                                    <a class="carousel-control-next" href="#roomImageCarousel" role="button" data-slide="next">
                                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                        <span class="sr-only">Next</span>
                                    </a>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <h3>${s.getTenNhaTro()} </h3>

                                <p><strong>Description:</strong> ${s.getMo_ta()}</p>
                                <p><strong>Address</strong> ${s.getDia_chi()}</p>
                                <p><strong>Owner </strong> ${s.getChuTro().getName()}</p>
                            </div>
                        </div>
                    </div>
                    <div class="card-footer">

                        <a href="nhatro" class="btn btn-secondary">Back to Room List</a>
                    </div>
                </div>
            </div>

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