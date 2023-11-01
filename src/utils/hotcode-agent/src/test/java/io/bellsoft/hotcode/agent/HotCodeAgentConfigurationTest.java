package io.bellsoft.hotcode.agent;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class HotCodeAgentConfigurationTest {

    @Test
    void top() {
        int expected = 42;
        var props = new Properties();
        props.put("top", String.valueOf(expected));
        var c = HotCodeAgentConfiguration.from(props);
        assertEquals(expected, c.top);
    }

    @Test
    void maxStackDepth() {
        int expected = 42;
        var props = new Properties();
        props.put("max-stack-depth", String.valueOf(expected));
        var c = HotCodeAgentConfiguration.from(props);
        assertEquals(expected, c.maxStackDepth);
    }

    @Test
    void profilingDelay() {
        var expected = Duration.ofMinutes(5);
        var props = new Properties();
        props.put("delay", "5m");
        var c = HotCodeAgentConfiguration.from(props);
        assertEquals(expected, c.profilingDelay);
    }

    @Test
    void profilingDuration() {
        var expected = Duration.ofSeconds(30);
        var props = new Properties();
        props.put("duration", "30s");
        var c = HotCodeAgentConfiguration.from(props);
        assertEquals(expected, c.profilingDuration);
    }

    @Test
    void samplingInterval() {
        var expected = Duration.ofMillis(50);
        var props = new Properties();
        props.put("interval", "50ms");
        var c = HotCodeAgentConfiguration.from(props);
        assertEquals(expected, c.samplingInterval);
    }

    @Test
    void samplingPeriod() {
        var expected = Duration.ofHours(2);
        var props = new Properties();
        props.put("period", "2h");
        var c = HotCodeAgentConfiguration.from(props);
        assertEquals(expected, c.profilingPeriod);
    }

    @Test
    void durationLessThanPeriod() {
        var props = new Properties();
        props.put("duration", "30s");
        HotCodeAgentConfiguration.from(props);
        props.put("period", "20s");
        assertThrows(IllegalArgumentException.class, () -> HotCodeAgentConfiguration.from(props));
    }
    
}