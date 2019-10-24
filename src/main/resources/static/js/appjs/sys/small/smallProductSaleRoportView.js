var prefix = "/view";
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
		type : 'month',
		value:""+yearCurr+"-"+monthCurr1
	});


	 load();
});

var gridDataColumns;
function getGridDataColumns() {
	gridDataColumns = [
			{
				field : '',
				title : '序号',
				formatter : function(value, row, index) {

					var pageSize=$('#dataGrid').bootstrapTable('getOptions').pageSize;//通过表的#id 可以得到每页多少条
					var pageNumber=$('#dataGrid').bootstrapTable('getOptions').pageNumber;//通过表的#id 可以得到当前第几页
					return pageSize * (pageNumber - 1) + index + 1;//返回每条的序号： 每页条数 * （当前页 - 1 ）+

				}

			},
			{field:'businessName',title:'商户名称',align:'center'},
			{field:'payProduct',title:'采购商品',align:'center',sortable:true,
				formatter : function(value, row, index) {
					var html = "<a onclick='productDetail(this)' type='1' businessId='"+row.businessId+"'>"+value+"</a>";
					return html;
				}
			},
			{field:'ownProduct',title:'在售自有商品',align:'center',sortable:true,
				formatter : function(value, row, index) {
					var html = "<a onclick='productDetail1(this)' type='2' businessId='"+row.businessId+"'>"+value+"</a>";
					return html;
				}
			},

			];

}

function load() {
	 getGridDataColumns();
	$('#dataGrid').bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : prefix + "/sellerMonthSaleDataGrid", // 服务器数据的加载地址
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
						storerName : $('#storerName').val(),
						sort:params.sort,
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

}

function reLoad() {
	$('#dataGrid').bootstrapTable('refresh');
}

function batchExport() {

    layer.confirm('确定导出数据？', {
        btn: ['确定', '取消']
    }, function (index) {

		var datea = $('#datea').val();
		var	storerName = $('#storerName').val();

        var params = datea + "&storerName=" + storerName;

        var url = prefix + "/batchExport?datea=" + params;
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
    var businessId = $(currThis).attr('businessId');
	var datea = $('#datea').val();
    var str = type+","+businessId +"," + datea ;
    var perContent = layer.open({
        type : 2,
        title : '销售明细',
        shadeClose : false, // 点击遮罩关闭层
        area : [ '700px', '480px' ],
        content : prefix + '/ShowDetailSmall01/' + str // iframe的url
    });
}

function productDetail1(currThis) {
	var type = $(currThis).attr('type');
	var businessId = $(currThis).attr('businessId');
	var datea = $('#datea').val();
	var str = type+","+businessId +"," + datea;
	var perContent = layer.open({
		type : 2,
		title : '销售明细',
		shadeClose : false, // 点击遮罩关闭层
		area : [ '700px', '480px' ],
		content : prefix + '/ShowDetailSmall02/' + str // iframe的url
	});
}

