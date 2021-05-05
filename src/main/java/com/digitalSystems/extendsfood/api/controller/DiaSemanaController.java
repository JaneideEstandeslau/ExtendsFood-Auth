package com.digitalSystems.extendsfood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalSystems.extendsfood.api.assembler.DiaSemanaModelAssembler;
import com.digitalSystems.extendsfood.api.model.DiaSemanaModel;
import com.digitalSystems.extendsfood.domain.model.DiaSemana;
import com.digitalSystems.extendsfood.domain.repository.DiaSemanaRepository;

@RestController
@RequestMapping("/dias-semana")
public class DiaSemanaController {

	@Autowired
	private DiaSemanaRepository diaSemanaRepository;
	
	@Autowired
	private DiaSemanaModelAssembler diaSemanaAssembler;
	
	@GetMapping
	public List<DiaSemanaModel> listar(){		
		List<DiaSemana> diasSemana = diaSemanaRepository.findAll();
		
		return diaSemanaAssembler.toCollectionModel(diasSemana);
	}
}
