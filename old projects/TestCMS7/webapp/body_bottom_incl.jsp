<%@ page language="java" contentType="text/html;charset=windows-1251" %>
<%@ page import="java.util.Date" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%response.setContentType("text/html;charset=windows-1251");response.getWriter();response.flushBuffer();%>


<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="resources.Messages" />

<c:set value="${pageContext.request.contextPath}" var="contextPath" scope="page"/>

<table border="0" align="center" width="90%" cellpadding=0 cellspacing=0 class="text4">
    <tr bgcolor='#C3C3C3'>
    <td nowrap align="right" > <B><font style="text-decoration : underline;"> <%= new java.text.SimpleDateFormat("dd.MM.yyyy").format(new Date())%></font></B>
    </td>
    <td nowrap width="100%">&nbsp; </td>
        <td nowrap align="right">
            <font id="uid1" style="font-size:10px;height:16px">
                <fmt:message key="user">
                    <fmt:param value="${sessionScope.userLogin}"/>
                </fmt:message>
            </font>
        </td>
    </tr>
</table>
