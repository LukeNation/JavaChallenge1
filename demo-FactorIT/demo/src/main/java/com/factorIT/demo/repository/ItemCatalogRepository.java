package com.factorIT.demo.repository;

import com.factorIT.demo.model.ItemCatalog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCatalogRepository extends JpaRepository<ItemCatalog,Long> {
}
