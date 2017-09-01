<!DOCTYPE html>
<%--<%@ page import="com.jeefw.model.sys.User" %>--%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<meta charset="UTF-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js"></script>
    <%--<link rel="stylesheet" type="text/css" href="static/css/bootstrap.css" />--%>
    <%--<link rel="stylesheet" type="text/css" href="static/css/bootstrapValidator.css"/>--%>
    <%--<link rel="stylesheet" type="text/css" href="static/css/bootstrap-responsive.css" />--%>
    <%--<link rel="stylesheet" type="text/css" href="static/css/style.css" />--%>
    <%--<script type="text/javascript" src="static/js/jquery-1.8.1.min.js"></script>--%>
    <%--<script type="text/javascript" src="static/js/bootstrap.min.js"></script>--%>
    <%--<script type="text/javascript" src="static/js/ckform.js"></script>--%>
    <%--<script type="text/javascript" src="static/js/common.js"></script>--%>
    <%--<script type="text/javascript" src="static/js/bootstrapValidator.js"></script>--%>
    <%--<script type="text/javascript" src="static/js/language/zh_CN.js"></script>--%>
   
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
<div class="container">
        <div class="row">
            <!-- form: -->
            <section>
                <div class="page-header">
                    <h1></h1>
                </div>

                <div class="col-lg-8 col-lg-offset-2">
                    <form id="edituserform" method="post" action="#" class="form-horizontal">
                        <fieldset>
                        	<input hidden type="text" name="userid" value=<c:if test="${!empty user}">"${user.id}"</c:if><c:if test="${empty user}">"0"</c:if> />
                            <div class="form-group">
                                <label class="col-lg-3 control-label">账号</label>
                                <div class="col-lg-5">
                                    <input type="text" class="form-control" name="username" 
                                    <c:if test="${!empty user}">readonly</c:if> 
                                    value=<c:if test="${!empty user}">"${user.account}"</c:if>
                                    	  <c:if test="${empty user}">""</c:if>/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-lg-3 control-label">密码</label>
                                <div class="col-lg-5">
                                    <input type="password" class="form-control" name="password" value=<c:if test="${!empty user}">"${user.pwd}"</c:if>
                                    	  <c:if test="${empty user}">""</c:if>/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-lg-3 control-label">重新输入密码</label>
                                <div class="col-lg-5">
                                    <input type="password" class="form-control" name="confirmpassword" value=<c:if test="${!empty user}">"${user.pwd}"</c:if>
                                    	  <c:if test="${empty user}">""</c:if>/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-lg-3 control-label">用户</label>
                                <div class="col-lg-5">
                                    <input type="text" class="form-control" name="realname" 
                                    value=<c:if test="${!empty user}">"${user.name}"</c:if>
                                    	  <c:if test="${empty user}">""</c:if>/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-lg-3 control-label">类型</label>
                                <div class="col-lg-5">
                                    <select class="form-control" name="usertype" id="usertype">                                  
										<c:forEach var="usertype" items="${usertypes}">  
                                        <option value=${usertype.id} <c:if test="${!empty user && user.type==usertype.id}">selected="selected"</c:if>>${usertype.name}</option>		
                                        </c:forEach>							   									     
                                    </select>
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-lg-3 control-label">上级用户</label>
                                <div class="col-lg-5">
                                    <select class="form-control" name="parentuser" id="parentusers">                                  
										<c:forEach var="parentuser" items="${parentusers}">  
                                        <option value=${parentuser.id} <c:if test="${!empty user && user.parentuserid==parentuser.id}">selected="selected"</c:if>>${parentuser.name}</option>		
                                        </c:forEach>							   									     
                                    </select>
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-lg-3 control-label">电话</label>
                                <div class="col-lg-5">
                                    <input type="text" class="form-control" name="phone" 
                                    value=<c:if test="${!empty user}">"${user.phone}"</c:if>
                                    	  <c:if test="${empty user}">""</c:if>/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-lg-3 control-label">地址</label>
                                <div class="col-lg-5">
                                    <input type="text" class="form-control" name="address" 
                                    value=<c:if test="${!empty user}">"${user.address}"</c:if>
                                    	  <c:if test="${empty user}">""</c:if>/>
                                </div>
                            </div>   
                            <div class="form-group">
                                <label class="col-lg-3 control-label">血氧技术开关</label>
                                <div class="col-lg-5">
                                    <select class="form-control" name="spo2warning" id="spo2warning">                                  										 
                                      <option value=1 <c:if test="${!empty user && user.spo2Warning==true}">selected="selected"</c:if> >开</option>
                                      <option value=0 <c:if test="${!empty user && user.spo2Warning==false}">selected="selected"</c:if>>关</option>		                                        
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-lg-3 control-label">体温技术开关</label>
                                <div class="col-lg-5">
                                    <select class="form-control" name="btwarning" id="btwarning">                                  
										<option value=1  <c:if test="${!empty user && user.BTWarning==true}">selected="selected"</c:if> >开</option>
                                        <option value=0  <c:if test="${!empty user && user.BTWarning==false}">selected="selected"</c:if>>关</option>							   									     
                                    </select>
                                </div>
                            </div>  
                            <div class="form-group">
                                <label class="col-lg-3 control-label">血压显示模式</label>
                                <div class="col-lg-5">
                                    <input type="text" class="form-control" name="bpshowmode" 
                                    value=<c:if test="${!empty user}">"${user.BPShowMode}"</c:if>
                                    	  <c:if test="${empty user}">"0"</c:if>/>
                                </div>
                            </div>                     
                        </fieldset>
                      
                        <div class="form-group">
                            <div class="col-lg-9 col-lg-offset-3">
                                <button type="submit" class="btn btn-primary col-lg-2 col-lg-offset-1" name="saveuser" id="saveuser">保存</button>                                                             
                                <button type="button" class="btn btn-success col-lg-2 col-lg-offset-1" name="backid" id="backid">返回列表</button>
                            </div>
                        </div>                       
                    </form>
                </div>
            </section>
            <!-- :form -->
        </div>
    </div>
<script>
    $(function () {       
		$('#backid').click(function(){
			window.location.href="mp/user/index";
		 });
    });
    
	$(function () {       
		$('#saveuser').click(function(e){
			e.preventDefault();
			$.ajax({
				type: "POST",
				url: 'mp/user/saveuser',  
				data: $('#edituserform').serialize(),  
				success: function(r){ 
					if($('#userid').val=="0"){
						alert('添加成功');
					}else{
						alert('修改成功');
						$('#backid').click();
					}					 
				}
			});
 		});
    });
    
    $(document).ready(function() {
    
    	Array.prototype.in_array = function(e)  
		{  
			for(i=0;i<this.length;i++)  
			{  
				if(this[i] == e)  
					return true;  
			}  
					return false;   
		}
		
    	$('#usertype').change(function(){ 
    		$('#parentusers').empty();
    	
    		var type_parent_map = {};
			type_parent_map['3'] = [1, 3];
			type_parent_map['2'] = [1];
			type_parent_map['4'] = [3];
			type_parent_map['5'] = [4];
			type_parent_map['6'] = [3, 4];
			type_parent_map['7'] = [3, 4];
			
			var selectType = $(this).children('option:selected').val();//这就是selected的值 
		    if(type_parent_map.hasOwnProperty(selectType)){
		    	var parentTypeArray = type_parent_map[selectType];
		    	<c:forEach var="parentuser" items="${parentusers}">
		    		if(parentTypeArray.in_array(${parentuser.type})){   
		    			$('#parentusers').append("<option value=${parentuser.id}>${parentuser.name}</option>");//.options.add(varItem);     
		    		}		   
                </c:forEach>
		    }
			 
		});
		$('#usertype').change();
			
	    $('#edituserform').bootstrapValidator({
	        message: 'This value is not valid',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	            username: {
	                message: '账号无效',
	                validators: {
	                    notEmpty: {
	                        message: '账号不能为空'
	                    },
	                    stringLength: {
	                        min: 3,
	                        max: 30,
	                        message: '用户名必须大于3，小于30个字'
	                    },
	                    regexp: {
	                        regexp: /^[a-zA-Z0-9_]+$/,
	                        message: '用户名只能由字母、数字、点和下划线组成'
	                    }
	                }
	            },
	            password: {
	                validators: {
	                    notEmpty: {
	                        message: '密码不能位空'
	                    },
	                }
	            },
	            confirmpassword: {
	                validators: {
	                    notEmpty: {
	                        message: '密码不能为空'
	                    },
	                    identical: {
	                        field: 'password',
	                        message: '两次密码不一致'
	                    },                 
	                }
	            }
	        }
	    });
	});
</script>

</body>
</html>
