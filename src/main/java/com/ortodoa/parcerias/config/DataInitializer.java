package com.ortodoa.parcerias.config;

import com.ortodoa.parcerias.entity.PartnerInstitution;
import com.ortodoa.parcerias.entity.UserAccount;
import com.ortodoa.parcerias.enums.PartnerRequestStatus;
import com.ortodoa.parcerias.enums.Role;
import com.ortodoa.parcerias.repository.PartnerInstitutionRepository;
import com.ortodoa.parcerias.repository.UserAccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

/**
 * Insere dados de exemplo para facilitar testes.
 */
@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner loadData(UserAccountRepository userAccountRepository,
                               PartnerInstitutionRepository partnerInstitutionRepository,
                               PasswordEncoder passwordEncoder) {
        return args -> {
            if (!userAccountRepository.existsByUsername("admin@ortodoa.com")) {
                UserAccount admin = new UserAccount();
                admin.setUsername("admin@ortodoa.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(Role.ADMIN);
                admin.setEnabled(true);
                userAccountRepository.save(admin);
            }

            if (!partnerInstitutionRepository.existsByEmail("hospital.aprovado@ortodoa.com")) {
                UserAccount institutionUser = new UserAccount();
                institutionUser.setUsername("hospital.aprovado@ortodoa.com");
                institutionUser.setPassword(passwordEncoder.encode("Parceiro@123"));
                institutionUser.setRole(Role.INSTITUICAO);
                institutionUser.setEnabled(true);

                PartnerInstitution institution = new PartnerInstitution();
                institution.setInstitutionName("Hospital Exemplo Aprovado");
                institution.setCnpj("12345678000199");
                institution.setResponsibleName("Maria Souza");
                institution.setEmail("hospital.aprovado@ortodoa.com");
                institution.setPhone("(11) 99999-0000");
                institution.setCity("São Paulo");
                institution.setState("SP");
                institution.setPartnershipReason("Atendimento de pacientes com necessidade de equipamentos ortopédicos.");
                institution.setStatus(PartnerRequestStatus.APROVADO);
                institution.setCreatedAt(LocalDateTime.now().minusDays(5));
                institution.setReviewedAt(LocalDateTime.now().minusDays(2));
                institution.setAdminNote("Instituição validada para piloto inicial.");
                institution.setUserAccount(institutionUser);

                partnerInstitutionRepository.save(institution);
            }
        };
    }
}
