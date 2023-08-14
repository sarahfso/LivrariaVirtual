package com.solutis.models;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import com.solutis.exceptions.LivroInvalidoException;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class LivrariaVirtual {
    private static final int MAX_IMPRESSOS = 10, MAX_ELETRONICOS = 20, MAX_VENDAS = 50;
    private Impresso[] impressos;
    private Eletronico[] eletronicos;
    private Venda[] vendas;
    private int numImpressos, numEletronicos, numVendas;

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    private Scanner scanner = new Scanner(System.in);

    public LivrariaVirtual() {
        impressos = new Impresso[MAX_IMPRESSOS];
        eletronicos = new Eletronico[MAX_ELETRONICOS];
        vendas = new Venda[MAX_VENDAS];
        numImpressos = 0;
        numEletronicos = 0;
        numVendas = 0;

        entityManagerFactory = Persistence.createEntityManagerFactory("livrariavirtual");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void cadastrarLivro() {

        System.out.println("Qual tipo de livro deseja cadastrar?");
        System.out.println("1. Impresso");
        System.out.println("2. Eletrônico");
        System.out.println("3. Ambos");
        int escolha = scanner.nextInt();

        scanner.nextLine();

        if (escolha == 1 || escolha == 3) {
            try {
                if (numImpressos < MAX_IMPRESSOS) {
                    Impresso impresso = criarLivroImpresso();
                    entityManager.getTransaction().begin();
                    entityManager.persist(impresso);
                    entityManager.getTransaction().commit();
                    numImpressos++;
                    System.out.println("\nLivro impresso cadastrado com sucesso.\n");
                } else {
                    throw new RuntimeException("Não há mais espaço para cadastrar livros impressos.");
                }
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }

        if (escolha == 2 || escolha == 3) {
            try {
                if (numEletronicos < MAX_ELETRONICOS) {
                    Eletronico eletronico = criarLivroEletronico();
                    entityManager.getTransaction().begin();
                    entityManager.persist(eletronico);
                    entityManager.getTransaction().commit();
                    numEletronicos++;
                    System.out.println("\nLivro eletrônico cadastrado com sucesso.\n");
                } else {
                    throw new RuntimeException("Não há mais espaço para cadastrar livros eletrônicos.");
                }
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void realizarVenda() {
        try {
            System.out.flush();
    
            System.out.println("\nNome do cliente: ");
            String nomeCliente = scanner.nextLine();
    
            System.out.println("Quantidade de livros que deseja comprar: ");
            int quantidade = scanner.nextInt();
    
            if (numVendas >= MAX_VENDAS) {
                System.out.println("Número máximo de vendas atingido. Não é possível realizar mais vendas.");
                return;
            }
            entityManager.getTransaction().begin();
            Venda venda = new Venda(nomeCliente, (MAX_ELETRONICOS + MAX_IMPRESSOS));
            entityManager.persist(venda);
    
            for (int i = 0; i < quantidade; i++) {
                try {
                    System.out.println("Tipo do livro (1. Impresso / 2. Eletrônico): ");
                    int tipoLivro = scanner.nextInt();
    
                    scanner.nextLine(); // Consumir a quebra de linha pendente
    
                    if (tipoLivro != 1 && tipoLivro != 2) {
                        System.out.println("Tipo de livro inválido.");
                        i--;
                        continue;
                    }
    
                    TypedQuery<Livro> query;
                    if (tipoLivro == 1) {
                        query = entityManager.createQuery("SELECT i FROM Impresso i", Livro.class);
                    } else {
                        query = entityManager.createQuery("SELECT e FROM Eletronico e", Livro.class);
                    }
    
                    List<Livro> livros = query.getResultList();
                    if (livros.isEmpty()) {
                        System.out.println("Nenhum livro disponível para venda.");
                        return;
                    }
    
                    System.out.println("Escolha o ID do livro: ");
                    for (Livro livro : livros) {
                        System.out.println("ID: " + livro.getId() + " | " + livro.toString());
                    }
                    long idLivro = scanner.nextLong();
                    scanner.nextLine(); // Consumir a quebra de linha pendente
        
                    Livro livroEscolhido = entityManager.find(Livro.class, idLivro);
        
                    if (livroEscolhido == null) {
                        System.out.println("Livro não encontrado.");
                        i--;
                        continue;
                    }
        
                    boolean livroJaAdicionado = false;
                    for (Livro livro : venda.getLivros()) {
                        if (livro != null && livro.getId().equals(idLivro)) {
                            System.out.println("Esse livro já foi adicionado à venda.");
                            livroJaAdicionado = true;
                            break;
                        }
                    }
        
                    if (livroJaAdicionado) {
                        continue; // Pular para a próxima iteração do loop
                    }
        
                    venda.addLivro(livroEscolhido, i);
                    System.out.println("Livro adicionado à venda.");
                    if (livroEscolhido instanceof Impresso) {
                        ((Impresso) livroEscolhido).atualizarEstoque();
                    }
                }catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Por favor, insira um valor numérico.");
                    scanner.nextLine();
                    i--;
                }
            }
            entityManager.getTransaction().commit();
            vendas[numVendas] = venda;
            numVendas++;
            System.out.printf("\nVenda realizada com sucesso. O valor total da venda é R$%.2f %n \n", venda.getValor());
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Certifique-se de inserir um valor numérico quando necessário.");
        }
    }
    

    public void listarLivrosImpressos() {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();

            TypedQuery<Impresso> query = em.createQuery("SELECT i FROM Impresso i", Impresso.class);
            List<Impresso> impressos = query.getResultList();

            for (Impresso impresso : impressos) {
                System.out.println(impresso.toString());
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void listarLivrosEletronicos() {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();

            TypedQuery<Eletronico> query = em.createQuery("SELECT e FROM Eletronico e", Eletronico.class);
            List<Eletronico> eletronicos = query.getResultList();

            for (Eletronico eletronico : eletronicos) {
                System.out.println(eletronico.toString());
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void listarLivros() {
        System.out.println("\nLivros Impressos:\n");
        listarLivrosImpressos();
        System.out.println("\nLivros Eletrônicos:");
        listarLivrosEletronicos();
    }

    public void listarVendas() {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();

            TypedQuery<Venda> query = em.createQuery("SELECT v FROM Venda v JOIN FETCH v.livros", Venda.class);
            List<Venda> vendas = query.getResultList();

            for (Venda venda : vendas) {
                System.out.println("\nNúmero da venda: " + venda.getNumero());
                System.out.println("Cliente: " + venda.getCliente());

                System.out.printf("Valor total: R$%.2f %n \n", venda.getValor());

                venda.listarLivros(em);
                System.out.println("-----------------------");
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    private Impresso criarLivroImpresso() {
        System.out.println("Cadastro de Livro Impresso:");
        System.out.println("Título: ");
        String titulo = scanner.nextLine();

        System.out.println("Autores: ");
        String autores = scanner.nextLine();

        System.out.println("Editora: ");
        String editora = scanner.nextLine();

        System.out.println("Preço: R$");
        float preco = readFloatInput();

        System.out.println("Frete: R$");
        float frete = readFloatInput();

        try {
            System.out.println("Estoque: ");
            int estoque = scanner.nextInt();

            if (estoque <= 0) {
                throw new LivroInvalidoException("Estoque inválido. O estoque deve ser maior que zero.");
            }

            Impresso impresso = new Impresso(titulo, autores, editora, preco, frete, estoque);

            entityManager.getTransaction().begin();
            entityManager.persist(impresso); // Persiste o novo livro impresso
            entityManager.getTransaction().commit();

            return impresso;
        } catch (InputMismatchException e) {
            scanner.nextLine(); // Consumir a quebra de linha pendente
            System.out.println("Entrada inválida. Certifique-se de inserir um valor numérico.");
            throw e;
        } catch (LivroInvalidoException e) {
            scanner.nextLine(); // Consumir a quebra de linha pendente
            System.out.println(e.getMessage());
            throw e;
        }
    }

    private Eletronico criarLivroEletronico() {
        scanner.nextLine();
        System.out.println("Cadastro de Livro Eletrônico:");
        System.out.println("Título: ");
        String titulo = scanner.nextLine();

        System.out.println("Autores: ");
        String autores = scanner.nextLine();

        System.out.println("Editora: ");
        String editora = scanner.nextLine();

        System.out.println("Preço: R$");
        float preco = readFloatInput();

        try {
            System.out.println("Tamanho (KB): ");
            int tamanho = scanner.nextInt();

            if (tamanho <= 0) {
                throw new LivroInvalidoException("Tamanho inválido. O tamanho deve ser maior que zero.");
            }

            Eletronico eletronico = new Eletronico(titulo, autores, editora, preco, tamanho);

            entityManager.getTransaction().begin();
            entityManager.persist(eletronico); // Persiste o novo livro eletrônico
            entityManager.getTransaction().commit();

            return eletronico;
        } catch (InputMismatchException e) {
            scanner.nextLine(); // Consumir a quebra de linha pendente
            System.out.println("Entrada inválida. Certifique-se de inserir um valor numérico.");
            throw e;
        } catch (LivroInvalidoException e) {
            scanner.nextLine();
            System.out.println(e.getMessage());
            throw e;
        }
    }

    private float readFloatInput() {
        String input = scanner.nextLine().replace(',', '.');
        return Float.parseFloat(input);
    }

}
