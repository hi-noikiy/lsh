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
<html>
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
	    <title>上传资料</title>
	    <link href="static/lshapp/css/center/app.css" rel="stylesheet" type="text/css">
		 <link rel="stylesheet" type="text/css" href="static/lshapp/css/center/weui.css" />
		 <link rel="stylesheet" type="text/css" href="static/lshapp/css/editbirthdaysytle.css" />
	    <script type="text/javascript">var ROOT_URL = '/mobile/';</script>
	    <script src="static/lshapp/js/goods_type.js"></script>
	    <script src="static/lshapp/js/center/app.js"></script>
		<script src="static/lshapp/js/center/zepto.min.js"></script>
		<script src="static/lshapp/js/center/jquery-1.9.1.min.js"></script>
		<script src="static/lshapp/js/jquery.form.js"></script>
		<script src="static/lshapp/js/center/ajaxfileupload.js"></script>

		<script src="static/lshapp/js/layer.js"></script>

		<style>
			.user-head-img-box-1{
				border: none;
				overflow: unset !important;
				text-align: left;
			}
			.p1{
				height: 2rem;
				line-height: 2rem;
				color: #000;
			}
			.p2{
				line-height: 1.8rem;
				color: rgba(0,0,0,.5);
				text-indent: 15px;
			}
			.yz{
				width: 100%;
				overflow: hidden;
			}
			.yz>span{
				float: left;
			}
			.upload{
				float: left;
				margin-left: 10px;
				position: relative;
				text-align: center;
				border: 1px solid #ccc;
				border-radius: 4px;
				-webkit-border-radius: 4px;
				-moz-border-radius: 4px;
				-o-border-radius: 4px;
			}
			.upload>input{
				display: block;
				width: 100%;
				height: 100%;
				position: absolute;
				top: 0;
				left: 0;
			}
			.upload>span{
				font-size: 40px;
			}
			.contentup{
				width: 93%;
				margin: 10px auto;
			}
			.s-user-top{
				float: left;
			}
		</style>
	</head>
		<body style="background:#fff;">
		<p style="text-align:right; display:none;"></p>
		<div id="loading" style="display: none;"><img src="static/lshapp/images/center/loading.gif"></div>

		<jsp:include page="../head_title.jsp">
			<jsp:param value="上传资料" name="pageTitle"/>
		</jsp:include>

		<div class="contentup">
	  		<p class="p1">尊敬的用户，申请为VIP条例：</p>
	  		<p class="p2">1、公司需上传法人身份证+营业执照，个人则需要上传个体经商+店主身份证，资料只用于上传需要，不用于其他用途。</p>
			<div class="yz">
				<span>营业执照：</span>
				<div class="s-user-top" style="background:#fff;margin-bottom:0.8rem;">
					<div class="user_profile_box p-r">
						<div class="dis-box t-goods1 onelist-hidden jian-top"  style="padding-bottom:2.0rem;padding-left:10%;">
							<div class="user-img " style="width: 9.5rem;height: 13.5rem;" onclick="upFileBusinessLicense()">
								<div class="user-head-img-box-1" style="width: 8.5rem;height: 13.5rem;border-radius: 4.5rem;">
								<c:if test="${pd.BUSINESS_LICENSE_PATH != null}">
									<img style="width: 7.5rem;height: 7.5rem;" id="imgBL" src="<%=basePath%>uploadFiles/uploadImgs/${pd.BUSINESS_LICENSE_PATH}"></c:if>
								<c:if test="${pd.BUSINESS_LICENSE_PATH == null}">
									<img id="imgBL" src="static/lshapp/images/business_license.png"></c:if></div>
							</div>
						</div>
						<!-- <div style="font-size: 1.7rem;color:#ABABAB;text-align:center;padding-top:0.1rem;" >点击更换营业执照</div> -->
						<input id="BUSINESS_LICENSE" name="BUSINESS_LICENSE" type="hidden"/>
					</div>
				</div>
			</div>

			<div class="yz">
				<span>身份证：</span>
				<div class="s-user-top" style="background:#fff;margin-bottom:0.8rem;">
					<div class="user_profile_box p-r">
						<div class="dis-box t-goods1 onelist-hidden jian-top"  style="padding-bottom:0.2rem;padding-left:10%;">
							<div class="user-img " style="width: 14.5rem;height: 7.5rem;" onclick="upFileIdentityCard()">
								<div class="user-head-img-box-1" style="width: 14.5rem;height: 7.5rem;border-radius: 4.5rem;">
									<c:if test="${pd.IDENTITY_CARD_PATH != null}"><img style="width: 7.5rem;height: 7.5rem;" id="imgIC" src="<%=basePath%>uploadFiles/uploadImgs/${pd.IDENTITY_CARD_PATH}"></c:if>
									<c:if test="${pd.IDENTITY_CARD_PATH == null}"><img id="imgIC" src="static/lshapp/images/identity_card.png"></c:if>

								</div>
							</div>
						</div>
						<div style="font-size: 1.3rem;color:#ABABAB;text-align:center;padding-top:0.1rem;" >身份证人像一面即可</div>
						<input id="IDENTITY_CARD" name="IDENTITY_CARD" type="hidden"/>
					</div>
				</div>
				<!-- <div class="upload">
					<input type="file">
					<span>+</span>
				</div>	 -->
			</div>

			<!-- <div class="z_mask">
		         弹出框
		         <div class="z_alert">
		             <p ><img src="static/lshapp/images/jiazais.gif"/></p>
		             <p style="color: white;padding-top: 5px;">图片加载中</p>
		         </div>
		     </div> -->
		</div>

		<!-- 营业执照 -->
		<script>
	        $("#file").change(function(){
	    		upPhoto();
	    	});
	        //上传营业执照
	    	function upPhoto(){
	    		$(".z_mask").css("display","block") ;
	    	    var url = "<%=basePath%>lshapp/uploading/businessLicense/saveForUrl.do?token=1";//这里填请求的地址
	    	    $.ajaxFileUpload({
	    	        url : url,
	    	        type : 'POST',
	    	        fileElementId : 'file',  //这里对应html中上传file的id
	    	        contentType:"application/json;charset=UTF-8",
	    	        success: function(data){
	    	            var str = $(data).find("body").text();//获取返回的字符串
	    	            var json = $.parseJSON(str);//把字符串转化为json对象
	    	            if(json.result=='ok'){
	    	            	$("#BUSINESS_LICENSE").val(json.PICTURES_ID);
	    	            	var p=json.BUSINESS_LICENSE_PATH;
	    	            	var path="<%=basePath%>"+"uploadFiles/uploadImgs/"+p;
	    	            	$("#imgBL").attr('src',path);
	    	            	$(".z_mask").css("display","none") ;
	    	                //alert("上传成功"+json.PICTURES_ID);
	    	                //修改用户图片编号
	    	                var BUSINESS_LICENSE = $("#BUSINESS_LICENSE").val();
	    	                $("#file").val("");
	    	            	 $.ajax({
    	            		 	async : false,
    	    			        /* async : false,
    	    			        cache : false, */
    	    			        //cache: false,
    	    			        type : 'POST',
    	    			        url : "<%=basePath%>lshapp/userCenter/update_business_license.do",// 请求的action路径
    	    			        data:{"BUSINESS_LICENSE":BUSINESS_LICENSE},
    	    			        success: function(data){// 请求失败处理函数
    	    			        	if(data=="success"){

    	    			        	}else{
    	    			        		alert("保存失败！");
    	    			        		return false;
    	    			        	}
    	    			        },
    	    			    });
	    	            }else{
	    	                alert("上传失败");
	    	            }
	    	        },
	    	        error: function(){
	    	            alert("请链接网络");
	    	        }
	    	    })
	    	}

	    	//营业执照
    	    function upFileBusinessLicense(){
    	    	var data='';
    	    	window.WebViewJavascriptBridge.callHandler(
    	              'postFile'
    	              ,data
    	              , function(responseData) {
    	            	 //$("#BUSINESS_LICENSE").val(responseData);
    	              	var json = eval('(' + responseData + ')');
    	             	$("#BUSINESS_LICENSE").val(json.PICTURES_ID);
    	              	$("#imgBL").attr('src',json.absoluteUrl);
    	              }
    	 	      );
    	   	}
    	    function showAlert(message){
    			//var message = "";
    			//var json = eval('(' + message + ')');
    	     	$("#BUSINESS_LICENSE").val(message.PICTURES_ID);
    	      	$("#imgBL").attr('src',message.absoluteUrl);
    	    }
		</script>

		<!-- 身份证 -->
		<script>
	        $("#file").change(function(){
	    		upPhoto();
	    	});
	        //上传身份证
	    	function upPhoto(){
	    		$(".z_mask").css("display","block") ;
	    	    var url = "<%=basePath%>lshapp/uploading/identityCard/saveForUrl.do?token=1";//这里填请求的地址
	    	    $.ajaxFileUpload({
	    	        url : url,
	    	        type : 'POST',
	    	        fileElementId : 'file',  //这里对应html中上传file的id
	    	        contentType:"application/json;charset=UTF-8",
	    	        success: function(data){
	    	            var str = $(data).find("body").text();//获取返回的字符串
	    	            var json = $.parseJSON(str);//把字符串转化为json对象
	    	            if(json.result=='ok'){
	    	            	$("#IDENTITY_CARD").val(json.PICTURES_ID);
	    	            	var p=json.IDENTITY_CARD_PATH;
	    	            	var path="<%=basePath%>"+"uploadFiles/uploadImgs/"+p;
	    	            	$("#imgIC").attr('src',path);
	    	            	$(".z_mask").css("display","none") ;
	    	                //alert("上传成功"+json.PICTURES_ID);
	    	                //修改用户图片编号
	    	                var IDENTITY_CARD = $("#IDENTITY_CARD").val();
	    	                $("#file").val("");
	    	            	 $.ajax({
    	            		 	async : false,
    	    			        /* async : false,
    	    			        cache : false, */
    	    			        //cache: false,
    	    			        type : 'POST',
    	    			        url : "<%=basePath%>lshapp/userCenter/update_identity_card.do",// 请求的action路径
    	    			        data:{"IDENTITY_CARD":IDENTITY_CARD},
    	    			        success: function(data){// 请求失败处理函数
    	    			        	if(data=="success"){

    	    			        	}else{
    	    			        		alert("保存失败！");
    	    			        		return false;
    	    			        	}
    	    			        },
    	    			    });
	    	            }else{
	    	                alert("上传失败");
	    	            }
	    	        },
	    	        error: function(){
	    	            alert("请链接网络");
	    	        }
	    	    })
	    	}

	    	//身份证
    	    function upFileIdentityCard(){
    	    	var data='';
    	    	window.WebViewJavascriptBridge.callHandler(
    	              'postFile'
    	              ,data
    	              , function(responseData) {
    	            	 //$("#IDENTITY_CARD").val(responseData);
    	              	var json = eval('(' + responseData + ')');
    	             	$("#IDENTITY_CARD").val(json.PICTURES_ID);
    	              	$("#imgIC").attr('src',json.absoluteUrl);
    	              }
    	 	      );
    	   	}
    	    function showAlert(message){
    			//var message = "";
    			//var json = eval('(' + message + ')');
    	     	$("#IDENTITY_CARD").val(message.PICTURES_ID);
    	      	$("#imgIC").attr('src',message.absoluteUrl);
    	    }
		</script>

		<script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	    <script src="https://res.wx.qq.com/open/libs/weuijs/1.0.0/weui.min.js"></script>
	</body>
</html>