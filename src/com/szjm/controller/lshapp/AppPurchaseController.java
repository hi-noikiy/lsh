package com.szjm.controller.lshapp;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.szjm.controller.base.BaseController;
import com.szjm.service.lsh.agent.AgentManager;
import com.szjm.service.lsh.agentpurchase.AgentPurchaseManager;
import com.szjm.service.lsh.bean.BeanManager;
import com.szjm.service.lsh.beanrecharge.BeanRechargeManager;
import com.szjm.service.lsh.lshuser.LshUserManager;
import com.szjm.util.Jurisdiction;
import com.szjm.util.PageData;

@Controller
@RequestMapping(value = "/lshapp/purchase")
public class AppPurchaseController extends BaseController {
	@Resource(name = "lshuserService")
	private LshUserManager appuserService;// 用户
	@Resource(name = "beanService")
	private BeanManager beanService;
	@Resource(name = "beanrechargeService")
	private BeanRechargeManager beanrechargeService;
	@Resource(name = "agentService")
	private AgentManager agentService;
	@Resource(name = "agentpurchaseService")
	private AgentPurchaseManager agentpurchaseService;

	/**
	 * 代理配置列表
	 *
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/agentList")
	public ModelAndView agentList(HttpServletRequest request) throws Exception {
		// logBefore(logger, Jurisdiction.getUsername()+"列表Bean");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		Integer user_id = Jurisdiction.getAppUserId();//获取当前用户id
		pd.put("USER_ID", user_id);
		PageData pdUser = appuserService.findById(pd);

		List<PageData> varList = agentService.listAll(pd); // 列出Agent列表
		mv.setViewName("lshapp/agent_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("pdUser", pdUser);
		// mv.addObject("QX",Jurisdiction.getHC()); //按钮权限
		return mv;
	}

	/**
	 * 代理购买记录
	 *
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/agentPurchase")
	public ModelAndView agentPurchase(HttpServletRequest request, String userId) throws Exception {
		// logBefore(logger, Jurisdiction.getUsername()+"列表Bean");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("USER_ID", userId);
		List<PageData> varList = agentpurchaseService.listAllByUserId(pd);
		mv.setViewName("lshapp/agent_purchase");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		// mv.addObject("QX",Jurisdiction.getHC()); //按钮权限
		return mv;
	}

	/**
	 * 金币配置列表
	 *
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/beanList")
	public ModelAndView beanList(HttpServletRequest request, String userId) throws Exception {
		// logBefore(logger, Jurisdiction.getUsername()+"列表Bean");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> varList = beanService.listAll(pd); // 列出Bean列表
		// 查询该用户是否存在充值信息
		PageData recharge = new PageData();
		recharge.put("USER_ID", userId);
		List<PageData> rechargeList = beanrechargeService.listAllByUserId(recharge);
		if (rechargeList.size() > 0) {
			varList = varList.subList(0, 9);
		}
		mv.setViewName("lshapp/bean_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		// mv.addObject("QX",Jurisdiction.getHC()); //按钮权限
		return mv;
	}

	/**
	 * 根据用户id查询该用户的所有金豆充值信息列表
	 *
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/beanRecharge")
	public ModelAndView beanRecharge(HttpServletRequest request, String userId) throws Exception {
		// logBefore(logger, Jurisdiction.getUsername()+"列表Bean");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		// //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("USER_ID", userId);
		List<PageData> varList = beanrechargeService.listAllByUserId(pd);
		mv.setViewName("lshapp/bean_recharge");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		// mv.addObject("QX",Jurisdiction.getHC()); //按钮权限
		return mv;
	}
}
