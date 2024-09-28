package com.joaovictor.picpaysimplificado.mappers;

import com.joaovictor.picpaysimplificado.dto.user.CreateUserRequestDTO;
import com.joaovictor.picpaysimplificado.dto.user.UserDTO;
import com.joaovictor.picpaysimplificado.entity.User;
import com.joaovictor.picpaysimplificado.utils.api.StringUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {
    public static User toEntity(CreateUserRequestDTO dto) {
        return new User()
              .builder()
              .name(dto.name())
              .document(dto.document())
              .email(dto.email())
              .password(dto.password())
              .balance(BigDecimal.ZERO)
              .createdAt(Instant.now())
              .build();
    }

    public static UserDTO toUserDTO(User entity) {
        return new UserDTO(
              entity.getId(),
              entity.getName(),
              setMask(entity.getDocument()),
              entity.getEmail(),
              entity.getCreatedAt(),
              entity.getBalance()
        );
    }

    private static String setMask(String document) {
        if(document.length() == 11) return StringUtil.toCPFMask(document);
        return StringUtil.toCNPJMask(document);
    }
}
