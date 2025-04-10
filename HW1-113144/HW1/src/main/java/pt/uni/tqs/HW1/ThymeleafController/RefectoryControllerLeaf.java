package pt.uni.tqs.HW1.ThymeleafController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pt.uni.tqs.HW1.service.RefectoryService;

@Controller
@RequestMapping("/site")
public class RefectoryControllerLeaf {

    @Autowired
    private RefectoryService refectoryService;

    @GetMapping("/refectories")
    public String chooseRefectory(Model model) {
        model.addAttribute("refectories", refectoryService.getAllRefectorys());
        return "refectory/choose"; // templates/refectory/choose.html
    }
}
