<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- 下拉框 -->
	<link rel="stylesheet" href="static/ace/css/chosen.css" />
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>
	<!-- 日期框 -->
	<link rel="stylesheet" href="static/ace/css/datepicker.css" />
</head>
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
					
					<form action="goldbeans/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="GOLDBEANS_ID" id="GOLDBEANS_ID" value="${pd.GOLDBEANS_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">支付id:</td>
								<td><input type="text" name="APPLY_ID" id="APPLY_ID" value="${pd.APPLY_ID}" maxlength="255" placeholder="这里输入支付id" title="支付id" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">支付流水号:</td>
								<td><input type="text" name="APPLY_ORDER" id="APPLY_ORDER" value="${pd.APPLY_ORDER}" maxlength="100" placeholder="这里输入支付流水号" title="支付流水号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">用户编号:</td>
								<td><input type="number" name="USER_ID" id="USER_ID" value="${pd.USER_ID}" maxlength="32" placeholder="这里输入用户编号" title="用户编号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">状态:</td>
								<td><input type="text" name="STATUS" id="STATUS" value="${pd.STATUS}" maxlength="50" placeholder="这里输入状态" title="状态" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">总数:</td>
								<td><input type="number" name="GLODBEANS" id="GLODBEANS" value="${pd.GLODBEANS}" maxlength="32" placeholder="这里输入总数" title="总数" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">修改数:</td>
								<td><input type="text" name="MODIFY_GLODBEANS" id="MODIFY_GLODBEANS" value="${pd.MODIFY_GLODBEANS}" maxlength="255" placeholder="这里输入修改数" title="修改数" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">交易类型:</td>
								<td><input type="text" name="TYPE" id="TYPE" value="${pd.TYPE}" maxlength="20" placeholder="这里输入交易类型" title="交易类型" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr>
						</table>
						</div>
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
					</form>
					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.page-content -->
		</div>
	</div>
	<!-- /.main-content -->
</div>
<!-- /.main-container -->


	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
			if($("#APPLY_ID").val()==""){
				$("#APPLY_ID").tips({
					side:3,
		            msg:'请输入支付id',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#APPLY_ID").focus();
			return false;
			}
			if($("#APPLY_ORDER").val()==""){
				$("#APPLY_ORDER").tips({
					side:3,
		            msg:'请输入支付流水号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#APPLY_ORDER").focus();
			return false;
			}
			if($("#USER_ID").val()==""){
				$("#USER_ID").tips({
					side:3,
		            msg:'请输入用户编号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#USER_ID").focus();
			return false;
			}
			if($("#STATUS").val()==""){
				$("#STATUS").tips({
					side:3,
		            msg:'请输入状态',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STATUS").focus();
			return false;
			}
			if($("#GLODBEANS").val()==""){
				$("#GLODBEANS").tips({
					side:3,
		            msg:'请输入总数',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#GLODBEANS").focus();
			return false;
			}
			if($("#MODIFY_GLODBEANS").val()==""){
				$("#MODIFY_GLODBEANS").tips({
					side:3,
		            msg:'请输入修改数',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#MODIFY_GLODBEANS").focus();
			return false;
			}
			if($("#TYPE").val()==""){
				$("#TYPE").tips({
					side:3,
		            msg:'请输入交易类型',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TYPE").focus();
			return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>