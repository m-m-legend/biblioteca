import javax.swing.*;
import java.awt.*;
import lp2g37.biblioteca.*;

public class DialogCadastroUsuario extends JDialog {

    @SuppressWarnings("unused")
    public DialogCadastroUsuario(JFrame parent, Biblioteca b) {
        super(parent, "Cadastro de Usuário", true);
        setLayout(new GridLayout(10, 2));

        JTextField tfNome = new JTextField();
        JTextField tfSobrenome = new JTextField();
        JTextField tfDia = new JTextField();
        JTextField tfMes = new JTextField();
        JTextField tfAno = new JTextField();
        JTextField tfCPF = new JTextField();
        JTextField tfPeso = new JTextField();
        JTextField tfAltura = new JTextField();
        JTextField tfEndereco = new JTextField();

        add(new JLabel("Nome:")); add(tfNome);
        add(new JLabel("Sobrenome:")); add(tfSobrenome);
        add(new JLabel("Dia:")); add(tfDia);
        add(new JLabel("Mês:")); add(tfMes);
        add(new JLabel("Ano:")); add(tfAno);
        add(new JLabel("CPF:")); add(tfCPF);
        add(new JLabel("Peso:")); add(tfPeso);
        add(new JLabel("Altura:")); add(tfAltura);
        add(new JLabel("Endereço:")); add(tfEndereco);

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnCancelar = new JButton("Cancelar");

        add(btnCadastrar); add(btnCancelar);

        btnCadastrar.addActionListener(e -> {
            try {
                String nome = tfNome.getText().trim();
                String sobrenome = tfSobrenome.getText().trim();
                String dia = tfDia.getText().trim();
                String mes = tfMes.getText().trim();
                String ano = tfAno.getText().trim();
                String cpf = tfCPF.getText().trim();
                float peso = Float.parseFloat(tfPeso.getText().trim());
                float altura = Float.parseFloat(tfAltura.getText().trim());
                String endereco = tfEndereco.getText().trim();

                if (!ValidaCPF.isCPF(cpf)) throw new Exception("CPF inválido.");
                if (!ValidaData.isDataValida(dia, mes, ano)) throw new Exception("Data inválida.");

                Usuario u = new Usuario(nome, sobrenome, dia, mes, ano, cpf, peso, altura, endereco);
                b.cadastraUsuario(u);
                JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso.");
                dispose();
            } catch (UsuarioJaCadastradoEx ex) {
                JOptionPane.showMessageDialog(this, "Usuário já cadastrado.");
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
