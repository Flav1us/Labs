<%@ page contentType="text/html; charset=windows-1251" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%response.setContentType("text/html;charset=windows-1251");response.getWriter();response.flushBuffer();%>
<fmt:setLocale value="${sessionScope.locale}" />

<fmt:setBundle basename="resources.Messages" />
<c:set value="${pageContext.request.contextPath}" var="contextPath" scope="page"/>
<c:set value="${contextPath}/resources/images/next_buff.gif" var="next_buff" scope="page"/>
<c:set value="${contextPath}/resources/images/prev_buff.gif" var="prev_buff" scope="page"/>
  
<html>
<head>
<link rel="stylesheet" type="text/css" href="<c:out value='${contextPath}'/>/resources/coolmenus.css">
<script language="JavaScript" src="resources/SiteList.js"></script>

<title>TestCMS</title>
</head>
<body class="bottombody">
<br>
   <table border=0 align=center width=90%>
     <tr><td align=center><P class=header2><fmt:message key="siteList"/></p></td></tr>
    </table>

<br>

<form action="<c:url value='/list'/>" onSubmit="return validate(this);" >
<input type='hidden' name='actionId' value="">
<input type='hidden' name='pkID' value="">
<input type='hidden' name='last_site' value="">
<input type='hidden' name='prev_site' value="">
<input type='hidden' name='by_sort' value="">

<CENTER>
<TABLE class='table table1' border="0">
       <c:if test="${!(empty requestScope.errors)}">
            <tr>
                <td  align="left">
                    <ul>
                        <c:forEach var="error" items="${requestScope.errors}">
                            <li><span class="error"><fmt:message key="error"/></span>
                        </c:forEach>
                        
                    </ul>
                </td>
            </tr>
        </c:if>

<tr>
 <td width='80%'>
  <fieldset >
   <div class='DivTbody'>
    <table class='table table1'>
      <tr style='background-color:#3a78b6;color:white;vertical-align:center;'>
        <td width='20px'><fmt:message key="siteNN"/></td>
        <td  width='190px' align='center'> <fmt:message key="siteName"/> </td>
        <td  width='190px' align='center' ><fmt:message key="siteDescr"/></td>
        <td  width='90px' align='center'><fmt:message key="siteDate"/></td>
        <td  width='50px' align='center'><fmt:message key="siteLogin"/></td>
      </tr>
      <TBODY id='dynamicTableBody'> </TBODY>
    </table>
   </div>
  </fieldset>
 </td>
 
 <td style='vertical-align:top'>
    <table class='table table1'>
      <tr class='tr1'><td><input type="button"  class='buttom' value="<fmt:message key="addSite"/>" onclick="addSite()"></td></tr>
      <tr class='tr1'><td><input type="button" id='bt_view_site'  class='buttom' value="<fmt:message key="viewSite"/>" onclick="viewSite()"></td></tr>
      <tr class='tr1'><td><input type="button" id='bt_edit_site' class='buttom' value="<fmt:message key="editSite"/>" onclick="editSite()"></td></tr>
      <tr class='tr1'><td><input type="button" id='bt_del_site' class='buttom' value="<fmt:message key="deleteSite"/>" onclick="deleteSite()"></td></tr>
      <tr class='tr1'><td>&nbsp;</td></tr>
      <tr class='tr1'><td><input type="button"  class='buttom' value="<fmt:message key="exit"/>" onclick="exit()"></td></tr>
     </table>
 </td>
</tr>
<tr>
 <td colspan="2">
    <table class='table table1'>
     <tr>
       <td width='200px'>
        <fieldset>
         <legend class='textLegend'><fmt:message key="sort"/></legend>
		    <table class='table table1'>
			   <tr>
			     <td width='20px'><input type="radio" name='sorting' value='1' style='cursor:pointer' <c:if test="${param.by_sort==null || param.by_sort=='1' || param.by_sort==''}">checked</c:if> onclick="sortData(this)"/></td>
			     <td> <fmt:message key="byName"/> </td>
		         <td width='20px'><input type='radio' name='sorting' value='2' style='cursor:pointer' <c:if test="${param.by_sort=='2'}">checked</c:if> onclick="sortData(this)"/></td>
		         <td> <fmt:message key="byDate"/> </td>
		       </tr>
		     </table>      
        </fieldset>
        </td>
        <td>
           <table class='table'><tr>
                   <td align="left">
                    <input type="button"  class='buttom' id='btPrevBuff' disabled value="<fmt:message key="prevBuff"/>" onclick="prevBuff()">
                   </td>
                   <td align="left">
                    <input type="button"  class='buttom' id='btNextBuff' disabled value="<fmt:message key="nextBuff"/>" onclick="nextBuff()">
                   </td> 
              </tr></table>
         </td>
     </tr>
    </table>
 </td>
</tr>
</TABLE>
</CENTER>
</form>
 <iframe name="dataIframe" width=40 height=40 src="" style="visibility:hidden"></iframe>

<script language="JavaScript">

<jsp:include page="resources/messages.js.jsp" flush="true"/>

var contextPath="<c:out value='${contextPath}'/>";
var selectPkID="<c:out value='${param.pkID}' escapeXml='false' default=''/>"; // если пришли по ссылке с экрана просмотра или редактирования
var last_site=""; // последний полученный сайт. Используется при получении следующей десятки	 
var prev_site=""; // то же, только для предыдущих	 
var Data=new Array();
 <c:if test="${!(empty requestScope.sites)}">
   <c:forEach var="site" items="${requestScope.sites}" varStatus="status">
    Data[<c:out value='${status.index}'/>]=new TSiteData(<c:out value='${status.index}'/>,"<c:out value='${site.url}' escapeXml='false' default=''/>", "<c:out value='${site.date}' escapeXml='false' default=''/>", "<c:out value='${site.descriptionList}' escapeXml='false' default=''/>", "<c:out value='${site.login}' escapeXml='false' default=''/>");                            
   </c:forEach>
   if(Data.length>0){
	   last_site=Data[Data.length-1].url;
	   prev_site=Data[0].url;
	  // document.forms[0].last_site.value=Data[Data.length-1].url;
	   document.forms[0].prev_site.value=Data[0].url;
   }
   
   if(document.forms[0].sorting[0].checked)
    sortData(document.forms[0].sorting[0]);
   if(document.forms[0].sorting[1].checked)
    sortData(document.forms[0].sorting[1]);
   
 </c:if>

 <c:if test="${prevnext!=null}">
  <c:if test="${prevnext[0]==1}">
   document.getElementById("btPrevBuff").disabled=false;
  </c:if>
  <c:if test="${prevnext[1]==1}">
   document.getElementById("btNextBuff").disabled=false;
  </c:if>
 </c:if>
 
 
 setBusinessRules();
 
</script>
<jsp:include page="body_bottom_incl.jsp" flush="true"/>

</body>
</html>
