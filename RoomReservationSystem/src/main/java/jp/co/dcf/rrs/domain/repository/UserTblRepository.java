package jp.co.dcf.rrs.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.dcf.rrs.domain.entity.UserTblEntity;
import jp.co.dcf.rrs.domain.entity.pk.UserTblEntityPK;

@Repository
public interface UserTblRepository extends JpaRepository<UserTblEntity, UserTblEntityPK> {
	public List<UserTblEntity> findByUsername(String username);
}
