$(document).ready(function(){
    // Activate Carousel
    $(".myCarousel").carousel({interval: false, wrap: true, touch : true});

    // Enable Carousel Indicators
    /*
    $(".item1").click(function(){
        $("#myCarousel").carousel(0);
    });
    $(".item2").click(function(){
        $("#myCarousel").carousel(1);
    });
    $(".item3").click(function(){
        $("#myCarousel").carousel(2);
    });
     */
});

function leftClick(a){
    $("#myCarousel" + a).carousel("prev");
}

function rightClick(a){
    $("#myCarousel" + a).carousel("next");
}

$(document).ready(function(){

    let followBtn = document.querySelector(".followBtn")

    followBtn.addEventListener("click", () => {

        if(followBtn.classList.contains("btn-primary")){
            followBtn.classList.remove("btn-primary");
            followBtn.classList.add("btn-secondary");
            followBtn.textContent="unfollow";
        } else{
            followBtn.classList.remove("btn-secondary");
            followBtn.classList.add("btn-primary");
            followBtn.textContent="follow";
        }
    });
});