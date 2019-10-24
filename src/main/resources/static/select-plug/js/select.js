$(function(){
			$(".nice-select").click(function(e){
				$(this).find("ul").show();
				e.stopPropagation();
			});
			
			$('#list').delegate('li','mouseover mouseout',function(e){
				$(this).toggleClass("on");
				e.stopPropagation();
			});
			
			$('#list').delegate('li','click',function(e){
				var val = $(this).text();
				var showContent = $('input[name="showContent"]:checked').val();
				if (showContent == "portion") {
					if (val.indexOf('--') != -1) 
						val = val.substring(0, val.indexOf('--'));
				}
				$(".nice-select").find("input").val(val);
				$(".nice-select").find("input").change();
				$(".nice-select").find("ul").hide();
				e.stopPropagation();
			});
			
			$(document).click(function(){
				$(".nice-select").find("ul").hide();
			});
			
			$("#sub").click(function() {
				var val = $.trim($("#addContent").val());
				if (val != '') {
					var content = "<li>"+val+"</li>";
					$("#list").append(content);
					alert("添加成功");
					$("#addContent").val("");
				}
			});
		});
		
		function searchList(strValue) {
			var count = 0;
			if (strValue != "") {
				$(".nice-select ul li").each(function(i) {
					var contentValue = $(this).text();
					if (contentValue.toLowerCase().indexOf(strValue.toLowerCase()) < 0) {
						$(this).hide();
						count++;
					} else {
						$(this).show();
					}
					if (count == (i + 1)) {
						$(".nice-select").find("ul").hide();
					} else {
						$(".nice-select").find("ul").show();
					}
				});
			} else {
				$(".nice-select ul li").each(function(i) {
					$(this).show();
				});
			}
		}