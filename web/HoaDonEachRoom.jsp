<%-- 
    Document   : hoaDonManagement
    Created on : Oct 6, 2024, 3:15:36 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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
    <body>
        <jsp:include page="sidebarHoaDonManagement.jsp"></jsp:include>
            <section class="home">
                <section class="property-management">
                    <div class="header">
                        <h2>Danh sách hóa đơn nhà trọ</h2>

                    </div>

                    <nav>
                        <div class="warpper">
                        <c:forEach var="nt" items="${sessionScope.housesByRole}">
                            <a href="hoadon?id=${nt.ID_NhaTro}">
                                <div class="tab <c:if test="${nt.ID_NhaTro == sessionScope.currentHouse}">active</c:if>" id="tab-${nt.ID_NhaTro}" >

                                    ${nt.tenNhaTro}

                                </div>
                            </a>
                        </c:forEach>
                        <c:if test="${sessionScope.housesByRole==null}">
                            <div>Bạn không được quyền truy cập vào bất cứ nhà trọ nào</div>
                        </c:if>
                    </div>
                </nav>
            </section>

            <section class="ftco-section">

                <div class="row m-0">
                    <div class="my-4 ml-4 ">
                        <h4 class="">Thông tin hóa đơn phòng ${currentRoomOfHoaDon.tenPhongTro}</h4>
                        <a class="btn btn-success" href="loadHoaDonForm?roomId=${currentRoomOfHoaDon.ID_Phong}" >+ Thêm hóa đơn tiền phòng</a>

                    </div>
                    <div class="col-lg-10 mt-1 ml-2">
                        <!--                        <div class="row filter">
                        
                        
                        
                                                </div>-->
                        <div class="card">
                            <div class="card-header pb-0 p-3">
                                <h6 class="mb-0">Thông tin hóa đơn</h6>
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
                                    <c:forEach var="hd" items="${invoices}">
                                        <div class="mb-3">
                                            <li class="list-group-item list-hoadon d-flex p-4 bg-gray-100 border-radius-lg"
                                                style="border-left: 5px solid ${hd.trang_thai == 1 ? 'green' : 'red'}">

                                                <div class="d-flex flex-column">
                                                    <% 
                                                        // Lấy ID hóa đơn từ đối tượng hd bằng cách sử dụng EL
                                                        int idHoadon = (Integer) ((HoaDon)  pageContext.getAttribute("hd")).getID_HoaDon();
                                                        TransactionDAO tdao= new TransactionDAO();
                                                        float totalAmount=tdao.getTotalMoneyByIdHoaDon(idHoadon);
                                                        request.setAttribute("Paid", totalAmount);
                                                        // Lấy danh sách giao dịch cho hóa đơn hiện tại
                                                        List<Transaction> transactions = tdao.getTransactionsByIdHoaDon(idHoadon);
                                                        request.setAttribute("transactions", transactions);
                                                    %>
                                                    <div class="d-flex header-invoice">
                                                        <span class="badge
                                                              ${hd.trang_thai == 1 ? 'badge-success' : 'badge-danger'}" 
                                                              >
                                                            ${hd.trang_thai == 1 ? 'Đã thanh toán' : 'Chưa thanh toán'}
                                                        </span>
                                                    </div>
                                                    <span class="mb-2">Tổng tiền: 
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
                                                    <button class="icon-transaction border-0 border-radius-lg btn-info mt-2" type="button" data-toggle="collapse" data-target="#collapsehd${hd.ID_HoaDon}" aria-expanded="false" aria-controls="collapseOne">
                                                        <i class="fa-solid fa-circle-arrow-down"></i> <span class="text-xs"> Danh sách chuyển tiền</span>
                                                    </button>


                                                </div>
                                                <div class=" pl-5 dich-vu-invoice">
                                                    <h6>Dịch vụ sử dụng: </h6>
                                                    <div class="ml-3">
                                                        <c:forEach var="dichVu" items="${hd.dichVus}">
                                                            <div class="service-item text-xs mr-2 mb-2">
                                                                <span class="text-primary ms-sm-2 font-weight-bold">Tên dịch vụ: ${dichVu.tenDichVu}</span> <br/>

                                                                <c:choose>
                                                                    <c:when test="${dichVu.tenDichVu == 'Điện' || dichVu.tenDichVu == 'Nước'}">
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
                                                    <div class="mt-2">
                                                        <a class="btn btn-link text-success px-3 mb-0" href="TransactionForm.jsp?id=${hd.ID_HoaDon}"><i class="fa-solid fa-plus"></i>Thêm giao dịch</a>

                                                        <button type="button" class="btn btn-link text-danger text-gradient px-3 mb-0" data-toggle="modal" data-target="#myModalDelete${hd.ID_HoaDon}">
                                                            <i class="fa-solid fa-trash"></i>Delete
                                                        </button>
                                                        <div id="myModalDelete${hd.ID_HoaDon}" class="modal fade" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                                            <div class="modal-dialog modal-confirm-delete">
                                                                <div class="modal-content">
                                                                    <div class="modal-header flex-column">
                                                                        <div class="icon-box">
                                                                            <i class="material-icons"><i class="fa-solid fa-circle-xmark"></i></i>
                                                                        </div>
                                                                        <h5 class="modal-title w-100">Bạn có chắc chắn bạn muốn xóa hóa đơn ?<br/> <span style="color: #5932ea"></span></h5>
                                                                    </div>
                                                                    <div class="modal-footer justify-content-center">
                                                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
                                                                        <button type="button" class="btn btn-danger">
                                                                            <a href="actionhoadon?action=dele&id=${hd.ID_HoaDon}&exit=" class="edit-film" style="color: white !important;">Xóa hóa đơn</a>
                                                                        </button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <br/>

                                            </li>
                                            <div id="collapsehd${hd.ID_HoaDon}" class="collapse" >
                                                <ul class="list-group transaction">
                                                    <c:forEach var="tr" items="${transactions}">

                                                        <li class="list-group-item d-flex justify-content-between ps-0 border-radius-lg">
                                                            <div class="d-flex align-items-center">
                                                                <div class="icon-transaction" >
                                                                    <i class="fa-solid fa-circle-arrow-down"></i>
                                                                </div>
                                                                <div class="d-flex flex-column">
                                                                    <div class="d-flex align-items-center text-success text-gradient text-sm font-weight-bold">
                                                                        + <fmt:formatNumber value="${tr.amount}" type="number" groupingUsed="true"/> VND
                                                                    </div>
                                                                    <span class="text-xs">${tr.transaction}</span>
                                                                    <span class="mb-2 text-xs">Phương thức thanh toán: ${tr.paymentMethod}</span>
                                                                    <span class="mb-2 text-xs">Mã giao dịch: ${tr.maGiaoDich}</span>
                                                                    <span class="mb-2 text-xs">Mô tả: ${tr.moTa}</span>
                                                                </div>

                                                            </div>
                                                            <c:if test="${a!=null}">
                                                                <c:if test="${a.role.equals('landlord')}">
                                                                    <div class="ms-auto text-end">
                                                                        <button type="button" class="col-4 btn btn-link text-danger text-gradient px-3 mb-0" data-toggle="modal" data-target="#myModalDelete${tr.ID_Transaction}">
                                                                            <i class="fa-solid fa-trash"></i>Delete

                                                                        </button>
                                                                    </div>
                                                                    <div id="myModalDelete${tr.ID_Transaction}" class="modal fade" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                                                        <div class="modal-dialog modal-confirm-delete">
                                                                            <div class="modal-content">
                                                                                <div class="modal-header flex-column">
                                                                                    <div class="icon-box">
                                                                                        <i class="material-icons"><i class="fa-solid fa-circle-xmark"></i></i>
                                                                                    </div>
                                                                                    <h5 class="modal-title w-100">Bạn có chắc chắn bạn muốn xóa giao dịch ?<br/> <span style="color: #5932ea"></span></h5>
                                                                                </div>
                                                                                <div class="modal-footer justify-content-center">
                                                                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
                                                                                    <button type="button" class="btn btn-danger">
                                                                                        <a href="actionTransaction?action=dele&id=${tr.ID_Transaction}&exit=" class="edit-film" style="color: white !important;">Xóa giao dịch</a>
                                                                                    </button>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </c:if>
                                                            </c:if>



                                                        </li>

                                                    </c:forEach>
                                                </ul>
                                            </div>
                                        </div>
                                    </c:forEach>


                                </ul>
                            </div>
                        </div>
                    </div>
                </div>

            </section>
        </section>

        <!-- Bootstrap JS and dependencies -->

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
            const body = document.querySelector('body'),
                    sidebar = body.querySelector('nav'),
                    toggle = body.querySelector(".toggle"),
                    modeSwitch = body.querySelector(".toggle-switch"),
                    modeText = body.querySelector(".mode-text");
            // Check if dark mode is enabled on page load
            if (localStorage.getItem("darkMode") === "disabled") {
                body.classList.add("light");
                modeText.innerText = "Light mode";
            }

            // Sidebar toggle functionality
            toggle.addEventListener("click", () => {
                sidebar.classList.toggle("close");
            });
            // Dark mode toggle functionality
            modeSwitch.addEventListener("click", () => {
                body.classList.toggle("dark");
                // Update the text for dark mode
                if (body.classList.contains("dark")) {
                    modeText.innerText = "Light mode";
                    localStorage.setItem("darkMode", "enabled");
                } else {
                    modeText.innerText = "Dark mode";
                    localStorage.setItem("darkMode", "disabled");
                }
            });
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
                // Lấy giá trị từ JSP
                var startDate = '<%= request.getAttribute("startDate") %>';
                var endDate = '<%= request.getAttribute("endDate") %>';

                // Khởi tạo biến start và end với giá trị mặc định
                var start = moment();
                var end = moment();

                // Kiểm tra và khởi tạo moment chỉ khi giá trị hợp lệ
                if (startDate && startDate !== 'null' && startDate.trim() !== '') {
                    start = moment(startDate, 'YYYY-MM-DD'); // Định dạng mà bạn đã sử dụng
                } else {
                    // Nếu không có startDate, đặt start về ngày hiện tại
                    start = moment();
                }

                if (endDate && endDate !== 'null' && endDate.trim() !== '') {
                    end = moment(endDate, 'YYYY-MM-DD'); // Định dạng mà bạn đã sử dụng
                } else {
                    // Nếu không có endDate, đặt end về ngày hiện tại
                    end = moment();
                }

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

                // Gọi hàm callback với giá trị khởi tạo
                cb(start, end);

                $('#reportrange').on('apply.daterangepicker', function (ev, picker) {
                    $('#dateRangeForm').submit(); // Gửi form đến servlet
                });
            });
        </script>


    </body>
</html>

