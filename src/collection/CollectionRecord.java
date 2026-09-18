package collection;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class CollectionRecord {

    private final int consecutive;
    private final String donorDocument;
    private final String siteCode;
    private final LocalDate collectionDate;
    private final ModalityType modality;
    private final int actualVolumeMl;
    private final int punctureTimeMinutes;
    private final String phlebotomist;
    private final String supplyLot;
    private final String campaign;
    private final String observations;
    private final List<String> adverseEvents;
    private final boolean firstTimeDonor;
    private final String responsiblePhlebotomist;
    private final Bag bag;
    private final Label label;
    private final ConservationProtocol conservationProtocol;
    private final String unitCode;

    private CollectionRecord(Builder builder) {
        this.consecutive = builder.consecutive;
        this.donorDocument = builder.donorDocument;
        this.siteCode = builder.siteCode;
        this.collectionDate = builder.collectionDate;
        this.modality = builder.modality;
        this.actualVolumeMl = builder.actualVolumeMl;
        this.punctureTimeMinutes = builder.punctureTimeMinutes;
        this.phlebotomist = builder.phlebotomist;
        this.supplyLot = builder.supplyLot;
        this.campaign = builder.campaign;
        this.observations = builder.observations;
        this.adverseEvents = Collections.unmodifiableList(new ArrayList<>(builder.adverseEvents));
        this.firstTimeDonor = builder.firstTimeDonor;
        this.responsiblePhlebotomist = builder.responsiblePhlebotomist;
        this.bag = modality.getFactory().createBag();
        this.label = modality.getFactory().createLabel();
        this.conservationProtocol = modality.getFactory().createConservationProtocol();
        this.unitCode = buildUnitCode();
    }

    private String buildUnitCode() {
        int yearTwoDigits = collectionDate.getYear() % 100;
        return String.format("%s-%s-%02d-%05d", label.getPrefix(), siteCode, yearTwoDigits, consecutive);
    }

    public int getConsecutive() {
        return consecutive;
    }

    public String getDonorDocument() {
        return donorDocument;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public LocalDate getCollectionDate() {
        return collectionDate;
    }

    public ModalityType getModality() {
        return modality;
    }

    public int getActualVolumeMl() {
        return actualVolumeMl;
    }

    public int getPunctureTimeMinutes() {
        return punctureTimeMinutes;
    }

    public String getPhlebotomist() {
        return phlebotomist;
    }

    public String getSupplyLot() {
        return supplyLot;
    }

    public String getCampaign() {
        return campaign;
    }

    public String getObservations() {
        return observations;
    }

    public List<String> getAdverseEvents() {
        return adverseEvents;
    }

    public boolean isFirstTimeDonor() {
        return firstTimeDonor;
    }

    public String getResponsiblePhlebotomist() {
        return responsiblePhlebotomist;
    }

    public Bag getBag() {
        return bag;
    }

    public Label getLabel() {
        return label;
    }

    public ConservationProtocol getConservationProtocol() {
        return conservationProtocol;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public static final class Builder {

        private int consecutive;
        private String donorDocument;
        private String siteCode;
        private LocalDate collectionDate;
        private ModalityType modality;
        private int actualVolumeMl;
        private int punctureTimeMinutes;
        private String phlebotomist;
        private String supplyLot;
        private String campaign;
        private String observations;
        private final List<String> adverseEvents = new ArrayList<>();
        private boolean firstTimeDonor;
        private String responsiblePhlebotomist;

        public Builder consecutive(int consecutive) {
            this.consecutive = consecutive;
            return this;
        }

        public Builder donorDocument(String donorDocument) {
            this.donorDocument = donorDocument;
            return this;
        }

        public Builder siteCode(String siteCode) {
            this.siteCode = siteCode;
            return this;
        }

        public Builder collectionDate(LocalDate collectionDate) {
            this.collectionDate = collectionDate;
            return this;
        }

        public Builder modality(ModalityType modality) {
            this.modality = modality;
            return this;
        }

        public Builder actualVolumeMl(int actualVolumeMl) {
            this.actualVolumeMl = actualVolumeMl;
            return this;
        }

        public Builder punctureTimeMinutes(int punctureTimeMinutes) {
            this.punctureTimeMinutes = punctureTimeMinutes;
            return this;
        }

        public Builder phlebotomist(String phlebotomist) {
            this.phlebotomist = phlebotomist;
            return this;
        }

        public Builder supplyLot(String supplyLot) {
            this.supplyLot = supplyLot;
            return this;
        }

        public Builder campaign(String campaign) {
            this.campaign = campaign;
            return this;
        }

        public Builder observations(String observations) {
            this.observations = observations;
            return this;
        }

        public Builder addAdverseEvent(String adverseEvent) {
            this.adverseEvents.add(adverseEvent);
            return this;
        }

        public Builder firstTimeDonor(boolean firstTimeDonor) {
            this.firstTimeDonor = firstTimeDonor;
            return this;
        }

        public Builder responsiblePhlebotomist(String responsiblePhlebotomist) {
            this.responsiblePhlebotomist = responsiblePhlebotomist;
            return this;
        }

        public CollectionRecord build() {
            validate();
            return new CollectionRecord(this);
        }

        private void validate() {
            if (consecutive <= 0) {
                throw new IllegalStateException("missing 'consecutive'");
            }
            if (donorDocument == null || donorDocument.isBlank()) {
                throw new IllegalStateException("missing 'donorDocument'");
            }
            if (siteCode == null || siteCode.isBlank()) {
                throw new IllegalStateException("missing 'siteCode'");
            }
            if (collectionDate == null) {
                throw new IllegalStateException("missing 'collectionDate'");
            }
            if (modality == null) {
                throw new IllegalStateException("missing 'modality'");
            }
            if (actualVolumeMl <= 0) {
                throw new IllegalStateException("missing 'actualVolumeMl'");
            }
            if (punctureTimeMinutes <= 0) {
                throw new IllegalStateException("missing 'punctureTimeMinutes'");
            }
            if (phlebotomist == null || phlebotomist.isBlank()) {
                throw new IllegalStateException("missing 'phlebotomist'");
            }
            if (supplyLot == null || supplyLot.isBlank()) {
                throw new IllegalStateException("missing 'supplyLot'");
            }
            if (punctureTimeMinutes > 15 && (observations == null || observations.isBlank())) {
                throw new IllegalStateException("puncture time exceeds 15 minutes without observations");
            }
            if (!adverseEvents.isEmpty() && (responsiblePhlebotomist == null || responsiblePhlebotomist.isBlank())) {
                throw new IllegalStateException("adverse events reported without a responsible phlebotomist");
            }
        }
    }
}
