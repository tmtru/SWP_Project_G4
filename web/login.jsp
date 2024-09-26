<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login Page</title>
        <link rel="stylesheet" href="css/styleLogin.css">
    </head>
    <body>
        <img src="assets/img/Web.png" alt="Logo" width="100" class="logo">
        <div class="container">
            <h2>Log in</h2>
            <button class="social-login">
                <img src="assets/img/Google.jpg" alt="Google" width="20">
                Continue with Google
            </button>
            <button class="social-login">
                <img src="assets/img/Facebook.jpg" alt="Facebook" width="20">
                Continue with Facebook
            </button>
            <button class="social-login">
                <img src="assets/img/Apple.jpg" alt="Apple" width="20">
                Continue with Apple
            </button>
            <p>OR</p>

            <form action="login" method="post">
                <input type="text" class="input-field" name="username" placeholder="User name" required>
                <input type="password" class="input-field" name="password" placeholder="Password" required>
                <button type="submit" class="login">Log in</button>
            </form>

            <c:if test="${not empty errorMessage}">
                <div style="color: red;">${errorMessage}</div>
            </c:if>

            <label>
                <span>
                    <input type="checkbox" name="remember"> Remember me
                </span>
                <a href="resetpassword.jsp">Forgot your password?</a>
            </label>

            <footer>
                <p>Don't have an account? <a href="register.jsp">Sign up</a></p>
            </footer>
        </div>
    </body>
</html>

