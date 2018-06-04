package application;

//画像の表示はこれを使ってみる。。。
import javafx.scene.image.WritableImage;

// ミノを構成するパネルクラス
class Panel {
	private static final double panelW = 10;
	private static final double panelH = 10;

	// 左上を1,1とした表示位置
	private int visibleX; // 表示位置（横）
	private int visibleY; // 表示位置（縦）

	private double panelX; // 左上を0としての表示座標差分（横）
	private double panelY; // 左上を0としての表示座標差分（縦）

	// パネルの相対表示位置を指定。左上が1,1
	public Panel(int x, int y) {
		this.visibleX = x;
		this.visibleY = y;
		this.panelX = this.visibleX * panelW;
		this.panelY = this.visibleY * panelH;
	}

	// パメルの表示位置を更新
	public void updateVisible(int x, int y) {
		this.visibleX = x;
		this.visibleY = y;
		this.panelX = this.visibleX * panelW;
		this.panelY = this.visibleY * panelH;
	}

	// パネルの座標（横）を返す
	public double panelX() {
		return panelX;
	}

	// パネルの座標（縦）を返す
	public double panelY() {
		return panelY;
	}

	// パネルのサイズ（幅）を返す
	public static double panelW() {
		return panelW;
	}

	// パネルのサイズ（高さ）を返す
	public static double panelH() {
		return panelH;
	}
}