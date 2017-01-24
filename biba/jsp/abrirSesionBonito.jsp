<%@ page import="biba.persistencia.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="biba.estructurasDatos.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="biba.agentes.*" %>

<%@ page session="true" %>


<!--abrirSesionPrimeraVez.jsp PRESENTACION PRIMERA VEZ DE LA SESION DE USUARIO-->
<!--
PAR�METROS QUE DEBEN ESTAR EN EL OBJETO SESSION:
- Vector de Strings "pestanasActivas "con tama�o m�ximo 4 con los nombres de las pesta�as activas
- String "nombreUsuario"
- String "pestana" con el nombre de la pesta�a seleccionada (general,informatica o musica con min�sculas)
- Plantilla Caracter�sticas "plantillaGeneral"
- objeto DE TIPO java.util.Vector "resultadoBusqueda", EL CUAL CONTIENE LAS URL'S encontradas
- string "textoBusqueda" con el valor de la b�squeda (puede ser null)
- Vector "hotlinks" de Tipo_Info con los hotlinks del tema


PAR�METROS QUE DEVUELVE SEG�N LAS ACCIONES REALIZADAS

1- Acciones Cambio de pesta�a y Buscar. Pasar� al servlet que lo invoca los siguientes par�metros:
   Devuelve el estado de todos los elementos:
      - texto de la b�squeda -> "textoBusqueda" = ...
      - tema elegido (s�lo en plantilla general) -> "temaElegido" = General, M�sica, ...
      - imprescindible (s�lo en plantillas particulares) ->"imprescindible"
      - checkboxes marcados en la/s plantilla/s: devuelve 'nombrecheckbox=ON'
      - string con la pesta�a antigua -> "pestaniaAntigua" = general, informatica...
      - string "accion" con el nombre de la accion realizada -> "accion" = buscar o cambioPestania
      - string con la nueva pesta�a seleccionada. En caso de que la acci�n realizada sea
        buscar, se devuelve la plantilla inicialmente cargada -> "nuevaPestaniaSeleccionada" = general, m�sica...
         

2- Mostrar Hotlinks. 
   "accion" = "abrirHotlinks"
    
3- Abrir una URL del resultado de la b�squeda. Pasar� al servlet que lo invoca los siguientes par�metros:
   "url"= n�mero-(como String)-que-identifica-a-la-url-seleccionada
-->

<html>

<head>

<!-- Para los gr�ficos y plantillas >
<BASE href="http://localhost:8080/biba/jsp/"-->

<!--RECUPERAMOS LA Info DE LA SESION -->

<%
  Vector textoPestanias = (Vector) session.getValue("pestanasActivas");
  String nombre = (String) session.getValue("nombreUsuario"); 
  String pestaniaSeleccionada = (String) session.getValue("pestana");
  String textoBusqueda = (String) session.getValue("textoBusqueda");
  Vector vectorHotlinks = (Vector) session.getValue("hotlinks");
  
  /*TRAZA PARA HOTLINKS
  Vector vectorHotlinks = new Vector();
  Tipo_Info elem1 = new Tipo_Info( "http://www.google.com", "titulazo", "resumencillo",77,"yomismo.com");
  vectorHotlinks.addElement( elem1 );
  vectorHotlinks.addElement( elem1 );
  vectorHotlinks.addElement( elem1 );
  session.putValue("vectorHotlinks", vectorHotlinks );
  */
  
  /* prueba off-line que funciona
  PlantillaCaracteristicasParticularesInformatica plantillaI = new PlantillaCaracteristicasParticularesInformatica();
  plantillaI.noticias = true;
  plantillaI.hardware = true;
  PlantillaCaracteristicasGenerales plantilla = (PlantillaCaracteristicasGenerales) plantillaI;
  pestaniaSeleccionada = "informatica";
  
  */
    
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
  
  
  String nombrePestania = null; 
  
  // RUTAS DE LOS JSP, SERVLETS E IM�GENES
  String rutaJsp="";
  String rutaServlet="";
  String rutaImagenes="";
  
  rutaJsp  = "/biba" + propiedades.getPropiedad("RUTA_JSP");
  rutaServlet = "/biba/servlet";
  rutaImagenes= rutaJsp + "images/";
%>


<%-- Valores plantillas --%>







<title>BIBA! SESION DEL USUARIO <%= nombre %></title>

<script Language = "JavaScript">


function nuevaVentana( numeroUrlDestino ) {
// funcion que se encarga de abrir una nueva ventana con la direcci�n de la url
// seleccionada de entre los resultados de la b�squeda
// Recibe como par�metro el n�mero que identifica a dicha url

   //ventana = open("http://localhost:8080/biba/servlet/AgenteUsuario?nombreUsuario=<%=nombre%>&amp;url="+numeroUrlDestino);
   ventana = open("<%=rutaServlet%>/AgenteUsuario?nombreUsuario=<%=nombre%>&amp;url="+numeroUrlDestino);
   
}

function esVacioTextoBusqueda() {

   if ( document.formularioGlobal.textoBusqueda.value=="" ) {
      //alert("vacio");
      return false;
      
   }
   else {
      //alert("lleno <" + document.formularioGlobal.textoBusqueda.value + ">");
      return true;
   }
}

function actualizaAccionYnuevaPestania( nuevaPestania, acc ) {
// actualiza el param oculto "nuevaPestaniaSeleccionada" con el valor "nuevaPestania"
// y el oculto "accion" con acc
   
   document.formularioGlobal.nuevaPestaniaSeleccionada.value = nuevaPestania;
   document.formularioGlobal.accion.value = acc;
}


function ventanaHotlinks() {
// funcion que se encarga de abrir una nueva ventana con los hotlinks

   v = open("<%=rutaServlet%>/ServletHotlinks");
}


function focoEnBuscar() {

   document.formularioGlobal.textoBusqueda.focus();
}

</script>

</head>

<!--h3 align="center">BIBA! Sesion del Usuario <%out.print(nombre);%> </h3-->

<body background="<%=rutaImagenes%>Bubinga.JPG" topmargin="0" leftmargin="0" marginwidth="0" marginheight="0" onload="focoEnBuscar()">



<!-- PROVISIONAL. PESTA�AS DISPONIBLES -->


<% int i = 0;%>
<% String nombreImagen = null; %>
<% int pestaniaSeleccionadaInt = 0; %>

<!-- traza input type=button value="kjljkjkjkj" onclick="esVacioTextoBusqueda()"-->

<%-- @ include file="componentes/tablaCabecera.html" --%>



<table border = "0" cellpadding="0" cellspacing="0" width="1024" height="36"
 background="<%=rutaImagenes%>bibaLogoCabecera.JPG">
<tr valign = "bottom" >
<td width="100"></td>
<!--td width="140"><img src="<%=rutaImagenes%>bibalogo.JPG"></td-->
<td width="600" valign = "center" align = "center"><font color="white" face="Verdana,Tahoma,Arial">sesi&oacute;n del usuario <%out.print(nombre);%></td>
</tr>
</table>

<br>



<!-- Tabla SUPER-EXTERNA que engloba todo el sistema de pesta�as, y el pseudo-frame de la izda-->
<table border ="0" cellpadding = "0" cellspacing="0" width="760">
<tr>
<td rowspan="2" valign="top" width="170"> <!-- PRIMERA Columna de la tabla super-externa-->


<!-- hotlinks y resultados de la b�squeda  -->

 <!--form name="formHotlinks"> <!-- action="http://localhost:8080/biba/servlet/ServletHotlinks" method="POST"> -->
    
 <INPUT align=absMiddle border=0 
               src="<%=rutaImagenes%>book2.GIF" onClick='ventanaHotlinks()'
            type=image > 

<input type="hidden" name="accion" value="abrirHotlinks">
<!--input type="button" value="HOTLINKS" onClick ="ventanaHotlinks()"><img src="images/anihot1a.GIF">            
<!--/form--> 

<img src="<%=rutaImagenes%>bilmesBlinkSmoke.GIF">


</td><!-- FIN primera Columna de la tabla super-externa-->




<td valign="top" align="left"><!-- SEGUNDA Columna de la tabla super-externa-->

<!-- Tabla externa que engloba a todo el sistema de pesta�as -->
<!-- su primera fila contiene las pesta�as -->

<table border="0" cellpadding="0" cellspacing="0" width="580" align="left">
   <tr>

<!-- Al pulsar en una pesta�a se desencadena la acci�n siguiente -->
<form name="formularioGlobal" onsubmit="return esVacioTextoBusqueda()" action="http://localhost:8080/biba/servlet/AgenteUsuario" method="POST">

<!-- presentamos las pesta�as -->

<% while ( i<(textoPestanias.size()) ) { %>
        
  <%--Para construir el nombre del archivo a presentar en las pesta�as --%>
  <% if ( pestaniaSeleccionada.equals((String)textoPestanias.elementAt(i) ) ) { %>
     
     <% pestaniaSeleccionadaInt = i; %>
     <% nombreImagen = (String)textoPestanias.elementAt(i);%> 
     <% nombrePestania = nombreImagen; %>
  <%
  }
       
  else { %> <!-- Concatenamos Back para se�alar que la pesta�a est� "detr�s" (no est� seleccionada) -->
     
     <% nombrePestania = (String)textoPestanias.elementAt(i); %>
     <% nombreImagen = (String)textoPestanias.elementAt(i)+"Back";%> 
  <%
  }
  %>
  
  
      <td valign="bottom" nowrap cellpadding="0" border="0"><input src="<%=rutaImagenes%><%=nombreImagen %>.GIF" width="140" height="20" border="0" usemap="#<%= ((String)textoPestanias.elementAt(i))%>" type="image" 
      name="coord_<%=nombrePestania%>" value="coord_<%=nombrePestania%>"
      onClick='actualizaAccionYnuevaPestania( "<%=nombrePestania%>", "cambioPestania" )'></td>
      <!--map name="<%= (String)textoPestanias.elementAt(i)%>">
      <!-- <area shape="rect" coords="0,0,140,25" href="/biba/servlet/AgenteUsuario">
      </map -->
   <% i++; %>
<%
 }
%>    
     
<!-- /form>  de las pesta�as-->
   </tr>


   <tr><!-- segunda fila de la tabla externa. Contiene una tabla -->
      <td colspan="4">
         <!-- la tabla contiene cabecera y plantillas:
            en su primera fila una cabecera (texto de la b�squeda + bot�n buscar...) 
            en su segunda fila la/s plantilla/s que sea menester presentar-->
         <table cellpadding="7" width="580" border="0" cellspacing="0" align="right">

<!-- SERVLET QUE SE INVOCA AL PULSAR EL BOT�N DE BUSCAR >
<form action="http://localhost:8080/biba/servlet/AgenteUsuario" method="POST" -->
             
    <% if ( "general".equals((String)textoPestanias.elementAt(pestaniaSeleccionadaInt)) ) { %>
    <!-- presentamos la cabecera y la plantilla general -->
      
            <!-- 
            en la primera fila la cabecera General (temas, texto-Busq, bot�n buscar )-->
            <tr>
               <td border="0" align="center" colspan="4" bgcolor="#006699"><font size="2">       
               <%@ include file="plantillas/plantillaGeneralCabecera.jsp" %>
               </td>
            </tr>
            <!-- 
            en la segunda fila la plantilla general -->
            <tr>
               <td border="0" valign="top" align="center" colspan="4" bgcolor="006699">
	       
               <%@ include file="plantillas/plantillaGeneral.jsp" %>	       

               </td>
            </tr>
      
       
       
    <% } else { %> <%--  1 NO S�LO HAY QUE PRESENTAR S�LO LA PLANTILLA GENERAL, SINO 
                        TAMBI�N PLANTILLAS DE TEMAS --%>
    
            <!-- 
            En la primera fila:Cabecera de tema. Texto b�squeda, bot�n buscar, imprescindible -->
            <tr> 
               <td align="center" colspan="4" bgcolor="#006699">
               <%@ include file="plantillas/plantillaTemaCabecera.jsp" %>
               
               </td>
            </tr>
            <!-- 
            En la segunda fila las plantillas tema particular + general 
            Esta segunda fila tendr� 2 columnas, la primera para la plantilla
            particular y la segunda para la general
            -->
            <tr>
               <!-- PRIMERA COLUMNA: TEMA PARTICULAR -->
               <td border="0" valign="center" align="center" colspan="2" bgcolor="006699" cellpadding="0" cellspacing="0">
       <!-- elecci�n de plantilla particular -->
       <% if ( "musica".equals(((String)textoPestanias.elementAt(pestaniaSeleccionadaInt))) ) {%>
          <%@ include file="plantillas/plantillamusica.jsp" %> 
         
       <% } %> 
       
       <% if ( "deportes".equals(((String)textoPestanias.elementAt(pestaniaSeleccionadaInt))) ) {%>
          <%@ include file="plantillas/plantilladeportes.html"%>           
       <% } %>
       
       <% if ( "informatica".equals(((String)textoPestanias.elementAt(pestaniaSeleccionadaInt))) ) {%> <!-- 3 -->
          <%@ include file="plantillas/plantillainformatica.jsp" %> 
       <% } %>          <!-- /3 -->
               </td>
    <%
    }
    %><!-- /1 -->

    <!-- tabla secundaria. Ser� vac�a en caso de que la pesta�a seleccionada
    sea la general o sera la propia general en caso de que la pesta�a seleccionada
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
               <td align="center" colspan="4" bgcolor="#006699">
               </td>
            </tr>
         </table><!-- con cabeceras y plantillas -->
         
      </td>
   </tr><!-- segunda fila de la tabla externa -->
 

</table><!-- tabla externa -->


</div>
<!-- PARAMS QUE SE PASAN AL SERVLET -->
<!-- param que indica el valor de la pesta�a inicialmente cargada -->
<input type="hidden" name="pestaniaAntigua" value="<%=pestaniaSeleccionada%>">
<!-- param que indica la pesta�a nueva seleccionada. Ser� modificado por la funci�n js
actualizaAccionYnuevaPestania( ... )-->
<input type="hidden" name="nuevaPestaniaSeleccionada" value="<%=pestaniaSeleccionada%>">
<!-- param que indica la acci�n realizada: buscar o cambiar de pesta�a. Modificada por
actualizaAccionYnuevaPestania( ... )-->
<input type="hidden" name="accion" value="buscar">
</form><!-- formularioGlobal -->
    


</td><!-- FIN SEGUNDA Columna de la tabla super-externa-->
</tr>



<tr>
   <td align ="center" valign="top">

   <br>
   
   <img src="<%=rutaImagenes%>utensiliosDeskBlack.gif">
   
   <!-- presenta los resultados de la b�squeda -->
   <%@ include file="/jsp/resultadosBusqueda.jsp" %>

   </td>
</tr>

</table> <!-- tabla super-externa -->

</body>
</html>


<!-- de momento el dise�o de la tabla de las pesta�as es:


tabla super externa
__________________________________________
					  |
		tabla externa pesta�as
                 _______________________
		|			|
		|pesta�as-4 columnas max|
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