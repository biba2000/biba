package biba.estructurasDatos;

import java.util.Vector;

public class PlantillaCaracteristicasGenerales implements java.io.Serializable{

  //atributos
  public boolean sitiosWeb=false;
  public boolean imagenes =false;
  public boolean audioMp3=false;
  public boolean noticias=false;
  public boolean productos=false;

  //constructor
  public PlantillaCaracteristicasGenerales(){
     this.sitiosWeb=false;
     this.imagenes =false;
     this.audioMp3=false;
     this.noticias=false;
     this.productos=false;

  };

  public PlantillaCaracteristicasGenerales(boolean sitiosWeb,
                                           boolean imagenes,
                                           boolean audioMp3,
                                           boolean noticias,
                                           boolean productos){

    this.sitiosWeb=sitiosWeb;
    this.imagenes=imagenes;
    this.audioMp3=audioMp3;
    this.noticias=noticias;
    this.productos=productos;
  }


  public String toString(){
     String res="plantillaGeneral(";
     if (this.sitiosWeb==true)
        res+="sitiosWeb=ON, ";
     else
         res+="sitiosWeb=OFF, ";

     if (this.imagenes==true)
        res+="imagenes=ON, ";
     else
         res+="imagenes=OFF, ";

     if (this.audioMp3==true)
        res+="audioMp3=ON, ";
     else
         res+="audioMp3=OFF, ";

     if (this.noticias==true)
        res+="noticias=ON, ";
     else
         res+="noticias=OFF, ";

     if (this.productos==true)
        res+="productos=ON)";
     else
         res+="productos=OFF)";
     return res;


  }

	public String aniadirAquery()
	{
		return "";
	}
	public String aniadirAquerySeparadoPorMas() {
	  return "";
	}

   /**
  * localiza una plantilla dentro de un vector de plantillas según el tema.
  * si no la localiza devuelve null
  */
  public PlantillaCaracteristicasGenerales localizarPlantillaPorTema( String tema, Vector vectorPlantillas ) {

     boolean found = false;
     int i = 0;
     PlantillaCaracteristicasGenerales plantilla = null;

     if (vectorPlantillas != null) {
      while ( ( i <= (vectorPlantillas.size()-1)) && (!found) ) {

        plantilla = (PlantillaCaracteristicasGenerales)(vectorPlantillas.elementAt(i));

        if ( tema.equalsIgnoreCase("musica")) {
           if (plantilla instanceof PlantillaCaracteristicasParticularesMusica )
              found = true;
        }
        if ( tema.equalsIgnoreCase("informatica")) {
           if (plantilla instanceof PlantillaCaracteristicasParticularesInformatica )
              found = true;
        }

        i++;
      }
     }
     return plantilla;
  }

}
