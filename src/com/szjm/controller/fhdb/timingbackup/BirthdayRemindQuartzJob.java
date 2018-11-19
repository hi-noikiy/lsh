package com.szjm.controller.fhdb.timingbackup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.szjm.controller.base.BaseController;
import com.szjm.entity.Page;
import com.szjm.service.lsh.birthday.impl.BirthdayService;
import com.szjm.service.lsh.lshuser.impl.LshUserService;
import com.szjm.service.lsh.memorialday.impl.MemorialDayService;
import com.szjm.util.PageData;
import com.szjm.util.SpringUtil;
import com.szjm.util.lsh.MessagePushUtil;

public class BirthdayRemindQuartzJob extends BaseController implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		System.out.println("生日提醒定时已启动........");
		remindUserBirthday(2);
		remindBirthday(2);
		memorial(2);
	}
	private void remindUserBirthday(int showCount){
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String today=sdf.format(new Date());
			int currentPage=1;
			int totalPage=1;
			LshUserService lshuserService=(LshUserService) SpringUtil.getObject("lshuserService");
			PageData pd=new PageData();
			for(;currentPage<=totalPage;currentPage++){
				Page page=new Page();
				page.setPd(pd);
				page.setCurrentPage(currentPage);
				page.setShowCount(showCount);
				List<PageData> userlist=lshuserService.list2(page);
				totalPage=page.getTotalPage();
				for(PageData temp:userlist){
					try {
						//提前提醒天数
						String advance_date_count=temp.get("ADVANCE_DATE_COUNT").toString();
						String[] split = advance_date_count.split(",");
						//如果提醒新历
						if("0".equals(temp.get("REMIND_TYPE").toString())){
							Integer next_sb_days=Integer.valueOf(temp.get("NEXT_SB_DAYS").toString());
							for (int i = 0; i < split.length; i++) {
								if(next_sb_days-Integer.parseInt(split[i])==0){
									//发极光推送
									sendPushUser(temp.get("TOKEN"), temp.get("NICK_NAME"), temp.get("NEXT_SOLAR_BRITHDAY"), 0);
								}
							}
						}else if("1".equals(temp.get("REMIND_TYPE").toString())){//如果提醒农历
							Integer next_lb_days=Integer.valueOf(temp.get("NEXT_LB_DAYS").toString());
							for (int i = 0; i < split.length; i++) {
								if(next_lb_days-Integer.parseInt(split[i])==0){
									//发极光推送
									sendPushUser(temp.get("TOKEN"), temp.get("NICK_NAME"), temp.get("NEXT_LUNAR_BRITHDAY"), 1);
								}
							}							
						}else if("2".equals(temp.get("REMIND_TYPE").toString())){//都提醒
							Integer next_sb_days=Integer.valueOf(temp.get("NEXT_SB_DAYS").toString());
							for (int i = 0; i < split.length; i++) {
								if(next_sb_days-Integer.parseInt(split[i])==0){
									//发极光推送
									sendPushUser(temp.get("TOKEN"), temp.get("NICK_NAME"), temp.get("NEXT_SOLAR_BRITHDAY"), 0);
								}else{
									Integer next_lb_days=Integer.valueOf(temp.get("NEXT_LB_DAYS").toString());
									if(next_lb_days-Integer.parseInt(split[i])==0){//农历
										//发极光推送
										sendPushUser(temp.get("TOKEN"), temp.get("NICK_NAME"), temp.get("NEXT_LUNAR_BRITHDAY"), 1);
									}
								}
							}
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void remindBirthday(int showCount){
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String today=sdf.format(new Date());
			int currentPage=1;
			int totalPage=1;
			BirthdayService birthdayService=(BirthdayService) SpringUtil.getObject("birthdayService");
			PageData pd=new PageData();
			for(;currentPage<=totalPage;currentPage++){
				Page page=new Page();
				page.setPd(pd);
				page.setCurrentPage(currentPage);
				page.setShowCount(showCount);
				List<PageData> userlist=birthdayService.list2(page);
				totalPage=page.getTotalPage();
				for(PageData temp:userlist){
					try {
						//提前提醒天数
						String advance_date_count=temp.get("ADVANCE_DATE_COUNT").toString();
						String[] split = advance_date_count.split(",");
						//如果提醒新历
						if("0".equals(temp.get("REMIND_TYPE").toString())){
							Integer next_sb_days=Integer.valueOf(temp.get("NEXT_SB_DAYS").toString());
							for (int i = 0; i < split.length; i++) {
								if(next_sb_days-Integer.parseInt(split[i])==0){
									//发极光推送
									sendPushFriend(temp.get("TOKEN"), temp.get("NAME"), temp.get("NEXT_SOLAR_BRITHDAY"), 0);
								}
							}
							
						}else if("1".equals(temp.get("REMIND_TYPE").toString())){//如果提醒农历
							Integer next_lb_days=Integer.valueOf(temp.get("NEXT_LB_DAYS").toString());
							for (int i = 0; i < split.length; i++) {
								if(next_lb_days-Integer.parseInt(split[i])==0){
									//发极光推送
									sendPushFriend(temp.get("TOKEN"), temp.get("NAME"), temp.get("NEXT_LUNAR_BRITHDAY"), 1);
								}
							}
						}else if("2".equals(temp.get("REMIND_TYPE").toString())){//都提醒
							Integer next_sb_days=Integer.valueOf(temp.get("NEXT_SB_DAYS").toString());
							for (int i = 0; i < split.length; i++) {															
								if(next_sb_days-Integer.parseInt(split[i])==0){//新历
									//发极光推送
									sendPushFriend(temp.get("TOKEN"), temp.get("NAME"), temp.get("NEXT_SOLAR_BRITHDAY"), 0);
								}else{
									Integer next_lb_days=Integer.valueOf(temp.get("NEXT_LB_DAYS").toString());
									if(next_lb_days-Integer.parseInt(split[i])==0){//农历
										//发极光推送
										sendPushFriend(temp.get("TOKEN"), temp.get("NAME"), temp.get("NEXT_LUNAR_BRITHDAY"), 1);
									}
								}
							}
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void memorial(int showCount){
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String today=sdf.format(new Date());
			int currentPage=1;
			int totalPage=1;
			MemorialDayService memorialDayService=(MemorialDayService) SpringUtil.getObject("memorialdayService");
			PageData pd=new PageData();
			for(;currentPage<=totalPage;currentPage++){
				Page page=new Page();
				page.setPd(pd);
				page.setCurrentPage(currentPage);
				page.setShowCount(showCount);
				List<PageData> memorialDayList=memorialDayService.list2(page);
				totalPage=page.getTotalPage();
				for(PageData temp:memorialDayList){
					try {
						//如果离纪念日天数为0天
						if("0".equals(temp.get("DAYS")) || Integer.parseInt(temp.get("DAYS").toString())==0 ){
							//发极光推送 推送人ID  纪念名称  纪念时间   0为新历日期
							sendPushMemorial(temp.get("TOKEN"), temp.get("NAME"), temp.get("COMMEMORATE_DATE"), 0);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 *
	 * @param user_id 推送用户编号
	 * @param name 推送生日人姓名
	 * @param birthday 生日日期
	 * @param remind_type 生日类型，0为新历，1为农历
	 */
	private void sendPushUser(Object token,Object name,Object birthday,int remind_type){
		MessagePushUtil.MessagePush(String.valueOf(token), "741b44999dc018521d9c1fdf", "3f22f8ccc3e8a18d41f6ad4e", ""+String.valueOf(birthday)+"是您的生日哦！", "生日快乐", null);
	}	
	private void sendPushFriend(Object token,Object name,Object birthday,int remind_type){
		MessagePushUtil.MessagePush(String.valueOf(token), "741b44999dc018521d9c1fdf", "3f22f8ccc3e8a18d41f6ad4e", ""+String.valueOf(birthday)+"有好友"+String.valueOf(name)+"生日哦！", "生日提醒", null);
	}	
	private void sendPushMemorial(Object token,Object name,Object birthday,int remind_type){
		MessagePushUtil.MessagePush(String.valueOf(token), "741b44999dc018521d9c1fdf", "3f22f8ccc3e8a18d41f6ad4e", ""+String.valueOf(birthday)+"您的"+String.valueOf(name)+"纪念日到啦", "纪念日提醒", null);
	}
}
