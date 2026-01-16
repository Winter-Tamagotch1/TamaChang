package diarytamagotchiswing;

import java.util.List;

public interface AppController {
    // 초기 시작(유저/펫 생성 및 선택 확정)
    void startNewGame(String userName, String petName, int selectedPet);


    void addDiary(String content);
    List<String> loadDiaryList();

    int getStress(); 
    int getLevel();   
    String getPetImagePath();

    // 케어 액션(먹이기/놀아주기/씻기기)
    void care(String action);
}
