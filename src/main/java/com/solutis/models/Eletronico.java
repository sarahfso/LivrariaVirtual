package com.solutis.models;

import jakarta.persistence.Entity;

@Entity
public class Eletronico extends Livro {
    private int tamanho;

    public Eletronico() {
    }

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
        return String.format("| Título: %-25s | Autor: %-15s | Editora: %-15s | Preço: R$%-5.2f | Tamanho: %-5d |",
                getTitulo(), getAutores(), getEditora(), getPreco(), tamanho);
    }

}
