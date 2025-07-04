package com.example.expensetracker;

import java.time.LocalDate;
import java.util.Objects;

public class Expense {
	private String category;
	private double amount;
	private LocalDate date;
	private String description;

	public Expense(String category, double amount, LocalDate date, String description) {
		this.category = category;
		this.amount = amount;
		this.date = date;
		this.description = description;
	}

	public String getCategory() { return category; }
	public double getAmount() { return amount; }
	public LocalDate getDate() { return date; }
	public String getDescription() { return description; }

	@Override
	public String toString() {
		return String.format("%s | %.2f | %s | %s", date, amount, category, description);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Expense)) return false;
		Expense that = (Expense) o;
		return Double.compare(that.amount, amount) == 0 &&
				Objects.equals(category, that.category) &&
				Objects.equals(date, that.date) &&
				Objects.equals(description, that.description);
	}

	@Override
	public int hashCode() {
		return Objects.hash(category, amount, date, description);
	}
}