<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <%@ taglib prefix="mm" tagdir="/WEB-INF/tags/messages" %>
    <%@ taglib prefix="tt" tagdir="/WEB-INF/tags/templating" %>
<%--    <c:import url="../assets/addons/header.jsp"> --%>
<%--    	<c:param name="tituloDaPagina">Memoriam | Login </c:param> --%>
<%--    	<c:param name="tituloDaSecao">Entrar</c:param> --%>
<%--    	<c:param name="navbarColor">red lighter-2</c:param> --%>
<%--    </c:import> --%>

<c:if test="${not empty sessionScope.usuario}">
<c:redirect url="/controller.do?op=showct"/>
</c:if>

<tt:template title="Memoriam | Login" tsectionHeader="Entrar" navbarColor="blue">
  <jsp:body>
	<mm:messages value="${msgs}" erroStyle="color:red" infoStyle="color:blue" avisoStyle="color:orange" />
	
   <form class="form-signin" action="${pageContext.request.contextPath }/controller.do" method="post">
   <input type="hidden" id="op" name="op" value="login" />
   <div class="container">
     <div class="row">
	   <div class="input-field col s12">
	     <i class="material-icons prefix">perm_identity</i>
	     <input type="email" name="loginEmail" id="loginEmail" value="${cookie['loginCookie'].value}">
	     <label for="loginEmail">Email</label>
	   </div>
	   </div>
	   <div class="row">
	     <div class="input-field col s8">
	       <i class="material-icons prefix">lock</i>
	       <input type="password" name="loginSenha" id="loginSenha">
	     <label for="loginSenha">Senha</label>
	     </div>
	     <div class="input-field col s4">
		   <input type="checkbox" id="lembrar" name="lembrar" class="filled-in" />
		    <label for="lembrar">Lembrar-me</label> 
  		 </div>
	   </div>
       <div class="row">
         <div class="input-field col s6 offset-s3">
           <button class="btn waves-effect waves-light col s12" type="submit" name="action">
	         Entrar<i class="material-icons right">chevron_right</i>
           </button>
         </div>
       </div>
     </div>
   </form>

  </jsp:body>
</tt:template>
