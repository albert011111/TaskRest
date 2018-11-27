package com.kruczek.model.user;

import com.kruczek.model.role.Role;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")//@UniqueConstrain --> ograniczenie unikatowosci
        // w tabeli o danej nazwie
        //jezeli bede chcial wprowadzic 2x taki sam mail bedzie exception
})

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //IDENTITY oznacza, ze id bedzie przydzielone na podstawie kolumny identity z bazy daych
    private Long id;

    @NotBlank // walidacja nie przepusci pustego znaku -> [" "]
    @Size(min = 3, max = 20)
    private String name;

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NaturalId //domyslnie jest niemutowalne, NIE POWINNO sie tworzyc settera do takiego pola
    //hibernate dostarcza funkcjonalnosc, ze mozna sie odwolac do encji (do DB) przez jego NaturalId
    // --> @see byNaturalId
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String name, String username, String email, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    //TODO zweryfikowac tresc adnotacji @NaturalId
/*    public void setEmail(String email) {
        this.email = email;
    }*/

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
