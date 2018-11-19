package com.szjm.service.lsh.awardrecord.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.szjm.dao.DaoSupport;
import com.szjm.entity.Page;
import com.szjm.util.PageData;
import com.szjm.service.lsh.awardrecord.AwardRecordManager;

/**
 * 说明： 抽奖记录管理
 * 创建人：
 * 创建时间：2018-07-30
 * @version
 */
@Service("awardrecordService")
public class AwardRecordService implements AwardRecordManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("AwardRecordMapper.save", pd);
	}

	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("AwardRecordMapper.delete", pd);
	}

	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("AwardRecordMapper.edit", pd);
	}

	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void editOrder(PageData pd)throws Exception{
		dao.update("AwardRecordMapper.editOrder", pd);
	}

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("AwardRecordMapper.datalistPage", page);
	}
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> appAwardRecordList(Page page)throws Exception{
		return (List<PageData>)dao.findForList("AwardRecordMapper.datalistPage1", page);
	}

	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AwardRecordMapper.listAll", pd);
	}

	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AwardRecordMapper.findById", pd);
	}
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByIdLoginAndB(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AwardRecordMapper.findByIdLoginAndB", pd);
	}

	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("AwardRecordMapper.deleteAll", ArrayDATA_IDS);
	}

}

