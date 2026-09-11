package com.biglibrary.order_service.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component("auditAwareImplementation")
public class AuditAwareImplementation implements AuditorAware<String> {

	@Override
	public java.util.Optional<String> getCurrentAuditor() {
		// Return the current user or any other identifier for auditing purposes
		return java.util.Optional.of("ORDER_SERVICE"); // Replace with actual user retrieval logic
	}
}
