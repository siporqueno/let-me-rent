package ru.letmerent.core.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.letmerent.core.entity.Instrument;
import ru.letmerent.core.exceptions.models.ApplicationError;
import ru.letmerent.core.services.InstrumentService;
import ru.letmerent.core.services.PictureStorageService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pictures")
@Tag(name = "API для работы с картинками")
public class PictureController {
    private static final String COULD_NOT_FIND_INSTRUMENT = "Could not find instrument with id: ";
    
    private final ObjectMapper mapper;
    private final PictureStorageService storageService;
    private final InstrumentService instrumentService;
    
    @Operation(summary = "Загрузка картинки")
    @PostMapping("/upload")
    @ApiResponse(responseCode = "200", description = "Картинка успешно загружена.")
    @ApiResponse(responseCode = "404", description = "Не корректные параметры запроса",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApplicationError.class)))
    public ResponseEntity<Object> uploadPicture(@RequestParam("picture") MultipartFile file, @RequestParam("instrumentId") Long instrumentId) {
        try {
            Optional<Instrument> oInstrument = instrumentService.getInstrumentById(instrumentId);
            if (oInstrument.isEmpty()) {
                
                return getErrorResponse(COULD_NOT_FIND_INSTRUMENT + instrumentId);
            }
            storageService.save(file, oInstrument.get());
            
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return getErrorResponse(e.getMessage());
        }
    }
    
    @Operation(summary = "Загрузка массива картинок")
    @PostMapping("/uploads")
    @ApiResponse(responseCode = "200", description = "Картинки успешно загружены.")
    @ApiResponse(responseCode = "404", description = "Не корректные параметры запроса",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApplicationError.class)))
    public ResponseEntity<Object> uploadPictures(@RequestParam("pictures") MultipartFile[] files, @RequestParam("instrumentId") Long instrumentId) {
        try {
            Optional<Instrument> oInstrument = instrumentService.getInstrumentById(instrumentId);
            if (oInstrument.isEmpty()) {
                
                return getErrorResponse(COULD_NOT_FIND_INSTRUMENT + instrumentId);
            }
            Arrays.stream(files).forEach(file -> storageService.save(file, oInstrument.get()));
            
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return getErrorResponse(e.getMessage());
        }
    }
    
    @Operation(summary = "Получение картинки по имени")
    @ApiResponse(responseCode = "200", description = "Получение картинки",
        content = {
            @Content(mediaType = "image/png", schema = @Schema(implementation = Resource.class)),
            @Content(mediaType = "image/jpeg", schema = @Schema(implementation = Resource.class)),
            @Content(mediaType = "image/gif", schema = @Schema(implementation = Resource.class))
        }
    )
    @GetMapping("/{pictureName:.+}")
    public ResponseEntity<Object> getPictureByName(@PathVariable String pictureName) {
        try {
            Resource picture = storageService.load(pictureName);
            MediaType mediaType = storageService.getMediaType(picture.getFilename());
            return ResponseEntity.ok().contentType(mediaType).body(picture);
        } catch (Exception e) {
            return getErrorResponse(e.getMessage());
        }
    }
    
    @Operation(summary = "Получение картинки по id")
    @ApiResponse(responseCode = "200", description = "Получение картинки",
        content = {
            @Content(mediaType = "image/png", schema = @Schema(implementation = Resource.class)),
            @Content(mediaType = "image/jpeg", schema = @Schema(implementation = Resource.class)),
            @Content(mediaType = "image/gif", schema = @Schema(implementation = Resource.class))
        }
    )
    @GetMapping("/byId/{pictureId}")
    public ResponseEntity<Object> getPictureById(@PathVariable Long pictureId) {
        try {
            Resource picture = storageService.load(pictureId);
            MediaType mediaType = storageService.getMediaType(picture.getFilename());
            return ResponseEntity.ok().contentType(mediaType).body(picture);
        } catch (Exception e) {
            return getErrorResponse(e.getMessage());
        }
    }
    
    @Operation(summary = "Удалить картинки инструмента")
    @ApiResponse(responseCode = "204", description = "Картинки инструмента удалены", content = @Content(mediaType = "application/json"))
    @DeleteMapping
    public ResponseEntity<Object> deletePictures(@RequestParam("instrumentId") Long instrumentId,
                                                 @RequestParam(value = "pictureIds", required = false) List<Long> pictureIds) {
        Optional<Instrument> oInstrument = instrumentService.getInstrumentById(instrumentId);
        if (oInstrument.isEmpty()) {
            return getErrorResponse(COULD_NOT_FIND_INSTRUMENT + instrumentId);
        }
        storageService.deletePictures(oInstrument.get().getId(), pictureIds);
        
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
    private ResponseEntity<Object> getErrorResponse(String message) {
        return ResponseEntity.badRequest()
            .body(mapper.valueToTree(ApplicationError.builder()
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .userMessage(message)
                .date(new Date())
                .build()));
    }
}
