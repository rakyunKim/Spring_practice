/* :: gnb s :; */
(function($) {
    $(document).ready(init);

    var  $win = $(window),
        $body, $header, $dep1, $dep2, $moveH, $dimed,
        $mobBtn, $gnb,
        $winW = $win.outerWidth(),
        $winH = $win.outerHeight();

    function init() {
        $body = $('body');
        $header = $('.header');
        $dep1 = $('.gnb-list > li');
        $dep2 = $('.gnb-dep2-wrap');
        $dimed = $('.gnb-dimed');
        $moveH = 220;
        $mobBtn = $('.mob-gnb-btn');
        $gnb = $('.gnb');

        webGnbFn();
        mobGnbFn();
    }

    $win.on({
        resize : function() {
            var $newWindW = $(this).width();
            $winH = $(this).height();
            if($newWindW !== $winW){
                SetViewSize();
                removeStyleFn($header);
                removeStyleFn($dep1);
                removeStyleFn($dep2);
                removeStyleFn($dimed);
                removeStyleFn($mobBtn);
                removeStyleFn($gnb);
                removeStyleFn($body);
                $winW = $newWindW;
            }
        },
        scroll : function() {
            var moveNum;
            $header = $('.header');
            if(PCUTDFC.IS_VIEWTYPE == "web") moveNum = 100;
            else moveNum = 50;
            if ($(this).scrollTop() > moveNum) $header.addClass('scroll');
            else $header.removeClass('scroll');
        }
    });


    function webGnbFn() {
        $dep1.find('>a').on({
            mouseenter : function() {
                if(PCUTDFC.IS_VIEWTYPE == "web") {
                    var $this = $(this);
                    $this.parent().siblings().removeClass('active');
                    $this.parent().addClass('active');
                    $header.addClass('active');

                    webAnimateFn($dep2, 1.2, $moveH);
                    webAnimateFn($dimed, 1.2, $moveH);
                }
            }
        });

        $dep2.on({
            mouseenter : function() {
                if(PCUTDFC.IS_VIEWTYPE == "web") {
                    var $this = $(this);
                    $this.parent().siblings().removeClass('active');
                    $this.parent().addClass('active');
                }
            }
        });

        $header.on({
            mouseleave : function() {
                if(PCUTDFC.IS_VIEWTYPE == "web") {
                    $(this).removeClass('active');
                    webAnimateFn($dep2, 0.5, 0);
                    webAnimateFn($dimed, 0.5, 0);
                }
            }
        });
    }

    function mobGnbFn() {
        $mobBtn.on({
            click: function() {
                if(PCUTDFC.IS_VIEWTYPE == "tablet" || PCUTDFC.IS_VIEWTYPE == "mobile") {
                    var $this = $(this);
                    $this.toggleClass('active');
                    $gnb.css('height', $winH-parseInt(65)).stop().slideToggle().toggleClass('active');
                    $header.toggleClass('active');
                    $dimed.toggleClass('active');
                    removeStyleFn($dep1);
                    $dep2.stop().slideUp().removeClass('active');
                    $body.toggleClass('prevent');
                }
            }
        });

        $dep1.find('>a').on({
            click: function(e) {
                if(PCUTDFC.IS_VIEWTYPE == "tablet" || PCUTDFC.IS_VIEWTYPE == "mobile") {
                    var $this = $(this),
                        $thisParent = $this.parent(),
                        $thisDep2 = $thisParent.find($dep2);
                    e.preventDefault();
                    $thisParent.siblings().removeClass('active');
                    $thisParent.toggleClass('active');
                    $thisParent.siblings().find($dep2).stop().slideUp().removeClass('active');
                    $thisDep2.stop().slideToggle().toggleClass('active');
                }
            }
        });
    }

    function webAnimateFn($target, $speed, $height) {
        TweenMax.killTweensOf($target);
        TweenMax.to($target, $speed, { height: parseInt($height), ease: Expo.easeOut });
    }

    function removeStyleFn($target) {
        $target.removeClass("active").removeAttr("style");
    }

})(jQuery);
/* :: gnb e :; */


/* :: dropdown s :: */
(function($) {
    $(document).ready(function() {
        var $dropdown = $('.js-dropdown'),
            $dropdownBtn =  $dropdown.find('.js-dropdown-btn'),
            $dropdownCon = $dropdown.find('.js-dropdown-cont');

        $dropdownBtn.on('click', function() {
            var $this = $(this),
                $thisData = $this.data('dropdown');
            $this.toggleClass('active');
            $('*[data-dropdown="'+$thisData+'"]').not($this).stop().slideToggle().toggleClass('active');
        });
    });
})(jQuery);
/* :: dropdown e :: */


/* :: tab s :: */
(function($) {
    $(document).ready(function() {
        var $btn =  $('.js-tab-btn'),
            $list = $('.js-tab-list'),
            $win = $(window),
            $winW = $win.outerWidth();

        $btn.text($list.find('.active').text());
        $btn.on('click', function() {
            var $this = $(this);
            $this.toggleClass('active');
            $list.stop().slideToggle().toggleClass('active');
        });

        $win.resize(function() {
            var $newWindW = $(this).width();
            if($newWindW !== $winW){
                SetViewSize();
                $btn.removeClass("active").removeAttr("style");
                $list.removeClass("active").removeAttr("style");
                $winW = $newWindW;
            }
        });
    });
})(jQuery);
/* :: tab e :: */


/* :: slide s :: */
(function($) {
    $(document).ready(function () {
        shopSlide();
    });

    function shopSlide(){
        var $slideTarget = $('.js-shop-slide');
        var slide = new bsSlide( $slideTarget );
        function loadslide(){
            var controlobj = {};
            controlobj.nextbtn = $slideTarget.find('.bas-next');
            controlobj.prevbtn = $slideTarget.find('.bas-prev');
            controlobj.stopbtn = $slideTarget.find('.bas-stop');
            controlobj.playbtn = $slideTarget.find('.bas-play');
            controlobj.pager = $slideTarget.find('.bas-pager');

            controlobj.speed = 1.2;
            controlobj.viewLen = [1,2,2];
            controlobj.totmove = true;
            controlobj.autoMove = false;
            slide.init( controlobj );
        }
        loadslide();
    }
})(jQuery);
/* :: slide e :: */

/* :: cms layer popup s :: */
(function($) {
    $(document).ready(layerFunc);

    var $close, $layer, $wrap, $layerLen;

    function layerFunc() {
        $wrap = $('.js-cms-popup-wrap');
        $layer = $(".js-cms-popup");
        $close = $(".js-cms-popup-close");
        $layerLen = $layer.length;

        var cid = $('.js-cms-popup').data("id");


        $layer.each(function(i,e) {
            var $this = $(this),
                $thisidx = $this.index()+1;
            $this.addClass("cms-popup"+$thisidx);
        });

        for(var i = 1; i < 7; i++) {
            if( laypopGetCookie(cid + "cms-popup"+i) == "done" ){ // 체크 했을 시
                $(".cms-popup"+i).removeClass("active");
            } else { // 체크 안했을 시
                $(".cms-popup"+i).addClass("active");
            }
        }

        $close.on({
            click : function() {
                var $this = $(this),
                    $thisLayer = $this.closest($layer);
                $thisLayer.removeClass("active");

                if($layer.siblings(".active").length == 0) { // 레이어팝업 1개 active 되었을 때 dimed remove
                    $(".cms-popup-dimed").remove();
                    $wrap.removeClass('active');
                }
            }
        });

        callDimed();

        $(window).resize(function() { // web일 때
            callDimed();
        });
    }

    function callDimed() {
        var $dimmed = $(".cms-popup-dimed");
        if($('.js-cms-popup.active').length > 0) { // 레이어팝업 1개라도 active 되었을 때 dimed function 호출
            if($dimmed.length > 0) return false;
            $wrap.addClass('active');
            makeDimed();
        }
    }

    function makeDimed() {
        var makeDim = "";
        makeDim += "<div class='cms-popup-dimed'></div>";
        $("body").append(makeDim);
    }

    function laypopGetCookie( name ){
        var nameOfCookie = name + "=";
        var x = 0;
        while ( x <= document.cookie.length )
        {
            var y = (x+nameOfCookie.length);
            if ( document.cookie.substring( x, y ) == nameOfCookie ) {
                if ( (endOfCookie=document.cookie.indexOf( ";", y )) == -1 )
                    endOfCookie = document.cookie.length;
                console.log(document.cookie);
                return unescape( document.cookie.substring( y, endOfCookie ) );
            }
            x = document.cookie.indexOf( " ", x ) + 1;
            if ( x == 0 )
                break;
        }
        return "";

    }
})(jQuery);
/* :: cms layer popup e :: */