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
        <jsp:include page="sidebarHoaDonManagement.jsp"></jsp:include>


            <section class="ftco-section">

                <div class="m-0">
                <c:if test="${room!=null}">
                    <div class="my-4 ml-4 ">
                        <h4 class="">Tạo hóa đơn hàng tháng </h4>
                    </div>
                </c:if>
                <div class="container mt-4">
                    <h3 class="text-center mb-4">Thiết lập các số liệu dịch vụ tạo hóa đơn</h3>
                    <div id="validationAlert" class="alert alert-danger d-none" role="alert"></div>
                    <form action="addfastinvoice" method="post" id="addFastInvoiceForm">
                        <!-- Checkbox for Room Rent Inclusion -->
                        <input type="hidden" name="step" value="2">
                        <div class="text-center mb-2">
                            <button type="submit" class="btn btn-success">Tạo Hóa Đơn</button>
                        </div>
                        <div class="form-check mb-4">
                            <input type="checkbox" name="includeRoomRent" id="includeRoomRent" class="form-check-input">
                            <label for="includeRoomRent" class="form-check-label">
                                Tính Tiền Phòng Cho Tất Cả Các Phòng
                            </label>
                        </div>

                        <c:forEach var="room" items="${selectedRooms}">
                            <div class="card mb-4" style="background-color: #f8f9fa; border: 1px solid #ced4da;"> <!-- Light gray background with border -->
                                <div class="card-body">
                                    <%
                                        int idPhong = (Integer) ((Phong) pageContext.getAttribute("room")).getID_Phong();
                                        HoaDonDAO hddao = new HoaDonDAO();
                                        int reading = hddao.getLatestElectricityReadingByPhongID(idPhong);
                                        request.setAttribute("chiSo", reading);
                                    %>

                                    <div class="text-center mb-3"> <!-- Center the room name -->
                                        <h5 class="card-title mb-0">Phòng: ${room.tenPhongTro}</h5> <!-- Room name as title -->
                                    </div>

                                    <div class="row mb-3">
                                        <div class="col">
                                            <div class="form-group mb-0">
                                                <label for="chiSoCu_dien_${room.ID_Phong}" class="small">Chỉ số cũ</label>
                                                <input type="number" placeholder="Chỉ số cũ" id="chiSoCu_dien_${room.ID_Phong}" name="chiSoCu_dien_${room.ID_Phong}" class="form-control" value="${chiSo}" readonly>
                                            </div>
                                        </div>
                                        <div class="col">
                                            <div class="form-group mb-0">
                                                <label for="chiSoMoi_dien_${room.ID_Phong}" class="small">Chỉ số mới</label>
                                                <input type="number" placeholder="Chỉ số mới" id="chiSoMoi_dien_${room.ID_Phong}" name="chiSoMoi_dien_${room.ID_Phong}" class="form-control" required>
                                                <div class="invalid-feedback" id="feedback_${room.ID_Phong}"></div> <!-- Feedback for invalid input -->
                                            </div>
                                        </div>
                                    </div>
                                    
                                </div>
                            </div>

                            <script>
                                // Validate input for each room
                                document.addEventListener('DOMContentLoaded', function () {
                                    const chiSoMoiInput = document.getElementById('chiSoMoi_dien_${room.ID_Phong}');
                                    const chiSoCu = parseInt(document.getElementById('chiSoCu_dien_${room.ID_Phong}').value);

                                    chiSoMoiInput.addEventListener('input', function () {
                                        const chiSoMoi = parseInt(this.value);
                                        const feedback = document.getElementById('feedback_${room.ID_Phong}');

                                        // Reset previous feedback
                                        feedback.textContent = '';
                                        this.classList.remove('is-invalid');
                                        this.classList.remove('is-valid');

                                        if (isNaN(chiSoMoi) || this.value.trim() === '') {
                                            feedback.textContent = 'Chỉ số mới không được để trống.';
                                            this.classList.add('is-invalid');
                                        } else if (chiSoMoi < chiSoCu) {
                                            feedback.textContent = 'Chỉ số mới không được nhỏ hơn chỉ số cũ.';
                                            this.classList.add('is-invalid');
                                        } else {
                                            this.classList.add('is-valid'); // Add valid class if input is correct
                                        }
                                    });
                                });
                            </script>





                        </c:forEach>




                        <!-- Submit Button -->

                    </form>
                </div>




            </div>

        </section>

        <script>
            document.getElementById('addFastInvoiceForm').addEventListener('submit', function (event) {
                let isValid = true;
                const chiSoMoiInputs = document.querySelectorAll('input[id^="chiSoMoi_dien_"]');
                const validationAlert = document.getElementById('validationAlert');

                // Reset alert
                validationAlert.classList.add('d-none');
                validationAlert.textContent = '';

                chiSoMoiInputs.forEach(function (input) {
                    if (input.classList.contains('is-invalid') || input.value.trim() === '') {
                        isValid = false;
                        input.classList.add('is-invalid');
                    }
                });

                if (!isValid) {
                    event.preventDefault();
                    validationAlert.textContent = 'Vui lòng điền đúng tất cả các chỉ số mới trước khi nộp.';
                    validationAlert.classList.remove('d-none'); // Show the alert
                }
            });
        </script>
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
            function updateInvoiceOnRoomChange() {
                const selectedRoomId = $('#phong').val();
                // Gửi yêu cầu AJAX để lấy thông tin hợp đồng và số người
                $.ajax({
                    url: '/your-endpoint/getContractInfo',
                    type: 'GET',
                    data: {idPhong: selectedRoomId},
                    dataType: 'json',
                    success: function (data) {
                        // Cập nhật thông tin hợp đồng và số người vào giao diện
                        if (data) {
                            $('#contractInfoText').text(data.contractId);
                            $('#numberOfPeopleText').text(data.numberOfPeople);
                        }
                    },
                    error: function (xhr, status, error) {
                        console.error('Error fetching contract info:', error);
                    }
                });
            }
        </script>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                var today = new Date().toISOString().split('T')[0]; // Lấy ngày hôm nay dưới định dạng yyyy-mm-dd
                document.getElementById("ngayHoaDon").value = today; // Set giá trị cho input ngày
            });
        </script>



    </body>
</html>


