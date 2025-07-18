package lp2g37.biblioteca;

import java.io.Serializable;
import java.util.*;

public class Livro implements Serializable {
    
    private int codigo;
    private String titulo;
    private String categoria;
    private int qtdDispon;
    private int qtdEmprest;

    private ArrayList<EmprestPara> hist;

    public Livro(int codigo, String titulo, String categoria, int qtdDispon, int qtdEmprest){
        this.codigo = codigo;
        this.titulo = titulo;
        this.categoria = categoria;
        this.qtdDispon = qtdDispon;
        setQtdEmprestados(qtdEmprest);
        this.hist = new ArrayList<EmprestPara>();
    }

    public void empresta() throws CopiaNaoDisponivelEx{
        if(this.qtdDispon - this.qtdEmprest == 0){
            throw new CopiaNaoDisponivelEx("Livro nao disponivel para emprestimo.");
        } else{
            this.qtdEmprest++;
        }

    }

    public void devolve() throws NenhumaCopiaEmprestadaEx{
        if(this.qtdEmprest == 0){
            throw new NenhumaCopiaEmprestadaEx("Nenhuma copia do livro foi emprestada.");
        } else{
            this.qtdEmprest--;
        }
    }

    private void setQtdEmprestados(int qtdEmprest){
        if(qtdEmprest > qtdDispon){
            this.qtdEmprest = 0;
        } else{
            this.qtdEmprest = qtdEmprest;
        }
    }

    public void addUsuarioHist(GregorianCalendar locacao, GregorianCalendar prazo, long CPF){
        this.hist.add(new EmprestPara(locacao, prazo, CPF));

    }

    public int getCodigo(){
        if(this.codigo >= 0 && this.codigo <= 999){
            return this.codigo;
        } else{
            return -1;
        }
    }

    public String getTitulo(){
        return this.titulo;
    }

    public String getCategoria(){
        return this.categoria;
    }

    public int getQtdDisp(){
        if(this.qtdDispon > 0){
            return this.qtdDispon;
        } else{
            return 0;
        }
    }

    public int getQtdEmprest(){
        if(this.qtdEmprest >= 0){
            return this.qtdEmprest;
        } else{
            return 0;
        }
    }

    public ArrayList<EmprestPara> getHist(){
       return this.hist;
    }

    @Override
    public String toString(){
        String historico = "";
        for(EmprestPara usuario : hist){
            historico += usuario.toString() + "\n";
        }
        return "\nCodigo: " + (this.codigo > 0 ? this.codigo : "Nao disponivel") +
        "\nTitulo: " + this.titulo +
        "\nCategoria: " + this.categoria +
        "\nQuantidade Total: " + getQtdDisp() +
        "\nQuantidade Emprestada: " + getQtdEmprest() +
        "\nHistorico:\n " + historico;
    }

    

}
