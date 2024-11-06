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
            <section class="home">
                <section class="property-management">
                    <div class="header">
                        <h2>Danh sách hóa đơn nhà trọ</h2>

                    </div>


                    <nav>
                        <div class="warpper">
                        <c:forEach var="nt" items="${sessionScope.housesByRole}">
                            <a href="hoadon?idHouse=${nt.ID_NhaTro}">
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
            <c:set var="a" value="${sessionScope.account}"></c:set>

                <section class="ftco-section">

                    <div class="row m-0">
                        <div class="col-lg-7 mt-4 ml-2">
                            <div class="row filter">
                                <div class="col-xl-4 date-detail  part-filter">
                                    <h6 class="" style="color: #ffffff">Chọn ngày: </h6>
                                    <div id="reportrange" style="background: #fff; cursor: pointer; padding: 5px 10px; border: 1px solid #ccc; width: 100%">
                                        <i class="fa fa-calendar"></i>&nbsp;

                                    <c:if test="${not empty startDate}">
                                        <c:if test="${not empty endDate}">
                                            <span>${startDate} - ${endDate}</span>
                                        </c:if>
                                        <c:if test="${empty endDate}">
                                            <span>${startDate} - Select an end date</span>
                                        </c:if>
                                    </c:if>

                                    <c:if test="${empty startDate}">
                                        <span>Select a date range</span>
                                    </c:if>

                                    <i class="fa fa-caret-down"></i>
                                </div>
                            </div>
                            <form id="dateRangeForm" action="hoadontheongay" method="get">
                                <input type="hidden" name="startDate" id="startDate">
                                <input type="hidden" name="endDate" id="endDate">
                            </form>
                            <!-- Trigger Button -->
                            <a href="#" class="btn m-2 add-fast-invoices btn-success" data-bs-toggle="modal" data-bs-target="#roomSelectionModal">
                                <i class="fa-solid fa-square-plus"></i>
                                <span>Tạo nhanh hóa đơn hàng tháng</span>
                            </a>

                            <!-- Room Selection Modal -->
                            <div class="modal fade" id="roomSelectionModal" tabindex="-1" aria-labelledby="roomSelectionModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="roomSelectionModalLabel">Chọn Phòng Tạo Hóa Đơn</h5>
                                        </div>
                                        <form action="addfastinvoice" method="Post" id="roomSelectionForm">
                                        <div class="modal-body">
                                            
                                                <input type="hidden" name="step" value="1">
                                                <button type="button" class="btn btn-primary mb-2" onclick="toggleSelectAll()">Chọn Tất Cả</button>

                                                <% HoaDonDAO hdd2 = new HoaDonDAO(); %>
                                                <c:forEach var="rr" items="${sessionScope.Rooms}">
                                                    <% 
                                                       int idRoom1 = (Integer) ((Phong) pageContext.getAttribute("rr")).getID_Phong();
                                                       PhongDAO pd1 = new PhongDAO();
                                                       boolean isRented1 = pd1.isRentedRoom(idRoom1);
                                                       request.setAttribute("isRented", isRented1);
                                                    %>
                                                    <c:if test="${isRented == true}">
                                                        <div class="form-check">
                                                            <input class="form-check-input" type="checkbox" name="selectedRooms" value="${rr.ID_Phong}" id="room${rr.ID_Phong}">
                                                            <label class="form-check-label" for="room${rr.ID_Phong}">
                                                                Phòng ${rr.tenPhongTro}
                                                            </label>
                                                        </div>
                                                    </c:if>
                                                </c:forEach>
                                            
                                        </div>


                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                                            <button type="submit" class="btn btn-primary" ">Xác nhận</button>
                                        </div>
                                                </form>
                                    </div>
                                </div>
                            </div>





                        </div>
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
                                <div class="row justify-content-between">
                                    <a href="TransactionHistory.jsp" class="btn btn-primary mt-2 ml-2">Lịch sử thanh toán</a>
                                    <form action="hoadontheongay" method="get" class="mx-2 mt-2">
                                        <input type="hidden" name="action" value="exportExcel">
                                        <input type="hidden" name="startDate" id="startDate" value="${startDate}">
                                        <input type="hidden" name="endDate" id="endDate" value="${endDate}">
                                        <button type="submit" class="btn export-to-excel" style="background-color: white">
                                            <i class="bx bxs-file-export"></i>
                                            <span>Export Hóa đơn(${startDate} đến ${endDate}) to Excel</span>
                                        </button>

                                    </form>
                                </div>


                            </div>

                            <div class="card-body pt-4 p-3">
                                <ul class="list-group">
                                    <% HoaDonDAO hdd= new HoaDonDAO();
                                    %>
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
                                    <c:set var="numOfPages" value="${(invoices.size() + limitItems - 1) / limitItems}" />

                                    <c:forEach var="hd" items="${invoices}" varStatus="status">
                                        <c:if test="${status.index >= startItem && status.index < endItem}">
                                            <li class="list-group-item list-hoadon d-flex p-4 mb-2 bg-gray-100 border-radius-lg"
                                                style="border-left: 5px solid ${hd.trang_thai == 1 ? 'green' : 'red'}">
                                                <div class="d-flex flex-column">
                                                    <% 
                                                        int idHoadon = (Integer) ((HoaDon)  pageContext.getAttribute("hd")).getID_HoaDon();                             
                                                        Phong room = hdd.getRoomOfHoaDon(idHoadon);
                                                        if (room != null) {
                                                            request.setAttribute("room", room);
                                                        }
                                                        TransactionDAO tdao= new TransactionDAO();
                                                        float totalAmount=tdao.getTotalMoneyByIdHoaDon(idHoadon);
                                                        request.setAttribute("Paid", totalAmount);
                                                    %>
                                                    <div class="d-flex header-invoice">
                                                        <h6 class="mb-3 text-sm">Phòng: 
                                                            <c:if test="${room != null}">
                                                                ${room.tenPhongTro}
                                                            </c:if>
                                                            <c:if test="${room == null}">
                                                                Không có thông tin phòng
                                                            </c:if>
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
                                                        <c:forEach var="dichVu" items="${hd.dichVus}">
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
                                                    <div class="mt-2">
                                                        <!--                                                    <a class="btn btn-link text-primary px-3 mb-0" href="javascript:;"><i class="fa-solid fa-circle-info"></i>Chi tiết</a>-->
                                                        <a class="btn btn-link text-success px-3 mb-0" href="TransactionForm.jsp?id=${hd.ID_HoaDon}"><i class="fa-solid fa-plus"></i>Thêm giao dịch</a>
                                                        <button type="button" class="btn btn-link text-danger text-gradient px-3 mb-0" data-toggle="modal" data-target="#myModalDelete${hd.ID_HoaDon}">
                                                            <i class="fa-solid fa-trash"></i>Xóa
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
                                                                    <div class="modal-footer justify-content-center d-flex">
                                                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
                                                                        <button type="button" class="btn btn-danger">
                                                                            <a href="actionhoadon?action=dele&id=${hd.ID_HoaDon}&exit=home" class="edit-film" style="color: white !important;">Xóa hóa đơn</a>
                                                                        </button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>

                                                    </div>
                                                </div>
                                            </li>
                                        </c:if>
                                    </c:forEach>


                                </ul>
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
                    <div class="col-lg-4 mt-4">
                        <div class="room-list">
                            <div class="">
                                <div class="card h-100">
                                    <div class="card-header pb-0 p-3">
                                        <div class="row">
                                            <div class="col-6 d-flex align-items-center">
                                                <h6 class="mb-0">Thông tin hóa đơn từng phòng</h6>
                                            </div>
                                            <div class="col-6 text-end">
                                                <button class="btn btn-outline-primary btn-sm mb-0">View All</button>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="card-body p-3 pb-0">
                                        <ul class="list-group">
                                            <% HoaDonDAO hdd1= new HoaDonDAO();
                                            %>
                                            <c:forEach var="rr" items="${sessionScope.Rooms}">
                                                <% 
                                                   int idRoom = (Integer) ((Phong)  pageContext.getAttribute("rr")).getID_Phong();
                                                   List<Integer> listContractByRoom = hdd1.getHopDongOfRentedRoom(idRoom);
                                                   
                                                   int countPaid = 0;
                                                   int countUnpaid = 0;
                                                   int countCurrentInvoice=0;

                                                   // Duyệt qua từng hợp đồng để lấy hóa đơn
                                                   for (Integer contractId : listContractByRoom) {
                                                       List<HoaDon> listHDByContract = hdd1.getHoaDonByHopDong(contractId);
                                                       List<HoaDon> ts=hdd.getHoaDonByCurrentMonthAndByIdHopDong(contractId);
                                                               countCurrentInvoice+= ts.size();

                                                       // Đếm số hóa đơn đã trả và chưa trả
                                                       for (HoaDon hd : listHDByContract) {
                                                           if (hd.getTrang_thai() == 1) { 
                                                               countPaid++;
                                                               
                                                               
                                                           } else {
                                                               countUnpaid++;
                                                           }
                                                       }
                                                   }
                                                   PhongDAO pd= new PhongDAO();
                                                   boolean isRented = pd.isRentedRoom(idRoom);
                                                   request.setAttribute("isRented", isRented);
                                                   request.setAttribute("countPaid", countPaid);
                                                   request.setAttribute("countUnpaid", countUnpaid);
                                                   request.setAttribute("countCurrentInvoice", countCurrentInvoice);
                

                                                %>
                                                <li class="list-group-item  d-flex justify-content-between ps-0 mb-2 border-radius-lg">
                                                    <div class="d-flex flex-column">
                                                        <h6 class="mb-1 text-dark font-weight-bold text-sm">Phòng ${rr.tenPhongTro}</h6>
                                                        <span class="text-xs">Hóa đơn đã tạo trong tháng này: ${countCurrentInvoice}</span>
                                                        <span class="text-xs" style="color: #009933">Đã thanh toán: ${countPaid} | <span class="text-xs" style="color: red">Chưa thanh toán: ${countUnpaid}</span></span> 


                                                    </div>
                                                    <div class="d-flex align-items-center text-sm">
                                                        <a class="btn btn-link text-primary px-3 mb-0" href="hoadonroom?roomId=${rr.ID_Phong}"><i class="fa-solid fa-circle-info"></i>Chi tiết</a>
                                                        <c:if test="${isRented==true}">
                                                            <a class="btn btn-link text-success text-gradient px-3 mb-0" href="loadHoaDonForm?roomId=${rr.ID_Phong}"><i class="fa-solid fa-plus"></i>Thêm hóa đơn</a>
                                                        </c:if>
                                                        <c:if test="${isRented==false || isRented==null}">
                                                            <span class="badge ml-4 mr-2 badge-danger">
                                                                <i class="fa-solid fa-triangle-exclamation"></i> Phòng trống
                                                            </span>
                                                        </c:if>
                                                    </div>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>
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
