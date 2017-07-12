<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <%@ taglib prefix="mm" tagdir="/WEB-INF/tags/messages" %>
    <%@ taglib prefix="tt" tagdir="/WEB-INF/tags/templating" %>
    
<tt:template title="Memoriam | Erro" tsectionHeader="Sauron está vendo..." tsectionIcon="remove_red_eye" navbarColor="red accent-4">
<jsp:body>
<mm:messages value="${msgs}" />
<mm:messages value="${erro}"/>
</span>
<p><a href="/memoriam/">Volte para a página inicial</a> <s><em>e preferencialmente deixe de quebrar o sistema.</em></s></p>
<p>A equipe de TI agradece!</p>
<p>:)</p>
</jsp:body>
</tt:template>