var prefix = "/businessBig";

$(function () {
    renderDate();
    window.setTimeout(function () {
        renderRows();
        renderFooter();
    }, 500)
});

function renderDate() {
    var date = new Date();
    var {year, month, date} = {year: date.getFullYear(), month: date.getMonth() + 1, date: date.getDate()};
    laydate.render({
        elem: '#beginDate',
        value: `${year}-${month}-1`
    });
    laydate.render({
        elem: '#endDate',
        value: `${year}-${month}-${date}`
    });
}

function renderRows() {
    var config = getConfig()
    $('#dataGrid').bootstrapTable(Object.assign(config, {
        url: prefix + "/purchaseProDataGrid", // 服务器数据的加载地址
        columns: getGridDataColumns(),
        pageSize: 10, // 如果设置了分页，每页数据条数
        pageNumber: 1, // 如果设置了分布，首页页码
        pagination: true, // 设置为true会在底部显示分页条
    }));
}

function renderFooter() {
    var config = getConfig()
    $('#footer').bootstrapTable(Object.assign(config, {
        url: prefix + "/purchaseProTotal", // 服务器数据的加载地址
        columns: getFooterColumns(),
    }));
}

function getConfig() {
    return {
        method: 'get', // 服务器数据的请求方式 get or post
        iconSize: 'outline',
        toolbar: '#exampleToolbar',
        striped: true, // 设置为true会有隔行变色效果
        dataType: "json", // 服务器返回的数据类型
        singleSelect: false, // 设置为true将禁止多选
        showColumns: false, // 是否显示内容下拉框（选择显示的列）
        sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者
        onSort: function (name, order) {
            $('#dataGrid').bootstrapTable('refreshOptions', {
                sortName: name,
                sortOrder: order
            });
        },
        queryParams: function (params) {
            return {
                // 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                limit: params.limit,
                offset: params.offset,
                sort: params.sort,
                order: params.order,
                beginDate: $('#beginDate').val(),
                endDate: $("#endDate").val(),
                productName: $("#productName").val().trim(),
                proStatus: $("#proStatus").val(),
                productCode : $("#productCode").val()
            };
        },
        searchAccentNeutralise: true,
        resizable: true,
        classes: "table table-bordered table-hover table-sm"
    }
}

function getGridDataColumns() {
    //商品名称/状态/上架时间/下架时间/采购单价/总采购量/采购总豆额/商户数量
    return [
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
            field: 'productName',
            title: '商品名称'
        }, {
            field: 'proStatus',
            title: '状态',
        }, {
            field: 'uDate',
            title: '上架时间',
        }, {
            field: 'downDate',
            title: '下架时间',
        }, {
            field: 'price',
            title: '采购单价',
        }, {
            field: 'quantity',
            title: '总采购量',
            sortable: true,
        }, {
            field: 'total',
            title: '采购总豆额',
            sortable: true,
        }, {
            field: 'storeAmount',
            title: '商户数量',
            sortable: true,
            formatter : function(value, row, index) {
                return "<a href='javascript:void(0)' onclick='showBusinessDetail("+ row.product_code+","+row.storeAmount+")'>"+value +"</a>";
            }
        }];
}

function getFooterColumns() {
    return [{
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
        field: 'quantity',
        title: '总采购量',
    }, {
        field: 'total',
        title: '采购总豆额',
    }, {
        field: 'storeAmount',
        title: '商户数量',
    }
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

function showBusinessDetail(productCode,storeAmount) {
    // $("#productCode").val(productCode);
    // var opts_Detail = dataGrid_Detail.datagrid("options");
    // if (opts_Detail.url == null) {
    //     opts_Detail.url = '/dataGrid/purchaseProDetailDataGrid';
    // }
    // dataGrid_Detail.datagrid('load', {'params' : JSON.stringify(serializeObject($('#form')))});

    $("#productCode").val(productCode);
    layer.open({
        type : 2,
        title : '商户商品采购详情',
        shadeClose : false,
        area : [ '650px', '480px' ],
        content : prefix + '/businessDetail'
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
        $('#export').attr('action',"/export/proPurchaseEx").submit();
        setTimeout(function(){layer.close(index);},1000)
    })
};

function getParams(){
    return {
        beginDate: $('#beginDate').val(),
        endDate: $("#endDate").val(),
        productName: $("#productName").val().trim(),
        proStatus: $("#proStatus").val(),
        productCode : $("#productCode").val()
    };
}


