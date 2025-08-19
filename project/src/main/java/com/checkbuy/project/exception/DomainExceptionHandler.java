package com.checkbuy.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class DomainExceptionHandler {

    @ExceptionHandler(ProdutoReferenciaNaoEncontrado.class)
    public ResponseEntity<ResponseError> produtoReferenciaNaoEncontrado(ProdutoReferenciaNaoEncontrado ex){

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
    public ResponseEntity<ResponseError> unidadeNaoEncontrada(UnidadeNaoEncontrada ex){

        ResponseError responseError =  new ResponseError(
                ex.getMessage(),
                HttpStatus.NOT_FOUND,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseError);
    }

    @ExceptionHandler(EmailJaCadastrado.class)
    public ResponseEntity<ResponseError> emailJaCadastrado(EmailJaCadastrado ex){

        ResponseError responseError =  new ResponseError(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
    }

    @ExceptionHandler(ListaNaoEncontada.class)
    public ResponseEntity<ResponseError> listaNaoEncontada(ListaNaoEncontada ex){

        ResponseError responseError =  new ResponseError(
                ex.getMessage(),
                HttpStatus.NOT_FOUND,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseError);
    }

    @ExceptionHandler(ProibidoInserirProdutoNotIndexEmLista.class)
    public ResponseEntity<ResponseError> proibidoInserirProdutoNotIndexEmLista(ProibidoInserirProdutoNotIndexEmLista ex){

        ResponseError responseError =  new ResponseError(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
    }

    @ExceptionHandler(ProdutoJaCadastroEmLista.class)
    public ResponseEntity<ResponseError> produtoJaCadastroEmLista(ProdutoJaCadastroEmLista ex){

        ResponseError responseError =  new ResponseError(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
    }

    @ExceptionHandler(ProibidoInserirQuantidadeZeroEmLista.class)
    public ResponseEntity<ResponseError> proibidoInserirQuantidadeZeroEmLista(ProibidoInserirQuantidadeZeroEmLista ex){

        ResponseError responseError =  new ResponseError(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
    }
}