function toggleServiceInputs(serviceName, donvi, servicePrice, checkbox) {
    var chiSoGroup = document.getElementById("chiSo_" + checkbox.value);
    var dauNguoiGroup = document.getElementById("dauNguoi_" + checkbox.value);

    // Hiển thị hoặc ẩn các ô nhập liệu dựa vào loại dịch vụ và trạng thái của checkbox
    if (checkbox.checked) {
        var invoiceTable = document.getElementById("invoiceServicesBody");
        var serviceRow = document.getElementById("service_" + checkbox.id);

        // Nếu dịch vụ chưa có dòng trong hóa đơn, tạo mới
        if (!serviceRow) {
            serviceRow = document.createElement("tr");
            serviceRow.setAttribute("id", "service_" + checkbox.id);

            var nameCell = document.createElement("td");
            nameCell.innerText = serviceName;

            var priceCell = document.createElement("td");
            priceCell.classList.add("text-right");

            serviceRow.appendChild(nameCell);
            serviceRow.appendChild(priceCell);

            invoiceTable.appendChild(serviceRow);
        }
        updateInvoiceService(serviceName, donvi, servicePrice, checkbox.id);
    } else {

        clearInvoiceService(checkbox.id);
    }
    console.log(checkbox.id);
    // Cập nhật hóa đơn nếu cần

}
function toggleInvoiceRoom(checkbox) {
    console.log(checkbox);
    var roomSelect = document.getElementById("phong");
    var selectedOption = roomSelect.options[roomSelect.selectedIndex];
    var roomPrice = parseFloat(selectedOption.getAttribute("data-price")); // Lấy giá phòng từ data-price
    var roomName = selectedOption.text; // Lấy tên phòng
    console.log(roomName);

    var invoiceTable = document.getElementById("invoiceServicesBody");
    var serviceRow = document.getElementById("service_room"); // ID hàng phòng

    if (checkbox.checked) {
        // Nếu checkbox được tick và hàng chưa có
        if (!serviceRow) {
            serviceRow = document.createElement("tr");
            serviceRow.setAttribute("id", "service_room");

            var nameCell = document.createElement("td");
            nameCell.innerText = "Tiền phòng: " + roomName;

            var priceCell = document.createElement("td");
            priceCell.classList.add("text-right");
            priceCell.innerText = roomPrice.toLocaleString() + " VND"; // Định dạng giá

            serviceRow.appendChild(nameCell);
            serviceRow.appendChild(priceCell);
            invoiceTable.appendChild(serviceRow);
        } else {
            invoiceTable.removeChild(serviceRow);
            serviceRow = document.createElement("tr");
            serviceRow.setAttribute("id", "service_room");

            var nameCell = document.createElement("td");
            nameCell.innerText = "Tiền phòng: " + roomName;

            var priceCell = document.createElement("td");
            priceCell.classList.add("text-right");
            priceCell.innerText = roomPrice.toLocaleString() + " VND"; // Định dạng giá

            serviceRow.appendChild(nameCell);
            serviceRow.appendChild(priceCell);
            invoiceTable.appendChild(serviceRow);
        }
    } else {
        // Nếu checkbox không được chọn, xóa hàng tiền phòng
        if (serviceRow) {
            invoiceTable.removeChild(serviceRow);
        }
    }

    // Cập nhật tổng tiền
    updateTotalPrice();
}



function updateInvoiceService(serviceName, donvi, servicePrice, inputElement) {
    var invoiceTable = document.getElementById("invoiceServicesBody");
    var serviceRow = document.getElementById("service_" + inputElement);



    // Tính toán giá trị cho dịch vụ dựa vào loại dịch vụ
    if (donvi === 'Tháng') {
        console.log(serviceName);
        var dauNguoi = document.getElementById("dauNguoiInput_" + inputElement).value;
        if (dauNguoi && !isNaN(dauNguoi)) {
            var soTien = dauNguoi * servicePrice;
            serviceRow.cells[1].innerText = soTien.toLocaleString() + " VND";
        }
    } else
    if (serviceName === 'Giá phòng') {
        var soTien = servicePrice;
        serviceRow.cells[1].innerText = soTien.toLocaleString() + " VND";
    } else {

        console.log("chiSoMoi_" + inputElement);
        var chiSoCu = document.getElementById("chiSoCu_" + inputElement).value;
        var chiSoMoi = document.getElementById("chiSoMoi_" + inputElement).value;
        if (chiSoCu && chiSoMoi && !isNaN(chiSoCu) && !isNaN(chiSoMoi)) {
            var soTien = (chiSoMoi - chiSoCu) * servicePrice;
            serviceRow.cells[1].innerText = soTien.toLocaleString() + " VND";
        }
        console.log(donvi);
    }

    // Cập nhật tổng tiền
    updateTotalPrice();
}

// Xóa dịch vụ khỏi bảng hóa đơn khi bỏ chọn checkbox
function clearInvoiceService(serviceId) {
    var serviceRow = document.getElementById("service_" + serviceId).remove();


    // Cập nhật tổng tiền
    updateTotalPrice();
}

// Hàm để tính toán và cập nhật tổng tiền
function updateTotalPrice() {
    var total = 0;
    var serviceRows = document.querySelectorAll("#invoiceServicesBody tr");

    // Tính tổng giá trị các dịch vụ trong bảng
    serviceRows.forEach(function (row) {
        var priceText = row.cells[1].innerText.replace(" VND", "").replace(/,/g, "");
        total += parseFloat(priceText);
    });

    // Hiển thị tổng tiền
    document.getElementById("totalPrice").innerText = total.toLocaleString() + " VND";
}
function updateInvoiceOnRoomChange() {
    var roomSelect = document.getElementById("phong");
    var selectedRoomId = roomSelect.value;
    var roomName = roomSelect.options[roomSelect.selectedIndex].text;
    var roomPrice = parseFloat(roomSelect.options[roomSelect.selectedIndex].getAttribute("data-price")); // Lấy giá phòng

    var checkbox = document.getElementById("tinhTienPhong"); // ID của checkbox tiền phòng
    if (checkbox.checked) {
        toggleInvoiceRoom(checkbox);

    }
}
//valid data form
function validateForm12() {
    const dichVuCheckboxes = document.querySelectorAll('input[name="dichVuId"]');
    let isValid = true; // Khởi tạo là true
    let errorMessage = "";

    dichVuCheckboxes.forEach((checkbox) => {
        if (checkbox.checked) {
            const dichVuId = checkbox.value;

            // Khai báo biến
            let chiSoCu, chiSoMoi, dauNguoi;
            const chiSoCuInput = document.getElementById(`chiSoCu_dichVu_${dichVuId}`);
            const chiSoMoiInput = document.getElementById(`chiSoMoi_dichVu_${dichVuId}`);
            const dauNguoiInput = document.getElementById(`dauNguoiInput_dichVu_${dichVuId}`);

            // Kiểm tra chỉ số cũ và chỉ số mới
            if (chiSoCuInput !== null) {
                if (chiSoCuInput.value !== "") {
                    chiSoCu = parseFloat(chiSoCuInput.value);
                } else {
                    isValid = false; // Nếu không có giá trị, không hợp lệ
                    chiSoCuInput.classList.add("is-invalid");
                }

                if (chiSoMoiInput.value !== "") {
                    chiSoMoi = parseFloat(chiSoMoiInput.value);
                } else {
                    isValid = false; // Nếu không có giá trị, không hợp lệ
                    chiSoMoiInput.classList.add("is-invalid");
                }

                // Kiểm tra nếu chỉ số cũ lớn hơn chỉ số mới
                if (chiSoCu > chiSoMoi) {
                    errorMessage += `Chỉ số cũ không được lớn hơn chỉ số mới cho dịch vụ ${dichVuId}!\n`;
                    isValid = false;
                    chiSoCuInput.classList.add("is-invalid");
                    chiSoMoiInput.classList.add("is-invalid");
                } else {
                    chiSoCuInput.classList.remove("is-invalid");
                    chiSoMoiInput.classList.remove("is-invalid");
                }
            } else {
                // Nếu không có trường chỉ số cũ và mới, kiểm tra đầu người
                if (dauNguoiInput !== null) {
                    if (dauNguoiInput.value !== "") {
                        dauNguoi = parseFloat(dauNguoiInput.value);
                    } else {
                        isValid = false; // Nếu không có giá trị, không hợp lệ
                        dauNguoiInput.classList.add("is-invalid");
                    }
                }

                // Kiểm tra đầu người phải lớn hơn 0
                if (dauNguoi <= 0) {
                    errorMessage += `Đầu người phải lớn hơn 0 cho dịch vụ ${dichVuId}!\n`;
                    isValid = false;
                    dauNguoiInput.classList.add("is-invalid");
                } else {
                    dauNguoiInput.classList.remove("is-invalid");
                }
            }
        }
    });

    // Hiển thị thông báo nếu có lỗi
    const notification = document.getElementById("notification");
    if (!isValid) {
        notification.innerText = errorMessage;
        notification.style.display = "block";
    } else {
        notification.style.display = "none";
    }

    return isValid; // Trả về giá trị của isValid
}





