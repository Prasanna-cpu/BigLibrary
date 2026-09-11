package com.biglibrary.notification_service.services;

import com.biglibrary.notification_service.ApplicationProperties;
import com.biglibrary.notification_service.entity.embedded_models.OrderCancelledEvent;
import com.biglibrary.notification_service.entity.embedded_models.OrderCreatedEvent;
import com.biglibrary.notification_service.entity.embedded_models.OrderDeliveredEvent;
import com.biglibrary.notification_service.entity.embedded_models.OrderErrorEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

	private final JavaMailSender mailSender;
	private final ApplicationProperties properties;

	private void sendEmail(String to, String subject, String body) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(properties.supportEmail());
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body);
			mailSender.send(message);
			log.info("Email sent to: {}", to);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public void sendOrderCreatedNotification(OrderCreatedEvent event) {
		String message = """
				===================================================
				Order Created Notification
				----------------------------------------------------
				Dear %s,
				Your order with orderNumber: %s has been created successfully.

				Thanks,
				Big Library
				===================================================
				""".formatted(event.customerDTO().getName(), event.orderNumber());
		log.info("\n{}", message);
		sendEmail(event.customerDTO().getEmail(), "Order Created Notification", message);
	}

	public void sendOrderDeliveredNotification(OrderDeliveredEvent event) {
		String message = """
				===================================================
				Order Delivered Notification
				----------------------------------------------------
				Dear %s,
				Your order with orderNumber: %s has been delivered successfully.

				Thanks,
				Big Library
				===================================================
				""".formatted(event.customerDTO().getName(), event.orderNumber());
		log.info("\n{}", message);
		sendEmail(event.customerDTO().getEmail(), "Order Delivered Notification", message);
	}

	public void sendOrderCancelledNotification(OrderCancelledEvent event) {
		String message = """
				===================================================
				Order Cancelled Notification
				----------------------------------------------------
				Dear %s,
				Your order with orderNumber: %s has been cancelled.
				Reason: %s

				Thanks,
				Big Library
				===================================================
				""".formatted(event.customer().getName(), event.orderNumber(), event.reason());
		log.info("\n{}", message);
		sendEmail(event.customer().getEmail(), "Order Cancelled Notification", message);
	}

	public void sendOrderErrorEventNotification(OrderErrorEvent event) {
		String message = """
				===================================================
				Order Processing Failure Notification
				----------------------------------------------------
				Hi %s,
				The order processing failed for orderNumber: %s.
				Reason: %s

				Thanks,
				Big Library
				===================================================
				""".formatted(properties.supportEmail(), event.orderNumber(), event.reason());
		log.info("\n{}", message);
		sendEmail(properties.supportEmail(), "Order Processing Failure Notification", message);
	}

}
