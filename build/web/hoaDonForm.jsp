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
        <nav class="sidebar">
            <header>
                <div class="image-text">
                    <a href="home.jsp">
                        <span class="image">
                            <img src="assets/img/Logo_nhatro.png" alt="alt" style="margin-top: 15px; width: 100%; margin-left:10px"/>
                            <!--<img src="logo.png" alt="">-->
                        </span>
                    </a>
                </div>

                <i class='bx bx-chevron-right toggle'></i>
            </header>

            <div class="menu-bar">
                <div class="menu">
                    <!-- 
                    <li class="search-box">
                        <i class='bx bx-search icon'></i>
                        <input type="text" placeholder="Search...">
                    </li>
                    -->

                    <ul class="menu-links">
                        <li class="">
                            <a href="#">
                                <i class='bx bx-home-alt icon' ></i>
                                <span class="text nav-text">Trang chủ</span>
                            </a>
                        </li>
                        <li class="">
                            <a href="nhatro" >
                                <i class='bx bxs-home icon' ></i>
                                <span class="text nav-text">Nhà trọ</span>
                            </a>
                        </li>

                        <li class="">
                            <a href="room">
                                <i class='bx bx-bar-chart-alt-2 icon' ></i>
                                <span class="text nav-text">Phòng trọ</span>
                            </a>
                        </li>

                        <li class="">
                            <a href="accountController">
                                <i class='bx bx-face icon' ></i>
                                <span class="text nav-text">Nhân viên</span>
                            </a>
                        </li>

                        <li class="s">
                            <a href="loaddichvu" >
                                <i class='bx bx-bell icon'></i>
                                <span class="text nav-text active">Dịch vụ</span>
                            </a>
                        </li>

                        <li class="">
                            <a href="#">
                                <i class='bx bx-id-card icon' ></i>
                                <span class="text nav-text">Hợp đồng</span>
                            </a>
                        </li>

                        <li class="">
                            <a href="#" class="active">
                                <i class='bx bx-wallet icon' ></i>
                                <span class="text nav-text">Hóa đơn</span>
                            </a>
                        </li>

                        <li class="">
                            <a href="#">
                                <i class='bx bx-devices icon' ></i>
                                <span class="text nav-text">Thiết bị</span>
                            </a>
                        </li>


                    </ul>
                </div>

                <div class="bottom-content">
                    <li class="">
                        <a href="#">
                            <i class='bx bx-log-out icon' ></i>
                            <span class="text nav-text">Logout</span>
                        </a>
                    </li>

                    <li class="mode">
                        <div class="sun-moon">
                            <i class='bx bx-moon icon moon'></i>
                            <i class='bx bx-sun icon sun'></i>
                        </div>
                        <span class="mode-text text">Dark mode</span>

                        <div class="toggle-switch">
                            <span class="switch"></span>
                        </div>
                    </li>

                </div>
            </div>

        </nav>
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

                <div class="m-0">
                    <c:if test="${room!=null}">
                        <div class="my-4 ml-4 ">
                            <h4 class="">Tạo hóa đơn hàng tháng </h4>
                        </div>
                    </c:if>
                    <form action="actionhoadon?action=add" method="post" class="col-lg-11 mt-1 ml-5" onsubmit="return validateForm12()">
                        <div class="invoice ">
                            <div class="invoice-body pl-5">

                                <div class="row">
                                    <div class="col-7 mr-5">
                                        <div class="panel panel-default">
                                            <div class="panel-heading">
                                                <h3 class="panel-title">Thông tin yêu cầu</h3>
                                            </div>

                                            <div class="panel-body ">

                                                <div class="form-group mb-5">

                                                    <label for="phong" class="header-service">Phòng *</label>

                                                    <br/>

                                                    <select name="idPhong" id="phong" class="form-control" onchange="updateInvoiceOnRoomChange()">
                                                        <c:if test="${room != null}">
                                                            <option value="${room.ID_Phong}" data-price="${room.gia}">${room.tenPhongTro}</option>
                                                        </c:if>
                                                        <c:forEach var="listroom" items="${rentedrooms}">
                                                            <option value="${listroom.ID_Phong}" data-price="${listroom.gia}">${listroom.tenPhongTro}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="form-group mb-3">
                                                    <label for="ngayHoaDon" class="header-service">Ngày hóa đơn *</label>
                                                    <input type="date" name="ngayHoaDon" id="ngayHoaDon" class="form-control" required>
                                                </div>

                                                <div class="form-group">
                                                    <label for="dichVu" class="header-service">Chọn dịch vụ:</label>
                                                    <div class="pl-4">
                                                        <!--                                                        private int ID_DichVu;
                                                            private String TenDichVu;
                                                            private int Don_gia;
                                                            private String Don_vi;
                                                            private String Mo_ta;
                                                            private boolean isActive; /-->
                                                        <div class="mb-2">
                                                            <input type="checkbox" id="tinhTienPhong" name="tinhTienPhong" value="${amount}" onchange="toggleInvoiceRoom(this)">
                                                            <label for="tinhTienPhong" style="color: #0B2F9F; font-weight: bolder">Tính tiền phòng</label>
                                                        </div>
                                                        <c:forEach var="dichVu" items="${dvList}">
                                                            <div class="form-check mb-2">

                                                                <input class="form-check-input" type="checkbox" name="dichVuId" value="${dichVu.ID_DichVu}" 
                                                                       id="dichVu_${dichVu.ID_DichVu}" 
                                                                       onchange="toggleServiceInputs('${dichVu.tenDichVu}', ${dichVu.don_gia}, this)">
                                                                <label class="form-check-label" for="dichVu_${dichVu.ID_DichVu}" style="color: #0B2F9F; font-weight: bolder">
                                                                    ${dichVu.tenDichVu} - Giá: <fmt:formatNumber value="${dichVu.don_gia}" type="number" groupingUsed="true"/> VND/${dichVu.don_vi}
                                                                </label>

                                                                <!-- Hiển thị ô nhập chỉ số cũ, mới hoặc đầu người -->
                                                                <c:choose>
                                                                    <c:when test="${dichVu.tenDichVu == 'Điện' || dichVu.tenDichVu == 'Nước'}">
                                                                        <div id="chiSo_${dichVu.ID_DichVu}" class="input-group mt-2">
                                                                            <div class="form-group">
                                                                                <label for="chiSoCu_dichVu_${dichVu.ID_DichVu}" class="small">Chỉ số cũ</label>
                                                                                <input type="number" placeholder="Chỉ số cũ" id="chiSoCu_dichVu_${dichVu.ID_DichVu}" name="chiSoCu_dichVu_${dichVu.ID_DichVu}" class="form-control" 
                                                                                       oninput="updateInvoiceService('${dichVu.tenDichVu}', ${dichVu.don_gia}, 'dichVu_${dichVu.ID_DichVu}')">
                                                                            </div>
                                                                            <div class="form-group">
                                                                                <label for="chiSoMoi_dichVu_${dichVu.ID_DichVu}" class="small">Chỉ số mới</label>
                                                                                <input type="number" placeholder="Chỉ số mới" id="chiSoMoi_dichVu_${dichVu.ID_DichVu}" name="chiSoMoi_dichVu_${dichVu.ID_DichVu}" class="form-control ml-2"  
                                                                                       oninput="updateInvoiceService('${dichVu.tenDichVu}', ${dichVu.don_gia}, 'dichVu_${dichVu.ID_DichVu}')">
                                                                            </div>
                                                                        </div>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <div id="dauNguoi_${dichVu.ID_DichVu}" class="input-group mt-2 ">
                                                                            <div class="form-group">
                                                                                <label for="dauNguoiInput_dichVu_${dichVu.ID_DichVu}" class="small">Đầu người</label>
                                                                                <input type="number" placeholder="Đầu người" id="dauNguoiInput_dichVu_${dichVu.ID_DichVu}" name="dauNguoiInput_dichVu_${dichVu.ID_DichVu}" class="form-control" value="1" 
                                                                                       oninput="updateInvoiceService('${dichVu.tenDichVu}', ${dichVu.don_gia}, 'dichVu_${dichVu.ID_DichVu}')">
                                                                            </div>
                                                                        </div>
                                                                    </c:otherwise>
                                                                </c:choose>



                                                            </div>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                                <div class="form-group mb-3"> <!-- Thêm mô tả -->
                                                    <label for="moTa" class="header-service">Mô tả</label>
                                                    <textarea name="moTa" id="moTa" class="form-control" rows="4" placeholder="Nhập mô tả về hóa đơn"></textarea>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-4">
                                        <div class="panel panel-default">
                                            <div class="panel-heading">
                                                <h3 class="panel-title">Hóa đơn</h3>
                                            </div>
                                            <table class="table table-bordered table-condensed">
                                                <thead>
                                                    <tr>
                                                        <th>Dịch vụ</th>
                                                        <th class="text-right">Giá</th>
                                                    </tr>
                                                </thead>
                                                <tbody id="invoiceServicesBody">
                                                    <!-- Các dịch vụ được thêm vào đây bởi JavaScript -->
                                                </tbody>
                                                <tfoot>
                                                    <tr>
                                                        <td><strong>Tổng cộng</strong></td>
                                                        <td class="text-right"><strong id="totalPrice">0 VND</strong></td>
                                                    </tr>
                                                </tfoot>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                                <div class="alert alert-danger mr-3" role="alert" id="notification" style="display: none;">

                                </div>
                                <div class="d-flex submit-part">

                                    <button type="submit" class="btn btn-primary">Tạo hóa đơn</button>
                                </div>
                            </div>

                        </div>

                    </form>


                </div>

            </section>
        </section>
        <script src="js/hoadonForm.js"></script>

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
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                var today = new Date().toISOString().split('T')[0]; // Lấy ngày hôm nay dưới định dạng yyyy-mm-dd
                document.getElementById("ngayHoaDon").value = today; // Set giá trị cho input ngày
            });
        </script>



    </body>
</html>


