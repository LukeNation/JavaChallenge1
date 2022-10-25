package com.factorIT.demo.dto.response;

import com.factorIT.demo.model.User;
import lombok.Data;

@Data
public class UsuarioVista {

    private Long dni;
    private String name;
    private String lastName;
    private String email;
    private String address;
    private Boolean vip;

    public UsuarioVista(User u){
        setDni(u.getDni());
        setName(u.getName());
        setLastName(u.getLastName());
        setEmail(u.getEmail());
        setAddress(u.getAddress());
        setVip(u.getVip());
    }
}
