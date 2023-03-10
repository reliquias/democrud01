package com.example.democrud01.service;


import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.example.democrud01.dto.SelectUsuarioExecuteFiltrosDTO;
import com.example.democrud01.enums.FormatoPaginaEnum;
import com.example.democrud01.enums.Localizacao;
import com.example.democrud01.enums.TipoDocumento;
import com.example.democrud01.model.SelectUsuario;
import com.example.democrud01.util.Utils;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

public class GeraDocumentoLogic {

	private final String tipoDocumento;
	private String nomeArquivoIntl;
	boolean ultimaLinhaNegrito;
	private final String codigoRelatorio;
	private String titulo;
	private HttpServletResponse response;

	public GeraDocumentoLogic(HttpServletResponse response, String tipoDocumento, String nomeArquivo, String titulo,
			List<SelectUsuarioExecuteFiltrosDTO> cabecalho, boolean ultimaLinhaNegrito, String codigoRelatorio) {
		this.response = response;
		this.tipoDocumento = tipoDocumento;
		this.nomeArquivoIntl = nomeArquivo;
		this.ultimaLinhaNegrito = ultimaLinhaNegrito;
		this.codigoRelatorio = codigoRelatorio;
		this.titulo = titulo;
		this.setCabecalho(cabecalho);
	}

	public void geraDocumento(List<String> nomeCampo, List<String> tipoCampo, List<String> registro,
			SelectUsuario selectUsuario) {
		if (this.tipoDocumento.equals(TipoDocumento.pdf.toString())) {
			gerarArquivoPDF(nomeCampo, tipoCampo, registro, selectUsuario);
		} else if (this.tipoDocumento.equals(TipoDocumento.xls.toString())) {
			gerarArquivoXLS(nomeCampo, tipoCampo, registro, selectUsuario);
		}
	}

	public void gerarArquivoPDF(List<String> nomeCampo, List<String> tipoCampo, List<String> registro, SelectUsuario selectUsuario) {
        // Esta POG aqui abaixo foi criado para o rodape, sera necessario verificar como colocar data e numeracao de pagina no mesmo rodape.
        String espacamento = "                                                                                                                                                                                          ";
        //O primeiro argumento e o tamanho da pagina.
        //Os proximos argumentos sao as margens esquerda, direita, superior e inferior, respectivamente.
        //Document document = new Document(PageSize.A4.rotate(), 20, 20, 20, 20);

        Document document;
        if (selectUsuario.getFormatoPagina().equals(FormatoPaginaEnum.RETRATO)) {
            document = new Document(PageSize.A4, 20, 20, 20, 20);
        } else {
            document = new Document(PageSize.A4.rotate(), 20, 20, 20, 20);
        }
        
        try {
        	//Obtem o caminho para o arquivo e efetua a leitura
        	//HttpServletResponse response =(HttpServletResponse) context.getResponse();
        	
            response.setContentType("inline/download"); //Faz download do arquivo pdf direto
//            response.setContentType("application/pdf"); //Primeiro pergunta se quer abrir ou fazer download (nada muda no I.E)
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, buffer);

            // Montagem do Cabecalho
            Font titleFont = new Font(Font.HELVETICA, 14, Font.BOLD, new Color(0, 0, 0));
            Paragraph cabecalho = new Paragraph(this.titulo, titleFont);
            cabecalho.setAlignment(Paragraph.ALIGN_LEFT);
            HeaderFooter header = new HeaderFooter(cabecalho, false);
            document.setHeader(header);

            // Montagem do Rodape
//            falta internacionalizar
            String rodapePOG = "Data: " + Utils.toString(Calendar.getInstance(), "dd/MM/yyyy") + "   " + this.codigoRelatorio + espacamento + " Pag.: ";
            //Paragraph rodape = new Paragraph("Data: " + Utils.toString(Calendar.getInstance(), "dd/MM/yyyy"),
            //        FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new Color(0, 0, 0)));
            //rodape.setAlignment(rodape.ALIGN_LEFT);
            HeaderFooter footer = new HeaderFooter(new Phrase(rodapePOG), true);
            footer.setBorder(Rectangle.TOP);
            document.setFooter(footer);

            // Coloca o numero da pagina
            //Paragraph numPagina = new Paragraph("Pag.: ",
            //        FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new Color(0, 0, 0)));
            //numPagina.setAlignment(numPagina.ALIGN_RIGHT);
            //HeaderFooter footer1 = new HeaderFooter(numPagina, true);
            //document.setFooter(footer1);

            //Cria da Tabela
            Table tabela = new Table(nomeCampo.size(), registro.size());
            tabela.setPadding(2);
            tabela.setAlignment("LEFT");
            tabela.setWidth(100);

            document.open();
            // Montagem dos Campos
            int alinha;
            for (int cont = 0; cont < nomeCampo.size(); cont++) {
//                Paragraph auxiliar = new Paragraph(nomeCampo.get(cont),
                String tipo = tipoCampo.get(cont);
                Paragraph auxiliar = new Paragraph((nomeCampo.get(cont)),
                        FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new Color(0, 0, 0)));
                Cell c1 = new Cell(auxiliar);
                alinha = 0;
                if (tipo.equals("java.math.BigDecimal")) {
                    alinha = 2;
                } else if (tipo.equals("java.util.Calendar")) {
                    alinha = 1;
                }
                c1.setHeader(true);
                c1.setHorizontalAlignment(alinha);
                tabela.addCell(c1);
            }

            tabela.endHeaders();

            //Montagem dos registros
            String camposTratados;
            for (int cont = 0; cont < registro.size(); cont++) {
                camposTratados = registro.get(cont).toString();//.replace('[', ' ');
//                camposTratados = camposTratados.replace(']', ' ');
                String[] campos = camposTratados.split("#@!", nomeCampo.size());

                for (int cont2 = 0; cont2 < campos.length; cont2++) {

                    String tipo = tipoCampo.get(cont2);                    
                    String campo = limpaNulo(campos[cont2]);
                    alinha = 0;
                    if (tipo.equals("java.math.BigDecimal") && !campo.equalsIgnoreCase(" ")) {                       
                        Double parseDouble = Double.parseDouble(Utils.substituiVazioPorZero(campo));
                        BigDecimal parseBig = BigDecimal.valueOf(parseDouble.doubleValue());
                        
                        NumberFormat formatMoney = NumberFormat.getInstance(Localizacao.PORTUGUES.getLocale());
                        formatMoney.setMaximumFractionDigits(2);
                        formatMoney.setMinimumFractionDigits(2);
                        
                        campo = formatMoney.format(parseBig);
                        alinha = 2;
                     } else if (tipo.equals("java.util.Calendar")) {
                         alinha =1;
                     }
                    Paragraph auxiliar = null;
                    if ((registro.size() == cont + 1) && this.ultimaLinhaNegrito) {
                        auxiliar = new Paragraph(campo,
                                FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, new Color(0, 0, 0)));
                    } else {
                        auxiliar = new Paragraph(campo,
                                FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new Color(0, 0, 0)));
                    }

                    Cell c2 = new Cell(auxiliar);
                    c2.setHorizontalAlignment(alinha);
                    if ((cont % 2) == 1) {
                        c2.setBackgroundColor(new Color(204, 204, 255));
                    }
//                    if (tipo.equals("BigDecimal")) {

  //                  }
                     c2.setHeader(false);
                     tabela.addCell(c2);
                }
            }

            document.add(tabela);
            document.close();

            nomeArquivoIntl = nomeArquivoIntl + ".pdf";	
            
            byte[] bytes = buffer.toByteArray();
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "attachment; filename=" + nomeArquivoIntl);
            ServletOutputStream ouputStream = response.getOutputStream();
            ouputStream.write(bytes, 0, bytes.length);
            ouputStream.flush();
            ouputStream.close();
        } catch (DocumentException de) {
            System.err.println(de.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }


    private String limpaNulo(String campo) {
        if (campo.indexOf("null") > -1) {
            return "";
        } else {
            return campo;
        }
    }
    
    public void gerarArquivoXLS(List<String> nomeCampo, List<String> tipoCampo,
    		List<String> registro, SelectUsuario selectUsuario) {
    		try {
    			Boolean temPlanilha = false;
    			HSSFWorkbook wb;
    			HSSFSheet planilha;
    			SelectUsuario select = null;
    			if (selectUsuario != null) {
    				select = selectUsuario;
    			}
    			if (select != null && select.getArquivo() != null) {
    				wb = new HSSFWorkbook(new ByteArrayInputStream(select.getArquivo()));
    				planilha = wb.getSheetAt(0);
    				temPlanilha = true;
    			} else {
    				wb = new HSSFWorkbook();
    				planilha = wb.createSheet("Relatorio " + nomeArquivoIntl);
    			}

    			String defaultFontNome = "Arial";
    			short defaultFontHeight = 10;

    			if (planilha.getRow(4) != null
    					&& planilha.getRow(4).getCell(0) != null
    					&& planilha.getRow(4).getCell(0).getCellStyle() != null
    					&& planilha.getRow(4).getCell(0).getCellStyle().getFont(wb) != null
    					&& planilha.getRow(4).getCell(0).getCellStyle().getFont(wb).getFontName() != null) {

    				defaultFontNome = planilha.getRow(4).getCell(0).getCellStyle().getFont(wb).getFontName();
    				defaultFontHeight = planilha.getRow(4).getCell(0).getCellStyle().getFont(wb).getFontHeightInPoints();
    			}

    			HSSFFont defaultFont = wb.createFont();
    			defaultFont.setFontName(defaultFontNome);
    			defaultFont.setFontHeightInPoints(defaultFontHeight);

    			HSSFFont fonte = wb.createFont();
    			fonte.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    			HSSFCellStyle estilo = wb.createCellStyle();
    			estilo.setFont(fonte);
    			estilo.setAlignment(HSSFCellStyle.ALIGN_LEFT);

    			// Estilo numerico Cabecalho
    			HSSFCellStyle estiloNumeroCabec = wb.createCellStyle();
    			estiloNumeroCabec.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
    			estiloNumeroCabec.setFont(fonte);

    			// Estilo numerico
    			HSSFCellStyle estiloNumero = wb.createCellStyle();
    			estiloNumero.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
    			estiloNumero.setFont(defaultFont);

    			// Estilo general
    			HSSFCellStyle estiloGeneral = wb.createCellStyle();
    			estiloGeneral.setAlignment(HSSFCellStyle.ALIGN_LEFT);
    			estiloGeneral.setFont(defaultFont);

    			// Estilo data
    			HSSFCellStyle dateCellStyle = wb.createCellStyle();
    			short dfDefault = wb.createDataFormat().getFormat("dd/MM/yyyy");
    			dateCellStyle.setDataFormat(dfDefault);
    			dateCellStyle.setFont(defaultFont);

    			// Definindo o Titulo
    			HSSFRow titulo = planilha.createRow((int) 0);
    			HSSFCell colunaTitulo = titulo.createCell(0);
    			colunaTitulo.setCellValue(this.titulo);
    			colunaTitulo.setCellStyle(estilo);

    			HSSFRow row = planilha.getRow((int) 2);
    			if (row == null) {
    				row = planilha.createRow((int) 2);
    			}
    			for (int cont = 0; cont < nomeCampo.size(); cont++) {
    				String tipo = tipoCampo.get(cont);
    				String nomeCampoNormalizado = nomeCampo.get(cont);
    				if (!temPlanilha) {
    					planilha.setColumnWidth(cont, ((1 + (nomeCampoNormalizado.length() * 2)) * 256));
    				}

    				HSSFCell coluna = row.getCell(cont);
    				if (coluna == null) {
    					coluna = row.createCell(cont);
    				}
    				coluna.setCellValue(nomeCampoNormalizado);

    				if (tipo.equals("java.math.BigDecimal") && !temPlanilha) {
    					coluna.setCellStyle(estiloNumeroCabec);
    				}
    			}

    			String camposTratados;
    			int contLinhas = 4;
    			List<HSSFCellStyle> listaCellStyle = new ArrayList<HSSFCellStyle>();
    			for (int cont = 0; cont < registro.size(); cont++) {
    				camposTratados = registro.get(cont).toString().replace('[', ' ');
    				camposTratados = camposTratados.replace(']', ' ');
    				String[] campos = camposTratados.split("#@!",nomeCampo.size() + 1);
    				HSSFRow row2 = planilha.createRow((int) contLinhas++);
    				for (int cont2 = 0; cont2 < campos.length; cont2++) {
    					if (contLinhas == 5) {
    						if (planilha != null && planilha.getRow(5) != null
    								&& planilha.getRow(5).getCell(cont2) != null) {
    							listaCellStyle.add(cont2, planilha.getRow(5).getCell(cont2).getCellStyle());
    						} else {
    							listaCellStyle.add(cont2, estiloGeneral);
    						}
    					}
    					HSSFCell celula = row2.createCell(cont2);
    					String tipo = tipoCampo.get(cont2);
    					String campo = limpaNulo(campos[cont2]);
    					if (campo.equals("Total:")) {
    						celula.setCellValue(campo);
    						celula.setCellStyle(estiloNumeroCabec);
    					} else {
    						if (tipo.equals("java.math.BigDecimal")) {
    							if (!campo.trim().equals("")) {
    								celula.setCellValue(Double.parseDouble(campo));
    							} else {
    								celula.setCellValue("");
    							}

    							if ((registro.size() == cont + 1) && this.ultimaLinhaNegrito) {
    								celula.setCellStyle(estiloNumeroCabec);
    							} else {
    								if (!temPlanilha) {
    									celula.setCellStyle(listaCellStyle.get(cont2));
    								}
    							}
    						} else if (tipo.equals("java.sql.Timestamp")&& !campo.trim().equals("")) {
    							celula.setCellValue(campo);
    						    /*if(campo.length() > 10){
    							campo = campo.substring(0, 10);
    						    }
    							celula.setCellValue(Utils.stringToCalendar(campo));*/

    							short df = wb.createDataFormat().getFormat("dd/MM/yyyy");
    							HSSFCellStyle csdat = listaCellStyle.get(cont2);
    							csdat.setDataFormat(df);
    							celula.setCellStyle(csdat);
    						} else {
    							if ((registro.size() == cont + 1) && this.ultimaLinhaNegrito) {
    								celula.setCellStyle(estilo);
    							}
    							celula.setCellValue(campo);
    							celula.setCellStyle(listaCellStyle.get(cont2));
    						}
    					}
    				}

    			}
    			planilha.setForceFormulaRecalculation(true);

    			response.setContentType("application/vnd.ms-excel");
    			response.setHeader("Content-Disposition", "attachment; filename="+ nomeArquivoIntl + ".xls");

    			OutputStream os = response.getOutputStream();
    			wb.write(os);
    			os.flush();
    			os.close();
    		} catch (IOException ioe) {
    			ioe.printStackTrace();
    		}
    	}

    private void setCabecalho(List<SelectUsuarioExecuteFiltrosDTO> cabecalho) {
    	//Caso tenha filtros vai preparar o cabeçalho para inserir os filtros.
    	if(cabecalho != null){
    		this.titulo = titulo + "\n";
	    	for (SelectUsuarioExecuteFiltrosDTO beanSelectUsuarioCampos : cabecalho) {
	    		if(beanSelectUsuarioCampos.getConteudo().equals("%")){
	    			beanSelectUsuarioCampos.setConteudo("[Todos]");
	    		}
	    		this.titulo = titulo + beanSelectUsuarioCampos.getLabel().trim() + ": " + beanSelectUsuarioCampos.getConteudo() + " | ";				
			}
	   	}
    }
}
