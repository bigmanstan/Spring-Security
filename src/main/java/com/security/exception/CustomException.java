package com.security.exception;

/**
 * The Class CustomException.
 *
 */

public class CustomException extends Exception {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -6614537780554254940L;

  /** The error codes. */
  private String errorMessage;

  /** The error code. */
  private String errorCode;

  /** The caused by. */
  private Exception causedBy;

  /**
   * Instantiates a new system exception.
   *
   * @param message the message
   * @param causedBy the caused by
   */
  public CustomException(final String message, final Exception causedBy) {
    super(message, causedBy);
  }

  /**
   * Gets the caused by.
   *
   * @return the caused by
   */
  public Exception getCausedBy() {
    return causedBy;
  }

  /**
   * Sets the caused by.
   *
   * @param causedBy the new caused by
   */
  public void setCausedBy(final Exception causedBy) {
    this.causedBy = causedBy;
  }

  /**
   * Gets the error message.
   *
   * @return the error message
   */
  public String getErrorMessage() {
    return errorMessage;
  }

  /**
   * Sets the error message.
   *
   * @param errorMessage the new error message
   */
  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  /**
   * Gets the error code.
   *
   * @return the error code
   */
  public String getErrorCode() {
    return errorCode;
  }

  /**
   * Sets the error code.
   *
   * @param errorCode the new error code
   */
  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

}
