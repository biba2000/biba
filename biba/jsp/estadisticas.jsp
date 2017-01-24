<%@ page import="java.io.*" %>
<%@ page import="biba.persistencia.*" %>
<%@ page import="biba.utiles.*"%>
<%@ page session="true" %>

<html>

<head>

<title>BIBA! Estadisticas de uso  </title>

</head>

<body>


<CENTER>	

<H1> ESTADISTICAS DEL SISTEMA BIBA</H1>

</CENTER>
 <% 
     

		

    
    String rutaDatos="";
    String estadisticas="";
    String tbuscadores="";
    rutaDatos = biba.persistencia.propiedades.getPropiedad("RUTA_TABLA_BUSCADORES");
    estadisticas = rutaDatos + "estadisticas.txt";
    tbuscadores =  rutaDatos + "tbuscadores.txt";
  
    //Sacamos por la salida el contenido de los ficheros de estadisticas y la tabla de buscadores
    
    
    String cadHtml="";
    BufferedReader entrada = null;
    entrada = new BufferedReader(new FileReader(estadisticas));
    
    UtilidadCadena uc = new UtilidadCadena();
    
    String s="";
    while((s = entrada.readLine()) != null) {
	cadHtml = uc.convierteCharEnString(s,' ',"&nbsp;");
	cadHtml+="<br>";
	out.print(cadHtml);
    }
    entrada.close();
  
  %>
  <H1> TABLA DE BUSCADORES </H1>
  <%
    cadHtml="";
    entrada = null;
    entrada = new BufferedReader(new FileReader(tbuscadores));
    
    
    s="";
    while((s = entrada.readLine()) != null) {
	cadHtml = uc.convierteCharEnString(s,' ',"&nbsp;");
	cadHtml+="<br>";
	out.print(cadHtml);
    }
    entrada.close();
  
  %>
  


</body>  
</html>
  