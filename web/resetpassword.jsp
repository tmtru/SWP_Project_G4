<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Reset Password</title>
        <link rel="stylesheet" href="css/styleResetPassword.css">
        <script src="js/utils.js"></script>
    </head>
    <body>
        <div class="container">
            <h2>Reset Password</h2>
            <form action="resetpassword" method="post">
                <label for="email">Enter your email address</label>
                <input type="email" id="email" name="email" class="input-field" placeholder="Email address" required>

                <label for="new-password">Enter your new password</label>
                <input type="password" id="new-password" name="newPassword" class="input-field" placeholder="New password" required oninput="checkPassword()">

                <label for="confirm-password">Confirm your new password</label>
                <input type="password" id="confirm-password" name="confirmPassword" class="input-field" placeholder="Confirm password" required>

                <button type="submit" class="reset-password-btn">Reset Password</button>
            </form>

            <div id="error-message">
                <c:if test="${not empty errorMessage}">
                    <p style="color: red;">${errorMessage}</p>
                </c:if>
            </div>

            <footer>
                <p>Remembered your password? <a href="login.jsp">Log in</a></p>
            </footer>
        </div>
    </body>
</html>

