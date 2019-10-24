var prefix = "/businessSmall";
$(function() {

	var data=new Date();
	var monthCurr=data.getMonth()+1;
	var yearCurr=data.getFullYear();
	var dayCurr = data.getDate();
	var monLength=monthCurr.toString().length;
	if(monLength==1){
		var monthCurr1="0"+monthCurr
	}else{
		var monthCurr1=monthCurr;
	}

	laydate.render({
		elem : '#beginDate',
		type : 'date',
		value:""+yearCurr+"-"+monthCurr1+"-01"
	});
	laydate.render({
		elem : '#endDate',
		type : 'date',
		value:""+yearCurr+"-"+monthCurr1+"-"+dayCurr+""
	});

	load();
});

var gridDataColumns;
var footerColumns;

function getGridDataColumns() {
	gridDataColumns = [
			{
				field : '',
				title : '序号',
				formatter : function(value, row, index) {

					var pageSize=$('#dataGrid').bootstrapTable('getOptions').pageSize;//通过表的#id 可以得到每页多少条
					var pageNumber=$('#dataGrid').bootstrapTable('getOptions').pageNumber;//通过表的#id 可以得到当前第几页
					return pageSize * (pageNumber - 1) + index + 1;//返回每条的序号： 每页条数 * （当前页 - 1 ）+

				},align:'center'

			}, 
			{field : 'businessName',title : '商户名称',align:'center'},
			{field : 'installTime',title : '安装日期',align:'center'},
			{field : 'firstPurchaseTime',title : '首单采购日期',align:'center'},
			{field : 'sellerName',title : '销售人员',align:'center'},
			{field : 'dgName',title : '督导人员',align:'center'},
			{field : 'address',title : '商户地址',align:'center'},

			{field : 'amount',title : '采购金额',sortable:true,align:'center'},
			{field : 'productNum',title : '采购次数',sortable:true,align:'center'},
			{field : 'amountSelf',title : '内采购金额',sortable:true,align:'center'},
			{field : 'allowanceAmount',title : '内采抵用金',sortable:true,align:'center'},
			{field : 'productNumSelf',title : '内采购次数',sortable:true,align:'center'},
			{field : 'leaseAmount',title : '缴纳货架租金',sortable:true,align:'center'},
			{field : 'commission',title : '佣金',sortable:true,align:'center'}

			];

}

function getFooterColumns(){
	footerColumns=	[ {
		field : '',
		title : '',
		formatter : function(value, row, index) {
			switch (index) {
				case 0:
					return '总计';
			}
		},
		width:80,align:'center',sortable:true
	},
		{field:'amount',title:'采购金额',width:80,align:'center',sortable:true},
		{field:'productNum',title:'采购次数',width:80,align:'center',sortable:true},
		{field:'amountSelf',title:'内采购金额',width:80,align:'center',sortable:true},
		{field:'allowanceAmount',title:'内采抵用金',width:80,align:'center',sortable:true},
		{field:'productNumSelf',title:'内采购次数',width:80,align:'center',sortable:true},
		{field:'leaseAmount',title:'缴纳货架租金',width:80,align:'center',sortable:true},
		{field:'commission',title:'佣金',width:60,align:'center',sortable:true},

	];
}

function load() {
	 getGridDataColumns();
	 getFooterColumns();
	$('#dataGrid').bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : prefix + "/smallOrganReportViewDataGrid", // 服务器数据的加载地址
				// showRefresh : true,
				// showToggle : true,
				// showColumns : true, 
				iconSize : 'outline',
				toolbar : '#exampleToolbar',
				striped : true, // 设置为true会有隔行变色效果
				dataType : "json", // 服务器返回的数据类型
				pagination : true, // 设置为true会在底部显示分页条
				// queryParamsType : "limit",
				// //设置为limit则会发送符合RESTFull格式的参数
				singleSelect : false, // 设置为true将禁止多选
				// contentType : "application/x-www-form-urlencoded",
				// //发送到服务器的数据编码类型
				pageSize : 10, // 如果设置了分页，每页数据条数
				pageNumber : 1, // 如果设置了分布，首页页码
				//search : true, // 是否显示搜索框
				showColumns : false, // 是否显示内容下拉框（选择显示的列）
				sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者
				// "server"
				// showFooter:true,
				// detailView: true, //父子表
				onSort:function(name,order)
				{

					$('#dataGrid').bootstrapTable('refreshOptions', {
						sortName:name,
						sortOrder:order
					});
				},
				queryParams : function(params) {
					return {
						// 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
						limit : params.limit,
						offset : params.offset,
						endDate : $('#endDate').val(),
						beginDate : $('#beginDate').val(),
						businessName : $('#businessName').val(),
						status :$('#status').val(),
						sort:params.sort ,
						order:params.order
					};
				},
				// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
				// queryParamsType = 'limit' ,返回参数必须包含
				// limit, offset, search, sort, order 否则, 需要包含:
				// pageSize, pageNumber, searchText, sortName,
				// sortOrder.
				// 返回false将会终止请求
				columns : gridDataColumns,

			});

	$('#footer').bootstrapTable(
		{
			method : 'get', // 服务器数据的请求方式 get or post
			url : prefix + "/queryBusinessSmallOrganFooter", // 服务器数据的加载地址
			// showRefresh : true,
			// showToggle : true,
			// showColumns : true,
			iconSize : 'outline',
			toolbar : '#exampleToolbar',
			striped : true, // 设置为true会有隔行变色效果
			dataType : "json", // 服务器返回的数据类型
			//pagination : true, // 设置为true会在底部显示分页条
			// queryParamsType : "limit",
			// //设置为limit则会发送符合RESTFull格式的参数
			singleSelect : false, // 设置为true将禁止多选
			// contentType : "application/x-www-form-urlencoded",
			// //发送到服务器的数据编码类型
			//pageSize : 10, // 如果设置了分页，每页数据条数
			//pageNumber : 1, // 如果设置了分布，首页页码
			// search : true, // 是否显示搜索框
			showColumns : false, // 是否显示内容下拉框（选择显示的列）
			sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者
			// "server"
			// showFooter:true,
			// detailView: true, //父子表
			onSort:function(name,order)
			{

				$('#dataGrid').bootstrapTable('refreshOptions', {
					sortName:name,
					sortOrder:order
				});
			},
			queryParams : function(params) {
				return {
					// 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
					// limit : params.limit,
					// offset : params.offset,
					endDate : $('#endDate').val(),
					beginDate : $('#beginDate').val(),
					businessName : $('#businessName').val(),
					status :$('#status').val(),
					sort:params.sort ,
					order:params.order
				};
			},
			// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
			// queryParamsType = 'limit' ,返回参数必须包含
			// limit, offset, search, sort, order 否则, 需要包含:
			// pageSize, pageNumber, searchText, sortName,
			// sortOrder.
			// 返回false将会终止请求
			columns : footerColumns,

		});

}

function reLoad() {
	$('#dataGrid').bootstrapTable('refresh');
	$('#footer').bootstrapTable('refresh');
}

function batchExport() {

    layer.confirm('确定导出数据？', {
        btn: ['确定', '取消']
    }, function (index) {

		var endDate = $('#endDate').val();
		var	beginDate = $('#beginDate').val();
		var	businessName = $('#businessName').val();
		var	status = $('#status').val();

        var params = endDate + "&beginDate=" + beginDate + "&businessName=" + businessName + "&status" + status;

        var url = prefix + "/batchExport?endDate=" + params;
        layer.close(index);
        $.ajax({
            //几个参数需要注意一下
            type: "GET",//方法类型

            url: url,
            success: function (result) {
                if (result.code) {
                    layer.msg(result.message);
                    return;
                }
                window.location.href = url;
            }
        });
    })
}

function productDetail(currThis) {
    var type = $(currThis).attr('type');
    var productId = $(currThis).attr('productId');
    var str = type+","+productId;
    var perContent = layer.open({
        type : 2,
        title : '销售详情',
        shadeClose : false, // 点击遮罩关闭层
        area : [ '650px', '480px' ],
        content : prefix + '/ShowDetail/' + str // iframe的url
    });
}

