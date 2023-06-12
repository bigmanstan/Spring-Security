package com.security.response;

import java.io.Serializable;

/**
 * The Class Response.
 */
public class Response implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -8985845863504124146L;

  /** The code. */
  private String code;

  /** The message. */
  private String message;

  /** The data. */
  private Object data;

  /**
   * Gets the code.
   *
   * @return the code
   */
  public String getCode() {
    return code;
  }

  /**
   * Sets the code.
   *
   * @param code the new code
   */
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * Gets the message.
   *
   * @return the message
   */
  public String getMessage() {
    return message;
  }

  /**
   * Sets the message.
   *
   * @param message the new message
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * Gets the data.
   *
   * @return the data
   */
  public Object getData() {
    return data;
  }

  /**
   * Sets the data.
   *
   * @param data the new data
   */
  public void setData(Object data) {
    this.data = data;
  }
}
