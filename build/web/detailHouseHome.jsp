<%-- 
    Document   : detailHouseHome
    Created on : Sep 23, 2024, 4:51:51 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@page import="java.util.List, java.util.ArrayList,java.text.DecimalFormat, model.NhaTro, model.Phong, dal.NhaTroDAO, dal.PhongDAO"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="https://kit.fontawesome.com/aab0c35bef.js" crossorigin="anonymous"></script>
        <title>Document</title>



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
        <!-- Header Start -->
        <jsp:include page="header.jsp" /> 
        <!-- Header End -->

        <!-- Room Start -->
        <div class="container">
            <nav aria-label="breadcrumb" class="m-4">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="home.jsp">Trang chủ</a></li>
                    <li class="breadcrumb-item"><a href="category">Nhà trọ</a></li>
                    <li class="breadcrumb-item active" aria-current="page">${currenthouse.tenNhaTro}</li>
                </ol>
            </nav>
        </div>

        <section class="rental-property">

            <div class="image-column">

                <c:if test="${not empty imgNhaTro}">
                    <c:forEach var="anh" items="${imgNhaTro}" varStatus="status">
                        <c:if test="${status.index == 0}">
                            <img src="${anh.URL_AnhNhaTro.get(0)}" alt="Ảnh nhà trọ" class="property-image"/>
                        </c:if>
                    </c:forEach>
                </c:if>
                <c:if test="${empty imgNhaTro}">
                    <p>Không có ảnh cho nhà trọ này.</p>
                </c:if>
            </div>
            <div class="details-column">
                <div class="price-section">
                    <span class="price">${avePrice} tr</span>
                    <span class="price-period">/tháng</span>
                </div>
                <div class="property-details">
                    <h2 class="property-name">${currenthouse.tenNhaTro}</h2>
                    <p class="property-info">
                        <span class="highlight">Địa chỉ:</span> ${currenthouse.dia_chi}
                    </p>
                    <p class="property-info">
                        <span class="highlight">Quản lí:</span> Trần Mạnh Trung
                    </p>
                    <p class="property-info">
                        <span class="highlight">Số điện thoại liên lạc:</span> 0231545554
                    </p>
                    
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
            <div class="stats">
                <div class="stat-item">
                    <span class="stat-value vacant">Trống</span>
                    <span class="stat-label">3 phòng</span>
                </div>
                <div class="stat-divider"></div>
                <div class="stat-item">
                    <span class="stat-value occupied">Đã thuê</span>
                    <span class="stat-label">7 phòng</span>
                </div>
            </div>
        </section>
                        <p class="property-info">
                        <% 

                                                        NhaTro nt = (NhaTro) session.getAttribute("currenthouse");
                                                        String moTa = nt.getMo_ta().replaceAll("(\r\n|\n)", "<br />");
                                                        request.setAttribute("moTa", moTa);
                        %>
                        <span class="highlight">Mô tả:</span> ${moTa}


                    </p>
        <!-- Room End -->

        <!--List Rooms of House Start-->
        <div class="container">

            <h3 class="ms-5 mt-5">Danh sách phòng</h3>
            <section class="search-tab my-1 row justify-content-center" id="filterstatus">
                <div class="tab-control"  style="max-width: 260px">
                    <div class="tab-items">
                        <div class="tab-item tab-item-active" tabindex="0" role="button" data-status="ALL">
                            <span class="">Tất cả</span>
                        </div>
                        <div class="tab-item" tabindex="0" role="button" data-status="T">
                            <img src="https://cdn.builder.io/api/v1/image/assets/TEMP/5cd65ab41fc6d423287b682a0ee310b9039fefcd15089122b388aefab255b240?placeholderIfAbsent=true&apiKey=3ed7f71bf41b4da6a6357316a7fb8826"
                                 alt="" class="tab-icon" />
                            <span>Trống</span>
                        </div>
                    </div>
                </div>
            </section>
            <div class="container-fluid row">
                <div class="col-lg-3 mt-3">
                    <form id="filterForm" action="filterrooms" method="get">
                        <div id="filter" class="p-2 bg-light ms-md-4 ms-sm-2 border">

                            <div class="border-bottom h5 text-uppercase p-2">Tiêu chí</div>
                            <div class="box border-bottom">
                                <div class="box-label text-uppercase d-flex align-items-center">Giá
                                    <button class="btn ms-auto" type="button" data-bs-toggle="collapse" data-bs-target="#inner-box" aria-expanded="false" aria-controls="inner-box">
                                        <span class="fas fa-plus"></span>
                                    </button>
                                </div>
                                <div id="inner-box" class="collapse show">
                                    <div class="my-1">
                                        <label class="tick">Dưới 1 triệu <input type="checkbox" name="price" value="0-1"> <span class="check"></span></label>
                                    </div>
                                    <div class="my-1">
                                        <label class="tick">1 - 2 triệu <input type="checkbox" name="price" value="1-2"> <span class="check"></span></label>
                                    </div>
                                    <div class="my-1">
                                        <label class="tick">2 - 3 triệu <input type="checkbox" name="price" value="2-3"> <span class="check"></span></label>
                                    </div>
                                    <div class="my-1">
                                        <label class="tick">3 - 4 triệu <input type="checkbox" name="price" value="3-4"> <span class="check"></span></label>
                                    </div>
                                    <div class="my-1">
                                        <label class="tick">4 - 6 triệu <input type="checkbox" name="price" value="4-6"> <span class="check"></span></label>
                                    </div>
                                    <div class="my-1">
                                        <label class="tick">6 - 8 triệu <input type="checkbox" name="price" value="6-8"> <span class="check"></span></label>
                                    </div>
                                    <div class="my-1">
                                        <label class="tick">8 - 10 triệu <input type="checkbox" name="price" value="8-10"> <span class="check"></span></label>
                                    </div>
                                    <div class="my-1">
                                        <label class="tick">Trên 10 triệu <input type="checkbox" name="price" value="10-9999"> <span class="check"></span></label>
                                    </div>
                                </div>
                            </div>
                            <div class="box border-bottom">
                                <div class="box-label text-uppercase d-flex align-items-center">Diện tích
                                    <button class="btn ms-auto" type="button" data-bs-toggle="collapse" data-bs-target="#property" aria-expanded="false" aria-controls="property">
                                        <span class="fas fa-plus"></span>
                                    </button>
                                </div>
                                <div id="property" class="collapse show">
                                    <div class="my-1">
                                        <label class="tick">Dưới 20m<sup>2</sup> <input type="checkbox" name="area" value="0-20"> <span class="check"></span></label>
                                    </div>
                                    <div class="my-1">
                                        <label class="tick">20m<sup>2</sup> - 30m<sup>2</sup><input type="checkbox" name="area" value="20-30"> <span class="check"></span></label>
                                    </div>
                                    <div class="my-1">
                                        <label class="tick">30m<sup>2</sup> - 40m<sup>2</sup> <input type="checkbox" name="area" value="30-40"> <span class="check"></span></label>
                                    </div>
                                    <div class="my-1">
                                        <label class="tick">40m<sup>2</sup> - 60m<sup>2</sup><input type="checkbox" name="area" value="40-60"> <span class="check"></span></label>
                                    </div>
                                    <div class="my-1">
                                        <label class="tick">60m<sup>2</sup> - 80m<sup>2</sup><input type="checkbox" name="area" value="60-80"> <span class="check"></span></label>
                                    </div>
                                    <div class="my-1">
                                        <label class="tick">Trên 80m<sup>2</sup> <input type="checkbox" name="area" value="80-10000"> <span class="check"></span></label>
                                    </div>
                                </div>
                            </div>

                            <div class="box">
                                <div class="box-label text-uppercase d-flex align-items-center">Tiện nghi phòng
                                    <button class="btn ms-auto" type="button" data-bs-toggle="collapse" data-bs-target="#room-facilities" aria-expanded="false" aria-controls="room-facilities">
                                        <span class="fas fa-plus"></span>
                                    </button>
                                </div>
                                <div id="room-facilities" class="collapse">
                                    <div class="my-1">
                                        <label class="tick">Điều hòa <input type="checkbox" checked="checked"> <span class="check"></span></label>
                                    </div>
                                    <div class="my-1">
                                        <label class="tick">Bàn làm việc <input type="checkbox"> <span class="check"></span></label>
                                    </div>
                                    <div class="my-1">
                                        <label class="tick">Ban công <input type="checkbox"> <span class="check"></span></label>
                                    </div>
                                    <div class="my-1">
                                        <label class="tick">TV  <input type="checkbox"> <span class="check"></span></label>
                                    </div>
                                    <div class="my-1">
                                        <label class="tick">Máy pha cà phê/trà <input type="checkbox"> <span class="check"></span></label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="col-lg-9">
                    <div class="filter container">
                        <div class="more-choice-genre d-flex justify-content-end">
                            <div class="d-flex align-items-start">
                                <p class="align-self-center p-2 d-flex">Sắp xếp theo:</p>
                                <div class="dropdown mb-3 p-2" style="background-color: transparent !important;">
                                    <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false" style="width: 140px">
                                        <c:if test="${param.by eq 'price'}">Giá</c:if>
                                        <c:if test="${param.by eq 'area'}">Diện tích</c:if>
                                        <c:if test="${param.by eq null || param.by eq ''}">Chọn sắp xếp</c:if>
                                        </button>
                                        <ul class="dropdown-menu dropdown-menu-dark" aria-labelledby="dropdownMenuButton">
                                            <li><button class="dropdown-item" type="button" data-sort="price">Giá</button></li>
                                            <li><button class="dropdown-item" type="button" data-sort="area">Diện tích</button></li>
                                        </ul>
                                    </div>
                                    <div class="reverse p-3">
                                        <button class="sort-button me-2" data-order="desc" style="
                                                border: none;
                                                background-color: transparent;"
                                                "><i class="fa-solid fa-arrow-down-wide-short"></i></button>
                                        <button class="sort-button me-2" data-order="asc" style="
                                                border: none;
                                                background-color: transparent;
                                                ""><i class="fa-solid fa-arrow-up-wide-short"></i></button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class=" rooms-of-house mt-3 d-flex" id="roomsContainer">
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
                                        <i class="fa-solid fa-stairs facility-icon"></i>
                                        <span class="facility-text">Tầng ${rs.tang}</span>
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
                </div>

            </div>
            <!--List Rooms of House End-->

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
            <script src="js/filter.js"></script>
            <!-- JavaScript Libraries -->
            <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
            <!--font awasome-->
            <script src="https://kit.fontawesome.com/aab0c35bef.js" crossorigin="anonymous"></script>
            <!--AOS lib-->
            <script>
                AOS.init();
            </script>

    </body>

</html>

