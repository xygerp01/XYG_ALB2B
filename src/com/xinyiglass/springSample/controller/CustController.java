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

import com.xinyiglass.springSample.entity.UserCustVO;
import com.xinyiglass.springSample.service.UserCustVOService;
import com.xinyiglass.springSample.util.LogUtil;

@Controller
@RequestMapping("/cust")
@Scope("prototype")
public class CustController {
    
	@Autowired
	UserCustVOService ucvs;
	
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
    
    @RequestMapping(value = "/getUserCustPage.do", method = RequestMethod.POST)
	public void getUserCustPage() throws Exception
	{   	
		int pageSize = Integer.parseInt(req.getParameter("pageSize"));
		int pageNo = Integer.parseInt(req.getParameter("pageNo"));
		boolean goLastPage = Boolean.parseBoolean(req.getParameter("goLastPage"));
   		Map<String,Object> conditionMap=new HashMap<String,Object>();
   		conditionMap.put("orderBy", req.getParameter("orderby"));
   		conditionMap.put("userId", Long.parseLong(req.getParameter("USER_ID")));
		res.getWriter().print(ucvs.findForPage(pageSize, pageNo, goLastPage, conditionMap,loginId));
	}
    
    @RequestMapping(value = "/insert.do", method = RequestMethod.POST)
	public void insret() throws Exception
	{ 
    	UserCustVO u = new UserCustVO();
    	u.setUserId(TypeConvert.str2Long(req.getParameter("USER_ID")));
    	u.setOrgId(TypeConvert.str2Long(req.getParameter("ORG_ID")));
    	u.setCustomerId(TypeConvert.str2Long(req.getParameter("CUSTOMER_ID")));
    	u.setStartDate(TypeConvert.str2uDate(req.getParameter("START_DATE")));
    	LogUtil.log("Time:"+req.getParameter("END_DATE"));
    	u.setEndDate(TypeConvert.str2uDate(req.getParameter("END_DATE")));
    	res.getWriter().print(ucvs.insert(u,loginId).toJsonStr());
	}
    
    @RequestMapping(value = "/preUpdate.do", method = RequestMethod.POST)
    public void preUpdate() throws Exception
    {
    	Long userCustId = Long.parseLong(req.getParameter("USER_CUST_ID"));
    	res.getWriter().print(ucvs.findByIdForJSON(userCustId,loginId));
    }
    
    @RequestMapping(value = "/update.do", method = RequestMethod.POST)
	public void update() throws Exception
	{ 
    	UserCustVO u = new UserCustVO();
    	u.setUserCustId(TypeConvert.str2Long(req.getParameter("USER_CUST_ID")));
    	u.setEndDate(TypeConvert.str2uDate(req.getParameter("END_DATE")));
    	res.getWriter().print(ucvs.update(u,loginId).toJsonStr());
	}
}
