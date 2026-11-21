package com.yoterra.hippo.jpa.entities;

import com.yoterra.hippo.jpa.entities.iasc.ActiveEntityMCMapUCAware;
import com.yoterra.hippo.jpa.entities.iasc.mcnts.MetacountsAware;
import com.yoterra.hippo.jpa.entities.iasc.usrctx.UserCtxAware;
import com.yoterra.hippo.jpa.entities.users.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Calendar;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "COMPANY")
public class Company extends ActiveEntityMCMapUCAware implements Owned,TimeAware.CreationTimeAware, TimeAware.UdpateTimeAware {

    public static final int NAME_MIN_LEN = 1, NAME_MAX_LEN = 64;
    public static final int WEBSITE_MIN_LEN = 1, WEBSITE_MAX_LEN = 64;
    @ManyToOne
//	@OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="OWNER_ID")
    private User owner;

    @Column(name = "name",nullable = false)
    private String name = null;


    @Column(name = "website",nullable = false)
    private String website = null;


    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    @Column(nullable = false)
    private Calendar creationTime;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    @Column(nullable = false)
    private Calendar updateTime;			// =creationTime if not modifications have happened


    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public User getOwner() {
        return this.owner;
    }

    @Override
    public Calendar getCreationTime() {
        return this.creationTime;
    }

    @Override
    public void setCreationTime(Calendar time) {
            this.creationTime = time;
    }

    @Override
    public Calendar getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public void setUpdateTime(Calendar time) {
        this.updateTime = time;
    }

    @Override
    public Class<? extends MetacountsAware> getMetacountsClass() {
        return Company.class;
    }

    @Override
    public Class<? extends UserCtxAware> getUCAwareClass() {
        return Company.class;
    }
}
