package biba.estructurasDatos;

import java.util.Hashtable;
import biba.persistencia.propiedades;
/**
 * Title:        Buscador Basado en Agentes ( BIBA )
 * Description:  Ver las descripciones del documento de requisitos
 * Prototipo 2
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author (Grupo SI)=(Emilio Bobadilla,Juan Gallardo,Jorge Glez.,Daniel Vilches)
 * @version 1.1
 */

public class InfoEstadisticas implements java.io.Serializable {


  /* ATRIBUTOS */
  private Hashtable estadisticas;

	private int debug=0;

  /* METODOS */
  /* Constructor */
  public InfoEstadisticas (){

	  // comprobamos si estamos en modo depuracion
    if ("si".equals(propiedades.getPropiedad("DEBUG"))) debug=1;else debug=0;


    estadisticas = new Hashtable();

    estadisticas.put("totales",new Estadisticas());
    estadisticas.put("general",new Estadisticas());
    estadisticas.put("informatica",new Estadisticas());
    estadisticas.put("musica",new Estadisticas());
    estadisticas.put("deportes",new Estadisticas());
    estadisticas.put("viajes",new Estadisticas());
  }

  /* Funciones para obtener los atributos */
  public long getNumBusquedas(String tema) {
    return  ((Estadisticas) estadisticas.get(tema)).getNumBusquedas();
  }

  public long getNumClicks(String tema) {
    return ((Estadisticas) estadisticas.get(tema)).getNumClicks();
  }

  public long getClicksGoogle(String tema) {
    return ((Estadisticas) estadisticas.get(tema)).getClicksGoogle();
  }

  public long getClicksYahoo(String tema) {
    return ((Estadisticas) estadisticas.get(tema)).getClicksYahoo();
  }

  public long getClicksExcite(String tema) {
    return ((Estadisticas) estadisticas.get(tema)).getClicksExcite();
  }
  public long getClicksLycos(String tema) {
    return ((Estadisticas) estadisticas.get(tema)).getClicksLycos();
  }

  /* Funciones para modificar los atributos */
  public void setNumBusquedas(long num, String tema) {
		if(debug>0) System.out.println("Actualizo búsquedas en "+tema);
    ((Estadisticas) estadisticas.get(tema)).setNumBusquedas(num);
  }

  public void setNumClicks(long num, String tema) {
    ((Estadisticas) estadisticas.get(tema)).setNumClicks(num);
  }

  public void setClicksGoogle(long num, String tema) {
    ((Estadisticas) estadisticas.get(tema)).setClicksGoogle(num);
  }

  public void setClicksYahoo(long num, String tema) {
    ((Estadisticas) estadisticas.get(tema)).setClicksYahoo(num);
  }
  public void setClicksLycos(long num, String tema) {
    ((Estadisticas) estadisticas.get(tema)).setClicksLycos(num);
  }

  public void setClicksExcite(long num, String tema) {
    ((Estadisticas) estadisticas.get(tema)).setClicksExcite(num);
  }

}