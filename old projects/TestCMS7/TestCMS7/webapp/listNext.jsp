<%@ page contentType="text/html; charset=windows-1251" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<html>
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1251">
</head>
<body class="bottombody">

<script language="JavaScript">

window.parent.clearList();
window.parent.Data=new Array();

 <c:if test="${!(empty requestScope.sites)}">
   <c:forEach var="site" items="${requestScope.sites}" varStatus="status">
    window.parent.Data[<c:out value='${status.index}'/>]=new window.parent.TSiteData(<c:out value='${status.index}'/>,"<c:out value='${site.url}' escapeXml='false' default=''/>", "<c:out value='${site.date}' escapeXml='false' default=''/>", "<c:out value='${site.descriptionList}' escapeXml='false' default=''/>", "<c:out value='${site.login}' escapeXml='false' default=''/>");                            
   </c:forEach>
   if(window.parent.Data.length>0){
	   window.parent.last_site=window.parent.Data[window.parent.Data.length-1].url;
	   window.parent.prev_site=window.parent.Data[0].url;
	   window.parent.document.forms[0].prev_site.value=window.parent.Data[0].url;
	   
   }   
   if(window.parent.document.forms[0].sorting[0].checked)
    window.parent.sortData(window.parent.document.forms[0].sorting[0]);
   if(window.parent.document.forms[0].sorting[1].checked)
    window.parent.sortData(window.parent.document.forms[0].sorting[1]);
   window.parent.setBusinessRules();
    <c:if test="${prevnext!=null}">
        <c:if test="${prevnext[0]==0}">
          window.parent.document.getElementById("btPrevBuff").disabled=true;
        </c:if>
        <c:if test="${prevnext[0]==1}">
    	   window.parent.document.getElementById("btPrevBuff").disabled=false;
        </c:if>
        <c:if test="${prevnext[1]==0}">
	       window.parent.document.getElementById("btNextBuff").disabled=true;
        </c:if>
        <c:if test="${prevnext[1]==1}">
           window.parent.document.getElementById("btNextBuff").disabled=false;
        </c:if>
	 </c:if>
   
 </c:if>
</script>
</body>
</html>

 