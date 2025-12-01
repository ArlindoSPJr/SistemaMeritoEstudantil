package lab.dev.meritoEstudantil.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lab.dev.meritoEstudantil.domain.aluno.Aluno;
import lab.dev.meritoEstudantil.domain.empresa.EmpresaParceira;
import lab.dev.meritoEstudantil.domain.professor.Professor;
import lab.dev.meritoEstudantil.domain.vantagem.Vantagem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Async
    public void sendResgateConfirmationEmail(Aluno aluno, EmpresaParceira empresa, Vantagem vantagem) {
        log.info("Iniciando preparação do e-mail para o aluno nº{}", aluno.getId());

        try {
            String cupom = gerarCupom();

            // ========== EMAIL PARA O ALUNO ==========
            String assuntoAluno = "Confirmação de Resgate - " + vantagem.getDescricao();
            String mensagemAluno = String.format("""
                    Olá %s,

                    Seu resgate da vantagem "%s" foi confirmado!

                    CUPOM: %s

                    Utilize este cupom na empresa parceira: %s.

                    Obrigado por utilizar o Sistema de Vantagens!
                    """,
                    aluno.getNome(),
                    vantagem.getDescricao(),
                    cupom,
                    empresa.getNomeFantasia());

            MimeMessage messageAluno = mailSender.createMimeMessage();
            MimeMessageHelper helperAluno = new MimeMessageHelper(messageAluno, true, "UTF-8");
            helperAluno.setTo(aluno.getEmail());
            helperAluno.setSubject(assuntoAluno);
            helperAluno.setText(mensagemAluno, false);

            mailSender.send(messageAluno);

            // ========== EMAIL PARA A EMPRESA PARCEIRA ==========
            String assuntoEmpresa = "Cupom de Resgate - " + aluno.getNome();
            String mensagemEmpresa = String.format("""
                    Olá %s,

                    Um aluno realizou um resgate da vantagem "%s".

                    Aluno: %s
                    Cupom gerado: %s

                    Utilize este cupom para validar o benefício.
                    """,
                    empresa.getNomeFantasia(),
                    vantagem.getDescricao(),
                    aluno.getNome(),
                    cupom);

            MimeMessage messageEmpresa = mailSender.createMimeMessage();
            MimeMessageHelper helperEmpresa = new MimeMessageHelper(messageEmpresa, true, "UTF-8");
            helperEmpresa.setTo(empresa.getEmail());
            helperEmpresa.setSubject(assuntoEmpresa);
            helperEmpresa.setText(mensagemEmpresa, false);

            mailSender.send(messageEmpresa);

            log.info(
                    "E-mails enviados. Aluno nº{} → {} | Empresa Parceira → {}",
                    aluno.getId(),
                    aluno.getEmail(),
                    empresa.getEmail());

        } catch (MessagingException e) {
            log.error("Falha ao enviar e-mail de confirmação para o aluno nº{}: {}", aluno.getId(), e.getMessage());
        }
    }

    @Async
    public void sendEmailEnvioMoedas(Professor professor, Aluno aluno, int quantidadeMoedas, String descricao) {
        log.info("Preparando e-mails de envio de moedas. Professor ID: {}, Aluno ID: {}",
                professor.getId(), aluno.getId());

        try {
            // ========== EMAIL PARA O PROFESSOR ==========
            String assuntoProfessor = "Confirmação de Envio de Moedas";
            String mensagemProfessor = String.format("""
                    Olá %s,

                    Você enviou %d moedas para o aluno %s.

                    Descrição: %s

                    Saldo atual após o envio: %d moedas.

                    Obrigado por utilizar o Sistema de Vantagens!
                    """,
                    professor.getNome(),
                    quantidadeMoedas,
                    aluno.getNome(),
                    descricao,
                    professor.getSaldoMoedas());

            MimeMessage msgProfessor = mailSender.createMimeMessage();
            MimeMessageHelper helperProfessor = new MimeMessageHelper(msgProfessor, true, "UTF-8");

            helperProfessor.setTo(professor.getEmail());
            helperProfessor.setSubject(assuntoProfessor);
            helperProfessor.setText(mensagemProfessor, false);

            mailSender.send(msgProfessor);

            // ========== EMAIL PARA O ALUNO ==========
            String assuntoAluno = "Você recebeu moedas!";
            String mensagemAluno = String.format("""
                    Olá %s,

                    Você recebeu %d moedas do professor %s.

                    Descrição do envio: %s

                    Seu novo saldo é: %d moedas.

                    Aproveite suas vantagens no sistema!
                    """,
                    aluno.getNome(),
                    quantidadeMoedas,
                    professor.getNome(),
                    descricao,
                    aluno.getSaldoMoedas());

            MimeMessage msgAluno = mailSender.createMimeMessage();
            MimeMessageHelper helperAluno = new MimeMessageHelper(msgAluno, true, "UTF-8");

            helperAluno.setTo(aluno.getEmail());
            helperAluno.setSubject(assuntoAluno);
            helperAluno.setText(mensagemAluno, false);

            mailSender.send(msgAluno);

            log.info("E-mails enviados para professor ({}) e aluno ({})", professor.getEmail(), aluno.getEmail());

        } catch (MessagingException e) {
            log.error("Erro ao enviar e-mails de envio de moedas: {}", e.getMessage());
        }
    }

    private String gerarCupom() {
        // Ex: CUPOM-AB12-CD34
        return "CUPOM-" + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase()
                .replace("-", "");
    }

}