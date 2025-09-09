package data;
import org.example.ExcelReader;
import org.testng.annotations.DataProvider;

public class ExcelDataProvider {

    @DataProvider(name = "CandidateInfo")
    public Object[][] getSignupData() {
        return ExcelReader.readExcelData("CandidateInfo.xlsx", "signup", 2);
    }

    @DataProvider(name = "login")
    public Object[][] getLoginData() {
        return ExcelReader.readExcelData("CandidateInfo.xlsx", "login", 2);
    }

}