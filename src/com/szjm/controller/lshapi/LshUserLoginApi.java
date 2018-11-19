package com.szjm.controller.lshapi;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szjm.controller.base.BaseController;
import com.szjm.controller.lshapp.BaseAppController;
import com.szjm.entity.Page;
import com.szjm.service.lsh.article.ArticleManager;
import com.szjm.util.Const;
import com.szjm.util.Jurisdiction;
import com.szjm.util.PageData;
import com.szjm.util.Tools;
import com.szjm.service.lsh.articlecomment.ArticleCommentManager;
import com.szjm.service.lsh.articlepicture.ArticlePictureManager;
import com.szjm.service.lsh.collection.CollectionManager;
import com.szjm.service.lsh.goods.GoodsManager;
import com.szjm.service.lsh.goodspicture.GoodsPictureManager;
import com.szjm.service.lsh.goodssku.GoodsSkuManager;
import com.szjm.service.lsh.goodstype.GoodsTypeManager;
import com.szjm.service.lsh.lshuser.LshUserManager;
import com.szjm.service.lsh.memorialday.MemorialDayManager;
import com.szjm.service.lsh.sysconfig.SysConfigManager;
import com.szjm.service.lsh.userbrithday.UserBrithdayManager;

/**
 * API接口类
 * @author 
 *
 */
@Controller
@RequestMapping(value="app/Api")
public class LshUserLoginApi extends BaseAppController {
	@Resource(name="lshuserService")
	private LshUserManager lshuserService;//用户
	
	/**
	 * 用户登陆
	 */
	@RequestMapping(value="/user_login")
	@ResponseBody
	public void user_login(HttpServletRequest request, HttpServletResponse response) throws Exception{
		PageData pd = new PageData();
		Map<String,Object> TokenMap=new HashMap<String,Object>();
		pd = this.getPageData();
		if((pd.get("PHONE_NUMBER")!=null&&!pd.get("PHONE_NUMBER").toString().equals(""))&&(pd.get("PASSWORD")!=null&&!pd.get("PHONE_NUMBER").toString().equals(""))){
			String PASSWORD = new SimpleHash("SHA-1",pd.get("PASSWORD").toString()).toString();	//密码加密
			pd.put("PASSWORD", PASSWORD);
			PageData userP=lshuserService.findById(pd);
			if(userP!=null){
				request.getSession().setAttribute(Const.SESSION_APP_USER_ID, userP.get("USER_ID"));
				request.getSession().setAttribute(Const.SESSION_APP_USER_ROLE, userP.get("ROLE"));
				TokenMap.put("TOKEN", userP.get("TOKEN").toString());
				WriteClientMessage(response,"0", "成功", TokenMap);
			}else{
				WriteClientMessage(response,"1", "失败", TokenMap);
			}
		}else{
			WriteClientMessage(response,"1", "失败", TokenMap);
		}	
	}
}
