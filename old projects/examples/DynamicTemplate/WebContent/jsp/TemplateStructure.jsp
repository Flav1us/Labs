<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap,main.ResultTemplateAction" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Template Structure</title>
</head>
<body>
<% HashMap errorMap = (HashMap)request.getAttribute("errorMap"); %>

<%if(errorMap != null) {%>
	<%if(errorMap.get(ResultTemplateAction.TEXTS_QTTY) != null) {%>
		<%=errorMap.get(ResultTemplateAction.TEXTS_QTTY)%><br>
	<%} %>
	<%if(errorMap.get(ResultTemplateAction.RADIOS_QTTY) != null) {%>
		<%=errorMap.get(ResultTemplateAction.RADIOS_QTTY)%><br>
	<%} %>
	<%if(errorMap.get(ResultTemplateAction.CHECKS_QTTY) != null) {%>
		<%=errorMap.get(ResultTemplateAction.CHECKS_QTTY)%><br>
	<%}} %>


<form action="/DynamicTemplate/ResultTemplate" method="post">
	<table style="width: 100%">
	<tr>
		<td style="width:20%">
			<label for='idTexts'> Количество текстовых полей в шаблоне</label>
		</td>
		<td>
			<input type='text' name='<%=ResultTemplateAction.TEXTS_QTTY%>' id='idTexts' value='<%=request.getAttribute(ResultTemplateAction.TEXTS_QTTY) != null ? request.getAttribute(ResultTemplateAction.TEXTS_QTTY) : ""  %>' >
		</td>
	</tr>
	<tr>
		<td style="width:20%">
			<label for='idRadios'> Количество radios</label>
		</td>
		<td>
			<input type='text' name='<%=ResultTemplateAction.RADIOS_QTTY%>' id='idRadios' value='<%=request.getAttribute(ResultTemplateAction.RADIOS_QTTY) != null ? request.getAttribute(ResultTemplateAction.RADIOS_QTTY) : ""  %>' >
		</td>
	</tr>
	<tr>
		<td style="width:20%">
			<label for='idChecks'> Количество checks</label>
		</td>
		<td>
			<input type='text' name='<%=ResultTemplateAction.CHECKS_QTTY%>' id='idChecks' value='<%=request.getAttribute(ResultTemplateAction.CHECKS_QTTY) != null ? request.getAttribute(ResultTemplateAction.CHECKS_QTTY) : ""  %>' >
		</td>
	</tr>
	<tr>
		<td style="width:20%">
			<label for='sq'> Количество select</label><br>
		</td>
		<td>
			<input type='text' name='selectsqtty' id='sq'>
		</td>
	</tr>
	</table>	
	
	<input type='submit' value="Подтвердить вид шаблона">
</form>
</body>
</html>