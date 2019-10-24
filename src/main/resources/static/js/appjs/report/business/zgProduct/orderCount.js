
$(function () {

    getOrderMonthFoldData();
    loadDate();
    initDate();
});




var title = {
    text: '订单数量曲线图'
};
var xAxis = {
    categories: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月']
};
var yAxis = {
    title: {
        text: '订单数'
    },
    plotLines: [{
        value: 0,
        width: 1,
        color: '#808080'
    }]
};
var legend = {
    layout: 'vertical',
    align: 'right',
    verticalAlign: 'middle',
    borderWidth: 0
};
var series =  [
    {
        name: 'Tokyo',
        data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
    },
];
function initDate() {
    var date = new Date();
    var dt = fnW(date.getMonth()+1);
    var d1 = date.getFullYear()+"-"+dt+"-01";
    var d2 = date.getFullYear()+"-"+dt+"-"+fnW(date.getDate());
    $("#beginDate").val(d1);
    $("#endDate").val(d2);
}

function fnW(str) {
    var num;
    str >= 10 ? num = str : num = "0" + str;
    return num;
}
function getOrderMonthFoldData() {
    // var formData = new FormData();
    // formData.append("monthTime",$('#categoryName').val.trim());
    $.ajax({
        url : "/userTime/orderCountsFoldData",
        data : {
            'categoryName' : $('#categoryName').val(),
            // 'params' : JSON.stringify(serializeObject($('#form')))
        },
        type : "POST",
        dataType : "json",
        async: false,
        success:function(result) {
            xAxis.categories = result.message.categories;
            series[0].name = "订单数量";
            series[0].data = result.message.counts;
            loadDate();
        },
    });
}

function loadDate() {
    var json = {};
    json.title = title;
    json.xAxis = xAxis;
    json.yAxis = yAxis;
    json.legend = legend;
    json.series = series;
    $('#fold').highcharts(json);
    $('svg').children("text:last-child").hide();

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