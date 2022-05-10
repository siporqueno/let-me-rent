package ru.letmerent.core.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import ru.letmerent.core.dto.comments.InstrumentCommentDto;
import ru.letmerent.core.dto.comments.UserCommentDto;
import ru.letmerent.core.exceptions.models.ApplicationError;
import ru.letmerent.core.services.comments.InstrumentCommentService;
import ru.letmerent.core.services.comments.UserCommentsService;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Tag(name = "API для работы с сервисов комментариев")
@RequestMapping("/api/v1/comments")
public class CommentController {
    private static final String HEADER = "about";
    private static final String USER = "user";
    private static final String INSTR = "instrument";

    private final InstrumentCommentService instrumentCommentService;
    private final UserCommentsService userCommentsService;
    private final ObjectMapper mapper;

    @Operation(summary = "Добавление нового комментария")
    @PostMapping
    @ApiResponse(responseCode = "202", description = "Успешное добавление нового комментария")
    @ApiResponse(responseCode = "400", description = "Ошибка выполнения запроса",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApplicationError.class)))
    public ResponseEntity<?> addNewComment(@NotNull @RequestHeader("about") String about,
                                           @Schema(oneOf = {InstrumentCommentDto.class, UserCommentDto.class})
                                           @RequestBody Object commentDto) {
        switch (about) {
            case USER:
                UserCommentDto userCommentDto = mapper.convertValue(commentDto, UserCommentDto.class);
                userCommentsService.addNewComment(userCommentDto);
                return ResponseEntity.accepted().build();
            case INSTR:
                InstrumentCommentDto instrumentCommentDto = mapper.convertValue(commentDto, InstrumentCommentDto.class);
                instrumentCommentService.addNewComment(instrumentCommentDto);
                return ResponseEntity.accepted().build();
        }
        return generateError();
    }

    @Operation(summary = "Запрос комментариев")
    @GetMapping
    @ApiResponse(responseCode = "200", description = "Список комментариев",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(oneOf = {InstrumentCommentDto.class, UserCommentDto.class})))
    @ApiResponse(responseCode = "400", description = "Ошибка выполнения запроса",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApplicationError.class)))
    public ResponseEntity<?> getComments(@RequestHeader(HEADER) String about,
                                         @NotNull @RequestParam("id") Long id) {
        switch (about) {
            case USER:
                return ResponseEntity.ok(userCommentsService.getCommentsAboutUser(id));
            case INSTR:
                return ResponseEntity.ok(instrumentCommentService.getCommentsAboutInstrument(id));
        }
        return generateError();
    }

    @Operation(summary = "Запрос грейда")
    @GetMapping("/grade")
    @ApiResponse(responseCode = "200", description = "Грейд",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Double.class)))
    @ApiResponse(responseCode = "400", description = "Ошибка выполнения запроса",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApplicationError.class)))
    public ResponseEntity<?> getGrade(@RequestHeader(HEADER) String about,
                                      @NotNull @RequestParam("id") Long id) {
        switch (about) {
            case USER:
                return ResponseEntity.ok(mapper.valueToTree(userCommentsService.getUserGrade(id)));
            case INSTR:
                return ResponseEntity.ok(mapper.valueToTree(instrumentCommentService.getInstrumentGrade(id)));
        }
        return generateError();
    }

    private ResponseEntity<?> generateError() {
        return ResponseEntity.badRequest()
                .body(ApplicationError.builder()
                        .userMessage("Не поддерживаемый заголовок запроса")
                        .date(new Date())
                        .build());
    }
}
