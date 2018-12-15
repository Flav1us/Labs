<%@ page language="java" contentType="text/html;charset=windows-1251" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<fmt:setLocale value="ru" />
<fmt:setBundle basename="resources.Messages" />
<%response.setContentType("text/html;charset=windows-1251");response.getWriter();response.flushBuffer();%>

<c:set value="${pageContext.request.contextPath}" var="contextPath" scope="page"/>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<c:out value='${contextPath}'/>/resources/coolmenus.css">
<title>TestCMS</title>
<script language="JavaScript">

function validate( form ) {
    var result = "";
    var control;
    var setFocus = true;

    if( form.login.value == "" ) {
      result += ' -  <fmt:message key="login.insertYourName"/>.\n';
        control = form.login;
        setFocus = false;
    }
    if( form.password.value == "" ) {
        result += ' - <fmt:message key="login.insertYourPass"/>.\n';
        if( setFocus ) {
            control = form.password;
            setFocus = false;
        }
    }
    if( result == "" ) {
        return true;
    } else {
        result = ' <fmt:message key="login.needToFillOutAllFields"/>.\n' +
                 ' <fmt:message key="login.pleaseFillOutTheFollowing"/>: \n' +
                 result;
        alert( result );
        control.focus();
        return false;
    }
}
</script>
</head>

<body class="bottombody">
<P>&nbsp;
<form action="<c:url value='/login'/>" onSubmit="return validate(this);" method="POST" type="application/x-www-form-urlencoded; charset=windows-1251">

<table cellSpacing="0" cellPadding="0"  width="90%">
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
                      <table cellSpacing="2" cellPadding="0" border="0">
                           <tr>
                                <td vAlign="top" align="left" colSpan="3"> <hr> </td>
                            </tr>
                           <tr>
                                <td align="right">
                                    <font class=text4><fmt:message key="login.YourName"/>:</font>&nbsp;
                                </td>
                                <td>
                                    &nbsp;<input type="text" maxLength="15" style="width:110px" name="login" value="">
                                </td>
                                <td>&nbsp;</td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <font class=text4><fmt:message key="login.YourPass"/>:</font>&nbsp;
                                </td>
                                <td>
                                    &nbsp;<input type="password" maxLength="20" style="width:110px" name="password" alt="blank">
                                </td>
                                <td>&nbsp;</td>
                            </tr>
                            <tr>
                            <td>&nbsp; </td>
                                <td>
                                    &nbsp;<input type="submit"  value="<fmt:message key="login.In"/>" style="width:110px">
                                </td>
                                <td>&nbsp;</td>
                            </tr>
                            <tr>
                                <td vAlign="top" align="left" colSpan="3"><hr></td>
                            </tr>
                     </table>
            </td>
        </tr>
 </table>

</form>

</body>
</html>
