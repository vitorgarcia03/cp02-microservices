package com.github.vitorgarcia03.dadocoletado.exception;

public class DadoNaoEncontradoException extends RuntimeException {

    public DadoNaoEncontradoException(Long id) {

        super("Dado coletado " + id + " não encontrado");
    }
}
