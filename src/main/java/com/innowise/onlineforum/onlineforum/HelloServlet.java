package com.innowise.onlineforum.onlineforum;

import java.io.*;

import com.innowise.onlineforum.onlineforum.entity.User;
import com.innowise.onlineforum.onlineforum.entity.UserCredentials;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;
    private final Configuration configuration = new Configuration().configure();

    public void init() {
        message = "Hello World!";

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");

        //Test field for Hibernate. Do not kick shit out of me plz, just testing.
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(UserCredentials.class);

        User user = User.builder()
                .username("testname")
                .email("testemail@mail.com")
                .build();

        UserCredentials userCredentials = UserCredentials.builder()
                .user(user)
                .passwordHash("testpasswordhash")
                .build();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(user);
            session.persist(userCredentials);
            session.getTransaction().commit();
        }
    }

    public void destroy() {
    }
}