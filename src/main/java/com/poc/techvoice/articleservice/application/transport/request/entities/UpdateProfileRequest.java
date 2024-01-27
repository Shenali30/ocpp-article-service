package com.poc.techvoice.articleservice.application.transport.request.entities;

import com.poc.techvoice.articleservice.application.validator.RequestEntityInterface;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UpdateProfileRequest implements RequestEntityInterface {

    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String name;
    @NotBlank
    private String profileDescription;
    @NotBlank
    private String countryOfOrigin;
}
