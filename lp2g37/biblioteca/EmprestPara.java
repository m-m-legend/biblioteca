package lp2g37.biblioteca;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class EmprestPara implements Serializable {
    
    private GregorianCalendar prazo;
    private GregorianCalendar locacao;
    private GregorianCalendar devol;
    private long CPF;

    public EmprestPara(GregorianCalendar locacao, GregorianCalendar prazo, long CPF){
        this.locacao = locacao;
        this.CPF = CPF;
        this.prazo = prazo;
        this.devol = null;
    }

    public long getCPF(){
        return this.CPF;
    }

    public GregorianCalendar getPrazo(){
        return this.prazo;
    }

    public GregorianCalendar getLocacao() throws NullPointerException{
        return this.locacao;
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
        String dataPrazoStr = (this.prazo != null) ?
                String.format("%02d/%02d/%04d",
                    this.prazo.get(Calendar.DAY_OF_MONTH),
                    this.prazo.get(Calendar.MONTH) + 1,
                    this.prazo.get(Calendar.YEAR)
                ) : "Nao informado";
        String dataDevolStr = (this.devol != null) ?
                String.format("%02d/%02d/%04d", 
                    this.devol.get(Calendar.DAY_OF_MONTH),
                    this.devol.get(Calendar.MONTH) + 1,
                    this.devol.get(Calendar.YEAR)
                    ) :
                    "Pendente";

        return "    \nCPF: " + this.CPF +
        "   \nLocacao: " + dataLocacaoStr +
        "   \nDevolucao: " + dataDevolStr +
        "   \nPrazo: " + dataPrazoStr;
    }
    
}
