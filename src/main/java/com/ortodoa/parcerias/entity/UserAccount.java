package com.ortodoa.parcerias.entity;

import com.ortodoa.parcerias.enums.Role;
import jakarta.persistence.*;

/**
 * Representa uma conta de acesso ao sistema.
 *
 * A ideia aqui é separar a autenticação da instituição em uma entidade própria,
 * facilitando manutenção, leitura e expansão futura.
 */
@Entity
@Table(name = "tb_user_account")
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private boolean enabled = true;

    @OneToOne(mappedBy = "userAccount")
    private PartnerInstitution partnerInstitution;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public PartnerInstitution getPartnerInstitution() { return partnerInstitution; }
    public void setPartnerInstitution(PartnerInstitution partnerInstitution) { this.partnerInstitution = partnerInstitution; }
}
