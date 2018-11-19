package com.szjm.service.lsh.article;

import java.util.List;
import com.szjm.entity.Page;
import com.szjm.util.PageData;

/**
 * 说明： 帖子管理接口
 * 创建人：
 * 创建时间：2018-07-11
 * @version
 */
public interface ArticleManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;

	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void articleDel(PageData pd)throws Exception;

	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;

	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> appArticleList(Page page)throws Exception;

	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;

	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;

	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;

	/**查看帖子详情
	 * @param pd
	 * @throws Exception
	 */
	public PageData articleDetail(PageData pd)throws Exception;;



	/**
	 * 帖子点赞
	 * @param pd
	 * @throws Exception
	 */
	public int articlePraise(PageData pd)throws Exception;
	/**
	 * 查询用户是否点赞过该帖子
	 * @param pd
	 */
	public PageData isPraise(PageData pd)throws Exception;
	/**
	 * 删除点赞表数据
	 * @param pd
	 * @throws Exception
	 */
	public void articlePraiseDel(PageData pd)throws Exception;
	/**评论列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> articleCommentList(Page page)throws Exception;
	/**
	 * 回复列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> queryReply(Page page)throws Exception;
	/**
	 * app帖子列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listArticle(Page page)throws Exception;

	public List<PageData> appQueryReply(PageData pd)throws Exception;
	/**
	 * 回复列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> myCommentList(Page page)throws Exception;
	/**
	 * 我的帖子
	 * @param page
	 * @return
	 * @throws Exception 
	 */
	public List<PageData> myListArticle(Page page) throws Exception;
}

