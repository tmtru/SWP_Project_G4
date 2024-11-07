<%-- 
    Document   : UserDashBoard
    Created on : Sep 22, 2024, 11:36:15 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@page import="java.util.List, java.util.ArrayList,java.text.DecimalFormat, model.DichVu, model.Phong, dal.DichVuDAO, dal.PhongDAO"%>
<!DOCTYPE html>

<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- Bootstrap CSS -->

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <!----======== CSS ======== -->
        <link rel="stylesheet" href="css/styleDichVu.css">
        <link rel="stylesheet" href="css/styleRoom.css">
        <link rel="stylesheet" href="css/modelDelete.css">
        <script src="https://kit.fontawesome.com/aab0c35bef.js" crossorigin="anonymous"></script>


        <!----===== Boxicons CSS ===== -->
        <link rel="stylesheet" href="css/open-iconic-bootstrap.min.css">
        <link href='https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css' rel='stylesheet'>
        <!--jquery-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>


    </head>
    <style>
        textarea {
            resize: none;
        }
    </style>
    <body>
        <c:set var="a" value="${sessionScope.account}"></c:set>
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
                                <a href="room" >
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
                                <a href="loaddichvu" class="active">
                                    <i class='bx bx-bell icon'></i>
                                    <span class="text nav-text">Dịch vụ</span>
                                </a>
                            </li>

                             <c:if test="${sessionScope.account.role == 'landlord'}">
                        <li class="">
                            <a href="DanhSachCacHopDongByAdmin">
                                <i class='bx bx-id-card icon' ></i>
                            <span class="text nav-text">Hợp đồng</span>
                            </a>
                        </li>
                    </c:if>
                   
                         <c:if test="${sessionScope.account.role == 'manager'}">
                        <li class="">
                            <a href="DanhSachCacHopDongByManager">
                                <i class='bx bx-id-card icon' ></i>
                            <span class="text nav-text">Hợp đồng</span>
                            </a>
                        </li>
                    </c:if>


                            <li class="">
                                <a href="hoadon">
                                    <i class='bx bx-wallet icon' ></i>
                                    <span class="text nav-text">Hóa đơn</span>
                                </a>
                            </li>

                            <li class="">
                                <a href="loadThietBi">
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
                <section class="property-management pb-3">
                    <div class="header">
                        <h2>Danh sách dịch vụ khu trọ</h2>

                    </div>
                    <p>Dịch vụ sẽ phải gán vào hợp đồng để khi thánh toán sẽ được tự động thêm vào hóa đơn</p>



                    <div class="filters">
                    <c:if test="${a!=null}">
                        <c:if test="${a.role.equals('landlord')}">
                            <button class="btn add-room " data-toggle="modal" data-target="#addRoomModal">+ Thêm dịch vụ</button>

                            <!-- Modal -->
                            <div class="modal fade" id="addRoomModal" tabindex="-1" role="dialog" aria-labelledby="addRoomModalLabel" aria-hidden="true">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="addRoomModalLabel">Thêm dịch vụ</h5>
                                            <button type="button " class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            <form action="adddichvu" method="post">

                                                <div class="form-group">
                                                    <label for="tendichvu">Tên Dịch vụ:</label>
                                                    <input type="text" class="form-control" id="tendichvu" name="tendichvu" required>
                                                    <div class="invalid-feedback" id="tendichvuError" style="display: none;">Tên dịch vụ không được để trống.</div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="donGia">Đơn giá (VNĐ):</label>
                                                    <input type="number" class="form-control" id="donGia" name="donGia" required>
                                                    <div class="invalid-feedback">Đơn giá phải lớn hơn 0.</div>
                                                </div>

                                                <div class="form-group" hidden>
                                                    <label for="donvi">Đơn vị:</label>
                                                    <select class="form-control" id="donvi" name="donvi" required>
                                                        <option value="Tháng" selected>Tháng</option>
                                                    </select>
                                                </div>

                                                <div class="form-group">
                                                    <label for="ghichu">Ghi chú:</label>
                                                    <textarea id="ghichu" name="mota" rows="4" cols="55"></textarea>
                                                    <div class="invalid-feedback" id="ghichuError" style="display: none;">Ghi chú không được để trống.</div>
                                                </div>

                                                <button type="submit" class="btn btn-primary">Thêm</button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </div>
                </section>
                <section class="ftco-section">
                    <div class="container-fluid mt-4">
                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-danger" role="alert">
                                ${errorMessage}
                            </div>
                        </c:if>


                    </div>


                    <div class="mx-5">
                        <table class="table mt-3">
                            <thead class="thead-dark">
                                <tr>
                                    <th scope="col">ID</th>
                                    <th scope="col">Tên Dịch vụ</th>
                                    <th scope="col">Đơn giá</th>
                                    <th scope="col">Đơn vị</th>
                                    <th scope="col">Ghi chú</th>
                                        <c:if test="${a.role.equals('landlord')}">
                                        <th scope="col">Kích hoạt</th>
                                        <th scope="col">Hành động</th>
                                        </c:if>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${dichVuList}" var="dv">
                                    <tr>
                                        <th scope="row">${dv.ID_DichVu}</th>
                                        <td>${dv.tenDichVu}</td>
                                        <td>${dv.don_gia}</td>
                                        <td>/${dv.don_vi}</td>
                                        <td>${dv.mo_ta}</td>
                                        <c:if test="${a.role.equals('landlord')}">
                                            <td>
                                                <label class="switch">
                                                    <input type="checkbox" ${dv.isActive ? "checked onclick='this.checked=true;'" : "onclick='updateIsActive(this)'"} data-id="${dv.ID_DichVu}">
                                                    <span class="slider" 
                                                          <c:if test="${dv.isActive}"> data-bs-toggle="modal" data-bs-target="#myModal${dv.ID_DichVu}"</c:if>></span>
                                                    </label>

                                                    <!-- Modal HTML -->
                                                    <div id="myModal${dv.ID_DichVu}" class="modal fade" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                                    <div class="modal-dialog modal-confirm">
                                                        <div class="modal-content">
                                                            <div class="modal-header flex-column">
                                                                <div class="icon-box">
                                                                    <i class="material-icons"><i class="fa-solid fa-circle-xmark"></i></i>
                                                                </div>
                                                                <h5 class="modal-title w-100">Bạn có chắc chắn bạn muốn tắt dịch vụ <br/> <span style="color: #5932ea">"${dv.tenDichVu}"</span> ?</h5>
                                                            </div>
                                                            <div class="modal-body">
                                                                <p style="color: red">Lưu ý: Tắt dịch vụ khiến dịch vụ ko còn được tính trong hợp đồng khi thanh toán </p>

                                                            </div>
                                                            <div class="modal-footer justify-content-center">
                                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                                                                <button type="button" class="btn btn-danger">
                                                                    <a href="action?action=dele&id=${dv.ID_DichVu}" class="edit-film" style="color: white !important;">Tắt dịch vụ</a>
                                                                </button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                            </td>

                                            <td>
                                                <button class="btn edit" data-toggle="modal" data-target="#EditModal${dv.ID_DichVu}">Chỉnh sửa</button>
                                                <div class="modal fade" id="EditModal${dv.ID_DichVu}" tabindex="-1" role="dialog" aria-labelledby="addRoomModalLabel" aria-hidden="true">
                                                    <div class="modal-dialog" role="document">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <h5 class="modal-title" id="addRoomModalLabel">Chỉnh sửa</h5>
                                                                <button type="button " class="close" data-dismiss="modal" aria-label="Close">
                                                                    <span aria-hidden="true">&times;</span>
                                                                </button>
                                                            </div>
                                                            <div class="modal-body">
                                                                <form action="action?action=edit&id=${dv.ID_DichVu}" method="post">
                                                                    <div class="form-group">
                                                                        <c:if test="${dv.tenDichVu=='Điện'}">
                                                                            <label for="tendichvu">Tên Dịch vụ:</label>
                                                                            <input type="text" class="form-control" id="tendichvu" name="tendichvu" value="${dv.tenDichVu}" readonly="">
                                                                        </c:if>
                                                                        <c:if test="${dv.tenDichVu!='Điện'}">
                                                                            <label for="tendichvu">Tên Dịch vụ:</label>
                                                                            <input type="text" class="form-control" id="tendichvu" name="tendichvu" value="${dv.tenDichVu}" required="">
                                                                        </c:if>
                                                                    </div>

                                                                    <div class="form-group">
                                                                        <label for="donGia">Đơn giá (VNĐ):</label>
                                                                        <input type="number" class="form-control" id="donGia" name="donGia" value="${dv.don_gia}" required>
                                                                        <div class="invalid-feedback">Đơn giá phải lớn hơn hoặc bằng 0.</div>
                                                                    </div>

                                                                    <div class="form-group khac-div" hidden>
                                                                        <label for="donvi1">Đơn vị:</label>
                                                                        <input type="text" class="form-control" id="donGia" name="donvi" value="${dv.don_vi}" required>
                                                                    </div>

                                                                    <div class="form-group">
                                                                        <label for="ghichu">Ghi chú:</label>
                                                                        <textarea id="ghichu" name="mota" rows="4" cols="55">${dv.mo_ta}</textarea>
                                                                        <div class="invalid-feedback" id="ghichuError" style="display: none;">Ghi chú không hợp lệ.</div>
                                                                    </div>

                                                                    <button type="submit" class="btn btn-primary">Lưu chỉnh sửa</button>
                                                                </form>

                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                            </td>

                                        </c:if>
                                    </tr>
                                </c:forEach>

                            </tbody>
                        </table>
                    </div>



                    <!-- Pagination -->


                </section>
            </c:if>
        </section>

        <!-- Bootstrap JS and dependencies -->
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>


        <script>
            const forms = document.querySelectorAll('form');

            forms.forEach(form => {
                form.addEventListener('submit', function (event) {
                    let valid = true;
                    const fieldsToTrimAndCheck = ['#tendichvu', '#donGia']; // Add the donGia selector

                    fieldsToTrimAndCheck.forEach(selector => {
                        const input = form.querySelector(selector);

                        if (input) { // Ensure the input exists
                            // Trim the value to remove extra spaces
                            input.value = input.value.trim();

                            // Validate to ensure the field is not empty
                            if (!input.value) {
                                valid = false;
                                input.classList.add('is-invalid');
                                const errorElement = document.getElementById(`${input.id}Error`);
                                if (errorElement) {
                                    errorElement.style.display = 'block';
                                }
                            } else {
                                input.classList.remove('is-invalid');
                                const errorElement = document.getElementById(`${input.id}Error`);
                                if (errorElement) {
                                    errorElement.style.display = 'none';
                                }
                            }

                            // Additional validation for donGia
                            if (selector === '#donGia') {
                                const donGiaValue = parseFloat(input.value);
                                if (isNaN(donGiaValue) || donGiaValue < 0) {
                                    valid = false;
                                    input.classList.add('is-invalid');
                                    const errorElement = document.getElementById(`${input.id}Error`);
                                    if (errorElement) {
                                        errorElement.textContent = 'Đơn giá phải lớn hơn hoặc bằng 0.'; // Change the error message
                                        errorElement.style.display = 'block';
                                    }
                                } else {
                                    input.classList.remove('is-invalid');
                                    const errorElement = document.getElementById(`${input.id}Error`);
                                    if (errorElement) {
                                        errorElement.style.display = 'none';
                                    }
                                }
                            }
                        }
                    });

                    // Prevent form submission if not valid
                    if (!valid) {
                        event.preventDefault();
                        console.log('Form is invalid, preventing submission.');
                    } else {
                        console.log('Form is valid, submission allowed.');
                    }
                });
            });
        </script>

        <script>

            //delete sẻvice confirm
            function confirmDelete(idDichvu, tenDichVu) {
                if (confirm('Bạn có muốn xóa dịch vụ ' + tenDichVu + ' ?')) {
                    window.location.href = 'action?action=dele&id=' + idDichvu;
                }
            }
        </script>
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
        </script>

        <script>
            function updateIsActive(checkbox) {
                const idDichVu = $(checkbox).data("id"); // Lấy ID dịch vụ từ data-id
                const isActive = checkbox.checked; // Trạng thái mới của checkbox

                // Gọi AJAX để cập nhật trạng thái isActive
                $.ajax({
                    url: 'action?action=update',
                    type: 'POST',
                    data: {id: idDichVu, isActive: true}, // Gửi ID và trạng thái isActive
                    success: function (response) {
                        console.log('Cập nhật thành công:', response);
                        location.reload(); // Tải lại trang
                    },
                    error: function (xhr, status, error) {
                        console.error('Có lỗi xảy ra:', error);
                    }
                });
            }
        </script>




    </body>
</html>
