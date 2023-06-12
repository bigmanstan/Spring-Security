package com.security.utils;

import java.io.InputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * The Class PropertyUtils.
 */
@Service
public final class PropertyUtils {

  /**
   * Instantiates a new property utils.
   */
  private PropertyUtils() {}

  /** The logger. */
  private static final Logger LOGGER = LogManager.getLogger(PropertyUtils.class.getName());

  /** The property. */
  private static Properties property = loadProperties("application.properties");

  /**
   * Load properties.
   *
   * @param fileName the file name
   * @return the properties
   */
  public static Properties loadProperties(final String fileName) {
    LOGGER.trace("Entering into loadProperties method");
    LOGGER.debug("Input Params : {} ", fileName);
    Properties prop = null;
    try {
      final InputStream in =
          Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
      prop = new Properties();
      prop.load(in);
    } catch (final Exception ex) {
      LOGGER.error("Exception", ex);
    }
    LOGGER.debug("Return Params : {}", prop);
    LOGGER.trace("Exiting from loadProperties");
    return prop;
  }

  /**
   * Gets the property.
   *
   * @param key the key
   * @return the property
   */
  public static String getProperty(final String key) {
    return property.getProperty(key);
  }

  /**
   * Return properties.
   *
   * @return the properties
   */
  public static Properties returnProperties() {
    return property;
  }
  

}
