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
        <!--leaftlet-->
        <link rel="stylesheet" href="https://unpkg.com/leaflet/dist/leaflet.css" />

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
                        Tìm kiếm nhà trọ gần bạn
                    </div>
                </div>
                <div class="bg-white shadow" style="padding: 25px;">
                    <div class="row g-2">
                        <div class="col-md-12">
                            <form action="category" class="row g-4" onsubmit="return checkLatLon();">
                                <div class="col-md-10">

                                    <div class="form-input" data-target-input="nearest">
                                        <label for="addressInput" class="label mb-1">Vị trị</label>
                                        <input id="addressInput" type="text" class="form-control" 
                                               placeholder="Địa điểm của bạn..." oninput="suggestAddress(this.value)" required/>
                                        <div id="suggestions"></div>
                                        <input type="text" id="selectedlat" name="lat" hidden=""/>
                                        <input type="text" id="selectedlon" name="lon" hidden=""/>
                                    </div>
                                </div>
                                <!--                                <div class="col-md-5">
                                
                                                                    <div class="form-input" data-target-input="nearest">
                                                                        <label for="date" class="label mb-1">Thời gian</label>
                                                                        <input id="date" type="date" class="form-control" placeholder="Check out" />
                                                                    </div>
                                                                </div>-->
                                <div class="col-md-2 fast-search-btn d-flex justify-content-center align-items-center">
                                    <button href="category" class="btn p-3" type="submit">Tìm nhà trọ</button>
                                </div>
                            </form>
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
                                    <p class="mb-0">Tại Khu trọ TQAT, chúng tôi cam kết mang đến cho bạn những lựa chọn phòng trọ với giá cả cạnh tranh, minh bạch, và phong phú nhất. Chúng tôi hiểu rằng việc cân đối chi phí thuê trọ là một trong những yếu tố quan trọng hàng đầu khi bạn lựa chọn nơi ở. </p>
                                </div>
                            </div>
                            <div class="col-sm-6">
                                <div class="p-4">
                                    <i class="fa fa-users-cog fa-2x hightlight mb-2"></i>
                                    <h2 class="mb-1" data-toggle="counter-up">Tiện ích đầy đủ</h2>
                                    <p class="mb-0">Với tiêu chí "Giá tốt, chất lượng cao," mỗi phòng trọ đều được thiết kế nhằm tối ưu hóa không gian và trang bị đầy đủ các tiện ích cần thiết cho cuộc sống hàng ngày.</p>
                                </div>
                            </div>
                        </div>
                        <div class="row g-3 pb-4" data-aos="fade-up" data-aos-duration="1000">
                            <div class="col-sm-6">
                                <div class="p-4">
                                    <i class="fa fa-hotel fa-2x hightlight mb-2"></i>
                                    <h2 class="mb-1" data-toggle="counter-up">Tiện ích đa dạng</h2>
                                    <p class="mb-0">Phòng trọ được trang bị sẵn tiện ích như internet tốc độ cao, chỗ để xe, an ninh 24/7, khu vực bếp và nhà vệ sinh sạch sẽ – tất cả nhằm đáp ứng nhu cầu sống hiện đại và thoải mái nhất.</p>
                                </div>
                            </div>
                            <div class="col-sm-6">
                                <div class="p-4">
                                    <i class="fa fa-users-cog fa-2x hightlight mb-2"></i>
                                    <h2 class="mb-1" data-toggle="counter-up">Hỗ Trợ Linh Hoạt - Dễ Dàng Quản Lý Thông Tin</h2>
                                    <p class="mb-0">Tại Khu trọ TQAT, chúng tôi mang đến cho bạn một trải nghiệm trực tuyến tiện lợi ngay trên trang web. Với hệ thống quản lý hiện đại, bạn có thể dễ dàng xem thông tin cá nhân, hóa đơn, và theo dõi lịch sử giao dịch một cách nhanh chóng và chính xác.</p>
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
                    <a class="btn py-3 px-5 mt-2 explore mb-2" href="category" data-aos="fade-down" data-aos-duration="700">Xem chi tiết</a>
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
                <div id="housesContainer">


                </div>


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

        <!-- Footer End -->
        <!--jquery-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <script src="js/main.js"></script>

        <!--leaftlet-->
        <script src="https://unpkg.com/leaflet/dist/leaflet.js"></script>
        <script>
            // JavaScript
            

            let debounceTimeout=0;

            function suggestAddress(input) {
                if (debounceTimeout) {
                    clearTimeout(debounceTimeout); 
                }

                debounceTimeout = setTimeout(() => {
                    if (input.length < 3) {
                        document.getElementById('suggestions').innerHTML = ''; 
                        return;
                    }

                    fetch(`https://nominatim.openstreetmap.org/search?format=json&q=\${input}&addressdetails=1&countrycodes=VN`)
                            .then(response => response.json())
                            .then(data => {
                                let suggestions = document.getElementById('suggestions');
                                suggestions.innerHTML = '';

                                data.forEach(item => {
                                    let div = document.createElement('div');
                                    div.className = 'suggestion-item';
                                    div.textContent = item.display_name;
                                    div.onclick = () => {
                                        selectAddress(item.lat, item.lon, item.display_name);
                                        document.getElementById('addressInput').value = item.display_name; 
                                    }
                                    suggestions.appendChild(div);
                                });
                            })
                            .catch(error => console.error('Error:', error));
                }, 200); // Đợi 500ms trước khi gọi API
            }


            function selectAddress(lat, lon, address) {

                document.getElementById("selectedlat").value = lat;
                document.getElementById("selectedlon").value = lon;
                document.getElementById('suggestions').innerHTML = '';
            }
            function checkLatLon() {
                const lat = document.getElementById('selectedlat').value;
                const lon = document.getElementById('selectedlon').value;
                if (!lat || !lon) {
                    const firstSuggestion = document.querySelector('#suggestions .suggestion-item');
                    if (firstSuggestion) {
                        firstSuggestion.click(); 
                    } else {

                        return false; 
                    }
                }

                return true; 
            }

        </script>
        <!-- JavaScript Libraries -->

        <!--font awasome-->
        <script src="https://kit.fontawesome.com/aab0c35bef.js" crossorigin="anonymous"></script>
        <!--AOS lib-->
        <script src="https://unpkg.com/aos@next/dist/aos.js"></script>
        <script>
            AOS.init();
        </script>
        <!--jquery-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
 <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    </body>
  
</html>
