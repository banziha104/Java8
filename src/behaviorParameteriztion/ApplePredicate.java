package behaviorParameteriztion;

import behaviorParameteriztion.behaviorParameterization.Apple;

@FunctionalInterface
public interface ApplePredicate {
    boolean test (Apple apple);
}
