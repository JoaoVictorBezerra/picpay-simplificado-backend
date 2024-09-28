package com.joaovictor.picpaysimplificado.service.impl;

import com.joaovictor.picpaysimplificado.constants.Constants;
import com.joaovictor.picpaysimplificado.dto.user.CreateUserRequestDTO;
import com.joaovictor.picpaysimplificado.dto.user.UserDTO;
import com.joaovictor.picpaysimplificado.entity.User;
import com.joaovictor.picpaysimplificado.exceptions.user.UserAlreadyExistsException;
import com.joaovictor.picpaysimplificado.mappers.UserMapper;
import com.joaovictor.picpaysimplificado.repository.UserRepository;
import com.joaovictor.picpaysimplificado.service.interfac.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public boolean verifyIfExistsUserByDocumentOrEmail(String document, String email) {
        return userRepository.findUserByEmailOrDocument(email, document).isPresent();
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
              () -> new RuntimeException("Usuário não encontrado")
        );
    }

    @Override
    public void saveUser(User user) {
        log.info("METHOD:: Start to save user {}", user);
        userRepository.save(user);
    }

    @Override
    public UserDTO createUser(CreateUserRequestDTO createUserRequestDTO) {
        log.info("METHOD:: Start to create user with document {} and e-mail {}", createUserRequestDTO.document(), createUserRequestDTO.email());
        var userAlreadyExists = verifyIfExistsUserByDocumentOrEmail(createUserRequestDTO.document(), createUserRequestDTO.email());
        if(userAlreadyExists){
            log.error("METHOD:: Aborting operation to {} - {}", createUserRequestDTO.document(), createUserRequestDTO.email());
            throw new UserAlreadyExistsException(Constants.USER_ALREADY_EXISTS_DETAIL, Constants.USER_ALREADY_EXISTS_TITLE);
        }
        var user = userRepository.save(UserMapper.toEntity(createUserRequestDTO));
        return UserMapper.toUserDTO(user);
    }
}
