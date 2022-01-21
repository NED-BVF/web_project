package com.jsc.fanCM.dto.board;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BoardSaveForm {
    private String name;
    private String detail;
}
