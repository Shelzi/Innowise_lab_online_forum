package com.innowise.onlineforum.controller.command.commandimpl;

import com.innowise.onlineforum.controller.attribute.PagePath;
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
    private static final String PARAM_TITLE = "title";
    private static final String PARAM_BODY = "body";
    private static final String PARAM_CATEGORY = "category";
    private static final String USER_ID = "userId";
    private static final String ERROR_INPUT_DATA = "errorInputData";
    private static final String GET_METHOD = "GET";
    private static final String POST_METHOD = "POST";
    private final TopicService topicService = TopicServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String method = request.getMethod();
        if (GET_METHOD.equalsIgnoreCase(method)) {
            return new CommandResult(PagePath.CREATE_TOPIC, CommandResult.Type.FORWARD);
        } else if (POST_METHOD.equalsIgnoreCase(method)) {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute(SessionAttribute.USER) == null) {
                return new CommandResult(PagePath.LOGIN, CommandResult.Type.REDIRECT);
            }

            User user = (User) session.getAttribute(SessionAttribute.USER);
            Map<String, String> fields = createFieldsMap(request, user);

            try {
                boolean isCreated = topicService.createTopic(fields);
                if (isCreated) {
                    return new CommandResult(PagePath.TOPICS, CommandResult.Type.REDIRECT);
                } else {
                    request.setAttribute(ERROR_INPUT_DATA, fields);
                    return new CommandResult(PagePath.CREATE_TOPIC, CommandResult.Type.FORWARD);
                }
            } catch (ServiceException e) {
                throw new CommandException("Error creating topic.", e);
            }
        }
        return new CommandResult(PagePath.TOPICS, CommandResult.Type.REDIRECT);
    }

    private Map<String, String> createFieldsMap(HttpServletRequest request, User user) {
        Map<String, String> fields = new HashMap<>();
        fields.put(PARAM_TITLE, request.getParameter(PARAM_TITLE));
        fields.put(PARAM_BODY, request.getParameter(PARAM_BODY));
        fields.put(PARAM_CATEGORY, request.getParameter(PARAM_CATEGORY));
        fields.put(USER_ID, String.valueOf(user.getId()));
        return fields;
    }
}