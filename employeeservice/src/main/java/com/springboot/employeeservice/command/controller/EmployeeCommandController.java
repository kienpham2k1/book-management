package com.springboot.employeeservice.command.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.employeeservice.command.command.CreateEmployeeCommand;
import com.springboot.employeeservice.command.command.DeleteEmployeeCommand;
import com.springboot.employeeservice.command.command.UpdateEmployeeCommand;
import com.springboot.employeeservice.command.model.EmployeeRequestModel;
import com.springboot.employeeservice.kafka.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import org.springframework.cloud.stream.messaging.Source;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/employees")
@Slf4j
public class EmployeeCommandController {
    @Autowired
    CommandGateway commandGateway;
    @Autowired
    private KafkaProducer kafkaProducer;

    @PostMapping
    public void addEmployee(@RequestBody EmployeeRequestModel model) {
        CreateEmployeeCommand command = new CreateEmployeeCommand(UUID.randomUUID().toString(), model.getFirstName(), model.getLastName(), model.getKin(), model.getIsDisciplined());
        commandGateway.sendAndWait(command);
    }

    @PutMapping("/{employeeId}")
    public void addEmployee(@PathVariable String employeeId, @RequestBody EmployeeRequestModel model) {
        UpdateEmployeeCommand command = new UpdateEmployeeCommand(employeeId, model.getFirstName(), model.getLastName(), model.getKin(), model.getIsDisciplined());
        commandGateway.sendAndWait(command);
    }

    @DeleteMapping("/{employeeId}")
    public void deleteEmployee(@PathVariable String employeeId) {
        DeleteEmployeeCommand command = new DeleteEmployeeCommand(employeeId);
        commandGateway.sendAndWait(command);
    }

    @PostMapping("/sendMessage")
    public void SendMessage(@RequestBody String message) {
        log.info("Kafka send message: {} ", message);
        kafkaProducer.sendMessage(message);
    }
}
