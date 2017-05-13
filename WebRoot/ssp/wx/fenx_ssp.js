wx.ready(function(){
	wx.onMenuShareTimeline({
	    title: "我参与随手拍活动", // 分享标题
	    link: JS_SDK_Timeline.domainName+"/wxtp/ssp/one-02.html", // 分享链接
	    imgUrl: JS_SDK_Timeline.domainName+"/wxtp/ssp/images/fenx_ssp.jpg", // 分享图标
	    success: function () { 
	    },
	    cancel: function () { 
	    }
	});
	wx.onMenuShareAppMessage({
		title: "我参与随手拍活动", // 分享标题
		link: JS_SDK_Timeline.domainName+"/wxtp/ssp/one-02.html", // 分享链接
		imgUrl: JS_SDK_Timeline.domainName+"/wxtp/ssp/images/fenx_ssp.jpg", // 分享图标
	    success: function () { 
	    },
	    cancel: function () { 
	    }
	});
});