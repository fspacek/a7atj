package eu.edhouse.spring.notes.web;

import eu.edhouse.spring.notes.business.NoteManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Frantisek Spacek
 */
@Controller
@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
public class IndexController {

    private final NoteManager noteManager;

    public IndexController(NoteManager noteManager) {
        this.noteManager = noteManager;
    }


    @GetMapping({"/", "/home"})
    public String getHome(Model model) {
        model.addAttribute("notes", noteManager.getAll());
        return "index";
    }

}
