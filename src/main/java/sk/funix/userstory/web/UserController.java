package sk.funix.userstory.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sk.funix.userstory.domain.User;
import sk.funix.userstory.service.UserService;

/** Controller of User.
 * Basic Operations on an user.
 * @author Nicolas Milliard
 *
 */
@Controller("userController")
@RequestMapping("/user/**")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	public UserController(){
		
	}

	/** Create a new user.
	 * @param user
	 * @param result
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public String create(@Valid User user, BindingResult result, ModelMap modelMap) {
	    if (user == null) throw new IllegalArgumentException("A user is required");
	    if (result.hasErrors()) {
	        modelMap.addAttribute("user", user);
	        return "user/create";
	    }
	    return "redirect:/user/" + userService.addNewUser(user).getId();
	}
	    
	/**
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/user/form", method = RequestMethod.GET)
	public String createForm(ModelMap modelMap) {
	    modelMap.addAttribute("user", new User());
	    return "user/create";
	}
	    
    /**
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("user", userService.findUserById(id));
        return "user/show";
    }
	    
    /**
     * @param page
     * @param size
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("users", userService.findUsersEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) userService.countAllUsers() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("users", userService.findAllUsers());
        }
        return "user/list";
    }
	    
    /**
     * @param user
     * @param result
     * @param modelMap
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid User user, BindingResult result, ModelMap modelMap) {
        if (user == null) throw new IllegalArgumentException("A user is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("user", user);
            return "user/update";
        }
        userService.updateAnUser(user);
        return "redirect:/user/" + user.getId();
    }
    
    /**
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/user/{id}/form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("user", userService.findUserById(id));
        return "user/update";
    }
    
    /**
     * @param id
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        userService.deleteAnUserById(id);
        return "redirect:/user?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
}
