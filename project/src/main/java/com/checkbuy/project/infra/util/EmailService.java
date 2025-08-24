package com.checkbuy.project.infra.util;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void enviarEmailDeConfirmacaoHtml(String toEmail, String nomeUsuario) {
        System.out.println("Iniciando o envio de e-mail HTML em uma thread separada...");

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Bem-vindo(a), " + nomeUsuario + "!");

            // ** MUDANÇA: O HTML foi ajustado para maior tamanho e para embutir os ícones **
            String htmlContent = "<body style='font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px;'>\n" +
                    "    <table width='100%' border='0' cellspacing='0' cellpadding='0'><tr><td align='center'>\n" +
                    "    <table width='100%' style='max-width: 600px; background-color: #ffffff; box-shadow: 0 0 10px rgba(0,0,0,0.1); border-top: 5px solid #1a6f3b;' border='0' cellspacing='0' cellpadding='0'>\n" +
                    "        <tr><td style='padding: 30px 20px; color: #333333; text-align: center;'>\n" +
                    "            <img src='cid:logo' alt='Logo da CheckBuy' style='width: 120px; height: auto; margin-bottom: 30px;'>\n" +
                    "            <h1 style='color: #1a6f3b; margin: 0;'>Bem-vindo(a), " + nomeUsuario + "!</h1>\n" +
                    "            <p style='line-height: 1.6;'>Estamos muito felizes em ter você conosco. Nossa equipe está pronta para te ajudar a encontrar os melhores preços do mercado.</p>\n" +
                    "            <a href='http://seusite.com/catalogo' style='background-color: #d9534f; color: white; padding: 12px 25px; text-decoration: none; border-radius: 5px; font-weight: bold; display: inline-block; margin-top: 20px;'>Explorar Produtos</a>\n" +
                    "        </td></tr>\n" +

                    "        <tr><td style='background-color: #f8f9fa; padding: 30px 20px; text-align: center;'>\n" +
                    "            <h2 style='margin-top: 0; color: #333;'>Sobre a CheckBuy</h2>\n" +
                    "            <p style='color: #6c757d; max-width: 500px; margin: 0 auto 30px auto;'>A CheckBuy nasceu em Blumenau com a vontade de transformar a rotina de milhões de consumidores, conectando o usuário às informações mais atualizadas do mercado.</p>\n" +
                    "            <table width='100%' border='0' cellspacing='0' cellpadding='10'><tr>\n" +

                    "                \n" +
                    "                <td align='center' valign='top' style='padding: 10px;'><table border='0' cellspacing='0' cellpadding='0' width='190'><tr><td align='center' style='padding: 15px; border: 1px solid #dee2e6; background-color: #fff;'>\n" +
                    "                   <img src='cid:caio' width='120' height='120' style='border-radius: 50%; margin-bottom: 15px;' alt='Foto de Caio Amaro'>\n" +
                    "                   <h5 style='margin: 0 0 5px 0; font-size: 16px;'>Caio Amaro</h5>\n" +
                    "                   <p style='margin: 0 0 10px 0; color: #6c757d; font-size: 14px;'>Desenvolvedor Back-end</p>\n" +
                    "                   <a href='https://github.com/CaioAmaro' target='_blank' style='text-decoration: none; margin: 0 5px;'><img src='cid:github-icon' width='24' alt='GitHub'></a>\n" +
                    "                   <a href='https://www.linkedin.com/in/caio-amaro-146775190/' target='_blank' style='text-decoration: none; margin: 0 5px;'><img src='cid:linkedin-icon' width='24' alt='LinkedIn'></a>\n" +
                    "                </td></tr></table></td>\n" +

                    "                \n" +
                    "                <td align='center' valign='top' style='padding: 10px;'><table border='0' cellspacing='0' cellpadding='0' width='190'><tr><td align='center' style='padding: 15px; border: 1px solid #dee2e6; background-color: #fff;'>\n" +
                    "                   <img src='cid:douglas' width='120' height='120' style='border-radius: 50%; margin-bottom: 15px;' alt='Foto de Eliton Douglas'>\n" +
                    "                   <h5 style='margin: 0 0 5px 0; font-size: 16px;'>Eliton Douglas S. P.</h5>\n" +
                    "                   <p style='margin: 0 0 10px 0; color: #6c757d; font-size: 14px;'>Gerente de Projeto & UI/UX</p>\n" +
                    "                   <a href='https://github.com/dougelit' target='_blank' style='text-decoration: none; margin: 0 5px;'><img src='cid:github-icon' width='24' alt='GitHub'></a>\n" +
                    "                   <a href='https://www.linkedin.com/in/eliton-douglas-silva-pereira-4a4209349/' target='_blank' style='text-decoration: none; margin: 0 5px;'><img src='cid:linkedin-icon' width='24' alt='LinkedIn'></a>\n" +
                    "                </td></tr></table></td>\n" +

                    "                \n" +
                    "                <td align='center' valign='top' style='padding: 10px;'><table border='0' cellspacing='0' cellpadding='0' width='190'><tr><td align='center' style='padding: 15px; border: 1px solid #dee2e6; background-color: #fff;'>\n" +
                    "                   <img src='cid:jackson' width='120' height='120' style='border-radius: 50%; margin-bottom: 15px;' alt='Foto de Jackson A. Lourenço'>\n" +
                    "                   <h5 style='margin: 0 0 5px 0; font-size: 16px;'>Jackson A. Lourenço</h5>\n" +
                    "                   <p style='margin: 0 0 10px 0; color: #6c757d; font-size: 14px;'>Desenvolvedor Front-end</p>\n" +
                    "                   <a href='https://github.com/jacksonlourenco' target='_blank' style='text-decoration: none; margin: 0 5px;'><img src='cid:github-icon' width='24' alt='GitHub'></a>\n" +
                    "                   <a href='https://www.linkedin.com/in/jackson-andre-lourenco-40743b231/' target='_blank' style='text-decoration: none; margin: 0 5px;'><img src='cid:linkedin-icon' width='24' alt='LinkedIn'></a>\n" +
                    "                </td></tr></table></td>\n" +

                    "            </tr></table>\n" +
                    "        </td></tr>\n" +

                    "        <tr><td style='text-align: center; padding: 20px; font-size: 12px; color: #777777; background-color: #e9e9e9;'>\n" +
                    "            <p style='margin: 5px 0;'>&copy; 2025 CheckBuy. Todos os direitos reservados.</p>\n" +
                    "            <p style='margin: 5px 0;'>Entre em contato conosco para maiores explicações do projeto.</p>\n" +
                    "        </td></tr>\n" +
                    "    </table>\n" +
                    "    </td></tr></table>\n" +
                    "</body>";

            helper.setText(htmlContent, true);

            // Embutindo as imagens (com os novos ícones)
            helper.addInline("logo", new ClassPathResource("static/images/logo.png"));
            helper.addInline("caio", new ClassPathResource("static/images/caio.jpg"));
            helper.addInline("douglas", new ClassPathResource("static/images/douglas.jpg"));
            helper.addInline("jackson", new ClassPathResource("static/images/jackson.jpg"));
            helper.addInline("github-icon", new ClassPathResource("static/images/github_icon.png"));
            helper.addInline("linkedin-icon", new ClassPathResource("static/images/linkedin_icon.png"));

            mailSender.send(message);

            System.out.println("E-mail HTML enviado com sucesso para " + toEmail);
        } catch (Exception e) {
            System.err.println("Erro ao enviar e-mail: " + e.getMessage());
            e.printStackTrace();
        }
    }
}