package com.example.expensetracker;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ExpenseTest {

	@Test
	public void testExpenseCreation() {
		Expense e = new Expense("Food", 10.5, LocalDate.of(2024, 1, 1), "Lunch");
		assertEquals("Food", e.getCategory());
		assertEquals(10.5, e.getAmount());
		assertEquals(LocalDate.of(2024, 1, 1), e.getDate());
		assertEquals("Lunch", e.getDescription());
	}

	@Test
	public void testExpenseToString() {
		Expense e = new Expense("Transport", 7.25, LocalDate.of(2024, 5, 15), "Bus ticket");
		String expected = "2024-05-15 | 7.25 | Transport | Bus ticket";
		assertEquals(expected, e.toString());
	}
}
