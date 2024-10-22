<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.HopDong" %>
<%@ page import="java.util.Date" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Danh Sách Các Hợp Đồng - Manager</title>
        <link rel="stylesheet" href="css/styledichvuthuephong.css">
        <style>
            .container {
                max-width: 1255px !important;
                margin-right: 0px !important;
                margin-left: 250px !important;
            }
            .people-section h2 {
                font-size: 18px !important;
                margin: 0 !important;
            }
            .alert {
                color: red;
            }
            .success {
                color: green;
            }
            .service-table {
                width: 100%;
                border-collapse: collapse;
            }
            .service-table th, .service-table td {
                border: 1px solid #ccc;
                padding: 10px;
                text-align: left;
            }
            .service-table th {
                background-color: #f2f2f2;
            }
            .service-table td form {
                display: inline;
            }
            .service-table td button {
                padding: 10px 15px;
                background-color: #007bff;
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
            }
            .service-table td button:hover {
                background-color: #0056b3;
                opacity: 0.8;
            }
            .bell-icon, .reject-icon {
                color: red;
                font-size: 25px;
                margin-left: 10px;
                cursor: pointer;
            }
            .modal {
                display: none;
                position: fixed;
                z-index: 1;
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0,0,0,0.4);
                justify-content: center;
                align-items: center;
            }
            .modal-content {
                background-color: white;
                padding: 20px;
                border-radius: 10px;
                text-align: center;
                width: 300px;
                max-width: 30%;
                margin: auto;
            }
            .modal button {
                margin: 5px;
                padding: 5px 10px;
            }
            .pagination {
                text-align: center;
                margin: 20px 0;
            }
            .pagination a {
                margin: 0 5px;
                padding: 8px 12px;
border: 1px solid #007bff;
                color: #007bff;
                text-decoration: none;
            }
            .pagination a:hover {
                background-color: #007bff;
                color: white;
            }
            .pagination a.active {
                background-color: #007bff;
                color: white;
            }
            .alert {
                background-color: white;
                color: #721c24;
                border: 1px solid #f5c6cb;
                border-radius: 5px;
                display: flex;
                justify-content: center;
            }
            .filter-section {
        margin-bottom: 20px;
        display: flex;
        gap: 10px;
    }
    .filter-section input, .filter-section select {
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
    }
        </style>
    </head>
    <body>
        <jsp:include page="adminUserManagement/sidebar.jsp"></jsp:include>
            <div class="container">
                <div class="header">
                    <h1>Danh Sách Các Hợp Đồng</h1>
                    <div class="buttons">
                        <button class="btn return" onclick="window.history.back();">Quay về</button>
                    </div>
                </div>
                <c:if test="${not empty message}">
                <div class="alert">${message}</div>
            </c:if>
            <div class="filter-section">
                <input type="text" id="searchInput" placeholder="Tìm theo tên phòng..." onkeyup="searchTable()">
                <select id="filterTrangThaiPhong" onchange="filterTable()">
                    <option value="">Tất cả trạng thái phòng</option>
                    <option value="Phòng đã được thuê">Phòng đã được thuê</option>
                    <option value="Phòng còn trống">Phòng còn trống</option>
                    <option value="Hợp đồng sắp hết hạn">Hợp đồng sắp hết hạn</option>
                </select>
                <select id="filterTrangThaiHopDong" onchange="filterTable()">
                    <option value="">Tất cả trạng thái hợp đồng</option>
                    <option value="waiting">waiting</option>
                    <option value="pending">pending</option>
                    <option value="accept">accept</option>
                    <option value="active">active</option>
                    <option value="reject">reject</option>
                    <option value="expired">expired</option>
                </select>
            </div>

            <div class="service-section">
                <table class="service-table" id="hopDongTable">
                    <thead>
                        
                        <tr>
                            <th>Số Thứ Tự</th>
                            <th>Tên Phòng Trọ</th>
                            <th>Ngày Bắt Đầu</th>
<th>Ngày Hết Hạn</th>
                            <th>Giá phòng</th>
                            <th>Trạng Thái Phòng</th>
                            <th>Trạng Thái Hợp Đồng</th>
                            <th>Hành Động</th>
                        </tr>
                    </thead>
                    <tbody id="hopDongBody">
                        <%
                            List<HopDong> hopDongList = (List<HopDong>) request.getAttribute("hopDongList");
                            int index = 1;
                            Date currentDate = new Date();

                            if (hopDongList != null && !hopDongList.isEmpty()) {
                                for (HopDong hopDong : hopDongList) {
                                    Date ngayHetHan = hopDong.getNgay_het_han();
                                    boolean showBellIcon = false;
                                    boolean showRejectIcon = false;
                                    String reasonReject = hopDong.getLy_do();

                                    // Kiểm tra nếu ngày hết hạn hợp đồng cách ngày hiện tại 20 ngày và trạng thái là "accept"
                                    if ("accept".equalsIgnoreCase(hopDong.getStatus()) && ngayHetHan != null && hopDong.getPhongTro() != null) {
                                        long differenceInMillies = ngayHetHan.getTime() - currentDate.getTime();
                                        long differenceInDays = differenceInMillies / (1000 * 60 * 60 * 24);

                                        if (differenceInDays <= 20 && !"T".equals(hopDong.getPhongTro().getTrang_thai()) && !"DT".equals(hopDong.getPhongTro().getTrang_thai())) {
                                            showBellIcon = true;
                                        }
                                    }

                                    // Kiểm tra trạng thái "reject"
                                    if ("reject".equalsIgnoreCase(hopDong.getStatus())) {
                                        showRejectIcon = true;
                                    }
                        %>
                        <tr>
                            <td><%= index++ %></td>
                            <td><%= hopDong.getPhongTro() != null ? hopDong.getPhongTro().getTenPhongTro() : "N/A" %></td>
                            <td><%= hopDong.getNgay_gia_tri() != null ? hopDong.getNgay_gia_tri() : "N/A" %></td>
                            <td><%= hopDong.getNgay_het_han() != null ? hopDong.getNgay_het_han() : "N/A" %></td>
                            <td><%= hopDong.getPhongTro() != null ? hopDong.getPhongTro().getGia() : "N/A" %></td>
                            <td>
                                <%= hopDong.getPhongTro() != null ? 
                                    ("D".equals(hopDong.getPhongTro().getTrang_thai()) ? "Phòng đã được thuê" :
"T".equals(hopDong.getPhongTro().getTrang_thai()) ? "Phòng còn trống" : 
                                     "DT".equals(hopDong.getPhongTro().getTrang_thai()) ? "Hợp đồng sắp hết hạn" : "N/A") : "N/A" %>
                            </td>

                            <td><%= hopDong.getStatus() %></td>
                            <td>
                                <form action="ChiTietHopDongByManager" method="post">
                                    <input type="hidden" name="hopDongId" value="<%= hopDong.getID_HopDong() %>">
                                    <button type="submit">Xem Chi Tiết</button>
                                </form>
                                <%
                                    if (showBellIcon) {
                                %>
                                <span class="bell-icon" onclick="showModal('<%= hopDong.getID_HopDong() %>')">🔔</span>
                                <%
                                    }
                                    if (showRejectIcon) {
                                %>
                                <span class="reject-icon" onclick="showRejectReason('<%= hopDong.getLy_do() %>')">❌</span>
                                <%
                                    }
                                %>
                            </td>
                        </tr>
                        <%
                                }
                            } else {
                        %>
                        <tr>
                            <td colspan="8">Không có hợp đồng nào.</td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
                    <p id="noResultsRow" style="display:none;">Không tìm thấy hợp đồng nào phù hợp với tìm kiếm.</p>

            </div>
            <div id="pagination" class="pagination"></div>
        </div>

        <!-- Modal để hiển thị hộp thoại -->
        <div id="confirmationModal" class="modal">
            <div class="modal-content">
                <h3>Bạn muốn gia hạn hợp đồng này không?</h3>
                <form id="confirmationForm" method="post" action="DanhSachCacHopDongByManager">
                    <input type="hidden" id="hopDongId" name="hopDongId" value="">
                    <button type="submit" name="confirm" value="yes">Có</button>
                    <button type="submit" name="confirm" value="no">Không</button>
                    <button type="button" onclick="closeModal()">Đóng</button>
                </form>
            </div>
        </div>

        <!-- Modal để hiển thị lý do từ chối -->
        <div id="rejectReasonModal" class="modal">
            <div class="modal-content">
                <h3>Lý do từ chối:</h3>
                <p id="rejectReasonText"></p>
<button onclick="closeRejectReasonModal()">Đóng</button>
            </div>
        </div>

        <script>
            const rowsPerPage = 6; // Số dòng hiển thị mỗi trang
let currentPage = 1;
let filteredRows = []; // Mảng chứa các hàng đã lọc

function paginateTable() {
    const tbody = document.getElementById('hopDongBody');
    const rows = filteredRows.length ? filteredRows : Array.from(tbody.getElementsByTagName('tr')); // Sử dụng hàng đã lọc hoặc tất cả hàng
    const totalRows = rows.length;
    const totalPages = Math.ceil(totalRows / rowsPerPage); // Tổng số trang

    // Ẩn tất cả các hàng trước
    Array.from(tbody.getElementsByTagName('tr')).forEach(row => {
        row.style.display = 'none';
    });

    // Hiển thị hàng cho trang hiện tại
    const startRow = (currentPage - 1) * rowsPerPage;
    const endRow = Math.min(currentPage * rowsPerPage, totalRows);

    for (let i = startRow; i < endRow; i++) {
        rows[i].style.display = ''; // Hiển thị các hàng thuộc trang hiện tại
    }

    // Hiển thị phân trang
    displayPagination(totalPages);
}

function displayPagination(totalPages) {
    const paginationDiv = document.getElementById('pagination');
    paginationDiv.innerHTML = ''; // Xóa nội dung cũ của phân trang

    if (totalPages <= 1) return; // Không hiển thị phân trang nếu chỉ có 1 trang

    // Nút quay lại
    const prevLink = document.createElement('a');
    prevLink.href = '#';
    prevLink.innerHTML = '&laquo;';
    prevLink.classList.add('prev');
    prevLink.addEventListener('click', function (e) {
        e.preventDefault();
        if (currentPage > 1) {
            currentPage--;
            paginateTable(); // Cập nhật phân trang
        }
    });
    paginationDiv.appendChild(prevLink);

    // Tạo các trang
    for (let i = 1; i <= totalPages; i++) {
        const pageLink = document.createElement('a');
        pageLink.href = '#';
        pageLink.textContent = i;

        if (i === currentPage) {
            pageLink.classList.add('active'); // Đánh dấu trang hiện tại
        }

        pageLink.addEventListener('click', function (e) {
            e.preventDefault();
            currentPage = i;
            paginateTable(); // Cập nhật phân trang
        });

        paginationDiv.appendChild(pageLink);
    }

    // Nút tiếp theo
    const nextLink = document.createElement('a');
    nextLink.href = '#';
    nextLink.innerHTML = '&raquo;';
    nextLink.classList.add('next');
    nextLink.addEventListener('click', function (e) {
        e.preventDefault();
        if (currentPage < totalPages) {
            currentPage++;
            paginateTable(); // Cập nhật phân trang
        }
    });
    paginationDiv.appendChild(nextLink);
}

function searchTable() {
    const searchInput = document.getElementById('searchInput').value.toLowerCase();
const tbody = document.getElementById('hopDongBody');
    const rows = Array.from(tbody.getElementsByTagName('tr'));
    const noResultsRow = document.getElementById('noResultsRow');
    
    filteredRows = []; // Đặt lại filteredRows khi tìm kiếm mới

    let matchCount = 0;

    rows.forEach(row => {
        if (row.id !== 'noResultsRow') {
            const tenPhongCell = row.getElementsByTagName('td')[1]; // Tên phòng trọ
            if (tenPhongCell) {
                const tenPhongText = tenPhongCell.textContent.toLowerCase();
                if (tenPhongText.includes(searchInput)) {
                    row.style.display = ''; // Hiển thị dòng nếu khớp
                    filteredRows.push(row); // Lưu kết quả vào filteredRows
                    matchCount++;
                } else {
                    row.style.display = 'none'; // Ẩn dòng nếu không khớp
                }
            }
        }
    });

    if (matchCount === 0) {
        noResultsRow.style.display = ''; // Hiển thị dòng thông báo nếu không có kết quả
        tbody.style.display = 'none'; // Ẩn toàn bộ bảng danh sách hợp đồng
    } else {
        noResultsRow.style.display = 'none'; // Ẩn dòng thông báo nếu có kết quả
        tbody.style.display = ''; // Hiển thị lại bảng danh sách hợp đồng
    }

    currentPage = 1;
    paginateTable(); // Cập nhật lại phân trang
}


function filterTable() {
    const trangThaiPhongFilter = document.getElementById('filterTrangThaiPhong').value.toLowerCase().trim();
    const trangThaiHopDongFilter = document.getElementById('filterTrangThaiHopDong').value.toLowerCase().trim();
    const tbody = document.getElementById('hopDongBody');
    const rows = Array.from(tbody.getElementsByTagName('tr'));
    const noResultsRow = document.getElementById('noResultsRow');
    filteredRows = []; // Đặt lại filteredRows khi lọc mới

    let matchCount = 0;

    rows.forEach(row => {
        const trangThaiPhongCell = row.getElementsByTagName('td')[5];
        const trangThaiHopDongCell = row.getElementsByTagName('td')[6];

        let matchTrangThaiPhong = !trangThaiPhongFilter || (trangThaiPhongCell && trangThaiPhongCell.textContent.toLowerCase().includes(trangThaiPhongFilter));
        let matchTrangThaiHopDong = !trangThaiHopDongFilter || (trangThaiHopDongCell && trangThaiHopDongCell.textContent.toLowerCase().includes(trangThaiHopDongFilter));

        if (matchTrangThaiPhong && matchTrangThaiHopDong) {
            row.style.display = '';
            filteredRows.push(row); // Lưu kết quả vào filteredRows
            matchCount++;
        } else {
            row.style.display = 'none';
        }
    });

    if (matchCount === 0) {
        noResultsRow.style.display = '';
        tbody.style.display = 'none';
    } else {
        noResultsRow.style.display = 'none';
        tbody.style.display = '';
    }

    currentPage = 1;
paginateTable();
}


// Khởi tạo phân trang khi tải trang
window.onload = paginateTable;


// Hiển thị modal và truyền ID hợp đồng
            function showModal(hopDongId) {
                document.getElementById('hopDongId').value = hopDongId;
                document.getElementById('confirmationModal').style.display = 'flex';
            }

// Đóng modal
            function closeModal() {
                document.getElementById('confirmationModal').style.display = 'none';
            }

// Hiển thị lý do từ chối
            function showRejectReason(reason) {
                document.getElementById('rejectReasonText').innerText = reason;
                document.getElementById('rejectReasonModal').style.display = 'flex';
            }

// Đóng modal lý do từ chối
            function closeRejectReasonModal() {
                document.getElementById('rejectReasonModal').style.display = 'none';
            }

        </script>
    </body>
</html>