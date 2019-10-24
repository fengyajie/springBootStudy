var prefix = "/businessBig";
var prefixDynamic = "/businessBigDynamicColumns";

var request ={
    general: {rows : "bigMonthReportDataGrid",footer:"bigMonthReportTotal", ex_column:[],export:'/export/bigMonthEx'},
    special: {rows : "bigMonthTeshuReportDataGrid",footer:"bigMonthTeshuReportTotal",ex_column:[],export:'/export/bigMonthTeshuEx'}
}

$(function () {
    var data = new Date();
    var monthCurr = data.getMonth() + 1;
    var yearCurr = data.getFullYear();
    var monLength = monthCurr.toString().length;
    if (monLength == 1) {
        var monthCurr1 = "0" + monthCurr
    } else {
        var monthCurr1 = monthCurr;
    }

    var monthStr = "" + yearCurr + "-" + monthCurr1;
    $("#monthDate").val(monthStr);
    laydate.render({
        elem: '#monthDate',
        type: 'month',
        value: monthStr
    });

    load();
});

function getQueryString(name){
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return unescape(r[2]); return null;
}

var gridDataColumns;
var footerColumns;

function getGridDataColumns() {
    gridDataColumns = [
        {
            field: 'id',
            title: '序号',
            width: 45,
            formatter: function (value, row, index) {
                // 获取每页显示的数量
                var pageSize = $('#dataGrid').bootstrapTable(
                    'getOptions').pageSize;
                // 获取当前是第几页
                var pageNumber = $('#dataGrid').bootstrapTable(
                    'getOptions').pageNumber;
                // 返回序号，注意index是从0开始的，所以要加上1
                return pageSize * (pageNumber - 1) + index + 1;
            }

        }, {
            field: 'business_id',
            title: '商户ID',
            width: 52
        }, {
            field: 'businessName',
            title: '商户名称',
            align: 'center',
            valign: 'middle'
        }, {
            field: 'installDate',
            title: '安装日期',
            align: 'center',
            valign: 'middle',
        }, {
            field: 'firstDate',
            sortable: true,
            title: '首单日期',
        }, {
            field: 'sallerName',
            title: '销售人员',
        }, {
            field: 'address',
            title: '商户地址',
            align: 'center',
            valign: 'middle'
        }, {
            field: 'firstRechargeMoneyCount',
            title: '首充金额',
        }, {
            field: 'state',
            title: '商户状态',
        }, {
            field: 'categoryName',
            title: '商户热卖类目',
        }, {
            field: 'accountBalance',
            sortable: true,
            title: '充值余额',
        }];
    gridDataColumns.concat(request[dataType].ex_column);
}

function getFooterColumns() {
    footerColumns = [{
        field: '',
        title: '',
        formatter: function (value, row, index) {
            switch (index) {
                case 0:
                    return '总统计';
                case 1:
                    return '平均值';
            }
        }
    }, {
        field: 'rechargeMoney',
        title: '充值的金额'
    }, {
        field: 'aliRechargeMoney',
        title: '充值(支付宝)'
    }, {
        field: 'aliRechargeMoney',
        title: '充值(聚合)'
    }, {
        field: 'accountBalanceCount',
        title: '充值的余额'
    }, {
        field: 'companyProfitCount',
        title: '公司净收金额'
    }, {
        field: 'customerPayMoneyCount',
        title: '用户支付金额'
    }, {
        field: 'allCustomerPayMoneyCount',
        title: '用户支付(聚合)'
    }, {
        field: 'aliAllCustomerPayMoneyCount',
        title: '用户支付(支付宝-聚合)'
    }, {
        field: 'aliAliCustomerPayMoneyCount',
        title: '用户支付(支付宝-支付宝)'
    }, {
        field: 'customerPayTimesCount',
        title: '用户支付笔数'
    }, {
        field: 'fansCount',
        title: '商户粉丝数'
    }, {
        field: 'salesMoneyCount',
        title: '销售额'
    }, {
        field: 'sceneOrderCount',
        title: '现兑订单数'
    },

    ];
}

function loadDynamicColumns(checkOldDate) {

    $.ajax({
        type: "POST",
        url: prefixDynamic + "/loadBigMonthDynamicColumns",
        data: {},// 你的formid
        async: false,
        error: function (request) {
            parent.layer.alert("Connection error");
        },
        success: function (data) {
            gridDataColumns = gridDataColumns.concat(data);
        }
    });
    return true;
}

function load() {
    getGridDataColumns();
    getFooterColumns();
    loadDynamicColumns();
    $('#dataGrid').bootstrapTable({
        method: 'get', // 服务器数据的请求方式 get or post
        url: prefix + "/"+ request[dataType].rows , // 服务器数据的加载地址
        // showRefresh : true,
        // showToggle : true,
        // showColumns : true,
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
        // search : true, // 是否显示搜索框
        showColumns: false, // 是否显示内容下拉框（选择显示的列）
        sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者
        onSort: function (name, order) {

            $('#dataGrid').bootstrapTable('refreshOptions', {
                sortName: name,
                sortOrder: order
            });
        },
        // "server"
        // showFooter:true,
        // detailView: true, //父子表
        queryParams: function (params) {
            return {
                // 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                limit: params.limit,
                offset: params.offset,
                sort: params.sort,
                order: params.order,

                monthDate: $('#monthDate').val().trim(),
                sellerName: $("#sellerName").val(),
                storerName: $("#storerName").val(),
                state: $("#state").val(),
                isNew: $("#isNew").val(),
                housing: $("#housing").val(),

                // username:$('#searchName').val()
            };
        },
        // //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
        // queryParamsType = 'limit' ,返回参数必须包含
        // limit, offset, search, sort, order 否则, 需要包含:
        // pageSize, pageNumber, searchText, sortName,
        // sortOrder.
        // 返回false将会终止请求
        columns: gridDataColumns,
        fixedColumns: true,
        fixedNumber: 11 + request[dataType].ex_column.length ,
        searchAccentNeutralise: true,
        resizable: true,
        classes: "table table-bordered table-hover table-sm"
    });
    $('#footer').bootstrapTable({
        method: 'get', // 服务器数据的请求方式 get or post
        url: prefix + "/"+ request[dataType].footer, // 服务器数据的加载地址
        // showRefresh : true,
        // showToggle : true,
        // showColumns : true,
        iconSize: 'outline',
        toolbar: '#exampleToolbar',
        striped: true, // 设置为true会有隔行变色效果
        dataType: "json", // 服务器返回的数据类型
        // pagination: true, // 设置为true会在底部显示分页条
        // queryParamsType : "limit",
        // //设置为limit则会发送符合RESTFull格式的参数
        singleSelect: false, // 设置为true将禁止多选
        // contentType : "application/x-www-form-urlencoded",
        // //发送到服务器的数据编码类型
        // pageSize: 10, // 如果设置了分页，每页数据条数
        // pageNumber: 1, // 如果设置了分布，首页页码
        // search : true, // 是否显示搜索框
        showColumns: false, // 是否显示内容下拉框（选择显示的列）
        sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者
        // "server"
        // showFooter:true,
        // detailView: true, //父子表
        queryParams: function (params) {
            return {
                // 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                limit: params.limit,
                offset: params.offset,
                sort: params.sort,
                order: params.order,

                monthDate: $('#monthDate').val().trim(),
                sellerName: $("#sellerName").val(),
                storerName: $("#storerName").val(),
                state: $("#state").val(),
                isNew: $("#isNew").val(),
                housing: $("#housing").val()
            };
        },
        // //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
        // queryParamsType = 'limit' ,返回参数必须包含
        // limit, offset, search, sort, order 否则, 需要包含:
        // pageSize, pageNumber, searchText, sortName,
        // sortOrder.
        // 返回false将会终止请求
        columns: footerColumns,
        classes: "table table-bordered table-hover table-sm"
    });

}

function reLoad() {
    $('#dataGrid').bootstrapTable('refresh');
    $('#footer').bootstrapTable('refresh');
}

exportExcel = function () {
     layer.confirm('确定导出数据？', {
        btn: ['确定', '取消']
    }, function (index) {
        $('#params').val(JSON.stringify(getParams()));
        $('#export').attr('action', request[dataType]["export"] ).submit();
        setTimeout(function(){layer.close(index);},1000)
    })
};

function getParams(){
    return {
        monthDate: $('#monthDate').val().trim(),
        sellerName: $("#sellerName").val(),
        storerName: $("#storerName").val(),
        state: $("#state").val(),
        isNew: $("#isNew").val(),
        housing: $("#housing").val()
    }
}
