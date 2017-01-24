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

    // Obtenemos la ruta donde se deja la salida est�ndar
    String ficheroOut = biba.persistencia.propiedades.getPropiedad("FICHERO_SALIDA_SERVLET_BUSQUEDA_RAPIDA");


    // Llevamos la salida est�ndar y la salida error a fichero.
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


  //Procesar una petici�n HTTP Post
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    //par�metros que recogemos del formulario que env�a el usuario
    String valorBusqueda="";
    String action ="";

    //infoServlet contendr� el contexto del Servlet y la peticion del usuario
    //necesario para comunicar el servlet con el recurso de visualizaci�n
    InfoServlet infoServlet = null;


    //Vector que contendra la lisat de Strings, con las URL's  del resultado de la b�squeda
    Vector resultadoBusqueda =null;

    // gestor del recurso de visualizaci�n
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
       //creamos la infoServlet para entregarla al recurso de visualizaci�n
       infoServlet = new InfoServlet(sesion,this.getServletContext(),request,response);
       gestorVisualizador.error("No se ha especificado una acci�n a realizar desde"+
                                 " el formulario de b�squeda r�pida",
                                 infoServlet);
       if (debug>0)System.out.println("action==null");
    }

    if (debug>0)System.out.println("action!=null");

    boolean accionCorrecta = "busquedaRapida".equals(action);


    if(!accionCorrecta){
        if (debug>0)System.out.println("action no correcta");
       //creamos la infoServlet para entregarla al recurso de visualizaci�n
       infoServlet = new InfoServlet(sesion,this.getServletContext(),request,response);
       gestorVisualizador.error("No se ha especificado una acci�n correcta a realizar desde"+
                                 " el formulario de b�squeda r�pida",
                                 infoServlet);

    }


    if("busquedaRapida".equals(action)){
        if (debug>0)System.out.println("action ==busquedaRapida");

        //cojemos el valor de la busqueda
        valorBusqueda = request.getParameter("valorBusqueda");

        if (debug>0)System.out.println("valorBusqueda="+valorBusqueda);

        //CREAR�A EL AGENTE AN�NIMO DE USUARIO Y SE REALIZARIA
        //LA BUSQUEDA
        //DESPUES SE DEBER�A LLAMAR A UN M�TODO DEL VISUALIZADOR QUE CONSTRUYESE LA RESPUESTA
        //A PARTIR DE UN java.util.Vector() de Strings, las cuales contienen URL's


        SuperAgenteAnonimo saa=new SuperAgenteAnonimo();
//        Vector respuestaBusqueda=new Vector();

        //consultamos al SuperAgenteAn�nimo la cadena pedida
        resultadoBusqueda=saa.busca(valorBusqueda);

        //le pasamos el resultado al visualizador
        //creamos la infoServlet para entregarla al recurso de visualizaci�n
        infoServlet = new InfoServlet(sesion,this.getServletContext(),request,response);

        gestorVisualizador.resultadoBusquedaRapida(valorBusqueda,resultadoBusqueda,infoServlet);
    }
  }

  //Obtener informaci�n del servlet
  public String getServletInfo() {
    return "ServleBusquedaRapida BIBA!";
  }
}