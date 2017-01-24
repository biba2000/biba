<%@ page session="true" %>

<!--error.jsp ERROR GENÉRICO-->
<!--PRECONDICION: EN  EL OBJETO SESSION DEBE ESTAR UN objeto DE TIPO String
 "mensajeError" CON EL MENSAJE DE ERROR A PRESENTAR-->



<html>

<head>

<title>BIBA! Error:  </title>
</head>

<body>


<!--RECUPERAMOS EL MENSAJE DE ERROR DE LA SESION-->
<%String mensaje = (String) session.getValue("mensajeError");%>


<h1 align="center">BIBA! Error: <%=mensaje%> </h1>

</body>
</html>