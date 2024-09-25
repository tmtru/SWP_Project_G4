<%-- 
    Document   : header
    Created on : Sep 23, 2024, 3:40:50 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.List, java.util.ArrayList,java.text.DecimalFormat, model.NhaTro, model.Phong, dal.NhaTroDAO, dal.PhongDAO"%>
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


    </head>
    <body>
        <!-- Header Start -->
        <div class="container-fluid bg-light px-0">
            <div class="row gx-0">
                <div class="col-lg-5 bg-light d-none d-lg-block">
                    <a href="index.html"
                       class="navbar-brand w-100 h-100 m-0 p-0 d-flex align-items-center justify-content-center">
                        <img src="assets/img/Logo_nhatro.png" alt="logo-nhatro" />
                    </a>
                </div>
                <div class="col-lg-7">

                    <nav class="navbar navbar-expand-lg bg-light navbar-light p-3 p-lg-0">
                        <a href="index.html" class="navbar-brand d-block d-lg-none m-0 p-0">
                            <img src="assets/img/Logo_nhatro.png" alt="logo-nhatro" />
                        </a>
                        <button type="button" class="navbar-toggler" data-bs-toggle="collapse"
                                data-bs-target="#navbarCollapse">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="collapse navbar-collapse justify-content-between" id="navbarCollapse">
                            <div class="navbar-nav mr-auto py-0">
                                <a href="homer" class="nav-item nav-link ${pageContext.request.servletPath == '/home.jsp' ? 'active' : ''}">Trang chủ</a>

                                <a href="category" class="nav-item nav-link ${pageContext.request.servletPath == '/category.jsp' || pageContext.request.servletPath == '/detailHouseHome.jsp' ? 'active' : ''}">Nhà trọ
                                </a>

                                <a href="about.html" class="nav-item nav-link">About</a>
                                <a href="contact.html" class="nav-item nav-link">Contact</a>
                            </div>
                            <div class="button">
                                <a href="register.jsp"
                                   class="btn my-3 signup-button mx-3">Sign up</a>
                                <a href="login.jsp"
                                   class="btn my-3 login-button mx-3">Login</a>
                            </div>

                        </div>

                    </nav>
                </div>
            </div>
        </div>
        <!-- Header End -->
        
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <script src="js/main.js"></script>

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
