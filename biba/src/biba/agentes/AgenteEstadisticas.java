package biba.agentes;

import biba.estructurasDatos.InfoEstadisticas;
import biba.persistencia.propiedades;
import java.io.*;
import java.util.Vector;
/**
 * Title:        Buscador Basado en Agentes ( BIBA )
 * Description:  Ver las descripciones del documento de requisitos
 * Prototipo 2
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author (Grupo SI)=(Emilio Bobadilla,Juan Gallardo,Jorge Glez.,Daniel Vilches)
 * @version 1.1
 */


public class AgenteEstadisticas {

  public final static String rutaEstadisticas = propiedades.getPropiedad ("RUTA_ESTADISTICAS");

  /* ATRIBUTOS */
  private InfoEstadisticas datos; // Estadisticas totales

	protected int debug=0;


  /* METODOS */

  /* Constructor */
  public AgenteEstadisticas() {

	  // comprobamos si estamos en modo depuracion
    if ("si".equals(propiedades.getPropiedad("DEBUG"))) debug=1;else debug=0;

    datos = new InfoEstadisticas();
  }


  public synchronized void actualizaBusquedas(String tema) {
    // Carga las estadisticas desde archivo
    datos = biba.persistencia.AccesoBD.cargarEstadisticas();

    // Suma 1 al numero de busquedas total y del tema
    datos.setNumBusquedas(datos.getNumBusquedas("totales")+1,"totales");
    datos.setNumBusquedas(datos.getNumBusquedas(tema)+1,tema);

    /// Guarda las estadisticas a archivo
    biba.persistencia.AccesoBD.guardarEstadisticas(datos);
		imprimeEstadisticas();

  }

  public synchronized void actualizaClicksGoogle(String tema) {
    // Carga las estadisticas desde archivo
    datos = biba.persistencia.AccesoBD.cargarEstadisticas();

    // Suma 1 al numero de clicks total,total de ese tema
    // y del buscador Google total y en ese tema
    datos.setNumClicks(datos.getNumClicks("totales")+1,"totales");
    datos.setNumClicks(datos.getNumClicks(tema)+1,tema);
    datos.setClicksGoogle(datos.getClicksGoogle("totales")+1,"totales");
    datos.setClicksGoogle(datos.getClicksGoogle(tema)+1,tema);

    /// Guarda las estadisticas a archivo
    biba.persistencia.AccesoBD.guardarEstadisticas(datos);

  }

  public synchronized void actualizaClicksLycos(String tema) {
    // Carga las estadisticas desde archivo
    datos = biba.persistencia.AccesoBD.cargarEstadisticas();

    // Suma 1 al numero de clicks total,total de ese tema
    // y del buscador Lycos total y en ese tema
    datos.setNumClicks(datos.getNumClicks("totales")+1,"totales");
    datos.setNumClicks(datos.getNumClicks(tema)+1,tema);
    datos.setClicksLycos(datos.getClicksLycos("totales")+1,"totales");
    datos.setClicksLycos(datos.getClicksLycos(tema)+1,tema);

    /// Guarda las estadisticas a archivo
    biba.persistencia.AccesoBD.guardarEstadisticas(datos);

  }

	/**
	 * Actualiza las estadísticas de la lista de buscadores en un tema cuando el usuario ha elegido algún enlace
	 */
  public synchronized void actualizaClick(String tema, Vector listaBusca) {

		for(int i=0;i<listaBusca.size();i++)
		{
			if (((String)listaBusca.elementAt(i)).equals("Yahoo"))
				actualizaClicksYahoo(tema);
			else if (((String)listaBusca.elementAt(i)).equals("Excite"))
				actualizaClicksExcite(tema);
			else if (((String)listaBusca.elementAt(i)).equals("Google"))
				actualizaClicksGoogle(tema);
			else if (((String)listaBusca.elementAt(i)).equals("Lycos"))
				actualizaClicksLycos(tema);
		  else
			{
				if (debug>0) System.err.println("ERROR: Se intento actualizar buscador:"+(String)listaBusca.elementAt(i)+" en la clase AgenteEstadísticas");
			}
		}

		imprimeEstadisticas();

	}

  public synchronized void actualizaClicksYahoo(String tema) {
    // Carga las estadisticas desde archivo
    datos = biba.persistencia.AccesoBD.cargarEstadisticas();

    // Suma 1 al numero de clicks total,total de ese tema
    // y del buscador Yahoo total y en ese tema
    datos.setNumClicks(datos.getNumClicks("totales")+1,"totales");
    datos.setNumClicks(datos.getNumClicks(tema)+1,tema);
    datos.setClicksYahoo(datos.getClicksYahoo("totales")+1,"totales");
    datos.setClicksYahoo(datos.getClicksYahoo(tema)+1,tema);

    /// Guarda las estadisticas a archivo
    biba.persistencia.AccesoBD.guardarEstadisticas(datos);

  }

  public synchronized void actualizaClicksExcite(String tema) {
    // Carga las estadisticas desde archivo
    datos = biba.persistencia.AccesoBD.cargarEstadisticas();

    // Suma 1 al numero de clicks total,total de ese tema
    // y del buscador Excite total y en ese tema
    datos.setNumClicks(datos.getNumClicks("totales")+1,"totales");
    datos.setNumClicks(datos.getNumClicks(tema)+1,tema);
    datos.setClicksExcite(datos.getClicksExcite("totales")+1,"totales");
    datos.setClicksExcite(datos.getClicksExcite(tema)+1,tema);

    /// Guarda las estadisticas a archivo
    biba.persistencia.AccesoBD.guardarEstadisticas(datos);
  }

  /* Genera un archivo de texto con las estadisticas */
  public void imprimeEstadisticas() {
    // Carga las estadisticas desde archivo
    datos = biba.persistencia.AccesoBD.cargarEstadisticas();

    String ficheroOut = rutaEstadisticas + "estadisticas.txt";
    try {
      System.setOut(new PrintStream(new FileOutputStream(ficheroOut),true));
      System.out.println("ESTADISTICAS:");
      System.out.println("-------------");
      System.out.println();
    } catch (FileNotFoundException e){
      System.out.println("No se ha encontrado: "+ficheroOut);
      e.printStackTrace();
    }

    String temas[] = {"totales","general","informatica","musica","deportes","viajes"};
    String temaActual;
    for (int i = 0; i <= 5; i++) {
      temaActual = temas[i];
      System.out.println();
      System.out.println("*"+temaActual);
      System.out.print(" Numero de Busquedas ");
      System.out.print(" Numero de Clicks ");
      System.out.print(" Numero de Clicks(Google) ");
      System.out.print(" Numero de Clicks (Yahoo) ");
      System.out.print(" Numero de Clicks(Excite) ");
      System.out.println();
      System.out.print(" ------------------- ");
      System.out.print(" ---------------- ");
      System.out.print(" ------------------------ ");
      System.out.print(" ------------------------ ");
      System.out.print(" ------------------------ ");
      System.out.println();
      System.out.print("          "+datos.getNumBusquedas(temaActual)+"          ");
      System.out.print("       "+datos.getNumClicks(temaActual)+"       ");
      System.out.print("             "+datos.getClicksGoogle(temaActual)+"             ");
      System.out.print("             "+datos.getClicksYahoo(temaActual)+"             ");
      System.out.print("             "+datos.getClicksExcite(temaActual)+"             ");
      System.out.println();
    }
    System.out.close();
  }
/**
 * Ejecutado a parte consigue un nuevo fichero de estadísticas con los datos
 * actuales del servidor
 */
  public static void main( String args[] ) {
    AgenteEstadisticas ae;

    ae = new AgenteEstadisticas();

/*    ae.actualizaBusquedas("general");
    ae.actualizaClicksGoogle("general");
    ae.actualizaClicksYahoo("general");
    ae.actualizaBusquedas("general");
    ae.actualizaClicksGoogle("general");
    ae.actualizaBusquedas("informatica");
    ae.actualizaClicksExcite("informatica");
    ae.actualizaClicksGoogle("informatica");
    ae.actualizaBusquedas("deportes");
    ae.actualizaClicksYahoo("deportes");
*/
    ae.imprimeEstadisticas();
  }


}