var images = {
		localId : [],
		serverId : []
};

wx.ready(function(){
	
});

//wx.chooseImage({
//    count: 1, // 默认9
//    sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
//    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
//    success: function (res) {
//        var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
//
//    }
//});
//
//wx.uploadImage({
//    localId: '', // 需要上传的图片的本地ID，由chooseImage接口获得
//    isShowProgressTips: 1, // 默认为1，显示进度提示
//    success: function (res) {
//        var serverId = res.serverId; // 返回图片的服务器端ID
//    }
//});




function chooseImage() {
	wx.chooseImage({
		count: 1,
		success : function(res) {
			var localIds = res.localIds;
			uploadImage(localIds);
		}
	});
}

function previewImage(imageLocalId) {
	wx.previewImage({
				current : imageLocalId,
				urls : [imageLocalId]
			
			});
}

function uploadImage(localIds){
	if (localIds.length == 0) {
		alert('请先使用 chooseImage 接口选择图片');
		return;
	}
	var i = 0, length = localIds.length;
//	var serverId = [];
	function upload() {
		wx.uploadImage({
			localId : localIds[i],
			success : function(res) {
				
//				serverId.push(res.serverId);

				/* 1、选择完图片后给返回serverId */
				$("#id2").attr("src",localIds[i]);
				$("#imgUrl").val(res.serverId);
				i++;
				if (i < length) {
					upload();
				}
			},
			fail : function(res) {
				alert(JSON.stringify(res));
			}
		});
	}
	upload();
}

function downloadImage(wsServerId,wsImage){
	if (wsServerId == null) {
		return;
	}
	var i = 0, length = images.serverId.length;
	images.localId = [];
	function download() {
		wx.downloadImage({
			serverId : wsServerId,
			success : function(res) {
				images.localId.push(res.localId);

				wsImage.setAttribute("src",res.localId);
				wsImage.setAttribute("onclick",'previewImage(\''+res.localId+'\')');

				i++;
				if (i < length) {
					download();
				}
			},
			fail : function(res) {
				alert(JSON.stringify(res));
			}
		});
	}
	download();
}