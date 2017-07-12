<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
   <%@ taglib prefix="mm" tagdir="/WEB-INF/tags/messages" %>
   <%@ taglib prefix="tt" tagdir="/WEB-INF/tags/templating" %>

<%--    <c:import url="../assets/addons/header.jsp"> --%>
<%--    	<c:param name="tituloDaPagina">Memoriam | Contatos </c:param> --%>
<%--    	<c:param name="tituloDaSecao">Listar contatos</c:param> --%>
<%--    	<c:param name="navbarColor">indigo darker-2</c:param> --%>
<%--    </c:import> --%>

<tt:template title="Memoriam | Contatos" tsectionHeader="Listar contatos" navbarColor="blue lighten-2">
<jsp:attribute name="tscript">
	  <script type="text/javascript">
	    $(document).ready(function () {
	      $(".button-collapse").sideNav();
	      console.log("entrou tudin até o footer");
	    });
	  </script>
	  
	<script>
	function seeCheckedBoxes() {
	  console.log($('.contatos-checked:checked'));
	  console.log("Qtd de contatos: "+$('.contatos-checked').length+" e marcados: "+$('.contatos-checked:checked'));
	  if ($('.contatos-checked:checked').length>0) {
	      $('#btnSubmitExcluir').show();
	      if($('.contatos-checked:checked').length == $('.contatos-checked').length){
	        $('#check-todos').prop('checked', this.checked);
	      }
	      else{ 
	     	$('#check-todos').not(this).prop('checked', this.checked);
	      }
	  }else {
	    $('#btnSubmitExcluir').hide();
	  }
	} 
	$(document).ready(function () {
	  console.log("Documento pronto");
	  $('#check-todos').change(function(){
	  $('.contatos-checked').not(this).prop('checked', this.checked);
        seeCheckedBoxes();
	  });
      
      $('.contatos-checked').change(seeCheckedBoxes);
	});
	  
	  $('#btnSubmitExcluir').click(function(){
		  var cttOuCtts;
		  $('.contatos-checked:checked').length>1 ? cttOuCtts = "contatos" : cttOuCtts = "contato";
		  if(!confirm('Você realmente deseja excluir '+$('.contatos-checked:checked').length + ' ' + cttOuCtts+'?'))
			  return false;
	  });
	  </script>
	
</jsp:attribute>
	
<jsp:body>
	<mm:messages value="${msgs}" erroStyle="color:red" infoStyle="color:blue" avisoStyle="color:orange" />
	
     <div class="row">
     <div class="col s10 offset-s1">
     
     <form action="${pageContext.request.contextPath }/controller.do?op=delct" method="post">
     <input type="hidden" name="op" value="delct"/>
     <table class="highlight">
       <thead>
         <tr>
           <th class="center-align"><input type="checkbox" id="check-todos"
               name="todos-checkbox" class="filled-in" /><label for="check-todos">X</label></th>
           <th>Nome</th>
           <th>Telefone</th>
           <th>Operadora</th>
         </tr>
       </thead>
     
       <tbody>
       <c:forEach var="contato" items="${ contatos }" varStatus="status">
         <tr>
           <td class="center-align">
             <input type="checkbox" id="${status.count}" value="${contato.id}"
               name="contatosPraDeletar" class="contatos-checked filled-in" />
             <label for="${status.count}"> </label>
           </td>
           <c:url value="controller.do?op=editct&idCt=${ contato.id }" var="editContatoURL"/>
           <td><a href="${ editContatoURL }">${ contato.nome }</a></td>
           <td><label for="${ status.count }">${ contato.fone }</label></td>
           <td><label for="${ status.count }">${ contato.operadora.nome }</label></td>
         </tr>
       </c:forEach>
       </tbody>
     </table>
     
     <div class="row" style="margin-top: 25px;">
       <button type="submit" id="btnSubmitExcluir" 
          class="waves-effect waves-light btn red light-2 col s4 m3">Excluir</button>
   <c:url value="contato/cadastro.jsp" var="cadastroNovoContato" ></c:url>
       <button type="submit" formaction="${ cadastroNovoContato }" 
          class="waves-effect waves-light btn green darken-2 col s7 m8 offset-s1 offset-m1">Novo</button>
     </div>
     
     </form>
     
     </div>
   </div>
</jsp:body>
	
</tt:template>   
	 
   
  