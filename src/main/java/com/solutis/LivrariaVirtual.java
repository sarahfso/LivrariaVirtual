package com.solutis;

import com.solutis.models.Eletronico;
import com.solutis.models.Impresso;
import com.solutis.models.Livro;
import com.solutis.models.Venda;
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

        scanner.nextLine(); // Consumir a quebra de linha pendente

        if (escolha == 1 || escolha == 3) {
            if (numImpressos < MAX_IMPRESSOS) {
                Impresso impresso = criarLivroImpresso();
                impressos[numImpressos] = impresso;
                numImpressos++;
                System.out.println("Livro impresso cadastrado com sucesso.");
            } else {
                System.out.println("Não há mais espaço para cadastrar livros impressos.");
            }
        }

        if (escolha == 2 || escolha == 3) {
            if (numEletronicos < MAX_ELETRONICOS) {
                Eletronico eletronico = criarLivroEletronico();
                eletronicos[numEletronicos] = eletronico;
                numEletronicos++;
                System.out.println("Livro eletrônico cadastrado com sucesso.");
            } else {
                System.out.println("Não há mais espaço para cadastrar livros eletrônicos.");
            }
        }
    }

    public void realizarVenda() {
        System.out.println("Nome do cliente: ");
        String nomeCliente = scanner.nextLine();

        System.out.println("Quantidade de livros que deseja comprar: ");
        int quantidade = scanner.nextInt();

        scanner.nextLine(); // Consumir a quebra de linha pendente

        Venda venda = new Venda(nomeCliente);

        for (int i = 0; i < quantidade; i++) {
            System.out.println("Tipo do livro (1. Impresso / 2. Eletrônico): ");
            int tipoLivro = scanner.nextInt();

            scanner.nextLine(); // Consumir a quebra de linha pendente

            if (tipoLivro == 1) {
                listarLivrosImpressos();
            } else if (tipoLivro == 2) {
                listarLivrosEletronicos();
            } else {
                System.out.println("Tipo de livro inválido.");
                i--;
                continue;
            }

            System.out.println("Escolha o índice do livro: ");
            int indiceLivro = scanner.nextInt();

            scanner.nextLine(); // Consumir a quebra de linha pendente

            Livro livroEscolhido = null;

            if (tipoLivro == 1 && indiceLivro >= 0 && indiceLivro < numImpressos) {
                livroEscolhido = impressos[indiceLivro];
            } else if (tipoLivro == 2 && indiceLivro >= 0 && indiceLivro < numEletronicos) {
                livroEscolhido = eletronicos[indiceLivro];
            } else {
                System.out.println("Índice inválido ou livro não encontrado.");
                i--;
                continue;
            }

            venda.addLivro(livroEscolhido, i);
            System.out.println("Livro adicionado à venda.");
        }

        vendas[numVendas] = venda;
        numVendas++;
        System.out.println("Venda realizada com sucesso.");
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
        System.out.println("Livros Impressos:");
        listarLivrosImpressos();
        System.out.println("Livros Eletrônicos:");
        listarLivrosEletronicos();
    }

    public void listarVendas() {
        for (int i = 0; i < numVendas; i++) {
            System.out.println("Número da venda: " + vendas[i].getNumero());
            System.out.println("Cliente: " + vendas[i].getCliente());
            System.out.println("Valor total: " + vendas[i].getValor());
            System.out.println("Livros: ");
            vendas[i].listarLivros();
            System.out.println("-----------------------");
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

        System.out.println("Preço: ");
        float preco = scanner.nextFloat();

        System.out.println("Frete: ");
        float frete = scanner.nextFloat();

        System.out.println("Estoque: ");
        int estoque = scanner.nextInt();

        return new Impresso(titulo, autores, editora, preco, frete, estoque);
    }

    private Eletronico criarLivroEletronico() {
        System.out.println("Cadastro de Livro Eletrônico:");
        System.out.println("Título: ");
        String titulo = scanner.nextLine();

        System.out.println("Autores: ");
        String autores = scanner.nextLine();

        System.out.println("Editora: ");
        String editora = scanner.nextLine();

        System.out.println("Preço: ");
        float preco = scanner.nextFloat();

        System.out.println("Tamanho (KB): ");
        int tamanho = scanner.nextInt();

        return new Eletronico(titulo, autores, editora, preco, tamanho);
    }

    public static void main(String[] args) {
        LivrariaVirtual livraria = new LivrariaVirtual();
    
        Scanner scanner = new Scanner(System.in);
        int opcao;
    
        do {
            System.out.println("=== Menu de Opções ===");
            System.out.println("1. Cadastrar livro");
            System.out.println("2. Realizar venda");
            System.out.println("3. Listar livros");
            System.out.println("4. Listar vendas");
            System.out.println("5. Sair do programa");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha pendente
    
            switch (opcao) {
                case 1:
                    livraria.cadastrarLivro();
                    break;
                case 2:
                    livraria.realizarVenda();
                    break;
                case 3:
                    livraria.listarLivros();
                    break;
                case 4:
                    livraria.listarVendas();
                    break;
                case 5:
                    System.out.println("Encerrando o programa.");
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
            }
    
        } while (opcao != 5);
    
        scanner.close();

    }    
    
}
