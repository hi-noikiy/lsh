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
	    <title>转让金豆</title>
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
			html,body{
				width: 100%;
				height: 100%;
			}
			.bgdiv{
				width: 100%;
				height: 100%;
				background: url('static/lshapp/images/erweimabg.jpg') no-repeat 0 0;
				background-size: 100% 100%;
				padding-top: 100px;
			}
			.erweimaimg{
				width: 75%;
				background: #fff;
				margin: 0 auto;
				padding:10px;
				border-radius: 10px;
			}
			.erweimaimg img{
				display: block;
				width: 100%;
			}
		</style>
	</head>
		<body style="background:#f5f5f5;">
		<p style="text-align:right; display:none;"></p>
		<div id="loading" style="display: none;"><img src="static/lshapp/images/center/loading.gif"></div>

		<jsp:include page="head_title.jsp">
			<jsp:param value="转让金豆" name="pageTitle"/>
		</jsp:include>

		<div class="bgdiv">
			<div class="erweimaimg">
				<img src="static/lshapp/images/erweima.png">
			</div>
		</div>


		<script>

		</script>

		<script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	    <script src="https://res.wx.qq.com/open/libs/weuijs/1.0.0/weui.min.js"></script>
	</body>
</html>