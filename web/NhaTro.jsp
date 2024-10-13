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
        <title>Danh sách nhà trọ</title>
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
                            <a href="nhatro" class="active">
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
                            <a href="loaddichvu" >
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

            <section class="property-management">

                <div class="action-buttons" style="margin-bottom: 20px">
                    <button class="btn add-property" data-toggle="modal" data-target="#addRoomModal">+ Thêm nhà trọ</button>
                    <form action="nhatro" method="get" class="filters">
                        <input type="text" name="search" value="${param.search}" placeholder="Search..">
                        <button type="submit"><i class='bx bx-search icon'></i></button>
                    </form>
                </div>
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
                <!-- Add Room Modal -->
                <div class="modal fade" id="addRoomModal" tabindex="-1" role="dialog" aria-labelledby="addRoomModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="addRoomModalLabel">Thêm nhà trọ</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <form id="addRoomForm" action="nhatro" method="post" enctype="multipart/form-data">
                                    <input type="hidden" name="action" value="add">

                                    <div class="form-group">
                                        <label for="tenNhaTro">Tên nhà trọ</label>
                                        <input type="text" class="form-control" id="tenNhaTro" name="tenNhaTro" >
                                    </div>

                                    <div class="form-group">
                                        <label for="diaChi">Địa chỉ</label>
                                        <input type="text" class="form-control" id="diaChi" name="diaChi" >
                                    </div>

                                    <div class="form-group">
                                        <label for="moTa">Mô tả</label>
                                        <textarea class="form-control" id="moTa" name="moTa" rows="3"></textarea>
                                    </div>

                                    <div class="form-group">
                                        <label for="images">Hình ảnh (tối đa 5 hình)</label>
                                        <input type="file" class="form-control" id="images" name="images" multiple accept="image/*">
                                    </div>

                                    <button type="submit" class="btn btn-primary">Thêm</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <section class="ftco-section">
                <div class="container">
                    <div class="row" style="margin-top: 20px">
                        <c:forEach var="s" items="${listS}">
                            <div class="col-md-4 mb-4">
                                <div class="card h-100">
                                    <div class="card-body">
                                        <h5 class="card-title">${s.getTenNhaTro()}</h5>
                                        <p class="card-text">
                                            <strong>Room</strong> <a href="room?nhaTro=${s.ID_NhaTro}">${s.getRoomNumber()}</a><br>
                                        </p>
                                        <p class="card-text"><strong>Owner </strong> ${s.getChuTro().getName()}</p>
                                        <p class="card-text">
                                            <strong>Address</strong> ${s.getDia_chi()}<br>
                                        </p>
                                        <p class="card-text">
                                            <strong>Description</strong> ${s.getMo_ta()}<br>
                                        </p>
                                    </div>
                                    <div class="card-footer bg-transparent">
                                        <div class="d-flex justify-content-between">
                                            <a href="nhatro-detail?id=${s.ID_NhaTro}" class="btn btn-outline-secondary btn-sm custom-btn" style="color: black;">
                                                <i class="bx bx-info-circle"></i> Details
                                            </a>
                                            <c:if test="${isOwwer == true}">
                                                <div class="d-flex justify-content-between">
                                                    <button class="btn btn-outline-secondary btn-sm custom-btn" 
                                                            data-toggle="modal" data-target="#editRoomModal-${s.getID_NhaTro()}"
                                                            style="color: black;">
                                                        <i class="bx bx-edit"></i> Edit
                                                    </button>
                                                </div>
                                                <div class="d-flex justify-content-between">
                                                    <!-- Existing buttons like "Edit" -->
                                                    <a href="#" class="btn btn-outline-secondary btn-sm custom-btn" style="color: black;" 
                                                       onclick="confirmDelete(${s.getID_NhaTro()}); return false;">
                                                        <i class="bx bx-minus-circle"></i> Delete
                                                    </a>
                                                    <form id="deleteForm" action="nhatro" method="post" style="display:none;">
                                                        <input type="hidden" name="action" value="delete">
                                                        <input type="hidden" name="nhaTroId" id="deleteNhaTroId">
                                                    </form>

                                                </div>
                                            </c:if>

                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Edit Room Modal -->
                            <div class="modal fade" id="editRoomModal-${s.getID_NhaTro()}" tabindex="-1" role="dialog" aria-labelledby="editRoomModalLabel-${s.getID_NhaTro()}" aria-hidden="true">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="editRoomModalLabel-${s.getID_NhaTro()}">Chỉnh sửa nhà trọ</h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            <form id="editRoomForm-${s.getID_NhaTro()}" action="nhatro" method="post" enctype="multipart/form-data">
                                                <input type="hidden" name="action" value="edit">
                                                <input type="hidden" name="nhaTroId" value="${s.getID_NhaTro()}">

                                                <div class="form-group">
                                                    <label for="tenNhaTro">Tên nhà trọ</label>
                                                    <input type="text" class="form-control" name="tenNhaTro" value="${s.getTenNhaTro()}" >
                                                </div>

                                                <div class="form-group">
                                                    <label for="diaChi">Địa chỉ</label>
                                                    <input type="text" class="form-control" name="diaChi" value="${s.getDia_chi()}" >
                                                </div>

                                                <div class="form-group">
                                                    <label for="moTa">Mô tả</label>
                                                    <textarea class="form-control" name="moTa" rows="3">${s.getMo_ta()}</textarea>
                                                </div>

                                                <div class="form-group">
                                                    <label>Hình ảnh hiện tại</label>
                                                    <div class="current-images">
                                                        <c:forEach var="image" items="${s.getAnhNhaTro()}">
                                                            <div class="image-block">
                                                                <img src="${image}" alt="Room Image" style="width: 100px; height: 100px;">
                                                                <input type="checkbox" name="deleteImages" value="${image}"> Remove
                                                            </div>
                                                        </c:forEach>
                                                    </div>
                                                </div>

                                                <c:if test="${s.getAnhNhaTro().size() < 5}">
                                                    <div class="form-group">
                                                        <label for="images">Tải lên thêm hình ảnh (tối đa 5)</label>
                                                        <input type="file" class="form-control" name="images" multiple accept="image/*">
                                                    </div>
                                                </c:if>

                                                <button type="submit" class="btn btn-primary">Cập nhật</button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <div class="pagination">
                        <c:if test="${currentPage > 1}">
                            <a href="?page=${currentPage - 1}" class="prev-next"><i class="fas fa-chevron-left"></i></a>
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
                            <a href="?page=${currentPage + 1}" class="prev-next"><i class="fas fa-chevron-right"></i></a>
                            </c:if>
                    </div>
                </div>
            </section>
        </section>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                // Add validation for addRoomForm
                document.getElementById("addRoomForm").addEventListener("submit", function (e) {
                    if (!validateForm(this)) {
                        e.preventDefault();
                    }
                });

                // Add validation for each editRoomForm
                document.querySelectorAll("[id^='editRoomForm-']").forEach(function (form) {
                    form.addEventListener("submit", function (e) {
                        if (!validateForm(this)) {
                            e.preventDefault();
                        }
                    });
                });
            });

            // Validation function
            function validateForm(form) {
                clearErrors(form);  // Clear previous errors
                let isValid = true;

                const tenNhaTro = form.querySelector("input[name='tenNhaTro']");
                const diaChi = form.querySelector("input[name='diaChi']");
                const images = form.querySelector("input[name='images']");

                if (!tenNhaTro.value.trim()) {
                    showError(tenNhaTro, "Tên nhà trọ không được để trống");
                    isValid = false;
                }

                if (!diaChi.value.trim()) {
                    showError(diaChi, "Địa chỉ không được để trống");
                    isValid = false;
                }

                if (images && images.files.length > 5) {
                    showError(images, "Chỉ có thể tải lên tối đa 5 hình ảnh");
                    isValid = false;
                }

                return isValid;
            }

            // Show error function
            function showError(element, message) {
                const errorDiv = document.createElement("div");
                errorDiv.classList.add("error-message");
                errorDiv.style.color = "red";
                errorDiv.innerText = message;
                element.parentElement.appendChild(errorDiv);
                element.classList.add("is-invalid");
            }

            // Clear previous errors
            function clearErrors(form) {
                form.querySelectorAll(".error-message").forEach(function (errorDiv) {
                    errorDiv.remove();
                });
                form.querySelectorAll(".is-invalid").forEach(function (input) {
                    input.classList.remove("is-invalid");
                });
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
            //filter room by floor ko can submit


            //delete room confirm
            function confirmDelete(nhaTroId) {
                if (confirm("Bạn có chắc chắn muốn xóa nhà trọ này?")) {
                    // Set the NhaTro ID in the hidden form
                    document.getElementById("deleteNhaTroId").value = nhaTroId;
                    // Submit the form
                    document.getElementById("deleteForm").submit();
                }
            }
        </script>

    </body>
</html>