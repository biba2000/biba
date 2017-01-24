package biba.agentes;

/**
 * Title:        Buscador Basado en Agentes ( BIBA )
 * Description:  Ver las descripciones del documento de requisitos
 * Prototipo 1
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author (Grupo de SI) : Jorge Glez
 * @version 1.0
 */
import biba.persistencia.propiedades;
import biba.agentes.sincronizacion.*;

public abstract class SuperAgente extends Thread
{

	protected int debug=0;


/**
 * Tiempo m�ximo de espera para la recolecci�n, se abortar�n las b�squedas pasado ese tiempo
 */
	protected final static int TIEMPO_MAXIMO_RECOLECCION_EN_MS = 120000;

/**
 * Tiempo m�ximo de espera para el filtraje
 */
	protected final static int TIEMPO_MAXIMO_FILTRAJE_EN_MS = 60000;

/**
 * N�mero total de recolectores paralelizables
 */
	protected final static int NUM_RECOLECTORES = 4;

/**
 * N�mero total de filtradores paralelizables
 */
	protected final static int NUM_FILTRADORES = 1;

/**
 * Buz�n de sincronizaci�n para los agentes recolectores y un temporizador
 */
	protected BuzonAgentes buzonRecolector;
/**
 * Buz�n de sincronizaci�n para los agentes filtradores y un temporizador
 */
	protected BuzonAgentes buzonFiltrador;

/**
 * Encargado de generar un evento que pare la b�squeda
 */
	protected Temporizador temporizador;

  public SuperAgente()
	{
	  buzonRecolector = new BuzonAgentes();
	  buzonFiltrador = new BuzonAgentes();
		// comprobamos si estamos en modo depuracion
    if ("si".equals(propiedades.getPropiedad("DEBUG"))) debug=1;else debug=0;
  }
}