package boxshop.controller;

import boxshop.model.Logista;
import boxshop.services.LogistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LogistaController  {
    @Autowired
    private LogistaService logistaService;

    @GetMapping("/inicio")
    public String cadastrarLogistaPage() {
        return "inicio";
    }

    @PostMapping("/cadastrar-logista")
    public String cadastrarLogista(@ModelAttribute Logista logista, RedirectAttributes redirectAttributes) {
        try {
            Logista novoLogista = logistaService.criarContaLogista(logista);
            redirectAttributes.addFlashAttribute("sucessMessage", "Cadastro realizado com sucesso!");
            return "redirect:/inicio";

        } catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao cadastrar: "+e.getMessage());
            return "redirect:/inicio";
        }
    }
}