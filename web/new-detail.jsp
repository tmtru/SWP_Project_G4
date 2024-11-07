<%-- 
    Document   : room
    Created on : Sep 22, 2024, 1:50:44 AM
    Author     : hihihihaha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<c:if test="${isLandLord == true}">
    <!DOCTYPE html>

    <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <!-- Bootstrap CSS -->
           <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
           
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
            <title>Chi tiết thông báo</title>
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
                                <a href="nhatro" class="">
                                    <i class='bx bxs-home icon active' ></i>
                                    <span class="text nav-text">Nhà trọ</span>
                                </a>
                            </li>
                            <li class="">
                                <a href="manage-new" class="active">
                                    <i class='bx bx-bell icon' ></i>
                                    <span class="text nav-text">Thông báo</span>
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
                <section class="property-management">
                    <h2>Quản lý thông báo</h2> 
                </section>
                <div class="container mt-4">
                    <h2 style="text-align: center">${n.title}</h2>
                    <div class="row" style="margin-bottom: 20px">
                        <span class="col-8"> Đăng bởi <strong>${n.creator.username}</strong></span>
                        <span class="col-4" style="float: right">Đăng lúc: <strong><fmt:formatDate value="${n.created_in}" pattern="dd/MM/yyyy HH:mm" /></strong></span>
                    </div>
                    <div style="background-color: whitesmoke">
                        <c:out value="${n.content}" escapeXml="false" />
                    </div>
                      <button style="color: wheat" onclick="window.location.href='manage-new'" class="btn btn-info btn-sm">Back</button>
             
                </div>
            </section>
            <script src="https://cdn.ckeditor.com/4.20.2/standard/ckeditor.js"></script>
            <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.1/umd/popper.min.js"></script>
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        </body>
    </html>
</c:if>
<c:if test="${isLandLord == false}">
    <!DOCTYPE html>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <script src="https://kit.fontawesome.com/aab0c35bef.js" crossorigin="anonymous"></script>
            <title>Chi tiết thông báo</title>

  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

            <!-- Font familiy-->
            <link rel="preconnect" href="https://fonts.googleapis.com">
            <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
            <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:ital,wght@0,200..800;1,200..800&display=swap"
                  rel="stylesheet">

            <!--Libarary animate-->
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css" />

            <!--AOS lib to reveal web when scroll-->
            <link rel="stylesheet" href="https://unpkg.com/aos@next/dist/aos.css" />

            <!-- Stylesheet -->
            <link href="css/styleHomeCatagory.css" rel="stylesheet">
            <link href="css/style.css" rel="stylesheet">
            <!--jquery-->
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>



        </head>
        <body>
            <jsp:include page="header.jsp" /> 
            <!-- Header End -->

            <!-- Room Start -->
            <div class="container">
                <nav aria-label="breadcrumb" class="m-4">
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="home.jsp">Trang chủ</a></li>
                        <li class="breadcrumb-item"><a href="new">Thông báo</a></li>
                        <li class="breadcrumb-item active" aria-current="page">${n.title}</li>
                    </ol>
                </nav>
            </div>


            <div class="container">

                <section class="search-tab my-1 row justify-content-center" id="filterstatus">
                    <h2 style="text-align: center">${n.title}</h2>
                    <div class="row" style="margin-bottom: 20px">
                        <span class="col-8"> Đăng bởi <strong>${n.creator.username}</strong></span>
                        <span class="col-4" style="float: right">Đăng lúc: <strong><fmt:formatDate value="${n.created_in}" pattern="dd/MM/yyyy HH:mm" /></strong></span>
                    </div>
                    <div style="background-color: whitesmoke">

                        <c:out value="${n.content}" escapeXml="false" />
                    </div>
                </section>
                    <button style="color: wheat" onclick="window.location.href='new'" class="btn btn-info btn-sm">Back</button>
                <!-- Footer Start -->
                <div class="container-fluid bg-light text-dark footer">
                    <div class="container pb-5">
                        <div class="row g-5">
                            <div class="col-md-6 col-lg-4">
                                <div class="rounded p-4">
                                    <a href="index.html">
                                        <img src="assets/img/Logo_nhatro.png" />
                                    </a>
                                </div>
                            </div>
                            <div class="col-md-6 col-lg-3">
                                <h6 class="section-title text-start text-primary text-uppercase mb-4">Contact</h6>
                                <p class="mb-2"><i class="fa fa-map-marker-alt me-3"></i>123 ABC, Hà Nội</p>
                                <p class="mb-2"><i class="fa fa-phone-alt me-3"></i>+012 345 67890</p>
                                <p class="mb-2"><i class="fa fa-envelope me-3"></i>info@example.com</p>
                                <div class="d-flex pt-2">
                                    <a class="btn btn-outline-light btn-social" href=""><i class="fab fa-twitter"></i></a>
                                    <a class="btn btn-outline-light btn-social" href=""><i class="fab fa-facebook-f"></i></a>
                                    <a class="btn btn-outline-light btn-social" href=""><i class="fab fa-youtube"></i></a>
                                    <a class="btn btn-outline-light btn-social" href=""><i class="fab fa-linkedin-in"></i></a>
                                </div>
                            </div>
                            <div class="col-lg-5 col-md-12">
                                <div class="row gy-5 g-4">
                                    <div class="col-md-6">
                                        <h6 class="section-title text-start text-primary text-uppercase mb-4">Company</h6>
                                        <a class="btn btn-link" href="">About Us</a>
                                        <a class="btn btn-link" href="">Contact Us</a>
                                        <a class="btn btn-link" href="">Privacy Policy</a>
                                        <a class="btn btn-link" href="">Terms & Condition</a>
                                        <a class="btn btn-link" href="">Support</a>
                                    </div>
                                    <div class="col-md-6">
                                        <h6 class="section-title text-start text-primary text-uppercase mb-4">Services</h6>
                                        <a class="btn btn-link" href="">???</a>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>


            <script src="https://kit.fontawesome.com/aab0c35bef.js" crossorigin="anonymous"></script>


            <!-- Footer End -->
            <script src="js/main.js"></script>
            <script src="js/filter.js"></script>
            <!-- JavaScript Libraries -->
            <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
            <!--font awasome-->
            <script src="https://kit.fontawesome.com/aab0c35bef.js" crossorigin="anonymous"></script>
            <!--AOS lib-->

        </body>
    </html>
</c:if>
