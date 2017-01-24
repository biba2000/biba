
<!-- APERTURA DE UNA URL -->

<!-- identificadorUrl va a ir valiendo 0, 1, 2, 3... para los diferentes enlaces
resultado-->

<% String urlDestino = "" ;
   int identificadorUrl= 0;
   
%>

<%
  String url="";
  String titulo="";
  String resumen="";
  String suministrador="";
  int j=1;

  if ((res!=null) && ( res.size()>0)){ %>

<br>
<CENTER>

<table bgcolor="#fffff0">
<tr>
<td align="left">
<center>
<h4>RESULTADOS DE LA BUSQUEDA</h4>
</center>
<%

  Tipo_Info info = new Tipo_Info();

  for (identificadorUrl=0;identificadorUrl<res.size();identificadorUrl++){

    if (res.elementAt(i)!=null)
    {

    info = (Tipo_Info) res.elementAt(identificadorUrl);

    j=identificadorUrl+1;
    titulo=info.dame_titulo();
    resumen=info.dame_resumen();
    //suministrador=info.dame_quien();
    url=info.dame_url();
    out.print(j+".Titulo: "+titulo);
    out.write("<br>");

    %>

    <input src="<%=rutaImagenes%>url.GIF"  border="0"  type="image"
      name="clickUrl" value="clickUrl"
      onClick="nuevaVentana(<%=identificadorUrl%>)">
<%
    out.write("<br>");
    out.write("URL:"+url);
    out.write("<br>");
    out.print("Resumen: "+resumen);
    out.write("<br>");
    out.print("Suministrado por: <STRONG>"+info.dame_quien().toString());
    out.write("</STRONG><br>");
    out.write("<br>");
    } // del if
  }
} else { // no hay resultados %>
   <!--img src="<%=rutaImagenes%>utensiliosDeskBlack.JPG"-->
<%
}
%>

</td>
</tr>
</table>
</CENTER>
