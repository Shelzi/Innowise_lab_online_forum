package com.innowise.onlineforum.controller.command.commandimpl;

import com.innowise.onlineforum.controller.attribute.JspAttribute;
import com.innowise.onlineforum.controller.attribute.RequestParameter;
import com.innowise.onlineforum.controller.attribute.SessionAttribute;
import com.innowise.onlineforum.controller.command.ActionCommand;
import com.innowise.onlineforum.controller.command.CommandResult;
import com.innowise.onlineforum.exception.CommandException;
import com.innowise.onlineforum.exception.ServiceException;
import com.innowise.onlineforum.model.entity.UserRole;
import com.innowise.onlineforum.model.service.UserService;
import com.innowise.onlineforum.model.service.serviceimpl.UserServiceImpl;
import com.innowise.onlineforum.controller.attribute.PagePath;
import com.innowise.onlineforum.util.MessageManagerUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class RegisterCommand implements ActionCommand {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String userName = request.getParameter(RequestParameter.USER_NAME);
        String email = request.getParameter(RequestParameter.EMAIL);
        String password = request.getParameter(RequestParameter.PASSWORD);
        String repeatedPassword = request.getParameter(RequestParameter.REPEATED_PASSWORD);
        String userRole = UserRole.AUTHORIZED.name();

        Map<String, String> requestFields = new LinkedHashMap<>();

        requestFields.put(RequestParameter.USER_NAME, userName);
        requestFields.put(RequestParameter.EMAIL, email);
        requestFields.put(RequestParameter.PASSWORD, password);
        requestFields.put(RequestParameter.REPEATED_PASSWORD, repeatedPassword);
        requestFields.put(RequestParameter.USER_ROLE, userRole);

        UserService service = UserServiceImpl.getInstance();
        CommandResult result = new CommandResult(SessionAttribute.MAIN_PAGE, CommandResult.Type.REDIRECT);

        try {
            if (service.create(requestFields)) {
                HttpSession session = request.getSession();
                session.setAttribute(SessionAttribute.SUCCESS_MESSAGE, Boolean.TRUE);
            } else {
                request.setAttribute(RequestParameter.USER_NAME, requestFields.get(RequestParameter.USER_NAME));
                request.setAttribute(RequestParameter.EMAIL, requestFields.get(RequestParameter.EMAIL));
                request.setAttribute(RequestParameter.PASSWORD, requestFields.get(RequestParameter.PASSWORD));
                request.setAttribute(RequestParameter.REPEATED_PASSWORD, requestFields.get(RequestParameter.REPEATED_PASSWORD));
                request.setAttribute(JspAttribute.ERROR_INPUT_DATA_ATTRIBUTE,
                        MessageManagerUtil.getProperty("message.registrationError"));
                return new CommandResult(PagePath.REGISTER, CommandResult.Type.FORWARD);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return result;
    }
}