<center>     
        
<table align="center" border="0" bgcolor="FFFFF0" width="270" cellpadding="0" cellspacing="0" vspace="0" >
    
    
    
      <tr>
<td align="left" valign="bottom" nowrap>
        
            <font size="1" face="Arial">Sitios Web  <input type="radio" name="caracteristicaMarcada" value="sitiosWeb"
            
            <% if (plantilla.sitiosWeb) {%>
            checked
            <%}%> 
            >
             Imágenes </font><font size="1"><input type="radio" name="caracteristicaMarcada" value="imagenes"
             <% if (plantilla.imagenes) {%>
                checked
             <%}%> 
            >
            </font>
            
             <font size="1" face="Arial">   Audio/Mp3 </font><font size="1"><input type="radio" name="caracteristicaMarcada" value="audioMp3"
             <% if (plantilla.audioMp3) {%>
                checked
             <%}%>
             >
             </font>
	<br>
            <font size="1" face="Arial">Noticias  <input type="radio" name="caracteristicaMarcada" value="noticias"
            <% if (plantilla.noticias) {%>
                checked
            <%}%> >
       	    </font>
            <font size="1" face="Arial">Ninguno  <input type="radio" name="caracteristicaMarcada" value="productos"
            <% if (plantilla.productos) {%>
                checked
            <%}%> >
       	    </font>

        
        </td>      
        </tr>   
    
        </table>
</center>                  
            