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

        <section class="container rental-property">

            <div class="image-column">

                <c:if test="${not empty imgNhaTro}">

                    <div id="anhnhatro" class="carousel slide">
                        <div style="position: relative">
                            <div class="carousel-inner img-above">
                                <c:forEach var="anh" items="${imgNhaTro.get(0).URL_AnhNhaTro}" varStatus="status">
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
                            <c:forEach var="anhUnder" items="${imgNhaTro.get(0).URL_AnhNhaTro}" varStatus="status">
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
                        <span class="highlight">Quản lí:</span> ${ql.name}
                    </p>
                    <p class="property-info">
                        <span class="highlight">Số điện thoại liên lạc:</span> ${ql.phone}
                    </p>

                    <div class="facilities">
                        <div class="facility">
                            <img src="https://cdn.builder.io/api/v1/image/assets/TEMP/01d699666662d8bd83b20fc07d97a0db5f5e91bf78cb0ee3e5320f65dcced623?placeholderIfAbsent=true&apiKey=3ed7f71bf41b4da6a6357316a7fb8826"
                                 alt="Bed icon" class="facility-icon">
                            <span class="facility-text">${rooms.size()} phòng</span>
                        </div>

                    </div>
                </div>

            </div>

        </section>
        <div class="container mo-ta">
            <div class="property-info ">
                <% 

                                                NhaTro nt = (NhaTro) session.getAttribute("currenthouse");
                                                String moTa = nt.getMo_ta().replaceAll("(\r\n|\n)", "<br />");
                                                request.setAttribute("moTa", moTa);
                %>
                <h5 class="highlight container">Thông tin mô tả:</h5> 
                <p class="container px-4" style="font-size: 15px; font-weight: 500;">${moTa}</p>


            </div>
            <div id="map" class="" style="height: 150px;"></div>
        </div>

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
                                <div class="">
                                    <!-- Image Carousel -->
                                    <c:set var="room" value="${rs.ID_Phong}" />

                                    <!--Get all room of the current nhatro-->
                                    <%  Integer idRoom = (Integer) pageContext.getAttribute("room");
                                            
                                                                    PhongDAO pd= new PhongDAO();
                                                                List<String> images= pd.getImagesByPhongId(idRoom);
                                                                request.setAttribute("images",images);

                                    %>
                                    <div id="roomImageCarousel" class="carousel slide " data-ride="carousel">
                                        <div class="carousel-inner img-detailRoom">
                                            <c:forEach items="${images}" var="image" varStatus="status">
                                                <div class="carousel-item ${status.index == 0 ? 'active' : ''}">
                                                    <img src="${image}" class="d-block w-100" alt="${rs.tenPhongTro}" style="
                                                         aspect-ratio: 16/9;
                                                         ">
                                                </div>
                                            </c:forEach>
                                        </div>
                                        <a class="carousel-control-prev" href="#roomImageCarousel" role="button" data-slide="prev">
                                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                            <span class="sr-only">Previous</span>
                                        </a>
                                        <a class="carousel-control-next" href="#roomImageCarousel" role="button" data-slide="next">
                                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                            <span class="sr-only">Next</span>
                                        </a>
                                    </div>
                                </div>
                                <div class="listing-info">
                                    <div class="listing-details">
                                        <div class="listing-price">
                                            <span class="price">${rs.gia/1000000} tr</span>
                                            <span class="price-period">/tháng</span>
                                        </div>
                                        <h4 class="listing-name">Phòng ${rs.tenPhongTro}</h4>
                                        <c:if test="${rs.trang_thai.equals('T')}">
                                            <span class="d-inline-block" tabindex="0" data-bs-toggle="popover" data-bs-trigger="hover focus" data-bs-content="Phòng trống" style="color: green">
                                                <i class="fa-solid fa-circle-exclamation" style="color: #28A745" disabled></i> Trống
                                            </span>
                                        </c:if>
                                        <c:if test="${rs.trang_thai.equals('D')}">
                                            <span class="d-inline-block" tabindex="0" data-bs-toggle="popover" data-bs-trigger="hover focus" data-bs-content="Phòng trống" style="color: red">
                                                <i class="fa-solid fa-circle-exclamation" style="color: red" disabled></i> Đã thuê
                                            </span>
                                        </c:if>
                                        <c:if test="${rs.trang_thai.equals('DT')}">
                                            <span class="d-inline-block" tabindex="0" data-bs-toggle="popover" data-bs-trigger="hover focus" data-bs-content="Phòng trống" style="color: #007BFF">
                                                <i class="fa-solid fa-circle-exclamation" style="color: #007BFF" disabled></i> Trống tháng sau
                                            </span>
                                        </c:if>

                                    </div>


                                    <div class="details-button">
                                        <button class="icons like" data-room-id="${rs.ID_Phong}">
                                            <i class="fa-regular fa-heart unlike"></i>
                                            <i class="fa-solid fa-heart like" style="color:rgb(98, 136, 218); display: none;"></i>
                                        </button>
                                        <input id="idphonglike" name="likephong" value="${rs.ID_Phong}" type="hidden"/>
                                        <a href="roomdetails?idPhong=${rs.ID_Phong}" class="mt-2">

                                            <span class="details-text">Chi tiết</span>
                                        </a>

                                        <img src="https://cdn.builder.io/api/v1/image/assets/TEMP/38af489b0b824b4e9a4ca4f97538a5a535b0617bf3be7a86b92c79ae52dfe2c5?placeholderIfAbsent=true&apiKey=3ed7f71bf41b4da6a6357316a7fb8826"
                                             alt="Underline" class="details-underline">
                                    </div>
                                </div>
                                <hr class="divider">
                                <div class="facilities">

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
            <div class="modal fade" id="loginPromptModal" tabindex="-1" aria-labelledby="loginPromptLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="loginPromptLabel">Yêu cầu đăng nhập</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            Xin vui lòng đăng nhập để sử dụng tiện ích của chúng tôi.
                        </div>

                    </div>
                </div>
            </div>

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
            <!-- Chat Button -->

            <!-- Chat Button -->
            <c:if  test="${account.role == 'tenant' or account.role == 'guest'}"  >

                <div id="chat-button" style="position: fixed; bottom: 20px; right: 20px; z-index: 1000;">
                    <button onclick="toggleChatBox()" style="background-color: #00bfff; border: none; padding: 15px; border-radius: 50%; position: relative;">
                        <i class="fas fa-comments" style="color: white; font-size: 24px;"></i>
                        <span id="unreadCount" style="display: none; position: absolute; top: -5px; right: -5px; background-color: red; color: white; border-radius: 50%; padding: 5px; font-size: 12px;">
                            0
                        </span>
                    </button>
                </div>
            </c:if>

            <section id="chat-box" style="display: none; position: fixed; bottom: 80px; right: 20px; z-index: 1000;  float: right">
                <div class="container py-5" style="
    max-width: 550px;">
                    <div class="row d-flex justify-content-center">


                        <!-- Main container -->
                        <div class="d-flex">
                            <c:if test="${account == null}">
                                <div class="card flex-grow-1" id="chat1" style="border-radius: 15px; margin-left: 15px; min-width: 450px;">
                                    <div class="card-header d-flex justify-content-between align-items-center p-3 bg-info text-white border-bottom-0"
                                         style="border-top-left-radius: 15px; border-top-right-radius: 15px;">
                                        <i class="fas fa-angle-left"></i>

                                        <i class="fas fa-times" onclick="toggleChatBox()"></i>
                                    </div>
                                    <div class="card-body" style="overflow-y: auto; min-height: 250px; text-align: center">
                                        Please <a href="login.jsp">Login </a> to start chatting
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${account != null}">

                                <!-- Left card: User list -->


                                <!-- Right card: Chat messages -->
                                <div class="card flex-grow-1" id="chat1" style="border-radius: 15px; margin-left: 15px;">
                                    <div class="card-header d-flex justify-content-between align-items-center p-3 bg-info text-white border-bottom-0"
                                         style="border-top-left-radius: 15px; border-top-right-radius: 15px;">
                                        <i class="fas fa-angle-left"></i>
                                        <p class="mb-0 fw-bold">${ct.name}</p>
                                        <i class="fas fa-times" onclick="toggleChatBox()"></i>
                                    </div>
                                    <div class="card-body" id="chatMessages" style="overflow-y: auto; min-height: 250px;">
                                        <!-- Chat messages will be appended here -->
                                    </div>
                                    <div data-mdb-input-init class="form-outline">
                                        <form id="sendMessageForm" class="d-flex align-items-center">
                                            <input type="hidden" name="receiver_id" value="${ct.account.getID_Account()}" />
                                            <input type="hidden" name="houseId" value="${currenthouse.getID_NhaTro()}" />
                                            <textarea class="form-control bg-body-tertiary me-3" id="textAreaExample" rows="1" name="messageContent" placeholder="Type your message"></textarea>
                                            <button type="button" class="btn btn-primary" id="sendMessageButton">
                                                <i class="fas fa-paper-plane"></i>
                                            </button>
                                        </form>
                                    </div>
                                </div>


                            </c:if>
                        </div>

                    </div>
                </div>
            </section>


            <!-- Add JavaScript to toggle the chat box -->
            <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
            <script>
                                            var userRole = "<c:out value='${account.role}' />";
                                            function toggleChatBox() {
                                                var chatBox = document.getElementById("chat-box");
                                                if (chatBox.style.display === "none" || chatBox.style.display === "") {
                                                    chatBox.style.display = "block";
                                                    fetchMessages(); // Fetch messages when chat box is opened
                                                    fetchUnreadMessageCount(); // Update unread count in the icon
                                                } else {
                                                    chatBox.style.display = "none";
                                                }
                                            }



                                            function displayMessages(response, currentUserId) {
                                                let chatContent = '';
                                                response.forEach(function (msg) {
                                                    if (msg.senderId === currentUserId) {
                                                        // Outgoing message
                                                        chatContent += `
        <div class="d-flex flex-row justify-content-end mb-4">
          <div class="p-3 outgoing-message message">
            <p class="small mb-0">` + msg.content + `</p>
          </div>
          <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava1-bg.webp" alt="avatar">
        </div>
      `;
                                                    } else {
                                                        // Incoming message
                                                        chatContent += `
        <div class="d-flex flex-row justify-content-start mb-4">
          <img src="https://cdn.pixabay.com/photo/2020/07/14/13/07/icon-5404125_1280.png" alt="avatar">
          <div class="p-3 incoming-message message">
            <p class="small mb-0">` + msg.content + `</p>
          </div>
        </div>
      `;
                                                    }
                                                });
                                                document.getElementById('chatMessages').innerHTML = chatContent;
                                                const chatMessagesDiv = document.getElementById('chatMessages');
                                                chatMessagesDiv.scrollTop = chatMessagesDiv.scrollHeight;
                                            }

                                            function fetchMessages() {
                                                // Directly fetch the updated receiver_id from the hidden input
                                                const receiverId = $('input[name="receiver_id"]').val();
                                                const houseId = $('input[name="houseId"]').val();
                                                const currentUserId = parseInt(${account.getID_Account()});

                                                // Verify both receiverId and currentUserId for accuracy

                                                if (!receiverId) {
                                                    return; // Exit if no user is selected
                                                }

                                                // Prepare data object
                                                var data = {houseId: houseId};
                                                if (userRole === 'landlord') {
                                                    data.selectedUserId = receiverId;  // Use receiverId for landlord's selected user
                                                } else {
                                                    data.receiverId = receiverId;  // Use receiverId for tenant role
                                                }

                                                // Confirm data object content
                                                $.ajax({
                                                    url: "getMessages",
                                                    method: "GET",
                                                    data: data,
                                                    dataType: "json",
                                                    success: function (response) {
                                                        displayMessages(response, currentUserId);
                                                    },
                                                    error: function () {
                                                    }
                                                });
                                            }

                                            if (userRole === 'landlord') {
                                                $(document).ready(function () {
                                                    function fetchUserList() {
                                                        $.ajax({
                                                            url: "getUserList",
                                                            method: "GET",
                                                            dataType: "json",
                                                            success: function (response) {
                                                                let userListContent = '';
                                                                response.forEach(function (user) {
                                                                    var unreadBadge = '';
                                                                    if (user.unreadCount > 0) {
                                                                        unreadBadge = `<span class="badge bg-danger ms-2">` + user.unreadCount + ` </span>`;
                                                                    }

                                                                    userListContent += `
                    <li class="list-group-item d-flex justify-content-between align-items-center" data-user-id="` + user.ID_Account + `" data-username="` + user.username + `" onclick="selectUser(this)">
                        ` + user.username + unreadBadge + `
                    </li>
                `;
                                                                });
                                                                $('#userList').html(userListContent);
                                                            },
                                                            error: function () {
                                                                console.error('Error fetching user list');
                                                            }
                                                        });
                                                    }

                                                    setInterval(fetchUserList, 5000);



                                                    window.selectUser = function (element) {
                                                        const userId = $(element).data('user-id');
                                                        const username = $(element).data('username');

                                                        // Set the selected user ID in the hidden input
                                                        $('input[name="receiver_id"]').val(userId);
                                                        $('#chatWithName').text('Chat with ' + username);

                                                        // Highlight the selected user
                                                        $('#userList .list-group-item').removeClass('active');
                                                        $(element).addClass('active');

                                                        // Fetch messages with the selected user
                                                        fetchMessages();

                                                        // Reset unread count badge
                                                        $(element).find('.badge').remove();
                                                    };



                                                    fetchUserList();
                                                });
                                            } else {
                                                // If the user is not a landlord, set receiver ID and fetch messages
                                                $(document).ready(function () {
                                                    $('#receiver_id').val(${ct.account.ID_Account});
                                                    fetchMessages();
                                                });
                                            }


                                            $(document).ready(function () {
                                                $('#sendMessageButton').click(function () {
                                                    const messageContent = $('#textAreaExample').val();
                                                    const receiverId = $('input[name="receiver_id"]').val();
                                                    const houseId = $('input[name="houseId"]').val();



                                                    if (messageContent !== "" && receiverId) {
                                                        var data = {
                                                            messageContent: messageContent,
                                                            houseId: houseId
                                                        };
                                                        if (userRole === 'landlord') {
                                                            data.selectedUserId = receiverId;
                                                        } else {
                                                            data.receiverId = receiverId;
                                                        }

                                                        $.ajax({
                                                            url: "sendMessage",
                                                            method: "POST",
                                                            data: data,
                                                            success: function () {
                                                                $('#textAreaExample').val(''); // Clear input on success
                                                                fetchMessages(); // Refresh messages
                                                            },
                                                            error: function () {
                                                                alert('Error sending message');
                                                            }
                                                        });
                                                    } else {
                                                    }
                                                });
                                            });

                                            function fetchUnreadMessageCount() {
                                                $.ajax({
                                                    url: 'getUnreadMessageCount',
                                                    method: 'GET',
                                                    dataType: 'json',
                                                    success: function (response) {
                                                        var unreadCount = response.unreadCount;
                                                        if (unreadCount > 0) {
                                                            $('#unreadCount').text(unreadCount);
                                                            $('#unreadCount').show();
                                                        } else {
                                                            $('#unreadCount').hide();
                                                        }
                                                    },
                                                    error: function () {
                                                        console.error('Error fetching unread message count');
                                                    }
                                                });
                                            }

// Call this function periodically
                                            setInterval(fetchUnreadMessageCount, 5000);

// Also call it on page load
                                            $(document).ready(function () {
                                                fetchUnreadMessageCount();
                                            });


                                            // If the user is not a landlord, set receiver ID and fetch messages
                <c:if test="${account.role != 'landlord'}">
                                            $(document).ready(function () {
                                                $('input[name="receiver_id"]').val(${ct.account.ID_Account});
                                                fetchMessages();
                                            });
                </c:if>
                                            setInterval(fetchMessages, 5000);

            </script>
            <!-- Font Awesome for chat icon -->
            <script src="https://kit.fontawesome.com/aab0c35bef.js" crossorigin="anonymous"></script>


            <!-- Footer End -->

            <script src="js/main.js"></script>
            <script src="js/filter.js"></script>
            <script>
                <c:choose>
                    <c:when test="${not empty sessionScope.account}">
                const userId = ${sessionScope.account.ID_Account};
                const likedRoomsKey = `likedRooms_\${userId}`;

                function setCookie(name, value, days) {
                    const d = new Date();
                    d.setTime(d.getTime() + (days * 24 * 60 * 60 * 1000));
                    document.cookie = `\${name}=\${value};expires=\${d.toUTCString()};path=/NhaTroTQAT`;
                }

                function getCookie(name) {
                    const value = `; \${document.cookie}`;
                    const parts = value.split(`; \${name}=`);
                    return parts.length === 2 ? parts.pop().split(';').shift() : null;
                }

                // Lấy danh sách các phòng đã thích từ cookie
                function getLikedRooms() {
                    const likedRooms = getCookie(likedRoomsKey);
                    return likedRooms ? likedRooms.split(',') : [];
                }

                // Cập nhật danh sách phòng đã thích trong cookie
                function updateLikedRooms(roomId, isLiked) {
                    let likedRooms = getLikedRooms();

                    if (isLiked) {
                        // Thêm phòng vào danh sách nếu chưa có
                        if (!likedRooms.includes(roomId)) {
                            likedRooms.push(roomId);
                        }
                    } else {
                        // Xóa phòng khỏi danh sách nếu đã có
                        likedRooms = likedRooms.filter(id => id !== roomId);
                    }

                    // Cập nhật lại cookie với danh sách phòng mới
                    setCookie(likedRoomsKey, likedRooms.join(','), 30);
                }

                // Xử lý sự kiện khi nhấn nút like
                $('.icons.like').on('click', function () {
                    const roomId = $(this).data('room-id').toString();
                    const likedRooms = getLikedRooms();
                    const isLiked = likedRooms.includes(roomId);
                    const newStatus = !isLiked;

                    // Cập nhật danh sách và cookie
                    updateLikedRooms(roomId, newStatus);

                    // Cập nhật giao diện
                    $(this).find('.unlike').toggle(!newStatus);
                    $(this).find('.like').toggle(newStatus);
                });

                // Cập nhật giao diện khi tải trang
                $(document).ready(function () {
                    const likedRooms = getLikedRooms();

                    $('.icons.like').each(function () {
                        const roomId = $(this).data('room-id').toString();
                        const isLiked = likedRooms.includes(roomId);

                        $(this).find('.unlike').toggle(!isLiked);
                        $(this).find('.like').toggle(isLiked);
                    });
                });
                    </c:when>
                    <c:otherwise>
                $('.icons.like').on('click', function () {
                    const loginPromptModal = new bootstrap.Modal(document.getElementById('loginPromptModal'));
                    loginPromptModal.show();
                });
                    </c:otherwise>
                </c:choose>
            </script>

            <!--leaftlet-->
            <script src="https://unpkg.com/leaflet/dist/leaflet.js"></script>
            <!-- Leaflet Routing Machine JS -->
            <script src="https://unpkg.com/leaflet-routing-machine/dist/leaflet-routing-machine.js"></script>

            <script>
                function openModal(imageSrc) {
                    document.getElementById('modalImage').src = imageSrc;
                    var imageModal = new bootstrap.Modal(document.getElementById('imageModal'));
                    imageModal.show();
                }
            </script>
            <script>
                // JavaScrip            t
                var map = L.map('map').setView([10.823099, 106.629654], 13);
                L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                    maxZoom: 19,
                    attribution: '© OpenStreetMap contributors'
                }).addTo(map);
                var marker = null;
                <%
double lat = nt.getLat();                 
double lon = nt.getLon(); 
String tenNhaTro = nt.getTenNhaTro(); 
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
                    routeWhileDragging: true,
                    showAlternatives: false, // Disable alternative routes
                    createMarker: function () {
                        return null;
                    } // Disable default markers
                }).addTo(map);
                </c:if>




            </script>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<!--             JavaScript Libraries 
-->            <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
            <!--font awasome-->
            <script src="https://kit.fontawesome.com/aab0c35bef.js" crossorigin="anonymous"></script>
            <!--AOS lib-->
            <script>
                                            AOS.init();
            </script>

    </body>
    <style>


        /* Outgoing message (aligns to the right) */
        /* Outgoing message (aligns to the right) */
        #chatMessages .outgoing-message {
            background-color: #e0f7fa;
            text-align: left;
            border-radius: 12px 12px 0 12px;
            margin: 2px 0 2px auto;
            align-self: flex-end;
            max-width: 60%; /* Add this line */
            word-wrap: break-word; /* Add this line */
            word-break: break-word; /* Add this line */
            white-space: normal; /* Add this line */
        }

        /* Incoming message (aligns to the left) */
        #chatMessages .incoming-message {
            background-color: #f1f1f1;
            text-align: left;
            border-radius: 12px 12px 12px 0;
            margin: 2px auto 2px 0;
            align-self: flex-start;
            max-width: 60%; /* Add this line */
            word-wrap: break-word; /* Add this line */
            word-break: break-word; /* Add this line */
            white-space: normal; /* Add this line */
        }


        /* Avatar styling for alignment with message */
        #chatMessages img {
            width: 30px;
            height: 30px;
            border-radius: 50%;
            margin: 0; /* Removes extra margin around avatar */
        }

        /* Container for each message with alignment adjustments */
        .message-container {
            display: flex;
            align-items: center; /* Center-aligns avatar and message bubble vertically */
            gap: 6px; /* Space between avatar and message */
            margin: 2px 0; /* Reduces space between messages */
        }

        /* Outgoing and Incoming containers */
        .message-container.outgoing {
            justify-content: flex-end;
        }

        .message-container.incoming {
            justify-content: flex-start;
        }

        /* Chat box styling */
        #chatMessages {
            overflow-y: auto;
            max-height: 400px;
            padding: 10px;
            display: flex;
            flex-direction: column;
        }

    </style>

</html>

