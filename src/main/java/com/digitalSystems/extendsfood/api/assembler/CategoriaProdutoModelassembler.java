package com.digitalSystems.extendsfood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digitalSystems.extendsfood.api.model.CategoriaProdutoModel;
import com.digitalSystems.extendsfood.domain.model.CategoriaProduto;

@Component
public class CategoriaProdutoModelassembler {

	@Autowired
	private ModelMapper modelMapper;

	public CategoriaProdutoModel toModel(CategoriaProduto categoriaProduto) {
		return modelMapper.map(categoriaProduto, CategoriaProdutoModel.class);
	}
	
	public List<CategoriaProdutoModel> toCollectionModel(List<CategoriaProduto> categorias){
		return categorias.stream()
				.map(categoriaProduto -> toModel(categoriaProduto))
				.collect(Collectors.toList());
	}
}
