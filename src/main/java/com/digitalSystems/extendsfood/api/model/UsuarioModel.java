package com.digitalSystems.extendsfood.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "usuarios")
@Getter
@Setter
public class UsuarioModel extends RepresentationModel<UsuarioModel>{

	@ApiModelProperty(example = "1", position = 5)
	private Long id;

	@ApiModelProperty(example = "892.798.690-35", position = 10)
	private String cpf;
	
	@ApiModelProperty(example = "Janeide Estandeslau", position = 15)
	private String nome;
	
	@ApiModelProperty(example = "janeide@gmail.com", position = 20)
	private String email;
}
