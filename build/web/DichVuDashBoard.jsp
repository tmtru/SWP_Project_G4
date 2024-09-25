<%-- 
    Document   : UserDashBoard
    Created on : Sep 22, 2024, 11:36:15 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@page import="java.util.List, java.util.ArrayList,java.text.DecimalFormat, model.DichVu, model.Phong, dal.DichVuDAO, dal.PhongDAO"%>
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



        <!----===== Boxicons CSS ===== -->
        <link rel="stylesheet" href="css/open-iconic-bootstrap.min.css">
        <link href='https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css' rel='stylesheet'>


    </head>
    <style>
        textarea {
            resize: none;
        }
    </style>
    <body>
        <nav class="sidebar close">
            <header>
                <div class="image-text">
                    <a href="homer">
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
                            <a href="loaddichvu" class="active">
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
                    <h2>Danh sách dịch vụ khu trọ</h2>

                </div>
                <p>Dịch vụ sẽ phải gán vào hợp đồng để khi thánh toán sẽ được tự động thêm vào hóa đơn</p>



                <div class="filters">
                    <!-- Button trigger modal -->
                    <button class="btn add-room " data-toggle="modal" data-target="#addRoomModal">+ Thêm dịch vụ</button>

                    <!-- Modal -->
                    <div class="modal fade" id="addRoomModal" tabindex="-1" role="dialog" aria-labelledby="addRoomModalLabel" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="addRoomModalLabel">Thêm dịch vụ</h5>
                                    <button type="button " class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <form action="adddichvu" method="post">

                                        <div class="form-group">
                                            <label for="tendichvu">Tên Dịch vụ:</label>
                                            <input type="text" class="form-control" id="tendichvu" name="tendichvu" required>
                                        </div>
                                        <div class="form-group">
                                            <label for="donGia">Đơn giá(VNĐ):</label>
                                            <input type="text" class="form-control" id="donGia" name="donGia" required>
                                        </div>

                                        <div class="form-group">
                                            <label for="donvi">Đơn vị:</label>
                                            <input type="text" class="form-control"id="donvi" name="donvi" required>
                                        </div>
                                        <div class="form-group">
                                            <label for="ghichu">Ghi chú:</label>
                                            <textarea id="ghichu" name="mota" rows="4" cols="55">
                                            </textarea>
                                        </div>


                                        <button type="submit" class="btn btn-primary">Thêm</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <input type="text" placeholder="Search..." />
                </div>
            </section>
            <section class="ftco-section">

                <div class="container">
                    <div class="row mt-4">        
                        <table class="table">
                            <thead class="thead-dark">
                                <tr>
                                    <th scope="col">ID</th>
                                    <th scope="col">Tên Dịch vụ</th>
                                    <th scope="col">Đơn giá</th>
                                    <th scope="col">Đơn vị</th>
                                    <th scope="col">Ghi chú</th>
                                    <th scope="col">Hành động</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${dichVuList}" var="dv">
                                    <tr>
                                        <th scope="row">${dv.ID_DichVu}</th>
                                        <td>${dv.tenDichVu}</td>
                                        <td>${dv.don_gia}</td>
                                        <td>/${dv.don_vi}</td>
                                        <td>${dv.mo_ta}</td>
                                        <td>
                                            <button class="btn btn-primary" data-toggle="modal" data-target="#EditModal${dv.ID_DichVu}">Chỉnh sửa</button>
                                            <div class="modal fade" id="EditModal${dv.ID_DichVu}" tabindex="-1" role="dialog" aria-labelledby="addRoomModalLabel" aria-hidden="true">
                                                <div class="modal-dialog" role="document">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h5 class="modal-title" id="addRoomModalLabel">Chỉnh sửa</h5>
                                                            <button type="button " class="close" data-dismiss="modal" aria-label="Close">
                                                                <span aria-hidden="true">&times;</span>
                                                            </button>
                                                        </div>
                                                        <div class="modal-body">
                                                            <form action="action?action=edit&id=${dv.ID_DichVu}" method="post">

                                                                <div class="form-group">
                                                                    <label for="tendichvu">Tên Dịch vụ:</label>
                                                                    <input type="text" class="form-control" id="tendichvu" name="tendichvu" value="${dv.tenDichVu}"required>
                                                                </div>
                                                                <div class="form-group">
                                                                    <label for="donGia">Đơn giá(VNĐ):</label>
                                                                    <input type="text" class="form-control" id="donGia" name="donGia" value="${dv.don_gia}" required>
                                                                </div>

                                                                <div class="form-group">
                                                                    <label for="donvi">Đơn vị:</label>
                                                                    <input type="text" class="form-control"id="donvi" name="donvi" value="${dv.don_vi}" required>
                                                                </div>
                                                                <div class="form-group">
                                                                    <label for="ghichu">Ghi chú:</label>
                                                                    <textarea id="ghichu" name="mota" rows="4" cols="55" >${dv.mo_ta}
                                                                    </textarea>
                                                                </div>


                                                                <button type="submit" class="btn btn-primary">Lưu chỉnh sửa</button>
                                                            </form>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <a href="action?action=dele&id=${dv.ID_DichVu}" class="edit-film btn btn-danger"> Xóa</a><br/><br/>
                                        </td>
                                    </tr>
                                </c:forEach>

                            </tbody>
                        </table>


                    </div>

                    <!-- Pagination -->

                </div>
            </section>
        </section>

        <!-- Bootstrap JS and dependencies -->
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>



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
