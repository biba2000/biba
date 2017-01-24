import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import java.util.*;

import biba.visualizador.*;
import biba.utiles.servlets.InfoServlet;
import biba.utiles.servlets.ErrorServletRegistro;
import biba.persistencia.*;
import biba.agentes.*;

public class ServletBusquedaRapida extends HttpServlet{
 private int debug=0;
  //Inicializar variables globales
  public void init(ServletConfig config) throws ServletException {

    super.init(config);

    // comprobamos si estamos en modo depuracion
    if ("si".equals(propiedades.getPropiedad("DEBUG"))) debug=1;else debug=0;


 		// Obtenemos la ruta donde se deja la salida de error
		String ficheroErr = biba.persistencia.propiedades.getPropiedad("FICHERO_ERROR_SERVLET_BUSQUEDA_RAPIDA");

    // Obtenemos la ruta donde se deja la salida estándar
    String ficheroOut = biba.persistencia.propiedades.getPropiedad("FICHERO_SALIDA_SERVLET_BUSQUEDA_RAPIDA");


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
  }


  //Procesar una petición HTTP Post
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    //parámetros que recogemos del formulario que envía el usuario
    String valorBusqueda="";
    String action ="";

    //infoServlet contendrá el contexto del Servlet y la peticion del usuario
    //necesario para comunicar el servlet con el recurso de visualización
    InfoServlet infoServlet = null;


    //Vector que contendra la lisat de Strings, con las URL's  del resultado de la búsqueda
    Vector resultadoBusqueda =null;

    // gestor del recurso de visualización
    biba.visualizador.Gestor gestorVisualizador= new Gestor();


    //Creamos la sesion
    HttpSession sesion = request.getSession(true);
    if (sesion == null) {
       gestorVisualizador.errorNoHaySesion(response);
       if (debug>0)System.out.println("Sesion==null");

    }
    if (debug>0)System.out.println("Sesion!=null");

    //Vemos si el valor action es el adecuado,es decir, el correspondiente
    // a una busqueda rapida
    action=request.getParameter("action");

    if (action==null){
       //creamos la infoServlet para entregarla al recurso de visualización
       infoServlet = new InfoServlet(sesion,this.getServletContext(),request,response);
       gestorVisualizador.error("No se ha especificado una acción a realizar desde"+
                                 " el formulario de búsqueda rápida",
                                 infoServlet);
       if (debug>0)System.out.println("action==null");
    }

    if (debug>0)System.out.println("action!=null");

    boolean accionCorrecta = "busquedaRapida".equals(action);


    if(!accionCorrecta){
        if (debug>0)System.out.println("action no correcta");
       //creamos la infoServlet para entregarla al recurso de visualización
       infoServlet = new InfoServlet(sesion,this.getServletContext(),request,response);
       gestorVisualizador.error("No se ha especificado una acción correcta a realizar desde"+
                                 " el formulario de búsqueda rápida",
                                 infoServlet);

    }


    if("busquedaRapida".equals(action)){
        if (debug>0)System.out.println("action ==busquedaRapida");

        //cojemos el valor de la busqueda
        valorBusqueda = request.getParameter("valorBusqueda");

        if (debug>0)System.out.println("valorBusqueda="+valorBusqueda);

        //CREARÍA EL AGENTE ANÓNIMO DE USUARIO Y SE REALIZARIA
        //LA BUSQUEDA
        //DESPUES SE DEBERÍA LLAMAR A UN MËTODO DEL VISUALIZADOR QUE CONSTRUYESE LA RESPUESTA
        //A PARTIR DE UN java.util.Vector() de Strings, las cuales contienen URL's


        SuperAgenteAnonimo saa=new SuperAgenteAnonimo();
//        Vector respuestaBusqueda=new Vector();

        //consultamos al SuperAgenteAnónimo la cadena pedida
        resultadoBusqueda=saa.busca(valorBusqueda);

        //le pasamos el resultado al visualizador
        //creamos la infoServlet para entregarla al recurso de visualización
        infoServlet = new InfoServlet(sesion,this.getServletContext(),request,response);

        gestorVisualizador.resultadoBusquedaRapida(valorBusqueda,resultadoBusqueda,infoServlet);
    }
  }

  //Obtener información del servlet
  public String getServletInfo() {
    return "ServleBusquedaRapida BIBA!";
  }
}