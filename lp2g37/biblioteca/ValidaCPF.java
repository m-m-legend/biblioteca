package lp2g37.biblioteca;

import java.lang.NumberFormatException;

public class ValidaCPF {

    public static boolean isCPF(String CPF){
        if (!(CPF.matches("\\d{11}") ||
        CPF.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}") ||
        CPF.matches("\\d{3}\\.\\d{3}\\.\\d{3}/\\d{2}"))) {
      return false;
  }
        
        CPF = CPF.replaceAll("[^\\d]", "");
        if (CPF.length() != 11) return false;

    
        if (CPF.matches("(\\d)\\1{10}")) return false;
        
        char dig10,dig11;
        int sm,j,r,num,peso;

        try {
            sm = 0;
            peso = 10;
            for(j=0;j<9;j++){
                num = (int) (CPF.charAt(j)-48);
                sm = sm + (num*peso);
                peso--;
            }
            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48);

            sm = 0;
            peso = 11;
            for(j=0;j<10;j++){
                num = (int)(CPF.charAt(j) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                 dig11 = '0';
            else dig11 = (char)(r + 48);

            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                 return(true);
            else return(false);
                } catch (NumberFormatException erro) {
                return(false);
            }
            }

            public static String imprimeCPF(String CPF) {
                CPF = CPF.replaceAll("[^\\d]","");
                if(CPF.length()!=11){
                    throw new IllegalArgumentException("CPF invalido para impressao. ");
                }
                return(CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." +
                CPF.substring(6, 9) + "-" + CPF.substring(9, 11));
            }

            
            public static long toLong(String CPF) {
                if (ValidaCPF.isCPF(CPF)) {
                    String somenteNumeros = CPF.replaceAll("[^\\d]", "");
                    return Long.parseLong(somenteNumeros);
                } else {
                    throw new IllegalArgumentException("CPF invalido.");
                    
                }
            }
            
                }
            
           
    
