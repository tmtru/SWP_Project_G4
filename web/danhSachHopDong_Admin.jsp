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
<% 
                String message = (String) request.getAttribute("message");
                if (message != null) {
            %>
            <div class="alert alert-info" style="color: green; font-weight: bold; margin-top: 10px;">
                <%= message %>
            </div>
            <%
                }
            %>


                <div class="service-section">
                    <table class="service-table" id="contractTable">
                        <thead>
                            <tr>
                                <th>ID Hợp Đồng</th>
                                <th>Tên Phòng Trọ</th>
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
                            <td colspan="6">Không có hợp đồng nào.</td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>

                <div class="pagination">
                    <button id="prevBtn" onclick="changePage(-1)" disabled>Trước</button>
                    <span id="pageNum">1</span>
                    <button id="nextBtn" onclick="changePage(1)">Tiếp</button>
                </div>
            </div>
        </div>

        <script>
            const itemsPerPage = 6; // Số hợp đồng mỗi trang
            let currentPage = 1;

            function paginate() {
                const rows = document.querySelectorAll("#contractBody tr");
                const totalPages = Math.ceil(rows.length / itemsPerPage);

                // Ẩn tất cả các hàng
                rows.forEach((row, index) => {
                    row.style.display = (index >= (currentPage - 1) * itemsPerPage && index < currentPage * itemsPerPage) ? "" : "none";
                });

                // Cập nhật trạng thái nút
                document.getElementById("prevBtn").disabled = currentPage === 1;
                document.getElementById("nextBtn").disabled = currentPage === totalPages;
                document.getElementById("pageNum").textContent = currentPage;
            }

            function changePage(direction) {
                currentPage += direction;
                paginate();
            }

            document.addEventListener("DOMContentLoaded", paginate);
            function searchTable() {
                const input = document.getElementById("searchInput").value.toLowerCase();
                const rows = document.querySelectorAll("#contractBody tr:not(#noResultRow)");
                let hasResult = false;

                rows.forEach(row => {
                    const tenPhong = row.querySelector("td:nth-child(2)").textContent.toLowerCase();
                    if (tenPhong.includes(input)) {
                        row.style.display = ""; // Hiển thị hàng phù hợp
                        hasResult = true;
                    } else {
                        row.style.display = "none"; // Ẩn hàng không phù hợp
                    }
                });

                checkNoResult(hasResult); // Kiểm tra có kết quả hay không
            }

            function filterTable() {
                const filterTrangThaiPhong = document.getElementById("filterTrangThaiPhong").value;
                const filterTrangThaiHopDong = document.getElementById("filterTrangThaiHopDong").value;
                const rows = document.querySelectorAll("#contractBody tr:not(#noResultRow)");
                let hasResult = false;

                rows.forEach(row => {
                    const trangThaiPhong = row.querySelector("td:nth-child(5)").textContent;
                    const trangThaiHopDong = row.querySelector("td:nth-child(5)").textContent;

                    const matchPhong = filterTrangThaiPhong === "" || trangThaiPhong === filterTrangThaiPhong;
                    const matchHopDong = filterTrangThaiHopDong === "" || trangThaiHopDong === filterTrangThaiHopDong;

                    if (matchPhong && matchHopDong) {
                        row.style.display = ""; // Hiển thị hàng phù hợp
                        hasResult = true;
                    } else {
                        row.style.display = "none"; // Ẩn hàng không phù hợp
                    }
                });

                checkNoResult(hasResult); // Kiểm tra có kết quả hay không
            }

            function checkNoResult(hasResult) {
                const rows = document.querySelectorAll("#contractBody tr:not(#noResultRow)");
                let visibleRows = Array.from(rows).filter(row => row.style.display !== "none");
                const noResultRow = document.getElementById("noResultRow");

                if (visibleRows.length === 0) {
                    // Không có kết quả, hiển thị hàng "Không có hợp đồng nào"
                    if (!noResultRow) {
                        const tbody = document.getElementById("contractBody");
                        const tr = document.createElement("tr");
                        tr.id = "noResultRow";
                        tr.innerHTML = '<td colspan="6" style="text-align: center; vertical-align: middle;">Không có hợp đồng nào.</td>';
                        tbody.appendChild(tr);
                    }
                } else if (noResultRow) {
                    // Có kết quả, xóa hàng "Không có hợp đồng nào"
                    noResultRow.remove();
                }
            }

        </script>

    </body>
</html>
