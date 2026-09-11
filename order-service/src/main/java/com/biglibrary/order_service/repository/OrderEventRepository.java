package com.biglibrary.order_service.repository;

import com.biglibrary.order_service.models.OrderEvents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderEventRepository extends JpaRepository<OrderEvents, UUID> {

}
