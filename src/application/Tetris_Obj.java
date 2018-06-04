package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Tetris_Obj {

	private String message = "ゲームタイトル";
	private long execTime = System.nanoTime();

	// 0:初期表示、1:ブロック初期化、2:ブロック移動中、3:ポーズ
	private Integer gameStatus = 0;

	// ミノ
	private Mino mino;
	private Mino nextMino;

	// 画面表示
	// テトリス画面
	private final double mainX = 10;
	private final double mainY = 10;
	private final double mainW = 300;
	private final double mainH = 400;

	// 次のミノ画面
	private final double minoX = 360;
	private final double minoY = 10;
	private final double minoW = 100;
	private final double minoH = 150;

	// メッセージの表示
	private void showMessage() {
		GraphicsContext canvas = GameLib.getGC();
		canvas.fillText(this.message, 60, 50 );
	}

	// ランダムに異なる形のミノを返す
	private Mino getMino() {
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

	// メイン画面の表示
	private void showMain() {
		GraphicsContext canvas = GameLib.getGC();
		canvas.setFill(Color.BLACK);
		canvas.fillRect(mainX, mainY, mainW, mainH);
	}

	private void showNextMino() {
		GraphicsContext canvas = GameLib.getGC();
		canvas.setFill(Color.BLACK);
		canvas.fillRect(minoX, minoY, minoW, minoH);

		// 背景を表示
		nextMino = this.getMino();
		nextMino.setMinoX(minoX + 10);
		nextMino.setMinoY(minoY + 10);
		nextMino.show(canvas);
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

	//Block block1 = new Block1

	// このメソッドが1秒間に60回ぐらい呼ばれるので
	// テトリスの内部処理をここに書く
	// タイマーの中でオブジェクトを生成するのは無理かも
	// →画面をマスで区切って色を塗った方がよいかもしれない。
	public void update() {

		// 不要なフレームを間引く
		long now = System.nanoTime();
		if (now - execTime < 100000000) {
			return;
		}
		this.execTime = now;

		GraphicsContext canvas = GameLib.getGC();
		//canvas.clearRect(0, 0, GameLib.width(), GameLib.height());

		if (this.gameStatus == 0) {
			this.gameStatus = 1;
			canvas.clearRect(0, 0, GameLib.width(), GameLib.height());
			mino = this.getMino();

			// 次のミノ画面を表示
			this.showNextMino();
		}


		// 以前のミノを削除してから背景を表示
		mino.clear(canvas);
		this.showMain();

		// L押下
		if (GameLib.isKeyOn("L")) {
			mino.turnLeft();
		}

		/*
		// ミノの接触判定
		mino.setMinoY(1);
		if (mino.getMinoX() > GameLib.width() - 40) {

		} else {

		}
		*/

		mino.show(canvas);


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

		//GraphicsContext canvas = GameLib.getGC();
		//canvas.fillRect(this.block.x(), this.block.y(), this.block.width(), this.block.height());

		/*
		switch(this.gameStatus) {
			case 0:
			// 画面の背景表示、初期化処理
				this.showInit();
				this.gameStatus = 1;
				break;
			case 1:
			// ブロック初期化
				this.createBlock();
				//this.showBlock();
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
				//this.showBlock();
			break;
		}
		// デフォルト処理
		// 　背景の表示
		// 　キャラクタの表示
		// 　ゲームタイトルの表示
*/



		this.showMessage();
	}
}

