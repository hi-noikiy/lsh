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

							<form action="agent/${msg }.do" name="Form" id="Form"
								method="post">
								<input type="hidden" name="AGENT_ID" id="AGENT_ID"
									value="${pd.AGENT_ID}" />
								<div id="zhongxin" style="padding-top: 13px;">
									<table id="table_report"
										class="table table-striped table-bordered table-hover">
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">售价:</td>
											<td><input type="number" name="PRICE" id="PRICE"
												value="${pd.PRICE}" maxlength="32" placeholder="这里输入售价"
												title="售价" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">标题:</td>
											<td><input type="text" name="TITLE" id="TITLE"
												value="${pd.TITLE}" maxlength="20" placeholder="这里输入标题"
												title="标题" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">内容:</td>
											<td><input type="text" name="ITEM_ONE" id="ITEM_ONE"
												value="${pd.ITEM_ONE}" maxlength="21845"
												placeholder="这里输入内容" title="内容" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">内容:</td>
											<td><input type="text" name="ITEM_TWO" id="ITEM_TWO"
												value="${pd.ITEM_TWO}" maxlength="21845"
												placeholder="这里输入内容" title="内容" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">内容:</td>
											<td><input type="text" name="ITEM_THREE" id="ITEM_THREE"
												value="${pd.ITEM_THREE}" maxlength="21845"
												placeholder="这里输入内容" title="内容" style="width: 98%;" /></td>
										</tr>
										<tr>
											<td
												style="width: 75px; text-align: right; padding-top: 13px;">状态:</td>
											
											<td><input style="margin-left: 1%;" type='radio'
												id='STATUS' name='STATUS' value="0"
												<c:if test="${pd.STATUS=='0'}">checked="checked"</c:if> />&nbsp;&nbsp;会员
											</td>
											<td><input style="margin-left: 1%;" type='radio'
												id='STATUS' name='STATUS' value="1"
												<c:if test="${pd.STATUS=='1'}">checked="checked"</c:if> />&nbsp;&nbsp;经销商
											</td>
											<td><input style="margin-left: 1%;" type='radio'
												id='STATUS' name='STATUS' value="2"
												<c:if test="${pd.STATUS=='2'}">checked="checked"</c:if> />&nbsp;&nbsp;企商VIP
											</td>

										</tr>
										<tr>
											<td style="text-align: center;" colspan="10"><a
												class="btn btn-mini btn-primary" onclick="save();">保存</a> <a
												class="btn btn-mini btn-danger"
												onclick="top.Dialog.close();">取消</a></td>
										</tr>
									</table>
								</div>
								<div id="zhongxin2" class="center" style="display: none">
									<br /> <br /> <br /> <br /> <br /> <img
										src="static/images/jiazai.gif" /><br />
									<h4 class="lighter block green">提交中...</h4>
								</div>
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
		function save() {
			if ($("#PRICE").val() == "") {
				$("#PRICE").tips({
					side : 3,
					msg : '请输入售价',
					bg : '#AE81FF',
					time : 2
				});
				$("#PRICE").focus();
				return false;
			}
			if ($("#TITLE").val() == "") {
				$("#TITLE").tips({
					side : 3,
					msg : '请输入标题',
					bg : '#AE81FF',
					time : 2
				});
				$("#TITLE").focus();
				return false;
			}
			if ($("#ITEM_ONE").val() == "") {
				$("#ITEM_ONE").tips({
					side : 3,
					msg : '请输入内容',
					bg : '#AE81FF',
					time : 2
				});
				$("#ITEM_ONE").focus();
				return false;
			}
			if ($("#ITEM_TWO").val() == "") {
				$("#ITEM_TWO").tips({
					side : 3,
					msg : '请输入内容',
					bg : '#AE81FF',
					time : 2
				});
				$("#ITEM_TWO").focus();
				return false;
			}
			if ($("#ITEM_THREE").val() == "") {
				$("#ITEM_THREE").tips({
					side : 3,
					msg : '请输入内容',
					bg : '#AE81FF',
					time : 2
				});
				$("#ITEM_THREE").focus();
				return false;
			}
			if ($("#STATUS").val() == "") {
				$("#STATUS").tips({
					side : 3,
					msg : '请输入状态（0.会员，1.经销商，3.企商VIP）',
					bg : '#AE81FF',
					time : 2
				});
				$("#STATUS").focus();
				return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}

		$(function() {
			//日期框
			$('.date-picker').datepicker({
				autoclose : true,
				todayHighlight : true
			});
		});
	</script>
</body>
</html>