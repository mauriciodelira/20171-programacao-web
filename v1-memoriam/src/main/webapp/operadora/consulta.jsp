<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <%@ taglib prefix="mm" tagdir="/WEB-INF/tags/messages" %>
    <%@ taglib prefix="tt" tagdir="/WEB-INF/tags/templating" %>
    
    
<%--    <c:import url="../assets/addons/header.jsp"> --%>
<%--    	<c:param name="tituloDaPagina">Memoriam | Operadoras </c:param> --%>
<%--    	<c:param name="tituloDaSecao">Listar operadoras</c:param> --%>
<%--    	<c:param name="navbarColor">deep-purple darker-2</c:param> --%>
<%--    </c:import> --%>

<c:if test="${sessionScope.usuario.perfil ne 'ADMIN'}">
<c:redirect url="/erro/"/>
</c:if>

<tt:template title="Memoriam | Operadoras" tsectionHeader="Listar operadoras" navbarColor="deep-purple darker-2">

  <jsp:attribute name="tscript">
  <script type="text/javascript">
  function seeCheckedBoxes() {
    console.log($('.operadoras-checked:checked'));
	  if ($('.operadoras-checked:checked').length>0) {
	    $('#btnSubmitExcluir').show();
	  } else {
	    $('#btnSubmitExcluir').hide();
	  }
    }
  
    $(document).ready(function () {
	  console.log("Documento pronto");
	  $('#check-todos').change(function(){
        $('.operadoras-checked').not(this).prop('checked', this.checked);
	    seeCheckedBoxes();
	  });
	  $('.operadoras-checked').change(seeCheckedBoxes);
    });
    
    $('#btnSubmitExcluir').click(function(){
	  var opOuOps;
	  $('.operadoras-checked:checked').length>1 ? opOuOps = "operadoras" : opOuOps = "operadora";
	  if(!confirm('Você realmente deseja excluir '+$('.operadoras-checked:checked').length + ' ' + opOuOps+'?'))
  	    return false;
    });
  </script>
  </jsp:attribute>
  <jsp:body>
	<mm:messages value="${msgs}" erroStyle="color:red" infoStyle="color:blue" avisoStyle="color:orange" />
   
   <div class="row">
     <div class="col s10 offset-s1">
     
     <form action="${pageContext.request.contextPath }/controller.do?op=delop" method="post">
     <input type="hidden" name="op" value="delop"/>
     <table class="highlight">
       <thead>
         <tr>
           <th class="center-align"><input type="checkbox" id="check-todos"
               name="todos-checkbox" class="filled-in" /><label for="check-todos"> </label></th>
           <th>Operadora</th>
           <th>DDD</th>
         </tr>
       </thead>
     
       <tbody>
       <c:forEach var="operadora" items="${ operadoras }" varStatus="status">
         <tr>
           <td class="center-align">
             <input type="checkbox" id="${operadora.id}" value="${operadora.id}"
               name="marcadosParaDeletar" class="operadoras-checked filled-in" />
             <label for="${operadora.id}"> </label>
           </td>
           <c:url value="controller.do?op=editop&id=${ operadora.id }" var="editURL"/>
           <td><a href="${ editURL }">${ operadora.nome }</a></td>
           <td><label for="${ status.count }">${ operadora.prefixo }</label></td>
         </tr>
       </c:forEach>
       </tbody>
     </table>
     
     <div class="row" style="margin-top: 25px;">
       <button type="submit" id="btnSubmitExcluir" 
          class="waves-effect waves-light btn red light-2 col s4 m3">Excluir</button>
   <c:url value="operadora/cadastro.jsp" var="cadastroNovaOperadora" ></c:url>
       <button type="submit" formaction="${ cadastroNovaOperadora }" 
          class="waves-effect waves-light btn green darken-2 col s7 m8 offset-s1 offset-m1">Novo</button>
     </div>
     
     </form>
     
     </div>
   </div>
  </jsp:body>
  
</tt:template>
   
<%--    <c:import url="../assets/addons/footer.jsp" /> --%>