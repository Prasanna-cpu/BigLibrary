package com.biglibrary.order_service.models;

import com.biglibrary.order_service.enums.OrderStatus;
import com.biglibrary.order_service.models.embedded_models.Address;
import com.biglibrary.order_service.models.embedded_models.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Orders extends BaseEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(name = "order_number", nullable = false)
	private String orderNumber;

	@Column(name = "username", nullable = false)
	private String userName;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<OrderItems> orderItems;

	@Embedded
	@AttributeOverrides({@AttributeOverride(name = "name", column = @Column(name = "customer_name")),
			@AttributeOverride(name = "email", column = @Column(name = "customer_email")),
			@AttributeOverride(name = "phone", column = @Column(name = "customer_phone"))})
	private Customer customer;

	@Embedded
	@AttributeOverrides({@AttributeOverride(name = "addressLine1", column = @Column(name = "delivery_address_line1")),
			@AttributeOverride(name = "addressLine2", column = @Column(name = "delivery_address_line2")),
			@AttributeOverride(name = "city", column = @Column(name = "delivery_address_city")),
			@AttributeOverride(name = "state", column = @Column(name = "delivery_address_state")),
			@AttributeOverride(name = "zipCode", column = @Column(name = "delivery_address_zip_code")),
			@AttributeOverride(name = "country", column = @Column(name = "delivery_address_country"))})
	private Address deliveryAddress;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	private String comments;

}
