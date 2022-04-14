package br.com.ead.course.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.ead.course.services.ConverterService;

@Service
public class ConverterServiceImpl implements ConverterService{
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public <T> T convert(Object data, Class<T> type) {
		return modelMapper.map(data, type);
	}

	@Override
	public <T> List<T> convert(List<?> dataList, Class<T> type) {
		return dataList.stream().map(d -> convert(d, type)).collect(Collectors.toList());
	}

	@Override
	public <T> Page<T> convert(Page<?> dataList, Class<T> type) {
		return dataList.map(d -> convert(d, type));
	}

}
