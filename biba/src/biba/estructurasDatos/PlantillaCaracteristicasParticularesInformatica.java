package biba.estructurasDatos;


public class PlantillaCaracteristicasParticularesInformatica
             extends PlantillaCaracteristicasGenerales  implements java.io.Serializable{



  //atributos
  public boolean imprescindible=false;

  public boolean hardware=false;
  public boolean tutoriales=false;
  public boolean programacion=false;
  public boolean articulos=false;
  public boolean driversHw=false;


  public PlantillaCaracteristicasParticularesInformatica(){
  this.hardware=false;
  this.tutoriales=false;
  this.programacion=false;
  this.articulos=false;
  this.driversHw=false;

  }
  public PlantillaCaracteristicasParticularesInformatica
                                (boolean imprescindible,
                                 boolean sitiosWeb,
                                 boolean imagenes,
                                 boolean audioMp3,
                                 boolean noticias,
                                 boolean productos,
                                 boolean hardware,
                                 boolean tutoriales,
                                 boolean programacion,
                                 boolean articulos,
                                 boolean driversHw){

    super(sitiosWeb,imagenes,audioMp3,noticias,productos);
    this.imprescindible=imprescindible;
    this.hardware=hardware;
    this.tutoriales=tutoriales;
    this.programacion=programacion;
    this.articulos=articulos;
    this.driversHw=driversHw;
    }

  /**
	 *	 devuelve un string que hay que añadir a la query según el valor
   *   de las plantillas particulares
	 */
  public String aniadirAquery() {

     String dev = "";

     if (this.articulos) dev = dev + "articles papers";
     if (this.driversHw) dev = dev + "drivers";
     if (this.hardware) dev = dev + "hardware";
     if (this.programacion) dev = dev + "languages compilator";
     if (this.tutoriales) dev = dev + "tutorial";

     return dev;
  }

  // devuelve un string que hay que añadir a la query según el valor
  // de las plantillas particulares. Es igual que añadirAquery
  // pero devuelve las palabras separadas por el signo +
  public String aniadirAquerySeparadoPorMas() {

     String dev = "";

     if (this.articulos) dev = dev + "+articles+papers";
     if (this.driversHw) dev = dev + "+drivers";
     if (this.hardware) dev = dev + "+hardware";
     if (this.programacion) dev = dev + "+languages+compilator";
     if (this.tutoriales) dev = dev + "+tutorial";

     return dev;
  }

}