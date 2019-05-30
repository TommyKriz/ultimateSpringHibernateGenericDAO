package cybermancer;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * 
 * strict set to true - ensues build failure for missing Cucumber step
 * definitions
 * 
 * @author Tommy
 *
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/main/resources/cucumberFeatures", strict = false, monochrome = true)
public class TestRunner_CucumberFeatures {
}
