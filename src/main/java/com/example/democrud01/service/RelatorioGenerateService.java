package com.example.democrud01.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.example.democrud01.bean.BeanOptions;
import com.example.democrud01.bean.BeanSelectUsuarioCampos;
import com.example.democrud01.dto.SelectUsuarioExecuteFiltrosDTO;
import com.example.democrud01.model.SelectUsuario;
import com.example.democrud01.model.SelectUsuarioFiltro;
import com.example.democrud01.util.Utils;

@Service
public class RelatorioGenerateService {
	
	@Autowired
	private Environment env;
	
	public static String queryExecuted;
	
	public void geraSelect(
			List<SelectUsuarioExecuteFiltrosDTO> camposSelect,
			HttpServletResponse response, 
			SelectUsuario selectUsuario,
			String tipoDocumento
			 ) throws SQLException{
		
		if(camposSelect!=null && camposSelect.size() ==1 && camposSelect.get(0).getLabel() == null) {
			camposSelect = null;
		}
		
		if(camposSelect!=null){
			for(SelectUsuarioExecuteFiltrosDTO camps : camposSelect) {
				if(camps.getConteudo() != null){
					if(camps.getConteudo().equalsIgnoreCase("[todos]")){
						camps.setConteudo("%");
					}else if(camps.getConteudo().equalsIgnoreCase("true")){
						camps.setConteudo("1");
					}else if(camps.getConteudo().equalsIgnoreCase("false")){
						camps.setConteudo("0");
					}
				}
			}
		}
		String nomeArquivo = selectUsuario.getApelido();
		String titulo = selectUsuario.getTitulo();
	    String select = "";
	    if(camposSelect != null && camposSelect.size() > 0){
			select = substituiParametrosSelect(camposSelect, selectUsuario);
		}else{
			select = selectUsuario.getSelectUsu();
		}
	    geraVerifica(select, nomeArquivo, titulo, response, selectUsuario, tipoDocumento, camposSelect);
	}
	
	private void geraVerifica(String select, 
			String nomeArquivo, 
			String titulo, 
			HttpServletResponse response, 
			SelectUsuario selectUsuario,
			String tipoDocumento,
			List<SelectUsuarioExecuteFiltrosDTO> camposSelect
			)throws SQLException {
		
		List<String> registro = new ArrayList<String>();
		ArrayList<String> nomeCampo = new ArrayList<String>();
		ArrayList<String> tipoCampo = new ArrayList<String>();
		String codigoRelatorio = "";
		
		String sConexao = env.getProperty("spring.datasource.url");
		String sUserName = env.getProperty("spring.datasource.username");
		String sPassword = env.getProperty("spring.datasource.password");
		
		try (Connection con = DriverManager.getConnection(sConexao, sUserName,sPassword)) {

			try (Statement stmt = con.createStatement()) {
				this.queryExecuted = select.replace("#@!", "").replace("@", "");
				try (ResultSet rsVerifica = stmt.executeQuery(select.replace("#@!", "").replace("@", ""))) {
					int coluna = rsVerifica.getMetaData().getColumnCount();

					if (nomeCampo.size() == 0) {
						for (int i = 1; i <= coluna; i++) {
							nomeCampo.add(rsVerifica.getMetaData().getColumnName(i));
							tipoCampo.add(rsVerifica.getMetaData().getColumnClassName((i)));
						}
					}
					List<String> camposTotal = new ArrayList<String>();
					camposTotal = Utils.camposTotais(select);
					String linha = "";
					String linhaTotal = "";
					
					Map<Object, BigDecimal> parametros = new HashMap();
					for (String campoTotal : camposTotal) {
						parametros.put(campoTotal, BigDecimal.ZERO);
					}
					while (rsVerifica.next()) {
						linha = "";
						for (int i = 1; i <= coluna; i++) {
							linha = linha + rsVerifica.getObject(i) + "#@!";
							for (String campoTotal : camposTotal) {
								BigDecimal total = BigDecimal.ZERO;
								if (rsVerifica.getMetaData().getColumnLabel(i)
										.equalsIgnoreCase(campoTotal)) {
									if (rsVerifica.getObject(i) != null) {
										total = parametros.get(campoTotal).add(Utils.substituiNuloPorZero(new BigDecimal(Utils.toString(rsVerifica.getObject(i)))));
										parametros.remove(campoTotal);
										parametros.put(campoTotal, total);
									}
								}
							}
						}
						linha = linha.substring(0, linha.length() - 3);
						registro.add(linha);
					}

					int contLabel = 0;
					for (String campo : nomeCampo) {
						String campoAgora = "";
						for (String campoTotal : camposTotal) {
							if (campoTotal.equalsIgnoreCase(campo)) {
								linhaTotal = linhaTotal + parametros.get(campoTotal) + "#@!";
								campoAgora = campoTotal;
							}
						}
						if (!campoAgora.equalsIgnoreCase(campo)) {
							if (contLabel == 0) {
								linhaTotal = linhaTotal + "Total" + "#@!";
							} else {
								linhaTotal = linhaTotal + " " + "#@!";
							}
						}
						contLabel++;
					}
					linhaTotal = linhaTotal.substring(0, linhaTotal.length() - 3);
					if (camposTotal.size() != 0) {
						registro.add(linhaTotal);
					}
					
					
					GeraDocumentoLogic geraDoc = new GeraDocumentoLogic(response, tipoDocumento, nomeArquivo, titulo,camposSelect, true, codigoRelatorio);
					geraDoc.geraDocumento(nomeCampo, tipoCampo, registro, selectUsuario);
				}
			}
		}
	}
	

	public String substituiParametrosSelect(List<SelectUsuarioExecuteFiltrosDTO> camposSelect, SelectUsuario selectUsuario){
		
		String select = selectUsuario.getSelectUsu().toUpperCase(); 
    	for (SelectUsuarioExecuteFiltrosDTO bean : camposSelect) {
    		select = select.replaceFirst(":" + bean.getLabel(), "'" + bean.getConteudo() + "'");
		
    		if(bean.getTipo().equals("texto")){ //Se o filtro começa com S_ então é texto, 
    			if(bean.getConteudo() == null || bean.getConteudo().trim().equals("") || bean.getConteudo().trim().equals("%")){//então se não for digitda nada tomará "todos" por default
    				select = select.replace(":S_" + bean.getLabel().toUpperCase(), "'%'");
    				bean.setConteudo("%");
    			}else{
    				//se o filtro tiver valor, segue tratamento normal
    				select = select.replace(":S_" + bean.getLabel().toUpperCase(), "'" + bean.getConteudo() + "'");
    			}
    		}else if(bean.getTipo().equals("data")){
    			if(bean.getConteudo() == null || bean.getConteudo().trim().equals("") || bean.getConteudo().trim().equals("%")){
    				select = select.replace(":D_" + bean.getLabel().toUpperCase(), "'%D'");
    				bean.setConteudo("%D");
    			}else{
    				//se o filtro tiver valor, segue tratamento normal
    				select = select.replace(":D_" + bean.getLabel().toUpperCase(), "'" + bean.getConteudo() + "'");
    			}
    		}else if(bean.getTipo().equals("calendar")){ //se o tipo é calendar então começa com C_ e é calendario
    			if(bean.getConteudo() == null ||bean.getConteudo().trim().equals("") || bean.getConteudo().trim().equals("%")){ //então se não for digitda nada tomará "todos" por default
    				select = select.replace(":C_" + bean.getLabel().toUpperCase(), "'%D'");
    				bean.setConteudo("%D");
    			}else{
    				//se o filtro tiver valor, segue tratamento normal
    				select = select.replace(":C_" + bean.getLabel().toUpperCase(), "'" + bean.getConteudo() + "'");
    			}
    		}else if(bean.getTipo().equals("numerico")){ //tipo Numerico
    			select = select.replace(":N_" + bean.getLabel().toUpperCase(), bean.getConteudo().replace(",", ".")); //não possui aspas simples e, se for decimal, substitui vírgula por ponto
    		}
    		else{
    			select = select.replace(":" + bean.getLabel().toUpperCase(), "'" + bean.getConteudo() + "'");
    		}
    		
    		//////////////////////////////////////////////////////////////////////////////////
    		////Correção da falta dos nulos quando o [todos] é usado no select do usuário.  //
    		//////////////////////////////////////////////////////////////////////////////////
    		if(bean.getConteudo().trim().equals("%")){
    			int index = select.lastIndexOf("'%'") + 3;
    			String aux = select.substring(0,index);
    			int ultimoFtw = aux.toUpperCase().lastIndexOf("FW23_");
    			if(ultimoFtw > -1){
	    			String aux2 = aux.substring(ultimoFtw,index);
	    			int vazio = aux2.indexOf(" ");
	    			String tabela = aux2.substring(0,vazio);
	    			String aux3 = "( " + aux2 + " OR " + tabela + " IS NULL )";
	    			int inicioRetirarAux2 = select.lastIndexOf(aux2);
	    			int fimRetirarAux2 = select.lastIndexOf(aux2)+aux2.length();
	    			String s1 = select.substring(0,inicioRetirarAux2);
	    			String s2 = select.substring(fimRetirarAux2,select.length());
	    			select = s1 + aux3 + s2;
    			} else {
    				int ultimoIvw = aux.toUpperCase().lastIndexOf("VIW");
    				if(ultimoIvw > -1){
    	    			String aux2 = aux.substring(ultimoIvw,index);
    	    			int vazio = aux2.indexOf(" ");
    	    			String tabela = aux2.substring(0,vazio);
    	    			String aux3 = "( " + aux2 + " OR " + tabela + " IS NULL )";
    	    			int inicioRetirarAux2 = select.lastIndexOf(aux2);
    	    			int fimRetirarAux2 = select.lastIndexOf(aux2)+aux2.length();
    	    			String s1 = select.substring(0,inicioRetirarAux2);
    	    			String s2 = select.substring(fimRetirarAux2,select.length());
    	    			select = s1 + aux3 + s2;
        			} else {
        				int ultimoEvw = aux.toUpperCase().lastIndexOf("EVW");
        				if(ultimoEvw > -1){
        	    			String aux2 = aux.substring(ultimoEvw,index);
        	    			int vazio = aux2.indexOf(" ");
        	    			String tabela = aux2.substring(0,vazio);
        	    			String aux3 = "( " + aux2 + " OR " + tabela + " IS NULL )";
        	    			int inicioRetirarAux2 = select.lastIndexOf(aux2);
        	    			int fimRetirarAux2 = select.lastIndexOf(aux2)+aux2.length();
        	    			String s1 = select.substring(0,inicioRetirarAux2);
        	    			String s2 = select.substring(fimRetirarAux2,select.length());
        	    			select = s1 + aux3 + s2;
            			}
        			}
    			}
    		}else if(bean.getConteudo().trim().equals("%D")){
    			if(isStoredProcedure(select)){
    				select = trataDateParaStoredProcedure(select);
    			}
    			
    			int index = select.lastIndexOf("'%D'") + 4;
    			String aux = select.substring(0,index);
    			int ultimoFtw = aux.toUpperCase().lastIndexOf("FW23_");
    			if(ultimoFtw > -1){
	    			String aux2 = aux.substring(ultimoFtw,index);
	    			int vazio = aux2.indexOf(" ");
	    			String tabela = aux2.substring(0,vazio);
	    			String aux3 = "( " + tabela + " IS NULL OR " + tabela + " IS NOT NULL )";
	    			int inicioRetirarAux2 = select.lastIndexOf(aux2);
	    			int fimRetirarAux2 = select.lastIndexOf(aux2)+aux2.length();
	    			String s1 = select.substring(0,inicioRetirarAux2);
	    			String s2 = select.substring(fimRetirarAux2,select.length());
	    			select = s1 + aux3 + s2;
    			} else {
    				int ultimoIvw = aux.toUpperCase().lastIndexOf("VIW");
        			if(ultimoIvw > -1){
    	    			String aux2 = aux.substring(ultimoIvw,index);
    	    			int vazio = aux2.indexOf(" ");
    	    			String tabela = aux2.substring(0,vazio);
    	    			String aux3 = "( " + tabela + " IS NULL OR " + tabela + " IS NOT NULL )";
    	    			int inicioRetirarAux2 = select.lastIndexOf(aux2);
    	    			int fimRetirarAux2 = select.lastIndexOf(aux2)+aux2.length();
    	    			String s1 = select.substring(0,inicioRetirarAux2);
    	    			String s2 = select.substring(fimRetirarAux2,select.length());
    	    			select = s1 + aux3 + s2;
        			} else {
        				int ultimoEvw = aux.toUpperCase().lastIndexOf("EVW");
            			if(ultimoEvw > -1){
        	    			String aux2 = aux.substring(ultimoEvw,index);
        	    			int vazio = aux2.indexOf(" ");
        	    			String tabela = aux2.substring(0,vazio);
        	    			String aux3 = "( " + tabela + " IS NULL OR " + tabela + " IS NOT NULL )";
        	    			int inicioRetirarAux2 = select.lastIndexOf(aux2);
        	    			int fimRetirarAux2 = select.lastIndexOf(aux2)+aux2.length();
        	    			String s1 = select.substring(0,inicioRetirarAux2);
        	    			String s2 = select.substring(fimRetirarAux2,select.length());
        	    			select = s1 + aux3 + s2;
            			}
        			}
    			}
    		}     		
    		
    	}
    	return select;
    }

	private boolean isStoredProcedure(String select) {
		if (select == null)
			throw new RuntimeException("Campo select não deve ser vazio!");

		return select.toUpperCase().contains("EXEC ") && !select.toUpperCase().contains("SELECT ");
	}

	private String trataDateParaStoredProcedure(String select) {

		if (select.contains("01/01/1900")) {
			select = select.replace("%D", "01/01/2999");
		} else {
			select = select.replace("%D", "01/01/1900");
		}

		return select;
	}
	
	
	public List<BeanSelectUsuarioCampos> Labels(SelectUsuario selectUsuario, List<SelectUsuarioFiltro> filtros) throws SQLException {
		String select = selectUsuario.getSelectUsu();
		String[] dados = select.split(":");

		List<BeanSelectUsuarioCampos> camposSelect = new ArrayList<BeanSelectUsuarioCampos>();
		int cont = 0;
		for (String label : dados) {
			String[] lab;
			BeanSelectUsuarioCampos bean = new BeanSelectUsuarioCampos();
			bean.setId(new Long(cont));
			
			lab = label.split(" ");
			boolean haTodos = false;
			if(lab[0].length()>2 && lab[0].substring(0, 2).equalsIgnoreCase("S_")){
				bean.setLabel(lab[0].substring(2, lab[0].length()));
				bean.setConteudo("[todos]");
				bean.setTipo("texto");
				haTodos = true;
			}else if(lab[0].length()>2 && lab[0].substring(0, 2).equalsIgnoreCase("D_")){
				bean.setLabel(lab[0].substring(2, lab[0].length()));
				bean.setConteudo("[todos]");
				bean.setTipo("data");
				haTodos = true;
			}else if(lab[0].length()>2 && lab[0].substring(0, 2).equalsIgnoreCase("C_")){ //tipo Calendar
				bean.setLabel(lab[0].substring(2, lab[0].length()));
				//bean.setConteudo("[todos]");
				bean.setTipo("calendar");
				haTodos = false;
			}else if(lab[0].length()>2 && lab[0].substring(0, 2).equalsIgnoreCase("N_")){ //tipo Numero
				bean.setLabel(lab[0].substring(2, lab[0].length()));
				bean.setTipo("numerico");
				haTodos = false;
			}
			else{
				bean.setLabel(lab[0]);
				haTodos = false;
			}
			if(!camposSelect.contains(bean)){
				if(cont>0 && !filtros.isEmpty()){
					List<SelectUsuarioFiltro> filtered= filtros.stream().filter(filtro -> filtro.getLabel().equals(bean.getLabel())).collect(Collectors.toList());
					SelectUsuarioFiltro filtro = filtered != null ? filtered.get(0) : null;
					if(filtro!= null){
						List<BeanOptions> lista = listaFiltros(bean, filtro, haTodos);
						bean.setLista(lista);
						Map<String, String> listMap = new HashMap<>();
						for (BeanOptions beanOptions : lista) {
							listMap.put(beanOptions.getConteudo(), beanOptions.getConteudo());
						}
						bean.setListMap(listMap);
					}
				}
				camposSelect.add(bean);
			}
			cont++;
		}
		if(!camposSelect.isEmpty()) { camposSelect.remove(0);}
		return camposSelect;
	}
	
	public List<BeanOptions> listaFiltros(BeanSelectUsuarioCampos camposSelect, SelectUsuarioFiltro filtro, boolean haTodos) throws SQLException{
		List<BeanOptions> filtros = new ArrayList<BeanOptions>();
		
		String sConexao = env.getProperty("spring.datasource.url");
		String sUserName = env.getProperty("spring.datasource.username");
		String sPassword = env.getProperty("spring.datasource.password");
		
		try (Connection con = DriverManager.getConnection(sConexao, sUserName,sPassword)) {

			try (Statement stmt = con.createStatement()) {
				try (ResultSet rsVerifica = stmt.executeQuery(filtro.getSelectFiltro())) {
					int cont = 1;
					int nColumns = rsVerifica.getMetaData().getColumnCount();
					String campo1 = rsVerifica.getMetaData().getColumnName(1);		
					
					while (rsVerifica.next()) {
						
						if(nColumns>1 && campo1.equalsIgnoreCase("ID")) {
							BeanOptions bean = new BeanOptions(rsVerifica.getLong(1), rsVerifica.getString(2));
							filtros.add(bean);
							cont++;
						}else {
							if(cont == 1 && haTodos){
								BeanOptions beanTodos = new BeanOptions(ThreadLocalRandom.current().nextLong(1000, 9999),"[todos]");
								filtros.add(beanTodos);
								cont++;
							}

							BeanOptions bean = new BeanOptions(ThreadLocalRandom.current().nextLong(1000, 9999),(String) rsVerifica.getObject(1));
							filtros.add(bean);
							cont++;
						}
						
					 }
				}
			}
		}
		return filtros;
	}

}