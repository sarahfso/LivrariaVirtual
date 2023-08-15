package com.solutis.models;

import com.solutis.exceptions.LivroInvalidoException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class LivrariaVirtual {
    private static final int MAX_IMPRESSOS = 10;
    private static final int MAX_ELETRONICOS = 20;
    private static final int MAX_VENDAS = 50;

    private Impresso[] impressos;
    private Eletronico[] eletronicos;
    private Venda[] vendas;

    private int numImpressos;
    private int numEletronicos;
    private int numVendas;

    private Scanner scanner = new Scanner(System.in);

    public LivrariaVirtual() {
        impressos = new Impresso[MAX_IMPRESSOS];
        eletronicos = new Eletronico[MAX_ELETRONICOS];
        vendas = new Venda[MAX_VENDAS];
        numImpressos = 0;
        numEletronicos = 0;
        numVendas = 0;
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
                    impressos[numImpressos] = impresso;
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
                    eletronicos[numEletronicos] = eletronico;
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

            Venda venda = new Venda(nomeCliente, (MAX_ELETRONICOS + MAX_IMPRESSOS));

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

                    if (tipoLivro == 1) {
                        listarLivrosImpressos();
                    } else {
                        listarLivrosEletronicos();
                    }

                    System.out.println("Escolha o índice do livro: ");
                    int indiceLivro = scanner.nextInt();

                    scanner.nextLine(); // Consumir a quebra de linha pendente

                    if ((tipoLivro == 1 && (indiceLivro < 0 || indiceLivro >= numImpressos)) ||
                            (tipoLivro == 2 && (indiceLivro < 0 || indiceLivro >= numEletronicos))) {
                        System.out.println("Índice inválido ou livro não encontrado.");
                        i--;
                        continue;
                    }

                    Livro livroEscolhido = (tipoLivro == 1) ? impressos[indiceLivro] : eletronicos[indiceLivro];

                    venda.addLivro(livroEscolhido, i);
                    System.out.println("Livro adicionado à venda.");
                    if (livroEscolhido instanceof Impresso) {
                        ((Impresso) livroEscolhido).atualizarEstoque(); // Adicione essa linha
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Por favor, insira um valor numérico.");
                    scanner.nextLine();
                    i--;
                }
            }

            vendas[numVendas] = venda;
            numVendas++;
            System.out.printf("\nVenda realizada com sucesso. O valor total da venda é R$%.2f %n \n", venda.getValor());
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Certifique-se de inserir um valor numérico quando necessário.");
        }
    }

    public void listarLivrosImpressos() {
        for (int i = 0; i < numImpressos; i++) {
            System.out.println(i + ". " + impressos[i].toString());
        }
    }

    public void listarLivrosEletronicos() {
        for (int i = 0; i < numEletronicos; i++) {
            System.out.println(i + ". " + eletronicos[i].toString());
        }
    }

    public void listarLivros() {
        System.out.println("\nLivros Impressos:\n");
        listarLivrosImpressos();
        System.out.println("\nLivros Eletrônicos:");
        listarLivrosEletronicos();
    }

    public void listarVendas() {

        for (Venda venda : vendas) {
            if (venda != null) {
                System.out.println("\nNúmero da venda: " + venda.getNumero());
                System.out.println("Cliente: " + venda.getCliente());

                System.out.printf("Valor total: R$%.2f %n \n", venda.getValor());

                venda.listarLivros();
                System.out.println("-----------------------");
            }
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
            
            scanner.nextLine(); // Consumir quebra de linha pendente

            if (estoque <= 0) {
                throw new LivroInvalidoException("Estoque inválido. O estoque deve ser maior que zero.");
            }

            return new Impresso(titulo, autores, editora, preco, frete, estoque);
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

            scanner.nextLine(); // Consumir quebra de linha pendente

            if (tamanho <= 0) {
                throw new LivroInvalidoException("Tamanho inválido. O tamanho deve ser maior que zero.");
            }

            return new Eletronico(titulo, autores, editora, preco, tamanho);
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
