import lp2g37.biblioteca.*;
import java.io.IOException;
import java.util.*;

public class P3nX{

    public static void main(String[] args){
        Configuracao config = null;
        try{
            config = new Configuracao("politica.properties");
        } catch(IOException e){
            System.out.println("Falha ao carregar configuracao. Usando valores padrao.");
            config = new Configuracao();
        }
        Scanner teclado = new Scanner(System.in);
        Biblioteca b = menu(teclado, config);
        principal(b, teclado);
    }

    public static void principal(Biblioteca b, Scanner teclado){
        if(b != null){
            while(true){
                System.out.println("---| MENU PRINCIPAL |---");
                System.out.println("1. Cadastro");
                System.out.println("2. Manutencao");
                System.out.println("3. Emprestimo");
                System.out.println("4. Relatorio");
                System.out.println("5. Sair da Biblioteca");
                System.out.println("Escolha uma opcao: ");

                int op;
                if(teclado.hasNextInt()){
                    op = teclado.nextInt();
                    teclado.nextLine();
                    switch (op) {
                        case 1: cadastro(teclado, b); break;
                        case 2: manutencao(teclado, b); break;
                        case 3: emprestimo(teclado, b); break;
                        case 4: relatorio(teclado, b); break;
                        case 5: return;
                        default: System.out.println("Opcao invalida."); break;
                    }
                } else{
                    teclado.nextLine();
                    continue;
                }
            }
        } else{
            return;
        }

    }
    
    
    
    public static Biblioteca menu(Scanner teclado, Configuracao config){
        int escolha;
        while(true){
            System.out.println("---| BIBLIOTECA |---");
            System.out.println("1. Cadastro zerado");
            System.out.println("2. Ler arquivos");
            System.out.println("3. Sair");
            System.out.println("\nEscolha uma opcao:");
            if(teclado.hasNextInt()){
                escolha = teclado.nextInt();
                if(escolha == 1){
                    Biblioteca b = new Biblioteca(config);
                    return b;
                } else if(escolha == 2){
                    teclado.nextLine();
                    while(true){
                        System.out.println("Digite o nome do arquivo de usuarios (ou ENTER para voltar): ");
                        String arqUsuarios = teclado.nextLine();
                        if(arqUsuarios.equals("")){
                            break;
                        }
                        System.out.println("Digite o nome do arquivo de livros (ou ENTER para voltar): ");
                        String arqLivros = teclado.nextLine();
                        if(arqLivros.equals("")){
                            break;
                        }
                        try {
                            Biblioteca b = new Biblioteca(arqUsuarios, arqLivros, config);
                            return b;
                        } catch (Exception e) {
                            System.out.println("Nomes invalidos. ");
                            continue;
                        }
                    }   
                } else if(escolha == 3){
                    System.out.println("Encerrando...\n\n");
                    return null;
                } else{
                    teclado.nextLine();
                    continue;
                }
            } else{
                teclado.nextLine();
                continue;
            }

            

        }
    }

    public static void emprestimo(Scanner teclado, Biblioteca b) {
        while (true) {
            System.out.println("---| EMPRESTIMO |---");
            System.out.println("1. Emprestimo de livro");
            System.out.println("2. Devolucao de livro");
            System.out.println("3. Voltar");
            System.out.println("Escolha uma opcao: ");

            int op;
            if (teclado.hasNextInt()) {
                op = teclado.nextInt();
                teclado.nextLine();

                if (op == 1) {
                    Usuario u = null;
                    while (true) {
                        System.out.println("Digite o CPF do usuario (ou ENTER para voltar): ");
                        String cpfStr = teclado.nextLine();
                        if (cpfStr.equals("")) break;
                        if (!ValidaCPF.isCPF(cpfStr)) {
                            System.out.println("CPF invalido.");
                            continue;
                        }
                        try {
                            u = b.getUsuario(ValidaCPF.toLong(cpfStr));
                            break;
                        } catch (UsuarioNaoCadastradoEx e) {
                            System.out.println("Usuario nao cadastrado.");
                        }
                    }
                    if (u == null) continue;

                    Livro l = null;
                    while (true) {
                        System.out.println("Digite o codigo do livro (ou ENTER para voltar): ");
                        String codStr = teclado.nextLine();
                        if (codStr.equals("")) break;
                        try {
                            int cod = Integer.parseInt(codStr);
                            l = b.getLivro(cod);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Codigo deve ser inteiro.");
                        } catch (LivroNaoCadastradoEx e) {
                            System.out.println("Livro nao cadastrado.");
                        }
                    }
                    if (l == null) continue;

                    GregorianCalendar prazo = null;
                    while (true) {
                        System.out.println("Digite a data limite para devolucao (dd/mm/aaaa) ou ENTER para voltar:");
                        String data = teclado.nextLine();
                        if (data.equals("")) break;

                        String[] partes = data.split("/");
                        if (partes.length != 3) {
                            System.out.println("Formato invalido.");
                            continue;
                        }

                        try {
                            int dia = Integer.parseInt(partes[0]);
                            int mes = Integer.parseInt(partes[1]) - 1;
                            int ano = Integer.parseInt(partes[2]);
                            prazo = new GregorianCalendar(ano, mes, dia);
                            break;
                        } catch (Exception e) {
                            System.out.println("Data invalida.");
                        }
                    }
                    if (prazo == null) continue;

                    try {
                        b.emprestaLivro(l, u, prazo);
                        System.out.println("Emprestimo realizado com sucesso.");
                        System.out.println("Livro atualizado:\n" + l);
                        System.out.println("Usuario atualizado:\n" + u);
                    } catch (Exception e) {
                        System.out.println("Erro no emprestimo: " + e.getMessage());
                    }

                } else if (op == 2) {
                    Usuario u = null;
                    while (true) {
                        System.out.println("Digite o CPF do usuario (ou ENTER para voltar): ");
                        String cpfStr = teclado.nextLine();
                        if (cpfStr.equals("")) break;
                        if (!ValidaCPF.isCPF(cpfStr)) {
                            System.out.println("CPF invalido.");
                            continue;
                        }
                        try {
                            u = b.getUsuario(ValidaCPF.toLong(cpfStr));
                            break;
                        } catch (UsuarioNaoCadastradoEx e) {
                            System.out.println("Usuario nao cadastrado.");
                        }
                    }
                    if (u == null) continue;

                    Livro l = null;
                    while (true) {
                        System.out.println("Digite o codigo do livro (ou ENTER para voltar): ");
                        String codStr = teclado.nextLine();
                        if (codStr.equals("")) break;
                        try {
                            int cod = Integer.parseInt(codStr);
                            l = b.getLivro(cod);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Codigo deve ser inteiro.");
                        } catch (LivroNaoCadastradoEx e) {
                            System.out.println("Livro nao cadastrado.");
                        }
                    }
                    if (l == null) continue;

                    try {
                        b.devolveLivro(l, u); 
                        System.out.println("Devolucao realizada com sucesso.");
                        System.out.println("Livro atualizado:\n" + l);
                        System.out.println("\n\nUsuario atualizado:\n" + u);
                    } catch (Exception e) {
                        System.out.println("Erro na devolucao: " + e.getMessage());
                    }

                } else if (op == 3) {
                    return;
                } else {
                    System.out.println("Opcao invalida.");
                }
            } else {
                System.out.println("Entrada invalida.");
                teclado.nextLine();
            }
        }
}



    public static void manutencao(Scanner teclado, Biblioteca b){
        while(true){
            System.out.println("---| MANUTENCAO |---");
            System.out.println("1. Salvar usuarios");
            System.out.println("2. Salvar livros");
            System.out.println("3. Salvar ambos");
            System.out.println("4. Voltar");
            System.out.println("Escolha uma opcao: ");
            int op;
            if(teclado.hasNextInt()){
                op = teclado.nextInt();
                teclado.nextLine();
            
                try {
                    if(op == 1){
                        while(true){
                            System.out.println("Digite o nome do arquivo para salvar os usuarios (ou ENTER para voltar): ");
                            String nomeUsuarios = teclado.nextLine();
                            if(nomeUsuarios.equals("")) break;
                            b.salvaArquivo(nomeUsuarios, b.getUsuarios());
                            System.out.println("Usuarios salvos com sucesso.");
                            break;
                        }
                    } else if(op == 2){
                        while(true){
                            System.out.println("Digite o nome do arquivo para salvar os livros (ou ENTER para voltar): ");
                            String nomeLivros = teclado.nextLine();
                            if(nomeLivros.equals("")) break;
                            b.salvaArquivo(nomeLivros, b.getLivros());
                            System.out.println("Livros salvos com sucesso.");
                            break;
                        }
                    } else if(op == 3){
                        while(true){
                            System.out.print("Digite o nome do arquivo para salvar os usuarios (ou ENTER para voltar): ");
                            String nomeUsuarios = teclado.nextLine();
                            if(nomeUsuarios.equals("")) break;
                            System.out.print("Digite o nome do arquivo para salvar os livros (ou ENTER para voltar): ");
                            String nomeLivros = teclado.nextLine();
                            if(nomeLivros.equals("")) break;
                            b.salvaArquivo(nomeUsuarios, b.getUsuarios());
                            b.salvaArquivo(nomeLivros, b.getLivros());
                            System.out.println("Usuarios e livros salvos com sucesso!");
                            break;
                        }
                    } else if(op == 4){
                        return;
                    } else{
                        System.out.println("Opcao invalida.");
                    }
            } catch (IOException e) {
                System.out.println("Erro ao salvar arquivo: " + e.getMessage());

            }
        
            

        } else {
            System.out.println("Entrada invalida. ");
            teclado.nextLine();
            }

        }
    }

    public static void cadastro(Scanner teclado, Biblioteca b){
        int escolha;
        while(true){
            System.out.println("---| CADASTRO |---");
            System.out.println("1. Cadastro de Livro");
            System.out.println("2. Cadastro de Usuario");
            System.out.println("3. Voltar");
            System.out.println("\nEscolha uma opcao:");
            if(teclado.hasNextInt()){
                escolha = teclado.nextInt();
                teclado.nextLine();
                if(escolha == 1){
                    Livro l = infoLivro(teclado);
                    if(l == null){
                        System.out.println("Cadastro de livro cancelado.");
                        continue;
                    } 
                    try {
                        b.cadastraLivro(l);
                    } catch (LivroJaCadastradoEx e) {
                        System.out.println("Livro com codigo ja cadastrado.");
                        continue;
                    }
                } else if(escolha == 2){
                    Usuario u = infoUsuario(teclado);
                    if(u == null){
                        System.out.println("Cadastro de usuario cancelado. ");
                        continue;
                    } 
                    try {
                        b.cadastraUsuario(u);
                    } catch(UsuarioJaCadastradoEx e){
                        System.out.println("Usuario ja cadastrado com este CPF.");
                        continue;
                    }

                } else if(escolha == 3){
                    return;
                } else{
                    System.out.println("Opcao invalida.");
                    continue;
                }
            } else{
                System.out.println("Entrada invalida. ");
                teclado.nextLine();
                continue;
            }
        }
    }

    public static void relatorio(Scanner teclado, Biblioteca b){
        while(true){
            System.out.println("---| RELATORIO |---");
            System.out.println("1. Acervo de livros");
            System.out.println("2. Cadastro de usuarios");
            System.out.println("3. Usuario especifico");
            System.out.println("4. Livro especifico");
            System.out.println("5. Voltar");
            System.out.println("Escolha uma opcao: ");
            
            int op;
            if(teclado.hasNextInt()){
                op = teclado.nextInt();
                teclado.nextLine();
                if(op == 1){
                    System.out.println("\n" + b.imprimeLivros());
                    break;
                } else if(op == 2){
                    System.out.println("\n" + b.imprimeUsuarios());
                    break;
                } else if(op == 3){
                    while(true){
                        System.out.println("Digite o CPF do usuario (ou ENTER para voltar): ");
                        String CPF = teclado.nextLine();
                        if(CPF.equals("")){
                            break;
                        } else if(ValidaCPF.isCPF(CPF)){
                            try{
                                Usuario u = b.getUsuario(ValidaCPF.toLong(CPF));
                                System.out.println("\n"+ u + "\n");
                                break;
                            } catch(UsuarioNaoCadastradoEx e){
                                System.out.println("Usuario nao cadastrado. ");
                                continue;
                            }
                        } else{
                            System.out.println("CPF invalido.");
                            continue; 
                        }
                    }
                } else if(op == 4){
                    while(true){
                        System.out.println("Digite o codigo do livro (ou ENTER para voltar): ");
                        String entrada = teclado.nextLine();
                        if(entrada.equals("")){
                            break;
                        } else{
                            try {
                                int cod = Integer.parseInt(entrada);
                                Livro l = b.getLivro(cod);
                                System.out.println("\n" + l + "\n");
                                break;
                            } catch(LivroNaoCadastradoEx e){
                                System.out.println("Livro nao cadastrado. ");
                            } catch(NumberFormatException e){
                                System.out.println("Codigo deve ser um inteiro. ");
                            }
                        }
                    }
                } else if(op == 5){
                    return;
                }
            } else{
                teclado.nextLine();
                continue;
            }
        }
        

    }

    
    public static Livro infoLivro(Scanner teclado) {
        int codigo = 0, qtdDisp = 0;
        String titulo = "", categoria = "";

    
        while (true) {
            System.out.println("Codigo (1-999) (ou ENTER para sair): ");
            String codStr = teclado.nextLine();
            if (codStr.equals("")) return null;
            try {
                codigo = Integer.parseInt(codStr);
                if (codigo >= 1 && codigo <= 999) break;
                else System.out.println("Codigo deve estar entre 1 e 999.");
            } catch (NumberFormatException e) {
                System.out.println("Codigo deve ser inteiro.");
            }
        }

        
        while (true) {
            System.out.println("Titulo (ou ENTER para sair): ");
            titulo = teclado.nextLine();
            if(titulo.equals("")) return null;
            if (titulo.matches("[A-Za-zÀ-ÿ\\s0-9]+") && !titulo.trim().isEmpty()) break;
            else System.out.println("Titulo invalido. Use apenas letras, espacos ou numeros.");
        }

        
        while (true) {
            System.out.println("Categoria (ou ENTER para sair): ");
            categoria = teclado.nextLine();
            if(categoria.equals("")) return null;
            if (categoria.matches("[A-Za-zÀ-ÿ\\s]+") && !categoria.trim().isEmpty()) break;
            else System.out.println("Categoria invalida. Use apenas letras e espacos.");
        }

        
        while (true) {
            System.out.println("Quantidade total (ou ENTER para sair): ");
            String dispStr = teclado.nextLine();
            if (dispStr.equals("")) return null;
            try {
                qtdDisp = Integer.parseInt(dispStr);
                if (qtdDisp > 0) break;
                else System.out.println("Quantidade deve ser maior que 0.");
            } catch (NumberFormatException e) {
                System.out.println("Quantidade deve ser inteira.");
            }
        }

        
        /*while (true) {
            System.out.println("Quantidade emprestada (ou ENTER para sair): ");
            String empStr = teclado.nextLine();
            if (empStr.equals("")) return null;
            try {
                qtdEmprest = Integer.parseInt(empStr);
                if (qtdEmprest < 0) {
                    System.out.println("Quantidade emprestada deve ser maior ou igual a 0.");
                } else if (qtdEmprest > qtdDisp) {
                    System.out.println("Nao pode ser maior que a quantidade disponivel.");
                } else break;
            } catch (NumberFormatException e) {
                System.out.println("Quantidade emprestada deve ser inteira.");
            }
        }*/

        
        return new Livro(codigo, titulo, categoria, qtdDisp, 0);
}


    public static Usuario infoUsuario(Scanner teclado){
                
                String nome;
                while(true){
                    System.out.println("Nome (ou ENTER para sair): ");
                    nome = teclado.nextLine();
                    if(nome.equals("")) return null;
                    if(nome.matches("[A-Za-zÀ-ÿ\\s]+") && !nome.trim().isEmpty()){
                        break;
                    } else{
                        System.out.println("Nome invalido. Reinsira somente com letras e espacos.");
                        continue;
                    }
                }
                
                String sobrenome;
                while(true){
                    System.out.println("Sobrenome (ou ENTER para sair): ");
                    sobrenome = teclado.nextLine();
                    if(sobrenome.equals("")) return null;
                    if(sobrenome.matches("[A-Za-zÀ-ÿ\\s]+") && !sobrenome.trim().isEmpty()){
                        break;
                    } else{
                        System.out.println("Sobrenome invalido. Reinsira somente com letras e espacos.");
                        continue;
                    }
                }
                 

                String ano;
                while(true){
                    System.out.println("Ano de nascimento (ou ENTER para sair): ");
                    ano = teclado.nextLine();
                    if(ano.equals("")) return null;
                    if(!ValidaData.isAno(ano)){
                        System.out.println("Ano invalido. Reinsira o ano.");
                        continue;
                    } else{
                        break;
                    }
                }
                

                String mes;
                while(true){
                    System.out.println("Mes de nascimento (ou ENTER para sair): ");
                    mes = teclado.nextLine();
                    if(mes.equals("")) return null;
                    if(!ValidaData.isMes(mes)){
                        System.out.println("Mes invalido. Reinsira o mes.");
                        continue;
                    } else{
                        break;
                    }

                }
                

                String dia;
                while(true){
                    System.out.println("Dia de nascimento (ou ENTER para sair): ");
                    dia = teclado.nextLine();
                    if(dia.equals("")) return null;
                    if(!ValidaData.isDataValida(dia, mes, ano)){
                        System.out.println("Dia invalido. Reinsira o dia de acordo com o mes e ano informado. ");
                        continue;
                    } else{
                        break;
                    }

                }
                
                    
                String CPF;
                while(true){
                    System.out.println("CPF (ou ENTER para sair): ");
                    CPF = teclado.nextLine();
                    if(CPF.equals("")) return null;
                    if(!ValidaCPF.isCPF(CPF)){
                        System.out.println("CPF invalido. Reinsira o CPF.\n  Moldes aceitos: \nXXX.XXX.XXX-XX\nXXX.XXX.XXX/XX\nXXXXXXXXXXX\n");
                        continue;
                    } else{
                        break;
                    }
                }
                

                String peso; float p = 0.0f;
                while(true){
                    System.out.println("Peso (ou ENTER para sair): ");
                    peso = teclado.nextLine();
                    if(peso.equals("")) return null;
                    try {
                        p = Float.parseFloat(peso);
                        if(p > 20 && p < 300){
                            break;
                        } else{
                            System.out.println("Peso invalido. Reinsira um peso real.");
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Peso invalido. Reinsira um numero convertivel.");   
                    }
                }
                

                String altura; float a = 0.0f;
                while(true){
                    System.out.println("Altura (ou ENTER para sair): ");
                    altura = teclado.nextLine();
                    if(altura.equals("")) return null;
                    try {
                        a = Float.parseFloat(altura);
                        if(a > 0.7 && a < 2.5){
                            break;
                        } else{
                            System.out.println("Altura invalida. Reinsira uma altura real.");
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Altura invalida. Reinsira um numero convertivel. ");
                    }
                }
                

                String endereco;
                while(true){
                    System.out.println("Endereco (ou ENTER para sair): ");
                    endereco = teclado.nextLine(); 
                    if(endereco.equals("")) return null;
                    if(endereco.matches("[A-Za-zÀ-ÿ\\s0-9,]+") && !endereco.trim().isEmpty()){
                        break;
                    } else{
                        System.out.println("Endereco invalido. Reinsira somente com letras, espacos e numeros.");
                        continue;
                    }
                }
                
                
                return new Usuario(nome, sobrenome, dia, mes, ano, CPF, p, a, endereco);
    }

}