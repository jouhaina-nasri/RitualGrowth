package com.yoterra.hippo.jpa.entities;

/**
 * Defines an entity whose 'data' is not in the database but it requires fetching the data from additional external source
 * 
 * @examples 
 * 	<li>Brands and stores data is spread across multiple different sources: the Hippo SQL database, the company service and Solr index(es)
 *  <li>Products and deals data is stored in the Hippo SQL database, Cassandra and Solr index
 * @author mihajlo
 */
public interface DataResolvable<ID> extends IdEntity<ID> {

	boolean isDataResolved();
}
