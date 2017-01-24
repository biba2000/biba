<%@ page import="java.util.Vector" %>
<%@ page import="biba.agentes.*" %>
<%@ page import="biba.persistencia.*" %>
<%@ page session="true" %>

<!-- resultadoBusquedaRapida.jsp -->
<!--PRECONDICION: EN  EL OBJETO SESSION DEBEN ESTAR:
 1)objeto DE TIPO java.lang.String LLAMADo valorBusqueda EL CUAL CONTIENE EL VALOR DE
 BUQUEDA DEL USUARIO.
 2)objeto DE TIPO java.util.Vector
 LLAMADO resultadoBusqueda, EL CUAL CONTIENE LAS URL'S encontradas -->


<%  String rutaHtml="";
    rutaHtml = "/biba" + biba.persistencia.propiedades.getPropiedad("RUTA_HTML");
%>

<html>
<head>
<script>
function esVacioTexto() {

   if ( document.enviar.valorBusqueda.value=="" ) {
      
      return false;

   }
   else {
      
      return true;
   }
}
</script>
</head>
<title>BIBA! RESULTADO BUSQUEDA RAPIDA</title>

<H2> RESULTADO DE LA BUSQUEDA </H2>
<br>
<h3> Recuerde que obtendrá mejores resultados al <a href=<%=rutaHtml%>formularioRegistro.html>registrarse</A> en BIBA.</h3>

<body>



                <form action="/biba/servlet/ServletBusquedaRapida" name="enviar"
                method="POST" onSubmit="return esVacioTexto()">
                    <input type="hidden" name="action"
                    value="busquedaRapida"><!-- Utilizamos una entrada de tipo oculto para enviar la informacion
                	al Servlet de que la acción a realizar es la de registro--><p><input type="text"
                    size="21" name="valorBusqueda"> <input
                    type="submit" value="Buscar"> </p>
                </form>




<%
  String valorBusqueda = (String) session.getValue("valorBusqueda");
  if (valorBusqueda==null) valorBusqueda="";
  Vector res = (Vector) session.getValue("resultadoBusqueda");
%>

  <i>
  Valor de la Búsqueda:
  </i>
  <b>
  <%=valorBusqueda%>
  </b>
  <br><br>
<%
  String url="";
  String titulo="";
  String resumen="";
//  String suministrador="";
  int puntos=0;
  int j=1;

  // pruebas
  //System.out.println(res.toString());
  //System.out.println(((Tipo_Info)res.elementAt(0)).toString());
  //System.out.println(((Tipo_Info)res.elementAt(0)).dame_url());



  if ( res.size() > 0 ) {

  Tipo_Info info = new Tipo_Info();

  for (int i=0;i<res.size();i++){

    if (res.elementAt(i)!=null)
    {

    j=i+1;
    info = (Tipo_Info) res.elementAt(i);
    url=info.dame_url();
    titulo=info.dame_titulo();
    resumen=info.dame_resumen();
//    suministrador=info.dame_quien();
//    puntos=info.getHits(); // puntuacion del URL (info extra debug)
    out.print(j+".Titulo: "+titulo);
    out.write("<br>");
//    out.print("Puntuación de filtrado: "+puntos);
//    out.write("<br>");
    out.print("URL: <a href="+url+">"+url+"</a>");
    out.write("<br>");
    out.print("Resumen: "+resumen);
    out.write("<br>");
    out.print("Suministrado por: <STRONG>"+info.dame_quien().toString());
    out.write("</STRONG><br>");
    out.write("<br>");
    }
    /*
    else
    {
      System.out.println("resultado Fue null");
    }
    */
  }

  Vector vacio=new Vector();
  session.putValue("resultadoBusqueda",vacio);
  }
  else
  out.print("<h3>No hubo resultados.</h3>");
%>




</body>
</html>