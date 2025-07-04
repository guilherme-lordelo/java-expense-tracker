package com.example.expensetracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseManagerTest {

	private ExpenseManager manager;

	@BeforeEach
	public void setUp() {
		manager = new ExpenseManager();
	}

	@Test
	public void testAddAndRetrieve() {
		Expense e = new Expense("Food", 20.0, LocalDate.now(), "Pizza");
		manager.addExpense(e);
		List<Expense> all = manager.getAllExpenses();
		assertEquals(1, all.size());
		assertEquals(e, all.get(0));
	}

	@Test
	public void testDeleteValidIndex() {
		manager.addExpense(new Expense("Food", 5, LocalDate.now(), "Snack"));
		boolean deleted = manager.deleteExpense(0);
		assertTrue(deleted);
		assertTrue(manager.getAllExpenses().isEmpty());
	}

	@Test
	public void testDeleteInvalidIndex() {
		boolean deleted = manager.deleteExpense(5);
		assertFalse(deleted);
	}

	@Test
	public void testFilterByCategory() {
		manager.addExpense(new Expense("Food", 10, LocalDate.now(), "A"));
		manager.addExpense(new Expense("Transport", 15, LocalDate.now(), "B"));
		List<Expense> food = manager.filterByCategory("food");
		assertEquals(1, food.size());
		assertEquals("A", food.get(0).getDescription());
	}

	@Test
	public void testGetTotalByCategory() {
		manager.addExpense(new Expense("Bills", 50, LocalDate.now(), "Internet"));
		manager.addExpense(new Expense("Bills", 25, LocalDate.now(), "Electricity"));
		double total = manager.getTotalByCategory("bills");
		assertEquals(75, total, 0.01);
	}
}
