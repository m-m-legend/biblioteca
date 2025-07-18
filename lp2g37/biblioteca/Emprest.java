package lp2g37.biblioteca;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Emprest implements Serializable {
    
    private GregorianCalendar locacao;
    private GregorianCalendar devol;
    private int codigo;

    public Emprest(GregorianCalendar locacao, int codigo){
        this.locacao = locacao;
        this.codigo = codigo;
        this.devol = null;
    }

    public int getCodigo() throws Exception{
        if(this.codigo > 0){
            return this.codigo;
        } else{
            throw new Exception("Codigo invalido. ");
        }
    }

    public GregorianCalendar getLocacao() throws NullPointerException{
        if(this.locacao != null){
            return this.locacao;
        } else{
            throw new NullPointerException("Data de locacao nao disponivel. ");

        }
    }

    public GregorianCalendar getDevolucao(){
        return this.devol;
    }

    public void setDataDevol(GregorianCalendar data) {
        this.devol = data;
    }

    @Override
    public String toString(){
        String dataLocacaoStr = (this.locacao != null) ?
                String.format("%02d/%02d/%04d", 
                    this.locacao.get(Calendar.DAY_OF_MONTH),
                    this.locacao.get(Calendar.MONTH) + 1,
                    this.locacao.get(Calendar.YEAR)
                    ) :
                    "Nao informado";
        String dataDevolStr = (this.devol != null) ?
                String.format("%02d/%02d/%04d", 
                    this.devol.get(Calendar.DAY_OF_MONTH),
                    this.devol.get(Calendar.MONTH) + 1,
                    this.devol.get(Calendar.YEAR)
                    ) :
                    "Pendente";

        return "  \nCodigo: " + (this.codigo > 0 ? this.codigo : "Nao disponivel") +
        "  \nLocacao: " + dataLocacaoStr +
        "  \nDevolucao: " + dataDevolStr;
    }

    public boolean foiDevolvido(){
        return this.devol != null;
    }

}
