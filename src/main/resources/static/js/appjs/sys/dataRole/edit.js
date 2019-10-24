var menuIds;
$(function() { 
	validateRule();
});
$.validator.setDefaults({
	submitHandler : function() { 
		update();
	}
}); 
function update() { 
	var role = $('#signupForm').serialize();
	$.ajax({
		cache : true,
		type : "POST",
		url : "/sys/dataRole/update",
		data : role, // 你的formid
		async : false,
		error : function(request) {
			alert("Connection error");
		},
		success : function(r) {
			if (r.status == 'OK') {
				parent.layer.msg(r.message);
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.msg(r.message);
			}

		}
	});
}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			dataRoleName : {
				required : true
			},
			dataRoleType: {
				required : true
			}
		},
		messages : {
			dataRoleName : {
				required : icon + "请输入数据角色名"
			},
			dataRoleType : {
				required : icon + "请输入数据角色名"
			}
		}
	});
}