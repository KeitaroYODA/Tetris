package application;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;

// 積み上がったおじゃまミノクラス
// ミノが積みあがるまでは背景として表示される
abstract class OjamaMino {

	public double ojamaX = 0.0; // 横
	public double ojamaY = 0.0; // 縦

	// １次元配列で保持するか２次元配列をつかうか？
	// 表示領域の縦、横のマス数を定義
	// 接触判定はパネルの左下だけみればいいかも
	// パネルの座標を取得してパネルオブジェクトをコピーする
	// 表示中のミノのY座標を画面の高さで除算すればY位置が求められる。
	// 求めたY位置を起点としてパネルオブジェクトをコピー
	// 接地したミノのパネルをおじゃまパネルとして保持
	private ArrayList<Panel> ojamaPanelList = new ArrayList<>();

	// ミノがおじゃまミノまたは画面下に接触した際に
	// おじゃまミノ化する
	public void addOjamaPanel(Mino mino) {
		for (int i = 0; i < mino.panelArray.length; i++) {
			// 座標の絶対値がわからない。。。
			ojamaPanelList.add(mino.panelArray[i]);
		}
	}

	// おじゃまパネルを表示
	public void show(GraphicsContext canvas) {

	}
}