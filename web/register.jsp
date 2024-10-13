<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Create an Account</title>
        <link rel="stylesheet" href="css/styleRegister.css">
    </head>
    <body>
        <img src="assets/img/Web.png" alt="Logo" width="100" class="logo"> 
        <div class="container">
            <h2>Create an account</h2>

            <form action="register" method="post">
                <input type="text" name="username" class="input-field" placeholder="Enter your name" required>
                <input type="email" name="email" class="input-field" placeholder="Enter your Gmail" required>
                <input type="password" name="password" class="input-field" placeholder="Create a password" required>
                <input type="password" name="confirmPassword" class="input-field" placeholder="Confirm password" required>
                <label class="checkbox-label">
                    <input type="checkbox" required> By creating an account, you agree to the <a href="#">Terms of Use</a> and <a href="#">Privacy Policy</a>.
                </label>
                <button type="submit">Create an account</button>
            </form>

            <c:if test="${not empty errorMessage}">
                <div style="color: red;">${errorMessage}</div>
            </c:if>

            <footer>
                <p>Already have an account? <a href="login.jsp">Log in</a></p>
            </footer>
        </div>

        <script>
            function validateEmail(email) {
                const regex = /^[^\s@]+@gmail\.com$/; 
                return regex.test(email);
            }

            function validatePassword(password) {
                const regex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;
                return regex.test(password);
            }

            const emailInput = document.querySelector('input[type="email"]');
            emailInput.addEventListener('input', function() {
                if (!validateEmail(emailInput.value)) {
                    emailInput.setCustomValidity("Email không hợp lệ!");
                } else {
                    emailInput.setCustomValidity(""); 
                }
            });

            const passwordInput = document.querySelector('input[type="password"]');
            passwordInput.addEventListener('input', function() {
                if (!validatePassword(passwordInput.value)) {
                    passwordInput.setCustomValidity("Mật khẩu phải có ít nhất 8 ký tự và chứa cả chữ cái và số!");
                } else {
                    passwordInput.setCustomValidity(""); 
                }
            });
        </script>
    </body>
</html>
