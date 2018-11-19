package com.szjm.controller.lsh.award;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szjm.controller.base.BaseController;
import com.szjm.entity.Page;
import com.szjm.util.AppUtil;
import com.szjm.util.ObjectExcelView;
import com.szjm.util.PageData;
import com.szjm.util.Jurisdiction;
import com.szjm.util.Tools;
import com.szjm.service.lsh.award.AwardManager;
import com.szjm.service.lsh.goods.GoodsManager;

/**
 * 说明：奖品管理
 * 创建人：
 * 创建时间：2018-07-30
 */
@Controller
@RequestMapping(value="/award")
public class AwardController extends BaseController {

	String menuUrl = "award/list.do"; //菜单地址(权限用)
	@Resource(name="awardService")
	private AwardManager awardService;
	//商品
	@Resource(name="goodsService")
	private GoodsManager goodsService;
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Award");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//pd.put("AWARD_ID", this.get32UUID());	//主键
		pd.remove("AWARD_ID");
		pd.put("CREATE_DATE", Tools.date2Str(new Date()));	//创建日期
		pd.put("CREATE_USER", Jurisdiction.getUsername());	//创建人
		if("1".equals(pd.get("TYPE").toString())){
			pd.put("GOODS_ID",null);
		}else{
			pd.put("PICTURE_ID",null);
			pd.put("AWARD_NAME",null);
		}
		awardService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}

	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除Award");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		awardService.delete(pd);
		out.write("success");
		out.close();
	}

	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Award");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("UPDATE_DATE", Tools.date2Str(new Date()));	//创建日期
		pd.put("UPDATE_USER", Jurisdiction.getUsername());	//创建人
		if("1".equals(pd.get("TYPE").toString())){
			pd.put("GOODS_ID",null);
		}else{
			pd.put("PICTURE_ID",null);
			pd.put("AWARD_NAME",null);
		}
		awardService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Award");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = awardService.list(page);	//列出Award列表
		mv.setViewName("lsh/award/award_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}

	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("GOODS_POSITION", 3);
		List<PageData> goodsList=goodsService.listAll(pd);

		mv.setViewName("lsh/award/award_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		mv.addObject("goodsList", goodsList);
		return mv;
	}

	 /**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("GOODS_POSITION", 3);
		List<PageData> goodsList=goodsService.listAll(pd);
		pd = awardService.findById(pd);	//根据ID读取
		mv.setViewName("lsh/award/award_edit");
		mv.addObject("msg", "edit");
		mv.addObject("goodsList", goodsList);
		mv.addObject("pd", pd);
		return mv;
	}

	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Award");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			awardService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}

	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出Award到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("备注1");	//1
		titles.add("创建时间");	//2
		titles.add("备注3");	//3
		titles.add("备注4");	//4
		titles.add("备注5");	//5
		titles.add("图片编号");	//6
		titles.add("商品编号");	//7
		titles.add("奖品名称");	//8
		titles.add("奖品类型");	//9
		titles.add("中奖概率");	//10
		dataMap.put("titles", titles);
		List<PageData> varOList = awardService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("AWARD_ID").toString());	//1
			vpd.put("var2", varOList.get(i).getString("CREATE_DATE"));	    //2
			vpd.put("var3", varOList.get(i).getString("CREATE_USER"));	    //3
			vpd.put("var4", varOList.get(i).getString("UPDATE_DATE"));	    //4
			vpd.put("var5", varOList.get(i).getString("UPDATE_USER"));	    //5
			vpd.put("var6", varOList.get(i).getString("PICTURE_ID"));	    //6
			vpd.put("var7", varOList.get(i).get("GOODS_ID").toString());	//7
			vpd.put("var8", varOList.get(i).getString("AWARD_NAME"));	    //8
			vpd.put("var9", varOList.get(i).get("TYPE").toString());	//9
			vpd.put("var10", varOList.get(i).get("CHANCE").toString());	//10
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
