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
		<title>金豆充值</title>
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
			<!--<div class="fgheader"><a href="javascript:window.history.go(-1);"><img src="images/Return.png"></a>充值</div>-->
			<div class="radiobox">
				<ul>
					<c:forEach items="${varList}" var="var" varStatus="vs">
						<li><span id="${var.BEAN_ID}">${var.BEAN_NUMBER}</br>${var.PRICE}元
						</span></li>
					</c:forEach>
				</ul>
			</div>
			<div class="logibtn">
				<span onclick="recharge(this)">充值</span>
			</div>
		</div>
	
		<script type="text/javascript">
			$('.radiobox>ul>li').eq(0).find('span').addClass('bgColor');
			$('.radiobox>ul>li').click(
					function() {
						$(this).find('span').addClass('bgColor').parent()
								.siblings().find('span').removeClass('bgColor');
					})
			// fastclick.js组件的引用
			$(function() {
				FastClick.attach(document.body);
			})
			
			//金豆充值
			function recharge(e) {
				debugger;
				var beanId = e.id;
				var userId = "1"; //用户id
				var type = "1";//支付方式（0.微信，1.支付宝）
				var agentPrice = "0.01"; //充值金额
				 $.ajax({
		        	 type: "post",
		        	 url: '<%=basePath%>lshapp/pay/buyGold.do?token=92fc5f1ce68d4f42a68b85c0a1c2e353',
						data : {
							beanId : beanId,
							userId : userId,
							payment : type,
							totalAmount : agentPrice
						},
						dataType : "json",
						success : function(data) {
						}
					})
				}
		</script>
	</body>
</html>