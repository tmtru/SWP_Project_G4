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
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet"/>
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&amp;display=swap" rel="stylesheet"/>
        <link href="${pageContext.request.contextPath}/css/css-account-management.css" rel="stylesheet" />
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
            .dropdown {
                position: relative; /* Để vị trí dropdown có thể được căn chỉnh */
            }

            .dropdown-menu {
                display: none; /* Ẩn menu theo mặc định */
                position: absolute; /* Để nó hiển thị bên dưới menu cha */
                background-color: white; /* Màu nền */
                border: 1px solid #ccc; /* Đường viền */
                padding: 10px; /* Khoảng cách bên trong */
                z-index: 1000; /* Để đảm bảo nó nằm trên các phần tử khác */
            }

            .dropdown:hover .dropdown-menu {
                display: block; /* Hiển thị menu khi di chuột vào mục cha */
            }



            .img-preview {
                width: 50px;
                border: 1px solid #ddd;
                border-radius: 5px;
                margin-right: 10px;
                margin-bottom: 10px;
            }
            /* Style for house selection tabs */
            .warpper {
                display: flex;
                justify-content: flex-start;
                gap: 10px;

                overflow-x: auto;
                -ms-overflow-style: none;
                scrollbar-width: none;
            }

            .warpper::-webkit-scrollbar {
                display: none;
            }

            .warpper a {
                text-decoration: none;
            }

            .warpper .tab {
                background: #9ca3af;
                color: white;
                padding: 12px 24px;
                border-radius: 8px;
                cursor: pointer;
                transition: all 0.3s ease;
                font-size: 16px;
                white-space: nowrap;
            }

            .warpper .tab:hover {
                background: #6b7280;
            }

            .warpper .tab.active {
                background: #4f46e5;
            }

            /* Style for filters section */
            .filters {
                display: flex;
                gap: 15px;
                padding: 20px;
                background: #f3f4f6;
                border-radius: 8px;
                flex-wrap: wrap;
            }

            .filters select {
                padding: 8px 16px;
                border: 1px solid #d1d5db;
                border-radius: 6px;
                background: white;
                min-width: 150px;
                font-size: 14px;
            }

            .filters .search-container {
                display: flex;
                align-items: center;
                margin-left: auto;
            }

            .filters .search-container input {
                padding: 8px 16px;
                border: 1px solid #d1d5db;
                border-radius: 6px 0 0 6px;
                width: 250px;
            }

            .filters .search-container button {
                padding: 8px 16px;
                background: #4f46e5;
                border: none;
                border-radius: 0 6px 6px 0;
                color: white;
                cursor: pointer;
            }

            .filters .search-container button:hover {
                background: #4338ca;
            }

            /* Responsive adjustments */
            @media (max-width: 768px) {
                .filters {
                    flex-direction: column;
                }

                .filters .search-container {
                    margin-left: 0;
                    width: 100%;
                }

                .filters select,
                .filters .search-container input {
                    width: 100%;
                }
            }


        </style>
    </head>
    <body>
        <!--Room manegement dash board-->

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
                        <a href="statistic-room" class="dropdown-toggle active">
                            <i class='bx bx-cog icon'></i>
                            <span class="text nav-text">Dashboard</span>
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="statistic-room">
                                    <span class="text nav-text">Room Statistic</span>
                                </a>
                            </li>
                            <li>
                                <a href="statistic-revenue">
                                    <span class="text nav-text">Revenue Statistic</span>
                                </a>
                            </li>
                            <li>
                                <a href="khach-no">
                                    <span class="text nav-text">Danh sách khách nợ</span>
                                </a>
                            </li>
                            <li>
                                <a href="statistic-revenue">
                                    <span class="text nav-text">Danh sách khách cọc</span>
                                </a>
                            </li>
                            <li>
                                <a href="khach-sap-het-han">
                                    <span class="text nav-text">Danh sách khách sắp hết hạn hợp đồng</span>
                                </a>
                            </li>
                        </ul>
                    </li>


                    <li class="">
                        <a href="nhatro">
                            <i class='bx bxs-home icon' ></i>
                            <span class="text nav-text">Nhà trọ</span>
                        </a>
                    </li>

                    <li class="">
                        <a href="room">
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
                <div class="header">
                    <h2>Danh sách khách cọc</h2>



                </div>




                <nav>
                    <div class="warpper" style="padding-bottom: 16px">
                        <c:forEach var="nt" items="${sessionScope.housesByRole}">
                            <a href="khach-coc?idHouse=${nt.ID_NhaTro}">
                                <div class="tab <c:if test="${nt.ID_NhaTro == sessionScope.currentHouse}">active</c:if>" id="tab-${nt.ID_NhaTro}">
                                    ${nt.tenNhaTro}
                                </div>
                            </a>
                        </c:forEach>
                        <c:if test="${sessionScope.housesByRole==null}">
                            <div>Bạn không được quyền truy cập vào bất cứ nhà trọ nào</div>
                        </c:if>
                    </div>
                </nav>
                <div class="filters" style="background-color: #f9f9f9;padding-left: 0px">

                    <form action="khach-coc" method="get" style="display: flex; align-items: center;">
                        <input name="searchTerm" placeholder="Search by name" type="text" value="${param.searchTerm}"/>
                        <button type="submit" class="btn btn-primary" style="margin-left: 6px;background-color: #4f46e5">
                            Search
                        </button>
                    </form>
                </div>
            </section>

            <section class="ftco-section">


                <div class="container">
                    <table class="account-table table">
                        <thead>
                            <tr>
                                <th>
                                    Tên tài khoản
                                </th>
                                <th>
                                    Email
                                </th>
                                <th>
                                    Phòng
                                </th>
                                <th>
                                    Giá
                                </th>
                                <th>
                                    Ngày giá trị
                                </th>
                                <th>
                                    Ngày hết hạn
                                </th>
                                <th>
                                    Tiền cọc
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="h" items="${hopdongs}">
                                <tr>
                                    <td>${h.khachThue.getUsername()}</td>
                                    <td>${h.khachThue.getEmail()}</td>
                                    <td>${h.phongTro.getTenPhongTro()}</td>
                                    <td>${h.phongTro.getGia()}</td>
                                    
                                    <td>${h.getNgay_gia_tri()}</td>
                                    <td>${h.getNgay_het_han()}</td>
                                    <td>${h.getTien_Coc()}</td>
                                    
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>



                </div>
                <div class="pagination">
                    <c:if test="${currentPage > 1}">
                        <button onclick="changePage(${currentPage - 1})">&laquo; Previous</button>
                    </c:if>

                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <c:choose>
                            <c:when test="${currentPage eq i}">
                                <button class="active">${i}</button>
                            </c:when>
                            <c:otherwise>
                                <button onclick="changePage(${i})">${i}</button>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:if test="${currentPage < totalPages}">
                        <button onclick="changePage(${currentPage + 1})">Next &raquo;</button>
                    </c:if>
                </div>

                <script>
                    function changePage(page) {
                        window.location.href = "khach-coc?searchTerm="+`${param.searchTerm}`+"&page=" + page;
                    }
                </script>
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
        </script>


        <script
            src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js">
        </script>

    </body>
</html>