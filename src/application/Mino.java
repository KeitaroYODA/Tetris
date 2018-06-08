package application;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

// ミノクラス
abstract class Mino implements Cloneable{

	// ミノを構成するパネル数
	public static final int PANEL_NUM = 4;

	// ミノに表示するタイル画像のトリミング始点
	protected String imgFile = "tile.png";
	protected int tileX = 0;
	protected int tileY = 0;

	// ミノの左上の座標
	public double minoX; // 横
	public double minoY; // 縦

	// ミノの向き
	// 1:正面、2:右向き、3:上下逆、4:左向き
	protected int direction = 1;

	// ミノを構成するパネル
	protected Panel[] panelArray = new Panel[PANEL_NUM];

	// 左上を1としたパネルの表示位置(1～4)
	// 0:横位置、1:縦位置
	protected double panelPositionArray[][] = new double[PANEL_NUM][2];

	protected double[] speedByLevel = new double[5];

	public Mino() {
		this.init();

		// 20の約数である1,2,4,5,10を指定
		// 5段階以上のレベルを設定できないので要修正
		this.speedByLevel[0] = 1;
		this.speedByLevel[1] = 2;
		this.speedByLevel[2] = 4;
		this.speedByLevel[3] = 5;
		this.speedByLevel[4] = 10;
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
		return mino;
	}

	// ミノ落下
	protected void moveDown(Field field, int level) {

		double moveY = this.minoY + this.getSpeedByLevel(level);

		// ミノがパネルに接するときだけ判定する
		if (moveY % Panel.panelH() == 0) {

			// ミノを構成するパネル全てで衝突判定をおこなう
			double x, y = 0;
			for (int i = 0; i < PANEL_NUM; i++) {

				// パネルの左下の座標を取得
				x = this.minoX + (panelPositionArray[i][0] * Panel.panelW()) - Panel.panelW();
				y = this.minoY + (panelPositionArray[i][1] * Panel.panelH());

				// どこかに衝突していれは落下しない
				if (field.colision(x, y)) {
					return;
				}
			}
		}
		this.minoY = moveY;
	}

	protected  double getSpeedByLevel(int level) {
		double speed = this.speedByLevel[0];

		if (level <= 5) {
			speed = this.speedByLevel[level - 1];
		}

		return speed;
	}

	// 右に移動
	protected void moveRight(Field field) {

		double moveX = this.minoX + Panel.panelW();

		// ミノを構成するパネル全てで衝突判定をおこなう
		double x, y = 0;
		for (int i = 0; i < PANEL_NUM; i++ ) {

			// パネルの左上の座標を取得
			x = moveX + (panelPositionArray[i][0] * Panel.panelW()) - Panel.panelW();
			y = this.minoY + (panelPositionArray[i][1] * Panel.panelH()) - Panel.panelH();

			if (field.colision(x, y)) {
				return;
			}
		}

		this.minoX = moveX;
	}

	// 左に移動
	protected void moveLeft(Field field) {
		// 移動後のX座標
		double moveX = this.minoX - Panel.panelW();

		// ミノを構成するパネル全てで衝突判定をおこなう
		double x, y = 0;
		for (int i = 0; i < PANEL_NUM; i++ ) {

			// パネルの左上の座標を取得
			x = moveX + (panelPositionArray[i][0] * Panel.panelW()) - Panel.panelW();
			y = this.minoY + (panelPositionArray[i][1] * Panel.panelH()) - Panel.panelH();

			// 衝突していたら移動しない
			if (field.colision(x, y)) {
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
		double x, y = 0;
		for (int i = 0; i < PANEL_NUM; i++ ) {

			// パネルの左上の座標を取得
			x = this.minoX + (panelPositionArray[i][0] * Panel.panelW()) - Panel.panelW();
			y = this.minoY + (panelPositionArray[i][1] * Panel.panelH());

			// 衝突していたら回転しない
			if (field.colision(x, y)) {
				this.direction = direction;
				this.turn();
				return;
			}
		}
	}

	// ミノとフィールドとの衝突判定
	protected boolean colision(Field field) {

		if (this.minoY % Panel.panelH() != 0) {
			return false;
		}

		double x, y = 0;
		for (int i = 0; i < PANEL_NUM; i++ ) {
			// パネルの左下の座標を取得
			x = this.minoX + (panelPositionArray[i][0] * Panel.panelW()) - Panel.panelW();
			y = this.minoY + (panelPositionArray[i][1] * Panel.panelH());

			if (field.colision(x, y)) {
				return true;
			}
		}
		return false;
	}

	// ミノがブロックまたは画面下に接触した際にブロック化する
	public void setPanel2Field(Field field) {

		double x, y = 0;
		for (int i = 0; i < Mino.PANEL_NUM; i++) {
			// パネルの左上の座標を取得
			x = this.minoX + (this.panelPositionArray[i][0] * Panel.panelW()) - Panel.panelW();
			y = this.minoY + (this.panelPositionArray[i][1] * Panel.panelH()) - Panel.panelH();
			field.setPanel(x, y, this.getPanel(i));
		}
	}

	// ミノを表示する
	protected void show(GraphicsContext canvas) {
		double x, y, w, h = 0;

		for (int i = 0; i < PANEL_NUM; i++ ) {
			// パネルの左上の座標を取得
			x = this.minoX + (panelPositionArray[i][0] * Panel.panelW()) - Panel.panelW() + Field.fieldX();
			y = this.minoY + (panelPositionArray[i][1] * Panel.panelH()) - Panel.panelH() + Field.fieldY();
			w = Panel.panelW();
			h = Panel.panelH();
			Image img = panelArray[i].getImage();
			canvas.drawImage(img,x, y, w, h);
		}
	}

	// ミノを削除する
	protected void clear(GraphicsContext canvas) {
		double x, y, w, h = 0;

		for (int i = 0; i < PANEL_NUM; i++) {
			// パネルの左上の座標を取得
			x = this.minoX + (panelPositionArray[i][0] * Panel.panelW()) - Panel.panelW() + Field.fieldX();
			y = this.minoY + (panelPositionArray[i][1] * Panel.panelH()) - Panel.panelH() + Field.fieldY();
			w = Panel.panelW();
			h = Panel.panelH();
			canvas.clearRect(x, y, w, h);
		}
	}

	// ミノを構成するパネルオブジェクトを作成して配列に格納
	protected void initPanel() {
		for (int i = 0; i < PANEL_NUM; i++) {
			panelArray[i] = new Panel(this.imgFile, this.tileX, this.tileY);
		}
	}

	public Panel getPanel(int i) {
		return panelArray[i];
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
}