package com.yoterra.hippo.jpa.entities;

import com.yoterra.hippo.jpa.entities.users.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Calendar;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "REQUEST_ACCESS")
public class RequestAccess extends ActiveEntity implements TimeAware.CreationTimeAware {

    @ManyToOne(optional = false) // ????
//	@OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="ADMIN_ID")
    private User admin;

    @ManyToOne(optional = false) // ????
//	@OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="USER_ID")
    private User user;

    @Column
    @Enumerated(EnumType.STRING)
    private UserRole userRole;


    @Column(name = "company_website")
    private String companyWebsite;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    RequestAccessStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Calendar creationTime;

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RequestAccessStatus getStatus() {
        return status;
    }

    public void setStatus(RequestAccessStatus status) {
        this.status = status;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    @Override
    public Calendar getCreationTime() {
        return creationTime;
    }

    @Override
    public void setCreationTime(Calendar time) {
        this.creationTime = time;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
