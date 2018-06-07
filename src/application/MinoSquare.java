package application;

//ミノ（■）
class MinoSquare extends Mino {

	public MinoSquare() {
		super();

		// ミノを構成するパネルの画像を指定
		this.tileX = 320;
		this.initPanel();

		// ミノを構成するパネルの位置を指定
		// 0:パネルの横位置、1:パネルの縦位置
		panelPositionArray[0][0] = 2;
		panelPositionArray[0][1] = 2;
		panelPositionArray[1][0] = 3;
		panelPositionArray[1][1] = 2;
		panelPositionArray[2][0] = 2;
		panelPositionArray[2][1] = 3;
		panelPositionArray[3][0] = 3;
		panelPositionArray[3][1] = 3;
	}

	public void turn() {
		// 回転しても形はかわらない
	}
}