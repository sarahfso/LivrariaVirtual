package com.solutis.models;

public class Venda {
    private Livro[] livros;
    private static int numVendas = 0;
    private int numero;
    private String cliente;
    private float valor;

    public Venda(String cliente, int maxLivros) {
        this.cliente = cliente;
        numVendas++;
        numero = numVendas;
        livros = new Livro[maxLivros]; // Inicialização do array livros
        valor = 0.0f;
    }

    public Livro[] getLivros() {
        return livros;
    }

    public void setLivros(Livro[] livros) {
        this.livros = livros;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public void addLivro(Livro livro, int index) {

        if (index >= 0 && index < livros.length) {
            livros[index] = livro;
            valor += livro.getPreco();
            if(livro instanceof Impresso) {
                Impresso impresso = (Impresso) livro;
                valor += impresso.getFrete();
            }
        } else {
            System.out.println("Índice inválido. O livro não pôde ser adicionado.");
        }

    }

    public void listarLivros() {
        System.out.println("Livros da Venda:");
        for (Livro livro : livros) {
            if (livro != null) {
                System.out.println("Título: " + livro.getTitulo());
                System.out.println("Autores: " + livro.getAutores());
                System.out.println("Editora: " + livro.getEditora());
                System.out.println("Preço: R$" + livro.getPreco());
                if (livro instanceof Impresso) {
                    Impresso impresso = (Impresso) livro;
                    System.out.println("Frete: R$" + impresso.getFrete());
                } else if (livro instanceof Eletronico) {
                    Eletronico eletronico = (Eletronico) livro;
                    System.out.println("Tamanho: " + eletronico.getTamanho() + "KB");
                }
                System.out.println("-----------------------");
            }
        }
    }
}
