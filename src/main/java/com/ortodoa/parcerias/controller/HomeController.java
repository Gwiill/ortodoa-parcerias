package com.ortodoa.parcerias.controller;

import com.ortodoa.parcerias.entity.PartnerInstitution;
import com.ortodoa.parcerias.service.PartnerInstitutionService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controla páginas públicas: home, formulário de parceria e login.
 */
@Controller
public class HomeController {

    private final PartnerInstitutionService partnerInstitutionService;

    public HomeController(PartnerInstitutionService partnerInstitutionService) {
        this.partnerInstitutionService = partnerInstitutionService;
    }

    @GetMapping("/")
    public String home(Model model) {
        if (!model.containsAttribute("partnerInstitution")) {
            model.addAttribute("partnerInstitution", new PartnerInstitution());
        }
        return "home";
    }

    @PostMapping("/parceria")
    public String submitPartnership(@Valid @ModelAttribute PartnerInstitution partnerInstitution,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.partnerInstitution", bindingResult);
            redirectAttributes.addFlashAttribute("partnerInstitution", partnerInstitution);
            return "redirect:/";
        }

        try {
            partnerInstitutionService.registerNewPartner(partnerInstitution);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Pedido enviado com sucesso. Informações de acesso enviadas para o e-mail informado");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("partnerInstitution", partnerInstitution);
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/redirect-por-perfil")
    public String redirectByRole(Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        return isAdmin ? "redirect:/admin/parceiros" : "redirect:/instituicao/painel";
    }
}
