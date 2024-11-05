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
            .main-content .filters {
                display: flex;
                align-items: center;
                margin-bottom: 20px;
            }
            .main-content .filters select,
            .main-content .filters button,
            .main-content .filters input {
                margin-right: 10px;
                padding: 10px;
                border: 1px solid #e0e0e0;
                border-radius: 5px;
                background-color: #fff;
                font-size: 14px;
                color: #3b3b3b;
            }
        </style>
        <title>Lịch sử hành động</title>
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
                    <h2>Lịch sử hành động- ${nhatro.getTenNhaTro()}</h2> 
                </section>
                <div class="container mt-4">

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

                    <div class="action-buttons">

                        <form action="manager-action" method="get" class="filters">
                            <input type="text" name="search" value="${param.search}" placeholder="Search..">
                            <input  type="hidden" name="page" value="${param.page}" />
                            <input type="hidden" name="id" value="${param.id}" />
                            <button type="submit"><i class='bx bx-search icon'></i></button>
                        </form>
                    </div>
                    <table>
                        <thead>
                            <tr>
                                <th>
                                    Quản lý
                                </th>
                                <th>
                                    Nhà Trọ
                                </th>
                                <th>
                                    Tiêu đề
                                </th>
                                <th>
                                    Nội dung
                                </th>
                                <th>
                                    Thời gian
                                </th>



                            </tr>
                        </thead>
                        <h3 style="margin-top: 20px; text-align: center">Danh sách hành động</h3>
                        <tbody>
                            <c:forEach var="l" items="${list}">
                                <tr>
                                    <td>${l.quanLy.getName()}</td>
                                    <td>${l.nhaTro.getTenNhaTro()}</td>
                                    <td>${l.title}</td>
                                    <td>${l.content}</td>
                                    <td><fmt:formatDate value="${l.createdDate}" pattern="dd/MM/yyyy hh:mm:ss"/></td>
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

                    <button class="btn btn-info" onclick="window.location.href = 'quanly-account-nha-tro?id=${id}'">Back</button>

                </div>
            </section>
        </c:if>

        <c:if test="${isOwner == false}">
            <section class="home">
                <section class="property-management">
                    <h2>Lích sử hành động- ${nhatro.getTenNhaTro()}</h2> 
                </section>
                <div class="container mt-4">

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

                    <div class="action-buttons">

                        <form action="manager-action" method="get" class="filters">
                            <input type="text" name="search" value="${param.search}" placeholder="Search..">
                            <input  type="hidden" name="page" value="${param.page}" />
                            <input type="hidden" name="id" value="${param.id}" />
                            <button type="submit"><i class='bx bx-search icon'></i></button>
                        </form>
                    </div>
                    <table>
                        <thead>
                            <tr>
                                <th>
                                    Quản lý
                                </th>
                                <th>
                                    Nhà Trọ
                                </th>
                                <th>
                                    Tiêu đề
                                </th>
                                <th>
                                    Nội dung
                                </th>
                                <th>
                                    Thời gian
                                </th>



                            </tr>
                        </thead>
                        <h3 style="margin-top: 20px; text-align: center">Danh sách hành động</h3>
                        <tbody>
                            <c:forEach var="l" items="${list}">
                                <tr>
                                    <td>${l.quanLy.getName()}</td>
                                    <td>${l.nhaTro.getTenNhaTro()}</td>
                                    <td>${l.title}</td>
                                    <td>${l.content}</td>
                                    <td><fmt:formatDate value="${l.createdDate}" pattern="dd/MM/yyyy hh:mm:ss"/></td>
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

                    <button class="btn btn-info" onclick="window.location.href = 'nhatro'">Back</button>

                </div>
            </section>
        </c:if>
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.1/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>



    </body>
</html>