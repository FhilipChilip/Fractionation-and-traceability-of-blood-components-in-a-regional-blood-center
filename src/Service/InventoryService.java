package service;

import collection.CollectionRecord;
import modality.ModalityType;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InventoryService {

    public LocalDate calculateExpiration(CollectionRecord record) {
        return record.getCollectionDate().plusDays(record.getConservationProtocol().getValidityDays());
    }

    public boolean isFit(CollectionRecord record) {
        int nominalVolume = record.getBag().getNominalVolumeMl();
        double lowerBound = nominalVolume * 0.9;
        double upperBound = nominalVolume * 1.1;
        return record.getActualVolumeMl() >= lowerBound && record.getActualVolumeMl() <= upperBound;
    }

    public String getUnfitReason(CollectionRecord record) {
        if (isFit(record)) {
            return null;
        }
        return "VOLUME_OUT_OF_RANGE";
    }

    public List<ModalitySummary> buildConsolidatedReport(List<CollectionRecord> records) {
        List<ModalitySummary> summaries = new ArrayList<>();
        for (ModalityType modality : ModalityType.values()) {
            List<CollectionRecord> group = new ArrayList<>();
            for (CollectionRecord record : records) {
                if (record.getModality() == modality) {
                    group.add(record);
                }
            }
            if (group.isEmpty()) {
                continue;
            }
            int fitCount = 0;
            int unfitCount = 0;
            int usefulVolume = 0;
            int punctureSum = 0;
            for (CollectionRecord record : group) {
                if (isFit(record)) {
                    fitCount++;
                    usefulVolume += record.getActualVolumeMl();
                } else {
                    unfitCount++;
                }
                punctureSum += record.getPunctureTimeMinutes();
            }
            double utilization = (fitCount * 100.0) / group.size();
            double averagePuncture = ((double) punctureSum) / group.size();
            summaries.add(new ModalitySummary(modality, fitCount, unfitCount, usefulVolume, utilization, averagePuncture));
        }
        return summaries;
    }

    public List<FefoAlertEntry> getFefoAlert(List<CollectionRecord> records, LocalDate cutoffDate) {
        List<FefoAlertEntry> alerts = new ArrayList<>();
        for (CollectionRecord record : records) {
            if (!isFit(record)) {
                continue;
            }
            LocalDate expiration = calculateExpiration(record);
            long daysRemaining = ChronoUnit.DAYS.between(cutoffDate, expiration);
            if (daysRemaining <= 7) {
                alerts.add(new FefoAlertEntry(record, expiration, daysRemaining));
            }
        }
        alerts.sort(Comparator.comparing(FefoAlertEntry::getExpirationDate)
                .thenComparingInt(entry -> entry.getRecord().getConsecutive()));
        return alerts;
    }
}
