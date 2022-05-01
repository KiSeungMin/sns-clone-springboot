// 좋아요 버튼 기능 구현
$(document).ready(function(){
    $("._heart").click(function(){
        if($("._heart").hasClass("liked")){
            $("._heart").html('<i class="fa fa-heart-o" aria-hidden="true"></i>');
            $("._heart").removeClass("liked");
        }else{
            $("._heart").html('<i class="fa fa-heart" aria-hidden="true"></i>');
            $("._heart").addClass("liked");
        }

        $("#likeForm").submit();
    });
});

// 캐러셀 기능 구현 관련 함수
$(document).ready(function(){
    // Activate Carousel
    $(".carousel").carousel({interval: false, wrap: true, touch : true});

    // Enable Carousel Indicators
    $(".item1").click(function(){
        $("#myCarousel").carousel(0);
    });
    $(".item2").click(function(){
        $("#myCarousel").carousel(1);
    });
    $(".item3").click(function(){
        $("#myCarousel").carousel(2);
    });

    // Enable Carousel Controls
    $(".left").click(function(){
        $("#myCarousel").carousel("prev");
    });
    $(".right").click(function(){
        $("#myCarousel").carousel("next");
    });
});

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