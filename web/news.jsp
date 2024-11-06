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
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="https://kit.fontawesome.com/aab0c35bef.js" crossorigin="anonymous"></script>
        <title>Chi tiết thông báo</title>



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


        <style> table {
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
            }</style>
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
            </section>
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
        <style>
            .pagination {
                display: flex;
                justify-content: center;
                align-items: center;
                margin-top: 20px;
            }

            .pagination a, .pagination span {
                margin: 0 5px;
                padding: 8px 12px;
                border-radius: 5px;
                font-size: 16px;
                text-decoration: none;
                color: #333;
                border: 1px solid #ddd;
                transition: background-color 0.3s, color 0.3s;
            }

            .pagination a:hover {
                background-color: #007bff;
                color: #fff;
                border-color: #007bff;
            }

            .pagination .current-page {
                background-color: #007bff;
                color: #fff;
                border-color: #007bff;
                font-weight: bold;
            }

            .pagination .prev-next {
                font-size: 18px;
                padding: 8px 12px;
                color: #333;
            }

            .pagination .prev-next:hover {
                background-color: #007bff;
                color: #fff;
                border-color: #007bff;
            }

        </style>

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
