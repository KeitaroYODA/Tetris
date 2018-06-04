package application;

import java.io.File;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Tetris_Obj {

	private String message = "ゲームタイトル";
	private long execTime = System.nanoTime();

	// 0:初期表示、1:ブロック初期化、2:ブロック移動中、3:ポーズ
	private Integer gameStatus = 0;

	// ミノ
	private Mino mino;
	private Mino nextMino;
	private OjamaMino ojamaMino;

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

	private void createOjamaMino() {

	}

	// このメソッドが1秒間に60回ぐらい呼ばれるので
	// テトリスの内部処理をここに書く
	public void update() {

		// 不要なフレームを間引く
		long now = System.nanoTime();
		if (now - execTime < 100000000) {
			return;
		}
		this.execTime = now;

		GraphicsContext canvas = GameLib.getGC();

		if (this.gameStatus == 0) {
			this.gameStatus = 1;
			canvas.clearRect(0, 0, GameLib.width(), GameLib.height());
			Image img =	new Image(new File("background.png").toURI().toString());
			canvas.drawImage(img,0, 0, GameLib.width(), GameLib.height());

			// ミノを表示
			mino = this.getMino();
			
			// おじゃまミノ初期化
			int col = (int) (GameLib.width() / Panel.panelW());
			int row = (int) (GameLib.height() / Panel.panelH());
			ojamaMino = new OjamaMino(col, row);
			
			// 次のミノ画面を表示
			this.showNextMino();
		}
		
		// 以前のミノを削除してから背景を表示
		mino.clear(canvas);
		this.showMain();

		ojamaMino.addOjamaMino(mino);
		ojamaMino.show(canvas);
		
		// L押下
		if (GameLib.isKeyOn("L")) {
			mino.turnLeft();
		}

		// ミノ落下
		double y = mino.getMinoY() + 5;
		if (y > GameLib.height() - 50) {
			y = GameLib.height() - 50;
		}
		mino.setMinoY(y);

		double x = mino.getMinoX();
		// A押下
		if (GameLib.isKeyOn("A")) {
			// 左へ移動
			x--;
			if (x <= 0) {
				x = 0;
			}
			mino.setMinoX(x);
		}

		// D押下
		if (GameLib.isKeyOn("D")) {
			// 右へ移動
			x++;
			if (x >= GameLib.width()) {
				x = GameLib.width();
			}
			mino.setMinoX(x);
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

