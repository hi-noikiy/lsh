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
		<link rel="stylesheet" type="text/css" href="static/lshapp/css/shoujisc.css">
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
				background-image: url(static/lshapp/images/beijing.png);
				background-repeat:   no-repeat;
				background-position: 0 0 ;
			}
			.zhidao{
		    z-index:300;
		    }
		    .hidden_pay{
	          display:none;
            }

			body {
				background: #fff;
			}
			.zhc{
	          width: 100%;
	          height: 100%;
	          position:absolute;
	          left:0;
	           top:0;
	           background: rgba(0,0,0,0.5);
				z-index:1;
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
						<li><span id="${var.BEAN_ID}" name="${var.PRICE}">${var.BEAN_NUMBER}</br>${var.PRICE}元
						</span></li>
					</c:forEach>
				</ul>
			</div>
			<div class="logibtn">
				<span onclick="recharge(this)">充值</span>
			</div>
		</div>
	    <div id="zhezao" class="zhc" style="display: none;"></div>
		 <!-- <div class="zhidao" id="fasnhu" style="display: none;background-color: white; position:fixed;width: 100%;bottom:0.1px;">
				 <ul style="z-index:300;">
					<li style="width: 100%;height: 40px;left: 10px;">
						<label  style="color: #000;font-size: 0.3rem;">请选择支付方式
						<img class="pay_image" style="width: 20px;height: 20px;right: 10px;position:absolute;" src="static/lshapp/images/Close-hdpi.png" onclick="closeN()">
						</label>

					</li>
					<li  style="display: block;height: 40px;left: 10px;" onclick="selectPayment('1');">
						<img class="pay_image" style="width: 20px;height: 20px;" src="static/lshapp/images/alipay.png">
						<label class="t-remark" style="font-size: 0.3rem;">支付宝支付</label>
						 <img id="ali_img" style="width: 20px;height: 20px;" class="select_image hidden_pay" src="static/lshapp/images/select2.png">
					</li>
					<li  style="display: block;height: 40px;left: 10px;"  onclick="selectPayment('0');">
						<img class="pay_image" style="width: 20px;height: 20px;" src="static/lshapp/images/wx.png">
						<label class="t-remark" style="font-size: 0.3rem;">微信支付</label>
						 <img id="wx_img" style="width: 20px;height: 20px;" class="select_image hidden_pay" src="static/lshapp/images/select2.png">
					</li>
					//支付方式 (默认支付宝支付)
					 <input id="payment" name="payment" value="1" type="hidden">
					 <input id="test" name="test" value="" type="hidden">
					 <input id="www" name="www" value="" type="hidden">
				</ul>

		</div> -->
		<div class="mcbtmdiv" id="fasnhu" style="display: none;z-index:300;">
					<div class="mctop">
						<!-- <i onclick="javascript:window.history.go(-1);">×</i> -->
						<i onclick="closeN()">×</i>
						<p>确认付款</p >
					</div>
					<div class="mcMiddle">
						<ul>
							<li>
								<p class='talmoney'></p >
							</li>
						    <li><img src="static/lshapp/images/payment_icon_Alipay.png"> <span>支付宝支付</span>
								<input class="paystyle1" type="radio" name="payradio" id="1" value="1" checked></li>
							<li style="border-bottom: 1px solid #eee;"><img
								src="static/lshapp/images/payment_icon_WeChat.png"> <span>微信支付</span>
								<!-- <input class="paystyle2" type="radio" name="payradio" id="2" disabled> -->
								<input class="paystyle2" type="radio" name="payradio" id="2" value="0" ></li>
						</ul>
					</div>
					<div class="mpaybox nextstep" style="margin: 42px auto;"
						onclick="javascript:immediatePayment();">立即付款</div>
						 <input id="payment" name="payment" value="1" type="hidden">
					 <input id="test" name="test" value="" type="hidden">
					 <input id="money" name="money" value="" type="hidden">
					  <input id="www" name="www" value="" type="hidden">
					  <input id="wwww" name="wwww" value="" type="hidden">
				</div>
		<script type="text/javascript">
			$('.radiobox>ul>li').eq(0).find('span').addClass('bgColor');
			$("#www").val($('.radiobox>ul>li').eq(0).find('span').attr("id"));
			$("#wwww").val($('.radiobox>ul>li').eq(0).find('span').attr("name"));
			$('.radiobox>ul>li').click(
					function() {
						$(this).find('span').addClass('bgColor').parent()
								.siblings().find('span').removeClass('bgColor');
						$("#www").val($(this).find('span').attr("id"));
						$("#wwww").val($(this).find('span').attr("name"));
					})
			// fastclick.js组件的引用
			$(function() {
				FastClick.attach(document.body);
			})

			//金豆充值
			<%-- function recharge(e) {
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
				} --%>
				/* function selectPayment(e){ // 选择支付方式
					if(e=="1"){ // 支付宝支付
						$("#payment").val("1");
						$("#ali_img").removeClass("hidden_pay");
						$("#wx_img").addClass("hidden_pay");
						var t=$("#www").val();
						pay1(t);
					}else if(e=="0"){ // 微信支付
						$("#payment").val("0");
						$("#ali_img").addClass("hidden_pay");
						$("#wx_img").removeClass("hidden_pay");
						var t=$("#www").val();
						pay1(t);
					}
				} */
				function recharge(e) {
					document.getElementById("fasnhu").style.display = "block";
					document.getElementById("zhezao").style.display = "block";
					$(".talmoney").html("￥"+$("#wwww").val());
				}
				//代理购买
				function immediatePayment() {
					var beanId = $("#www").val();
					//var agentStatus = "1"; //代理状态（0.会员，1.经销商，2.企商VIP）
					var type =$('input:radio:checked').val();; //支付方式（0.微信，1.支付宝）
					$.ajax({
			            type: "POST",
			           <%--  url: '<%=basePath%>lshapp/pay/buyGold.do', --%>
			           url: '<%=basePath%>lshapp/order/createOrderNew.do',
						data: {"beanId":beanId,"payment":type},
						dataType: "json",
			            success: function (data) {
			            	if(data.status =="0"){ // 成功返回支付信息
			            		orderPay(data); // 唤起原生支付
			                }
			            }
			        });
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
				            			   window.location.replace("<%=basePath%>lshapp/userCenter/center_index.do");
				            		   }
				            	   }else if(obj.type=="cancel" || obj.type=="exception"){
				            		   	   window.location.replace("<%=basePath%>lshapp/userCenter/center_index.do");
				            	   }

				               }
				           );
			       }
			       //注册事件监听
			       function connectWebViewJavascriptBridge(callback) {
			           if (window.WebViewJavascriptBridge) {
			               callback(WebViewJavascriptBridge)
			           } else {
			               document.addEventListener(
			                   'WebViewJavascriptBridgeReady'
			                   , function() {
			                       callback(WebViewJavascriptBridge)
			                   },
			                   false
			               );
			           }
			       }
			       //注册回调函数，第一次连接时调用 初始化函数
			       connectWebViewJavascriptBridge(function(bridge) {
			           bridge.init(function(message, responseCallback) {
			               var responseData = '默认接收收到来自Java的数据，回传数据给你';
			               responseCallback(responseData);
			           });

			           bridge.registerHandler("functionInJs", function(data, responseCallback) {
			               var responseData = '指定接收收到来自Java的数据，回传数据给你';
			               responseCallback(responseData);
			           });
			       });
			       function closeN(){
			    	   document.getElementById("fasnhu").style.display = "none";
						document.getElementById("zhezao").style.display = "none";
			       }
		</script>
	</body>
</html>