package lp2g37.biblioteca;

import java.util.*;

public class Usuario extends Pessoa {
    
    private String endereco;
    private ArrayList<Emprest> hist;

    public Usuario(String nome, String sobreNome, String dia, String mes, String ano, String CPF, float peso, float altura, String endereco){
        super(nome, sobreNome, dia, mes, ano, CPF, peso, altura);
        this.endereco = endereco;
        this.hist = new ArrayList<Emprest>();
    }

    public void addLivroHist(GregorianCalendar locacao, int codigo){
        Emprest e = new Emprest(locacao, codigo);
        this.hist.add(e);
    }

    public String getEndereco(){
        return this.endereco;
    }

    public int getQtdEmprest(){
        return this.hist.size();
    }

    public int getQtdEmprestAtivos(){
        int qtd = 0;
        for(Emprest emp:hist){
            if(!emp.foiDevolvido()){
                qtd++;
            }
        }
        return qtd;
    }

    public ArrayList<Emprest> getHist(){
        return this.hist;
    }

    public long getCPF() {
        return super.getCPF();
}


    @Override
    public String toString(){
        String historico = "";
        for(Emprest livro : hist){
            historico += livro.toString() + "\n";
        }
        return super.toString() + "\nEndereco: " + this.endereco +
            "\nHistorico:\n " + historico;
    }
    
}
