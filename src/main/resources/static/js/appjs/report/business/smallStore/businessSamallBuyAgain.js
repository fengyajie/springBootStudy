var prefix = "/BusinessSamallBuyAgain";
var prefixDynamic = "/businessSmallDynamicColumns";
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

    var first = "" + yearCurr + "-" + monthCurr1;
    laydate.render({
        type: 'month',
        elem: '#date',
        trigger: 'click',
        value: first,
    });
    load();
    //getorganList();
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
            $("#organ").val(sysNo);
            boo = true;
        }
    }
    if (!boo) {
        $("#organ").val("");
    }
}

var arr = new Array();//全局分公司数组
//分公司下拉列表
function getorganList() {
    $.ajax({
        url: "/businessDaySmall/getOrganList?storeType=0",
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
var total;
var sqlPlan;
var filters;

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
            field: 'businessName',
            title: '商户名称',
            colspan: 1,
            valign: 'middle',
            rowspan: 2
        },
        {
            field: 'cusOrderNum',
            title: '用户数',
            align: 'center',
            colspan: 1,
            rowspan: 2,
            valign: 'middle',
        },
        {
            field: 'orderNum',
            title: '订单数',
            align: 'center',
            valign: 'middle',
            colspan: 1,
            rowspan: 2
        },
        {
            field: 'buAgainCount',
            title: '复购率',
            colspan: 1,
            rowspan: 2,
            valign: 'middle',
        },
    ];

}

function loadFirstDynamicColumns(checkOldDate) {

    var date = $('#date').val()
    var storerName = $("#storerName").val()
    var storeType = $("#storeType").val()
    $.ajax({
        type: "POST",
        url: prefixDynamic + "/loadFirstSmallBuyAgainDynamicColumns",
        data: {'date': date, 'storerName': storerName, 'storeType': storeType},// 你的formid
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

    var date = $('#date').val()
    var storerName = $("#storerName").val()
    var storeType = $("#storeType").val()
    $.ajax({
        type: "POST",
        url: prefixDynamic + "/loadSmallBuyAgainDynamicColumns",
        data: {'date': date, 'storerName': storerName, 'storeType': storeType},// 你的formid
        async: false,
        error: function (request) {
            parent.layer.alert("Connection error");
        },
        success: function (data) {
            total = [gridDataColumns, data['columns']]
            sqlPlan = data['sqlPlan']
            filters = data['filters']
        }
    });

    return true;
}

//回车事件
$(function () {
    document.onkeydown = function (event) {
        var e = event || window.event || arguments.callee.caller.arguments[0];
        if (e && e.keyCode == 13) {
            load();
        }
    };
});

function checkDate() {
    var begin = $('#date').val();
    if ('' == begin) {
        layer.alert('请选择日期');
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
    $('#dataGrid').bootstrapTable({
            method: 'post', // 服务器数据的请求方式 get or post
            url: prefix + "/buyAgainDataGrid2", // 服务器数据的加载地址
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
                    date: $('#date').val(),
                    storeType: $("#storeType").val(),
                    storerName: $("#storerName").val(),
                    sqlPlan: sqlPlan,
                    organ: $("#organList").val(),
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
}

function reLoad() {
    if (!checkDate()) {
        return false;
    }
    $("#dataGrid").bootstrapTable('destroy');
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
            loadDynamicColumns();
            //setOrganSysno();
            var date = $('#date').val();
            var storeType = $("#storeType").val();
            var storerName = $("#storerName").val();
            var organ = $("#organList").val();
            var url = '/BusinessSamallBuyAgain/downloadExcel';
            openPostWindow(url, date, storeType, storerName, organ)
            layer.close(index);
        }
    )
}

function openPostWindow(url, date, storeType, storeName, organ) {
    var tempForm = document.createElement("form");
    tempForm.id = "tempForm1";
    tempForm.method = "post";
    tempForm.action = url;
    tempForm.target = "_blank"; //打开新页面
    var hideInput1 = document.createElement("input");
    hideInput1.type = "hidden";
    hideInput1.name = "date"; //后台要接受这个参数来取值
    hideInput1.value = date; //后台实际取到的值
    var hideInput2 = document.createElement("input");
    hideInput2.type = "hidden";
    hideInput2.name = "storeType";
    hideInput2.value = storeType;
    var hideInput3 = document.createElement("input");
    hideInput3.type = "hidden";
    hideInput3.name = "storeName";
    hideInput3.value = storeName;
    var hideInput4 = document.createElement("input");
    hideInput4.type = "hidden";
    hideInput4.name = "sqlPlan";
    hideInput4.value = sqlPlan;
    var hideInput5 = document.createElement("input");
    hideInput5.type = "hidden";
    hideInput5.name = "filters";
    hideInput5.value = filters;
    var hideInput6 = document.createElement("input");
    hideInput6.type = "hidden";
    hideInput6.name = "organ";
    hideInput6.value = organ;
    var hideInput7 = document.createElement("input");
    hideInput7.type = "hidden";
    hideInput7.name = "orderExcel";
    hideInput7.value = orderExcel;
    var hideInput8 = document.createElement("input");
    hideInput8.type = "hidden";
    hideInput8.name = "sortExcel";
    hideInput8.value = sortExcel;
    tempForm.appendChild(hideInput1);
    tempForm.appendChild(hideInput2);
    tempForm.appendChild(hideInput3);
    tempForm.appendChild(hideInput4);
    tempForm.appendChild(hideInput5);
    tempForm.appendChild(hideInput6);
    tempForm.appendChild(hideInput7);
    tempForm.appendChild(hideInput8);
    if (document.all) {
        tempForm.attachEvent("onsubmit", function () {
        });        //IE
    } else {
        var subObj = tempForm.addEventListener("submit", function () {
        }, false);    //firefox
    }
    document.body.appendChild(tempForm);
    if (document.all) {
        tempForm.fireEvent("onsubmit");
    } else {
        tempForm.dispatchEvent(new Event("submit"));
    }
    tempForm.submit();
    document.body.removeChild(tempForm);
}






