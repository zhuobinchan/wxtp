wx.ready(function(){
	wx.onMenuShareTimeline({
	    title: "我参与狮王争霸活动", // 分享标题
	    link: JS_SDK_Timeline.domainName+"/wxtp/L/index.html", // 分享链接
	    imgUrl: JS_SDK_Timeline.domainName+"/wxtp/L/images/shareMessageImg.png", // 分享图标
	    success: function () { 
	        $.post("shareMessage_PollTeamToTimeline.action",
	        	function(result){
	        		if(result.message.trim()=="true"){
	        			alert("已经成功分享到朋友圈");
	        			window.location.href = JS_SDK_Timeline.domainName+"/wxtp/choujiang/index.jsp";
	        		}
	        	},"json");
	    },
	    cancel: function () { 
	        alert("要分享到朋友圈才能参与抽奖喔，亲");
	    }
	});
	wx.onMenuShareAppMessage({
		title: "我参与狮王争霸活动", // 分享标题
		link: JS_SDK_Timeline.domainName+"/wxtp/L/index.html", // 分享链接
		imgUrl: JS_SDK_Timeline.domainName+"/wxtp/L/images/shareMessageImg.png", // 分享图标
	    success: function () { 
	    },
	    cancel: function () { 
	    }
	});
});