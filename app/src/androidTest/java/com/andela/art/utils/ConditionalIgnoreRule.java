package com.andela.art.utils;

import org.junit.Assume;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Modifier;

/**
 * Created by Kalela on 02/04/2019.
 */
public class ConditionalIgnoreRule implements MethodRule {
    /**
     * Condition interface.
     */
    public interface IgnoreCondition {
        /**
         * Is condition satisfied.
         * @return boolean.
         */
        boolean isSatisfied();
    }

    /**
     * Conditional ignore annotation retained in runtime.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    public @interface ConditionalIgnore {
        /**
         *
         * @return condition.
         */
        Class<? extends IgnoreCondition> condition();
    }

    @Override
    public Statement apply(Statement base, FrameworkMethod method, Object target) {
        Statement result = base;
        if (hasConditionalIgnoreAnnotation(method)) {
            IgnoreCondition condition = getIgnoreContition(target, method);
            if (condition.isSatisfied()) {
                result = new IgnoreStatement(condition);
            }
        }
        return result;
    }

    /**
     * Check if test has a conditional ignore annotation.
     * @param method method
     * @return boolean
     */
    private static boolean hasConditionalIgnoreAnnotation(FrameworkMethod method) {
        return method.getAnnotation(ConditionalIgnore.class) != null;
    }

    /**
     * Get the ignore condition.
     * @param target target
     * @param method method
     * @return the condition.
     */
    private static IgnoreCondition getIgnoreContition(Object target, FrameworkMethod method) {
        ConditionalIgnore annotation = method.getAnnotation(ConditionalIgnore.class);
        return new IgnoreConditionCreator(target, annotation).create();
    }

    /**
     * Created by Kalela on 02/04/2019.
     */
    private static class IgnoreConditionCreator {
        private final Object target;
        private final Class<? extends IgnoreCondition> conditionType;

        /**
         * constructor.
         * @param target target
         * @param annotation annotation
         */
        IgnoreConditionCreator(Object target, ConditionalIgnore annotation) {
            this.target = target;
            this.conditionType = annotation.condition();
        }

        /**
         *
         * @return condition
         */
        IgnoreCondition create() {
            checkConditionType();
            try {
                return createCondition();
            } catch (RuntimeException re) {
                throw re;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        /**
         *
         * @return condition
         * @throws Exception exception
         */
        private IgnoreCondition createCondition() throws Exception {
            IgnoreCondition result;
            if (isConditionTypeStandalone()) {
                result = conditionType.newInstance();
            } else {
                result = conditionType.getDeclaredConstructor(target.getClass())
                        .newInstance(target);
            }
            return result;
        }

        /**
         * Check condition type.
         */
        private void checkConditionType() {
            if (!isConditionTypeStandalone() && !isConditionTypeDeclaredInTarget()) {
                String msg
                        = "Conditional class '%s' is a member class "
                        + "but was not declared inside the test case using it.\n"
                        + "Either make this class a static class, "
                        + "standalone class (by declaring it in it's own file) "
                        + "or move it inside the test case using it";
                throw new IllegalArgumentException(String.format(msg, conditionType.getName()));
            }
        }

        /**
         *
         * @return boolean
         */
        private boolean isConditionTypeStandalone() {
            return !conditionType.isMemberClass() || Modifier
                    .isStatic(conditionType.getModifiers());
        }

        /**
         *
         * @return boolean
         */
        private boolean isConditionTypeDeclaredInTarget() {
            return target.getClass().isAssignableFrom(conditionType.getDeclaringClass());
        }
    }

    /**
     * Created by Kalela on 02/04/2019.
     */
    private static class IgnoreStatement extends Statement {
        private final IgnoreCondition condition;

        /**
         * constructor.
         * @param condition condition
         */
        IgnoreStatement(IgnoreCondition condition) {
            this.condition = condition;
        }

        @Override
        public void evaluate() {
            Assume.assumeTrue("Ignored by " + condition.getClass().getSimpleName(), true);
        }
    }
}
