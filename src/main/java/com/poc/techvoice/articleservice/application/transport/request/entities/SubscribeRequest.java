package com.poc.techvoice.articleservice.application.transport.request.entities;

import com.poc.techvoice.articleservice.application.validator.RequestEntityInterface;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SubscribeRequest implements RequestEntityInterface {

    @Email
    @NotBlank
    private String userEmail;
    @NotNull
    private Integer categoryToSubscribe;
}
