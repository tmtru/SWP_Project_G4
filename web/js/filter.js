$(document).ready(function () {
    // Hàm để gửi yêu cầu AJAX
    function sendAjaxRequest(sortBy, order) {
        var selectedPrices = [];
        var selectedAreas = [];
        $('input[name="area"]:checked').each(function () {
            selectedAreas.push($(this).val());
        });

        $('input[name="price"]:checked').each(function () {
            selectedPrices.push($(this).val());
        });

        var status = $('.tab-item-active').data('status');
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

    $('input[type="checkbox"]').on('change', function () {
        var sortBy = $('.dropdown-item.active').data('sort');
        var order = $('.sort-button.active').data('order') || 'desc';

        sendAjaxRequest(sortBy, order);
    });


    $('.tab-item').on('click', function () {
        $('.tab-item').removeClass('tab-item-active');
        $(this).addClass('tab-item-active');
        var sortBy = $('.dropdown-item.active').data('sort');
        var order = $('.sort-button.active').data('order') || 'desc';
        sendAjaxRequest(sortBy, order);
    });


    $('.dropdown-item').on('click', function () {
        var sortBy = $(this).data('sort');
        var order = $('.sort-button.active').data('order') || 'desc';
        var selectedText = $(this).text();


        $('#dropdownMenuButton').text(selectedText);
        $('.dropdown-item').removeClass('active');
        $(this).addClass('active');

        sendAjaxRequest(sortBy, order);
    });


    $('.sort-button').on('click', function () {
        $('.sort-button').removeClass('active');
        $(this).addClass('active');
        var sortBy = $('.dropdown-item.active').data('sort');
        var order = $(this).data('order');
        sendAjaxRequest(sortBy, order);
    });

    //lam viec voi coookie like
    
    
});
