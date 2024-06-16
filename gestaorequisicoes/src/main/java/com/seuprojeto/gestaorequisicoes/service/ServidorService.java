package com.seuprojeto.gestaorequisicoes.service;

import com.seuprojeto.gestaorequisicoes.model.Servidor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServidorService {
    private final List<Servidor> servidores = new ArrayList<>();

    // Adiciona um servidor à lista. Se o servidor com o mesmo nome já existir, retorna false.
    public boolean adicionarServidor(Servidor servidor) {
        if (servidores.stream().anyMatch(s -> s.getNome().equals(servidor.getNome()))) {
            return false;
        }
        servidor.setAtivo(true); // Ativa o servidor ao adicioná-lo
        servidores.add(servidor);
        return true;
    }

    // Parar um servidor específico pelo nome, retornando true se for bem sucedido.
    public boolean pararServidor(String nome) {
        Optional<Servidor> servidor = servidores.stream()
                .filter(s -> s.getNome().equals(nome) && s.isAtivo())
                .findFirst();
        if (servidor.isPresent()) {
            servidor.get().setAtivo(false);
            return true;
        }
        return false;
    }

    // Reiniciar um servidor específico pelo nome, tornando-o ativo novamente.
    public boolean reiniciarServidor(String nome) {
        Optional<Servidor> servidor = servidores.stream()
                .filter(s -> s.getNome().equals(nome))
                .findFirst();
        if (servidor.isPresent()) {
            servidor.get().setAtivo(true);
            return true;
        }
        return false;
    }

    // Retorna a lista completa de servidores.
    public List<Servidor> listarServidores() {
        return servidores;
    }

    // Incrementa o contador de requisições de um servidor específico pelo nome.
    public void adicionarRequisicao(String nome) {
        servidores.stream()
                .filter(s -> s.getNome().equals(nome) && s.isAtivo())
                .findFirst()
                .ifPresent(Servidor::adicionarRequisicao);
    }

    // Decrementa o contador de requisições de um servidor específico pelo nome, se possível.
    public void removerRequisicao(String nome) {
        servidores.stream()
                .filter(s -> s.getNome().equals(nome) && s.isAtivo())
                .findFirst()
                .ifPresent(Servidor::removerRequisicao);
    }
}
