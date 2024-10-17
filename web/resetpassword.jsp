<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Reset Password</title>
        <link rel="stylesheet" href="css/styleResetPassword.css">
    </head>
    <body>
        <div class="container">
            <h2>Reset Password</h2>
            <form action="resetpassword" method="post">
                <label for="new-password">Enter your new password</label>
                <input type="password" id="new-password" name="newPassword" class="input-field" placeholder="New password" required>

                <label for="confirm-password">Confirm your new password</label>
                <input type="password" id="confirm-password" name="confirmPassword" class="input-field" placeholder="Confirm password" required>

                <button type="submit" class="reset-password-btn">Reset Password</button>
            </form>

            <div id="error-message">
                <c:if test="${not empty errorMessage}">
                    <p style="color: red;">${errorMessage}</p>
                </c:if>
            </div>
        </div>
        <script>
            function validatePassword(password) {
                const regex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;
                return regex.test(password);
            }

            const passwordInput = document.querySelector('input[type="password"]');
            passwordInput.addEventListener('input', function () {
                if (!validatePassword(passwordInput.value)) {
                    passwordInput.setCustomValidity("Mật khẩu phải có ít nhất 8 ký tự và chứa cả chữ cái và số!");
                } else {
                    passwordInput.setCustomValidity("");
                }
            });
        </script>
    </body>
</html>
