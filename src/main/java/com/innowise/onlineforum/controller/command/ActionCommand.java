package com.innowise.onlineforum.controller.command;

import com.innowise.onlineforum.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ActionCommand {
    CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;
}
