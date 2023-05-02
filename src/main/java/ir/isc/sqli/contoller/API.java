package ir.isc.sqli.contoller;

import ir.isc.sqli.dao.UserRepo;
import ir.isc.sqli.model.USER;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@RestController("API")
public class API {
    @Resource
    UserRepo userRepo;

    @RequestMapping("/")
    private ModelAndView index()
    {
        return new ModelAndView("login");
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
    @PostMapping("/vlogin")
    private String vlogin(HttpServletRequest request)
    {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/lab","lab","07262578");
            Statement stmt=con.createStatement();
            String query = "select * from user where username='" + username + "' AND password='" + password + "'";
            ResultSet rs=stmt.executeQuery(query);
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

}
