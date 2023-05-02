package ir.isc.sqli.dao;

import ir.isc.sqli.model.USER;
import org.springframework.data.repository.Repository;


public interface UserRepo extends Repository<USER, Long> {
    USER findByUsernameAndPassword(String username, String password);
}
