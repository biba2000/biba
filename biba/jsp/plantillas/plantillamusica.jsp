<!---------------------------------PLANTILLA MÚSICA ---------------------------->

<center>
<table align="center" border="0" bgcolor="FFFFF0" width="270" cellspacing="0" cellpadding="0">

   <tr>
      <td align="center" valign="bottom" nowrap>
         <font size="1" face="Arial">
          Letras  <input type="checkbox" value="ON" name="letras"  
          <% if (plantillaMusica.letras) { %>
             checked
          <%}%> > 
          Conciertos  <input type="checkbox" value="ON" name="conciertos"  
          <% if (plantillaMusica.conciertos) { %>
             checked
          <%}%> > 
          <br>
          Oficiales  <input type="checkbox" value="ON" name="paginasOficiales"  
          <% if (plantillaMusica.paginasOficiales) { %>
             checked
          <%}%> > 
          Discograf&iacute;a   <input type="checkbox" value="ON" name="discografia"  
          <% if (plantillaMusica.discografia) { %>
             checked
          <%}%> > 
          </font>
      </td>
    </tr>
</table>
</center>       
<!---------------------------------FIN PLANTILLA MÚSICA ---------------------------->          
    