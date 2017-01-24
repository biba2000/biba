package biba.agentes;
import java.util.*;
import biba.estructurasDatos.*;
import biba.agentes.sincronizacion.*;
import biba.utiles.UtilidadCadena;

/**
 *
 * Hereda de MotorBusqueda e implementa el m�todo abstracto adapta.
 * Pruebas de implementaci�n
 * Title:        <p>
 * Description:  <p>
 * Copyright:    Copyright (c) Juan Gallardo<p>
 * Company:      <p>
 * @author Juan Gallardo
 * @version 1.0
 *
 */



/**
 * Title:        Buscador Basado en Agentes ( BIBA )
 * Description:  Ver las descripciones del documento de requisitos
 * Prototipo 1
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author (Grupo de SI)
 * @version 1.0
 */

public class AgenteRecolectorYahoo extends AgenteRecolector
{
  // no se sabe para qu� vale esto
  int pagina;

	UtilidadCadena util=new UtilidadCadena();

/*  public AgenteRecolectorYahoo(BuzonAgentes buz)
	{
    super("Yahoo",buz);
    pagina=0;
  }
	*/
  public AgenteRecolectorYahoo(BuzonAgentes buz, Busqueda busqueda)
	{
    super(buz);
		this.nombre_fil="Yahoo";
		nombre_buscador="Yahoo";
    pagina=0;
		this.auxBusqueda = busqueda;
		this.setNombreFiltrador();
  }


  protected String adapta()
   {
   /*
    b�squeda normal (salen las categor�as)
    http://search.yahoo.com/bin/search?p=oscars&se supone que h=c

    web pages: 17 categor�as y 260 sites
    http://google.yahoo.com/bin/query?p=oscars&hc=17&hs=260

    web sites: 17 categor�as y 260 sites
    http://search.yahoo.com/search?p=oscars&hc=17&hs=260&h=s
   */
    // sustitu�mos los blancos por "+" en la cadena de consulta
    String consulta_formateada="";

		consulta_formateada = util.convierteCharEnString(this.auxBusqueda.cadenaBusqueda.trim(),' ',"+");

		String anyade = this.auxBusqueda.cadenaAnyadirBusqueda;

		consulta_formateada+=anyade;

    String ret = "http://google.yahoo.com/bin/query?p=" + consulta_formateada;

    // primero comprobamos si hay que a�adir caract. particualres
    // al query (esto �ltimo no se aplica a la plantill "general"
    // despues vemos si alguna de las caracter�sticas generales
    // est�n a true para hacer b�squedas especializadas
/*
    PlantillaCaracteristicasGenerales plantilla = null;

    // primero: a�adir al query
    if(idTema.equals("musica"))
    {
      // vemos si la plantilla "musica" esta en el vector
      plantilla = PlantillaCaracteristicasGenerales.localizarPlantillaPorTema("musica", plant);
      // en caso afirmativo, modificamos la query
//      if (plantilla != null) consulta_formateada=consulta_formateada+plantilla.aniadirAquerySeparadoPorMas();
    }
    else if (idTema.equals("informatica"))
    {
      // vemos si la plantilla "informatica" esta en el vector
      plantilla = PlantillaCaracteristicasGenerales.localizarPlantillaPorTema("informatica", plant);
      // en caso afirmativo, modificamos la query
//      if (plantilla != null) consulta_formateada=consulta_formateada+plantilla.aniadirAquerySeparadoPorMas();
    }
    else if (idTema.equals("general")) {
      plantilla = PlantillaCaracteristicasGenerales.localizarPlantillaPorTema("informatica", plant);
    }

    // segundo: buscador especializado
     if (plantilla != null ) {
      if (plantilla.audioMp3) {
          System.out.println("La plantilla elegida tiene AUDIO-MP3 marcada");
      }
      else {
       if (plantilla.imagenes) {
       }
       else {
        if (plantilla.noticias) {
        }
        else {
         if (plantilla.productos) {
         }
         else {
          if (plantilla.sitiosWeb) {

             System.out.println("La plantilla elegida tiene SITIOS-WEB marcada");
             //this.cambiarFiltro("yahooSitiosWeb.filtro");
             ret = "http://search.yahoo.com/search?p=" + consulta_formateada;
          }
          else { // ninguna caracter�stica se�alada. Busqueda normal
            System.out.println("La plantilla elegida no tiene caracteristicas marcadas");
             ret = "http://google.yahoo.com/bin/query?p=" + consulta_formateada;
          }
         }
        }
       }
      }
     } // if plantilla != null
*/

    ret = "http://google.yahoo.com/bin/query?p=" + consulta_formateada;
    //System.out.println("La b�squeda es : "+ret);

		// colocamos el numero de hits que queremos
		ret += "&n="+this.NUM_RESULTADOS;

    return ret;

   }
}
