package com.szjm.service.lsh.article.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.szjm.dao.DaoSupport;
import com.szjm.entity.Page;
import com.szjm.util.PageData;
import com.szjm.service.lsh.article.ArticleManager;

/**
 * 说明： 帖子管理
 * 创建人：
 * 创建时间：2018-07-11
 * @version
 */
@Service("articleService")
public class ArticleService implements ArticleManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("ArticleMapper.save", pd);
	}

	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void articleDel(PageData pd)throws Exception{
		dao.update("ArticleMapper.articleDel", pd);
	}

	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("ArticleMapper.edit", pd);
	}

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ArticleMapper.datalistPage", page);
	}
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> appArticleList(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ArticleMapper.datalistPage5", page);
	}

	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ArticleMapper.listAll", pd);
	}

	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ArticleMapper.findById", pd);
	}

	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ArticleMapper.deleteAll", ArrayDATA_IDS);
	}


	/**查看帖子详情
	 * @param pd
	 * @throws Exception
	 */
	public PageData articleDetail(PageData pd) throws Exception {
		return (PageData)dao.findForObject("ArticleMapper.articleDetail", pd);
	}


	/**
	 * 帖子点赞
	 */
	@Override
	public int articlePraise(PageData pd) throws Exception {

		return (int) dao.save("ArticleMapper.articlePraise", pd);

	}
	/**
	 * 查询用户是否点赞过该帖子
	 */
	@Override
	public PageData isPraise(PageData pd) throws Exception {
		return (PageData)dao.findForObject("ArticleMapper.isPraise", pd);
	}
	/**
	 * 取消点赞
	 */
	@Override
	public void articlePraiseDel(PageData pd) throws Exception {
		dao.delete("ArticleMapper.articlePraiseDel", pd);
	}

	/**评论列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> articleCommentList(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ArticleMapper.datalistPage1", page);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> queryReply(Page page) throws Exception {
		return (List<PageData>)dao.findForList("ArticleMapper.datalistPage2", page);
	}
	/**
	 * app
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listArticle(Page page) throws Exception {
		return (List<PageData>)dao.findForList("ArticleMapper.datalistPage3", page);
	}

	@SuppressWarnings("unchecked")
	public List<PageData> appQueryReply(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("ArticleMapper.appQueryReply", pd);
	}
	/**
	 * 我的回复列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> myCommentList(Page page) throws Exception {
		return (List<PageData>)dao.findForList("ArticleMapper.datalistPage6", page);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> myListArticle(Page page) throws Exception {
		return (List<PageData>)dao.findForList("ArticleMapper.datalistPage7", page);
	}

}

