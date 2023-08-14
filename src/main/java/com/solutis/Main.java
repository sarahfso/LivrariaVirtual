package com.solutis;

import java.util.Scanner;

import com.solutis.exceptions.LivroInvalidoException;
import com.solutis.models.LivrariaVirtual;

public class Main {
    public static void main(String[] args) {
        LivrariaVirtual livraria = new LivrariaVirtual();

        Scanner scanner = new Scanner(System.in);
        int opcao;
        
        do {
            System.out.println("\n=== Menu de Opções ===");
            System.out.println("1. Cadastrar livro");
            System.out.println("2. Realizar venda");
            System.out.println("3. Listar livros");
            System.out.println("4. Listar vendas");
            System.out.println("5. Sair do programa");
            System.out.println("\n======================");
            System.out.print("\nEscolha uma opção: ");
          
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha pendente

            
            switch (opcao) {
                
                case 1:
                    try {
                        livraria.cadastrarLivro();
                    } catch (LivroInvalidoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        livraria.realizarVenda();
                    } catch (LivroInvalidoException e) {
                        System.out.println(e.getMessage());
                    }
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

    public void cleanConsole(){
        System.out.print("\033[H\033[2J");  
        System.out.flush();
    }
}
