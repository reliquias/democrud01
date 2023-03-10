package com.example.democrud01.controller;

import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.democrud01.bean.BeanSelectUsuarioCampos;
import com.example.democrud01.dto.SelectUsuarioDTO;
import com.example.democrud01.dto.SelectUsuarioExecuteDTO;
import com.example.democrud01.dto.SelectUsuarioExecuteFiltrosDTO;
import com.example.democrud01.dto.SelectUsuarioForm;
import com.example.democrud01.model.SelectUsuario;
import com.example.democrud01.model.SelectUsuarioFiltro;
import com.example.democrud01.repository.SelectUsuarioFiltroRepository;
import com.example.democrud01.service.RelatorioGenerateService;
import com.example.democrud01.service.SelectUsuarioService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping({"/api/selectUsuario"})
@Api(value = "SelectUsuario")
@CrossOrigin(origins = {"http://localhost:4200/", "http://www.505crm.com.br/"}, maxAge = 3600, allowCredentials = "true", allowedHeaders = "*")
public class SelectUsuarioController {

	@Autowired
	private SelectUsuarioService selectUsuarioService;
	
	@Autowired
	private SelectUsuarioFiltroRepository selectUsuarioFiltroRepository;
	
	@Autowired
	private RelatorioGenerateService relatorioGenerateService;

	@ApiOperation(value = "Cadastrar um novo selectUsuario do sistema")
    @PostMapping
    public ResponseEntity<SelectUsuarioDTO> create(@RequestBody SelectUsuarioForm form, UriComponentsBuilder uriBuilder) {
        SelectUsuario selectUsuario = form.converter();
        selectUsuarioService.create(selectUsuario);
		URI uri = uriBuilder.path("/api/selectUsuario/{id}").buildAndExpand(selectUsuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new SelectUsuarioDTO(selectUsuario));
    }

	@ApiOperation(value = "Alterar Pedido de Venda do sistema")
	@PutMapping(value="/{id}")
	public ResponseEntity<SelectUsuarioDTO> update(@PathVariable("id") long id, @RequestBody SelectUsuarioForm form, UriComponentsBuilder uriBuilder) {
		SelectUsuario selectUsuario = form.converter();
		selectUsuarioService.update(id, selectUsuario);
		URI uri = uriBuilder.path("/api/selectUsuario/{id}").buildAndExpand(selectUsuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new SelectUsuarioDTO(selectUsuario));
	}

	@ApiOperation(value = "Procura um selectUsuario pelo id")
    @GetMapping(path = "/{id}")
    public ResponseEntity get(@PathVariable final Long id) {
    	return selectUsuarioService.get(id)
 	           .map(record -> ResponseEntity.ok().body(record))
 	           .orElse(ResponseEntity.notFound().build());
    }
	
	@DeleteMapping(path ={"/{id}"})
	public ResponseEntity <?> delete(@PathVariable long id) {
	   return selectUsuarioService.get(id)
	           .map(record -> {
	        	   selectUsuarioService.deleteById(id);
	               return ResponseEntity.ok().build();
	           }).orElse(ResponseEntity.notFound().build());
	}
    
	@ApiOperation(value = "Listas todos os selectUsuarios do sistema")
    @GetMapping
    public Page<SelectUsuarioDTO> getAll(@RequestParam int pagina, @RequestParam int qtd, @RequestParam String ordenacao, @RequestParam Boolean ordemAsc ) {
		Pageable page = PageRequest.of(pagina, qtd, ordemAsc ? Direction.ASC : Direction.DESC, ordenacao);
		
		Page<SelectUsuario> selectUsuarios = selectUsuarioService.getAll(page);
        return SelectUsuarioDTO.converter(selectUsuarios);
    }
	
	@GetMapping(path = "/filtros")
    public List<BeanSelectUsuarioCampos> relatorioUsuarioFiltros(@RequestParam Long idRelatorio) throws SQLException {

        SelectUsuario selectUsuario = selectUsuarioService.get(idRelatorio).get();
        List<SelectUsuarioFiltro> filtros = selectUsuarioFiltroRepository.findBySelectUsuario(selectUsuario);
        
        List<BeanSelectUsuarioCampos> response = relatorioGenerateService.Labels(selectUsuario, filtros);
        
        return response;
    }	

	
	@GetMapping(path = "/docUsu", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<?> relatorioUsuario(HttpServletResponse response, @RequestParam Long idRelatorio, @RequestParam String tipoDocumento) throws SQLException, InterruptedException {

        SelectUsuario selectUsuario = selectUsuarioService.get(idRelatorio).get();
        
        try {
			relatorioGenerateService.geraSelect(new ArrayList<SelectUsuarioExecuteFiltrosDTO>(), response, selectUsuario, tipoDocumento);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return ResponseEntity.ok().build();
    }	

	
	@PostMapping(path = "/docUsu")
    public ResponseEntity<?> relatorioUsuario(HttpServletResponse response, @RequestBody SelectUsuarioExecuteDTO selectUsuarioExecuteDTO) throws SQLException, InterruptedException {

        SelectUsuario selectUsuario = selectUsuarioService.get(selectUsuarioExecuteDTO.getIdRelatorio()).get();
        
        try {
			relatorioGenerateService.geraSelect(selectUsuarioExecuteDTO.getFiltros(), response, selectUsuario, selectUsuarioExecuteDTO.getTipoDocumento().toString());
		} catch (SQLException e) {
			String errorMessage = RelatorioGenerateService.queryExecuted != null ? e.getMessage()+ " \n Query Executada: " + RelatorioGenerateService.queryExecuted : e.getMessage();
			e.printStackTrace();
			return ResponseEntity
		            .status(HttpStatus.BAD_REQUEST)
		            .body(errorMessage);
		}
        return ResponseEntity.ok().build();
    }	
}
