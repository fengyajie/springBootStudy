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
    // getorganList();
});

/**
 * 提交前填充机构号到表单
 */
function setOrganSysno() {
    var organName = $("#organName").val();
    var boo = false;
    for (var i = 0; i < arr.length; i++) {
        var obj = arr[i];
        var briefName = obj.BriefName;
        var sysNo = obj.SysNo;
        if (organName == briefName) {
            $("#organSysno").val(sysNo);
            boo = true;
        }
    }
    if (!boo) {
        $("#organSysno").val("");
    }
}

var arr = new Array();//全局分公司数组
//分公司下拉列表
function getorganList() {
    $.ajax({
        url: "getOrganList?storeType=0",
        type: "POST",
        dataType: "json",
        async: false,
        success: function (result) {
            var s = result;
            arr = result.message;
            $("#list").append('<li>全部</li>');
            for (var i = 0; i < result.message.length; i++) {
                var item = result.message[i];
                $("#list").append('<li value="' + item.SysNo + '">' + item.BriefName + '</li>');
            }
        },
    });
}

var sortExcel;
var orderExcel;
var gridDataColumns;
var footerColumns;
var total;

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
            valign: 'middle',
            rowspan: 2
        },
        {
            field: 'install_time',
            title: '安装日期',
            align: 'center',
            colspan: 1,
            rowspan: 2,
            valign: 'middle',
        },
        {
            field: 'address',
            title: '商户地址',
            align: 'center',
            valign: 'middle',
            colspan: 1,
            rowspan: 2
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
            colspan: 1,
            rowspan: 2,
            valign: 'middle',
        },
        {
            field: 'purchase_amount',
            sortable: true,
            title: '采购金额',
            align: 'center',
            colspan: 1,
            rowspan: 2,
            valign: 'middle',
        },
        {
            field: 'purchase_count',
            sortable: true,
            title: '采购次数',
            colspan: 1,
            rowspan: 2,
            valign: 'middle',
        },
        {
            field: 'in_purchase_amount',
            sortable: true,
            title: '内采购金额',
            colspan: 1,
            rowspan: 2,
            valign: 'middle',
        },
        {
            field: 'in_purchase_count',
            sortable: true,
            title: '内采购次数',
            colspan: 1,
            rowspan: 2,
            valign: 'middle',
        },
        {
            field: 'allowance_amount',
            sortable: true,
            title: '内采抵用金',
            colspan: 1,
            rowspan: 2,
            valign: 'middle',
        },


    ];

}

function loadFirstDynamicColumns(checkOldDate) {

    var beginDate = $('#beginDate').val()
    var endDate = $("#endDate").val()
    $.ajax({
        type: "POST",
        url: prefixDynamic + "/loadFirstSmallDayDynamicColumns",
        data: {'beginDate': beginDate, 'endDate': endDate},// 你的formid
        async: false,
        error: function (request) {
            parent.layer.alert("Connection error");
        },
        success: function (data) {
            gridDataColumns = gridDataColumns.concat(data);

        }
    });
}

function loadDynamicColumns(checkOldDate) {
    var beginDate = $('#beginDate').val()
    var endDate = $("#endDate").val()
    $.ajax({
        type: "POST",
        url: prefixDynamic + "/loadSmallDayDynamicColumns",
        data: {'beginDate': beginDate, 'endDate': endDate},// 你的formid
        async: false,
        error: function (request) {
            parent.layer.alert("Connection error");
        },
        success: function (data) {
            total = [gridDataColumns, data]
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
        field: 'address',
        title: '采购次数'
    }, {
        field: 'sales_user_name',
        title: '内采购金额'
    }, {
        field: 'dg_user_name',
        title: '内采购次数'
    }, {
        field: 'purchase_amount',
        title: '内采购抵用金'
    }, {
        field: 'purchase_count',
        title: '采购商品销售'
    }, {
        field: 'in_purchase_amount',
        title: '自有商品销售额'
    }, {
        field: 'in_purchase_count',
        title: '采购商品订单补贴'
    }, {
        field: 'allowance_amount',
        title: '自有商品订单补贴'
    }, {
        field: 'product_amount',
        title: '饭钱'
    }, {
        field: 'product_self_amount',
        title: '总用户数'
    }, {
        field: 'purchase_amountNum',
        title: '用户数'
    }, {
        field: 'purchase_self_amount',
        title: '下单数量'
    }, {
        field: 'amount',
        title: '领取红包人数'
    },

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
    var beginMonth = new Date(begin.replace(/-/g, "/")).Format("yyyy-MM");
    var endMonth = new Date(end.replace(/-/g, "/")).Format("yyyy-MM");
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
    total = [];
    getGridDataColumns();
    loadFirstDynamicColumns()
    loadDynamicColumns();
    getFooterColumns();
    $('#dataGrid').bootstrapTable({
            method: 'get', // 服务器数据的请求方式 get or post
            url: prefix + "/smallStoreDayReport", // 服务器数据的加载地址
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
            }
            ,
            queryParams: function (params) {
                sortExcel = params.sort;
                orderExcel = params.order;
                return {
                    // 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                    limit: params.limit,
                    offset: params.offset,
                    beginDate: $('#beginDate').val(),
                    endDate: $("#endDate").val(),
                    businessName: $("#businessName").val(),
                    organSysno: $("#organList").val(),
                    sellerName: $("#sellerName").val(),
                    status: $("#status").val(),
                    storeType: $("#storeType").val(),
                    sort: params.sort,
                    order: params.order
                    // username:$('#searchName').val()
                };
            }
            ,
            // //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
            // queryParamsType = 'limit' ,返回参数必须包含
            // limit, offset, search, sort, order 否则, 需要包含:
            // pageSize, pageNumber, searchText, sortName,
            // sortOrder.
            // 返回false将会终止请求
            columns: total,

        }
    )
    ;
    $('#footer').bootstrapTable({
        method: 'get', // 服务器数据的请求方式 get or post
        url: prefix + "/smallDayReportTotal", // 服务器数据的加载地址
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
                storeType: $("#storeType").val(),
                organSysno : $("#organList").val(),
                sellerName: $("#sellerName").val(),
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
    //setOrganSysno();
    load();
}

exportDayExcel = function () {
    layer.confirm('确定导出数据？', {
        btn: ['确定', '取消']
    }, function (index) {
        if (!checkDate()) {
            return false;
        }
        if (sortExcel == undefined || sortExcel == 'undefined') {
            sortExcel = ''
        }
        if (orderExcel == undefined || orderExcel == 'undefined') {
            orderExcel = ''
        }
        // setOrganSysno()
        var beginDate = $('#beginDate').val();
        var endDate = $("#endDate").val();
        var businessName = $("#businessName").val();
        var organSysno = $("#organList").val();
        var sellerName = $("#sellerName").val();
        var status = $("#status").val();
        var storeType = $("#storeType").val();
        var params = beginDate + "&endDate=" + endDate + "&businessName=" + businessName + "&organSysno=" + organSysno + "&sellerName=" + sellerName
            + "&status=" + status + "&storeType=" + storeType + "&sort=" + sortExcel + "&order=" + orderExcel;
        /* window.location.href = "cmReport/export?monthDate="+params; */
        window.open("/businessDaySmall/downloadExcel?beginDate=" + params);
        layer.close(index);
    })
}

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
