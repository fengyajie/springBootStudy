var prefix = "/bigStore"
	
	
var highc;
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

	$(function() {
		getorganList();
	});
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

	function loadData(result) {
		var s = result;

		var data = [];
		for (var i = 0; i < result.message.length; i++) {
			var item = result.message[i];

			var row = {};

			row.name = item.business_name;

			row.y = item.nums;

			data.push(row);
		}
		Highcharts.chart('container', {
			chart : chart,
			title: {
				text: '商户充值分析'
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
      if(!checkDate()){return;}
		$.ajax({
					url : prefix+"/queryBigProduct",
					type : "POST",
					data : {
						'params' : JSON.stringify(serializeObject($('#form')))
					},
					dataType : "json",
					async : false,
					success : function(result) {
						loadData(result);
					}
				});

		$('svg').children("text:last-child").hide();
		$(".highcharts-exporting-group").hide();
	};
	//分公司下拉列表
	function getorganList() {
		$.ajax({
			url : prefix+"/getOrganList",
			type : "POST",
			dataType : "json",
			async : false,
			success : function(result) {
				var s = result;
				$("#organ").append(
						'<option value="" selected="selected">全部</option>');

				for (var i = 0; i < result.message.length; i++) {
					var item = result.message[i];
					$("#organ").append(
							'	<option value="'+item.SysNo+'">' + item.BriefName
									+ '</option>');
				}
			},
		});
	}