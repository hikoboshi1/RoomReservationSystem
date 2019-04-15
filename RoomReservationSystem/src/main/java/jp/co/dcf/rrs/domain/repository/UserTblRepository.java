package jp.co.dcf.rrs.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.dcf.rrs.domain.entity.UserTblEntity;

@Repository
public interface UserTblRepository extends JpaRepository<UserTblEntity, Integer> {
	public List<UserTblEntity> findByUsername(String username);
}
