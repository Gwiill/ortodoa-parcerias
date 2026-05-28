package com.ortodoa.parcerias.service;

import com.ortodoa.parcerias.entity.PartnerInstitution;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String from;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendRequestReceivedEmail(PartnerInstitution institution) {
        String subject = "ORTODOA - Solicitação de parceria recebida";

        String body = """
                Olá!

                Recebemos sua solicitação de parceria com o ORTODOA.

                Dados recebidos:
                - Instituição: %s
                - E-mail: %s
                - CNPJ: %s

                Sua solicitação está em análise.
                Você receberá um novo e-mail quando o administrador aprovar ou reprovar o pedido.

                Credenciais para acesso ao portal após aprovação:
                - Login: %s
                - Senha inicial: %s
                
                Observação: O status da sua solicitação poderá ser acompanhado na área do parceiro, utilizando as credenciais acima.

                Atenciosamente,
                Equipe ORTODOA
                """.formatted(
                institution.getInstitutionName(),
                institution.getEmail(),
                institution.getCnpj(),
                institution.getEmail(),
                institution.getTemporaryPassword()
        );

        send(institution.getEmail(), subject, body);
    }

    public void sendApprovedEmail(PartnerInstitution institution) {
        String subject = "ORTODOA - Parceria aprovada";

        String note = institution.getAdminNote() != null && !institution.getAdminNote().isBlank()
                ? institution.getAdminNote()
                : "Sem observações do administrador.";

             String body = """
              Olá!

               Sua solicitação de parceria foi APROVADA.

               Observação do administrador:
               %s

               Seu acesso ao portal já está liberado.

               Utilize o login e senha enviados no primeiro e-mail.

               Login: %s
    
               Atenciosamente,
               Equipe ORTODOA
        """.formatted(
                note,
                institution.getEmail()
        );

        send(institution.getEmail(), subject, body);
    }

    public void sendRejectedEmail(PartnerInstitution institution) {
        String subject = "ORTODOA - Parceria reprovada";

        String note = institution.getAdminNote() != null && !institution.getAdminNote().isBlank()
                ? institution.getAdminNote()
                : "Sem observações do administrador.";

        String body = """
                Olá!

                Sua solicitação de parceria foi REPROVADA.

                Observação do administrador:
                %s

                Credenciais registradas:
                - Login: %s
                - Senha inicial: %s

                Atenciosamente,
                Equipe ORTODOA
                """.formatted(
                note,
                institution.getEmail(),
                institution.getCnpj()
        );

        send(institution.getEmail(), subject, body);
    }

    private void send(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}
