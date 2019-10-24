var prefix = "/bigStore"

var chart = {
	plotBackgroundColor : null,
	plotBorderWidth : null,
	plotShadow : false,
	type : 'pie'
};
var tooltip = {
	pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
};
var plotOptions = {
	pie : {
		allowPointSelect : true,
		cursor : 'pointer',
		dataLabels : {
			enabled : true,
			format : '<b>{point.name}</b>: {point.percentage:.1f} %',
			style : {
				color : (Highcharts.theme && Highcharts.theme.contrastTextColor)
						|| 'black'
			}
		}
	}
};

	function loadData(result) {
		var s = result;

		var data = [];
		for (var i = 0; i < result.message.length; i++) {
			var item = result.message[i];

			var row = {};

			row.name = item.name;

			row.y = item.nums;

			data.push(row);
		}
		Highcharts.chart('container', {
			chart : chart,
			title : {
				text : $("#selectType").find("option:selected").text()
			},
			tooltip : tooltip,
			plotOptions : plotOptions,
			series : [ {
				name : 'Brands',
				colorByPoint : true,
				data : data
			} ]
		});
	}
	function checkDate() {

		if ($("#beginDate").val() == '') {
			alert('请选择开始时间');
			return false;
		}

		if ($("#endDate").val() == '') {
			alert('请选择结束时间');
			return false;
		}
		return true;
	}
	function searchFun() {
		if (!checkDate()) {
			return;
		}
		$.ajax({
					url : prefix+"/queryBigRechargeMoney",
					type : "POST",
					data : $("#searchForm").serialize(),
					dataType : "json",
					async : false,
					success : function(result) {
						loadData(result);
					}
				});
		$('svg').children("text:last-child").hide();
		$(".highcharts-exporting-group").hide();
	};