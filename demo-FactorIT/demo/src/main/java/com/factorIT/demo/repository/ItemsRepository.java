package com.factorIT.demo.repository;

import com.factorIT.demo.model.ItemCatalog;
import com.factorIT.demo.model.Items;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsRepository extends JpaRepository<Items,Long> {
}
