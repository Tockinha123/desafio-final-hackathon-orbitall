package br.com.orbitall.channels.canonicals;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionRequest(

        @NotNull(message = "This field is mandatory")
        UUID customerId,

        @NotNull(message = "This field is mandatory")
        @Min(value = 1, message = "The amount must be greater than zero")
        BigDecimal amount,

        @NotBlank(message = "This field is mandatory")
        @Pattern(regexp = "DÉBITO|CRÉDITO", message = "The card type must be either debit or credit only.")
        String cardType
) {
}
