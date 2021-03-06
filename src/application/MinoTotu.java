package application;

//ミノ（凸）
class MinoTotu extends Mino {
	public MinoTotu() {
		super();

		// ミノを構成するパネルの画像を指定
		this.tileX = 192;
		this.initPanel();

		// ミノを構成するパネルの位置を指定
		// 0:パネルの横位置、1:パネルの縦位置
		panelPositionArray[0][0] = 1;
		panelPositionArray[0][1] = 3;
		panelPositionArray[1][0] = 2;
		panelPositionArray[1][1] = 2;
		panelPositionArray[2][0] = 2;
		panelPositionArray[2][1] = 3;
		panelPositionArray[3][0] = 3;
		panelPositionArray[3][1] = 3;
	}

	// ミノを回転
	public void turn() {
		switch(this.direction) {
		case 1: // 正面
			panelPositionArray[0][0] = 1;
			panelPositionArray[0][1] = 3;
			panelPositionArray[1][0] = 2;
			panelPositionArray[1][1] = 2;
			panelPositionArray[2][0] = 2;
			panelPositionArray[2][1] = 3;
			panelPositionArray[3][0] = 3;
			panelPositionArray[3][1] = 3;
			break;
		case 2: // 右向き
			panelPositionArray[0][0] = 2;
			panelPositionArray[0][1] = 1;
			panelPositionArray[1][0] = 2;
			panelPositionArray[1][1] = 2;
			panelPositionArray[2][0] = 2;
			panelPositionArray[2][1] = 3;
			panelPositionArray[3][0] = 3;
			panelPositionArray[3][1] = 2;
			break;
		case 3: // 上下逆
			panelPositionArray[0][0] = 2;
			panelPositionArray[0][1] = 2;
			panelPositionArray[1][0] = 3;
			panelPositionArray[1][1] = 2;
			panelPositionArray[2][0] = 4;
			panelPositionArray[2][1] = 2;
			panelPositionArray[3][0] = 3;
			panelPositionArray[3][1] = 3;
			break;
		case 4: // 左向き
			panelPositionArray[0][0] = 2;
			panelPositionArray[0][1] = 3;
			panelPositionArray[1][0] = 3;
			panelPositionArray[1][1] = 2;
			panelPositionArray[2][0] = 3;
			panelPositionArray[2][1] = 3;
			panelPositionArray[3][0] = 3;
			panelPositionArray[3][1] = 4;
			break;
		}
	}
}