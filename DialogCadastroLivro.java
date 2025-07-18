import javax.swing.*;
import java.awt.*;
import lp2g37.biblioteca.*;

public class DialogCadastroLivro extends JDialog {

    @SuppressWarnings("unused")
    public DialogCadastroLivro(JFrame parent, Biblioteca b) {
        super(parent, "Cadastro de Livro", true);
        setLayout(new GridLayout(6, 2));

        JTextField tfCodigo = new JTextField();
        JTextField tfTitulo = new JTextField();
        JTextField tfCategoria = new JTextField();
        JTextField tfQtdDisp = new JTextField();
        JTextField tfQtdEmprest = new JTextField();

        add(new JLabel("Código:")); add(tfCodigo);
        add(new JLabel("Título:")); add(tfTitulo);
        add(new JLabel("Categoria:")); add(tfCategoria);
        add(new JLabel("Qtd. Disponível:")); add(tfQtdDisp);
        add(new JLabel("Qtd. Emprestado:")); add(tfQtdEmprest);

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnCancelar = new JButton("Cancelar");

        add(btnCadastrar); add(btnCancelar);

        btnCadastrar.addActionListener(e -> {
            try {
                int codigo = Integer.parseInt(tfCodigo.getText().trim());
                String titulo = tfTitulo.getText().trim();
                String categoria = tfCategoria.getText().trim();
                int disp = Integer.parseInt(tfQtdDisp.getText().trim());
                int emp = Integer.parseInt(tfQtdEmprest.getText().trim());

                if (codigo < 1 || codigo > 999) throw new Exception("Código fora do intervalo.");
                if (emp > disp) throw new Exception("Emprestados não pode ser maior que disponíveis.");

                Livro l = new Livro(codigo, titulo, categoria, disp, emp);
                b.cadastraLivro(l);
                JOptionPane.showMessageDialog(this, "Livro cadastrado com sucesso.");
                dispose();
            } catch (LivroJaCadastradoEx ex) {
                JOptionPane.showMessageDialog(this, "Livro já cadastrado.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        btnCancelar.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }
}
