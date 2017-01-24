package biba.agentes;

/**
* @(#) NumHits.java
*/

import java.lang.*;
import java.io.*;

/**
 * Clase que encapsula la información del número de enlaces devuelto por un buscador.
 */

public class NumHits implements Serializable{
	/**
	 * Entero que representa a cada buscador.
	 */
	public int buscador;

	/**
	 * Nombre del buscador.
	 */
	public String nomBuscador;

	/**
	 * Número de enlaces.
	 */
	public int numHits;

	/**
	 * Constructora. Recibe como parámetros el numero de enlaces y el nombre  n del buscador.
	 */
	public NumHits(String n, int numero) {
		nomBuscador = n;
		numHits = numero;
		buscador = 0;
		if (n.equalsIgnoreCase("Goole")) buscador = 0;
		else if (n.equalsIgnoreCase("Excite")) buscador = 1;
		else if (n.equalsIgnoreCase("Lycos")) buscador = 2;
		else if (n.equalsIgnoreCase("Yahoo")) buscador = 3;
	}

}



