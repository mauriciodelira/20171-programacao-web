<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Informa��es de usu�rio</title>
</head>
<body>
<h1>Bem vindo, usu�rio ${user}</h1>
<h3>Seus dados</h3>
<ul><li>Nome: ${user}</li>
<li>Idade: ${age}</li></ul>
<br/><p>Est documento foi servido por meio do m�todo ${pageContext.request.method}.</p><br/>
URL de acesso ao m�todo: ${pageContext.request.requestURI }.<br/>
QueryString: ${pageContext.request.queryString}.
<h4>The end.</h4>
</body>
</html>