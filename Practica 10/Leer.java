import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Isaac Ulises
 * @author Saul
 * @version 1.45
 */
public class Leer {
    private static Nodo head = null;
    // Atributos a utilizar en la clase
    private final String nom1 = "P1ASMtmp.txt";
    private int cont = 1;
    private String codop = null, oper = null, et = null;
    private static Validador val = new Validador();
    private static LcTabop bus = new LcTabop();

    /**
     * Método para leer el archivo
     * 
     * @param nom nombre del archivo
     * @throws FileNotFoundException arroja error archivo no encontrado
     */
    public void leerArc(String nom) throws FileNotFoundException, IOException {
        PrintWriter out = null;

        try {// abre el fichero
            /*Creo variables para los archivos y los elimino para que se puedan sobreescribir 
            en dado caso de que esten creados*/
            File tmp = new File("TMP.txt");
            File tab = new File("TABSIM.txt");
            if (tmp.exists() || tab.exists()) {
                tmp.delete();
                tab.delete();
            }

            // Creo un nuevo arcivo "Temporal" de escritura
            out = new PrintWriter(new FileWriter(nom1));

            // Genero el bufferedReader para la lectura de las lineas
            BufferedReader ej = new BufferedReader(new FileReader(nom));
            String linea;
            // Lee el archivo origial y reemplaza todos los espacios en blanco
            // por comas en el archivo temporal
            while ((linea = ej.readLine()) != null) {
                if (linea.endsWith("\"")|| linea.endsWith("\"&")) {
                    out.write(reemplBlkPlcs(linea));
                    out.println();
                }else{
                    out.write(reemplBlkPlcs(linea.toUpperCase()));
                    out.println();                 
                }
                
            }
        } finally {
            if (out != null) {
                out.close();
            } // Fin del if
        } // Fin de try

    }// Fin de método

    /**
     * Método para leer el archivo temporal
     * 
     * @param nom nombre del archivo
     * @throws FileNotFoundException arroja error archivo no encontrado
     */
    public void Lcc() throws FileNotFoundException, IOException {
        FileReader in = null;
        File archivo = new File(nom1);
        boolean ban = true;
        BufferedReader ej = new BufferedReader(new FileReader(nom1));
        try {// abre el fichero
            String linea;
            while ((linea = ej.readLine()) != null && ban) {
                EvdCasos(linea);
                ++cont;
                if (linea.contains("&END")) {
                    linea = null;
                    ban = false;
                    archivo.delete();
                }
            }
            if (archivo.exists()) {
                archivo.delete();// Elimina el archivo
            }

        } finally {
            if (in != null) {
                in.close();
                ej.close();

            } // Fin del if
        } // Fin de try
    }

    /**
     * En este metodo se busca quitar las comas y evaluar
     * para implementar los valores en un arreglo que va
     * ayudar a determinar la posicion evaluada metiondolos
     * a una lista
     * 
     * @param cadena cadena de caracteres sacada del archivo temporal
     */
    public void EvdCasos(String cadena) throws FileNotFoundException, IOException {
        boolean ver = true;
        et = "";
        codop = "";
        oper = "";
        String[] arr = cadena.split("&");

        // Creo los evaluadores
        Pattern Icomplete;
        Matcher buscador;

        Pattern onlycodop;
        Matcher buscador1;

        Pattern cod_oper;
        Matcher buscador2;

        Pattern et_codop;
        Matcher buscador3;

        Pattern vacio;
        Matcher buscador4;
        
        Pattern fcc;
        Matcher buscador5;

        Pattern fcc1;
        Matcher buscador6;

        // Desarrollo de la expresion regular
        Icomplete = Pattern.compile("^[\\w]+&[\\w.*]+&[\\[]*[#|$|@|%|+|-|,\\w+|-]+[\\]]*&?$");// Instrucción completa
        buscador = Icomplete.matcher(cadena);
        boolean encontrado = buscador.find();// verdadero si la expresion se cumple

        onlycodop = Pattern.compile("^&[\\w.*]+&?$");// Solo CODOP
        buscador1 = onlycodop.matcher(cadena);
        boolean encontrado1 = buscador1.find();

        cod_oper = Pattern.compile("^&[\\w.*]+&[\\[]*[#|$|@|%|+|-|,\\w+|-]+[\\]]*&?$");// CODOP y OPERANDO
        buscador2 = cod_oper.matcher(cadena);
        boolean encontrado2 = buscador2.find();

        et_codop = Pattern.compile("^[\\w]+&[\\w.*]+&?$");// etiqueta y codop
        buscador3 = et_codop.matcher(cadena);
        boolean encontrado3 = buscador3.find();

        vacio = Pattern.compile("^&*$");// Líneas en blanco
        buscador4 = vacio.matcher(cadena);
        boolean encontrado4 = buscador4.find();

        fcc = Pattern.compile("^&[\\w.*]+&[\\[]*[#|$|@|%|+|-|,\\S+\\s*|-]+[\\]]*&?$");//CODOP y operando de FCC
        buscador5 = fcc.matcher(cadena);
        boolean encontrado5 = buscador5.find();

        fcc1 = Pattern.compile("^[\\w]+&[\\w.*]+&[\\[]*[#|$|@|%|+|-|,\\S+\\s*|-]+[\\]]*&?$");//Instrucción completa de FCC
        buscador6 = fcc1.matcher(cadena);
        boolean encontrado6 = buscador6.find();
        int caso = 0;

        // Evaluadores de los casos para el switch
        if (cadena.startsWith(";") || cadena.startsWith("&;")) {// solo comentarios
            caso = 1;
        } else if (encontrado) {
            et = arr[0];
            codop = arr[1];
            oper = arr[2];
            caso = 2;
        } else if (encontrado1) {
            et = null;
            codop = arr[1];
            oper = null;
            caso = 2;
        } else if (encontrado2) {
            et = null;
            codop = arr[1];
            oper = arr[2];
            caso = 2;
        } else if (encontrado3) {
            et = arr[0];
            codop = arr[1];
            oper = null;
            caso = 2;
        } else if(encontrado5) {
            et = null;
            codop = arr[1];
            int cont = 0;
            for(int i = 2; i<arr.length;i++){
                
                if(cont!=0){
                    oper = oper.concat(" ");
                    oper = oper.concat(arr[i]);
                }else{
                    oper = oper.concat(arr[i]);
                    cont++;
                }
                
            }
            caso = 2;
            
        }else if(encontrado6){
            et = arr[0];
            codop = arr[1];
            int cont = 0;
            for(int i = 2; i<arr.length;i++){
                
                if(cont!=0){
                    oper = oper.concat(" ");
                    oper = oper.concat(arr[i]);
                }else{
                    oper = oper.concat(arr[i]);
                    cont++;
                }
                
            }
            caso = 2;
        }
        else {// Error, no hay codop
            caso = 3;
        }

        switch (caso) {
            case 1:// Caso del comentario
                if (val.valComentario(cadena) == true) {
                    System.out.println("COMENTARIO");
                    System.out.println();
                } else {
                    System.out.println("ERROR en el comentario en la línea: " + cont);
                    System.out.println();
                }
                break;

            case 2:
                //Validar etiqueta que sea valida y que no se repita
                if (et != null) {// Validar etiqueta
                    if (buscarConcurrencias(et)) {
                        if (val.valEtiqueta(et) == null) {
                            System.out.println("Error en la etiqueta en la línea: " + cont);
                            ver = false;
                        }
                    } else {
                        System.out.println("Error, la etiqueta " + et + " ya existe");
                        ver = false;
                    }
                }
                //Validar que existan solamente EQU antes que ORG
                if (!codop.contentEquals("EQU") && !codop.contentEquals("ORG")) {
                    if (buscarOrg("ORG")) {
                        System.out.println("Error, no puede haber instrucciones antes del ORG " + "[" + codop + "]");
                        ver = false;
                    }
                }
                
                //Validar que el codop EQU tenga la instrucción completa
                if(codop.contentEquals("EQU")){
                    if(et == null || oper == null){
                        System.out.println("Error, el codigo de operación EQU debe tener etiqueta y operando");
                        ver = false;
                    }
                }
                //Validar ORG
                if (codop.equals("ORG")) {
                    if (oper != null && et == null) {
                        if (!buscarOrg(codop)) {
                            System.out.println("Error, ya existe un ORG");
                            ver = false;
                        }
                    }else{
                        System.out.println("Error, el ORG debe de tener un operando");
                        ver = false;
                    }
                }

                if (val.valCodop(codop) == null) {// Validar codop
                    System.out.println("Error en el código de operación de la línea: " + cont);
                    ver = false;
                }

                if (ver) {// Guardar los valores en la lista
                    codop = codop.toUpperCase();
                    bus.add(et, codop, oper);
                    add(et, codop);
                }
                break;
            
            case 3:// En caso de hacer una línea vacía o que la línea no tenga un operando valido
                if (!encontrado4) {
                    System.out.println("Error en la instrucción de la línea: " + cont + " No existe un CODOP\n");
                }

                break;
            default:
                
                break;
        }

    }// Fin de metodo

    /**
     * Metodo para eliminar los espacios en blanco y ponerles una coma
     * 
     * @param frase linea de texto a evaluarse
     */
    public static String reemplBlkPlcs(String frase) {
        String result = frase.replaceAll("\\s+", "&");
        // System.out.println(result); esto se hacia para comprobar el resultado
        return result;

    }

    /**
     * Método para guardar los valores en una lista
     * 
     * @param et
     * @param cp
     * @param op
     */
    static void add(String et, String cp) {
        Nodo nuevo = new Nodo(et, cp);

        if (clear() == true) {
            head = nuevo;
        } else {
            Nodo anterior = head;
            Nodo actual = anterior;

            while (actual != null) {
                anterior = actual;
                actual = actual.getNext();
            }
            anterior.setNext(nuevo);
        }
    }

    /**
     * Método para verificar cuando la lista está vacía
     * 
     * @return retorno de vacío
     */
    private static boolean clear() {
        return head == null;
    }

    /**
     * Método para buscar las coincidencias de las etiquetas
     */
    public boolean buscarConcurrencias(String et) {
        Nodo aux = head;
        while (aux != null && !aux.getEtiqueta().contentEquals(et)) {
            aux = aux.getNext();
        }
        if (aux == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Método para buscar el codop ORG
     */
    public boolean buscarOrg(String codop) {
        Nodo aux = head;
        while (aux != null && !aux.getCodop().contentEquals(codop)) {
            aux = aux.getNext();
        }
        if (aux == null) {
            return true;
        } else {
            return false;
        }
    }

}