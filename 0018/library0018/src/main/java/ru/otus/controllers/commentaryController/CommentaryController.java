package ru.otus.controllers.commentaryController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.controllers.NavigatorController;
import ru.otus.dto.CommentaryCreateDto;
import ru.otus.dto.CommentaryUpdateDto;
import ru.otus.service.CommentaryService;

@Validated
@RequiredArgsConstructor
@Controller
public class CommentaryController {

    private final CommentaryService commentaryService;


    @GetMapping("/comments")
    public String getComments(Model model) {
        model.addAllAttributes(NavigatorController.getValue(NavigatorController.COMMENTARY_PAGE, true));
        model.addAttribute("comments", commentaryService.getAll());
        return NavigatorController.COMMENTARY_PAGE;
    }

    @PostMapping("/commentary")
    public String createCommentary(Model model,
                                   @Valid CommentaryCreateDto commentaryCreateDto) {
        model.addAllAttributes(NavigatorController.getValue(NavigatorController.COMMENTARY_PAGE, true));
        model.addAttribute("commentary", commentaryService.create(commentaryCreateDto));
        return NavigatorController.COMMENTARY_PAGE;
    }

    @GetMapping("/commentary")
    public String getCommentary(Model model, @Valid @NotNull  @RequestParam("id") Long id) {
        model.addAllAttributes(NavigatorController.getValue(NavigatorController.COMMENTARY_PAGE, true));
        model.addAttribute("commentary", commentaryService.read(id));
        return NavigatorController.COMMENTARY_PAGE;
    }


    @PutMapping("/commentary")
    public String updateCommentary(Model model,
                                   @Valid CommentaryUpdateDto commentaryUpdateDto) {
        model.addAllAttributes(NavigatorController.getValue(NavigatorController.COMMENTARY_PAGE, true));
        model.addAttribute("commentary", commentaryService.update(commentaryUpdateDto));
        return NavigatorController.COMMENTARY_PAGE;
    }
}
