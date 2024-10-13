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
                    <div class="my-4 ml-4 ">
                        <h4 class="">Tạo giao dịch cho hóa đơn: </h4>
                    </div>

                    <%
                        String idHoaDonParam = request.getParameter("id");
    Integer idHoaDon2 = (Integer) request.getAttribute("id"); // Ép kiểu về Integer
    int idHoaDon = 0;
    HoaDon hoaDon = null;

    if (idHoaDonParam != null && !idHoaDonParam.isEmpty()) {
        try {
            idHoaDon = Integer.parseInt(idHoaDonParam);
            // Khởi tạo HoaDonDAO và lấy thông tin hóa đơn
            HoaDonDAO hoaDonDAO = new HoaDonDAO();
            hoaDon = hoaDonDAO.getHoaDonById(idHoaDon);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    } else if (idHoaDon2 != null) { // Kiểm tra idHoaDon2 không null
        try {
            idHoaDon = idHoaDon2; // Sử dụng giá trị từ attribute
            // Khởi tạo HoaDonDAO và lấy thông tin hóa đơn
            HoaDonDAO hoaDonDAO = new HoaDonDAO();
            hoaDon = hoaDonDAO.getHoaDonById(idHoaDon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    request.setAttribute("hd", hoaDon);
                    %>
                    <div class="container-fluid">
                        <li class="list-group-item list-hoadon d-flex p-4 bg-gray-100 border-radius-lg"
                            style="border-left: 5px solid ${hd.trang_thai == 1 ? 'green' : 'red'}">

                            <div class="d-flex flex-column">
                                <% 
                                    // Lấy ID hóa đơn từ đối tượng hd bằng cách sử dụng EL
                               
                                    TransactionDAO tdao= new TransactionDAO();
                                    float totalAmount=tdao.getTotalMoneyByIdHoaDon(idHoaDon);
                                    request.setAttribute("Paid", totalAmount);
                                    List<Transaction> transactions = tdao.getTransactionsByIdHoaDon(idHoaDon);
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
                                                            <a href="actionTransaction?action=dele&id=${tr.ID_Transaction}&exit=" class="edit-film" style="color: white !important;">Xóa giao dịch</a>
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>



                                    </li>

                                </c:forEach>
                            </ul>
                        </div>
                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-danger">
                                ${errorMessage}
                            </div>
                        </c:if>
                        <c:if test="${not empty successMessage}">
                            <div class="alert alert-success">
                                ${successMessage}
                            </div>
                        </c:if>

                        <form action="actionTransaction?action=add" method="POST" class="col-5 p-3 bg-gray-100 mt-3 ml-5" id="transactionForm">
                            <div class="mb-3">
                                <label for="maGiaoDich" class="form-label">Mã Giao Dịch</label>
                                <input type="text" class="form-control" id="maGiaoDich" name="maGiaoDich" required>
                            </div>
                            <div class="mb-3">
                                <label for="transactionDate" class="form-label">Ngày Giao Dịch</label>
                                <input type="datetime-local" class="form-control" id="transactionDate" name="transactionDate" required>
                            </div>
                            <div class="mb-3">
                                <label for="amount" class="form-label">Số Tiền</label>
                                <input type="text" class="form-control" id="amount" name="amount" step="0.01" required>
                                <div class="invalid-feedback" id="amountError" style="display: none;">Số tiền phải lớn hơn 0 và hợp lệ.</div>
                            </div>
                            <div class="mb-3">
                                <label for="paymentMethod" class="form-label">Phương Thức Thanh Toán</label>
                                <select class="form-select" id="paymentMethod" name="paymentMethod" required>
                                    <option value="" disabled selected>Chọn phương thức</option>
                                    <option value="Tiền mặt">Tiền mặt</option>
                                    <option value="Chuyển khoản">Chuyển khoản</option>
                                    <option value="Thẻ tín dụng">Thẻ tín dụng</option>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="moTa" class="form-label">Mô Tả</label>
                                <textarea class="form-control" id="moTa" name="moTa"></textarea>
                            </div>
                            <div class="mb-3">
                                <label for="idHoaDon" class="form-label">ID Hóa Đơn</label>
                                <input type="number" class="form-control" id="idHoaDon" name="idHoaDon" value="<%= idHoaDon %>" readonly>
                            </div>

                            <button type="submit" class="btn btn-primary">Thêm Giao Dịch</button>
                        </form>
                    </div>


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
        <!-- JavaScript để định dạng số tiền và kiểm tra hợp lệ -->
        <script>
            document.getElementById('amount').addEventListener('input', function (e) {
                var value = e.target.value;

                // Loại bỏ dấu chấm hiện tại
                value = value.replace(/\./g, '');

                // Định dạng lại thành 1.000.000
                var formattedValue = value.replace(/\B(?=(\d{3})+(?!\d))/g, '.');

                // Cập nhật lại giá trị vào ô input
                e.target.value = formattedValue;
            });

            // Kiểm tra trước khi gửi form
            document.getElementById('transactionForm').addEventListener('submit', function (e) {
                var amountInput = document.getElementById('amount');
                var amountValue = amountInput.value.replace(/\./g, ''); // Lấy giá trị không chứa dấu chấm
                var amountError = document.getElementById('amountError');

                // Kiểm tra số tiền có lớn hơn 0 hay không
                if (parseFloat(amountValue) <= 0 || isNaN(parseFloat(amountValue))) {
                    e.preventDefault(); // Ngăn form gửi đi
                    amountError.style.display = 'block'; // Hiển thị lỗi
                } else {
                    amountError.style.display = 'none'; // Ẩn lỗi
                    // Loại bỏ dấu chấm trước khi gửi form
                    amountInput.value = amountValue;
                }
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
            document.addEventListener("DOMContentLoaded", function () {
                var today = new Date().toISOString().split('T')[0]; // Lấy ngày hôm nay dưới định dạng yyyy-mm-dd
                document.getElementById("transactionDate").value = today; // Set giá trị cho input ngày
            });
        </script>



    </body>
</html>



