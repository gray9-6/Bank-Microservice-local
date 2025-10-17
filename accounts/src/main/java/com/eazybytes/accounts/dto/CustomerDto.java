package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(  // Swagger annotation to describe the schema.It Tells that this class represents a Customer schema in the API documentation.
        name = "Customer", // Name of the schema. It is set to "Customer".
        description = "Schema to hold Customer and Account information" // Description of the schema.
)
public class CustomerDto {


    @Schema(
            name = "name", // Name of the field in the schema.
            description = "Name of the customer", // Description of the name field.
            example = "Eazy Bytes" // Example value for the name field.
    )
    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 5,max = 30,message = "Name should be between 5 and 30 characters")
    private String name;

    @Schema(
            description = "Email address of the customer", example = "tutor@eazybytes.com"
    )
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;

    @Schema(
            description = "Mobile Number of the customer", example = "9345432123"
    )
    @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Schema(
            description = "Account details of the Customer"
    )
    private AccountsDto accountsDto;
}