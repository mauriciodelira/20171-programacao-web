<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <%@ taglib prefix="mm" tagdir="/WEB-INF/tags/messages" %>
    <%@ taglib prefix="tt" tagdir="/WEB-INF/tags/templating" %>
    
<c:if test="${sessionScope.usuario.perfil ne 'ADMIN'}">
<c:redirect url="/erro/"/>
</c:if>
   
<tt:template title="Memoriam | Nova operadora" tsectionHeader="Dados da operadora" navbarColor="purple darker-1">
   
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
     
   <form action="${pageContext.request.contextPath}/controller.do" method="POST" id="controleOperadora">
     <input type="hidden" name="op" value="newop" />
     <input type="hidden" name="idOp" value="${operadora.id}"/> 
     
     <div class="row">
     <div class="input-field col s12">
         <i class="material-icons prefix">business</i>
         <input id="nomeOp" name="nomeOp" type="text" value="${ operadora.nome }"/>
         <label for="nomeOp">Nome</label>
       </div>
     </div>
     <div class="row">
       <div class="input-field col s12">
         <i class="material-icons prefix">dialpad</i>
         <input type="text" id="prefixoddd" name="prefixoddd" value="${ operadora.prefixo }" />
         <label for="prefixoddd">Prefixo (DDD) (apenas números)</label>
       </div>
     </div>
<%--      <fmt:formatDate var="varDataAniv" value="${ contato.dataAniversario }" pattern="dd/MM/yyyy" /> --%>
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
   
