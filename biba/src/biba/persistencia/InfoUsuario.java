/*
 * Clase que encapsula todos los datos acerca de los usuarios del metabuscador
 */

package biba.persistencia;

import biba.persistencia.Caracteristica;
import biba.persistencia.Tema;
import biba.estructurasDatos.*;

import java.util.*;
import java.io.*;

public class InfoUsuario implements Serializable {

	/* ATRIBUTOS */
	private String nombre="";
	private String clave="";
  public String temaActual="";
	private Hashtable temas=null;
  public  String valorBusqueda="";
  public  Vector resultadosBusqueda=null;
	/* METODOS */

	/* Constructor */


  /* Constructor con parámetros */
	public InfoUsuario( String usuario, String password ){
		nombre = usuario;
		clave = password;

    temas = new Hashtable();

    Tema temaInformatica = new Tema(new PlantillaCaracteristicasParticularesInformatica());
    Tema temaMusica      = new Tema(new PlantillaCaracteristicasParticularesMusica());
    Tema temaDeportes    = new Tema(new PlantillaCaracteristicasParticularesDeportes());
    Tema temaViajes      = new Tema(new PlantillaCaracteristicasParticularesViajes());
    Tema temaGeneral     = new Tema(new PlantillaCaracteristicasGenerales());
    temaGeneral.setActivoEnSesion(true);
    temas.put("informatica",temaInformatica);
    temas.put("musica",temaMusica);
    temas.put("deportes",temaDeportes);
    temas.put("viajes",temaViajes);
    temas.put("general",temaGeneral);


}


	/* Funciones para obtener los atributos */
	public String getNombre(){
		return nombre;
	}

  public Vector getHotlinks(String tema)
  {
    Tema tem=getTema(tema);
    return tem.getHotlinks();
  }

	public String getClave(){
		return clave;
	}


  public Vector getTemasActivos(){
    Vector res=new Vector();
    String key="";
    Tema tema=null;
    for (Enumeration enume = temas.keys() ; enume.hasMoreElements() ;) {
         key=(String)enume.nextElement();
         tema = this.getTema(key);
         if (tema.getActivoEnSesion()) res.addElement(key);
     }
    return res;



  }
	public Tema getTema(String idTema){
    if(!temas.containsKey(idTema))
      return null;
    else
  		return (Tema)temas.get(idTema);
	}

  public PlantillaCaracteristicasGenerales getPlantilla(String idTema){
    PlantillaCaracteristicasGenerales plantilla=null;
    Tema tema;
    if(!temas.containsKey(idTema))
      return null;
    else{
  		tema = (Tema) temas.get(idTema);
      plantilla = tema.getPlantilla();
      return plantilla;
    }
	}


  public boolean setPlantilla(String idTema,PlantillaCaracteristicasGenerales plantilla){
    boolean res=true;
    Tema tema;
    if(!temas.containsKey(idTema))
      res=false;
    else{
  		tema = (Tema) temas.get(idTema);
      tema.setPlantilla(plantilla);

    }
    return res;

  }



	/* Funciones para modificar los atributos */
	public void setNombre( String s ){
		nombre = s;
	}

	public void setClave( String s ){
		clave = s;
	}

	public boolean setTema( String idTema, Tema t){
      boolean res=false;
      if (temas.containsKey(idTema)){
        temas.put(idTema,t);
        res=true;
      }

      return res;
	}

  public void setTemaActual(String tema)
  {
    temaActual=tema;
  }

  public String getTemaActual()
  {
    return temaActual;
  }

}
