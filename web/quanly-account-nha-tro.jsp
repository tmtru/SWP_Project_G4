<%-- 
    Document   : room
    Created on : Sep 22, 2024, 1:50:44 AM
    Author     : hihihihaha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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
            .require{
                color: red;
            }
            /* Style for the table */
            table {
                width: 100%;
                border-collapse: collapse;
                margin: 20px 0;
                font-size: 16px;
                text-align: left;
            }

            /* Style for table header */
            thead th {
                background-color: #f2f2f2;
                color: #333;
                font-weight: bold;
                padding: 12px 15px;
                border: 1px solid #ddd;
            }

            /* Style for table body rows */
            tbody td {
                padding: 12px 15px;
                border: 1px solid #ddd;
            }

            /* Zebra striping for table rows */
            tbody tr:nth-child(even) {
                background-color: #f9f9f9;
            }

            /* Hover effect for rows */
            tbody tr:hover {
                background-color: #f1f1f1;
            }

            /* Style for actions button */
            .action-button {
                background-color: #007bff;
                color: white;
                padding: 5px 10px;
                border: none;
                cursor: pointer;
                border-radius: 5px;
            }

            /* Style for inactive/disabled rows */
            tbody tr.inactive {
                background-color: #f2dede;
            }
        </style>
        <title>Quản lý thành viên</title>
    </head>
    <body>
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
        <c:if test="${isOwner == true}">
            <section class="home">
                <section class="property-management">

                    <h2>Quản lý thành viên nhà trọ - ${nhatro.getTenNhaTro()}</h2> <a href="manager-action?id=${id}" style="float: right">Lịch sử hành động</a>


                </section>
                <div class="container mt-4">
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
                    <form action="quanly-account-nha-tro" method="post"  onsubmit="return validateForm()">
                        <div class="form-row">

                            <div class="form-group col-md-3">
                                <div class="col-md-12 row">
                                    <label for="name">Tên <span class="require">*</span></label>
                                    <input type="text" class="form-control" id="name" name="name" value="${name}">
                                    <input type="hidden" class="form-control" name="action" value="add">
                                    <input type="hidden" class="form-control" name="id" value="${param.id}">
                                    <small id="nameError" class="text-danger error-message"></small>
                                </div>
                            </div>

                            <!-- Username Field -->
                            <div class="form-group col-md-3">
                                <div class="col-md-12 row">
                                    <label for="username">Tên đăng nhập <span class="require">*</span></label>
                                    <input type="text" class="form-control" id="username" name="username" value="${username}">
                                    <small id="usernameError" class="text-danger error-message"></small>
                                </div>
                            </div>

                            <!-- Email Field -->
                            <div class="form-group col-md-3">
                                <div class="col-md-12 row">
                                    <label for="email">Email <span class="require">*</span></label>
                                    <input type="text" class="form-control" id="email" name="email" value="${email}">
                                    <small id="emailError" class="text-danger error-message"></small>
                                </div>
                            </div>

                            <!-- Password Field -->
                            <div class="form-group col-md-3">
                                <div class="col-md-12 row">
                                    <label for="password">Mật khẩu <span class="require">*</span></label>
                                    <input type="password" class="form-control" id="password" name="password" value="${password}">
                                    <small id="passwordError" class="text-danger error-message"></small>
                                </div>
                            </div>

                            <!-- Phone Field -->
                            <div class="form-group col-md-4">
                                <div class="col-md-12 row">
                                    <label for="phone">SDT <span class="require">*</span></label>
                                    <input type="text" class="form-control" id="phone" name="phone" value="${phone}">
                                    <small id="phoneError" class="text-danger error-message"></small>
                                </div>
                            </div>

                            <!-- Date of Birth Field -->
                            <div class="form-group col-md-4">
                                <div class="col-md-12 row">
                                    <label for="dob">Ngày sinh <span class="require">*</span></label>
                                    <input type="date" class="form-control" id="dob" name="dob"  value="${dob}">
                                    <small id="dobError" class="text-danger error-message"></small>
                                </div>
                            </div>

                            <!-- CCCD Field -->
                            <div class="form-group col-md-4">
                                <div class="col-md-12 row">
                                    <label for="cccd">CCCD <span class="require">*</span></label>
                                    <input type="text" class="form-control" id="cccd" name="cccd" value="${cccd}">
                                    <small id="cccdError" class="text-danger error-message"></small>
                                </div>
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="col-md-12">
                                <button type="submit" class="btn btn-primary" id="saveButton" >Lưu</button>
                                <a href="nhatro" class="btn btn-warning">Quay về</a>
                            </div>
                        </div>

                    </form>
                    <table>
                        <thead>
                            <tr>
                                <th>
                                    Tên
                                </th>
                                <th>
                                    Username
                                </th>
                                <th>
                                    Ngày sinh
                                </th>
                                <th>
                                    SDT
                                </th>
                                <th>
                                    CCCD
                                </th>
                                <th>
                                    Hành động
                                </th>

                            </tr>
                        </thead>
                        <h3 style="margin-top: 20px; text-align: center">Danh sách quản lý</h3>
                        <tbody>
                            <c:forEach var="l" items="${quanlys}">
                                <tr>
                                    <td>${l.name}</td>
                                    <td>${l.account.username}</td>
                                    <td><fmt:formatDate value="${l.dob}" pattern="dd/MM/yyyy"/></td>
                                    <td>${l.phone}</td>
                                    <td>${l.cccd}</td>
                                    <td> <form action="quanly-account-nha-tro" method="post" style="display:inline;">
                                            <input type="hidden" name="action" value="delete">
                                            <input type="hidden" name="quanLyId" value="${l.id}">
                                            <input type="hidden" name="id" value="${id}"> <!-- Current NhaTro ID -->
                                            <button type="submit" class="btn btn-danger" onclick="return confirm('Bạn có chắc muốn xóa quản lý này?');">Xóa</button>
                                        </form></td>
                                </tr>
                            </c:forEach>
                        </tbody>

                    </table>
                    <table>
                        <thead>
                            <tr>
                                <th>
                                    Tên
                                </th>
                                <th>
                                    Ngày sinh
                                </th>
                                <th>
                                    SDT
                                </th>
                                <th>
                                    CCCD
                                </th>
                                <th>
                                    Phòng
                                </th>
                                <th>
                                    Nghề Nghiệp
                                </th>
                                <th>
                                    HKTT
                                </th>


                            </tr>
                        </thead>
                        <h3 style="margin-top: 20px; text-align: center">Danh sách khách thuê</h3>
                        <tbody>
                            <c:forEach var="l" items="${listkKhachThue}">
                                <tr>
                                    <td>${l.name}</td>
                                    <td><fmt:formatDate value="${l.dob}" pattern="dd/MM/yyyy"/></td>
                                    <td>${l.phone}</td>
                                    <td>${l.cccd}</td>
                                    <td>${l.roomName}</td>
                                    <td>${l.job}</td>
                                    <td>${l.hk_thuong_tru}</td>
                                </tr>
                            </c:forEach>
                        </tbody>

                    </table>
                    <div class="pagination">
                        <c:if test="${currentPage > 1}">
                            <a href="?id=${id}&page=${currentPage - 1}" class="prev-next"><i class="fas fa-chevron-left"></i></a>
                            </c:if>

                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <c:choose>
                                <c:when test="${currentPage eq i}">
                                    <span class="current-page">${i}</span>
                                </c:when>
                                <c:otherwise>
                                    <a href="?id=${id}&page=${i}" class="page-number">${i}</a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>

                        <c:if test="${currentPage < totalPages}">
                            <a href="?id=${id}&page=${currentPage + 1}" class="prev-next"><i class="fas fa-chevron-right"></i></a>
                            </c:if>
                    </div>

                </div>
            </section>
        </c:if>
        <c:if test="${isOwner == false}">
            <section class="home">
                <section class="property-management">

                    <h2>Quản lý thành viên nhà trọ - ${nhatro.getTenNhaTro()}</h2> <a href="manager-action?id=${id}" style="float: right">Lích sử hành động</a>


                </section>
                <div class="container mt-4">
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


                    <table>
                        <thead>
                            <tr>
                                <th>
                                    Tên
                                </th>
                                <th>
                                    Ngày sinh
                                </th>
                                <th>
                                    SDT
                                </th>
                                <th>
                                    CCCD
                                </th>
                                <th>
                                    Phòng
                                </th>
                                <th>
                                    Nghề Nghiệp
                                </th>
                                <th>
                                    HKTT
                                </th>


                            </tr>
                        </thead>
                        <h3 style="margin-top: 20px; text-align: center">Danh sách khách thuê</h3>
                        <tbody>
                            <c:forEach var="l" items="${listkKhachThue}">
                                <tr>
                                    <td>${l.name}</td>
                                    <td><fmt:formatDate value="${l.dob}" pattern="dd/MM/yyyy"/></td>
                                    <td>${l.phone}</td>
                                    <td>${l.cccd}</td>
                                    <td>${l.roomName}</td>
                                    <td>${l.job}</td>
                                    <td>${l.hk_thuong_tru}</td>
                                </tr>
                            </c:forEach>
                        </tbody>

                    </table>
                    <div class="pagination">
                        <c:if test="${currentPage > 1}">
                            <a href="?id=${id}&page=${currentPage - 1}" class="prev-next"><i class="fas fa-chevron-left"></i></a>
                            </c:if>

                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <c:choose>
                                <c:when test="${currentPage eq i}">
                                    <span class="current-page">${i}</span>
                                </c:when>
                                <c:otherwise>
                                    <a href="?id=${id}&page=${i}" class="page-number">${i}</a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>

                        <c:if test="${currentPage < totalPages}">
                            <a href="?id=${id}&page=${currentPage + 1}" class="prev-next"><i class="fas fa-chevron-right"></i></a>
                            </c:if>
                    </div>

                </div>
            </section>
        </c:if>

        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.1/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
    function validateForm() {
        let isValid = true;

        // Username validation
        const username = document.getElementById("username").value.trim();
        const usernameError = document.getElementById("usernameError");
        const usernameRegex = /^[a-zA-Z0-9]+$/;
        if (!username || !usernameRegex.test(username)) {
            usernameError.textContent = "Username cannot be empty and must not contain special characters.";
            isValid = false;
        } else {
            usernameError.textContent = "";
        }

        // Name validation
        const name = document.getElementById("name").value.trim();
        const nameError = document.getElementById("nameError");
        const nameRegex = /^[a-zA-Z\s]+$/;
        if (!name || !nameRegex.test(name) || name.trim() === "") {
            nameError.textContent = "Name must contain only letters and spaces and cannot be empty or only spaces.";
            isValid = false;
        } else {
            nameError.textContent = "";
        }

        // Email validation
        const email = document.getElementById("email").value.trim();
        const emailError = document.getElementById("emailError");
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!email || !emailRegex.test(email)) {
            emailError.textContent = "Please enter a valid email address.";
            isValid = false;
        } else {
            emailError.textContent = "";
        }

        // Password validation
        const password = document.getElementById("password").value;
        const passwordError = document.getElementById("passwordError");
        if (!password || password.length < 6) {
            passwordError.textContent = "Password must be at least 6 characters long.";
            isValid = false;
        } else {
            passwordError.textContent = "";
        }

        // Phone validation
        const phone = document.getElementById("phone").value.trim();
        const phoneError = document.getElementById("phoneError");
        const phoneRegex = /^0\d{9}$/;
        if (!phone || !phoneRegex.test(phone)) {
            phoneError.textContent = "Phone must start with 0 and have exactly 10 digits.";
            isValid = false;
        } else {
            phoneError.textContent = "";
        }

        // DOB validation
        const dob = document.getElementById("dob").value;
        const dobError = document.getElementById("dobError");
        const currentDate = new Date();
        const dobDate = new Date(dob);
        if (!dob || dobDate >= currentDate) {
            dobError.textContent = "Date of Birth must be earlier than the current date.";
            isValid = false;
        } else {
            dobError.textContent = "";
        }

        // CCCD validation
        const cccd = document.getElementById("cccd").value.trim();
        const cccdError = document.getElementById("cccdError");
        const cccdRegex = /^\d+$/;
        if (!cccd || !cccdRegex.test(cccd)) {
            cccdError.textContent = "CCCD must contain digits only.";
            isValid = false;
        } else {
            cccdError.textContent = "";
        }

        // Prevent form submission if validation fails
        return isValid;
    }
</script>

    </body>
</html>
