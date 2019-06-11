package jp.co.dcf.rrs.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.dcf.rrs.model.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {
	public List<User> findByUsername(String username);
}
