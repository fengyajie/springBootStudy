var prefix = "/zg";
$(function() {
	load();
});
var gridDataColumns;
function getGridDataColumns() {
	gridDataColumns = [
		{
			field : 'userId',
			title : '用户ID',
			align : 'center',
			valign : 'middle'
		},
		{
			field : 'userName',
			title : '用户名称',
			align : 'center',
			valign : 'middle'
		},
			{
				field : 'registeDate',
				title : '注册时间',
                width:52
			}, {
				field : 'firstOrderDate',
				title : '首单时间',
				align : 'center',
				valign : 'middle'
			}, {
				field : 'channel',
				title : '渠道来源',
				align : 'center',
				valign : 'middle'
			}, {
				field : 'orderQuantity',
				title : '订单数'
			}, {
				field : 'envelopeAmount',
				title : '红包领取金额',
				align : 'center',
				valign : 'middle'
			},
			{
				field : 'orderTotalAmount',
				title : '交易额',
				align : 'center',
				valign : 'middle'
			},
			{
				field : 'bwBeanTimes',
				title : '借豆次数',
				align : 'center',
				valign : 'middle'
			},
			{
				field : 'orderAmount',
				title : '未使用转换豆金额',
				align : 'center',
				valign : 'middle'
			},
			{
				field : 'averagePrice',
				title : '消费均单价',
				align : 'center',
				valign : 'middle'
			},
			{
				field : 'orderTotalAmountMin',
				title : '最小消费金额',
				align : 'center',
				valign : 'middle'
			},
			{
				field : 'orderTotalAmountMax',
				title : '最大消费金额',
				align : 'center',
				valign : 'middle'
			},
			{
				field : 'useBeans',
				title : '使用抵用豆额',
				align : 'center',
				valign : 'middle'
			},
			{
				field : 'oftenCategory',
				title : '最常购买类目',
				align : 'center',
				valign : 'middle'
			},
			{
				field : 'refundOrder',
				title : '退货订单数',
				align : 'center',
				valign : 'middle'
			},
			{
				field : 'signTimes',
				title : '签到领豆人',
				align : 'center',
				valign : 'middle'
			},
			{
				field : 'conveneQuentity',
				title : '邀请好友人数',
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
		url : prefix + "/zgCusomterDataGrid", // 服务器数据的加载地址
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
				beginDate : $('#beginDate').val(),
				endDate:$("#endDate").val(),
				userName:$("#userName").val(),
				channel:$("#channel").val()
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
		var beginDate = $('#beginDate').val();
		var endDate = $('#endDate').val();
		var userName = $('#userName').val();
		var channel = $("#channel").val();
		window.open("/export/zgCustomerWx?beginDate="+beginDate+"&endDate="+endDate+"&userName="+userName+"&channel="+channel);
		layer.close(index);
	})
};
