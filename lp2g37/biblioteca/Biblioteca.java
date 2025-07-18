package lp2g37.biblioteca;

import java.util.*;
import java.io.*;

public class Biblioteca {
    
    private Configuracao config;
    private Hashtable<Long, Usuario> usuarios;
    private Hashtable<Integer, Livro> livros;

    public Biblioteca(Configuracao config){
        usuarios = new Hashtable<Long, Usuario>();
        livros = new Hashtable<Integer, Livro>();
        this.config = config;
    }

    public Biblioteca(String arqUsuarios, String arqLivros, Configuracao config) throws IOException, ClassNotFoundException{
        usuarios = leArqUsu(arqUsuarios);
        livros = leArqLiv(arqLivros);
        this.config = config;

    }

    @SuppressWarnings("unchecked")
    private Hashtable<Long, Usuario> leArqUsu(String nomeArq) throws IOException, ClassNotFoundException {
        FileInputStream arqEntr = new FileInputStream(nomeArq);
        ObjectInputStream objEntr = new ObjectInputStream(arqEntr);
        Hashtable<Long, Usuario> tabela = (Hashtable<Long, Usuario>) objEntr.readObject();
        objEntr.close();
        return tabela;
}

    @SuppressWarnings("unchecked")
    private Hashtable<Integer, Livro> leArqLiv(String nomeArq) throws IOException, ClassNotFoundException {
        FileInputStream arqEntr = new FileInputStream(nomeArq);
        ObjectInputStream objEntr = new ObjectInputStream(arqEntr);
        Hashtable<Integer, Livro> tabela = (Hashtable<Integer, Livro>) objEntr.readObject();
        objEntr.close();
        return tabela;
    }

    public void salvaArquivo(String nomeArq, Hashtable<?, ?> tabela) throws IOException {
        FileOutputStream arquivoSaida  = new FileOutputStream(nomeArq);
        ObjectOutputStream objetoSaida = new ObjectOutputStream(arquivoSaida);
        objetoSaida.writeObject(tabela);
        objetoSaida.close();

    }

    public void cadastraUsuario(Usuario u) throws UsuarioJaCadastradoEx{
       if(usuarios.containsKey(u.getCPF())){
        throw new UsuarioJaCadastradoEx("Usuario ja cadastrado com este CPF.");
       } else{
        this.usuarios.put(u.getCPF(), u);
       }
    }

    public void cadastraLivro(Livro l) throws LivroJaCadastradoEx{
        if(livros.containsKey(l.getCodigo())){
            throw new LivroJaCadastradoEx("Livro ja cadastrado com este codigo.");
        } else{
            this.livros.put(l.getCodigo(), l);
        }
    }

    public void emprestaLivro(Livro l, Usuario u, GregorianCalendar prazo) throws Exception {
        GregorianCalendar hoje = new GregorianCalendar();

        if (usuarioTemEmprestimoAtrasado(u)) {
        throw new Exception("Usuario possui livro em atraso. Nao pode realizar novo emprestimo.");
    }

        int maxLivros = this.config.getMaxLivrosUsuario();
        int emprestimosAtuais = 0;
        for(Emprest emp : u.getHist()){
            if(emp.getDevolucao() == null){
                emprestimosAtuais++;
            }
        }

        if(emprestimosAtuais >= maxLivros) throw new Exception("Usuario ja possui o numero maximo de emprestimos permitidos (" + maxLivros + ").");

        for (Emprest livro : u.getHist()) {
            if (livro.getCodigo() == l.getCodigo() && livro.getDevolucao() == null) {
                throw new Exception("O usuario ja pegou esse livro emprestado. Emprestimo cancelado.");
            }
        }

        try {
            l.empresta();
        } catch (CopiaNaoDisponivelEx e) {
            throw e;
        }

        l.addUsuarioHist(hoje, prazo, u.getCPF());
        u.addLivroHist(hoje, l.getCodigo());
}

public boolean usuarioTemEmprestimoAtrasado(Usuario u) {
    GregorianCalendar hoje = new GregorianCalendar();

    for (Livro livro : this.livros.values()) { 
        for (EmprestPara ep : livro.getHist()) {
            if (ep.getCPF() == u.getCPF() && ep.getDevolucao() == null) {
                GregorianCalendar prazo = ep.getPrazo();
                if (prazo != null && hoje.after(prazo)) {
                    return true;
                }
            }
        }
    }

    return false;
}



    public void devolveLivro(Livro l, Usuario u) throws Exception {
        l.devolve();

        GregorianCalendar hoje = new GregorianCalendar();

        boolean emprestimoEncontrado = false;
        int diasSemMulta = this.config.getDiasSemMulta();

        for (Emprest livro : u.getHist()) {
            if (livro.getCodigo() == l.getCodigo() && livro.getDevolucao() == null) {
                livro.setDataDevol(hoje);
                emprestimoEncontrado = true;

                for (EmprestPara ep : l.getHist()) {
                    if (ep.getCPF() == u.getCPF() && ep.getDevolucao() == null) {
                        GregorianCalendar prazo = ep.getPrazo(); 

                        if (prazo != null) {
                            long diffMillis = hoje.getTimeInMillis() - prazo.getTimeInMillis();
                            long diasAtraso = diffMillis / (1000 * 60 * 60 * 24);
                            
                            if (diasAtraso > diasSemMulta) {
                                double multa = diasAtraso * 2.5;
                                System.out.println("Atencao: devolucao com " + diasAtraso + " dias de atraso.");
                                System.out.printf("Multa: R$ %.2f%n", multa);
                            }
                        }


                        ep.setDataDevol(hoje);
                        break;
                    }
                }

                break;
            }
        }

        if (!emprestimoEncontrado) {
            throw new Exception("Emprestimo nao encontrado no historico do usuario.");
        }
}


    public String imprimeLivros() {
        ArrayList<Livro> lista = new ArrayList<>(livros.values());
        lista.sort(Comparator.comparingInt(l -> {
            try {
                return l.getCodigo();
            } catch (Exception e) {
                return Integer.MAX_VALUE;
            }
        }));

        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (Livro l : lista) {
            sb.append("Livro " + ++i + "\n-----------------\n");
            sb.append(l.toString()).append("\n-----------------\n");
        }
        return sb.toString();
    }

    public String imprimeUsuarios() {
        ArrayList<Usuario> lista = new ArrayList<>(usuarios.values());
        lista.sort(Comparator.comparingLong(Usuario::getCPF));

        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (Usuario u : lista) {
            sb.append("Usuario " + ++i + "\n-----------------\n");
            sb.append(u.toString()).append("\n-----------------\n");
        }
        return sb.toString();
    }



    public Livro getLivro(int cod) throws LivroNaoCadastradoEx{
        if(!livros.containsKey(cod)){
            throw new LivroNaoCadastradoEx("Livro nao cadastrado com este codigo. ");
        } else{
            return livros.get(cod);
        }
    }

    public Usuario getUsuario(long CPF) throws UsuarioNaoCadastradoEx{
        if(!usuarios.containsKey(CPF)){
            throw new UsuarioNaoCadastradoEx("Usuario nao cadastrado com este CPF. ");
        } else{
            return usuarios.get(CPF);
        }
    }

    public Hashtable<Long, Usuario> getUsuarios() {
        return this.usuarios;
}

    public Hashtable<Integer, Livro> getLivros() {
        return this.livros;
    }

    public Configuracao getConfig(){
        return this.config;
    }



    
    
}
