package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

// ミノクラス
abstract class Mino implements Cloneable{

	// ミノの向き
	// 1:正面
	// 2:右向き
	// 3:上下逆
	// 4:左向き
	protected int direction = 1;

	// ミノの左上の座標
	public double minoX; // 横
	public double minoY; // 縦

	// ミノを構成するパネル数
	public static final int PANEL_NUM = 4;

	// ミノを構成するパネル
	protected Panel[] panelArray = new Panel[PANEL_NUM];

	// ミノを構成するパネルの装飾
	protected String fileImage;

	// タイル画像のトリミング始点
	protected int tileX = 0;
	protected int tileY = 0;
	
	// パネルの表示位置を格納
	protected double panelPositionArray[][] = new double[PANEL_NUM][2];

	// ミノを構成するパネルオブジェクトを返す
	public Panel getPanel(int i) {
		return panelArray[i];
	}

	// ランダムに異なる形状のミノのオブジェクトを返す
	public static Mino getMino() {
		double rand = Math.random() * 10;
		Mino mino;
		if (rand <= 2) {
			mino = new Mino1();
		} else if ((rand >= 3) && (rand <= 4)) {
			mino = new Mino2();
		} else if ((rand >= 5) && (rand <= 6)) {
			mino = new Mino3();
		} else if ((rand >= 7) && (rand <= 8)) {
			mino = new Mino4();
		} else {
			mino = new Mino5();
		}
		return mino;
	}
	
	// ミノを削除する
	protected void clear(GraphicsContext canvas) {
		double x = 0;
		double y = 0;
		double w = 0;
		double h = 0;

		for (int i = 0; i < PANEL_NUM; i++) {
			x = this.minoX + (panelPositionArray[i][0] * Panel.panelW()) - Panel.panelW();
			y = this.minoY + (panelPositionArray[i][1] * Panel.panelH()) - Panel.panelH();
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

		for (int i = 0; i < PANEL_NUM; i++ ) {
			x = this.minoX + (panelPositionArray[i][0] * Panel.panelW()) - Panel.panelW();
			y = this.minoY + (panelPositionArray[i][1] * Panel.panelH()) - Panel.panelH();
			w = Panel.panelW();
			h = Panel.panelH();
			Image img = panelArray[i].getImage();
			canvas.drawImage(img,x, y, w, h);
		}
	}

	protected void turn() {
		// 継承先で実装
	}

	// 右に移動
	protected void moveRight(Field field) {
		
		double moveX = this.minoX + Panel.panelW();	
		Panel[][] panelArray = field.getPanelArray();
		
		// ミノを構成するパネル全てで衝突判定をおこなう
		for (int i = 0; i < PANEL_NUM; i++ ) {

			// パネルの左上の座標を取得
			double x, y = 0;
			x = moveX + (panelPositionArray[i][0] * Panel.panelW()) - Panel.panelW();
			y = this.minoY + (panelPositionArray[i][1] * Panel.panelH()) - Panel.panelH();

			// 座標に相当する配列インデックスを取得
			int col, row = 0;
			col = (int) (x / Panel.panelW());
			row = (int) (y / Panel.panelH());

			// 衝突したら移動しない
			// 右側に衝突
			if (col >= Field.COL()) {
				return;
			}
			
			// 左側に衝突
			if (col < 0) {
				return;
			}
		
			// パネルに衝突
			if (panelArray[col][row] != null) {
				return;
			}
		}
		this.minoX = moveX;
	}
	
	// 左に移動
	protected void moveLeft(Field field) {		
		double moveX = this.minoX - Panel.panelW();
		Panel[][] panelArray = field.getPanelArray();
		
		// ミノを構成するパネル全てで衝突判定をおこなう
		for (int i = 0; i < PANEL_NUM; i++ ) {

			// パネルの左上の座標を取得
			double x, y = 0;
			x = moveX + (panelPositionArray[i][0] * Panel.panelW()) - Panel.panelW();
			y = this.minoY + (panelPositionArray[i][1] * Panel.panelH()) - Panel.panelH();

			// 座標に相当する配列インデックスを取得
			int col, row = 0;
			col = (int) (x / Panel.panelW());
			row = (int) (y / Panel.panelH());

			// 衝突したら移動しない
			// 右側に衝突
			if (col >= Field.COL()) {
				return;
			}
			
			// 左側に衝突
			if (col < 0) {
				return;
			}
		
			// パネルに衝突
			if (panelArray[col][row] != null) {
				return;
			}
		}
		this.minoX = moveX;
	}
	
	protected void turnRight() {
		this.direction++;
		if (this.direction > 4) {
			this.direction = 1;
		}
		this.turn();
	}

	protected void turnLeft() {
		// 衝突後戻す用
		//int currentDirection = this.direction;
		
		this.direction--;
		if (this.direction < 1) {
			this.direction = 4;
		}
		
		// ミノを回転
		this.turn();
		
		// ここで衝突判定
		
		// 衝突していたら元に戻す
		//this.direction = currentDirection;
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

	// ミノを構成するパネルオブジェクトを作成して配列に格納
	protected void makePanel() {
		for (int i = 0; i < PANEL_NUM; i++) {
			panelArray[i] = new Panel(this.tileX, this.tileY);
		}
	}

	// ゲームオーバーの判定
	// ミノを構成するパネルの１つが天井に接触したらゲームオーバー
	protected boolean gameOver() {
		double y = 0;
		for (int i = 0; i < PANEL_NUM; i++ ) {
			// パネルの左上の座標を取得
			y = this.minoY + (panelPositionArray[i][1] * Panel.panelH()) - Panel.panelH();

			// ミノが天井に接触した
			if (y <= 0) {
				return true;
			}
		}
		return false;
	}

	// おじゃまミノと床との衝突判定
	protected boolean colision(Field field) {
		int col = 0;
		int row = 0;

		double x = 0;
		double y = 0;

		// おじゃまミノのパネル情報を取得
		Panel[][] panelArray = field.getPanelArray();

		for (int i = 0; i < PANEL_NUM; i++ ) {
			// パネルの左上の座標を取得
			x = this.minoX + (panelPositionArray[i][0] * Panel.panelW()) - Panel.panelW();
			y = this.minoY + (panelPositionArray[i][1] * Panel.panelH()) - Panel.panelH();

			// 床に衝突した
			if ((y + Panel.panelH()) >= (Field.ROW() * Panel.panelH())) {
				return true;
			}

			// パネルに衝突した
			// 衝突後に判定しているので１パネル分埋まる。。。
			// 横から衝突する場合はfalseを返さなければいけない。
			// →配列に含むまれることを条件にしない。座標で判定する？
			col = (int) (x / Panel.panelW());
			row = (int) (y / Panel.panelH());
			if (panelArray[col][row] != null) {
				return true;
			}
		}
		return false;
	}

	// コンストラクタ
	public Mino() {
		this.minoX = Panel.panelW() * 8;
		this.minoY = 0.0;
	}
}

//ミノ（｜）
class Mino1 extends Mino {

	public Mino1() {
		super();

		// ミノを構成するパネルの画像を指定
		this.fileImage = "panel.png";
		this.tileX = 64;
		this.makePanel();

		// ミノを構成するパネルの位置を指定
		// 0:パネルの横位置、1:パネルの縦位置
		panelPositionArray[0][0] = 1;
		panelPositionArray[0][1] = 1;
		panelPositionArray[1][0] = 1;
		panelPositionArray[1][1] = 2;
		panelPositionArray[2][0] = 1;
		panelPositionArray[2][1] = 3;
		panelPositionArray[3][0] = 1;
		panelPositionArray[3][1] = 4;
	}

	// ミノを回転
	public void turn() {
		switch(this.direction) {
		case 1: // 正面
			panelPositionArray[0][0] = 1;
			panelPositionArray[0][1] = 1;
			panelPositionArray[1][0] = 1;
			panelPositionArray[1][1] = 2;
			panelPositionArray[2][0] = 1;
			panelPositionArray[2][1] = 3;
			panelPositionArray[3][0] = 1;
			panelPositionArray[3][1] = 4;
			break;
		case 2: // 右向き
			panelPositionArray[0][0] = 1;
			panelPositionArray[0][1] = 1;
			panelPositionArray[1][0] = 2;
			panelPositionArray[1][1] = 1;
			panelPositionArray[2][0] = 3;
			panelPositionArray[2][1] = 1;
			panelPositionArray[3][0] = 4;
			panelPositionArray[3][1] = 1;
			break;
		case 3: // 上下逆
			panelPositionArray[0][0] = 4;
			panelPositionArray[0][1] = 1;
			panelPositionArray[1][0] = 4;
			panelPositionArray[1][1] = 2;
			panelPositionArray[2][0] = 4;
			panelPositionArray[2][1] = 3;
			panelPositionArray[3][0] = 4;
			panelPositionArray[3][1] = 4;
			break;
		case 4: // 左向き
			panelPositionArray[0][0] = 1;
			panelPositionArray[0][1] = 4;
			panelPositionArray[1][0] = 2;
			panelPositionArray[1][1] = 4;
			panelPositionArray[2][0] = 3;
			panelPositionArray[2][1] = 4;
			panelPositionArray[3][0] = 4;
			panelPositionArray[3][1] = 4;
			break;
		}
	}
}

// ミノ（凸）
class Mino2 extends Mino {
	public Mino2() {
		super();
		// ミノを構成するパネルの画像を指定
		this.fileImage = "panel.png";
		this.tileX = 192;
		this.makePanel();

		// ミノを構成するパネルの位置を指定
		// 0:パネルの横位置、1:パネルの縦位置
		panelPositionArray[0][0] = 1;
		panelPositionArray[0][1] = 4;
		panelPositionArray[1][0] = 2;
		panelPositionArray[1][1] = 3;
		panelPositionArray[2][0] = 2;
		panelPositionArray[2][1] = 4;
		panelPositionArray[3][0] = 3;
		panelPositionArray[3][1] = 4;
	}

	// ミノを回転
	public void turn() {
		switch(this.direction) {
		case 1: // 正面
			panelPositionArray[0][0] = 1;
			panelPositionArray[0][1] = 4;
			panelPositionArray[1][0] = 2;
			panelPositionArray[1][1] = 3;
			panelPositionArray[2][0] = 2;
			panelPositionArray[2][1] = 4;
			panelPositionArray[3][0] = 3;
			panelPositionArray[3][1] = 4;
			break;
		case 2: // 右向き
			panelPositionArray[0][0] = 1;
			panelPositionArray[0][1] = 1;
			panelPositionArray[1][0] = 1;
			panelPositionArray[1][1] = 2;
			panelPositionArray[2][0] = 1;
			panelPositionArray[2][1] = 3;
			panelPositionArray[3][0] = 2;
			panelPositionArray[3][1] = 2;
			break;
		case 3: // 上下逆
			panelPositionArray[0][0] = 2;
			panelPositionArray[0][1] = 1;
			panelPositionArray[1][0] = 3;
			panelPositionArray[1][1] = 1;
			panelPositionArray[2][0] = 4;
			panelPositionArray[2][1] = 1;
			panelPositionArray[3][0] = 3;
			panelPositionArray[3][1] = 2;
			break;
		case 4: // 左向き
			panelPositionArray[0][0] = 4;
			panelPositionArray[0][1] = 2;
			panelPositionArray[1][0] = 3;
			panelPositionArray[1][1] = 3;
			panelPositionArray[2][0] = 4;
			panelPositionArray[2][1] = 3;
			panelPositionArray[3][0] = 4;
			panelPositionArray[3][1] = 4;
			break;
		}
	}
}

//ミノ（■）
class Mino3 extends Mino {
	public Mino3() {
		super();
		// ミノを構成するパネルの画像を指定
		this.fileImage = "panel.png";
		this.tileX = 320;
		this.makePanel();

		// ミノを構成するパネルの位置を指定
		// 0:パネルの横位置、1:パネルの縦位置
		panelPositionArray[0][0] = 1;
		panelPositionArray[0][1] = 3;
		panelPositionArray[1][0] = 1;
		panelPositionArray[1][1] = 4;
		panelPositionArray[2][0] = 2;
		panelPositionArray[2][1] = 3;
		panelPositionArray[3][0] = 2;
		panelPositionArray[3][1] = 4;
	}

	public void turn() {
		// 回転しても形はかわらない
	}
}

//ミノ（カギ１）
class Mino4 extends Mino {
	public Mino4() {
		super();
		// ミノを構成するパネルの画像を指定
		this.fileImage = "background.png";
		this.tileX = 384;
		this.makePanel();

		// ミノを構成するパネルの位置を指定
		// 0:パネルの横位置、1:パネルの縦位置
		panelPositionArray[0][0] = 1;
		panelPositionArray[0][1] = 2;
		panelPositionArray[1][0] = 1;
		panelPositionArray[1][1] = 3;
		panelPositionArray[2][0] = 2;
		panelPositionArray[2][1] = 3;
		panelPositionArray[3][0] = 2;
		panelPositionArray[3][1] = 4;
	}

	// ミノを回転
	public void turn() {
		switch(this.direction) {
		case 1: // 正面
			panelPositionArray[0][0] = 1;
			panelPositionArray[0][1] = 2;
			panelPositionArray[1][0] = 1;
			panelPositionArray[1][1] = 3;
			panelPositionArray[2][0] = 2;
			panelPositionArray[2][1] = 3;
			panelPositionArray[3][0] = 2;
			panelPositionArray[3][1] = 4;
			break;
		case 2: // 右向き
			panelPositionArray[0][0] = 3;
			panelPositionArray[0][1] = 1;
			panelPositionArray[1][0] = 4;
			panelPositionArray[1][1] = 1;
			panelPositionArray[2][0] = 2;
			panelPositionArray[2][1] = 2;
			panelPositionArray[3][0] = 3;
			panelPositionArray[3][1] = 2;
			break;
		case 3: // 上下逆
			panelPositionArray[0][0] = 3;
			panelPositionArray[0][1] = 2;
			panelPositionArray[1][0] = 3;
			panelPositionArray[1][1] = 3;
			panelPositionArray[2][0] = 4;
			panelPositionArray[2][1] = 3;
			panelPositionArray[3][0] = 4;
			panelPositionArray[3][1] = 4;
			break;
		case 4: // 左向き
			panelPositionArray[0][0] = 1;
			panelPositionArray[0][1] = 4;
			panelPositionArray[1][0] = 2;
			panelPositionArray[1][1] = 3;
			panelPositionArray[2][0] = 2;
			panelPositionArray[2][1] = 4;
			panelPositionArray[3][0] = 3;
			panelPositionArray[3][1] = 3;
			break;
		}
	}
}

//ミノ（カギ２）
class Mino5 extends Mino {
	public Mino5() {
		super();
		// ミノを構成するパネルの画像を指定
		this.tileX = 448;
		this.fileImage = "background.png";
		this.makePanel();

		// ミノを構成するパネルの位置を指定
		// 0:パネルの横位置、1:パネルの縦位置
		panelPositionArray[0][0] = 1;
		panelPositionArray[0][1] = 3;
		panelPositionArray[1][0] = 1;
		panelPositionArray[1][1] = 4;
		panelPositionArray[2][0] = 2;
		panelPositionArray[2][1] = 2;
		panelPositionArray[3][0] = 2;
		panelPositionArray[3][1] = 3;
	}

	// ミノを回転
	public void turn() {
		switch(this.direction) {
		case 1: // 正面
			panelPositionArray[0][0] = 1;
			panelPositionArray[0][1] = 3;
			panelPositionArray[1][0] = 1;
			panelPositionArray[1][1] = 4;
			panelPositionArray[2][0] = 2;
			panelPositionArray[2][1] = 2;
			panelPositionArray[3][0] = 2;
			panelPositionArray[3][1] = 3;
			break;
		case 2: // 右向き
			panelPositionArray[0][0] = 2;
			panelPositionArray[0][1] = 1;
			panelPositionArray[1][0] = 3;
			panelPositionArray[1][1] = 1;
			panelPositionArray[2][0] = 3;
			panelPositionArray[2][1] = 2;
			panelPositionArray[3][0] = 4;
			panelPositionArray[3][1] = 2;
			break;
		case 3: // 上下逆
			panelPositionArray[0][0] = 4;
			panelPositionArray[0][1] = 2;
			panelPositionArray[1][0] = 3;
			panelPositionArray[1][1] = 3;
			panelPositionArray[2][0] = 4;
			panelPositionArray[2][1] = 3;
			panelPositionArray[3][0] = 3;
			panelPositionArray[3][1] = 4;
			break;
		case 4: // 左向き
			panelPositionArray[0][0] = 1;
			panelPositionArray[0][1] = 3;
			panelPositionArray[1][0] = 2;
			panelPositionArray[1][1] = 3;
			panelPositionArray[2][0] = 2;
			panelPositionArray[2][1] = 4;
			panelPositionArray[3][0] = 3;
			panelPositionArray[3][1] = 4;
			break;
		}
	}
}