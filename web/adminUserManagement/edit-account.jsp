<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8"/>
        <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
        <title>Edit Account</title>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet"/>
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet"/>
        <link href="${pageContext.request.contextPath}/css/css-account-management.css" rel="stylesheet" />
        <style>
            .form-group {
                margin-bottom: 15px;
            }
            .form-group label {
                display: block;
                margin-bottom: 5px;
            }
            .form-group input, .form-group select {
                width: 100%;
                padding: 8px;
                border: 1px solid #ddd;
                border-radius: 4px;
            }
            .form-group button {
                background-color: #5a67d8;
                color: white;
                border: none;
                padding: 10px 15px;
                border-radius: 4px;
                cursor: pointer;
            }
            .form-group button:hover {
                background-color: #4c51bf;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <jsp:include page="sidebar.jsp"></jsp:include>
                <section class="home mx-5">
                    <div class="main-content">
                        <h2>Edit Account</h2>
                        <form action="accountController" method="post">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="id" value="${account.idAccount}">
                        <div class="form-group">
                            <label for="username">Username:</label>
                            <input type="text" id="username" name="username" value="${account.username}" required>
                        </div>
                        <div class="form-group">
                            <label for="email">Email:</label>
                            <input type="email" id="email" name="email" value="${account.email}" required>
                        </div>
                        <div class="form-group">
                            <label for="password">Password:</label>
                            <input type="password" id="password" name="password" value="${account.password}" required>
                        </div>
                        <div class="form-group">
                            <label for="role">Role:</label>
                            <select id="role" name="role">
                                <option value="User" ${account.role == 'User' ? 'selected' : ''}>User</option>
                                <option value="Admin" ${account.role == 'Admin' ? 'selected' : ''}>Admin</option>
                                <option value="Manager" ${account.role == 'Manager' ? 'selected' : ''}>Manager</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="isActive">Active:</label>
                            <select id="isActive" name="isActive">
                                <option value="true" ${account.active ? 'selected' : ''}>Yes</option>
                                <option value="false" ${!account.active ? 'selected' : ''}>No</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <button type="submit">Update Account</button>
                        </div>
                    </form>
                </div>
            </section>
        </div>
    </body>
</html>