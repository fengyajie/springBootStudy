var prefix = "/fundsManagement";
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
        type: 'datetime',
        elem: '#beginDate',
        trigger: 'click',
    });

    laydate.render({
        type: 'datetime',
        elem: '#endDate',
        trigger: 'click',
    });
    load();
});


var gridDataColumns;

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
        },
        {
            field: 'userId',
            title: '用户ID',
        },
        {
            field: 'cellPhone',
            title: '用户手机号',
        },
        {
            field: 'incomeHousingCoin',
            title: '累计物业币',
        },
        {
            field: 'housingCoin',
            title: '剩余物业币',
        },
        {
            field: 'payHousingCoin',
            title: '累计充缴物业费',
        },
        {
            field: 'businessName',
            title: '绑定物业',
        },
        {
            field: 'registeDateDesc',
            title: '注册时间',
        },

    ];

}


function checkDate() {
    var begin = $('#beginDate').val();
    var end = $('#endDate').val();
    var beginDate = new Date(begin).getTime();
    var endDate = new Date(end).getTime();
    if (begin != '' && end != '' && beginDate > endDate) {
        layer.alert('开始时间不能大于结束时间');
        return false;
    }
    if (begin != '' && end != '' && beginDate == endDate) {
        layer.alert('开始时间不能等于结束时间');
        return false;
    }
    var beginDay = new Date(begin.replace(/-/g, "/"));
    var endDay = new Date(end.replace(/-/g, "/"));
    if ((endDay - beginDay) / (1000 * 60 * 60 * 24) > 31) {
        layer.alert('请选择30天内的数据！');
        return false;
    }
    return true;
}

function load() {
    if (!checkDate()) {
        return false;
    }
    getGridDataColumns();
    $('#dataGrid').bootstrapTable({
            method: 'get', // 服务器数据的请求方式 get or post
            url: prefix + "/dataGrid", // 服务器数据的加载地址
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
                    registeDateBegin: $('#beginDate').val(),
                    registeDateEnd: $("#endDate").val(),
                    userIdParam: $("#userIdParam").val(),
                    cellPhoneParam: $("#cellPhoneParam").val(),
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
            columns: gridDataColumns,

        }
    )
    ;

}

function reLoad() {
    if (!checkDate()) {
        return false;
    }
    $("#dataGrid").bootstrapTable('destroy');
    load();
}

exportAccountExcel = function () {
    layer.confirm('确定导出数据？', {
        btn: ['确定', '取消']
    }, function (index) {
        if (!checkDate()) {
            return false;
        }
        var registeDateBegin = $('#beginDate').val();
        var registeDateEnd = $("#endDate").val();
        var userIdParam = $("#userIdParam").val();
        var cellPhoneParam = $("#cellPhoneParam").val();
        var params = registeDateBegin + "&registeDateEnd=" + registeDateEnd + "&userIdParam=" + userIdParam + "&cellPhoneParam=" + cellPhoneParam
        window.open("/fundsManagement/cusCustomerAccountExport?registeDateBegin=" + params);
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
