package campaign;

import java.util.ArrayList;
import java.util.List;

public class CampaignTemplate implements Cloneable {

    private String name;
    private String site;
    private String municipality;
    private int goal;
    private String defaultModality;
    private Schedule schedule;
    private final List<String> supplies;

    public CampaignTemplate(String name, String site, String municipality, int goal,
                             String defaultModality, Schedule schedule, List<String> supplies) {
        this.name = name;
        this.site = site;
        this.municipality = municipality;
        this.goal = goal;
        this.defaultModality = defaultModality;
        this.schedule = schedule;
        this.supplies = supplies;
    }

    @Override
    public CampaignTemplate clone() {
        List<String> copiedSupplies = new ArrayList<>(this.supplies);
        Schedule copiedSchedule = new Schedule(this.schedule);
        return new CampaignTemplate(this.name, this.site, this.municipality, this.goal,
                this.defaultModality, copiedSchedule, copiedSupplies);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public String getDefaultModality() {
        return defaultModality;
    }

    public void setDefaultModality(String defaultModality) {
        this.defaultModality = defaultModality;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public List<String> getSupplies() {
        return supplies;
    }

    public void addSupply(String supply) {
        this.supplies.add(supply);
    }

    public int getSupplyCount() {
        return supplies.size();
    }
}
