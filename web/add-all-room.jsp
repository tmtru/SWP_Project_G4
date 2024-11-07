<%-- 
    Document   : room
    Created on : Sep 22, 2024, 1:50:44 AM
    Author     : hihihihaha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<!--
Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Html.html to edit this template
-->
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
        <!-- Them font awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">


        <!----===== Boxicons CSS ===== -->
        <link rel="stylesheet" href="css/open-iconic-bootstrap.min.css">
        <link href='https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css' rel='stylesheet'>

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
            .require{
                color: red;
            }
        </style>
        <title>Thêm phòng nhanh</title>
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
                        <a href="nhatro" class="active">
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

                    <li class="">
                        <a href="hop-dong">
                            <i class='bx bx-id-card icon' ></i>
                            <span class="text nav-text">Hợp đồng</span>
                        </a>
                    </li>

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

        <section class="home">
            <section class="property-management">

                <h2>Thêm Phòng Nhanh</h2>
             
            </section>
            <div class="container mt-4">
               
                <form action="add-all-room" method="post"enctype="multipart/form-data">
                    <div class="form-row">
                        <div class="form-group col-md-4">
                            <label for="tenPhong">Từ phòng <span class="require">*</span></label>
                            <div class="col-md-12 row">
                                <input type="number" step="1" min="1" class="form-control" id="tenPhong" name="tuPhong" required>
                                <input type="hidden"  name="nhatroId" value="${r}">
                            </div>
                        </div>
                        <div class="form-group col-md-4">
                            <div class="col-md-12 row">

                                <label for="den">Đến <span class="require">*</span></label>
                                <input type="number" class="form-control" id="den" name="denPhong" min="0">
                            </div>
                        </div>
                        <div class="form-group col-md-4  " >
                            <label for="donGia">Đơn giá  <span class="require">*</span></label>
                            <div class="col-md-12 row">
                                <input type="number" class="form-control col-md-10" id="donGia" name="donGia" required>
                                <input type="text" class="form-control col-md-2" readonly value="VND">
                            </div>
                        </div>

                    </div>

                    <div class="form-row">

                        <div class="form-group col-md-4">
                            <label for="nha">Nhà  <span class="require">*</span></label>
                            <div class="col-md-12 row">

                                <select class="form-control" id="nha" name="floor" required>
                                    <option value="1">Tầng 1</option>
                                    <option value="2">Tầng 2</option>
                                    <option value="3">Tầng 3</option>
                                    <option value="4">Tầng 4</option>
                                    <option value="5">Tầng 5</option>
                                    <option value="6">Tầng 6</option>
                                    <option value="7">Tầng 7</option>
                                    <option value="8">Tầng 8</option>
                                    <option value="9">Tầng 9</option>
                                    <option value="10">Tầng 10</option>

                                </select>
                            </div>
                        </div>
                        <div class="form-group col-md-4">
                            <label for="dai">Diện tích  <span class="require">*</span></label>
                            <div class="col-md-12 row">
                                <input type="number" class="form-control col-md-10" id="dai" name="dai" min="0">
                                <input type="text" class="form-control col-md-2" readonly value="m²">
                            </div>
                        </div>
                        <div class="form-group col-md-4">
                            <label for="roomImages">Upload Images (Max 5) <span class="require">*</span></label>
                            <input type="file" class="form-control-file" id="roomImages" name="roomImages" multiple accept="image/*" required>
                            <small class="form-text text-muted">You can upload up to 5 images.</small>
                        </div>

                    </div>


                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="loaiPhong">Loại Phòng  <span class="require">*</span></label>
                            <div class="col-md-12 row">
                                <select class="form-control" id="loaiPhong" name="loaiPhong" required>
                                    <c:forEach items="${roomtypes}" var="r" >
                                        <option value="${r.id}">${r.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="form-group col-md-6">
                            <label for="thietBi">Thiết bị <span class="require">*</span></label>
                            <div class="col-md-12 row">
                                <!-- Dropdown button for selecting devices -->
                                <div class="dropdown">
                                    <button class="btn btn-secondary dropdown-toggle" type="button" id="thietBiDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        Chọn thiết bị
                                    </button>
                                    <div class="dropdown-menu p-3" aria-labelledby="thietBiDropdown" style="width: 300px">
                                        <!-- Loop through the list of devices -->
                                        <c:forEach var="device" items="${devices}">
                                            <div class="form-check dropdown-item">
                                                <input class="form-check-input" type="checkbox" id="thietBi${device.id}" name="thietBi[]" value="${device.name}">
                                                <label class="form-check-label" for="thietBi${device.id}"> ${device.name} </label>
                                                <input type="number" class="form-control form-control-sm mt-1 quantity-input" name="soLuong${device.name}" placeholder="Số lượng" min="0" style="width: 100px;">
                                                <small class="error-message text-danger"></small>
                                            </div>
                                        </c:forEach>

                                        <!-- OK Button inside dropdown -->
                                        <div class="dropdown-item text-center">
                                            <button type="button" class="btn btn-success" onclick="validateSelection()">OK</button>
                                        </div>
                                        <small id="generalError" class="text-danger"></small> <!-- General error message -->
                                    </div>
                                    <div id="selectedDevices" class="mt-3"></div>
                                </div>
                            </div>
                        </div>


                        <!-- Div to show the selected items -->


                        <script>
                            function validateSelection() {
                                var isValid = true;
                                var selectedDevices = [];
                                var checkboxes = document.querySelectorAll('input[name="thietBi[]"]');

                                // Clear previous inline error messages
                                document.querySelectorAll('.error-message').forEach(function (el) {
                                    el.textContent = "";
                                });

                                // Clear the general error message
                                document.getElementById('generalError').textContent = "";

                                // Iterate over each checkbox
                                checkboxes.forEach(function (checkbox) {
                                    if (checkbox.checked) {
                                        // Get the corresponding quantity input for the selected checkbox
                                        var quantityInput = checkbox.parentElement.querySelector('.quantity-input');
                                        var quantityValue = quantityInput.value;

                                        if (!quantityValue || quantityValue <= 0) {
                                            // If quantity is not valid, show an error message and set isValid to false
                                            quantityInput.nextElementSibling.textContent = "Vui lòng nhập số lượng lớn hơn 0";
                                            isValid = false;
                                        } else {
                                            // If valid, collect the label and quantity
                                            var label = document.querySelector('label[for="' + checkbox.id + '"]').innerText;
                                            selectedDevices.push(label + " (" + quantityValue + ")");
                                        }
                                    }
                                });

                                // Check if at least one device is selected
                                if (selectedDevices.length === 0) {
                                    isValid = false;
                                    document.getElementById('generalError').textContent = "Vui lòng chọn ít nhất một thiết bị";
                                }

                                // Enable or disable the "Lưu" button based on the validation status
                                document.getElementById('saveButton').disabled = !isValid;

                                // Display selected devices if valid
                                if (isValid) {
                                    document.getElementById('selectedDevices').innerHTML = "<strong>Thiết bị đã chọn:</strong> " + selectedDevices.join(", ");
                                }
                            }
                        </script>

                    </div>



                    <div class="form-row">
                        <div class="col-md-12">
                            <button type="submit" class="btn btn-primary" id="saveButton" disabled>Lưu</button>
                            <a href="nhatro" class="btn btn-warning">Quay về</a>
                        </div>
                    </div>

                </form>
            </div>
        </section>

        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.1/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                var today = new Date().toISOString().split('T')[0]; // Lấy ngày hôm nay dưới định dạng yyyy-mm-dd
                document.getElementById("ngayHoaDon").value = today; // Set giá trị cho input ngày
            });
        </script>

    </body>
</html>