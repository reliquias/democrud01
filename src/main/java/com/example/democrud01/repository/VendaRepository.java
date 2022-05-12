package com.example.democrud01.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.democrud01.model.Venda;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {}