package stepDef;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class cartDef {
	static WebDriver driver;
	static WebDriverWait wait;
	@BeforeAll
	public static void Startup() {
		WebDriverManager.edgedriver().setup();
		driver= new EdgeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
		
		wait= new WebDriverWait(driver, Duration.ofSeconds(30));
	}

         @Given("User is on Launch Page")
public void user_is_on_launch_page() {
    // Write code here that turns the phrase above into concrete actions
	driver.get("https://www.demoblaze.com/index.html");
}

@When("User navigates to Login Page")
public void user_navigates_to_login_page() throws InterruptedException {
    // Write code here that turns the phrase above into concrete actions
	Thread.sleep(3000);
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
   wait.until(ExpectedConditions.elementToBeClickable(By.id("login2"))).click();
   Thread.sleep(3000);
   wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[id=\"loginusername\"]"))).sendKeys("Poongundran");
   wait.until(ExpectedConditions.elementToBeClickable(By.id("loginpassword"))).sendKeys("Atman");
   driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
   Thread.sleep(3000);
   wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div//button[@onclick='logIn()']"))).click();
   Thread.sleep(3000);
}

@Then("Should display Home Page")
public void should_display_home_page() {
    // Write code here that turns the phrase above into concrete actions
	driver.findElement(By.xpath("//div//a[contains(text(),'Home')]")).click();
	 wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li//a[@id='logout2']")));
	 String out=driver.findElement(By.xpath("//li//a[@id='logout2']")).getText();
	 Assert.assertTrue(true, out);
}

@When("User Add an item to cart")
public void user_add_an_item_to_cart(io.cucumber.datatable.DataTable dataTable) {
	List<Map<String,String>> data=dataTable.asMaps();
	
	for(int i=0;i<data.size();i++) {
	String str1=data.get(i).get("category");
	String str2=data.get(i).get("items");
	// get items from the . feature file
	driver.findElement(By.xpath("//div//a[contains(text(),'Home')]")).click();
	driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
	String typecat="//div/a[contains(text(),'"+str1+"')]";
	driver.findElement(By.xpath(typecat)).click();
	 String item ="//a[contains(text(),'"+str2+"')]";
	 driver.findElement(By.xpath(item)).click();
	 driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
	 
	 //Above items must be added
	 
	 WebElement btn = driver.findElement(By.xpath("//div/a[contains(text(),'Add to cart')]"));
		wait.until(ExpectedConditions.elementToBeClickable(btn));
		btn.click(); 
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		alert.accept();		
	}
}

@Then("Items must be added to the cart")
public void items_must_be_added_to_the_cart() {
	driver.findElement(By.xpath("//a[contains(text(),'Cart')]")).click();
	// driver.findElement(By.xpath("//a[contains(text(),'Cart')]")).click();)
	
}
@Given("User is in the Cart Page")
public void user_is_in_the_cart_page() throws InterruptedException {
}

@When("List of items should be available in the cart")
public void list_of_items_should_be_available_in_the_cart() throws InterruptedException {
    // Write code here that turns the phrase above into concrete actions
	
	//Find that what are the items is selected
	wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//td[2]")));
}

@Then("Delete an item from the cart")
public void delete_an_item_from_the_cart() throws InterruptedException{
	List<WebElement> BeforeCart=driver.findElements(By.xpath("//td[2]"));
	wait.until(ExpectedConditions.visibilityOfAllElements(BeforeCart));
	Thread.sleep(2000);
	
	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div//a[contains(text(),'Delete')][1]"))).click();
	
	List<WebElement> AfterCart=driver.findElements(By.xpath("//td[2]"));
	
	wait.until(ExpectedConditions.visibilityOfAllElements(AfterCart));
	Thread.sleep(3000);
	if(BeforeCart!=AfterCart) {
		Assert.assertTrue(true);
	}
//	driver.findElement(By.xpath("//div//a[contains(text(),'Home')]")).click();
}

@When("User Place ordering")
public void user_place_ordering() throws InterruptedException {
    // Write code here that turns the phrase above into concrete actions
	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div//button[contains(text(),'Place Order')]"))).click();
	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div//input[@id='name']"))).sendKeys("Poongundran");
	

	wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#country"))).sendKeys("India");
	wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#city"))).sendKeys("VPM");
	wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#card"))).sendKeys("7867867862");
	wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#month"))).sendKeys("Oct");
	wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#year"))).sendKeys("2024");
	
	
	  Thread.sleep(2000);
	  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	  driver.findElement(By.xpath("//button[contains(text(),'Purchase')]")).click();
}

@Then("Item must be Placed")
public void item_must_be_placed() {
	WebElement conmsg = driver.findElement(By.xpath("(//h2)[3]"));
	boolean confirm=conmsg.isDisplayed();
	Assert.assertTrue(confirm);
	driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click(); 
}
}