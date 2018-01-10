package io.prometheus.client;

import java.util.regex.Pattern;

/* Various utility functions for implementing Collectors. */

public class CollectorUtils {
    /**
     * Number of nanoseconds in a second.
     */
    public static final double NANOSECONDS_PER_SECOND = 1E9;
    /**
     * Number of milliseconds in a second.
     */
    public static final double MILLISECONDS_PER_SECOND = 1E3;
    public static final Pattern METRIC_NAME_RE = Pattern.compile("[a-zA-Z_:][a-zA-Z0-9_:]*");
    public static final Pattern METRIC_LABEL_NAME_RE = Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*");
    public static final Pattern RESERVED_METRIC_LABEL_NAME_RE = Pattern.compile("__.*");
    public static final Pattern SANITIZE_PREFIX_PATTERN = Pattern.compile("^[^a-zA-Z_]");
    public static final Pattern SANITIZE_BODY_PATTERN = Pattern.compile("[^a-zA-Z0-9_]");

    /**
     * Throw an exception if the metric name is invalid.
     */
    public static void checkMetricName(String name) {
        if (!METRIC_NAME_RE.matcher(name).matches()) {
            throw new IllegalArgumentException("Invalid metric name: " + name);
        }
    }

    /**
     * Sanitize metric name
     */
    public static String sanitizeMetricName(String metricName) {
        return SANITIZE_BODY_PATTERN.matcher(
                SANITIZE_PREFIX_PATTERN.matcher(metricName).replaceFirst("_")
        ).replaceAll("_");
    }

    /**
     * Throw an exception if the metric label name is invalid.
     */
    public static void checkMetricLabelName(String name) {
        if (!METRIC_LABEL_NAME_RE.matcher(name).matches()) {
            throw new IllegalArgumentException("Invalid metric label name: " + name);
        }
        if (RESERVED_METRIC_LABEL_NAME_RE.matcher(name).matches()) {
            throw new IllegalArgumentException("Invalid metric label name, reserved for internal use: " + name);
        }
    }

    /**
     * Convert a double to its string representation in Go.
     */
    public static String doubleToGoString(double d) {
        if (d == Double.POSITIVE_INFINITY) {
            return "+Inf";
        }
        if (d == Double.NEGATIVE_INFINITY) {
            return "-Inf";
        }
        if (Double.isNaN(d)) {
            return "NaN";
        }
        return Double.toString(d);
    }
}