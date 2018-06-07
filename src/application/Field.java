package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

class Field {

	// 表示位置
	private static final double fieldX = Panel.panelW();
	private static final double fieldY = 0;

	// 画面に表示するパネルの範囲
	private static final int COL = 16; // 列数
	private static final int ROW = 18; // 行数

	// 画面に表示されるパネルオブジェクトを格納
	private Panel[][] panelArray = new Panel[ROW][COL];

	public Field() {
		// パネルオブジェクト格納配列初期化
		for (int i = 0; i < ROW; i++) {
			for (int l = 0; l < COL; l++) {
				this.panelArray[i][l] = null;
			}
		}
	}

	// パネルをフィールドにセット
	protected void setPanel(double x, double y, Panel panel) {
		int col = (int) (x / Panel.panelW());
		int row = (int) (y / Panel.panelH());
		if ((col >= 0 && col < COL) && (row >= 0 && row < ROW)) {
			this.panelArray[row][col] = panel;
		}
	}

	// 座標に一致するパネルがある場合は真を返す
	protected boolean colision(double x, double y) {

		int col = (int) (x / Panel.panelW());
		int row = (int) (y / Panel.panelH());

		// フィールド範囲外
		if ((col < 0 || col >= COL) || (row < 0 || row >= ROW)) {
			return true;
		}

		// パネルに衝突した
		if (this.panelArray[row][col] != null) {
			return true;
		}

		return false;
	}

	// おじゃまミノの行が揃っているかチェック
	// 揃った行数を返す
	public int checkRemoveRow() {

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
	public void removeRow() {
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

				double x = l * Panel.panelW() + Field.fieldX;
				double y = i * Panel.panelH() + Field.fieldY;
				double w = Panel.panelW();
				double h = Panel.panelH();

				if (this.panelArray[i][l] != null) {
					// パネルを表示
					Image img =	panelArray[i][l].getImage();
					canvas.drawImage(img,x, y, w, h);
				} else {
					// 背景色で塗りつぶし
					canvas.setFill(Color.DARKGRAY);
					canvas.fillRect(x, y, w, h);
					canvas.setFill(Color.BLACK);
					canvas.fillRect(x, y, w-1, h-1);
				}
			}
		}
	}

	public static double fieldX() {
		return fieldX;
	}

	public static double fieldY() {
		return fieldY;
	}

	public static int COL() {
		return COL;
	}

	public static int ROW() {
		return ROW;
	}
}