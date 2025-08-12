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
                    System.out.println("3. Atualizar usuário");
                    System.out.println("4. Excluir usuário");
                    System.out.println("5. Criar postagem");
                    System.out.println("6. Atualizar postagem");
                    System.out.println("7. Excluir postagem");
                    System.out.println("8. Listar postagens de um usuário");
                    System.out.println("9. Criar comentário");
                    System.out.println("10. Listar comentários de uma postagem");
                    System.out.println("11. Curtir postagem");
                    System.out.println("12. Descurtir postagem");
                    System.out.println("13. Contar postagens por usuário");
                    System.out.println("14. Contar comentários por usuário");
                    System.out.println("15. Média de curtidas por postagem");
                    System.out.println("16. Total de curtidas por usuário");
                    System.out.println("17. Listar ranking de usuários");
                    System.out.println("18. Sair");
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
                            System.out.print("ID do usuário para atualizar: ");
                            Long id = scanner.nextLong();
                            scanner.nextLine();
                            System.out.print("Novo nome: ");
                            String novoNome = scanner.nextLine();
                            System.out.print("Novo email: ");
                            String novoEmail = scanner.nextLine();
                            usuarioService.atualizarUsuario(id, novoNome, novoEmail);
                            System.out.println("Usuário atualizado!");
                        }
                        case 4 -> {
                            System.out.print("ID do usuário para excluir: ");
                            Long id = scanner.nextLong();
                            scanner.nextLine();
                            usuarioService.excluirUsuario(id);
                            System.out.println("Usuário excluído!");
                        }
                        case 5 -> {
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
                        case 6 -> {
                            System.out.print("ID da postagem para atualizar: ");
                            int idPostagem = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("Novo conteúdo: ");
                            String novoConteudo = scanner.nextLine();
                            postagemService.atualizarPostagem(idPostagem, novoConteudo);
                            System.out.println("Postagem atualizada!");
                        }
                        case 7 -> {
                            System.out.print("ID da postagem para excluir: ");
                            int idPostagem = scanner.nextInt();
                            scanner.nextLine();
                            postagemService.excluirPostagem(idPostagem);
                            System.out.println("Postagem excluída!");
                        }
                        case 8 -> {
                            System.out.print("ID do usuário: ");
                            Long usuarioId = scanner.nextLong();
                            scanner.nextLine();
                            List<?> postagens = (List<?>) postagemService.listarPostagensPorUsuario(usuarioId.intValue());
                            postagens.forEach(System.out::println);
                        }
                        case 9 -> {
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
                        case 10 -> {
                            System.out.print("ID da postagem: ");
                            int idPostagem = scanner.nextInt();
                            scanner.nextLine();
                            var comentarios = postagemService.listarComentariosDaPostagem(idPostagem);
                            ((List<?>)comentarios).forEach(System.out::println);
                        }
                        case 11 -> {
                            System.out.print("ID da postagem: ");
                            int idPostagem = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("ID do usuário: ");
                            int idUsuario = scanner.nextInt();
                            scanner.nextLine();
                            postagemService.curtirPostagem(idPostagem, idUsuario);
                            System.out.println("Postagem curtida!");
                        }
                        case 12 -> {
                            System.out.print("ID da postagem: ");
                            int idPostagem = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("ID do usuário: ");
                            int idUsuario = scanner.nextInt();
                            scanner.nextLine();
                            postagemService.descurtirPostagem(idPostagem, idUsuario);
                            System.out.println("Curtida removida!");
                        }
                        case 13 -> {
                            System.out.print("ID do usuário: ");
                            int idUsuario = scanner.nextInt();
                            scanner.nextLine();
                            int total = postagemService.contarPostagensPorUsuario(idUsuario);
                            System.out.println("Total de postagens: " + total);
                        }
                        case 14 -> {
                            System.out.print("ID do usuário: ");
                            int idUsuario = scanner.nextInt();
                            scanner.nextLine();
                            int total = postagemService.contarComentariosPorUsuario(idUsuario);
                            System.out.println("Total de comentários: " + total);
                        }
                        case 15 -> {
                            double media = postagemService.mediaCurtidas().doubleValue();
                            System.out.println("Média de curtidas por postagem: " + media);
                        }
                        case 16 -> {
                            System.out.print("ID do usuário: ");
                            Long usuarioId = scanner.nextLong();
                            scanner.nextLine();
                            int total = usuarioService.totalCurtidasPorUsuario(usuarioId);
                            System.out.println("Total de curtidas do usuário: " + total);
                        }
                        case 17 -> {
                            System.out.println("Ranking de usuários por curtidas:");
                            var ranking = usuarioService.listarRanking();
                            ranking.forEach(System.out::println);
                        }
                        case 18 -> System.out.println("Saindo...");
                        default -> System.out.println("Opção inválida!");
                    }
                } while (opcao != 18);
        }
    }
}
