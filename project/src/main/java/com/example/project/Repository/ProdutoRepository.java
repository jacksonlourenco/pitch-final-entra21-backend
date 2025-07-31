package com.example.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project.Model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

}
