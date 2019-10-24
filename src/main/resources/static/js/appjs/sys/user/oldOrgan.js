var prefix = "/sys/user";
$(function() {
	load();
});

function load() {
	$('#exampleTable')
			.bootstrapTable(
                {
                    method : 'get', // 服务器数据的请求方式 get or post
                    url : prefix + "/getOldOrganList", // 服务器数据的加载地址
                    iconSize : 'outline',
                    toolbar : '#exampleToolbar',
                    striped : true, // 设置为true会有隔行变色效果
                    dataType : "json", // 服务器返回的数据类型
                    pagination : true, // 设置为true会在底部显示分页条
                    singleSelect : false, // 设置为true将禁止多选
                    pageSize : 10, // 如果设置了分页，每页数据条数
                    pageNumber : 1, // 如果设置了分布，首页页码
                    showColumns : false, // 是否显示内容下拉框（选择显示的列）
                    sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
                    queryParams : function(params) {
                        return {
                            //说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                            limit : params.limit,
                            offset : params.offset,
                            boName : $('#boName').val().trim(),
                        };
                    },
                    // //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
                    // queryParamsType = 'limit' ,返回参数必须包含
                    // limit, offset, search, sort, order 否则, 需要包含:
                    // pageSize, pageNumber, searchText, sortName,
                    // sortOrder.
                    // 返回false将会终止请求
                    singleSelect :true,
                    columns : [
                        {
                            checkbox : true
                        },
                        {
                            field : 'SysNo',
                            title : '序号'
                        },
                        {
                            field : 'BOID',
                            title : '机构编号',
                        },
                        {
                            field : 'BOName',
                            title : '机构全称',
                        },
                        {
                            field : 'BriefName',
                            title : '机构简称',
                        }]
                });
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}

//添加数据到父页面
function addSelectedToFatherView() {
    var rows = $('#exampleTable').bootstrapTable('getSelections');
    if(rows.length == 0) {
        layer.msg('请选择数据');
        return;
    }
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    parent.sendOrganDataToFatherView(rows[0]);
    parent.layer.close(index);
}
