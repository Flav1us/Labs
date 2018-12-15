<%@ page language="java" contentType="text/html;charset=windows-1251"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="resources.Messages" />
<%response.setContentType("text/html;charset=windows-1251");response.getWriter();response.flushBuffer();%>


var localesStr = {'shouldYouDel'      : '<fmt:message key="shouldYouDel"/>',
				  'sites'             : '<fmt:message key="sites"/>',
				  'site'              : '<fmt:message key="site"/>',
				  'urlMask'           : '<fmt:message key="siteUrl"/>'
}
//localesStr['urlMask']