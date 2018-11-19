package com.szjm.controller.lsh.article;

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
import com.szjm.service.lsh.article.ArticleManager;

/** 
 * 说明：帖子管理
 * 创建人：
 * 创建时间：2018-07-11
 */
@Controller
@RequestMapping(value="/article")
public class ArticleController extends BaseController {
	
	String menuUrl = "article/list.do"; //菜单地址(权限用)
	@Resource(name="articleService")
	private ArticleManager articleService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Article");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.remove("ARTICLE_ID");
		articleService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/articleDel")
	@ResponseBody
	public void articleDel(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除Article");
		PageData pd = new PageData();
		pd = this.getPageData();
		String status = pd.get("status").toString();
		if("0".equals(status)){
			pd.put("DELETE_STATUS", 0);
			articleService.articleDel(pd);
		}else{
			pd.put("DELETE_STATUS", 1);
			articleService.articleDel(pd);
		}
	}
	
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Article");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = articleService.list(page);	//列出Article列表
		mv.setViewName("lsh/article/article_list");
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
		mv.setViewName("lsh/article/article_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**去帖子详情页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goArticleDetail")
	public ModelAndView goArticleDetail()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		List<PageData> pList = new ArrayList<PageData>();
		pd = this.getPageData();
		pd = articleService.articleDetail(pd);	//根据ID读取帖子
		if(pd.get("PATH") != null){
			String[] path = pd.get("PATH").toString().split(",");
			for (int i = 0; i < path.length; i++) {
				PageData pds1 = new PageData();
				pds1.put("PATH", path[i]);
				pList.add(pds1);
			}
		}
		mv.setViewName("lsh/article/article_detail");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		mv.addObject("pList", pList);
		return mv;
	}
	
	
	
	/**
	 * 帖子点赞
	 */
	@RequestMapping(value="/articlePraise")
	public ModelAndView articlePraise() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"帖子点赞");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("USER_ID", 110);
		pd.put("ARTICLE_ID", 1);
		//String sign = pd.getString("sign").toString();//1为点赞  0为取消点赞
		String sign = "1";
		PageData praise = articleService.isPraise(pd);//查询用户是否有点赞
		PageData findById = articleService.findById(pd);
		int praise_volume = Integer.parseInt(findById.get("PRAISE_VOLUME").toString());
		if("1".equals(sign)){
			if(praise == null){
				articleService.articlePraise(pd);//点赞表插入数据
				praise_volume = praise_volume+1;
				pd.put("PRAISE_VOLUME", praise_volume);	
				pd.remove("USER_ID");
				articleService.edit(pd);
				//pd.get("ARTICLE_ID");
			}
		}else if(praise != null){
			articleService.articlePraiseDel(pd);//删除点赞表数据
			praise_volume = praise_volume-1;
			pd.put("PRAISE_VOLUME", praise_volume);	
			pd.remove("USER_ID");
			articleService.edit(pd);
		}
		mv.addObject("praise_volume",praise_volume);
		return mv;
	}
	
	/**帖子评论列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/articleCommentList")
	public ModelAndView articleCommentList(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表ArticleComment");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = articleService.articleCommentList(page);	//列出ArticleComment列表
		mv.setViewName("lsh/article/articlecomment_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**帖子回复列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/goReply")
	public ModelAndView goReply(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表ArticleComment");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = articleService.queryReply(page);	//列出ArticleComment列表
		mv.setViewName("lsh/article/articlereply_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Article");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			articleService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出Article到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("备注1");	//1
		titles.add("备注2");	//2
		titles.add("备注3");	//3
		titles.add("备注4");	//4
		titles.add("备注5");	//5
		titles.add("备注6");	//6
		titles.add("备注7");	//7
		titles.add("备注8");	//8
		titles.add("备注9");	//9
		titles.add("备注10");	//10
		titles.add("备注11");	//11
		titles.add("备注12");	//12
		dataMap.put("titles", titles);
		List<PageData> varOList = articleService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("ARTICLE_ID").toString());	//1
			vpd.put("var2", varOList.get(i).getString("CONTENT"));	    //2
			vpd.put("var3", varOList.get(i).get("VISITOR_VOLUME").toString());	//3
			vpd.put("var4", varOList.get(i).get("USER_ID").toString());	//4
			vpd.put("var5", varOList.get(i).get("COLLECTION_VOLUME").toString());	//5
			vpd.put("var6", varOList.get(i).get("PRAISE_VOLUME").toString());	//6
			vpd.put("var7", varOList.get(i).getString("TITLE"));	    //7
			vpd.put("var8", varOList.get(i).getString("CREATE_DATE"));	    //8
			vpd.put("var9", varOList.get(i).getString("CREATE_USER"));	    //9
			vpd.put("var10", varOList.get(i).getString("UPDATE_DATE"));	    //10
			vpd.put("var11", varOList.get(i).getString("UPDATE_USER"));	    //11
			vpd.put("var12", varOList.get(i).get("DELETE_STATUS").toString());	//12
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
