package com.digitalSystems.extendsfood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digitalSystems.extendsfood.domain.model.DiaSemana;

@Repository
public interface DiaSemanaRepository extends JpaRepository<DiaSemana, Long>{

}
