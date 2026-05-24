package com.ortodoa.parcerias.entity;

import com.ortodoa.parcerias.enums.PartnerRequestStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

/**
 * Entidade principal do domínio deste mini sistema.
 *
 * Cada registro representa uma instituição que pediu parceria com o ORTODOA.
 * O administrador avalia esse pedido e altera o status.
 */
@Entity
@Table(name = "tb_partner_institution")
public class PartnerInstitution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Informe o nome da instituição.")
    @Column(nullable = false)
    private String institutionName;

    @NotBlank(message = "Informe o CNPJ.")
    @Column(nullable = false, unique = true)
    private String cnpj;

    @NotBlank(message = "Informe o nome do responsável.")
    @Column(nullable = false)
    private String responsibleName;

    @NotBlank(message = "Informe o e-mail.")
    @Email(message = "E-mail inválido.")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Informe o telefone.")
    @Column(nullable = false)
    private String phone;

    @NotBlank(message = "Informe a cidade.")
    @Column(nullable = false)
    private String city;

    @NotBlank(message = "Informe o estado.")
    @Column(nullable = false)
    private String state;

    @NotBlank(message = "Descreva o motivo da parceria.")
    @Column(nullable = false, length = 1500)
    private String partnershipReason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PartnerRequestStatus status = PartnerRequestStatus.PENDENTE;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime reviewedAt;

    @Column(length = 1000)
    private String adminNote;

    
    @Transient
    private String temporaryPassword;

    /**
     * Relacionamento com a conta de login.
     *
     * A conta já nasce junto do cadastro, mas permanece desabilitada até o ADM aprovar.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getInstitutionName() { return institutionName; }
    public void setInstitutionName(String institutionName) { this.institutionName = institutionName; }
    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
    public String getResponsibleName() { return responsibleName; }
    public void setResponsibleName(String responsibleName) { this.responsibleName = responsibleName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public String getPartnershipReason() { return partnershipReason; }
    public void setPartnershipReason(String partnershipReason) { this.partnershipReason = partnershipReason; }
    public PartnerRequestStatus getStatus() { return status; }
    public void setStatus(PartnerRequestStatus status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getReviewedAt() { return reviewedAt; }
    public void setReviewedAt(LocalDateTime reviewedAt) { this.reviewedAt = reviewedAt; }
    public String getAdminNote() { return adminNote; }
    public void setAdminNote(String adminNote) { this.adminNote = adminNote; }
    public String getTemporaryPassword() { return temporaryPassword; }
    public void setTemporaryPassword(String temporaryPassword) { this.temporaryPassword = temporaryPassword; }
    public UserAccount getUserAccount() { return userAccount; }
    public void setUserAccount(UserAccount userAccount) { this.userAccount = userAccount; }
}
