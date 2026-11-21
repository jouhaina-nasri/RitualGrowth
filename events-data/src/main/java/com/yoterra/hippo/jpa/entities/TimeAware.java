package com.yoterra.hippo.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Calendar;

public interface TimeAware {

	/**
	 * Creation time field/property should never be null and it should be assigned when creating the entity (table entry)
	 */
	public static interface CreationTimeAware{
		Calendar getCreationTime();
		void setCreationTime(Calendar time);
	}

	/**
	 * Modification time field/property is nullable and exists only if there was at least one modification after the creation of the entity 
	 */
	public static interface ModificationTimeAware{			
		Calendar getModificationTime();
		void setModificationTime(Calendar time);
	}
	
	/**
	 * Update time field/property is NOT nullable and it's set to the creation time 
	 * if there were no modifications after the creation, otherwise it's set to the modification time 
	 */
	public static interface UdpateTimeAware {
		Calendar getUpdateTime();

		void setUpdateTime(Calendar time);
	}
}
