package com.ortodoa.parcerias.controller;

import com.ortodoa.parcerias.service.PartnerInstitutionService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controla a visão da instituição parceira após login.
 */
@Controller
public class InstitutionController {

    private final PartnerInstitutionService partnerInstitutionService;

    public InstitutionController(PartnerInstitutionService partnerInstitutionService) {
        this.partnerInstitutionService = partnerInstitutionService;
    }

    @GetMapping("/instituicao/painel")
    public String dashboard(Authentication authentication, Model model) {
        String username = authentication.getName();
        model.addAttribute("partner", partnerInstitutionService.findByUsername(username));
        return "institution-dashboard";
    }
}
