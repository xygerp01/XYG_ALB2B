package com.xinyiglass.springSample.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import xygdev.commons.util.TypeConvert;

import com.xinyiglass.springSample.entity.OnhandSetVO;
import com.xinyiglass.springSample.service.FfInvService;
import com.xinyiglass.springSample.service.LgInvService;
import com.xinyiglass.springSample.service.OnhandSetVOService;
import com.xinyiglass.springSample.service.ResnInvService;

@Controller
@RequestMapping("/inv")
@Scope("prototype")
public class InvController {
	@Autowired
	LgInvService lis;
	@Autowired
	FfInvService fis;
	@Autowired
	ResnInvService ris;
	@Autowired 
	OnhandSetVOService oss;
	
	protected HttpServletRequest req; 
    protected HttpServletResponse res; 
    protected HttpSession sess; 
    protected Long loginId; 
    
    @ModelAttribute 
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{ 
        this.req = request; 
        this.res = response; 
        this.sess = request.getSession(); 
        req.setCharacterEncoding("utf-8");
		res.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");  
		loginId=(Long)sess.getAttribute("LOGIN_ID");
    }
    
    @RequestMapping("/lgInvQuery.do")
	public String listLgInv(){
		return "inv/lgInvQuery";
	}
    
    @RequestMapping(value = "/getLgInvPage.do", method = RequestMethod.POST)
	public void getLgInvPage() throws Exception
	{   	
		int pageSize=Integer.parseInt(req.getParameter("pageSize"));
		int pageNo=Integer.parseInt(req.getParameter("pageNo"));
		boolean goLastPage=Boolean.parseBoolean(req.getParameter("goLastPage"));
   		Map<String,Object> conditionMap=new HashMap<String,Object>();
   		conditionMap.put("orderBy", req.getParameter("orderby"));
   		conditionMap.put("organizationId", TypeConvert.str2Long(req.getParameter("ORGANIZATION_ID")));
   		conditionMap.put("width", TypeConvert.str2Long(req.getParameter("WIDTH")));
   		conditionMap.put("height",TypeConvert.str2Long(req.getParameter("HEIGHT")) );
   		conditionMap.put("thickness", TypeConvert.str2Double(req.getParameter("THICKNESS")));
   		conditionMap.put("coatingType", req.getParameter("COATING_TYPE"));
		res.getWriter().print(lis.findForPage(pageSize, pageNo, goLastPage, conditionMap,loginId));
	}
    
    @RequestMapping("/ffInvQuery.do")
	public String listFfInv(){
		return "inv/ffInvQuery";
	}
    
    @RequestMapping(value = "/getFfInvPage.do", method = RequestMethod.POST)
	public void getFfInvPage() throws Exception
	{   	
		int pageSize=Integer.parseInt(req.getParameter("pageSize"));
		int pageNo=Integer.parseInt(req.getParameter("pageNo"));
		boolean goLastPage=Boolean.parseBoolean(req.getParameter("goLastPage"));
   		Map<String,Object> conditionMap=new HashMap<String,Object>();
   		conditionMap.put("orderBy", req.getParameter("orderby"));
   		conditionMap.put("organizationId", TypeConvert.str2Long(req.getParameter("ORGANIZATION_ID")));
   		conditionMap.put("width", TypeConvert.str2Long(req.getParameter("WIDTH")));
   		conditionMap.put("height",TypeConvert.str2Long(req.getParameter("HEIGHT")) );
   		conditionMap.put("thickness",TypeConvert.str2Double(req.getParameter("THICKNESS")) );
   		conditionMap.put("colour", req.getParameter("COLOUR"));
   		conditionMap.put("grade", req.getParameter("GRADE"));
   		conditionMap.put("packCode",req.getParameter("PACK_CODE") );
		res.getWriter().print(fis.findForPage(pageSize, pageNo, goLastPage, conditionMap,loginId));
	}
    
    @RequestMapping("/resnInvQuery.do")
	public String listResnInv(){
		return "inv/resnInvQuery";
	}
    
    @RequestMapping(value = "/getResnInvPage.do", method = RequestMethod.POST)
	public void getResnInvPage() throws Exception
	{   	
		int pageSize=Integer.parseInt(req.getParameter("pageSize"));
		int pageNo=Integer.parseInt(req.getParameter("pageNo"));
		boolean goLastPage=Boolean.parseBoolean(req.getParameter("goLastPage"));
   		Map<String,Object> conditionMap=new HashMap<String,Object>();
   		conditionMap.put("userId", (Long)sess.getAttribute("USER_ID"));
   		conditionMap.put("orderBy", req.getParameter("orderby"));
   		conditionMap.put("custId", TypeConvert.str2Long(req.getParameter("CUSTOMER_ID")));
   		conditionMap.put("width", TypeConvert.str2Long(req.getParameter("WIDTH")));
   		conditionMap.put("height", TypeConvert.str2Long(req.getParameter("HEIGHT")));
   		conditionMap.put("coatingType", req.getParameter("COATING_TYPE"));
   		conditionMap.put("packCode", req.getParameter("PACK_CODE"));
		res.getWriter().print(ris.findForPage(pageSize, pageNo, goLastPage, conditionMap,loginId));
	}
    
    @RequestMapping("/onhandSet.do")
	public String listOnhandSet(){
		return "basic/onhandSet";
	}    

	@RequestMapping(value = "/getOnhandSetPage.do", method = RequestMethod.POST)
	public void getOnhandSetPage() throws Exception
	{   	
		int pageSize =Integer.parseInt(req.getParameter("pageSize"));
		int pageNo = Integer.parseInt(req.getParameter("pageNo"));
		boolean goLastPage = Boolean.parseBoolean(req.getParameter("goLastPage"));
   		Map<String,Object> conditionMap=new HashMap<String,Object>();
   		conditionMap.put("orderBy", req.getParameter("orderby"));
   		conditionMap.put("organId", TypeConvert.str2Long(req.getParameter("ORGANIZATION_ID")));
		res.getWriter().print(oss.findForPage(pageSize, pageNo, goLastPage, conditionMap,loginId));
	}
    
    @RequestMapping(value = "/preUpdateOS.do", method = RequestMethod.POST)
    public void preUpdateOS() throws Exception
    {
    	Long organizationId = TypeConvert.str2Long(req.getParameter("ORGANIZATION_ID"));
    	OnhandSetVO os = oss.findForOnhandSetVOById(organizationId,loginId);
    	sess.setAttribute("lockOsVO", os);
    	res.getWriter().print(oss.findOnhandSetByIdForJSON(organizationId,loginId));
    }
    
    @RequestMapping(value = "/insertOS.do", method = RequestMethod.POST)
    public void insertOS() throws Exception
    {
    	OnhandSetVO os = new OnhandSetVO();
    	os.setOrganizationId(TypeConvert.str2Long(req.getParameter("ORGANIZATION_ID")));
    	os.setOnhandGreaterBox(TypeConvert.str2Long(req.getParameter("GREATER_BOX")));
    	os.setOnhandDisplayBox(TypeConvert.str2Long(req.getParameter("DISPLAY_BOX")));
    	res.getWriter().print(oss.insert(os,loginId).toJsonStr());
    }
    
    @RequestMapping(value = "/updateOS.do", method = RequestMethod.POST)
	public void updateOS() throws Exception
	{
    	Long organizationId = TypeConvert.str2Long(req.getParameter("ORGANIZATION_ID"));
    	OnhandSetVO lockOsVO = (OnhandSetVO)sess.getAttribute("lockOsVO");
    	if (lockOsVO ==null){
			throw new RuntimeException("更新数据出错:会话数据已经无效!请返回再重新操作!");
		}
		if (!organizationId.equals(lockOsVO.getOrganizationId())){
			throw new RuntimeException("更新的数据无法匹配!请重新查询!");
		}
		OnhandSetVO os = new OnhandSetVO();
		os = (OnhandSetVO)lockOsVO.clone();
		os.setOrganizationId(organizationId);
    	os.setOnhandGreaterBox(TypeConvert.str2Long(req.getParameter("GREATER_BOX")));
    	os.setOnhandDisplayBox(TypeConvert.str2Long(req.getParameter("DISPLAY_BOX")));
    	res.getWriter().print(oss.update(lockOsVO, os,loginId).toJsonStr());
	}
    
}
