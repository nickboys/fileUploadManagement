package com.qf.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * Description:s
 * User: JianHuangsh
 * Date: 2018-03-14
 * Time: 18:02
 */
@Getter
@Setter
public class Contract {

  private String contractNo;
  private String appId;
  private String status;
  private String applyTime;
  public String createTime;
  public String notifyContent;

}
