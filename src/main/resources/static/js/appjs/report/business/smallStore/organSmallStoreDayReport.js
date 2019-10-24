var prefix = "/businessDaySmall";
var prefixDynamic = "/businessSmallDynamicColumns";
$(function () {
    var data = new Date();
    var monthCurr = data.getMonth() + 1;
    var dayCurr = data.getDate();
    var yearCurr = data.getFullYear();
    var monLength = monthCurr.toString().length;
    var dayLength = dayCurr.toString().length;
    if (monLength == 1) {
        var monthCurr1 = "0" + monthCurr
    } else {
        var monthCurr1 = monthCurr;
    }
    if (dayLength == 1) {
        var dayCurr1 = "0" + dayCurr
    } else {
        var dayCurr1 = dayCurr;
    }

    var first = "" + yearCurr + "-" + monthCurr1 + "-01";
    var now = "" + yearCurr + "-" + monthCurr1 + "-" + dayCurr1;
    //getorganList();
    laydate.render({
        elem: '#beginDate',
        trigger: 'click',
        value: first,
        format: 'yyyy-MM-dd',
    });

    laydate.render({
        elem: '#endDate',
        trigger: 'click',
        value: now,
        format: 'yyyy-MM-dd',
    });

    load();
});


var sortExcel;
var orderExcel;
var total;
var gridDataColumns;
var footerColumns;

function getGridDataColumns() {
    gridDataColumns = [
        {
            field: '',
            title: '序号',
            formatter: function (value, row, index) {
                var pageSize = $('#dataGrid').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
                var pageNumber = $('#dataGrid').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
                return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
            },
            valign: 'middle',
            colspan: 1,
            rowspan: 2
        },
        {
            field: 'business_name',
            title: '商户名称',
            colspan: 1,
            rowspan: 2,
            valign: 'middle',
        },
        {
            field: 'install_time',
            title: '安装日期',
            align: 'center',
            valign: 'middle',
            colspan: 1,
            rowspan: 2
        },
        {
            field: 'first_purchase_time',
            title: '首单采购日期',
            align: 'center',
            valign: 'middle',
            colspan: 1,
            rowspan: 2
        },
        {
            field: 'address',
            title: '商户地址',
            colspan: 1,
            rowspan: 2,
            valign: 'middle',
        },
        {
            field: 'sales_user_name',
            title: '销售人员',
            colspan: 1,
            rowspan: 2,
            valign: 'middle',
        },
        {
            field: 'dg_user_name',
            title: '督导人员',
            align: 'center',
            valign: 'middle',
            colspan: 1,
            rowspan: 2
        }
    ];

}

function loadFirstDynamicColumns(checkOldDate) {

    var beginDate = $('#beginDate').val()
    var endDate = $("#endDate").val()
    $.ajax({
        type: "POST",
        url: prefixDynamic + "/loadFirstOrganSmallDayDynamicColumns",
        data: {'beginDate': beginDate, 'endDate': endDate},// 你的formid
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

function loadDynamicColumns(checkOldDate) {
    var beginDate = $('#beginDate').val()
    var endDate = $("#endDate").val()
    $.ajax({
        type: "POST",
        url: prefixDynamic + "/loadOrganSmallDayDynamicColumns",
        data: {'beginDate': beginDate, 'endDate': endDate},// 你的formid
        async: false,
        error: function (request) {
            parent.layer.alert("Connection error");
        },
        success: function (data) {
            //gridDataColumns = gridDataColumns.concat(data);
            total=[gridDataColumns,data]
        }
    });

    return true;
}

function getFooterColumns() {
    footerColumns = [{
        field: '',
        title: '',
        formatter: function (value, row, index) {
            switch (index) {
                case 0:
                    return '总统计';
            }
        }
    }, {
        field: 'install_time',
        title: '采购金额'
    }, {
        field: 'first_purchase_time',
        title: '采购次数'
    }, {
        field: 'address',
        title: '内采购金额'
    }, {
        field: 'sales_user_name',
        title: '内采购次数'
    }, {
        field: 'dg_user_name',
        title: '内采抵用金'
    }
    ];
}

function checkDate() {
    var begin = $('#beginDate').val();
    var end = $('#endDate').val();
    var beginDate = new Date(begin).getTime();
    var endDate = new Date(end).getTime();
    if ('' == begin || '' == end) {
        layer.alert('请选择日期');
        return false;
    }
    if (beginDate > endDate) {
        layer.alert('开始时间不能大于结束时间');
        return false;
    }
    var beginMonth = new Date(begin.replace(/-/g,"/")).Format("yyyy-MM");
    var endMonth = new Date(end.replace(/-/g,"/")).Format("yyyy-MM");
    if (beginMonth != endMonth) {
        layer.alert('请选择同年同月日期!');
        return false;
    }
    return true;
}

function load() {
    if (!checkDate()) {
        return false;
    }

    total=[];
    getGridDataColumns();
    getFooterColumns();
    loadFirstDynamicColumns()
    loadDynamicColumns();
    $('#dataGrid').bootstrapTable({
        method: 'get', // 服务器数据的请求方式 get or post
        url: prefix + "/organSmallStoreDayReport", // 服务器数据的加载地址
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
        queryParams: function (params) {
            sortExcel=params.sort;
            orderExcel=params.order;
            return {
                // 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                limit: params.limit,
                offset: params.offset,
                beginDate: $('#beginDate').val(),
                endDate: $("#endDate").val(),
                businessName: $("#businessName").val(),
                sellerName: $("#sellerName").val(),
                status: $("#status").val(),
                storeType: $("#storeType").val(),
                sort: params.sort,
                order: params.order
                // username:$('#searchName').val()
            };
        },
        // //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
        // queryParamsType = 'limit' ,返回参数必须包含
        // limit, offset, search, sort, order 否则, 需要包含:
        // pageSize, pageNumber, searchText, sortName,
        // sortOrder.
        // 返回false将会终止请求
        columns: total,

    });
    $('#footer').bootstrapTable({
        method: 'get', // 服务器数据的请求方式 get or post
        url: prefix + "/organSmallDayReportTotal", // 服务器数据的加载地址
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
        queryParams: function (params) {
            return {
                // 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                limit: params.limit,
                offset: params.offset,
                beginDate: $('#beginDate').val(),
                endDate: $("#endDate").val(),
                businessName: $("#businessName").val(),
                sellerName: $("#sellerName").val(),
                storeType: $("#storeType").val(),
                status: $("#status").val(),
                sort: params.sort,
                order: params.order
            };
        },
        columns: footerColumns,
    });

}

function reLoad() {
    if (!checkDate()) {
        return false;
    }
    $("#dataGrid").bootstrapTable('destroy');
    $('#footer').bootstrapTable('destroy');
    load();
}

exportDayExcel = function () {
    layer.confirm('确定导出数据？', {
        btn: ['确定', '取消']
    }, function (index) {
        if (!checkDate()) {
            return false;
        }
        if(sortExcel==undefined||sortExcel=='undefined'){
            sortExcel=''
        }
        if(orderExcel==undefined||orderExcel=='undefined'){
            orderExcel=''
        }
        var beginDate = $('#beginDate').val();
        var endDate = $("#endDate").val();
        var businessName = $("#businessName").val();
        var sellerName = $("#sellerName").val();
        var status = $("#status").val();
        var storeType = $("#storeType").val();
        var params = beginDate + "&endDate=" + endDate + "&businessName=" + businessName +  "&sellerName=" + sellerName
            + "&status=" + status+ "&storeType=" + storeType+ "&sort=" + sortExcel+ "&order=" + orderExcel;
        /* window.location.href = "cmReport/export?monthDate="+params; */
        window.open("/businessDaySmall/organDownloadExcel?beginDate=" + params);
        layer.close(index);
    })
};
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
