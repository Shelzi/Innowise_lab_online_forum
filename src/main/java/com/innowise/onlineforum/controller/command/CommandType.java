package com.innowise.onlineforum.controller.command;

import com.innowise.onlineforum.controller.command.commandimpl.*;
import com.innowise.onlineforum.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public enum CommandType {
    LOGIN {
        {
            this.command = new LoginCommand();
        }
    },
    LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    },
    REGISTER {
        {
            this.command = new RegisterCommand();
        }
    },
    TOPIC_LIST {
        {
            this.command = new TopicsListCommand();
        }
    },
    DELETE_TOPIC {
        {
            this.command = new DeleteTopicCommand();
        }
    },
    CREATE_TOPIC {
        {
            this.command = new CreateTopicCommand();
        }
    };

    ActionCommand command;

    public ActionCommand getCurrentCommand() {
        return command;
    }
}
