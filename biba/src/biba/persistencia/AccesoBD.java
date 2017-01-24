package biba.persistencia;

import java.io.*;
import java.util.Vector;
import java.util.Date;
import java.util.Hashtable;
import biba.agentes.Tipo_Info;
import biba.persistencia.InfoUsuario;
import biba.persistencia.Tema;
import biba.persistencia.Caracteristica;
import biba.persistencia.TablaBuscadores;
import biba.estructurasDatos.InfoEstadisticas;

public class AccesoBD implements Serializable {

  public final static String rutaPerfiles = propiedades.getPropiedad ("RUTA_PERFILES_USUARIO");
  public final static String rutaTablaBuscadores = propiedades.getPropiedad ("RUTA_TABLA_BUSCADORES");
  public final static String rutaEstadisticas = propiedades.getPropiedad ("RUTA_ESTADISTICAS");


  /**** METODOS SOBRE LA INFORMACION DE LOS USUARIOS ****/

  public synchronized static boolean existe (String usuario) {
    //String fichero usuario
    String fUsuario = rutaPerfiles + usuario + ".dat";
    System.out.println("biba.persistencia.AccesoBD.existe :"+ fUsuario);
    File f = new File( fUsuario );
    return f.exists();
  }

  public synchronized static boolean es_correcta (String usuario, String clave){
    if (existe(usuario)) {
      InfoUsuario usu = cargarUsuario(usuario);
      String contrasenia = usu.getClave();
      if (clave == contrasenia) {
        return true;
      }
      else
        return false;
    }
    else
      return false;
  }

  public synchronized static InfoUsuario cargarUsuario (String usuario){
    //String fichero usuario
    String fUsuario = rutaPerfiles + usuario + ".dat";

    try {
      if(existe(usuario)) {
        ObjectInputStream in = new ObjectInputStream(
                          new FileInputStream(rutaPerfiles+usuario+".dat"));
        return (InfoUsuario)in.readObject();
      }
      else {
        System.out.println("Usuario: "+fUsuario+" no encontrado");
        return null;
      }
    } catch(Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public synchronized static void guardarUsuario (InfoUsuario datos){
    try {
      ObjectOutputStream out = new ObjectOutputStream(
                   new FileOutputStream(rutaPerfiles + datos.getNombre()+".dat"));
      out.writeObject(datos);
      out.flush();
      out.close(); // Also flushes output
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  /** Actualiza la información de hotlinks para un usuario en la BD
   *  @param tit Título del nuevo hotlink
   *  @param ur Url del nuevo hotlink
   *  @param idTema Identificador del tema en el que estaba al pinchar en la url
   *  @param nombre Nombre del usuario que se va a actualizar
   *  */
  public synchronized static void actualizaHotlinks(Tipo_Info info, String idTema, String nombre)
  {
    InfoUsuario user = cargarUsuario(nombre);

    Tema tem=user.getTema(idTema);
    tem.actualiza_hotlinks(info);
    user.setTema(idTema,tem);
    guardarUsuario(user);

  }

  public synchronized static InfoUsuario nuevoUsuario (String usuario, String clave){
    InfoUsuario infoUser = new InfoUsuario(usuario,clave);
    guardarUsuario(infoUser);
    return infoUser;
  }


  /**** METODOS SOBRE LA INFORMACION DE LOS BUSCADORES ****/

  public synchronized static TablaBuscadores cargarTabla () {

    String fBuscadores = rutaTablaBuscadores + "tablaBuscadores.dat";

    File f = new File( fBuscadores );

    try {
      if ( f.exists() ) {
        ObjectInputStream in = new ObjectInputStream(
                        new FileInputStream(fBuscadores));
        return (TablaBuscadores)in.readObject();
      }
      else // Si el fichero no existe, creamos una tabla nueva
        return new TablaBuscadores();
    } catch(Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public synchronized static void guardarTabla (TablaBuscadores tabla){
    try {
      ObjectOutputStream out = new ObjectOutputStream(
          new FileOutputStream(rutaTablaBuscadores+"tablaBuscadores.dat"));
      out.writeObject(tabla);
      out.close(); // Also flushes output
    } catch(Exception e) {
      e.printStackTrace();
    }
  }


  /**** METODOS SOBRE LA INFORMACION DE LAS ESTADISTICAS ****/

  public synchronized static InfoEstadisticas cargarEstadisticas () {

    String fEstadisticas = rutaEstadisticas + "estadisticas.dat";

    File f = new File( fEstadisticas );

    try {
      if ( f.exists() ) {
        ObjectInputStream in = new ObjectInputStream(
                        new FileInputStream(fEstadisticas));
        return (InfoEstadisticas)in.readObject();
      }
      else // Si el fichero no existe, creamos uno nuevo
        return new InfoEstadisticas();
    } catch(Exception e) {
      e.printStackTrace();
			System.err.println("ERROR: FAllo al leer archivo de estadísticas");
      return null;
    }
  }

  public synchronized static void guardarEstadisticas (InfoEstadisticas estad){
    try {
      ObjectOutputStream out = new ObjectOutputStream(
          new FileOutputStream(rutaEstadisticas+"estadisticas.dat"));
      out.writeObject(estad);
      out.close(); // Also flushes output
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

}
