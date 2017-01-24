<%@ page import="biba.persistencia.*" %>
<%@ page session="true" %>

<% String urlResultado= (String)session.getValue("urlResultado"); %>

<html>
<head>
<script>
function cargaPagina() {
  location.href="<%=urlResultado%>";
}

</script>
</head>

<body onload="cargaPagina()">
</body>

</html>
