package com.innowise.onlineforum.controller.command.commandimpl;

import com.innowise.onlineforum.controller.attribute.JspAttribute;
import com.innowise.onlineforum.controller.attribute.PagePath;
import com.innowise.onlineforum.controller.attribute.RequestParameter;
import com.innowise.onlineforum.controller.attribute.SessionAttribute;
import com.innowise.onlineforum.controller.command.ActionCommand;
import com.innowise.onlineforum.controller.command.CommandResult;
import com.innowise.onlineforum.exception.CommandException;
import com.innowise.onlineforum.exception.ServiceException;
import com.innowise.onlineforum.model.entity.User;
import com.innowise.onlineforum.model.service.TopicService;
import com.innowise.onlineforum.model.service.serviceimpl.TopicServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

public class CreateTopicCommand implements ActionCommand {
    private final TopicService topicService = TopicServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(SessionAttribute.USER);
        Map<String, String> fields = createFieldsMap(request, user);

        try {
            boolean isCreated = topicService.createTopic(fields);
            if (isCreated) {
                return new CommandResult(PagePath.TOPICS, CommandResult.Type.REDIRECT);
            } else {
                request.setAttribute(JspAttribute.ERROR_INPUT_DATA, "Failed to create topic.");
                return new CommandResult(PagePath.CREATE_TOPIC, CommandResult.Type.FORWARD);
            }
        } catch (ServiceException e) {
            throw new CommandException("Error creating topic.", e);
        }
    }

    private Map<String, String> createFieldsMap(HttpServletRequest request, User user) {
        Map<String, String> fields = new HashMap<>();
        fields.put(RequestParameter.TOPIC_TITLE, request.getParameter(RequestParameter.TOPIC_TITLE));
        fields.put(RequestParameter.TOPIC_BODY, request.getParameter(RequestParameter.TOPIC_BODY));
        fields.put(RequestParameter.TOPIC_CATEGORY, request.getParameter(RequestParameter.TOPIC_CATEGORY));
        fields.put(SessionAttribute.USER_ID, String.valueOf(user.getId()));
        return fields;
    }
}