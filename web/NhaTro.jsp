<%-- 
    Document   : room
    Created on : Sep 22, 2024, 1:50:44 AM
    Author     : hihihihaha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@page import= "java.util.List, java.util.ArrayList, model.Phong, model.NhaTro, model.LoaiPhong, model.AnhPhongTro, dal.PhongDAO, dal.NhaTroDAO, dal.LoaiPhongDAO"%>
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

            .img-preview {
                width: 50px;
                border: 1px solid #ddd;
                border-radius: 5px;
                margin-right: 10px;
                margin-bottom: 10px;
            }
        </style>
        <title>Danh sách nhà trọ</title>
    </head>
    <body>
        <!--Room manegement dash board-->

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

        <section class="home">

            <section class="property-management">
                <h2>Danh sách nhà trọ</h2>

                <div class="action-buttons" style="margin-bottom: 20px">

                    <button class="btn add-property" data-toggle="modal" data-target="#addRoomModal">+ Thêm nhà trọ</button>

                    <form action="nhatro" method="get" class="filters">
                        <input type="text" name="search" value="${param.search}" placeholder="Search..">
                        <button type="submit"><i class='bx bx-search icon'></i></button>





                    </form>
                </div>
                <c:if test="${not empty sessionScope.notification}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        ${sessionScope.notification}
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <%
                        session.removeAttribute("notification");
                    %>
                </c:if>

                <c:if test="${not empty sessionScope.notificationErr}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        ${sessionScope.notificationErr}
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <%
                        session.removeAttribute("notificationErr");
                    %>
                </c:if>
                <!-- Add Room Modal -->
                <div class="modal fade" id="addRoomModal" tabindex="-1" role="dialog" aria-labelledby="addRoomModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">

                                <h5 class="modal-title" id="addRoomModalLabel">Thêm nhà trọ</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>

                            </div>




                            <div class="modal-body">
                                <form id="addRoomForm" action="nhatro" method="post" enctype="multipart/form-data">
                                    <input type="hidden" name="action" value="add">

                                    <div class="form-group">
                                        <label for="tenNhaTro">Tên nhà trọ</label>
                                        <input type="text" class="form-control" id="tenNhaTro" name="tenNhaTro" >
                                    </div>

                                    <div class="form-group">
                                        <div class="form-input" data-target-input="nearest">
                                            <label for="addressInput" class="label mb-1">Vị trí</label>
                                            <div class="input-group">
                                                <input id="addressInput" type="text" class="form-control" name="address"
                                                       placeholder="Địa điểm của bạn..." oninput="suggestAddress(this.value)" required />

                                            </div>
                                            <div id="suggestions"></div>
                                            <input type="text" id="selectedlat" name="lat" hidden />
                                            <input type="text" id="selectedlon" name="lon" hidden />
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label for="moTa">Mô tả</label>
                                        <textarea class="form-control" id="moTa" name="moTa" rows="3"></textarea>
                                    </div>

                                    <div class="form-group">
                                        <label for="images">Hình ảnh (tối đa 5 hình)</label>
                                        <input type="file" class="form-control" id="images" name="images" multiple accept="image/*">
                                    </div>

                                    <button type="submit" class="btn btn-primary">Thêm</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <section class="ftco-section">
                <div class="container">
                    <div class="row" style="margin-top: 20px">
                        <c:forEach var="s" items="${listS}">
                            <div class="col-md-4 mb-4">
                                <div class="card h-100">
                                    <div class="card-body">
                                        <h5 class="card-title">${s.getTenNhaTro()}</h5>
                                        <p class="card-text">
                                            <strong>Room</strong> <a href="room?nhaTro=${s.ID_NhaTro}">${s.getRoomNumber()}</a><br>
                                        </p>
                                        <p class="card-text"><strong>Owner </strong> ${s.getChuTro().getName()}</p>
                                        <p class="card-text">
                                            <strong>Address</strong> ${s.getDia_chi()}<br>
                                        </p>
                                        <p class="card-text">
    <strong class="des-text">Description</strong>
    <span id="description-${s.ID_NhaTro}">${s.getMo_ta()}</span><br>
</p>


                                    </div>
                                    <div class="card-footer bg-transparent">
                                        <div class="d-flex justify-content-between">
                                            <a href="nhatro-detail?id=${s.ID_NhaTro}" class="btn btn-outline-secondary btn-sm custom-btn" style="color: black;">
                                                <i class="bx bx-info-circle"></i> 
                                            </a>

                                            <c:if test="${isOwwer == true}">
                                                <div class="d-flex justify-content-between">
                                                    <button class="btn btn-outline-secondary btn-sm custom-btn" 
                                                            data-toggle="modal" data-target="#editRoomModal-${s.getID_NhaTro()}"
                                                            style="color: black;">
                                                        <i class="bx bx-edit"></i> 
                                                    </button>
                                                </div>
                                                <div class="d-flex justify-content-between">
                                                    <!-- Existing buttons like "Edit" -->
                                                    <a href="#" class="btn btn-outline-secondary btn-sm custom-btn" style="color: black;" 
                                                       onclick="confirmDelete(${s.getID_NhaTro()}); return false;">
                                                        <i class="bx bx-minus-circle"></i> 
                                                    </a>
                                                    <form id="deleteForm" action="nhatro" method="post" style="display:none;">
                                                        <input type="hidden" name="action" value="delete">
                                                        <input type="hidden" name="nhaTroId" id="deleteNhaTroId">
                                                    </form>

                                                </div>
                                                <a href="add-all-room?id=${s.ID_NhaTro}" class="btn btn-outline-secondary btn-sm custom-btn" style="color: black;">
                                                    <i class="bx bx-plus"></i>
                                                </a>

                                            </c:if>
                                            <a href="quanly-account-nha-tro?id=${s.ID_NhaTro}" class="btn btn-outline-secondary btn-sm custom-btn" style="color: black;">
                                                <i class="bx bx-user-plus"></i> 
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Edit Room Modal -->

                            <div class="modal fade" id="editRoomModal-${s.getID_NhaTro()}" tabindex="-1" role="dialog" aria-labelledby="editRoomModalLabel-${s.getID_NhaTro()}" aria-hidden="true">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="editRoomModalLabel-${s.getID_NhaTro()}">Chỉnh sửa nhà trọ</h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            <form id="editRoomForm-${s.getID_NhaTro()}" action="nhatro" method="post" enctype="multipart/form-data">
                                                <input type="hidden" name="action" value="edit">
                                                <input type="hidden" name="nhaTroId" value="${s.getID_NhaTro()}">

                                                <div class="form-group">
                                                    <label for="tenNhaTro">Tên nhà trọ</label>
                                                    <input type="text" class="form-control" name="tenNhaTro" value="${s.getTenNhaTro()}" >
                                                </div>

                                                <div class="form-group">
                                                    <label for="addressInput-${s.getID_NhaTro()}" class="label mb-1">Vị trí</label>
                                                    <div class="input-group">
                                                        <input id="addressInput-${s.getID_NhaTro()}" type="text" class="form-control" name="address"
                                                               placeholder="Địa điểm của bạn..." oninput="suggestAddress1(${s.getID_NhaTro()},this.value)" value="${s.getDia_chi()}" required />
                                                    </div>
                                                    <div id="suggestions-${s.getID_NhaTro()}"></div>
                                                    <input type="text" id="selectedlat-${s.getID_NhaTro()}" name="lat" hidden />
                                                    <input type="text" id="selectedlon-${s.getID_NhaTro()}" name="lon" hidden />
                                                </div>

                                                <div class="form-group">
                                                    <label for="moTa">Mô tả</label>
                                                    <textarea class="form-control" name="moTa" rows="3">${s.getMo_ta()}</textarea>
                                                </div>

                                                <div class="form-group">
                                                    <label>Hình ảnh hiện tại</label>
                                                    <div class="current-images">
                                                        <c:forEach var="image" items="${s.getAnhNhaTro()}">
                                                            <div class="image-block">
                                                                <img src="${image}" alt="Room Image" style="width: 100px; height: 100px;">
                                                                <input type="checkbox" name="deleteImages" value="${image}"> Remove
                                                            </div>
                                                        </c:forEach>
                                                    </div>
                                                </div>

                                                <c:if test="${s.getAnhNhaTro().size() < 5}">
                                                    <div class="form-group">
                                                        <label for="images">Tải lên thêm hình ảnh (tối đa 5)</label>
                                                        <input type="file" class="form-control" name="images" multiple accept="image/*">
                                                    </div>
                                                </c:if>

                                                <button type="submit" class="btn btn-primary">Cập nhật</button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>


                        </c:forEach>
                    </div>

                    <div class="pagination">
                        <c:if test="${currentPage > 1}">
                            <a href="?page=${currentPage - 1}" class="prev-next"><i class="fas fa-chevron-left"></i></a>
                            </c:if>

                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <c:choose>
                                <c:when test="${currentPage eq i}">
                                    <span class="current-page">${i}</span>
                                </c:when>
                                <c:otherwise>
                                    <a href="?page=${i}" class="page-number">${i}</a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>

                        <c:if test="${currentPage < totalPages}">
                            <a href="?page=${currentPage + 1}" class="prev-next"><i class="fas fa-chevron-right"></i></a>
                            </c:if>
                    </div>
                </div>
            </section>
        </section>
        <script>
            // JavaScript
            let debounceTimeout;

            function suggestAddress(input) {
                if (debounceTimeout) {
                    clearTimeout(debounceTimeout);
                }

                debounceTimeout = setTimeout(() => {
                    if (input.length < 3) {
                        document.getElementById('suggestions').innerHTML = '';
                        return;
                    }

                    fetch(`https://nominatim.openstreetmap.org/search?format=json&q=\${input}&addressdetails=1&countrycodes=VN`)
                            .then(response => response.json())
                            .then(data => {
                                let suggestions = document.getElementById('suggestions');
                                suggestions.innerHTML = '';

                                data.forEach(item => {
                                    let div = document.createElement('div');
                                    div.className = 'suggestion-item';
                                    div.textContent = item.display_name;
                                    div.onclick = () => {
                                        selectAddress(item.lat, item.lon, item.display_name);
                                        document.getElementById('addressInput').value = item.display_name;
                                    }
                                    suggestions.appendChild(div);
                                });
                            })
                            .catch(error => console.error('Error:', error));
                }, 100); // Đợi 500ms trước khi gọi API
            }


            function selectAddress(lat, lon, address) {

                document.getElementById("selectedlat").value = lat;
                document.getElementById("selectedlon").value = lon;
                document.getElementById('suggestions').innerHTML = '';
            }
            function checkLatLon() {
                const lat = document.getElementById('selectedlat').value;
                const lon = document.getElementById('selectedlon').value;
                if (!lat || !lon) {
                    const firstSuggestion = document.querySelector('#suggestions .suggestion-item');
                    if (firstSuggestion) {
                        firstSuggestion.click();
                    } else {

                        return false;
                    }
                }

                return true;
            }
            function useCurrentLocation() {
                if (navigator.geolocation) {
                    navigator.geolocation.getCurrentPosition(
                            function (position) {
                                document.getElementById("selectedlat").value = position.coords.latitude;
                                document.getElementById("selectedlon").value = position.coords.longitude;
                                document.getElementById("addressInput").value = "Vị trí hiện tại của bạn";
                                document.querySelector("form").submit();
                            },
                            function (error) {
                                alert("Không thể lấy vị trí hiện tại. Hãy kiểm tra quyền truy cập vị trí của trình duyệt.");
                            }
                    );
                } else {
                    alert("Trình duyệt của bạn không hỗ trợ lấy vị trí.");
                }
            }

        </script>
        <script>
           document.addEventListener("DOMContentLoaded", function () {
    // Select all elements with the description prefix
    document.querySelectorAll("[id^='description-']").forEach(element => {
        truncateText(element, 100); // Adjust the max length as needed
    });
});

function truncateText(element, maxLength) {
    const originalText = element.textContent.trim(); // Trim extra spaces

    // Check if the text length exceeds max length
    if (originalText.length > maxLength) {
        // Truncate the text and add ellipsis
        element.textContent = originalText.substring(0, maxLength) + "...";
    }
}

        </script>
        <script>
            // JavaScript


            function suggestAddress1(id, input) {
                let debounceTimeout;
                if (debounceTimeout) {
                    clearTimeout(debounceTimeout);
                }

                debounceTimeout = setTimeout(() => {
                    if (input.length < 3) {
                        document.getElementById(`suggestions-\${id}`).innerHTML = '';
                        return;
                    }

                    fetch(`https://nominatim.openstreetmap.org/search?format=json&q=\${input}&addressdetails=1&countrycodes=VN`)
                            .then(response => response.json())
                            .then(data => {
                                let suggestions = document.getElementById(`suggestions-\${id}`);
                                suggestions.innerHTML = '';

                                data.forEach(item => {
                                    let div = document.createElement('div');
                                    div.className = 'suggestion-item';
                                    div.textContent = item.display_name;
                                    div.onclick = () => {
                                        selectAddress1(id, item.lat, item.lon, item.display_name);
                                        document.getElementById(`addressInput-\${id}`).value = item.display_name;
                                    }
                                    suggestions.appendChild(div);
                                });
                            })
                            .catch(error => console.error('Error:', error));
                }, 100); // Delay 100ms before calling API
            }

// Function to select address from suggestions
            function selectAddress1(id, lat, lon, address) {
                document.getElementById(`selectedlat-\${id}`).value = lat;
                document.getElementById(`selectedlon-\${id}`).value = lon;
                document.getElementById(`suggestions-\${id}`).innerHTML = '';
            }



        </script>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                // Add validation for addRoomForm
                document.getElementById("addRoomForm").addEventListener("submit", function (e) {
                    if (!validateForm(this)) {
                        e.preventDefault();
                    }
                });

                // Add validation for each editRoomForm
                document.querySelectorAll("[id^='editRoomForm-']").forEach(function (form) {
                    form.addEventListener("submit", function (e) {
                        if (!validateForm(this)) {
                            e.preventDefault();
                        }
                    });
                });
            });

            // Validation function
            function validateForm(form) {
                clearErrors(form);  // Clear previous errors
                let isValid = true;

                const tenNhaTro = form.querySelector("input[name='tenNhaTro']");
                const diaChi = form.querySelector("input[name='diaChi']");
                const images = form.querySelector("input[name='images']");

                if (!tenNhaTro.value.trim()) {
                    showError(tenNhaTro, "Tên nhà trọ không được để trống");
                    isValid = false;
                }

                if (!diaChi.value.trim()) {
                    showError(diaChi, "Địa chỉ không được để trống");
                    isValid = false;
                }

                if (images && images.files.length > 5) {
                    showError(images, "Chỉ có thể tải lên tối đa 5 hình ảnh");
                    isValid = false;
                }

                return isValid;
            }

            // Show error function
            function showError(element, message) {
                const errorDiv = document.createElement("div");
                errorDiv.classList.add("error-message");
                errorDiv.style.color = "red";
                errorDiv.innerText = message;
                element.parentElement.appendChild(errorDiv);
                element.classList.add("is-invalid");
            }

            // Clear previous errors
            function clearErrors(form) {
                form.querySelectorAll(".error-message").forEach(function (errorDiv) {
                    errorDiv.remove();
                });
                form.querySelectorAll(".is-invalid").forEach(function (input) {
                    input.classList.remove("is-invalid");
                });
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
            //filter room by floor ko can submit


            //delete room confirm
            function confirmDelete(nhaTroId) {
                if (confirm("Bạn có chắc chắn muốn xóa nhà trọ này?")) {
                    // Set the NhaTro ID in the hidden form
                    document.getElementById("deleteNhaTroId").value = nhaTroId;
                    // Submit the form
                    document.getElementById("deleteForm").submit();
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
