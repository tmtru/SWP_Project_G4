<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.HopDong" %>


<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Danh Sách Các Hợp Đồng</title>
        <link rel="stylesheet" href="css/styledichvuthuephong.css">

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

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
                display: flex;
                align-items: center;
                justify-content: center;
                gap: 8px;
                margin-top: 20px;
            }

            .pagination button,
            .pagination .page-number,
            .pagination .current-page {
                padding: 8px 12px;
                border: none;
                background-color: #536DFE;
                color: #fff;
                font-size: 16px;
                cursor: pointer;
                text-decoration: none;
                border-radius: 6px;
            }

            .pagination .current-page {
                background-color: #3D5AFE;
                font-weight: bold;
            }

            .pagination button:disabled {
                opacity: 0.5;
                cursor: default;
            }

            .pagination .page-number:hover,
            .pagination button:not(:disabled):hover {
                background-color: #3D5AFE;
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
            .reject-icon {
                color: red;
                font-size: 25px;
                margin-left: 10px;
                cursor: pointer;
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
                <div class="filter-section">
                    <input type="text" id="searchInput" placeholder="Tìm theo tên phòng..." onkeyup="searchTable()">
                    <select id="filterTrangThaiHopDong" onchange="filterTable()">
                        <option value="">Tất cả trạng thái hợp đồng</option>
                        <option value="pending">pending</option>
                        <option value="accept">accept</option>
                        <option value="active">active</option>
                        <option value="reject">reject</option>
                        <option value="expired">expired</option>
                    </select>
                </div>

                <div class="service-section">
                    <table class="service-table" id="contractTable">
                        <thead>
                            <tr>
                                <th>ID Hợp Đồng</th>
                                <th>Tên Phòng Trọ</th>
                                <th>Tên Khách Thuê</th>
                                <th>Ngày Bắt Đầu</th>
                                <th>Ngày Hết Hạn</th>
                                <th>Trạng Thái</th>
                                <th>Hành Động</th>
                            </tr>
                        </thead>
                        <tbody id="contractBody">
                        <% 
                            List<HopDong> hopDongList = (List<HopDong>) request.getAttribute("hopDongList");
                            if (hopDongList != null && !hopDongList.isEmpty()) {
                                for (HopDong hopDong : hopDongList) {
                        %>
                        <tr>
                            <td><%= hopDong.getID_HopDong() %></td>
                            <td><%= hopDong.getTenPhongTro() %></td>
                            <td><%= hopDong.getTen_khach() %></td>
                            <td><%= hopDong.getNgay_gia_tri() != null ? hopDong.getNgay_gia_tri() : "N/A" %></td>
                            <td><%= hopDong.getNgay_het_han() != null ? hopDong.getNgay_het_han() : "N/A" %></td>
                            <td><%= hopDong.getStatus() %></td>
                            <td>
                                <form action="DanhSachCacHopDongByAdmin" method="post">
                                    <input type="hidden" name="hopDongId" value="<%= hopDong.getID_HopDong() %>">
                                    <button type="submit">Xem Chi Tiết</button>
                                </form>
                                <form action="KetThucHopDongSomByAdmin" method="get" style="display:inline;">
                                    <input type="hidden" name="hopDongId" value="<%= hopDong.getID_HopDong() %>">
                                    <button type="submit" onclick="return confirm('Bạn có chắc chắn muốn kết thúc hợp đồng này không?')" style="background: none; border: none; cursor: pointer;">
                                        <i class="fas fa-times-circle reject-icon"></i>
                                    </button>
                                </form>
                            </td>
                        </tr>
                        <% 
                                }
                            } else {
                        %>
                        <tr>
                            <td colspan="7">Không có hợp đồng nào.</td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>

                <div class="pagination">
                    <button id="prevBtn" onclick="changePage(-1)" disabled><i class="fas fa-chevron-left"></i></button>
                    <span id="pageNum">1</span>
                    <button id="nextBtn" onclick="changePage(1)"><i class="fas fa-chevron-right"></i></button>
                </div>
            </div>
        </div>

        <script>
            const itemsPerPage = 6;
            let currentPage = 1;
            let filteredRows = [];

            function paginate() {
                const totalPages = Math.ceil(filteredRows.length / itemsPerPage);
                document.querySelectorAll("#contractBody tr").forEach(row => row.style.display = "none");
                const start = (currentPage - 1) * itemsPerPage;
                const end = currentPage * itemsPerPage;
                filteredRows.slice(start, end).forEach(row => row.style.display = "");
                document.getElementById("prevBtn").disabled = currentPage === 1;
                document.getElementById("nextBtn").disabled = currentPage === totalPages || totalPages === 0;
                document.getElementById("pageNum").textContent = currentPage;
            }

            function changePage(direction) {
                currentPage += direction;
                paginate();
            }

            function searchTable() {
                const input = document.getElementById("searchInput").value.toLowerCase();
                const rows = document.querySelectorAll("#contractBody tr");
                filteredRows = Array.from(rows).filter(row => {
                    const tenPhong = row.querySelector("td:nth-child(2)").textContent.toLowerCase();
                    return tenPhong.includes(input);
                });
                checkNoResult(filteredRows.length > 0);
                currentPage = 1;
                paginate();
            }

            function filterTable() {
                const filterTrangThaiHopDong = document.getElementById("filterTrangThaiHopDong").value;
                const allRows = Array.from(document.querySelectorAll("#contractBody tr:not(#noResultRow)"));

                // Kiểm tra nếu trạng thái được chọn thì chỉ lọc theo trạng thái, nếu không hiển thị tất cả hàng
                if (filterTrangThaiHopDong) {
                    filteredRows = allRows.filter(row => {
                        const trangThaiHopDong = row.querySelector("td:nth-child(6)").textContent;
                        return trangThaiHopDong === filterTrangThaiHopDong;
                    });
                } else {
                    filteredRows = allRows; // Hiển thị tất cả nếu không có bộ lọc
                }

                checkNoResult(filteredRows.length > 0);
                currentPage = 1; // Đặt lại trang về 1
                paginate(); // Phân trang sau khi lọc
            }

            function checkNoResult(hasResult) {
                const noResultRow = document.getElementById("noResultRow");
                if (!hasResult) {
                    if (!noResultRow) {
                        const tbody = document.getElementById("contractBody");
                        const tr = document.createElement("tr");
                        tr.id = "noResultRow";
                        tr.innerHTML = '<td colspan="7" style="text-align: center; vertical-align: middle;">Không có hợp đồng nào.</td>';
                        tbody.appendChild(tr);
                    }
                } else if (noResultRow) {
                    noResultRow.remove();
                }
            }

            document.addEventListener("DOMContentLoaded", () => {
                filteredRows = Array.from(document.querySelectorAll("#contractBody tr"));
                paginate();
            });
        </script>
    </body>
</body>
</html>
