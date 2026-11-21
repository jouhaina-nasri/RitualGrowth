package com.yoterra.hippo.jpa.entities;

/**
 * Represents entities that define a relation between a user and some other item (entity). That other entity is named 'parent'. 
 * Classes implementing this interface usually implement Owned interface as well.
 * @examples
 * <li> Like - parent is the likeable item. For example, for LikeBrand it's brand, for LikeCollection it's collection, ...
 * <li> Comment - parent is the commentable item. For example, for CommentDeal it's deal, for CommentCollectionProduc it's collection product, ...
 * <li> UserFavorite - parent is the favoritable item. For example, for UserFavoriteStre it's store, for UserFavoriteCategory it's taxonomy element, ...
 * <li> ...
 * @author mihajlo
 *
 * @param <T>
 */
public interface ParentItemAware<T> {

	T getParent();
	void setParent(T parent);
}
