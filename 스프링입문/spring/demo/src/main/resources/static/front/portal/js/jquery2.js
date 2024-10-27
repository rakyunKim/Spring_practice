/* :: visual s :: */
(function($) {
    $(document).ready(function () {
        mainVisualSlide();
        mainNoticeSlide();
    });

    function mainVisualSlide() {
        var $slideTarget = $('.js-visual-slide');
        var slide = new opSlide($slideTarget);
        var controlobj = {};
        controlobj.nextbtn = $slideTarget.find('.op-next');
        controlobj.prevbtn = $slideTarget.find('.op-prev');
        controlobj.indi = $slideTarget.find('.op-indicator');
        controlobj.pager = $slideTarget.find('.op-pager');
        controlobj.play = $slideTarget.find('.op-play');
        controlobj.stop = $slideTarget.find('.op-stop');
        controlobj.speed = 2;
        controlobj.auto = true;
        controlobj.num = false;
        slide.init(controlobj);
    }

    function mainNoticeSlide(){
        var $slideTarget = $('.js-notice-slide');
        var slide = new bsSlide( $slideTarget );
        function loadslide(){
            var controlobj = {};
            controlobj.nextbtn = $slideTarget.find('.bas-next');
            controlobj.prevbtn = $slideTarget.find('.bas-prev');
            controlobj.stopbtn = $slideTarget.find('.bas-stop');
            controlobj.playbtn = $slideTarget.find('.bas-play');
            controlobj.speed = 1.2;
            controlobj.viewLen = [1,1,1];
            controlobj.totmove = false;
            controlobj.autoMove = false;
            controlobj.numcheck = false;
            slide.init(controlobj);
        }
        loadslide();
    }
})(jQuery);
/* :: visual e :: */