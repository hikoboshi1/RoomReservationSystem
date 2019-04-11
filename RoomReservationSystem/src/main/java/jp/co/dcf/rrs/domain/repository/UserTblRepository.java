package jp.co.dcf.rrs.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.dcf.rrs.domain.entity.UserTblEntity;
import jp.co.dcf.rrs.domain.entity.id.UserTblEntityId;

@Repository
public interface UserTblRepository extends JpaRepository<UserTblEntity, UserTblEntityId> {
	public List<UserTblEntity> findByUsername(String username);
}
