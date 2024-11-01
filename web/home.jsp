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
                        Renting
                    </div>
                </div>
                <div class="bg-white shadow" style="padding: 25px;">
                    <div class="row g-2">
                        <div class="col-md-12">
                            <div class="row g-4">
                                <div class="col-md-5">

                                    <div class="form-input" data-target-input="nearest">
                                        <label for="addressInput" class="label mb-1">Vị trí</label>
                                        <input id="addressInput" type="text" class="form-control" 
                                               placeholder="Địa điểm của bạn..." oninput="suggestAddress(this.value)"/>
                                        <div id="suggestions"></div>
                                        <input type="text" id="selectedlat" name="lat" hidden/>
                                        <input type="text" id="selectedlon" name="lon" hidden/>
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


                <div id="map"></div>

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
        <!--jquery-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <script src="js/main.js"></script>

        <!--leaftlet-->
        <script src="https://unpkg.com/leaflet/dist/leaflet.js"></script>
        <script>
            // JavaScript
            var map = L.map('map').setView([10.823099, 106.629654], 13);
            L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                maxZoom: 19,
                attribution: '© OpenStreetMap contributors'
            }).addTo(map);
            var marker = null;
            map.on('click', function (e) {
                let lat = e.latlng.lat; // Lấy vĩ độ
                let lon = e.latlng.lng; // Lấy kinh độ

                if (marker) {
                    map.removeLayer(marker);
                }
                marker = new L.marker([lat, lon]);

                fetch(`https://nominatim.openstreetmap.org/reverse?format=json&lat=\${lat}&lon=\${lon}&addressdetails=1`)
                        .then(response => response.json())
                        .then(data => {
                            let address = data.display_name || 'Địa chỉ không tìm thấy';
                            map.addLayer(marker); // Thêm marker với địa chỉ
                            marker.bindPopup(address).openPopup(); // Hiển thị popup với địa chỉ
                        })
                        .catch(error => console.error('Error:', error));
            });

            function suggestAddress(input) {
                if (input.length < 3) {
                    document.getElementById('suggestions').innerHTML = ''; // Xóa gợi ý
                    return;
                }

                fetch(`https://nominatim.openstreetmap.org/search?format=json&q=\${input}&addressdetails=1&countrycodes=VN`)
                        .then(response => response.json())
                        .then(data => {
                            let suggestions = document.getElementById('suggestions');
                            suggestions.innerHTML = ''; // Xóa gợi ý trước đó

                            data.forEach(item => {
                                let div = document.createElement('div');
                                div.className = 'suggestion-item';
                                div.textContent = item.display_name;
                                div.onclick = () => {
                                    selectAddress(item.lat, item.lon, item.display_name);
                                    document.getElementById('addressInput').value = item.display_name; // Cập nhật giá trị input
                                }
                                suggestions.appendChild(div);
                            });
                        })
                        .catch(error => console.error('Error:', error));
            }

            function selectAddress(lat, lon, address) {

                map.setView([lat, lon], 13);
                if (marker) {
                    map.removeLayer(marker);
                }
                marker = new L.marker([lat, lon]).addTo(map).bindPopup(address).openPopup();
                document.getElementById('suggestions').innerHTML = '';
            }

        </script>
        <!-- JavaScript Libraries -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
        <!--font awasome-->
        <script src="https://kit.fontawesome.com/aab0c35bef.js" crossorigin="anonymous"></script>
        <!--AOS lib-->
        <script src="https://unpkg.com/aos@next/dist/aos.js"></script>
        <script>
            AOS.init();
        </script>
        <!--jquery-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <script>

        </script>
    </body>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</html>
