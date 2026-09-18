package service;

import modality.ModalityType;

public class ModalitySummary {

    private final ModalityType modality;
    private final int fitCount;
    private final int unfitCount;
    private final int usefulVolumeMl;
    private final double utilizationPercentage;
    private final double averagePunctureTimeMinutes;

    public ModalitySummary(ModalityType modality, int fitCount, int unfitCount, int usefulVolumeMl,
                            double utilizationPercentage, double averagePunctureTimeMinutes) {
        this.modality = modality;
        this.fitCount = fitCount;
        this.unfitCount = unfitCount;
        this.usefulVolumeMl = usefulVolumeMl;
        this.utilizationPercentage = utilizationPercentage;
        this.averagePunctureTimeMinutes = averagePunctureTimeMinutes;
    }

    public ModalityType getModality() {
        return modality;
    }

    public int getFitCount() {
        return fitCount;
    }

    public int getUnfitCount() {
        return unfitCount;
    }

    public int getUsefulVolumeMl() {
        return usefulVolumeMl;
    }

    public double getUtilizationPercentage() {
        return utilizationPercentage;
    }

    public double getAveragePunctureTimeMinutes() {
        return averagePunctureTimeMinutes;
    }
}
