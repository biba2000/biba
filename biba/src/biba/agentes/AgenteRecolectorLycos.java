package biba.agentes;
import java.util.*;
import biba.estructurasDatos.*;
import biba.agentes.sincronizacion.*;
import biba.utiles.UtilidadCadena;

/**
 *
 * Hereda de MotorBusqueda e implementa el método abstracto adapta.
 * Pruebas de implementación
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
 * @author (Grupo de SI)+Jorge Glez
 * @version 2.0
 */

public class AgenteRecolectorLycos extends AgenteRecolector
{
  // no se sabe para qué vale esto
  int pagina;

	UtilidadCadena util=new UtilidadCadena();

/* 	public AgenteRecolectorLycos(BuzonAgentes buz)
	{
			super("Lycos",buz);
    pagina=0;

	}
	*/
 	public AgenteRecolectorLycos(BuzonAgentes buz, Busqueda busqueda)
	{
		super(buz);
		if(busqueda.vec_plantillas!=null)
		{
		if( ((PlantillaCaracteristicasGenerales)busqueda.vec_plantillas).audioMp3)
		  nombre_fil="LycosMP3";
		else if (((PlantillaCaracteristicasGenerales)busqueda.vec_plantillas).imagenes)
		  nombre_fil="LycosImages";
		else
			nombre_fil="Lycos";
		}
		else
			nombre_fil="Lycos";
    pagina=0;
		this.auxBusqueda = busqueda;
		nombre_buscador="Lycos";

		this.setNombreFiltrador();
  }
  protected String adapta()
   {
   /*
    busqueda normal
    http://www.lycos.es/cgi-bin/pursuit?matchmode=and&mtemp=main&etemp=error&query=rom+para+pokemon+stadium+de+nintendo&cat=lycos

    búsqueda normal en spanol
    http://www.lycos.es/cgi-bin/pursuit?matchmode=and&mtemp=main&etemp=error&query=rom+para+pokemon+stadium+de+nintendo&cat=es&x=20&y=8


   */


    // sustituímos los blancos por "+" en la cadena de consulta
    String consulta_formateada="";

		consulta_formateada = util.convierteCharEnString(this.auxBusqueda.cadenaBusqueda.trim(),' ',"+AND+");
		String anyade = util.convierteCharEnString(this.auxBusqueda.cadenaAnyadirBusqueda,'+',"+OR+");
		consulta_formateada+=anyade;
		String ret="";

    // primero comprobamos si hay que añadir caract. particualres
    // al query (esto último no se aplica a la plantill "general"
    // despues vemos si alguna de las características generales
    // están a true para hacer búsquedas especializadas
/*
    PlantillaCaracteristicasGenerales plantilla = null;

    // primero: añadir al query
    if(this.auxBusqueda.idTema.equals("musica"))
    {
      // vemos si la plantilla "musica" esta en el vector
      plantilla = PlantillaCaracteristicasGenerales.localizarPlantillaPorTema("musica", this.auxBusqueda.vec_plantillas.elementAt(0));
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
          ret = "http://www.lycos.es/cgi-bin/pursuit?matchmode=and&mtemp=main&etemp=error&loc=searchbox&adv=1&query=" + consulta_formateada + "&cat=mp3";
          //this.cambiarFiltro("lycosMP3.filtro");
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
          }
          else { // ninguna característica señalada. Busqueda normal
            System.out.println("La plantilla elegida no tiene caracteristicas marcadas");
            ret = "http://www.lycos.es/cgi-bin/pursuit?matchmode=and&mtemp=main&etemp=error&query=" + consulta_formateada + "&cat=lycos";
          }
         }
        }
       }
      }
     } // if plantilla != null
*/
		if(auxBusqueda.vec_plantillas!=null)
		{
		if( ((PlantillaCaracteristicasGenerales)auxBusqueda.vec_plantillas).audioMp3)
		  ret="http://www.lycos.es/cgi-bin/pursuit?matchmode=and&mtemp=main&etemp=error&loc=searchbox&adv=1&query=" + consulta_formateada + "&cat=mp3";
		else if (((PlantillaCaracteristicasGenerales)auxBusqueda.vec_plantillas).imagenes)
		  ret="http://www.lycos.es/cgi-bin/pursuit?matchmode=and&mtemp=main&etemp=error&loc=searchbox&adv=1&query=" + consulta_formateada + "&cat=graphics&x=39&y=10";
		else
			ret="http://www.lycos.es/cgi-bin/pursuit?matchmode=or&mtemp=main&etemp=error&query=" + consulta_formateada + "&cat=lycos";
		}
		else
		  ret="http://www.lycos.es/cgi-bin/pursuit?matchmode=or&mtemp=main&etemp=error&query=" + consulta_formateada + "&cat=lycos";
    //System.out.println("La búsqueda es : "+ret);

		// colocamos el numero de hits que queremos
		ret += "&maxhits="+this.NUM_RESULTADOS;

    return ret;

   }
}