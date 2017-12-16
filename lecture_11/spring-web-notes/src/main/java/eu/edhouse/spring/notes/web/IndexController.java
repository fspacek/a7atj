package eu.edhouse.spring.notes.web;

import eu.edhouse.spring.notes.business.NoteManager;
import eu.edhouse.spring.notes.business.Owner;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
    public String getHome(Model model, Authentication authentication) {
        model.addAttribute("notes", noteManager.getAllByOwner((Owner) authentication.getPrincipal()));
        return "index";
    }

}
