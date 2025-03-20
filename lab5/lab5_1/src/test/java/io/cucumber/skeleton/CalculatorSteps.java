package io.cucumber.skeleton;

import io.cucumber.java.en.Given;
import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CalculatorSteps {

    // static final Logger log = getLogger(lookup().lookupClass());

    private Calculator calc;

    @Given("^a calculator I just turned on$")
    public void setup() {
        calc = new Calculator();
    }

    @When("I add {int} and {int}")
    public void add(int arg1, int arg2) {
        // log.debug("Adding {} and {}", arg1, arg2);
        calc.push(arg1);
        calc.push(arg2);
        calc.push("+");
    }

    @When("I substract {int} to {int}")
    public void substract(int arg1, int arg2) {
        // log.debug("Substracting {} and {}", arg1, arg2);
        calc.push(arg1);
        calc.push(arg2);
        calc.push("-");
    }

    @When("I multiply {int} to {int}")
    public void i_multiply_to(Integer int1, Integer int2) {
        calc.push(int1);
        calc.push(int2);
        calc.push("*");
    }


    private Exception exceptionThrown;

    @When("I divide {int} by {int}")
    public void divide(Integer arg1, Integer arg2) {
        try {
            calc.push(arg1);
            calc.push(arg2);
            calc.push("/");
        } catch (ArithmeticException e) {
            exceptionThrown = e;
        }
    }

    @Then("^Error division by zero$")
    public void error_division_by_zero() {
        assertThat(exceptionThrown).isNotNull();
        assertThat(exceptionThrown).isInstanceOf(ArithmeticException.class);
        assertThat(exceptionThrown.getMessage()).isEqualTo("Division by zero not allowed.");
    }

    @Then("the result is {int}")
    public void the_result_is(double expected) {
        Number value = calc.value();
        // log.debug("Result: {} (expected {})", value, expected);
        assertThat(value).isEqualTo(expected);
    }

}