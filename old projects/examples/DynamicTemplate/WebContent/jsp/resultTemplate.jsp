<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="main.TemplateBean, main.ResultTemplateAction"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
TemplateBean bean = (TemplateBean)request.getAttribute("templateBean");
%>

<html>
<head>
<title>Template</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
<script type="text/javascript" src="js/formPage.js"></script>
<script type="text/javascript" src="js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<script>
//UseBean jsp attrib

var oForm = {
	textsqtty	: '<%= bean.getTextsqtty()%>',
	radiosqtty	: '<%= bean.getRadiosqtty()%>',
	checksqtty	: '<%= bean.getChecksqtty()%>'		
}

function init() {
	var aim="#panel2_container";
	$(document).ready(function(){
		$(aim).append('<div class="container">');
		for(var i=0; i<oForm.textsqtty; i++) {
			$(aim).append('<div class="row">');
			$(aim).append('<div class="col-lg-3"><input type="text" name="textField'+i+'" value="textField'+i+'"></div>');
			$(aim).append('</div>');
		}
		for(var i=0; i<oForm.radiosqtty; i++) {
			$(aim).append('<div class="row">');
			$(aim).append('<div class="col-lg-1"><input type="radio" name="fieldRadio'+i+'"></div>');
			$(aim).append('<div class="col-lg-3">Radio'+i+'</div>')
			$(aim).append('</div>');
		}
		for(var i=0; i<oForm.checksqtty; i++) {
			$(aim).append('<div class="row">');
			$(aim).append('<div class="col-lg-1"><input type="checkbox" name="fieldCheck'+i+'"></div>');
			$(aim).append('<div class="col-lg-3">Check'+i+'</div>');
			$(aim).append('</div>');
		}
		for(var i=0; i<oForm.checksqtty; i++) {
			$(aim).append('<div class="row">');
			$(aim).append('<div class="col-lg-3"><select><option label="select'+i+'" value="select'+i+'"></select></div>');
			$(aim).append('</div>');
		}
		
		$(aim).append('</table>');
	})
}

function createTextInputs() {
	for(var t=1; t<=oForm.textsqtty; t++) {
		appendInput('text', 'fieldText'+t);
	}
}
function createRadioInputs() {
	for(var t=1; t <= oForm.radiosqtty ; t++) {
		appendInput('radio', 'fieldRadio');
	}
}
function createCheckInputs() {
	for(var t=1; t<=oForm.checksqtty; t++) {
		appendInput('checkbox', 'fieldCheck'+t);
	}
}

function appendInput(type, name) {
	var input = document.createElement('input');
	input.type = type;
	input.name = name;
	document.body.appendChild(input);
} 

</script>
</head>

<body onload="init()">

<ul class="nav nav-tabs">
	<li class="active"><a data-toggle="tab" href="#panel1" >JavaScript</a></li>
	<li><a data-toggle="tab" href="#panel2" >jQuery</a></li>
</ul>

<div class="tab-content">
	<div id="panel1" class="tab-pane fade in active">
		<div class="container" style="margin-top:20px"> 
			<%if (bean.getTextsqtty() != 0) {%>
				<table>
					<%for(int i=0; i<bean.getTextsqtty(); i++) { %>
					<tr>
						<td style="width:30%"><input type = "text" name = "textField<%=i%>" value = "textField<%=i%>"></td>
					</tr>
				<%} %>
				</table>
				</br>
			<%} %>
	
			<%if (bean.getRadiosqtty() != 0) { %>
				<table>
					<%for (int i=0; i<bean.getRadiosqtty(); i++) {%>
						<tr>
							<td style="width:2%"><input type="radio" name="radio<%=i%>"></td>
							<td style="width:30%">Radio<%=i%></td>
						</tr>
					<%}%>
				</table>
				</br>
			<%}%>
			
			<%if (bean.getChecksqtty() != 0) { %>
				<table>
					<%for (int i=0; i<bean.getChecksqtty(); i++) {%>
						<tr>
							<td style="width:2%"><input type="checkbox" name="check<%=i%>"></td>
							<td style="width:30%">Checkbox<%=i%></td>
						</tr>
					<%}%>
				</table>
				</br>
			<%}%>
			
			<%if (bean.getSelectsqtty() != 0) { %>
				<select>
					<%for (int i=0; i<bean.getSelectsqtty(); i++) { %>
						<option label="select<%=i%>" value="select<%=i%>">
					<%} %>		
				</select>
			<%} %>
			</div>
		</div>
	<div id="panel2" class="tab-pane fade" style="margin-top:20px">
		<div id="panel2_container" class="container" style="margin-top:20px">
		</div>
	</div>
</div>

</body>
</html>