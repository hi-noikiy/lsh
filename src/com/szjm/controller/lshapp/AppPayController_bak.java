package com.szjm.controller.lshapp;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.szjm.controller.base.BaseController;
import com.szjm.service.lsh.agent.AgentManager;
import com.szjm.service.lsh.agentpurchase.AgentPurchaseManager;
import com.szjm.service.lsh.bean.BeanManager;
import com.szjm.service.lsh.beanrecharge.BeanRechargeManager;
import com.szjm.service.system.appuser.AppuserManager;
import com.szjm.service.system.user.UserManager;
import com.szjm.util.Jurisdiction;
import com.szjm.util.PageData;
import com.szjm.util.Tools;

import spiderman.alipay.util.AlipayInterfaceInvokeUtil;
import spiderman.wechat.domain.param.UnifiedOrderSend;
import spiderman.wechat.domain.result.UnifiedOrderResult;
import spiderman.wechat.util.AppWechatInterfaceInvokeUtil;
import spiderman.wechat.util.SignUtil;

/**
 * 支付
 * @author Totoro
 *
 */
@RestController
@RequestMapping(value = "/lshapp/pay")
public class AppPayController_bak extends BaseController {

	@Resource(name = "agentpurchaseService")
	private AgentPurchaseManager agentpurchaseService;
	@Resource(name = "beanrechargeService")
	private BeanRechargeManager rechargeService;
	@Resource(name = "userService")
	private UserManager userService;
	@Resource(name = "beanService")
	private BeanManager beanService;
	@Resource(name = "agentService")
	private AgentManager agentService;
	@Resource(name = "appuserService")
	private AppuserManager appuserService;
	
	/**
	 * 
	 * @param userId 用户id
	 * @param agentId 代理配置id
	 * @param beanId 金豆充值id
	 * @param payment 支付方式（0.微信）
	 * @param money 支付金额（1.支付宝）
	 * @throws Exception
	 */
	@Transactional
	@RequestMapping(value = "/pay")
	@ResponseBody
	public Object pay(String userId, String agentId,String beanId, String payment, Double money,String ipAddress) throws Exception {
		
		Map<String, Object> aliResult = new HashMap<String, Object>();
		String number ="";
		if (payment.equals("0")) {
			String type = "0";
			//微信
			if (null != agentId && !agentId.equals("")) {
				//代理购买
				number = purchaseAdd(userId,  agentId,  money, type);
			}else if (null != beanId && !beanId.equals("")) {
				//金豆充值
				number = rechargeAdd(userId,  beanId,  money, type);
			}
			UnifiedOrderSend unifiedOrderSend = new UnifiedOrderSend();
			// send.set
			unifiedOrderSend.setBody("礼尚汇商品");
			unifiedOrderSend.setOut_trade_no(number);
			Double realPay = Double.valueOf(money) * 100;
			String real_pay = realPay.toString();
			String fee = real_pay;
			if (fee.contains(".")) {
				fee = fee.substring(0, fee.indexOf("."));
			}
			//unifiedOrderSend.setTotal_fee(Integer.valueOf(fee));
			unifiedOrderSend.setTotal_fee(1);
			unifiedOrderSend.setSpbill_create_ip(ipAddress);
			unifiedOrderSend.setTrade_type("APP");
			UnifiedOrderResult result = AppWechatInterfaceInvokeUtil
					.unifiedOrder(unifiedOrderSend);
			Map<String, Object> wxResult = SignUtil.signOrderResult4App(result);
			wxResult.put("paytype", "wxpay");
			wxResult.put("order_id", number);
			
		} else if(payment.equals("1")) {
			String type = "1";
			//支付宝
			if (null != agentId && !agentId.equals("")) {
				//代理购买
				number = purchaseAdd(userId,  agentId,  money, type);
			}else if (null != beanId && !beanId.equals("")) {
				//金豆充值
				number = rechargeAdd(userId,  beanId,  money, type);
			}
			aliResult.put("paytype", "alipay");
			/*String alipayMessge = AlipayInterfaceInvokeUtil.AlipayTradeAppPay(
					"礼尚汇商品", "礼尚汇支付", out_order_id, real_pay);*/
			String outtradeno = number;
			String totalAmount = "0.01";
			String alipayMessge = AlipayInterfaceInvokeUtil.AlipayTradeAppPay(
					"礼尚汇商品", "礼尚汇支付", outtradeno, totalAmount);
			aliResult.put("alipaymessge", alipayMessge
					.replaceAll("\u003d", "=").replaceAll("\u0026", "&"));
			aliResult.put("order_id", number);
		}
		return aliResult;
	}
	

	/**
	 * 新增代理购买记录
	 * 
	 * @param page
	 * @throws Exception
	 */
	public String purchaseAdd(String userId, String agentId, Double money,String type) throws Exception {
		Integer appUserId = Jurisdiction.getAppUserId();
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("PURCHASE_ID", this.get32UUID()); // 主键
		String df = new SimpleDateFormat("yyyy").format(new Date());
		String number = df + (new Date()).getTime();
		pd.put("PURCHASE_NUMBER", number); // 代理购买编号
		pd.put("USER_ID", userId); // 用户id
		pd.put("AGENT_ID", agentId); // 代理配置id
		// 根据代理配置id查询代理信息
		PageData agent = new PageData();
		agent.put("AGENT_ID", agentId);
		agent = agentService.findById(agent);
		pd.put("AGENT_STATUS", agent.get("STATUS")); // 代理状态
		pd.put("AGENT_PRICE", money); // 支付金额
		pd.put("TYPE", type); // 支付方式
		pd.put("CREATE_TIME", Tools.date2Str(new Date())); // 创建时间
		pd.put("STATUS", "0"); // 创建时间
		pd.put("PAY_TIME", ""); // 创建时间
		agentpurchaseService.save(pd);
		return number;
	}

	/**
	 * 新增金豆充值记录
	 * 
	 * @param page
	 * @throws Exception
	 */
	public String rechargeAdd(String userId, String beanId, Double money,String type) throws Exception {
		Integer appUserId = Jurisdiction.getAppUserId();
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("RECHARGE_ID", this.get32UUID()); // 主键
		String df = new SimpleDateFormat("yyyy").format(new Date());
		String number = df + (new Date()).getTime();
		pd.put("RECHARGE_NUMBER", number); // 充值编号
		pd.put("USER_ID", userId); // 用户id
		pd.put("BEAN_ID", beanId); // 金豆配置id
		// 根据金豆配置id查询配置信息
		PageData bean = new PageData();
		bean.put("BEAN_ID", beanId);
		bean = beanService.findById(bean);
		pd.put("BEAN_NUMBER", bean.get("BEAN_NUMBER")); // 充值数量
		pd.put("BEAN_PRICE", money); // 支付金额
		pd.put("TYPE", type); // 支付方式
		pd.put("CREATE_TIME", Tools.date2Str(new Date())); // 创建时间
		pd.put("STATUS", "0");  
		pd.put("PAY_TIME", ""); 
		agentpurchaseService.save(pd);
		
		//给用户添加金豆
		PageData user = new PageData();
		user.put("USER_ID", user);
		user = appuserService.findByUiId(user);
		Integer  integration = Integer.parseInt(user.get("INTEGRATION").toString());
		Integer  bean_number = Integer.parseInt(bean.get("BEAN_NUMBER").toString());
		user.put("INTEGRATION", integration+bean_number);
		appuserService.saveU(user);
		return number;
	}

	/**
	 * 支付成功后修改金豆充值记录
	 * @param beanId
	 * @return
	 * @throws Exception
	 */
	public String rechargeUpdate(String rechargeId) throws Exception {
		
		PageData pd = new PageData();
		pd.put("RECHARGE_ID", rechargeId);
		PageData recharge = rechargeService.findById(pd);
		recharge.put("STATUS", "1");
		recharge.put("PAY_TIME", Tools.date2Str(new Date()));
		rechargeService.save(recharge);
		return "修改成功";
	}
	
	/**
	 * 支付成功后修改代理购买记录
	 * @param beanId
	 * @return
	 * @throws Exception
	 */
	public String purshaseUpdate(String purchaseId) throws Exception {
		
		PageData pd = new PageData();
		pd.put("PURCHASE_ID", purchaseId);
		PageData purchase = agentpurchaseService.findById(pd);
		purchase.put("STATUS", "1");
		purchase.put("PAY_TIME", Tools.date2Str(new Date()));
		agentpurchaseService.save(purchase);
		return "修改成功";
	}
}
