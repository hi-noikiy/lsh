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
		<!-- 引入支付样式 -->
		<link rel="stylesheet" type="text/css" href="static/lshapp/css/shoujisc.css">
		<script src="static/lshapp/js/layer.js"></script>
		
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
			<div>
				<input type="hidden" id="USER_ID" value="${pdUser.USER_ID}">
			</div>
			<div class="radiobox">
				<ul>
					<c:forEach items="${varList}" var="var" varStatus="vs">
						<li><span id="${var.BEAN_ID}">${var.BEAN_NUMBER}</br>${var.PRICE}元
						</span></li>
					</c:forEach>
				</ul>
			</div>
			<!-- <div class="logibtn">
				<span onclick="recharge(this)">充值</span>
			</div> -->
			<div class="mpaybox nextstep" style="margin: 42px auto;"onclick="recharge(this)">充值</div>
			
			<form name="theForm" id="theForm" >
				<div class="mcbtmdiv">
					<div class="mctop">
						<!-- <i onclick="javascript:window.history.go(-1);">×</i> -->
						<i onclick="closebox()">×</i>
						<p>确认付款</p>
					</div>
					<div class="mcMiddle">
						<ul>
							<li>
								<p class='talmoney'>￥${wlorder.paytotalfee}</p>
							</li>
						    <li><img src="static/lshapp/images/payment_icon_Alipay.png"> <span>支付宝支付</span>
								<input class="paystyle1" type="radio" name="payradio" id="1" checked></li>
							<li style="border-bottom: 1px solid #eee;"><img
								src="static/lshapp/images/payment_icon_WeChat.png"> <span>微信支付</span> 
								<!-- <input class="paystyle2" type="radio" name="payradio" id="2" disabled> -->
								<input class="paystyle2" type="radio" name="payradio" id="2" ></li> 
						</ul>
					</div>
					<div class="mpaybox nextstep" style="margin: 42px auto;"
						onclick="javascript:immediatePayment();">立即付款</div>
				</div>
			</form>
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
				//debugger;
				var beanId = e.id;//金豆id
				var userId = $('#USER_ID').val(); //用户id
				var type = "1";//支付方式（0.微信，1.支付宝）
				var agentPrice = "0.01"; //充值金额
				 $.ajax({
		        	 type: "post",
		        	 url: '<%=basePath%>lshapp/pay/buyGold.do',
						data : {
							beanId : beanId,
							userId : userId,
							payment : type,
							totalAmount : agentPrice
						},
						dataType : "json",
						success : function(data) {
							orderPay(data); // 唤起原生支付
						}
				})
			}
			
			function orderPay(data) {
	           //调用本地java方法
	           //第一个参数是 调用java的函数名字 第二个参数是要传递的数据 第三个参数js在被回调后具体执行方法，responseData为java层回传数据
				window.WebViewJavascriptBridge.send(
		               data
		               , function(responseData) {
		            	   var obj = JSON.parse(responseData);
		            	   if(obj.type=="success" || obj.type=="fail"){
		            		   if(obj.orderId !=''){
		            			   //进入代理购买记录
		            			   window.location.replace("<%=basePath%>lshapp/purchase/agentPurchase.do");
		            			   <%-- window.location.replace("<%=basePath%>lshapp/order/goOrderDetail.do?orderId="+obj.orderId); --%>
		            		   }
		            	   }else if(obj.type=="cancel" || obj.type=="exception"){
		            		   	window.location.replace("<%=basePath%>lshapp/purchase/agentPurchase");
		            		   	<%-- window.location.replace("<%=basePath%>lshapp/order/goOrderDetail.do?orderId="+obj.orderId); --%>
		            	   }
		               }
		           );
		       }
			
			//提示用户是否离开收银台
			function closebox(){
			   layer.open({
			      content: '确定要离开收银台',
			      btn:['确定','继续支付'],
			      yes:function(index){
			     	 window.location.href = document.referrer;//返回上一页并刷新 
			      }
			   });
			}
		</script>
	</body>
</html>