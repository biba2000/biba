package biba.persistencia;

import java.io.*;

public class Caracteristica implements Serializable {
	/* ATRIBUTOS */
	private String tipo;
	private boolean estado;

	/* METODOS */

	/* Constructor */
	public Caracteristica(){
	  tipo = "";
	  estado = false;
	}

	/* Constructor con parámetros */
	public Caracteristica( String nombre ){
	  tipo = nombre;
	  estado = false;
	}

	/* Funciones para obtener los atributos */
	public boolean getEstado(){
		return estado;
	}

	/* Funciones para modificar los atributos */
	public void setEstado( boolean b ){
		estado = b;
	}

}