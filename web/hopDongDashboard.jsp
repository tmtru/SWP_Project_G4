<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
    <head>
        <meta charset="utf-8"/>
        <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
        <title>
            Contract Management
        </title>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet"/>
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&amp;display=swap" rel="stylesheet"/>
        <link href="${pageContext.request.contextPath}/css/css-account-management.css" rel="stylesheet" />
    </head>
    <body>
        <div class="container">
            <jsp:include page="adminUserManagement/sidebar.jsp"></jsp:include>
                <section class="home mx-3">
                    <div class="main-content">
                        <h2>
                            Quản lý hợp đồng
                        </h2>
                        <div class="filters">
                            <button onclick="window.location.href = 'hop-dong?action=add'">+ Thêm hợp đồng</button>
                            <form action="accountController" method="get" style="display: flex; align-items: center;">
                                <input name="searchTerm" placeholder="Search by name" type="text" value="${param.searchTerm}"/>
                            <button type="submit">
                                Search
                            </button>
                        </form>
                    </div>
                    <table class="account-table">
                        <thead>
                            <tr>
                                <th scope="col">ID</th>
                                <th scope="col">ID Khách thuê</th>
                                <th scope="col">ID Phòng trọ</th>
                                <th scope="col">Ngày có giá trị</th>
                                <th scope="col">Ngày hết hạn</th>
                                <th scope="col">Tiền cọc</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${listHopDong}" var="hd">
                                <tr>
                                    <td>${hd.getID_HopDong()}</td>
                                    <td>${hd.getID_KhachThue()}</td>
                                    <td>${hd.getID_PhongTro()}</td>
                                    <td>${hd.getNgay_gia_tri()}</td>
                                    <td>${hd.getNgay_het_han()}</td>
                                    <td>${hd.getTien_Coc()}</td>
                                </tr>
                            </c:forEach>

                        </tbody>
                    </table>
                    <div class="pagination">
                        <c:if test="${currentPage > 1}">
                            <button onclick="changePage(${currentPage - 1})">&laquo; Previous</button>
                        </c:if>

                        <c:forEach begin="1" end="${noOfPages}" var="i">
                            <c:choose>
                                <c:when test="${currentPage eq i}">
                                    <button class="active">${i}</button>
                                </c:when>
                                <c:otherwise>
                                    <button onclick="changePage(${i})">${i}</button>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>

                        <c:if test="${currentPage < noOfPages}">
                            <button onclick="changePage(${currentPage + 1})">Next &raquo;</button>
                        </c:if>
                    </div>

                    <script>
                        function changePage(page) {
                            window.location.href = 'hop-dong?page=' + page;
                        }
                    </script>
                </div>
            </section>
        </div>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    </body>
</html>
</html>