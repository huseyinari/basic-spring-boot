package org.sinhasoft.basicspringboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sinhasoft.basicspringboot.dto.UserDTO;
import org.sinhasoft.basicspringboot.exception.EntityNotFoundException;
import org.sinhasoft.basicspringboot.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ConstraintViolationException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK) // WebEnvironment default MOCK, belirtmesek de olur
@AutoConfigureMockMvc
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private UserService service;

  private final String apiUrl = "/api/user";

  @Test
  public void whenCallGetAll_thenReturnAllUsers() throws Exception {
    List<UserDTO> userDTOList = new ArrayList<>();
    userDTOList.add(new UserDTO(1L, "Hüseyin", "Arı", "hsynari1060@gmail.com", 23));
    userDTOList.add(new UserDTO(2L, "Mustafa", "Arı", "mustafa@gmail.com", 18));
    userDTOList.add(new UserDTO(3L, "Mehmet Akif", "Pişkiner", "mehmetakif@gmail.com", 30));

    Mockito.when(service.getAll()).thenReturn(userDTOList);

    this.mockMvc.perform(get(apiUrl))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(userDTOList.size()))
            .andExpect(jsonPath("$[0].name").value(userDTOList.get(0).getName()))
            .andExpect(jsonPath("$[0].surname").value(userDTOList.get(0).getSurname()));
  }
  @Test
  public void whenCallGetOneWithExistUserId_thenReturnUser() throws Exception {
    UserDTO userDTO = new UserDTO(1L, "Hüseyin", "Arı", "hsynari1060@gmail.com", 23);

    Mockito.when(service.getOne(1L)).thenReturn(userDTO);

    this.mockMvc.perform(get(apiUrl + "/1"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value(userDTO.getName()));
  }
  @Test
  public void whenCallGetOneWithNotExistUserId_thenReturnNotFound() throws Exception {
    Mockito.when(service.getOne(6L)).thenThrow(new EntityNotFoundException(6L));

    this.mockMvc.perform(get(apiUrl + "/6"))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(content().string("6 id'li kayıt bulunamadı !"));
  }
  @Test
  public void whenCallCreateWithConstraint_thenReturnBadRequest() throws Exception {
    Mockito.when(service.create(Mockito.any(UserDTO.class))).thenThrow(ConstraintViolationException.class);

    this.mockMvc.perform(post(apiUrl))
            .andDo(print())
            .andExpect(status().isBadRequest());
  }
  @Test
  public void whenCallCreateWithValidUser_thenReturnUser() throws Exception {
    UserDTO userDTO = new UserDTO(1L, "Hüseyin", "Arı", "hsynari1060@gmail.com", 23);

    Mockito.when(service.create(Mockito.any(UserDTO.class))).thenReturn(userDTO);

    this.mockMvc.perform(
              post(apiUrl)
              .contentType(MediaType.APPLICATION_JSON)
              .content(new ObjectMapper().writeValueAsBytes(userDTO))
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value(userDTO.getName()))
            .andExpect(jsonPath("$.surname").value(userDTO.getSurname()))
            .andExpect(jsonPath("$.email").value(userDTO.getEmail()))
            .andExpect(jsonPath("$.age").value(userDTO.getAge()));
  }
  @Test
  public void whenCallUpdateWithValidUser_thenReturnUser() throws Exception {
    UserDTO userDTO = new UserDTO(1L, "Test", "Test", "test@gmail.com", 30);

    Mockito.when(service.update(Mockito.any(UserDTO.class))).thenReturn(userDTO);

    this.mockMvc.perform(
                put(apiUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(new ObjectMapper().writeValueAsBytes(userDTO))
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value(userDTO.getName()))
            .andExpect(jsonPath("$.surname").value(userDTO.getSurname()))
            .andExpect(jsonPath("$.email").value(userDTO.getEmail()))
            .andExpect(jsonPath("$.age").value(userDTO.getAge()));
  }
  @Test
  public void whenCallDelete_thenReturnOk() throws Exception {
    mockMvc.perform(delete(apiUrl + "/1"))
            .andDo(print())
            .andExpect(status().isOk());
  }

}
