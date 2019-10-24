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
		elem : '#datea',
		trigger: 'click',
		type : 'month',
		value:""+yearCurr+"-"+monthCurr1+""
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

				},
				width:111,align:'center'

			}, 
			{field : 'businessName',title : '商户名称',align:'center'},
			{field : 'installTime',title : '安装日期',align:'center'},
			{field : 'sellerName',title : '销售人员',width:111,align:'center'},
			{field : 'dgName',title : '督导人员',width:111,align:'center'},
			{field : 'address',title : '商户地址',align:'center'},

			{field : 'amount',title : '采购金额',width:111,align:'center',sortable:true},
			{
				field : 'orderNum',
				title : '采购次数',
				formatter : function(value, row, index) {
					var html = "<a onclick='productDetail(this)'    type='1' businessId=" + "'" +row.businessId+"'>"+value+"</a>";
					return html;
				},
				width:111,align:'center',sortable:true
			},
			{field : 'amountSelf',title : '内采购金额',width:111,align:'center',sortable:true},
			{field : 'allowanceAmount',title : '内采抵用金',width:111,align:'center',sortable:true},
			{
				field : 'orderNumSelf',
				title : '内采购次数',
				formatter : function(value, row, index) {
					var html = "<a onclick='productDetail(this)'    type='2' businessId=" + "'" +row.businessId+"'>"+value+"</a>";
					return html;
				},
				width:111,align:'center',sortable:true
			},
			{field : 'leaseAmount',title : '缴纳货架租金',width:111,high:50,align:'center',sortable:true},

			{field:'productAmount',title:'采购商品销售额',width:111,align:'center',sortable:true},
			{field:'productSelfAmount',title:'自有商品销售额',width:111,align:'center',sortable:true},
			{field:'purchaseAmount',title:'采购商品订单补贴',width:111,align:'center',sortable:true},
			{field:'purchaseSelfAmount',title:'自有商品订单补贴',width:111,align:'center',sortable:true},
			{field:'mealAmount',title:'饭钱',width:111,align:'center',sortable:true},
			{field:'cusNum',title:'总用户数',width:111,align:'center',sortable:true},
			{field:'cusOrderNum',title:'用户数',width:111,align:'center',sortable:true},
			{field:'receiveEnvNum',title:'领取红包人数',width:111,align:'center',sortable:true},
			{field:'ra',title:'下单率',width:111,align:'center',sortable:true}

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
		{field : 'amount',title : '采购金额',width:111,align:'center'},
		{field : 'orderNum',title : '采购次数',width:111,align:'center'},
		{field : 'amountSelf',title : '内采购金额',width:111,align:'center'},
		{field : 'allowanceAmount',title : '内采抵用金',width:111,align:'center'},
		{field : 'orderNumSelf',title : '内采购次数',width:111,align:'center'},
		{field : 'leaseAmount',title : '缴纳货架租金',width:111,high:50,align:'center'},
		{field:'productAmount',title:'采购商品销售额',width:111,align:'center'},
		{field:'productSelfAmount',title:'自有商品销售额',width:111,align:'center'},
		{field:'purchaseAmount',title:'采购商品订单补贴',width:111,align:'center'},
		{field:'purchaseSelfAmount',title:'自有商品订单补贴',width:111,align:'center'},
		{field:'mealAmount',title:'饭钱',width:111,align:'center'},
		{field:'cusNum',title:'总用户数',width:111,align:'center'},
		{field:'cusOrderNum',title:'用户数',width:111,align:'center'},
		{field:'receiveEnvNum',title:'领取红包人数',width:111,align:'center'},
		{field:'ra',title:'下单率',width:111,align:'center'}

	];
}

function load() {
	 getGridDataColumns();
	 getFooterColumns();
	$('#dataGrid').bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : prefix + "/smallReportViewDataGrid", // 服务器数据的加载地址
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
						datea : $('#datea').val(),
						businessName : $('#businessName').val(),
						dgName :$('#dgName').val(),
						sellerName :$('#sellerName').val(),
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
			url : prefix + "/queryBusinessSmallFooter", // 服务器数据的加载地址
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
			queryParams : function(params) {
				return {
					// 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
					// limit : params.limit,
					// offset : params.offset,
					datea : $('#datea').val(),
					businessName : $('#businessName').val(),
					dgName :$('#dgName').val(),
					sellerName :$('#sellerName').val(),
					status :$('#status').val(),
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
}

function batchExport() {

    layer.confirm('确定导出数据？', {
        btn: ['确定', '取消']
    }, function (index) {

		var datea = $('#datea').val();
		var	businessName = $('#businessName').val();
		var	dgName = $('#dgName').val();
		var	sellerName =$('#sellerName').val();
		var	status = $('#status').val();

        var params = datea + "&dgName=" + dgName + "&businessName=" + businessName + "&status" + status + "&sellerName" + sellerName;

        var url = prefix + "/batchSmallExport?datea=" + params;
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
    var type = $(currThis).attr('type');
    var businessId = $(currThis).attr('businessId');
	var datea = $('#datea').val()+"-01";
    var str = type+","+businessId + "," + datea;
    var perContent = layer.open({
        type : 2,
        title : '商户采购详情',
        shadeClose : false, // 点击遮罩关闭层
        area : [ '700px', '500px' ],
        content : prefix + '/ShowSmallDetail/' + str // iframe的url
    });
}

