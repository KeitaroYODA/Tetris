package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

// 積み上がったおじゃまミノクラス
// ミノが積みあがるまでは背景として表示される
class OjamaMino {

	// 画面に表示するパネルの範囲
	private static final int COL = 16; // 列数
	private static final int ROW = 18; // 行数
	
	// 画面に表示されるパネルオブジェクトを格納
	private Panel[][] panelArray = new Panel[COL][ROW];

	// おじゃまミノを構成するパネルオブジェクトの配列を返す
	public Panel[][] getPanelArray() {
		return this.panelArray;
	}

	// ミノがおじゃまミノまたは画面下に接触した際に
	// おじゃまミノ化する
	public void addOjamaMino(Mino mino) {
		// ミノの左上の座標を取得
		double minoX = mino.getMinoX();
		double minoY = mino.getMinoY();

		for (int i = 0; i < Mino.PANEL_NUM; i++) {
			double panelX = (mino.panelPositionArray[i][0] * Panel.panelW()) - Panel.panelW();
			double panelY = (mino.panelPositionArray[i][1] * Panel.panelH()) - Panel.panelH();
			
			// パネルの座標の位置から配列のインデックスを取得してパネルオブジェクトを格納
			int col = (int) ((int)(minoX + panelX) / Panel.panelW());
			int row = (int) ((int)(minoY + panelY) / Panel.panelH());
			this.panelArray[col][row] = mino.getPanel(i);
		}
	}

	// おじゃまミノの行が揃っているかチェック
	public boolean checkOjamaMinoRow() {
		boolean check = false;

		for (int i = 0; i < ROW; i++) {
			check = true;
			for (int l = 0; l < COL; l++) {
				if (this.panelArray[l][i] == null) {
					check = false;
				}
			}

			// １行でも揃っていれば真を返す
			if (check) {
				return check;
			}
		}

		return check;
	}

	// おじゃまパネルを表示
	public void show(GraphicsContext canvas) {
		for (int i = 0; i < COL; i++) {
			for (int l = 0; l < ROW; l++) {
				double x = i * Panel.panelW();
				double y = l * Panel.panelH();
				double w = Panel.panelW();
				double h = Panel.panelH();
				if (this.panelArray[i][l] != null) {
					// パネルを表示
					Image img =	panelArray[i][l].getImage();
					canvas.drawImage(img,x, y, w, h);
				} else {
					// 背景色で塗りつぶし
					canvas.setFill(Color.BLACK);
					canvas.fillRect(x, y, w, h);
				}
			}
		}
	}
	
	public static int COL() {
		return COL;
	}
	
	public static int ROW() {
		return ROW;
	}
}