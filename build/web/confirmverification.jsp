<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Email Verification</title>
        <link rel="stylesheet" href="css/styleResetPassword.css">
    </head>
    <body>
        <div class="container">
            <h2>Verify Email</h2>
            <form action="verification" method="post">
                <label for="verify-code">Enter verification code</label>
                <input type="text" id="verify-code" name="verifyCode" class="input-field" placeholder="Verification Code" required>
                <button type="submit" class="submit-btn">Submit</button>
            </form>

            <div id="error-message">
                <c:if test="${not empty errorMessage}">
                    <p style="color: red;">${errorMessage}</p>
                </c:if>
            </div>
        </div>
    </body>
</html>