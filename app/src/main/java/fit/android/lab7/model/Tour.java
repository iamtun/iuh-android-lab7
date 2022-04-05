package fit.android.lab7.model;

public class Tour {
    private int id;
    private String nameTour;

    public Tour(int id, String nameTour) {
        this.id = id;
        this.nameTour = nameTour;
    }

    public Tour() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameTour() {
        return nameTour;
    }

    public void setNameTour(String nameTour) {
        this.nameTour = nameTour;
    }
}
