package application;

//ミノ（｜）
class MinoBar extends Mino {

	public MinoBar() {
		super();

		// ミノを構成するパネルの画像を指定
		this.tileX = 64;
		this.initPanel();

		// ミノを構成するパネルの位置を指定
		// 0:パネルの横位置、1:パネルの縦位置
		panelPositionArray[0][0] = 2;
		panelPositionArray[0][1] = 1;
		panelPositionArray[1][0] = 2;
		panelPositionArray[1][1] = 2;
		panelPositionArray[2][0] = 2;
		panelPositionArray[2][1] = 3;
		panelPositionArray[3][0] = 2;
		panelPositionArray[3][1] = 4;
	}

	// ミノを回転
	public void turn() {
		switch(this.direction) {
		case 1: // 正面
			panelPositionArray[0][0] = 2;
			panelPositionArray[0][1] = 1;
			panelPositionArray[1][0] = 2;
			panelPositionArray[1][1] = 2;
			panelPositionArray[2][0] = 2;
			panelPositionArray[2][1] = 3;
			panelPositionArray[3][0] = 2;
			panelPositionArray[3][1] = 4;
			break;
		case 2: // 右向き
			panelPositionArray[0][0] = 1;
			panelPositionArray[0][1] = 2;
			panelPositionArray[1][0] = 2;
			panelPositionArray[1][1] = 2;
			panelPositionArray[2][0] = 3;
			panelPositionArray[2][1] = 2;
			panelPositionArray[3][0] = 4;
			panelPositionArray[3][1] = 2;
			break;
		case 3: // 上下逆
			panelPositionArray[0][0] = 2;
			panelPositionArray[0][1] = 1;
			panelPositionArray[1][0] = 2;
			panelPositionArray[1][1] = 2;
			panelPositionArray[2][0] = 2;
			panelPositionArray[2][1] = 3;
			panelPositionArray[3][0] = 2;
			panelPositionArray[3][1] = 4;
			break;
		case 4: // 左向き
			panelPositionArray[0][0] = 1;
			panelPositionArray[0][1] = 2;
			panelPositionArray[1][0] = 2;
			panelPositionArray[1][1] = 2;
			panelPositionArray[2][0] = 3;
			panelPositionArray[2][1] = 2;
			panelPositionArray[3][0] = 4;
			panelPositionArray[3][1] = 2;
			break;
		}
	}
}