package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.dto.ErrorResponseDto;
import com.eazybytes.accounts.dto.ResponseDto;
import com.eazybytes.accounts.service.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag( //swagger tag annotation. It is used to group and describe a set of related API endpoints in the generated OpenAPI documentation.
        name = "CRUD REST APIs for Accounts in EazyBank", // Name of the tag that categorizes the API endpoints, providing a clear and concise label for the group of endpoints related to account management in EazyBank.
        description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH AND DELETE account details" // Description of the tag that provides additional context about the purpose and functionality of the grouped API endpoints, highlighting that they enable Create, Read, Update, and Delete operations for account details in EazyBank.
)
@RestController // Marks this class as a RESTful web service controller, allowing it to handle HTTP requests and return JSON/XML responses
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE}) // Sets the base URL path for all endpoints in this controller to "/api" and specifies that all responses will be in JSON format
@RequiredArgsConstructor
@Validated // Enables validation for method parameters in this controller, such as @RequestParam and @PathVariable, this tells Spring to enforce validation rules on the parameters of the methods within this controller.
public class AccountsController {

    private final IAccountsService iAccountsService;


    @Operation( //swagger operation annotation. It is used to describe a single API operation or endpoint in the generated OpenAPI documentation.
            summary = "Create Account REST API", // A brief summary of what the API operation does, providing a concise overview of its purpose, which in this case is to create a new account.
            description = "REST API to create new Customer &  Account inside EazyBank" // A more detailed description of the API operation, explaining its functionality and purpose, specifically stating that this REST API is designed to create a new customer and account within the EazyBank system.
    )
    @ApiResponses({ //swagger api responses annotation. It is used to document the possible responses for an API operation in the generated OpenAPI documentation. For example, it specifies the HTTP status codes, descriptions, and response schemas for different scenarios.
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),

            // This Defines a Schema Object In API response for HTTP status code 500 (Internal Server Error)
            @ApiResponse( //swagger api response annotation. It is used to document a single possible response for an API operation in the generated OpenAPI documentation.
                    responseCode = "500", // The HTTP status code for the response, indicating that this response corresponds to an internal server error.
                    description = "HTTP Status Internal Server Error", // A brief description of the response, indicating that it represents an internal server error.
                    content = @Content( //swagger content annotation. It is used to describe the content of a response or request body in the generated OpenAPI documentation.
                            schema = @Schema(implementation = ErrorResponseDto.class) // With this i am telling to my Spring documentation. For this Update Operation when there is a 500 error, the response body will follow the structure defined in the ErrorResponseDto class.
                    )
            )
    }
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) { // @Valid annotation is used to trigger validation on the CustomerDto object based on the constraints defined in its class.
        iAccountsService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }



    @Operation(
            summary = "Fetch Account Details REST API",
            description = "REST API to fetch Customer &  Account details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam
                                                           @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") // Validation to ensure the mobile number is exactly 10 digits
                                                           String mobileNumber) {
        CustomerDto customerDto = iAccountsService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }


    @Operation(
            summary = "Update Account Details REST API",
            description = "REST API to update Customer &  Account details based on a account number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) { // @Valid annotation is used to trigger validation on the CustomerDto object based on the constraints defined in its class.
        boolean isUpdated = iAccountsService.updateAccount(customerDto);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
        }
    }


    @Operation(
            summary = "Delete Account & Customer Details REST API",
            description = "REST API to delete Customer &  Account details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam
                                                            @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") // Validation to ensure the mobile number is exactly 10 digits
                                                            String mobileNumber) {
        boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
        }
    }


}