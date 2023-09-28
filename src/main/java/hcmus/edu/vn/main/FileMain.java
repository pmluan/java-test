package hcmus.edu.vn.main;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileMain {

	public static void main(String[] args) {
		try {
			String link = "https://hdsaison-public-prod-bucket-statics-resource.s3.ap-southeast-1.amazonaws.com/bank-logo/WHXDZN39.png?response-content-disposition=inline&X-Amz-Security-Token=IQoJb3JpZ2luX2VjEIj%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FwEaDmFwLXNvdXRoZWFzdC0xIkcwRQIgb48jbTo4BpmivAPfpJlM%2Ba0a8hwcFJ%2Bv4d3%2Fql1vXhgCIQDKkulQIG6duJZm72Vt8aqbYbAxQm2f5RdYnKos6ZwMOSqFAwgREAIaDDk5Njg5MTEyOTMyMyIMSBxnEsDusNh%2BvSRhKuIClBE%2BWM3lIJpY62sguG8k%2B5Xc0yGh%2BAgQ3RHWbslHpIzU%2FC6%2BeUI%2F6xtWsyrL4SkdOiQb%2F9PebfRwLdPPXBhNHxd9DIRzGVxtD2SaIb10vQxPseFDDlAbuwLdxH6m80D83UnnpQ%2FG1rBtZ7GZg2sPlmxG0nETUvC9eeAlcFKgWrnqZwSmJLe9GUg2e7q8ZZG46s1IobVzyHq5k2vusyEafe8W2yMt2kGJ6M0Q9JRuONOp285R0py2Al6sERNuzFSkWDOzymH3rpTHmLmxws8o0DLjlRVQwIRjFifeYf7iVrbIM5sq%2F5zVunpiA9t21N5ht1gzG6%2Bmn0qfS8TBIgBX%2FclQX4GJt03ZFfTAR%2B8PXAXpCz%2FQYBvUfBOfAUHvm7w5ndEY0%2Bsd7EcjaGgTJiogjiL07HRFin2Jw9Xgs6ON41BqPh8AGzR7jEjrw3QLoeiYjp3SIi9A4nAr1ayG9IOswxqlMKr635EGOrMCIf8e%2FDOSA6Wqr8Bi6DVIW3ka9FFA55tHZuZLkT3FNB7ngG69Kbiapw5NEnx5YbJ2oqykQKYN%2BIn%2FLoxwNtWnxrqaMrGWao1%2BAZpea9gKwEWxvSAI%2Bhe%2FiEGJhVBOtxcPw6g1vT5bNWY7w3EL2yUDZrJEjxVjGAQlLLBH%2Fc%2BM9teJRreJdyDV%2BrfPrekc8qs7eYPRoVcR5%2F%2B2tZyvjLw33ZUZMWVFGrmYATtT2CmX31wkgxnonZspGD%2FF8R%2BkRgVL8KA%2Fs1odl9%2BYENQwAIHsNPgEjf1VWginbefIlUq%2FV0IT2Xk%2BvRXL1pAMp38Jqc8isq5haKvGG%2FvE5rsu%2BREho7SFK%2B8L2dIFM3nfSGATEnyfNk39nJ7w28LBCYGRr6xqqzPbW8adg52%2FifRrzg0wiIHJEg%3D%3D&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20220321T072845Z&X-Amz-SignedHeaders=host&X-Amz-Expires=604800&X-Amz-Credential=ASIA6QG2XOXVSO2NCWQY%2F20220321%2Fap-southeast-1%2Fs3%2Faws4_request&X-Amz-Signature=1ddc8686115a36ee75c08bef5e21d579e29167316bd632370c9ca484f0cd33b1";

			String folder = "test";

			URL url = new URL(link);

			String path = "C:\\Users\\phamm\\Desktop";
			
			Files.createDirectories(Paths.get(path +"\\"+ folder));
			Path targetPath = new File(path + "\\" +folder + "\\aa.jpg").toPath();

			Files.copy(url.openStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

			System.out.println(targetPath.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
