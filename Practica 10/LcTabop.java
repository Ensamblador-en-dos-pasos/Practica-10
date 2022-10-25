import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * @author Isaac Ulises
 * @author Saul
 * @version 1.45
 */
public class LcTabop {
    private static Nodo head = null;
    Operando valOpe = new Operando();
    Conversor conv = new Conversor();
    int Contloc, ContlocEqu = 0;
    int dec = 0, op, op1;
    String dec1 = "", dec2 = "";
    String et, codop, oper;

    /**
     * Método para buscar los valores de la lista en el tabop
     * 
     * @throws IOException
     */
    public void buscar() throws IOException {
        Nodo aux = head;
        if (!clear()) {
            while (aux != null) {
                et = aux.getEtiqueta();
                codop = aux.getCodop();
                oper = aux.getOperando();

                if (et == "") {
                    et = null;
                }
                Scanner sc = null;

                File tmp = new File("TMP.txt");
                File tab = new File("TABSIM.txt");

                PrintWriter TABSIM = null;
                PrintWriter TMP = null;
                boolean ban = false;// Bandera para salir del while
                int ope, bpc = 0, tb, addr2 = 0, bytes = 0, base;// Es 1 0 que indica si lleva o no operador
                String addr, addr1, cmc;

                try {
                    sc = new Scanner(new FileReader("Tabop.txt"));
                    TABSIM = new PrintWriter(new FileWriter(tab, true));
                    TMP = new PrintWriter(new FileWriter(tmp, true));

                    while (sc.hasNextLine()) {// Leer hasta que termine

                        String palabra = sc.next();

                        if (palabra.equals(codop)) {
                            ope = sc.nextInt();

                            switch (ope) {
                                case 1:
                                case 0:// Caso de las directivas del tabop
                                    addr = sc.next();
                                    cmc = sc.next();
                                    bpc = sc.nextInt();
                                    tb = sc.nextInt();

                                    if (addr.equals("Extendido")) {
                                        addr2 = 0;
                                    } else if (addr.equals("REL")) {
                                        addr2 = 1;
                                    }

                                    addr1 = valOpe.valOperando(oper, ope, addr2, bpc, addr);
                                    if (addr1.equals(addr)) {
                                        if (EOpe(ope, oper)) {
                                            TMP.printf("%s\t %s\t %s\t %s\t %s\t %s\t %s\t %s\t", "ContLoc", dec1, et,
                                                    codop, oper, addr, cmc, bpc);
                                            if (et != null) {
                                                TABSIM.printf("%s\t %s\t %s", "ContLoc(Etiqueta-relativa)", et, dec1);
                                                TABSIM.println();
                                            }
                                            // Metodos para sumar el operando y que se cargue a la siguiente direccion
                                            dec = tb + conv.hextodec(dec1);
                                            dec1 = conv.dectohex(dec);
                                            dec1 = dec1.toUpperCase();// Convierte el valor de dec1 a mayuscula
                                            dec1 = conv.ceros(dec1);
                                            TMP.printf("%s", dec1);
                                            TMP.println();
                                            ban = true;
                                        }
                                    }
                                    break;// Break del caso 1 o 0

                                case 2:// Constantes
                                    addr = sc.next();
                                    bytes = sc.nextInt();
                                    switch (bytes) {
                                        case 1:
                                            TMP.printf("%s\t %s\t %s\t %s\t %s\t %s\t %s\t %s\t", "ContLoc", dec1, et, codop,
                                                    oper, addr, bpc, bytes);
                                            if (et != null) {
                                                TABSIM.printf("%s\t %s\t %s\t", "ContLoc(Etiqueta-relativa)", et, dec1);
                                                TABSIM.println();
                                            }
                                            // Metodos para sumar el operando y quese cargue a la siguiente direccion
                                            dec = 1 + conv.hextodec(dec1);
                                            dec1 = conv.dectohex(dec);
                                            dec1 = conv.ceros(dec1);
                                            dec1 = dec1.toUpperCase();// Convierte el valor de dec1 a mayuscula
                                            TMP.printf("%s", dec1);
                                            TMP.println();
                                            ban = true;
                                            break;
                                        case 2:
                                            TMP.printf("%s\t %s\t %s\t %s\t %s\t %s\t %s\t %s\t", "ContLoc", dec1, et, codop,
                                                    oper, addr, bpc, bytes);
                                            if (et != null) {
                                                TABSIM.printf("%s\t %s\t %s\t", "ContLoc(Etiqueta-relativa)", et, dec1);
                                                TABSIM.println();
                                            }
                                            // Metodos para sumar el operando y quese cargue a la siguiente direccion
                                            dec = 2 + conv.hextodec(dec1);
                                            dec1 = conv.dectohex(dec);
                                            dec1 = conv.ceros(dec1);
                                            dec1 = dec1.toUpperCase();// Convierte el valor de dec1 a mayuscula
                                            TMP.printf("%s", dec1);
                                            TMP.println();
                                            ban = true;
                                            break;

                                        default:
                                            break;
                                    }

                                    break;
                                case 3:// Directivas
                                    addr = sc.next();
                                    bytes = sc.nextInt();

                                    if (addr.equals("Dire_Inic")) {

                                        dec1 = valOpe.Val_directivas(oper);// Examina el valor y lo convierte a
                                                                           // hexadecimal
                                        dec1 = conv.ceros(dec1);
                                        TMP.printf("%s\t %s\t %s\t %s\t %s\t %s\t %s\t %s\t %s", "Dir_Inic", dec1, et,
                                                codop, oper, addr, bytes, bpc, dec1);
                                        TMP.println();
                                        ban = true;
                                    } else {
                                        dec1 = conv.ceros(dec1);
                                        TMP.printf("%s\t %s\t %s\t %s\t %s\t %s\t %s\t %s\t %s", "ContLoc", dec1, et,
                                                codop, oper, addr, bytes, bpc, dec1);
                                        TMP.println();
                                        if (et != null) {
                                            TABSIM.printf("%s\t %s\t %s\t %s\t %s\t", "ContLoc(Etiqueta-relativa)", et,
                                                    dec1, addr, bytes);
                                            TABSIM.println();
                                        }
                                        ban = true;
                                    }
                                    break;
                                case 4:// Equate
                                    addr = sc.next();
                                    bytes = sc.nextInt();
                                    // Convertir hexa

                                    // Bases
                                    op = valOpe.basesnum(oper);
                                    switch (op) {
                                        case 1:// Hex
                                            op1 = conv.hextodec(oper);
                                            break;
                                        case 2:// Oct
                                            op1 = conv.octtodec(oper);
                                            break;
                                        case 3:// Bin
                                            op1 = conv.bintodec(oper);
                                            break;
                                        case 4:// Bin
                                            op1 = Integer.parseInt(oper);
                                            break;

                                        default:
                                            break;
                                    }

                                    ContlocEqu = op1;
                                    dec2 = conv.dectohex(ContlocEqu);// Examina el valor y lo convierte a hexadecimal
                                    dec2 = conv.ceros(dec2);
                                    dec2 = dec2.toUpperCase();
                                    TMP.printf("%s\t %s\t %s\t %s\t %s\t %s\t %s\t %s\t %s", "Valor-EQU", dec2, et,
                                            codop, oper, addr, bytes, bpc, dec2);
                                    TMP.println();
                                    TABSIM.printf("%s\t %s\t %s\t", "EQU(Etiqueta-absoluta)", et, dec2);
                                    TABSIM.println();

                                    ban = true;

                                    break;
                                case 5:// FCC
                                    addr = sc.next();
                                    bytes = sc.nextInt();
                                    String result = oper.replaceAll("\s", "&");
                                    result = result.replaceAll("\"", "");
                                    TMP.printf("%s\t %s\t %s\t %s\t %s\t %S\t %s\t %s\t", "ContLoc", dec1, et, codop, result, addr, bytes, bpc);
                                    if (et != null) {
                                        TABSIM.printf("%s\t %s\t %s\t", "ContLoc(Etiqueta-relativa)", et, dec1);
                                        TABSIM.println();
                                    }
                                    // Metodos para sumar el operando y quese cargue a la siguiente direccion
                                    dec = (oper.length() - 2) + conv.hextodec(dec1);
                                    dec1 = conv.dectohex(dec);
                                    dec1 = dec1.toUpperCase();// Convierte el valor de dec1 a mayuscula
                                    dec1 = conv.ceros(dec1);
                                    TMP.printf("%s", dec1);
                                    TMP.println();
                                    ban = true;

                                    break;
                                case 6:// Memoria
                                    addr = sc.next();
                                    bytes = sc.nextInt();
                                    // Conversor de bases

                                    // Multiplicarlo por 1 o 2 dependiendo los bytes
                                    switch (bytes) {
                                        case 1:

                                            TMP.printf("%s\t %s\t %s\t %s\t %s\t %s\t %s\t %s\t", "ContLoc", dec1, et, codop, oper, addr, bytes, bpc);
                                            if (et != null) {
                                                TABSIM.printf("%s\t %s\t %s\t", "ContLoc(Etiqueta-relativa)", et, dec1);
                                                TABSIM.println();
                                            }
                                            // Convertir el operando a hexadecimal
                                            oper = valOpe.Val_directivas(oper);
                                            dec = (conv.hextodec(oper) * 1) + conv.hextodec(dec1);// Suma en y multiplica en decimal
                                            dec1 = conv.dectohex(dec);
                                            dec1 = conv.ceros(dec1);
                                            dec1 = dec1.toUpperCase();
                                            TMP.printf("%s", dec1);
                                            TMP.println();
                                            ban = true;

                                            break;
                                        case 2:
                                            TMP.printf("%s\t %s\t %s\t %s\t %s\t %s\t %s\t %s\t", "ContLoc", dec1, et, codop,
                                                    oper, addr, bytes, bpc);
                                            if (et != null) {
                                                TABSIM.printf("%s\t %s\t %s\t", "ContLoc(Etiqueta-relativa)", et, dec1);
                                                TABSIM.println();
                                            }
                                            // Convertir el operando a hexadecimal
                                            oper = valOpe.Val_directivas(oper);
                                            dec = (conv.hextodec(oper) * 2) + conv.hextodec(dec1);// Suma en y
                                                                                                  // multiplica en
                                                                                                  // decimal
                                            dec1 = conv.dectohex(dec);
                                            dec1 = dec1.toUpperCase();
                                            dec1 = conv.ceros(dec1);
                                            TMP.printf("%s", dec1);
                                            TMP.println();
                                            ban = true;
                                            break;

                                        default:

                                            break;
                                    }
                                    break;
                                default:
                                    break;
                            }

                        }
                    }
                    if (!ban) {
                        System.out.println("Error, CODOP no encontrado " + "[" + codop + "]");
                    }
                } finally {
                    if (sc != null) {
                        sc.close();
                        TMP.close();
                        TABSIM.close();

                    }
                }
                aux = aux.getNext();
            }
        } else {
            System.out.println("No hay instrucciones");
        }
    }

    /**
     * Método para guardar los valores en una lista
     * 
     * @param et
     * @param cp
     * @param op
     */
    public void add(String et, String cp, String op) {
        Nodo nuevo = new Nodo(et, cp, op);

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
     * Metodo para buscar el codigo de operacion que se encuentra en el Tabop
     * 
     * @param nomtabop nombre del archivo
     * @param codop    valor del codigo de operacion
     * @param oper     valor del operando
     * @throws IOException
     */
    public void BuscarCodop(String nomtabop, String et, String codop, String oper) throws IOException {

    }

    /**
     * Metodo que evalua los operandos si requieren o no operando
     * 
     * @param op       valor del operando , puede ser 0 o 1
     * @param operando valor del oprando que puede ser n numero o vacio
     */
    public boolean EOpe(int op, String operando) {
        boolean ret = false;
        switch (op) {
            case 1:
                if (operando == null) {
                    System.out.println(" Error se requiere operando");
                    ret = false;
                } else {
                    ret = true;
                }
                break;
            case 0:
                if (operando != null) {
                    System.out.println(" Error no se requiere operando");
                    ret = false;
                } else {
                    ret = true;
                }
                break;
            default:

                break;
        }
        return ret;
    }

}