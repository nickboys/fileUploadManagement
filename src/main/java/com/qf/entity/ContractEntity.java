package com.qf.entity;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

/**
 * param:
 * describe: ContractEntity
 * author: JianHuangsh
 * creat_date: 2018/3/15
 **/

@Entity
@Table(name = "fileuplod", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"appId", "contractNo"})})
@Getter
@Setter
public class ContractEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column(name = "contractNo")
  private String contractNo;
  @Column(name = "appId")
  private String appId;

  @Lob
  @Basic(fetch = FetchType.LAZY)
  @Column(name = " filePath", columnDefinition = "BLOB", length = 2000)
  private String filePath;

  @Column(name = "fileType")
  private String fileType;

  /**
   * 合作公司
   */
  @Column(name = "cooperateCompany")
  private String cooperateCompany;
  /**
   * 协议下载结果状态
   */
  @Column(name = "downloadStatus")
  private String downloadStatus;
  /**
   * 协议上传结果状态
   */
  @Column(name = "uploadStatus")
  private String uploadStatus;
  /**
   * 创建时间
   */
  @Column(name = "createTime")
  private Date createTime;

  /**
   * 更新时间
   */
  @Column(name = "updateTime")
  private Date updateTime;


}
