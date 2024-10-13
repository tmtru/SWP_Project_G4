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

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
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

                    <div class="col-lg-11 mt-4 ml-5">

                        <div class="transactions mt-3">
                            <div class="card h-100 mb-4">
                                <div class="card-header pb-0 p-3">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <h6 class="mb-0">Lịch sử thanh toán</h6>
                                            <c:if test="${not empty notification}">
                                                <div class="alert alert-success mt-2" role="alert">
                                                    ${notification}
                                                </div>
                                            </c:if>
                                        </div>
                                        <div class="col-md-6 d-flex justify-content-start justify-content-md-end align-items-center">
                                            <i class="fa-solid fa-calendar-days"></i>
                                        </div>
                                    </div>
                                </div>
                                <div class="card-body pt-4 p-3">

                                    <ul class="list-group transaction">
                                        <% 
                   // Retrieve existing query parameters
                   String queryString = request.getQueryString();
                   if (queryString == null) {
                       queryString = "";
                   } else {
                       // Remove any existing page parameter from the query string
                       queryString = queryString.replaceAll("&?page=\\d*", "");
                       if (!queryString.isEmpty() && !queryString.endsWith("&")) {
                           queryString += "&";
                       }
                   }
                   request.setAttribute("queryString",queryString );
                                        %>


                                        <c:set var="page" value="${param.page != null ? param.page : 0}" />
                                        <c:set var="items" value="0" />
                                        <c:set var="limitItems" value="10" />
                                        <c:set var="startItem" value="${page * limitItems}" />
                                        <c:set var="endItem" value="${startItem + limitItems}" />
                                        <c:set var="numOfPages" value="${(sessionScope.transactions.size() + limitItems - 1) / limitItems}" />
                                        <c:forEach var="tr" items="${sessionScope.transactions}" varStatus="status">
                                            <c:if test="${status.index >= startItem && status.index < endItem}">
                                                <li class="list-group-item d-flex justify-content-between ps-0 border-radius-lg">
                                                    <div class="d-flex align-items-center">
                                                        <button class="icon-transaction" type="button" data-toggle="collapse" data-target="#collapse${tr.ID_Transaction}" aria-expanded="false" aria-controls="collapseOne">
                                                            <i class="fa-solid fa-circle-arrow-down"></i>
                                                        </button>
                                                        <% 
                                                           // Lấy ID hóa đơn từ đối tượng rr bằng cách sử dụng EL
                                                           Phong pByTransaction=null;
                                                           if (pageContext.getAttribute("tr")!=null){
                                                               int idTr = (Integer) ((Transaction)  pageContext.getAttribute("tr")).getID_Transaction();
                                                               // Gọi phương thức để lấy thông tin phòng của transaction
                                                               TransactionDAO trd = new TransactionDAO();
                                                               pByTransaction = trd.getRoomOfTransaction(idTr);
                                                            }
                                                           request.setAttribute("roomByTr", pByTransaction);
                

                                                        %>
                                                        <div class="d-flex flex-column">
                                                            <h6 class="mb-1 text-dark text-sm">Phòng ${roomByTr.tenPhongTro}</h6>
                                                            <span class="text-xs">${tr.transaction}</span>
                                                        </div>
                                                    </div>
                                                    <div class="d-flex align-items-center text-success text-gradient text-sm font-weight-bold">
                                                        + <fmt:formatNumber value="${tr.amount}" type="number" groupingUsed="true"/> VND
                                                    </div>

                                                </li>
                                                <div id="collapse${tr.ID_Transaction}" class="collapse" >
                                                    <div class="card-body detail-transaction pl-5 d-flex">
                                                        <div class="col-7">
                                                            <span class="mb-2 text-xs">Phương thức thanh toán: ${tr.paymentMethod}</span><br/>
                                                            <span class="mb-2 text-xs">Mã giao dịch: ${tr.maGiaoDich}</span><br/>
                                                            <span class="mb-2 text-xs">Mô tả: ${tr.moTa}</span>
                                                        </div>
                                                        <div class="ms-auto text-end">
                                                            <button type="button" class="col-4 btn btn-link text-danger text-gradient px-3 mb-0" data-toggle="modal" data-target="#myModalDelete${tr.ID_Transaction}">
                                                                <i class="fa-solid fa-trash"></i>Delete

                                                            </button>
                                                        </div>
                                                        <div id="myModalDelete${tr.ID_Transaction}" class="modal fade" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                                            <div class="modal-dialog modal-confirm">
                                                                <div class="modal-content">
                                                                    <div class="modal-header flex-column">
                                                                        <div class="icon-box">
                                                                            <i class="material-icons"><i class="fa-solid fa-circle-xmark"></i></i>
                                                                        </div>
                                                                        <h5 class="modal-title w-100">Bạn có chắc chắn bạn muốn xóa giao dịch ?<br/> <span style="color: #5932ea"></span></h5>
                                                                    </div>
                                                                    <div class="modal-footer justify-content-end">
                                                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
                                                                        <button type="button" class="btn btn-danger">
                                                                            <a href="actionTransaction?action=dele&id=${tr.ID_Transaction}&exit=home" class="edit-film" style="color: white !important;">Xóa giao dịch</a>
                                                                        </button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                </div>
                                            </c:if>
                                        </c:forEach>
                                    </ul>




                                </div>
                                <div class="pagination-bar">
                                    <nav aria-label="Page navigation example">
                                        <ul class="pagination justify-content-center">
                                            <c:if test="${page > 0}">
                                                <li class="page-item">
                                                    <a class="page-link" href="?${queryString}page=${page - 1}">Previous</a>
                                                </li>
                                            </c:if>
                                            <c:forEach begin="0" end="${numOfPages - 1}" var="i">
                                                <li class="page-item ${page == i ? 'active' : ''}">
                                                    <a class="page-link" href="?${queryString}page=${i}">${i + 1}</a>
                                                </li>
                                            </c:forEach>
                                            <c:if test="${page < numOfPages - 2}">
                                                <li class="page-item">
                                                    <a class="page-link" href="?${queryString}page=${page + 1}">Next</a>
                                                </li>
                                            </c:if>
                                        </ul>
                                    </nav>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </section>
        </section>

        <!-- Bootstrap JS and dependencies -->
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
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

