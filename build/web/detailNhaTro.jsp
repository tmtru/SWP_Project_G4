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
            .img-preview {
                width: 50px;
                border: 1px solid #ddd;
                border-radius: 5px;
                margin-right: 10px;
                margin-bottom: 10px;
            }
        </style>
        <title >Chi tiết nhà trọ</title>
    </head>
    <body>
        <!--Room manegement dash board-->

        <nav class="sidebar">
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
                    <ul class="menu-links">
                        <li class="">
                            <a href="#">
                                <i class='bx bx-home-alt icon' ></i>
                                <span class="text nav-text">Trang chủ</span>
                            </a>
                        </li>
                        <li class="">
                            <a href="nhatro" class="active">
                                <i class='bx bxs-home icon active' ></i>
                                <span class="text nav-text">Nhà trọ</span>
                            </a>
                        </li>

                        <li class="">
                            <a href="room">
                                <i class='bx bx-bar-chart-alt-2 icon ' ></i>
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
                            <a href="loaddichvu">
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
                <h2>Chi tiết ${s.getTenNhaTro()}</h2>
            </section>
            <div class="container mt-5">

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
                <div class="card">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-6">
                                <!-- Image Carousel -->
                                <div id="roomImageCarousel" class="carousel slide" data-ride="carousel">
                                    <div class="carousel-inner">
                                        <c:forEach items="${listImage}" var="image" varStatus="status">
                                            <div class="carousel-item ${status.index == 0 ? 'active' : ''}">
                                                <img src="${image}" class="d-block w-100" alt="${s.getTenNhaTro()}">
                                            </div>
                                        </c:forEach>
                                    </div>
                                    <a class="carousel-control-prev" href="#roomImageCarousel" role="button" data-slide="prev">
                                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                        <span class="sr-only">Previous</span>
                                    </a>
                                    <a class="carousel-control-next" href="#roomImageCarousel" role="button" data-slide="next">
                                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                        <span class="sr-only">Next</span>
                                    </a>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <h3>${s.getTenNhaTro()} </h3>

                                <p><strong>Description:</strong> ${s.getMo_ta()}</p>
                                <p><strong>Address</strong> ${s.getDia_chi()}</p>
                                <p><strong>Owner </strong> ${s.getChuTro().getName()}</p>
                            </div>
                        </div>
                    </div>
                    <div class="card-footer">

                        <a href="nhatro" class="btn btn-secondary">Back to Room List</a>
                    </div>
                </div>
            </div>
            <div id="chat-button" style="position: fixed; bottom: 20px; right: 20px; z-index: 1000;">
                <button onclick="toggleChatBox()" style="background-color: #00bfff; border: none; padding: 15px; border-radius: 50%; position: relative;">
                    <i class="fas fa-comments" style="color: white; font-size: 24px;"></i>
                    <span id="unreadCount" style="display: none; position: absolute; top: -5px; right: -5px; background-color: red; color: white; border-radius: 50%; padding: 5px; font-size: 12px;">
                        0
                    </span>
                </button>
            </div>
            <section id="chat-box" style="display: none; position: fixed; bottom: 80px; right: 20px; z-index: 1000; width: 70% ; float: right">
                <div class="container py-5">
                    <div class="row d-flex justify-content-center">


                        <!-- Main container -->
                        <div class="d-flex">
                            <c:if test="${account == null}">
                                <div class="card flex-grow-1" id="chat1" style="border-radius: 15px; margin-left: 15px;">
                                    <div class="card-header d-flex justify-content-between align-items-center p-3 bg-info text-white border-bottom-0"
                                         style="border-top-left-radius: 15px; border-top-right-radius: 15px;">
                                        <i class="fas fa-angle-left"></i>

                                        <i class="fas fa-times" onclick="toggleChatBox()"></i>
                                    </div>
                                    <div class="card-body" style="overflow-y: auto; max-height: 400px; text-align: center" >
                                        Please <a href="login.jsp">Login </a> to start chatting
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${account != null}">

                                <!-- Left card: User list -->
                                <c:if test="${account.role == 'landlord'}">
                                    <div class="card" id="userListCard" style="width: 100%; border-radius: 15px;">
                                        <div class="card-header bg-info text-white" style="border-top-left-radius: 15px; border-top-right-radius: 15px;">
                                            <p class="mb-0 fw-bold">User List</p>
                                        </div>
                                        <div class="card-body" style="overflow-y: auto; max-height: 400px;">
                                            <ul class="list-group" id="userList">
                                                <!-- Usernames will be appended here -->
                                            </ul>
                                        </div>
                                    </div>
                                </c:if>

                                <!-- Right card: Chat messages -->
                                <div class="card flex-grow-1" id="chat1" style="border-radius: 15px; margin-left: 15px;">
                                    <div class="card-header d-flex justify-content-between align-items-center p-3 bg-info text-white border-bottom-0"
                                         style="border-top-left-radius: 15px; border-top-right-radius: 15px;">
                                        <i class="fas fa-angle-left"></i>
                                        <p class="mb-0 fw-bold"></p>
                                        <i class="fas fa-times" onclick="toggleChatBox()"></i>
                                    </div>
                                    <div class="card-body" id="chatMessages" style="overflow-y: auto; max-height: 400px;">
                                        <!-- Chat messages will be appended here -->
                                    </div>
                                    <div data-mdb-input-init class="form-outline">
                                        <form id="sendMessageForm" class="d-flex align-items-center">
                                            <input type="hidden" name="receiver_id" />
                                            <input type="hidden" name="houseId" value="${s.getID_NhaTro()}" />
                                            <textarea class="form-control bg-body-tertiary me-3" id="textAreaExample" rows="1" name="messageContent" placeholder="Type your message"></textarea>
                                            <button type="button" class="btn btn-primary" id="sendMessageButton" disabled>
                                                <i class="fas fa-paper-plane"></i>
                                            </button>
                                        </form>
                                    </div>
                                </div>


                            </c:if>
                        </div>

                    </div>
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
            //filter room by floor ko can submit
            function filterRoomsByFloor(select) {
                var selectedFloor = select.value;
                window.location.href = 'room?tang=' + selectedFloor;
            }
            function filterRoomsByNhaTro(select) {
                var selectedNhaTro = select.value;
                window.location.href = 'room?nhaTro=' + selectedNhaTro; // Chú ý sử dụng 'nhaTro'
            }


            //delete room confirm
            function confirmDelete(roomId) {
                if (confirm('Are you sure you want to delete this room?')) {
                    window.location.href = 'deleteRoom?id=' + roomId;
                }
            }
        </script>
        <script type="text/javascript">
            function previewImage(input) {
                var previewContainer = document.getElementById('image-previews');
                previewContainer.innerHTML = ''; // Xóa nội dung cũ khi chọn ảnh mới

                if (input.files) {
                    Array.from(input.files).forEach(file => {
                        var reader = new FileReader();

                        reader.onload = function (e) {
                            var img = document.createElement('img');
                            img.src = e.target.result;
                            img.className = 'img-preview';
                            previewContainer.appendChild(img);
                        }

                        reader.readAsDataURL(file);
                    });
                }
            }

        </script>


        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script>
            var userRole = "<c:out value='${account.role}' />";
            function toggleChatBox() {
                var chatBox = document.getElementById("chat-box");
                if (chatBox.style.display === "none" || chatBox.style.display === "") {
                    chatBox.style.display = "block";
                    fetchMessages(); // Fetch messages when chat box is opened
                    fetchUnreadMessageCount(); // Update unread count in the icon
                } else {
                    chatBox.style.display = "none";
                }
            }



            function displayMessages(response, currentUserId) {
                let chatContent = '';
                response.forEach(function (msg) {
                    if (msg.senderId === currentUserId) {
                        // Outgoing message
                        chatContent += `
        <div class="d-flex flex-row justify-content-end mb-4">
          <div class="p-3 outgoing-message message">
            <p class="small mb-0">` + msg.content + `</p>
          </div>
          <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava1-bg.webp" alt="avatar">
        </div>
      `;
                    } else {
                        // Incoming message
                        chatContent += `
        <div class="d-flex flex-row justify-content-start mb-4">
          <img src="https://cdn.pixabay.com/photo/2020/07/14/13/07/icon-5404125_1280.png" alt="avatar">
          <div class="p-3 incoming-message message">
            <p class="small mb-0">` + msg.content + `</p>
          </div>
        </div>
      `;
                    }
                });
                document.getElementById('chatMessages').innerHTML = chatContent;
                const chatMessagesDiv = document.getElementById('chatMessages');
                chatMessagesDiv.scrollTop = chatMessagesDiv.scrollHeight;
            }


            function fetchMessages() {
                // Directly fetch the updated receiver_id from the hidden input
                const receiverId = $('input[name="receiver_id"]').val();
                const houseId = $('input[name="houseId"]').val();
                const currentUserId = parseInt(${account.getID_Account()});

                // Verify both receiverId and currentUserId for accuracy

                if (!receiverId) {
                    return; // Exit if no user is selected
                }

                // Prepare data object
                var data = {houseId: houseId};
                if (userRole === 'landlord') {
                    data.selectedUserId = receiverId;  // Use receiverId for landlord's selected user
                }

                $.ajax({
                    url: "getMessages",
                    method: "GET",
                    data: data,
                    dataType: "json",
                    success: function (response) {
                        displayMessages(response, currentUserId);
                    },
                    error: function () {
                    }
                });
            }

            if (userRole === 'landlord') {
                $(document).ready(function () {
                    function fetchUserList() {
                        $.ajax({
                            url: "getUserList",
                            method: "GET",
                            dataType: "json",
                            success: function (response) {
                                let userListContent = '';
                                response.forEach(function (user) {
                                    var unreadBadge = '';
                                    if (user.unreadCount > 0) {
                                        unreadBadge = `<span class="badge bg-danger ms-2">` + user.unreadCount + ` </span>`;
                                    }

                                    userListContent += `
                    <li class="list-group-item d-flex justify-content-between align-items-center" data-user-id="` + user.ID_Account + `" data-username="` + user.username + `" onclick="selectUser(this)">
                        ` + user.username + unreadBadge + `
                    </li>
                `;
                                });
                                $('#userList').html(userListContent);
                            },
                            error: function () {
                                console.error('Error fetching user list');
                            }
                        });
                    }

                    setInterval(fetchUserList, 5000);



                    window.selectUser = function (element) {
                        const userId = $(element).data('user-id');
                        const username = $(element).data('username');

                        // Set the selected user ID in the hidden input
                        $('input[name="receiver_id"]').val(userId);
                        $('#chatWithName').text('Chat with ' + username);

                        // Highlight the selected user
                        $('#userList .list-group-item').removeClass('active');
                        $(element).addClass('active');

                        // Fetch messages with the selected user
                        fetchMessages();

                        // Reset unread count badge
                        $(element).find('.badge').remove();
                    };



                    fetchUserList();
                });
            } else {
                // If the user is not a landlord, set receiver ID and fetch messages
                $(document).ready(function () {
                    $('#receiver_id').val(${ct.account.ID_Account});
                    fetchMessages();
                });
            }


            $(document).ready(function () {
                // Initially disable the Send button if no user is selected
                $('#sendMessageButton').prop('disabled', true);

                // Enable/disable Send button based on the receiver_id input value
                function toggleSendButton() {
                    const receiverId = $('input[name="receiver_id"]').val();
                    $('#sendMessageButton').prop('disabled', !receiverId);
                }

                // Run toggleSendButton initially in case receiver_id has a value set automatically
                toggleSendButton();

                // Update the Send button state whenever a user is selected
                window.selectUser = function (element) {
                    const userId = $(element).data('user-id');
                    const username = $(element).data('username');

                    // Set the selected user ID in the hidden input
                    $('input[name="receiver_id"]').val(userId);
                    $('#chatWithName').text('Chat with ' + username);

                    // Highlight the selected user
                    $('#userList .list-group-item').removeClass('active');
                    $(element).addClass('active');

                    // Enable the Send button after user selection
                    toggleSendButton();

                    // Fetch messages with the selected user
                    fetchMessages();

                    // Reset unread count badge
                    $(element).find('.badge').remove();
                };

                // Check the Send button state each time the receiver_id changes
                $('input[name="receiver_id"]').on('change', toggleSendButton);

                // Handle the Send button click event
                $('#sendMessageButton').click(function () {
                    const messageContent = $('#textAreaExample').val();
                    const receiverId = $('input[name="receiver_id"]').val();
                    const houseId = $('input[name="houseId"]').val();

                    if (messageContent !== "" && receiverId) {
                        var data = {
                            messageContent: messageContent,
                            houseId: houseId
                        };
                        if (userRole === 'landlord') {
                            data.selectedUserId = receiverId;
                        } else {
                            data.receiverId = receiverId;
                        }

                        $.ajax({
                            url: "sendMessage",
                            method: "POST",
                            data: data,
                            success: function () {
                                $('#textAreaExample').val(''); // Clear input on success
                                fetchMessages(); // Refresh messages
                            },
                            error: function () {
                                alert('Error sending message');
                            }
                        });
                    }
                });
            });


            function fetchUnreadMessageCount() {
                $.ajax({
                    url: 'getUnreadMessageCount',
                    method: 'GET',
                    dataType: 'json',
                    success: function (response) {
                        var unreadCount = response.unreadCount;
                        if (unreadCount > 0) {
                            $('#unreadCount').text(unreadCount);
                            $('#unreadCount').show();
                        } else {
                            $('#unreadCount').hide();
                        }
                    },
                    error: function () {
                        console.error('Error fetching unread message count');
                    }
                });
            }

            setInterval(fetchUnreadMessageCount, 5000);

            $(document).ready(function () {
                fetchUnreadMessageCount();
            });


            // If the user is not a landlord, set receiver ID and fetch messages
            <c:if test="${account.role != 'landlord'}">
            $(document).ready(function () {
                $('input[name="receiver_id"]').val(${ct.account.ID_Account});
                fetchMessages();
            });
            </c:if>
            setInterval(fetchMessages, 5000);

        </script>
        <style>
           
            /* Style for chat messages to wrap long texts */
            #chatMessages .message {
                white-space: pre-wrap; /* Preserves newlines and wraps long text */
                word-wrap: break-word; /* Allows words to break and wrap within container */
                word-break: break-word; /* Forces line breaks on long unbreakable words */
                max-width: 100%; /* Prevents message from overflowing container */
            }

            /* Styling outgoing and incoming messages with consistent width */
           /* Outgoing message (aligns to the right) */
#chatMessages .outgoing-message {
    background-color: #e0f7fa;
    text-align: left;
    border-radius: 12px 12px 0 12px;
    margin: 2px 0 2px auto;
    align-self: flex-end;
    max-width: 60%; /* Add this line */
    word-wrap: break-word; /* Add this line */
    word-break: break-word; /* Add this line */
    white-space: normal; /* Add this line */
}

/* Incoming message (aligns to the left) */
#chatMessages .incoming-message {
    background-color: #f1f1f1;
    text-align: left;
    border-radius: 12px 12px 12px 0;
    margin: 2px auto 2px 0;
    align-self: flex-start;
    max-width: 60%; /* Add this line */
    word-wrap: break-word; /* Add this line */
    word-break: break-word; /* Add this line */
    white-space: normal; /* Add this line */
}

            /* Outgoing message styling */
            #chatMessages .outgoing-message {
                background-color: #e0f7fa;
                align-self: flex-end; /* Align outgoing messages to the right */
            }

            /* Incoming message styling */
            #chatMessages .incoming-message {
                background-color: #f1f1f1;
                align-self: flex-start; /* Align incoming messages to the left */
            }

            /* Avatar styling for consistent alignment */
            #chatMessages img {
                max-width: 45px;
                max-height: 45px;
                border-radius: 50%;
                margin: 5px;
            }

        </style>
    </body>
</html>