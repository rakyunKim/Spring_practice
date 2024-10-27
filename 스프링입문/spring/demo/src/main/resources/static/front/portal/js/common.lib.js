function get_version_of_IE () {
    var word;
    var version = "N/A";
    var agent = navigator.userAgent.toLowerCase();
    var name = navigator.appName;

    // IE old version ( IE 10 or Lower )
    if ( name == "Microsoft Internet Explorer" ) word = "msie ";
    else {
        // IE 11
        if ( agent.search("trident") > -1 ) word = "trident/.*rv:";
        // Microsoft Edge
        else if ( agent.search("edge/") > -1 ) word = "edge/";
    }
    var reg = new RegExp( word + "([0-9]{1,})(\\.{0,}[0-9]{0,1})" );
    if (  reg.exec( agent ) != null  ) version = RegExp.$1 + RegExp.$2;
    return version;
}

function checkVersion () {
    var verNumber = parseInt ( get_version_of_IE() , 10 );
    if(verNumber == '8'){
        $('html').addClass('ie8');
    } else if(verNumber == '9') $('html').addClass('ie9');
}
checkVersion();


/*! modernizr 3.5.0 (Custom Build) | MIT *
 * https://modernizr.com/download/?-csstransitions-preserve3d-setclasses-shiv !*/
!function(e,t,n){function r(e){var t=b.className,n=Modernizr._config.classPrefix||"";if(w&&(t=t.baseVal),Modernizr._config.enableJSClass){var r=new RegExp("(^|\\s)"+n+"no-js(\\s|$)");t=t.replace(r,"$1"+n+"js$2")}Modernizr._config.enableClasses&&(t+=" "+n+e.join(" "+n),w?b.className.baseVal=t:b.className=t)}function o(){return"function"!=typeof t.createElement?t.createElement(arguments[0]):w?t.createElementNS.call(t,"http://www.w3.org/2000/svg",arguments[0]):t.createElement.apply(t,arguments)}function a(e,t){return typeof e===t}function i(){var e,t,n,r,o,i,s;for(var l in S)if(S.hasOwnProperty(l)){if(e=[],t=S[l],t.name&&(e.push(t.name.toLowerCase()),t.options&&t.options.aliases&&t.options.aliases.length))for(n=0;n<t.options.aliases.length;n++)e.push(t.options.aliases[n].toLowerCase());for(r=a(t.fn,"function")?t.fn():t.fn,o=0;o<e.length;o++)i=e[o],s=i.split("."),1===s.length?Modernizr[s[0]]=r:(!Modernizr[s[0]]||Modernizr[s[0]]instanceof Boolean||(Modernizr[s[0]]=new Boolean(Modernizr[s[0]])),Modernizr[s[0]][s[1]]=r),C.push((r?"":"no-")+s.join("-"))}}function s(e,t){return function(){return e.apply(t,arguments)}}function l(e,t,n){var r;for(var o in e)if(e[o]in t)return n===!1?e[o]:(r=t[e[o]],a(r,"function")?s(r,n||t):r);return!1}function c(e,t){return!!~(""+e).indexOf(t)}function u(e){return e.replace(/([a-z])-([a-z])/g,function(e,t,n){return t+n.toUpperCase()}).replace(/^-/,"")}function f(e){return e.replace(/([A-Z])/g,function(e,t){return"-"+t.toLowerCase()}).replace(/^ms-/,"-ms-")}function d(t,n,r){var o;if("getComputedStyle"in e){o=getComputedStyle.call(e,t,n);var a=e.console;if(null!==o)r&&(o=o.getPropertyValue(r));else if(a){var i=a.error?"error":"log";a[i].call(a,"getComputedStyle returning null, its possible modernizr test results are inaccurate")}}else o=!n&&t.currentStyle&&t.currentStyle[r];return o}function p(){var e=t.body;return e||(e=o(w?"svg":"body"),e.fake=!0),e}function m(e,n,r,a){var i,s,l,c,u="modernizr",f=o("div"),d=p();if(parseInt(r,10))for(;r--;)l=o("div"),l.id=a?a[r]:u+(r+1),f.appendChild(l);return i=o("style"),i.type="text/css",i.id="s"+u,(d.fake?d:f).appendChild(i),d.appendChild(f),i.styleSheet?i.styleSheet.cssText=e:i.appendChild(t.createTextNode(e)),f.id=u,d.fake&&(d.style.background="",d.style.overflow="hidden",c=b.style.overflow,b.style.overflow="hidden",b.appendChild(d)),s=n(f,e),d.fake?(d.parentNode.removeChild(d),b.style.overflow=c,b.offsetHeight):f.parentNode.removeChild(f),!!s}function h(t,r){var o=t.length;if("CSS"in e&&"supports"in e.CSS){for(;o--;)if(e.CSS.supports(f(t[o]),r))return!0;return!1}if("CSSSupportsRule"in e){for(var a=[];o--;)a.push("("+f(t[o])+":"+r+")");return a=a.join(" or "),m("@supports ("+a+") { #modernizr { position: absolute; } }",function(e){return"absolute"==d(e,null,"position")})}return n}function g(e,t,r,i){function s(){f&&(delete T.style,delete T.modElem)}if(i=a(i,"undefined")?!1:i,!a(r,"undefined")){var l=h(e,r);if(!a(l,"undefined"))return l}for(var f,d,p,m,g,v=["modernizr","tspan","samp"];!T.style&&v.length;)f=!0,T.modElem=o(v.shift()),T.style=T.modElem.style;for(p=e.length,d=0;p>d;d++)if(m=e[d],g=T.style[m],c(m,"-")&&(m=u(m)),T.style[m]!==n){if(i||a(r,"undefined"))return s(),"pfx"==t?m:!0;try{T.style[m]=r}catch(y){}if(T.style[m]!=g)return s(),"pfx"==t?m:!0}return s(),!1}function v(e,t,n,r,o){var i=e.charAt(0).toUpperCase()+e.slice(1),s=(e+" "+_.join(i+" ")+i).split(" ");return a(t,"string")||a(t,"undefined")?g(s,t,r,o):(s=(e+" "+N.join(i+" ")+i).split(" "),l(s,t,n))}function y(e,t,r){return v(e,n,n,t,r)}var C=[],S=[],E={_version:"3.5.0",_config:{classPrefix:"",enableClasses:!0,enableJSClass:!0,usePrefixes:!0},_q:[],on:function(e,t){var n=this;setTimeout(function(){t(n[e])},0)},addTest:function(e,t,n){S.push({name:e,fn:t,options:n})},addAsyncTest:function(e){S.push({name:null,fn:e})}},Modernizr=function(){};Modernizr.prototype=E,Modernizr=new Modernizr;var b=t.documentElement,w="svg"===b.nodeName.toLowerCase();w||!function(e,t){function n(e,t){var n=e.createElement("p"),r=e.getElementsByTagName("head")[0]||e.documentElement;return n.innerHTML="x<style>"+t+"</style>",r.insertBefore(n.lastChild,r.firstChild)}function r(){var e=C.elements;return"string"==typeof e?e.split(" "):e}function o(e,t){var n=C.elements;"string"!=typeof n&&(n=n.join(" ")),"string"!=typeof e&&(e=e.join(" ")),C.elements=n+" "+e,c(t)}function a(e){var t=y[e[g]];return t||(t={},v++,e[g]=v,y[v]=t),t}function i(e,n,r){if(n||(n=t),f)return n.createElement(e);r||(r=a(n));var o;return o=r.cache[e]?r.cache[e].cloneNode():h.test(e)?(r.cache[e]=r.createElem(e)).cloneNode():r.createElem(e),!o.canHaveChildren||m.test(e)||o.tagUrn?o:r.frag.appendChild(o)}function s(e,n){if(e||(e=t),f)return e.createDocumentFragment();n=n||a(e);for(var o=n.frag.cloneNode(),i=0,s=r(),l=s.length;l>i;i++)o.createElement(s[i]);return o}function l(e,t){t.cache||(t.cache={},t.createElem=e.createElement,t.createFrag=e.createDocumentFragment,t.frag=t.createFrag()),e.createElement=function(n){return C.shivMethods?i(n,e,t):t.createElem(n)},e.createDocumentFragment=Function("h,f","return function(){var n=f.cloneNode(),c=n.createElement;h.shivMethods&&("+r().join().replace(/[\w\-:]+/g,function(e){return t.createElem(e),t.frag.createElement(e),'c("'+e+'")'})+");return n}")(C,t.frag)}function c(e){e||(e=t);var r=a(e);return!C.shivCSS||u||r.hasCSS||(r.hasCSS=!!n(e,"article,aside,dialog,figcaption,figure,footer,header,hgroup,main,nav,section{display:block}mark{background:#FF0;color:#000}template{display:none}")),f||l(e,r),e}var u,f,d="3.7.3",p=e.html5||{},m=/^<|^(?:button|map|select|textarea|object|iframe|option|optgroup)$/i,h=/^(?:a|b|code|div|fieldset|h1|h2|h3|h4|h5|h6|i|label|li|ol|p|q|span|strong|style|table|tbody|td|th|tr|ul)$/i,g="_html5shiv",v=0,y={};!function(){try{var e=t.createElement("a");e.innerHTML="<xyz></xyz>",u="hidden"in e,f=1==e.childNodes.length||function(){t.createElement("a");var e=t.createDocumentFragment();return"undefined"==typeof e.cloneNode||"undefined"==typeof e.createDocumentFragment||"undefined"==typeof e.createElement}()}catch(n){u=!0,f=!0}}();var C={elements:p.elements||"abbr article aside audio bdi canvas data datalist details dialog figcaption figure footer header hgroup main mark meter nav output picture progress section summary template time video",version:d,shivCSS:p.shivCSS!==!1,supportsUnknownElements:f,shivMethods:p.shivMethods!==!1,type:"default",shivDocument:c,createElement:i,createDocumentFragment:s,addElements:o};e.html5=C,c(t),"object"==typeof module&&module.exports&&(module.exports=C)}("undefined"!=typeof e?e:this,t),Modernizr.addTest("preserve3d",function(){var t,n,r=e.CSS,a=!1;return r&&r.supports&&r.supports("(transform-style: preserve-3d)")?!0:(t=o("a"),n=o("a"),t.style.cssText="display: block; transform-style: preserve-3d; transform-origin: right; transform: rotateY(40deg);",n.style.cssText="display: block; width: 9px; height: 1px; background: #000; transform-origin: right; transform: rotateY(40deg);",t.appendChild(n),b.appendChild(t),a=n.getBoundingClientRect(),b.removeChild(t),a=a.width&&a.width<4)});var x="Moz O ms Webkit",_=E._config.usePrefixes?x.split(" "):[];E._cssomPrefixes=_;var N=E._config.usePrefixes?x.toLowerCase().split(" "):[];E._domPrefixes=N;var k={elem:o("modernizr")};Modernizr._q.push(function(){delete k.elem});var T={style:k.elem.style};Modernizr._q.unshift(function(){delete T.style}),E.testAllProps=v,E.testAllProps=y,Modernizr.addTest("csstransitions",y("transition","all",!0)),i(),r(C),delete E.addTest,delete E.addAsyncTest;for(var j=0;j<Modernizr._q.length;j++)Modernizr._q[j]();e.Modernizr=Modernizr}(window,document);



var PCUTDFC = PCUTDFC || {};
PCUTDFC.UI = PCUTDFC.UI || {};
PCUTDFC.VIEWPORT_WIDTH = null;
PCUTDFC.VIEWPORT_HEIGHT = null;
PCUTDFC.IS_MOBILE = false;
PCUTDFC.IS_VIEWTYPE = null;
PCUTDFC.IS_IE8 = ( $('html').hasClass('ie8') ) ? true : false;
PCUTDFC.IS_SIZE = PCUTDFC.IS_SIZE || {};
PCUTDFC.IS_SIZE.MAXMOBILE = 768;
PCUTDFC.IS_SIZE.MAXTABLET = 1023;
PCUTDFC.IS_TABLET = false;
PCUTDFC.FOCUS_ELEM = null;

PCUTDFC.DELAY_FUNC = (function(){
	var timer = 0;
	return function(callback, ms){
		clearTimeout (timer);
		timer = setTimeout(callback, ms);
	};
})();

var SetViewSize = function(){
	PCUTDFC.VIEWPORT_WIDTH = Math.max(document.documentElement.clientWidth, window.innerWidth || 0);
	PCUTDFC.VIEWPORT_HEIGHT = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);
};

var CheckMobile = function(){
	var mobileInfo = ['Android', 'iPhone', 'iPod', 'iPad', 'BlackBerry', 'Windows CE', 'SAMSUNG', 'LG', 'MOT', 'SonyEricsson'];
	$.each(mobileInfo, function(index){
		if (navigator.userAgent.match(mobileInfo[index]) != null){
			PCUTDFC.IS_MOBILE = true;
		}
	});
};

var DeivceChkFn= function() {
	if(PCUTDFC.VIEWPORT_WIDTH < PCUTDFC.IS_SIZE.MAXMOBILE && PCUTDFC.IS_MOBILE){
		PCUTDFC.IS_VIEWTYPE = 'mobile';
	} else if(PCUTDFC.VIEWPORT_WIDTH <= PCUTDFC.IS_SIZE.MAXTABLET && PCUTDFC.IS_MOBILE){
		PCUTDFC.IS_VIEWTYPE = 'tablet';
	} else {
		if(PCUTDFC.VIEWPORT_WIDTH < PCUTDFC.IS_SIZE.MAXMOBILE ) {
			PCUTDFC.IS_VIEWTYPE = 'mobile';
		} else if (PCUTDFC.VIEWPORT_WIDTH <= PCUTDFC.IS_SIZE.MAXTABLET ) {
			PCUTDFC.IS_VIEWTYPE = 'tablet';
		} else {
			PCUTDFC.IS_VIEWTYPE = 'web';
		}
	}
};

$(window).resize(function(){
	SetViewSize();
	CheckMobile();
	DeivceChkFn();
});

$(document).ready(function(){
	SetViewSize();
	CheckMobile();
	DeivceChkFn();

	dotlineFn();
});

function dotlineFn() {
	if($('.dot_line').length){
		var $dotline =$('.dot_line');
		$dotline.dotdotdot({
			watch:true
		});
	}
}