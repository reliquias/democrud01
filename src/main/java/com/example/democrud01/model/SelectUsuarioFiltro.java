package com.example.democrud01.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "FW23_SELECT_USUARIO_FILTRO")
public class SelectUsuarioFiltro{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String label;

    @Lob
    private String selectFiltro;
    
    @JoinColumn(name = "SELECT_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne
    private SelectUsuario selectUsuario;

	public SelectUsuarioFiltro(String label, String selectFiltro, SelectUsuario selectUsuario) {
		this.label = label;
		this.selectFiltro = selectFiltro;
		this.selectUsuario = selectUsuario;
	}
}
