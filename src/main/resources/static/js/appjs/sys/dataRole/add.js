//
$(function() { 
	validateRule();
});
$.validator.setDefaults({
	submitHandler : function() { 
		save();
	}
});
 

function save() {
	var role = $('#signupForm').serialize();
	$.ajax({
		cache : true,
		type : "POST",
		url : "/sys/dataRole/save",
		data : role, // 你的formid

		async : false,
		error : function(request) {
			alert("Connection error");
		},
		success : function(data) {
			if (data.status == 'OK') {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引

				parent.layer.close(index);

			} else {
				parent.layer.msg(data.message);
			}
		}
	});
}

function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			roleName : {
				required : true
			}
		},
		messages : {
			dataRoleName : {
				required : icon + "请输入角色名"
			},
			dataRoleType : {
				required : icon + "请输入角色类型"
			}
		}
	});
}