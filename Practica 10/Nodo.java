/**
 * @author Isaac Ulises
 * @author Saul
 * @version 1.45
 */
public class Nodo {
    private String etiqueta, codop, operando;
    private Nodo next;//Puntero del siguiente nodo
    /** Constructor para los valores del ensamblador
     * más el nodo siguiente
      * 
      * @param et valor etiqueta
      * @param codop valor codigo de operacion
      * @param oper valor operando
      * @param next siguiente nodo
      */
    public Nodo(String et, String codop, String oper, Nodo next){
        this.etiqueta = et;
        this.codop = codop;
        this.operando = oper;
        this.next =next;
    }
    /**
     * Constructor para los valores del ensamblador
     * @param et etiqueta
     * @param codop codigo de operacion
     * @param oper operando
     */
    public Nodo(String et, String codop, String oper){
        this.etiqueta = et;
        this.codop = codop;
        this.operando = oper;
        this.next = null;
    }

    /**
     * Constructor para los valores del ensamblador
     * @param et etiqueta
     * @param codop codigo de operacion
     */
    public Nodo(String et, String codop){
        this.etiqueta = et;
        this.codop = codop;
        this.next = null;
    }

    /* Getters y Setters de et, codop y oper */
    public String getEtiqueta(){
        if(etiqueta == null){
            return "";
        }
        return etiqueta;
    }

    public String getCodop(){
        return codop;
    }
    
    public String getOperando(){
        return operando;
    }

    public void setEtiqueta(String et){
        this.etiqueta = et;
    } 
    
    public void setCodop(String cdp){
        this.codop = cdp;
    }

    public void setOperando(String op){
        this.operando = op;
    }

    public Nodo getNext(){
        return next;
    }

    public void setNext(Nodo next){
        this.next = next;
    }
}
