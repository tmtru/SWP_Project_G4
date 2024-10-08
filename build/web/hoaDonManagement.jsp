<%-- 
    Document   : hoaDonManagement
    Created on : Oct 6, 2024, 3:15:36 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@page import="java.util.List, java.util.ArrayList,java.text.DecimalFormat, model.DichVu, model.Phong, dal.DichVuDAO, dal.PhongDAO, dal.HoaDonDAO, model.HoaDon"%>
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
                <div class="row m-0">
                    <div class="col-lg-7 mt-4 ml-2">
                        <div class="card">
                            <div class="card-header pb-0 p-3">
                                <h6 class="mb-0">Thông tin hóa đơn</h6>
                            </div>
                            <div class="card-body pt-4 p-3">
                                <ul class="list-group">
                                    <c:forEach var="hd" items="${sessionScope.invoices}">
                                        <li class="list-group-item list-hoadon d-flex p-4 mb-2 bg-gray-100 border-radius-lg"
                                            style="border-left: 5px solid ${hd.trang_thai == 1 ? 'green' : 'red'}">
                                            <div class="d-flex flex-column">
                                                <% 
                                                    // Lấy ID hóa đơn từ đối tượng hd bằng cách sử dụng EL
                                                    int idHoadon = (Integer) ((HoaDon)  pageContext.getAttribute("hd")).getID_HoaDon();
                
                                                    // Gọi phương thức để lấy thông tin phòng
                                                    HoaDonDAO hdd = new HoaDonDAO();
                                                    Phong room = hdd.getRoomOfHoaDon(idHoadon);
                
                                                    // Kiểm tra null trước khi lưu trữ vào request
                                                    if (room != null) {
                                                        request.setAttribute("room", room);
                                                    }
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
                                                    <span class="mb-2 text-xs">Tổng tiền: <span class="text-dark ms-sm-2 font-weight-bold">${hd.tong_gia_tien}</span></span>
                                                    <span class="text-xs">Tiền đã thanh toán: <span class="text-dark ms-sm-2 font-weight-bold">${hd.tienDaThanhToan}</span></span>
                                                        <c:if test="${hd.trang_thai==1}">
                                                        <span class="text-xs">Mã giao dịch: <span class="text-dark font-weight-bold ms-sm-2">${hd.maGiaoDich}</span></span>
                                                        <span class="text-xs">Ngày thanh toán: <span class="text-dark ms-sm-2 font-weight-bold">${hd.ngayThanhToan}</span></span>
                                                        </c:if>

                                            </div>
                                            <div class="ms-auto text-end">
                                                <div class="text-right">
                                                    <span class="mb-2 text-xs">Ngày tạo: <span class="text-dark font-weight-bold ms-sm-2">${hd.ngay}</span></span>
                                                </div>
                                                <div class="mt-2">
                                                    <a class="btn btn-link text-danger text-gradient px-3 mb-0" href="javascript:;"><i class="fa-solid fa-trash"></i>Delete</a>
                                                    <a class="btn btn-link text-dark px-3 mb-0" href="javascript:;"><i class="fa-solid fa-pen"></i>Edit</a>
                                                </div>
                                            </div>
                                        </li>
                                    </c:forEach>


                                </ul>
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
                                                <h6 class="mb-0">Invoices</h6>
                                            </div>
                                            <div class="col-6 text-end">
                                                <button class="btn btn-outline-primary btn-sm mb-0">View All</button>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="card-body p-3 pb-0">
                                        <ul class="list-group">
                                            <li class="list-group-item border-0 d-flex justify-content-between ps-0 mb-2 border-radius-lg">
                                                <div class="d-flex flex-column">
                                                    <h6 class="mb-1 text-dark font-weight-bold text-sm">March, 01, 2020</h6>
                                                    <span class="text-xs">#MS-415646</span>
                                                </div>
                                                <div class="d-flex align-items-center text-sm">
                                                    $180
                                                    <button class="btn btn-link text-dark text-sm mb-0 px-0 ms-4"><i class="material-icons text-lg position-relative me-1">picture_as_pdf</i> PDF</button>
                                                </div>
                                            </li>
                                            <li class="list-group-item border-0 d-flex justify-content-between ps-0 mb-2 border-radius-lg">
                                                <div class="d-flex flex-column">
                                                    <h6 class="text-dark mb-1 font-weight-bold text-sm">February, 10, 2021</h6>
                                                    <span class="text-xs">#RV-126749</span>
                                                </div>
                                                <div class="d-flex align-items-center text-sm">
                                                    $250
                                                    <button class="btn btn-link text-dark text-sm mb-0 px-0 ms-4"><i class="material-icons text-lg position-relative me-1">picture_as_pdf</i> PDF</button>
                                                </div>
                                            </li>
                                            <li class="list-group-item border-0 d-flex justify-content-between ps-0 mb-2 border-radius-lg">
                                                <div class="d-flex flex-column">
                                                    <h6 class="text-dark mb-1 font-weight-bold text-sm">April, 05, 2020</h6>
                                                    <span class="text-xs">#FB-212562</span>
                                                </div>
                                                <div class="d-flex align-items-center text-sm">
                                                    $560
                                                    <button class="btn btn-link text-dark text-sm mb-0 px-0 ms-4"><i class="material-icons text-lg position-relative me-1">picture_as_pdf</i> PDF</button>
                                                </div>
                                            </li>
                                            <li class="list-group-item border-0 d-flex justify-content-between ps-0 mb-2 border-radius-lg">
                                                <div class="d-flex flex-column">
                                                    <h6 class="text-dark mb-1 font-weight-bold text-sm">June, 25, 2019</h6>
                                                    <span class="text-xs">#QW-103578</span>
                                                </div>
                                                <div class="d-flex align-items-center text-sm">
                                                    $120
                                                    <button class="btn btn-link text-dark text-sm mb-0 px-0 ms-4"><i class="material-icons text-lg position-relative me-1">picture_as_pdf</i> PDF</button>
                                                </div>
                                            </li>
                                            <li class="list-group-item border-0 d-flex justify-content-between ps-0 border-radius-lg">
                                                <div class="d-flex flex-column">
                                                    <h6 class="text-dark mb-1 font-weight-bold text-sm">March, 01, 2019</h6>
                                                    <span class="text-xs">#AR-803481</span>
                                                </div>
                                                <div class="d-flex align-items-center text-sm">
                                                    $300
                                                    <button class="btn btn-link text-dark text-sm mb-0 px-0 ms-4"><i class="material-icons text-lg position-relative me-1">picture_as_pdf</i> PDF</button>
                                                </div>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="transactions mt-3">
                            <div class="card h-100 mb-4">
                                <div class="card-header pb-0 p-3">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <h6 class="mb-0">Your Transaction's</h6>
                                        </div>
                                        <div class="col-md-6 d-flex justify-content-start justify-content-md-end align-items-center">
                                            <i class="material-icons me-2 text-lg">date_range</i>
                                            <small>23 - 30 March 2020</small>
                                        </div>
                                    </div>
                                </div>
                                <div class="card-body pt-4 p-3">
                                    <h6 class="text-uppercase text-body text-xs font-weight-bolder mb-3">Newest</h6>
                                    <ul class="list-group">
                                        <li class="list-group-item border-0 d-flex justify-content-between ps-0 mb-2 border-radius-lg">
                                            <div class="d-flex align-items-center">
                                                <button class="btn btn-icon-only btn-rounded btn-outline-danger mb-0 me-3 p-3 btn-sm d-flex align-items-center justify-content-center"><i class="material-icons text-lg">expand_more</i></button>
                                                <div class="d-flex flex-column">
                                                    <h6 class="mb-1 text-dark text-sm">Netflix</h6>
                                                    <span class="text-xs">27 March 2020, at 12:30 PM</span>
                                                </div>
                                            </div>
                                            <div class="d-flex align-items-center text-danger text-gradient text-sm font-weight-bold">
                                                - $ 2,500
                                            </div>
                                        </li>
                                        <li class="list-group-item border-0 d-flex justify-content-between ps-0 mb-2 border-radius-lg">
                                            <div class="d-flex align-items-center">
                                                <button class="btn btn-icon-only btn-rounded btn-outline-success mb-0 me-3 p-3 btn-sm d-flex align-items-center justify-content-center"><i class="material-icons text-lg">expand_less</i></button>
                                                <div class="d-flex flex-column">
                                                    <h6 class="mb-1 text-dark text-sm">Apple</h6>
                                                    <span class="text-xs">27 March 2020, at 04:30 AM</span>
                                                </div>
                                            </div>
                                            <div class="d-flex align-items-center text-success text-gradient text-sm font-weight-bold">
                                                + $ 2,000
                                            </div>
                                        </li>
                                    </ul>
                                    <h6 class="text-uppercase text-body text-xs font-weight-bolder my-3">Yesterday</h6>
                                    <ul class="list-group">
                                        <li class="list-group-item border-0 d-flex justify-content-between ps-0 mb-2 border-radius-lg">
                                            <div class="d-flex align-items-center">
                                                <button class="btn btn-icon-only btn-rounded btn-outline-success mb-0 me-3 p-3 btn-sm d-flex align-items-center justify-content-center"><i class="material-icons text-lg">expand_less</i></button>
                                                <div class="d-flex flex-column">
                                                    <h6 class="mb-1 text-dark text-sm">Stripe</h6>
                                                    <span class="text-xs">26 March 2020, at 13:45 PM</span>
                                                </div>
                                            </div>
                                            <div class="d-flex align-items-center text-success text-gradient text-sm font-weight-bold">
                                                + $ 750
                                            </div>
                                        </li>
                                        <li class="list-group-item border-0 d-flex justify-content-between ps-0 mb-2 border-radius-lg">
                                            <div class="d-flex align-items-center">
                                                <button class="btn btn-icon-only btn-rounded btn-outline-success mb-0 me-3 p-3 btn-sm d-flex align-items-center justify-content-center"><i class="material-icons text-lg">expand_less</i></button>
                                                <div class="d-flex flex-column">
                                                    <h6 class="mb-1 text-dark text-sm">HubSpot</h6>
                                                    <span class="text-xs">26 March 2020, at 12:30 PM</span>
                                                </div>
                                            </div>
                                            <div class="d-flex align-items-center text-success text-gradient text-sm font-weight-bold">
                                                + $ 1,000
                                            </div>
                                        </li>
                                        <li class="list-group-item border-0 d-flex justify-content-between ps-0 mb-2 border-radius-lg">
                                            <div class="d-flex align-items-center">
                                                <button class="btn btn-icon-only btn-rounded btn-outline-success mb-0 me-3 p-3 btn-sm d-flex align-items-center justify-content-center"><i class="material-icons text-lg">expand_less</i></button>
                                                <div class="d-flex flex-column">
                                                    <h6 class="mb-1 text-dark text-sm">Creative Tim</h6>
                                                    <span class="text-xs">26 March 2020, at 08:30 AM</span>
                                                </div>
                                            </div>
                                            <div class="d-flex align-items-center text-success text-gradient text-sm font-weight-bold">
                                                + $ 2,500
                                            </div>
                                        </li>
                                        <li class="list-group-item border-0 d-flex justify-content-between ps-0 mb-2 border-radius-lg">
                                            <div class="d-flex align-items-center">
                                                <button class="btn btn-icon-only btn-rounded btn-outline-dark mb-0 me-3 p-3 btn-sm d-flex align-items-center justify-content-center"><i class="material-icons text-lg">priority_high</i></button>
                                                <div class="d-flex flex-column">
                                                    <h6 class="mb-1 text-dark text-sm">Webflow</h6>
                                                    <span class="text-xs">26 March 2020, at 05:00 AM</span>
                                                </div>
                                            </div>
                                            <div class="d-flex align-items-center text-dark text-sm font-weight-bold">
                                                Pending
                                            </div>
                                        </li>
                                    </ul>
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
        <script>

            //delete sẻvice confirm
            function confirmDelete(idDichvu, tenDichVu) {
                if (confirm('Bạn có muốn xóa dịch vụ ' + tenDichVu + ' ?')) {
                    window.location.href = 'action?action=dele&id=' + idDichvu;
                }
            }
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
    </body>
</html>
