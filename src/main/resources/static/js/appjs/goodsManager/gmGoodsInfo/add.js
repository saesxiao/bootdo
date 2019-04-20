$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save() {

	var priceList=$("#goodsPrice").split(",");
	if(priceList==null||priceList.length!=4){
        layer.alert("单价填写错误,格式为: 创始人价格,总经销价格,经销商价格,零售价格");
        return false;
	}

    var loading = layer.load(2, {
        shade: [0.1,'#505050'] //0.1透明度的白色背景
    });
	$.ajax({
		cache : true,
		type : "POST",
		url : "/goodsManager/gmGoodsInfo/save",
		data : $('#signupForm').serialize(),// 你的formid
		async : true,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);
                layer.close(loading);

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});

}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			name : {
				required : true
			}
		},
		messages : {
			name : {
				required : icon + "请输入姓名"
			}
		}
	})
}