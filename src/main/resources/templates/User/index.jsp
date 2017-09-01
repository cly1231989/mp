<!DOCTYPE html>
<%--<%@ page import="com.jeefw.model.sys.User" %>--%>
<%--<%@ page import="com.jeefw.model.sys.UserType" %>--%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<meta charset="UTF-8">
    <title></title>
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
<!--<form class="form-inline definewidth m20" method="get" id="searchform" > -->   
<div class="form-inline definewidth m20">
    <input type="text" name="username" id="searchcondition" class="abc input-default" placeholder="请输入账号或者用户名" value="">&nbsp;&nbsp;  
    <button id="searchbtn" type="button" class="btn btn-primary">查询</button>&nbsp;&nbsp; 
    <button id="addnew" type="button" class="btn btn-success">新增用户</button>&nbsp;&nbsp;
</div>    
<!--</form>-->
<table class="table table-bordered table-hover definewidth m10">
    <thead>
    <tr>
    	<!-- <th><input type="checkbox" name="selallcheckbox" onclick="allSelect('selallcheckbox', 'selonecheckbox')"/></th> -->
        <th>序号</th>        
        <th>上级用户</th>   
        <th>用户</th>        
        <th>账号</th>
		<th>密码</th>        
		<th>电话</th>
		<th>地址</th>
		<th>类型</th>
		<th>血氧开关</th>
		<th>体温开关</th>
		<th>血压显示模式</th>
        <th>操作</th>
    </tr>
    </thead>
	<tbody id="userlist">
		
	</tbody>	
</table>

<script>
    $(function () {
		$('#addnew').click(function(){
				window.location.href="mp/user/add?id=0";
		 });
    });
    
    
    $(function () {
    	$('#selonecheckbox').click(function(){
    	});
    });
    
    $(function(){
    	$('#searchform').submit(function(){
    		if($('#searchcondition').value == "")
    			return false;
    		else
    			return true;
    	});
    });
    
    function searchUser()
    {
    	$.ajax({
			type: "POST",			
			url: 'mp/user/search',  
			//contentType:'application/x-www-form-urlencoded; charset=UTF-8',
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
			
			var usertypes = jsonData.usertypes;
			var parentusers = jsonData.parentusers;
			var users = jsonData.users;
			var index = 0;
			
			for(var parentindex = 0; parentindex < parentusers.length; parentindex++){
				var userindex = 0; 
				
				for(var userindex = 0; userindex < users.length; userindex++) {
					var type="";
					
					if(parentusers[parentindex].id == users[userindex].parentuserid){
						for(var typeindex = 0; typeindex < usertypes.length; typeindex++){
							if(usertypes[typeindex].id == users[userindex].type){
								type = usertypes[typeindex].name;
								break;
							}
						}
						
						index++;
						var spo2Warning = users[userindex].spo2Warning ? "开" : "关";
						var BTWarning = users[userindex].BTWarning ? "开" : "关";
						
						str += "<tr><td>" + index + "</td>" +						
						"<td>" + parentusers[parentindex].name + "</td>" +									
						"<td>" + users[userindex].name + "</td>" +			
					   "<td>" + users[userindex].account + "</td>" +
						"<td>" + users[userindex].pwd + "</td>" +
					   "<td>" + users[userindex].phone + "</td>" +
					   "<td>" + users[userindex].address + "</td>" +
					   "<td>" + type + "</td>" +
					   "<td>" + spo2Warning + "</td>" +
					   "<td>" + BTWarning + "</td>" +
					   "<td>" + users[userindex].BPShowMode + "</td>" +
					   "<td><a href=\"mp/user/add?id=" + users[userindex].id + "\">编辑</a>&nbsp;&nbsp;</td></tr>";
					   //<a onclick=\"return confirmDelUser()\" href=\"mp/user/del?id="  + users[i].id + "\">删除</a>
					}
				}
			}								
		
			$('#userlist').html(str);}});
    }
    
    searchUser();
    
    $(function () {       
		$('#searchbtn').click(function(){
			searchUser(); 
 		});
    });
    
    function confirmDelUser(){
    	return confirm("删除该账户会同时删除该账户下的所有子账户，确定要删除吗？");
    }
    $(function () {
		$('#deluser').click(function(){
			
			if( !confirm("删除该账户会同时删除该账户下的所有子账户，确定要删除吗？") )
				return;
				
			var sels = document.all("selonecheckbox");
			var ids = new Array();
			
			for(var i = 0; i < sels.length; i++)
			{
				if(sels[i].checked)
				{
					ids.push(sels[i].value);
				}
			}
				
			$.ajax({
				type: "POST",
				url: 'mp/user/del',  
				data: {"ids":ids},  
				dataType: "json", 
				traditional: true,  
				success: function(r){ alert('添加成功'); }});
			//window.location.href="mp/user/del?id=?";
		 });
    });

	function del(id)
	{
		if(confirm("确定要删除吗？"))
		{
			var url = "index.html";
			window.location.href=url;		
		}	
	}
	
	function allSelect(check_v, checkname)
	{
	    var v_item = document.getElementsByName(check_v);
	    var items = document.getElementsByName(checkname);
	    for (var i = 0; i < items.length; ++i)
	    {
	        if (v_item[0].checked)
	        {
	            items[i].checked = true;
	        }
	        else
	        {
	            items[i].checked = false;
	        }
	    }
	}
	
	function singleSelect2parent(check_v, checkname)
	{
	    var v_item = document.getElementsByName(check_v);
	    var items = document.getElementsByName(checkname);
	    var childStatus = true;
	    for (var i = 0; i < items.length; ++i)
	    {
	        childStatus = (childStatus && items[i].checked);
	    }
	    if (childStatus)
	    {
	        v_item[0].checked = true;
	    }
	    else
	    {
	        v_item[0].checked = false;
	    }
	}

</script>
</body>
</html>