function validateEmail(email) {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // Định dạng email
    return regex.test(email);
}

function validatePassword(password) {
    // Định dạng password: ít nhất 8 ký tự, bao gồm chữ cái và số
    const regex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;
    return regex.test(password);
}

function checkEmail() {
    const emailInput = document.querySelector('input[type="email"]');
    if (!validateEmail(emailInput.value)) {
        emailInput.setCustomValidity("Email không hợp lệ!");
    } else {
        emailInput.setCustomValidity(""); // Đặt lại thông báo
    }
}

function checkPassword() {
    const passwordInput = document.querySelector('input[type="password"]');
    if (!validatePassword(passwordInput.value)) {
        passwordInput.setCustomValidity("Mật khẩu phải có ít nhất 8 ký tự và chứa cả chữ cái và số!");
    } else {
        passwordInput.setCustomValidity(""); // Đặt lại thông báo
    }
}
