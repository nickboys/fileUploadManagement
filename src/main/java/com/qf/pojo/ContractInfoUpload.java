package com.qf.pojo;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * Description:
 * User: JianHuangsh
 * Date: 2018-03-15
 * Time: 14:50
 */
@Getter
@Setter
public class ContractInfoUpload {

  private Long id;
  private String appId;
  private String contractNo;
  private String filePath;
  private String fileType;
  private String cooperateCompany;
  private String downloadStatus;
  private String uploadStatus;
  private Date createTime;
  private Date updateTime;
}
