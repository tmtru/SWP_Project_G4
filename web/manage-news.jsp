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
            .dropdown-menu {
                display: none;
                list-style: none;
                padding: 0px 27px;
                margin: 0px ;
            }

            /* Khi li có class active, hiển thị dropdown */
            .dropdown.active .dropdown-menu {
                display: block;
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
                            <a href="manage-new" class="active">
                                <i class='bx bx-bell icon ' ></i>
                                <span class="text nav-text">Thông báo</span>
                            </a>                      
                     </li>
                    </c:if>
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
            <section class="property-management">
                <h2>Quản lý thông báo</h2> 
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
                <form action="manage-new" method="post"  onsubmit="return validateForm()">
                    <div class="form-row">

                        <div class="form-group col-md-6">
                            <div class="col-md-12 row">
                                <label for="nhaTro">Chọn nhà trọ <span class="require">*</span></label>
                                <select name="nhaTro" class="form-control">
                                    <c:forEach items="${listNhaTro}" var="n">
                                        <option value="${n.getID_NhaTro()}">${n.getTenNhaTro()}</option>
                                    </c:forEach>
                                </select>
                                <input type="hidden" class="form-control" name="action" value="add">

                                <small id="nhaTroError" class="text-danger error-message"></small>
                            </div>
                        </div>

                        <div class="form-group col-md-6">
                            <div class="col-md-12 row">
                                <label for="title">Tiêu đề <span class="require">*</span></label>
                                <input type="text" class="form-control" id="title" name="title" >
                                <small id="titleError" class="text-danger error-message"></small>
                            </div>
                        </div>

                        <div class="form-group col-md-12">
                            <div class="col-md-12 row">
                                <label for="content">Content <span class="require">*</span></label><br>
                                <textarea  id="editor1" type="text" class="form-control" id="content" name="content"></textarea>
                                <small id="contentError" class="text-danger error-message"></small>
                            </div>
                        </div>



                        <div class="form-row">
                            <div class="col-md-12">
                                <button type="submit" class="btn btn-primary" id="saveButton" >Lưu</button>
                                <button type="reset" class="btn btn-warning">Xóa</button>
                            </div>
                        </div>
                    </div>
                </form>

                <h3 style="text-align: center">Danh sách thông báo</h3>

                <table>
                    <thead>
                        <tr>
                            <th>
                                STT
                            </th>
                            <th>
                                Tiêu đề
                            </th>
                            <th>
                                Ngày tạo
                            </th>
                            <th>
                                Nhà trọ
                            </th>
                            <th>
                                Hành động
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="n" items="${news}" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>${n.title}</td>
                                <td><fmt:formatDate value="${n.created_in}" pattern="dd/MM/yyyy HH:mm" /></td>
                                <td>${n.nhaTro.getTenNhaTro()}</td>
                                <td>
                                    <button class="btn btn-info" onclick="window.location.href = 'new-detail?id=${n.id}'" >Detail</button>
                                </td>
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
        <script src="https://cdn.ckeditor.com/4.20.2/standard/ckeditor.js"></script>
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.1/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script>
                                        function validateForm() {
                                            let isValid = true;

                                            // Validate Nha Tro selection
                                            const nhaTro = document.querySelector('select[name="nhaTro"]').value;
                                            const nhaTroError = document.getElementById("nhaTroError");
                                            if (nhaTro === "") {
                                                nhaTroError.textContent = "Please select a Nha Tro.";
                                                isValid = false;
                                            } else {
                                                nhaTroError.textContent = "";
                                            }

                                            // Validate Title (not empty)
                                            const title = document.getElementById("title").value.trim();
                                            const titleError = document.getElementById("titleError");
                                            if (title === "") {
                                                titleError.textContent = "Tiêu đề không được để trống.";
                                                isValid = false;
                                            } else {
                                                titleError.textContent = "";
                                            }

                                            // Validate Content from CKEditor (not empty)
                                            const content = CKEDITOR.instances.editor1.getData().trim();
                                            const contentError = document.getElementById("contentError");
                                            if (content === "") {
                                                contentError.textContent = "Nội dung không được để trống.";
                                                isValid = false;
                                            } else {
                                                contentError.textContent = "";
                                            }

                                            // Prevent form submission if validation fails
                                            return isValid;
                                        }

                                        // Initialize CKEditor with width and height settings
                                        CKEDITOR.replace('editor1', {
                                            width: '100%', // Set the width to 100% of its container
                                            height: '300px' // Set height as required
                                        });
        </script>

    <script>
            function toggleDropdown() {
                var dropdown = document.querySelector('.dropdown');
                dropdown.classList.toggle('active'); // Thêm/xóa class 'active' khi nhấn
            }
        </script>
    </body>
</html>