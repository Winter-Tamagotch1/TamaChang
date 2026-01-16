package diarytamagotchiswing;


public class AppState {

    private static final AppState INSTANCE = new AppState();
    public static AppState getInstance() { 
        return INSTANCE;
    }
    private AppState() {}

    private int selectedPet = 0;
    private String petName = "";
    private String userName = "";

    public int getSelectedPet() {
        return selectedPet;
    }

    public void setSelectedPet(int selectedPet) {
        this.selectedPet = selectedPet;
    }

    public String getPetImagePath() {
        return switch (selectedPet) {
            case 1 -> "/image/image1.jpg";
            case 2 -> "/image/image2.jpg";
            case 3 -> "/image/image3.jpg";
            default -> "/image/image1.jpg";
        };
    }

    public String getPetName() { 
        return petName;
    }

    public void setPetName(String petName) { 
        this.petName = (petName == null) ? "" : petName;
    }

    public String getUserName() { 
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = (userName == null) ? "" : userName;
    }
}
