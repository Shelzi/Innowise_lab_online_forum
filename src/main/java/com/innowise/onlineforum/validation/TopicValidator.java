package com.innowise.onlineforum.validation;

import com.innowise.onlineforum.controller.attribute.RequestParameter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TopicValidator {
    private static final Logger logger = LogManager.getLogger();
    private static final Pattern TITLE_PATTERN = Pattern.compile(".{3,100}");
    private static final Pattern CATEGORY_PATTERN = Pattern.compile("[a-zA-Z0-9_\\- ]{3,50}");

    private TopicValidator() {
    }

    public static boolean isCreateTopicFormValid(Map<String, String> fields) {
        boolean result = true;

        String title = fields.get(RequestParameter.TOPIC_TITLE);
        if (!isTitleValid(title)) {
            fields.put(RequestParameter.TOPIC_TITLE, "Invalid title. It must be between 3 and 100 characters.");
            result = false;
        }

        String body = fields.get(RequestParameter.TOPIC_BODY);
        if (body == null || body.trim().isEmpty()) {
            fields.put(RequestParameter.TOPIC_BODY, "Body cannot be empty.");
            result = false;
        }

        String category = fields.get(RequestParameter.TOPIC_CATEGORY);
        if (!isCategoryValid(category)) {
            fields.put(RequestParameter.TOPIC_CATEGORY, "Invalid category. It must be between 3 and 50 characters.");
            result = false;
        }

        return result;
    }

    public static boolean isTitleValid(String title) {
        if (title == null) {
            return false;
        }
        Matcher matcher = TITLE_PATTERN.matcher(title);
        boolean result = matcher.matches();
        if (!result) {
            logger.log(Level.DEBUG, "Title is invalid: " + title);
        }
        return result;
    }

    public static boolean isCategoryValid(String category) {
        if (category == null) {
            return false;
        }
        Matcher matcher = CATEGORY_PATTERN.matcher(category);
        boolean result = matcher.matches();
        if (!result) {
            logger.log(Level.DEBUG, "Category is invalid: " + category);
        }
        return result;
    }
}
