package com.innowise.onlineforum.controller.command;

import com.innowise.onlineforum.controller.command.commandimpl.LoginCommand;
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
            //this.command = new LogoutCommand();
            this.command = new ActionCommand() {
                @Override
                public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
                    return null;
                }
            };
        }
    },
    REGISTER {
        {
            //this.command = new RegisterCommand();
            this.command = new ActionCommand() {
                @Override
                public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
                    return null;
                }
            };
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
