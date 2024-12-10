package com.innowise.onlineforum.controller.command;

import com.innowise.onlineforum.controller.command.commandimpl.LoginCommand;
import com.innowise.onlineforum.controller.command.commandimpl.LogoutCommand;
import com.innowise.onlineforum.controller.command.commandimpl.RegisterCommand;
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
    CHANGE_LANGUAGE {
        {
            //this.command = new ChangeLanguageCommand();
            this.command = new ActionCommand() {
                @Override
                public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
                    return null;
                }
            };
        }
    };

    ActionCommand command;

    public ActionCommand getCurrentCommand() {
        return command;
    }
}
