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
	    <title>我的推荐</title>
	    <link href="static/lshapp/css/center/app.css" rel="stylesheet" type="text/css">
	    <link rel="stylesheet" href="static/lshapp/css/center/common3.css"/>
	    <script type="text/javascript">var ROOT_URL = '/mobile/';</script>
	    <script src="static/lshapp/js/center/app.js"></script>
		<script src="static/lshapp/js/center/jquery-1.9.1.min.js"></script>
		<script src="static/lshapp/js/center/ajaxfileupload.js"></script>
		<script src="static/lshapp/js/center/jquery.qrcode.min.js"></script>
		<style type="text/css">
			.return_image{
				width:1.2rem;
				height:1.8rem;
			}
		</style>
	</head>

	<body style="background:url(static/lshapp/images/erweimabg.jpg) no-repeat; background-size: cover;width: 100%; ">
		<p style="text-align:right; display:none;"></p>

		<%-- <jsp:include page="head_title.jsp">
			<jsp:param value="转让金豆" name="pageTitle"/>
		</jsp:include> --%>

		<div id="loading" style="display: none;"><img src="static/lshapp/images/center/loading.gif"></div>

		<div class="con" style="width: 100%;max-height: 300px;">
		   <div class="affiliate-cont-box" style="background-color: white;position:relative;max-width: 100%;max-height: 100%; ">
				    <div id="dialog" style="display: block;position:fixed; bottom:290px;width: 272px;height: 272px;padding: 24px;padding-bottom:5px;padding-right: 22px;text-align: center;left: 43%;border-radius: 9px;">

				    </div>

					 <%-- <div  style="position:fixed; bottom:70px;text-align:center;width: 100%;">
							<h2 style="color: white;font-weight: 400;">邀请码: &nbsp; ${pd.INVITE_CODE}</h2>
					   </div> --%>
		   </div>
		   <!-- <div  style="position:fixed; bottom:140px;text-align:center;width: 100%;z-index:10000;">
				<h2 style="color: red;font-size: 1.6rem;">浏览器扫一扫，有惊喜</h2>
		   </div> -->
		   <div id="qrcode"></div>
		</div>

		<!--快捷导航-->


		<div class="common-show"></div>
		<script>
			jQuery('#dialog').qrcode({width: 225,height: 225,text: "${url}"});
		</script>
		<script src="static/lshapp/js/center/qrcode.js"></script>

	</body>
</html>