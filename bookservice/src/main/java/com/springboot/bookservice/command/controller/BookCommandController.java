package com.springboot.bookservice.command.controller;

import com.springboot.bookservice.command.command.CreateBookCommand;
import com.springboot.bookservice.command.command.DeleteBookCommand;
import com.springboot.bookservice.command.command.UpdateBookCommand;
import com.springboot.bookservice.command.model.BookRequestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
public class BookCommandController {
    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public String addBook(@RequestBody BookRequestModel model) {
        CreateBookCommand command =
                new CreateBookCommand(UUID.randomUUID().toString(), model.getName(), model.getAuthor(), true);
        commandGateway.sendAndWait(command);
        return "added Book";
    }

    @PutMapping("/{bookId}")
    public String updateBook(@PathVariable String bookId,
                             @RequestBody BookRequestModel model) {
        UpdateBookCommand command =
                new UpdateBookCommand(bookId, model.getName(), model.getAuthor(), model.isReady());
        commandGateway.sendAndWait(command);
        return "update Book";
    }

    @DeleteMapping("/{bookId}")
    public String deleteBNook(@PathVariable String bookId
    ) {
        DeleteBookCommand command =
                new DeleteBookCommand(bookId);
        commandGateway.sendAndWait(command);
        return "deleted Book";
    }
}
