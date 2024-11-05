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
            .stats-container {
                display: flex;
                align-items: center;
                justify-content: center;
                gap: 2rem;
                padding: 2rem;
            }

            .chart-container {
                width: 60%;
                position: relative;
            }

            .legend-container {
                background: #f8f9fa;
                padding: 1.5rem;
                border-radius: 8px;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            }

            .legend-item {
                display: flex;
                align-items: center;
                margin-bottom: 1rem;
                font-size: 1.1rem;
            }

            .color-box {
                width: 20px;
                height: 20px;
                margin-right: 10px;
                border-radius: 4px;
            }

            .count {
                font-weight: bold;
                margin-left: 8px;
            }
            .card {
                position: relative;
                display: flex;
                flex-direction: column;
                min-width: 0;
                word-wrap: break-word;
                background-color: #fff;
                background-clip: border-box;
                border: 1px solid #e3e6f0;
                border-radius: 0.35rem;
                transition: transform 0.2s ease-in-out;
            }

            .card:hover {
                transform: translateY(-5px);
            }

            .text-xs {
                font-size: 1rem;
            }

            .text-gray-300 {
                color: #dddfeb!important;
            }

            .text-gray-800 {
                color: #5a5c69!important;
            }

            .shadow {
                box-shadow: 0 .15rem 1.75rem 0 rgba(58,59,69,.15)!important;
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
                                    <span class="text nav-text">Phòng trống</span>
                                </a>
                            </li>
                            <li>
                                <a href="statistic-revenue">
                                    <span class="text nav-text">Doanh thu</span>
                                </a>
                            </li>
                            <li>
                                <a href="khach-no">
                                    <span class="text nav-text">Khách nợ</span>
                                </a>
                            </li>
                            <li>
                                <a href="khach-coc">
                                    <span class="text nav-text">Khách đã cọc</span>
                                </a>
                            </li>
                            <li>
                                <a href="khach-sap-het-han">
                                    <span class="text nav-text">Khách sắp hết hạn hợp đồng</span>
                                </a>
                            </li>
                            <li>
                                <a href="thiet-bi">
                                    <span class="text nav-text">Thiết bị cần sửa</span>
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
                    <h2>Thống kê phòng</h2>

                </div>




                <nav>
                    <div class="warpper" style="padding-bottom: 30px">
                        <c:forEach var="nt" items="${sessionScope.housesByRole}">
                            <a href="statistic-room?idHouse=${nt.ID_NhaTro}">
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
            </section>

            <div class="container mt-4">
                <div class="row">
                    <!-- Total Rooms Card -->
                    <div class="col-xl-3 col-md-6 mb-4">
                        <div class="card border-left-primary shadow h-100 py-2" style="border-left: 4px solid #4e73df;">
                            <div class="card-body">
                                <div class="row no-gutters align-items-center">
                                    <div class="col mr-2">
                                        <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                            Tổng số phòng</div>
                                        <div class="h5 mb-0 font-weight-bold text-gray-800">${statisticRoom[0] + statisticRoom[1]}</div>
                                    </div>
                                    <div class="col-auto">
                                        <i class="fas fa-building fa-2x text-gray-300"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Empty Rooms Card -->
                    <div class="col-xl-3 col-md-6 mb-4">
                        <div class="card border-left-success shadow h-100 py-2" style="border-left: 4px solid #1cc88a;">
                            <div class="card-body">
                                <div class="row no-gutters align-items-center">
                                    <div class="col mr-2">
                                        <div class="text-xs font-weight-bold text-success text-uppercase mb-1">
                                            Phòng trống</div>
                                        <div class="h5 mb-0 font-weight-bold text-gray-800">${statisticRoom[0]}</div>
                                    </div>
                                    <div class="col-auto">
                                        <i class="fas fa-door-open fa-2x text-gray-300"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Occupied Rooms Card -->
                    <div class="col-xl-3 col-md-6 mb-4">
                        <div class="card border-left-info shadow h-100 py-2" style="border-left: 4px solid #36b9cc;">
                            <div class="card-body">
                                <div class="row no-gutters align-items-center">
                                    <div class="col mr-2">
                                        <div class="text-xs font-weight-bold text-info text-uppercase mb-1">
                                            Phòng đã thuê</div>
                                        <div class="h5 mb-0 font-weight-bold text-gray-800">${statisticRoom[1]}</div>
                                    </div>
                                    <div class="col-auto">
                                        <i class="fas fa-users fa-2x text-gray-300"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Occupancy Rate Card -->
                    <div class="col-xl-3 col-md-6 mb-4">
                        <div class="card border-left-warning shadow h-100 py-2" style="border-left: 4px solid #f6c23e;">
                            <div class="card-body">
                                <div class="row no-gutters align-items-center">
                                    <div class="col mr-2">
                                        <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">
                                            Tỷ lệ lấp đầy</div>
                                        <div class="h5 mb-0 font-weight-bold text-gray-800">
                                            <fmt:formatNumber 
                                                value="${(statisticRoom[1] / (statisticRoom[0] + statisticRoom[1])) * 100}" 
                                                maxFractionDigits="1"/>%
                                        </div>
                                    </div>
                                    <div class="col-auto">
                                        <i class="fas fa-chart-pie fa-2x text-gray-300"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <section class="ftco-section">


                </div>
                <div class="stats-container">
                    <div class="chart-container">
                        <canvas id="myChart"></canvas>
                    </div>
                    <div class="legend-container">
                        <h3 style="margin-bottom: 1rem;"></h3>
                        <div class="legend-item">
                            <div class="color-box" style="background: #bc6da6"></div>
                            <span>Phòng trống:</span>
                            <span class="count" id="empty-count"></span>
                        </div>
                        <div class="legend-item">
                            <div class="color-box" style="background: #55549b"></div>
                            <span>Đang thuê:</span>
                            <span class="count" id="occupied-count"></span>
                        </div>
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
        </script>


        <script
            src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js">
        </script>
        <script>
            const xValues = ["Trống", "Đang thuê"];
            const yValues = [];

            <c:forEach items="${statisticRoom}" var="r">
            yValues.push(`${r}`);
            </c:forEach>

            const barColors = ["#bc6da6", "#55549b"];

            // Update legend counts
            document.getElementById('empty-count').textContent = yValues[0] + " phòng";
            document.getElementById('occupied-count').textContent = yValues[1] + " phòng";

            new Chart("myChart", {
                type: "pie",
                data: {
                    labels: xValues,
                    datasets: [{
                            backgroundColor: barColors,
                            data: yValues
                        }]
                },
                options: {
                    title: {
                        display: true,
                        text: "Danh sách phòng còn trống và đang thuê",
                        fontSize: 16,
                        padding: 20
                    },
                    animation: {
                        animateScale: true,
                        animateRotate: true,
                        duration: 2000,
                        easing: 'easeInOutQuart'
                    },
                    legend: {
                        display: false
                    }
                }
            });

        </script>
    </body>
</html>