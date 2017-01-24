        <table>
        <tr>
        <td>
            <p><select name="temaElegido" size="1">
                <option <%if ("General".equals( (String)request.getParameter( "temaElegido" ) )) {%> selected <%}%> >
                General
                </option>
                <option<%if ("Deportes".equals( (String)request.getParameter( "temaElegido" ) )) {%> selected <%}%> >
                Deportes</option>
                <option<%if ("Informática".equals( (String)request.getParameter( "temaElegido" ) )) {%> selected <%}%> >
                Informática</option>
                <option<%if ("Música".equals( (String)request.getParameter( "temaElegido" ) )) {%> selected <%}%> >
                Música</option>
                <option<%if ("Viajes".equals( (String)request.getParameter( "temaElegido" ) )) {%> selected <%}%> >
                Viajes</option>
            </select>

             <input type="text" size="32"
            name="textoBusqueda">
            <INPUT align=absMiddle border=0 name="buscar<%=pestaniaSeleccionada%>"
            src="<%=rutaImagenes%>buscar.GIF"
            type=image value="buscar<%=pestaniaSeleccionada%>"
            onClick='actualizaAccionYnuevaPestania( "<%=pestaniaSeleccionada%>", "buscar" )'> </FONT>

        </td>
        </tr>
        </table>


