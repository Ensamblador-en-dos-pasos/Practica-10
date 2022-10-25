import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Isaac Ulises
 * @author Saul
 * @version 1.45
 */
public class Validador {

    /**
     * Evalua la catidad de palabras que se repitan en una cadena
     * 
     * @param cadena entrada de texto a evaluar
     * @param valor  valor a buscar
     * @return retorno de las veces que se repitió
     */
    public int Contador_Caracter(String cadena, String valor) {
        int pos = 0, contador = 0;
        pos = cadena.indexOf(valor);
        while (pos != -1) {
            pos = cadena.indexOf(valor, pos + 1);
            contador++;
        }
        return contador;
    }// Fin contador_caracter

    /**
     * Este metodo sirve para evaluar el comnetario
     * 
     * @param cadena entrada de texto
     */
    public boolean valComentario(String cadena) {
        // Creo los evaluadores
        Pattern patron;
        Matcher buscador;

        // Desarrollo mi expresion regular
        patron = Pattern.compile("^;[a-zA-Z0-9]");
        buscador = patron.matcher(cadena);
        boolean encontrado = buscador.find(); // verdadero si la expresion se cumple

        // Evaluo para determinar si es un comentario o no
        // Retorno el valor resultante
        if (encontrado && cadena.length() < 80 && Contador_Caracter(cadena, ";") == 1) {
            return true;
        } else {
            return false;
        }
    }// Fin valcomentario

    /**
     * Metodo para validar la etiqueta
     * 
     * @param cadena entrada de texto
     */
    public String valEtiqueta(String cadena) {

        // Creo los evaluadores
        Pattern patron;
        Matcher buscador;

        // Desarrollo de la expresion regular
        patron = Pattern.compile("^[a-zA-Z]+\\w*+$");
        buscador = patron.matcher(cadena);
        boolean encontrado = buscador.find();// verdadero si la expresion se cumple

        // Evaluo para determinar si es una etiqueta o no
        // Retorno el valor resultante
        if (encontrado && cadena.length() <= 8) {
            return cadena;
        } else {
            return null;
        }
    }// Fin Etiqueta

    /**
     * Metodo para validar el código de operación
     * 
     * @param cadena entrada de texto
     */
    public String valCodop(String cadena) {

        // Creo los evaluadores
        Pattern patron;
        Matcher buscador;

        // Desarrollo de la expresion regular
        patron = Pattern.compile("^[a-zA-Z][.a-zA-Z]*$");
        buscador = patron.matcher(cadena);
        boolean encontrado = buscador.find();// verdadero si la expresion se cumple

        // Evaluo para determinar si es un CODOP o no
        // Retorno el valor resultante
        if (encontrado && cadena.length() <= 5 && Contador_Caracter(cadena, ".") <= 1) {
            return cadena;
        } else {

            return null;
        }
    }// Fin valcodop

}// Fin del class
