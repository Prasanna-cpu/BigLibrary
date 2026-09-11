package com.biglibrary.notification_service.repository;

import com.biglibrary.notification_service.entity.OrderEvents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderEventRepository extends JpaRepository<OrderEvents, UUID> {

	boolean existsByEventId(String eventId);

}
