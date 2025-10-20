
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.*;

public class WellnessTest1  {

    WebDriver driver;
    Boots BT;
    Browser browser;
    @BeforeClass
    public void setUp() {
        try{
            browser = new Browser(null);
            driver = browser.chrome("https://www.test1.boots.com/online/wellness/healthier-you-review-quiz");
            BT = new Boots(driver);
        }catch(Exception e){
            System.out.println("Interrupted browser" +e.getMessage());
        }
        Reporter.log("Browser started",true);
    }
    @Test(priority = 1, dataProvider = "CandidateInfo", dataProviderClass = data.ExcelDataProvider.class)
    public void AboutYou(String FirstName, String Age){
        try {

            BT.id("firstName", FirstName);
            BT.id("age", Age);
            BT.click("xpath", "//*[@id=\"root\"]/div/div/div/div/div/div/div/div[3]/div/button[1]/p");
            BT.ScreenShot(" main.png");
            BT.click("name", "Next");

        }catch(Exception e){
            System.out.println("Interrupted AboutYou " +e.getMessage());
        }
        Reporter.log("About You Content Started",true);
    }
    @Test(priority=2)
    public void pageA1(){
        try{

            BT.jsexe("cssSelector","#Slider4 > div", 80);
            BT.jsexe("cssSelector","#Slider5 > div", 70);
            BT.jsexe("cssSelector","#Slider6 > div", 60);
            BT.jsexe("cssSelector","#Slider7 > div", 90);
            BT.jsexe("cssSelector","#Slider8 > div", 50);
            BT.jsexe("cssSelector","#Slider9 > div", 60);
            BT.ScreenShot(" pageA1.png");
            BT.click("name","Next");
        }catch(Exception e){
            System.out.println("Interrupted pageA1 " +e.getMessage());
        }
        Reporter.log("Page A1 started",true);
    }
    @Test(priority=3)
    public void pageA2(){
        try{

            BT.click("cssSelector","#root > div > div > div > div > div.Question > div.Picker > button:nth-child(1)");
            BT.click("cssSelector","#root > div > div > div > div > div.Question > div.Picker > button:nth-child(5)");
            BT.ScreenShot(" page2.png");
            BT.click("name","Next");
            BT.ScreenShot(" page2.png");
        }catch(Exception e){
            System.out.println("Interrupted pageA2 " +e.getMessage());
        }
        Reporter.log("Page A2 started",true);
    }
    @Test(priority=4)
    public void pageA3(){
        try{
            BT.click("cssSelector","#root > div > div > div > div > div.Question > div.Picker > button:nth-child(3)");
            BT.click("cssSelector","#root > div > div > div > div > div.Question > div.Picker > button:nth-child(7)");
            BT.ScreenShot(" pageA3.png");
            BT.click("name","Next");
        }catch(Exception e){
            System.out.println("Interrupted pageA3 " +e.getMessage());
        }
        Reporter.log("Page A3 started",true);
    }
    @Test(priority=5)
    public void pageA4(){
        try{
            BT.click("cssSelector","#root > div > div > div > div > div.Question > div.Picker > button:nth-child(1)");
            BT.click("cssSelector","     #root > div > div > div > div > div.Question > div.Picker > button:nth-child(5)");
            BT.click("cssSelector","#root > div > div > div > div > div.Question > div.Picker > button:nth-child(6)");
            BT.ScreenShot(" pageA4.png");
            BT.click("name","Next");
        }catch(Exception e){
            System.out.println("Interrupted pageA4 " +e.getMessage());
        }
        Reporter.log("Page A4 started",true);
    }
    @Test(priority=6)
    public void pageA5(){
        try{
            BT.ScreenShot(" pageA5.png");
            BT.click("name","Next");
        }catch(Exception e){
            System.out.println("Interrupted pageA5 " +e.getMessage());
        }
        Reporter.log("Page A5 started",true);
    }


    @Test(priority=7)
    public void Eating(){
        try{
            BT.click("cssSelector","#root > div > div > div > div > div.Question > div.Picker > button:nth-child(1)");
            BT.click("cssSelector","#root > div > div > div > div > div.Question > div.Picker > button:nth-child(3)");
            BT.ScreenShot(" Eating.png");
            BT.click("name","Next");

        }catch(Exception e){
            System.out.println("Interrupted Eating " +e.getMessage());
        }
        Reporter.log("Eating Content started",true);
    }
    @Test(priority=8)
    public void pageE1(){
        try{
            BT.click("cssSelector","#root > div > div > div > div > div.Question > div.Picker > button:nth-child(1)");
            BT.ScreenShot(" pageE1.png");
            BT.click("name","Next");
        }catch(Exception e){
            System.out.println("Interrupted pageE1 " +e.getMessage());
        }
        Reporter.log("Page E1 started",true);
    }

    @Test(priority=9)
    public void pageE2(){
        try{
            BT.click("cssSelector","#root > div > div > div > div > div.Question > div.Picker > button:nth-child(4)");
            BT.click("cssSelector","#root > div > div > div > div > div.Question > div.Picker > button:nth-child(3)");
            BT.ScreenShot(" pageE2.png");
            BT.click("name","Next");
        }catch(Exception e){
            System.out.println("Interrupted pageE2 " +e.getMessage());
        }
        Reporter.log("Page E2 started",true);
    }

    @Test(priority=10)
    public void pageE3(){
        try{
            BT.click("cssSelector","#root > div > div > div > div > div.Question > div.Picker > button:nth-child(1)");
            BT.click("cssSelector","    #root > div > div > div > div > div.Question > div.Picker > button:nth-child(3)");
            BT.ScreenShot(" pageE3.png");
            BT.click("name","Next");
        }catch(Exception e){
            System.out.println("Interrupted pageE3 " +e.getMessage());
        }
        Reporter.log("Page E3 started",true);
    }
    @Test(priority=11)
    public void pageE4(){
        try{
            BT.click("cssSelector","#root > div > div > div > div > div.Question > div.Picker > button:nth-child(4)");
            BT.click("cssSelector","#root > div > div > div > div > div.Question > div.Picker > button:nth-child(1)");
            BT.ScreenShot(" pageE4.png");
            BT.click("name","Next");
        }catch(Exception e){
            System.out.println("Interrupted pageE4 " +e.getMessage());
        }
        Reporter.log("Page E4 started",true);
    }
    @Test(priority=12)
    public void pageE5() {
        try {
            BT.ValueRadio("cssSelector", "button.Picker_radioLabel", 2);
            BT.ScreenShot(" pageE5.png");
            BT.click("name", "Next");
        } catch (Exception e) {
            System.out.println("Interrupted pageE5 " + e.getMessage());
        }
        Reporter.log("Page E5 started", true);
    }
    @Test(priority=13)
    public void pageE6(){
        try{
            BT.click("cssSelector","#root > div > div > div > div > div.Question > div.Picker > button:nth-child(4)");
            BT.click("cssSelector","#root > div > div > div > div > div.Question > div.Picker > button:nth-child(1)");
            BT.ScreenShot(" pageE6.png");
            BT.click("name","Next");
        } catch (Exception e) {
            System.out.println("Interrupted pageE6 " + e.getMessage());
        }
        Reporter.log("Page E6 started", true);
    }
    @Test(priority=14)
    public void pageE7(){
        try{
            BT.ScreenShot(" pageE7.png");
            BT.click("name","Next");
        } catch (Exception e) {
            System.out.println("Interrupted pageE7 " + e.getMessage());
        }
        Reporter.log("Page E7 started", true);
    }
    @Test(priority=15)
    public void Drinking(){
        try{
            BT.click("cssSelector","#root > div > div > div > div > div.Question > div.Picker > button:nth-child(3)");
            BT.ScreenShot(" Drinking.png");
            BT.click("name","Next");
        } catch (Exception e) {
            System.out.println("Interrupted  Drinking " + e.getMessage());
        }
        Reporter.log("Drinking content started", true);
    }
    @Test(priority=16)
    public void PageD1(){
        try{
            BT.incrementValue("cssSelector", "#root > div > div > div > div > div.Question > div.Dropdown_content > div > div:nth-child(4) > div.Numeric > button.Numeric_button.Numeric_buttonAppend", 2);
            BT.incrementValue("cssSelector","#root > div > div > div > div > div.Question > div.Dropdown_content > div > div:nth-child(2) > div.Numeric > button.Numeric_button.Numeric_buttonAppend",2);
            BT.ScreenShot(" pageD1.png");
            BT.click("name","Next");
        } catch (Exception e) {
            System.out.println("Interrupted  PageD1 " + e.getMessage());
        }
        Reporter.log("Page D1 started", true);
    }
    @Test(priority=17)
    public void PageD2(){
        try{
//            Thread.sleep(2000);
            BT.ScreenShot(" pageD2.png");
            BT.click("name","Next");
        } catch (Exception e) {
            System.out.println("Interrupted  PageD2 " + e.getMessage());
        }
        Reporter.log("Page D2 started", true);
    }
    @Test(priority=18)
    public void Moving(){
        try{
            BT.jsexe("cssSelector","#Slider28 > div",70);
            BT.jsexe("cssSelector","#Slider34 > div",80);
            BT.jsexe("cssSelector","#Slider35 > div",90);
            BT.ScreenShot(" Moving.png");
            BT.click("name","Next");
        } catch (Exception e) {
            System.out.println("Interrupted  Moving " + e.getMessage());
        }
        Reporter.log("Moving Content started", true);
    }
    @Test(priority=19)
    public void PageM1(){
        try{
            BT.jsexe("cssSelector","#Slider46 > div",70);
            BT.jsexe("cssSelector","#Slider49 > div",80);
            BT.jsexe("cssSelector","#Slider51 > div",90);
            BT.ScreenShot(" pageM1.png");
            BT.click("name","Next");
        } catch (Exception e) {
            System.out.println("Interrupted  PageM1 " + e.getMessage());
        }
        Reporter.log("Page M1 started", true);
    }
    @Test(priority=20)
    public void PageM2(){
        try{
            BT.ScreenShot(" pageM2.png");
            BT.click("name","Next");
        } catch (Exception e) {
            System.out.println("Interrupted  PageM2 " + e.getMessage());
        }
        Reporter.log("Page M2 started", true);
    }
    @Test(priority=21)
    public void Smoking(){
        try{
            BT.click("cssSelector","#root > div > div > div > div > div.Question > div.Picker > button:nth-child(2)");
            BT.ScreenShot(" Smoking.png");
            BT.click("name","Next");
        } catch (Exception e) {
            System.out.println("Interrupted  Smoking " + e.getMessage());
        }
        Reporter.log("Smoking content started", true);
    }
    @Test(priority=22)
    public void PageS1(){
        try{
            BT.ScreenShot(" pageS1.png");
            BT.click("name","Next");
        } catch (Exception e) {
            System.out.println("Interrupted  PageS1 " + e.getMessage());
        }
        Reporter.log("Page S1 started", true);
    }
//    @Test(priority=23)
//    public void WithoutLogin(){
//        try{
//            BT.ScreenShot(" pageWithoutLogin.png");
//            BT.click("cssSelector","#root > div > div > div > div > button");
//        } catch (Exception e) {
//            System.out.println("Interrupted  WithoutLogin " + e.getMessage());
//        }
//        BT.ScreenShot(" pageWithoutLogin-1.png");
//        Reporter.log("Without Login started", true);
//    }
    //@Test(priority=23)
//public void Registor() throws InterruptedException{
//        try{
//
//            BT.click("id","register-tab");
//            Thread.sleep(2000);
//            BT.ValueRadio("id","gigya-dropdown-114535114757577890",2);
//        } catch (Exception e) {
//            System.out.println("Interrupted  Registor " + e.getMessage());
//        }
//    Reporter.log("Registor content started", true);
//}
@Test(priority = 23, dataProvider = "login", dataProviderClass = data.ExcelDataProvider.class)

public void Login(String email,String password){
        try{
        BT.click("id","login-tab");
        BT.cssSelector("#gigya-textbox-loginID",email);
        BT.cssSelector("#gigya-password-52903167484493380",password);
        BT.ScreenShot(" Login.png");
        BT.click("cssSelector","#gigya-login-form > div.gigya-layout-row > div.gigya-composite-control.gigya-composite-control-submit.submit-wrapper > input");
        } catch (Exception e) {
            System.out.println("Interrupted  Login " + e.getMessage());
        }
    Reporter.log("Login  started", true);

}
    @AfterClass
    public void tearDown() throws InterruptedException {
//        Thread.sleep(5000);
//        driver.quit();
        Reporter.log("Browser finished",true);
    }
}