package biba.estructurasDatos;

/**
 * Title:        Buscador Basado en Agentes ( BIBA )
 * Description:  Ver las descripciones del documento de requisitos
 * Prototipo 2
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author (Grupo SI)=(Emilio Bobadilla,Juan Gallardo,Jorge Glez.,Daniel Vilches)
 * @version 1.1
 */

public class Estadisticas implements java.io.Serializable {


  /* ATRIBUTOS */
  private long numBusquedas; // Numero total de busquedas
  private long numClicks; // Numero total de clicks en resultados de busquedas
  private long clicksGoogle; // Numero de clicks en un resultado devuelto por Google
  private long clicksYahoo; // Numero de clicks en un resultado devuelto por Yahoo
  private long clicksExcite; // Numero de clicks en un resultado devuelto por Excite
  private long clicksLycos; // Numero de clicks en un resultado devuelto por Lycos

  /* METODOS */
  /* Constructor */
  public Estadisticas (){
    numBusquedas = 0;
    numClicks = 0;
    clicksGoogle = 0;
    clicksYahoo = 0;
    clicksExcite = 0;
    clicksLycos = 0;
  }

  /* Funciones para obtener los atributos */
  public long getNumBusquedas() {
    return numBusquedas;
  }

  public long getNumClicks() {
    return numClicks;
  }

  public long getClicksGoogle() {
    return clicksGoogle;
  }

  public long getClicksYahoo() {
    return clicksYahoo;
  }

  public long getClicksLycos() {
    return clicksLycos;
  }

  public long getClicksExcite() {
    return clicksExcite;
  }

  /* Funciones para modificar los atributos */
  public void setNumBusquedas(long num) {
    numBusquedas = num;
  }

  public void setNumClicks(long num) {
    numClicks = num;
  }

  public void setClicksGoogle(long num) {
    clicksGoogle = num;
  }

  public void setClicksYahoo(long num) {
    clicksYahoo = num;
  }

  public void setClicksExcite(long num) {
    clicksExcite = num;
  }
  public void setClicksLycos(long num) {
    clicksLycos = num;
  }

}