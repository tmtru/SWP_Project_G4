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
<!--        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:ital,wght@0,200..800;1,200..800&display=swap"
              rel="stylesheet">

        Libarary animate
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css" />

        AOS lib to reveal web when scroll
        <link rel="stylesheet" href="https://unpkg.com/aos@next/dist/aos.css" />-->
        <!--leaftlet-->
        <link rel="stylesheet" href="https://unpkg.com/leaflet/dist/leaflet.css" />
        <!-- Leaflet Routing Machine CSS -->
        <link rel="stylesheet" href="https://unpkg.com/leaflet-routing-machine/dist/leaflet-routing-machine.css" />

        


        <!-- Stylesheet -->
        <link href="css/style.css" rel="stylesheet">


    </head>
    <body>
        <!-- Header Start -->
        <jsp:include page="header.jsp" /> 
        <!-- Header End -->

        <!--Map-->


        <section class="contact-section mt-5">
            <div class="contact-container container " data-aos="fade-down" data-aos-duration="700">
                <div class="contact-content col-md-6">
                    <div class="contact-info">
                        <div class="contact-details">
                            <h2 class="contact-headline">Hãy liên hệ với chúng tôi để biết thêm chi tiết</h2>
                            <div class="contact-features">
                                <div class="contact-card">
                                    <div class="card-header">
                                        <div class="card-content">
                                            <div class="icon-wrapper">
                                                <div class="icon" aria-hidden="true"><i class="fa-solid fa-map-location-dot" style="
                                                                                        text-align: center;
                                                                                        width: 64px;
                                                                                        height: 64px;
                                                                                        display: flex;
                                                                                        align-items: center;
                                                                                        justify-content: center;
                                                                                        color: white;
                                                                                        font-size: 26px;"></i></div>
                                            </div>
                                            <h3 class="card-title">Thông tin liên hệ nhà trọ </h3>
                                            <div class="dropdown">
                                            <button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                                                <c:if test="${nhatrohientai!=null}">
                                                    ${nhatrohientai.tenNhaTro}
                                                </c:if>
                                            </button>
                                            <ul class="dropdown-menu">
                                                <c:forEach var="nt" items="${sessionScope.nhatros}">
                                                    <li><a class="dropdown-item" href="thongtinlienhe?id=${nt.ID_NhaTro}">${nt.tenNhaTro}</a></li>
                                                </c:forEach>
                                            </ul>
                                        </div>   
                                        </div>
                                                                         </div>
                                    <p class="contact-address">
                                        <strong style="color:black">Địa chỉ nhà trọ:</strong> ${nhatrohientai.dia_chi}<br>
                                        <c:if test="${ql!=null}" >
                                        
                                        <strong style="color:black">SĐT liên hệ nhà trọ</strong>: ${ql.phone}<br/>
                                        </c:if>
                                    </p>
                                    <hr>
                                    <p class="contact-address">
                                        <strong style="color:black">SĐT liên hệ khu trọ</strong>: 02315611542
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


                <div id="map" class=""></div>

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
        <!-- Leaflet Routing Machine JS -->
        <script src="https://unpkg.com/leaflet-routing-machine/dist/leaflet-routing-machine.js"></script>


        <script>
            // JavaScript
            var map = L.map('map').setView([10.823099, 106.629654], 13);
            L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                maxZoom: 19,
                attribution: '© OpenStreetMap contributors'
            }).addTo(map);
            var marker = null;
            
            <c:if test="${not empty requestScope.nhatrohientai}">
                <%
        NhaTro nhatrohientai = (NhaTro) request.getAttribute("nhatrohientai");
        double lat = nhatrohientai.getLat(); 
        double lon = nhatrohientai.getLon(); 
        String tenNhaTro = nhatrohientai.getTenNhaTro(); 
                %>
            var nhatroLat = <%= lat %>;
            var nhatroLon = <%= lon %>;
            var nhatroName = "<%= tenNhaTro %>";
            map.setView([nhatroLat, nhatroLon], 13);
            var nhatroMarker = new L.marker([nhatroLat, nhatroLon]).addTo(map)
                    .bindPopup(nhatroName).openPopup();

                <c:if test="${not empty sessionScope.lat and not empty sessionScope.lon}">
                    <%
                        String latStr = (String) session.getAttribute("lat");
                        String lonStr = (String) session.getAttribute("lon");
                        double userLat = 0.0;
                        double userLon = 0.0;

                        try {
                            userLat = Double.parseDouble(latStr);
                            userLon = Double.parseDouble(lonStr);
                        } catch (NumberFormatException e) {
                            e.printStackTrace(); 
                        }
                    %>
            var userMarker = new L.marker([<%= userLat %>, <%= userLon %>], {
                icon: L.icon({
                    iconUrl: 'assets/img/navigation.png',
                    iconSize: [25, 30]
                })
            }).addTo(map)
                    .bindPopup("Vị trí của bạn");
            var control = L.Routing.control({
                waypoints: [
                    L.latLng(nhatroLat, nhatroLon), 
                    L.latLng(<%= userLat %>, <%= userLon %>)      
                ],
                routeWhileDragging: true
            }).addTo(map);
                </c:if>
            </c:if>


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

        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
         <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    </body>

</html>
