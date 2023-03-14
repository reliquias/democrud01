package com.example.democrud01.dto;

import org.springframework.data.domain.Page;

import com.example.democrud01.enums.TipoAgente;
import com.example.democrud01.model.Agente;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AgenteDTO {
	
	private Long id;
	private String name;
	private String email;
	private String phone;
	private TipoAgente tipo;
	private Boolean desativado;
	
	public AgenteDTO(Agente agente) {
		this.id = agente.getId();
		this.name = agente.getName();
		this.email = agente.getEmail();
		this.phone = agente.getPhone();
		this.tipo = agente.getTipo();
		this.desativado = agente.getDesativado();
	}
	
	
	public static Page<AgenteDTO> converter(Page<Agente> agentes) {
		return agentes.map(AgenteDTO::new);
	}
	


}
