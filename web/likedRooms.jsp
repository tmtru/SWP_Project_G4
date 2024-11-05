<%-- 
    Document   : likedRooms
    Created on : Nov 5, 2024, 10:36:11 PM
    Author     : Admin
--%>

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

    <body>
        <!-- Header Start -->
        <%
// Lấy danh sách phòng đã thích từ request
String likedRoomsString = request.getParameter("likedRooms");
List<Phong> likedRooms = new ArrayList<>();

if (likedRoomsString != null) {
String[] roomIds = likedRoomsString.split(","); // Tách chuỗi thành mảng

PhongDAO pd = new PhongDAO();
for (String roomId : roomIds) {
if (roomId != null) {
            
likedRooms.add(pd.getDetailRoom(Integer.parseInt(roomId)));
}
}
}
request.setAttribute("likedRooms",likedRooms);
        %>

        <div id="likedRoomsContainer"> <!-- Container for liked rooms will be populated here -->
            <c:forEach items="${likedRooms}" var="rs">

                <article class="listing-card item">
                    
                        <div class="row"> <!-- Sử dụng class row của Bootstrap -->
                            <div class="col-md-6" style="
    display: flex;
    justify-content: center;
    align-items: center;
"> <!-- Cột cho hình ảnh -->
                                <c:set var="room" value="${rs.ID_Phong}" />

                                <%  
                                    Integer idRoom = (Integer) pageContext.getAttribute("room");
                                    PhongDAO pd= new PhongDAO();
                                    List<String> images= pd.getImagesByPhongId(idRoom);
                                    request.setAttribute("images", images);
                                %>
                                <div id="roomImageCarousel" class="carousel slide" data-ride="carousel">
                                    <div class="carousel-inner img-detailRoom">
                                        <c:forEach items="${images}" var="image" varStatus="status">
                                            <div class="carousel-item ${status.index == 0 ? 'active' : ''}">
                                                <img src="${image}" class="d-block w-100" alt="${rs.tenPhongTro}" style="aspect-ratio: 16/9;">
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
                                

                            <div class="col-md-6 liked"> <!-- Cột cho thông tin -->
                                <a href="roomdetails?idPhong=${rs.ID_Phong}" class="mt-2">
                                <div class="listing-info">
                                    <div class="listing-details">
                                        <div class="listing-price">
                                            <span class="price">${rs.gia / 1000000} tr</span>
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
                                </div>
                                        </a>
                            </div>
                                          
                        </div>
                  
                </article>


            </c:forEach>

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

