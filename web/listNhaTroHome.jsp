<%-- 
    Document   : listNhaTroHome
    Created on : Sep 27, 2024, 2:50:51 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.List, java.util.ArrayList,java.text.DecimalFormat, model.NhaTro, model.Phong, dal.NhaTroDAO, dal.PhongDAO, model.AnhNhaTro, dal.AnhNhaTroDAO"%>

<body>
    <c:if test="${not empty nhatrosP}">
        <c:forEach items="${nhatrosP}" var="nt">
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
                    <a href="allrooms?id=${nt.ID_NhaTro}" >
                    <div class="featured-card">
                        <c:if test="${not empty imgNhaTro}">
                            <c:forEach var="anh" items="${imgNhaTro}" varStatus="status">
                                <c:if test="${status.index == 0}">
                                    <img src="${anh.URL_AnhNhaTro.get(0)}" alt="Ảnh nhà trọ" class="featured-image"/>
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
                            <div class="listing-address">${nt.dia_chi}</div>
                            <div class="desciption">${nt.mo_ta}</div>
                            
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
                                </a>
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

    <nav aria-label="Page navigation example d-flex mt-5">
        <ul class="pagination  justify-content-center">
            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                <span class="page-link" aria-label="Previous" onclick="loadPage(${page-1})">
                    <button aria-hidden="true" >&laquo;</button>
                </span>
            </li>
            <c:forEach var="page" begin="1" end="${totalPages}">
                <li class="page-item ${page == currentPage ? 'active' : ''}">
                    <button class="page-link" onclick="loadPage(${page})">${page}</button>
                </li>
            </c:forEach>
            <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                <span class="page-link" aria-label="Next" onclick="loadPage(${page+1})">
                    <button aria-hidden="true" style="text-decoration: none" >&raquo;</button>
                </span>
            </li>
        </ul>

    </nav>

<script src="js/main.js"></script>
</body>

