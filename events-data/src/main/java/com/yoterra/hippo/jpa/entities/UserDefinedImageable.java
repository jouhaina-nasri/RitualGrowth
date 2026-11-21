package com.yoterra.hippo.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Imageable where the associated image is defined by the user (usually uploaded image).
 * @examples
 * <li>{@link }
 * <li>{@link }
 * @author mihajlo
 *
 * @param <ID>
 */
public interface UserDefinedImageable<ID> extends Imageable {
	
	public enum ImageableType{
		USER("u".getBytes(Charset.defaultCharset())),				
		COLLECTION("c".getBytes(Charset.defaultCharset()));
		
		private ImageableType(byte[] tbytes) {
			this.tbytes = tbytes;
		}

		private final byte[] tbytes;
		
		public byte[] tbytes() {
			return tbytes;
		}
	}
	
	byte[] getIdBytes();
	ImageableType getImageableType();

}
