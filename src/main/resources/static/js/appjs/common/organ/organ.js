var organPrefix = "/sys/organ";
$().ready(function() {
	$(window).on('load', function () {  
		                $('.selectpicker').selectpicker({  
		                    'selectedText': '选择机构'
		                });  
		                // $('.selectpicker').selectpicker('hide');  
		               });  
	 
	loadOrgan();
});

function loadOrgan() {

	$.ajax({
		type : "POST",
		url : organPrefix + "/getOrganList",
		data : {'storeType':$("#stoType").val()},// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(result) {
			var str ='<option value="">全部</option>';
			for (var i = 0; i < result.message.length; i++) {
				var item = result.message[i]; 
				str+=
						'<option value="' + item.SysNo + '">' + item.BriefName
								+ '</option>' ;
			}
			
			  $('#organList').html(str); 
	            $('#organList').selectpicker('refresh');
	            
		}
	}); 
}