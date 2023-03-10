package com.example.democrud01.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColumnsToPrintExecuteDTO {

    private String codeEntity;
    private String qrpId;
    private String codePrint;
    private String descriptionCodePrint;
    private String itemDetails;
    private String descriptionType;
    private BigDecimal size;
    private BigDecimal order;
    private boolean selected;
    private String icAgroup;
    private String keyNumber;

    public String uniqueByTwo(){
        return codeEntity+keyNumber;
    }
}
