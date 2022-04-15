package ru.letmerent.core.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import ru.letmerent.core.dto.InstrumentDto;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/api/v1/instruments")
@Tag(name = "API для работы с инструментом")
public class InstrumentController {

    @Operation(summary = "Вывод информации по всем инструментам")
    @GetMapping
    @ApiResponse(
            responseCode = "200",
            description = "Список инструментов.",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(
                                    implementation = InstrumentDto.class))
            ))
    ResponseEntity<Collection<InstrumentDto>> getAllInstrument() {
        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
    }

    @Operation(summary = "Информация по инструменту")
    @GetMapping("/{id}")
    @ApiResponse(
            responseCode = "200",
            description = "Информация по инструменту.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = InstrumentDto.class))
    )
    ResponseEntity<InstrumentDto> getInstrumentById(@Parameter(description = "Идентификатор инструмента", required = true) @PathVariable Long id) {
        return new ResponseEntity<>(new InstrumentDto(), HttpStatus.OK);
    }

    @Operation(summary = "Добавление нового инструмента")
    @ApiResponse(
            responseCode = "201",
            description = "Инструмент успешно создан. Ссылка на созданный инструмент в поле заголовка `Location`.",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(
                                    implementation = InstrumentDto.class))
            ))
    @PostMapping
    ResponseEntity<InstrumentDto> addNewInstrument(@RequestBody InstrumentDto instrument, UriComponentsBuilder uriComponentsBuilder) {
        return new ResponseEntity<>(new InstrumentDto(), HttpStatus.CREATED);
    }

    @Operation(summary = "Информация по инструменту по имени пользователя")
    @GetMapping("/{username}")
    @ApiResponse(
            responseCode = "200",
            description = "Информация по инструменту.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = InstrumentDto.class))
    )
    ResponseEntity<InstrumentDto> getInstrumentByUsername(@Parameter(description = "Имя пользователя") @PathVariable String username) {
        return new ResponseEntity<>(new InstrumentDto(), HttpStatus.OK);
    }

    @Operation(summary = "Удаление инструмента")
    @DeleteMapping("/{id}")
    @ApiResponse(
            responseCode = "200",
            description = "Пользователь успешно удалён."
    )
    ResponseEntity<Boolean> deleteInstrument(@Parameter(description = "Идентификатор инструмента", required = true) @PathVariable Long id) {
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @Operation(summary = "Удаление инструмента")
    @DeleteMapping()
    @ApiResponse(
            responseCode = "200",
            description = "Пользователь успешно удалён."
    )
    ResponseEntity<Boolean> deleteInstrument(@RequestBody InstrumentDto instrument) {
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @Operation(summary = "Изменение информации по инструменту")
    @PutMapping
    @ApiResponse(
            responseCode = "200",
            description = "Инструмент успешно изменён.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = InstrumentDto.class))
    )
    ResponseEntity<InstrumentDto> modifyInstrument(@RequestBody InstrumentDto instrument) {
        return new ResponseEntity<>(new InstrumentDto(), HttpStatus.OK);
    }

    @Operation(summary = "Изменение цены инструмента")
    @PutMapping("/{id}")
    @ApiResponse(
            responseCode = "200",
            description = "Цена инструмента успешно изменена.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = InstrumentDto.class))
    )
    ResponseEntity<InstrumentDto> changeInstrumentPrice(@Parameter(description = "Идентификатор инструмента", required = true) @PathVariable Long id,
                                     @RequestParam BigDecimal price) {
        return new ResponseEntity<>(new InstrumentDto(), HttpStatus.OK);
    }
}
