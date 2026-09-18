package modality;

public interface Bag {
    String getDescription();
    String getAnticoagulant();
    int getNominalVolumeMl();
}

public interface ConservationProtocol {
    String getTemperatureRange();
    int getValidityDays();
}

public interface Label {
    String getPrefix();
}

public interface ModalityFactory {
    Bag createBag();
    Label createLabel();
    ConservationProtocol createConservationProtocol();
}

public enum ModalityType {

    WHOLE_BLOOD(new WholeBloodFactory()),
    APHERESIS_PLATELETS(new ApheresisPlateletsFactory()),
    APHERESIS_PLASMA(new ApheresisPlasmaFactory());

    private final ModalityFactory factory;

    ModalityType(ModalityFactory factory) {
        this.factory = factory;
    }

    public ModalityFactory getFactory() {
        return factory;
    }
}

public class WholeBloodBag implements Bag {

    public String getDescription() {
        return "Quadruple bag with filter";
    }

    public String getAnticoagulant() {
        return "CPD-A1";
    }

    public int getNominalVolumeMl() {
        return 450;
    }
}

public class WholeBloodConservationProtocol implements ConservationProtocol {

    public String getTemperatureRange() {
        return "2 to 6 C";
    }

    public int getValidityDays() {
        return 35;
    }
}

public class WholeBloodFactory implements ModalityFactory {

    public Bag createBag() {
        return new WholeBloodBag();
    }

    public Label createLabel() {
        return new WholeBloodLabel();
    }

    public ConservationProtocol createConservationProtocol() {
        return new WholeBloodConservationProtocol();
    }
}

public class WholeBloodLabel implements Label {

    public String getPrefix() {
        return "E00";
    }
}

public class ApheresisPlasmaBag implements Bag {

    public String getDescription() {
        return "Apheresis kit PLS";
    }

    public String getAnticoagulant() {
        return "ACD-A";
    }

    public int getNominalVolumeMl() {
        return 600;
    }
  
public class ApheresisPlasmaConservationProtocol implements ConservationProtocol {

    public String getTemperatureRange() {
        return "-25 C or lower";
    }

    public int getValidityDays() {
        return 365;
    }

public class ApheresisPlasmaFactory implements ModalityFactory {

    public Bag createBag() {
        return new ApheresisPlasmaBag();
    }

    public Label createLabel() {
        return new ApheresisPlasmaLabel();
    }

    public ConservationProtocol createConservationProtocol() {
        return new ApheresisPlasmaConservationProtocol();
    }
}

public class ApheresisPlasmaLabel implements Label {

    public String getPrefix() {
        return "E70";
    }
}

public class ApheresisPlateletsBag implements Bag {

    public String getDescription() {
        return "Apheresis kit PLT";
    }

    public String getAnticoagulant() {
        return "ACD-A";
    }

    public int getNominalVolumeMl() {
        return 300;
    }
}

public class ApheresisPlateletsConservationProtocol implements ConservationProtocol {

    public String getTemperatureRange() {
        return "20 to 24 C with agitation";
    }

    public int getValidityDays() {
        return 5;
    }
}

public class ApheresisPlateletsFactory implements ModalityFactory {

    public Bag createBag() {
        return new ApheresisPlateletsBag();
    }

    public Label createLabel() {
        return new ApheresisPlateletsLabel();
    }

    public ConservationProtocol createConservationProtocol() {
        return new ApheresisPlateletsConservationProtocol();
    }
}

public class ApheresisPlateletsLabel implements Label {

    public String getPrefix() {
        return "E30";
    }
}
