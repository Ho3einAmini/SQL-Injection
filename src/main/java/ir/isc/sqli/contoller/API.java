package ir.isc.sqli.contoller;

import ir.isc.sqli.dao.UserRepo;
import ir.isc.sqli.model.PRODUCT;
import ir.isc.sqli.model.USER;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController("API")
public class API {
    @Resource
    UserRepo userRepo;

    @GetMapping("/")
    private ModelAndView index()
    {
        return new ModelAndView("login");
    }
    @PostMapping("/login")
    private String login(HttpServletRequest request)
    {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            USER user = userRepo.findByUsernameAndPassword(username, password);
            if (user == null)
                throw new Exception();
            return "OK";
        } catch (Exception e) {
            return "Not Found";
        }
    }
    @GetMapping("/index")
    private String i()
    {
        return "Index...";
    }





    @RequestMapping("/vsqli")
    private ModelAndView vsqli()
    {
        return new ModelAndView("sqliLogin");
    }

    @PostMapping("/vlogin")
    private String vlogin(HttpServletRequest request)
    {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String query = "select * from user where username='" + username + "' AND password='" + password + "'";
            ResultSet rs= executeQuery(query);
            USER user = null;
            while (rs.next())
                user = new USER(rs.getLong(1) , rs.getString(2), rs.getString(3));
            if (user == null)
                throw new Exception();
            return "OK";
        } catch (Exception e) {
            return "Not Found";
        }
    }
    //attack payload : http://192.168.209.102:1010/products?category=laptops%27%20union%20SELECT%20null,%20username,%20%20password%20from%20user%20--%20
    @RequestMapping("/products")
    private Object products(HttpServletRequest request)
    {
        String category = request.getParameter("category");
        String query = "select id, name, description from PRODUCT";
        if (category != null && category.length() > 0)
            query += " where category='" + category + "'";
        List<PRODUCT> products = new ArrayList<>();
        try {
            ResultSet rs = executeQuery(query);
            while (rs.next())
            {
                PRODUCT product = new PRODUCT(rs.getLong(1) , rs.getString(2), rs.getString(3));
                products.add(product);
            }
        } catch (Exception e) {
            return e.getStackTrace();
        }
       return products;
    }

    private ResultSet executeQuery(String query) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/lab","lab","07262578");
        Statement stmt=con.createStatement();
        return stmt.executeQuery(query);
    }

}
