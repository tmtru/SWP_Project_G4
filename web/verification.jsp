<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Email Verification</title>
        <link rel="stylesheet" href="assets/css/styleResetPassword.css">
    </head>
    <body>
        <div class="container">
            <h2>Verify Email</h2>
            <form action="sendemail" method="post">
                <label for="email">Enter your email address</label>
                <input type="email" id="email" name="email" class="input-field" placeholder="Email address" required>
                <button type="submit" class="send-code-btn">Send Code</button>
            </form>

            <div id="error-message">
                <c:if test="${not empty errorMessage}">
                    <p style="color: red;">${errorMessage}</p>
                </c:if>
            </div>
        </div>
    </body>
</html>
