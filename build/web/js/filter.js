$(document).ready(function () {
    // Hàm để gửi yêu cầu AJAX
    function sendAjaxRequest(sortBy, order) {
        var selectedPrices = [];
        var selectedAreas = [];

        // Lấy các checkbox đã chọn cho diện tích
        $('input[name="area"]:checked').each(function () {
            selectedAreas.push($(this).val());
        });

        // Lấy các checkbox đã chọn cho giá
        $('input[name="price"]:checked').each(function () {
            selectedPrices.push($(this).val());
        });

        var status = $('.tab-item-active').data('status');

        // Gửi dữ liệu qua Ajax đến server
        $.ajax({
            type: "GET",
            url: "filterrooms", 
            data: {
                price: selectedPrices,
                area: selectedAreas,
                status: status,
                sortBy: sortBy,
                order: order
            },
            traditional: true, 
            success: function (response) {
                $('#roomsContainer').html(response);
            },
            error: function (xhr, status, error) {
                console.error("Có lỗi xảy ra: " + error);
            }
        });
    }

    // Khi người dùng thay đổi trạng thái của checkbox price và area
    $('input[type="checkbox"]').on('change', function () {
        var sortBy = $('.dropdown-item.active').data('sort'); // Lấy sortBy từ phần tử active
        var order = $('.sort-button.active').data('order') || 'desc'; // Lấy order từ phần tử active

        sendAjaxRequest(sortBy, order); 
    });

    // Khi người dùng nhấn vào các tab-item
    $('.tab-item').on('click', function () {
        $('.tab-item').removeClass('tab-item-active');
        $(this).addClass('tab-item-active');
        var sortBy = $('.dropdown-item.active').data('sort'); // Lấy sortBy từ phần tử active
        var order = $('.sort-button.active').data('order') || 'desc'; // Lấy order từ phần tử active
        sendAjaxRequest(sortBy, order); 
    });

    // Xử lý sự kiện khi người dùng chọn sắp xếp từ dropdown
    $('.dropdown-item').on('click', function () {
        var sortBy = $(this).data('sort'); 
        var order = $('.sort-button.active').data('order') || 'desc'; 
        var selectedText = $(this).text();

        // Cập nhật văn bản của dropdown
        $('#dropdownMenuButton').text(selectedText);
        
        // Cập nhật trạng thái active
        $('.dropdown-item').removeClass('active'); 
        $(this).addClass('active'); 

        sendAjaxRequest(sortBy, order); 
    });

    // Xử lý sự kiện khi người dùng nhấn vào các nút sắp xếp
    $('.sort-button').on('click', function () {
        $('.sort-button').removeClass('active'); 
        $(this).addClass('active'); // Thêm lớp active vào nút được nhấn
        var sortBy = $('.dropdown-item.active').data('sort'); // Lấy sortBy từ phần tử active
        var order = $(this).data('order'); 
        sendAjaxRequest(sortBy, order); 
    });
});
