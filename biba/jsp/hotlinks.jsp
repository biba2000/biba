<%@ page session="true" %>
<%@ page import="biba.agentes.*" %>

<html>

<% 
String nombre = (String) session.getValue("nombreUsuario");
Vector vectorHotlinks = (Vector)session.getValue("vectorHotlinks");
%>




<body bgcolor="orange">

<center>
<h3><font color="white">HOTLINKS DE <%=nombre%> </font>
</h3>
</center>



<%
  String url="";
  String titulo="";
  String resumen="";
  String suministrador="";
  int j=1;

  if ( vectorHotlinks != null ) {
  
  Tipo_Info info = new Tipo_Info();

  for (int i=0;i<vectorHotlinks.size();i++){

    info = (Tipo_Info) vectorHotlinks.elementAt(i);
    url=info.dame_url();
    titulo=info.dame_titulo();
    resumen=info.dame_resumen();
    suministrador=info.dame_quien().toString();
    out.print(j+".Titulo: "+titulo);
    out.write("<br>");
    out.print("URL: <a href="+url+">"+url+"</a>");
    out.write("<br>");
    out.print("Resumen: "+resumen);
    out.write("<br>");
    out.print("Suministrado por: <STRONG>"+suministrador);
    out.write("</STRONG><br>");
    out.write("<br>");
    j=i+1;
    }

} else {

   out.write("No hay hotlinks para este tema");
}%>


</body>

</html>