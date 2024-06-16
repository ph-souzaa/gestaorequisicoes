package com.seuprojeto.gestaorequisicoes.database;

import com.seuprojeto.gestaorequisicoes.model.Item;
import com.seuprojeto.gestaorequisicoes.model.Requisicao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Database {
    private static final List<Item> itens = new ArrayList<>();
    private static final List<Requisicao> requisicoes = new ArrayList<>();
    private static final AtomicLong itemIdGenerator = new AtomicLong();
    private static final AtomicLong requisicaoIdGenerator = new AtomicLong();

    public static List<Item> getAllItens() {
        return itens;
    }

    public static Item addItem(Item item) {
        item.setId(itemIdGenerator.incrementAndGet());
        itens.add(item);
        return item;
    }

    public static Item getItem(Long id) {
        return itens.stream().filter(i -> i.getId().equals(id)).findFirst().orElse(null);
    }

    public static Item updateItem(Long id, Item newItem) {
        Item item = getItem(id);
        if (item != null) {
            item.setDescricao(newItem.getDescricao());
        }
        return item;
    }

    public static boolean deleteItem(Long id) {
        return itens.removeIf(i -> i.getId().equals(id));
    }

    public static List<Requisicao> getAllRequisicoes() {
        return requisicoes;
    }

    public static Requisicao addRequisicao(Requisicao requisicao) {
        requisicao.setId(requisicaoIdGenerator.incrementAndGet());
        requisicoes.add(requisicao);
        return requisicao;
    }

    public static Requisicao getRequisicao(Long id) {
        return requisicoes.stream().filter(r -> r.getId().equals(id)).findFirst().orElse(null);
    }

    public static Requisicao updateRequisicao(Long id, Requisicao newRequisicao) {
        Requisicao requisicao = getRequisicao(id);
        if (requisicao != null) {
            requisicao.setDescricao(newRequisicao.getDescricao());
            requisicao.setDataCriacao(newRequisicao.getDataCriacao());
            requisicao.setStatus(newRequisicao.getStatus());
            requisicao.setItem(newRequisicao.getItem());
        }
        return requisicao;
    }

    public static boolean deleteRequisicao(Long id) {
        return requisicoes.removeIf(r -> r.getId().equals(id));
    }
}
