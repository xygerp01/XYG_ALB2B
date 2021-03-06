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

import com.xinyiglass.springSample.entity.RespVO;
import com.xinyiglass.springSample.service.RespVOService;

@Controller
@RequestMapping("/resp")
@Scope("prototype")
public class RespController {
	
    @Autowired
    RespVOService rvs;
    
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
    
    @RequestMapping("/respManage.do")
	public String listRespVO(){
		return "basic/respManage";
	}
    
    @RequestMapping(value = "/insert.do", method = RequestMethod.POST)
	public void insret() throws Exception
	{ 
    	RespVO r = new RespVO();
    	r.setRespCode(req.getParameter("RESP_CODE"));
    	r.setRespName(req.getParameter("RESP_NAME"));
    	r.setDescription(req.getParameter("DESCRIPTION"));
    	r.setMenuId(TypeConvert.str2Long(req.getParameter("MENU_ID")));
    	r.setStartDate(TypeConvert.str2uDate(req.getParameter("START_DATE")));
    	r.setEndDate(TypeConvert.str2uDate(req.getParameter("END_DATE")));
    	res.getWriter().print(rvs.insert(r,loginId).toJsonStr());
	}
    
    @RequestMapping(value = "/preUpdate.do", method = RequestMethod.POST)
    public void preUpdate() throws Exception
    {
    	Long respId = Long.parseLong(req.getParameter("RESP_ID"));
    	RespVO respVO = rvs.findForRespVOById(respId,loginId);
    	sess.setAttribute("lockRespVO", respVO);//记录在session变量
    	res.getWriter().print(rvs.findRespByIdForJSON(respId,loginId));
    }
    
    @RequestMapping(value = "/update.do", method = RequestMethod.POST)
	public void update() throws Exception
	{
    	Long respId = Long.parseLong(req.getParameter("RESP_ID"));
    	RespVO lockRespVO = (RespVO)sess.getAttribute("lockRespVO");
    	if (lockRespVO ==null){
			throw new RuntimeException("更新数据出错:会话数据已经无效!请返回再重新操作!");
		}
		if (!respId.equals(lockRespVO.getRespId())){
			throw new RuntimeException("更新的数据无法匹配!请重新查询!");
		}
		RespVO r = new RespVO();
		r = (RespVO)lockRespVO.clone();
		r.setRespId(respId);
		r.setRespCode(req.getParameter("RESP_CODE"));
    	r.setRespName(req.getParameter("RESP_NAME"));
    	r.setDescription(req.getParameter("DESCRIPTION"));
    	r.setMenuId(TypeConvert.str2Long(req.getParameter("MENU_ID")));
    	r.setStartDate(TypeConvert.str2uDate(req.getParameter("START_DATE")));
    	r.setEndDate(TypeConvert.str2uDate(req.getParameter("END_DATE")));
    	res.getWriter().print(rvs.update(lockRespVO, r,loginId).toJsonStr());
	}
    
    @RequestMapping(value = "/getRespPage.do", method = RequestMethod.POST)
	public void getRespPage() throws Exception
	{   	
		int pageSize=Integer.parseInt(req.getParameter("pageSize"));
		int pageNo=Integer.parseInt(req.getParameter("pageNo"));
		boolean goLastPage=Boolean.parseBoolean(req.getParameter("goLastPage"));
   		Map<String,Object> conditionMap=new HashMap<String,Object>();
   		conditionMap.put("orderBy", req.getParameter("orderby"));
   		conditionMap.put("menuId", TypeConvert.str2Long(req.getParameter("MENU_ID")));
   		conditionMap.put("respId", TypeConvert.str2Long(req.getParameter("RESP_ID")));
   		conditionMap.put("startDate_F", TypeConvert.str2uDate(req.getParameter("START_DATE_F")));
   		conditionMap.put("startDate_T", TypeConvert.str2uDate(req.getParameter("START_DATE_T")));
		res.getWriter().print(rvs.findForPage(pageSize, pageNo, goLastPage, conditionMap, loginId));
	}  
}
