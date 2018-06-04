package application;

import java.io.File;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

// 積み上がったおじゃまミノクラス
// ミノが積みあがるまでは背景として表示される
class OjamaMino {

	// 画面に表示するパネルの範囲
	private final int col; // 列数
	private final int row; // 行数
	
	// １次元配列で保持するか２次元配列をつかうか？
	// 表示領域の縦、横のマス数を定義
	// 接触判定はパネルの左下だけみればいいかも
	// パネルの座標を取得してパネルオブジェクトをコピーする
	// 表示中のミノのY座標を画面の高さで除算すればY位置が求められる。
	// 求めたY位置を起点としてパネルオブジェクトをコピー
	// 接地したミノのパネルをおじゃまパネルとして保持
	private Panel[][] panelArray;
	OjamaMino(int col, int row) {
		this.col = col;
		this.row = row;
		panelArray = new Panel[this.col][this.row];
	}

	// ミノがおじゃまミノまたは画面下に接触した際に
	// おじゃまミノ化する
	public void addOjamaMino(Mino mino) {
		// ミノの左上の座標を取得
		double minoX = mino.getMinoX();
		double minoY = mino.getMinoY();
		
		for (int i = 0; i < mino.panelArray.length; i++) {
			// ミノを構成するパネルの左上の座標を取得
			double panelX = mino.getPanel(i).panelX();
			double panelY = mino.getPanel(i).panelY();
			
			// パネルの座標の位置から配列のインデックスを取得してパネルオブジェクトを格納
			int col = (int) ((int)(minoX + panelX) / Panel.panelW());
			int row = (int) ((int)(minoY + panelY) / Panel.panelH());
			this.panelArray[col][row] = mino.getPanel(i);
		}
	}

	// おじゃまパネルを表示
	public void show(GraphicsContext canvas) {
		for (int i = 0; i < this.col; i++) {
			for (int l = 0; l < this.row; l++) {
				if (this.panelArray[i][l] == null) {
					break;
				}
				double x = i * Panel.panelW();
				double y = l * Panel.panelH();
				double w = Panel.panelW();
				double h = Panel.panelH();
				Image img =	new Image(new File("panel.png").toURI().toString());
				canvas.drawImage(img,x, y, w, h);
			}
		}
	}
}