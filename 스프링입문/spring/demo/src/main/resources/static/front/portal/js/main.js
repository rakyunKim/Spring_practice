/**
 * @since 2020-02
 * @version 1.0.0
 */


(function($) {
	'use strict';

	var $window = $(window);

	$(function() {
		

		$window.on('screen:wide.menu screen:web.menu', function(event) {
			window.mode = 'pc';
		});

		$window.on('screen:tablet.menu screen:phone.menu', function(event) {
			window.mode = 'mobile';
		});
		
		//schedule
		var $scheduleSlide = $('.schedule .schedule_list');
		var scheduleSlideLength = $('.schedule_list .schedule_item').length;
		$scheduleSlide.addClass('divide'+scheduleSlideLength);
		function schedulereinit() {
			$scheduleSlide.trigger('destroy.owl.carousel');
			$scheduleSlide.owlCarousel({
				center: false,
				items: scheduleSlideLength,
				loop: false,
				nav: false,
				mouseDrag: false,
				touchDrag: true,
				dots: false,
				responsive: {
				  0: {
					items: 13,
					autoWidth: false,
					mouseDrag: true,
					touchDrag: true
				  },
				  640: {
					items: 19,
					autoWidth: false,
					mouseDrag: true,
					touchDrag: true
				  },
				  1000: {
					items: scheduleSlideLength,
					autoWidth: false,
					mouseDrag: true,
					touchDrag: true
				  }
				}
			});
		}
		$scheduleSlide.owlCarousel({
			center: false,
			items: scheduleSlideLength,
			loop: false,
			nav: false,
			mouseDrag: false,
			touchDrag: true,
			responsive: {
			  0: {
				items: 13,
				autoWidth: false,
				mouseDrag: true,
				touchDrag: true
			  },
			  640: {
				items: 19,
				autoWidth: false,
				mouseDrag: true,
				touchDrag: true
			  },
			  1000: {
				items: scheduleSlideLength,
				autoWidth: false,
				mouseDrag: true,
				touchDrag: true
			  }
			}
		});
		$window.on('screen:wide screen:web', function(event) {
			schedulereinit();
		});
		$window.on('screen:tablet screen:phone', function(event) {
			schedulereinit();
		});
		var ScheduleFocusPoint = 0;
		$('.schedule .schedule_item.exist .schedule_btn').on('click', function() {
			var $this = $(this),
				$MyParent = $this.parents('.owl-item'),
				IsSelected = $MyParent.is('.selected'),
				OffsetLeft = $MyParent.position().left,
				$schedulebox = $this.parents('.schedulebox'),
				ScheduleboxWidth = $schedulebox.outerWidth(),
				ScheduleboxPaddingLeft = $schedulebox.css('padding-left'),
				PaddingLeftResult = ScheduleboxPaddingLeft.replace('px',''),
				PaddingLeftResultNum = Number(PaddingLeftResult),
				ScheduleboxPaddingRight = $schedulebox.css('padding-right'),
				PaddingRightResult = ScheduleboxPaddingRight.replace('px',''),
				PaddingRightResultNum = Number(PaddingRightResult),
				ParentIndex = $MyParent.index(),
				ParentWidth = $MyParent.width(),
				LeftOffset = OffsetLeft+PaddingLeftResultNum+2,
				RightOffset = ScheduleboxWidth-OffsetLeft-ParentWidth-PaddingRightResultNum-1,
				ThisDate = $this.attr('data-date'),
				$OtherParents = $MyParent.siblings('.owl-item'),
				$OtherBtns = $OtherParents.find('.schedule_btn'),
				$layerbox = $this.parents('.schedulebox').siblings('.layerbox'),
				$layerCloseBtn = $layerbox.find('.close'),
				LayerboxIsActive = $layerbox.is('.active'),
				$ItemListbox = $layerbox.find('.itemlistbox'),
				$MyLayer = $ItemListbox.find('.itembox[data-date="'+ThisDate+'"]'),
				$MyLayerFocusPoint = $MyLayer.find('.listbox').find('li:first-child').find('a'),
				$OtherLayer = $MyLayer.siblings('.itembox'),
				NowState = $.screen.settings.state[0];
			console.log(OffsetLeft);
			if(!IsSelected){
				if(ParentIndex<16){
					$layerbox.removeAttr('data-right').css('right', 'auto').css('left', LeftOffset+'px');
				} else{
					$layerbox.attr('data-right', 'y').css('left', 'auto').css('right', RightOffset+'px');
				};
				if(!LayerboxIsActive){
					$layerbox.addClass('active').fadeIn();
				};
				$OtherParents.removeClass('selected');
				$OtherBtns.removeAttr('title');
				$MyParent.addClass('selected');
				$this.attr('title', '�좏깮��');
				$OtherLayer.removeClass('active');
				$MyLayer.addClass('active');
				ScheduleFocusPoint = ThisDate;
				$MyLayerFocusPoint.focus();
			}
		});
		$('.schedule .layerbox .layer .close').on('click', function() {
			var $this = $(this),
				$LayerBox = $this.parents('.layerbox'),
				$itembox = $this.siblings('.itemlistbox').find('.itembox'),
				$OwlItem = $scheduleSlide.find('.owl-item'),
				$OwlItemSelected = $scheduleSlide.find('.owl-item.selected'),
				$OwlItemSelectedBtn = $OwlItemSelected.find('.schedule_btn');
			$OwlItemSelected.removeClass('selected');
			$OwlItemSelectedBtn.removeAttr('title').focus();
			$LayerBox.removeClass('active').fadeOut(function() {
				$LayerBox.removeAttr('data-right').removeAttr('style');
			});
		});
		$('.schedule .layerbox .curtain').on('click', function() {
			var $this = $(this),
				$LayerBox = $this.parents('.layerbox'),
				$itembox = $this.siblings('.layer').find('.itembox'),
				$OwlItem = $scheduleSlide.find('.owl-item'),
				$OwlItemSelected = $scheduleSlide.find('.owl-item.selected'),
				$OwlItemSelectedBtn = $OwlItemSelected.find('.schedule_btn');
			$OwlItemSelected.removeClass('selected');
			$OwlItemSelectedBtn.removeAttr('title').focus();
			$LayerBox.removeClass('active').fadeOut(function() {
				$LayerBox.removeAttr('data-right').removeAttr('style');
			});
		});

		var $performanceListbox = $('.performancebox .tab_content .itembox');
		$performanceListbox.each(function(){

			var $ItemSlide = $(this).find('.slide_list'),
				$SlideItem = $ItemSlide.find('.slide_item'),
				$SlideControl = $ItemSlide.siblings('.slide_control'),
				$ControlBox = $ItemSlide.siblings('.controlbox'),
				$CountBox = $(this).find('.count');

			$ItemSlide.slick({
				autoplay : true,
				dots:false,
				slidesToShow: 1,
				slidesToScroll: 1,
				prevArrow : $SlideControl.find('.prev'),
				nextArrow : $SlideControl.find('.next'),
				pauseOnDotsHover : true,
				swipe:false,
				draggable:false,
				infinite: true,
				centerMode: true,
                centerPadding: 0,
				variableWidth: true,
				autoArrow : $ControlBox.find('.auto'),
				isRunOnLowIE : false,
				pauseOnArrowClick : true,
				pauseOnDirectionKeyPush : true,
				pauseOnSwipe : true,
				pauseOnDotsClick : true,
				pauseText : '�뺤�',
				playText : '�ъ깮',
				total : $CountBox.find('.total'),
				current : $CountBox.find('.current'),
				responsive: [
				{
				  breakpoint: 1001,
				  settings: {
					swipe:true,
					draggable:true
				  }
				},
				{
				  breakpoint: 641,
				  settings: {
					swipe:true,
					draggable:true,
					variableWidth: false
				  }
				}]
			});
		});
		$('.performance .tabbox .tab_btn').on('click', function() {
			var $this = $(this),
				$MyParent = $this.parent('li'),
				ParentIndex = $MyParent.index(),
				IsActive = $MyParent.is('.active'),
				$OtherParents = $MyParent.siblings('li'),
				$OtherBtns = $OtherParents.find('.tab_btn'),
				$tab_content = $this.parents('.tabbox').siblings('.tab_content'),
				$MyItem = $tab_content.find('.itembox').eq(ParentIndex),
				$OtherItems = $MyItem.siblings('.itembox'),
				$MySlide = $MyItem.find('.slide_list');
			if(!IsActive){
				$OtherParents.removeClass('active');
				$OtherBtns.removeAttr('title');
				$MyParent.addClass('active');
				$this.attr('title', '�좏깮��');
				$OtherItems.removeClass('active');
				$MyItem.addClass('active');
				$MySlide.slick('setPosition');
			};
		});
		
	});
})(window.jQuery);