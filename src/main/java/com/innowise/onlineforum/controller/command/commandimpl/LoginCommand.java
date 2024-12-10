package com.innowise.onlineforum.controller.command.commandimpl;

import com.innowise.onlineforum.controller.attribute.JspAttribute;
import com.innowise.onlineforum.controller.attribute.PagePath;
import com.innowise.onlineforum.controller.attribute.SessionAttribute;
import com.innowise.onlineforum.controller.command.ActionCommand;
import com.innowise.onlineforum.controller.command.CommandResult;
import com.innowise.onlineforum.exception.CommandException;
import com.innowise.onlineforum.exception.ServiceException;
import com.innowise.onlineforum.model.entity.User;
import com.innowise.onlineforum.model.service.UserService;
import com.innowise.onlineforum.model.service.serviceimpl.UserServiceImpl;
import com.innowise.onlineforum.util.MessageManagerUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

public class LoginCommand implements ActionCommand {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final UserService userService = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        //request.getSession().setAttribute("currentLocale", new Locale("en", "US"));
        CommandResult commandResult = new CommandResult(PagePath.LOGIN, CommandResult.Type.FORWARD);
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String password = request.getParameter(PARAM_NAME_PASSWORD);
        try {
            Optional<User> userOptional = userService.login(login, password);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (!user.isBanned()) {
                    HttpSession session = request.getSession();
                    session.setAttribute(SessionAttribute.USER, user);
                    session.setAttribute(SessionAttribute.CURRENT_ROLE, user.getUserRole());
                    session.setAttribute(SessionAttribute.USER_ID, user.getId());
                    return new CommandResult(SessionAttribute.MAIN_PAGE, CommandResult.Type.REDIRECT);
                } else {
                    request.setAttribute(JspAttribute.ERROR_INPUT_DATA_ATTRIBUTE, MessageManagerUtil.getProperty("message.bannedOrDeletedError"));
                }
            } else {
                request.setAttribute(JspAttribute.ERROR_INPUT_DATA_ATTRIBUTE, MessageManagerUtil.getProperty("message.loginError"));
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return commandResult;
    }
}
