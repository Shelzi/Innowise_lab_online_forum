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
import com.innowise.onlineforum.model.entity.UserRole;
import com.innowise.onlineforum.model.service.TopicService;
import com.innowise.onlineforum.model.service.serviceimpl.TopicServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteTopicCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();
    private final TopicService topicService = TopicServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        String topicIdStr = request.getParameter(RequestParameter.TOPIC_ID);
        CommandResult result = new CommandResult(PagePath.TOPICS, CommandResult.Type.REDIRECT);

        try {
            long topicId = Long.parseLong(topicIdStr);
            long userId = (long) session.getAttribute(SessionAttribute.USER_ID);
            if (!topicService.deleteTopic(topicId, userId)) {
                result = new CommandResult(PagePath.TOPICS, CommandResult.Type.FORWARD);
                request.setAttribute(JspAttribute.ERROR_TOPIC_DELETE_ATTRIBUTE, "Failed to delete the topic.");
            }
        } catch (ServiceException | NumberFormatException e) {
            logger.log(Level.ERROR, "Failed to delete a topic with id = " + topicIdStr + ": " + e);
            throw new CommandException(e);
        }

        return result;
    }
}
