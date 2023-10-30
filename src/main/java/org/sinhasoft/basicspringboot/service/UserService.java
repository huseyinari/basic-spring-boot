package org.sinhasoft.basicspringboot.service;

import org.sinhasoft.basicspringboot.dto.UserDTO;
import org.sinhasoft.basicspringboot.dto.UserSearchDTO;
import org.sinhasoft.basicspringboot.entity.User;
import org.sinhasoft.basicspringboot.exception.EntityNotFoundException;
import org.sinhasoft.basicspringboot.mapper.UserMapper;
import org.sinhasoft.basicspringboot.repository.UserRepository;
import org.sinhasoft.basicspringboot.repository.specification.SearchCriteria;
import org.sinhasoft.basicspringboot.repository.specification.UserSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.validation.Valid;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

  private final UserRepository repository;
  private final UserMapper mapper;
  private final MailService mailService;
  private final UserSpecifications userSpecifications;

//  @Autowired
//  public UserService(UserRepository userRepository, UserMapper userMapper, MailService mailService, UserSpecifications userSpecifications) {
//    this.repository = userRepository;
//    this.mapper = userMapper;
//    this.mailService = mailService;
//    this.userSpecifications = userSpecifications;
//  }

  public List<UserDTO> getAll() {
    List<User> userList = repository.findAll();
    return mapper.toDtoList(userList);
  }

  public UserDTO getOne(Long id) throws EntityNotFoundException{
    User user = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
    return mapper.toDto(user);
  }

  public UserDTO create(@Valid UserDTO userDTO) {
    if (userDTO.getId() != null)
      throw new RuntimeException("Yeni kaydedilecek kullanıcı id içeremez.");

    User user = repository.save(mapper.toEntity(userDTO));
    mailService.sendEMail(user.getName(), user.getSurname());

    return mapper.toDto(user);
  }

  public UserDTO update(UserDTO userDTO) {
    if (userDTO.getId() == null)
      throw new RuntimeException("Güncellenecek kullanıcının id'si bulunmalıdır.");

    UserDTO current = getOne(userDTO.getId());

    if (StringUtils.hasText(userDTO.getName()))
      current.setName(userDTO.getName());
    if (StringUtils.hasText(userDTO.getSurname()))
      current.setSurname(userDTO.getSurname());

    current.setEmail(userDTO.getEmail());
    current.setAge(userDTO.getAge());

    repository.save(mapper.toEntity(current));
    return current;
  }

  public void delete(Long id) {
    UserDTO userDTO = getOne(id);
    repository.deleteById(userDTO.getId());
  }

  public List<UserDTO> search(UserSearchDTO searchDTO) {
//    List<User> users =
//          repository.findAll(
//            userSpecifications.nameEquals(searchDTO.getName())
//            .and(userSpecifications.surnameLike(searchDTO.getSurname()))
//            .and(userSpecifications.emailEqualsIgnoreCase(searchDTO.getEmail()))
//            .and(userSpecifications.ageGreaterThan(searchDTO.getAge()))
//            , Sort.by(Sort.Direction.ASC, "name", "surname")
//          );

    Specification<User> nameSpec = userSpecifications.customSpesification(new SearchCriteria("name", "=", searchDTO.getName()));
    Specification<User> surnameSpec = userSpecifications.customSpesification(new SearchCriteria("surname", "=", searchDTO.getSurname()));
    Specification<User> emailSpec = userSpecifications.customSpesification(new SearchCriteria("email", "=", searchDTO.getEmail()));
    Specification<User> ageSpec = userSpecifications.customSpesification(new SearchCriteria("age", ">", searchDTO.getAge()));
    List<User> users =
            repository.findAll(
                    nameSpec.and(surnameSpec).and(emailSpec).and(ageSpec),
                    Sort.by(Sort.Direction.ASC, "name", "surname")
            );
    return mapper.toDtoList(users);
  }
}
