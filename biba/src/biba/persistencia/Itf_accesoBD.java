/* Clase para el manejo de la base de datos que guarda los datos de los usuarios
   registrados en el sistema */
package biba.persistencia;

import biba.persistencia.InfoUsuario;
import biba.agentes.*;
import biba.estructurasDatos.*;

public interface Itf_accesoBD {


  /**** METODOS SOBRE LA INFORMACION DE LOS USUARIOS ****/

	/* Devuelve: 'cierto' si existe un usuario registrado con ese nombre,
      	       'falso' en caso contrario */
	boolean existe (String usuario);

	/* Devuelve: 'cierto' si existe un usuario registrado con ese nombre y con esa clave,
      	       'falso' en otro caso */
	boolean es_correcta (String usuario, String clave);

	/* Devuelve la información disponible del usuario dado, si está registrado */
	InfoUsuario cargarUsuario (String usuario);

	/* Guarda los datos del usuario */
	void guardarUsuario (InfoUsuario datos);

        /** Actualiza la información de hotlinks para un usuario en la BD
         *  @param tit Título del nuevo hotlink
         *  @param ur Url del nuevo hotlink
         *  @param idTema Identificador del tema en el que estaba al pinchar en la url
         *  @param nombre Nombre del usuario que se va a actualizar
         *  */
         void actualizaHotlinks(Tipo_Info info, String idTema, String nombre);

	/* Crea un nuevo usuario con el nombre y la clave dados, lo insertamos en persistencia
        y devolvemos una instancia */
	InfoUsuario nuevoUsuario (String usuario, String clave);


  /**** METODOS SOBRE LA INFORMACION DE LOS BUSCADORES ****/

        /* Carga la tabla con la información acerca de los buscadores */
        TablaBuscadores cargarTabla ();

        /* Guarda la información de los buscadores en un archivo */
        void guardarTabla (TablaBuscadores tabla);


  /**** METODOS SOBRE LA INFORMACION DE LAS ESTADISTICAS ****/

        /* Devuelve la información disponible sobre las estadisticas */
        InfoEstadisticas cargarEstadisticas ();

        /* Guarda las estadisticas */
        void guardarEstadisticas (InfoEstadisticas estad);
}