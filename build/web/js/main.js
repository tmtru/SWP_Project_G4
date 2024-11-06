let prbar, lastBar, NOItem;
let isLoading = false; // Biến để theo dõi trạng thái tải

function NOItems(handle) {
    return handle.closest(".mini-slide").querySelectorAll(".item").length;
}

function calculateProgressBar(handle) {
    const slider = document.querySelector(".listings-list");
    
    // Kiểm tra xem slider có tồn tại không
    if (!slider) {
        console.error("Slider không tồn tại!");
        return;
    }
    
    const item = slider.querySelector(".item");
    
    // Kiểm tra xem item có tồn tại không
    if (!item) {
        console.error("Không tìm thấy item trong slider!");
        return;
    }
    
    const itemWidth = item.offsetWidth + 20;
    NOItem = NOItems(handle);
    const allItemWidth = itemWidth * NOItem;
    const vpWidth = slider.offsetWidth;

    prbar = Math.floor(allItemWidth / vpWidth);
    lastBar = allItemWidth / vpWidth - prbar;

    if (lastBar < 0.5 && lastBar !== 0) {
        prbar -= 0.5;
    } else if (lastBar > 0.5) {
        lastBar -= 0.5;
    }
}


document.addEventListener("DOMContentLoaded", (event) => {
    loadInitialHandles();
});

function loadInitialHandles() {
    let handleLeft = document.querySelectorAll(".left-handle");
    let handleRight = document.querySelectorAll(".right-handle");

    handleLeft.forEach((item) => {
        item.style.opacity = "0";
    });

    handleRight.forEach((item) => {
        calculateProgressBar(item);
        updateHandleOpacity(item);
    });
}

function updateHandleOpacity(item) {
    if ((prbar === 1 && (lastBar < 0 || lastBar === 0)) || prbar < 1) {
        item.style.opacity = "0";
    } else {
        item.style.opacity = "1"; // Đặt lại opacity thành 1 nếu có thể
    }
}

document.addEventListener("click", (e) => {
    const handle = e.target.closest(".handle");
    if (handle) {
        onHandleClick(handle);
    }
});

function onHandleClick(handle) {
    calculateProgressBar(handle);
    const slider = handle.closest(".mini-slide").querySelector(".slider");
    const sliderIndex = parseFloat(getComputedStyle(slider).getPropertyValue("--slider-index"));

    if (handle.classList.contains("left-handle")) {
        if (sliderIndex - 0.5 <= 0) {
            slider.style.setProperty("--slider-index", 0);
            handle.style.opacity = "0";
        } else {
            slider.style.setProperty("--slider-index", sliderIndex - 0.5);
        }
    }

    if (handle.classList.contains("right-handle")) {
        if (sliderIndex + 0.5 > prbar) {
            slider.style.setProperty("--slider-index", 0);
            handle.closest(".mini-slide").querySelector(".left-handle").style.opacity = "0";
        } else if (sliderIndex + 0.5 === prbar) {
            handle.closest(".mini-slide").querySelector(".left-handle").style.opacity = "0.7";
            slider.style.setProperty("--slider-index", sliderIndex + lastBar);
        } else {
            handle.closest(".mini-slide").querySelector(".left-handle").style.opacity = "0.7";
            slider.style.setProperty("--slider-index", sliderIndex + 0.5);
        }
    }
}

function loadPage(pageNumber) {
    if (pageNumber < 1 || isLoading) {
        return; // Không load trang nhỏ hơn 1 hoặc nếu đang tải
    }

    isLoading = true; // Đánh dấu là đang tải

    $.ajax({
        url: 'homer',
        type: 'GET',
        data: { pageNumber: pageNumber },
        success: function (response) {
            $('#housesContainer').html(response);
            loadInitialHandles(); // Gọi lại hàm sau khi tải dữ liệu thành công
        },
        error: function (xhr, status, error) {
            console.error("Có lỗi xảy ra: " + error);
            console.error("Mã trạng thái: " + xhr.status);
            console.error("Chi tiết: " + xhr.responseText);
        },
        complete: function () {
            isLoading = false; // Đánh dấu là không còn tải
        }
    });
}
document.addEventListener("click", function (e) {
  var myTargetElement = e.target;
  var selector, mainElement;
  if (
    myTargetElement.classList.contains("search-toggle") ||
    (myTargetElement.parentElement &&
      myTargetElement.parentElement.classList.contains("search-toggle")) ||
    (myTargetElement.parentElement &&
      myTargetElement.parentElement.parentElement &&
      myTargetElement.parentElement.parentElement.classList.contains(
        "search-toggle"
      ))
  ) {
    if (myTargetElement.classList.contains("search-toggle")) {
      selector = myTargetElement.parentElement;
      mainElement = myTargetElement;
    } else if (
      myTargetElement.parentElement.classList.contains("search-toggle")
    ) {
      selector = myTargetElement.parentElement.parentElement;
      mainElement = myTargetElement.parentElement;
    } else if (
      myTargetElement.parentElement.parentElement.classList.contains(
        "search-toggle"
      )
    ) {
      selector = myTargetElement.parentElement.parentElement.parentElement;
      mainElement = myTargetElement.parentElement.parentElement;
    }
    document.querySelectorAll(".navbar-right li").forEach(function (elem) {
      if (elem !== selector) {
        elem.classList.remove("iq-show");
        // elem.querySelector(".search-toggle").classList.remove("active");
      }
    });

    if (
      !mainElement.classList.contains("active") &&
      document.querySelector(".navbar-list li .active")
    ) {
      document.querySelectorAll(".navbar-right li").forEach(function (elem) {
        elem.classList.remove("iq-show");
        elem.classList.remove("active");
      });
    }

    selector.classList.toggle("iq-show");
    mainElement.classList.toggle("active");
    e.preventDefault();
  } else if (myTargetElement.classList.contains("search-input")) {
    // Do nothing if clicking on search-input
  } else {
    document.querySelectorAll(".navbar-right li").forEach(function (elem) {
      elem.classList.remove("iq-show");
    });
    document
      .querySelectorAll(".navbar-right li .search-toggle")
      .forEach(function (elem) {
        elem.classList.remove("active");
      });
  }
});
// Tải trang đầu tiên khi mở trang
$(document).ready(function () {
    loadPage(1); // Tải trang đầu tiên
});
