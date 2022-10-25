import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Registros_S {
    BcTabSim bt = new BcTabSim();
    Conversor conv = new Conversor();
    Idx coms = new Idx();
    String data, cm, ck, cc, addr, chk, bin;
    int suma, cont, contS1=0, aux, aux1, tamaño, tam, pos;
    String datauni = "", dataaux = "";

    /**
     * Proceso para generar el codigo
     * del registro s0
     * 
     * @param nomArch
     * @throws IOException
     * @throws InterruptedException
     */
    public void S0(String nomArch) throws IOException, InterruptedException {
        /* declaracion de variables y concatenacion de hexadecimales */
        data = bt.asciiS0(nomArch);
        data = data + "0A";
        data = "43 3A 20 " + data;
        aux = (data.length() / 3 + 4);
        suma = aux;
        cc = conv.dectohex(aux);
        cc = conv.ceros9bits(cc);
        /* Sumar los valores en decimal */
        String arr[] = data.split(" ");
        for (int i = 0; i < arr.length; i++) {
            aux1 = conv.hextodec(arr[i]);
            suma = suma + aux1;
        }
        /* Tomar los valores menos significativos del hexadecimal */
        chk = conv.dectohex(suma);
        arr = chk.split("");
        chk = arr[arr.length - 2] + arr[arr.length - 1];
        /* Aplicar complemento a 1 */
        cont = conv.hextodec(chk);
        bin = Integer.toBinaryString(cont);
        bin = coms.C1_8bits(bin);
        aux1 = conv.bintodec(bin);
        ck = conv.dectohex(aux1);
        // Escribir en el documento
        WriteObj("S0", cc.toUpperCase(), "0000", data, ck.toUpperCase());
    }

    /**
     * Proceso para generar el codigo
     * del registro(s) s1
     * 
     * @throws IOException
     * @throws InterruptedException
     */
    public void data(String data, String dir_inic, String addr) throws IOException {
        datauni = datauni + data;
        if (addr.equals("Memoria") || addr.equals("END")) {
            s1(datauni, dir_inic);
            datauni = "";
        } else if (datauni.length() == 32) {
            s1(datauni, dir_inic);
            datauni = "";
        } else if (datauni.length() > 32) {
            String arr[] = datauni.split("");
            datauni = "";
            for (int i = 0; i < arr.length; i = i + 2) {
                if (i < 32) {
                    datauni = datauni + arr[i] + arr[i + 1];
                } else if (i >= 32) {
                    dataaux = dataaux + arr[i] + arr[i + 1];
                }
            }
            s1(datauni, dir_inic);
            datauni = "";
            datauni = dataaux;
            dataaux = "";
        }
    }

    /**
     * Proceso para generar el codigo
     * del registro(s) s1
     * 
     * @throws IOException
     * @throws InterruptedException
     */
    public void s1(String data, String dir_inic) throws IOException {
        if (!data.equals("")) {
            contS1++;
            String arr1[] = data.split("");
            data = "";

            for (int i = 0; i < arr1.length; i = i + 2) {
                data = data + arr1[i] + arr1[i + 1] + " ";
            }

            aux = (data.length() / 3 + 3);
            aux1 = (data.length() / 3);
            if (contS1 > 1) {   
                tamaño = tam;//Retoma el valor del S1
                tamaño = tamaño + aux1;//Le suma las instrucciones para el siguiente S1
                dir_inic = conv.dectohex(tam);//Toma el valor del addr que se calculó antes
                dir_inic = conv.ceros(dir_inic);//LO pasa a una variable para imprimirlo

                tam = tamaño;//Toma el valor para el siguiente S1
            } else if (contS1 == 1) {
                tamaño = conv.hextodec(dir_inic);//Toma el valor del addr del primer S1
                tam = tamaño + aux1;//Guarda el tamaño del siguiente S1
            }

            suma = aux;
            suma = suma + tamaño;
            cc = conv.dectohex(aux);
            cc = conv.ceros9bits(cc);

            /* Sumar los valores en decimal */
            String arr[] = data.split(" ");
            for (int i = 0; i < arr.length; i++) {
                aux1 = conv.hextodec(arr[i]);
                suma = suma + aux1;
            }

            /* Tomar los valores menos significativos del hexadecimal */
            chk = conv.dectohex(suma);
            arr = chk.split("");
            chk = arr[arr.length - 2] + arr[arr.length - 1];
            /* Aplicar complemento a 1 */
            cont = conv.hextodec(chk);
            bin = Integer.toBinaryString(cont);
            bin = coms.C1_8bits(bin);
            aux1 = conv.bintodec(bin);
            ck = conv.dectohex(aux1);
            ck = conv.ceros9bits(ck);

            WriteObj("S1", cc.toUpperCase(), dir_inic.toUpperCase(), data, ck.toUpperCase());
        }

    }

    /**
     * Proceso para generar el codigo
     * del registro s9
     * 
     * @throws IOException
     * @throws InterruptedException
     */
    public void S9() throws IOException, InterruptedException {
        WriteObj("S9", "03", "0000", "", "FC");
    }

    /**
     * @param s
     * @param cc
     * @param addr
     * @param data
     * @param ck
     * @throws IOException
     * @throws InterruptedException
     */
    public void WriteObj(String s, String cc, String addr, String data, String ck) throws IOException {
        File obj = new File("OBJ.txt");
        FileWriter arc = new FileWriter(obj.getAbsolutePath(), true);
        PrintWriter out = null;

        try {// abre el fichero
            out = new PrintWriter(arc);
            out.write(s + " " + cc + " " + addr + " " + data + " " + ck);
            out.println();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        } // Fin de try

    }// Fin de método
}
