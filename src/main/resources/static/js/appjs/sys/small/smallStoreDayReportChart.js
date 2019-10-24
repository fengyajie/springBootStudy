var prefix = "/businessSmall";
$(function() {

	var data=new Date();
	var monthCurr=data.getMonth()+1;
	var yearCurr=data.getFullYear();
	var monLength=monthCurr.toString().length;
	if(monLength==1){
		var monthCurr1="0"+monthCurr
	}else{
		var monthCurr1=monthCurr;
	}

	laydate.render({
		elem : '#datea',
		trigger: 'click',
		type : 'month',
		value:""+yearCurr+"-"+monthCurr1+""
	});

});
