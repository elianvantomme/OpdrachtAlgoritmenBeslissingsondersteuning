package input;

import park.TargetYard;
import park.Yard;

public class Instance {
    private Yard initialYard;
    private TargetYard desiredYard;

    public Instance(Yard initialYard, TargetYard desiredYard) {
        this.initialYard = initialYard;
        this.desiredYard = desiredYard;
    }

    public TargetYard getDesiredYard() {
        return desiredYard;
    }

    public Yard getInitialYard() {
        return initialYard;
    }

}
