package com.biglibrary.catalog_service.records;

import java.util.List;

public record PagedResult<T>(List<T> data, int totalElements, int pageNumber, int totalPages, boolean isFirst,
		boolean isLast, boolean hasNext, boolean hasPrevious) {
}
