<!DOCTYPE html>
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

    <style type="text/css">
        body {
            padding-bottom: 40px;
        }
        .sidebar-nav {
            padding: 9px 0;
        }

        @media (max-width: 980px) {
            /* Enable use of floated navbar text */
            .navbar-text.pull-right {
                float: none;
                padding-left: 5px;
                padding-right: 5px;
            }
        }


    </style>
</head>
<body>
<div class="form-inline definewidth m20">
    编号：
    <input type="text" name="ternum" id="searchcondition" class="abc input-default" placeholder="" value="">&nbsp;&nbsp; 
    <button id="searchbtn" type="botton" class="btn btn-primary">查询</button>&nbsp;&nbsp; <button type="button" class="btn btn-success" id="addnew">新增终端</button>
</div>
<table class="table table-bordered table-hover definewidth m10">
    <thead>
    <tr>
        <th>序号</th>
        <th>用户</th>
		<th>终端编号</th>    
        <th>操作</th>
    </tr>
    </thead>
    <tbody id="terminallist">
	 
	</tbody>
</table>

</body>
</html>
<script>
    $(function () {
        
		$('#addnew').click(function(){
			window.location.href="mp/terminal/add?id=0";
		 });

		$('#searchbtn').click(function(){
			$.ajax({
			type: "POST",			
			url: 'mp/terminal/search',  
			data: $('#searchcondition').serialize(),  
			error: function(){alert('搜索失败');},  
			success: function(data){ 
			var str = "";
			var jsonData = eval("("+data+")");
			if(jsonData.result == "relogin"){
				alert("请重新登录后再进行操作"); 
				top.location="/mp/logout"; 
				return;
			}
					
			var users = jsonData.users;
			var userid = jsonData.userid;
			var terminals = jsonData.terlist;
			for(var i = 0; i < terminals.length; i++) {		
				var userName = "";
				for(var userIndex = 0; userIndex < users.length; userIndex++){
					if(users[userIndex].id == terminals[i].userid){
						userName = users[userIndex].name;
						break;
					}
				}
						
				str += "<tr><td>" + (i+1) + "</td>" +
						"<td>" + userName + "</td>" +
					   "<td id=\"ternum\">" + terminals[i].terminalnumber.substr(-6) + "</td>";					   
					   //"<td>" + terminals[i].bindtime + "</td>";
				if(terminals[i].userid == userid){
					str += "<td><a href=\"mp/terminal/add?id=" + terminals[i].id + "\">编辑</a>&nbsp;&nbsp;<a href=\"mp/terminal/del?id="  + terminals[i].id + "\">删除</a></td></tr>"; 
				}				
			}	
		
			$('#terminallist').html(str);}});
		}).click();
    });
	
</script>