package eu.edhouse.spring.notes.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Frantisek Spacek
 */
@Controller
@RequestMapping("/registration")
public class RegistrationController {


    @GetMapping
    public String getRegistrationPage() {
        return "registration";
    }
}
