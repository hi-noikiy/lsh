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

					<form action="award/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="AWARD_ID" id="AWARD_ID" value="${pd.AWARD_ID}"/>
						<input type="hidden" name="PICTURE_ID" id="PICTURE_ID" value="${pd.PICTURE_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">请选择奖品类型:</td>
								<td>
									<select class="chosen-select form-control" name="TYPE" id="TYPE" data-placeholder="请选择商品类目" style="vertical-align:top;width: 220px;" onchange="xuanze(this)">
											<option value="0" <c:if test="${pd.TYPE=='0'}">selected="selected"</c:if>>商品</option>
											<option value="1" <c:if test="${pd.TYPE=='1'}">selected="selected"</c:if>>非商品</option>
									  </select>
								</td>
							</tr>
								<tr id="award1" style="display:
									<c:if test="${pd.TYPE=='1'}">show</c:if>
									<c:if test="${pd.TYPE=='0'}">none</c:if>
								">
									<td style="width:75px;text-align: right;padding-top: 13px;">奖品名称:</td>
									<td><input type="number" name="AWARD_NAME" id="AWARD_NAME" value="${pd.AWARD_NAME}" maxlength="255" placeholder="这里输入商品名称" title="商品名称" style="width:78%;"/>礼豆</td>
								</tr>
								<tr id="award2" style="display:
									<c:if test="${pd.TYPE=='1'}">show</c:if>
									<c:if test="${pd.TYPE=='0'}">none</c:if>
								">
									<td style="width:75px;text-align: right;padding-top: 13px;">奖品小图:</td>
									<td>
										<input  type="file" name="file" id="file" value="${pd.PICTURE_ID}" maxlength="255" placeholder="这里输入类目图片" title="类目图片" style="width:50%;"/>
										<span style="color: red">建议上传图片大小为120x120像素，格式为jpg或png格式</span>
									</td>
								</tr>

								<tr id="award3" style="display:
									<c:if test="${pd.TYPE=='0'}">show</c:if>
									<c:if test="${pd.TYPE=='1'}">none</c:if>
								 ">
									<td style="width:75px;text-align: right;padding-top: 13px;">情选择商品:</td>
									<td>
										<select class="chosen-select form-control" name="GOODS_ID" id="GOODS_ID" data-placeholder="请选择商品类目" style="vertical-align:top;width: 220px;">
											<option value="">请选择商品</option>
											<c:forEach items="${goodsList}" var="var" varStatus="vs">
												<option value="${var.GOODS_ID}" <c:if test="${pd.GOODS_ID==var.GOODS_ID}">selected="selected"</c:if>>${var.GOODS_NAME}</option>
											</c:forEach>
										  </select>
									</td>
								</tr>

							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">中奖概率:</td>
								<td><input type="number" name="CHANCE" id="CHANCE" value="${pd.CHANCE}" maxlength="32" placeholder="这里输入中奖概率" title="中奖概率" style="width:98%;"/></td>
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
	<script type="text/javascript" src="static/js/ajaxfileupload.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
		$("#file").change(function(){
			upPhoto();
			});
		function upPhoto(){
		    var url = "<%=basePath%>pictures/saveForUrl.do";//这里填请求的地址
		    $.ajaxFileUpload({
		        url : url,
		        type : 'POST',
		        fileElementId : 'file',  //这里对应html中上传file的id
		        contentType:"application/json;charset=UTF-8",
		        success: function(data){
		            var str = $(data).find("body").text();//获取返回的字符串
		            var json = $.parseJSON(str);//把字符串转化为json对象
		            if(json.result=='ok'){
		            	$("#PICTURE_ID").val(json.PICTURES_ID);
		                //alert("上传成功"+json.PICTURES_ID);
		            }
		            else{
		                alert("上传失败");
		            }
		        },
		        error: function(){
		            alert("请链接网络");
		        }
		    })
		}
		function xuanze(obj){
			var val=$("#TYPE").val();
			if(parseInt(val)==1){/*
				document.getElementById("award3").style.display="none";
				document.getElementById("award1").style.display="show";
				document.getElementById("award2").style.display="show"; */
				$("#award3").hide();
				$("#award1").show();
				$("#award2").show();
			}else{/*
				document.getElementById("award1").style.display="none";
				document.getElementById("award2").style.display="none";
				document.getElementById("award3").style.display="show"; */
				$("#award1").hide();
				$("#award2").hide();
				$("#award3").show();
			}
		}
		</script>
		<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){

			if($("#TYPE").val()==""){
				$("#TYPE").tips({
					side:3,
		            msg:'请输入奖品类型',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TYPE").focus();
			return false;
			}
			if(parseInt($("#TYPE").val())==1){
				if($("#PICTURE_ID").val()==""){
					$("#PICTURE_ID").tips({
						side:3,
			            msg:'请输入图片编号',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#PICTURE_ID").focus();
				return false;
				}
				if($("#AWARD_NAME").val()==""){
					$("#AWARD_NAME").tips({
						side:3,
			            msg:'请输入奖品名称',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#AWARD_NAME").focus();
				return false;
				}
			}else{
				if($("#GOODS_ID").val()==""){
					$("#GOODS_ID").tips({
						side:3,
			            msg:'请输入商品编号',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#GOODS_ID").focus();
				return false;
				}
			}



			if($("#CHANCE").val()==""){
				$("#CHANCE").tips({
					side:3,
		            msg:'请输入中奖概率',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CHANCE").focus();
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