package com.yoterra.hippo.jpa.entities.users;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.yoterra.hippo.jpa.entities.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import com.google.common.collect.Lists;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import com.yoterra.hippo.jpa.entities.Autocompletable;
import com.yoterra.hippo.jpa.entities.Privilege;
import com.yoterra.hippo.jpa.entities.TimeAware.CreationTimeAware;
import com.yoterra.hippo.jpa.entities.iasc.ActiveEntityMCMapUCAware;
import com.yoterra.hippo.jpa.entities.iasc.mcnts.MetacountsAware;
import com.yoterra.hippo.jpa.entities.iasc.usrctx.UserCtxAware;
import com.yoterra.hippo.security.Roles;
import com.yoterra.utils.Opt;

/**
 * User entity 
 * @author mihajlo
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class User extends ActiveEntityMCMapUCAware implements  Autocompletable<Long>, CreationTimeAware {

	public static final int FULL_NAME_MIN_LEN = 1, FULL_NAME_MAX_LEN = 64;
	public static final int USERNAME_MIN_LEN = 1, USERNAME_MAX_LEN = 64;
	
	@Column(name = "ap_uid",nullable = false, unique = true)
	private String apUid;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String fullName;
    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Calendar creationTime;
    
    @ElementCollection
    @CollectionTable(name="user_statuses",joinColumns =@JoinColumn(name = "user_id"))
	private Set<UserStatus> statuses;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_privileges",
		joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), 
		inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id")
	)
	private Set<Privilege> privileges;
    
    @Column
    private String reactivationToken;

	@Column
	private Boolean disabled;

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isStatus(UserStatus status) {
		return statuses != null && statuses.contains(status);
	}

	public boolean isSuperUser() {
		return privileges.stream().anyMatch(p->Roles.ADMIN.equals(p.getName()));
	}

	@Override
	public String getDisplayName() {
		List<String> l = Lists.newArrayList(getFullName());
		l = Opt.strm(l).filter(StringUtils::isNotBlank).toList();
		return StringUtils.join(l, " ");
	}

	@Override
	public Class<? extends MetacountsAware> getMetacountsClass() {
		return User.class;
	}
	
	@Override
	public Class<? extends UserCtxAware> getUCAwareClass() {
		return User.class;
	}
}