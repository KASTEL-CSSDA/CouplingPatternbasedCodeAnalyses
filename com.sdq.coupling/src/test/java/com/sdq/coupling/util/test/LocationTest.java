package com.sdq.coupling.util.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sdq.coupling.util.Location;

public class LocationTest {

	private Location location1 = new Location(null, "Test", null);
	private Location location2 = new Location("com.sdq.other", "Test", "operation");
	private Location location3 = new Location("", "Test", null);
	private Location location4 = new Location(null, "Test", "operation2");
	private Location location5 = new Location("com.sdq.example", "Test", "operation2");
	private Location location6 = new Location("com.sdq.example", "Test", "operation");

	@Test
	public void testConstructorPackageAndClasseName() {
		Location location = new Location("com.sdq.example", "Test");
		assertTrue(location.getClassName().equals("Test"));
		assertTrue(location.getPackageName().equals("com.sdq.example"));
		assertTrue(location.getMethodName() == "");
	}
	
	@Test
	public void testConstructorPackageAndClasseAndMethodName() {
		Location location = new Location("com.sdq.example", "Test", "operation");
		assertTrue(location.getClassName().equals("Test"));
		assertTrue(location.getPackageName().equals("com.sdq.example"));
		assertTrue(location.getMethodName() == "operation");
	}
	
	@Test
	public void testHasSameClass() {
		Location location = new Location("com.sdq.example", "Test", "operation");
		Location location1 = new Location(null, "Test", null);
		Location location2 = new Location("com.sdq.other", "Test", "operation");
		Location location3 = new Location("", "Test", null);
		Location location4 = new Location(null, "Test", "operation2");
		Location location5 = new Location("com.sdq.example", "Test", "operation2");
		assertTrue(location.hasSameClass(location1));
		assertTrue(location.hasSameClass(location2));
		assertTrue(location.hasSameClass(location3));
		assertTrue(location.hasSameClass(location4));
		assertTrue(location.hasSameClass(location5));
		assertTrue(location.hasSameClass(location6));
	}
	
	@Test
	public void testHasSameClassAndPackage() {
		Location location = new Location("com.sdq.example", "Test", "operation");
		assertFalse(location.hasSameClassAndPackage(location1));
		assertFalse(location.hasSameClassAndPackage(location2));
		assertFalse(location.hasSameClassAndPackage(location3));
		assertFalse(location.hasSameClassAndPackage(location4));
		assertTrue(location.hasSameClassAndPackage(location5));
		assertTrue(location.hasSameClassAndPackage(location6));
	}
	
	@Test
	public void testEquals() {
		Location location = new Location("com.sdq.example", "Test", "operation");
		assertFalse(location.equals(location1));
		assertFalse(location.equals(location2));
		assertFalse(location.equals(location3));
		assertFalse(location.equals(location4));
		assertFalse(location.equals(location5));
		assertTrue(location.equals(location6));
	}

}
