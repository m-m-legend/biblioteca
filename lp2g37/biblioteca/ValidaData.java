package lp2g37.biblioteca;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ValidaData {


    public static boolean isDia(String dia){
        try {
            int num = Integer.parseInt(dia);
            return isDia(num);
            
        } catch (NumberFormatException e) {
            return false;
        }

    }
    public static boolean isDia(int dia){
        return (dia>=1 && dia <=31);

    }

    public static boolean isAno(String ano){
        try {
            int num = Integer.parseInt(ano);
            return isAno(num);
            
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isAno(int ano){
        GregorianCalendar cal = new GregorianCalendar();
        int anoCorrente = cal.get(Calendar.YEAR);
        if(ano <=(anoCorrente-120) || ano>anoCorrente){
            return false;
        }
        return true;
    }

    public enum Mes {
        JANEIRO(1),
        FEVEREIRO(2),
        MARCO(3),
        ABRIL(4),
        MAIO(5),
        JUNHO(6),
        JULHO(7),
        AGOSTO(8),
        SETEMBRO(9),
        OUTUBRO(10),
        NOVEMBRO(11),
        DEZEMBRO(12);
    
    private final int numero;

    Mes(int numero){
        this.numero = numero;
    }
    public int getNumero(){
        return numero;
    }
    public static Mes from (String entrada){
        entrada = entrada.trim().toLowerCase();
        //caso numerico
        try {
            int num = Integer.parseInt(entrada);
            if(num >= 1 && num <= 12){
                for(Mes mes: Mes.values()){
                if(mes.getNumero() == num){
                    return mes;
            }
        }
    }
        //caso por extenso
        } catch (NumberFormatException e) {
            for(Mes mes: Mes.values()){
                if(mes.name().toLowerCase().equals(entrada)) return mes;
            }
            
        }
        return null;
    }

}

public static boolean isMes(String entrada){
    return Mes.from(entrada) != null;
}

public static boolean isMes(int numero) {
    return numero >= 1 && numero <= 12;
}

public static boolean isDataValida(String dia, String mes, String ano) {
    if (!isDia(dia) || !isMes(mes) || !isAno(ano)) return false;
    
    int d, a;

    
    try {
        d = Integer.parseInt(dia);
        a = Integer.parseInt(ano);
    } catch (NumberFormatException e) {
        return false;
    }

    
    Mes mesEnum = Mes.from(mes);
    if (mesEnum == null) return false;

    int m = mesEnum.getNumero();

    return isDataValida(d, m, a);
}

public static boolean isDataValida(int dia, int mes, int ano) {
    if (!isDia(dia) || !isMes(mes) || !isAno(ano)) return false;

    switch (mes) {
        case 2:
            if (isAnoBissexto(ano)) return dia <= 29;
            else return dia <= 28;
        case 4: case 6: case 9: case 11:
            return dia <= 30;
        default:
            return dia <= 31;
    }
}


public static boolean isAnoBissexto(int ano) {
    return (ano % 4 == 0 && ano % 100 != 0) || (ano % 400 == 0);
}

}
