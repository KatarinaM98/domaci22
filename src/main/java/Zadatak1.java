import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Zadatak1 {
    /*
    Testirati na stranici https://vue-demo.daniel-avellaneda.com login stranicu.
Test 1: Verifikovati da se u url-u stranice javlja ruta "/login".
Verifikovati da atribut type u polju za unos email ima vrednost "email"
i za password da ima atribut type "password.
Test 2: Koristeci Faker uneti nasumicno generisan email i password
i verifikovati da se pojavljuje poruka "User does not exist".
Test 3: Verifikovati da kad se unese admin@admin.com (sto je dobar email)
 i pogresan password (generisan faker-om), da se pojavljuje poruka "Wrong password"
Koristiti TestNG i dodajte before i after class metode.
Domaci se kači na github.
     */

    private WebDriver driver;


    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", "D:\\Google driver\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @BeforeMethod
    public void beforeTest() {
        driver.get("https://vue-demo.daniel-avellaneda.com");
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }

    @Test
    public void validUrlAndValidAtribute(){
        driver.get("https://vue-demo.daniel-avellaneda.com/login");
        String actualLink = driver.getCurrentUrl();
        Assert.assertTrue(actualLink.contains("/login"));

        WebElement atributeEmail = driver.findElement(By.xpath("//*[@id=\"email\"]"));
        Assert.assertEquals(atributeEmail.getAttribute("type"), "email");

        WebElement atributePassword = driver.findElement(By.xpath("//*[@id=\"password\"]"));
        Assert.assertEquals(atributePassword.getAttribute("type"), "password");
    }

    @Test
    public void ErrorMessageTest(){
        driver.get("https://vue-demo.daniel-avellaneda.com/login");

        Faker faker = new Faker();

        WebElement inputEmail = driver.findElement(By.xpath("//*[@id=\"email\"]"));
        inputEmail.sendKeys(faker.internet().emailAddress());

        WebElement inputPassword = driver.findElement(By.xpath("//*[@id=\"password\"]"));
        inputPassword.sendKeys(faker.internet().password());

        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"app\"]/div/main/div/div[2]/div/div/div[3]/span/form/div/div[3]/button"));
        loginButton.click();

        String expectedMessage = "User does not exist";

        WebElement actualMessage = driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/main/div/div[2]/div/div/div[4]/div/div/div/div/div[1]/ul/li/text()"));

        Assert.assertEquals(expectedMessage, actualMessage);

    }

    @Test
    public void WrongPasswordMessage(){
        driver.get("https://vue-demo.daniel-avellaneda.com/login");

        Faker faker2 = new Faker();

        WebElement inputEmail2 = driver.findElement(By.xpath("//*[@id=\"email\"]"));
        inputEmail2.sendKeys("admin@admin.com");

        WebElement inputPassword = driver.findElement(By.xpath("//*[@id=\"password\"]"));
        inputPassword.sendKeys(faker2.internet().password());

        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"app\"]/div/main/div/div[2]/div/div/div[3]/span/form/div/div[3]/button"));
        loginButton.click();

        String expectedMessage2 = "Wrong password";

        WebElement actualMessage2 = driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/main/div/div[2]/div/div/div[4]/div/div/div/div/div[1]/ul/li"));

        Assert.assertEquals(expectedMessage2, actualMessage2);

    }








}
