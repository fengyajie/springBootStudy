var prefix = "/businessSmall";
$(function() {
	load(); 
});

var gridDataColumns;
function getGridDataColumns() {
	gridDataColumns = [
			{
				field : 'id',
				title : '序号',
				formatter : function(value, row, index) {

					var pageSize=$('#dataGrid').bootstrapTable('getOptions').pageSize;//通过表的#id 可以得到每页多少条
					var pageNumber=$('#dataGrid').bootstrapTable('getOptions').pageNumber;//通过表的#id 可以得到当前第几页
					return pageSize * (pageNumber - 1) + index + 1;//返回每条的序号： 每页条数 * （当前页 - 1 ）+

				},align:'center',

			}, 
			{field : 'productName',title : '产品名称',align:'center',},
			{field : 'productNum',title : '产品数量',align:'center',},
			{field : 'amount',title : '金额',align:'center',},
			{field : 'repTime',title : '采购日期',align:'center',}

			];

}

function load() {
	
	 getGridDataColumns();
	$('#dataGrid').bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : prefix + "/salesDetail", // 服务器数据的加载地址
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
				// search : true, // 是否显示搜索框
				showColumns : false, // 是否显示内容下拉框（选择显示的列）
				sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者
				// "server"
				// showFooter:true,
				// detailView: true, //父子表
				queryParams : function(params) {
					return {
						// 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
						limit : params.limit,
						offset : params.offset,
						str : $('#str').val().trim(),
					// username:$('#searchName').val()
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

}

function reLoad() {
	$('#dataGrid').bootstrapTable('refresh');
}

