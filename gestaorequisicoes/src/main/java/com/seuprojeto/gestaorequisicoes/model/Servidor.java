package com.seuprojeto.gestaorequisicoes.model;

public class Servidor {
    //
    // ATRIBUTOS
    //
    private String  nome;
    private String  path;
    private int     numRequisicoes;
    private boolean ativo;

    //
    // MÉTODOS
    //
    public Servidor(String nome, String path) {
        this.setNome(nome);
        this.setPath(path);
        this.numRequisicoes = 0;
        this.ativo = false;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getNumRequisicoes() {
        return numRequisicoes;
    }

    public void adicionarRequisicao() {
        this.numRequisicoes++;
    }

    public void removerRequisicao() {
        if(this.numRequisicoes > 0)
            this.numRequisicoes--;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}

