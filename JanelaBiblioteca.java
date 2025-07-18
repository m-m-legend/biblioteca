import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.Dimension;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import lp2g37.biblioteca.*;

public class JanelaBiblioteca extends JFrame {

    private JTable tabelaUsuarios;
    private JTable tabelaLivros;
    private Biblioteca biblioteca; 

    public JanelaBiblioteca(Biblioteca b) {
        super("Sistema da Biblioteca");
        this.biblioteca = b;

        JTabbedPane abas = new JTabbedPane();

        // Abas
        abas.addTab("Usuários", criarTabelaUsuarios(b.getUsuarios()));
        abas.addTab("Livros", criarTabelaLivros(b.getLivros()));
        abas.addTab("Cadastro", criarPainelCadastro(b));
        abas.addTab("Empréstimo", criarPainelEmprestimo(b));
        abas.addTab("Manutenção", criarPainelManutencao(b));
        abas.addTab("Relatório", criarPainelRelatorio(b));

        add(abas);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); 
        setVisible(true);
    }

    private JScrollPane criarTabelaUsuarios(Hashtable<Long, Usuario> usuarios) {
        String[] colunas = { "CPF", "Nome", "Endereço", "Qtd. Empréstimos" };
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

        for (Usuario u : usuarios.values()) {
            Object[] linha = {
                u.getCPF(),
                u.getNome() + " " + u.getSobrenome(),
                u.getEndereco(),
                u.getQtdEmprestAtivos()
            };
            modelo.addRow(linha);
        }

        tabelaUsuarios = new JTable(modelo); 
        tabelaUsuarios.setAutoCreateRowSorter(true);
        return new JScrollPane(tabelaUsuarios);
    }

    private JScrollPane criarTabelaLivros(Hashtable<Integer, Livro> livros) {
        String[] colunas = { "Código", "Título", "Categoria", "Disponíveis", "Emprestados" };
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

        for (Livro l : livros.values()) {
            Object[] linha = {
                l.getCodigo(),
                l.getTitulo(),
                l.getCategoria(),
                l.getQtdDisp(),
                l.getQtdEmprest()
            };
            modelo.addRow(linha);
        }

        tabelaLivros = new JTable(modelo); 
        tabelaLivros.setAutoCreateRowSorter(true);
        return new JScrollPane(tabelaLivros);
    }

    public void atualizarTabelas() {
        
        DefaultTableModel modeloUsuarios = (DefaultTableModel) tabelaUsuarios.getModel();
        modeloUsuarios.setRowCount(0);
        for (Usuario u : biblioteca.getUsuarios().values()) {
            Object[] linha = {
                u.getCPF(),
                u.getNome() + " " + u.getSobrenome(),
                u.getEndereco(),
                u.getQtdEmprestAtivos()
            };
            modeloUsuarios.addRow(linha);
        }

        
        DefaultTableModel modeloLivros = (DefaultTableModel) tabelaLivros.getModel();
        modeloLivros.setRowCount(0);
        for (Livro l : biblioteca.getLivros().values()) {
            Object[] linha = {
                l.getCodigo(),
                l.getTitulo(),
                l.getCategoria(),
                l.getQtdDisp(),
                l.getQtdEmprest()
            };
            modeloLivros.addRow(linha);
        }
    }

    @SuppressWarnings("unused")
    private JPanel criarPainelCadastro(Biblioteca b) {
        JPanel painel = new JPanel();
        JButton btnUsuario = new JButton("Cadastrar Usuário");
        JButton btnLivro = new JButton("Cadastrar Livro");

        btnUsuario.addActionListener(e -> {
            new DialogCadastroUsuario(this, b);
            atualizarTabelas(); 
        });

        btnLivro.addActionListener(e -> {
            new DialogCadastroLivro(this, b);
            atualizarTabelas(); 
        });

        painel.add(btnUsuario);
        painel.add(btnLivro);
        return painel;
    }

    @SuppressWarnings("unused")
    private JPanel criarPainelEmprestimo(Biblioteca b) {
        JPanel painel = new JPanel();
        JButton btnEmprestar = new JButton("Emprestar Livro");
        JButton btnDevolver = new JButton("Devolver Livro");

        btnEmprestar.addActionListener(e -> {
            try {
                String cpf = JOptionPane.showInputDialog(this, "Digite o CPF do usuário:");
                if (cpf == null || cpf.trim().isEmpty()) return;
                if (!ValidaCPF.isCPF(cpf)) throw new Exception("CPF inválido.");
                Usuario u = b.getUsuario(ValidaCPF.toLong(cpf));

                String cod = JOptionPane.showInputDialog(this, "Digite o código do livro:");
                if (cod == null || cod.trim().isEmpty()) return;
                int codigo = Integer.parseInt(cod);
                Livro l = b.getLivro(codigo);

                String data = JOptionPane.showInputDialog(this, "Digite a data de devolução (dd/mm/aaaa):");
                if (data == null || data.trim().isEmpty()) return;
                String[] partes = data.split("/");
                if (partes.length != 3) throw new Exception("Formato de data inválido.");
                int dia = Integer.parseInt(partes[0]);
                int mes = Integer.parseInt(partes[1]) - 1;
                int ano = Integer.parseInt(partes[2]);
                GregorianCalendar prazo = new GregorianCalendar(ano, mes, dia);

                b.emprestaLivro(l, u, prazo);
                JOptionPane.showMessageDialog(this, "Empréstimo realizado com sucesso.");
                atualizarTabelas();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        btnDevolver.addActionListener(e -> {
            try {
                String cpf = JOptionPane.showInputDialog(this, "Digite o CPF do usuário:");
                if (cpf == null || cpf.trim().isEmpty()) return;
                if (!ValidaCPF.isCPF(cpf)) throw new Exception("CPF inválido.");
                Usuario u = b.getUsuario(ValidaCPF.toLong(cpf));

                String cod = JOptionPane.showInputDialog(this, "Digite o código do livro:");
                if (cod == null || cod.trim().isEmpty()) return;
                int codigo = Integer.parseInt(cod);
                Livro l = b.getLivro(codigo);

                b.devolveLivro(l, u);
                JOptionPane.showMessageDialog(this, "Devolução realizada com sucesso.");
                atualizarTabelas();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        painel.add(btnEmprestar);
        painel.add(btnDevolver);
        return painel;
    }

    @SuppressWarnings("unused")
    private JPanel criarPainelManutencao(Biblioteca b) {
        JPanel painel = new JPanel();
        JButton btnSalvarUsuarios = new JButton("Salvar Usuários");
        JButton btnSalvarLivros = new JButton("Salvar Livros");
        JButton btnSalvarAmbos = new JButton("Salvar Ambos");

        btnSalvarUsuarios.addActionListener(e -> {
            String nome = JOptionPane.showInputDialog(this, "Nome do arquivo para salvar os usuários:");
            if (nome != null && !nome.trim().isEmpty()) {
                try {
                    b.salvaArquivo(nome, b.getUsuarios());
                    JOptionPane.showMessageDialog(this, "Usuários salvos com sucesso.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
                }
            }
        });

        btnSalvarLivros.addActionListener(e -> {
            String nome = JOptionPane.showInputDialog(this, "Nome do arquivo para salvar os livros:");
            if (nome != null && !nome.trim().isEmpty()) {
                try {
                    b.salvaArquivo(nome, b.getLivros());
                    JOptionPane.showMessageDialog(this, "Livros salvos com sucesso.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
                }
            }
        });

        btnSalvarAmbos.addActionListener(e -> {
            String nomeU = JOptionPane.showInputDialog(this, "Arquivo para usuários:");
            if (nomeU == null || nomeU.trim().isEmpty()) return;
            String nomeL = JOptionPane.showInputDialog(this, "Arquivo para livros:");
            if (nomeL == null || nomeL.trim().isEmpty()) return;
            try {
                b.salvaArquivo(nomeU, b.getUsuarios());
                b.salvaArquivo(nomeL, b.getLivros());
                JOptionPane.showMessageDialog(this, "Ambos salvos com sucesso.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        painel.add(btnSalvarUsuarios);
        painel.add(btnSalvarLivros);
        painel.add(btnSalvarAmbos);
        return painel;
    }

    private void mostrarTextoRolavel(String titulo, String conteudo) {
        JTextArea areaTexto = new JTextArea(conteudo);
        areaTexto.setEditable(false);
        areaTexto.setLineWrap(true);
        areaTexto.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(areaTexto);
        scroll.setPreferredSize(new Dimension(600, 400));

        JOptionPane.showMessageDialog(this, scroll, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    @SuppressWarnings("unused")
    private JPanel criarPainelRelatorio(Biblioteca b) {
        JPanel painel = new JPanel();
        JButton btnUsuarios = new JButton("Todos os Usuários");
        JButton btnLivros = new JButton("Todos os Livros");
        JButton btnUsuarioEspecifico = new JButton("Usuário Específico");
        JButton btnLivroEspecifico = new JButton("Livro Específico");

        btnUsuarios.addActionListener(e ->
            mostrarTextoRolavel("Todos os Usuários", b.imprimeUsuarios())
        );

        btnLivros.addActionListener(e ->
            mostrarTextoRolavel("Todos os Livros", b.imprimeLivros())
        );

        btnUsuarioEspecifico.addActionListener(e -> {
            String cpf = JOptionPane.showInputDialog(this, "Digite o CPF:");
            if (cpf == null || cpf.trim().isEmpty()) return;
            if (!ValidaCPF.isCPF(cpf)) {
                JOptionPane.showMessageDialog(this, "CPF inválido.");
                return;
            }
            try {
                Usuario u = b.getUsuario(ValidaCPF.toLong(cpf));
                JOptionPane.showMessageDialog(this, u.toString());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        btnLivroEspecifico.addActionListener(e -> {
            String cod = JOptionPane.showInputDialog(this, "Digite o código do livro:");
            if (cod == null || cod.trim().isEmpty()) return;
            try {
                int codigo = Integer.parseInt(cod);
                Livro l = b.getLivro(codigo);
                JOptionPane.showMessageDialog(this, l.toString());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        painel.add(btnUsuarios);
        painel.add(btnLivros);
        painel.add(btnUsuarioEspecifico);
        painel.add(btnLivroEspecifico);
        return painel;
    }
}


