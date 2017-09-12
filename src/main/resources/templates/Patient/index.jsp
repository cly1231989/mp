<!DOCTYPE html>
<%--<%@ page import="com.jeefw.model.sys.PatientInfo" %>--%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title></title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js"></script>
    <%--<link rel="stylesheet" type="text/css" href="static/css/bootstrap.css" />--%>
    <%--<link rel="stylesheet" type="text/css" href="static/css/bootstrap-responsive.css" />--%>
    <%--<link rel="stylesheet" type="text/css" href="static/css/style.css" />--%>
    <%--<script type="text/javascript" src="static/js/jquery.js"></script>--%>
    <%--<script type="text/javascript" src="static/js/jquery.sorted.js"></script>--%>
    <%--<script type="text/javascript" src="static/js/bootstrap.js"></script>--%>
    <%--<script type="text/javascript" src="static/js/ckform.js"></script>--%>
    <%--<script type="text/javascript" src="static/js/common.js"></script>--%>
    	
    <%--<style type="text/css">--%>
        <%--body {--%>
            <%--padding-bottom: 40px;--%>
        <%--}--%>
        <%--.sidebar-nav {--%>
            <%--padding: 9px 0;--%>
        <%--}--%>

        <%--@media (max-width: 980px) {--%>
            <%--/* Enable use of floated navbar text */--%>
            <%--.navbar-text.pull-right {--%>
                <%--float: none;--%>
                <%--padding-left: 5px;--%>
                <%--padding-right: 5px;--%>
            <%--}--%>
        <%--}--%>
    <%--</style>--%>
</head>
<body>
<div class="form-inline definewidth m20">
    病人姓名：
    <input type="text" name="patientname" id="searchcondition" class="abc input-default" placeholder="" value="">&nbsp;&nbsp; 
    <button id="searchbtn" type="botton" class="btn btn-primary">查询</button>
</div>
<div style="margin-left:20px; margin-top:20px;">
    <table id="jqGrid"></table>
    <div id="jqGridPager"></div>
</div> 
<table class="table table-bordered table-hover definewidth m10">
    <thead>
    <tr>
        <th>索引</th>
        <th>用户</th>
		<th>姓名</th>
        <th>年龄</th>
        <th>性别</th>     
        <th>门诊号</th>    
		<th>住院号</th>            
        <th>床号</th>  
        <th>症状</th>     
        <th>申请医生</th>
        <th>护士</th>
        <th>护理等级</th>
        <th>费别</th>   
    </tr>
    </thead>
    <tbody id="patientlist">
	 
	</tbody>
</table>
<div align="center" style="padding-top: 10px;">
	<a id="firstpage" href="#"> << </a>&nbsp;&nbsp;
	<a id="prevpage" href="#">  <  </a>&nbsp;&nbsp;
	<input id="pageth" type="text" style="width:50; height:20"/> &nbsp;&nbsp;页(共<label id="totalpagelabel"></label>页)&nbsp;&nbsp; 
	<a id="nextpage" href="#">  >  </a>&nbsp;&nbsp; 
	<a id="lastpage" href="#">  >> </a>&nbsp;&nbsp;
	<select id="countperpage">
		<option value ="100">100</option>
		<option value ="200">200</option>
		<option value ="300">300</option>
	</select>
</div>>
</body>
</html>
<script>
    $(function () {

    	var curPageth = 1;
    	var totalPage = 1;

    	function searchPatients(name, pageth, rows){
    		$.ajax({
				type: "POST",			
				url: 'patient/page',
				data: {"patientname":name, "page":pageth, "rows":rows, "sord":"desc"},//$('#searchcondition').serialize(),  
				error: function(){alert('搜索失败');},  
				success: function(data){ 
					var str = "";
					var jsonData = eval("("+data+")");
					if(jsonData.result == "relogin"){
						alert("请重新登录后再进行操作"); 
						top.location="/logout";
						return;
					}
					
					totalPage = jsonData.total;
					var pageth = jsonData.page;
					var totalCount = jsonData.records;
					var patients = jsonData.rows;
					
					$('#pageth').val(pageth);
					$('#totalpagelabel').text(totalPage);
				
					for(var i = 0; i < patients.length; i++) {									
					
						str +=  "<tr><td>" + patients[i].patient.id 			+ "</td>" +
								"<td>" + patients[i].username					+ "</td>" +										
								"<td>" + patients[i].patient.name 				+ "</td>" +
								"<td>" + patients[i].patient.age 				+ "</td>" +
								"<td>" + patients[i].patient.sex 				+ "</td>" +
								"<td>" + patients[i].patient.outpatientnumber 	+ "</td>" +
								"<td>" + patients[i].patient.hospitalnumber 	+ "</td>" +
								"<td>" + patients[i].patient.bednumber			+ "</td>" +										
								"<td>" + patients[i].patient.symptom	 		+ "</td>" +
								"<td>" + patients[i].patient.applydoctor 		+ "</td>" +
								"<td>" + patients[i].patient.nurse 				+ "</td>" +
								"<td>" + patients[i].patient.level 				+ "</td>" +
								"<td>" + patients[i].patient.category 			+ "</td></tr>";
					}													
				
					$('#patientlist').html(str);
				}
			});
    	}
            
		$('#searchbtn').click(function(){
			searchPatients( $('#searchcondition').val(), 1, $('#countperpage').val() );
		});
		$('#searchbtn').click();

		$('#firstpage').click(function(event){
			event.preventDefault();
			if(curPageth == 1)
				return;

			curPageth = 1;
			$('#pageth').val(curPageth);
			searchPatients( $('#searchcondition').val(), curPageth, $('#countperpage').val() );
		});

		$('#prevpage').click(function(event){
			event.preventDefault();
			if(curPageth == 1)
				return;

			curPageth--;
			$('#pageth').val(curPageth);
			searchPatients( $('#searchcondition').val(), curPageth, $('#countperpage').val() );
		});

		$('#nextpage').click(function(event){
			event.preventDefault();
			if(curPageth == totalPage)
				return;

			curPageth++;
			$('#pageth').val(curPageth);
			searchPatients( $('#searchcondition').val(), curPageth, $('#countperpage').val() );
		});

		$('#lastpage').click(function(event){
			event.preventDefault();
			if(curPageth == totalPage)
				return;

			curPageth = totalPage;
			$('#pageth').val(curPageth);
			searchPatients( $('#searchcondition').val(), curPageth, $('#countperpage').val() );
		});		

		$('#countperpage').change(function(){
			searchPatients( $('#searchcondition').val(), curPageth, $('#countperpage').val() );
		});

    });
	
</script>