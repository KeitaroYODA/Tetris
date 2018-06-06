package application;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

// ミノクラス
abstract class Mino implements Cloneable{

	// ミノの向き
	// 1:正面、2:右向き、3:上下逆、4:左向き
	protected int direction = 1;

	// ミノの左上の座標
	public double minoX; // 横
	public double minoY; // 縦

	// ミノを構成するパネル数
	public static final int PANEL_NUM = 4;

	// ミノを構成するパネル
	protected Panel[] panelArray = new Panel[PANEL_NUM];

	// ミノに表示するタイル画像のトリミング始点
	protected String imgFile = "tile.png";
	protected int tileX = 0;
	protected int tileY = 0;

	// パネルの表示位置を格納
	// 0:横位置、1:縦位置
	protected double panelPositionArray[][] = new double[PANEL_NUM][2];

	public Mino() {
		this.init();
	}

	public void init() {
		this.minoX = Panel.panelW() * 8;
		this.minoY = 0.0;
	}

	// ランダムに異なる形状のミノのオブジェクトを返す
	public static Mino getMino() {

		Mino mino;
		Random randI = new Random();
		int num = randI.nextInt(10);

		if (num < 2) {
			mino = new MinoBar();
		} else if ((num >= 2) && (num <= 3)) {
			mino = new MinoTotu();
		} else if ((num >= 4) && (num <= 5)) {
			mino = new MinoSquare();
		} else if ((num >= 6) && (num <= 7)) {
			mino = new MinoKagi1();
		} else {
			mino = new MinoKagi2();
		}
//mino = new MinoBar();

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

	// ミノ落下
	protected void moveDown(Field field) {
		double moveY = Panel.panelH() / 2;

		// ミノがブロックに接するときだけ判定する
		if ((this.minoY + moveY) % Panel.panelH() != 0) {
			this.minoY = this.minoY + moveY;
			return;
		}

		Panel[][] panelArray = field.getPanelArray();

		// ミノを構成するパネル全てで衝突判定をおこなう
		for (int i = 0; i < PANEL_NUM; i++ ) {

			// パネルの左下の座標を取得
			double x, y = 0;
			x = this.minoX + (panelPositionArray[i][0] * Panel.panelW()) - Panel.panelW();
			y = this.minoY + (panelPositionArray[i][1] * Panel.panelH());

			// 座標に相当する配列インデックスを取得
			int col, row = 0;
			col = (int) (x / Panel.panelW());
			row = (int) (y / Panel.panelH());

			// パネルに衝突
			if (panelArray[row][col] != null) {
				return;
			}
		}

		this.minoY = this.minoY + moveY;
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
			// パネルに衝突
			if (panelArray[row][col] != null) {
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
			// 左側に衝突
			if (col < 0) {
				return;
			}
			// パネルに衝突
			if (panelArray[row][col] != null) {
				return;
			}
		}
		this.minoX = moveX;
	}

	protected void turn() {
		// 継承先で実装
	}


	// ミノを回転
	protected void turnLeft(Field field) {

		int direction = this.direction;
		this.direction--;
		if (this.direction < 1) {
			this.direction = 4;
		}
		this.turn();

		// ミノを構成するパネル全てで衝突判定をおこなう
		for (int i = 0; i < PANEL_NUM; i++ ) {

			// パネルの左上の座標を取得
			double x, y = 0;
			x = this.minoX + (panelPositionArray[i][0] * Panel.panelW()) - Panel.panelW();
			y = this.minoY + (panelPositionArray[i][1] * Panel.panelH());

			// 座標に相当する配列インデックスを取得
			int col, row = 0;
			col = (int) (x / Panel.panelW());
			row = (int) (y / Panel.panelH());

			// 衝突したら回転しない
			// 右側に衝突
			if (col >= Field.COL()) {
				this.direction = direction;
				this.turn();
				return;
			}
			// 左側に衝突
			if (col < 0) {
				this.direction = direction;
				this.turn();
				return;
			}
			// パネルに衝突
			Panel[][] panelArray = field.getPanelArray();
			if (panelArray[row][col] != null) {
				this.direction = direction;
				this.turn();
				return;
			}
		}
	}

	// ゲームオーバーの判定
	// ミノを構成するパネルの１つが天井に接触したらゲームオーバー
	protected boolean isGameOver() {
		double y = 0;
		for (int i = 0; i < PANEL_NUM; i++) {
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

		if (this.minoY % Panel.panelH() != 0) {
			return false;
		}

		int col, row = 0;
		double x, y = 0;

		// おじゃまミノのパネル情報を取得
		Panel[][] panelArray = field.getPanelArray();

		for (int i = 0; i < PANEL_NUM; i++ ) {
			// パネルの左下の座標を取得
			x = this.minoX + (panelPositionArray[i][0] * Panel.panelW()) - Panel.panelW();
			y = this.minoY + (panelPositionArray[i][1] * Panel.panelH());

			// 床に衝突した
			if (y >= (Field.ROW() * Panel.panelH())) {
				return true;
			}
			// パネルに衝突した
			col = (int) (x / Panel.panelW());
			row = (int) (y / Panel.panelH());
			if (panelArray[row][col] != null) {
				return true;
			}
		}
		return false;
	}

	// ミノがおじゃまミノまたは画面下に接触した際に
	// おじゃまミノ化する
	public void setPanel2Field(Field field) {
		// ミノの左上の座標を取得

		Panel[][] panelArray = field.getPanelArray();
		
		for (int i = 0; i < Mino.PANEL_NUM; i++) {
			double panelX = (this.panelPositionArray[i][0] * Panel.panelW()) - Panel.panelW();
			double panelY = (this.panelPositionArray[i][1] * Panel.panelH()) - Panel.panelH();

			// パネルの座標の位置から配列のインデックスを取得してパネルオブジェクトを格納
			int col = (int) ((int)(minoX + panelX) / Panel.panelW());
			int row = (int) ((int)(minoY + panelY) / Panel.panelH());
			panelArray[row][col] = this.getPanel(i);
		}
		
		field.setPanelArray(panelArray);
	}
	
	// ミノを構成するパネルオブジェクトを作成して配列に格納
	protected void makePanel() {
		for (int i = 0; i < PANEL_NUM; i++) {
			panelArray[i] = new Panel(this.imgFile, this.tileX, this.tileY);
		}
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

	// ミノを構成するパネルオブジェクトを返す
	public Panel getPanel(int i) {
		return panelArray[i];
	}
}