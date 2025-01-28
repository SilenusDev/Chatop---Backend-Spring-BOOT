package com.openclassrooms.api.controllers;

import com.openclassrooms.api.dto.MessageRequestDTO;
import com.openclassrooms.api.dto.MessageResponseDTO;
import com.openclassrooms.api.dto.RentalDTO;
import com.openclassrooms.api.models.ErrorResponse;
import com.openclassrooms.api.services.MessageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Operation(summary = "Create a new message", description = "Creates a new message")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "message created successfully",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = RentalDTO.class))),
    @ApiResponse(responseCode = "400", description = "Invalid input",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
    @ApiResponse(responseCode = "500", description = "Internal server error",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    })
    @PostMapping
    public ResponseEntity<MessageResponseDTO> createMessage(
        @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = MessageRequestDTO.class),
                examples = @ExampleObject(
                    name = "Message Example",
                    value = "{\"rentalId\": 1, \"userId\": 1, \"message\": \"Je suis intéressé par votre location\"}"
                )
            )
        ) MessageRequestDTO request) {
        MessageResponseDTO response = messageService.createMessage(request);
        return ResponseEntity.ok(response);
    }
}
