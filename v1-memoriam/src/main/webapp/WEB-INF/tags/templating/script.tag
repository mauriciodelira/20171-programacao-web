<%@ tag description="Script comum à todas as páginas" body-content="empty" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



  <script type="text/javascript">

  var form = document.getElementById("logout-form");
  document.getElementById("form-submit-mobile").addEventListener("click", function(){
  	form.submit();
  });
  document.getElementById("form-submit").addEventListener("click", function(){
	  	form.submit();
	  });

//   var formsearch = document.getElementById("search-form");
//   document.getElementById("form-search-submit").addEventListener("click", function(){
//   	formsearch.submit();
//   });
  </script>
  

<script type="text/javascript">
  $(document).ready(function () {
    $(".button-collapse").sideNav();
  });
</script>

