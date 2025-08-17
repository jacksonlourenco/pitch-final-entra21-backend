package com.checkbuy.project.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class DomainExceptionHandler {

    @ExceptionHandler(ProdutoReferenciaNaoEncontrado.class)
    public ResponseEntity<ResponseError> ProdutoReferenciaNaoEncontrado(ProdutoReferenciaNaoEncontrado ex){


        ResponseError responseError =  new ResponseError(
                ex.getMessage(),
                HttpStatus.NOT_FOUND,
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseError);
    }

    @ExceptionHandler(AliasProdutoReferenciaNaoEncontrado.class)
    public ResponseEntity<ResponseError> aliasProdutoReferenciaNaoEncontrado(AliasProdutoReferenciaNaoEncontrado ex){

        ResponseError responseError =  new ResponseError(
                ex.getMessage(),
                HttpStatus.NOT_FOUND,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseError);
    }

    @ExceptionHandler(ProdutoReferenciaNotIndexImutavel.class)
    public ResponseEntity<ResponseError> produtoReferenciaNotIndexImutavel(ProdutoReferenciaNotIndexImutavel ex){

        ResponseError responseError =  new ResponseError(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
    }

    @ExceptionHandler(UnidadeNaoEncontrada.class)
    public ResponseEntity<ResponseError> UnidadeNaoEncontrada(UnidadeNaoEncontrada ex){

        ResponseError responseError =  new ResponseError(
                ex.getMessage(),
                HttpStatus.NOT_FOUND,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseError);
    }
}