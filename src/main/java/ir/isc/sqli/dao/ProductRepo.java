package ir.isc.sqli.dao;

import ir.isc.sqli.model.PRODUCT;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ProductRepo extends Repository<PRODUCT, Long> {
    List<PRODUCT> findByCategory(String category);
}
