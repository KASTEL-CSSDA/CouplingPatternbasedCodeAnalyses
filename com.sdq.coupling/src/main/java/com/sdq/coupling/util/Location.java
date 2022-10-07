package com.sdq.coupling.util;

/**
 * Represents a location defined by a class, by a class and package 
 * or by a class, package and method. This class is used for representing
 * locations throughout a program and for matching based on locations
 * of architecture properties and code errors.
 *
 * @author Laura
 *
 */
public class Location {
  private String packageName = "";
  private String className = "";
  private String methodName = "";

  public Location(String className) {
    this.className = className;
  }

  /**
   * Constructs a location with the passed package name and class name.
   *
   * @param packageName The name of the package.
   * @param className The name of the class.
   */
  public Location(String packageName, String className) {
    this.packageName = (packageName != null) ? packageName : "";
    this.className = (className != null) ? className : "";
  }

  /**
   * Constructs a location with the passed package name, class name and 
   * method name.
   *
   * @param packageName The name of the package.
   * @param className The name of the class.
   * @param methodName The name of the method.
   */
  public Location(String packageName, String className, String methodName) {
    this.packageName = (packageName != null) ? packageName : "";
    this.className = (className != null) ? className : "";
    this.methodName = (methodName != null) ? methodName : "";
  }

  public String getPackageName() {
    return this.packageName;
  }

  public String getClassName() {
    return this.className;
  }

  public String getMethodName() {
    return this.methodName;
  }

  public boolean hasSameClass(Location location) {
    return location.className.equals(this.className);
  }

  public boolean hasSameClassAndPackage(Location location) {
    return location.getPackageName().equals(this.packageName) 
        && location.getClassName().equals(this.className);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    } else if (!(obj instanceof Location)) {
      return false;
    }

    Location t = (Location) obj;
    return t.getPackageName().equals(this.packageName) && t.getClassName().equals(this.className)
        && t.getMethodName().equals(this.methodName);
  }

  @Override
  public String toString() {
    if (this.getMethodName() == null) {
      return this.getPackageName() + "." + this.getClassName();
    }
    return this.getPackageName() + "." + this.getClassName() + "." + this.getMethodName() + "()";
  }
}
