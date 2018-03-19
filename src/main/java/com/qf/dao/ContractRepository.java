package com.qf.dao;

import com.qf.entity.ContractEntity;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Description: 保存信息到mysql
 * User: JianHuangsh
 * Date: 2018-03-14
 * Time: 19:42
 */
@Repository
public interface ContractRepository extends JpaRepository<ContractEntity, Long> {

  @Query("select  f  from ContractEntity f where f.downloadStatus=?1")
  public List<ContractEntity> findAllByDownloadStatus(String downloadStatus);

  @Query("select  f  from ContractEntity f where f.appId=?1")
  public ContractEntity findAllByAppId(String appId);

  @Modifying
  @Query("update  ContractEntity c  set c.filePath=?1 , c.updateTime=?3  where c.appId=?2")
  public void updateFilePath(String newFilePath, String appId,Date newDate);
}
