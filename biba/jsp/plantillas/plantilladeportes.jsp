<!---------------------------- PLANTILLA DEPORTES ---------------------------------------->
<center>
<table align="center" border="0" bgcolor="FFFFF0" width="270" cellspacing="0" cellpadding="0">

   <tr>
      <td align="center" valign="bottom" nowrap>
         <font size="1" face="Arial">
          Resultados  <input type="checkbox" name="resultados"  value="ON"
          <% if (plantillaDeportes.resultados) { %>
             checked
          <%}%> > 
          Fotos  <input type="checkbox" name="fotos"  value="ON"
          <% if (plantillaDeportes.fotos) { %>
             checked
          <%}%> > 
          Calendario  <input type="checkbox" name="calendario"  value="ON"
          <% if (plantillaDeportes.calendario) { %>
             checked
          <%}%> > 
         <br>
          Art&iacute;culos  <input type="checkbox" name="articulos"  value="ON"
          <% if (plantillaDeportes.articulos) { %>
             checked
          <%}%> >   
          Estad&iacute;sticas  <input type="checkbox" name="estadisticas"  value="ON"
          <% if (plantillaDeportes.estadisticas) { %>
             checked
          <%}%> > 
          </font>
      </td>
    </tr>
</table>
</center>       
<!---------------------------- FIN PLANTILLA DEPORTES ---------------------------------------->
