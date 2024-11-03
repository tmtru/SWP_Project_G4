<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="model.HopDong" %>
<%@ page import="model.Phong" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Calendar</title>
        <link rel="stylesheet" href="css/styledichvuthuephong.css">
        <style>
            .container {
                max-width: 1255px !important;
                margin-right: 0px !important;
                margin-left: 250px !important;
            }
            h1, h2 {
                text-align: center;
                color: #333;
                font-family: Arial, sans-serif;
            }
            .header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 20px;
            }
            .buttons button {
                background-color: #4CAF50;
                color: white;
                border: none;
                padding: 10px 15px;
                font-size: 16px;
                cursor: pointer;
                border-radius: 5px;
                transition: background-color 0.3s;
            }
            .buttons button:hover {
                background-color: #45a049;
            }
            .calendar-navigation {
                text-align: center;
                margin: 15px 0;
            }
            .calendar-navigation a {
                color: #333;
                background-color: #e7e7e7;
                border: 1px solid #ddd;
                padding: 8px 12px;
                text-decoration: none;
                border-radius: 5px;
                margin: 0 5px;
                font-family: Arial, sans-serif;
                transition: background-color 0.3s;
            }
            .calendar-navigation a:hover {
                background-color: #ddd;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 15px;
                background-color: #ffffff;
                border-radius: 8px;
                overflow: hidden;
                font-family: Arial, sans-serif;
            }
            th, td {
                padding: 15px;
                text-align: center;
                border: 1px solid #eee;
            }
            th {
                background-color: #4CAF50;
                color: white;
            }
            td {
                background-color: #fafafa;
                cursor: pointer;
            }
            .dot {
                height: 15px;
                width: 15px;
                border-radius: 50%;
                display: inline-block;
                margin-left: 5px;
            }
            .yellow-dot {
                background-color: yellow;
            }
            .green-dot {
                background-color: green;
            }
            .red-dot {
                background-color: red;
            }
            #roomListModal {
                display: none;
                position: fixed;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                background-color: white;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 4px 20px rgba(0, 0, 0, 0.3);
                z-index: 1000;
                max-height: 80vh;
                overflow: auto;
                width: 90%;
                max-width: 600px;
            }

            #roomListModal h3 {
                text-align: center;
                margin-bottom: 20px;
                color: #4CAF50;
                font-family: Arial, sans-serif;
            }

            #roomList {
                margin-bottom: 20px;
            }

            #roomList div {
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 5px;
                margin-bottom: 10px;
                background-color: #f9f9f9;
                transition: background-color 0.3s;
            }

            #roomList div:hover {
                background-color: #e9f9e9;
            }

            #roomListModal button {
                display: block;
                width: 100%;
                padding: 10px;
                background-color: #4CAF50;
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px;
                transition: background-color 0.3s;
            }

            #roomListModal button:hover {
                background-color: #45a049;
            }
            .current-date {
                background-color: #d4edda; 
                font-weight: bold; 
                border-radius: 5px; 
            }

        </style>
    </head>
    <body>
        <jsp:include page="adminUserManagement/sidebar.jsp"></jsp:include>
            <div class="container">
                <div class="header">
                    <h1>Lịch Quản Lý Phòng Trọ</h1>
                    <div class="buttons">
                        <button class="btn return" onclick="window.location.href = 'DanhSachCacHopDongByManager'">Quay về</button>
                    </div>
                </div>

            <%
                int currentYear = (int) request.getAttribute("currentYear");
                int currentMonth = (int) request.getAttribute("currentMonth");
                int prevMonth = (currentMonth == 1) ? 12 : currentMonth - 1;
                int nextMonth = (currentMonth == 12) ? 1 : currentMonth + 1;
                int prevYear = (currentMonth == 1) ? currentYear - 1 : currentYear;
                int nextYear = (currentMonth == 12) ? currentYear + 1 : currentYear;
            %>

            <h2>Calendar for <%= currentMonth %>/<%= currentYear %></h2>

            <div class="calendar-navigation">
                <a href="?month=<%= prevMonth %>&year=<%= prevYear %>">Previous Month</a>
                <a href="?month=<%= nextMonth %>&year=<%= nextYear %>">Next Month</a>
            </div>

            <div class="calendar-navigation">
                <a href="?month=<%= currentMonth %>&year=<%= currentYear - 1 %>">Previous Year</a>
                <a href="?month=<%= currentMonth %>&year=<%= currentYear + 1 %>">Next Year</a>
            </div>

            <table>
                <tr>
                    <th>Mon</th>
                    <th>Tue</th>
                    <th>Wed</th>
                    <th>Thu</th>
                    <th>Fri</th>
                    <th>Sat</th>
                    <th>Sun</th>
                </tr>
                <%
                    int daysInMonth = (int) request.getAttribute("daysInMonth");
                    List<HopDong> hopDongList = (List<HopDong>) request.getAttribute("hopDongList");
                    List<Phong> danhSachPhongTroTrong = (List<Phong>) request.getAttribute("danhSachPhongTroTrong");
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(currentYear, currentMonth - 1, 1);

                    int firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK) - 1;
                    int day = 1;

                    // Get today's date for comparison
                    Calendar today = Calendar.getInstance();
                    int currentDay = today.get(Calendar.DAY_OF_MONTH);
                    int currentMonthToday = today.get(Calendar.MONTH);
                    int currentYearToday = today.get(Calendar.YEAR);

                    for (int week = 0; week < 6; week++) {
                        out.print("<tr>");
                        for (int i = 0; i < 7; i++) {
                            if (week == 0 && i < firstDayOfMonth || day > daysInMonth) {
                                out.print("<td></td>");
                            } else {
                                calendar.set(currentYear, currentMonth - 1, day);
                                Date currentDate = calendar.getTime();

                                boolean hasYellowDot = false;
                                boolean hasGreenDot = false;
                                boolean hasRedDot = false;

                                for (HopDong hopDong : hopDongList) {
                                    Phong phongTro = hopDong.getPhongTro();
                                    Date ngayHetHan = hopDong.getNgay_het_han();
                                    String trangThai = phongTro.getTrang_thai();
                                    Date ngayGiaTri = hopDong.getNgay_gia_tri();

                                    if (ngayHetHan != null && ngayGiaTri != null) {
                                        long daysRemaining = (ngayHetHan.getTime() - currentDate.getTime()) / (1000 * 60 * 60 * 24);
                                        if (!currentDate.before(ngayGiaTri) && !currentDate.after(ngayHetHan) && daysRemaining <= 20 && daysRemaining > 0) {
                                            hasYellowDot = true;
                                        } else if (!currentDate.before(ngayGiaTri) && daysRemaining > 20) {
                                            hasRedDot = true;
                                        }
                                    }
                                }
                    
                                if (danhSachPhongTroTrong != null && !danhSachPhongTroTrong.isEmpty()) {
                                    hasGreenDot = true;
                                }
                    
                                // Determine if the current day matches
                                String currentDateClass = (day == currentDay && currentMonthToday == (currentMonth - 1) && currentYearToday == currentYear) ? "current-date" : "";

                                // Thêm sự kiện onclick vào phần tử <td> của ngày
                                out.print("<td class='" + currentDateClass + "' onclick='showRoomList(" + day + ")'>" + day);

                                if (hasGreenDot) {
                                    out.print("<span class='dot green-dot' title='Rooms available'></span>");
                                }
                                if (hasYellowDot) {
                                    out.print("<span class='dot yellow-dot' title='Contract is expiring soon'></span>");
                                }
                                if (hasRedDot) {
                                    out.print("<span class='dot red-dot' title='Contract is valid'></span>");
                                }
                                out.print("</td>");
                                day++;
                            }
                        }
                        out.print("</tr>");
                        if (day > daysInMonth) break;
                    }
                %>
            </table>


            <div id="roomListModal" >
                <h3>Danh sách phòng trọ</h3>

                <div id="roomList"></div>
                <button onclick="document.getElementById('roomListModal').style.display = 'none'">Đóng</button>
            </div>

        </div>

        <script>
           const year = <%= currentYear %>;  // Gán year từ JSP
           const month = <%= currentMonth %>; // Gán month từ JSP

           function showRoomList(day) {
               console.log("Clicked day: ", day);

               const url = 'DanhSachPhongTroCalender?year=' + encodeURIComponent(year) + '&month=' + encodeURIComponent(month) + '&day=' + encodeURIComponent(day);
               console.log("Fetching from URL: ", url);

               fetch(url)
                       .then(response => {
                           if (!response.ok) {
                               throw new Error('Network response was not ok: ' + response.statusText);
                           }
                           return response.json();
                       })
                       .then(data => {
                           console.log("Data received: ", data); // Kiểm tra dữ liệu nhận được

                           // Kiểm tra xem data có phải là đối tượng và có các thuộc tính đã được đặt tên chính xác hay không
                           if (data && typeof data === 'object') {
                               const roomListDiv = document.getElementById('roomList');
                               roomListDiv.innerHTML = ''; // Xóa nội dung cũ

                               // Hiển thị danh sách phòng đang thuê
                               const rentedHeader = document.createElement('h4');
                               rentedHeader.textContent = "Danh sách phòng đang thuê:";
                               roomListDiv.appendChild(rentedHeader);

                               if (Array.isArray(data.phongDangThue) && data.phongDangThue.length > 0) {
                                   data.phongDangThue.forEach(phongDangThue => {
                                       const roomDiv = document.createElement('div');
                                       roomDiv.textContent = "Room Name: " + phongDangThue.phongTro.TenPhongTro + ", Price: " + phongDangThue.phongTro.Gia;
                                       roomListDiv.appendChild(roomDiv);
                                   });
                               } else {
                                   const noRentedMessage = document.createElement('div');
                                   noRentedMessage.textContent = "Không có phòng nào đang thuê.";
                                   roomListDiv.appendChild(noRentedMessage);
                               }

                               // Hiển thị danh sách phòng sắp hết hạn
                               const nearingExpiryHeader = document.createElement('h4');
                               nearingExpiryHeader.textContent = "Danh sách phòng sắp hết hạn:";
                               roomListDiv.appendChild(nearingExpiryHeader);

                               if (Array.isArray(data.phongSapHetHan) && data.phongSapHetHan.length > 0) {
                                   data.phongSapHetHan.forEach(phongSapHetHan => {
                                       const roomDiv = document.createElement('div');
                                       roomDiv.textContent = "Room Name: " + phongSapHetHan.phongTro.TenPhongTro + ", Price: " + phongSapHetHan.phongTro.Gia;
                                       roomListDiv.appendChild(roomDiv);
                                   });
                               } else {
                                   const noExpiringMessage = document.createElement('div');
                                   noExpiringMessage.textContent = "Không có phòng nào sắp hết hạn.";
                                   roomListDiv.appendChild(noExpiringMessage);
                               }

                               // Hiển thị danh sách phòng trống
                               const availableHeader = document.createElement('h4');
                               availableHeader.textContent = "Danh sách phòng trống:";
                               roomListDiv.appendChild(availableHeader);

                               if (Array.isArray(data.phongTrong) && data.phongTrong.length > 0) {
                                   data.phongTrong.forEach(phongTrong => {
                                       const roomDiv = document.createElement('div');
                                       roomDiv.textContent = "Room Name: " + phongTrong.TenPhongTro + ", Price: " + phongTrong.Gia;
                                       roomListDiv.appendChild(roomDiv);
                                   });
                               } else {
                                   const noAvailableMessage = document.createElement('div');
                                   noAvailableMessage.textContent = "Không có phòng trống.";
                                   roomListDiv.appendChild(noAvailableMessage);
                               }

                               document.getElementById('roomListModal').style.display = 'block'; // Đảm bảo modal được hiển thị

                           } else {
                               alert('Dữ liệu không hợp lệ nhận được từ server.');
                               console.error('Expected an object with arrays, but got:', data);
                           }
                       })
                       .catch(error => {
                           console.error('Error:', error);
                           alert('Có lỗi xảy ra khi lấy dữ liệu. Vui lòng kiểm tra console để biết thêm chi tiết.');
                       });
           }
        </script>

    </body>
</html>
