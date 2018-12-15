<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://code.jquery.com/jquery-1.10.2.js"
	type="text/javascript"></script>
<script src="js/upload.js" type="text/javascript"></script>
<title>Ark</title>
</head>
<body>
	<form action="/ArcantosServer/arcantos" method="post">
		<input type="text" value="https://www.youtube.com" name="target_webpage">
		<input type="submit" value="Начать сканирование">
	</form>
	<br><br>
	<div id="dynamic_textfield"></div>
</body>
</html>