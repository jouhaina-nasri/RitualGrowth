package com.yoterra.hippo.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yoterra.hippo.jpa.entities.users.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "USER_COMPANY")
public class UserCompany extends ActiveEntity {

    @ManyToOne(optional = false) // ????
//	@OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="USER_ID")
    private User user;

    @ManyToOne(optional = false) // ????
//	@OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="COMPANY_ID")
    private Company company;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole userRole;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
