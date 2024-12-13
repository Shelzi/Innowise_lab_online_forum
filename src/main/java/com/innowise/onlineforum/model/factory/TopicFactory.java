package com.innowise.onlineforum.model.factory;

import com.innowise.onlineforum.controller.attribute.RequestParameter;
import com.innowise.onlineforum.model.entity.Topic;
import com.innowise.onlineforum.validation.TopicValidator;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TopicFactory implements EntityFactory<Topic> {
    private static final Lock locker = new ReentrantLock();
    private static EntityFactory<Topic> instance;

    private TopicFactory() {
    }

    public static EntityFactory<Topic> getInstance() {
        if (instance == null) {
            locker.lock();
            try {
                if (instance == null) {
                    instance = new TopicFactory();
                }
            } finally {
                locker.unlock();
            }
        }
        return instance;
    }

    @Override
    public Optional<Topic> create(Map<String, String> fields) {
        if (TopicValidator.isCreateTopicFormValid(fields)) {
            String title = fields.get(RequestParameter.TOPIC_TITLE).trim();
            String body = fields.get(RequestParameter.TOPIC_BODY).trim();
            String category = fields.get(RequestParameter.TOPIC_TITLE).trim();

            Topic topic = Topic.builder()
                    .title(title)
                    .body(body)
                    .category(category)
                    .build();

            return Optional.of(topic);
        }
        return Optional.empty();
    }
}
