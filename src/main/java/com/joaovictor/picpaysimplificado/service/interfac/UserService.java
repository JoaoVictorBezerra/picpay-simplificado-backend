package com.joaovictor.picpaysimplificado.service.interfac;

import com.joaovictor.picpaysimplificado.dto.user.CreateUserRequestDTO;
import com.joaovictor.picpaysimplificado.dto.user.UserDTO;
import com.joaovictor.picpaysimplificado.entity.User;

public interface UserService {
    boolean verifyIfExistsUserByDocumentOrEmail(String document, String email);
    User findUserById(Long id);
    void saveUser(User user);
    UserDTO createUser(CreateUserRequestDTO createUserRequestDTO);
}
