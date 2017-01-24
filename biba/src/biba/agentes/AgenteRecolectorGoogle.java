package biba.agentes;
import java.util.*;
import biba.agentes.sincronizacion.*;
import biba.utiles.UtilidadCadena;

/**
 *
 * Hereda de MotorBusqueda e implementa el método abstracto adapta.
 * Pruebas de implementación
 * Title:        <p>
 * Description:  <p>
 * Copyright:    Copyright (c) Jorge González<p>
 * Company:      <p>
 * @author (Grupo de SI) : Jorge Glez
 * @version 1.0
 *
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

public class AgenteRecolectorGoogle extends AgenteRecolector
{
  // no se sabe para qué vale esto
  int pagina;

	UtilidadCadena util=new UtilidadCadena();

  public AgenteRecolectorGoogle(BuzonAgentes buz, Busqueda busqueda)
	{
    super(buz);
		this.nombre_fil="Google";
		nombre_buscador="Google";
    pagina=0;
		this.auxBusqueda = busqueda;
		setNombreFiltrador();
  }
/*  public AgenteRecolectorGoogle(BuzonAgentes buz)
	{
    super("Google",buz);
    pagina=0;
  }
	*/
  protected String adapta()
   {
    // sustituímos los blancos por "+" en la cadena de consulta
    String consulta_formateada="";

	  if (debug>0)System.out.println("La cad de busqueda es "+this.auxBusqueda.cadenaBusqueda);

		consulta_formateada = util.convierteCharEnString(this.auxBusqueda.cadenaBusqueda.trim(),' ',"+");
	  if (debug>0)System.out.println("La consulta fo 1 :"+consulta_formateada);
		String anyade = util.convierteCharEnString(this.auxBusqueda.cadenaAnyadirBusqueda,'+',"+OR+");
	  if (debug>0)System.out.println("anyade :"+anyade);
		consulta_formateada+=anyade;
		String ret="";

		// usamos los datos de las plantillas para focalizar la búsqueda
		// añadimos la cadena con operador OR
    ret="http://www.google.com/search?q="+consulta_formateada;

    if (debug>0)System.out.println("La búsqueda es : "+ret);

		// colocamos el numero de hits que queremos
	  ret += "&num="+this.NUM_RESULTADOS;

		return ret;

   }
}
