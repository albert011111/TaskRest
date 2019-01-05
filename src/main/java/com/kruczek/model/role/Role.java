package com.kruczek.model.role;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(name = "ROLES")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 50)
    private RoleName roleName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    //todo sprawdzic czy bez settera Ok, wg @NaturalId nie powinno tworzyc sie setterow do takich pol
    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }
}
