<%@ tag description="Template" pageEncoding="UTF-8"%>
<%@ attribute name="title" required="true" %>
<%@ attribute name="theader" fragment="true" required="false" %>
<%@ attribute name="tscript" fragment="true" required="false" %>
<%@ attribute name="tsectionHeader" required="false" %>
<%@ attribute name="tsectionIcon" required="false" %>
<%@ attribute name="navbarColor" required="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/templating" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>${title}</title>
<!-- BootstrapCDN: -->
<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous"> -->
<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous"> -->
<!-- MaterializeCSS: -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/materialize/css/materialize.min.css">
<!-- Memoriam CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/memoriam.css">
<!-- Google Icons -->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>

<body>

  <c:if test="${empty theader}">
    <c:if test="${empty navbarColor}">
  	  <t:header navbarColor="blue" />
  	</c:if>
  	<c:if test="${not empty navbarColor}">
  	  <t:header navbarColor="${navbarColor}" />
  	</c:if>
  </c:if>
  
  <c:if test="${not empty theader}">
  	<jsp:invoke fragment="theader"/>
  </c:if>
  
  <div class="container body-container">
  <c:if test="${not empty tsectionHeader}">
  <div class="row">
     <h4 class="section-title"> 
     <c:if test="${empty tsectionIcon}">
     <i class="material-icons prefix">keyboard_arrow_right</i>
     </c:if>
     <c:if test="${not empty tsectionIcon}">
     <i class="material-icons prefix">${ tsectionIcon }</i>
     </c:if>
      <c:out value="${ tsectionHeader }" /></h4>
  </div>
  </c:if>
  <jsp:doBody/>
  <hr/>
  </div>
  
  <!-- JQuery tem que vir antes de todos os scripts porque os que serão invocados precisam dele para funcionar -->
  <script src="${pageContext.request.contextPath}/assets/materialize/js/jquery-3.2.1.min.js"></script>
  <c:if test="${empty tscript}">
  	<t:script />
  </c:if>
  <c:if test="${not empty tscript}">
    <jsp:invoke fragment="tscript"/>
    <t:script />
  </c:if>
  <!-- MaterializeJS -->
  <script src="${pageContext.request.contextPath}/assets/materialize/js/materialize.min.js"></script>
  
  
  </body>
</html>