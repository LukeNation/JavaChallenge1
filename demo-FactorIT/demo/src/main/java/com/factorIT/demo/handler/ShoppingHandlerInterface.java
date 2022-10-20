package com.factorIT.demo.handler;

import com.factorIT.demo.dto.request.CreateCartRequest;
import com.factorIT.demo.model.Shop;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.awt.print.Book;

public interface ShoppingHandlerInterface {

    @Operation(summary = "Get a book by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart Created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Shop.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Cart not found",
                    content = @Content) })
    ResponseEntity<String> createCart(@RequestBody CreateCartRequest request);
}
