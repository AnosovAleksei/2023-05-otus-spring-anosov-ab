package ru.otus.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.domain.Commentary;
import ru.otus.dto.CommentaryCreateDto;
import ru.otus.dto.CommentaryUpdateDto;
import ru.otus.service.CommentaryService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentaryController {

    private final CommentaryService commentaryService;

    @GetMapping("/api/v1/commentary")
    public List<Commentary> getCommentaryes() {
        return commentaryService.getAll();
    }


    @GetMapping("/api/v1/commentary/{id}")
    public Commentary getCommentary(@PathVariable Long id) {
        return commentaryService.read(id);
    }

    @PostMapping("/api/v1/commentary")
    public Commentary createCommentary(@Valid @RequestBody CommentaryCreateDto commentaryCreateDto) {
        return commentaryService.create(commentaryCreateDto);
    }

    @PutMapping("/api/v1/commentary")
    public Commentary updateCommentary(@Valid @RequestBody CommentaryUpdateDto commentaryUpdateDto) {
        return commentaryService.update(commentaryUpdateDto);
    }

}
