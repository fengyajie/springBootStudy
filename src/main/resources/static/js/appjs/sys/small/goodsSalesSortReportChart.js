var prefix = "/businessSmallSale";
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
			{field : 'productName',title : '商品名称',align:'center',},
			{
				field : 'productType',title : '商品类型',
				formatter : function(value, row, index) {
					if (value == 1) {
						return '加价购现兑产品';
					}if (value == 2) {
						return '加价购采购产品';
					}if (value == 3) {
						return '机构采购产品';
					}if (value == 4) {
						return '商户采购产品';
					}if (value == 5) {
						return '现兑商品';
					}if (value == 6) {
						return '转换直购';
					}if (value == 7) {
						return '机构采购加价购产品';
					}if (value == 8) {
						return '机构小商户采购产品';
					}if (value == 9) {
						return '小商户内采产品';
					}if (value == 10) {
						return '小商户自有产品';
					}
				},align:'center',

			},
			{field : 'onSaleTime',title : '上架时间',align:'center',},
			{field : 'offSaleTime',title : '下架时间',align:'center',},
			{
				field : 'productNum',
				title : '月销量',
                formatter : function(value, row, index) {
                    var html = "<a onclick='productDetail(this)'    type='1' productId=" + "'" +row.productId+"'>"+value+"</a>";
                    return html;
                },align:'center',sortable:true
			}, {
				field : 'allProductNum',
				title : '总销量',
                formatter : function(value, row, index) {
                    var html = "<a onclick='productDetail(this)'    type='2' productId=" + "'" +row.productId+"'>"+value+"</a>";
                    return html;
                },align:'center',sortable:true
			} ];

}

function load() {
	
	 getGridDataColumns();
	$('#dataGrid').bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : prefix + "/goodsSalesSortReport", // 服务器数据的加载地址
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
						productName : $('#productName').val().trim(),
						productType : $('#productType').val().trim(),
						organSysno : $('#organList').val(),
						sort:params.sort ,
						order:params.order
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

function batchExport() {

    layer.confirm('确定导出数据？', {
        btn: ['确定', '取消']
    }, function (index) {


        var productName = $('#productName').val().trim();
        var productType = $('#productType').val().trim();
        var organSysno = $('#organList').val().trim();


        var params = productName + "&productType=" + productType + "&organSysno=" + organSysno;

        var url = prefix + "/batchExport?productName=" + params;
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
    /*layer.full(perContent);*/
}


