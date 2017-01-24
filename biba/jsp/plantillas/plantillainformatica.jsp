<!---------------------------- PLANTILLA INFORMATICA ---------------------------------------->
<center>
<table align="center" border="0" bgcolor="FFFFF0" width="270" cellspacing="0" cellpadding="0">

   <tr>
      <td align="center" valign="bottom" nowrap>
         <font size="1" face="Arial">
          Hardware  <input type="checkbox" name="hardware"  value="ON"
          <% if (plantillaInformatica.hardware) { %>
             checked
          <%}%> > 
          Tutoriales  <input type="checkbox" name="tutoriales"  value="ON"
          <% if (plantillaInformatica.tutoriales) { %>
             checked
          <%}%> > 
          Programaci&oacute;n <input type="checkbox" name="programacion"  value="ON"
          <% if (plantillaInformatica.programacion) { %>
             checked
          <%}%> > 
         <br>
          Art&iacute;culos  <input type="checkbox" name="articulos"  value="ON"
          <% if (plantillaInformatica.articulos) { %>
             checked
          <%}%> >   
          Drivers HW   <input type="checkbox" name="driversHw"  value="ON"
          <% if (plantillaInformatica.driversHw) { %>
             checked
          <%}%> > 
          </font>
      </td>
    </tr>
</table>
</center>       
<!---------------------------- FIN PLANTILLA INFORMATICA ---------------------------------------->
