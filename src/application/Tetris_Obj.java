package application;

import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.canvas.GraphicsContext;


// ブロッククラス
abstract class Block{
	protected int[][] block = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};// ブロックの形状を配列で保持（横、縦）
	protected int blockStatus = 0; // ブロックの状態（回転）

	// ブロックの大きさ
	protected static final int width = 10;
	protected static final int height = 10;

	// ブロックの座標
	protected double x;
	protected double y;

	public Block() {
		this.x = 0;
		this.y = 0;
	}

	// ブロックを左回転
	protected void turnLeft() {
		// 継承先で定義
	}
	// ブロックを右回転
	protected void turnRight() {
		// 継承先で定義
	}

	protected void show() {
		GraphicsContext canvas = GameLib.getGC();

		for (int i = 0; i <= 4; i++) {
			for(int l = 0; l <= 4; l++) {
				if (this.block[i][l] == 1) {
					// ブロックを表示する
					double x = this.x + (i * this.width);
					double y = this.y + (l * this.height);
					canvas.fillRect(x, y, this.width, this.height);
				}
			}
		}
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}
}

// ブロック（｜）
class Block1 extends Block {
	public Block1() {
		super();
		this.block[0][1] = 1;
		this.block[0][2] = 1;
		this.block[0][3] = 1;
		this.block[0][4] = 1;
	}
}


//ブロック（■）
class Block2 extends Block {
	public Block2() {
		super();
		this.block[0][2] = 1;
		this.block[0][3] = 1;
		this.block[1][2] = 1;
		this.block[1][3] = 1;
	}
}



public class Tetris_Obj {

	private String message = "ゲームタイトル";

	// 0:初期表示、1:ブロック初期化、2:ブロック移動中、3:ポーズ	
	private Integer gameStatus = 1;
	private Block block;
	
	// ブロック情報
	private int blockWidth = 10;
	private int blockHeight = 10;

	// 画面のマス数
	private final int blockRow = 100;
	private final int blockCol = 50;
	//private int tables[][] = new int[Tetris_Obj.blockRow][Tetris_Obj.blockCol];

	private double blockX = 60.0;
	private double blockY = 50.0;


	// 画面の初期化
	private void showInit() {
		GraphicsContext canvas = GameLib.getGC();
		canvas.clearRect(0, 0, GameLib.width(), GameLib.height());
	}

	// メッセージの表示
	private void showMessage() {
		GraphicsContext canvas = GameLib.getGC();
		canvas.fillText(this.message, 60, 50 );
	}

	// 次に出てくるブロック欄の表示
	private void showNextBlock() {

	}


	// メイン画面の表示
	private void showBlock() {

	}

	// 背景の表示
	private void showBase() {

	}

	// 魔法エフェクト（メラ）の表示
	private void showMera() {

	}

	// 魔法エフェクト（イオ）表示
	private void showIo() {

	}


	//ブロックオブジェクトの定義
	private void makeBlock() {
		this.block = new Block1();
	}
	//Block block1 = new Block1

	// このメソッドが1秒間に60回ぐらい呼ばれるので
	// テトリスの内部処理をここに書く
	// タイマーの中でオブジェクトを生成するのは無理かも
	// →画面をマスで区切って色を塗った方がよいかもしれない。
	public void update() {

		// 領域の初期化
		// 背景等固定の箇所は？
		

		// ENTER押下
		/*
		if (GameLib.isKeyOn("ENTER")) {
			if (this.gameMode == 0) {
				this.gameMode = 1;
				this.message = "ゲーム中。。。";
			} else {
				this.gameMode = 0;
				this.message = "ゲーム中にENTERボタンが押下された";
			}
		}*/

		switch(this.gameStatus) {
			case 0:
			// 画面の背景表示、初期化処理
				this.showInit();
				this.gameStatus = 1;
				break;
			case 1:
			// ブロック初期化
				this.makeBlock();
				this.showBlock();
				this.gameStatus = 2;
				break;
			case 2:
			// ブロック落下中
				// A押下
				if (GameLib.isKeyOn("A")) {
					// 左へ移動
					this.blockX = this.blockX - 1;
				}

				// D押下
				if (GameLib.isKeyOn("D")) {
					// 右へ移動
					this.blockX = this.blockX + 1;
				}

				// P押下
				if (GameLib.isKeyOn("P")) {

				}

				// O押下
				if (GameLib.isKeyOn("O")) {

				}
				this.blockY = this.blockY + 0.1;
				this.showBlock();
			break;
		}

		// デフォルト処理
		// 　背景の表示
		// 　キャラクタの表示
		// 　ゲームタイトルの表示




		this.showMessage();
	}
}

