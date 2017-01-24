<%@ page import="biba.persistencia.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="biba.estructurasDatos.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="biba.agentes.*" %>

<%@ page session="true" %>


<!--abrirSesionPrimeraVez.jsp PRESENTACION PRIMERA VEZ DE LA SESION DE USUARIO-->
<!--
PARÁMETROS QUE DEBEN ESTAR EN EL OBJETO SESSION:
- Vector de Strings "pestanasActivas "con tamaño máximo 4 con los nombres de las pestañas activas
- String "nombreUsuario"
- String "pestana" con el nombre de la pestaña seleccionada (general,informatica o musica con minúsculas)
- Plantilla Características "plantillaGeneral"
- objeto DE TIPO java.util.Vector "resultadoBusqueda", EL CUAL CONTIENE LAS URL'S encontradas
- string "textoBusqueda" con el valor de la búsqueda (puede ser null)
- Vector "hotlinks" de Tipo_Info con los hotlinks del tema


PARÁMETROS QUE DEVUELVE SEGÚN LAS ACCIONES REALIZADAS

1- Acciones Cambio de pestaña y Buscar. Pasará al servlet que lo invoca los siguientes parámetros:
   Devuelve el estado de todos los elementos:
      - texto de la búsqueda -> "textoBusqueda" = ...
      - tema elegido (sólo en plantilla general) -> "temaElegido" = General, Música, ...
      - imprescindible (sólo en plantillas particulares) ->"imprescindible"
      - checkboxes marcados en la/s plantilla/s: devuelve 'nombrecheckbox=ON'
      - string con la pestaña antigua -> "pestaniaAntigua" = general, informatica...
      - string "accion" con el nombre de la accion realizada -> "accion" = buscar o cambioPestania
      - string con la nueva pestaña seleccionada. En caso de que la acción realizada sea
        buscar, se devuelve la plantilla inicialmente cargada -> "nuevaPestaniaSeleccionada" = general, música...


2- Mostrar Hotlinks.
   "accion" = "abrirHotlinks"

3- Abrir una URL del resultado de la búsqueda. Pasará al servlet que lo invoca los siguientes parámetros:
   "url"= número-(como String)-que-identifica-a-la-url-seleccionada
   "combo"= estado del combo box para determinar dónde actualizar los hotlinks
-->

<html>

<head>

<!-- Para los gráficos y plantillas >
<BASE href="http://localhost:8080/biba/jsp/"-->

<!--RECUPERAMOS LA Info DE LA SESION -->

<%
  Vector textoPestanias = (Vector) session.getValue("pestanasActivas");
  String nombre = (String) session.getValue("nombreUsuario");
  String pestaniaSeleccionada = (String) session.getValue("pestana");
  String textoBusqueda = (String) session.getValue("textoBusqueda");
  Vector vectorHotlinks;
  PlantillaCaracteristicasGenerales plantilla = (PlantillaCaracteristicasGenerales) session.getValue("plantillaGeneral");
  Vector res = (Vector) session.getValue("resultadoBusqueda");

  PlantillaCaracteristicasParticularesMusica plantillaMusica = new PlantillaCaracteristicasParticularesMusica();
  if (pestaniaSeleccionada.equals("musica")) {
    plantillaMusica=(PlantillaCaracteristicasParticularesMusica) plantilla;
  }

  PlantillaCaracteristicasParticularesInformatica plantillaInformatica = new PlantillaCaracteristicasParticularesInformatica();
  if (pestaniaSeleccionada.equals("informatica")) {
     plantillaInformatica=(PlantillaCaracteristicasParticularesInformatica ) plantilla;
  }

  PlantillaCaracteristicasParticularesDeportes plantillaDeportes = new PlantillaCaracteristicasParticularesDeportes();
  if (pestaniaSeleccionada.equals("deportes")) {
     plantillaDeportes=(PlantillaCaracteristicasParticularesDeportes) plantilla;
  }
  PlantillaCaracteristicasParticularesViajes plantillaViajes = new PlantillaCaracteristicasParticularesViajes();
  if (pestaniaSeleccionada.equals("viajes")) {
     plantillaViajes=(PlantillaCaracteristicasParticularesViajes ) plantilla;
  }

  String nombrePestania = null;

  // RUTAS DE LOS JSP, SERVLETS E IMÁGENES
  String rutaJsp="";
  String rutaServlet="";
  String rutaImagenes="";
  String rutaHtml="";

  rutaJsp  = "/biba" + propiedades.getPropiedad("RUTA_JSP");
  rutaServlet = "/biba/servlet";
  rutaImagenes= rutaJsp + "images/";
  rutaHtml = "/biba" + propiedades.getPropiedad("RUTA_HTML");

%>


<%-- Valores plantillas --%>







<title>BIBA! SESION DEL USUARIO <%= nombre %></title>

<script Language = "JavaScript">


function nuevaVentana( numeroUrlDestino ) {
// funcion que se encarga de abrir una nueva ventana con la dirección de la url
// seleccionada de entre los resultados de la búsqueda
// Recibe como parámetro el número que identifica a dicha url y el valor del combo-box
   if (document.formularioGlobal.pestaniaAntigua.value == "general")
      // pasamos los atributos pestana y combo
      ventana = open("<%=rutaServlet%>/AgenteUsuario?accion=abrirResultado&amp;nombreUsuario=<%=nombre%>&amp;url="+numeroUrlDestino+"&amp;combo="+document.formularioGlobal.temaElegido.options[document.formularioGlobal.temaElegido.selectedIndex].text+"&amp;pestana="+document.formularioGlobal.pestaniaAntigua.value);
   else
      // sólo pasamos pestana
      ventana = open("<%=rutaServlet%>/AgenteUsuario?accion=abrirResultado&amp;nombreUsuario=<%=nombre%>&amp;url="+numeroUrlDestino+"&amp;pestana="+document.formularioGlobal.pestaniaAntigua.value);
}


// cambia el valor de la variable según la selección que haya en el combo
function cambioCombo(combo)
{
  alert(document.formularioGlobal.temaElegido.options[document.formularioGlobal.temaElegido.selectedIndex].text);
  //alert(combo.options[combo.selectedIndex].text);
}


function ventanaAyuda() {

    var options = "width=450" + ",height=450,";
    options += "resizable=yes,scrollbars=yes,status=yes,";
    options += "menubar=no,toolbar=no,location=no,directories=no";
    var newWin = window.open("<%=rutaHtml%>ayuda.html",'newWin',options);
   newWin.focus();

}

//-----------------------------------------------------


function esVacioTextoBusqueda() {

   if ( document.formularioGlobal.textoBusqueda.value=="") {
      //alert("vacio");
      return false;

   }
   else {
      //alert("lleno <" + document.formularioGlobal.textoBusqueda.value + ">");
      return true;
   }
}

function clickCorrecto() {

  // Si es busqueda, comprobamos que no esté vacío el texto de búsqueda
  if (document.formularioGlobal.accion.value == "buscar") {
    return esVacioTextoBusqueda();
  }
  // Si es cambio de pestaña, comprobamos que es una pestaña distinta a la actual
  else if (document.formularioGlobal.accion.value == "cambioPestania") {
    return (document.formularioGlobal.nuevaPestaniaSeleccionada.value
      !=    document.formularioGlobal.pestaniaAntigua.value);
  }
  else return false;
}





function actualizaAccionYnuevaPestania( nuevaPestania, acc ) {
// actualiza el param oculto "nuevaPestaniaSeleccionada" con el valor "nuevaPestania"
// y el oculto "accion" con acc

   document.formularioGlobal.nuevaPestaniaSeleccionada.value = nuevaPestania;
   document.formularioGlobal.accion.value = acc;
}

// procesa la pulsación del botón de logout


function ventanaHotlinks() {
// funcion que se encarga de abrir una nueva ventana con los hotlinks

    v = open("<%=rutaServlet%>/AgenteUsuario?accion=abrirHotlinks&amp;nombreUsuario=<%=nombre%>");
}


function focoEnBuscar() {

   document.formularioGlobal.textoBusqueda.focus();
}

</script>

</head>



<body background="<%=rutaImagenes%>Bubinga.JPG" topmargin="0" leftmargin="0" marginwidth="0" marginheight="0" onload="focoEnBuscar()">



<!-- PROVISIONAL. PESTAÑAS DISPONIBLES -->


<% int i = 0;
   String nombreImagen = null;
   int pestaniaSeleccionadaInt = 0; %>

<!-- traza input type=button value="kjljkjkjkj" onclick="esVacioTextoBusqueda()"-->



<table border = "0" cellpadding="0" cellspacing="0" width="1024" height="36"
 background="<%=rutaImagenes%>bibaLogoCabecera.JPG">

<tr valign = "bottom" >
<td width="100"></td>
<td width="600" valign = "center" align = "center"><font color="black" face="Verdana,Tahoma,Arial">sesi&oacute;n del usuario <%out.print(nombre);%></td>
</tr>
</table>

<br>



<!-- Tabla SUPER-EXTERNA que engloba todo el sistema de pestañas, y el pseudo-frame de la izda-->
<table border ="0" cellpadding = "0" cellspacing="0" width="950"> <!-- 800-->
<tr>
<td rowspan="2" valign="top" width="170"> <!-- PRIMERA Columna de la tabla super-externa-->


<!-- hotlinks y resultados de la búsqueda  -->



 <!-- cambio hotlinks -->
<INPUT align=absMiddle border=0
               src="<%=rutaImagenes%>book2.JPG" onClick='ventanaHotlinks()'
            type=image >


</form>


<input border=0 src="<%=rutaImagenes%>bilmes<%=pestaniaSeleccionada%>.GIF" onClick="ventanaAyuda()" type=image alt="Pulse aquí para obtener ayuda" usemap="#bilmesBlinkSmoke" >
<BR>
<a onClick="ventanaAyuda()"><font color="white"> ¿Qu&eacute; es BIBA? </a>

</td><!-- FIN primera Columna de la tabla super-externa-->




<td valign="top" align="left"><!-- SEGUNDA Columna de la tabla super-externa-->

<!-- Tabla externa que engloba a todo el sistema de pestañas -->
<!-- su primera fila contiene las pestañas -->

<table border="0" cellpadding="0" cellspacing="0" width="725" align="left" > <!--580-->
   <tr>

<!-- Al pulsar en una pestaña se desencadena la acción siguiente -->
<form name="formularioGlobal" onsubmit="return clickCorrecto()" action="<%=rutaServlet%>/AgenteUsuario" method="POST">

<!-- presentamos las pestañas -->


<%
  int numelems = textoPestanias.size();
	if (numelems>5) numelems = 5;
  while ( i<numelems ) { %>
  <%--Para construir el nombre del archivo a presentar en las pestañas --%>
  <% if ( pestaniaSeleccionada.equals((String)textoPestanias.elementAt(i) ) ) { %>
     <% pestaniaSeleccionadaInt = i; %>
     <% nombreImagen = (String)textoPestanias.elementAt(i);%>
     <% nombrePestania = nombreImagen; %>
  <%
  }
  else { %> <!-- Concatenamos Back para señalar que la pestaña está "detrás" (no está seleccionada) -->
     <% nombrePestania = (String)textoPestanias.elementAt(i); %>
     <% nombreImagen = (String)textoPestanias.elementAt(i)+"Back";%>
  <%
  }
  %><td valign="bottom" nowrap cellpadding="0" border="0">
  <input src="<%=rutaImagenes%><%=nombreImagen %>.GIF" width="145" height="20" border="0" usemap="#<%= ((String)textoPestanias.elementAt(i))%>" type="image"
   name="coord_<%=nombrePestania%>" value="coord_<%=nombrePestania%>"
   onClick='actualizaAccionYnuevaPestania( "<%=nombrePestania%>", "cambioPestania" )'></td>
   <%i++;%>
<%
}
%>

<%-- relleno --%>
<%
for ( int j=i; i < 5; i++ ) {%>
<td valign="bottom" nowrap cellpadding="0" border="0" width="145">&nbsp;</td>
<%
}
%>


<!-- /form>  de las pestañas-->
   </tr>


   <tr><!-- segunda fila de la tabla externa. Contiene una tabla -->
      <td colspan="5">
         <!-- la tabla contiene cabecera y plantillas:
            en su primera fila una cabecera (texto de la búsqueda + botón buscar...)
            en su segunda fila la/s plantilla/s que sea menester presentar-->
         <table cellpadding="7" width="725" border="0" cellspacing="0" align="right">

<!-- SERVLET QUE SE INVOCA AL PULSAR EL BOTÓN DE BUSCAR >


    <% if ( "general".equals((String)textoPestanias.elementAt(pestaniaSeleccionadaInt)) ) { %>
    <!-- presentamos la cabecera y la plantilla general -->

            <!--
            en la primera fila la cabecera General (temas, texto-Busq, botón buscar )-->
            <tr>
               <td border="0" align="center" colspan="5" bgcolor="#006699"><font size="2">
               <%@ include file="plantillas/plantillaGeneralCabecera.jsp" %>
               </td>
            </tr>
            <!--
            en la segunda fila la plantilla general -->
            <tr>
               <td border="0" valign="top" align="center" colspan="5" bgcolor="006699">

               <%@ include file="plantillas/plantillaGeneral.jsp" %>

               </td>
            </tr>



    <% } else { %> <%--  1 NO SÓLO HAY QUE PRESENTAR SÓLO LA PLANTILLA GENERAL, SINO
                        TAMBIÉN PLANTILLAS DE TEMAS --%>

            <!--
            En la primera fila:Cabecera de tema. Texto búsqueda, botón buscar, imprescindible -->
            <tr>
               <td align="center" colspan="5" bgcolor="#006699">
						     <%@ include file="plantillas/plantillaTemaCabecera.jsp" %>

               </td>
            </tr>
            <!--
            En la segunda fila las plantillas tema particular + general
            Esta segunda fila tendrá 2 columnas, la primera para la plantilla
            particular y la segunda para la general
            -->
            <tr>
               <!-- PRIMERA COLUMNA: TEMA PARTICULAR -->
               <td border="0" valign="center" align="center" colspan="2" bgcolor="006699" cellpadding="0" cellspacing="0">
       <!-- elección de plantilla particular -->
       <% if ( "musica".equals(((String)textoPestanias.elementAt(pestaniaSeleccionadaInt))) ) {%>
          <%@ include file="plantillas/plantillamusica.jsp" %>
       <% } %>

       <% if ( "deportes".equals(((String)textoPestanias.elementAt(pestaniaSeleccionadaInt))) ) {%>
          <%@ include file="plantillas/plantilladeportes.jsp"%>
       <% } %>

       <% if ( "viajes".equals(((String)textoPestanias.elementAt(pestaniaSeleccionadaInt))) ) {%> <!-- 3 -->
          <%@ include file="plantillas/plantillaviajes.jsp" %>
       <% } %>          <!-- /3 -->

       <% if ( "informatica".equals(((String)textoPestanias.elementAt(pestaniaSeleccionadaInt))) ) {%> <!-- 3 -->
          <%@ include file="plantillas/plantillainformatica.jsp" %>
       <% } %>          <!-- /3 -->
               </td>
    <%
    }
    %><!-- /1 -->

    <!-- tabla secundaria. Será vacía en caso de que la pestaña seleccionada
    sea la general o sera la propia general en caso de que la pestaña seleccionada
    sea de un tema particular -->

    <% if ( "general".equals((String)textoPestanias.elementAt(pestaniaSeleccionadaInt)) ) { %>

        <!-- nada -->
    <% } else { %>
    	       <!-- SEGUNDA COLUMNA: PLANTILLA GENERAL -->
               <td border="0" valign="top" align="center" colspan="2" bgcolor="006699">
               <%@ include file="plantillas/plantillaGeneral.jsp" %>
               </td>
    <%
    }
    %>

            </tr>
            <tr> <!-- fila relleno inferior -->
               <td align="center" colspan="5" bgcolor="#006699">
               </td>
            </tr>
         </table><!-- con cabeceras y plantillas -->

      </td>
   </tr><!-- segunda fila de la tabla externa -->

   <tr>
    <td colspan="5" valign="bottom" height="30">
      <a href="<%=rutaJsp%>logout.html" >
            <img src="<%=rutaImagenes%>Salir.JPG" align="right" border=0 height = "25" width="120">
  </a>

    </td>
   </tr>


</table><!-- tabla externa -->


</div>
<!-- PARAMS QUE SE PASAN AL SERVLET -->
<!-- param que indica el valor de la pestaña inicialmente cargada -->
<% String nombreUsuario = "";
   nombreUsuario = nombre;
%>
<input type="hidden" name="nombreUsuario" value="<%=nombreUsuario%>">
<input type="hidden" name="pestaniaAntigua" value="<%=pestaniaSeleccionada%>">
<!-- param que indica la pestaña nueva seleccionada. Será modificado por la función js
actualizaAccionYnuevaPestania( ... )-->
<input type="hidden" name="nuevaPestaniaSeleccionada" value="<%=pestaniaSeleccionada%>">
<!-- param que indica la acción realizada: buscar o cambiar de pestaña. Modificada por
actualizaAccionYnuevaPestania( ... )-->
<input type="hidden" name="accion" value="buscar">



</form><!-- formularioGlobal -->

</td><!-- FIN SEGUNDA Columna de la tabla super-externa-->
</tr>



<tr>

   <td align ="center" valign="top">

   <!-- presenta los resultados de la búsqueda -->

<!-- identificadorUrl va a ir valiendo 0, 1, 2, 3... para los diferentes enlaces
resultado-->

<% String urlDestino = "" ;
   int identificadorUrl= 0;
   
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

  Tipo_Info info = null;//new Tipo_Info();

  for (identificadorUrl=0;identificadorUrl<res.size();identificadorUrl++){

    if (res.elementAt(identificadorUrl)!=null) // i
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
   <img src="<%=rutaImagenes%>utensiliosDeskBlack.JPG">
<%
}
%>

</td>
</tr>
</table>
</CENTER>















   </td>
</tr>

</table> <!-- tabla super-externa -->

</body>
</html>


<!-- de momento el diseño de la tabla de las pestañas es:


tabla super externa
__________________________________________
					  |
		tabla externa pestañas
                 _______________________
		|			|
		|pestañas-4 columnas max|
		|_______________________|
		|			|
	|	|			| |
hotlinks|       |  tabla interna        | |
	|	|______________________ | |
	|	||cabecera	       || |
		||_____________________|| |
		||plantilla/s          || |
		||(una o dos columnas  ||
		||_____________________||
                |_______________________|
                			  |
              ____________________________|
              				  |
        |        resultados busqueda	  |
        |
        |
        |
__________________________________________|



-->