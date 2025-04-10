package pt.uni.tqs.HW1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.uni.tqs.HW1.model.Refectory;
import pt.uni.tqs.HW1.model.Menu;
import pt.uni.tqs.HW1.service.RefectoryService;
import pt.uni.tqs.HW1.service.MenuService;

import java.util.List;

@RestController
@RequestMapping("/api/refectories")
public class RefectoryController {

    @Autowired
    private RefectoryService refectoryService;

    @Autowired
    private MenuService menuService;

    @GetMapping
    public List<Refectory> getAllRefectories() {
        return refectoryService.getAllRefectorys();
    }

    @GetMapping("/{id}/menus")
    public ResponseEntity<List<Menu>> getMenusByRefectory(@PathVariable Long id) {
        List<Menu> menus = menuService.getMenusByRefectory(id);
        return ResponseEntity.ok(menus);
    }

    @GetMapping("/{id}/menus/next7days")
    public ResponseEntity<List<Menu>> getMenusForNextSevenDays(@PathVariable Long id) {
        List<Menu> menus = menuService.getMenusForNextSevenDays(id);
        return ResponseEntity.ok(menus);
    }
    @GetMapping("/refectories/{id}")
    public ResponseEntity<Refectory> getRefectory(@PathVariable Long id) {
        try {
            Refectory refectory = refectoryService.getRefectoryById(id);
            return ResponseEntity.ok(refectory);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
