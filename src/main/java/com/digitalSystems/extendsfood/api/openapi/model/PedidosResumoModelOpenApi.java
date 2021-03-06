package com.digitalSystems.extendsfood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.digitalSystems.extendsfood.api.model.PedidoResumoModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("PedidosResumoModel")
@Getter
@Setter
public class PedidosResumoModelOpenApi {
	
	private PedidosEmbeddedModelOpenApi _embedded;
	private Links _links;
	private PageModelOpenApi page;

	@ApiModel("PedidosEmbeddedModel")
	@Data
	public class PedidosEmbeddedModelOpenApi {

		private List<PedidoResumoModel> pedidos;

	}
}