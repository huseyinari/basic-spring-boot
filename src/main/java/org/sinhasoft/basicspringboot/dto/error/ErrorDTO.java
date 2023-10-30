package org.sinhasoft.basicspringboot.dto.error;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ErrorDTO {
    private List<ConstraintError> errors;
    private String error;
    private LocalDateTime dateTime;
}
