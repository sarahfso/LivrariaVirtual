package com.solutis.models;

public class Venda {
    private Livro[] livros;
    private static int numVendas = 0;
    private int numero;
    private String cliente;
    private float valor;

    public Venda(String cliente) {
        this.cliente = cliente;
        numVendas++;
        numero = numVendas;
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
        } else {
            System.out.println("Índice inválido. O livro não pôde ser adicionado.");
        }

    }

    public void listarLivros() {
        for (Livro livro : livros) {
            if (livro != null) {
                System.out.println("Título: " + livro.getTitulo());
                System.out.println("Autores: " + livro.getAutores());
                System.out.println("Editora: " + livro.getEditora());
                System.out.println("Preço: " + livro.getPreco());
                System.out.println("-----------------------");
            }
        }

    }
}
