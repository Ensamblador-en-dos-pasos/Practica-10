public class Idx {
    /**
     * Resta de binarios para pos o pre incremento
     * 
     * @param bin
     * @return
     */
    public String C2(String bin) {
        String resultado = "";
        boolean ban = true;
        int x = 0;
        x = (bin.length()-1);
        String[] arr = bin.split("");

        while (ban) {
            if(arr[x].equals("0")){
                x--;
            }else{
                x--;
                ban=false;
            }

        }

        for (int i = x; i >= 0; i--) {
            if (arr[i].equals("0")) {
                arr[i] = "1";
            } else {
                arr[i] = "0";
            }
        }

        
        for (int i = 0; i < arr.length; i++) {
            resultado = resultado+ arr[i];

        }

        return resultado;
    }// fin metodo

    /**
     * Resta de binarios para pos o pre incremento
     * @param bin
     * @return
     */
    public String C1_8bits(String bin) {
        String resultado = "";
        int x = 0, y = 7, y1 = 7;
        String[] result = {"0", "0", "0", "0", "0", "0", "0", "0"};
        x = (bin.length()-1);
        String[] arr = bin.split("");

        while(x >= 0){
            result[y] = arr[x];
            y--;
            x--;
        }

        for (int i = y1; i >= 0; i--) {
            if (result[i].equals("0")) {
                result[i] = "1";
            } else {
                result[i] = "0";
            }
        }

        
        for (int i = 0; i < result.length; i++) {
            resultado = resultado + result[i];

        }

        return resultado;
    }// fin metodo

    /**
     * Resta de binarios para pos o pre incremento
     * @param bin
     * @return
     */
    public String C2_8bits(String bin) {
        String resultado = "";
        boolean ban = true;
        int x = 0, y = 7, y1 = 7;
        String[] result = {"0", "0", "0", "0", "0", "0", "0", "0"};
        x = (bin.length()-1);
        String[] arr = bin.split("");

        while(x >= 0){
            result[y] = arr[x];
            y--;
            x--;
        }

        while (ban) {
            if(result[y1].equals("0")){
                y1--;
            }else{
                y1--;
                ban=false;
            }
            
        }
        for (int i = y1; i >= 0; i--) {
            if (result[i].equals("0")) {
                result[i] = "1";
            } else {
                result[i] = "0";
            }
        }

        
        for (int i = 0; i < result.length; i++) {
            resultado = resultado + result[i];

        }

        return resultado;
    }// fin metodo

    /**
     * Complemento a 2 
     * 
     * @param bin
     * @return
     */
    public String C2_16bits(String bin) {
        String resultado = "";
        boolean ban = true;
        int x = 0, y = 15, y1 = 15;
        String[] result = {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"};
        x = (bin.length()-1);
        String[] arr = bin.split("");

        while(x >= 0){
            result[y] = arr[x];
            y--;
            x--;
        }

        while (ban) {
            if(result[y1].equals("0")){
                y1--;
            }else{
                y1--;
                ban=false;
            }
            
        }
        for (int i = y1; i >= 0; i--) {
            if (result[i].equals("0")) {
                result[i] = "1";
            } else {
                result[i] = "0";
            }
        }

        
        for (int i = 0; i < result.length; i++) {
            resultado = resultado + result[i];

        }

        return resultado;
    }// fin metodo
}
