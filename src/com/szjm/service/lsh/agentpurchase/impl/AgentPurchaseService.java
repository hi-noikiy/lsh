package com.szjm.service.lsh.agentpurchase.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.szjm.dao.DaoSupport;
import com.szjm.entity.Page;
import com.szjm.util.PageData;
import com.szjm.service.lsh.agentpurchase.AgentPurchaseManager;

/** 
 * 说明： 代理购买记录
 * 创建人：
 * 创建时间：2018-11-21
 * @version
 */
@Service("agentpurchaseService")
public class AgentPurchaseService implements AgentPurchaseManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("AgentPurchaseMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("AgentPurchaseMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("AgentPurchaseMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("AgentPurchaseMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AgentPurchaseMapper.listAll", pd);
	}
	
	/**根据用户id查询列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> listAllByUserId(PageData pd) throws Exception {
	return (List<PageData>)dao.findForList("AgentPurchaseMapper.listAllByUserId", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AgentPurchaseMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("AgentPurchaseMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**根据购买编号获取数据
	 * @param purchaseNumber 购买编号
	 * @throws Exception
	 */
	public PageData findPurchaseNumber(PageData purchaseNumber)throws Exception{
		return (PageData) dao.findForObject("AgentPurchaseMapper.findPurchaseNumber", purchaseNumber);
	}
	
}

