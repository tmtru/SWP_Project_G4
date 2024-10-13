function toggleServiceInputs(serviceName, servicePrice, checkbox) {
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
        updateInvoiceService(serviceName, servicePrice, checkbox.id);
    } else {
        if (serviceName === 'Điện' || serviceName === 'Nước') {
            clearInvoiceService(checkbox.id);
        } else {
            clearInvoiceService(checkbox.id);
        }
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



function updateInvoiceService(serviceName, servicePrice, inputElement) {
    var invoiceTable = document.getElementById("invoiceServicesBody");
    var serviceRow = document.getElementById("service_" + inputElement);

    

    // Tính toán giá trị cho dịch vụ dựa vào loại dịch vụ
    if (serviceName === 'Điện' || serviceName === 'Nước') {
        console.log("chiSoMoi_" + inputElement);
        var chiSoCu = document.getElementById("chiSoCu_" + inputElement).value;
        var chiSoMoi = document.getElementById("chiSoMoi_" + inputElement).value;
        if (chiSoCu && chiSoMoi && !isNaN(chiSoCu) && !isNaN(chiSoMoi)) {
            var soTien = (chiSoMoi - chiSoCu) * servicePrice;
            serviceRow.cells[1].innerText = soTien.toLocaleString() + " VND";
        }
        console.log(serviceName);
    } else 
    if (serviceName === 'Giá phòng') {
        var soTien = servicePrice;
        serviceRow.cells[1].innerText = soTien.toLocaleString() + " VND";
    } else {
        console.log(serviceName);
        var dauNguoi = document.getElementById("dauNguoiInput_" + inputElement).value;
        if (dauNguoi && !isNaN(dauNguoi)) {
            var soTien = dauNguoi * servicePrice;
            serviceRow.cells[1].innerText = soTien.toLocaleString() + " VND";
        }
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
    // Lấy tất cả các checkbox dịch vụ
    const dichVuCheckboxes = document.querySelectorAll('input[name="dichVuId"]');
    let isValid = true; // Biến kiểm tra tính hợp lệ
    let errorMessage = ""; // Biến chứa thông báo lỗi

    dichVuCheckboxes.forEach((checkbox) => {
        if (checkbox.checked) {
            // Lấy ID của dịch vụ
            const dichVuId = checkbox.value;

            // Lấy các trường chỉ số cũ và mới
            const chiSoCu = parseFloat(document.getElementById(`chiSoCu_dichVu_${dichVuId}`).value) || 0;
            const chiSoMoi = parseFloat(document.getElementById(`chiSoMoi_dichVu_${dichVuId}`).value) || 0;

            // Kiểm tra xem chỉ số cũ có lớn hơn chỉ số mới không
            if (chiSoCu > chiSoMoi) {
                errorMessage += `Chỉ số cũ không được lớn hơn chỉ số mới!\n`;
                isValid = false;
            }

            // Kiểm tra xem cả hai trường đều đã được nhập
            if (chiSoCu === 0 || chiSoMoi === 0) {
                errorMessage += `Bạn phải nhập cả chỉ số cũ và chỉ số mới cho dịch vụ!\n`;
                isValid = false;
            }
        }
    });

    // Cập nhật thông báo
    const notification = document.getElementById("notification");
    if (!isValid) {
        notification.innerText = errorMessage;
        notification.style.display = "block"; // Hiển thị thông báo
    } else {
        notification.style.display = "none"; // Ẩn thông báo nếu hợp lệ
    }

    return isValid; // Trả về true nếu tất cả đều hợp lệ, ngược lại false
}
