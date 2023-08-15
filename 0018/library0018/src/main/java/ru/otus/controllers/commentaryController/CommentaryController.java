package ru.otus.controllers.commentaryController;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.controllers.NavigatorController;
import ru.otus.domain.Commentary;
import ru.otus.service.CommentaryService;

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
    public String createCommentary(Model model, CommentaryCreateRequest commentaryCreateRequest) {
        model.addAllAttributes(NavigatorController.getValue(NavigatorController.COMMENTARY_PAGE, true));
        Commentary commentary = new Commentary();
        commentary.setBookId(commentaryCreateRequest.getBookId());
        commentary.setMessage(commentaryCreateRequest.getMessage());


        model.addAttribute("commentary", commentaryService.create(commentary));
        return NavigatorController.COMMENTARY_PAGE;
    }

    @GetMapping("/commentary")
    public String getCommentary(Model model, @RequestParam("id") Long id) {
        model.addAllAttributes(NavigatorController.getValue(NavigatorController.COMMENTARY_PAGE, true));
        model.addAttribute("commentary", commentaryService.read(id));
        return NavigatorController.COMMENTARY_PAGE;
    }


    @PutMapping("/commentary")
    public String updateCommentary(Model model, CommentaryUpdateRequest commentaryUpdateRequest) {
        model.addAllAttributes(NavigatorController.getValue(NavigatorController.COMMENTARY_PAGE, true));
        Commentary commentary = new Commentary();
        commentary.setBookId(commentaryUpdateRequest.getBookId());
        commentary.setMessage(commentaryUpdateRequest.getMessage());
        commentary.setId(commentaryUpdateRequest.getId());

        model.addAttribute("commentary", commentaryService.update(commentary));
        return NavigatorController.COMMENTARY_PAGE;
    }
}
