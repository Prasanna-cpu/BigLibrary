package com.biglibrary.order_service.repository;

import com.biglibrary.order_service.enums.OrderStatus;
import com.biglibrary.order_service.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Orders, UUID> {

	@Query("SELECT o FROM Orders o WHERE o.status = :status")
	List<Orders> findByStatus(OrderStatus status);

	@Query("SELECT o FROM Orders o WHERE o.orderNumber = :orderNumber")
	Optional<Orders> findByOrderNumber(String orderNumber);

	default void updateOrderStatus(String orderNumber, OrderStatus status) {
		Orders order = this.findByOrderNumber(orderNumber).orElseThrow();
		order.setStatus(status);
		this.save(order);
	}

	@Query("SELECT o FROM Orders o WHERE o.userName = :userName")
	List<Orders> findAllByUserName(String userName);

	@Query("select distinct o from Orders o left join fetch o.orderItems where o.userName = :userName and o.orderNumber = :orderNumber")
	Optional<Orders> findByUserNameAndOrderNumber(String userName, String orderNumber);
}
