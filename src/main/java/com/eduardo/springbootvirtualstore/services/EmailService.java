package com.eduardo.springbootvirtualstore.services;

import com.eduardo.springbootvirtualstore.domain.Pedido;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    void sendOrderConfirmationEmail(Pedido obj);

    void sendEmail(SimpleMailMessage msg);
}