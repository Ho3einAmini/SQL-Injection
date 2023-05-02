package ir.isc.sqli.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "USER")
public class USER {
    @Id
    private Long id;
    private String username;
    private String password;

    public Long getId() {
        return id;
    }

    public USER() {
    }

    public USER(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
