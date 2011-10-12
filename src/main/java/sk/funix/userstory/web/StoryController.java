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

import sk.funix.userstory.domain.Story;
import sk.funix.userstory.service.StoryService;

/** Controller of Story.
 * Basic Operations on an story.
 * @author Nicolas Milliard
 *
 */
@Controller("storyController")
@RequestMapping("/story/**")
public class StoryController {
	
	@Autowired
	private StoryService storyService;
	
	public StoryController(){
		
	}

	/** Create a new story for the authenticated user.
	 * @param story
	 * @param result
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/story", method = RequestMethod.POST)
	public String create(@Valid Story story, BindingResult result, ModelMap modelMap) {
	    if (story == null) throw new IllegalArgumentException("A story is required");
	    if (result.hasErrors()) {
	        modelMap.addAttribute("story", story);
	        return "story/create";
	    }
	    return "redirect:/story/" + storyService.addNewStory(story).getId();
	}
	    
    /** Return a story own by the authenticated user.
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/story/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, ModelMap modelMap) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        modelMap.addAttribute("story", storyService.findStoryById(id));
        return "story/show";
    }
	    
    /** List all stories own by the authenticated user.
     * @param page
     * @param size
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/story", method = RequestMethod.GET)
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            modelMap.addAttribute("storys", storyService.findStorysEntries( page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) storyService.countAllStories() / sizeNo;
            modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            modelMap.addAttribute("storys", storyService.findAllStories());
        }
        return "story/list";
    }
	    
    /** Update a story own by the authenticated user.
     * @param story
     * @param result
     * @param modelMap
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid Story story, BindingResult result, ModelMap modelMap) {
        if (story == null) throw new IllegalArgumentException("A story is required");
        if (result.hasErrors()) {
            modelMap.addAttribute("story", story);
            return "story/update";
        }
        storyService.updateAnStory(story);
        return "redirect:/story/" + story.getId();
    }
    
    /** Delete a story own by the authenticated user.
     * @param id
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/story/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        if (id == null) throw new IllegalArgumentException("An Identifier is required");
        storyService.deleteAnStoryById(id);
        return "redirect:/story?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
}
