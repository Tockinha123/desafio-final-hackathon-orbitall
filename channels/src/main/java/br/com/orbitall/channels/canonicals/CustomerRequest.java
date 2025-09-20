package br.com.orbitall.channels.canonicals;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerRequest(

        @NotBlank(message = "This field is mandatory")
        @Size(max = 255, message = "This field must be between 1 and 255 characters")
        String fullName,

        @Email
        @NotBlank(message = "This field is mandatory")
        @Size(max = 100, message = "This field must be between 1 and 255 characters")
        String email,

        @NotBlank(message = "This field is mandatory")
        String phone
) {
}
