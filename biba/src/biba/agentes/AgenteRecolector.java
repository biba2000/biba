package biba.agentes;
import java.io.*;
import java.net.*;
import java.util.*;
import biba.agentes.*;
import biba.persistencia.propiedades;
import biba.agentes.sincronizacion.*;
import biba.agentes.Busqueda;
/**
 *
 * Clase abstracta encargada de centralizar todo el proceso de consulta a un buscador.
 *
 */

/**
 * Title:        Buscador Basado en Agentes ( BIBA )
 * Description:  Ver las descripciones del documento de requisitos
 * Prototipo 1
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author (Grupo de SI) : Jorge Glez
 * @version 2.0
 */
public abstract class AgenteRecolector extends Thread
{

  protected int debug=0;

	/**
	 * N�mero de resultados a obtener en cada recolector
	 */
	protected final static int NUM_RESULTADOS = 20;

	/**
	 * referencia al buz�n de sincronizaci�n
	 */
	 BuzonAgentes buzon;

  /**
   * Direcci�n base del buscador.
   */

  protected String dirBase;

  /**
   * Atributo encargado de filtrar la informaci�n de la p�gina web.
   */

  private FiltradorHTML filtrador;

  /**
   * Nombre del filtrador
   */

  protected String nombre_fil;

  /**
   * Nombre del buscador
   */

  protected String nombre_buscador;

  /**
   * N�mero de Hits
   */

  private NumHits hits;

  /**
   * Resultados de la consulta
   */

  private Vector resul_consulta;

	/**
	 * Informaci�n sobre la busqueda a realizar
	 */
	protected Busqueda auxBusqueda;

/**
 *
 * Fijamos el proxy de salida al exterior
 *
 *
 *
 */

  public synchronized void  fijarProxy(){
    // Fijamos el Proxy
    System.setProperty("proxySet", "true" );
	  System.setProperty("http.proxyHost",biba.persistencia.propiedades.getPropiedad("PROXY_HOST"));
	  System.setProperty("http.proxyPort",biba.persistencia.propiedades.getPropiedad("PROXY_PORT"));


  }

/**
 *
 * Constructora.
 *
 * @param archivo_filtro nombre del buscador.
 * @param buz referencia al buz�n de sincronizaci�n del agente
 *
 */
  public AgenteRecolector(BuzonAgentes buz) {

    // comprobamos si estamos en modo depuracion
    if ("si".equals(propiedades.getPropiedad("DEBUG"))) debug=1;else debug=0;

    //Hay que fijar proxy?
    if(biba.persistencia.propiedades.getPropiedad("FIJAR_PROXY").equalsIgnoreCase("si")) fijarProxy();
		// asignamos el buz�n de sincronizaci�n
		buzon=buz;

  }

	/**
	 * Asigna el nobre del filtrador HTML a usar
	 */
	protected void setNombreFiltrador()
	{
    try{
      filtrador = new FiltradorHTML (nombre_buscador,nombre_fil);
    } catch (Exception e) {
        if (debug>0)System.out.println( "No se pudo crear un ejemplar de FiltradorHTML :"+nombre_fil);
    }
	}

 /**
  * Devuelve el vector de resultados de la �ltima b�squeda
  */

  public synchronized Vector getResultados()
  {
    if (resul_consulta ==null) if (debug>0)System.out.println("Se llam� a getResul_consulta sin hacer consulta o con 0 hits");
    return resul_consulta;
  }


  /**
 *
 * Prepara la consulta en el buscador rellenando los atributos necesarios y llamando a run()
 *
 *
 *
 */
/*
  synchronized public void setConsulta(Busqueda busqueda)
  {
    aux_plantillas=busqueda.vec_plantillas;
    aux_tema=busqueda.idTema;
    aux_cadena=busqueda.cadenaBusqueda;
		aux_anyade=busqueda.cadenaAnyadirBusqueda;
  }
*/

/**
 * Realiza la b�squeda, la filtra a trav�s del atributo Filtrador y devuelve un vector de resultados. La primera posici�n del vector es de la clase Num_Hits y el resto de posiciones son de la clase Tipo_Info (ver mas adelante).
 */

 public void run()
 {
    Vector v = new Vector ();
    if (debug>0)System.out.println ("Estoy en consulta "+nombre_fil+" !! Cadena="+this.auxBusqueda.cadenaBusqueda+" A�adido="+this.auxBusqueda.cadenaAnyadirBusqueda);
    try {
      String direccion;
			if (debug>0)System.out.println("antes de adapta "+nombre_fil);
      direccion=adapta();
	    if (debug>0)System.out.println("tras adapta "+nombre_fil);
      URL url = new URL(direccion);
	    if (debug>0)System.out.println("tras construir url "+nombre_fil);
      URLConnection conex = url.openConnection();
	    if (debug>0)System.out.println("tras construir conex "+nombre_fil);
      DataInputStream datos = new DataInputStream(conex.getInputStream());
	    if (debug>0)System.out.println("tras construir datos "+nombre_fil);
      v = filtrador.filtra(datos);
	    if (debug>0)System.out.println("tras filtrar datos "+nombre_fil);
    } catch (MalformedURLException e) {
        if (debug>0)System.err.println( "Direcci�n URL mal construida "+nombre_fil);
				e.printStackTrace();
    } catch (IOException e) {
        if (debug>0)System.err.println( "No se puede establecer la conexi�n "+nombre_fil);
				e.printStackTrace();
    } catch (Exception e) {
        if (debug>0)System.err.println( "Hubo problemas al filtrar "+nombre_fil);
				e.printStackTrace();
    }

    //eliminamos el objeto NumHits de la lista devuelta
    if(!v.isEmpty())
    {
      hits=(NumHits) v.firstElement();
      v.removeElementAt(0);
      resul_consulta=v;
    }

		// metemos la respuesta en el buzon
		Event ev = new Event(Event.BUSQUEDA_FINALIZADA,resul_consulta);
		buzon.meter(ev);
		if(debug>0)System.out.println("A punto de terminar hebra de Recolector");
 }

/**
 *
 * M�todo abstracto que las clases herederas deben implementar. Dada una consulta de entrada debe devolver la direcci�n de internet completa correspondiente a esa b�squeda en ese buscador. Este es el �nico m�todo que deben definir las hijas porque el resto del trabajo lo hace la clase madre.
 *
 * @param archivo_filtro nombre del buscador.
 *
 */

  abstract protected String adapta();

  /**
 * Devuelve el n�mero de hits obtenidos en la �ltima consulta.
 */
 public NumHits getHits()
 {
  if (hits==null) if (debug>0)System.out.println("Se llam� a getNumHits sin hacer consulta");
  return hits;
 }



}// de la clase

