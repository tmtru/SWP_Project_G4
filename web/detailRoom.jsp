<%-- 
    Document   : detailRoom
    Created on : Sep 22, 2024, 10:01:51 PM
    Author     : hihihihaha
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Room Details</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css">
        <!-- Custom CSS -->
        <link rel="stylesheet" href="css/styleRoom.css">
        <!-- Boxicons CSS -->
        <link href='https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css' rel='stylesheet'>
        <!-- Them font awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

        <!-- Bootstrap JS and dependencies -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </head>
    <body>
        <nav class="sidebar close">
            <header>
                <div class="image-text">
                    <span class="image">
                        <img src="images/logo.png" alt="alt" style="margin-top: 15px"/>
                        <!--<img src="logo.png" alt="">-->
                    </span>

                    <div class="text logo-text">
                        <h2 style="margin: 28px 10px">TQATM</h2>
                    </div>
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
                            <a href="#" class="active">
                                <i class='bx bx-bar-chart-alt-2 icon' ></i>
                                <span class="text nav-text">Phòng trọ</span>
                            </a>
                        </li>

                        <li class="">
                            <a href="">
                                <i class='bx bx-face icon' ></i>
                                <span class="text nav-text">Nhân viên</span>
                            </a>
                        </li>

                        <li class="loaddichvu">
                            <a href="#">
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
                <h2>Chi tiết phòng</h2>
                <div class="card">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-6">
                                <!-- Image Carousel -->
                                <div id="roomImageCarousel" class="carousel slide" data-ride="carousel">
                                    <div class="carousel-inner">
                                        <c:forEach items="${room.images}" var="image" varStatus="status">
                                            <div class="carousel-item ${status.index == 0 ? 'active' : ''}">
                                                <img src="${image}" class="d-block w-100" alt="${room.tenPhongTro}">
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
                                <h3>${room.tenNhaTro} - ${room.tenPhongTro}</h3>
                                <p><strong>Floor:</strong> ${room.tang}</p>
                                <p><strong>Area:</strong> ${room.dien_tich} m²</p>
                                <p><strong>Price:</strong> <fmt:formatNumber value="${room.gia}" type="currency" currencyCode="VND"/></p>
                                <p><strong>Room Type:</strong> ${room.tenLoaiPhong}</p>
                                <p><strong>Status:</strong> 
                                    <span class="badge ${room.trang_thai == 'T' ? 'badge-success' : 'badge-danger'}">
                                        ${room.trang_thai == 'T' ? 'Trống' : 'Đang thuê'}
                                    </span>
                                </p>
                                <p><strong>Description:</strong> ${room.mo_ta}</p>
                            </div>
                        </div>
                    </div>
                    <div class="card-footer">
                        
                        <a href="room" class="btn btn-secondary">Quay lại danh sách phòng</a>
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