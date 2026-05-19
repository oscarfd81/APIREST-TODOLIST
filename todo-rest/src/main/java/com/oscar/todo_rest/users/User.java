package com.oscar.todo_rest.users;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;
import java.util.Collection;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_entity")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String email;
    private String password;

    @Builder.Default
    private boolean isAdmin = false;
    
    @Builder.Default
    private boolean isGestor = false;

    @Builder.Default
    private boolean isUser = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = "ROLE_" + ((isAdmin) ? "ADMIN" : (isGestor)?"GESTOR":"USER");
        return List.of(new SimpleGrantedAuthority(role));
    }
}