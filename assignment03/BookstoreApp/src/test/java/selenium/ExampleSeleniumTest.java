package selenium;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

class ExampleSeleniumTest
{

	static Process server;
	private WebDriver driver;

	@BeforeAll
	public static void setUpBeforeClass() throws Exception
	{
		ProcessBuilder pb = new ProcessBuilder("java", "-jar", "bookstore5.jar");
		server = pb.start();
	}

	@BeforeEach
	void setUp()
	{
		// Pick your browser
		// driver = new FirefoxDriver();
		// driver = new SafariDriver();
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("http://localhost:8080/");
		// wait to make sure Selenium is done loading the page
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("title")));
	}

	@AfterEach
	public void tearDown()
	{
		driver.close();
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception
	{
		server.destroy();
	}

	@Test
	void test1()
	{
		WebElement element = driver.findElement(By.id("title"));
		String expected = "YAMAZONE BookStore";
		String actual = element.getText();
		assertEquals(expected, actual);
	}

	@Test
	public void test2()
	{
		WebElement welcome = driver.findElement(By.cssSelector("p"));
		String expected = "Welcome";
		String actual = welcome.getText();
		assertEquals(expected, getWords(actual)[0]);
		WebElement langSelector = driver.findElement(By.id("locales"));
		langSelector.click();
		WebElement frSelector = driver.findElement(By.cssSelector("option:nth-child(3)"));
		frSelector.click();
		welcome = driver.findElement(By.cssSelector("p"));
		expected = "Bienvenu";
		actual = welcome.getText();
		assertEquals(expected, getWords(actual)[0]);
	}
	
	private String[] getWords(String s)
	{
		return s.split("\\s+");
	}

	////////////////////////////////////////////////////////////////////////////////////
	// F1 positive
	// Administrator adds a new book with valid information to the system
	@Test
	public void f1PTest()
	{
		admAddBook1();

		String expectedFB = "Successfully";
		String actualFB = driver.findElement(By.id("feedback")).getText();
		System.out.println("AAAAAAAAAACCCCCCCCCCCCCCCCCTTTTTTTTTTTTTTT: " + actualFB);

		boolean bool = actualFB.contains(expectedFB);

		admDelBook1();

		assertTrue(bool);
	}

	// F1 negative
	// Administrator cannot add a new book with invalid information to the system
	@Test
	public void f1NTest()
	{
		admLogin();

		WebElement cat = driver.findElement(By.id("addBook-category"));
		cat.sendKeys("CAT2");
		WebElement id = driver.findElement(By.id("addBook-id"));
		id.sendKeys("ID2222");
		WebElement title = driver.findElement(By.id("addBook-title"));
		title.sendKeys("TITLE2");
		WebElement author = driver.findElement(By.id("addBook-authors"));
		author.sendKeys("AUTHOR2");
		WebElement dscp = driver.findElement(By.id("longDescription"));
		dscp.sendKeys("DESCRIPTION DESCRIPTION DESCRIPTION DESCRIPTION DESCRIPTION");
		WebElement cost = driver.findElement(By.id("cost"));
		cost.sendKeys("cost");

		WebElement addBt = driver.findElement(By.name("addBook"));
		addBt.click();

		String expectedFB = "errors";
		String actualFB = driver.findElement(By.id("feedback")).getText();
		System.out.println("AAAAAAAAAACCCCCCCCCCCCCCCCCTTTTTTTTTTTTTTT: " + actualFB);
		assertTrue(actualFB.contains(expectedFB));
	}

	// F2 positive
	// Customer browses a book by searching by its category
	@Test
	public void f2PTest()
	{
		admAddBook1();

		driver.get("http://localhost:8080");

		WebElement search = driver.findElement(By.id("search"));
		search.sendKeys("CAT1");

		WebElement searchBt = driver.findElement(By.id("searchBtn"));
		searchBt.click();

		String expected = "We currently have the following items in category";
		String actual = driver.findElement(By.tagName("h1")).getText();
		System.out.println("AAAAAAAAAACCCCCCCCCCCCCCCCCTTTTTTTTTTTTTTT: " + actual);

		boolean bool = actual.contains(expected);

		admDelBook1();

		assertTrue(bool);
	}

	// F2 negative
	// Customer browses a Non-existent book
	@Test
	public void f2NTest()
	{
		driver.get("http://localhost:8080");

		WebElement search = driver.findElement(By.id("search"));
		search.sendKeys("CAT");

		WebElement searchBt = driver.findElement(By.id("searchBtn"));
		searchBt.click();

		String expected = "Sorry";
		String actual = driver.findElement(By.tagName("h1")).getText();
		System.out.println("AAAAAAAAAACCCCCCCCCCCCCCCCCTTTTTTTTTTTTTTT: " + actual);
		assertTrue(actual.contains(expected));
	}

	// F2.2 positive
	// Customer browses all books in catalogue by searching nothing
	@Test
	public void f22PTest()
	{
		admAddBook1();

		driver.get("http://localhost:8080");

		WebElement searchBt = driver.findElement(By.id("searchBtn"));
		searchBt.click();

		String expected = "We currently have the following items in category";
		String actual = driver.findElement(By.tagName("h1")).getText();
		System.out.println("AAAAAAAAAACCCCCCCCCCCCCCCCCTTTTTTTTTTTTTTT: " + actual);

		boolean bool = actual.contains(expected);

		admDelBook1();

		assertTrue(bool);
	}

	// F3 positive
	// Customer adds a book to books order
	@Test
	public void f3PTest()
	{
		admAddBook1();

		cusAddBook1Go2Cart();

		String expected = "1";
		String actual = driver.findElement(By.id("ID1111")).getAttribute("value");
		System.out.println("AAAAAAAAAACCCCCCCCCCCCCCCCCTTTTTTTTTTTTTTT: " + actual);

		admDelBook1();

		assertEquals(expected, actual);
	}

	// F3 negative. Cannot be implemented,
	// because there is no attribute called "storage" for a book,
	// which means the books in the system can never be out of storage

	// F4 positive
	// Customer views books order
	@Test
	public void f4PTest()
	{
		admAddBook1();
		cusAddBook1Go2Cart();

		boolean bool = cartHasBook1();

		admDelBook1();

		assertTrue(bool);
	}

	// F4 negative
	// Customer views an empty books order
	@Test
	public void f4NTest()
	{
		admAddBook1();

		driver.get("http://localhost:8080/orderPage");

		boolean bool = cartIsEmpty();

		admDelBook1();

		assertTrue(bool);
	}

	// F5 positive
	// A customer updates the number of ordered copies of a book in his/her books order.
	@Test
	public void f5PTest()
	{
		admAddBook1();

		cusAddBook1Go2Cart();

		WebElement inputBox = driver.findElement(By.id("ID1111"));
		inputBox.clear();
		inputBox.sendKeys("5");
		WebElement updateBt = driver.findElement(By.name("updateOrder"));
		updateBt.click();
		WebElement searchBt = driver.findElement(By.id("searchBtn"));
		searchBt.click();
		WebElement cartBt = driver.findElement(By.id("cartLink"));
		cartBt.click();

		String expected = "5";
		String actual = driver.findElement(By.id("ID1111")).getAttribute("value");
		System.out.println("AAAAAAAAAACCCCCCCCCCCCCCCCCTTTTTTTTTTTTTTT: " + actual);

		admDelBook1();

		assertEquals(expected, actual);
	}

	// F5 negative. Cannot be implemented,
	// because when the Cart/books order is empty,
	// There is nothing the customer can update,
	// Then there is nothing can be tested.

	// F5.1 positive
	// A customer updates the number of ordered copies of a book in his/her books order to 0,
	// and the book is removed from the books order.
	@Test
	public void f51PTest()
	{
		admAddBook1();

		cusAddBook1Go2Cart();

		WebElement inputBox = driver.findElement(By.id("ID1111"));
		inputBox.clear();
		inputBox.sendKeys("0");
		WebElement updateBt = driver.findElement(By.name("updateOrder"));
		updateBt.click();
		WebElement searchBt = driver.findElement(By.id("searchBtn"));
		searchBt.click();
		WebElement cartBt = driver.findElement(By.id("cartLink"));
		cartBt.click();

		boolean bool = cartIsEmpty();

		admDelBook1();

		assertTrue(bool);
	}

	// F6 positive
	// The date, total amount, amount of taxes, and amount of handling
	// and shipping fee of a customer’s books order is computed by the e-BookStore.
	@Test
	public void f6PTest()
	{
		admAddBook1();

		cusAddBook1Go2Cart();
		WebElement checkoutBt = driver.findElement(By.name("checkout"));
		checkoutBt.click();

		String expectedTotal = "$128.00";
		String expectedTax = "$13.00";
		String expectedShip = "$15.00";
		String actualTotal = driver.findElement(By.id("order_total")).getText();
		String actualTax = driver.findElement(By.id("order_taxes")).getText();
		String actualShip = driver.findElement(By.id("order_shipping")).getText();
		System.out.println("AAAAAAAAAACCCCCCCCCCCCCCCCCTTTTTTTTTTTTTTT: " + actualTotal + " " + actualTax + " " + actualShip);

		admDelBook1();

		assertEquals(expectedTotal, actualTotal);
		assertEquals(expectedTax, actualTax);
		assertEquals(expectedShip, actualShip);
	}

	// F6 negative
	// A customer’s books order is empty, the 0 will be shown for the total price and any other fee.
	@Test
	public void f6NTest()
	{
		admAddBook1();

		driver.get("http://localhost:8080/orderPage");
		WebElement checkoutBt = driver.findElement(By.name("checkout"));
		checkoutBt.click();

		String expectedTotal = "$0.00";
		String expectedTax = "$0.00";
		String expectedShip = "$0.00";
		String actualTotal = driver.findElement(By.id("order_total")).getText();
		String actualTax = driver.findElement(By.id("order_taxes")).getText();
		String actualShip = driver.findElement(By.id("order_shipping")).getText();
		System.out.println("AAAAAAAAAACCCCCCCCCCCCCCCCCTTTTTTTTTTTTTTT: " + actualTotal + " " + actualTax + " " + actualShip);

		admDelBook1();

		assertEquals(expectedTotal, actualTotal);
		assertEquals(expectedTax, actualTax);
		assertEquals(expectedShip, actualShip);
	}

	// F7 positive
	// A book is removed from the store’s books catalogue.
	@Test
	public void f7PTest() throws InterruptedException
	{
		admAddBook1();
		TimeUnit.SECONDS.sleep(1);
		admDelBook1();

		List<WebElement> tdList = driver.findElements(By.tagName("td"));
		boolean bool = true;
		for (int i = 0; i < tdList.size(); i++)
		{
			List<WebElement> list = driver.findElements(By.tagName("td"));
			if (list.size() > tdList.size() && i > tdList.size())
			{
				i = tdList.size();
			}
			try
			{
				if (list.get(i).getAttribute("id").contains("ID1111"))
				{
					bool = false;
					break;
				}
			}
			catch (Exception e)
			{
			}
		}
		assertTrue(bool);
	}

	// F7 negative. Cannot be implemented,
	// because when the catalogue is empty,
	// There is nothing the administrator can remove,
	// Then there is nothing can be tested.

	// F8 positive
	// An administrator is authenticated before adding/removing books.
	@Test
	public void f8PTest()
	{
		// URL to authenticate the administrators
		String expected = "http://localhost:8080/login";
		String actual = "";
		driver.get("http://localhost:8080");

		// The URL of adding books
		driver.get("http://localhost:8080/admin");
		actual = driver.getCurrentUrl();
		boolean add = expected.equals(actual);

		// The URL of removing books
		driver.get("http://localhost:8080/admin/catalog");
		actual = driver.getCurrentUrl();
		boolean remove = expected.equals(actual);

		boolean res = add && remove;
		assertTrue(res);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	// Use cases

	// Use case 1: Test the administrator login function of the system.
	// TC1: Administrator login one time and successful.
	@Test
	public void uc11Test()
	{
		admLogin();

		String expected = "http://localhost:8080/admin";
		String actual = driver.getCurrentUrl();

		assertEquals(expected, actual);
	}

	// Use case 1: Test the administrator login function of the system.
	// TC2: Administrator login failed
	@Test
	public void uc12Test()
	{
		driver.get("http://localhost:8080/admin");
		WebElement username = driver.findElement(By.id("loginId"));
		username.sendKeys("admin");
		WebElement pwd = driver.findElement(By.id("loginPasswd"));
		pwd.sendKeys("wrongpwd");
		WebElement loginBt = driver.findElement(By.id("loginBtn"));
		loginBt.click();

		String expected1 = "http://localhost:8080/login?error";
		String actual1 = driver.getCurrentUrl();
		System.out.println("AAAAAAAAAACCCCCCCCCCCCCCCCCTTTTTTTTTTTTTTT: " + actual1);

		username = driver.findElement(By.id("loginId"));
		username.sendKeys("admin");
		pwd = driver.findElement(By.id("loginPasswd"));
		pwd.sendKeys("password");
		loginBt = driver.findElement(By.id("loginBtn"));
		loginBt.click();

		String expected2 = "http://localhost:8080/admin";
		String actual2 = driver.getCurrentUrl();
		System.out.println("AAAAAAAAAACCCCCCCCCCCCCCCCCTTTTTTTTTTTTTTT: " + actual2);

		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2);
	}

	// Use case 2: Test the administrator sign out function of the system.
	// TC1: Administrator sign out successfully
	@Test
	public void uc21Test()
	{
		admLogin();

		WebElement signoutBt = (WebElement) driver.findElement(By.xpath("//*[@value='Sign out']"));

		signoutBt.click();

		String expected = "http://localhost:8080/login?logout";
		String actual = driver.getCurrentUrl();
		System.out.println("AAAAAAAAAACCCCCCCCCCCCCCCCCTTTTTTTTTTTTTTT: " + actual);

		assertEquals(expected, actual);
	}

	// Use case 3: Test the administrator adding book function of the system.
	// TC1: Administrator adds one book to the store’s catalogue successfully
	@Test
	public void uc31Test()
	{
		admAddBook1();

		String expectedFB = "Successfully";
		String actualFB = driver.findElement(By.id("feedback")).getText();
		System.out.println("UUUUUUUCCCCCCCCCCC333333331111111111-----------AAAAAAAAAACCCCCCCCCCCCCCCCCTTTTTTTTTTTTTTT: " + actualFB);

		boolean bool = actualFB.contains(expectedFB);

		admDelBook1();

		assertTrue(bool);
	}

	// Use case 3: Test the administrator adding book function of the system.
	// TC2: Administrator adds one book to the store’s catalogue failed due to wrong information
	@Test
	public void uc32Test()
	{
		admLogin();

		WebElement cat = driver.findElement(By.id("addBook-category"));
		cat.sendKeys("CAT2");
		WebElement id = driver.findElement(By.id("addBook-id"));
		id.sendKeys("ID2222");
		WebElement title = driver.findElement(By.id("addBook-title"));
		title.sendKeys("TITLE2");
		WebElement author = driver.findElement(By.id("addBook-authors"));
		author.sendKeys("AUTHOR2");
		WebElement dscp = driver.findElement(By.id("longDescription"));
		dscp.sendKeys("DESCRIPTION DESCRIPTION DESCRIPTION DESCRIPTION DESCRIPTION");
		WebElement cost = driver.findElement(By.id("cost"));
		cost.sendKeys("cost");

		WebElement addBt = driver.findElement(By.name("addBook"));
		addBt.click();

		String expectedFB = "errors";
		String actualFB = driver.findElement(By.id("feedback")).getText();
		System.out.println("AAAAAAAAAACCCCCCCCCCCCCCCCCTTTTTTTTTTTTTTT: " + actualFB);
		assertTrue(actualFB.contains(expectedFB));
	}

	// Use case 3: Test the administrator adding book function of the system.
	// TC3: Administrator adds one book to the store’s catalogue failed due to missing information
	@Test
	public void uc33Test()
	{
		admLogin();

		WebElement cat = driver.findElement(By.id("addBook-category"));
		cat.sendKeys("CAT2");
		WebElement id = driver.findElement(By.id("addBook-id"));
		id.sendKeys("ID2222");
		WebElement title = driver.findElement(By.id("addBook-title"));
		title.sendKeys("TITLE2");
		WebElement author = driver.findElement(By.id("addBook-authors"));
		author.sendKeys("AUTHOR2");
		WebElement dscp = driver.findElement(By.id("longDescription"));
		dscp.sendKeys("DESCRIPTION DESCRIPTION DESCRIPTION DESCRIPTION DESCRIPTION");
		WebElement cost = driver.findElement(By.id("cost"));
		cost.sendKeys("");

		WebElement addBt = driver.findElement(By.name("addBook"));
		addBt.click();

		String expectedFB = "errors";
		String actualFB = driver.findElement(By.id("feedback")).getText();
		System.out.println("AAAAAAAAAACCCCCCCCCCCCCCCCCTTTTTTTTTTTTTTT: " + actualFB);
		assertTrue(actualFB.contains(expectedFB));
	}

	// Use case 3: Test the administrator adding book function of the system.
	// TC4: Administrator adds one book to the store’s catalogue failed due to duplicated book id
	@Test
	public void uc34Test()
	{
		admAddBook1();

		driver.get("http://localhost:8080/admin");

		WebElement cat = driver.findElement(By.id("addBook-category"));
		cat.sendKeys("CAT2");
		WebElement id = driver.findElement(By.id("addBook-id"));
		id.sendKeys("ID1111");
		WebElement title = driver.findElement(By.id("addBook-title"));
		title.sendKeys("TITLE2");
		WebElement author = driver.findElement(By.id("addBook-authors"));
		author.sendKeys("AUTHOR2");
		WebElement dscp = driver.findElement(By.id("longDescription"));
		dscp.sendKeys("DESCRIPTION DESCRIPTION DESCRIPTION DESCRIPTION DESCRIPTION");
		WebElement cost = driver.findElement(By.id("cost"));
		cost.sendKeys("100");

		WebElement addBt = driver.findElement(By.name("addBook"));
		addBt.click();

		String expectedFB = "already exist";
		String actualFB = driver.findElement(By.id("feedback")).getText();
		System.out.println("AAAAAAAAAACCCCCCCCCCCCCCCCCTTTTTTTTTTTTTTT: " + actualFB);

		boolean bool = actualFB.contains(expectedFB);

		admDelBook1();

		assertTrue(bool);
	}

	// Use case 4: Test the user browse store’s catalogue function of the system.
	// TC1: Administrator searches a book by category, and e-BookStore shows a
	// list of books satisfying the selected category
	@Test
	public void uc41Test()
	{
		admAddBook1();

		WebElement search = driver.findElement(By.id("search"));
		search.sendKeys("CAT1");

		WebElement searchBt = driver.findElement(By.id("searchBtn"));
		searchBt.click();

		String expected = "We currently have the following items in category";
		String actual = driver.findElement(By.tagName("h1")).getText();
		System.out.println("AAAAAAAAAACCCCCCCCCCCCCCCCCTTTTTTTTTTTTTTT: " + actual);

		boolean bool = actual.contains(expected);

		admDelBook1();

		assertTrue(bool);
	}

	// Use case 4: Test the user browse store’s catalogue function of the system.
	// TC2: Administrator specifies no category, and e-BookStore shows a list of all the books in the catalogue
	@Test
	public void uc42Test()
	{
		admAddBook1();

		WebElement searchBt = driver.findElement(By.id("searchBtn"));
		searchBt.click();

		String expected = "We currently have the following items in category";
		String actual = driver.findElement(By.tagName("h1")).getText();
		System.out.println("AAAAAAAAAACCCCCCCCCCCCCCCCCTTTTTTTTTTTTTTT: " + actual);

		boolean bool = actual.contains(expected);

		admDelBook1();

		assertTrue(bool);
	}

	// Use case 4: Test the user browse store’s catalogue function of the system.
	// TC3: No book satisfying the searched category by administrator is found,
	// and e-BookStore displays a no matching book found message
	@Test
	public void uc43Test()
	{
		admLogin();

		WebElement search = driver.findElement(By.id("search"));
		search.sendKeys("CAT");

		WebElement searchBt = driver.findElement(By.id("searchBtn"));
		searchBt.click();

		String expected = "Sorry";
		String actual = driver.findElement(By.tagName("h1")).getText();
		System.out.println("AAAAAAAAAACCCCCCCCCCCCCCCCCTTTTTTTTTTTTTTT: " + actual);
		assertTrue(actual.contains(expected));
	}

	// Use case 4: Test the user browse store’s catalogue function of the system.
	// TC4: Customer searches a book by category, and e-BookStore shows a list
	// of books satisfying the selected category
	@Test
	public void uc44Test()
	{
		admAddBook1();

		driver.get("http://localhost:8080");

		WebElement search = driver.findElement(By.id("search"));
		search.sendKeys("CAT1");

		WebElement searchBt = driver.findElement(By.id("searchBtn"));
		searchBt.click();

		String expected = "We currently have the following items in category";
		String actual = driver.findElement(By.tagName("h1")).getText();
		System.out.println("AAAAAAAAAACCCCCCCCCCCCCCCCCTTTTTTTTTTTTTTT: " + actual);

		boolean bool = actual.contains(expected);

		admDelBook1();

		assertTrue(bool);
	}

	// Use case 4: Test the user browse store’s catalogue function of the system.
	// TC5: Customer specifies no category, and e-BookStore shows a list of all the books in the catalogue
	@Test
	public void uc45Test()
	{
		admAddBook1();

		driver.get("http://localhost:8080");

		WebElement searchBt = driver.findElement(By.id("searchBtn"));
		searchBt.click();

		String expected = "We currently have the following items in category";
		String actual = driver.findElement(By.tagName("h1")).getText();
		System.out.println("AAAAAAAAAACCCCCCCCCCCCCCCCCTTTTTTTTTTTTTTT: " + actual);

		boolean bool = actual.contains(expected);

		admDelBook1();

		assertTrue(bool);
	}

	// Use case 4: Test the user browse store’s catalogue function of the system.
	// TC6: No book satisfying the searched category by customer is found, and
	// e-BookStore displays a no matching book found message
	@Test
	public void uc46Test()
	{
		driver.get("http://localhost:8080/");

		WebElement search = driver.findElement(By.id("search"));
		search.sendKeys("CAT");

		WebElement searchBt = driver.findElement(By.id("searchBtn"));
		searchBt.click();

		String expected = "Sorry";
		String actual = driver.findElement(By.tagName("h1")).getText();
		System.out.println("AAAAAAAAAACCCCCCCCCCCCCCCCCTTTTTTTTTTTTTTT: " + actual);
		assertTrue(actual.contains(expected));
	}

	// Use case 5: Test the administrator Remove Book function of the system.
	// TC1: Administrator removes a book from the store’s catalogue
	@Test
	public void uc51Test() throws InterruptedException
	{
		admAddBook1();
		TimeUnit.SECONDS.sleep(1);
		admDelBook1();

		List<WebElement> tdList = driver.findElements(By.tagName("td"));
		boolean bool = true;
		for (int i = 0; i < tdList.size(); i++)
		{
			List<WebElement> list = driver.findElements(By.tagName("td"));
			if (list.size() > tdList.size() && i > tdList.size())
			{
				i = tdList.size();
			}
			try
			{
				if (list.get(i).getAttribute("id").contains("ID1111"))
				{
					bool = false;
					break;
				}
			}
			catch (Exception e)
			{
			}
		}
		assertTrue(bool);
	}

	// Use case 6: Test the customer Order Books function of the system.
	// TC1: Customer selects a book to add to his/her books order
	@Test
	public void uc61Test()
	{
		admAddBook1();

		cusAddBook1Go2Cart();

		String expected = "1";
		String actual = driver.findElement(By.id("ID1111")).getAttribute("value");
		System.out.println("AAAAAAAAAACCCCCCCCCCCCCCCCCTTTTTTTTTTTTTTT: " + actual);

		admDelBook1();

		assertEquals(expected, actual);
	}

	// Use case 6: Test the customer Order Books function of the system.
	// TC2: Customer selects a book already in his/her books order to add to his/her books order
	@Test
	public void uc62Test()
	{
		admAddBook1();

		cusAddBook1Go2Cart();

		WebElement searchBt = driver.findElement(By.id("searchBtn"));
		searchBt.click();
		WebElement addBt = driver.findElement(By.id("order-ID1111"));
		addBt.click();
		WebElement cartBt = driver.findElement(By.id("cartLink"));
		cartBt.click();

		String expected = "2";
		String actual = driver.findElement(By.id("ID1111")).getAttribute("value");
		System.out.println("AAAAAAAAAACCCCCCCCCCCCCCCCCTTTTTTTTTTTTTTT: " + actual);

		admDelBook1();

		assertEquals(expected, actual);
	}

	// Use case 7: Test the customer View Books Order function of the system.
	// TC1: Customer adds a book to books order and views it.
	@Test
	public void uc71Test()
	{
		admAddBook1();
		cusAddBook1Go2Cart();

		boolean bool = cartHasBook1();

		admDelBook1();

		assertTrue(bool);
	}

	// Use case 7: Test the customer View Books Order function of the system.
	// TC2: Customer adds nothing to books order and views it.
	@Test
	public void uc72Test()
	{
		admAddBook1();

		driver.get("http://localhost:8080");
		driver.get("http://localhost:8080/orderPage");

		boolean bool = cartIsEmpty();

		admDelBook1();

		assertTrue(bool);
	}

	// Use case 8: Test the customer Update Books Order function of the system.
	// TC1: Customer specifies a number of copies for an ordered book
	@Test
	public void uc81Test()
	{
		admAddBook1();

		cusAddBook1Go2Cart();

		WebElement inputBox = driver.findElement(By.id("ID1111"));
		inputBox.clear();
		inputBox.sendKeys("5");
		WebElement updateBt = driver.findElement(By.name("updateOrder"));
		updateBt.click();
		WebElement searchBt = driver.findElement(By.id("searchBtn"));
		searchBt.click();
		WebElement cartBt = driver.findElement(By.id("cartLink"));
		cartBt.click();

		String expected = "5";
		String actual = driver.findElement(By.id("ID1111")).getAttribute("value");
		System.out.println("AAAAAAAAAACCCCCCCCCCCCCCCCCTTTTTTTTTTTTTTT: " + actual);

		admDelBook1();

		assertEquals(expected, actual);
	}

	// Use case 8: Test the customer Update Books Order function of the system.
	// TC2: Customer specifies a number of 0 to copies for an ordered book
	@Test
	public void uc82Test()
	{
		admAddBook1();

		cusAddBook1Go2Cart();

		WebElement inputBox = driver.findElement(By.id("ID1111"));
		inputBox.clear();
		inputBox.sendKeys("0");
		WebElement updateBt = driver.findElement(By.name("updateOrder"));
		updateBt.click();
		WebElement searchBt = driver.findElement(By.id("searchBtn"));
		searchBt.click();
		WebElement cartBt = driver.findElement(By.id("cartLink"));
		cartBt.click();

		boolean bool = cartIsEmpty();

		admDelBook1();

		assertTrue(bool);
	}

	// Use case 8: Test the customer Update Books Order function of the system.
	// TC3: Customer specifies a number of less than 0 to copies for an ordered book
	@Test
	public void uc83Test()
	{
		admAddBook1();

		cusAddBook1Go2Cart();

		WebElement inputBox = driver.findElement(By.id("ID1111"));
		inputBox.clear();
		inputBox.sendKeys("-5");
		WebElement updateBt = driver.findElement(By.name("updateOrder"));
		updateBt.click();
		WebElement searchBt = driver.findElement(By.id("searchBtn"));
		searchBt.click();
		WebElement cartBt = driver.findElement(By.id("cartLink"));
		cartBt.click();

		boolean bool = cartIsEmpty();

		admDelBook1();

		assertTrue(bool);
	}

	// Use case 9: Test the customer Check Out function of the system.
	// TC1: Customer select to checkout with a book
	@Test
	public void uc91Test()
	{
		admAddBook1();

		cusAddBook1Go2Cart();
		WebElement checkoutBt = driver.findElement(By.name("checkout"));
		checkoutBt.click();

		String expectedTotal = "$128.00";
		String expectedTax = "$13.00";
		String expectedShip = "$15.00";
		String actualTotal = driver.findElement(By.id("order_total")).getText();
		String actualTax = driver.findElement(By.id("order_taxes")).getText();
		String actualShip = driver.findElement(By.id("order_shipping")).getText();
		System.out.println("AAAAAAAAAACCCCCCCCCCCCCCCCCTTTTTTTTTTTTTTT: " + actualTotal + " " + actualTax + " " + actualShip);

		admDelBook1();

		assertEquals(expectedTotal, actualTotal);
		assertEquals(expectedTax, actualTax);
		assertEquals(expectedShip, actualShip);
	}

	// Use case 9: Test the customer Check Out function of the system.
	// TC2: Customer select to checkout with empty cart
	@Test
	public void uc92Test()
	{
		admAddBook1();

		driver.get("http://localhost:8080/orderPage");
		WebElement checkoutBt = driver.findElement(By.name("checkout"));
		checkoutBt.click();

		String expectedTotal = "$0.00";
		String expectedTax = "$0.00";
		String expectedShip = "$0.00";
		String actualTotal = driver.findElement(By.id("order_total")).getText();
		String actualTax = driver.findElement(By.id("order_taxes")).getText();
		String actualShip = driver.findElement(By.id("order_shipping")).getText();
		System.out.println("AAAAAAAAAACCCCCCCCCCCCCCCCCTTTTTTTTTTTTTTT: " + actualTotal + " " + actualTax + " " + actualShip);

		admDelBook1();

		assertEquals(expectedTotal, actualTotal);
		assertEquals(expectedTax, actualTax);
		assertEquals(expectedShip, actualShip);
	}

	// Use case 10: Test the User Change Language function of the system.
	// TC1: Administrator change the language
	@Test
	public void ucX1Test()
	{
		admLogin();

		WebElement selectBar = driver.findElement(By.id("locales"));
		Select sel = new Select(selectBar);

		sel.selectByValue("fr-CA");

		String expectedUrl = "http://localhost:8080/admin?lang=fr-CA";
		String expectedTitle = "Librairie Y'AMAZONE";

		String actualUrl = driver.getCurrentUrl();
		String actualTitle = driver.findElement(By.id("title")).getText();
		System.out.println("AAAAAAAAAACCCCCCCCCCCCCCCCCTTTTTTTTTTTTTTT: " + actualUrl);
		System.out.println("AAAAAAAAAACCCCCCCCCCCCCCCCCTTTTTTTTTTTTTTT: " + actualTitle);

		assertEquals(expectedUrl, actualUrl);
		assertEquals(expectedTitle, actualTitle);
	}

	// Use case 10: Test the User Change Language function of the system.
	// TC2: Customer change the language
	@Test
	public void ucX2Test()
	{
		driver.get("http://localhost:8080/");

		WebElement selectBar = driver.findElement(By.id("locales"));
		Select sel = new Select(selectBar);

		sel.selectByValue("fr-CA");

		String expectedUrl = "http://localhost:8080/?lang=fr-CA";
		String expectedTitle = "Librairie Y'AMAZONE";

		String actualUrl = driver.getCurrentUrl();
		String actualTitle = driver.findElement(By.id("title")).getText();
		System.out.println("AAAAAAAAAACCCCCCCCCCCCCCCCCTTTTTTTTTTTTTTT: " + actualUrl);
		System.out.println("AAAAAAAAAACCCCCCCCCCCCCCCCCTTTTTTTTTTTTTTT: " + actualTitle);

		assertEquals(expectedUrl, actualUrl);
		assertEquals(expectedTitle, actualTitle);
	}

	
	//Tool methods
	
	public void admLogin()
	{
		driver.get("http://localhost:8080/admin");
		if (driver.getCurrentUrl().equals("http://localhost:8080/login"))
		{
			WebElement username = driver.findElement(By.id("loginId"));
			username.sendKeys("admin");

			WebElement pwd = driver.findElement(By.id("loginPasswd"));
			pwd.sendKeys("password");

			WebElement loginBt = driver.findElement(By.id("loginBtn"));
			loginBt.click();
		}
		else
		{
			System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAlready Logged in");
		}
	}

	public void admAddBook1()
	{
		admLogin();

		WebElement cat = driver.findElement(By.id("addBook-category"));
		cat.sendKeys("CAT1");
		WebElement id = driver.findElement(By.id("addBook-id"));
		id.sendKeys("ID1111");
		WebElement title = driver.findElement(By.id("addBook-title"));
		title.sendKeys("TITLE1");
		WebElement author = driver.findElement(By.id("addBook-authors"));
		author.sendKeys("AUTHOR1");
		WebElement dscp = driver.findElement(By.id("longDescription"));
		dscp.sendKeys("DESCRIPTION DESCRIPTION DESCRIPTION DESCRIPTION DESCRIPTION");
		WebElement cost = driver.findElement(By.id("cost"));
		cost.sendKeys("100");

		WebElement addBt = driver.findElement(By.name("addBook"));
		addBt.click();
	}

	public void admDelBook1()
	{
		admLogin();

		driver.get("http://localhost:8080/admin/catalog");
		WebElement delBt = driver.findElement(By.id("del-ID1111"));
		delBt.click();
	}

	public void cusAddBook1Go2Cart()
	{
		driver.get("http://localhost:8080");

		WebElement search = driver.findElement(By.id("search"));
		search.sendKeys("CAT1");

		WebElement searchBt = driver.findElement(By.id("searchBtn"));
		searchBt.click();

		WebElement addCartBt = driver.findElement(By.id("order-ID1111"));
		addCartBt.click();

		WebElement cartBt = driver.findElement(By.id("cartLink"));
		cartBt.click();
	}

	public boolean cartIsEmpty()
	{
		boolean isEmpty = false;
		try
		{
			WebElement box = driver.findElement(By.id("ID1111"));
		}
		catch (Exception e)
		{
			System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCcart is Empty");
			isEmpty = true;
		}

		return isEmpty;
	}

	public boolean cartHasBook1()
	{
		boolean hasBook1 = false;

		List<WebElement> tdList = driver.findElements(By.tagName("td"));
		List<String> strList = new ArrayList<String>();
		for (int i = 0; i < tdList.size(); i++)
		{
			strList.add(tdList.get(i).getText());
		}
		String expected1 = "ID1111";
		String expected2 = "TITLE1";
		String expected3 = "$100.00";
		if ((strList.contains(expected1)) && (strList.contains(expected2)) && (strList.contains(expected3)))
		{
			hasBook1 = true;
		}

		return hasBook1;
	}
}
