package com.innowise.onlineforum.controller.filter;

import com.innowise.onlineforum.controller.attribute.PagePath;
import com.innowise.onlineforum.controller.attribute.SessionAttribute;
import com.innowise.onlineforum.controller.command.ActionCommand;
import com.innowise.onlineforum.controller.command.CommandProvider;
import com.innowise.onlineforum.controller.command.CommandType;
import com.innowise.onlineforum.model.entity.UserRole;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PremissionFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();
    private static final Map<UserRole, EnumSet<CommandType>> permissionCommands = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) {
        EnumSet<CommandType> sameCommands = EnumSet.of(CommandType.CHANGE_LANGUAGE);

        EnumSet<CommandType> guestCommands = EnumSet.of(CommandType.REGISTER, CommandType.LOGIN);
        guestCommands.addAll(sameCommands);

        EnumSet<CommandType> adminCommands = EnumSet.of(CommandType.LOGOUT);
        adminCommands.addAll(sameCommands);

        EnumSet<CommandType> authorizedCommands = EnumSet.of(CommandType.LOGOUT); // more in a future // don't like this name

        permissionCommands.put(UserRole.GUEST, guestCommands);
        permissionCommands.put(UserRole.ADMIN, adminCommands);

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        UserRole role = (UserRole) session.getAttribute(SessionAttribute.CURRENT_ROLE);
        Optional<ActionCommand> optionalCommand = CommandProvider.defineCommand(request);
        if (optionalCommand.isEmpty()) {
            chain.doFilter(request, response);
            return;
        }
        EnumSet<CommandType> commands = permissionCommands.get(role);
        Optional<CommandType> command = CommandProvider.defineCommandType(request);
        if (commands == null || command.isEmpty() || !commands.contains(command.get())) {
            logger.log(Level.ERROR, "User hasn't got permission to execute command = " + command);
            RequestDispatcher dispatcher = request.getRequestDispatcher(PagePath.MAIN);
            dispatcher.forward(request, response);
        } else {
            chain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
    }
}
