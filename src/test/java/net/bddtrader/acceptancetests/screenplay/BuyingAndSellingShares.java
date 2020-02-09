package net.bddtrader.acceptancetests.screenplay;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.junit.CucumberOptions;
import net.bddtrader.portfolios.Position;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        plugin = {"pretty"},
        features = "src/test/resources/features/portfolios/buying_and_selling_shares.feature"
)
public class BuyingAndSellingShares {}
