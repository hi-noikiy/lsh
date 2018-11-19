package com.szjm.controller.weixin.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import spiderman.wechat.domain.result.WechatUser;

import com.szjm.controller.base.BaseController;
import com.szjm.service.lsh.goods.GoodsManager;
import com.szjm.service.lsh.goodspicture.GoodsPictureManager;
import com.szjm.service.lsh.lshuser.LshUserManager;
import com.szjm.service.system.wechat.wechatuser.WechatUserManager;
import com.szjm.util.PageData;
/**
 * 微信页面例子
 * @author spiderman
 * 2017年5月12日下午2:02:57
 */
@Controller
@RequestMapping(value="/weixin/web/example")
public class WebGoodsDetails extends BaseController{
	@Resource(name="lshuserService")
	private LshUserManager lshuserService;//用户
	@Resource(name="wechatuserService")
	private WechatUserManager wechatuserService;//用户
	// 商品
	@Resource(name = "goodsService")
	private GoodsManager goodsService;
	// 商品图片
	@Resource(name = "goodspictureService")
	private GoodsPictureManager goodspictureService;
	/**
	 * 去商品详情
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goods_details")
	public ModelAndView myFistView(HttpServletRequest request) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		WechatUser user=(WechatUser) request.getSession().getAttribute("wechatUser");
		if(pd.get("INVITE_USER_ID")!=null&&!"".equals(pd.get("INVITE_USER_ID").toString())){
			user.setInvite_user_id(Integer.valueOf(pd.get("INVITE_USER_ID").toString()));
			wechatuserService.editDomain(user);
		}
		ModelAndView view=this.getModelAndView();

		PageData goods = goodsService.findById(pd);
		List<PageData> pictureList = goodspictureService.listByGoodsId(pd);
		view.addObject("goods", goods); // 商品
		view.addObject("pd", pd);
		view.addObject("pictureList", pictureList);// 商品轮播图
		view.setViewName("lshapp/goods_index");
		return view;
	}
}
