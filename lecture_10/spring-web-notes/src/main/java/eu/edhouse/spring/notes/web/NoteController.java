package eu.edhouse.spring.notes.web;

import eu.edhouse.spring.notes.business.Note;
import eu.edhouse.spring.notes.business.NoteManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/**
 * @author Frantisek Spacek
 */
@Controller
@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
@RequestMapping("/note")
public class NoteController {

    private final NoteManager noteManager;

    public NoteController(NoteManager noteManager) {
        this.noteManager = noteManager;
    }

    @GetMapping
    public String getEmptyForm(Model model) {
        model.addAttribute("noteForm", new NoteForm());
        return "note";
    }

    @GetMapping("{id}")
    public String getEmptyForm(Model model, @PathVariable("id") long id) {
        final Note note = noteManager.getOne(id);
        model.addAttribute("noteForm", NoteForm.from(note));
        return "note";
    }

    @Transactional
    @PostMapping
    public String createOrUpdateNote(@ModelAttribute NoteForm noteForm) {
        if (noteForm.getId() == null) {
            noteManager.create(NoteForm.toEntity(noteForm));
        } else {
            noteManager.update(NoteForm.toEntity(noteForm));
        }
        return "redirect:home";
    }


    @Transactional
    @GetMapping("{id}/delete")
    public String deleteNote(@PathVariable("id") long id){
        noteManager.delete(id);
        return "redirect:home";
    }
}

