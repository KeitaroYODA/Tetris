package application;

import java.io.File;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

// ミノクラス
abstract class Mino {

	// ミノの向き
	// 1:正面
	// 2:右向き
	// 3:上下逆
	// 4:左向き
	protected int direction = 1;

	// ミノの左上の座標
	public double minoX = 0.0; // 横
	public double minoY = 0.0; // 縦

	// ミノを構成するパネル数
	public static final int PANEL_NUM = 4;

	// ミノを構成するパネル
	protected Panel[] panelArray = new Panel[PANEL_NUM];

	public Panel getPanel(int i) {
		return panelArray[i];
	}
	
	// ミノを削除する
	protected void clear(GraphicsContext canvas) {
		double x = 0;
		double y = 0;
		double w = 0;
		double h = 0;

		for (int i = 0; i < PANEL_NUM; i++) {
			x = this.minoX + panelArray[i].panelX();
			y = this.minoY + panelArray[i].panelY();
			w = Panel.panelW();
			h = Panel.panelH();
			canvas.clearRect(x, y, w, h);
		}
	}

	// ミノを表示する
	protected void show(GraphicsContext canvas) {
		double x = 0;
		double y = 0;
		double w = 0;
		double h = 0;

		for (int i = 0; i < PANEL_NUM; i++) {
			x = this.minoX + panelArray[i].panelX();
			y = this.minoY + panelArray[i].panelY();
			w = Panel.panelW();
			h = Panel.panelH();
			//canvas.setFill(Color.BLUE);
			//canvas.fillRect(x, y, w, h);
			Image img =	new Image(new File("panel.png").toURI().toString());
			canvas.drawImage(img,x, y, w, h);
		}
	}

	protected void turn() {
		// 継承先で実装
	}

	protected void turnRight() {
		this.direction++;
		if (this.direction > 4) {
			this.direction = 1;
		}
		this.turn();
	}

	protected void turnLeft() {
		this.direction--;
		if (this.direction < 1) {
			this.direction = 4;
		}
		this.turn();
	}

	protected double getMinoX() {
		return this.minoX;
	}

	protected double getMinoY() {
		return this.minoY;
	}

	protected void setMinoX(double x) {
		this.minoX = x;
	}

	protected void setMinoY(double y) {
		this.minoY = y;
	}

	public Mino() {
		// 継承先で実装
	}
}

//ミノ（｜）
class Mino1 extends Mino {
	public Mino1() {
		panelArray[0] = new Panel(1, 1);
		panelArray[1] = new Panel(1, 2);
		panelArray[2] = new Panel(1, 3);
		panelArray[3] = new Panel(1, 4);
	}

	// ミノを回転
	public void turn() {
		switch(this.direction) {
		case 1: // 正面
			panelArray[0].updateVisible(1, 1);
			panelArray[1].updateVisible(1, 2);
			panelArray[2].updateVisible(1, 3);
			panelArray[3].updateVisible(1, 4);
			break;
		case 2: // 右向き
			panelArray[0].updateVisible(1, 1);
			panelArray[1].updateVisible(2, 1);
			panelArray[2].updateVisible(3, 1);
			panelArray[3].updateVisible(4, 1);
			break;
		case 3: // 上下逆
			panelArray[0].updateVisible(4, 1);
			panelArray[1].updateVisible(4, 2);
			panelArray[2].updateVisible(4, 3);
			panelArray[3].updateVisible(4, 4);
			break;
		case 4: // 左向き
			panelArray[0].updateVisible(1, 4);
			panelArray[1].updateVisible(2, 4);
			panelArray[2].updateVisible(3, 4);
			panelArray[3].updateVisible(4, 4);
			break;
		}
	}
}

// ミノ（凸）
class Mino2 extends Mino {
	public Mino2() {
		panelArray[0] = new Panel(1, 4);
		panelArray[1] = new Panel(2, 3);
		panelArray[2] = new Panel(2, 4);
		panelArray[3] = new Panel(3, 4);
	}

	// ミノを回転
	public void turn() {
		switch(this.direction) {
		case 1: // 正面
			panelArray[0].updateVisible(1, 4);
			panelArray[1].updateVisible(2, 3);
			panelArray[2].updateVisible(2, 4);
			panelArray[3].updateVisible(3, 4);
			break;
		case 2: // 右向き
			panelArray[0].updateVisible(1, 1);
			panelArray[1].updateVisible(1, 2);
			panelArray[2].updateVisible(1, 3);
			panelArray[3].updateVisible(2, 2);
			break;
		case 3: // 上下逆
			panelArray[0].updateVisible(2, 1);
			panelArray[1].updateVisible(3, 1);
			panelArray[2].updateVisible(4, 1);
			panelArray[3].updateVisible(3, 2);
			break;
		case 4: // 左向き
			panelArray[0].updateVisible(4, 2);
			panelArray[1].updateVisible(3, 3);
			panelArray[2].updateVisible(4, 3);
			panelArray[3].updateVisible(4, 4);
			break;
		}
	}
}

//ミノ（■）
class Mino3 extends Mino {
	public Mino3() {
		panelArray[0] = new Panel(1, 3);
		panelArray[1] = new Panel(1, 4);
		panelArray[2] = new Panel(2, 3);
		panelArray[3] = new Panel(2, 4);
	}

	public void turn() {
		// 回転しても形はかわらない
	}
}

//ミノ（カギ１）
class Mino4 extends Mino {
	public Mino4() {
		panelArray[0] = new Panel(1, 2);
		panelArray[1] = new Panel(1, 3);
		panelArray[2] = new Panel(2, 3);
		panelArray[3] = new Panel(2, 4);
	}

	// ミノを回転
	public void turn() {
		switch(this.direction) {
		case 1: // 正面
			panelArray[0].updateVisible(1, 2);
			panelArray[1].updateVisible(1, 3);
			panelArray[2].updateVisible(2, 3);
			panelArray[3].updateVisible(2, 4);
			break;
		case 2: // 右向き
			panelArray[0].updateVisible(3, 1);
			panelArray[1].updateVisible(4, 1);
			panelArray[2].updateVisible(2, 2);
			panelArray[3].updateVisible(3, 2);
			break;
		case 3: // 上下逆
			panelArray[0].updateVisible(3, 2);
			panelArray[1].updateVisible(3, 3);
			panelArray[2].updateVisible(4, 3);
			panelArray[3].updateVisible(4, 4);
			break;
		case 4: // 左向き
			panelArray[0].updateVisible(1, 4);
			panelArray[1].updateVisible(2, 3);
			panelArray[2].updateVisible(2, 4);
			panelArray[3].updateVisible(3, 3);
			break;
		}
	}
}

//ミノ（カギ２）
class Mino5 extends Mino {
	public Mino5() {
		panelArray[0] = new Panel(1, 3);
		panelArray[1] = new Panel(1, 4);
		panelArray[2] = new Panel(2, 2);
		panelArray[3] = new Panel(2, 3);
	}

	// ミノを回転
	public void turn() {
		switch(this.direction) {
		case 1: // 正面
			panelArray[0].updateVisible(1, 3);
			panelArray[1].updateVisible(1, 4);
			panelArray[2].updateVisible(2, 2);
			panelArray[3].updateVisible(2, 3);
			break;
		case 2: // 右向き
			panelArray[0].updateVisible(2, 1);
			panelArray[1].updateVisible(3, 1);
			panelArray[2].updateVisible(3, 2);
			panelArray[3].updateVisible(4, 2);
			break;
		case 3: // 上下逆
			panelArray[0].updateVisible(4, 2);
			panelArray[1].updateVisible(3, 3);
			panelArray[2].updateVisible(4, 3);
			panelArray[3].updateVisible(3, 4);
			break;
		case 4: // 左向き
			panelArray[0].updateVisible(1, 3);
			panelArray[1].updateVisible(2, 3);
			panelArray[2].updateVisible(2, 4);
			panelArray[3].updateVisible(3, 4);
			break;
		}
	}
}