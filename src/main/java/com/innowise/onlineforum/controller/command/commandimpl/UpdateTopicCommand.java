package com.innowise.onlineforum.controller.command.commandimpl;

import com.innowise.onlineforum.controller.attribute.JspAttribute;
import com.innowise.onlineforum.controller.attribute.PagePath;
import com.innowise.onlineforum.controller.attribute.RequestParameter;
import com.innowise.onlineforum.controller.attribute.SessionAttribute;
import com.innowise.onlineforum.controller.command.ActionCommand;
import com.innowise.onlineforum.controller.command.CommandResult;
import com.innowise.onlineforum.exception.CommandException;
import com.innowise.onlineforum.exception.ServiceException;
import com.innowise.onlineforum.model.service.TopicService;
import com.innowise.onlineforum.model.service.serviceimpl.TopicServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

public class UpdateTopicCommand implements ActionCommand {
    private final TopicService service = TopicServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        String topicIdStr = request.getParameter(RequestParameter.TOPIC_ID);
        String title = request.getParameter(RequestParameter.TOPIC_TITLE);
        String body = request.getParameter(RequestParameter.TOPIC_BODY);
        String category = request.getParameter(RequestParameter.TOPIC_CATEGORY);
        String pinnedStr = request.getParameter(RequestParameter.TOPIC_PINNED);
        String ratingStr = request.getParameter(RequestParameter.TOPIC_RATING);

        long userId = (long) session.getAttribute(SessionAttribute.USER_ID);

        Map<String, String> fields = new HashMap<>();
        fields.put(RequestParameter.TOPIC_ID, topicIdStr);
        fields.put(RequestParameter.TOPIC_TITLE, title);
        fields.put(RequestParameter.TOPIC_BODY, body);
        fields.put(RequestParameter.TOPIC_CATEGORY, category);
        fields.put(RequestParameter.TOPIC_PINNED, pinnedStr);
        fields.put(RequestParameter.TOPIC_RATING, ratingStr);
        fields.put(SessionAttribute.USER_ID, String.valueOf(userId));

        CommandResult result = new CommandResult(PagePath.TOPICS, CommandResult.Type.REDIRECT);

        try {
            boolean isUpdated = service.updateTopic(fields);
            if (!isUpdated) {
                result = new CommandResult(PagePath.UPDATE_TOPIC_JSP, CommandResult.Type.FORWARD);
                request.setAttribute(JspAttribute.ERROR_TOPIC_UPDATE_ATTRIBUTE, "Failed to update the topic.");
            }
        } catch (ServiceException | NumberFormatException e) {
            throw new CommandException("Error updating topic.", e);
        }

        return result;
    }
}
