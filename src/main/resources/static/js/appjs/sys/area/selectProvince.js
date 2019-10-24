//添加数据到父页面
function addSelectedToFatherView() {
	var ids = '';
	var names = '';
	$('input:checkbox[name=province]:checked').each(function(k){
	    if(k == 0){
	    	ids = $(this).val();
	    	names = $(this).next('span').text();
	    }else{
	    	ids += ','+$(this).val();
	    	names += ','+$(this).next('span').text();
	    }
	})
	if(ids == '') {
		layer.msg('请选择城市');
		return;
	}
	var inputId = $('#inputId').val();
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.sendDataToFatherView(ids, names, inputId);
	parent.layer.close(index);
}
