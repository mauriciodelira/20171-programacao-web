<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><c:out value="${ param.tituloDaPagina }" /></title>

<!-- MaterializeCSS: -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/materialize/css/materialize.min.css">

<!-- Google Icons -->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

<!-- JQuery -->
<script src="${pageContext.request.contextPath}/assets/materialize/js/jquery-3.2.1.min.js"></script>
    
<!-- BootstrapCDN: -->
<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous"> -->
<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous"> -->
</head>
<style>
.body-container{
  padding:25px;
  background-color:#eceff1;
  border-radius:10px;
  margin: 25px auto;
}

.filled-in[type="checkbox"]:checked + label::after{
  border-color: #1c313a;
  background-color: #455a64;
}
a > i.material-icons{
  vertical-align: bottom;
}
nav .brand-logo{
  white-space: pre-line;
}
.brand-logo{
  margin-left: 10%;
}
#sidenav-overlay{
  z-index: 990;
}

.section-title{
  margin-left:5%;
}
h4>i.material-icons{
  font-size:42px;
  vertical-align: middle;
}
table.highlight > tbody > tr:hover{
  background-color: #cdd7db;
}
#btnSubmitExcluir{
  display: none;
  opacity: 1;
  transition: visibility 0s, opacity 0.5s linear;
}
</style>


<body>
  
   <div class="container body-container" ><!-- class=container finaliza em footer.jsp -->
   <c:if test="${not empty param.tituloDaSecao}">
   <div class="row">
      <h4 class="section-title"> <i class="material-icons prefix">keyboard_arrow_right</i> <c:out value="${ param.tituloDaSecao }" /></h4>
   </div>
   </c:if>