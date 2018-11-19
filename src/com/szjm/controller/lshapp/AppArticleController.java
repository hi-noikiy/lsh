package com.szjm.controller.lshapp;

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
import com.szjm.service.lsh.articlecomment.ArticleCommentManager;
import com.szjm.service.lsh.articlepicture.ArticlePictureManager;
import com.szjm.service.lsh.collection.CollectionManager;
import com.szjm.service.lsh.lshuser.LshUserManager;

/**
 * 说明：帖子管理 创建人： 创建时间：2018-07-11
 */
@Controller
@RequestMapping(value = "lshapp/article")
public class AppArticleController extends BaseController {

	@Resource(name = "articleService")
	private ArticleManager articleService;

	@Resource(name = "collectionService")
	private CollectionManager collectionService;

	@Resource(name = "articlecommentService")
	private ArticleCommentManager articlecommentService;

	@Resource(name = "lshuserService")
	private LshUserManager lshuserService;// 用户

	@Resource(name = "articlepictureService")
	private ArticlePictureManager articlepictureService;

	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public String save() throws Exception {
		Integer appUserId = Jurisdiction.getAppUserId();
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		PageData pds = new PageData();

		pds.put("USER_ID", appUserId);
		PageData findById = lshuserService.findById(pds);
		String nick_name = findById.get("NICK_NAME").toString();
		pd = this.getPageData();
		pd.put("VISITOR_VOLUME", 0);
		pd.put("USER_ID", appUserId);
		pd.put("COLLECTION_VOLUME", 0);
		pd.put("PRAISE_VOLUME", 0);
		pd.put("CREATE_DATE", Tools.date2Str(new Date()));
		pd.put("CREATE_USER", nick_name);
		pd.put("DELETE_STATUS", 0);
		articleService.save(pd);
		String article_id = pd.get("article_id").toString();
		List<PageData> picture = new ArrayList<PageData>();
		if (pd.get("PICTURE_ID") != null && !"".equals(pd.get("PICTURE_ID"))) {
			String picture_id = pd.get("PICTURE_ID").toString();
			String[] split = picture_id.split(",");
			for (int i = 0; i < split.length; i++) {
				PageData ps = new PageData();
				ps.put("PICTURE_ID", split[i]);
				ps.put("ARTICLE_ID", article_id);
				picture.add(ps);
			}
			articlepictureService.listPicture(picture);
		}

		return "1";
	}

	/**
	 * 删除
	 * 
	 * @param out
	 * @throws Exception
	 */
	/*
	 * @RequestMapping(value="/apparticleDel")
	 * 
	 * @ResponseBody public void articleDel(PrintWriter out) throws Exception{
	 * logBefore(logger, Jurisdiction.getUsername()+"删除Article"); PageData pd =
	 * new PageData(); pd = this.getPageData(); String status =
	 * pd.get("status").toString(); if("0".equals(status)){
	 * pd.put("DELETE_STATUS", 0); articleService.articleDel(pd); }else{
	 * pd.put("DELETE_STATUS", 1); articleService.articleDel(pd); } }
	 */

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		Integer appUserId = Jurisdiction.getAppUserId();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("USER_ID", appUserId);
		page.setPd(pd);
		page.setShowCount(10);
		List<PageData> varList = articleService.listArticle(page); // 列出Article列表

		for (int i = 0; i < varList.size(); i++) {
			List<PageData> articlePathList = new ArrayList<PageData>();
			if (varList.get(i).get("ARTICLE_PATH") != null) {
				String article_path = varList.get(i).get("ARTICLE_PATH")
						.toString();
				String[] split = article_path.split(",");
				for (int j = 0; j < split.length; j++) {
					PageData pds = new PageData();
					pds.put("ARTICLE_PATH", split[j]);
					articlePathList.add(pds);
					varList.get(i).put("articlePathList", articlePathList);
				}
			}
		}
		mv.setViewName("lshapp/article_home");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("totalPage", page.getTotalPage());
		return mv;
	}

	@RequestMapping(value = "/ajaxList")
	public ModelAndView ajaxList(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		Integer appUserId = Jurisdiction.getAppUserId();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("USER_ID", appUserId);
		page.setShowCount(10);
		page.setPd(pd);
		List<PageData> varList = articleService.listArticle(page); // 列出Article列表

		for (int i = 0; i < varList.size(); i++) {
			List<PageData> articlePathList = new ArrayList<PageData>();
			if (varList.get(i).get("ARTICLE_PATH") != null) {
				String article_path = varList.get(i).get("ARTICLE_PATH")
						.toString();
				String[] split = article_path.split(",");
				for (int j = 0; j < split.length; j++) {
					PageData pds = new PageData();
					pds.put("ARTICLE_PATH", split[j]);
					articlePathList.add(pds);
					varList.get(i).put("articlePathList", articlePathList);
				}
			}
		}
		mv.setViewName("lshapp/ajax_article");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 去新增页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goSendArticle")
	public ModelAndView goSendArticle() throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("lshapp/send_article");
		return mv;
	}

	/**
	 * 去帖子详情页面
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/articleDetail")
	public ModelAndView goArticleDetail(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		/*
		 * PageData findById = articleService.findById(pd); int visitor_volume =
		 * Integer.parseInt(findById.get("VISITOR_VOLUME").toString());
		 * visitor_volume = visitor_volume+1; pd.put("VISITOR_VOLUME",
		 * visitor_volume);
		 */
		List<PageData> pList = new ArrayList<PageData>();
		pd = this.getPageData();
		pd = articleService.articleDetail(pd); // 根据ID读取帖子
		int visitor_volume = Integer.parseInt(pd.get("VISITOR_VOLUME")
				.toString());
		visitor_volume = visitor_volume + 1;
		pd.put("VISITOR_VOLUME", visitor_volume);
		articleService.edit(pd);
		if (pd.get("PATH") != null) {
			String[] path = pd.get("PATH").toString().split(",");
			for (int i = 0; i < path.length; i++) {
				PageData pds1 = new PageData();
				pds1.put("PATH", path[i]);
				pList.add(pds1);
			}
		}
		page.setShowCount(5);
		page.setPd(pd);
		List<PageData> commentList = articleService.articleCommentList(page); // 列出评论列表
		for (int i = 0; i < commentList.size(); i++) {
			String articlecomment_id = commentList.get(i)
					.get("ARTICLECOMMENT_ID").toString();
			pd.put("TOP_PARENT_ID", articlecomment_id);
			List<PageData> ReplyList = articleService.appQueryReply(pd);
			 /*PageData  replyLists =new PageData ();
			replyLists .put( "replyLists" , ReplyList );*/
			commentList.get(i).put("replyLists", ReplyList);
		}

		mv.setViewName("lshapp/article_detail");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		mv.addObject("pList", pList);
		mv.addObject("commentList", commentList);
		mv.addObject("totalPage", page.getTotalPage());
		return mv;
	}

	@RequestMapping(value = "/ajaxcomment")
	public ModelAndView ajaxcomment(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setShowCount(5);
		page.setPd(pd);
		List<PageData> commentList = articleService.articleCommentList(page); // 列出评论列表
		for (int i = 0; i < commentList.size(); i++) {
			String articlecomment_id = commentList.get(i)
					.get("ARTICLECOMMENT_ID").toString();
			pd.put("TOP_PARENT_ID", articlecomment_id);
			List<PageData> ReplyList = articleService.appQueryReply(pd); // 列出A回复列表

			commentList.get(i).put("replyLists", ReplyList);
		}
		mv.setViewName("lshapp/ajax_article_comment");
		mv.addObject("pd", pd);
		mv.addObject("commentList", commentList);
		mv.addObject("totalPage", page.getTotalPage());
		return mv;
	}

	/**
	 * 帖子点赞
	 */
	@RequestMapping(value = "/articlePraise")
	@ResponseBody
	public Integer articlePraise() throws Exception {
		Integer appUserId = Jurisdiction.getAppUserId();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("USER_ID", appUserId);
		PageData praise = articleService.isPraise(pd);// 查询用户是否有点赞
		PageData findById = articleService.findById(pd);
		int praise_volume = Integer.parseInt(findById.get("PRAISE_VOLUME")
				.toString());
		if (praise == null) {
			articleService.articlePraise(pd);// 点赞表插入数据
			praise_volume = praise_volume + 1;
			pd.put("PRAISE_VOLUME", praise_volume);
			pd.remove("USER_ID");
			articleService.edit(pd);
		}
		return praise_volume;
	}

	/**
	 * 帖子收藏 取消收藏
	 * 
	 */
	@RequestMapping(value = "/articleCollection")
	@ResponseBody
	public Integer articleCollection() throws Exception {
		Integer appUserId = Jurisdiction.getAppUserId();
		// ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("USER_ID", appUserId);
		pd.put("COLLECTION_TYPE", 1);
		String sign = pd.getString("SIGN").toString();
		PageData findById = articleService.findById(pd);

		int collection_volume = Integer.parseInt(findById.get(
				"COLLECTION_VOLUME").toString());// 帖子收藏量
		if ("0".equals(sign)) {
			pd.put("CREATE_DATE", Tools.date2Str(new Date())); // 创建日期
			collectionService.save(pd);// 收藏表插入数据
			collection_volume = collection_volume + 1;
			pd.put("COLLECTION_VOLUME", collection_volume);
			pd.remove("CREATE_DATE");
			pd.remove("USER_ID");
			articleService.edit(pd);
			// pd.get("ARTICLE_ID");
			return collection_volume;

		} else {
			collectionService.delete(pd);// 删除收藏表数据
			collection_volume = collection_volume - 1;
			pd.put("COLLECTION_VOLUME", collection_volume);
			pd.remove("CREATE_DATE");
			pd.remove("USER_ID");
			articleService.edit(pd);
			return collection_volume;
		}

	}

	/**
	 * 帖子评论
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/comment")
	@ResponseBody
	public String articleCommentList() throws Exception {
		Integer appUserId = Jurisdiction.getAppUserId();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("CREATE_DATE", Tools.date2Str(new Date()));
		pd.put("DELETE_STATUS", 0);
		pd.put("USER_ID", appUserId);
		articlecommentService.save(pd);
		System.out.println(pd);
		
		return "1";	
	}

	/**
	 * 帖子回复列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	/*
	 * @RequestMapping(value="/appgoReply") public ModelAndView goReply(Page
	 * page) throws Exception{ logBefore(logger,
	 * Jurisdiction.getUsername()+"列表ArticleComment");
	 * //if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
	 * //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码) ModelAndView mv =
	 * this.getModelAndView(); PageData pd = new PageData(); pd =
	 * this.getPageData(); String keywords = pd.getString("keywords"); //关键词检索条件
	 * if(null != keywords && !"".equals(keywords)){ pd.put("keywords",
	 * keywords.trim()); } page.setPd(pd); List<PageData> varList =
	 * articleService.queryReply(page); //列出ArticleComment列表
	 * mv.setViewName("lsh/article/articlereply_list"); mv.addObject("varList",
	 * varList); mv.addObject("pd", pd);
	 * mv.addObject("QX",Jurisdiction.getHC()); //按钮权限 return mv; }
	 * 
	 * 
	 * @InitBinder public void initBinder(WebDataBinder binder){ DateFormat
	 * format = new SimpleDateFormat("yyyy-MM-dd");
	 * binder.registerCustomEditor(Date.class, new
	 * CustomDateEditor(format,true)); }
	 */
}
