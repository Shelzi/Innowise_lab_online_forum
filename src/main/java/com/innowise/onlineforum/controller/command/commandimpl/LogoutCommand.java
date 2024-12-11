package com.innowise.onlineforum.controller.command.commandimpl;

import com.innowise.onlineforum.controller.attribute.SessionAttribute;
import com.innowise.onlineforum.controller.command.ActionCommand;
import com.innowise.onlineforum.controller.command.CommandResult;
import com.innowise.onlineforum.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LogoutCommand implements ActionCommand {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
        }
        return (new CommandResult(SessionAttribute.LOGIN_PAGE, CommandResult.Type.REDIRECT));
    }
}