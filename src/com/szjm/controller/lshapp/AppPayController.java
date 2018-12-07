package com.szjm.controller.lshapp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alipay.api.internal.util.AlipaySignature;
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

import spiderman.alipay.config.AlipayConfig;
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
public class AppPayController extends BaseController {

	public Logger log = (Logger) LoggerFactory.getLogger(AppPayController.class);
	
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
	 * 购买金豆
	 * @param userId 用户id
	 * @param agentId 代理配置id
	 * @param agentStatus 代理状态（0.会员，1.经销商，2.企商VIP）
	 * 
	 * @param beanId 金豆充值id
	 * @param payment 支付方式（0.微信,1.支付宝）
	 * @param totalAmount 支付金额
	 * @throws Exception
	 */
	@Transactional
	@RequestMapping(value = "/buyGold")
	@ResponseBody
	public Object buyGold(String userId, String agentId,String beanId, /*String agentStatus,*/
			String payment, String totalAmount,String ipAddress) throws Exception {
		Map<String, Object> aliResult = new HashMap<String, Object>();
		String number ="";
		if (payment.equals("0")) {//微信支付
			//微信
			if (null != agentId && !agentId.equals("")) {
				//代理购买
				number = purchaseAdd(userId,  agentId,  /*agentStatus,*/ totalAmount, payment);
			}else if (null != beanId && !beanId.equals("")) {
				//金豆充值
				number = rechargeAdd(userId,  beanId,  totalAmount, payment);
			}
			UnifiedOrderSend unifiedOrderSend = new UnifiedOrderSend();
			// send.set
			unifiedOrderSend.setBody("礼尚汇商品");
			unifiedOrderSend.setOut_trade_no(number);
			Double realPay = Double.valueOf(totalAmount) * 100;
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
			
		} else if(payment.equals("1")) {//支付宝支付
			//支付宝
			if (null != agentId && !agentId.equals("")) {
				//代理购买
				number = purchaseAdd(userId,  agentId, /*agentStatus,*/ totalAmount, payment);
			}else if (null != beanId && !beanId.equals("")) {
				//金豆充值
				number = rechargeAdd(userId,  beanId,  totalAmount, payment);
			}
			aliResult.put("paytype", "alipay");
			/*String alipayMessge = AlipayInterfaceInvokeUtil.AlipayTradeAppPay(
					"礼尚汇商品", "礼尚汇支付", out_order_id, real_pay);*/
			String outtradeno = number;
			//String totalAmount = "0.01";
			String alipayMessge = AlipayInterfaceInvokeUtil.AlipayTradeAppPayAgent(
					"礼尚汇商品", "礼尚汇支付", outtradeno, totalAmount);
			aliResult.put("alipaymessge", alipayMessge
					.replaceAll("\u003d", "=").replaceAll("\u0026", "&"));
			aliResult.put("order_id", number);
		}
		return aliResult;
	}
	
	/**
	 * 新增代理购买记录
	 * @param userId 用户id
	 * @param agentId 代理配置id
	 * @param payment 支付方式（0.微信,1.支付宝）
	 * @param totalAmount 支付金额
	 * @throws Exception
	 */
	public String purchaseAdd(String userId, String agentId, /*String agentStatus,*/String totalAmount,String payment) throws Exception {
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
		pd.put("AGENT_STATUS", agent.get("STATUS")); // 代理状态（0.会员，1.经销商，2.企商VIP）
		//pd.put("AGENT_STATUS", agentStatus); // 代理状态（0.会员，1.经销商，2.企商VIP）
		pd.put("AGENT_PRICE", totalAmount); // 支付金额
		pd.put("TYPE", payment); // 支付方式（0.微信，1.支付宝）
		pd.put("CREATE_TIME", Tools.date2Str(new Date())); // 创建时间
		pd.put("STATUS", "0"); // 支付状态（0.未支付，1.已支付）
		agentpurchaseService.save(pd);
		return number;
	}

	/**
	 * 新增金豆充值记录
	 * 
	 * @param userId 用户id
	 * @param beanId 金豆充值id
	 * @param payment 支付方式（0.微信,1.支付宝）
	 * @param totalAmount 支付金额
	 * @throws Exception
	 */
	public String rechargeAdd(String userId, String beanId, String totalAmount,String payment) throws Exception {
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
		pd.put("BEAN_PRICE", totalAmount); // 支付金额
		pd.put("TYPE", payment); // 支付方式
		pd.put("CREATE_TIME", Tools.date2Str(new Date())); // 创建时间
		pd.put("STATUS", "0");  //支付状态（0.未支付，1.已支付）
		rechargeService.save(pd);
		return number;
	}

	/**
	 * 支付宝回调地址
	 * 
	 * @param request
	 * @param httpResponse
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/alipayReturnOrder")
	public void alipayReturnOrder(HttpServletRequest request, HttpServletResponse httpResponse, HttpSession session)
			throws Exception {
		// 获取支付宝POST过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		Iterator iter = requestParams.keySet().iterator();
		while (iter.hasNext()) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		// 商户订单号
		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
		// 支付宝交易号
		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
		// 交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
		// 支付金额
		String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");
		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表
		boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET,
				AlipayConfig.SIGNTYPE);

		if (verify_result) {// 验证成功
			if (trade_status.equals("TRADE_SUCCESS")) {// 支付成功
				synchronized (out_trade_no.intern()) {
					PageData pd = new PageData();
					pd.put("PURCHASE_NUMBER", out_trade_no);//购买编号
					PageData  lshAgentPurchase = agentpurchaseService.findPurchaseNumber(pd);
					if(lshAgentPurchase != null) {//如果lshAgentPurchase不等于null进行购买代理支付 否则金豆充值支付
						//支付成功后调用修改的方法
						purshaseUpdate(lshAgentPurchase.get("PURCHASE_ID").toString());//获取当前用户的代理购买id
					}else if(lshAgentPurchase == null) {//金豆充值
						pd.put("RECHARGE_NUMBER", out_trade_no);//充值编号
						PageData lshBeanRecharge = rechargeService.findRechargeNumber(pd);
						if(lshBeanRecharge != null) {
							//支付成功后调用修改的方法(用户累加金豆数量)
							Integer user_id = Jurisdiction.getAppUserId();//获取当前用户id
							lshAppUserUpdate(user_id+"");
							//支付成功后如果是金豆充值就调用以下方法
							rechargeUpdate(lshBeanRecharge.get("RECHARGE_ID").toString());//获取当前用户的金豆充值id
						}
					}
				}
			}
		} else {
			// 验证失败
			log.info("验证失败!");
		}
	}
	
	/**
	 * 支付成功 给用户添加金豆(用户累加金豆数量)
	 * @param userId 用户id
	 * @return
	 * @throws Exception
	 */
	public String lshAppUserUpdate(String userId) throws Exception {
		// 根据金豆配置id查询配置信息
		PageData bean = new PageData();
		PageData user = new PageData();
		user.put("USER_ID", userId);
		user = appuserService.findByUiId(user);
		Integer  integration = Integer.parseInt(user.get("INTEGRATION").toString());
		Integer  bean_number = Integer.parseInt(bean.get("BEAN_NUMBER").toString());
		user.put("INTEGRATION", integration+bean_number);
		appuserService.editU(user);
		return "修改成功";
	}	

	/**
	 * 支付成功后修改金豆充值记录
	 * @param rechargeId 金豆充值id
	 * @return
	 * @throws Exception
	 */
	public String rechargeUpdate(String rechargeId) throws Exception {
		PageData pd = new PageData();
		pd.put("RECHARGE_ID", rechargeId);
		PageData recharge = rechargeService.findById(pd);
		recharge.put("STATUS", "1");
		recharge.put("PAY_TIME", Tools.date2Str(new Date()));
		rechargeService.edit(recharge);
		return "修改成功";
	}
	
	/**
	 * 支付成功后修改代理购买记录
	 * @param purchaseId 代理购买id
	 * @return
	 * @throws Exception
	 */
	public String purshaseUpdate(String purchaseId) throws Exception {
		PageData pd = new PageData();
		pd.put("PURCHASE_ID", purchaseId);
		PageData purchase = agentpurchaseService.findById(pd);
		purchase.put("STATUS", "1");
		purchase.put("PAY_TIME", Tools.date2Str(new Date()));
		agentpurchaseService.edit(purchase);
		return "修改成功";
	}

}
