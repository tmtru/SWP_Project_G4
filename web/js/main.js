
//Thanh scroll ngang
let numberOfItem;
let progressBar, lastBar;
function numberOfItems(handle) {
    console.log(handle.closest(".mini-slide").querySelectorAll(".item").length);
    console.log(">>>length of item", handle.closest(".mini-slide").querySelectorAll(".item").length);
    return handle.closest(".mini-slide").querySelectorAll(".item").length;
}
function calculateProgressBar(handle) {
    const slider = document.querySelector(".listings-list");
    const itemWidth = slider.querySelector(".item").offsetWidth+20;
    numberOfItem = numberOfItems(handle);
    const allItemWidth = itemWidth * numberOfItem;
    let vpWidth = slider.offsetWidth;
    console.log(itemWidth);
    console.log(vpWidth);
    console.log(allItemWidth);
    progressBar = Math.floor(allItemWidth / vpWidth);
    console.log("number of bar", progressBar);
    lastBar = allItemWidth / vpWidth - progressBar;
    // if (progressBar % 2 == 0) {
    //   progressBar = progressBar * 2;
    //   if (lastBar > 0.5) lastBar = lastBar + 0.5;
    // } else {
    //   progressBar = progressBar * 2 - 1;
    // }
    console.log("Last bar",lastBar);
    if (lastBar < 0.5 && lastBar !== 0) {
        progressBar = progressBar - 0.5;
    } else if (lastBar > 0.5) {
        lastBar -= 0.5;
    }
}
addEventListener("load", (event) => {
    let handleLeft = document.querySelectorAll(".left-handle");
    let handleRight = document.querySelectorAll(".right-handle");
    handleLeft.forEach((item) => {
        item.style.opacity = "0";
    });
    handleRight.forEach((item) => {
        calculateProgressBar(item);
        if ((progressBar == 0.5 && lastBar == 0) || progressBar < 0.5) {
            item.style.opacity = "0";
        }
    });
});
document.addEventListener("click", (e) => {
    let handle;
    if (e.target.matches(".handle")) {
        handle = e.target;
    } else {
        handle = e.target.closest(".handle");
    }
    if (handle != null) onHandleClick(handle);
});
function onHandleClick(handle) {
    let handleLeft = handle.closest(".mini-slide").querySelector(".left-handle");
    calculateProgressBar(handle);
    const slider = handle.closest(".mini-slide").querySelector(".slider");
    const sliderIndex = parseFloat(
        getComputedStyle(slider).getPropertyValue("--slider-index")
    );
    if (handle.classList.contains("left-handle")) {
        if (sliderIndex - 0.5 <= 0) {
            slider.style.setProperty("--slider-index", 0);
            handle.style.opacity = "0";
        } else {
            slider.style.setProperty("--slider-index", sliderIndex - 0.5);
        }
    }
    if (handle.classList.contains("right-handle")) {
        if (sliderIndex + 0.5 > progressBar) {
            slider.style.setProperty("--slider-index", 0);
            handleLeft.style.opacity = "0";
        } else if (sliderIndex + 0.5 == progressBar) {
            handleLeft.style.opacity = "0.7";
            slider.style.setProperty("--slider-index", sliderIndex + lastBar);
        } else {
            handleLeft.style.opacity = "0.7";
            slider.style.setProperty("--slider-index", sliderIndex + 0.5);
        }
    }
}

