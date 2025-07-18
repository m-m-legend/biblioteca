import lp2g37.biblioteca.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;

public class P4nX {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            Configuracao config = new Configuracao("politica.properties");

            String[] opcoes = { "Carregar biblioteca existente", "Criar nova biblioteca" };
            int escolha = JOptionPane.showOptionDialog(
                null,
                "O que deseja fazer?",
                "Inicialização do Sistema",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
            );

            Biblioteca b;

            if (escolha == 0) {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Selecione o arquivo de USUÁRIOS (u.dat)");
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos .dat", "dat");
                chooser.setFileFilter(filter);
                int resultado1 = chooser.showOpenDialog(null);

                if (resultado1 != JFileChooser.APPROVE_OPTION)
                    throw new Exception("Operação cancelada.");

                File arquivoUsuarios = chooser.getSelectedFile();

                chooser.setDialogTitle("Selecione o arquivo de LIVROS (l.dat)");
                int resultado2 = chooser.showOpenDialog(null);

                if (resultado2 != JFileChooser.APPROVE_OPTION)
                    throw new Exception("Operação cancelada.");

                File arquivoLivros = chooser.getSelectedFile();

                b = new Biblioteca(arquivoUsuarios.getPath(), arquivoLivros.getPath(), config);

            } else {
                b = new Biblioteca(config);
                JOptionPane.showMessageDialog(null, "Nova biblioteca iniciada.");
            }

            new JanelaBiblioteca(b);

        } catch (Exception e) {
            e.printStackTrace(); 
            JOptionPane.showMessageDialog(null, "Erro ao iniciar GUI: " + e.getMessage());
        }
    }
}

