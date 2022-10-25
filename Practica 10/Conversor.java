/**
 * Metodo para convertir a las diferentes bases numericas
 * @version 1.45
 */
public class Conversor {
    public int octtodec(String octal){
            String result = octal.replaceAll("\\@", "");
            int num = Integer.parseInt(result,8);
            return num;
    }
    public int hextodec(String hex){
        String result = hex.replaceAll("\\$", "");
        int num = Integer.parseInt(result,16);
        return num;
    }
    public int bintodec(String bin){
        String result = bin.replaceAll("\\%", "");
        int num = Integer.parseInt(result,2);
        return num;
    }
    
    
    //Inmediatos
    public int octtodecimm(String octal){
            String result = octal.replaceAll("\\#", "");
            String result1 = result.replaceAll("\\@", "");
            int num = Integer.parseInt(result1,8);
            return num;
    }
    public int hextodecimm(String hex){
        String result = hex.replaceAll("\\#", "");
        String result1 = result.replaceAll("\\$", "");
        int num = Integer.parseInt(result1,16);
        return num;
    }
    public int bintodecimm(String bin){
        String result = bin.replaceAll("\\#", "");
        String result1 = result.replaceAll("\\%", "");
        int num = Integer.parseInt(result1,2);
        return num;
    }

    //Quitar # en inmediatos con decimal
    public int decimm(String bin){
        String result = bin.replaceAll("\\#", "");
        int num = Integer.parseInt(result);
        return num;
    }

    //Retorna el hex de un decimal
    public String dectohex(int dec){
        String num = Integer.toHexString(dec);
        return num;
    }

    /**
     * Metodo para crear cero dependiendo la longitud del hexadecimal
     * @param valor Cadena en hexadecimal
     * @return El Hexadecimal con los ceros necesarios a la derecha
     */
    public String ceros(String valor) {
        switch (valor.length()) {
            case 1://un solo hexadecimal en la direccion
                valor = "000"+valor;
                break;
                case 2://Dos hexadecimales en la direccion
                valor = "00"+valor;
                break;
                case 3://Tres hexadecimales en la direccion
                valor = "0"+valor;
                break;
            default:
                break;
        }
        return valor;
    }
    /**
     * Metodo para crear cero dependiendo la longitud del hexadecimal
     * @param valor Cadena en hexadecimal
     * @return El Hexadecimal con los ceros necesarios a la derecha
     */
    public String ceros9bits(String valor) {
        switch (valor.length()) {
            case 1://un solo hexadecimal en la direccion
                valor = "0"+valor;
                break;
            default:
                break;
        }
        return valor;
    }

    /**
     * Metodo para crear cero dependiendo la longitud del hexadecimal
     * @param valor Cadena en hexadecimal
     * @return El Hexadecimal con los ceros necesarios a la derecha
     */
    public String cerosF(String valor) {
        switch (valor.length()) {
            case 1://un solo hexadecimal en la direccion
                valor = "FFF"+valor;
                break;
                case 2://Dos hexadecimales en la direccion
                valor = "FF"+valor;
                break;
                case 3://Tres hexadecimales en la direccion
                valor = "F"+valor;
                break;
            default:
                break;
        }
        return valor;
    }
}
