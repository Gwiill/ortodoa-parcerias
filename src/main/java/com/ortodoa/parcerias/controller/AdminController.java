package com.ortodoa.parcerias.controller;

import com.ortodoa.parcerias.service.PartnerInstitutionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controla a área administrativa.
 *
 * É aqui que o ADM analisa todos os cadastros, inclusive os logins gerados.
 */
@Controller
public class AdminController {

    private final PartnerInstitutionService partnerInstitutionService;

    public AdminController(PartnerInstitutionService partnerInstitutionService) {
        this.partnerInstitutionService = partnerInstitutionService;
    }

    @GetMapping("/admin/parceiros")
    public String listPartners(Model model) {
        model.addAttribute("partners", partnerInstitutionService.listAll());
        return "admin-list";
    }

    @GetMapping("/admin/parceiros/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("partner", partnerInstitutionService.findById(id));
        return "admin-detail";
    }

    @PostMapping("/admin/parceiros/{id}/aprovar")
    public String approve(@PathVariable Long id,
                          @RequestParam(defaultValue = "Aprovado pelo administrador.") String adminNote,
                          RedirectAttributes redirectAttributes) {
        partnerInstitutionService.approve(id, adminNote);
        redirectAttributes.addFlashAttribute("successMessage", "Pedido aprovado com sucesso.");
        return "redirect:/admin/parceiros/" + id;
    }

    @PostMapping("/admin/parceiros/{id}/reprovar")
    public String reject(@PathVariable Long id,
                         @RequestParam(defaultValue = "Pedido reprovado pelo administrador.") String adminNote,
                         RedirectAttributes redirectAttributes) {
        partnerInstitutionService.reject(id, adminNote);
        redirectAttributes.addFlashAttribute("successMessage", "Pedido reprovado com sucesso.");
        return "redirect:/admin/parceiros/" + id;
    }
}
