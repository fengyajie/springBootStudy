
var prefix = "/zgProduct"

$(function () {
    load();
    laydate.render({
        elem : '#saleDate',
        type : 'month'
    });
});

var footerColumns;
var sortExcel;
var orderExcel;

function getFooterColumns(){
    footerColumns=	[ {
        field : '',
        title : '',
        formatter : function(value, row, index) {
            switch (index) {
                case 0:
                    return '总统计';
                case 1:
                    return '平均值';
            }
        }
    },
        {field : 'pSaleQuantity',title : '总销量'},
        {field : 'mpSaleQuantity',title : '月销售量'},
        {field : 'productQuantity',title : '当日销售量'},

    ];
}

function load() {
    getFooterColumns();
    $('#exampleTable')
        .bootstrapTable(
            {
                method: 'get', // 服务器数据的请求方式 get or post
                url: prefix + "/list", // 服务器数据的加载地址
                //	showRefresh : true,
                //	showToggle : true,
                //	showColumns : true,
                iconSize: 'outline',
                toolbar: '#exampleToolbar',
                striped: true, // 设置为true会有隔行变色效果
                dataType: "json", // 服务器返回的数据类型
                pagination: true, // 设置为true会在底部显示分页条
                // queryParamsType : "limit",
                // //设置为limit则会发送符合RESTFull格式的参数
                singleSelect: false, // 设置为true将禁止多选
                // contentType : "application/x-www-form-urlencoded",
                // //发送到服务器的数据编码类型
                pageSize: 10, // 如果设置了分页，每页数据条数
                pageNumber: 1, // 如果设置了分布，首页页码
                //search : true, // 是否显示搜索框
                showColumns: false, // 是否显示内容下拉框（选择显示的列）
                sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
                onSort:function(name,order)
                {

                    $('#exampleTable').bootstrapTable('refreshOptions', {
                        sortName:name,
                        sortOrder:order
                    });
                },
                queryParams: function (params) {
                    sortExcel=params.sort;
                    orderExcel=params.order;
                    return {
                        //说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                        limit: params.limit,
                        offset: params.offset,
                        shopName: $('#shopName').val(),
                        saleDate: $("#saleDate").val(),
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
                columns: [
                    {
                        checkbox: true
                    },
                    {
                        field: 'productName',
                        title: '商户名称'
                    },
                    {
                        field: 'productState',
                        title: '状态',
                    }
                    ,
                    {
                        field: 'productSalesQuantity',
                        sortable : true,
                        title: '总销售量'
                    },
                    {
                        field: 'productSalesMonthQuantity',
                        sortable : true,
                        title: '月销售量'
                    },
                    {
                        field: 'productSalesDayQuantity',
                        title: '当天销售量'
                    },
                    {
                        field: 'productRefundQuantity',
                        sortable : true,
                        title: '退换货量'
                    },
                    {
                        field: 'orderFailureQuantity',
                        sortable : true,
                        title: '支付失败单数'
                    }]
            });

    $('#footer').bootstrapTable({
        method : 'get', // 服务器数据的请求方式 get or post
        url : prefix + "/shopSaleFooter", // 服务器数据的加载地址
        // showRefresh : true,
        // showToggle : true,
        // showColumns : true,
        iconSize : 'outline',
        toolbar : '#exampleToolbar',
        striped : true, // 设置为true会有隔行变色效果
        dataType : "json", // 服务器返回的数据类型
        // pagination: true, // 设置为true会在底部显示分页条
        // queryParamsType : "limit",
        // //设置为limit则会发送符合RESTFull格式的参数
        singleSelect : false, // 设置为true将禁止多选
        // contentType : "application/x-www-form-urlencoded",
        // //发送到服务器的数据编码类型
        // pageSize: 10, // 如果设置了分页，每页数据条数
        // pageNumber: 1, // 如果设置了分布，首页页码
        // search : true, // 是否显示搜索框
        showColumns : false, // 是否显示内容下拉框（选择显示的列）
        sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者
        // "server"
        // showFooter:true,
        // detailView: true, //父子表
        queryParams : function(params) {
            return {
                // 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                // limit: params.limit,
                // offset: params.offset,
                // searchName:$('#searchName').val().trim(),
                // type:$('#type').val().trim(),
                saleDate : $('#saleDate').val().trim(),
                shopName : $('#shopName').val().trim()
            };
        },
        // //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
        // queryParamsType = 'limit' ,返回参数必须包含
        // limit, offset, search, sort, order 否则, 需要包含:
        // pageSize, pageNumber, searchText, sortName,
        // sortOrder.
        // 返回false将会终止请求
        columns :footerColumns,

    });

}


function reLoad() {
    $('#exampleTable').bootstrapTable('refresh');
    $('#footer').bootstrapTable('refresh');
}

function batchExport() {

    layer.confirm('确定导出数据？', {
        btn: ['确定', '取消']
    }, function (index) {
        var shopName = $('#shopName').val().trim();
        var saleDate = $('#saleDate').val().trim();

        var obj = {};
        obj.shopName = $('#shopName').val().trim();
        obj.saleDate = $('#saleDate').val().trim();

        var params = 'shopName=' + shopName + '&saleDate=' + saleDate;
        var url = prefix + '/batchExport?' + params; //?params=' + JSON.stringify(obj);
        layer.close(index);
        $.ajax({
            //几个参数需要注意一下
            type: "get",//方法类型
            url: url + '&flag=1',
            success: function (result) {
                if (result.status == 'OK') {
                    window.location.href = url;
                } else {
                    layer.msg(result.message);
                    return;
                }
            }
        });
    })
}

