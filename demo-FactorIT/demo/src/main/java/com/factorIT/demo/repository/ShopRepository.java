package com.factorIT.demo.repository;

import com.factorIT.demo.model.Shop;
import com.factorIT.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop,Long> {
}
