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
    private final TopicService topicService = TopicServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String method = request.getMethod();
        if ("GET".equalsIgnoreCase(method)) {
            return new CommandResult(PagePath.CREATE_TOPIC, CommandResult.Type.FORWARD);
        } else if ("POST".equalsIgnoreCase(method)) {
            HttpSession session = request.getSession(false);
            if (session == null) {
                return new CommandResult(PagePath.LOGIN, CommandResult.Type.REDIRECT);
            }

            User user = (User) session.getAttribute(SessionAttribute.USER);
            if (user == null) {
                return new CommandResult(PagePath.LOGIN, CommandResult.Type.REDIRECT);
            }

            String title = request.getParameter(PARAM_TITLE);
            String body = request.getParameter(PARAM_BODY);
            String category = request.getParameter(PARAM_CATEGORY);

            // Собираем поля в карту для валидации и создания топика
            Map<String, String> fields = new HashMap<>();
            fields.put(PARAM_TITLE, title);
            fields.put(PARAM_BODY, body);
            fields.put(PARAM_CATEGORY, category);
            fields.put("userId", String.valueOf(user.getId())); // Добавляем userId для сервиса

            try {
                boolean isCreated = topicService.createTopic(fields);
                if (isCreated) {
                    return new CommandResult(PagePath.TOPICS, CommandResult.Type.REDIRECT);
                } else {
                    // Если создание не удалось, вероятно, есть ошибки валидации
                    request.setAttribute("errorInputData", fields); // Передаём карту с ошибками
                    return new CommandResult(PagePath.CREATE_TOPIC, CommandResult.Type.FORWARD);
                }
            } catch (ServiceException e) {
                throw new CommandException("Error creating topic.", e);
            }
        }
        return new CommandResult(PagePath.TOPICS, CommandResult.Type.REDIRECT);
    }
}
