<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
    <head>
        <meta charset="utf-8"/>
        <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
        <title>Account Management</title>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet"/>
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&amp;display=swap" rel="stylesheet"/>
        <link href="${pageContext.request.contextPath}/css/css-account-management.css" rel="stylesheet" />
    </head>
    <body>
        <div class="container">
            <jsp:include page="sidebar.jsp"></jsp:include>
            <section class="home mx-3">
                <div class="main-content">
                    <h2>Quản lý tài khoản</h2>
                    <div class="filters">
                        <button onclick="window.location.href = 'accountController?action=add'">+ Thêm quản lý</button>
                        <form action="accountController" method="get" style="display: flex; align-items: center;">
                            <input name="searchTerm" placeholder="Search by name" type="text" value="${param.searchTerm}"/>
                            <select name="role" onchange="this.form.submit()">
                                <option value="">Tất cả vai trò</option>
                                <option value="landlord" <c:if test="${param.role == 'landlord'}">selected</c:if>>Chủ trọ</option>
                                <option value="manager" <c:if test="${param.role == 'manager'}">selected</c:if>>Quản lý</option>
                                <option value="tenant" <c:if test="${param.role == 'tenant'}">selected</c:if>>Khách thuê</option>
                                <option value="guest" <c:if test="${param.role == 'guest'}">selected</c:if>>Khách xem</option>
                            </select>
                            <button type="submit">Search</button>
                        </form>
                    </div>
                    <table class="account-table">
                        <thead>
                            <tr>
                                <th>Tên tài khoản</th>
                                <th>Email</th>
                                <th>Vai trò</th>
                                <th>Kích hoạt</th>
                                <th>Hành động</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="account" items="${accounts}">
                                <tr>
                                    <td>${account.username}</td>
                                    <td>${account.email}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${account.role == 'landlord'}">Chủ trọ</c:when>
                                            <c:when test="${account.role == 'manager'}">Quản lý</c:when>
                                            <c:when test="${account.role == 'tenant'}">Khách thuê</c:when>
                                            <c:when test="${account.role == 'guest'}">Khách xem</c:when>
                                            <c:otherwise>${account.role}</c:otherwise> 
                                        </c:choose>
                                    </td>
                                    <td>
                                        <label class="switch">
                                            <input type="checkbox" ${account.active ? 'checked' : ''} data-account-id="${account.ID_Account}"
                                            <c:if test="${sessionScope.role == 'landlord' || (sessionScope.role == 'manager' && (account.role == 'guest' || account.role == 'tenant'))}">
                                                enabled
                                            </c:if>
                                            <c:if test="${!(sessionScope.role == 'landlord' || (sessionScope.role == 'manager' && (account.role == 'guest' || account.role == 'tenant')))}">
                                                disabled
                                            </c:if>
                                            >
                                            <span class="slider"></span>
                                        </label>
                                    </td>
                                    <td class="actions">
                                        <c:choose>
                                            <c:when test="${sessionScope.role == 'landlord'}">
                                                <c:if test="${account.role != 'landlord'}">
                                                    <button onclick="window.location.href = 'accountController?action=edit&id=${account.ID_Account}'" class="edit">Chỉnh sửa</button>
                                                </c:if>
                                            </c:when>
                                            <c:when test="${sessionScope.role == 'manager'}">
                                                <c:if test="${account.role == 'guest' || account.role == 'tenant'}">
                                                    <button onclick="window.location.href = 'accountController?action=edit&id=${account.ID_Account}'" class="edit">Chỉnh sửa</button>
                                                </c:if>
                                            </c:when>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <div class="pagination">
                        <c:if test="${currentPage > 1}">
                            <button onclick="changePage(${currentPage - 1})">&laquo; Previous</button>
                        </c:if>

                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <c:choose>
                                <c:when test="${currentPage eq i}">
                                    <button class="active">${i}</button>
                                </c:when>
                                <c:otherwise>
                                    <button onclick="changePage(${i})">${i}</button>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>

                        <c:if test="${currentPage < totalPages}">
                            <button onclick="changePage(${currentPage + 1})">Next &raquo;</button>
                        </c:if>
                    </div>

                    <script>
                        function changePage(page) {
                            window.location.href = '?page=' + page;
                        }
                    </script>
                </div>
            </section>
        </div>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script>
            $(document).ready(function () {
                $('.switch input[type="checkbox"]').change(function () {
                    var accountId = $(this).data('account-id');
                    var isActive = this.checked;

                    $.ajax({
                        url: 'accountController',
                        type: 'POST',
                        data: {
                            action: 'toggleActive',
                            id: accountId,
                            isActive: isActive
                        },
                        success: function (response) {
                            console.log('Account status updated successfully');
                        },
                        error: function (xhr, status, error) {
                            console.error('Error updating account status');
                            $(this).prop('checked', !isActive);
                        }
                    });
                });
            });
        </script>
    </body>
</html>
