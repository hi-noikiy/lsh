<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta name="description" content="">
<meta name="keywords" content="">
<title>代理购买记录</title>
<base href="<%=basePath%>">
<link href="static/lshapp/css/goods_type.css" rel="stylesheet"
	type="text/css">
<script type="text/javascript">
	var ROOT_URL = '/mobile/';
</script>
<link rel="stylesheet" type="text/css" href="static/lshapp/css/base.css">
<script type="text/javascript"
	src="static/lshapp/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="static/lshapp/js/mobile-util.js"></script>
<script type="text/javascript" src="static/lshapp/js/fastclick2.js"></script>
<style type="text/css">
::-webkit-input-placeholder { /* WebKit browsers */
	color: rgba(0, 0, 0, .5);
}

:-moz-placeholder { /* Mozilla Firefox 4 to 18 */
	color: rgba(0, 0, 0, .5);
}

::-moz-placeholder { /* Mozilla Firefox 19+ */
	color: rgba(0, 0, 0, .5);
}

:-ms-input-placeholder { /* Internet Explorer 10+ */
	color: rgba(0, 0, 0, .5);
}

.regmobile {
	width: 94%;
	margin: .4rem auto;
	font-size: .4rem;
	color: rgba(0, 0, 0, .7);
}

body {
	background: #fff;
}
</style>
</head>
<body>
	<div class="common-show"></div>
	<script src="static/js/jquery.cookie.js"></script>
	<script src="static/lshapp/js/fastclick2.js"></script>
	<div class="contents">
		<!-- <div class="fgheader">
			<a href="javascript:window.history.go(-1);"><img
				src="images/Return.png"></a>租借记录
		</div> -->
		<ul class="zjlist">
			<c:forEach items="${varList}" var="var" varStatus="vs">
				<li>
					<div class="zjbox">
						<p class="tops">
							<span class="fl"> 
								<c:if test="${var.AGENT_STATUS == 0}">会员</c:if>
								<c:if test="${var.AGENT_STATUS == 1}">经销商</c:if>
								<c:if test="${var.AGENT_STATUS == 2}">企商VIP</c:if>
							</span> 
							<span class="fr green"> 
								<c:if test="${var.TYPE == 0}">&nbsp&nbsp&nbsp微信</c:if>
								<c:if test="${var.TYPE == 1}">&nbsp&nbsp&nbsp支付宝</c:if>
							</span> <span class="fr green">-${var.AGENT_PRICE}元</span>
						</p>
						<p class="times">${var.CREATE_TIME}</p>

					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
	<script type="text/javascript">
		// fastclick.js组件的引用
		$(function() {
			FastClick.attach(document.body);
		})
	</script>
</body>
</html>