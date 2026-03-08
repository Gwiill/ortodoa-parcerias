package com.ortodoa.parcerias.service;

import com.ortodoa.parcerias.entity.PartnerInstitution;
import com.ortodoa.parcerias.entity.UserAccount;
import com.ortodoa.parcerias.enums.PartnerRequestStatus;
import com.ortodoa.parcerias.enums.Role;
import com.ortodoa.parcerias.repository.PartnerInstitutionRepository;
import com.ortodoa.parcerias.repository.UserAccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Camada de regra de negócio do módulo.
 *
 * Aqui ficam as decisões importantes do sistema:
 * - criar pedido de parceria;
 * - gerar login da instituição;
 * - aprovar ou reprovar;
 * - habilitar ou desabilitar acesso.
 */
@Service
public class PartnerInstitutionService {

    private final PartnerInstitutionRepository partnerInstitutionRepository;
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    public PartnerInstitutionService(PartnerInstitutionRepository partnerInstitutionRepository,
                                     UserAccountRepository userAccountRepository,
                                     PasswordEncoder passwordEncoder) {
        this.partnerInstitutionRepository = partnerInstitutionRepository;
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public PartnerInstitution registerNewPartner(PartnerInstitution partnerInstitution) {
        validateUniqueFields(partnerInstitution);

        UserAccount account = new UserAccount();
        account.setUsername(partnerInstitution.getEmail());
        account.setPassword(passwordEncoder.encode(partnerInstitution.getCnpj()));
        account.setRole(Role.INSTITUICAO);
        account.setEnabled(false);

        partnerInstitution.setStatus(PartnerRequestStatus.PENDENTE);
        partnerInstitution.setCreatedAt(LocalDateTime.now());
        partnerInstitution.setUserAccount(account);

        return partnerInstitutionRepository.save(partnerInstitution);
    }

    public List<PartnerInstitution> listAll() {
        return partnerInstitutionRepository.findAll();
    }

    public List<PartnerInstitution> listPending() {
        return partnerInstitutionRepository.findByStatusOrderByCreatedAtAsc(PartnerRequestStatus.PENDENTE);
    }

    public PartnerInstitution findById(Long id) {
        return partnerInstitutionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Parceiro não encontrado."));
    }

    public PartnerInstitution findByUsername(String username) {
        return partnerInstitutionRepository.findByUserAccountUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Instituição não encontrada."));
    }

    @Transactional
    public void approve(Long id, String adminNote) {
        PartnerInstitution institution = findById(id);
        institution.setStatus(PartnerRequestStatus.APROVADO);
        institution.setAdminNote(adminNote);
        institution.setReviewedAt(LocalDateTime.now());
        institution.getUserAccount().setEnabled(true);
        partnerInstitutionRepository.save(institution);
    }

    @Transactional
    public void reject(Long id, String adminNote) {
        PartnerInstitution institution = findById(id);
        institution.setStatus(PartnerRequestStatus.REPROVADO);
        institution.setAdminNote(adminNote);
        institution.setReviewedAt(LocalDateTime.now());
        institution.getUserAccount().setEnabled(false);
        partnerInstitutionRepository.save(institution);
    }

    private void validateUniqueFields(PartnerInstitution partnerInstitution) {
        if (partnerInstitutionRepository.existsByCnpj(partnerInstitution.getCnpj())) {
            throw new IllegalArgumentException("Já existe uma instituição cadastrada com este CNPJ.");
        }
        if (partnerInstitutionRepository.existsByEmail(partnerInstitution.getEmail())) {
            throw new IllegalArgumentException("Já existe uma instituição cadastrada com este e-mail.");
        }
        if (userAccountRepository.existsByUsername(partnerInstitution.getEmail())) {
            throw new IllegalArgumentException("O login gerado por e-mail já existe.");
        }
    }
}
