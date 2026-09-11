package com.biglibrary.order_service.jobs;

import com.biglibrary.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderProcessingJobs {

	private final OrderService orderService;

	@Scheduled(cron = "${order.new-orders-job-cron}")
	@SchedulerLock(name = "processNewOrders")
	public void processNewOrders() {
		LockAssert.assertLocked();
		log.info("Processing new orders at {}", Instant.now());
		orderService.processNewOrders();
	}

}
