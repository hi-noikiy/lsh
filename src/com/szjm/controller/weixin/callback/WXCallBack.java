package com.szjm.controller.weixin.callback;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import spiderman.alipay.config.AlipayConfig;
import spiderman.util.base.BaseUtil;
import spiderman.util.base.StringUtil;
import spiderman.wechat.domain.callback.PayCallBack;
import spiderman.wechat.domain.callback.Result;
import spiderman.wechat.util.SignUtil;

import com.alipay.api.internal.util.AlipaySignature;
import com.szjm.controller.base.BaseController;
import com.szjm.service.lsh.agent.AgentManager;
import com.szjm.service.lsh.agentpurchase.AgentPurchaseManager;
import com.szjm.service.lsh.beanrecharge.BeanRechargeManager;
import com.szjm.service.lsh.finance.FinanceManager;
import com.szjm.service.lsh.lshuser.LshUserManager;
import com.szjm.service.lsh.order.OrderManager;
import com.szjm.service.lsh.sysconfig.SysConfigManager;
import com.szjm.util.PageData;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
/**
 * 微信支付回调
 * @author spiderman
 * 2017年5月12日下午2:01:01
 */
@Controller
@RequestMapping(value="/weixin/callback/wxCallBack")
public class WXCallBack extends BaseController {
	@Resource(name="sysconfigService")
	private SysConfigManager sysconfigService;
	@Resource(name="lshuserService")
	private LshUserManager lshuserService;//用户
	@Resource(name="financeService")
	private FinanceManager financeService;
	@Resource(name="orderService")
	private OrderManager orderService;
	@Resource(name = "beanrechargeService")
	private BeanRechargeManager beanrechargeService;
	@Resource(name = "agentService")
	private AgentManager agentService;
	@Resource(name = "agentpurchaseService")
	private AgentPurchaseManager agentpurchaseService;
	@RequestMapping(value="/callback")
	public void callback(HttpServletRequest request,HttpServletResponse response){
		try {
			InputStream input=request.getInputStream();
			String content=StringUtil.getString(input);
			XStream xs=new XStream(new DomDriver());
			xs.alias("xml", PayCallBack.class);
			PayCallBack receive=(PayCallBack)xs.fromXML(content);
			BaseUtil.getLogger().debug("getAppid----->"+receive.getAppid());
			BaseUtil.getLogger().debug("getOut_trade_no----->"+receive.getOut_trade_no());
			BaseUtil.getLogger().debug("getSign----->"+receive.getSign());
			BaseUtil.getLogger().debug(BaseUtil.getJson(receive));
			if(SignUtil.signScanPayCallBack(receive).equals(receive.getSign())){
				System.out.println("验证成功");
				synchronized (receive.getOut_trade_no().intern()) {
					if("SUCCESS".equals(receive.getReturn_code())&&"SUCCESS".equals(receive.getResult_code())){

						//先用微信回调返回的支付编号，在订单表、礼豆充值表、礼包购买记录表中查询。修改对应记录的状态
						PageData pd1 = new PageData();
						pd1.put("PURCHASE_NUMBER", receive.getOut_trade_no());
						PageData agentpd=	agentpurchaseService.findById(pd1);
						PageData pd2 = new PageData();
						pd2.put("RECHARGE_NUMBER", receive.getOut_trade_no());
						PageData beanpd=beanrechargeService.findById(pd2);
						if(agentpd!=null){
							agentpd.put("STATUS", 1);
							agentpurchaseService.edit(agentpd);
						}else if(beanpd!=null){
							beanpd.put("STATUS", 1);
							beanrechargeService.edit(beanpd);
						}else{
							orderService.finishOrder(receive.getOut_trade_no());
						}

					}else if("FAIL".equals(receive.getReturn_code())){

					}
				}
			}else{
				System.out.println("验证失败");
			}
			//响应服务器
			Result result=new Result();
			result.setReturn_code("SUCCESS");
			result.setReturn_msg("OK");
			XStream xs1=new XStream(new DomDriver());
			xs1.alias("xml", result.getClass());
			System.out.println(xs1.toXML(result));
			response.getWriter().write(xs1.toXML(result));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
	//支付宝回调接口
	@RequestMapping(value="/alipay")
	public void alipay(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			//商户订单号

			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			//支付宝交易号

			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

			//交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
			//计算得出通知验证结果
			//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
			boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, "RSA2");

			if(verify_result){//验证成功
				//////////////////////////////////////////////////////////////////////////////////////////
				//请在这里加上商户的业务逻辑程序代码

				//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

				if(trade_status.equals("TRADE_FINISHED")){
					//判断该笔订单是否在商户网站中已经做过处理
						//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
						//请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
						//如果有做过处理，不执行商户的业务程序

					//注意：
					//如果签约的是可退款协议，退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
					//如果没有签约可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
				} else if (trade_status.equals("TRADE_SUCCESS")){
					//判断该笔订单是否在商户网站中已经做过处理
						//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
						//请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
						//如果有做过处理，不执行商户的业务程序

					//注意：
					//如果签约的是可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
				}
				synchronized (trade_no.intern()) {
					orderService.finishOrder(out_trade_no);
				}
				//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
//				out.clear();
//				out.println("success");	//请不要修改或删除
				response.getWriter().write("success");
				System.out.println("success!!");
				//////////////////////////////////////////////////////////////////////////////////////////
			}else{//验证失败
				//out.println("fail");
				response.getWriter().write("fail");
				System.out.println("fail!!");
			}
	}
}
