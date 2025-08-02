package com.example.project.repository;

import com.example.project.model.ProdutoScraping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoScrapingRepository extends JpaRepository<ProdutoScraping, Integer> {
}
