package ru.letmerent.core.controllers;

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
@RequestMapping("/api/v1/comments")
public class CommentController {
    private static final String HEADER = "about";
    private static final String USER = "user";
    private static final String INSTR = "instrument";

    private final InstrumentCommentService instrumentCommentService;
    private final UserCommentsService userCommentsService;

    @PostMapping("/user")
    public ResponseEntity<?> addCommentAboutUser(@RequestBody UserCommentDto commentDto) {
        userCommentsService.addNewComment(commentDto);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/instrument")
    public ResponseEntity<?> addCommentAboutInstrument(@RequestBody InstrumentCommentDto commentDto) {
        instrumentCommentService.addNewComment(commentDto);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
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

    @GetMapping("/grade")
    public ResponseEntity<?> getGrade(@RequestHeader(HEADER) String about,
                                      @NotNull @RequestParam("id") Long id) {
        switch (about) {
            case USER:
                return ResponseEntity.ok(userCommentsService.getUserGrade(id));
            case INSTR:
                return ResponseEntity.ok(instrumentCommentService.getInstrumentGrade(id));
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
