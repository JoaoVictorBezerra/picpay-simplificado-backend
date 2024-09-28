package com.joaovictor.picpaysimplificado.dto.user;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
public record UserDTO(
      long id,
      String name,
      String document,
      String email,
      Instant createdAt,
      BigDecimal balance) {
}
