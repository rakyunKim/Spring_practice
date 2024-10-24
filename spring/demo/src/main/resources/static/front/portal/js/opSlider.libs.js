(function($){
    var self = this;
    var opSlide = null;
    opSlide = self.opSlide = {};

    self.opSlide = function( $slide ){

        var self = this;
        var autoTimer;
        var activeClass = 'op-active';                                  // 활성화 class
        var $activeElem = $('.' + activeClass);                         // 활성화 class element
        var maxLen;                                                     // 슬라이드 개수
        var thisidx = 0;                                                // 슬라이드 active index
        var oldClickIndex;                                              // 슬라이드 oldclick index
        var autocheck = false;                                          // 슬라이드 active index
        var basispeed = 1.2;                                            // 슬라이드 autorun
        var elemIndex, oldelemIndex;                                    // 이전 움직임 element
        var $elem = $slide,                                             // slide target element
            $elemList = $elem.find('li'),                               // slide list element
            $elemListLnk = $elemList.find('a'),                         // slide list link element
            $elemListFrame = $elemList.find('iframe');                  // slide iframe element
        var $elemNextBtn, $elemPrevBtn;                                 // next, prev button element
        var $elemindi, $elempagerBtn, $elemPlayBtn, $elemStopBtn;       // pager, play, stop button element
        var swipeOptions;                                               // swipe plugin option
        var numcheck = false; //페이지 info
        var $curNum; //페이지 info

        /* 슬라이드 opacity 활성화 */
        self.openani = function( $elem, speed ){var moveSpeed = (speed != undefined) ? speed : basispeed;
            $elem.css('display','block').addClass( activeClass );
            TweenMax.to($elem, moveSpeed, { opacity: 1, ease: Expo.easeOut});

            /* 연속적으로 클릭시 처리 */
            elemIndex = $elem.index();
            $elemList.each(function( index ){
                if(elemIndex != index && oldelemIndex != index) {
                    TweenMax.killTweensOf($elemList.eq(index));
                    $elemList.eq(index).css({
                        opacity: 0,
                        display: 'none'
                    });

                }
            });
        };

        /* 슬라이드 opacity 비활성화 */
        self.closeani = function( $elem, speed ){
            var moveSpeed = (speed != undefined) ? speed : basispeed;
            TweenMax.killTweensOf($elem);
            $elem.removeClass( activeClass );
            TweenMax.to($elem, moveSpeed, { opacity: 0, ease: Expo.easeOut, onComplete:listHide, onCompleteParams:[ $elem ] });
            oldelemIndex = $elem.index();

        };

        function listHide( $elem ){
            $elem.css('display','none');
        }

        function activeStop(){
            $elemPlayBtn.css('display','none');
            $elemStopBtn.css('display','inline-block');
        }

        function activePlay(){
            $elemPlayBtn.css('display','inline-block');
            $elemStopBtn.css('display','none');
        }

        function interval(){
            stopInterval(autoTimer);
            autoTimer = setInterval(function () {
                nextMove();
            }, 4000);
            activeStop();

        }

        function stopInterval(){
            clearInterval(autoTimer);
            activePlay();
        }

        /* 다음슬라이드 이동 */
        function nextMove(){
            thisidx = activeInedx();
            self.closeani( $elemList.eq( thisidx ) );
            if( thisidx < maxLen - 1) {
                self.openani( $elemList.eq( thisidx+1 ) );
                activepager(thisidx+1);
            } else {
                self.openani( $elemList.eq( 0 ) );
                activepager(0);
            }

            if(maxLen < 2) return false;
            $curNum = activeInedx();
            numCheckFn($curNum + 1);
        }

        /* 이전슬라이드 이동 */
        function prevMove(){
            thisidx = activeInedx();
            self.closeani( $elemList.eq( thisidx ) );
            if( thisidx > 0 ) {
                self.openani( $elemList.eq( thisidx - 1 ) );
                activepager( thisidx - 1 );
            } else {
                self.openani( $elemList.eq( maxLen - 1 ) );
                activepager( maxLen - 1 );
            }

            if(maxLen < 2) return false;
            $curNum = activeInedx();
            numCheckFn($curNum + 1);
        }

        /* direct 이동 */
        function directMove(e){
            var $this = $(this);
            var clickIndex = $elempagerBtn.index( $this );
            if(clickIndex == oldClickIndex) return;
            thisidx = activeInedx();
            self.closeani( $elemList.eq( thisidx ) );
            self.openani( $elemList.eq( clickIndex ) );
            activepager( clickIndex );
            stopInterval();
            oldClickIndex = $elempagerBtn.index( $this );
        }

        /* active list index check */
        function activeInedx() {
            var activeidx = $elemList.siblings("." + activeClass).index();
            return activeidx;
        }

        function controlSet( controlobj ){
            $elemNextBtn = controlobj.nextbtn;
            $elemPrevBtn = controlobj.prevbtn;
            $elemindi = controlobj.indi;
            $elempagerBtn = controlobj.pager;
            $elemPlayBtn = controlobj.play;
            $elemStopBtn = controlobj.stop;
            basispeed = controlobj.speed;
            autocheck = controlobj.auto;
            numcheck = controlobj.num;
        }

        function activepager( idx ){
            $elempagerBtn.removeClass('active');
            $elempagerBtn.eq(idx).addClass('active');
        }

        /* pager keyEvent */
        function pagerKeyEvt(e){
            var $this = $(this);
            var thisKeyindex = $this.index();
            var linkchk, framechk;
            var speed = 0;

            if($elemList.eq(thisKeyindex).find('a').length > 0) {
                linkchk = true;
            } else {
                linkchk = false;
            }

            if($elemList.eq(thisKeyindex).find('iframe').length > 0) {
                framechk = true;
            } else {
                framechk = false;
            }

            if(e.shiftKey && e.keyCode == 9) {
            } else if (e.keyCode == 9){
                if(thisKeyindex != maxLen - 1){
                    e.preventDefault();
                    if(linkchk) { $elemList.eq(thisKeyindex).find('a').focus() }
                    if(framechk) { $elemList.eq(thisKeyindex).find('iframe').focus() }
                }

                if(thisKeyindex == 0) {
                    self.closeani( $elemList.find("." + activeClass), 0);
                    self.openani( $elemList.eq( 0 ), 0 );
                    activepager( 0 );

                }
            }

        }

        /* list link keyEvent */
        function listLnkKeyEvt(e){
            var $this = $(this);
            var thisKeyindex = $elemList.index( $this.parent() );
            var speed = 0;
            if(e.shiftKey && e.keyCode == 9) {
            } else if (e.keyCode == 9){
                if(thisKeyindex != maxLen -1){
                    e.preventDefault();
                    self.closeani( $elemList.eq( thisKeyindex ), speed);
                    self.openani( $elemList.eq( thisKeyindex + 1 ), speed );
                    activepager( thisKeyindex+1 );
                    $elempagerBtn.eq(thisKeyindex+1).focus();

                    $curNum = activeInedx();
                    numCheckFn($curNum + 1);
                }
            }
        }

        function playBtnfocus(e){
            if(e.keyCode == 13) {
                e.preventDefault();
                activeStop();
                $elemStopBtn.focus();
            }
        }

        function stopBtnfocus(e){
            if(e.keyCode == 13) {
                e.preventDefault();
                activePlay();
                $elemPlayBtn.focus();
            }
        }

        function numCheckFn($num) {
            var $curPage = $elem.find(".curjs");
            $curPage.text($num);
        }

        function swipeStatus(event, phase, direction, distance, duration){
            if (phase == "move" && (direction == "left" || direction == "right")) {
            } else if ( phase == "cancel" || phase =="end"){
                if(distance>75){
                    if (direction == "right"){
                        prevMove();
                    } else if (direction == "left"){
                        nextMove();
                    }
                }
            }
        }

        /* 이벤트 추가 */
        function add_evt(){
            $elemNextBtn.on('click', nextMove );
            $elemPrevBtn.on('click', prevMove );
            $elempagerBtn.on('click focus', directMove );
            $elempagerBtn.on('keydown', pagerKeyEvt );
            $elemListLnk.on('keydown', listLnkKeyEvt );
            $elemPlayBtn.on('click', interval );
            $elemStopBtn.on('click', stopInterval );
            $elemPlayBtn.on('keydown', playBtnfocus );
            $elemStopBtn.on('keydown', stopBtnfocus );
            $elemList.swipe( swipeOptions );
        }

        self.init = function( controlobj ){
            self.openani( $elemList.eq(0) );
            CheckMobile();

            swipeOptions = {
                triggerOnTouchEnd : true,
                swipeStatus : PCUTDFC.IS_MOBILE && swipeStatus,
                allowPageScroll:"vertical",
                threshold:80,
                excludedElements : []
            };

            maxLen = $elemList.length;
            controlSet( controlobj );
            activepager( 0 );
            add_evt();
            if(autocheck) {
                interval();
            } else {
                stopInterval();
            }

            if(numcheck) {
                var $len = $elemList.length;
                var $totPage = $elem.find(".totjs");
                $totPage.text($len);

                $curNum = 1;
                numCheckFn($curNum);
            }
        };
    }
})(jQuery);