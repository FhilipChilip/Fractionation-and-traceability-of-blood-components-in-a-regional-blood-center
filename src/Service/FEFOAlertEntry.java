package service;

import collection.CollectionRecord;

import java.time.LocalDate;

public class FefoAlertEntry {

    private final CollectionRecord record;
    private final LocalDate expirationDate;
    private final long daysRemaining;

    public FefoAlertEntry(CollectionRecord record, LocalDate expirationDate, long daysRemaining) {
        this.record = record;
        this.expirationDate = expirationDate;
        this.daysRemaining = daysRemaining;
    }

    public CollectionRecord getRecord() {
        return record;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public long getDaysRemaining() {
        return daysRemaining;
    }
}
