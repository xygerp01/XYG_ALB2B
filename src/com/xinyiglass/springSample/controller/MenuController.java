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

import com.xinyiglass.springSample.entity.MenuHeaderVO;
import com.xinyiglass.springSample.entity.MenuLineVO;
import com.xinyiglass.springSample.service.MenuHeaderVOService;
import com.xinyiglass.springSample.service.MenuLineVOService;
import com.xinyiglass.springSample.service.RespVOService;

@Controller
@RequestMapping("/menu")
@Scope("prototype")
public class MenuController {
	@Autowired
    RespVOService rvs;
	@Autowired
	MenuHeaderVOService mhvs;
	@Autowired
	MenuLineVOService mlvs;
	
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
    
    @RequestMapping("/getPersonalMenu.do")
    public void getPersonalMenu() throws Exception{
    	Long respId = (Long)sess.getAttribute("RESP_ID");
    	Long menuId = rvs.findMenuId(respId,loginId);
    	res.getWriter().print(mhvs.findPersonalMenuById(menuId,loginId));
    }
    
    @RequestMapping("/menuManage.do")
	public String listMenuHeaderVO(){
		return "basic/menuManage";
	}
    
    @RequestMapping("/getMenuHeaderPage.do")
    public void getMenuHeaderPage() throws Exception
	{   	
		int pageSize=Integer.parseInt(req.getParameter("pageSize"));
		int pageNo=Integer.parseInt(req.getParameter("pageNo"));
		boolean goLastPage=Boolean.parseBoolean(req.getParameter("goLastPage"));
   		Map<String,Object> conditionMap=new HashMap<String,Object>();
   		conditionMap.put("orderBy", req.getParameter("orderby"));
   		conditionMap.put("menuId", TypeConvert.str2Long(req.getParameter("MENU_ID")));
		res.getWriter().print(mhvs.findForPage(pageSize, pageNo, goLastPage, conditionMap, loginId));
	} 
    
    @RequestMapping(value = "/insertMenuHeader.do", method = RequestMethod.POST)
	public void insretMenuHeader() throws Exception
	{ 
    	MenuHeaderVO m = new MenuHeaderVO();
    	m.setMenuCode(req.getParameter("MENU_CODE"));
    	m.setMenuName(req.getParameter("MENU_NAME"));
    	m.setDescription(req.getParameter("DESCRIPTION"));
    	m.setMenuIconId(TypeConvert.str2Long(req.getParameter("ICON_ID")));
    	res.getWriter().print(mhvs.insert(m,loginId).toJsonStr());
	}
    
    @RequestMapping(value = "/preUpdateMenuHeader.do", method = RequestMethod.POST)
    public void preUpdateMenuHeader() throws Exception
    {
    	Long menuId = Long.parseLong(req.getParameter("MENU_ID"));
    	//System.out.println(menuId);
    	MenuHeaderVO menuVO = mhvs.findForMenuVOById(menuId,loginId);
    	sess.setAttribute("lockMenuHeaderVO", menuVO);//记录在session变量
    	res.getWriter().print(mhvs.findMenuByIdForJSON(menuId,loginId));
    }
    
    @RequestMapping(value = "/updateMenuHeader.do", method = RequestMethod.POST)
   	public void updateMenuHeader() throws Exception
   	{
       	Long menuId = Long.parseLong(req.getParameter("MENU_ID"));
       	MenuHeaderVO lockMenuVO = (MenuHeaderVO)sess.getAttribute("lockMenuHeaderVO");
       	if (lockMenuVO ==null){
   			throw new RuntimeException("更新数据出错:会话数据已经无效!请返回再重新操作!");
   		}
   		if (!menuId.equals(lockMenuVO.getMenuId())){
   			throw new RuntimeException("更新的数据无法匹配!请重新查询!");
   		}
   		MenuHeaderVO m = new MenuHeaderVO();
   		m = (MenuHeaderVO)lockMenuVO.clone();
   		m.setMenuId(menuId);
   		m.setMenuCode(req.getParameter("MENU_CODE"));
    	m.setMenuName(req.getParameter("MENU_NAME"));
    	m.setDescription(req.getParameter("DESCRIPTION"));
    	m.setMenuIconId(TypeConvert.str2Long(req.getParameter("ICON_ID")));
       	res.getWriter().print(mhvs.update(lockMenuVO, m,loginId).toJsonStr());
   	}
    
    @RequestMapping("/getMenuLinePage.do")
    public void getMenuLinePage() throws Exception
	{   	
		int pageSize=Integer.parseInt(req.getParameter("pageSize"));
		int pageNo=Integer.parseInt(req.getParameter("pageNo"));
		boolean goLastPage=Boolean.parseBoolean(req.getParameter("goLastPage"));
   		Map<String,Object> conditionMap=new HashMap<String,Object>();
   		conditionMap.put("orderBy", req.getParameter("orderby"));
   		conditionMap.put("menuId", Long.parseLong(req.getParameter("MENU_ID")));
		res.getWriter().print(mlvs.findForPage(pageSize, pageNo, goLastPage, conditionMap, loginId));
	} 
    
    @RequestMapping("/getAutoAddSeq.do")
    public void getAutoAddSeq() throws Exception
	{
    	Long menuId = Long.parseLong(req.getParameter("MENU_ID"));
    	res.getWriter().print(mlvs.findAutoAddSequence(menuId,loginId));
	}
    
    @RequestMapping(value = "/insertMenuLine.do", method = RequestMethod.POST)
	public void insretMenuLine() throws Exception
	{ 
    	MenuLineVO m = new MenuLineVO();
    	m.setMenuId(TypeConvert.str2Long(req.getParameter("MENU_ID")));
    	m.setMenuSequence(TypeConvert.str2Long(req.getParameter("MENU_SEQUENCE")));
    	m.setSubMenuId(TypeConvert.str2Long(req.getParameter("SUB_MENU_ID")));
    	m.setFunctionId(TypeConvert.str2Long(req.getParameter("FUNCTION_ID")));
    	m.setEnabledFlag(req.getParameter("ENABLED_FLAG"));
    	res.getWriter().print(mlvs.insert(m,loginId).toJsonStr());
	}
    
    @RequestMapping("/preUpdateMenuLine.do")
    public void preUpdateMenuLine() throws Exception
    {
    	Long menuId = Long.parseLong(req.getParameter("MENU_ID"));
    	Long menuSeq = Long.parseLong(req.getParameter("MENU_SEQUENCE"));
    	MenuLineVO menuVO = mlvs.findForMenuVOById(menuId, menuSeq,loginId);
    	sess.setAttribute("lockMenuLineVO", menuVO);//记录在session变量
    	res.getWriter().print(mlvs.findMenuLineForJSON(menuId, menuSeq,loginId));
    }
    
    @RequestMapping(value = "/updateMenuLine.do", method = RequestMethod.POST)
   	public void updateMenuLine() throws Exception
   	{
       	Long menuId = TypeConvert.str2Long(req.getParameter("MENU_ID"));
       	MenuLineVO lockMenuVO = (MenuLineVO)sess.getAttribute("lockMenuLineVO");
       	if (lockMenuVO == null){
   			throw new RuntimeException("更新数据出错:会话数据已经无效!请返回再重新操作!");
   		}
   		if (!menuId.equals(lockMenuVO.getMenuId())){
   			throw new RuntimeException("更新的数据无法匹配!请重新查询!");
   		}
   		MenuLineVO m = new MenuLineVO();
   		m = (MenuLineVO)lockMenuVO.clone();
   		m.setMenuId(menuId);
    	m.setMenuSequence(TypeConvert.str2Long(req.getParameter("MENU_SEQUENCE")));
    	m.setSubMenuId(TypeConvert.str2Long(req.getParameter("SUB_MENU_ID")));
    	m.setFunctionId(TypeConvert.str2Long(req.getParameter("FUNCTION_ID")));
    	m.setEnabledFlag(req.getParameter("ENABLED_FLAG"));
       	res.getWriter().print(mlvs.update(lockMenuVO, m,loginId).toJsonStr());
   	}
}
