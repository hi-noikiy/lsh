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
		
		<!-- 引入支付样式 -->
		<link rel="stylesheet" type="text/css" href="static/lshapp/css/shoujisc.css">
		<script src="static/lshapp/js/layer.js"></script>
		
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
				background-image: url(static/lshapp/images/beijing.png);
				background-repeat:   no-repeat;
				background-position: 0 0 ;
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
			<div>
				<input type="hidden" id="USER_ID" value="${pdUser.USER_ID}">
			</div>
			<ul class="clistUl">
				<c:forEach items="${varList}" var="var" varStatus="vs">
					<li>
						<div class="shoplist">
							<div class="price">
								<p>${var.PRICE}</p>
								<span onclick="agentBuying(this)" id="${var.AGENT_ID}"><a>购买</a></span>
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
				<%-- <div class="con">
					<div class="flow-checkout">
						<input type="hidden" name="ids" id="ids" value="${pd.ids}"/>
						<input type="hidden" name="ADDRESS_ID" id="ADDRESS_ID" value="${address.ADDRESS_ID}"/>
						<section class="m-top10">
							<ul>
								<li class="dis-box padding-all-n m-top1px b-color-f ">
									<label class="t-remark g-t-temark" style="color: #000;font-size: 1.6rem;">请选择支付方式</label>
								</li>
								<li class="dis-box padding-all-n m-top1px b-color-f " style="display: block;" onclick="selectPayment('1');">
									<img class="pay_image" src="static/lshapp/images/alipay.png">
									<label class="t-remark">支付宝支付</label>
									<img id="ali_img" class="select_image" src="static/lshapp/images/select2.png">
								</li>
								<li class="dis-box padding-all-n m-top1px b-color-f " style="display: block;"  onclick="selectPayment('2');">
									<img class="pay_image" src="static/lshapp/images/wx.png">
									<label class="t-remark">微信支付</label>
									<img id="wx_img" class="select_image hidden_pay" src="static/lshapp/images/select2.png">
								</li>
								<!--支付方式 (默认支付宝支付)-->
								 <input id="payment" name="payment" value="alipay" type="hidden">
							</ul>
						</section>
					</div>
				</div> --%>
		
				<div class="mask-filter-div"></div>
				<!--悬浮btn star-->
				<%-- <section class="filter-btn f-checkout-filter-btn dis-box pl">
					<input name="user_id" value="" type="hidden"> <input
						id="store_id" name="store_id" value="0" type="hidden"> <span
						class="box-flex t-remark">实付款 <em class="t-first" id="amount">¥${pd.real_pay_amount }</em></span>
					<div>
						<div  type="button" class="btn-submit"
							onclick="submitOrder();">提交订单</div>
					</div>
				</section> --%>
			</form>
		</div>
		<script type="text/javascript">
			// fastclick.js组件的引用
			$(function() {
				FastClick.attach(document.body);
			})
		</script>
		<script type="text/javascript">
			function selectPayment(e){ // 选择支付方式
				if(e=="1"){ // 支付宝支付
					$("#payment").val("alipay");
					$("#ali_img").removeClass("hidden_pay");
					$("#wx_img").addClass("hidden_pay");
				}else if(e=="2"){ // 微信支付
					$("#payment").val("wxpay");
					$("#ali_img").addClass("hidden_pay");
					$("#wx_img").removeClass("hidden_pay");
				}
			}
		
			//代理购买
			function agentBuying(e) {
				debugger;
				var agentId = e.id;//代理ID
				var userId = $('#USER_ID').val(); //用户id
				//var agentStatus = "1"; //代理状态（0.会员，1.经销商，2.企商VIP）
				var agentPrice = "0.01"; //购买金额
				var type = "1"; //支付方式（0.微信，1.支付宝）
				$.ajax({
		        	type: "post",
		        	url: '<%=basePath%>lshapp/pay/buyGold.do',
					data : {
						agentId : agentId,
						userId : userId,
						/* agentStatus : agentStatus, */
						payment : type,
						totalAmount : agentPrice
					},
					dataType : "json",
					success : function(data) {
						if(data.status =="0"){ // 成功返回支付信息
		            		orderPay(data); // 唤起原生支付
		                }
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