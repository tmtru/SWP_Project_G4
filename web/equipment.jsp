<%-- 
    Document   : UserDashBoard
    Created on : Sep 22, 2024, 11:36:15 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@page import="java.util.List, java.util.ArrayList,java.text.DecimalFormat, model.ThietBi, model.Phong, dal.ThietBiDAO, dal.PhongDAO"%>
<!DOCTYPE html>

<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- Bootstrap CSS -->

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <!----======== CSS ======== -->
        <link rel="stylesheet" href="css/styleDichVu.css">
        <link rel="stylesheet" href="css/styleRoom.css">
        <link rel="stylesheet" href="css/modelDelete.css">
        <script src="https://kit.fontawesome.com/aab0c35bef.js" crossorigin="anonymous"></script>


        <!----===== Boxicons CSS ===== -->
        <link rel="stylesheet" href="css/open-iconic-bootstrap.min.css">
        <link href='https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css' rel='stylesheet'>
        <!--jquery-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>


    </head>
    <style>
        textarea {
            resize: none;
        }
    </style>
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
                            <a href="#">
                                <i class='bx bx-wallet icon' ></i>
                                <span class="text nav-text">Hóa đơn</span>
                            </a>
                        </li>

                        <li class="">
                            <a href="loadThietBi" class="active">
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
                    <h2>Danh sách thiết bị khu trọ</h2>

                </div>




                <div class="filters">
                    <!-- Button trigger modal -->
                    <button class="btn add-room " data-toggle="modal" data-target="#addThietBiModal">+ Thêm thiết bị</button>
                    <!-- Modal -->
                    <div class="modal fade" id="addThietBiModal" tabindex="-1" role="dialog" aria-labelledby="addThietBiModalLabel" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="addThietBiModalLabel">Thêm thiết bị</h5>
                                    <button type="button " class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <form action="addThietBi" method="post">
                                        <div class="form-group">
                                            <label for="tenthietbi">Tên Thiết bị:</label>
                                            <input type="text" class="form-control" id="tenthietbi" name="tenthietbi" required>
                                        </div>
                                        <div class="form-group">
                                            <label for="giatien">Giá tiền (VNĐ):</label>
                                            <input type="number" class="form-control" id="giatien" name="giatien" required>
                                        </div>
                                        <div class="form-group">
                                            <label for="soluong">Số lượng:</label>
                                            <input type="number" class="form-control" id="soluong" name="soluong" required>
                                        </div>
                                        <div class="form-group">
                                            <label for="mota">Mô tả:</label>
                                            <textarea id="mota" name="mota" rows="4" cols="55" class="form-control"></textarea>
                                        </div>
                                        <button type="submit" class="btn btn-primary">Thêm</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <input type="text" placeholder="Tìm kiếm..." />
                </div>
            </section>
            <section class="ftco-section">
                <div class="mx-5">
                    <table class="table mt-3">
                        <thead class="thead-dark">
                            <tr>
                                <th scope="col">ID</th>
                                <th scope="col">Tên Thiết Bị</th>
                                <th scope="col">Giá tiền</th>
                                <th scope="col">Mô tả</th>
                                <th scope="col">Số lượng</th>
                                <th scope="col">Hành động</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${thietBiList}" var="tb">
                                <tr>
                                    <th scope="row">${tb.ID_ThietBi}</th>
                                    <td>${tb.tenThietBi}</td>
                                    <td>${tb.gia_tien}</td>
                                    <td>${tb.mo_ta}</td>
                                    <td>${tb.so_luong}</td>
                                    <td>
                                        <button class="btn edit" data-toggle="modal" data-target="#EditModal${tb.ID_ThietBi}">Chỉnh sửa</button>
                                        <div class="modal fade" id="EditModal${tb.ID_ThietBi}" tabindex="-1" role="dialog" aria-labelledby="editModalLabel${tb.ID_ThietBi}" aria-hidden="true">
                                            <div class="modal-dialog" role="document">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title" id="editModalLabel${tb.ID_ThietBi}">Chỉnh sửa Thiết Bị</h5>
                                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                            <span aria-hidden="true">&times;</span>
                                                        </button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <form action="editThietBi" method="post">
                                                            <input type="hidden" name="id" value="${tb.ID_ThietBi}">
                                                            <div class="form-group">
                                                                <label for="tenthietbi${tb.ID_ThietBi}">Tên Thiết bị:</label>
                                                                <input type="text" class="form-control" id="tenthietbi${tb.ID_ThietBi}" name="tenthietbi" value="${tb.tenThietBi}" required>
                                                            </div>
                                                            <div class="form-group">
                                                                <label for="giatien${tb.ID_ThietBi}">Giá tiền (VNĐ):</label>
                                                                <input type="number" class="form-control" id="giatien${tb.ID_ThietBi}" name="giatien" value="${tb.gia_tien}" required>
                                                            </div>
                                                            <div class="form-group">
                                                                <label for="soluong${tb.ID_ThietBi}">Số lượng:</label>
                                                                <input type="number" class="form-control" id="soluong${tb.ID_ThietBi}" name="soluong" value="${tb.so_luong}" required>
                                                            </div>
                                                            <div class="form-group">
                                                                <label for="mota${tb.ID_ThietBi}">Mô tả:</label>
                                                                <textarea id="mota${tb.ID_ThietBi}" name="mota" rows="4" cols="55" class="form-control">${tb.mo_ta}</textarea>
                                                            </div>
                                                            <button type="submit" class="btn btn-primary">Lưu chỉnh sửa</button>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
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
