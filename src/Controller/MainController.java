package Controller;
 
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
 
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
 
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
 
import Service.UserService;
 
@Controller
@RequestMapping("/record")
public class MainController {
 
	@Resource(name = "userService")
	private UserService userService;
 
	@RequestMapping(value = "/login")
	public String GetFront(Model model) {
		return "login";
	}
 
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public String authenticate(@RequestParam("uname") String userName, @RequestParam("pass") String password, HttpSession session, HttpServletRequest request, Model model) {
 
//			boolean success = userService.validate(userName, password);
		boolean success = true;
		if (success) {
			session = request.getSession();
			session.setAttribute("users", userName);
			model.addAttribute("userName", userName);
			return "Hello";
		} else {
			return "error";
		}
 
	}
 
	@RequestMapping(value = "/Logout")
	public String Logout(HttpSession session, HttpServletRequest request, Model model) {
		HttpSession session2 = request.getSession(false);
		session2.removeAttribute("users");
		if (session2 != null) {
			session2.invalidate();
		}
		return "logout";
	}
 
	@RequestMapping(value = "/file/uploadFile", method = RequestMethod.POST)
	public String uploadFileHandler(@RequestParam("file") MultipartFile file, Model model) {
 
		if (!file.isEmpty()) {
			try {
				byte[] content = file.getBytes();
 
				BufferedReader br = null;
				InputStream is = null;
				int words = 0;
				int totalLines = 0;
				int totalCharacters = 0;
 
				String line;
				is = new ByteArrayInputStream(content);
 
				// Read file contents
				br = new BufferedReader(new InputStreamReader(is));
 
				// read each line one by one
				while ((line = br.readLine()) != null) {
					totalLines++;
 
					// ignore multiple white spaces
					String[] myWords = line.replaceAll("\\s+", " ").split(" ");
 
					for (String s : myWords) {
						totalCharacters += s.length();
					}
 
					words += myWords.length;
				}
 
				model.addAttribute("characters", totalCharacters);
				model.addAttribute("words", words);
				model.addAttribute("lines", totalLines);
				return "output";
 
			} catch (Exception e) {
				return "You failed to upload " + e.getMessage();
			}
		} else {
			return "You failed to upload " + " because the file was empty.";
		}
	}
}