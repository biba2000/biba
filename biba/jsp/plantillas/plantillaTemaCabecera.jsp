<!---------------------------------PLANTILLA CABECERA TEMA PARTICULAR---------------------------->
<table >
   <tr>
      <td>
      <input type="text" size="32" name="textoBusqueda"> 
      <INPUT align=absMiddle border=0  name="buscar"
             src="<%=rutaImagenes%>buscar.GIF" type=image value="buscar"
             onClick='actualizaAccionYnuevaPestania( "<%=pestaniaSeleccionada%>", "buscar" )'> 
      <!--font color="#FFCC66" size="3" face="Garamond">    
      Imprescindible</font>
      <input type="checkbox" name="imprescindible"
      <%-- if (plantilla.imprescindible) { --%>
         checked
      <%-- } --%> -->
      </td>
   </tr>
</table>
    
<!--------------------------------- FIN PLANTILLA CABECERA TEMA PARTICULAR---------------------------->                   
