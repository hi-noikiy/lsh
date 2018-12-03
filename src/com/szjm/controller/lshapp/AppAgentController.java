package com.szjm.controller.lshapp;

import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.szjm.service.lsh.caketickets.CakeTicketsManager;
import com.szjm.service.lsh.giftbeans.GiftBeansManager;
import com.szjm.service.lsh.goldbeans.GoldBeansManager;
import com.szjm.service.lsh.lshuser.LshUserManager;
import com.szjm.service.system.appuser.impl.AppuserService;
import com.szjm.util.Jurisdiction;
import com.szjm.util.PageData;
import com.szjm.util.StringUtil;
import com.szjm.util.Tools;

import cn.jiguang.common.utils.StringUtils;

@Controller
@RequestMapping(value = "lshapp/agent")
public class AppAgentController {
	@Resource(name="lshuserService")
	private LshUserManager lshuserService;//用户
	@Autowired
	private CakeTicketsManager caketicketService;
	@Autowired
	private GiftBeansManager giftBeansManager;
	@Autowired
	private GoldBeansManager goldBeansManager;
	final private String VIP="普通店主";
	final private String VIPGOLD="金牌店主";
	final private String VIPJEWEL="钻石店主";
	final private String JXS="经销商";
	final private String JXSQ="区级经销商";
	final private String JXSX="县级经销商";
	final private String JXSS="市级经销商";
	

	// TODO 购买会员
	@RequestMapping("/buyVip")
	public Object buyVip() {
		boolean result=false;
		//获取当前APP登录的用户编号
		Integer appUserId = Jurisdiction.getAppUserId();
		if(appUserId==null) {
			return result;
		}
		PageData pd=new PageData();
		try {
			pd.put("USER_ID",appUserId);
			//查询当前操作的用户
			PageData usPd=lshuserService.findById(pd);
			String roleName=(String) usPd.get("ROLE");
			if(!StringUtils.isEmpty(roleName)) {
				//赠送蛋糕券CREATE_DATE
				PageData cakePd=new PageData();
				Integer cakeTicket=398;
				cakePd.put("CAKETICKETS_ID",UUID.randomUUID().toString().replace("-", "").toLowerCase());
				cakePd.put("USER_ID",appUserId);
				cakePd.put("REMARKS","购买会员赠送");
				cakePd.put("STATUS","增加");
				cakePd.put("MODIFY_TICKET","+398");
				cakePd.put("CAKE_TICKET",cakeTicket);
				cakePd.put("CREATE_DATE",Tools.date2Str(new Date()));
				cakePd.put("CREATE_USER",appUserId);
				cakePd.put("DELETE_STATUS",0);
				caketicketService.save(cakePd);
				
				PageData giftPd=new PageData();
				Integer giftBeans=2000;
				giftPd.put("GIFTBEANS_ID",UUID.randomUUID().toString().replace("-", "").toLowerCase());
				giftPd.put("USER_ID", appUserId);
				giftPd.put("REMARKS", "购买升级会员赠送");
				giftPd.put("STATUS","增加");
				giftPd.put("MODIFY_GIFT", "+2000");
				giftPd.put("GIFT_BEANS", giftBeans);
				giftPd.put("CREATE_DATE",Tools.date2Str(new Date()) );
				giftPd.put("CREATE_USER", appUserId);
				giftPd.put("DELETE_STATUS", 0);
				giftBeansManager.save(giftPd);
				
				//购买vip时查询是否有推荐人 parent_id，如果有就更新推荐记录的直接推荐店主统计 
				if(null!=usPd.get("PARENT_ID")) {
					String parendId=(String)usPd.get("PARENT_ID");
					PageData invitePds=new PageData();
					invitePds.put("USER_ID", parendId);
					//根据推荐人id查询推荐人信息
					PageData invitePd=lshuserService.findById(pd);
					String inviteRoleName=invitePd.getString("ROLE");
					Integer inviteVipCount=null==invitePd.get("INVITE_VIP_COUNT")?null:(Integer)invitePd.get("INVITE_VIP_COUNT");
					Integer inviteCount=null==invitePd.get("INVITE_COUNT")?null:(Integer)invitePd.get("INVITE_COUNT");
					if(inviteVipCount!=null) {
						inviteVipCount++;
					}else {
						inviteVipCount=1;
					}
					invitePd.put("INVITE_VIP_COUNT",inviteVipCount);
					//如果已经有推荐的下级了只再+1  否则就为1
					if(inviteCount!=null) {
						inviteCount++;
					}else {
						inviteCount=1;
					}
					invitePd.put("INVITE_COUNT",inviteCount);
					//如果推荐的会员人数达到了20个，则升级为 金牌店主
					//直接推荐的金牌店主人数
					Integer inviteGoldvipCount=null==invitePd.get("INVITE_GOLDVIP_COUNT")?1:(Integer)invitePd.get("INVITE_GOLDVIP_COUNT");
					if(inviteVipCount>=20&&!VIPGOLD.equals(inviteRoleName)) {
						invitePd.put("ROLE",VIPGOLD);
						//升级金牌店主时更新推荐人的invite_goldvip_count  金牌会员推荐人数
						if(inviteGoldvipCount!=1) {
							inviteGoldvipCount++;
						}
					}
					invitePd.put("INVITE_GOLDVIP_COUNT", inviteGoldvipCount);
					//如果推荐的金牌会员数有3个，推荐的总人vip数量有500个 升级为 钻石店主
					if(inviteGoldvipCount>=3&&inviteCount>=500&&!VIPJEWEL.equals(VIPJEWEL)) {
						invitePd.put("ROLE", VIPJEWEL);
					}	
					//更新信息到数据库
					lshuserService.edit(invitePd);
				} 
				//更新用户表信息
				//设置角色为普通会员
				usPd.put("ROLE", VIP);
				usPd.put("CAKE_TICKET",  cakeTicket);
				usPd.put("GIFT_BEANS",  giftBeans);
				lshuserService.edit(usPd);
			}
			result=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	// TODO 购买经销商/代理商
	@RequestMapping("/buyJXS")
	public Object buyJXS() {
		boolean result = false;
		// 获取当前APP登录的用户编号
		Integer appUserId = Jurisdiction.getAppUserId();
		if (appUserId == null) {
			return result;
		}
		PageData pd=new PageData();
		try {
			pd.put("USER_ID",appUserId);
			PageData usPd=lshuserService.findById(pd);
			String roleName=(String) usPd.get("ROLE");
			if(StringUtils.isEmpty(roleName)) {
				PageData giftPd=new PageData();
				Integer giftBeans=getUserGiftBeans(appUserId,"GIFT_BEANS")+10000;
				giftPd.put("GIFTBEANS_ID",UUID.randomUUID().toString().replace("-", "").toLowerCase());
				giftPd.put("USER_ID", appUserId);
				giftPd.put("REMARKS", "购买升级经销商赠送");
				giftPd.put("STATUS","增加");
				giftPd.put("MODIFY_GIFT", "+10000");
				giftPd.put("GIFT_BEANS", giftBeans);
				giftPd.put("CREATE_DATE",Tools.date2Str(new Date()) );
				giftPd.put("CREATE_USER", appUserId);
				giftPd.put("DELETE_STATUS", 0);
				giftBeansManager.save(giftPd);
				//购买经销商时查询是否有推荐人，如果有就更新推荐记录的直接经销商的统计
				if(null!=usPd.get("PARENT_ID")) {
					String parendId=(String)usPd.get("PARENT_ID");
					//根据推荐人id查询推荐人信息
					PageData invitePds=new PageData();
					invitePds.put("USER_ID", parendId);
					PageData invitePd=lshuserService.findById(pd);
					String inviteRoleName=invitePd.getString("ROLE");
					Integer inviteCount=null==invitePd.get("INVITE_COUNT")?null:(Integer)invitePd.get("INVITE_COUNT");
					Integer invitejxsCount=null==invitePd.get("INVITE_JXS_COUNT")?null:(Integer)invitePd.get("INVITE_JXS_COUNT");
					if(invitejxsCount!=null) {
						invitejxsCount++;
					}else {
						invitejxsCount=1;
					}
					invitePd.put("INVITE_JXS_COUNT",invitejxsCount);
					//如果已经有推荐的下级了只再+1  否则就为1
					if(inviteCount!=null) {
						inviteCount++;
					}else {
						inviteCount=1;
					}
					invitePd.put("INVITE_COUNT",inviteCount);
					if(inviteCount>=50&&invitejxsCount>=5&&!inviteRoleName.equals(JXSQ)) {
						//升级为区经销商代理
						invitePd.put("ROLE", JXSQ);
					}else if(inviteCount>=200&&invitejxsCount>=10&&!inviteRoleName.equals(JXSX)) {
						//升级为县经销商代理
						invitePd.put("ROLE", JXSX);
					}else if(inviteCount>=1000&&invitejxsCount>=15&&!inviteRoleName.equals(JXSS)) {
						//升级为市经销商代理
						invitePd.put("ROLE", JXSS);
					}
					//更新信息到数据库
					lshuserService.edit(invitePd);
				}
				//更新用户表信息
				//设置角色为经销商
				usPd.put("ROLE", JXS);
				usPd.put("GIFT_BEANS",  giftBeans);
				lshuserService.edit(usPd);
				result = true;
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	// TODO 购买企商VIP
	@RequestMapping("/buyQSVIP")
	public Object buyQSVIP() {
		boolean result = false;
		// 获取当前APP登录的用户编号
		Integer appUserId = Jurisdiction.getAppUserId();
		if (appUserId == null) {
			return result;
		}
		PageData pd=new PageData();
		try {
			pd.put("USER_ID",appUserId);
			PageData usPd=lshuserService.findById(pd);
			String roleName=(String) usPd.get("ROLE");
			if(StringUtils.isEmpty(roleName)) {
				//插入金豆信息记录
				PageData goldPd=new PageData();
				Integer goldBeans=getUserGiftBeans(appUserId,"GOLD_BEANS")+10000;
				goldPd.put("GIFTBEANS_ID",UUID.randomUUID().toString().replace("-", "").toLowerCase());
				goldPd.put("USER_ID", appUserId);
				goldPd.put("REMARKS", "购买升级经销商赠送");
				goldPd.put("STATUS","增加");
				goldPd.put("MODIFY_GLODBEANS", "+10000");
				goldPd.put("MODIFY_GIFT", "+10000");
				goldPd.put("MODIFY_GIFT", "+10000");
				goldPd.put("GIFT_BEANS", goldBeans);
				goldPd.put("CREATE_DATE",Tools.date2Str(new Date()) );
				goldPd.put("CREATE_USER", appUserId);
				goldPd.put("DELETE_STATUS", 0);
				giftBeansManager.save(goldPd);
				//更新用户表信息
				//设置角色为企商VIP 并且更新金豆信息
				usPd.put("ROLE", JXS);
				usPd.put("GOLD_BEANS",  goldBeans);
				lshuserService.edit(usPd);
				result = true;
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 根据用户id获取礼豆数量 或者 金豆
	 * @param appUserId
	 * @return
	 */
	public Integer getUserGiftBeans(Integer appUserId,String type) {
		PageData pd=new PageData();
		pd.put("USER_ID", appUserId);
		try {
			PageData giftPd=lshuserService.findById(pd);
			String str=null!=giftPd.get(type)?(String)giftPd.get(type):null;
			if(null!=str&&!"".equals(str)) {
				return Integer.valueOf(str);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
