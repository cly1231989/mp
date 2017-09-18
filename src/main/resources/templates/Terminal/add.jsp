<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <%--<script type="text/javascript" src="static/js/jedate.js"></script>--%>

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
                    <form id="editterform" method="post" action="#" class="form-horizontal">
                        <fieldset>
                        	<input hidden type="text" id="terid" name="terid" value=<c:if test="${!empty ter}">"${ter.id}"</c:if><c:if test="${empty ter}">"0"</c:if> />
                            <div class="form-group">
                                <label class="col-lg-3 control-label">终端编号</label>
                                <div class="col-lg-5">
                                    <input type="text" class="form-control" name="ternum"                                     
                                    value=<c:if test="${!empty ter}">"${fn:substringAfter(ter.terminalnumber, "850271201")}"</c:if>
                                    	  <c:if test="${empty ter}">""</c:if>/>
                                </div>
                            </div>                            
                            <!-- <div class="form-group">
                                <label class="col-lg-3 control-label">绑定时间</label>
                                <div class="col-lg-5">
                                    <input type="text" id="dateinfo" class="form-control datainp" name="bindtime" readonly
                                    value=<c:if test="${!empty ter}">"${ter.bindtime}"</c:if>
                                    	  <c:if test="${empty ter}">""</c:if>/>
                                </div>
                            </div>     -->                                            
                        </fieldset>
                      
                        <div class="form-group">
                            <div class="col-lg-9 col-lg-offset-3">
                                <button type="submit" class="btn btn-primary col-lg-2 col-lg-offset-1" name="saveter" id="saveter">保存</button>                                                             
                                <button type="button" class="btn btn-success col-lg-2 col-lg-offset-1" name="backid" id="backid">返回列表</button>
                            </div>
                        </div>                       
                    </form>
                </div>
            </section>
            <!-- :form -->
        </div>
    </div>
</body>
</html>
<script>
    $(function () {
           
		$('#backid').click(function(){
				window.location.href="mp/terminal/index";
		});
		 		       
		$('#saveter').click(function(e){
			e.preventDefault();
			$.ajax({
				type: "POST",
				url: 'mp/terminal/Save',
				data: $('#editterform').serialize(),  
				success: function(r){ 
					if($('#terid').val()==0){
						alert('添加成功');
					}else{
						alert('修改成功');
						$('#backid').click();
					}					 
				}
			});
 		});    

	jeDate({
		dateCell:"#dateinfo",
		format:"YYYY-MM-DD hh:mm:ss",
		isTime:true, //isClear:false,
		minDate:"2014-09-19 00:00:00"
	})
	
    });
</script>