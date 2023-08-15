package com.solutis.models;

public class Eletronico extends Livro {
    private int tamanho;

    public Eletronico(String titulo, String autores, String editora, float preco, int tamanho) {
        super(titulo, autores, editora, preco);
        this.tamanho = tamanho;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    @Override
    public String toString() {
        return String.format("| Título: %-15s | Autor: %-15s | Editora: %-10s | Preço: R$%-6.2f | Tamanho: %-3d MB |",
                getTitulo(), getAutores(), getEditora(), getPreco(), tamanho);
    }

}
