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
<title>代理购买</title>
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

.shoptitle {
	width: 100%;
	text-align: center;
	font-size: 16px;
	font-weight: bold;
}

.textlist {
	height: 30px;
	line-height: 30px;
	width: 98%;
	margin: 0 auto;
	text-overflow: ellipsis;
	overflow: hidden;
	white-space: nowrap;
	font-size: 14px;
}

.clistUl, .clistUl li {
	width: 100%;
	overflow: hidden;
}

.clistUl li div.shoplist {
	width: 98%;
	margin: 8px auto;
	padding: 4px;
	overflow: hidden;
	box-shadow: 0 2px 10px #ccc;
	border-radius: 4px;
	-webkit-border-radius: 4px;
	-ms-border-radius: 4px;
	-o-border-radius: 4px;
	position: relative;
}

.price {
	position: absolute;
	top: 4px;
	right: 6px;
}

.price p {
	color: #fe504f;
	font-weight: bold;
}

.price a {
	display: block;
	width: 60px;
	height: 22px;
	line-height: 22px;
	border-radius: 2px;
	-webkit-border-radius: 2px;
	-ms-border-radius: 2px;
	-o-border-radius: 2px;
	color: #fff;
	background: #fe504f;
	text-align: center;
	font-size: 13px;
	letter-spacing: 4px;
	margin-top: 4px;
}
</style>
</head>
<body>
	<div class="common-show"></div>
	<script src="static/js/jquery.cookie.js"></script>
	<script src="static/lshapp/js/fastclick2.js"></script>
	<div class="contents">
		<!-- <div class="fgheader"><a href="javascript:window.history.go(-1);"><img src="images/Return.png"></a>代理入口</div> -->
		<ul class="clistUl">
			<c:forEach items="${varList}" var="var" varStatus="vs">
				<li>
					<div class="shoplist">
						<div class="price">
							<p>${var.PRICE}</p>
							<span onclick="pay(this)" id="${var.AGENT_ID}"><a>购买</a></span>
						</div>
						<p class="shoptitle">
							<c:if test="${var.STATUS == 0}">会员</c:if>
							<c:if test="${var.STATUS == 1}">经销商</c:if>
							<c:if test="${var.STATUS == 2}">企商VIP</c:if>
						</p>
						<p class="textlist">${var.TITLE}</p>
						<p class="textlist">${var.ITEM_ONE}</p>
						<p class="textlist">${var.ITEM_TWO}</p>
						<p class="textlist">${var.ITEM_THREE}</p>
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
	<script type="text/javascript">
	//测试
	function pay(e) {
		debugger;
		var agentId = e.id;
		var userId = "1";
		var payment = "1";
		var money = "1";
		var type = "1";
		 $.ajax({
        	 type: "post",
        	 url: '<%=basePath%>lshapp/pay/pay.do?token=66c629b5151c4100b56406841a546445',
				data : {
					agentId : agentId,
					userId : userId,
					payment:payment,
					money : money
				},
				dataType : "json",
				success : function(data) {
				}
			})
		}
	</script>
</body>
</html>