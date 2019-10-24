$(function() {
	load();
});
var gridDataColumns;
function getGridDataColumns() {
	gridDataColumns = [
		{
			field : 'id',
			title : 'ID',
			align : 'center',
			valign : 'middle'
		},
		{
			field : 'cellPhone',
			title : '手机号',
			align : 'center',
			valign : 'middle'
		},
			{
				field : 'userRealName',
				title : '会员姓名',
                width:70
			}, {
				field : 'cusTypeDesc',
				title : '会员类型',
				align : 'center',
				valign : 'middle'
			}, {
				field : 'amount',
				title : '提现金额',
				align : 'center',
				valign : 'middle'
			}, {
				field : 'statusDesc',
				title : '提现状态'
			}, {
				field : 'aliAccount',
				title : '提现账号',
				align : 'center',
				valign : 'middle'
			},
			{
				field : 'withdrawTimeDesc',
				title : '提现时间',
				align : 'center',
				valign : 'middle'
			}];
}


serializeObject = function(form) {
	var o = {};
	$.each(form.serializeArray(), function(index) {
		if (o[this['name']]) {
			o[this['name']] = o[this['name']] + "," + this['value'];
		} else {
			o[this['name']] = this['value'];
		}
	});
	return o;
};

function load() {
	getGridDataColumns();
	$('#dataGrid').bootstrapTable({
		method : 'get', // 服务器数据的请求方式 get or post
		url : "/withDrawController/dataGrid", // 服务器数据的加载地址
		iconSize : 'outline',
		toolbar : '#exampleToolbar',
		striped : true, // 设置为true会有隔行变色效果
		dataType : "json", // 服务器返回的数据类型
		pagination : true, // 设置为true会在底部显示分页条
		// //设置为limit则会发送符合RESTFull格式的参数
		singleSelect : false, // 设置为true将禁止多选
		// //发送到服务器的数据编码类型
		pageSize : 10, // 如果设置了分页，每页数据条数
		pageList : [ 10, 20, 30 ],//可选择单页记录数
		pageNumber : 1, // 如果设置了分布，首页页码
		showColumns : false, // 是否显示内容下拉框（选择显示的列）
		sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者
		// detailView: true, //父子表
		queryParams : function(params) {
			return {
				// 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
				limit : params.limit,
				offset : params.offset,  
				idParam : $('#idParam').val(),
				cellPhoneParam:$("#cellPhoneParam").val(),
				cusType:$("#cusType").val()
			};
		},
		// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
		columns : gridDataColumns,
        fixedColumns: true,
        fixedNumber: 1,
        searchAccentNeutralise:true,
        resizable:true,
        showFooter:true,
        classes:"table table-bordered table-hover table-sm"
	});
}

function reLoad() {
	$('#dataGrid').bootstrapTable('refresh');
}

exportExcel = function() {
	layer.confirm('确定导出数据？', {
		btn : [ '确定', '取消' ]
	}, function(index) {
		window.open("/export/withDrawExport?idParam="+$('#idParam').val()+"&cellPhoneParam="+$("#cellPhoneParam").val()+"&cusType="+$("#cusType").val());
		layer.close(index);
	})
};
