var resourceIds;
$(function() {
	getdataTreeData();
	validateRule();
});
$.validator.setDefaults({
	submitHandler : function() {
		getAllSelectNodes();
		update();
	}
});
function loaddataTree(dataTree) {
	$('#dataTree').jstree({
		"plugins" : [ "wholerow", "checkbox" ],
		'core' : {
			'data' : dataTree
		},
		"checkbox" : {
			//"keep_selected_style" : false,
			//"undetermined" : true
			//"three_state" : false,
			//"cascade" : ' up'
		}
	});
	$('#dataTree').jstree('open_all');
}
function getAllSelectNodes() {
	var ref = $('#dataTree').jstree(true); // 获得整个树
	resourceIds = ref.get_selected(); // 获得所有选中节点的，返回值为数组
	$("#dataTree").find(".jstree-undetermined").each(function(i, element) {
		resourceIds.push($(element).closest('.jstree-node').attr("id"));
	});
	console.log(resourceIds); 
}
function getdataTreeData() {
	var id = $('#id').val();
	$.ajax({
		type : "GET",
		url : "/sys/dataRole/allTree/" + id,
		success : function(data) {
			loaddataTree(data);
		}
	});
}
function update() {
	$('#resourceIds').val(resourceIds);
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
			roleName : {
				required : true
			}
		},
		messages : {
			roleName : {
				required : icon + "请输入角色名"
			}
		}
	});
}