package behaviorParameteriztion;

public class AppleHeavyWeightPredicate implements ApplePredicate {
    @Override
    public boolean test(behaviorParameterization.Apple apple) {
        return apple.getWeight() > 150;
    }
}
