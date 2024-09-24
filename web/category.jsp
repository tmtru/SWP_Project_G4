<%-- 
    Document   : category
    Created on : Sep 23, 2024, 11:31:00 AM
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
        <link href="assets/css/style.css" rel="stylesheet">

    </head>
    <body>
        <!-- Header Start -->
        <jsp:include page="header.jsp" /> 
        <!-- Header End -->

        <!-- Room Start -->

        <section class="property-listings">
            <div class="listings-container">
                <header class="headline mb-2" data-aos="fade-down" data-aos-duration="1000">
                    <h2 class="headline-title">Nhà trọ hiện có</h2>
                    <p class="headline-subtitle">Khu trọ của chúng tôi gồm nhiều nhà trọ cho bạn tìm kiếm.</p>
                </header>


                <!--                <section class="search-tab"  >
                                    <div class="tab-control " data-aos="fade-right" data-aos-duration="1000">
                                        <div class="tab-items">
                                            <div class="tab-item tab-item-active" tabindex="0" role="button">
                                                <span class="">Tất cả</span>
                                            </div>
                                            <div class="tab-item" tabindex="0" role="button">
                                                <img src="https://cdn.builder.io/api/v1/image/assets/TEMP/5cd65ab41fc6d423287b682a0ee310b9039fefcd15089122b388aefab255b240?placeholderIfAbsent=true&apiKey=3ed7f71bf41b4da6a6357316a7fb8826"
                                                     alt="" class="tab-icon" />
                                                <span>Trống</span>
                                            </div>
                                            <div class="tab-item" tabindex="0" role="button">
                                                <img src="https://cdn.builder.io/api/v1/image/assets/TEMP/b68d8cb6353a474df137ed96c6b665b54872bd3f2dc6fb6018265eb6f8085ad1?placeholderIfAbsent=true&apiKey=3ed7f71bf41b4da6a6357316a7fb8826"
                                                     alt="" class="tab-icon" />
                                                <span>Sell</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="search-container " data-aos="fade-left" data-aos-duration="1000">
                                        <form class="search-form">
                                            <img src="https://cdn.builder.io/api/v1/image/assets/TEMP/d59257a87d0000cee73bb9136a5b9cdae2ec07143049522212b9cdd453a64aa5?placeholderIfAbsent=true&apiKey=3ed7f71bf41b4da6a6357316a7fb8826"
                                                 alt="" class="search-icon" />
                                            <label for="search-input" class="visually-hidden">Search</label>
                                            <input type="text" id="search-input" class="search-input" placeholder="Search..."
                                                   aria-label="Search" />
                                        </form>
                                    </div>
                                </section>-->
                <c:if test="${not empty sessionScope.nhatros}">
                    <div class="container-list d-flex f-row nhatros">
                        <c:forEach items="${sessionScope.nhatros}" var="nt">
                            <c:set var="nhatro" value="${nt.ID_NhaTro}" />

                            <!--Get all room of the current nhatro-->
                            <%  Integer idNhaTro = (Integer) pageContext.getAttribute("nhatro");
                            NhaTroDAO daont= new NhaTroDAO();
                            ArrayList<Phong> rooms=daont.getAllPhongTro(idNhaTro);
                            float allPrice=0;
                            for (int i=0; i<rooms.size();i++){
                            allPrice+=rooms.get(i).getGia();
                            }
                                float avePrice = allPrice / rooms.size();
    
                                // Làm tròn giá trị avePrice đến 2 chữ số thập phân
                                DecimalFormat df = new DecimalFormat("#.##");
                                String formattedAvePrice = df.format(avePrice / 1000000);

                                // Set các giá trị vào request để truyền sang phần hiển thị
                                request.setAttribute("avePrice", formattedAvePrice);
                                request.setAttribute("rooms", rooms);
                            %>
                            <a class="link-house" href="allrooms?id=${nt.ID_NhaTro}">
                                <div class="listings-content mb-2 d-flex" data-aos="fade-right" data-aos-duration="700">
                                    <article class="featured-listing">
                                        <div class="featured-card">
                                            <img src="${nt.URL_AnhNhaTro}"
                                                 alt="Featured property image" class="featured-image">
                                            <div class="featured-details">
                                                <div class="price-title-wrapper">
                                                    <div>
                                                        <div class="price-wrapper">
                                                            <span class="price">TB  ${avePrice}tr</span>
                                                            <span class="price-period">/tháng</span>
                                                        </div>
                                                        <h3 class="listing-title">${nt.tenNhaTro}</h3>
                                                    </div>
                                                    <img src="https://cdn.builder.io/api/v1/image/assets/TEMP/90ea229282d786c2880b0b9f62e1a929d080f8ab2a7374939c5c5341de6a27e6?placeholderIfAbsent=true&apiKey=3ed7f71bf41b4da6a6357316a7fb8826"
                                                         alt="Property icon" class="listing-icon">
                                                </div>
                                                <p class="listing-address">${nt.dia_chi}</p>
                                                <p class="listing-address">${nt.mo_ta}</p>
                                                <hr class="divider">
                                                <div class="facilities">
                                                    <div class="facility">
                                                        <img src="https://cdn.builder.io/api/v1/image/assets/TEMP/01d699666662d8bd83b20fc07d97a0db5f5e91bf78cb0ee3e5320f65dcced623?placeholderIfAbsent=true&apiKey=3ed7f71bf41b4da6a6357316a7fb8826"
                                                             alt="Bed icon" class="facility-icon">
                                                        <span class="facility-text">${rooms.size()} phòng</span>
                                                    </div>
                                                    <div class="facility">
                                                        <img src="https://cdn.builder.io/api/v1/image/assets/TEMP/9231b2dc8c867269a0642952d2e56b1bb47eae017ffe429a8a13c2342805d139?placeholderIfAbsent=true&apiKey=3ed7f71bf41b4da6a6357316a7fb8826"
                                                             alt="Bath icon" class="facility-icon">
                                                        <span class="facility-text">???</span>
                                                    </div>
                                                    <div class="facility">
                                                        <img src="https://cdn.builder.io/api/v1/image/assets/TEMP/b386f5e42bcb3e6ff44952904ea0ae16cda92a7ebfd22180c2860cf11135bb36?placeholderIfAbsent=true&apiKey=3ed7f71bf41b4da6a6357316a7fb8826"
                                                             alt="Dimension icon" class="facility-icon">
                                                        <span class="facility-text">5x7 m²</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </article>
                                </div>
                            </a>
                        </c:forEach>
                    </div>
                </c:if>

            </div>
        </section>



        <!-- Room End -->
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
        <!-- Footer End -->
        <script src="js/main.js"></script>

        <!-- JavaScript Libraries -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
        <!--font awasome-->
        <script src="https://kit.fontawesome.com/aab0c35bef.js" crossorigin="anonymous"></script>
        <!--AOS lib-->
        <script src="https://unpkg.com/aos@next/dist/aos.js"></script>
        <script>
            AOS.init();
        </script>
    </body>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</html>
