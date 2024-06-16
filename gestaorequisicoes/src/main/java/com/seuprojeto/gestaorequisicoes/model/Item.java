package com.seuprojeto.gestaorequisicoes.model;

public class Item {
    private Long id;
    private String descricao;
    private String nome;
    
    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNome() {
        return nome; // Getter para nome
    }

    public void setNome(String nome) {
        this.nome = nome; // Setter para nome
    }
}
