package com.yoterra.hippo.jpa.repositories;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yoterra.hippo.jpa.entities.users.User;
import com.yoterra.utils.CollectionUtils;
import com.yoterra.utils.Opt;


@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

	long countByApUid(String apUid);

	long countByEmail(String email);

	boolean existsByEmail(String email);

	User findByApUid(String apUid);

	User findByEmailAndDisabled(String email,Boolean disabled);

	User findByEmail(String email);

	@Query(value = "SELECT u FROM User u WHERE u.id IN (:ids)")
	List<User> getUserByIds(@Param("ids") Collection<Long> ids);

	public default Map<Long, User> getUserByIdsMap(Collection<Long> ids) {
		List<User> users = getUserByIds(ids);
		return Opt.strm(users).toMapByKey(User::getId);
	}


	@Query(value = "SELECT COUNT(*) FROM users WHERE ap_uid = ?1", nativeQuery = true)
	long includeDeactivated_countByApUid(String apUid);

	@Query(value = "SELECT COUNT(*) FROM users WHERE email = ?1", nativeQuery = true)
	long includeDeactivated_countByEmail(String email);

	@Query(value = "SELECT id, reactivation_token FROM users WHERE email = ?1", nativeQuery = true)
	List<Object[]> includeDeactivated_idAndReactivationTokenByEmail(String email);

	default Pair<Long, String> includeDeactivated_idAndReactivationTokenPairByEmail(String email) {
		Object[] p = Opt.get(email, this::includeDeactivated_idAndReactivationTokenByEmail, CollectionUtils::first);
		if (p == null)
			return null;

		long id = ((Number) p[0]).longValue();
		String rt = p[1].toString();
		Pair<Long, String> pair = Pair.of(id, rt);
		return pair;
	}

	@Query(value = "SELECT COUNT(*) FROM users WHERE username = ?1", nativeQuery = true)
	long includeDeactivated_countByUsername(String username);

	@Query(value = "SELECT id, email, username, image_url FROM users WHERE email IN ( ?1 )", nativeQuery = true)
	List<Object[]> includeDeactivated_getStatusesByEmails(Collection<String> emails);

	// sort users bu the number of collection's followers

}
