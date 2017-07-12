<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <%@ taglib prefix="mm" tagdir="/WEB-INF/tags/messages" %>
   <%@ taglib prefix="tt" tagdir="/WEB-INF/tags/templating" %>
    
<%--    <c:import url="../assets/addons/header.jsp"> --%>
<%--    	<c:param name="tituloDaPagina">Memoriam | Novo contato</c:param> --%>
<%--    	<c:param name="tituloDaSecao">Dados do contato</c:param> --%>
<%--    	<c:param name="navbarColor">blue darker-1</c:param> --%>
<%--    </c:import> --%>
<%--    <c:if test="${not empty msgs}"> --%>
<!--    <div class="row"> -->
<!--      <div style="color: red;" class="col s4 offset-s4"> -->
<!--        <ul class="collection"> -->
<%--        <c:forEach var="msg" items="${msgs}"> --%>
<%--          <li class="collection-item dismissable">${ msg }</li> --%>
<%--        </c:forEach> --%>
<!--        </ul> -->
<!--      </div> -->
<!--    </div> -->
<%--    </c:if> --%>

<tt:template title="Memoriam | Novo contato" tsectionHeader="Dados do contato" navbarColor="blue darker-1">
    
    
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
     
   <form action="${pageContext.request.contextPath}/controller.do" method="POST" id="controleContato">
     <input type="hidden" name="op" value="newct" />
     <input type="hidden" id="idCt" name="idCt" value="${contato.id}"/> 
     
     <div class="row">
     <div class="input-field col s12">
         <i class="material-icons prefix">perm_identity</i>
         <input id="nomeCt" name="nomeCt" type="text" value="${ contato.nome }"/>
         <label for="nomeCt">Nome</label>
       </div>
     </div>
     <div class="row">
       <div class="input-field col s12">
         <i class="material-icons prefix">phone</i>
         <input type="text" id="foneCt" name="foneCt" value="${ contato.fone }" />
         <label for="foneCt">Telefone (apenas números)</label>
       </div>
     </div>
     
     <fmt:formatDate var="varDataAniv" value="${ contato.dataAniversario }" pattern="dd/MM/yyyy" />
     
     <div class="row">
       <div class="input-field col s6">      
         <i class="material-icons prefix">cake</i>
         <input type="date" class="datepicker active" id="dataaniv" name="dataaniv" value="${varDataAniv}" />    
       </div>
         
     <div class="input-field col s6">
       <select id="operadoraCt" name="operadoraCt" class="browser-default">
        <c:if test="${ operadora.id ne contato.operadora.id }">
       	  <option value="" disabled selected>Selecione uma operadora</option>
       	</c:if>
       	<c:if test="${ operadora.id eq contato.operadora.id }">
       	 <option value="" disabled>Selecione uma operadora</option>
       	</c:if>
       	<c:forEach var="operadora" items="${utilBean.operadoras}">
       	  <c:if test="${ operadora.id eq contato.operadora.id}">
       	    <option value="${operadora.id}" label="${ operadora.nome }" selected>
       	      ${ operadora.nome }
       	    </option>
       	  </c:if> 
       	  <c:if test="${operadora.id ne contato.operadora.id }">
       	    <option value="${operadora.id}" label="${ operadora.nome }">
       	      ${ operadora.nome }
       	    </option>
       	  </c:if>
       	</c:forEach>
       </select>
     </div></div>
     <div class="row">
       <button class="btn waves-effect waves-light col s6 m12" type="submit" name="action">
	     Salvar<i class="material-icons right">done</i>
       </button>
      </div>
   </form>
   </div>
   </div>
   <c:set var="endofconversation" value="true" scope="request" />
</jsp:body>
  
</tt:template>
  
  
<%--    <c:import url="../assets/addons/footer.jsp" /> --%>