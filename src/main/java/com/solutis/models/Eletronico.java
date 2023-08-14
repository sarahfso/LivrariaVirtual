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
        return String.format("| Título: %-20s | Autor: %-20s | Editora: %-20s | Preço: R$%-10.2f | Tamanho: %-10d |",
                getTitulo(), getAutores(), getEditora(), getPreco(), tamanho);
    }

}
