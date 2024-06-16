package com.seuprojeto.gestaorequisicoes.model;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String conta;
    private long dataHoraLogin;

    public Usuario() {}

    public Usuario(String conta) {
        this.conta = conta;
        this.dataHoraLogin = System.currentTimeMillis();
    }

    public long getDataHoraLogin() {
        return dataHoraLogin;
    }

    public void setDataHoraLogin(long dataHoraLogin) {
        this.dataHoraLogin = dataHoraLogin;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }
}
