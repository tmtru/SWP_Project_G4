<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.List"%>
<%@page import="model.Phong"%>
<%@page import="model.FeedBack"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="https://kit.fontawesome.com/aab0c35bef.js" crossorigin="anonymous"></script>
        <title>Room Details</title>

        <!-- Bootstrap CSS -->

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <!-- Google Font -->
        <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@200;800&display=swap" rel="stylesheet">

        <!-- Animate.css -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css" />

        <!-- AOS library -->
        <link rel="stylesheet" href="https://unpkg.com/aos@next/dist/aos.css" />
        <link href="css/styleHomeCatagory.css" rel="stylesheet">
        <link href="css/style.css" rel="stylesheet">
        <!-- Stylesheet -->

        <style>
            body {
                font-family: 'Plus Jakarta Sans', sans-serif;
                overflow-x: hidden;
                background-color: #f9f9f9;
                color: #444;
            }

            img {
                max-width: 100%;
                margin-bottom: 15px;
            }

            .container {
                margin-top: 20px;
            }

            .room-card {
                background: #ffffff;
                border: 1px solid #e0e0e0;
                border-radius: 0.5rem;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
                margin-bottom: 20px;
                transition: transform 0.2s, box-shadow 0.2s;
            }

            .room-card:hover {
                transform: scale(1.02);
                box-shadow: 0 8px 20px rgba(0, 0, 0, 0.2);
            }

            .card-body {
                padding: 20px;
            }

            .card-tit {
                font-size: 1.75rem;
                font-weight: bold;
                color: #343a40; /* Set to black */
                text-shadow: 1px 1px 3px rgba(0, 0, 0, 0.2);
            }

            .card-text {
                font-size: 1.1rem;
                color: #666;
            }


            .room-info {
                padding-left: 10px; /* Spacing between image and text */
            }

            /* Feedback Styles */
            h3 {
                margin-top: 30px;
                font-weight: bold;
                color: #007bff;
            }

            ul {
                list-style: none;
                padding-left: 0;
            }

            li {
                margin-bottom: 10px;
                font-size: 1.1rem;
                color: #444;
            }

            .star {
                color: gold;
                font-size: 1.5em;
                margin-right: 2px;
            }

            /* Footer Styles */
            .footer {
                background-color: #343a40;
                color: white;
                padding: 20px 0;
            }

            .footer a {
                color: #ffc107;
            }

            .footer a:hover {
                text-decoration: underline;
            }

            /* Media Queries */
            @media screen and (max-width: 768px) {
                .room-card {
                    margin-bottom: 15px;
                }
                .card-title {
                    font-size: 1.5rem;
                }
                .card-text {
                    font-size: 1rem;
                }
            }
        </style>
        <link href="css/style.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="header.jsp" /> 
                <div class="container">
            <nav aria-label="breadcrumb" class="m-3 mb-0">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="home.jsp">Trang chủ</a></li>
                    <li class="breadcrumb-item"><a href="category">Nhà trọ</a></li>
                    <li class="breadcrumb-item"><a href="allrooms?id=${currenthouse.ID_NhaTro}">${currenthouse.tenNhaTro}</a></li>
                    <li class="breadcrumb-item active" aria-current="page">Phòng ${room.tenPhongTro}</li>
                </ol>
            </nav>
        </div>
        <div style="padding-top : 20px" class="container">
            
            <c:if test="${not empty room}">
                <div  class="room-card">
                    <div class="row g-0">
                        <div style="padding : 20px" class="col-md-4">

                            <c:if test="${not empty room.images}">

                                <div id="anhnhatro" class="carousel slide">
                                    <div style="position: relative">
                                        <div class="carousel-inner img-above">
                                            <c:forEach var="anh" items="${room.images}" varStatus="status">
                                                <div class="carousel-item ${status.first ? 'active' : ''}">
                                                    <img src="${anh}" class="d-block w-100" alt="Slide ${status.index + 1}">
                                                </div>
                                            </c:forEach>
                                        </div>
                                        <button class="carousel-control-prev" type="button" data-bs-target="#anhnhatro" data-bs-slide="prev">
                                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                            <span class="visually-hidden">Previous</span>
                                        </button>
                                        <button class="carousel-control-next" type="button" data-bs-target="#anhnhatro" data-bs-slide="next">
                                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                            <span class="visually-hidden">Next</span>
                                        </button>
                                    </div>
                                    <div class="carousel-indicators img-under">
                                        <c:forEach var="anhUnder" items="${room.images}" varStatus="status">
                                            <img src="${anhUnder}"
                                                 type="button" data-bs-target="#anhnhatro" 
                                                 data-bs-slide-to="${status.index}" 
                                                 class="${status.first ? 'active' : ''}" 
                                                 aria-current="${status.first ? 'true' : 'false'}" 
                                                 aria-label="Slide ${status.index + 1}"/>
                                        </c:forEach>
                                    </div>
                                </div>
                                <div class="modal fade" id="imageModal" tabindex="-1" aria-labelledby="imageModalLabel" aria-hidden="true">
                                    <div class="modal-dialog modal-dialog-centered modal-lg">
                                        <div class="modal-content">
                                            <div class="modal-body">
                                                <img id="modalImage" src="" class="img-fluid" alt="Enlarged View">
                                            </div>
                                            <button type="button" class="btn-close position-absolute top-0 end-0 me-2 mt-2" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                    </div>
                                </div>

                            </c:if>
                            <c:if test="${empty room.images}">
                                <p>Không có ảnh cho nhà trọ này.</p>
                            </c:if>
                        </div>
                        <div style="padding: 20px" class="col-md-8 room-info">
                            <div class="card-body">
                                <h5 class="card-tit">${room.tenPhongTro}</h5>
                                <p class="card-text"><strong>Giá :</strong> ${room.gia} VND</p>
                                <p class="card-text"><strong>Diện tích :</strong> ${room.dien_tich} m²</p>
                                <p class="card-text"><strong>Địa chỉ :</strong> ${room.diaChiPhongTro}</p>
                                <p class="card-text"><strong>Thiết bị :</strong> ${room.motathietbi}</p>
                            </div>

                        </div>
                    </div>
                </div>

                <h3>Feedback</h3>
                <c:if test="${not empty feedbackList}">
                    <ul>
                        <c:forEach var="feedback" items="${feedbackList}">
                            <li>
                                ${feedback.ten_khach} - ${feedback.noi_dung} - 
                                <c:forEach begin="1" end="${feedback.danh_gia}">
                                    <i class="fa fa-star star"></i>
                                </c:forEach>
                                <c:forEach begin="${feedback.danh_gia + 1}" end="5">
                                    <i class="fa fa-star star" style="color: lightgray;"></i>
                                </c:forEach>
                            </li>
                        </c:forEach>
                    </ul>
                </c:if>
                <c:if test="${empty feedbackList}">
                    <p>No feedback available for this room.</p>
                </c:if>
            </c:if>
        </div>

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
                                <h6 class="section-title text-start text-primary text-uppercase mb-4">Nhà trọ</h6>
                                <a class="btn btn-link" href="thongtinlienhe">Liên hệ</a>
                                <a class="btn btn-link" href="category">Danh sách nhà trọ</a>
                                <a class="btn btn-link" href="">Privacy Policy</a>
                            </div>
                            <div class="col-md-6">


                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>

        <!-- Bootstrap JS -->
 <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    </body>
</html>
