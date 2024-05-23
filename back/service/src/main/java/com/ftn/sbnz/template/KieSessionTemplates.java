package com.ftn.sbnz.template;

import java.io.InputStream;
import java.util.List;

import org.drools.template.DataProvider;
import org.drools.template.DataProviderCompiler;
import org.drools.template.objects.ArrayDataProvider;

import com.ftn.sbnz.model.models.ContinuousSensor;
import com.ftn.sbnz.model.models.Configuration;

public class KieSessionTemplates {

    private static final String SENSOR_LOW_TEMPLATE_PATH = "/rules/drt/sensor_low.drt";
    private static final String SENSOR_MEDIUM_TEMPLATE_PATH = "/rules/drt/sensor_medium.drt";
    private static final String SENSOR_HIGH_TEMPLATE_PATH = "/rules/drt/sensor_high.drt";

    public static String addSensorLowToSession(List<ContinuousSensor> sensors) {
        return compileTemplateWithSensors(SENSOR_LOW_TEMPLATE_PATH, sensors);
    }

    public static String addSensorMediumToSession(List<ContinuousSensor> sensors) {
        return compileTemplateWithSensors(SENSOR_MEDIUM_TEMPLATE_PATH, sensors);
    }

    public static String addSensorHighToSession(List<ContinuousSensor> sensors) {
        return compileTemplateWithSensors(SENSOR_HIGH_TEMPLATE_PATH, sensors);
    }

    private static String compileTemplateWithSensors(String templatePath, List<ContinuousSensor> sensors) {
        InputStream template = KieSessionTemplates.class.getResourceAsStream(templatePath);

        String[][] data = sensors.stream()
                .map(sensor -> {
                    Configuration config = sensor.getConfig();
                    return new String[] { sensor.getId().toString(), sensor.getType(),
                            String.valueOf(config.getCriticalLowValue()),
                            String.valueOf(config.getCriticalHighValue()) };
                })
                .toArray(String[][]::new);

        DataProvider dataProvider = new ArrayDataProvider(data);
        DataProviderCompiler compiler = new DataProviderCompiler();
        return compiler.compile(dataProvider, template);
    }
}
