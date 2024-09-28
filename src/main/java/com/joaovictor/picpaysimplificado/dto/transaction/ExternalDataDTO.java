package com.joaovictor.picpaysimplificado.dto.transaction;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternalDataDTO {
    private boolean authorization;
}
