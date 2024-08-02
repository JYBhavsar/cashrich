package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.CoinData;

public interface CoinDataRepository extends JpaRepository<CoinData, Long> {
	List<CoinData> findBySymbol(String symbol);
}
