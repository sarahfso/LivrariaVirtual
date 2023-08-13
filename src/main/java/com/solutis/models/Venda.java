package com.solutis.models;

public class Venda {
    private int numVendas;
    private int numero;
    private String cliente;
    private float valor;

    public Venda(int numVendas, int numero, String cliente, float valor) {
        this.numVendas = numVendas;
        this.numero = numero;
        this.cliente = cliente;
        this.valor = valor;
    }
    
    public int getNumVendas() {
        return numVendas;
    }
    
    public void setNumVendas(int numVendas) {
        this.numVendas = numVendas;
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

    }

    public void listarLivros() {
        
    }

}
