<%-- 
    Document   : detailRoom
    Created on : Sep 22, 2024, 10:01:51 PM
    Author     : hihihihaha
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Room Details</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css">
        <!-- Custom CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styleRoom.css">
        <!-- Boxicons CSS -->
        <link href='https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css' rel='stylesheet'>
        <!-- Them font awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

        <!-- Bootstrap JS and dependencies -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <style>
            .dropdown-menu {
                display: none;
                list-style: none;
                padding: 0px 27px;
                margin: 0px ;
            }

            /* Khi li có class active, hiển thị dropdown */
            .dropdown.active .dropdown-menu {
                display: block;
            }
        </style>
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


                    <li class="">
                        <a href="#">
                            <i class='bx bx-home-alt icon' ></i>
                            <span class="text nav-text">Trang chủ</span>
                        </a>
                    </li>

                    <li class="dropdown">
                        <a href="javascript:void(0);" class="dropdown-toggle" onclick="toggleDropdown()">
                            <i class='bx bx-cog icon'></i>
                            <span class="text nav-text">Báo cáo</span>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="statistic-room"><span class="text nav-text" style="margin-left: 30px">Phòng trống</span></a></li>
                            <li><a href="statistic-revenue"><span class="text nav-text" style="margin-left: 30px">Doanh thu</span></a></li>
                            <li><a href="khach-no"><span class="text nav-text" style="margin-left: 30px">Khách nợ</span></a></li>
                            <li><a href="khach-coc"><span class="text nav-text" style="margin-left: 30px">Khách cọc</span></a></li>
                            <li><a href="khach-sap-het-han"><span class="text nav-text">Sắp hết hạn hợp đồng</span></a></li>
                            <li><a href="thiet-bi"><span class="text nav-text" style="margin-left: 33px">Thiết bị</span></a></li>
                        </ul>
                    </li>


                    <li class="">
                        <a href="nhatro">
                            <i class='bx bxs-home icon' ></i>
                            <span class="text nav-text">Nhà trọ</span>
                        </a>
                    </li>
                    <c:if test="${sessionScope.account.role == 'landlord'}">
                     <li class="">
                            <a href="manage-new" class="">
                                <i class='bx bx-bell icon ' ></i>
                                <span class="text nav-text">Thông báo</span>
                            </a>                      
                     </li>
                    </c:if>
                    <li class="">
                        <a href="room" >
                            <i class='bx bx-bar-chart-alt-2 icon active' ></i>
                            <span class="text nav-text">Phòng trọ</span>
                        </a>
                    </li>

                    <li class="">
                        <a href="accountController">
                            <i class='bx bx-face icon' ></i>
                            <span class="text nav-text">Người dùng</span>
                        </a>
                    </li>

                    <li class="s">
                        <a href="loaddichvu" >
                            <i class='bx bx-bell icon'></i>
                            <span class="text nav-text">Dịch vụ</span>
                        </a>
                    </li>

                    
                    <c:if test="${sessionScope.account.role == 'landlord'}">
                        <li class="">
                            <a href="DanhSachCacHopDongByAdmin">
                                <i class='bx bx-id-card icon' ></i>
                            <span class="text nav-text">Hợp đồng</span>
                            </a>
                        </li>
                    </c:if>
                   
                         <c:if test="${sessionScope.account.role == 'manager'}">
                        <li class="">
                            <a href="DanhSachCacHopDongByManager">
                                <i class='bx bx-id-card icon' ></i>
                            <span class="text nav-text">Hợp đồng</span>
                            </a>
                        </li>
                    </c:if>

                    <li class="">
                        <a href="hoadon" >
                            <i class='bx bx-wallet icon' ></i>
                            <span class="text nav-text">Hóa đơn</span>
                        </a>
                    </li>
                    <c:if test="${sessionScope.account.role == 'landlord'}">
                        <li class="">
                            <a href="loadThietBi">
                                <i class='bx bx-devices icon' ></i>
                                <span class="text nav-text">Thiết bị</span>
                            </a>
                        </li>
                    </c:if>

                    </ul>
                </div>

                <div class="bottom-content">
                    <li class="">
                        <a href="#">
                            <i class='bx bx-log-out icon' ></i>
                            <span class="text nav-text">Logout</span>
                        </a>
                    </li>


                </div>
            </div>

        </nav>



        <script>
            const body = document.querySelector('body'),
                    sidebar = body.querySelector('nav'),
                    toggle = body.querySelector(".toggle"),
                    modeSwitch = body.querySelector(".toggle-switch"),
                    modeText = body.querySelector(".mode-text");

            // Kiểm tra xem trang hiện tại có phải là /HopDong.jsp không
            const currentPath = window.location.pathname;

            if (currentPath.includes('/ThemKhachThuePhong.jsp') || currentPath.includes('/DangKyDichVuPhongTro') || currentPath.includes('/HopDongThuePhong.jsp')) {
                // Xóa class 'active' khỏi tất cả các nút đang active
                const activeLinks = document.querySelectorAll('.menu-links a.active');
                activeLinks.forEach(link => link.classList.remove('active'));

                // Tìm nút "Hợp đồng" và thêm class 'active'
                const hopDongLink = document.querySelector('a[href="#"] i.bx-id-card').parentElement;
                hopDongLink.classList.add('active');
            }

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
            //filter room by floor ko can submit
            function filterRoomsByFloor(select) {
                var selectedFloor = select.value;
                window.location.href = 'room?tang=' + selectedFloor;
            }
            function filterRoomsByNhaTro(select) {
                var selectedNhaTro = select.value;
                window.location.href = 'room?nhaTro=' + selectedNhaTro; // Chú ý sử dụng 'nhaTro'
            }


            //delete room confirm
            function confirmDelete(roomId) {
                if (confirm('Are you sure you want to delete this room?')) {
                    window.location.href = 'deleteRoom?id=' + roomId;
                }
            }
        </script>
        <script>
            function toggleDropdown() {
                var dropdown = document.querySelector('.dropdown');
                dropdown.classList.toggle('active'); // Thêm/xóa class 'active' khi nhấn
            }
        </script>


    </body>
</html>
