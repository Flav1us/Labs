<%@ page language="java" contentType="text/html;charset=windows-1251" %>
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
function validate(obj){
return true;
}
</script>
</head>
<body class="bottombody">
<br>
   <table border=0 align=center width=90%>
     <tr><td align=center><P class=header2><fmt:message key="siteEdit"/></p></td></tr>
    </table>

<br>

<form action="<c:url value='/siteedit'/>" onSubmit="return validate(this);" >
<input type='hidden' name='actionId' value="edit">
<input type='hidden' name='pkID' value="<c:out value='${requestScope.site.url}' escapeXml='false' default=''/>">
<input type='hidden' name='prev_site' value="<c:out value='${param.prev_site}' escapeXml='false' default=''/>">
<input type='hidden' name='by_sort' value="<c:out value='${param.sorting}' escapeXml='false' default=''/>">

<CENTER>
<TABLE class='table1' border="0">
<TABLE class='table' width='50%'>
<tr>
 <td align="center">
   <fieldset>
    <table class='table table1'>
      <tr>
        <td  width='100px' align="right"> <fmt:message key="siteName"/>: </td>
        <td><input class="text text1 text3" name="url" type="text" readonly value="<c:out value='${requestScope.site.url}' escapeXml='false' default='http://'/>"></td>
      </tr>
      <tr>
        <td align="right"><fmt:message key="siteDescr"/>:</td>
        <td><textarea name='description'  rows=5 style='width:300px;' ><c:out value='${requestScope.site.description}' escapeXml='false' default=''/></textarea></td>
      </tr>
      <tr>
        <td  width='100px' align="right"> <fmt:message key="siteDate"/>: </td>
        <td><input class="text text2 text3" name="data" type="text" readonly value="<c:out value='${requestScope.site.date}' escapeXml='false' default='http://'/>"></td>
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
