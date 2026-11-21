package com.yoterra.hippo.services.auth;

import org.springframework.stereotype.Service;

import com.yoterra.hippo.jpa.entities.Owned;
import com.yoterra.hippo.jpa.entities.users.User;
import com.yoterra.hippo.security.ActiveUser;
import com.yoterra.utils.Opt;

@Service
public class AuthorizationService {

	public AuthPredicate owned(Owned owned) {
		return t->this.isOwned(owned);
	}
	
	public AuthPredicate anyOwned(Owned ... owned) {
		return t->this.isAnyOwned(owned);
	}
	
	public AuthPredicate allOwned(Owned ... owned) {
		return t->this.isAllOwned(owned);
	}
	
	public AuthPredicate superuser() {
		return t->this.isSuperUser();
	}
	
	public AuthPredicate ownedOrSuperuser(Owned owned) {
		return owned(owned).or(superuser());
	}
	
	public AuthPredicate notBlocked(Owned owned) {
		return t->true; // TODO implement blocking
	}
	
	
	public boolean isOwned(Owned owned) {
		Long auid = ActiveUser.getId();
		Long owid = owned.getOwner().getId();
		return auid.equals(owid);
	}
	
	public boolean isAnyOwned(Owned ... owned) {
		return Opt.strm(owned).anyMatch(this::isOwned);
	}
	
	public boolean isAllOwned(Owned ... owned) {
		return Opt.strm(owned).allMatch(this::isOwned);
	}
	
	public boolean isSuperUser() {
		User user = ActiveUser.get();
		return user.isSuperUser();
	}
}
