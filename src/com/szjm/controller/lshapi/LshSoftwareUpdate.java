package com.szjm.controller.lshapi;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.szjm.controller.lshapp.BaseAppController;
import com.szjm.service.lsh.softwareupdate.SoftwareUpdateManager;
import com.szjm.util.PageData;
/**
 * API接口类
 *
 * @author
 *
 */
@Controller
@RequestMapping(value = "app/Api")
public class LshSoftwareUpdate extends BaseAppController{
	@Resource(name="softwareupdateService")
	private SoftwareUpdateManager softwareupdateService;


	  @RequestMapping("/download")
	    public void download(HttpServletRequest request,HttpServletResponse response) throws Exception{
		    String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + path + "/";
	    	Map<String,Object> content=new HashMap<String, Object>();
	    	PageData pd = new PageData();
	    	pd.put("XIAZAI", "XIAZAI");
	    	pd=softwareupdateService.findById(pd);
	    	if(pd!=null){
	    		content.put("versionCode", pd.get("VERSIONCODE"));
	    		content.put("versionName", pd.get("VERSIONNAME"));
	    		content.put("content", pd.get("CONTENT"));
	    		content.put("url",  basePath + "uploadFiles/uploadImgs/"+pd.get("PATH"));
	    	}else{
	    		content.put("versionCode", "");
	    		content.put("versionName", "");
	    		content.put("content", "");
	    		content.put("url", "");
	    	}
	    	WriteClientMessage(response,"0", "成功",content );
	    }
}
