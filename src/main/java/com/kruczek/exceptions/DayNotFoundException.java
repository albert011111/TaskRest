package com.kruczek.exceptions;

public class DayNotFoundException extends RuntimeException {
	public DayNotFoundException(Long id) {
		super("Day with given id is not found - id:[" + id + "]");
	}
}
