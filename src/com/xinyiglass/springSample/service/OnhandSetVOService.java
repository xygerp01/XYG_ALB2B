package com.xinyiglass.springSample.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xinyiglass.springSample.dao.OnhandSetVODao;
import com.xinyiglass.springSample.entity.OnhandSetVO;

import xygdev.commons.entity.PlsqlRetValue;
import xygdev.commons.page.PagePub;
import xygdev.commons.springjdbc.DevJdbcSubProcess;
import xygdev.commons.sqlStmt.SqlStmtPub;

@Service
@Transactional(rollbackFor=Exception.class)//指定checked的异常Exception也要回滚！
public class OnhandSetVOService {
	@Autowired
	PagePub pagePub;
	@Autowired
	OnhandSetVODao onhandDao;
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public String findForPage(int pageSize,int pageNo,boolean goLastPage,Map<String,Object> conditionMap,Long loginId) throws Exception{
		Map<String,Object> paramMap=new HashMap<String,Object>();
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append("SELECT * FROM XYG_ALB2B_ONHAND_SET_V A WHERE 1=1");
		sqlBuff.append(SqlStmtPub.getAndStmt("ORGANIZATION_ID",conditionMap.get("organId"),paramMap));
		sqlBuff.append(" ORDER BY "+conditionMap.get("orderBy"));
		return pagePub.qPageForJson(sqlBuff.toString(), paramMap, pageSize, pageNo, goLastPage);
	}
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public OnhandSetVO findForOnhandSetVOById(Long organizationId,Long loginId) throws Exception{
		return onhandDao.findById(organizationId);
	}
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public String findOnhandSetByIdForJSON(Long organizationId,Long loginId) throws Exception{
		return "{\"rows\":"+onhandDao.findByIdForJSON(organizationId).toJsonStr()+"}";
	}
	
	public PlsqlRetValue insert(OnhandSetVO os,Long loginId) throws Exception{
		PlsqlRetValue ret=onhandDao.insert(os);
		if(ret.getRetcode()!=0){
			DevJdbcSubProcess.setRollbackOnly();//该事务必须要回滚！
		}
		return ret;
	}
	
	public PlsqlRetValue update(OnhandSetVO lockOsVO,OnhandSetVO updateOsVO,Long loginId) throws Exception
	{ 
		PlsqlRetValue ret=onhandDao.lock(lockOsVO);
		if(ret.getRetcode()==0){
			ret=onhandDao.update(updateOsVO);
		}else{
			DevJdbcSubProcess.setRollbackOnly();//该事务必须要回滚！
		}
		return ret;
	}
}
