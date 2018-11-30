package com.szjm.controller.lshapp;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.szjm.controller.base.BaseController;
import com.szjm.service.lsh.giftbeans.GiftBeansManager;
import com.szjm.service.lsh.lshuser.LshUserManager;
import com.szjm.util.Jurisdiction;
import com.szjm.util.PageData;

/**
 * 说明：礼豆 创建人： 创建时间：2018-11-27
 */
@Controller
@RequestMapping(value = "/giftbeans")
public class AppGiftBeansController {
	String menuUrl = "giftbeans/list.do"; // 菜单地址(权限用)
	@Resource(name = "giftbeansService")
	private GiftBeansManager giftbeansService;
	@Resource(name = "lshuserService")
	private LshUserManager lshuserService;// 用户

	@RequestMapping("/buyGiftBeans")
	public int buyGiftBeans(Integer buyNums) {
		int result = 0;
		// 获取当前APP登录的用户编号
		Integer appUserId = Jurisdiction.getAppUserId();
		if (null != appUserId) {
			PageData pd = new PageData();
			pd.put("USER_ID", appUserId);
			//查询当前操作的用户
			try {
				PageData usPd=lshuserService.findById(pd);
				String roleName=usPd.getString("ROLE");
				if(!StringUtils.isEmpty(roleName)) {
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
}
