<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Prática 4x01</title>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.2/css/materialize.min.css">
	<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.2/js/materialize.min.js"></script>
</head>
<body style="max-width:100px;margin:30px auto;">
	<h4>Temperatura: ${temp}°${med}</h4>
	<form method="GET" action="converter">
		<label for="temp">Temperatura</label><input type="number" name="temp" /><br/>
		<input type="radio" name="med" value="F" id="F" class="with-gap"/>
		<label for="F">Fahreheint</label><br/>
		<input type="radio" name="med" value="C" id="C" class="with-gap"/>
		<label for="C">Celsius</label><br><br>
		<button class="btn waves-effect waves-light" type="submit" name="go">Transformar!</button>
	</form>
</body>
</html>