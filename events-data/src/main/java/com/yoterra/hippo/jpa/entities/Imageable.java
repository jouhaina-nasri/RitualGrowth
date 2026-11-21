package com.yoterra.hippo.jpa.entities;

import java.util.Collection;

import com.google.common.collect.Lists;

/**
 * Anything that has an image associated with it 
 * @exmaples
 * <li> product's main image 
 * <li> brand logo 
 * <li> store logo 
 * <li> user avatar 
 * <li> ...
 * @author mihajlo
 */
public interface Imageable {
	String getImageUrl();
	
	default Collection<String> getImageUrls(){
		return Lists.newArrayList(getImageUrl());
	}
}
