package boxshop.controller;

import boxshop.exception.CpfJaCriadoException;
import boxshop.exception.EmailComercioJaCriadoException;
import boxshop.exception.WhatsappComercioJaCriadoException;
import boxshop.model.Logista;
import boxshop.services.LogistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

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
            redirectAttributes.addFlashAttribute("successMessage", "Cadastro realizado com sucesso!");
            return "redirect:/inicio";

        } catch (CpfJaCriadoException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/inicio";

        } catch (EmailComercioJaCriadoException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/inicio";

        } catch (WhatsappComercioJaCriadoException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/inicio";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao cadastrar: "+e.getMessage());
            return "redirect:/inicio";
        }
    }

    @PostMapping("/verificar-nome-comercio")
    @ResponseBody
    public ResponseEntity<Map<String, Boolean>> verificarNomeComercio(@RequestBody Map<String, String> request) {
        String nomeComercio = request.get("nomeComercio");

        boolean nomeExistente = logistaService.verificarNomeComercioExistente(nomeComercio);

        Map<String, Boolean> response = new HashMap<>();
        response.put("nomeExistente", nomeExistente);

        return ResponseEntity.ok(response);
    }
}