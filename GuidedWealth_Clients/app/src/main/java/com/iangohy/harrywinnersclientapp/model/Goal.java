package com.iangohy.harrywinnersclientapp.model;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Goal {
    private double currAmount;
    private double amount;
    private Date deadline;
    private String description;

    public Goal() {}

    public Goal(double currAmount, double amount, Date deadline, String description) {
        this.currAmount = currAmount;
        this.amount = amount;
        this.deadline = deadline;
        this.description = description;
    }

    public double getCurrAmount() {
        return currAmount;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDeadline() {
        return deadline;
    }

    public String getDescription() {
        return description;
    }

    public String getDeadlineString() {
        Date now = new Date();

        long diffInMillies = Math.abs(now.getTime() - deadline.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        // If more than a year, use years instead
        if (diff > 365) {
            diff = (long) diff / 365;
            return diff + " years left";
        } else {
            return diff + " days left";
        }
    }

    @Override
    public String toString() {
        return "Goal{" +
                "amount=" + amount +
                ", deadline=" + deadline +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goal goal = (Goal) o;
        return Double.compare(goal.amount, amount) == 0 &&
                Double.compare(goal.currAmount, currAmount) == 0 &&
                Objects.equals(deadline, goal.deadline) &&
                Objects.equals(description, goal.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, deadline, description);
    }
}
