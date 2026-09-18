backage app;

import java.util.ArrayList;
import java.time.LocalDate;

public class Main{
	
	public static void main(String[] args) {
        System.out.println("=== REGIONAL BLOOD BANK OF THE NORTHEAST ===");
        System.out.println();

        Schedule baseSchedule = new Schedule("08:00", "14:00");
        List<String> baseSupplies = new ArrayList<>(List.of("Collection bags", "Test tubes", "Alcohol swabs", "Adhesive bandages"));
        CampaignTemplate baseTemplate = new CampaignTemplate(
                "Standard University Campaign",
                "Main site",
                "Bucaramanga",
                100,
                "WHOLE_BLOOD",
                baseSchedule,
                baseSupplies);

        CampaignTemplate campaignOne = baseTemplate.clone();
        campaignOne.setSite("UIS - Bucaramanga");
        campaignOne.setMunicipality("Bucaramanga");
        campaignOne.setGoal(80);

        CampaignTemplate campaignTwo = baseTemplate.clone();
        campaignTwo.setSite("UFPS - Cucuta");
        campaignTwo.setMunicipality("Cucuta");
        campaignTwo.setGoal(120);
        campaignTwo.addSupply("Thermal tent");

        System.out.println("Base template: " + baseTemplate.getName() + " | supplies: " + baseTemplate.getSupplyCount());
        System.out.println("Cloned campaign 1: " + campaignOne.getSite() + " | goal: " + campaignOne.getGoal() + " | supplies: " + campaignOne.getSupplyCount());
        System.out.println("Cloned campaign 2: " + campaignTwo.getSite() + " | goal: " + campaignTwo.getGoal() + " | supplies: " + campaignTwo.getSupplyCount());
        System.out.println("Base template check -> supplies: " + baseTemplate.getSupplyCount() + " (NOT altered)");
        System.out.println();

        List<CollectionRecord> records = new ArrayList<>();

        records.add(new CollectionRecord.Builder()
                .consecutive(1)
                .donorDocument("D001")
                .siteCode("BUC")
                .collectionDate(LocalDate.of(2026, 3, 20))
                .modality(ModalityType.WHOLE_BLOOD)
                .actualVolumeMl(455)
                .punctureTimeMinutes(8)
                .phlebotomist("Ana Ruiz")
                .supplyLot("LOT-2026-01")
                .firstTimeDonor(true)
                .build());

        records.add(new CollectionRecord.Builder()
                .consecutive(2)
                .donorDocument("D002")
                .siteCode("CUC")
                .collectionDate(LocalDate.of(2026, 3, 25))
                .modality(ModalityType.WHOLE_BLOOD)
                .actualVolumeMl(390)
                .punctureTimeMinutes(9)
                .phlebotomist("Carlos Diaz")
                .supplyLot("LOT-2026-02")
                .build());

        records.add(new CollectionRecord.Builder()
                .consecutive(3)
                .donorDocument("D003")
                .siteCode("BUC")
                .collectionDate(LocalDate.of(2026, 4, 13))
                .modality(ModalityType.APHERESIS_PLATELETS)
                .actualVolumeMl(298)
                .punctureTimeMinutes(60)
                .phlebotomist("Ana Ruiz")
                .supplyLot("LOT-2026-03")
                .observations("Extended puncture time due to apheresis procedure")
                .build());

        records.add(new CollectionRecord.Builder()
                .consecutive(4)
                .donorDocument("D004")
                .siteCode("CUC")
                .collectionDate(LocalDate.of(2026, 4, 16))
                .modality(ModalityType.APHERESIS_PLATELETS)
                .actualVolumeMl(305)
                .punctureTimeMinutes(64)
                .phlebotomist("Carlos Diaz")
                .supplyLot("LOT-2026-04")
                .observations("Extended puncture time due to apheresis procedure")
                .addAdverseEvent("Mild bruising at puncture site")
                .responsiblePhlebotomist("Carlos Diaz")
                .build());

        records.add(new CollectionRecord.Builder()
                .consecutive(5)
                .donorDocument("D005")
                .siteCode("BUC")
                .collectionDate(LocalDate.of(2026, 2, 1))
                .modality(ModalityType.APHERESIS_PLASMA)
                .actualVolumeMl(612)
                .punctureTimeMinutes(45)
                .phlebotomist("Ana Ruiz")
                .supplyLot("LOT-2026-05")
                .observations("Extended puncture time due to apheresis procedure")
                .build());

        records.add(new CollectionRecord.Builder()
                .consecutive(6)
                .donorDocument("D006")
                .siteCode("CUC")
                .collectionDate(LocalDate.of(2026, 4, 10))
                .modality(ModalityType.APHERESIS_PLASMA)
                .actualVolumeMl(550)
                .punctureTimeMinutes(50)
                .phlebotomist("Carlos Diaz")
                .supplyLot("LOT-2026-06")
                .campaign("UFPS - Cucuta")
                .firstTimeDonor(true)
                .observations("Extended puncture time due to apheresis procedure")
                .build());

        InventoryService inventoryService = new InventoryService();

        System.out.println("--- REGISTERED UNITS ---");
        for (CollectionRecord record : records) {
            boolean fit = inventoryService.isFit(record);
            StringBuilder line = new StringBuilder();
            line.append(record.getUnitCode())
                    .append(" | ").append(record.getModality())
                    .append(" | ").append(record.getActualVolumeMl()).append(" mL")
                    .append(" | ");
            if (fit) {
                LocalDate expiration = inventoryService.calculateExpiration(record);
                line.append("FIT | expires ").append(expiration);
            } else {
                line.append("NOT FIT | ").append(inventoryService.getUnfitReason(record));
            }
            System.out.println(line);
        }
        System.out.println();

        try {
            new CollectionRecord.Builder()
                    .consecutive(7)
                    .donorDocument("D007")
                    .siteCode("BUC")
                    .collectionDate(LocalDate.of(2026, 4, 15))
                    .modality(ModalityType.WHOLE_BLOOD)
                    .actualVolumeMl(450)
                    .punctureTimeMinutes(10)
                    .phlebotomist("Ana Ruiz")
                    .build();
        } catch (IllegalStateException exception) {
            System.out.println("[CONTROLLED ERROR] Cannot build the record: " + exception.getMessage());
        }
        System.out.println();

        List<ModalitySummary> summaries = inventoryService.buildConsolidatedReport(records);
        System.out.println("--- CONSOLIDATED REPORT BY MODALITY ---");
        System.out.printf("%-22s %-5s %-7s %-14s %-12s %-12s%n",
                "Modality", "Fit", "NotFit", "UsefulVol(mL)", "Utilization", "AvgPuncture");
        for (ModalitySummary summary : summaries) {
            System.out.printf("%-22s %-5d %-7d %-14d %-11.1f%% %-9.1f min%n",
                    summary.getModality(),
                    summary.getFitCount(),
                    summary.getUnfitCount(),
                    summary.getUsefulVolumeMl(),
                    summary.getUtilizationPercentage(),
                    summary.getAveragePunctureTimeMinutes());
        }
        System.out.println();

        LocalDate cutoffDate = LocalDate.of(2026, 4, 20);
        List<FefoAlertEntry> fefoAlerts = inventoryService.getFefoAlert(records, cutoffDate);
        System.out.println("--- FEFO ALERT (cutoff " + cutoffDate + ", expiring in <= 7 days) ---");
        int index = 1;
        for (FefoAlertEntry entry : fefoAlerts) {
            String expiredTag = entry.getDaysRemaining() < 0 ? " (EXPIRED)" : "";
            System.out.println(index + ". " + entry.getRecord().getUnitCode()
                    + " | " + entry.getRecord().getModality()
                    + " | expires " + entry.getExpirationDate()
                    + " | " + entry.getDaysRemaining() + " days remaining" + expiredTag);
            index++;
        }
    }
}


	public static void main(int[] args){
		ArrayList<int> validity = new ArrayList<int>();
		validity.add(35 + " days");
		validity.add(5 + " days");
		validity.add(365 + " days");
		}
