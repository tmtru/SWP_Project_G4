<%-- 
    Document   : header
    Created on : Sep 23, 2024, 3:40:50 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import="jakarta.servlet.http.Cookie,java.util.List, java.util.ArrayList,java.text.DecimalFormat, model.NhaTro,model.Account, model.Phong, dal.NhaTroDAO, dal.PhongDAO"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="https://kit.fontawesome.com/aab0c35bef.js" crossorigin="anonymous"></script>
        <title>Document</title>



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
        <link href="css/style.css" rel="stylesheet">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script>
            $(document).ready(function () {
                // Hàm để lấy giá trị của cookie theo tên
                function getCookie(name) {
                    let cookieArr = document.cookie.split(";");
                    for (let i = 0; i < cookieArr.length; i++) {
                        let cookiePair = cookieArr[i].trim();
                        // Kiểm tra cookie có tên yêu cầu
                        if (cookiePair.startsWith(name + "=")) {
                            return cookiePair.split("=")[1]; // Trả về giá trị của cookie
                        }
                    }
                    return null; // Nếu không tìm thấy cookie
                }

                // Lấy giá trị cookie likedRooms theo userId
                let userId = '${sessionScope.account.getID_Account()}'; // Giả sử bạn đã lưu userId trong session
                let likedRoomsCookie = getCookie("likedRooms_" + userId);

                if (likedRoomsCookie) {
                    let likedRoomsArray = likedRoomsCookie.split(","); // Chia tách giá trị cookie thành mảng

                    // Gửi danh sách ID phòng về server
                    $.ajax({
                        type: "POST",
                        url: "likedRooms.jsp", // Thay đổi thành URL của servlet hoặc JSP xử lý
                        data: {likedRooms: likedRoomsArray.join(",")}, // Chuyển đổi mảng thành chuỗi
                        success: function (response) {
                            $('#likedRoomsContainer').html(response); // Cập nhật nội dung từ response
                        },
                        error: function () {
                            $('#likedRoomsContainer').html('<div>Có lỗi xảy ra.</div>');
                        }
                    });
                } else {
                    $('#likedRoomsContainer').html('<div>Không có phòng nào đã thích.</div>');
                }
            });
        </script>


    </head>
    <body>
        <!-- Header Start -->
        <c:set var="a" value="${sessionScope.account}"></c:set>
            <div class="container-fluid bg-light px-0">
                <div class="row gx-0">
                    <div class="col-lg-5 bg-light d-none d-lg-block">
                        <a href="home.jsp"
                           class="navbar-brand w-100 h-100 m-0 p-0 d-flex align-items-center justify-content-center">
                            <img src="assets/img/Logo_nhatro.png" alt="logo-nhatro" />
                        </a>
                    </div>
                    <div class="col-lg-7">

                        <nav class="navbar navbar-expand-lg bg-light navbar-light p-3 p-lg-0">
                            <a href="home.jsp" class="navbar-brand d-block d-lg-none m-0 p-0">
                                <img src="assets/img/Logo_nhatro.png" alt="logo-nhatro" />
                            </a>
                            <button type="button" class="navbar-toggler" data-bs-toggle="collapse"
                                    data-bs-target="#navbarCollapse">
                                <span class="navbar-toggler-icon"></span>
                            </button>
                            <div class="collapse navbar-collapse justify-content-between" id="navbarCollapse">
                                <div class="navbar-nav mr-auto py-0">
                                    <a href="home.jsp" class="nav-item nav-link ${pageContext.request.servletPath == '/home.jsp' ? 'active' : ''}">Trang chủ</a>

                                <a href="category" class="nav-item nav-link ${pageContext.request.servletPath == '/category.jsp' || pageContext.request.servletPath == '/detailHouseHome.jsp' ? 'active' : ''}">Nhà trọ
                                </a>

                                <a href="thongtinlienhe" class="nav-item nav-link ${pageContext.request.servletPath == '/contactUs_map.jsp' ? 'active' : ''}">Liên hệ</a>
                                <c:if test="${a!=null}">
                                    <c:if test="${a.role.equals('landlord') || a.role.equals('manager')}">

                                        <a href="loaddashboardbyrole" class="nav-item nav-link" style="color: red">Trang quản lí</a>
                                    </c:if>

                                </c:if>
                            </div>







                            <c:if test="${a==null}">

                                <div class="button">
                                    <a href="register.jsp"
                                       class="btn my-3 signup-button mx-3">Đăng kí</a>
                                    <a href="login.jsp"
                                       class="btn my-3 login-button mx-3">Đăng nhập</a>
                                </div>
                            </c:if>


                            <c:if test="${a!=null}">
                                <ul class="navbar-nav ms-auto mb-2 mb-lg-0 profile-menu"> 
                                    <li class="nav-item nav-icon">
                                        <a href="#" class="search-toggle" data-toggle="search-toggle">
                                            <i class="fa-solid fa-heart-circle-exclamation" style="font-size: 25px; color: #cc33ff"></i>
                                            <span class="bg-danger dots"></span>
                                        </a>
                                        <div class="iq-sub-dropdown iq-notify-dropdown" style="
    z-index: 900;">
                                            <div class="iq-card shadow-none m-0">
                                                <div class="iq-card-body">
                                                    <div id="likedRoomsContainer"> <!-- Container for liked rooms will be populated here -->
                                                        
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                    </li>
                                    <li class="nav-item dropdown">
                                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                            <div class="profile-pic">
                                                <img src="assets/img/user.png" alt="Profile Picture" class="img-fuild">
                                            </div>
                                            <!-- You can also use icon as follows: -->
                                            <!--  <i class="fas fa-user"></i> -->
                                        </a>
                                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                            <li><a class="dropdown-item" href="profile.jsp"><i class="fas fa-sliders-h fa-fw"></i> Tài khoản</a></li>
                                            <li><a class="dropdown-item" href="#"><i class="fas fa-cog fa-fw"></i> Cài đặt</a></li>
                                            <li><hr class="dropdown-divider"></li>
                                            <li><a class="dropdown-item" href="logout"><i class="fas fa-sign-out-alt fa-fw"></i> Đăng xuất</a></li>
                                        </ul>
                                    </li>
                                </ul>

                            </c:if>

                        </div>

                    </nav>
                </div>
            </div>
        </div>
        <!-- Header End -->

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>




        <!-- JavaScript Libraries -->
        <!--font awasome-->
        <script src="https://kit.fontawesome.com/aab0c35bef.js" crossorigin="anonymous"></script>
        <!--AOS lib-->
        <script src="https://unpkg.com/aos@next/dist/aos.js"></script>
        <script>
            AOS.init();
        </script>
    </body>

</html>
