<%-- 
    Document   : header
    Created on : Jun 15, 2024, 4:05:55 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.List, java.util.ArrayList,java.text.DecimalFormat, model.NhaTro, model.Phong, dal.NhaTroDAO, dal.PhongDAO, model.AnhNhaTro, dal.AnhNhaTroDAO"%>
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
        <jsp:include page="header.jsp" /> 
        <!-- Header End -->
        <!-- Carousel Start -->
        
        <div class="p-0 mb-5">
            <div id="header-carousel" class="carousel slide" data-bs-ride="carousel">
                <div class="carousel-inner">
                    <div class="carousel-item active">
                        <img class="img-banner" src="assets/img/decor-phong-ngu-9.jpg" alt="">
                        <div class="carousel-caption d-flex align-items-center">
                            <div class="ps-5" style="max-width: 900px;">
                                <div class="text">
                                    <h4 class="display-3 mb-4 animate__animated animate__slideInDown">Tìm phòng trọ trong mơ
                                        của bạn</h4>
                                    <p class="display-3 mb-4 animate__animated animate__slideInDown"
                                       style="max-width: 500px;">Khu trọ của chúng tôi TQAT phân bố ở Hà Nội,
                                        nơi bạn có thể tìm phòng trọ phù hợp với nhu cầu của bạn.</p>
                                </div>


                            </div>
                        </div>
                    </div>
                </div>

                <button class="carousel-control-prev" type="button" data-bs-target="#header-carousel" data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#header-carousel" data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>
        </div>
        <!-- Carousel End -->
        <!-- fast Searching room Start -->
        <div class="container fast-searching pb-5" data-aos="fade-down">

            <div class="container input">
                <div class="tag">
                    <div class="bg-white text-dark">
                        Renting
                    </div>
                </div>
                <div class="bg-white shadow" style="padding: 25px;">
                    <div class="row g-2">
                        <div class="col-md-12">
                            <div class="row g-4">
                                <div class="col-md-5">

                                    <div class="form-input" data-target-input="nearest">
                                        <label for="location" class="label mb-1">Vị trí</label>
                                        <input id="location" type="text" class="form-control"
                                               placeholder="Địa điểm bạn muốn tìm..." />
                                    </div>
                                </div>
                                <div class="col-md-5">

                                    <div class="form-input" data-target-input="nearest">
                                        <label for="date" class="label mb-1">Thời gian</label>
                                        <input id="date" type="date" class="form-control" placeholder="Check out" />
                                    </div>
                                </div>
                                <div class="col-md-2 fast-search-btn d-flex justify-content-center align-items-center">
                                    <button class="btn">Tìm phòng</button>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
        <!-- Searching room End -->
        <!-- About Start -->

        <div class="container-xxl about-start my-5">
            <div class="container-fluid">
                <div class="row g-5 align-items-center">
                    <div class="col-lg-7 justify-content-center align-items-center">
                        <h6 class="section-title text-start hightlight text-uppercase" data-aos="fade-down">About Us</h6>
                        <h1 class="mb-4" data-aos="fade-right">Chào mừng tới <span class="hightlight text-uppercase">Khu
                                trọ TQAT</span></h1>
                        <div class="row g-3 pb-4" data-aos="fade-up" data-aos-duration="1000">
                            <div class="col-sm-6">
                                <div class="p-4">
                                    <i class="fa fa-hotel fa-2x hightlight mb-2"></i>
                                    <h2 class="mb-1" data-toggle="counter-up">Giá cả hợp lí</h2>
                                    <p class="mb-0">Not sure what you should be charging for your property?
                                        No need to worry, let us do the numbers for you.</p>
                                </div>
                            </div>
                            <div class="col-sm-6">
                                <div class="p-4">
                                    <i class="fa fa-users-cog fa-2x hightlight mb-2"></i>
                                    <h2 class="mb-1" data-toggle="counter-up">Giá cả hợp lí</h2>
                                    <p class="mb-0">Not sure what you should be charging for your property? No need to
                                        worry,
                                        let us do the numbers for you.</p>
                                </div>
                            </div>
                        </div>
                        <div class="row g-3 pb-4" data-aos="fade-up" data-aos-duration="1000">
                            <div class="col-sm-6">
                                <div class="p-4">
                                    <i class="fa fa-hotel fa-2x hightlight mb-2"></i>
                                    <h2 class="mb-1" data-toggle="counter-up">1234</h2>
                                    <p class="mb-0">Not sure what you should be charging for your property? No need to
                                        worry,
                                        let us do the numbers for you.</p>
                                </div>
                            </div>
                            <div class="col-sm-6">
                                <div class="p-4">
                                    <i class="fa fa-users-cog fa-2x hightlight mb-2"></i>
                                    <h2 class="mb-1" data-toggle="counter-up">1234</h2>
                                    <p class="mb-0">Not sure what you should be charging for your property? No need to
                                        worry,
                                        let us do the numbers for you.</p>
                                </div>
                            </div>
                        </div>

                        <a class="btn py-3 px-5 mt-2" href="">Explore More</a>
                    </div>
                    <div class="col-lg-5">
                        <div class="about-img d-flex">
                            <img class="" data-aos="zoom-in" src="assets/img/nhatro.png" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- About End -->


        <!-- Room Start -->



        <section class="property-listings container">
            <div class="listings-container">
                <header class="headline mb-2" data-aos="fade-down" data-aos-duration="1000">
                    <h2 class="headline-title">Nhà trọ hiện có</h2>
                    <p class="headline-subtitle">Khu trọ của chúng tôi gồm nhiều nhà trọ cho bạn tìm kiếm.</p>
                </header>
                <div class="d-flex justify-content-center">
                    <a class="btn py-3 px-5 mt-2 explore" href="category" data-aos="fade-down" data-aos-duration="700">Explore More</a>
                </div>



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
                    <c:forEach items="${sessionScope.nhatros}" var="nt">
                        <c:set var="nhatro" value="${nt.ID_NhaTro}" />

                        <!--Get all room of the current nhatro-->
                        <%  Integer idNhaTro = (Integer) pageContext.getAttribute("nhatro");
                        NhaTroDAO daont= new NhaTroDAO();
                        ArrayList<Phong> rooms=daont.getAllPhongTro(idNhaTro);
                        AnhNhaTroDAO dao = new AnhNhaTroDAO();
                        // Gọi phương thức để lấy danh sách ảnh
                        ArrayList<AnhNhaTro> anhNhaTroList = dao.getAllAnhByNhaTroId(idNhaTro);
                        
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
                        request.setAttribute("imgNhaTro", anhNhaTroList);
                        request.setAttribute("rooms",rooms);
                        %>

                        <div class="listings-content mb-2" data-aos="fade-right" data-aos-duration="700">
                            <article class="featured-listing">
                                <div class="featured-card">
                                    <c:if test="${not empty imgNhaTro}">
                                        <c:forEach var="anh" items="${imgNhaTro}" varStatus="status">
                                            <c:if test="${status.index == 0}">
                                                <img src="${anh.URL_AnhNhaTro.get(0)}" alt="Ảnh nhà trọ" />
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${empty imgNhaTro}">
                                        <p>Không có ảnh cho nhà trọ này.</p>
                                    </c:if>
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
                                        <p class="desciption">${nt.mo_ta}</p>
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

                            <section class="listings-list" >
                                <h3 class="listings-list-title">Danh sách phòng</h3>
                                <div class="listings-grid mini-slide">
                                    <button class="handle left-handle">
                                        <div class="text">&#8249;</div>
                                    </button>
                                    <div class="slider">
                                        <c:forEach items="${rooms}" var="rs">
                                            <article class="listing-card item" >
                                                <img src="https://cdn.builder.io/api/v1/image/assets/TEMP/807191b1c5ec9a365e8d960bde1533408c378d956c435939b9c17e77e1cb689b?placeholderIfAbsent=true&apiKey=3ed7f71bf41b4da6a6357316a7fb8826"
                                                     alt="Room image" class="listing-image">
                                                <div class="listing-info">
                                                    <div class="listing-details">
                                                        <div class="listing-price">
                                                            <span class="price">${rs.gia/1000000} tr</span>
                                                            <span class="price-period">/tháng</span>
                                                        </div>
                                                        <h4 class="listing-name">Phòng ${rs.tenPhongTro}</h4>
                                                        <c:if test="${rs.trang_thai.equals('T')}">
                                                            <span class="d-inline-block" tabindex="0" data-bs-toggle="popover" data-bs-trigger="hover focus" data-bs-content="Phòng trống" style="color: red">
                                                                <i class="fa-solid fa-circle-exclamation" style="color: red" disabled></i> Trống
                                                            </span>
                                                        </c:if>
                                                        <c:if test="${rs.trang_thai.equals('D')}">
                                                            <span class="d-inline-block" tabindex="0" data-bs-toggle="popover" data-bs-trigger="hover focus" data-bs-content="Phòng trống" style="color: #009933">
                                                                <i class="fa-solid fa-circle-exclamation" style="color: #009933" disabled></i> Đã thuê
                                                            </span>
                                                        </c:if>
                                                        <p class="listing-description">${rs.mo_ta}</p>
                                                    </div>
                                                    <div class="details-button">
                                                        <img src="https://cdn.builder.io/api/v1/image/assets/TEMP/dca00f6f57a639b42ff911d49af82395b3e490bbba3e80779f4f7169c56fc15a?placeholderIfAbsent=true&apiKey=3ed7f71bf41b4da6a6357316a7fb8826"
                                                             alt="Details icon" class="details-icon">
                                                        <span class="details-text">Chi tiết</span>
                                                        <img src="https://cdn.builder.io/api/v1/image/assets/TEMP/38af489b0b824b4e9a4ca4f97538a5a535b0617bf3be7a86b92c79ae52dfe2c5?placeholderIfAbsent=true&apiKey=3ed7f71bf41b4da6a6357316a7fb8826"
                                                             alt="Underline" class="details-underline">
                                                    </div>
                                                </div>
                                                <hr class="divider">
                                                <div class="facilities">
                                                    <div class="facility">
                                                        <img src="https://cdn.builder.io/api/v1/image/assets/TEMP/91548f776f5f1c44de4afa0b594a698a690aa51b3155ce2280947cf745e5ca4d?placeholderIfAbsent=true&apiKey=3ed7f71bf41b4da6a6357316a7fb8826"
                                                             alt="Bed icon" class="facility-icon">
                                                        <span class="facility-text">Bed</span>
                                                    </div>
                                                    <div class="facility">
                                                        <img src="https://cdn.builder.io/api/v1/image/assets/TEMP/85dbe2fd8f4bec9ea5fd31b994100d3d26b701697786321befe16c9ae5f4a5ec?placeholderIfAbsent=true&apiKey=3ed7f71bf41b4da6a6357316a7fb8826"
                                                             alt="Bath icon" class="facility-icon">
                                                        <span class="facility-text">Bathroom</span>
                                                    </div>
                                                    <div class="facility">
                                                        <img src="https://cdn.builder.io/api/v1/image/assets/TEMP/18c032977b463fe970eb067104d0c95807cbf310ae9c273e9de01b3d7e27099a?placeholderIfAbsent=true&apiKey=3ed7f71bf41b4da6a6357316a7fb8826"
                                                             alt="Dimension icon" class="facility-icon">
                                                        <span class="facility-text">${rs.dien_tich} m²</span>
                                                    </div>
                                                </div>
                                            </article>
                                        </c:forEach>

                                    </div>

                                    <button class="handle right-handle">
                                        <div class="text">&#8250;</div>
                                    </button>
                                </div>
                            </section>
                        </div>
                    </c:forEach>
                </c:if>

            </div>
        </section>



        <!-- Room End -->
        <!--Map-->


        <section class="contact-section">
            <div class="contact-container container" data-aos="fade-down" data-aos-duration="700">
                <div class="contact-content">
                    <div class="contact-info">
                        <div class="contact-details">
                            <h2 class="contact-headline">Hãy liên hệ với chúng tôi để biết thêm chi tiết</h2>
                            <div class="contact-features">
                                <div class="contact-card">
                                    <div class="card-header">
                                        <div class="card-content">
                                            <div class="icon-wrapper">
                                                <div class="icon" aria-hidden="true"></div>
                                            </div>
                                            <h3 class="card-title">Thông tin liên hệ nhà trọ</h3>
                                        </div>
                                        <div class="dropdown">
                                            <div class="dropdown-header">
                                                <span class="dropdown-label">Nhà trọ TMT</span>
                                                <img loading="lazy"
                                                     src="https://cdn.builder.io/api/v1/image/assets/TEMP/0643ef6a3e60a121631d6b7c6aade8e9196110b12a218cb14d9aee3e52d0bc48?placeholderIfAbsent=true&apiKey=3ed7f71bf41b4da6a6357316a7fb8826"
                                                     class="dropdown-icon" alt="" aria-hidden="true">
                                            </div>
                                            <div class="dropdown-items"></div>
                                        </div>
                                    </div>
                                    <p class="contact-address">
                                        <strong>Địa chỉ</strong>: Thôn 4, Thach Hòa, Thạch Thất<br>
                                        <strong>Số điện thoại</strong>: 02315611542
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="stats-container">
                            <div class="stat-item">
                                <span class="stat-value">7.4%</span>
                                <span class="stat-label">Property Return Rate</span>
                            </div>
                            <div class="stat-divider" aria-hidden="true"></div>
                            <div class="stat-item">
                                <span class="stat-value">3,856</span>
                                <span class="stat-label">Property in Sell & Rent</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="image-column">
                    <img loading="lazy"
                         src="https://cdn.builder.io/api/v1/image/assets/TEMP/d3c84be9490fee00cd3c4cf5426e3474c8735a9e869f0b5a756b98bb3d589a88?placeholderIfAbsent=true&apiKey=3ed7f71bf41b4da6a6357316a7fb8826"
                         class="contact-image" alt="Illustration of property or contact information">
                </div>
            </div>
        </section>
        <!--Map end-->
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
