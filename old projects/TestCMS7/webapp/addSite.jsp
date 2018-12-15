<%@ page language="java" contentType="text/html;charset=windows-1251" %>
<%@ page import="ua.com.testcms.dto.SiteDto,java.util.Date"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="resources.Messages" />
<%response.setContentType("text/html;charset=windows-1251");response.getWriter();response.flushBuffer();%>

<c:set value="${pageContext.request.contextPath}" var="contextPath" scope="page"/>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<c:out value='${contextPath}'/>/resources/coolmenus.css">
<script language="JavaScript" src="resources/SiteList.js"></script>
<title>TestCMS</title>

<script language="JavaScript">
<jsp:include page="resources/messages.js.jsp" flush="true"/>

function validate(obj){
try{
	var re = new RegExp(/^http:\/\/.+/)
	var input = document.forms[0].url.value;
	var index = input.search(re);
	if(index==-1){
	 alert(localesStr['urlMask']);
	 return false;
	}

return true;
}catch(e){alert(e.message); return false;}
}
</script>
</head>
<body class="bottombody">
<br>
   <table border=0 align=center width=90%>
     <tr><td align=center><P class=header2><fmt:message key="newSite"/></p></td></tr>
    </table>

<br>

<form action="<c:url value='siteedit'/>" onSubmit="return validate(this);" >
<input type='hidden' name='actionId' value="add">
<input type='hidden' name='last_site' value="<c:out value='${param.last_site}' escapeXml='false' default=''/>">
<input type='hidden' name='prev_site' value="<c:out value='${param.prev_site}' escapeXml='false' default=''/>">
<input type='hidden' name='by_sort' value="<c:out value='${param.sorting}' escapeXml='false' default=''/>">

<CENTER>

<TABLE class='table' width='55%'>
       <c:if test="${!(empty requestScope.errors)}">
            <tr>
                <td  align="left">
                    <ul>
                        <c:forEach var="error" items="${requestScope.errors}">
                            <li><span class="error"><fmt:message key="${error}"/></span>
                        </c:forEach>
                        
                    </ul>
                </td>
            </tr>
        </c:if>

<tr>
 <td align="center">
   <fieldset>
    <table class='table table1'>
      <tr>
        <td  width='150px' align="right"> <fmt:message key="siteName"/>: </td>
        <td><input class="text1" name="url" type="text" value="<c:out value='${requestScope.site.url}' escapeXml='false' default='http://'/>" onkeypress="return checkUrl(event)"></td>
      </tr>
      <tr>
        <td align="right"><fmt:message key="siteDescr"/>:</td>
        <td><textarea name='description'  rows=5 style='width:250px;' ><c:out value='${requestScope.site.description}' escapeXml='false' default=''/></textarea></td>
      </tr>
    </table>
   </fieldset>
 </td>
  <td  style='vertical-align:top'>
    <table class='table'>
     <tr>
       <td align="left">
         <input type="submit"  class='buttom' value="<fmt:message key="save"/>" >
       </td>
     </tr>
     <tr>  
       <td align="left">
         <input type="button"  class='buttom' value="<fmt:message key="list"/>" onclick="backToList()">
       </td> 
     </tr>
    </table>
  </td>
</tr>

</TABLE>

</CENTER>
</form>

<script language="JavaScript">
var contextPath="<c:out value='${contextPath}'/>";
</script>
<br/>
<jsp:include page="body_bottom_incl.jsp" flush="true"/>

</body>
</html>
