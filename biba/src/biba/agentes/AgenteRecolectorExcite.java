package biba.agentes;
import java.util.*;
import biba.estructurasDatos.*;
import biba.agentes.sincronizacion.*;
import biba.utiles.UtilidadCadena;
import biba.estructurasDatos.*;
/**
 *
 * Hereda de MotorBusqueda e implementa el método abstracto adapta.
 *
 * Pruebas de implementación
 * Title:        <p>
 * Description:  <p>
 * Copyright:    Copyright (c) Jorge González<p>
 * Company:      <p>
 * @author (Grupo de SI) : Jorge Glez
 * @version 1.0
 */


/**
 * Title:        Buscador Basado en Agentes ( BIBA )
 * Description:  Ver las descripciones del documento de requisitos
 * Prototipo 1
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author (Grupo de SI) + Jorge Glez
 * @version 1.0
 */

public class AgenteRecolectorExcite extends AgenteRecolector
{
	UtilidadCadena util=new UtilidadCadena();

	public AgenteRecolectorExcite(BuzonAgentes buz, Busqueda busqueda)
	{
		super(buz);
		if(busqueda.vec_plantillas!=null)
		{
		  // cogemos las características generales
			if( ((PlantillaCaracteristicasGenerales)busqueda.vec_plantillas).audioMp3)
		    nombre_fil = "ExciteMP3";
		  else if (((PlantillaCaracteristicasGenerales)busqueda.vec_plantillas).sitiosWeb)
		    nombre_fil = "ExciteSitiosWeb";
		  else
			  nombre_fil = "Excite";
		}
		else
			  nombre_fil = "Excite";


		nombre_buscador="Excite";

		this.auxBusqueda = busqueda;
		this.setNombreFiltrador();

	}

  protected String adapta()
  {
    /* la llamada antigua(del aleph) era "http://search.excite.com/search.gw?s="+devolucion+"&c=web&start=0&showSummary=true",
         sigue funcionando */

		/* la actual es
		http://www.excite.com/search/
		*/

    String consulta_formateada="";

		consulta_formateada = util.convierteCharEnString(this.auxBusqueda.cadenaBusqueda.trim(),' ',"+AND+");

    // usando la información para focalizar la búsqueda
		String anyade = util.convierteCharEnString(this.auxBusqueda.cadenaAnyadirBusqueda,'+',"+OR+");
		consulta_formateada+=anyade;

    String ret="";

		if(auxBusqueda.vec_plantillas!=null)
		{
		if( ((PlantillaCaracteristicasGenerales)auxBusqueda.vec_plantillas).audioMp3)
		  ret = "http://search.excite.com/search.gw?c=audio.mp3&s="+consulta_formateada;
		else if (((PlantillaCaracteristicasGenerales)auxBusqueda.vec_plantillas).sitiosWeb)
		  ret = "http://search.excite.com/search.gw?c=web&s="+consulta_formateada;
		else
			ret="http://search.excite.com/search.gw?s="+consulta_formateada;
		}
		else
			ret="http://search.excite.com/search.gw?s="+consulta_formateada;

    //System.out.println("La búsqueda es : "+ret);

	 // colocamos el numero de hits que queremos
	 ret += "&perPage="+this.NUM_RESULTADOS;

   return ret;


   }

}