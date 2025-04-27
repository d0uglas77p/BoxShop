package boxshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {

    @GetMapping("/")
    public String home() {
        return "inicio";
    }

    @GetMapping("/inicio")
    public String homePage() {
        return "inicio";
    }
}