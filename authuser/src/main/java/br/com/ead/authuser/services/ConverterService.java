package br.com.ead.authuser.services;

import java.util.List;

import org.springframework.data.domain.Page;

public interface ConverterService {
	<T> T convert(Object data, Class<T> type);

	<T> List<T> convert(List<?> dataList, Class<T> type);

	<T> Page<T> convert(Page<?> dataList, Class<T> type);
}
