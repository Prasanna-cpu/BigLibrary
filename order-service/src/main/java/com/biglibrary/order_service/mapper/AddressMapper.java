package com.biglibrary.order_service.mapper;

import com.biglibrary.order_service.dto.AddressDTO;
import com.biglibrary.order_service.models.embedded_models.Address;

public class AddressMapper {

	public static AddressDTO mapToAddressDTO(Address address) {
		AddressDTO addressDTO = new AddressDTO();

		addressDTO.setAddressLine1(address.addressLine1());
		addressDTO.setAddressLine2(address.addressLine2());
		addressDTO.setCity(address.city());
		addressDTO.setState(address.state());
		addressDTO.setZipCode(address.zipCode());
		addressDTO.setCountry(address.country());

		return addressDTO;
	}

	public static Address mapToAddress(AddressDTO addressDTO) {
		Address address = new Address(addressDTO.getAddressLine1(), addressDTO.getAddressLine2(), addressDTO.getCity(),
				addressDTO.getState(), addressDTO.getZipCode(), addressDTO.getCountry());

		return address;

	}

}
