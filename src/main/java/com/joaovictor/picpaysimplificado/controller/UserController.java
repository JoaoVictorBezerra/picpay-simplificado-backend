package com.joaovictor.picpaysimplificado.controller;

import com.joaovictor.picpaysimplificado.dto.RestResponseDTO;
import com.joaovictor.picpaysimplificado.dto.user.CreateUserRequestDTO;
import com.joaovictor.picpaysimplificado.service.interfac.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<RestResponseDTO> createUser(@RequestBody CreateUserRequestDTO createUserRequestDTO) {
        var createdUser = userService.createUser(createUserRequestDTO);
        var response = new RestResponseDTO(createdUser, null, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
