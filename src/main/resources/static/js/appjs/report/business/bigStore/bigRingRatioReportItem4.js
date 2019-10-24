
var prefix = "/bigStore"
$(function() {
	getBusinessBigList();
});

var title = {
		text : ''
	};
	var xAxis = {
		categories : []
	};
	var yAxis = {
		title : {
			text : '数值'
		},
		plotLines : [ {
			value : 0,
			width : 1,
			color : '#808080'
		} ]
	};
	var legend = {
		layout : 'vertical',
		align : 'right',
		verticalAlign : 'middle',
		borderWidth : 0
	};
	var series = [{
		name : '',
		data : [ ]
	}  ];

	function getSmallDayFoldData() {
		$.ajax({
					url : prefix+"/queryBusinessFoldDatas",
					data : {
						'selectType':$("#selectType").val(),
						'month':$("#month").val(),
						"business_id":$("#businessName").val()
					},
					type : "POST",
					dataType : "json",
					async : false,
					success : function(result) {
						xAxis.categories = result.message.categories;
					 	
						series[0].name = $('#businessName').val(); 
						series[0].data = result.message.series;
						
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
		$('#container').highcharts(json);
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
	function searchFun() {
		if (checkDate()) {
			getSmallDayFoldData();
		}
	};
	function checkDate() {

		if ($("#month").val() == '') {
			alert('请选择月份');
			return false;
		}
		return true;
	}

    //商户名称下拉列表
	function getBusinessBigList() {
		$.ajax({
			url : prefix+"/queryBusinessBigOwn",
			type : "POST",
			dataType : "json",
			async : false,
			success : function(result) {
				var s = result;
				$("#businessName").append(
						'<option value="" selected="selected">全部</option>');

				for (var i = 0; i < result.message.length; i++) {
					var item = result.message[i];
					$("#businessName").append(
							'<option value="'+item.business_id+'">' + item.business_name
									+ '</option>');
				}
			},
		});
	}