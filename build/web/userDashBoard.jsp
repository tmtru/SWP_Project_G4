<%-- 
    Document   : UserDashBoard
    Created on : Sep 22, 2024, 11:36:15 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <!----======== CSS ======== -->
        <link rel="stylesheet" href="css/styleDashBoard.css">

       

        <!----===== Boxicons CSS ===== -->
        <link rel="stylesheet" href="css/open-iconic-bootstrap.min.css">
        <link href='https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css' rel='stylesheet'>

        <!-- Bootstrap JS and dependencies -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    </head>
    <body>
        <nav class="sidebar close">
            <header>
                <div class="image-text">
                    <span class="image">
                        <img src="images/logo.png" alt="alt" style="margin-top: 15px"/>
                        <!--<img src="logo.png" alt="">-->
                    </span>

                    <div class="text logo-text">
                        <h2 style="margin: 28px 10px">TQATM</h2>
                    </div>
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
                            <a href="#">
                                <i class='bx bx-bar-chart-alt-2 icon' ></i>
                                <span class="text nav-text">Phòng trọ</span>
                            </a>
                        </li>

                        <li class="">
                            <a href="#">
                                <i class='bx bx-face icon' ></i>
                                <span class="text nav-text">Nhân viên</span>
                            </a>
                        </li>

                        <li class="s">
                            <a href="#">
                                <i class='bx bx-bell icon'></i>
                                <span class="text nav-text">Dịch vụ</span>
                            </a>
                        </li>

                        <li class="">
                            <a href="#">
                                <i class='bx bx-id-card icon' ></i>
                                <span class="text nav-text">Hợp đồng</span>
                            </a>
                        </li>

                        <li class="">
                            <a href="#">
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
                    <h3>Danh sách phòng trọ của</h3>
                    <div class="property-selector">
                        <select>
                            <option value="tmt">Nhà trọ TMT</option>
                            <option value="other">Nhà trọ khác</option>
                        </select>
                        <span>Còn trống 2 | Đã thuê 6 | Chưa thu phí 0</span>
                    </div>
                </div>

                <div class="action-buttons">
                    <button class="btn add-property">+ Thêm nhà trọ</button>
                    <button class="btn edit-property">Sửa thông tin nhà</button>
                    <button class="btn delete-property">Xóa nhà</button>
                </div>

                <div class="room-actions">
                    <button class="btn add-room">+ Thêm phòng trọ</button>
                    <button class="btn quick-add-room">+ Thêm phòng trọ nhanh</button>
                </div>

                <div class="filters">
                    <select>
                        <option value="floor">Số Tầng</option>
                    </select>
                    <select>
                        <option value="room-status">Trạng thái phòng</option>
                    </select>
                    <select>
                        <option value="payment-status">Trạng thái trả phí</option>
                    </select>
                    <input type="text" placeholder="Search..." />
                </div>
            </section>
            <section class="ftco-section">

                <div class="container">
                    <div class="row mt-4">        
                        <c:choose>
                            <c:when test="">
                                <c:forEach >
                                    <div class="col-md-4 mb-4">
                                        <div class="card h-100">
                                            <img src="#'}" 
                                                 class="card-img-top" >
                                            <div class="card-body text-center">
                                                <h5 class="card-title"></h5>
                                                <p class="card-text">

                                                </p>
                                                <div class="d-flex justify-content-around">
                                                    <a href="#" class="btn btn-outline-secondary btn-sm">
                                                        <i class="ion-ios-menu"></i> Chi tiết
                                                    </a>
                                                    <a href="#"" class="btn btn-outline-primary btn-sm">
                                                        <i class="ion-ios-cart"></i> Chỉnh sửa
                                                    </a>
                                                    <a href="compare?action=add&productId=${product.id}" class="btn btn-outline-danger btn-sm"> Xóa 
                                                        <i class="ion-ios-heart"></i> 
                                                    </a>

                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <div class="col-12 text-center">
                                    <p>No room available at the moment.</p>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <!-- Pagination -->

                </div>
            </section>
        </section>


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


    </body>
</html>
