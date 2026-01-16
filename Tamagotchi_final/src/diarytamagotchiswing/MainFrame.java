package diarytamagotchiswing;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Color;

import screens.ScreenSelectPet;
import screens.ScreenDiary;
import screens.ScreenMainPet;
import screens.ScreenMemories;
import screens.ScreenPlaza;

public class MainFrame extends JFrame implements ScreenSwitcher{

    // 화면 전환을 관리하는 CardLayout
    // 用来管理页面切换的 CardLayout
    private CardLayout cardLayout;

    // 모든 화면을 담는 메인 패널
    // 用来装所有页面的主面板
    private JPanel cardPanel;

    public MainFrame() {
        setTitle("Diary Tamagotchi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 350);
        setLocationRelativeTo(null);

        // =========================
        // CardLayout 설정
        // 设置 CardLayout，用来切换不同 화면
        // =========================
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // =========================
        // 첫 번째 화면: 펫 선택 화면
        // 第一个页面：选择宠物页面
        // =========================
        ScreenSelectPet screenSelectPet = new ScreenSelectPet();
        ScreenMainPet screenMainPet = new ScreenMainPet();
        ScreenMemories screenMemories = new ScreenMemories();
        ScreenDiary screenDiary = new ScreenDiary();
        ScreenPlaza screenPlaza = new ScreenPlaza();


        // =========================
        // 화면들을 CardLayout에 등록
        // 把页面注册到 CardLayout 中
        // =========================
        cardPanel.add(screenSelectPet, "select");
        cardPanel.add(screenMainPet, "main");
        cardPanel.add(screenMemories, "memories");
        cardPanel.add(screenDiary, "diary");
        cardPanel.add(screenPlaza, "plaza");

        // 메인 프레임에 cardPanel 추가
        // 把卡片面板加到窗口中
        getContentPane().add(cardPanel);

        // 처음 실행 시 첫 번째 화면 보여주기
        // 程序启动时先显示第一个页面
        cardLayout.show(cardPanel, "select");

        setVisible(true);
    }
    
    @Override
    public void switchScreen(String screenName) {
        cardLayout.show(cardPanel, screenName);
    }

    public CardLayout getCardLayout() {
		return cardLayout;
	}

	public void setCardLayout(CardLayout cardLayout) {
		this.cardLayout = cardLayout;
	}

	public static void main(String[] args) {
        new MainFrame();
    }
}
