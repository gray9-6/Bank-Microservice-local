package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;


@Schema(  // Swagger annotation to describe the schema. It tells that this class represents a Response schema in the API documentation.
        name = "Response", // Name of the schema. It is set to "Response".
        description = "Schema to hold successful response information" // Description of the schema.
)
@Data @AllArgsConstructor
public class ResponseDto {
    @Schema(
            description = "Status code in the response"
    )
    private String statusCode;

    @Schema(
            description = "Status message in the response"
    )
    private String statusMsg;
}