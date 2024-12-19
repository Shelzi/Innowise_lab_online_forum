package com.innowise.onlineforum.controller.command.commandimpl;

import com.innowise.onlineforum.controller.attribute.JspAttribute;
import com.innowise.onlineforum.controller.attribute.PagePath;
import com.innowise.onlineforum.controller.attribute.RequestParameter;
import com.innowise.onlineforum.controller.command.ActionCommand;
import com.innowise.onlineforum.controller.command.CommandResult;
import com.innowise.onlineforum.exception.CommandException;
import com.innowise.onlineforum.exception.ServiceException;
import com.innowise.onlineforum.model.entity.Topic;
import com.innowise.onlineforum.model.service.TopicService;
import com.innowise.onlineforum.model.service.serviceimpl.TopicServiceImpl;
import com.innowise.onlineforum.util.MessageManagerUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class TopicsListCommand implements ActionCommand {
    private static final TopicService topicService = TopicServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String category = request.getParameter(RequestParameter.CATEGORY_NAME);
        try {
            List<Topic> topics;
            if (category != null && !category.trim().isEmpty()) {
                topics = topicService.getTopicsByCategory(category.trim());
                request.setAttribute(JspAttribute.CURRENT_CATEGORY, category.trim());
            } else {
                topics = topicService.getAllTopics();
            }
            request.setAttribute("topicList", topics);
            return new CommandResult(PagePath.TOPICS, CommandResult.Type.FORWARD);
        } catch (ServiceException e) {
            // todo logger
            request.setAttribute(JspAttribute.ERROR_INPUT_DATA_ATTRIBUTE, MessageManagerUtil.getProperty("message.serviceError"));
            return new CommandResult(PagePath.ERROR, CommandResult.Type.FORWARD); // todo make 500
        }
    }
}
