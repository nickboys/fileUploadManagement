package com.qf.common;

import com.qf.contstant.ResponseCode;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * Description:
 * User: JianHuangsh
 * Date: 2018-03-14
 * Time: 18:07
 */
@Getter
@Setter
public class Response<T> implements Serializable {

  private int code;
  private String msg;
  private T data;

  public static final Response defaultResponse = new Response(ResponseCode.SUCCESS_CODE, "aaa");

  public Response(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }
}
