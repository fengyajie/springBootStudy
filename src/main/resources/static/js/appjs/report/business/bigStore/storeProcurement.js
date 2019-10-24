var prefix = "/bigStore";
$(function() {
	gettime();
	load();
});
var dataGrid_Detail;
var options_Detail={};
var options={};
var gridDataColumns;
var footerColumns;
function getGridDataColumns() {
	gridDataColumns = [
			{
				field : '商户ID',
				title : '商户ID',
                width:52,
                formatter: function(value,row,index){
                	$("#id").val(row.商户ID)
                	return row.商户ID;
                }
			}, {
				field : '商户名称',
				title : '商户名称',
				align : 'center',
				valign : 'middle'
			}, {
				field : '商户状态',
				title : '商户状态',
				align : 'center',
				valign : 'middle',
				formatter: function(value,row,index){
                    if(row.商户ID=="总计"||row.商户ID=="平均值"){
                        return row.商户状态;
                    }
                    else{
                        if(row.商户状态==0&(row.商户ID!="总计"||row.商户ID!="平均值")){
                            return "有效";
                        }
                        else{
                            return "无效";
                        }
                    }
                }
			}, {
				field : '采购商品数量',
				sortable : true,
				title : '采购商品数量',
				sortable:true,
				 formatter: function(value,row,index){
	                    if(row.商户ID=="总计"||row.商户ID=="平均值"){
	                        return row.采购商品数量;
	                    }
	                    else{
	                        return '<a style="cursor:pointer" onclick="showProductDetail('+row.商户ID+')">'+row.采购商品数量+'</a>';
	                    }
	                }
			}, {
				field : '采购总金额',
				title : '采购总金额',
			}];
}
function getFooterColumns() {
	footerColumns = [ 
	{
		field : '商户ID',
		title : '商户ID',
        width:52
	}, {
		field : '商户名称',
		title : '商户名称',
		align : 'center',
		valign : 'middle'
	}, {
		field : '商户状态',
		title : '商户状态',
		align : 'center',
		valign : 'middle',
		formatter: function(value,row,index){
            if(row.商户ID=="总计"||row.商户ID=="平均值"){
                return row.商户状态;
            }
            else{
                if(row.商户状态==0&(row.商户ID!="总计"||row.商户ID!="平均值")){
                    return "有效";
                }
                else{
                    return "无效";
                }
            }
        }
	}, {
		field : '采购商品数量',
		sortable : true,
		title : '采购商品数量',
		sortable:true,
		 formatter: function(value,row,index){
                if(row.商户ID=="总计"||row.商户ID=="平均值"){
                    return row.采购商品数量;
                }
                else{
                    return '<a style="cursor:pointer" onclick="showProductDetail('+row.商户ID+')">'+row.采购商品数量+'</a>';
                }
            }
	}, {
		field : '采购总金额',
		title : '采购总金额',
	}

	];
}

function showProductDetail(id){
    var perContent = layer.open({
        type : 2,
        title : '商户采购详情',
        shadeClose : false, // 点击遮罩关闭层
        area : [ '700px', '500px' ],
        content : '/view/productDetailView?years='+$("#syears").val()+"&months="+$("#smonths").val()+"&storerName="+$("#storerName").val()+"&id="+$("#id").val() // iframe的url
    });
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

function loadDynamicColumns() {
	$.ajax({
  	    url : "/column/loadProductDetailDynamicColumns",
  	    data:{'params' : JSON.stringify(serializeObject($('#form')))},
  	    type : "Post",
  	    dataType : "json",
  	  	async: false,
  	    success:function(result) {
  	    	 var arr = new Array();
  	    	 var columns=new Array();
  	    	 
  	    	for(var i=0;i<parseInt(result);i++){
  	    		var o={};
  	    		o={rowspan:'2',field:'purchase_'+(i+1),title:'第'+(i+1)+'次采购数量',width:150,align:'center'};
  	    		columns.push(o);
  	    	}
  	    	arr.push(columns);
  	    	options_Detail.columns = arr;
  	    },
  	});
    return true;
}

//获取时间选择
function gettime(){
    var myDate = new Date();
    //获取当前年
    var year=myDate.getFullYear();
    //获取当前月
    var month=myDate.getMonth()+1;
    for (var i = 2016; i <= year; i++) {
        if (i==year) {
            $("#syears").append("<option selected='true' value='"+i+"'>"+i+"</option>");
        }else{
            $("#syears").append("<option value='"+i+"'>"+i+"</option>");
        }
    }
    for (var i = 1; i <= 12; i++) {
        if (i==month) {
            $("#smonths").append("<option selected='true' value='"+i+"'>"+i+"</option>");
        }else{
            $("#smonths").append("<option value='"+i+"'>"+i+"</option>");
        }
    }
}
function load() {
	getGridDataColumns();
	getFooterColumns();
	$('#dataGrid').bootstrapTable({
		method : 'get', // 服务器数据的请求方式 get or post
		url : prefix + "/bigStoreProcurenmentDataGrid", // 服务器数据的加载地址
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
				years : $('#syears').val(),
				months:$("#smonths").val(),
				storerName:$("#storerName").val()
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
	
	$('#footer').bootstrapTable({
		method : 'get', // 服务器数据的请求方式 get or post
		url : prefix + "/bigStoreProcurenmentTotal", // 服务器数据的加载地址
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
				years : $('#syears').val(),
				months:$("#smonths").val(),
				storerName:$("#storerName").val()
			};
		},
		// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
		columns : footerColumns,
        fixedColumns: true,
        fixedNumber: 1,
        searchAccentNeutralise:true,
        resizable:true,
        classes:"table table-bordered table-hover table-sm"
	});
}

function reLoad() {
	$('#dataGrid').bootstrapTable('refresh');
	$('#footer').bootstrapTable('refresh');
}

exportExcel = function() {
	layer.confirm('确定导出数据？', {
		btn : [ '确定', '取消' ]
	}, function(index) {
		var years = $('#syears').val();
		var months = $('#smonths').val();
		var storerName = $('#storerName').val();
		var id = $("#id").val();
		window.open("/export/productEx?years="+years+"&months="+months+"&storerName="+storerName+"&id="+id);
		layer.close(index);
	})
};
