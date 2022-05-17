package ru.letmerent.core.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import ru.letmerent.core.converters.InstrumentConverter;
import ru.letmerent.core.dto.CriteriaSearch;
import ru.letmerent.core.dto.InstrumentDto;
import ru.letmerent.core.dto.InstrumentForListDto;
import ru.letmerent.core.dto.InstrumentInfoDto;
import ru.letmerent.core.dto.InstrumentRentDto;
import ru.letmerent.core.dto.PageDto;
import ru.letmerent.core.entity.Instrument;
import ru.letmerent.core.entity.User;
import ru.letmerent.core.exceptions.models.ApplicationError;
import ru.letmerent.core.security.AuthenticationFacade;
import ru.letmerent.core.services.InstrumentService;
import ru.letmerent.core.services.impl.UserService;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/v1/instruments")
@Tag(name = "API для работы с инструментом")
@RequiredArgsConstructor
public class InstrumentController {
    private static final String COULD_NOT_FIND_INSTRUMENT = "Could not find instrument with id: ";
    
    private final InstrumentService instrumentService;
    private final InstrumentConverter instrumentConverter;
    private final ObjectMapper mapper;
    private final UserService userService;
    private final AuthenticationFacade authenticationFacade;
    
    @Operation(summary = "Вывод информации по всем инструментам")
    @GetMapping
    @ApiResponse(
        responseCode = "200",
        description = "Список инструментов.",
        content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(
                schema = @Schema(
                    implementation = InstrumentForListDto.class))
        ))
    PageDto<InstrumentForListDto> getAllInstrument(@PageableDefault Pageable pageable, CriteriaSearch criteriaSearch) {
        return getInstruments( pageable, criteriaSearch);
    }
    
    @Operation(summary = "Вывод информации по всем инструментам пользователя")
    @GetMapping("/lk")
    @ApiResponse(
            responseCode = "200",
            description = "Список инструментов пользователя.",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(
                                    implementation = InstrumentForListDto.class))
            ))
    PageDto<InstrumentForListDto> getAllUserInstrument(@PageableDefault Pageable pageable, CriteriaSearch criteriaSearch) {
        if (!authenticationFacade.isAdmin()) {
            String username = authenticationFacade.getLogin();
            criteriaSearch.setOwnerName(username);
        }
    
        return getInstruments( pageable, criteriaSearch);
    }
    
    @Operation(summary = "Вывод информации по арендам своих инструментов")
    @GetMapping("/rent")
    @ApiResponse(
        responseCode = "200",
        description = "Список арендованных инструментов.",
        content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(
                schema = @Schema(
                    implementation = InstrumentRentDto.class))
        ))
    ResponseEntity<Object> getAllRentInstrument(@RequestParam("instrumentId") Long instrumentId) {
        Long userId = userService.findByUsername(authenticationFacade.getLogin()).getId();
        Collection<InstrumentRentDto> instrumentRents = instrumentService.getInstrumentRents(instrumentId, userId);
        
        return new ResponseEntity<>(instrumentRents, HttpStatus.OK);
    }
    
    @Operation(summary = "Информация по инструменту")
    @GetMapping("/{id}")
    @ApiResponse(
            responseCode = "200",
            description = "Информация по инструменту.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = InstrumentInfoDto.class))
    )
    ResponseEntity<Object> getInstrumentById(@Parameter(description = "Идентификатор инструмента", required = true) @PathVariable Long id) {
        Optional<Instrument> oInstrument = instrumentService.getInstrumentById(id);
        if (oInstrument.isEmpty()) {
            return ResponseEntity.badRequest()
                .body(mapper.valueToTree(ApplicationError.builder()
                    .errorCode(HttpStatus.BAD_REQUEST.value())
                    .userMessage(COULD_NOT_FIND_INSTRUMENT)
                    .date(new Date())
                    .build()));
        }
        
        InstrumentInfoDto infoDto = instrumentConverter.toInstrumentInfoDto(oInstrument.get());
        
        return new ResponseEntity<>(infoDto, HttpStatus.OK);
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
    @Transactional
    ResponseEntity<InstrumentInfoDto> addNewInstrument(@RequestBody InstrumentInfoDto instrumentDto, Principal principal, UriComponentsBuilder uriComponentsBuilder) {
        User user = userService.findByUsername(principal.getName());

        Instrument instrument = instrumentConverter.toInstrument(instrumentDto, user);

        Instrument newInstrument = instrumentService.createInstrument(instrument);

        InstrumentInfoDto instrumentInfoDto = instrumentConverter.toInstrumentInfoDto(newInstrument);

        return new ResponseEntity<>(instrumentInfoDto, HttpStatus.CREATED);
    }

//    @Operation(summary = "Информация по инструменту по имени пользователя") //todo конфликт мапинга с ru.letmerent.core.controllers.InstrumentController.getInstrumentById
//    @GetMapping("/{username}")
//    @ApiResponse(
//            responseCode = "200",
//            description = "Информация по инструменту.",
//            content = @Content(
//                    mediaType = "application/json",
//                    schema = @Schema(
//                            implementation = InstrumentDto.class))
//    )
//    ResponseEntity<InstrumentDto> getInstrumentByUsername(@Parameter(description = "Имя пользователя") @PathVariable String username) {
//        return new ResponseEntity<>(new InstrumentDto(), HttpStatus.OK);
//    }

    @Operation(summary = "Удаление инструмента")
    @DeleteMapping("/{id}")
    @ApiResponse(
            responseCode = "200",
            description = "Инструмент успешно удалён."
    )
    ResponseEntity<Object> deleteInstrument(@Parameter(description = "Идентификатор инструмента", required = true) @PathVariable Long id) {
        if(authenticationFacade.isAdmin()){//TODO: тут, как мне видится, должна быть проверка не только на роль админа, но и на то, что пользователь является вледельцем инструмента? Иначе функционал удаления своих инструментов из ЛК не сработает
            instrumentService.remove(id);
            return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
        }else
            return ResponseEntity.badRequest()
            .body(mapper.valueToTree(ApplicationError.builder()
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .userMessage("only admin can delete instruments")
                .date(new Date())
                .build()));
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
    ResponseEntity<InstrumentDto> modifyInstrument(@RequestBody InstrumentInfoDto instrumentInfoDto) {
        User user = userService.findByUsername(instrumentInfoDto.getOwnerUsername());
        Instrument instrument = instrumentConverter.toInstrument(instrumentInfoDto, user);
        return new ResponseEntity<>(instrumentConverter.toInstrumentInfoDto(instrumentService.updateInstrument(instrument)), HttpStatus.OK);
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
    
    private PageDto<InstrumentForListDto> getInstruments(Pageable pageable, CriteriaSearch criteriaSearch) {
        Page<Instrument> page = instrumentService.getAllInstruments(pageable, criteriaSearch);
        
        List<InstrumentForListDto> instrumentForListDtos = page.get()
            .map(instrumentConverter::toListDto).collect(toList());
        PageDto<InstrumentForListDto> pageDto = new PageDto<>();
        pageDto.setInstruments(instrumentForListDtos);
        pageDto.setFirstPage(page.isFirst());
        pageDto.setLastPage(page.isLast());
        pageDto.setNumber(page.getNumber());
        pageDto.setTotalPages(page.getTotalPages());
        pageDto.setTotalElements(page.getTotalElements());
        
        return pageDto;
    }
}
