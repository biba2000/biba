package biba.agentes.sincronizacion;

/**
* clase de los Eventos que se van a depositar en el buzón
*
*/

    public class Event{

           //
           /** variables de clase:
           *
           *  public final static int NO_INICIALIZADO = 0;
           *  public final static int TIME_OUT = 1;
           *  public final static int INFORMACION = 2;
           *
           *
           */
           public final static int NO_INICIALIZADO = 0;
           public final static int TIME_OUT = 1;
           public final static int INFORMACION = 2;
           public final static int BUSQUEDA_FINALIZADA = 3;
           public final static int FILTRAJE_FINALIZADO = 4;

           //atributos
           Object contenido=null;
           int tipoEvento=0;

           /**
           * Constructor de eventos con contenido.
           * @param tipoEvento, posibles tipos: Event.NO_INICIALIZADO
           *                                    Event.TIME_OUT
           *                                    Event.INFORMACION
           * @param contenido, contenido del Evento
           *
           */

           public Event(int tipoEvento,Object contenido){
                  this.contenido=contenido;
                  this.tipoEvento=tipoEvento;
           }


           /**
           * Constructor de eventos sin contenido
           * @param tipoEvento, posibles tipos: Event.NO_INICIALIZADO
           *                                    Event.TIME_OUT
           *                                    Event.INFORMACION
           */
           public Event(int tipoEvento){
                  this.tipoEvento=tipoEvento;
           }
           //métodos

           /**
           * Devuelve el contenido del Evento, null si no contiene Object
           * @return Object,contenido del evento
           *
           */

           public Object getContenido(){return contenido;}

           /**
           * Devuelve el tipo del Evento
           * @return int, tipo del evento, posibles tipos: Event.NO_INICIALIZADO
           *                                               Event.TIME_OUT
           *                                               Event.INFORMACION
           *
           */
           public int getTipoEvento(){return tipoEvento;}
    }