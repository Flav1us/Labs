<%@ page contentType="text/html; charset=windows-1251" %>
<%@ page import="java.io.PrintWriter"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%response.setContentType("text/html;charset=windows-1251");response.getWriter();response.flushBuffer();%>
<fmt:setLocale value="${sessionScope.locale}" />
<c:set value="${pageContext.request.contextPath}" var="contextPath" scope="page"/>

<html>
<head>
<link rel="stylesheet" type="text/css" href="<c:out value='${contextPath}'/>/resources/coolmenus.css">
<title>
error.jsp
</title>

</head>

<body class='bottombody'>
<%
 Throwable t = (Throwable) request.getAttribute("javax.servlet.error.exception");
try {
      if (t instanceof ServletException) {
        // move down to get first non-ServletException
        Throwable causedException = null;
        ServletException servletException = (ServletException) t;
        while(true) {
            Throwable current = servletException.getRootCause();
            if (current == null) {
                break;
            } else if (current instanceof ServletException) {
                servletException = (ServletException) current;
            } else {
                causedException = current;
                break;
            }
        }
        if (causedException != null) {
            t = causedException;
        }
    }
}catch(Exception e){e.printStackTrace();}
%>
<table align=center width=90%>
    <tr>
        <td>
            В результате вашего последнего запроса возникла ошибка:
        </td>
    </tr>
    <tr>
        <td class="error">
            <% try { 	out.println(t.getMessage());}catch(Exception e){e.printStackTrace();}%>
        </td>
    </tr>
    <tr>
        <td>
            <div style="border: groove; height: 100px; display: block; overflow: auto;">
                <% try {  t.printStackTrace (new PrintWriter (out));}catch(Exception e){e.printStackTrace();}%>
            </div>
        </td>
    </tr>
</table>
</body>
</html>
