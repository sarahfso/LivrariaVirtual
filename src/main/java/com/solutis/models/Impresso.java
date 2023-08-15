package com.solutis.models;

public class Impresso extends Livro{
    private float frete;
    private int estoque;

    public Impresso(String titulo, String autores, String editora, float preco, float frete, int estoque) {
        super(titulo, autores, editora, preco);
        this.frete = frete;
        this.estoque = estoque;
    }

    public float getFrete() {
        return frete;
    }

    public void setFrete(float frete) {
        this.frete = frete;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public void atualizarEstoque() {
        estoque--;
    }

    @Override
public String toString() {
    return String.format("| Título: %-15s | Autor: %-15s | Editora: %-10s | Preço: R$%-6.2f | Frete: R$%-6.2f | Estoque: %-5d |", getTitulo(), getAutores(), getEditora(), getPreco(), frete, estoque);
}

    

}
