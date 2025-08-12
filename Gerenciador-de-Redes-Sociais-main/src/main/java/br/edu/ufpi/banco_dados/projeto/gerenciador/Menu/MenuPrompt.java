package br.edu.ufpi.banco_dados.projeto.gerenciador.Menu;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MenuPrompt implements CommandLineRunner {

    @Autowired
    private br.edu.ufpi.banco_dados.projeto.gerenciador.service.UsuarioService usuarioService;

        @Autowired
        private br.edu.ufpi.banco_dados.projeto.gerenciador.service.PostagemService postagemService;

    @Override
    public void run(String... args) {
        int opcao;
        try (Scanner scanner = new Scanner(System.in)) {
            do {
                    System.out.println("\n==== Gerenciador de Redes Sociais ====");
                    System.out.println("1. Cadastrar usuário");
                    System.out.println("2. Listar usuários");
                    System.out.println("3. Excluir usuário");
                    System.out.println("4. Criar postagem");
                    System.out.println("5. Atualizar postagem");
                    System.out.println("6. Listar postagens de um usuário");
                    System.out.println("7. Criar comentário");
                    System.out.println("8. Listar comentários de uma postagem");
                    System.out.println("9. Curtir postagem");
                    System.out.println("10. Descurtir postagem");
                    System.out.println("11. Contar postagens por usuário");
                    System.out.println("12. Média de curtidas por postagem");
                    System.out.println("13. Total de comentários por usuário");
                    System.out.println("14. Ranking de usuários");
                    System.out.println("15. Sair");
                    System.out.print("Escolha uma opção: ");
                    opcao = scanner.nextInt();
                    scanner.nextLine(); // Limpa o buffer

                    switch (opcao) {
                        case 1 -> {
                            System.out.print("Nome: ");
                            String nome = scanner.nextLine();
                            System.out.print("Email: ");
                            String email = scanner.nextLine();
                            var usuario = new br.edu.ufpi.banco_dados.projeto.gerenciador.model.Usuario();
                            usuario.setNome(nome);
                            usuario.setEmail(email);
                            usuarioService.criarUsuario(usuario);
                            System.out.println("Usuário cadastrado!");
                        }
                        case 2 -> {
                            System.out.println("Usuários cadastrados:");
                            var usuarios = usuarioService.listarUsuarios();
                            usuarios.forEach(System.out::println);
                        }
                        case 3 -> {
                            System.out.print("ID do usuário para excluir: ");
                            Long id = scanner.nextLong();
                            scanner.nextLine();
                            usuarioService.excluirUsuario(id);
                            System.out.println("Usuário excluído!");
                        }
                        case 4 -> {
                            System.out.print("ID do usuário: ");
                            Long usuarioId = scanner.nextLong();
                            scanner.nextLine();
                            System.out.print("Conteúdo da postagem: ");
                            String conteudo = scanner.nextLine();
                            try {
                                postagemService.criarPostagem(usuarioId.intValue(), conteudo);
                                System.out.println("Postagem criada!");
                            } catch (Exception e) {
                                String msg = e.getMessage();
                                String userMsg = msg;
                                if (msg != null && msg.contains("ERRO: Usuário com ID")) {
                                    int start = msg.indexOf("ERRO: Usuário com ID");
                                    int end = msg.indexOf(".\n", start);
                                    if (end == -1) end = msg.length();
                                    userMsg = msg.substring(start, end).trim();
                                }
                                System.out.println(userMsg);
                            }
                        }
                        case 5 -> {
                            System.out.print("ID da postagem para atualizar: ");
                            int idPostagem = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("Novo conteúdo: ");
                            String novoConteudo = scanner.nextLine();
                            postagemService.atualizarPostagem(idPostagem, novoConteudo);
                            System.out.println("Postagem atualizada!");
                        }
                        case 6 -> {
                            System.out.print("ID do usuário: ");
                            Long usuarioId = scanner.nextLong();
                            scanner.nextLine();
                            List<?> postagens = (List<?>) postagemService.listarPostagensPorUsuario(usuarioId.intValue());
                            postagens.forEach(System.out::println);
                        }
                        case 7 -> {
                            System.out.print("ID da postagem: ");
                            int idPostagem = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("ID do usuário: ");
                            int idUsuario = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("Conteúdo do comentário: ");
                            String conteudo = scanner.nextLine();
                            try {
                                postagemService.criarComentario(idPostagem, idUsuario, conteudo);
                                System.out.println("Comentário criado!");
                            } catch (IllegalArgumentException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        case 8 -> {
                            System.out.print("ID da postagem: ");
                            int idPostagem = scanner.nextInt();
                            scanner.nextLine();
                            var comentarios = postagemService.listarComentariosDaPostagem(idPostagem);
                            ((List<?>)comentarios).forEach(System.out::println);
                        }
                        case 9 -> {
                            System.out.print("ID da postagem: ");
                            int idPostagem = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("ID do usuário: ");
                            int idUsuario = scanner.nextInt();
                            scanner.nextLine();
                            postagemService.curtirPostagem(idPostagem, idUsuario);
                            System.out.println("Postagem curtida!");
                        }
                        case 10 -> {
                            System.out.print("ID da postagem: ");
                            int idPostagem = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("ID do usuário: ");
                            int idUsuario = scanner.nextInt();
                            scanner.nextLine();
                            postagemService.descurtirPostagem(idPostagem, idUsuario);
                            System.out.println("Curtida removida!");
                        }
                        case 11 -> {
                            System.out.print("ID do usuário: ");
                            int idUsuario = scanner.nextInt();
                            scanner.nextLine();
                            int total = postagemService.contarPostagensPorUsuario(idUsuario);
                            System.out.println("Total de postagens: " + total);
                        }
                        case 12 -> {
                            double media = postagemService.mediaCurtidas().doubleValue();
                            System.out.println("Média de curtidas por postagem: " + media);
                        }
                        case 13 -> {
                            System.out.print("ID do usuário: ");
                            int idUsuario = scanner.nextInt();
                            scanner.nextLine();
                            int total = postagemService.contarComentariosPorUsuario(idUsuario);
                            System.out.println("Total de comentários: " + total);
                        }
                        case 14 -> {
                            System.out.println("Ranking de usuários:");
                            var ranking = usuarioService.listarRanking();
                            ranking.forEach(System.out::println);
                        }
                        case 15 -> System.out.println("Saindo...");
                        default -> System.out.println("Opção inválida!");
                    }
                } while (opcao != 18);
        }
    }
}
