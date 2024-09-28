package com.joaovictor.picpaysimplificado.dto;

import java.util.List;

public record RestResponseDTO(Object data, List<String> errors, String message) {
}
