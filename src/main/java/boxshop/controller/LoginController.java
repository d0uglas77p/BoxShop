package boxshop.controller;

import boxshop.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/recuperarSenha")
    public String recuperarSenhaPag() {
        return "recuperarSenha";
    }

    @PostMapping("/recuperar-conta-logista")
    public String recuperarSenhaLog(@RequestParam("emailComercioLogista") String emailComercioLogista,
                                    RedirectAttributes redirectAttributes) {
        try {
            if (!loginService.isEmailCadastrado(emailComercioLogista)) {
                redirectAttributes.addFlashAttribute("errorMessage", "Esse e-mail não está cadastrado!");
                return "redirect:/inicio";
            }

            String token = loginService.gerarTokenRecuperacao(emailComercioLogista);

            String urlRecuperacao = "http://localhost:8080/recuperarSenha?token=" + token;

            loginService.enviarEmailRecuperacao(emailComercioLogista, "Clique no link para recuperar sua senha: " + urlRecuperacao);

            redirectAttributes.addFlashAttribute("successMessage", "Um e-mail foi enviado com as instruções.");
            return "redirect:/inicio";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao Recuperar senha: "+e.getMessage());
            return "redirect:/inicio";
        }
    }

    @PostMapping("/atualizar-senha")
    public String atualizarSenhaLog(@RequestParam("token")String token,
                                    @RequestParam("novaSenhaComercialLogista") String novaSenhaComercialLogista,
                                    RedirectAttributes redirectAttributes){
        System.out.println("Token recebido: " + token);

        try {
            if(loginService.isTokenValido(token)){
                loginService.atualizarSenha(token, novaSenhaComercialLogista);
                redirectAttributes.addFlashAttribute("successMessage", "Senha atualizado com sucesso!");

                return "redirect:/inicio";
          } else{
                redirectAttributes.addFlashAttribute("errorMessage","Erro ao atualizar senha, token inválido");
                return "redirect:/inicio";
            }
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMessage","Erro ao atualizar senha"+e.getMessage());
            return "redirect:/inicio";
        }

    }

}