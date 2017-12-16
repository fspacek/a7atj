package eu.edhouse.spring.notes.web;

import eu.edhouse.spring.notes.business.OwnerManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Objects;

/**
 * @author Frantisek Spacek
 */
@Controller
@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
@RequestMapping("/registration")
public class RegistrationController {

    private final OwnerManager ownerManager;

    public RegistrationController(OwnerManager ownerManager) {
        this.ownerManager = Objects.requireNonNull(ownerManager);
    }

    @GetMapping
    public String getRegistrationPage() {
        return "registration";
    }

    @PostMapping
    @Transactional
    public String register(@Valid RegistrationForm registrationForm) {
        ownerManager.createUser(registrationForm.createOwner());
        return "redirect:login";
    }
}
