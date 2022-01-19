package com.eduardo.springbootvirtualstore.services;

import javax.mail.internet.MimeMessage;

import com.eduardo.springbootvirtualstore.domain.Cliente;
import com.eduardo.springbootvirtualstore.domain.Pedido;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendOrderConfirmationHtmlEmail(Pedido obj);

    void sendHtmlEmail(MimeMessage msg);

    void sendOrderConfirmationEmail(Pedido obj);

    void sendEmail(SimpleMailMessage msg);

    void sendNewPasswordEmail(Cliente cliente, String newPass);
}