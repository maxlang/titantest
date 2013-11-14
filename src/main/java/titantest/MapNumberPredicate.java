package titantest;

import com.tinkerpop.blueprints.Element;

import javax.annotation.Nullable;
import java.util.Map;


public class MapNumberPredicate implements com.google.common.base.Predicate<Map.Entry<Element,Number>> {

    public static enum IntegerPredicateOperator {
        GREATER_THAN, LESS_THAN, EQUAL, GREATER_THAN_EQUAL, LESS_THAN_EQUAL, NOT_EQUAL
    }

    IntegerPredicateOperator o;
    Number n;
    MapNumberPredicate(IntegerPredicateOperator o, Number n) {
        this.o = o;
        this.n = n;
    }

    @Override
    public boolean apply(@Nullable Map.Entry<Element,Number> entry) {
        if (entry == null) {
            return false;
        }
        Number number = entry.getValue();
        switch (this.o) {
            case GREATER_THAN:
                return n.doubleValue() > number.doubleValue();
            case LESS_THAN:
                return n.doubleValue() < number.doubleValue();
            case EQUAL:
                return n.doubleValue() == number.doubleValue();
            case GREATER_THAN_EQUAL:
                return n.doubleValue() >= number.doubleValue();
            case LESS_THAN_EQUAL:
                return n.doubleValue() <= number.doubleValue();
            case NOT_EQUAL:
                return n.doubleValue() != number.doubleValue();
            default:
                return false;
        }
    }
}
