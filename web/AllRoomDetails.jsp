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
    <div style="padding-top : 50px" class="container">
        <c:if test="${not empty room}">
            <div  class="room-card">
                <div class="row g-0">
                    <div style="padding : 20px" class="col-md-4">
                        <c:if test="${not empty room.images}">
                            <img src="${room.images[0]}" class="img-fluid" alt="Room Image">
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
                            <img src="assets/img/Logo_nhatro.png" alt="Logo">
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
                            <h6 class="section-title text-start text-primary text-uppercase mb-4">Services</h6>
                            <p><i class="fa fa-angle-right me-2"></i>Phòng ở</p>
                            <p><i class="fa fa-angle-right me-2"></i>Dịch vụ</p>
                            <p><i class="fa fa-angle-right me-2"></i>Giải trí</p>
                        </div>
                        <div class="col-md-6">
                            <h6 class="section-title text-start text-primary text-uppercase mb-4">Quick Links</h6>
                            <p><i class="fa fa-angle-right me-2"></i>Về chúng tôi</p>
                            <p><i class="fa fa-angle-right me-2"></i>Liên hệ</p>
                            <p><i class="fa fa-angle-right me-2"></i>Đăng nhập</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-VoPFZgklPIaFS+pThm6rrNRL0RXdOguE6B0YhUDm7JsV6ATkGPj57I36oUdXX9i0" crossorigin="anonymous"></script>
</body>
</html>