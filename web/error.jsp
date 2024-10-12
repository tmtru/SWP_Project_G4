<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error Page</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <div class="row">
            <div class="col-md-6 offset-md-3 text-center">
                <h1 class="display-4">Oops!</h1>
                <h2 class="mb-4">Đã xảy ra lỗi</h2>
                
                <div class="alert alert-danger">
                    <%-- Check if there's a specific error message --%>
                    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
                    <p><%= errorMessage != null ? errorMessage : "Đã xảy ra lỗi không mong muốn. Vui lòng thử lại sau." %></p>
                </div>
                
                <div class="mt-4">
                    <a href="home" class="btn btn-primary mr-2">Về trang chủ</a>
                    <button onclick="history.back()" class="btn btn-secondary">Quay lại trang trước</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>