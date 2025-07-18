package lp2g37.biblioteca;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuracao {
    private int maxLivrosUsuario;
    private int diasSemMulta;

    public Configuracao(){
        this.diasSemMulta = 10;
        this.maxLivrosUsuario = 5;
    }

    public Configuracao(String arquivo) throws IOException{
        Properties props = new Properties();
        props.load(new FileInputStream(arquivo));

        this.maxLivrosUsuario = Integer.parseInt(props.getProperty("max.livros.usuario", "3"));
        this.diasSemMulta = Integer.parseInt(props.getProperty("dias.sem.multa", "7"));
    }

    public int getMaxLivrosUsuario() {
        return maxLivrosUsuario;
    }

    public int getDiasSemMulta() {
        return diasSemMulta;
    }
}
