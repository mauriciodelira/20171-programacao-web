<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <%@ taglib prefix="mm" tagdir="/WEB-INF/tags/messages" %>
    <%@ taglib prefix="tt" tagdir="/WEB-INF/tags/templating" %>

<c:if test="${sessionScope.usuario.perfil ne 'ADMIN'}">
<c:redirect url="/erro/"/>
</c:if>

<tt:template title="Memoriam | Usuários" tsectionHeader="Listar usuários" navbarColor="deep-indigo darker-2">

 <jsp:attribute name="tscript">
  <script type="text/javascript">
  function seeCheckedBoxes() {
    console.log($('.usuario-checked:checked'));
	  if ($('.usuario-checked:checked').length>0) {
	    $('#btnSubmitExcluir').show();
	  } else {
	    $('#btnSubmitExcluir').hide();
	  }
    }
  
    $(document).ready(function () {
	  console.log("Documento pronto");
	  $('#check-todos').change(function(){
        $('.usuario-checked').not(this).prop('checked', this.checked);
	    seeCheckedBoxes();
	  });
	  $('.usuario-checked').change(seeCheckedBoxes);
    });
    
    $('#btnSubmitExcluir').click(function(){
	  var pluralOuSingular;
	  $('.usuario-checked:checked').length>1 ? pluralOuSingular = "s" : pluralOuSingular = '';
	  if(!confirm('Você realmente deseja excluir '+$('.usuario-checked:checked').length + ' usuário' + pluralOuSingular+'?'
		  +' Você irá excluir todos os contatos desse'+pluralOuSingular+' usuário' + pluralOuSingular + '.'))
  	    return false;
    });
  </script>
  </jsp:attribute>
  
  <jsp:body>
	<mm:messages value="${msgs}" erroStyle="color:red" infoStyle="color:blue" avisoStyle="color:orange" />
   
   <div class="row">
     <div class="col s10 offset-s1">
     
     <form action="${pageContext.request.contextPath }/controller.do" method="post">
     <input type="hidden" name="op" value="delus"/>
     <table class="highlight">
       <thead>
         <tr>
           <th class="center-align"><input type="checkbox" id="check-todos"
               name="todos-checkbox" class="filled-in" /><label for="check-todos"> </label></th>
           <th>Usuário</th>
           <th>Perfil</th>
         </tr>
       </thead>
     
       <tbody>
       <c:forEach var="user" items="${ usuarios }" varStatus="status">
         <tr>
           <td class="center-align">
             <input type="checkbox" id="${user.id}" value="${user.id}"
               name="marcadosParaDeletar" class="usuario-checked filled-in" />
             <label for="${user.id}"> </label>
           </td>
           <c:url value="controller.do?op=editus&id=${ user.id }" var="editURL"/>
           <td><a href="${ editURL }">${user.nome }</a></td>
           <td><label for="${ status.count }">${user.perfil.nome}</label></td>
         </tr>
       </c:forEach>
       </tbody>
     </table>
     
     <div class="row" style="margin-top: 25px;">
       <button type="submit" id="btnSubmitExcluir" 
          class="waves-effect waves-light btn red light-2 col s4 m3">Excluir</button>
   <c:url value="usuario/cadastro.jsp" var="cadastroNovoUsuario" ></c:url>
       <button type="submit" formaction="${ cadastroNovoUsuario }" 
          class="waves-effect waves-light btn green darken-2 col s7 m8 offset-s1 offset-m1">Novo</button>
     </div>
     
     </form>
     
     </div>
   </div>
  </jsp:body>
  
</tt:template>
   
<%--    <c:import url="../assets/addons/footer.jsp" /> --%>