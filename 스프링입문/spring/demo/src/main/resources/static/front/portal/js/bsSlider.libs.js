(function($){
    var self = this;
    var bsSlide = null;
    bsSlide = self.bsSlide = {};

    self.bsSlide = function( $slide ){
        var self = this;
        var basicobj ={};           // setting object
        var autoTimer;
        var slideWidth,             // slide item width
            totLen,                 // slide length
            viewLen,                // slide view length
            moveLen,                // slide move length
            speed,                  // slide move speed
            moveWidth,              // slide move width
            swipeOptions;           // swipe option


        var thisidx = 0,            // setting object
            oldidx = null,          // oldidx
            checkMaxNum,            // max check index
            checkMinNum;            // min check index

        var $elem,                  // slider
            $elemNextBtn,           // next button
            $elemPrevBtn,           // prev button
            $elemStopBtn,
            $elemPlayBtn,
            $elemSlidebox,          // slide base div
            $elemSlideContainer,    // slide container div
            $elemContainer,         // slide ul
            $elemList,              // slide li
            $elemListLnk,           // slide a
            $elemMove,              // animation element
            $autoMove = false,
            numcheck = false, // 페이지 info
            $curNum; //페이지 info

        /* next */
        function nextSlide(){
            var moveX;
            if(viewLen >= totLen) return;
            speed = basicobj.speed;
            thisidx = thisidx + 1;
            if(thisidx >= checkMaxNum) {
                thisidx = 0;
            }
            moveX = thisidx * moveWidth;

            if(basicobj.totmove) {
                if(thisidx == checkMaxNum-1) {
                    moveX = (totLen-moveLen) * moveWidth;
                } else{
                    moveX = thisidx* moveLen * moveWidth;
                }
            }

            if(numcheck) {
                numCheckFn(thisidx+1);
            }

            moveElem(moveX);
            oldidx = thisidx;
            stopInterval();
        }

        /* prev */
        function prevSlide(){
            var moveX;
            if(viewLen >= totLen) return;
            speed = basicobj.speed;
            thisidx = thisidx - 1;
            if(thisidx == totLen-1) {
                thisidx = 1;
            }
            if(thisidx < checkMinNum) {
                thisidx = checkMaxNum - 1;
            }
            moveX = thisidx * moveWidth;
            if(numcheck) {
                numCheckFn(thisidx+1);
            }
            if(basicobj.totmove) {
                if(thisidx == checkMaxNum-1) {
                    moveX = (totLen-moveLen) * moveWidth;
                }else{
                    moveX = thisidx * moveLen * moveWidth;
                }
            }
            moveElem(moveX);
            oldidx = thisidx;
            stopInterval();
        }

        /* focus event */
        function listFocusEvt(e){
            e.preventDefault();
            stopInterval();
            if(viewLen >= totLen) return;
            speed = 0;
            var focusindex = $(this).parent().index();

            if(focusindex == 0) {
                moveElem( 0 );
            }
            if(focusindex >= totLen-1) {
                var shifTabBasic;
                if(basicobj.totmove) {
                    shifTabBasic = (totLen-moveLen) * moveWidth;
                } else {
                    shifTabBasic = parseInt(checkMaxNum - 1) * moveWidth;
                }

                moveElem( shifTabBasic );
            }

          /*  if(numcheck) {
                numCheckFn(focusindex + 1);
            }*/

        }

        /* keydown event */
        function listLnkKeyEvt(e){
            var moveX;
            var keydownindex = $(this).parent().index();
            thisidx = keydownindex;
            var moveidx = thisMoveIdx( thisidx );

            if(keydownindex == 0) return;
            speed = 0;
            if(e.shiftKey && e.keyCode == 9) {
                if(keydownindex > (totLen) - viewLen) return;
                e.preventDefault();
                moveX = (thisidx-1) * moveWidth;
                moveElem( moveX );
                $elemList.eq(thisidx-1).find('a').focus();
            } else if(e.keyCode == 9){
                if(thisidx == totLen-1) return;
                if(thisidx < viewLen-1) return;

                e.preventDefault();

                moveX = ((thisidx + 1) - (viewLen-1)) * moveWidth;

                moveElem(moveX);
                $elemList.eq(thisidx + 1).find('a').focus();
                //              }
            }
            thisidx = moveidx;
            oldidx = thisidx;
            stopInterval();
        }

        /* retrun index */
        function thisMoveIdx( thisidx ){
            var returnIdx;
            returnIdx = parseInt((thisidx) / moveLen)+1;
            return returnIdx;
        }

        /* active list */
        self.activeList = function( idx ){
            var moveX;
            speed = basicobj.speed;
            thisidx = idx;
            $elemList.eq(idx).addClass('active');
            if(thisidx >= totLen) {
                console.log("slideList Max length over error");
                return;
            }

            if(thisidx >= checkMaxNum) {
                thisidx = checkMaxNum-1;
            }
            if(basicobj.totmove) {
                thisidx = thisMoveIdx( idx );
                if(thisidx == checkMaxNum) {
                    moveX = (totLen-moveLen) * moveWidth;
                } else {
                    moveX = (thisidx-1) * moveLen * moveWidth;
                }
                thisidx = thisidx - 1;

            } else {
                moveX = thisidx * moveWidth;
            }

            moveElem(moveX);
        };

        /* animation */
        function moveElem( moveX ){
            TweenMax.killTweensOf($elemMove);
            TweenMax.to($elemMove, speed, { marginLeft: -moveX, ease: Expo.easeOut });
        }

        /* add event */
        function add_Evt(){
            self.onEvtSet();
            $elemList.swipe( swipeOptions );
            $(window).on('resize', resizeSet );
            $elemPlayBtn.on('click', function() {
                interval();
                $elemStopBtn.focus();
            } );
            $elemStopBtn.on('click', function() {
                stopInterval();
                $elemPlayBtn.focus();
            } );
            $elemPlayBtn.on('keydown', playBtnfocus );
            $elemStopBtn.on('keydown', stopBtnfocus );
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

        /* on event set */
        self.onEvtSet = function(){
            $elemNextBtn.on('click', nextSlide );
            $elemPrevBtn.on('click', prevSlide );
            $elemListLnk.on('keydown',  listLnkKeyEvt );
            $elemListLnk.on('focus',  listFocusEvt );
        };

        /* off event set */
        self.offEvtSet = function(){
            $elemNextBtn.off('click', nextSlide );
            $elemPrevBtn.off('click', prevSlide );
            $elemListLnk.off('keydown',  listLnkKeyEvt );
            $elemListLnk.off('focus',  listFocusEvt );
        };

        /* 초기 position setting */
        self.setPosition = function( reloadidx ){
            if(reloadidx) {
                thisidx = 0;
            }
            $elemSlideContainer.css({
                'width': $elemSlidebox.width()
            });

            $elemList.css({
                'width' : parseInt($elemSlideContainer.width() / viewLen)
            });

            $elemContainer.css({
                'width': $elemList.width() * totLen
            });

            slideWidth = $elemList.width();

            if(basicobj.totmove) {
                moveWidth = slideWidth;
            } else {
                moveWidth = moveLen * slideWidth
            }
            if(thisidx != 0) {
                self.activeList(0)
            }
            if(basicobj.onMobile) { // onMobile = true
                if(PCUTDFC.IS_VIEWTYPE != 'mobile')  {
                    $elemList.removeAttr('style');
                    $elemContainer.removeAttr('style');
                    $elemSlideContainer.removeAttr('style');
                }
            } else { //onMobile = false
            }
        };

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
                var moveX;
                if(viewLen >= totLen) return;
                speed = basicobj.speed;
                thisidx = thisidx + 1;
                if(thisidx >= checkMaxNum) {
                    thisidx = 0;
                }
                moveX = thisidx * moveWidth;
                if(basicobj.totmove) {
                    if(thisidx == checkMaxNum-1) {
                        moveX = (totLen-moveLen) * moveWidth;
                    } else{
                        moveX = thisidx* moveLen * moveWidth;
                    }

                }

                if(numcheck) {
                    numCheckFn(thisidx+1);
                }

                moveElem(moveX);
                oldidx = thisidx;
            }, 5000);
            activeStop();
        }

        function stopInterval(){
            clearInterval(autoTimer);
            activePlay();
        }

        function numCheckFn($num) {
            var $curPage = $elem.find(".js-current-num");
            $curPage.text($num);
        }

        /* element setting */
        self.controlSet = function( basicobj ){
            $elem = $slide,
            $elemNextBtn = (basicobj.nextbtn) ? basicobj.nextbtn : $elem.find('.bas-next'),
            $elemPrevBtn = (basicobj.prevbtn) ? basicobj.prevbtn : $elem.find('.bas-prev'),
            $elemStopBtn = (basicobj.stopbtn) ? basicobj.stopbtn : $elem.find('.bas-stop'),
            $elemPlayBtn = (basicobj.playbtn) ? basicobj.playbtn : $elem.find('.bas-play'),
            $elemSlidebox = $elem.find('.basic-slide'),
            $elemSlideContainer = $elem.find('.basic-slide-container'),
            $elemContainer = $elem.find('.slide'),
            $elemList = $elem.find('.slide > li'),
            $elemListLnk = $elemList.find('a'),
            $elemMove = $elemContainer,
            $autoMove = basicobj.autoMove;
            numcheck = basicobj.numcheck;

            totLen = $elemList.length;
            viewLen = deviceLen( basicobj.viewLen );
            speed = basicobj.speed;

            if(basicobj.totmove) {
                moveLen = viewLen;
                checkMaxNum = Math.ceil(totLen/moveLen);
            } else {
                moveLen = 1;
                checkMaxNum = parseInt(totLen - (viewLen * moveLen) + 1);
            }
            checkMinNum = 0;
        };

        /* response element length retrun */
        function deviceLen ( arr ) {
            var checkDeviceLen;
            switch(PCUTDFC.IS_VIEWTYPE) {
                case 'web':
                    checkDeviceLen = arr[2];
                    break;
                case 'tablet':
                    checkDeviceLen = arr[1];
                    break;
                case 'mobile':
                    checkDeviceLen = arr[0];
                    break;
            }
            return checkDeviceLen;
        }

        /* resize function */
        function resizeSet() {
            SetViewSize();
            DeivceChkFn();
            self.controlSet( basicobj );
            self.setPosition();
            numAttrFn();
        }

        /* swipe option */
        function swipeStatus(event, phase, direction, distance, duration){
            if (phase == "move" && (direction == "left" || direction == "right")) {
            } else if ( phase == "cancel" || phase =="end"){
                if(distance>75){
                    if (direction == "right"){
                        prevSlide();
                    } else if (direction == "left"){
                        nextSlide();
                    }
                }
            }
        }

        /* json :: dom make */
        function domMake( data ){
            var keyArray = [];
            for(key in data) {
                keyArray.push(key);
            }

            var makeHtml = '';
            makeHtml += '<ul>';
            for (var i= 0, slideNum = keyArray.length; i < slideNum; i++) {
                makeHtml += '<li><a href="#lnk1">'+ data[i] +'</a></li>';
            }
            makeHtml += '</ul>';

            self.offEvtSet();
            $elemSlideContainer.html('');
            $elemSlideContainer.append(makeHtml);
            self.controlSet( basicobj );

        }

        /* json :: data load success */
        function dataLoadSuccess($value, $status) {
            if($status == 'success') {
                self.controlSet( basicobj );
                domMake( $value );
                slideInit();
            }
        }

        /* json :: data load fail */
        function load_fail(){
            console.log("data load error")
        }

        /* json :: data load */
        self.dataLoad = function ( dataurl ) {
            var ajaxobj = {};
            ajaxobj.url = dataurl;
            ajaxobj.type = 'post';
            ajaxobj.dataType = 'JSON';
            ajaxobj.success = dataLoadSuccess;
            ajaxobj.fail = load_fail;
            $.ajax(ajaxobj);
        };

        /* slide init function */
        function slideInit(){
            swipeOptions = {
                triggerOnTouchEnd : true,
                swipeStatus : PCUTDFC.IS_MOBILE && swipeStatus,
                allowPageScroll:"vertical",
                threshold:80,
                excludedElements : []
            };

            self.setPosition();
            add_Evt( basicobj );
        }

        function numAttrFn() {
            if(numcheck) {
                var $text,
                    $totPage = $elem.find(".js-total-num");
                if(basicobj.totmove) {

                    $text = parseInt(totLen/moveLen);
                    if(totLen%moveLen !== 0)  $text = $text+1;
                } else {
                    $text = totLen;
                }

                $totPage.text($text);
                $curNum = 1;
                numCheckFn($curNum);
            }
        }

        /* slide init */
        self.init = function( controlobj ) {
            basicobj = controlobj;
            SetViewSize();
            DeivceChkFn();
            CheckMobile();
            if (basicobj.dataJSON) {
                self.dataLoad( basicobj.dataJSON );
            } else {
                self.controlSet( basicobj );
                slideInit();
            }

            if($autoMove) {
                interval();
            } else {
                stopInterval();
            }

            numAttrFn();
        };
    }
})(jQuery);