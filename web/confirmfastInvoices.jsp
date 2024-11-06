<%-- 
    Document   : hoaDonManagement
    Created on : Oct 6, 2024, 3:15:36 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.List, java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@page import="java.util.List, java.util.ArrayList,java.text.DecimalFormat, model.DichVu, model.Phong, dal.DichVuDAO, dal.PhongDAO, dal.HoaDonDAO, model.HoaDon, model.DichVu, model.Transaction, dal.TransactionDAO"%>
<!DOCTYPE html>

<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- Bootstrap CSS -->


        <!----======== CSS ======== -->
        <link rel="stylesheet" href="css/styleRoom.css">
        <link rel="stylesheet" href="css/modelDelete.css">
        <script src="https://kit.fontawesome.com/aab0c35bef.js" crossorigin="anonymous"></script>

        <!--Date Range Picker-->

        <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />


        <!----===== Boxicons CSS ===== -->
        <link rel="stylesheet" href="css/open-iconic-bootstrap.min.css">
        <link href='https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css' rel='stylesheet'>
        <!-- Nucleo Icons -->
        <link href="css/nucleo-icons.css" rel="stylesheet" />
        <link href="css/nucleo-svg.css" rel="stylesheet" />
        <!-- Font Awesome Icons -->
        <script src="https://kit.fontawesome.com/42d5adcbca.js" crossorigin="anonymous"></script>

        <!-- CSS Files -->
        <link href="css/styleHoaDon.css" rel="stylesheet" />

        <!--jquery-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <!--datepicker boostrap-->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.min.css">



    </head>
    <style>
        /* Increase checkbox size */
        #roomSelectionForm .form-check-input {
            width: 1.5em;
            height: 1.5em;
        }

        /* Optional: Increase the label font size for readability */
        #roomSelectionForm .form-check-label {
            font-size: 1.1em;
            margin-left: 0.5em;
        }
    </style>

    <body>
        <jsp:include page="sidebarHoaDonManagement.jsp"></jsp:include>
            <section class="home container">

            <c:set var="a" value="${sessionScope.account}"></c:set>

                <section class="ftco-section container">

                    <div class="row m-0">
                        <div class="col-lg-7 mt-4 ml-2">

                        </div>
                        <div class="card">
                            <div class="card-header pb-0 p-3">
                                <h6 class="mb-0">Xác nhận tất cả hóa đơn trước khi tạo</h6>
                            <c:if test="${not empty notification}">
                                <div class="alert alert-success" role="alert">
                                    ${notification}
                                </div>
                            </c:if>
                            <c:if test="${not empty errorMessage}">
                                <div class="alert alert-danger" role="alert">
                                    ${errorMessage}
                                </div>
                            </c:if>
                        </div>

                        <div class="card-body pt-4 p-3">
                            <ul class="list-group">
                                <% HoaDonDAO hdd= new HoaDonDAO();
                                %>


                                <c:forEach var="invoiceMap" items="${sessionScope.invoicesToConfirm}">
                                    <c:forEach var="entry" items="${invoiceMap}">
                                        <c:set var="hd" value="${entry.key}" />
                                        <c:set var="dichVuList" value="${entry.value}" />

                                        <li class="list-group-item list-hoadon d-flex p-4 mb-2 bg-gray-100 border-radius-lg"
                                            style="border-left: 5px solid ${hd.trang_thai == 1 ? 'green' : 'red'}">
                                            <div class="d-flex flex-column">
                                                <% 
                                                    int idHoadon = (Integer) ((HoaDon)  pageContext.getAttribute("hd")).getID_HoaDon();                             
                                                    Phong room = hdd.getRoomOfHoaDon(idHoadon);
                                                    if (room != null) {
                                                        request.setAttribute("room", room);
                                                    }
                                                %>
                                                <div class="d-flex header-invoice">
                                                    <h6 class="mb-3 text-sm">
                                                        ${hd.moTa}

                                                    </h6>
                                                    <span class="badge ml-5
                                                          ${hd.trang_thai == 1 ? 'badge-success' : 'badge-danger'}" 
                                                          >
                                                        ${hd.trang_thai == 1 ? 'Đã thanh toán' : 'Chưa thanh toán'}
                                                    </span>
                                                </div>
                                                <span class="mb-2 text-xs">Tổng tiền: 
                                                    <span class="text-dark ms-sm-2 font-weight-bold">
                                                        <fmt:formatNumber value="${hd.tong_gia_tien}" type="number" groupingUsed="true"/> VND
                                                    </span>
                                                </span>
                                                <span class="text-xs">Người tạo: <span class="text-dark ms-sm-2 font-weight-bold">${hd.nguoiTao}</span></span>

                                                <span class="text-xs">Đã thanh toán: 
                                                    <span class="text-dark ms-sm-2 font-weight-bold">
                                                        <fmt:formatNumber value="${Paid}" type="number" groupingUsed="true"/> VND
                                                    </span>
                                                </span>
                                                <span class="text-xs">Mo tả: <span class="text-dark ms-sm-2 font-weight-bold">${hd.moTa}</span></span>


                                            </div>
                                            <div class=" pl-5 dich-vu-invoice">
                                                <h6>Dịch vụ sử dụng: </h6>
                                                <div class="ml-3">
                                                    <c:forEach var="dichVu" items="${dichVuList}">
                                                        <div class="service-item text-xs mr-2 mb-2">
                                                            <span class="text-primary ms-sm-2 font-weight-bold">Tên dịch vụ: ${dichVu.tenDichVu}</span> <br/>

                                                            <c:choose>
                                                                <c:when test="${dichVu.don_vi != 'Tháng'}">
                                                                    <span class="text-dark ms-sm-2">
                                                                        <span class="text-dark ms-sm-2 font-weight-bold">Chỉ số:</span> 
                                                                        ${dichVu.chiSoCu} - ${dichVu.chiSoMoi}
                                                                    </span>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <span class="text-dark ms-sm-2">
                                                                        <span class="text-dark ms-sm-2 font-weight-bold">Đầu người:</span> 
                                                                        ${dichVu.dauNguoi}
                                                                    </span>
                                                                </c:otherwise>
                                                            </c:choose>

                                                        </div>

                                                    </c:forEach>
                                                </div>

                                            </div>
                                            <div class="ms-auto text-end">
                                                <div class="text-right">
                                                    <span class="mb-2 text-xs">Ngày tạo: <span class="text-dark font-weight-bold ms-sm-2">${hd.ngay}</span></span>
                                                </div>
                                            </div>
                                        </li>

                                    </c:forEach>    
                                </c:forEach>


                            </ul>
                            <div class="button">
                                <form action="addfastinvoice" method="post">
                                    <!-- Checkbox for Room Rent Inclusion -->
                                    <input type="hidden" name="step" value="3">
                                    <div class="text-center mb-2">
                                        <button type="submit" class="btn btn-success">Tạo tất cả hóa đơn</button>
                                        <a href="hoadon" class="btn btn-danger">Hủy thêm hóa đơn</a>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

                </div>

            </section>
        </section>

        <!--Date Range Picker-->
        <script type="text/javascript" src="https://cdn.jsdelivr.net/jquery/latest/jquery.min.js"></script>
        <script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
        <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>
        <script>
            $("#dpMonths").datepicker({
                format: "mm-yyyy",
                viewMode: "months",
                minViewMode: "months"
            });
        </script>
        <script>
            let allSelected = false;

            function toggleSelectAll() {
                const checkboxes = document.querySelectorAll('#roomSelectionForm .form-check-input');
                allSelected = !allSelected;
                checkboxes.forEach((checkbox) => {
                    checkbox.checked = allSelected;
                });
            }

        </script>

        <script>
            function updateIsActive(checkbox) {
                const idDichVu = $(checkbox).data("id"); // Lấy ID dịch vụ từ data-id
                const isActive = checkbox.checked; // Trạng thái mới của checkbox

                // Gọi AJAX để cập nhật trạng thái isActive
                $.ajax({
                    url: 'action?action=update',
                    type: 'POST',
                    data: {id: idDichVu, isActive: true}, // Gửi ID và trạng thái isActive
                    success: function (response) {
                        console.log('Cập nhật thành công:', response);
                        location.reload(); // Tải lại trang
                    },
                    error: function (xhr, status, error) {
                        console.error('Có lỗi xảy ra:', error);
                    }
                });
            }
        </script>
        <script type="text/javascript">
            $(function () {
                // Lấy giá trị từ JSP và khởi tạo moment
                var startDate = '<%= request.getAttribute("startDate") %>';
                var endDate = '<%= request.getAttribute("endDate") %>';

                // Sử dụng moment để khởi tạo các biến start và end
                var start = moment(startDate);
                var end = moment(endDate);

                function cb(start, end) {
                    $('#reportrange span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
                    $('#startDate').val(start.format('YYYY-MM-DD'));
                    $('#endDate').val(end.format('YYYY-MM-DD'));
                }

                $('#reportrange').daterangepicker({
                    startDate: start,
                    endDate: end,
                    ranges: {
                        'Hôm nay': [moment(), moment()],
                        'Hôm qua': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                        '7 Ngày trước': [moment().subtract(6, 'days'), moment()],
                        '30 Ngày trước': [moment().subtract(29, 'days'), moment()],
                        'Tháng này': [moment().startOf('month'), moment().endOf('month')],
                        'Tháng trước': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
                    }
                }, cb);

                cb(start, end);
                $('#reportrange').on('apply.daterangepicker', function (ev, picker) {
                    $('#dateRangeForm').submit(); // Gửi form đến servlet
                });
            });
        </script>

    </body>
</html>
