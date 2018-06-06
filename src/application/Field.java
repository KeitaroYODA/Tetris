package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

class Field {

	// 画面に表示するパネルの範囲
	private static final int COL = 16; // 列数
	private static final int ROW = 18; // 行数

	// 画面に表示されるパネルオブジェクトを格納
	//private Panel[][] panelArray = new Panel[COL][ROW];
	private Panel[][] panelArray = new Panel[ROW][COL];

	// おじゃまミノを構成するパネルオブジェクトの配列を返す
	public Panel[][] getPanelArray() {
		return this.panelArray;
	}

	public Field() {
		for (int i = 0; i < ROW; i++) {
			for (int l = 0; l < COL; l++) {
				this.panelArray[i][l] = null;
			}
		}
	}

	// ミノがおじゃまミノまたは画面下に接触した際に
	// おじゃまミノ化する
	public void addMinoPanel(Mino mino) {
		// ミノの左上の座標を取得
		double minoX = mino.getMinoX();
		double minoY = mino.getMinoY();

		for (int i = 0; i < Mino.PANEL_NUM; i++) {
			double panelX = (mino.panelPositionArray[i][0] * Panel.panelW()) - Panel.panelW();
			double panelY = (mino.panelPositionArray[i][1] * Panel.panelH()) - Panel.panelH();

			// パネルの座標の位置から配列のインデックスを取得してパネルオブジェクトを格納
			int col = (int) ((int)(minoX + panelX) / Panel.panelW());
			int row = (int) ((int)(minoY + panelY) / Panel.panelH());
			this.panelArray[row][col] = mino.getPanel(i);
		}
	}

	// おじゃまミノの行が揃っているかチェック
	// 揃った行数を返す
	public int checkOjamaMinoRow() {

		int checkNum = 0;
		boolean check = false;

		for (int i = 0; i < ROW; i++) {
			check = true;
			for (int l = 0; l < COL; l++) {
				if (this.panelArray[i][l] == null) {
					check = false;
				}
			}

			// １行でも揃っていれば真を返す
			if (check) {
				checkNum++;
			}
		}

		return checkNum;
	}

	// 行が揃ったおじゃまミノを削除する
	public void removeOjamaMinoRow() {
		boolean check = false;
		int index = ROW - 1;

		Panel[][] editArray = new Panel[ROW][COL];

		// 下から揃っているかチェック
		for (int i = (ROW - 1); i > 0; i--) {
			check = true;
			for (int l = 0; l < COL; l++) {
				if (this.panelArray[i][l] == null) {
					check = false;
				}
			}

			// 揃った行のを削除する
			// 揃っている行はコピーしない
			if (!check) {
				for (int l = 0; l < COL; l++) {
					editArray[index][l] = this.panelArray[i][l];
				}
				index--;
			}
		}
		this.panelArray = editArray;
	}

	// フィールドを表示
	public void show(GraphicsContext canvas) {
		for (int i = 0; i < ROW; i++) {
			for (int l = 0; l < COL; l++) {

				double x = l * Panel.panelW();
				double y = i * Panel.panelH();
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