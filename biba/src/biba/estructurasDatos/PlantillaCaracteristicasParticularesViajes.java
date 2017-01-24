package biba.estructurasDatos;


public class PlantillaCaracteristicasParticularesViajes
             extends PlantillaCaracteristicasGenerales  implements java.io.Serializable{



  //atributos
  public boolean imprescindible=false;

  public boolean billetes=false;
  public boolean reservas=false;
  public boolean hoteles=false;
  public boolean articulos=false;
  public boolean agencias=false;


  public PlantillaCaracteristicasParticularesViajes(){
  this.billetes=false;
  this.reservas=false;
  this.hoteles=false;
  this.articulos=false;
  this.agencias=false;

  }
  public PlantillaCaracteristicasParticularesViajes
                                (boolean imprescindible,
                                 boolean sitiosWeb,
                                 boolean imagenes,
                                 boolean audioMp3,
                                 boolean noticias,
                                 boolean productos,
                                 boolean billetes,
                                 boolean reservas,
                                 boolean hoteles,
                                 boolean articulos,
                                 boolean agencias){

    super(sitiosWeb,imagenes,audioMp3,noticias,productos);
    this.imprescindible=imprescindible;

    this.billetes=billetes;
    this.reservas=reservas;
    this.hoteles=hoteles;
    this.articulos=articulos;
    this.agencias=agencias;
    }


  /**
	 *	 devuelve un string que hay que añadir a la query según el valor
   *   de las plantillas particulares
	 */
  public String aniadirAquery() {

     String dev = "";

     if (this.billetes) dev = dev + "ticket";
     if (this.reservas) dev = dev + "reserve";
     if (this.hoteles) dev = dev + "hotel";
     if (this.articulos) dev = dev + "report";
		 if (this.agencias) dev = dev + "travel agency";
     return dev;
  }

  public String aniadirAquerySeparadoPorMas() {

     String dev = "";

     if (this.billetes) dev = dev + "+ticket";
     if (this.reservas) dev = dev + "+reserve";
     if (this.hoteles) dev = dev + "+hotel";
     if (this.articulos) dev = dev + "+report";
		 if (this.agencias) dev = dev + "+travel+agency";
     return dev;
  }
}