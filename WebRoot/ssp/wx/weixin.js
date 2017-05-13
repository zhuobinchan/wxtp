var JS_SDK = {
	timestamp : '', // 必填，生成签名的时间戳
	nonceStr : '', // 必填，生产签名的随机串
	signature : ''// 必填，签名，
};

var windowLocationHref = encodeURIComponent(window.location.href.split('#')[0]);

$.ajax({
	type : "GET",
	url : "http://zhuobinchan.tunnel.2bdata.com/wxtp/gradeJsSdkAction.action?url="
			+ windowLocationHref,	
	dataType : "jsonp",
	jsonp : "callback",
	jsonpCallback : "success_jsonpCallback",
	success : function(data) {
		var json = eval(data);
		JS_SDK.timestamp = json.time;
		JS_SDK.nonceStr = json.nonceStr;
		JS_SDK.signature = json.signature;
		
//		alert(JS_SDK.timestamp + "+++" + JS_SDK.nonceStr + "==="
//				+ JS_SDK.signature);
				
		wx.config({
			appId : 'wxc7eee0d7cb088852',//海印微办公
			timestamp : JS_SDK.timestamp,
			nonceStr : JS_SDK.nonceStr,
			signature : JS_SDK.signature,
			jsApiList : [

			'startRecord', 'stopRecord', 'onRecordEnd', 'playVoice',
					'pauseVoice', 'stopVoice', 'uploadVoice',
					'downloadVoice', 'chooseImage',
					'previewImage', 'uploadImage', 'downloadImage'
					,'onMenuShareTimeline'
					,'onMenuShareAppMessage'
					,'onMenuShareQQ'
					,'onMenuShareWeibo'
					,'onMenuShareQZone'
			]
		});

	},
	error : function(jqXHR) {
		alert("网络异常！请重试" + jqXHR.status);
	}
});

