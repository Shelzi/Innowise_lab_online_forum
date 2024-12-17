package com.innowise.onlineforum.controller.command.commandimpl;

import com.innowise.onlineforum.controller.attribute.PagePath;
import com.innowise.onlineforum.controller.attribute.RequestParameter;
import com.innowise.onlineforum.controller.command.ActionCommand;
import com.innowise.onlineforum.controller.command.CommandResult;
import com.innowise.onlineforum.exception.CommandException;
import com.innowise.onlineforum.exception.ServiceException;
import com.innowise.onlineforum.model.entity.Topic;
import com.innowise.onlineforum.model.service.TopicService;
import com.innowise.onlineforum.model.service.serviceimpl.TopicServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class ViewTopicCommand implements ActionCommand {
    private final TopicService topicService = TopicServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String topicIdStr = request.getParameter(RequestParameter.TOPIC_ID);
        if (topicIdStr == null || topicIdStr.trim().isEmpty()) {
            return new CommandResult(PagePath.ERROR, CommandResult.Type.FORWARD); //404
        }

        Long topicId;
        try {
            topicId = Long.parseLong(topicIdStr);
        } catch (NumberFormatException e) {
            return new CommandResult(PagePath.ERROR, CommandResult.Type.FORWARD);
        }

        try {
            Optional<Topic> topicOpt = topicService.getTopicById(topicId);
            if (topicOpt.isPresent()) {
                request.setAttribute("topic", topicOpt.get());
                return new CommandResult(PagePath.VIEW_TOPIC_JSP, CommandResult.Type.FORWARD);
            } else {
                return new CommandResult(PagePath.ERROR, CommandResult.Type.FORWARD);
            }
        } catch (ServiceException e) {
            throw new CommandException("Error viewing topic.", e);
        }
    }
}
