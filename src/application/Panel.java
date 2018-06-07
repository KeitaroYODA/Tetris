package application;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

// ミノを構成するパネルクラス
class Panel {
	// パネル幅
	private static final double panelW = 20;

	// パネル高さ
	private static final double panelH = 20;

	// 画像オブジェクト
	private WritableImage resizedImage;

	public Panel(String imgFile, int x, int y) {
		Image img = new Image(new File(imgFile).toURI().toString());
		resizedImage = new WritableImage(img.getPixelReader(),x, y, (int) (img.getWidth() / 16), (int) (img.getHeight() / 16));
	}

	// パネルの画像オブジェクトを返す
	public Image getImage() {
		return resizedImage;
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