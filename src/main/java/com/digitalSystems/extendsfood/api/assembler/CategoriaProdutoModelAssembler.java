package com.digitalSystems.extendsfood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.digitalSystems.extendsfood.api.controller.CategoriaProdutoController;
import com.digitalSystems.extendsfood.api.model.CategoriaProdutoModel;
import com.digitalSystems.extendsfood.domain.model.CategoriaProduto;

@Component
public class CategoriaProdutoModelAssembler 
	extends RepresentationModelAssemblerSupport<CategoriaProduto, CategoriaProdutoModel>{

	@Autowired
	private ModelMapper modelMapper;
	
	public CategoriaProdutoModelAssembler() {
		super(CategoriaProdutoController.class, CategoriaProdutoModel.class);
	}

	@Override
	public CategoriaProdutoModel toModel(CategoriaProduto categoriaProduto) {
		
		CategoriaProdutoModel categoriaProdutoModel = createModelWithId(categoriaProduto.getId(), categoriaProduto);
		
		modelMapper.map(categoriaProduto, categoriaProdutoModel);
		
//		categoriaProdutoModel.add(linkTo(CategoriaProdutoController.class).withRel("categorias"));
		
		return categoriaProdutoModel;
	}
	
	
//	public List<CategoriaProdutoResumoModel> toCollectionModel(List<CategoriaProduto> categorias){
//		return categorias.stream()
//				.map(categoriaProduto -> toModelResumo(categoriaProduto))
//				.collect(Collectors.toList());
//	}
}
