package com.szjm.service.lsh.beanrecharge.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.szjm.dao.DaoSupport;
import com.szjm.entity.Page;
import com.szjm.util.PageData;
import com.szjm.service.lsh.beanrecharge.BeanRechargeManager;

/** 
 * 说明： 金豆充值记录
 * 创建人：
 * 创建时间：2018-11-21
 * @version
 */
@Service("beanrechargeService")
public class BeanRechargeService implements BeanRechargeManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("BeanRechargeMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("BeanRechargeMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("BeanRechargeMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("BeanRechargeMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("BeanRechargeMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("BeanRechargeMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("BeanRechargeMapper.deleteAll", ArrayDATA_IDS);
	}

	/**根据用户id查询列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> listAllByUserId(PageData pd) throws Exception {
	return (List<PageData>)dao.findForList("BeanRechargeMapper.listAllByUserId", pd);
	}
	
	/**根据充值编号获取数据
	 * @param rechargeNumber 充值编号
	 * @throws Exception
	 */
	public PageData findRechargeNumber(PageData rechargeNumber)throws Exception {
		return (PageData) dao.findForObject("BeanRechargeMapper.findRechargeNumber", rechargeNumber);
	}
	
}

