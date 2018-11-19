package com.szjm.controller.lshapi;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.szjm.controller.lshapp.BaseAppController;
import com.szjm.entity.Page;
import com.szjm.service.lsh.article.ArticleManager;
import com.szjm.service.lsh.collection.CollectionManager;
import com.szjm.util.Jurisdiction;
import com.szjm.util.PageData;
import com.szjm.util.Tools;

/**
 * API接口类
 * @author
 *
 */
@Controller
@RequestMapping(value="app/Api")
public class LshArticleApi extends BaseAppController {
	@Resource(name = "articleService")
	private ArticleManager articleService;

	@Resource(name = "collectionService")
	private CollectionManager collectionService;



	/**
	 * 帖子列表
	 */
	@RequestMapping(value = "/user_article")
	public void user_article(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";
		PageData pd = new PageData();
		pd = this.getPageData();
		Page page=new Page();
		Integer user_id = Jurisdiction.getAppUserId();
		pd.put("USER_ID", user_id);
		if(pd.get("currentPage") !=null ){
			page.setCurrentPage(Integer.parseInt(pd.get("currentPage").toString()));
		}
		page.setPd(pd);
		Map<String,Object> content=new HashMap<String,Object>();
		List<Object> listB=new ArrayList<Object>();
		List<PageData> varList = articleService.listArticle(page); // 列出Article列表
		for (int i = 0; i < varList.size(); i++) {
			Map<String,Object> contentNew=new HashMap<String,Object>();
			List<Object> listM=new ArrayList<Object>();
			if (varList.get(i).get("ARTICLE_PATH") != null) {
				String article_path = varList.get(i).get("ARTICLE_PATH")
						.toString();
				String[] split = article_path.split(",");
				for (int j = 0; j < split.length; j++) {
					//Map<String,Object> contentNewP=new HashMap<String,Object>();
					//contentNew.put("ARTICLE_PATH", basePath+"uploadFiles/uploadImgs/"+split[j]);
					listM.add(basePath+"uploadFiles/uploadImgs/"+split[j]);
				}
			}
			contentNew.put("PATH", basePath+"uploadFiles/uploadImgs/"+varList.get(i).get("PATH"));//用户头像
			contentNew.put("CREATE_USER", varList.get(i).get("CREATE_USER"));//昵称
			contentNew.put("CONTENT", varList.get(i).get("CONTENT"));
			contentNew.put("CREATE_DATE", varList.get(i).get("CREATE_DATE"));
			contentNew.put("articlePathList", listM);
			contentNew.put("PRAISE_VOLUME", varList.get(i).get("PRAISE_VOLUME"));//点赞量
			contentNew.put("VISITOR_VOLUME", varList.get(i).get("VISITOR_VOLUME"));//浏览量
			contentNew.put("COLLECTION_VOLUME", varList.get(i).get("COLLECTION_VOLUME"));//收藏量
			contentNew.put("COMMENT_COUNT", varList.get(i).get("COMMENT_COUNT"));//评论量
			contentNew.put("COLLID", varList.get(i).get("COLLID"));//是否收藏
			contentNew.put("PRAISEID", varList.get(i).get("PRAISEID"));//是否点赞
			contentNew.put("ARTICLE_ID", varList.get(i).get("ARTICLE_ID"));
			contentNew.put("SEX", varList.get(i).get("SEX"));
			listB.add(contentNew);
		}
		content.put("articleList", listB);
		content.put("pages", page.getTotalPage());
		content.put("currentPage", page.getCurrentPage());
		content.put("pageSize", page.getShowCount());
		WriteClientMessage(response,"0", "成功", content);
	}




	/**
	 * 浏览量
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/articleDetail")
	@ResponseBody
	public void goArticleDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> visitorCount=new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = articleService.articleDetail(pd);
		int visitor_volume = Integer.parseInt(pd.get("VISITOR_VOLUME")
				.toString());
		visitor_volume = visitor_volume + 1;
		visitorCount.put("VISITOR_VOLUME", visitor_volume);
		WriteClientMessage(response,"0", "成功", visitorCount);
	}
	/**
	 * 帖子点赞
	 */
	@RequestMapping(value = "/articlePraise")
	@ResponseBody
	public void articlePraise(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer user_id = Jurisdiction.getAppUserId();
		Map<String,Object> praiseCount=new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("USER_ID", user_id);
		PageData praise = articleService.isPraise(pd);// 查询用户是否有点赞
		PageData findById = articleService.findById(pd);
		int praise_volume = Integer.parseInt(findById.get("PRAISE_VOLUME").toString());
		if (praise == null) {
			articleService.articlePraise(pd);// 点赞表插入数据
			praise_volume = praise_volume + 1;
			pd.put("PRAISE_VOLUME", praise_volume);
			pd.remove("USER_ID");
			articleService.edit(pd);
		}
		praiseCount.put("PRAISE_VOLUME", praise_volume);
		WriteClientMessage(response,"0", "成功", praiseCount);
	}

	/**
	 * 帖子收藏
	 */
	@RequestMapping(value = "/articleCollection")
	@ResponseBody
	public void articleCollection(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> collectionCount=new HashMap<String,Object>();
		Integer appUserId = Jurisdiction.getAppUserId();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("COLLECTION_TYPE", 1);
		pd.put("USER_ID", appUserId);
		String sign = pd.getString("SIGN").toString();
		PageData findById = articleService.findById(pd);
		int collection_volume = Integer.parseInt(findById.get("COLLECTION_VOLUME").toString());// 帖子收藏量
		if ("0".equals(sign)) {
			pd.put("CREATE_DATE", Tools.date2Str(new Date())); // 创建日期
			collectionService.save(pd);// 收藏表插入数据
			collection_volume = collection_volume + 1;
			pd.put("COLLECTION_VOLUME", collection_volume);
			pd.remove("CREATE_DATE");
			pd.remove("USER_ID");
			articleService.edit(pd);
			// pd.get("ARTICLE_ID");
			collectionCount.put("COLLECTION_VOLUME", collection_volume);
		} else {
			collectionService.delete(pd);// 删除收藏表数据
			collection_volume = collection_volume - 1;
			pd.put("COLLECTION_VOLUME", collection_volume);
			pd.remove("CREATE_DATE");
			pd.remove("USER_ID");
			articleService.edit(pd);
			collectionCount.put("COLLECTION_VOLUME", collection_volume);
		}
		WriteClientMessage(response,"0", "成功", collectionCount);
	}
	@RequestMapping(value = "/uploadFile")
	@ResponseBody
	public void uploadFile(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("headImage") MultipartFile[] pictureFile,String fsdf) throws Exception {
		//原始文件名称
		String pictureFile_name =  pictureFile[0].getOriginalFilename();
		//新文件名称
		String newFileName = UUID.randomUUID().toString()+pictureFile_name.substring(pictureFile_name.lastIndexOf("."));

		//上传图片
		File uploadPic = new java.io.File("F:/"+newFileName);
		if(!uploadPic.exists()){
			uploadPic.mkdirs();
		}
		//向磁盘写文件
		pictureFile[0].transferTo(uploadPic);
		WriteClientMessage(response,"0", "成功", "url");
	}
}
