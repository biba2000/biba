<!---------------------------- PLANTILLA VIAJES ---------------------------------------->
<center>
<table align="center" border="0" bgcolor="FFFFF0" width="270" cellspacing="0" cellpadding="0">

   <tr>
      <td align="center" valign="bottom" nowrap>
         <font size="1" face="Arial">
          Billetes  <input type="checkbox" name="billetes"  value="ON"
          <% if (plantillaViajes.billetes) { %>
             checked
          <%}%> > 
          Reservas  <input type="checkbox" name="reservas"  value="ON"
          <% if (plantillaViajes.reservas) { %>
             checked
          <%}%> > 
          Hoteles  <input type="checkbox" name="hoteles"  value="ON"
          <% if (plantillaViajes.hoteles) { %>
             checked
          <%}%> > 
         <br>
          Art&iacute;culos  <input type="checkbox" name="articulos"  value="ON"
          <% if (plantillaViajes.articulos) { %>
             checked
          <%}%> >   
          Agencias  <input type="checkbox" name="agencias"  value="ON"
          <% if (plantillaViajes.agencias) { %>
             checked
          <%}%> > 
          </font>
      </td>
    </tr>
</table>
</center>       
<!---------------------------- FIN PLANTILLA VIAJES ---------------------------------------->
