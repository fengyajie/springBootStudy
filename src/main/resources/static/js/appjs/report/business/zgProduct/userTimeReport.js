
var prefix = "/userTime"

$(function () {
    // load();
    loadDate();
    initDate();
    $("svg text:last").hide();
    getuserTimeCountFoldData();
    laydate.render({
        elem : '#beginDate',
        type : 'date'
    });
    laydate.render({
        elem : '#endDate',
        type : 'date',
        max : getNowFormatDate()
    });
});

function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month
        + seperator1 + strDate + " " + date.getHours() + seperator2
        + date.getMinutes() + seperator2 + date.getSeconds();
    return currentdate;
}



var title = {
    text: '用户时间区'
};
var xAxis = {
    categories: ['0:00', '1:00', '2:00', '3:00', '4:00', '5:00', '6:00', '7:00', '8:00', '9:00', '10:00', '11:00']
};
var yAxis = {
    title: {
        text: '金额'
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
        name: '金额',
        data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
    },
];

function fnW(str) {
    var num;
    str >= 10 ? num = str : num = "0" + str;
    return num;
}

function dateSub(beginDate,endDate) {
    var begin = Date.parse(beginDate.replace(/-/g, "/"));
    var end = Date.parse(endDate.replace(/-/g, "/"));
    return end - begin;
}

function initDate() {
    var date = new Date();
    var dt = fnW(date.getMonth()+1);
    var d1 = date.getFullYear()+"-"+dt+"-01";
    var d2 = date.getFullYear()+"-"+dt+"-"+fnW(date.getDate());
    $("#beginDate").val(d1);
    $("#endDate").val(d2);
}

function getuserTimeCountFoldData() {
    if (!checkDate()) {
        return;
    }
    $.ajax({
        url : "/userTime/userTimeCountFoldData",
        data : {
            'beginDate' : $('#beginDate').val().trim(),
            'endDate' : $('#endDate').val().trim(),
            'categoryName' : $('#categoryName').val(),
        },
        type : "POST",
        dataType : "json",
        async: false,
        success:function(result) {
            xAxis.categories = result.message.categories;
            series[0].name = "金额";
            series[0].data = result.message.orderMoney;
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



function checkDate() {
    var beginDate = $('#beginDate').val();
    var endDate = $('#endDate').val();

    if('' == beginDate || '' == endDate) {
        parent.layer.msg("请选择日期");
        return false;
    }
    var dateDifference = dateSub(beginDate,endDate);
    if(dateDifference > 0) {
        var begin = new Date(beginDate.replace(/-/g,"/")).Format("yyyy-MM");
        var end = new Date(endDate.replace(/-/g,"/")).Format("yyyy-MM");
        if (begin != end) {
            parent.layer.msg("请选择同年同月日期");
            return false;
        }
    }
    return true;
}