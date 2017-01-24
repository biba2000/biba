/**
 * Servlet que recoge las peticiones de los usuarios y gestiona los perfiles de los mismos
 */

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import java.util.*;

import biba.visualizador.*;
import biba.utiles.servlets.*;
import biba.persistencia.*;
import biba.estructurasDatos.*;
import biba.agentes.*;

public class AgenteUsuario extends HttpServlet implements SingleThreadModel {

  private int debug=0;
  /**
   *  Densidad minima a partir de la cual se añade una nueva pestaña
   */
  public final static int DENSIDAD_MINIMA = 3;

	/**
	 * Agente que realiza el seguimiento de las estadísticas
	 */
	private static AgenteEstadisticas ae;

	/**
	 * Tabla que recoge características de los buscadores
	 *
	 */
	private static TablaBuscadores tablaBuscadores;


  //Inicializar variables globales
  public void init(ServletConfig config) throws ServletException {

    super.init(config);

    // comprobamos si estamos en modo depuracion
    if ("si".equals(propiedades.getPropiedad("DEBUG"))) debug=1;else debug=0;


 		// Obtenemos la ruta donde se deja la salida de error
		String ficheroErr = biba.persistencia.propiedades.getPropiedad("FICHERO_ERROR_AGENTE_USUARIO");

    // Obtenemos la ruta donde se deja la salida estándar
    String ficheroOut = biba.persistencia.propiedades.getPropiedad("FICHERO_SALIDA_AGENTE_USUARIO");


    // Llevamos la salida estándar y la salida error a fichero.
	try{
			System.setErr(new PrintStream(new FileOutputStream(ficheroErr),true));
			System.err.println("Fecha y hora:" + new Date(System.currentTimeMillis()).toString());
			System.setOut(new PrintStream(new FileOutputStream(ficheroOut),true));
			System.out.println("Fecha y hora:" + new Date(System.currentTimeMillis()).toString());
		} catch (FileNotFoundException e){
      System.out.println("no se han encontrado: "+ficheroErr+" "+ficheroOut);
			e.printStackTrace();
		}

		// inicializamos los atributos

		ae=new AgenteEstadisticas();
		tablaBuscadores= new TablaBuscadores();


  }

  // Actualiza la plantilla (general) según las opciones elegidas por el usuario
  private void actualizaPlantilla( HttpServletRequest request,
     PlantillaCaracteristicasGenerales plantilla) {

    String imagenes="OFF";
    String audio="OFF";
    String productos="OFF";
    String noticias="OFF";
    String sitiosWeb="OFF";
    String caracteristicaMarcada="";

    caracteristicaMarcada = (String) request.getParameter("caracteristicaMarcada");

    if ( ("imagenes".equals( caracteristicaMarcada )) ) {
       imagenes = "ON";
    }

    if ( ("noticias".equals( caracteristicaMarcada )) ) {
       noticias = "ON";
    }

    if ( ("audioMp3".equals( caracteristicaMarcada )) ) {
       audio = "ON";
    }

    if ( ("sitiosWeb".equals( caracteristicaMarcada )) ) {
       sitiosWeb = "ON";
    }

    if ( ("productos".equals( caracteristicaMarcada )) ) {
       productos = "ON";
    }


    //valores plantilla caracteristicas general
/*    audio   = request.getParameter("audio");
    if (audio==null) audio ="OFF";
    imagenes  = request.getParameter("imagenes");
    if (imagenes==null) imagenes="OFF";
    productos = request.getParameter("productos");
    if (productos == null) productos ="OFF";
    noticias  = request.getParameter("noticias");
    if (noticias==null) noticias="OFF";
    sitiosWeb = request.getParameter("sitiosWeb");
    if (sitiosWeb==null) sitiosWeb="OFF";
*/
    if(!(audio.equals("OFF")))
       plantilla.audioMp3 = true;
    else
       plantilla.audioMp3 = false;

    if (!imagenes.equals("OFF"))
       plantilla.imagenes = true;
    else
       plantilla.imagenes = false;

   if(!productos.equals("OFF"))
       plantilla.productos= true;
   else
      plantilla.productos= false;

   if(!noticias.equals("OFF"))
       plantilla.noticias= true;
   else
     plantilla.noticias= false;

   if (!sitiosWeb.equals("OFF"))
        plantilla.sitiosWeb= true;
   else
        plantilla.sitiosWeb= false;

  }
  // Actualiza la plantilla (musica) según las opciones elegidas por el usuario
  private void actualizaPlantillaMusica( HttpServletRequest request,
     PlantillaCaracteristicasParticularesMusica plantilla) {

    String letras="";
    String conciertos="";
    String paginasOficiales="";
    String discografia="";

    //valores plantilla caracteristicas general
    letras   = request.getParameter("letras");
    if (letras==null) letras ="OFF";
    conciertos  = request.getParameter("conciertos");
    if (conciertos==null) conciertos="OFF";
    paginasOficiales = request.getParameter("paginasOficiales");
    if (paginasOficiales == null) paginasOficiales ="OFF";
    discografia  = request.getParameter("discografia");
    if (discografia==null) discografia="OFF";

    if(!(letras.equals("OFF")))
       plantilla.letras = true;
    else
       plantilla.letras = false;

    if (!conciertos.equals("OFF"))
       plantilla.conciertos = true;
    else
       plantilla.conciertos = false;

   if(!paginasOficiales.equals("OFF"))
       plantilla.paginasOficiales= true;
   else
      plantilla.paginasOficiales= false;

   if(!discografia.equals("OFF"))
       plantilla.discografia= true;
   else
     plantilla.discografia= false;

  }
  // Actualiza la plantilla (informatica) según las opciones elegidas por el usuario
  private void actualizaPlantillaInformatica( HttpServletRequest request,
     PlantillaCaracteristicasParticularesInformatica plantilla) {

    String hardware="";
    String tutoriales="";
    String programacion="";
    String articulos="";
    String driversHw="";

    //valores plantilla caracteristicas general

    hardware   = request.getParameter("hardware");
    if (hardware==null) hardware ="OFF";
    tutoriales  = request.getParameter("tutoriales");
    if (tutoriales==null) tutoriales="OFF";
    programacion = request.getParameter("programacion");
    if (programacion == null) programacion ="OFF";
    articulos  = request.getParameter("articulos");
    if (articulos==null) articulos="OFF";
    driversHw = request.getParameter("driversHw");
    if (driversHw==null) driversHw="OFF";

    if(!(hardware.equals("OFF")))
       plantilla.hardware = true;
    else
       plantilla.hardware = false;

    if (!tutoriales.equals("OFF"))
       plantilla.tutoriales = true;
    else
       plantilla.tutoriales = false;

   if(!programacion.equals("OFF"))
       plantilla.programacion= true;
   else
      plantilla.programacion= false;

   if(!articulos.equals("OFF"))
       plantilla.articulos= true;
   else
     plantilla.articulos= false;

   if (!driversHw.equals("OFF"))
        plantilla.driversHw= true;
   else
        plantilla.driversHw= false;

  }

  // Actualiza la plantilla (deportes) según las opciones elegidas por el usuario
  private void actualizaPlantillaDeportes( HttpServletRequest request,
     PlantillaCaracteristicasParticularesDeportes plantilla) {

    String resultados="";
    String fotos="";
    String calendario="";
    String articulos="";
    String estadisticas="";

    //valores plantilla caracteristicas general

    resultados   = request.getParameter("resultados");
    if (resultados==null) resultados ="OFF";
    fotos  = request.getParameter("fotos");
    if (fotos==null) fotos="OFF";
    calendario = request.getParameter("calendario");
    if (calendario == null) calendario ="OFF";
    articulos  = request.getParameter("articulos");
    if (articulos==null) articulos="OFF";
    estadisticas = request.getParameter("estadisticas");
    if (estadisticas==null) estadisticas="OFF";

    if(!(resultados.equals("OFF")))
       plantilla.resultados = true;
    else
       plantilla.resultados = false;

    if (!fotos.equals("OFF"))
       plantilla.fotos = true;
    else
       plantilla.fotos = false;

   if(!calendario.equals("OFF"))
       plantilla.calendario= true;
   else
      plantilla.calendario= false;

   if(!articulos.equals("OFF"))
       plantilla.articulos= true;
   else
     plantilla.articulos= false;

   if (!estadisticas.equals("OFF"))
        plantilla.estadisticas= true;
   else
        plantilla.estadisticas= false;

  }
  // Actualiza la plantilla (viajes) según las opciones elegidas por el usuario
  private void actualizaPlantillaViajes( HttpServletRequest request,
     PlantillaCaracteristicasParticularesViajes plantilla) {

    String billetes="";
    String reservas="";
    String hoteles="";
    String articulos="";
    String agencias="";

    //valores plantilla caracteristicas general

    billetes   = request.getParameter("billetes");
    if (billetes==null) billetes ="OFF";
    reservas  = request.getParameter("reservas");
    if (reservas==null) reservas="OFF";
    hoteles = request.getParameter("hoteles");
    if (hoteles == null) hoteles ="OFF";
    articulos  = request.getParameter("articulos");
    if (articulos==null) articulos="OFF";
    agencias = request.getParameter("agencias");
    if (agencias==null) agencias="OFF";

    if(!(billetes.equals("OFF")))
       plantilla.billetes = true;
    else
       plantilla.billetes = false;

    if (!reservas.equals("OFF"))
       plantilla.reservas = true;
    else
       plantilla.reservas = false;

   if(!hoteles.equals("OFF"))
       plantilla.hoteles= true;
   else
      plantilla.hoteles= false;

   if(!articulos.equals("OFF"))
       plantilla.articulos= true;
   else
     plantilla.articulos= false;

   if (!agencias.equals("OFF"))
        plantilla.agencias= true;
   else
        plantilla.agencias= false;

  }




  //Procesar una petición HTTP Post
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    //valor de la búsqueda
    String nombreUsuario="";
    String valorBusqueda="";
    String imprescindible="";
    Tema temazo=null;
    PlantillaCaracteristicasGenerales plantilla=null;
    String idTemaActual="";
    String idTemaEscogidoListaDesplegable="";
    Vector resultadoBusqueda=null;
    // gestor del recurso de visualización
    biba.visualizador.Gestor gestorVisualizador= new Gestor();

    //infoServlet contendrá el contexto del Servlet y la peticion del usuario
    //necesario para comunicar el servlet con el recurso de visualización
    InfoServlet infoServlet = null;

    //Información del usuario
    InfoUsuario infoUsuario =null;
    //accion a realizar
    String accion= request.getParameter("accion");

    //Creamos la sesion
    HttpSession sesion = request.getSession(true);
    if (sesion == null) {
       gestorVisualizador.errorNoHaySesion(response);
       if (debug>0)System.out.println("Sesion==null");
      }

    if (debug>0)System.out.println("Sesion!=null");


    // Si hay que abrir sesión es debido a que ServletInicio ha delegado el control
    // en nosotros, y debe haber insertado el nombre del usuario en el ServletContext
    if ("abrirSesion".equals(accion)){

       nombreUsuario = (String) this.getServletContext().getAttribute("nombreUsuario");

       if (debug>0)System.out.println("Recuperamos la  InfoUsuario de "+ nombreUsuario );
       infoUsuario = biba.persistencia.AccesoBD.cargarUsuario(nombreUsuario);

//       if (debug>0)System.out.println("InfoUsuario("+infoUsuario.getNombre()+", "+infoUsuario.getClave());

       accion="";
       //creamos la infoServlet para entregarla al recurso de visualización
       infoServlet = new InfoServlet(sesion,this.getServletContext(),request,response);

       String idTema="general";
       //CUANDO SE ABRE LA SESION DEL USUARIO,LE PONEMOS POR DEFECTO EN LA PLANTILLA
       //GENERAL
       //recuperamos el estado de la plantilla de características generales del usuario
        plantilla  = infoUsuario.getPlantilla(idTema);

        if (plantilla==null)if (debug>0)System.out.println("La plantilla general está vacía");

        if (debug>0)System.out.println("recuperamos la plantilla general");
        if (plantilla.productos)
           if (debug>0)System.out.println("plantilla.productos ON");
        else
           if (debug>0)System.out.println("plantilla.productos OFF");


       //recuperamos los temas que están avtivos de entre todos los posibles
       Vector temasActivos = infoUsuario.getTemasActivos();
       if (debug>0)System.out.println("Vector temasActivos.size="+temasActivos.size()+" "+(String)temasActivos.elementAt(0));
       //abrimos la sesión por primera vez
       gestorVisualizador.abrirSesionPrimeraVez(temasActivos,idTema,plantilla,nombreUsuario,infoServlet);


    }else
    if ("buscar".equals(accion)){

/*		String ficheroOut = biba.persistencia.propiedades.getPropiedad("FICHERO_SALIDA_AGENTE_USUARIO");
		try{
				System.setOut(new PrintStream(new FileOutputStream(ficheroOut),true));
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
*/

       nombreUsuario = request.getParameter("nombreUsuario");
       //recuperamos la Info del usuario
			 if (debug>0)System.out.flush();
       if (debug>0)System.out.println("Recuperamos InfoUsuario");
       infoUsuario = biba.persistencia.AccesoBD.cargarUsuario(nombreUsuario);
       if (debug>0)System.out.println("InfoUsuario("+nombreUsuario+", "+infoUsuario.getClave());

       //creamos la infoServlet para entregarla al recurso de visualización
       infoServlet = new InfoServlet(sesion,this.getServletContext(),request,response);


       //recuperamos los parámetros que nos ha enviado la vista cliente del sistema
       if (debug>0)System.out.println("valor pestaniaAntigua="+request.getParameter("pestaniaAntigua"));

       idTemaActual=request.getParameter("pestaniaAntigua");

       valorBusqueda = request.getParameter("textoBusqueda");

       //recuperamos la plantilla del usuario
       plantilla = infoUsuario.getPlantilla(idTemaActual);

       if (debug>0){
          System.out.println("LA PLANTILLA QUE RECUPERAMOS DE PERSISTENCIA del tema:"+idTemaActual);
          plantilla.toString();
       }

      // actualizamos plantilla con las características generales marcadas en el interfaz
      actualizaPlantilla(request,plantilla);

     //Si idTemaActual no es "general" hay que rellenar el resto de la
     //características particulares
       if ("musica".equals(idTemaActual))
          actualizaPlantillaMusica(request,
              (PlantillaCaracteristicasParticularesMusica)plantilla);
       else if ("informatica".equals(idTemaActual))
          actualizaPlantillaInformatica(request,
              (PlantillaCaracteristicasParticularesInformatica)plantilla);
       else if ("deportes".equals(idTemaActual))
          actualizaPlantillaDeportes(request,
              (PlantillaCaracteristicasParticularesDeportes)plantilla);
       else if ("viajes".equals(idTemaActual))
          actualizaPlantillaViajes(request,
              (PlantillaCaracteristicasParticularesViajes)plantilla);

/*     else if ("...".equals(idTemaActual))
        actualizaPlantilla...(plantilla);
      */

     // actualizamos la información de usuario referente a la plantilla
     infoUsuario.setPlantilla(idTemaActual,plantilla);


     // actualizar valor de imprescindible (solo si no es general)
     if (!"general".equals(idTemaActual)){
       imprescindible = request.getParameter("imprescindible");
       if (imprescindible==null) imprescindible="OFF";
       if (imprescindible.equals("OFF"))
         infoUsuario.getTema(idTemaActual).setImprescindible(false);
       else
         infoUsuario.getTema(idTemaActual).setImprescindible(true);
     }


      // Si la pestaña era la general, recogemos el valor de la lista
      //desplegable de temas (puede ser "general")
      if ("general".equals(idTemaActual)) {
        idTemaEscogidoListaDesplegable = Tema.convierteTema(request.getParameter("temaElegido"));
      }
      else {
        idTemaEscogidoListaDesplegable = idTemaActual;
      }

     // Solo hacemos algo si el Tema escogido de la lista desplegable no es general
     if (!idTemaEscogidoListaDesplegable.equals("general"))
     // Control extra prototipo 1:
     //    solo consideramos esto si el tema es informatica o musica
     /*
		 &&(idTemaEscogidoListaDesplegable.equals("informatica")
     ||idTemaEscogidoListaDesplegable.equals("musica"))
     )
		 */
			{

       // cargamos el tema actual
       temazo = infoUsuario.getTema(idTemaEscogidoListaDesplegable);

       //actualizamos la información referente al tema por el que el usuario ha buscado
       temazo.setUnaBusquedasMas();

       //calculamos la nueva densidad de búsqueda del tema buscado
       temazo.calculaDensidad();

       // si temazo no estaba activo, ver si reune las propiedades adecuadas
       // para ser activado
       if (!temazo.getActivoEnSesion()) {
          // En el prototipo 1 activamos a partir de la 3º busqueda
          // Mas adelante activaremos segun la densidad
          //if (debug>0)System.out.println("- DENSIDAD ACTUAL= "+temazo.getDensidad()+" -> de la pestaña "+idTemaEscogidoListaDesplegable);
          //if (temazo.getDensidad() > DENSIDAD_MINIMA)
          if (temazo.getNumeroBusquedas() >= DENSIDAD_MINIMA)
             temazo.setActivoEnSesion(true);
       }

       // guardamos el tema actualizado en info usuario
       infoUsuario.setTema(idTemaEscogidoListaDesplegable,temazo);
     }


/**********************************************************************
********************************** POR HACER ****************************/

/***** BEGIN TRABAJO DE AGENTES DE RECOL. Y FILTRADO  ********/
     //Ahora procedemos a la búsqueda
     //Aquí lanzariamos la hebra cronometro
     // new crono("Cronometro Busuqeda").start();
     //

/*     if (debug>0)System.out.println("valorBusqueda="+valorBusqueda);

        //CREARÍA EL AGENTE DE USUARIO Y SE REALIZARIA
        //LA BUSQUEDA
        //DESPUES SE DEBERÍA LLAMAR A UN MËTODO DEL VISUALIZADOR QUE CONSTRUYESE LA RESPUESTA
        //A PARTIR DE UN java.util.Vector() de Strings, las cuales contienen URL's
*/

         SuperAgenteUsuario sau=new SuperAgenteUsuario();

         // creamos el objeto búsqueda para pasárselo al superagente
         //consultamos al SuperAgenteUsuario
         PlantillaCaracteristicasGenerales plantillas=new PlantillaCaracteristicasGenerales();
 				 String anyadir= "";

         if (idTemaActual.equalsIgnoreCase("general"))
				 {
            plantillas = (infoUsuario.getPlantilla("general"));
				 }
         else
         {
            //plantillas.addElement(infoUsuario.getPlantilla("general"));
            plantillas = (infoUsuario.getPlantilla(idTemaActual));
						anyadir=infoUsuario.getPlantilla(idTemaActual).aniadirAquerySeparadoPorMas();
         }

         Busqueda busq=new Busqueda(plantillas, idTemaActual, valorBusqueda, anyadir);

         resultadoBusqueda=sau.busca(busq);

         //biba.utiles.EliminaRepeticiones eliminaRep = new biba.utiles.EliminaRepeticiones();
         //resultadoBusqueda = eliminaRep.elimina(resultadoBusqueda);

				 // actualizamos el número total de búsquedas en las estadísticas
				 ae.actualizaBusquedas(idTemaActual);

				 // actualizamos la tabla de buscadores
				 tablaBuscadores.setUnaBusquedaMas(idTemaActual);

////////////////////////////////// PRUEBAS OFF-LINE


//  resultadoBusqueda = new Vector();
/**  Tipo_Info elem1 = new Tipo_Info( "http://www.google.com", "titulazo", "resumencillo kjdfjfj k k jk jk jk jj   jjjjjjj",77,"yomismo.com");
  resultadoBusqueda.addElement( elem1 );
  resultadoBusqueda.addElement( elem1 );
  resultadoBusqueda.addElement( elem1 );
*/
///////////////////////////////////////////////////////////


         //Guardamos la información de busqueda en la infoUsuario para así poder recuperarla
         //después, cuando el usario desee consultar una URL
         infoUsuario.valorBusqueda=valorBusqueda;
         infoUsuario.resultadosBusqueda=resultadoBusqueda;

         //Guardamos el tema actual para actualizar los hotlinks posteriormente
         infoUsuario.setTemaActual(idTemaEscogidoListaDesplegable);

         //guardamos la infousuario en persistencia
         biba.persistencia.AccesoBD.guardarUsuario(infoUsuario);

         //le pasamos el resultado al visualizador
         //creamos la infoServlet para entregarla al recurso de visualización


         infoServlet = new InfoServlet(sesion,this.getServletContext(),request,response);

         gestorVisualizador.abrirSesion(valorBusqueda,
                                       resultadoBusqueda,
                                       infoUsuario.getTemasActivos(),
                                       idTemaActual,
                                       plantilla,
                                       nombreUsuario,
                                       infoServlet);

    }
	else
    if ("cambioPestania".equals(accion)){
       nombreUsuario = request.getParameter("nombreUsuario");
       //recuperamos la Info del usuario
       if (debug>0)System.out.println("Recuperamos InfoUsuario");
       infoUsuario = biba.persistencia.AccesoBD.cargarUsuario(nombreUsuario);
       if (debug>0)System.out.println("InfoUsuario("+nombreUsuario+", "+infoUsuario.getClave());

       //creamos la infoServlet para entregarla al recurso de visualización
       infoServlet = new InfoServlet(sesion,this.getServletContext(),request,response);


       //recuperamos los parámetros que nos ha enviado la vista cliente del sistema
       if (debug>0)System.out.println("CAMBIANDO A PESTANIA="+request.getParameter("nuevaPestaniaSeleccionada"));

       // obtenemos la id de la nueva pestaña
       idTemaActual=request.getParameter("nuevaPestaniaSeleccionada");

       valorBusqueda = request.getParameter("textoBusqueda");

       // limpiamos el vector de resultados
       resultadoBusqueda = new Vector();

       //le pasamos el resultado al visualizador
       //creamos la infoServlet para entregarla al recurso de visualización

       infoServlet = new InfoServlet(sesion,this.getServletContext(),request,response);

       gestorVisualizador.abrirSesion(valorBusqueda,
                                      resultadoBusqueda,
                                      infoUsuario.getTemasActivos(),
                                      idTemaActual,
                                      infoUsuario.getPlantilla(idTemaActual),
                                      nombreUsuario,
                                      infoServlet);
    }
    else if ("logout".equals(accion))
    {
      // invalidar la sesión y salir
      infoServlet = new InfoServlet(sesion,this.getServletContext(),request,response);
      sesion.invalidate();
      gestorVisualizador.hacerLogout(infoServlet);
      if (debug>0)System.out.println("El estado tras hacer el logout es "+infoServlet.toString());
    }
/*    else if ("hotlinks".equals(accion))
    {
      // mostrar los hotlinks
//      infoServlet = new InfoServlet(sesion,this.getServletContext(),request,response);
//      sesion.invalidate();
//      gestorVisualizador.hacerLogout(infoServlet);
      if (debug>0)System.out.println("Acción mostrar hotlinks pulsada");
    }
*/
    else
    {
      infoServlet = new InfoServlet(sesion,this.getServletContext(),request,response);
      gestorVisualizador.error("Se ha producido un error inesperado al procesar la acción devuelta al agente de Usuario",infoServlet);
      // error, la acción no es correcta
    }
  }

/*
	Por GET me llegan

  1 las peticiones de abrir URL
  2 la petición de abrir los hotlinks
*/
public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
      int debug = 0;

   // comprobamos si estamos en modo depuracion
    if ("si".equals(propiedades.getPropiedad("DEBUG"))) debug=1;else debug=0;

///////////////////////////////////////

 String accion = (String)request.getParameter("accion");
 //Creamos el visualizador
  biba.visualizador.Gestor gestorVisualizador= new  biba.visualizador.Gestor();

 if ( "abrirHotlinks".equals(accion) ) {

    //parámetros que recogemos del formulario que envía el usuario
    String nombre= "";

    //infoServlet contendrá el contexto del Servlet y la peticion del usuario
    //necesario para comunicar el servlet con el recurso de visualización
    InfoServlet infoServlet = null;

    //Creamos la sesion
    HttpSession sesion = request.getSession(true);


    //creamos la infoServlet para entregarla al recurso de visualización
    infoServlet = new InfoServlet(sesion,this.getServletContext(),request,response);

    //cogemos nombre del usuario
    nombre = request.getParameter("nombreUsuario");

    //cogemos vector de hotlinks de persistencia
    InfoUsuario infoUser = AccesoBD.cargarUsuario(nombre);

    Vector vectorHotlinks = infoUser.getHotlinks((String) sesion.getAttribute("pestana") );

    if (debug>0)System.out.println("vectorHotlinks "+ vectorHotlinks.toString());

    //creamos la infoServlet para entregarla al gestor de visualización
    infoServlet = new InfoServlet(sesion,this.getServletContext(),request,response);

    gestorVisualizador.abrirHotLinks(vectorHotlinks,infoServlet);

} // abrirHotlinks
//////////////////////////////////////////////////////////////
else { //ABRIR UNA URL

    //Creamos la sesion
    HttpSession sesion = request.getSession(true);
    if (sesion == null) {
      gestorVisualizador.errorNoHaySesion(response);
      if (debug>0)System.out.println("Sesion==null");
    }

    if (debug>0)System.out.println("Sesion!=null");

    //recuperamos los parametros que nos envía el cliente
    String nombreUsuario = request.getParameter("nombreUsuario");
    String numeroUrlElegida = request.getParameter("url");

    //infoServlet contendrá el contexto del Servlet y la peticion del usuario
    //necesario para comunicar el servlet con el recurso de visualización
    InfoServlet infoServlet = new InfoServlet(sesion,this.getServletContext(),request,response);

    //Recuperamos la info del usuario para saber cual era el resultado de su búsqueda
    if (debug>0)System.out.println("Recuperamos la  InfoUsuario de "+ nombreUsuario );
    InfoUsuario infoUsuario = biba.persistencia.AccesoBD.cargarUsuario(nombreUsuario);

    if (debug>0)System.out.println("InfoUsuario("+infoUsuario.getNombre()+", "+infoUsuario.getClave());

    //Extraemos los parametros de la busqueda de la infousuario
    Vector resultados=infoUsuario.resultadosBusqueda;
    String valor=infoUsuario.valorBusqueda;
    if (debug>0)System.out.println("El valor de busqueda del usuario es "+valor);
    if (debug>0)System.out.println("El tamaño del vector de resultados es:"+resultados.size());

    biba.agentes.Tipo_Info info =  (biba.agentes.Tipo_Info)resultados.elementAt(Integer.parseInt(numeroUrlElegida));
    String url = info.dame_url();
    String titulo = info.dame_titulo();
    if (debug>0)System.out.println("La url elegida por"+nombreUsuario+" es "+ url);

    /* HOTLINKS */
    // nueva actualización de los hotlinks
    /* Si estamos en la pestaña GENERAL añadimos el hotlink en
       dicho tema y en el tema del combo box , si es distinto
       si no sólo actualizamos el hotlink en la pestaña en la que estemos
    */
    String pestana=request.getParameter("pestana");
    String combo=request.getParameter("combo");
    Tema tma=new Tema(null);

    // Primero convertimos el valor del combo, si es null tomamos
    // y usamos el valor de la pestaña ( significa que no estamos en la
    // pestaña general

    // convertimos el valor del combo antes de usarlo

    if(debug==1) System.out.println("Intento convertir el combo: "+combo);
    combo = tma.convierteTema(combo);
    if(debug==1) System.out.println("Convertido a : "+combo);


    // actualizamos los hotlinks
    if(debug==1) System.out.println("Actualizo hotlinks con "+url+" pestaña="+pestana+" combo="+combo+" user="+nombreUsuario);
    // pestaña general y combo general
    if (pestana.equalsIgnoreCase("general") & combo.equalsIgnoreCase("general"))
    {
        biba.persistencia.AccesoBD.actualizaHotlinks(info,pestana,nombreUsuario);
    }
    else if (pestana.equalsIgnoreCase("general") & !combo.equalsIgnoreCase("general"))
    {
        biba.persistencia.AccesoBD.actualizaHotlinks(info,pestana,nombreUsuario);
        if(!combo.equalsIgnoreCase("error"))
        biba.persistencia.AccesoBD.actualizaHotlinks(info,combo,nombreUsuario);
    }
    else if (!(pestana==null))
    {
      // estamos en una pestaña que no es general, usamos la pestaña directamente
      biba.persistencia.AccesoBD.actualizaHotlinks(info,pestana,nombreUsuario);
    }
    else
    {
        // situacion de error
        if(debug==1) System.out.println("ERROR: Error al determinar la pestaña para añadir los hotlinks");
    }
    // asertamos el vector de hotlinks en la sesión para que sea mostrado por ServletHotlinks
    Vector hl;
    hl=infoUsuario.getHotlinks(pestana);

    sesion.setAttribute("hotlinks",hl);
    if (debug>0) System.out.println("hotlinks asertados en el session");

		// ESTADISTICAS
		ae.actualizaClick(pestana,info.dame_quien());

		// TABLA DE BUSCADORES
		tablaBuscadores.actualizaClick(pestana,info.dame_quien());

    infoServlet = new InfoServlet(sesion,this.getServletContext(),request,response);

    gestorVisualizador.abrirResultado( url, infoServlet );

    } // abrirResultado
}


  //Obtener información del servlet
  public String getServletInfo() {
    return "AgenteUsuario BIBA!";
  }


}
