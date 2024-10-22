<%-- 
    Document   : detailHouseHome
    Created on : Sep 23, 2024, 4:51:51 PM
    Author     : Admin
--%>
<!--Trng để render phần ajax cho detailHouseHome-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@page import="java.util.List, java.util.ArrayList,java.text.DecimalFormat, model.NhaTro, model.Phong, dal.NhaTroDAO, dal.PhongDAO"%>


<!--List Rooms of House Start-->

<div class=" rooms-of-house d-flex">
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

<!--List Rooms of House End-->



<script src="js/main.js"></script>
<!-- JavaScript Libraries -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<!--font awasome-->
<script src="https://kit.fontawesome.com/aab0c35bef.js" crossorigin="anonymous"></script>
<!--AOS lib-->
<script>
    AOS.init();
</script>



