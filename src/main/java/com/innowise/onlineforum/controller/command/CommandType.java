package com.innowise.onlineforum.controller.command;

import com.innowise.onlineforum.controller.command.commandimpl.LoginCommand;
import com.innowise.onlineforum.controller.command.commandimpl.LogoutCommand;
import com.innowise.onlineforum.controller.command.commandimpl.RegisterCommand;
import com.innowise.onlineforum.controller.command.commandimpl.TopicsListCommand;
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
    };

    ActionCommand command;

    public ActionCommand getCurrentCommand() {
        return command;
    }
}
