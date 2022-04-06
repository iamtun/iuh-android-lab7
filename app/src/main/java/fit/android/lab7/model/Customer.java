package fit.android.lab7.model;

public class Customer {
    private int id;
    private String name;
    private int id_tour;

    public Customer(int id, String name, int id_tour) {
        this.id = id;
        this.name = name;
        this.id_tour = id_tour;
    }

    public Customer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId_tour() {
        return id_tour;
    }

    public void setId_tour(int id_tour) {
        this.id_tour = id_tour;
    }
}
