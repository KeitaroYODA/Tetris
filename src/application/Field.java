package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

class Field {

	// 画面に表示するパネルの範囲
	private static final int COL = 16; // 列数
	private static final int ROW = 18; // 行数

	// 画面に表示されるパネルオブジェクトを格納
	private Panel[][] panelArray = new Panel[ROW][COL];

	public Field() {
		// 配列初期化
		for (int i = 0; i < ROW; i++) {
			for (int l = 0; l < COL; l++) {
				this.panelArray[i][l] = null;
			}
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

			// 行が揃っている
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
	
	public Panel[][] getPanelArray() {
		return this.panelArray;
	}

	public void setPanelArray(Panel[][] panelArray) {
		this.panelArray = panelArray;
	}
}