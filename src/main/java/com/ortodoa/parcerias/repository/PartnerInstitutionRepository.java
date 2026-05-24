package com.ortodoa.parcerias.repository;

import com.ortodoa.parcerias.entity.PartnerInstitution;
import com.ortodoa.parcerias.enums.PartnerRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório da entidade de pedido de parceria.
 */
public interface PartnerInstitutionRepository extends JpaRepository<PartnerInstitution, Long> {
    List<PartnerInstitution> findByStatusOrderByCreatedAtAsc(PartnerRequestStatus status);
    Optional<PartnerInstitution> findByUserAccountUsername(String username);
    boolean existsByCnpj(String cnpj);
    boolean existsByEmail(String email);

    long countByStatus(PartnerRequestStatus status);
}
