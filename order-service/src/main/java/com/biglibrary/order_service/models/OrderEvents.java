package com.biglibrary.order_service.models;

import com.biglibrary.order_service.enums.OrderEventType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_events")
public class OrderEvents extends BaseEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private String orderNumber;

	@Column(name = "event_id", nullable = false, unique = true)
	private String eventId;

	@Column(name = "event_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private OrderEventType eventType;

	@Column(name = "payload", nullable = false)
	private String payload;

}
