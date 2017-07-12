<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <%@ taglib prefix="mm" tagdir="/WEB-INF/tags/messages" %>
    <%@ taglib prefix="tt" tagdir="/WEB-INF/tags/templating" %>
    
<c:if test="${sessionScope.usuario.perfil ne 'ADMIN'}">
<c:redirect url="/erro/"/>
</c:if>

<tt:template title="Memoriam | Novo usuário" tsectionHeader="Dados do usuário" navbarColor="indigo darker-1">
   
  <jsp:attribute name="tscript">
  <script type="text/javascript">
  $(document).ready(function() {
	    Materialize.updateTextFields();
	  });
  </script>
  </jsp:attribute>
  
  <jsp:body> 
	<mm:messages value="${msgs}" erroStyle="color:red" infoStyle="color:blue" avisoStyle="color:orange" />
   
   <div class="row">
   <div class="col s10 offset-s1">
     
   <form action="${pageContext.request.contextPath}/controller.do" method="POST" id="controleUsuario">
     <input type="hidden" name="op" value="newus" />
     <input type="hidden" name="id" value="${user.id}"/> 
     
     <div class="row">
       <div class="input-field col s8">
         <i class="material-icons prefix">perm_identity</i>
         <input id="nome" name="nome" type="text" value="${user.nome }"/>
         <label for="nome">Nome</label>
       </div>
       <div class="input-field col s4">
       <select id="perfil" name="perfil" class="browser-default">
            <c:if test="${ perfil.nome ne user.perfil.nome }">
        	<option value="" disabled selected>Selecione um perfil</option>
        	</c:if>
        	<c:if test="${ perfil.nome eq user.perfil.nome }">
        	 <option value="" disabled>Selecione um perfil</option>
        	</c:if>
        	<c:forEach var="perfil" items="${utilBean.perfis}">
        	  <c:if test="${ perfil eq user.perfil}">
        	    <option value="${perfil}" label="${ perfil.nome }" selected>
        	      ${ perfil.nome }
        	    </option>
        	  </c:if> 
        	  <c:if test="${perfil ne user.perfil }">
        	    <option value="${perfil}" label="${perfil.nome}">
        	      ${perfil.nome}
        	    </option>
         	  </c:if>
         	</c:forEach>
         </select>
       </div>
     </div>
     
     <div class="row">
       <div class="input-field col s12">
         <i class="material-icons prefix">email</i>
         <input id="email" name="email" type="text" value="${user.email }"/>
         <label for="email">Email</label>
       </div>
     </div>
     
     <div class="row">
       <div class="input-field col s12">
         <i class="material-icons prefix">lock</i>
         <input id="senha" name="senha" type="password" value=""/>
         <label for="senha">Senha</label>
       </div>
     </div>
     
     <div class="row">
       <div class="input-field col s8">
         <i class="material-icons prefix">check_box</i>
         <input id="ativo" class="filled-in" name="ativo" type="checkbox" checked="${user.ativo}"/>
         <label for="ativo">Ativo?</label>
       </div>
     </div>
     
     
     <div class="row">
       <button class="btn waves-effect waves-light col s6 m12" type="submit" name="action">
	     Salvar<i class="material-icons right">done</i>
       </button>
      </div>
   </form>
   
   </div><!-- fim col s10 -->
   </div><!-- fim row -->
   <c:set var="endofconversation" value="true" scope="request" />
  
  </jsp:body>

</tt:template>
   
