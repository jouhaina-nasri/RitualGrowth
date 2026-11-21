package com.yoterra.hippo.dto;

import com.yoterra.hippo.jpa.entities.Autocompletable;
import com.yoterra.utils.Opt;

public class AcDto {

	private final String id;
	private final String displayName;
	
	public static <ID> AcDto of(Autocompletable<ID> ac){
		return Opt.get(ac, AcDto::new);
	}

	public <ID> AcDto(Autocompletable<ID> ac){
		this(ac.getId(), ac.getDisplayName());
	}
	
	public <ID> AcDto(ID id, String displayName){
		this.id = Opt.get(id, Object::toString);
		this.displayName = displayName;
	}

	public String getId() {
		return id;
	}

	public String getDisplayName() {
		return displayName;
	}
}
