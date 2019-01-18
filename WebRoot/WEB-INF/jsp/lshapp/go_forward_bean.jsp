<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	    <meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	    <meta name="apple-mobile-web-app-capable" content="yes">
	    <meta name="apple-mobile-web-app-status-bar-style" content="black">
	    <meta name="format-detection" content="telephone=no">
	    <meta name="description" content="">
	    <meta name="keywords" content="">
	    <title>金豆数量</title>
	    <link href="static/lshapp/css/center/app.css" rel="stylesheet" type="text/css">
	    <link rel="stylesheet" href="static/lshapp/css/center/common3.css"/>
	    <script type="text/javascript">var ROOT_URL = '/mobile/';</script>
	    <script src="static/lshapp/js/center/app.js"></script>
		<script src="static/lshapp/js/center/jquery-1.9.1.min.js"></script>
		<script src="static/lshapp/js/center/ajaxfileupload.js"></script>

		<script src="static/lshapp/js/layer.js"></script>

		<style type="text/css">
			.return_image{
				width:1.2rem;
				height:1.8rem;
			}
			.submint {
			height: 40px;
			width: 94%;
			/* border: 1.3px solid #00AEED; */
			border-radius: 7px;
			margin-left: 3%;
			top: 198px;
			text-align: center;
			font-size: 1.7rem;
			background-color:#ED4C44;
			color: white;
			line-height:40px;
			position: absolute;
			}
			.submint1 {
			height: 97px;
			width: 94%;
			/* border: 1.3px solid #00AEED; */
			border-radius: 7px;
			margin-left: 3%;
			background-color:white;
			top: 72px;
			position: absolute;
			}
		</style>
		<script type="text/javascript">
			//保存
			function beannumber(){
				debugger
				var beanNumber = $("#bean_number").val();//输入金豆
				var integration = $("#INTEGRATION").val();//剩余的金豆
				if(beanNumber <= 0){
					layer.open({
						content : '金豆不能空',
						skin : 'msg',
						time : 3, //3秒后自动关闭
						style : 'border:none; background-color:#FF4351; color:#fff;'
					});
				}else if(beanNumber > integration){//判断输入金豆是否大于剩余的金豆
					layer.open({
						content : '金豆不足',
						skin : 'msg',
						time : 2, //2秒后自动关闭

						style : 'border:none; background-color:#FF4351; color:#fff;'
					});
				}else{//保存
					var receiveUserId = $("#receive_user_id").val();
				    $.ajax({
						url : '<%=basePath%>lshapp/userCenter/forwardBean.do',
						type : 'post',
						async : false,
						dataType : 'json',
						data : {
							'receiveUserId' : receiveUserId,
							'beanNumber'  : beanNumber
						},
						success : function(data) {
							if (data == 1) {
								layer.open({
									content : '金豆转让成功',
									skin : 'msg',
									time : 2, //2秒后自动关闭
									style : 'border:none; background-color:#FF4351; color:#fff;'
								});
							} else {
								layer.open({
									content : '金豆转让失败',
									skin : 'msg',
									time : 2, //2秒后自动关闭
									style : 'border:none; background-color:#FF4351; color:#fff;'
								});
							}
						}
					});

				    <%--var token = $("#token").val();
					window.location.href="<%=basePath%>lshapp/userCenter/bean_transfer.do?beanNumber="+beanNumber
							+'&TOKEN=' + token; --%>
				}
			}
		</script>
	</head>

	<body style="background-color:#f5f5f5; ">
		<p style="text-align:right; display:none;"></p>
		<div id="loading" style="display: none;"><img src="static/lshapp/images/center/loading.gif"></div>
		<%-- <jsp:include page="head_title.jsp">
			<jsp:param value="金豆数量" name="pageTitle"/>
		</jsp:include> --%>
		<div class="submint1">
			<div style="font-size:1.8rem;color:#615d5d;margin-top: 100px;margin-left: 30px;position:relative;margin-top: 16px;">
				金豆数量
			</div>
			<div style="font-size:1.8rem;color:#504b4b;margin-top: 10px;margin-left: 30px;">
				<%-- <span style="font-size: 2.5rem;font-weight: 700;">￥&nbsp;&nbsp;${pd.INTEGRATION}</span> &nbsp;&nbsp;&nbsp; --%>
				<!-- <input type="number" id="bean_number" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/> -->

				￥&nbsp;&nbsp;<input type="text" style="font-size: 26px;" id="bean_number" oninput = "value=value.replace(/[^\d]/g,'')"/>
				<input type="hidden" id="INTEGRATION" value="${pd.INTEGRATION}"/>
				<input type="hidden" id="receive_user_id" value="${pd.receive_user_id}"/>
				<%-- <input type="hidden" id="token" value="${pd.TOKEN}"/> --%>
			</div>
		</div>
		<div class="submint" onclick="beannumber()">确&nbsp;&nbsp;定</div>
	</body>
</html>