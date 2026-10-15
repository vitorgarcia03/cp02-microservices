package com.github.vitorgarcia03.dadocoletado.exception;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.util.LinkedHashMap;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(DadoNaoEncontradoException.class)
    public ProblemDetail naoEncontrado(DadoNaoEncontradoException e) {

        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail validacao(MethodArgumentNotValidException e) {

        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Verifique os campos informados");
        var campos = new LinkedHashMap<String, String>();
        e.getBindingResult().getFieldErrors().forEach(erro -> campos.putIfAbsent(erro.getField(), erro.getDefaultMessage()));
        problem.setProperty("campos", campos);
        return problem;
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    public ProblemDetail formato(Exception e) {

        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
            "Formato inválido. Use ID numérico, data yyyy-MM-dd e tipo TEMPERATURA, PRESSAO, RADIACAO, COMPOSICAO_ATMOSFERICA ou VELOCIDADE_VENTO.");
    }
}
