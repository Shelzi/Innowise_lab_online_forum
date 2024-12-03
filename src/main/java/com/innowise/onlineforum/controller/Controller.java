package com.innowise.onlineforum.controller;

import java.io.*;
import java.util.Optional;

import com.innowise.onlineforum.exception.CommandException;
import com.innowise.onlineforum.controller.command.ActionCommand;
import com.innowise.onlineforum.controller.command.CommandProvider;
import com.innowise.onlineforum.controller.command.CommandResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(urlPatterns = "*.do", name = "Controller", loadOnStartup = 1)
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void init() {
        //ConnectionPool.getInstance().init();
    }

    @Override
    public void destroy() {
        /*try {
            ConnectionPool.getInstance().destroy();
        } catch (ConnectionPoolException e) {
            logger.log(Level.ERROR, e);
        }*/
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response)
            throws ServletException, IOException {
        Optional<ActionCommand> command = CommandProvider.defineCommand(request);
        String date = request.getParameter("startDate");
        try {
            CommandResult commandResult = command.isPresent() ? command.get().execute(request, response) : new CommandResult(CommandResult.DEFAULT_PATH);
            commandResult.redirect(request, response);
        } catch (CommandException e) {
            logger.log(Level.ERROR, "Couldn't process request: " + e);
            throw new ServletException(e);
        }
    }
}