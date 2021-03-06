<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8"
	      import="java.util.*" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>发件箱</title>
    <base href="<%=basePath%>"> 
    <meta http-equiv="content-type" content="text/html;charset=gb2312">
	<link rel="stylesheet" href="plugin/css/font-awesome.min.css">
	<link rel="stylesheet" href="plugin/css/jquery-ui.min.css">
	<link rel="stylesheet" type="text/css" href="plugin/css/public.css">	
	<link rel="stylesheet" type="text/css" href="plugin/css/mail/send.css">
	<link rel="stylesheet" type="text/css" href="plugin/css/mail/tag.css">
	<script type="text/javascript" src="plugin/jQuery/jQuery-2.1.4.min.js"></script>
	<script type="text/javascript" src="plugin/js/jquery.ui.widget.min.js"></script>
    <script type="text/javascript" src="plugin/js/jquery.marcopolo.min.js"></script>
    <script type="text/javascript" src="plugin/js/jquery.manifest.js"></script>
    <script type="text/javascript" src="plugin/layer/layer.js"></script>
	<script src="plugin/jQuery/jquery-ui.min.js"></script>
	<link rel="stylesheet" href="plugin/css/jquery.datetimepicker.css">
	<script src="plugin/jQuery/jquery.datetimepicker.full.js"></script>	
	<script src="plugin/js/xygdev.commons.js"></script>	
  </head> 
  <body>
    <div id="container">
      <!-- 数据加载动画 start -->
  	  <div class="ajax_loading">
        <i class="fa fa-spinner fa-pulse fa-4x" style="color:white"></i>
      </div>
      <!-- 数据加载动画 end -->
    
      <!-- 主表格区域 start -->
      <div class="table">
        <table id="main-table" data-table="Mail">
          <tr>
            <th class="MAIL_TITLE" data-column="db">邮件标题</th>
     	    <th class="SEND_DATE" data-column="db">发件时间</th>
     	    <th class="REC_USER_NAME" data-column="db">收件账号</th>
     	    <th class="ACTION" data-column="normal">操作</th> 
     	    <th class="SEND_ID" style="display:none" data-column="hidden">&nbsp;</th>
     	    <th class="RECEIVE_ID" style="display:none" data-column="hidden">&nbsp;</th>
     	  </tr>
     	  <tr>    
     	    <td class="MAIL_TITLE" data-column="db"></td>
     	    <td class="SEND_DATE" data-column="db"></td>
     	    <td class="REC_USER_NAME" data-column="db"></td>
     	    <td class="ACTION" data-column="normal">
     	      <i class="fa fa fa-eye view pointer" title="邮件详情" data-show="true" data-reveal-id="detail" data-dismissmodalclass="close-detail-frame" data-crudtype="pre-update" data-preupdateurl="mail/getSendMailDetail.do" data-type="detail" data-updateparam=["SEND_ID",".SEND_ID"]></i>
     	    </td>
     	    <td class="SEND_ID" style="display:none" data-column="hidden">&nbsp;</td>
     	    <td class="RECEIVE_ID" style="display:none" data-column="hidden">&nbsp;</td>
     	  </tr>
        </table>
      </div>
      <!-- 主表格区域 end -->
   
      <!-- 主表格按钮区域 start -->
      <div class="table_button" id="table" data-table="Mail">
        <div class="setting">
          <i class="fa fa-cog pointer" title="表格设置" data-reveal-id="setting" data-dismissmodalclass="close-setting"></i>
        </div>
        <div class="setting">
          <i class="fa fa-search pointer" title="条件查询" data-reveal-id="query" data-key="true" data-dismissmodalclass="close-query-frame"></i>
        </div>
        <div class="setting">
          <i class="fa fa-envelope pointer" title="发送邮件" data-reveal-id="send_mail" data-dismissmodalclass="close-send-frame" data-type="send" data-crudtype="pre-insert"></i>
        </div>
        <div class="setting">
          <i id='refresh' class="fa fa-refresh pointer" title="刷新发件箱" data-pagetype='refresh' data-pageframe="table"></i>
        </div>
        <div id="setting">
          <!-- 设置菜单区域 start -->
          <jsp:include page="../public/setting.jsp" >
			<jsp:param name="rdtable" value="#main-table" />
			<jsp:param name="odtable" value="#main-table" />
			<jsp:param name="pageframe" value="table" />
		  </jsp:include>
          <!-- 设置菜单区域 end -->    
        </div>
        <div>
          <!-- 分页按钮区域 start -->
          <jsp:include page="../public/pageArrow.jsp" >
			<jsp:param name="pageframe" value="table" />
		  </jsp:include>
          <!-- 分页按钮区域 end -->
          <input type="hidden" data-type="size" id="page_size" value="10"/>
          <input type="hidden" data-type="number" id="page_no" value="1"/>
          <input type="hidden" data-type="orderby" id="ORDER_BY" value="SEND_DATE DESC"/> 
          <input type="hidden" data-type="cond"/>
          <input type="hidden" data-type="autoquery" value="Y"/>
          <input type="hidden" data-type="url" value="mail/getSendMail.do"/>
          <input type="hidden" data-type="jsontype" value="table"/> 
        </div>
      </div>
      <!-- 主表格按钮区域 end --> 
   
      <!-- 定义列区域 start --> 
      <jsp:include page="../public/rowdefine.jsp"></jsp:include>
      <!-- 定义列区域 end -->
    
      <!-- 多维排序区域 start -->
      <jsp:include page="../public/orderby.jsp"></jsp:include>
      <!-- 多维排序区域 end -->
   
      <!-- 个人配置区域 start -->
      <jsp:include page="../public/config.jsp"></jsp:include>
      <!-- 个人配置区域 end -->
     
      <!-- lov区域 start -->
      <jsp:include page="../public/lov.jsp"></jsp:include>
      <!-- lov区域 end -->
     
      <!-- 邮件详情区域 start -->
      <div id="detail" class="detail_frame">
        <div class='title pointer'>      
          <span data-type="detail"><i class="fa fa-envelope"></i>&nbsp;邮件详情</span>
        </div>
        <a class="close-detail-frame" data-type="close">&#215;</a>
        <div class='line'></div>
        <div class='content'>
          <div class="left short"><span>发件账号:</span><span id="SEND_USER_NAME"></span></div>
          <div class="left short"><span>发件人:</span><span id="SEND_USER_DESC"></span></div>
          <div class="left long"><span>发件时间:</span><span id="SEND_DATE"></span></div>
          <br style="clear:both"/>
          <label class="left rec">收件人:</label>
          <input type="text" id="REC_USER_NAME" class="left rec" readonly="readonly"/>
          <br style="clear:both"/>
          <label class="left mail_title">标题:</label>
          <input type="text" id="MAIL_TITLE" class="left mail_title" readonly="readonly"/>
          <br style="clear:both"/>
          <div id="MAIL_CONTENT" name="MAIL_CONTENT" class="textarea"></div>
        </div>
      </div>
      <!-- 邮件详情区域 end -->
     
      <!-- 条件查询区域 start -->
      <div id='query' class='pop_frame row-2'>     
        <div class='title pointer'>      
          <span><i class="fa fa-envelope"></i>&nbsp;邮件查询</span>
        </div>
        <a class="close-query-frame" data-type="close">&#215;</a>
        <div class='line'></div>
        <div class='content row-2'>
          <form>
            <label for="SEND_TITLE_Q" class="left md">邮件标题:</label>
            <input type="text" id="SEND_TITLE_Q" name="SEND_TITLE" class="left lgx2"/>
            <br style="clear:both"/>
            <label for='SEND_DATE_F' class='left md'>发件时间:</label> 
            <input type="text" id="SEND_DATE_F" name="SEND_DATE_F" class="left lg" data-datatype="date" placeholder="起始发件时间"/>
            <label class='left blank'></label> 
            <input type="text" id="SEND_DATE_T" name="SEND_DATE_T" class="left lg" data-datatype="date" placeholder="截止发件时间"/>
            <br style="clear:both"/>
          </form>
        </div>
        <div class='foot'>             
          <button class="right pointer"  data-buttonframe="table" data-crudtype="query" data-pageframe="query">邮件查询</button>
        </div> 
      </div>
      <!-- 条件查询区域 end -->
      
      <!-- 邮件发送区域 start -->
      <div id="send_mail" class="send_frame">
        <div class='title pointer'>      
          <span data-type="send"><i class="fa fa-envelope"></i>&nbsp;邮件发送</span>
        </div>
        <a class="close-send-frame" data-type="close">&#215;</a>
        <div class='line'></div>
        <div class='content'>            
          <form id="send_data">  
            <label for="locations" class="rec left">收件账号:</label>
            <input type="text" name="locations" id="locations"/>
            <i class="fa fa-plus-circle pointer right" data-pageframe="lov" data-reveal-id="lov" data-bg="lov-modal-bg" data-dismissmodalclass='close-lov' data-lovname="发件人查询" data-queryurl="lov/getUserPage.do" data-jsontype="user" data-th=["用户id","发件账号","发件人"] data-td=["USER_ID","USER_NAME","DESCRIPTION"] data-selectname=["发件账号","发件人"] data-selectvalue=["USER_NAME","DESCRIPTION"] data-choose=[".USER_NAME"] data-recid=["#locations"] ></i>     
            <br style="clear:both"/>
            <label class="left invert">全发送:</label>
          	<input type="checkbox" id="ALLUSER_FLAG" name="ALLUSER_FLAG" class="invert left"/>
          	<br style="clear:both"/>
          	<label class="left mail_title">标题:</label>
          	<input type="text" id="SEND_TITLE" name="SEND_TITLE" data-update="db" class="left mail_title"/>
          	<br style="clear:both"/>
          	<textarea id="SEND_CONTENT" name="SEND_CONTENT" data-update="db" cols="100"></textarea> 	
          </form>
        </div>
        <div class='foot'>             
          <button data-type="send" class="right pointer" id="Mail_send" data-keyup="enter">发送邮件</button>
        </div> 
      </div>
      <!-- 邮件发送区域 end -->
      
      <!-- 用户信息存放区域 start -->
      <input type="hidden" id="USER_ID" value="${USER_ID}"/>  
      <input type="hidden" id="INTERACT_CODE" value="SEND_MAIL"/> 
      <input type="hidden" id="HEADER_ID" value=""/> 
      <!-- 用户信息存放区域 end -->  
    </div>
    <script>
        $(function() {
            //设置拖拽
    		$("#detail").draggable({ handle: ".title" });
    		$("#query").draggable({ handle: ".title" });
    		$("#send_mail").draggable({ handle: ".title" });
    		
    		//初始化CRUD和LOV条件查询
    		$().crudListener();	
    		$().revealListener(); 
    		
    		//搜索栏初始化
    		$('#locations').manifest({
          		formatDisplay: function (data, $item, $mpItem) {
            		return data.USER_NAME;
          		},
          		formatValue: function (data, $value, $item, $mpItem) {
            		return data.USER_ID;
          		},
          		marcoPolo: {
            		url: 'mail/findSendUser.do',
					formatItem: function (data, $item) {
						return data.USER_NAME + ' ' + data.DESCRIPTION;
					},
            		minChars: 2,
            		param: 'query'
          		},
          		required: true
        	});       	
        	
        	//邮件发送动作
        	$('#Mail_send').on('click',function(){
        	    $('.ajax_loading').show();
        	    val=$('#mf_locations_list .mf_value');
        	    Array.prototype.contains = function (obj) {  
        			var i = this.length;  
        			while (i--) {  
            			if (this[i] === obj) {  
                			return true;  
            			}  
        			}  
        			return false;  
    			}  
        	    arrRec=new Array;
        	    for(i=0;i<val.length;i++){
        	        if(arrRec.contains(val[i].value)){
        	            continue;
        	        }else{
        	            arrRec.push(val[i].value);
        	        }
        	    }
                rec=arrRec.join(',');
                
                var excludes = ['locations','locations_values[]'];
                
				$.each(excludes, function (i, v) {
  					excludes[i] = '[name="' + v + '"]';
				});
 
				excludes = excludes.join(',');
 
				param = $($('#send_data').prop('elements')).not(excludes).serialize();
                param = param+'&REC_USER='+rec;
                console.log(param);
                if(!$('#ALLUSER_FLAG').is(':checked')){
                    if(rec==''||rec==null){
                        layer.alert('发件人栏位此时不允许为空！',{title:'警告',offset: [150]});
                        $('.ajax_loading').hide();
                        return;
                    };
                };

                $.ajax({
				    type:'post', 
				    data:param,
				    url:'mail/insertSendMail.do',
				    dataType:'json',
				    success: function (data) {
				    	if(data.retcode=="0"){
				    	    $('.ajax_loading').hide();
				    		layer.msg("邮件发送成功!");
				    		$('#send_mail a[data-type="close"]').click();
				    		$('#refresh').click();
				    	}else{
				    	    $('.ajax_loading').hide();
				    		layer.alert("邮件发送处理失败！错误信息:"+data.errbuf,{title:'警告',offset: [150]});
				    	}						  
				    },
				    error: function () {
				        $('.ajax_loading').hide();
				    	layer.alert("获取数据失败",{title:'警告',offset: [150]});
				    }	
                });              
                $('.mf_list li').remove();
        	}); 
    		//默认值
    		//$('#SEND_DATE_F').val(new Date().addMonths(-2).format('yyyy-MM-dd hh:mm:ss'));
    		//$('#SEND_DATE_T').val(new Date().format('yyyy-MM-dd hh:mm:ss'));
    		//日期选择2016.12.8 by sam.t
    		$('input[data-datatype="date"]').datetimepicker({
				  lang:"ch",           //语言选择中文
				  timepicker:true,    //启用时间选项
				  format:"Y-m-d H:i:s",      //格式化日期
				  step: 30,
				  showOnClick: true
			});
        	$('#SEND_DATE_F').on('blur',function(){
        		if(!$('#SEND_DATE_T').val()&&$('#SEND_DATE_F').val()){
        			$('#SEND_DATE_T').val($('#SEND_DATE_F').val());
        		}
        	});
        });
        
        jQuery.json={
        	getContent:function(data,JSONtype){  
        	    if(JSONtype=='table'){
        	        for(i=0;i<(pageMaxRow-pageMinRow+1);i++){
        	            $('.REC_USER_NAME',$('#main-table tr:eq('+(i+1)+')')).html(data.rows[i].REC_USER_NAME); 
                    	$('.MAIL_TITLE',$('#main-table tr:eq('+(i+1)+')')).html(data.rows[i].SEND_TITLE); 
                    	$('.SEND_DATE',$('#main-table tr:eq('+(i+1)+')')).html(data.rows[i].SEND_DATE);   
                    	$('.SEND_ID',$('#main-table tr:eq('+(i+1)+')')).html(data.rows[i].SEND_ID); 
                    	$('.RECEIVE_ID',$('#main-table tr:eq('+(i+1)+')')).html(data.rows[i].RECEIVE_ID); 
                	}
                	$().afterRowDefine();
                	$().crudListener();
                	$().mailListener();
                	$().revealListener();  
        	    }else if(JSONtype=='user'){
        	        if(pageMaxRow==0&&pageMinRow==0){
        	            console.log('no data');
        	        }else{
        	            for(i=0;i<(pageMaxRow-pageMinRow+1);i++){
       	            	    $('td:eq(0)',$('.contentbox tr:eq('+(i+1)+')')).html(data.rows[i].USER_ID);       	                    
       	            	    $('td:eq(1)',$('.contentbox tr:eq('+(i+1)+')')).html(data.rows[i].USER_NAME);
       	            	    $('td:eq(2)',$('.contentbox tr:eq('+(i+1)+')')).html(data.rows[i].DESCRIPTION);       	               	        
       	            	} 
        	        }       	            	    
        	    }   	            	   	
       	    },	
       	    getUpdateJSON:function(data){   
       	        $('#SEND_USER_NAME').text(data.rows[0].SEND_USER_NAME);
       	        $('#SEND_USER_DESC').text(data.rows[0].SEND_USER_DESC);
       	        $('#SEND_DATE').text(data.rows[0].SEND_DATE);
       	        $('#REC_USER_NAME').val(data.rows[0].RECEIVE_USER_NAME_LIST);
       	        $('#MAIL_TITLE').val(data.rows[0].SEND_TITLE);
       	        $('#MAIL_CONTENT').html(data.rows[0].SEND_CONTENT);
       	    },
       	    getMSG:function(data){
       	        pageMinRow=parseInt(data.pageMinRow);
        	    pageMaxRow=parseInt(data.pageMaxRow);
        	    firstPageFlag=data.firstPageFlag;
        	    lastPageFlag=data.lastPageFlag;
        	   	totalPages=data.totalPages;
       	    }
       	}
    </script>
    <script type="text/javascript" src="plugin/layer/layer.js"></script>
    <script type="text/javascript" src="plugin/js/jQuery.reveal.js"></script> 
    <script type="text/javascript" src="plugin/js/jQuery.page.js"></script>    
    <script type="text/javascript" src="plugin/js/jQuery.lov.js"></script> 
    <script type="text/javascript" src="plugin/js/jQuery.crud.js"></script> 
    <script type="text/javascript" src="plugin/js/jQuery.mail.js"></script>
    <script type="text/javascript" src="plugin/js/jQuery.rowdefine.js"></script>
    <script type="text/javascript" src="plugin/js/jQuery.irr.orderby.js"></script>	
    <script type="text/javascript" src="plugin/js/jQuery.irr.init.js"></script>	     
  </body>
</html>