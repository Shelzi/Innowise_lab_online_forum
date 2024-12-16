package com.innowise.onlineforum.controller.command.commandimpl;

import com.innowise.onlineforum.controller.attribute.PagePath;
import com.innowise.onlineforum.controller.attribute.SessionAttribute;
import com.innowise.onlineforum.controller.command.ActionCommand;
import com.innowise.onlineforum.controller.command.CommandResult;
import com.innowise.onlineforum.exception.CommandException;
import com.innowise.onlineforum.model.entity.User;
import com.innowise.onlineforum.model.service.TopicService;
import com.innowise.onlineforum.model.service.serviceimpl.TopicServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

public class DeleteTopicCommand implements ActionCommand {


    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        return null;
    }
}
