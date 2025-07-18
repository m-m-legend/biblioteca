package lp2g37.biblioteca;

import java.io.Serializable;
import java.util.*;



public class Pessoa implements Serializable {
    private String nome;
    private String sobreNome;
    private GregorianCalendar dataNasc;
    private long numCPF;
    private float altura;
    private float peso;

    private static int qtdPessoas = 0;
    private boolean combinacaoAvisada = false;

    private static void contaPessoa(){
        qtdPessoas++;
    }

    public Pessoa(String nome, String sobreNome, String dia, String mes, String ano){
        setNome(nome);
        setSobrenome(sobreNome);
        setDataNasc(dia,mes,ano);
        contaPessoa();
    }

    public Pessoa(String nome, String sobreNome, String dia, String mes, String ano, String CPF, float peso, float altura){
        setNome(nome);
        setSobrenome(sobreNome);
        setDataNasc(dia,mes,ano);
        setCPF(CPF);
        setPeso(peso);
        setAltura(altura);
        contaPessoa();
    }

        public static int getQtdPessoas(){return qtdPessoas;}
        public String getNome(){return nome;}
        public String getSobrenome(){return sobreNome;}
        public float getPeso(){return peso;}
        public float getAltura(){return altura;}
        public GregorianCalendar getDataNasc(){return dataNasc;}
        public long getCPF(){return numCPF;}

        public void setNome(String nome) {
            if (nome == null || !nome.matches("[A-Za-zÀ-ÿ\\s]+")) {
                throw new IllegalArgumentException("Nome invalido. Use apenas letras e espaços.");
            }
            this.nome = nome.trim();
            }

        public void setSobrenome(String sobreNome){
                if(sobreNome == null || !sobreNome.matches("[A-Za-zÀ-ÿ\\s]+")){
                    throw new IllegalArgumentException("Sobrenome invalido. Use apenas letras e espaços. ");
                }
                this.sobreNome = sobreNome.trim();
        }
        private void validarCombinacao() {
        if (this.peso != 0 && this.altura != 0) {
                double imc = this.peso / (this.altura * this.altura);
                if ((imc < 13 || imc > 60) && !combinacaoAvisada) {
                System.out.println("-----> Combinacao de peso e altura incoerente (IMC fora do intervalo realista). <-----");
                combinacaoAvisada = true;
                        }
                }
        }
        public void setPeso(float peso) {
                if (peso <20 || peso >300) {
                        throw new IllegalArgumentException("Peso invalido. ");
                }
                else{
                        this.peso = peso;
                        validarCombinacao();
                        
                        
                        
                }
            }
            
        public void setAltura(float altura) {
        if (altura < 0.5 || altura > 2.5) {
                throw new IllegalArgumentException("Altura invalida. ");
        } else {
                this.altura = altura;
                validarCombinacao();
                
        }
    }

        public void setCPF(String CPF){
            this.numCPF = ValidaCPF.toLong(CPF);
        }

        public void setDataNasc(String dia, String mes, String ano){
            if(!ValidaData.isDataValida(dia, mes, ano)){
                throw new IllegalArgumentException("Data invalida.");
            }

                ValidaData.Mes mesEnum = ValidaData.Mes.from(mes);
                if(mesEnum == null){
                    throw new IllegalArgumentException("Mes invalido: " + mes);
                }
                int d = Integer.parseInt(dia);
                int a = Integer.parseInt(ano);
                int m = mesEnum.getNumero() - 1;
                
                this.dataNasc = new GregorianCalendar(a,m,d);

            }
        private int idade() {
                if (this.dataNasc == null) {
                        System.err.println("Data de nascimento nao informada. ");
                        return -1;
                }
                GregorianCalendar hoje = new GregorianCalendar();
                int anoAtual = hoje.get(Calendar.YEAR);
                int mesAtual = hoje.get(Calendar.MONTH);
                int diaAtual = hoje.get(Calendar.DAY_OF_MONTH);

                int anoNasc = this.dataNasc.get(Calendar.YEAR);
                int mesNasc = this.dataNasc.get(Calendar.MONTH);
                int diaNasc  = this.dataNasc.get(Calendar.DAY_OF_MONTH);

                int idade = anoAtual - anoNasc;
                

                if (mesAtual < mesNasc || (mesAtual == mesNasc && diaAtual < diaNasc)) {
                    idade--;
        }

                return (idade >=1 && idade <=120) ? idade : -1;

          	
        } 
        
        @Override
        public String toString() {
        String CPFformat;
        try {
            String CPFstr = String.format("%011d", this.numCPF);
            CPFformat = ValidaCPF.imprimeCPF(CPFstr);
        } catch(IllegalArgumentException e) {
            CPFformat = "Invalido";
        }
        int idadeCalculada = this.idade();
        String dataNascStr = (dataNasc != null) ?
            String.format("%02d/%02d/%04d",
                dataNasc.get(Calendar.DAY_OF_MONTH),
                dataNasc.get(Calendar.MONTH) + 1,
                dataNasc.get(Calendar.YEAR))
                : "Não informada";

        return "\nNome: " + nome + " " + sobreNome +
        "\nIdade: " + (idadeCalculada != -1 ? idadeCalculada : "Idade nao disponivel") +
        "\nPeso: " + (peso > 0 ? peso : "Nao informado") +
        "\nAltura: " + (altura > 0 ? altura : "Nao informado") +
        "\nData de Nascimento: " + dataNascStr +
        "\nCPF: " + CPFformat;
        }
        }



    




