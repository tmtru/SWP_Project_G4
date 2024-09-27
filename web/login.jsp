<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login Page</title>
        <link rel="stylesheet" href="assets/css/styleLogin.css">
    </head>
    <body>
        <img src="assets/images/Web.png" alt="Logo" width="100" class="logo">
        <div class="container">
            <h2>Log in</h2>
            <button class="social-login">
                <img src="assets/images/Google.jpg" alt="Google" width="20">
                <a href="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:9999/WebApplication1/logingoogle&response_type=code&client_id=973218303475-v2dh2eglhdv165jlcln5esv1je0c8j2b.apps.googleusercontent.com&approval_prompt=force">Continue with Google</a>
            </button>
            <button class="social-login">
                <img src="assets/images/Facebook.jpg" alt="Facebook" width="20">
                Continue with Facebook
            </button>
            <button class="social-login">
                <img src="assets/images/Apple.jpg" alt="Apple" width="20">
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
                <a href="verification.jsp">Forgot your password?</a>
            </label>

            <footer>
                <p>Don't have an account? <a href="register.jsp">Sign up</a></p>
            </footer>
        </div>
    </body>
</html>

