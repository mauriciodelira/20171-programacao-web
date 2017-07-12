<%@ tag description="Barra de navegação comum às páginas" body-content="empty" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="navbarColor" required="true" %>

<c:set var="perfil" value="${sessionScope.usuario.perfil}"/>
<c:set var="nome" value="${sessionScope.usuario.nome}"/>
<div class="navbar-fixed">
   <nav id="navbar-topo">
     <div class="nav-wrapper ${navbarColor}">
     <a class="brand-logo" href="${pageContext.request.contextPath}">Memoriam<i class="material-icons right">contact_phone</i></a>
     <c:if test="${not empty sessionScope.usuario}">
	     <a href="#" data-activates="mobile-navbar" class="button-collapse hide-on-med-and-up"><i class="material-icons right">menu</i></a>

	     <ul class="right hide-on-small-only">
	       <li>
	         <form action="${pageContext.request.contextPath}/controller.do" method="get" id="search-form">
	           <input type="hidden" id="op" name="op" value="pesquisar"/>
	           <div class="row">
	           <div class="input-field inline">
	             <input id="q" name="q" type="search" placeholder="Buscar contato" required>
	             <label class="label-icon" for="q"><i class="material-icons">search</i></label>
	           </div>
	           </div>
	         </form>
	       </li>
	       <li><a href="${pageContext.request.contextPath}/controller.do?op=showct">Contatos<i class="material-icons right">contacts</i></a></li>
	       <c:if test="${perfil eq 'ADMIN' }">
	       <li><a href="${pageContext.request.contextPath}/controller.do?op=showop">Operadoras<i class="material-icons right">network_cell</i></a></li>
	       <li><a href="${pageContext.request.contextPath}/controller.do?op=showus">Usuários<i class="material-icons right">people</i></a></li>
	       </c:if>
	       <li><a class="dropdown-button" href="#" data-activates="dropdown-user" data-beloworigin="true">${sessionScope.usuario.nome} <i class="material-icons right ">keyboard_arrow_down</i></a></li>
	     </ul>
	     
			  
		 <ul id="dropdown-user" class="dropdown-content">
	       	<li><a id="form-submit" href="#">Sair</a></li>
	       	<li class="divider"></li>
	       	<li><a href="#">${sessionScope.usuario.perfil}</a></li>
	     </ul>     
	     
	     <ul class="side-nav" id="mobile-navbar">
	        <li><a href="${pageContext.request.contextPath}/controller.do?op=showct">Contatos<i class="material-icons right">people</i></a></li>
	       <li><a href="${pageContext.request.contextPath}/controller.do?op=showop">Operadoras<i class="material-icons right">network_cell</i></a></li>
	       <li><a class="dropdown-button" href="#" data-activates="dropdown-user-mobile" data-beloworigin="true">${sessionScope.usuario.nome} <i class="material-icons right ">keyboard_arrow_down</i></a></li>
	     </ul>
		 <ul id="dropdown-user-mobile" class="dropdown-content">
	       	<li><a id="form-submit-mobile" href="#">Sair</a></li>
	       	<li class="divider"></li>
	       	<li><a href="#">${perfil}</a></li>
	     </ul>    
     </c:if>
     </div>
   </nav>
   
   <form id="logout-form" action="${pageContext.request.contextPath}/controller.do" method="post">
   <input type="hidden" name="op" value="logout"/>
   </form>
   
  </div> 