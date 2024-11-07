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
        <style>  .dropdown-menu {
                display: none;
                list-style: none;
                padding: 0px 27px;
                margin: 0px ;
            }

            /* Khi li có class active, hiển thị dropdown */
            .dropdown.active .dropdown-menu {
                display: block;
            }
        </style>
    </head>
    <body>
         <nav class="sidebar">
            <header>
                <div class="image-text">
                    <a href="home.jsp">
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


                    <li class="">
                        <a href="#">
                            <i class='bx bx-home-alt icon' ></i>
                            <span class="text nav-text">Trang chủ</span>
                        </a>
                    </li>

                    <li class="dropdown">
                        <a href="javascript:void(0);" class="dropdown-toggle" onclick="toggleDropdown()">
                            <i class='bx bx-cog icon'></i>
                            <span class="text nav-text">Báo cáo</span>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="statistic-room"><span class="text nav-text" style="margin-left: 30px">Phòng trống</span></a></li>
                            <li><a href="statistic-revenue"><span class="text nav-text" style="margin-left: 30px">Doanh thu</span></a></li>
                            <li><a href="khach-no"><span class="text nav-text" style="margin-left: 30px">Khách nợ</span></a></li>
                            <li><a href="khach-coc"><span class="text nav-text" style="margin-left: 30px">Khách cọc</span></a></li>
                            <li><a href="khach-sap-het-han"><span class="text nav-text">Sắp hết hạn hợp đồng</span></a></li>
                            <li><a href="thiet-bi"><span class="text nav-text" style="margin-left: 33px">Thiết bị</span></a></li>
                        </ul>
                    </li>


                    <li class="">
                        <a href="nhatro">
                            <i class='bx bxs-home icon' ></i>
                            <span class="text nav-text">Nhà trọ</span>
                        </a>
                    </li>
                    <c:if test="${sessionScope.account.role == 'landlord'}">
                     <li class="">
                            <a href="manage-new" class="">
                                <i class='bx bx-bell icon ' ></i>
                                <span class="text nav-text">Thông báo</span>
                            </a>                      
                     </li>
                    </c:if>
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
                        <a href="loaddichvu" >
                            <i class='bx bx-bell icon'></i>
                            <span class="text nav-text">Dịch vụ</span>
                        </a>
                    </li>

                    <li class="">
                        <a href="hop-dong">
                            <i class='bx bx-id-card icon' ></i>
                            <span class="text nav-text">Hợp đồng</span>
                        </a>
                    </li>

                    <li class="">
                        <a href="hoadon" >
                            <i class='bx bx-wallet icon' ></i>
                            <span class="text nav-text">Hóa đơn</span>
                        </a>
                    </li>
                    <c:if test="${sessionScope.account.role == 'landlord'}">
                        <li class="">
                            <a href="loadThietBi">
                                <i class='bx bx-devices icon' ></i>
                                <span class="text nav-text">Thiết bị</span>
                            </a>
                        </li>
                    </c:if>

                    </ul>
                </div>

                <div class="bottom-content">
                    <li class="">
                        <a href="#">
                            <i class='bx bx-log-out icon' ></i>
                            <span class="text nav-text">Logout</span>
                        </a>
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
                                <div id="roomImageCarousel" class="carousel slide " data-ride="carousel">
                                    <div class="carousel-inner img-detailRoom">
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
                                <h3>Phòng: ${room.tenPhongTro}</h3>
                                <p><strong>Nhà trọ:</strong> ${room.tenNhaTro}</p>
                                <p><strong>Loại phòng:</strong> ${room.tenLoaiPhong}</p>
                                <p><strong>Tầng:</strong> ${room.tang}</p>
                                <p><strong>Diện tích:</strong> ${room.dien_tich} m²</p>
                                <p><strong>Giá tiền:</strong> <fmt:formatNumber value="${room.gia}" type="currency" currencyCode="VND"/></p>

                                <p><strong>Trạng thái:</strong> 
                                    <span class="badge
                                          ${room.trang_thai == 'T' ? 'badge-success' : room.trang_thai == 'D' ? 'badge-danger' : 'badge-warning'}">
                                        ${room.trang_thai == 'T' ? 'Trống' : room.trang_thai == 'D' ? 'Đang thuê' : 'Đang sửa'}
                                    </span>

                                </p>
                                <p><strong>Mô tả:</strong> ${room.mo_ta}</p>
                            </div>
                        </div>
                        <div class="row mt-4">
                            <div class="col-12">
                                <h4>Danh sách thiết bị trong phòng</h4>


                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Tên thiết bị</th>
                                            <th>Số lượng</th>
                                            <th>Trạng thái</th>
                                            <th>Mô tả</th>
                                            <th>Hành động</th> <!-- New column -->
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${thietBiList}" var="thietBi">
                                            <tr>
                                                <td>${thietBi.ID_ThietBiPhong}</td>
                                                <td>${thietBi.tenThietBi}</td>
                                                <td>${thietBi.so_luong}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${thietBi.trang_thai == 'T'}">
                                                            <span class="badge badge-success">Tốt</span>
                                                        </c:when>
                                                        <c:when test="${thietBi.trang_thai == 'BT'}">
                                                            <span class="badge badge-warning">Bình thường</span>
                                                        </c:when>
                                                        <c:when test="${thietBi.trang_thai == 'CSC'}">
                                                            <span class="badge badge-danger">Cần sửa chữa</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="badge badge-secondary">${thietBi.trang_thai}</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>${thietBi.mo_ta}</td>
                                                <td>
                                                    <button class="btn btn-danger btn-sm" data-toggle="modal" data-target="#deleteEquipmentModal${thietBi.ID_ThietBiPhong}">
                                                        Xóa
                                                    </button>
                                                    <button class="btn btn-primary btn-sm" 
                                                            onclick="openEditEquipmentModal(${thietBi.ID_ThietBiPhong}, '${thietBi.so_luong}', '${thietBi.trang_thai}', '${thietBi.mo_ta}', ${room.ID_Phong}, '${thietBi.tenThietBi}')">
                                                        Sửa
                                                    </button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                        <c:forEach items="${thietBiList}" var="thietBi">
                            <div id="deleteEquipmentModal${thietBi.ID_ThietBiPhong}" class="modal fade" tabindex="-1" aria-labelledby="deleteEquipmentModalLabel" aria-hidden="true">
                                <div class="modal-dialog modal-confirm">
                                    <div class="modal-content">
                                        <div class="modal-header flex-column">
                                            <div class="icon-box">
                                                <i class="fa-solid fa-circle-xmark"></i>
                                            </div>
                                            <h5 class="modal-title w-100">Bạn có chắc chắn muốn xóa thiết bị này?</h5>
                                        </div>
                                        <div class="modal-body">
                                            <p>Thiết bị: ${thietBi.tenThietBi}</p>
                                            <p>Số lượng: ${thietBi.so_luong}</p>
                                        </div>
                                        <div class="modal-footer justify-content-center">
                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
                                            <a href="deleteThietBiFromRoom?idThietBiPhong=${thietBi.ID_ThietBiPhong}&idPhong=${room.ID_Phong}" class="btn btn-danger" style="color: white">Xóa thiết bị</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                        <!-- Add this right after the existing equipment table in detailRoom.jsp -->
                        <div class="row mt-4">
                            <div class="col-12">
                                <button class="btn btn-primary" data-toggle="modal" data-target="#addEquipmentModal">
                                    Thêm thiết bị mới
                                </button>
                            </div>
                        </div>

                        <!-- Add Equipment Modal -->
                        <div class="modal fade" id="addEquipmentModal" tabindex="-1" role="dialog" aria-labelledby="addEquipmentModalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="addEquipmentModalLabel">Thêm thiết bị vào phòng</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <form action="addThietBiToRoom" method="POST">
                                        <div class="modal-body">
                                            <input type="hidden" name="idPhong" value="${room.ID_Phong}">

                                            <div class="form-group">
                                                <label for="idThietBi">Chọn thiết bị:</label>
                                                <select class="form-control" id="idThietBi" name="idThietBi" required>
                                                    <c:forEach items="${allThietBi}" var="tb">
                                                        <option value="${tb.ID_ThietBi}">${tb.tenThietBi}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>

                                            <div class="form-group">
                                                <label for="soLuong">Số lượng:</label>
                                                <input type="number" class="form-control" id="soLuong" name="soLuong" required>
                                            </div>

                                            <div class="form-group">
                                                <label for="trangThai">Trạng thái:</label>
                                                <select class="form-control" id="trangThai" name="trangThai" required>
                                                    <option value="T" >Tốt</option>
                                                    <option value="BT" >Bình thường</option>
                                                    <option value="CSC" >Cần sửa chữa</option>
                                                </select>
                                            </div>

                                            <div class="form-group">
                                                <label for="moTa">Mô tả:</label>
                                                <textarea class="form-control" id="moTa" name="moTa" rows="3"></textarea>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                                            <button type="submit" class="btn btn-primary">Thêm thiết bị</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>

                        <!-- Thêm Modal để cập nhật thiết bị -->
                        <div class="modal fade" id="editEquipmentModal" tabindex="-1" role="dialog" aria-labelledby="editEquipmentModalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="editEquipmentModalLabel">Cập nhật thiết bị trong phòng</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <form action="updateThietBiInRoom" method="POST">
                                        <div class="modal-body">
                                            <input type="hidden" id="editIdThietBiPhong" name="idThietBiPhong">
                                            <input type="hidden" id="editIdPhong" name="idPhong">

                                            <div class="form-group">
                                                <label for="editTenThietBi">Tên thiết bị:</label>
                                                <input type="text" class="form-control" id="editTenThietBi" readonly>
                                            </div>

                                            <div class="form-group">
                                                <label for="editSoLuong">Số lượng:</label>
                                                <input type="number" class="form-control" id="editSoLuong" name="soLuong" required>
                                            </div>

                                            <div class="form-group">
                                                <label for="editTrangThai">Trạng thái:</label>
                                                <select class="form-control" id="editTrangThai" name="trangThai" required>
                                                    <option value="T" >Tốt</option>
                                                    <option value="BT" >Bình thường</option>
                                                    <option value="CSC" >Cần sửa chữa</option>
                                                </select>
                                            </div>

                                            <div class="form-group">
                                                <label for="editMoTa">Mô tả:</label>
                                                <textarea class="form-control" id="editMoTa" name="moTa" rows="3"></textarea>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                                            <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>

                        <!-- Existing error message for adding equipment -->
                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-danger" role="alert">
                                ${errorMessage}
                            </div>
                        </c:if>

                        <!-- New error message for updating equipment -->
                        <c:if test="${not empty updateErrorMessage}">
                            <div class="alert alert-warning" role="alert">
                                ${updateErrorMessage}
                            </div>
                        </c:if>

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
            function confirmDeleteEquipment(idThietBiPhong, idPhong) {
                if (confirm('Bạn có chắc chắn muốn xóa thiết bị này khỏi phòng không?')) {
                    window.location.href = 'deleteThietBiFromRoom?idThietBiPhong=' + idThietBiPhong + '&idPhong=' + idPhong;
                }
            }

            function openEditEquipmentModal(idThietBiPhong, soLuong, trangThai, moTa, idPhong, tenThietBi) {
                document.getElementById('editIdThietBiPhong').value = idThietBiPhong;
                document.getElementById('editIdPhong').value = idPhong;
                document.getElementById('editTenThietBi').value = tenThietBi;
                document.getElementById('editSoLuong').value = soLuong;
                document.getElementById('editTrangThai').value = trangThai;
                document.getElementById('editMoTa').value = moTa;

                $('#editEquipmentModal').modal('show');
            }
        </script>
        <script>
            function toggleDropdown() {
                var dropdown = document.querySelector('.dropdown');
                dropdown.classList.toggle('active'); // Thêm/xóa class 'active' khi nhấn
            }
        </script>


    </body>
</html>