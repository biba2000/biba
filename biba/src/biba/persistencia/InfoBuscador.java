package biba.persistencia;


public class InfoBuscador implements java.io.Serializable {


  /* ATRIBUTOS */
  private long numClicks;
  private double valoracion;



  /* METODOS */
  /* Constructor */
  public InfoBuscador (){
    numClicks = 0;
		/**
		 * Asignamos un valor menor al minimo para poder chequear los posibles
		 * errores en las busquedas
		 */
    valoracion = 1;
  }

  /* Constructor con parámetros */
  public InfoBuscador ( long num, double val ){
    numClicks = num;
    valoracion = val;
  }

  /* Funciones para obtener los atributos */
  public long getNumClicks() {
    return numClicks;
  }

  public double getValoracion() {
    return valoracion;
  }

  /* Funciones para modificar los atributos */
  public void setNumClicks( long num ) {
    numClicks = num;
  }

  public void setValoracion( double val ) {
    valoracion = val;
  }


  /* Otras funciones */
  public void unClickMas (){
    numClicks++;
  }


}