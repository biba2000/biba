package biba.agentes;
import java.util.Vector;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c)Jorge Glez
 * Company:
 * @author (Grupo de SI) : Jorge Glez
 * @version 1.0
 */

public class Busqueda {

  /**
   * Identifica el tema al que pertenece la búsqueda
   */
  public String idTema="";

  /**
   * Estado de la plantilla con las características generales.
   * Contiene una plantilla de carac generales y otra de particulares, si es necesario.
   * Los componentes se saben consultando el valor de idTema
   * idTema="general" -> tiene sólo las generales
   * idTema != "general" -> tiene generales y particulares de ese tema
   */
  public biba.estructurasDatos.PlantillaCaracteristicasGenerales vec_plantillas;

  /**
   * Cadena que el usuario quiere buscar
   */
  public String cadenaBusqueda="";

	/**
	 * Cadena que se añade al query para focalizar la búsqueda, se usa siempre
	 * con el operador OR para no reducir los resultados.
	 * Debería contener algun separador entre diferentes palabras ( por ejemplo un signo +)
	 */
	public String cadenaAnyadirBusqueda="";

  public Busqueda(biba.estructurasDatos.PlantillaCaracteristicasGenerales plantillas, String tema,String cadenaBusq,String cadenaAnyade) {
    vec_plantillas=plantillas;
    idTema=tema;
    cadenaBusqueda=cadenaBusq;
		cadenaAnyadirBusqueda=cadenaAnyade;
  }

}