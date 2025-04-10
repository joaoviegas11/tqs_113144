package pt.uni.tqs.HW1.model;

import jakarta.persistence.*;
import pt.uni.tqs.HW1.utils.MealType;

import java.time.LocalDate;

@Entity
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private String meal;
    
    @Column(nullable = false, columnDefinition = "integer default 0")
    private int occupiedSeats = 0;

    public int getOccupiedSeats() {
        return occupiedSeats;
    }

    public void setOccupiedSeats(int occupiedSeats) {
        this.occupiedSeats = occupiedSeats;
    }

    @Enumerated(EnumType.STRING)
    private MealType mealType;

    
    @ManyToOne
    @JoinColumn(name = "refectory_id")
    private Refectory refectory;

    public Menu(LocalDate date, String meal, MealType mealType, Refectory refectory) {
        this.date = date;
        this.meal = meal;
        this.mealType = mealType;
        this.refectory = refectory;
    }

    public Menu() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public Refectory getRefectory() {
        return refectory;
    }

    public void setRefectory(Refectory refectory) {
        this.refectory = refectory;
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }


}
