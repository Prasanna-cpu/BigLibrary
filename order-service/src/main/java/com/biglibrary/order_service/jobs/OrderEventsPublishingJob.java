package com.biglibrary.order_service.jobs;

import com.biglibrary.order_service.service.OrderEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderEventsPublishingJob {

	private final OrderEventService orderEventService;

	@Scheduled(cron = "${order.publish-order-events-job-cron}")
	@SchedulerLock(name = "publishOrderEvents")
	public void publishingEvents() {
		log.info("Publishing order events job started at {}", Instant.now());
		orderEventService.publishOrderEvents();
	}

}
