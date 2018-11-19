<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
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
<title>商品详情</title>
<base href="<%=basePath%>">
<link href="static/lshapp/css/goods_type.css" rel="stylesheet"
	type="text/css">
<script type="text/javascript">
	var ROOT_URL = '/mobile/';
</script>
<script src="static/lshapp/js/goods_type.js"></script>
<style type="text/css">
.jt_image {
	width: 1.3rem;
	height: 1.3rem;
	position: absolute;
	left: .2rem;
	top: 36.6%;
}
.customer{
	width:1.8rem;
	height:1.8rem;
	margin-right:0.3rem;
	margin-top: -0.2rem;
}
.remark{
	width: 4rem;
	height: 4rem;
	margin-right:0.2rem;
}
.close_image{
	border:none;
	 position: absolute;
	 right: 0;
	 width: 2.3rem;
	 height: 2.3rem;
	 margin-top: -0.3rem;
	}
.close_images{
	border:none;
	 right: 0;
	 width: 2.3rem;
	 height: 2.3rem;
	 margin-top: -0.3rem;
	}
.collection{
	width:1.8rem;
	height:1.8rem;
	display: block;
	margin: 0.2rem auto 0 auto;
}
.right_arrow{
	width: 0.8rem;
	height: 1.3rem;
	margin-top: -0.2rem;
	margin-left: 0.2rem;
}
.change_background{
	background: #fff;
}
.navigat_image{
	width: 2rem;
	height: 2rem;
}
</style>
</head>
<body>
	<p style="text-align:right; display:none;"></p>
	<div id="loading" style="display: none;">
		<img src="/mobile/public/img/loading.gif">
		<input id="judgeA" name="judgeA" value="" type="hidden"/>
		<input id="U_ID" name="U_ID" value="${pd.USER_ID}" type="hidden"/>
	</div>

	<div class="con goods" id="checkPage" style="display: block;">
		<div id="change_goods" style="position: relative;top: -3.9rem;">
		<form name="ECS_FORMBUY" id="ECS_FORMBUY" method="post">

				<div
					class="goods-photo j-show-goods-img goods-banner j-goods-box swiper-container-horizontal swiper-container-android" >
					<span class="goods-num" id="goods-num"><span
						id="g-active-num">1</span>/<span id="g-all-num">2</span></span>
					<div class="swiper-wrapper">
						<c:forEach items="${pictureList}" var="var" varStatus="vs">
							<li class="swiper-slide tb-lr-center swiper-slide-active"
								style="width: 400px;"><img
								src="<%=basePath%>uploadFiles/uploadImgs/${var.PATH}" alt="">
							</li>
						</c:forEach>
					</div>
					<!-- Add Pagination -->
					<div class="swiper-pagination"></div>
				</div>

				<section class="goods-title b-color-f padding-all ">
					<h3>${goods.GOODS_NAME }</h3>

				</section>

				<section class="goods-price padding-all b-color-f">
					<p class="p-price">
						<span class="t-first" id="ECS_SHOPPRICE">¥${goods.CURRENT_PRICE }
							&nbsp;<span
							style="color:gray;font-size: 1.1rem;text-decoration: line-through;">¥${goods.ORIGINAL_PRICE }</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;销量${goods.SALES_VOLUME }
						</span>
						<!-- <em class="em-promotion"><font id="ECS_DISCOUNT">8.3</font>折</em> -->
					</p>

				</section>
				<section class="b-color-f m-top08 n-goods-box">
					<!--address-start-->
					<div id="editAddressForm">
						<input type="hidden" id="town_id" name="town_region_id" value="">
						<input type="hidden" id="village_id" name="village_region_id" value="">
					</div>
					<!--address-end-->
				</section>
				<section
					class="m-top08 b-color-f goods-attr j-goods-attr j-show-div">

					<!--商品属性弹出层star-->
					<div class="mask-filter-div"></div>
					<div class="show-goods-attr j-filter-show-div ts-3 b-color-1">
						<section class="s-g-attr-title b-color-1  product-list-small">
							<div class="product-div" style="overflow: inherit;">
								<div class="product-list-box">
									<img class="product-list-img"
										src="<%=basePath%>uploadFiles/uploadImgs/${goods.PATH}">
								</div>
								<div class="product-text">
									<div class="dis-box position-rel">
										<h5 class="f-04 box-flex onelist-hidden"
											style="margin-right: 2rem;">${goods.GOODS_NAME }</h5>
											<i class="iconfont show-div-guanbi"><img style="border:none; position: absolute; right: 0;width: 2.0rem;height: 2.0rem;margin-top: -0.3rem;" src="static/lshapp/images/Close-hdpi.png"/></i>
									</div>
									<div>
										<span class="f-06 t-first" >¥<span id="ECS_GOODS_AMOUNT">${goods.CURRENT_PRICE }</span></span>
									</div>
									<span class="box-flex f-02 col-9">库存:
										<font id="GOODS_INVENTORY" class="goods_attr_num">${goods.GOODS_INVENTORY }</font>
									</span>
								</div>
							</div>
						</section>
						<section
							class="s-g-attr-con swiper-scroll b-color-f padding-all m-top1px swiper-container-vertical swiper-container-free-mode swiper-container-android">
							<div class="swiper-wrapper">
								<div class="swiper-slide swiper-slide-active">
									<h4 class="t-remark">规格</h4>
									<!-- 判断属性是复选还是单选 -->
									<ul class="select-one j-get-one m-top10">
										<!-- pc没属性图片 -->
										<c:forEach items="${skuList}" var="var" varStatus="vs">
											<div class="ect-select dis-flex fl" onclick="skuChange('${var.SKU_ID}','${var.GOODS_PRICE}',${var.GOODS_INVENTORY});" >
												<c:if test="${not empty skuList}">
													<label id="sku${var.SKU_ID}" class="ts-1" style="padding: .4rem 1.6rem;" for="spec_value_770">${var.SPECVALUE_NAME}</label>
												</c:if>
											</div>
										</c:forEach>
									</ul>
									<input type="hidden" name="spec_list" value="48">


									<h4 class="t-remark">数量</h4>
									<!-- 普通商品可修改数量 -->
									<div class="div-num dis-box m-top08">
										<a class="num-less" onclick="subtract()"></a>
										<input class="box-flex" type="tel" value="1" onblur="inputPrice()" name="goods_number" id="goods_number">
										<a class="num-plus" onclick="add()"></a>
									</div>
								</div>
							</div>
							<div class="swiper-scrollbar"></div>
						</section>
						<section class="ect-button-more dis-box">
							<input type="hidden" value="6" id="province_id"
								name="province_region_id"> <input type="hidden"
								value="77" id="city_id" name="city_region_id"> <input
								type="hidden" value="0" id="district_id"
								name="district_region_id"> <input type="hidden"
								value="3" id="region_id" name="region_id"> <input
								type="hidden" value="574" id="goods_id" name="good_id">
							<input type="hidden" value="0" id="user_id" name="user_id">
							<input type="hidden" value="689" id="area_id" name="area_id">
							<a class="btn-disab box-flex quehuo" href="javascript:;"
								style="display:none">暂时缺货</a>
								<div class="btn-cart box-flex add-to-cart" onclick="goShoppingCart('${pd.GOODS_ID}');" style="display: none;">立即购买</div>
							<div class="btn-submit box-flex add-to-cart"
								onclick="addGoods('${pd.GOODS_ID}')">确认</div>
						</section>
					</div>
					<!--商品属性弹出层end-->
				</section>
				<section
					class="m-top1px padding-all b-color-f goods-service j-show-div">
					<div class="dis-box">
						<label class="t-remark g-t-temark">服务</label>
						<div class="box-flex">
							<div class="dis-box">
								<p class="box-flex t-goods1">由礼尚汇发货并提供售后服务。</p>
								<!-- <i class="iconfont icon-102 goods-min-icon"></i> -->
								<!--服务信息star-->
							<div class="show-goods-service j-filter-show-div ts-3 b-color-1">
                                <section class="goods-show-title of-hidden padding-all b-color-f">
                                    <h3 class="fl g-c-title-h3">服务说明</h3>
                                  <i class="iconfont show-div-guanbi"><img class="close_image" style="right: 0.3rem;" src="static/lshapp/images/Close-hdpi.png"/></i>
                                </section>
                                <section class="goods-show-con goods-big-service swiper-scroll swiper-container-vertical swiper-container-free-mode swiper-container-android">
                                    <div class="swiper-wrapper">
                                        <div class="swiper-slide swiper-slide-active">
                                            <ul>

                                                                                                <li class="m-top1px b-color-f padding-all of-hidden">
                                                    <p class="dis-box t-remark3">
                                                        <em >
                                                       		<img class="remark" style="margin-top: 0.2rem;" src="static/lshapp/images/quality_assurance.png">
                                                        </em>
                                                        <span class="box-flex">正品保证</span>
                                                    </p>
                                                </li>
                                                <li class="m-top1px b-color-f padding-all of-hidden">
                                                    <p class="dis-box t-remark3">
                                                        <em >
                                                        <img class="remark" style="margin-top: 0.2rem;" src="static/lshapp/images/flash_delivery.png">
                                                        </em>
                                                        <span class="box-flex">闪速配送</span>
                                                    </p>
                                                </li>


                                            </ul>

                                        </div>
                                    </div>

                                </section>
                           </div>

								<!--服务信息end-->
							</div>
							<div class="dis-box m-top08 g-r-rule">
								<p class="box-flex t-remark3">
									<em class="fl">
									<img class="customer" style="margin-top: 0.2rem;" src="static/lshapp/images/quality_assurance.png">
									</em>
									<span class="fl">正品保证</span>
								</p>
								<p class="box-flex t-remark3">
									<em class="fl"><img class="customer" style="margin-top: 0.2rem;" src="static/lshapp/images/flash_delivery.png"></em>
									<span class="fl">闪速配送</span>
								</p>
							</div>
						</div>
					</div>
				</section>

				<c:if test="${pd.USER_ID!=null}">
				<section class="m-top08 goods-evaluation">
					<div onclick="goGoodsComment('${pd.GOODS_ID}');">
						<div class="dis-box padding-all b-color-f  g-evaluation-title">
							<label class="t-remark g-t-temark">用户评价</label>
							<div class="box-flex t-goods1">
							</div>
							<div class="t-goods1">
								<em class="t-first">${comment.ids}</em><span class="t-jiantou">条评论
								<img class="right_arrow" src="static/lshapp/images/right_arrow.png" /></span>
							</div>
						</div>
					</div>
				</section>
				</c:if>
				<section class="m-top08 goods-evaluation" style="margin-top: 0rem;">
					<div style="width: 100%;height: 40px;text-align: center;background-color: #e9e9e9;font-size: 1.5rem;line-height: 40px;">
								详情
					</div>
					<div
			class="goods-info of-hidden ect-tab b-color-f j-goods-info j-ect-tab ts-3"
			style="padding-top:4.2rem">


			<div id="j-tab-con"
				class="b-color-f m-top1px swiper-container-horizontal swiper-container-autoheight swiper-container-android">
				<div class="swiper-wrapper" >
					<c:if test="${goods.GOODS_DETAILS !=''}">
						<section class="swiper-slide swiper-slide-active">
							<div>
								${goods.GOODS_DETAILS}
							</div>

						</section>
					</c:if>
					<c:if test="${goods.GOODS_DETAILS ==''}">
						<section class="swiper-slide goods-info-attr swiper-slide-next"
							style="width: 400px;">
							<div class="no-div-message">
								<p>亲，此处没有内容～！</p>
							</div>
						</section>
					</c:if>
				</div>
			</div>
		</div>
				</section>
				<section class="m-top08  goods-shop b-color-f no-shopping-title">
					<div class="goods-shop-pic of-hidden ">
						<h4 class="title-hrbg m-top06">
							<span>关联商品</span>
							<hr>
						</h4>
						<div
							class="g-s-p-con product-one-list of-hidden scrollbar-none j-g-s-p-con padding-all swiper-container-horizontal swiper-container-android">
							<div class="swiper-wrapper ">
								<c:forEach items="${relationList}" var="var" varStatus="vs">
									<li class="swiper-slide swiper-slide-active">
										<div class="product-div" onclick="goGoods('${var.GOODS_ID}');">
											<div>
												<img class="product-list-img" src="<%=basePath%>uploadFiles/uploadImgs/${var.PATH}">
											</div>
											<div class="product-text m-top06 index-product-text">
												<h4>${var.GOODS_NAME}</h4>
												<p>
													<span class="p-price t-first "> ¥${var.ORIGINAL_PRICE} </span>
												</p>
											</div>
										</div>
									</li>
								</c:forEach>
							</div>
						</div>
					</div>
					<c:if test="${pd.USER_ID!=null}">
					<div
						class="ect-button-more n-ect-button-more m-top10 dis-box padding-all">
						<div class="box-flex btn-default-new br-5 min-btn"  onclick="showPhone();">
						<img class="customer" src="static/lshapp/images/customer_service.png" /><span style="color:#000;">联系客服</span></div>
					</div>
					</c:if>
				</section>

			<!--悬浮btn star-->
			<section class="filter-btn dis-box">
				<c:if test="${pd.USER_ID!=null}">
					<input type="hidden" name="is_collection" id="is_collection" value="${goods.is_collection}"/>
					<!--飞入购物车位置s-->
					<span class="quick_links" id="shopCart"></span>
					<!--飞入购物车位置e-->
					<div class="filter-btn-kefu heart" onclick="showPhone();" >
					<i class="kefu" style="background-size: 1.8rem 1.8rem;"></i><em>客服</em></div>
					<div
						class="filter-btn-flow  heart j-heart " onclick="collect('${pd.GOODS_ID}');"
						id="ECS_COLLECT">

						<img id="img_collection" class="collection"
							<c:if test="${goods.is_collection =='1' }">src="static/lshapp/images/already_collected.png"</c:if>
							<c:if test="${goods.is_collection =='0' }">src="static/lshapp/images/not_collection.png"</c:if>
						 />
						<em>收藏</em>
					</div>
					<div
						class="btn-submit box-flex add-to-cart "
						onclick="checkAdd();" style="background-color: #FFD700;">加入购物车
					</div>
					<div
						class="btn-submit box-flex add-to-cart "
						onclick="checkBuy();">立即购买
					</div>
				</c:if>
				<%--  --%>
				<c:if test="${pd.USER_ID==null}">
					<div style="position: fixed;bottom: 0;height: 70px;width: 100%;background:#000; filter:alpha(opacity:30); opacity:0.4;">
					</div>
					<div style="position: fixed;bottom: 0;width: 100%;height: 70px;">
						<div style="float: left;width: 20%;padding: 10px;"><img src="static/lshapp/images/logo.png" style="width: 50px;height: 50px;"/></div>
						<div style="float: left;width: 45%;font-size: 1.55rem;padding: 12px 0;color: white;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;礼尚汇</br>一个DDP的APP</div>
						<div style="float: left;width: 33%;padding: 15px 0;">
							<div style="border-radius: 8px;border: 1.3px solid #ffffff;height: 40px;line-height: 40px;text-align: center;color: white;">打开APP</div>
						</div>
					</div>
			    </c:if>

			</section>

		</form>
		</div>
	</div>
	<!--地区选择 s-->
	<div class="choose-address-page" id="chooseAddressPage"
		style="display: none;">
		<div class="head-fix">
			<header>
				<div class="jd-index-header">
					<div class="jd-index-header-icon-back">
						<span id="goBack"></span>
					</div>
					<div class="jd-index-header-title"></div>
				</div>

			</header>
			<ul class="head-address-ul" id="headAddressUl">
				<li mytitle="0"></li>
				<li mytitle="1"></li>
				<li mytitle="2"></li>
				<li mytitle="3"></li>
				<li mytitle="4"></li>
			</ul>
		</div>
		<div class="address-container" id="addressContainer">
			<div class="address-content" id="addressContentDiv">
				<ul class="address-ul"></ul>
				<ul class="address-ul"></ul>
				<ul class="address-ul"></ul>
				<ul class="address-ul"></ul>
				<ul class="address-ul"></ul>
			</div>
		</div>
	</div>
	<!--地区选择 e-->
	<div class="shopping-prompt ts-2">
		<img src="/mobile/public/img/fengxiang.png">
	</div>
	<!--图片放大关闭-->
	<div class="goods-bg-box">
		<div class="goods-list-close position-abo">
			<i class="iconfont text-r"><img class="close_images" src="static/lshapp/images/Close-hdpi.png"/></i>
		</div>
	</div>
	<!--联系电话-->
	<div id="contact_number">
	</div>
	<!--快捷导航-->
	<script>
		$(function() {
			// 获取节点
			var block = document.getElementById("ectouch-top");
			var oW, oH;
			// 绑定touchstart事件
			block.addEventListener("touchstart", function(e) {
				var touches = e.touches[0];
				//oW = touches.clientX - block.offsetLeft;
				oH = touches.clientY - block.offsetTop;
				//阻止页面的滑动默认事件
				document.addEventListener("touchmove", defaultEvent, false);
			}, false)

			block.addEventListener("touchmove", function(e) {
				var touches = e.touches[0];
				//var oLeft = touches.clientX - oW;
				var oTop = touches.clientY - oH;
				//  if(oLeft < 0) {
				//   oLeft = 0;
				//  }else if(oLeft > document.documentElement.clientWidth - block.offsetWidth) {
				//   oLeft = (document.documentElement.clientWidth - block.offsetWidth);
				//  }
				// block.style.left = oLeft + "px";
				block.style.top = oTop + "px";
				var max_top = block.style.top = oTop;
				if (max_top < 30) {
					block.style.top = 30 + "px";
				}
				if (max_top > 440) {
					block.style.top = 440 + "px";
				}
			}, false);

			block.addEventListener("touchend", function() {
				document.removeEventListener("touchmove", defaultEvent, false);
			}, false);
			function defaultEvent(e) {
				e.preventDefault();
			}
		});
	</script>
	<c:if test="${pd.USER_ID!=null}">
	<nav class="commom-nav dis-box ts-5" id="ectouch-top">
		<div class="left-icon">
			<div class="nav-icon">
				<img class="jt_image" src="static/lshapp/images/jiantou.png">快速导航
			</div>
			<div class="filter-top filter-top-index" id="scrollUp">
				<i class="iconfont icon-jiantou"></i> <span>顶部</span>
			</div>
		</div>
		 <div class="right-cont box-flex">
			<ul class="nav-cont">
				<li><div onclick="goBIndex();"> <i
						class="iconfont"><img class="navigat_image" src="static/lshapp/images/Birthday-Silhouett-hdpi.png"></i>
						<p>首页</p>
				</div></li>
				<li><div onclick="goGIndex();"> <i
						class="iconfont"><img class="navigat_image" src="static/lshapp/images/Give_a_gift-Silhouett.png"></i>
						<p>送礼</p>
				</div></li>
				<li><div  onclick="goAIndex();"> <i
						class="iconfont"><img class="navigat_image" src="static/lshapp/images/Community-Silhouett-hdpi.png"></i>
						<p>社区</p>
				</div></li>
				<li><div onclick="goUIndex();"> <i
						class="iconfont"><img class="navigat_image" src="static/lshapp/images/Personal_Center-Silhouette.png"></i>
						<p>个人中心</p>
				</div></li>

			</ul>
		</div>
	</nav>
	</c:if>
	<div class="common-show"></div>
	<script type="text/javascript">
		/* $('.j-del-store').click(
				function() {
					$(this).siblings('span').html(
							'门店自提' + '<em class="f-02 col-7">(可选择门店)</em>');
					$('#store_id').val('');
					$(this).removeAttr('style');
					$(this).children().removeClass(
							'icon-guanbi2 store-close-btn').addClass(
							'icon-jiantou tf-180 store-close-btn');
					$.ajax({
						url : "/mobile/index.php?m=goods&a=clearstoreid"
					});
				}); */
		/*商品详情相册切换*/
		var swiper = new Swiper(
				'.goods-photo',
				{
					paginationClickable : true,
					onInit : function(swiper) {
						document.getElementById("g-active-num").innerHTML = swiper.activeIndex + 1;
						document.getElementById("g-all-num").innerHTML = swiper.slides.length;
					},
					onSlideChangeStart : function(swiper) {
						document.getElementById("g-active-num").innerHTML = swiper.activeIndex + 1;
					}
				});
		/*推荐商品滚动*/
		var swiper = new Swiper('.j-g-s-p-con', {
			scrollbarHide : true,
			slidesPerView : 'auto',
			centeredSlides : false,
			grabCursor : true
		});
		$(function() {
			changePrice('onload');
			//商品详情属性弹出层
			$(".click-show-attr").click(function() {
				$(".show-goods-attr").addClass("show");
				$(".mask-filter-div").addClass("show");
			});
		});
		/*加入购物车的模式*/
		function checkAdd() {
			$(".show-goods-attr").addClass("show");
			$(".mask-filter-div").addClass("show");
			$("#judgeA").val(1);
			 $(".ts-1").removeClass("active");
		}
		function checkBuy() {
			$(".show-goods-attr").addClass("show");
			$(".mask-filter-div").addClass("show");
			$("#judgeA").val(2);
			 $(".ts-1").removeClass("active");
		}
	</script>
	<script src="static/js/jquery.cookie.js"></script>
	<script src="static/lshapp/js/fastclick.js"></script>
	<script type="text/javascript">
		function goGoodsIndex(Id){
			if(!$(".div1").hasClass("active")&&Id!=""){
				d_messages("正在加载..."); //显示加载时候的提示
				if($("#U_ID").val()!=""){
					window.location.replace("<%=basePath%>lshapp/shop/goods.do?GOODS_ID="+Id);
				}else{
					window.location.replace("<%=basePath%>weixin/web/example/goods_details?GOODS_ID="+Id);
				}

			}
		}

		function goGoodsComment(Id){
			if(!$(".div4").hasClass("active")&&Id!=""){
				d_messages("正在加载..."); //显示加载时候的提示
				<%-- window.location.replace("<%=basePath%>lshapp/shop/goodsComment.do?GOODS_ID="+Id); --%>
				window.location.href="<%=basePath%>lshapp/shop/goodsComment.do?GOODS_ID="+Id;
			}
		}

		function goGoodsDetails(Id){ // 商品详情图片
			if(!$(".div3").hasClass("active")&&Id!=""){
				$.ajax({ // 加载更多数据
		            type:"POST",
		            url:'<%=basePath%>lshapp/shop/goodsDetailPicture.do',
		            //dataType:'json',
		            cache: false,
		            data: {"GOODS_ID":Id},
		            beforeSend:function(){
		            	d_messages("正在加载..."); //显示加载时候的提示
		            },
		            success:function(ret){
		                 $("#change_goods").html(ret); //将ajxa请求的数据追加到内容里面
		                 $("#checkPage").addClass("change_background");
		            }
		        });
			}
		}

		function goGoods(Id){ // 关联商品详情
			window.location.href="<%=basePath%>lshapp/shop/goods.do?index=index&GOODS_ID="+Id;
		}

		var unitPrice = ""; //当前单价
		var skuId = ""; // 当前选中SKU
		function skuChange(Id,price,inventory){ // 关联商品详情
			$("#sku"+Id).addClass("active"); //修改选中样式
			unitPrice = price; // 当前价格
			skuId = Id;
			var number = $("#goods_number").val(); // 当前数量
			if(isNumber(number)){
				$("#ECS_GOODS_AMOUNT").text((parseFloat(price)*number).toFixed(2)); //修改价格 保留两位小数
				$("#GOODS_INVENTORY").text(inventory); // 修改库存
			}
		}

		function subtract(){ // 减少商品数量
			var number =  $("#goods_number").val(); // 当前选择数量
			var current = 0;
			if(isNumber(number)&&parseInt(number)>1){
				current = parseInt(number)-1;
				$("#goods_number").val(current);
				$("#ECS_GOODS_AMOUNT").text((parseFloat(unitPrice)*current).toFixed(2)); //修改价格 保留两位小数
			}
		}

		function add(){ // 增加商品数量
			var number =  $("#goods_number").val(); // 当前选择数量
			var current = 0;
			if(isNumber(number)&&parseInt(number)>0){
				current = parseInt(number)+1;
				$("#goods_number").val(current);
				$("#ECS_GOODS_AMOUNT").text((parseFloat(unitPrice)*current).toFixed(2)); //修改价格 保留两位小数
			}

		}

		function inputPrice(){ //输入框改变值
			var number = $("#goods_number").val();// 当前选择数量
			if(isNumber(number)){
				$("#ECS_GOODS_AMOUNT").text((parseFloat(unitPrice)*number).toFixed(2)); //修改价格 保留两位小数
			}
		}

		function goShoppingCart(Id){ // 去购物车页面
			var number =  $("#goods_number").val(); // 当前选择数量
			if(skuId == ""){
				 d_messages("请选择商品规格");
			}else{
				window.location.href="<%=basePath%>lshapp/shop/addCart.do?GOODS_ID="+Id+"&SKU_ID="+skuId+"&number="+number;
			}
		}

		function addGoods(Id){ // 添加商品到购物车
			var number = $("#goods_number").val();// 当前选择数量
			var judgeA = $("#judgeA").val();// 当前选择数量
			if(skuId == ""){
				 d_messages("请选择商品规格");
			}else{
				 if(judgeA=="1"){
					 $.ajax({
							type: "POST",
							url: '<%=basePath%>lshapp/shop/addGoods.do?GOODS_ID='+Id+'&SKU_ID='+skuId+'&number='+number,
					    	data: {},
							dataType:'json',
							cache: false,
							success: function(data){
								 if(data.msg=="no"){
									 d_messages("加入购物车失败");
									 $(".show-goods-attr").removeClass("show");
									 $(".mask-filter-div").removeClass("show");
								 }else{
									 d_messages("商品已加入购物车");
									 $(".show-goods-attr").removeClass("show");
									 $(".mask-filter-div").removeClass("show");
								 }
							}
						});
				}else{
					$.ajax({
						type: "POST",
						url: '<%=basePath%>lshapp/shop/addGoodsBuy.do?GOODS_ID='+Id+'&SKU_ID='+skuId+'&number='+number,
				    	data: {},
						dataType:'json',
						cache: false,
						success: function(data){
							 if(data.msg=="no"){
								 d_messages("立即购买失败，请重试!");
								 $(".show-goods-attr").removeClass("show");
								 $(".mask-filter-div").removeClass("show");
							 }else{
								 //去提交订单页面
								 window.location.replace("<%=basePath%>/lshapp/order/submit.do?ids="+data.msg);
							 }
						}
					});
				}

			}
		}


		var isCollect =false;
		// 修改收藏状态
		function collect(Id){
			var coType = $("#is_collection").val();
			if(Id!=""&&!isCollect){
				isCollect = true;
				$.ajax({
					type: "POST",
					url: '<%=basePath%>lshapp/shop/goodsCollection.do',
			    	data: {"GOODS_ID":Id,"coType":coType},
					dataType:'json',
					cache: false,
					success: function(data){
						isCollect = false;
						 if(data.msg=="ok" && data.collect=="collect"){
							 d_messages("商品已收藏");
							 $("#is_collection").val("1");
							 $("#img_collection").attr('src','static/lshapp/images/already_collected.png');
						 }else if(data.msg=="ok" && data.collect=="cancle"){
							 d_messages("商品取消收藏");
							 $("#is_collection").val("0");
							 $("#img_collection").attr('src','static/lshapp/images/not_collection.png');
						 }else{
							 d_messages("操作失败");
						 }
					}
				});
				return true;
			}else{
				return false;
			}
		}
		// 去购物车
		function goCart(){
			window.location.href="<%=basePath%>lshapp/shop/goCart.do";
		}
		// 联系客服
		function showPhone(){
			$.ajax({
		        /* async : false,
		        cache : false, */
		        //cache: false,
		        type : 'POST',
		        url : "<%=basePath%>lshapp/shop/customerService.do",// 请求的action路径
		        data:{},
		        success: function(data){// 请求失败处理函数
		        	$("#contact_number").append(data);
		        },
		    });
		}
		// 关闭联系客服电话
		function closeNumber(){
			$("#contact_number").empty();
		}
		// 生日去首页
		function goBIndex(){
			var data='{"index":"0"}';
           window.WebViewJavascriptBridge.callHandler(
               'openPage'
               ,data
               , function(responseData) {
               }
           );
		}

		// 送礼去首页
		function goGIndex(){
			var data='{"index":"1"}';
           window.WebViewJavascriptBridge.callHandler(
               'openPage'
               ,data
               , function(responseData) {
               }
           );
		}

		// 社区去首页
		function goAIndex(){
			var data='{"index":"3"}';
           window.WebViewJavascriptBridge.callHandler(
               'openPage'
               ,data
               , function(responseData) {
               }
           );
		}

		// 个人中心去首页
		function goUIndex(){
			var data='{"index":"4"}';
			window.WebViewJavascriptBridge.callHandler(
               'openPage'
               ,data
               , function(responseData) {
               }
			);
		}

		/**
	     * 数字判断
	     * @param value
	     */
	    function isNumber(value) {
	        return /(0|^[1-9]\d*$)/.test(value);
	    }

	</script>
	<script type="text/javascript">
		$(function($) {
			/** 将商品信息存储 */
			var expireDate = new Date();
			var goods_info = {
				name : "上海青",
				url : "/mobile/index.php?m=goods&id=574"
			};
			expireDate.setTime(expireDate.getTime() + (60 * 1000));
			$.cookie('index-history', JSON.stringify(goods_info), {
				expires : expireDate
			});

			// 元素以及其他一些变量
			var eleFlyElement = document.querySelector("#flyItem"), eleShopCart = document
					.querySelector("#shopCart");
			var numberItem = 0;
			// 抛物线运动
			var myParabola = funParabola(eleFlyElement, eleShopCart, {
				speed : 10, //抛物线速度
				curvature : 0.009, //控制抛物线弧度
				complete : function() {
					eleFlyElement.style.visibility = "hidden";
					//eleShopCart.querySelector("span").innerHTML = ++numberItem;
				}
			});
			// 绑定点击事件
			if (eleFlyElement && eleShopCart) {

				[].slice
						.call(document.getElementsByClassName("btnCart"))
						.forEach(
								function(button) {
									button
											.addEventListener(
													"click",
													function(event) {
														// 滚动大小
														var scrollLeft = document.documentElement.scrollLeft
																|| document.body.scrollLeft
																|| 0, scrollTop = document.documentElement.scrollTop
																|| document.body.scrollTop
																|| 0;
														eleFlyElement.style.left = event.clientX
																+ scrollLeft
																+ "px";
														eleFlyElement.style.top = event.clientY
																+ scrollTop
																+ "px";
														eleFlyElement.style.visibility = "visible";

														// 需要重定位
														myParabola.position()
																.move();
													});
								});
			}
			//禁止ios双击穿透
			window.addEventListener('load', function() {
				FastClick.attach(document.body);
			}, false);
		});

		$(function() {
			/*点击弹出搜索层*/
			$(".j-price-ladder").click(function() {
				$(".t-price-ladder").toggleClass("active");
			});
		});
	</script>



<script type="text/javascript">
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
var data1= '{"goodsName":"${goods.GOODS_NAME }","imgPath":"<%=basePath%>uploadFiles/uploadImgs/${goods.PATH}","describe":"礼尚汇一家专门做礼品的APP","webUrl":"<%=basePath%>weixin/web/example/goods_details?INVITE_USER_ID=${pd.USER_ID}&GOODS_ID=${goods.GOODS_ID}"}';

connectWebViewJavascriptBridge(function(bridge) {
    bridge.init(function(message, responseCallback) {
        var responseData = '默认接收收到来自Java的数据，回传数据给你';
        responseCallback(responseData);
    });

    bridge.registerHandler("getGoodsInfo", function(data, responseCallback) {
        //bridgeLog('指定接收收到来自Java数据： ' + data);
        //var responseData = '指定接收收到来自Java的数据，回传数据给你';
        responseCallback(data1);
    });
})

</script>


</body>
</html>