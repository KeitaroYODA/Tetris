package application;

import java.io.File;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
public class Tetris_Obj {

	private String message = "ゲームタイトル";
	private long execTime = System.nanoTime();

	// サウンド
	private final AudioClip mediaTurn = new AudioClip(new File("turn.mp3").toURI().toString()); // ブロック回転
	private final AudioClip mediaColision = new AudioClip(new File("colision.mp3").toURI().toString()); // ブロック衝突

	// 0:初期表示、1:ブロック初期化、2:ブロック移動中、3:ポーズ
	private Integer gameStatus = 0;

	// ミノ
	private Mino mino; // 落下中のミノ
	private Mino nextMino; // 次に表示されるミノ
	private OjamaMino ojamaMino; // 落下済みのミノ

	// 画面表示
	// テトリス画面
	/*
	private static final double mainX = 0;
	private static final double mainY = 0;
	private static final double mainW = Panel.panelW() * 16;
	private static final double mainH = Panel.panelH() * 18;
	*/
	// 得点画面
	private static final double countX = Panel.panelW() * 17;
	private static final double countY = Panel.panelW() * 1;
	private static final double countW = Panel.panelW() * 12;
	private static final double countH = Panel.panelH() * 3;
	// 次のミノ画面
	private final double minoX = Panel.panelW() * 17;
	private final double minoY = Panel.panelW() * 5;
	private final double minoW = Panel.panelW() * 5;
	private final double minoH = Panel.panelH() * 6;

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

	// 背景の表示
	private void showBackground() {
		// 背景の１パネルあたりの表示サイズ
		int panelW = 20;

		GraphicsContext canvas = GameLib.getGC();
		Image img = new Image(new File("tile.png").toURI().toString());
		WritableImage resizedImage = new WritableImage(img.getPixelReader(),64, 192, (int) (img.getWidth() / 8), (int) (img.getHeight() / 8));

		int col = GameLib.width() / panelW;
		int row = GameLib.height() / panelW;

		int x,y;
		for (int i = 0; i < col; i++) {
			for (int l = 0; l < row; l++) {
				x = i * panelW;
				y = l * panelW;
				canvas.drawImage(resizedImage,x, y, panelW, panelW);
			}
		}
	}

	// 得点表示
	private void showCount() {
		GraphicsContext canvas = GameLib.getGC();
		canvas.setFill(Color.BLACK);
		canvas.fillRect(countX, countY, countW, countH);
		canvas.setFill(Color.WHITE);
		canvas.fillText("ここに得点が表示される", countX + Panel.panelW(), countY + Panel.panelW());
	}

	// 次のミノ表示
	private void showNextMino() {
		GraphicsContext canvas = GameLib.getGC();
		canvas.setFill(Color.BLACK);
		canvas.fillRect(minoX, minoY, minoW, minoH);

		nextMino = this.getMino();
		nextMino.setMinoX(minoX + 10);
		nextMino.setMinoY(minoY + 10);
		nextMino.show(canvas);
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
			// 初回表示時またはゲームオーバ後の再開時に実行される
			this.gameStatus = 1;
			canvas.clearRect(0, 0, GameLib.width(), GameLib.height());
			this.showBackground();

			// ミノ作成
			mino = this.getMino();
			// 背景作成
			ojamaMino = new OjamaMino();

			// 次のミノ画面を表示
			this.showNextMino();
			this.showCount();
		}
		// 次にミノをミノにセットして最上段に表示
		else if (this.gameStatus == 3) {
			// おじゃまミノの行が揃っていれば１行削除


			// 参照を渡しているのでだめ
			//this.mino = (Mino)nextMino.clone();
			mino = this.getMino();
			// 次のミノ画面を表示
			this.showNextMino();
			this.gameStatus = 1;

		}
		else if (this.gameStatus == 4) {
			canvas.setFill(Color.WHITE);
			canvas.fillText("ゲームオーバーしました。。。PRESS ENTER ", 60, 50 );

			// ENTER押下
			if (GameLib.isKeyOn("ENTER")) {
				this.gameStatus = 0;
			}
			return;

		} else if (this.gameStatus == 5) {

			// E押下
			if (GameLib.isKeyOn("E")) {
				this.gameStatus = 1;
			}

			canvas.setFill(Color.WHITE);
			canvas.fillText("PAUSE。。。PRESS E ", 60, 50 );
			return;
		}

		// 以前のミノを削除してから背景を表示
		mino.clear(canvas);

		// L押下
		if (GameLib.isKeyOn("L")) {
			mediaTurn.play();
			mino.turnLeft();
		}

		// ミノ落下
		double y = mino.getMinoY() + (Panel.panelH() / 2);
		mino.setMinoY(y);

		// ミノの衝突判定
		if (mino.colision(ojamaMino)) {

			// ゲームオーバの判定
			if (mino.gameOver()) {
				// ゲームオーバー
				this.gameStatus = 4;
				return;
			}

			// おじゃまミノが揃っているかチェック
			if (ojamaMino.checkOjamaMinoRow()) {
				canvas.setFill(Color.WHITE);
				canvas.fillText("列がそろいました。。。", 60, 50 );
			}

			// 接地したらおじゃまミノ化する
			ojamaMino.addOjamaMino(mino);
			ojamaMino.show(canvas);
			// 衝突音を鳴らす
			mediaColision.play();
			this.gameStatus = 3;
			return;
		}

		ojamaMino.show(canvas);

		// ミノ操作
		double x = mino.getMinoX();
		// A押下
		if (GameLib.isKeyOn("A")) {
			mino.moveLeft(ojamaMino);
		}
		// D押下
		if (GameLib.isKeyOn("D")) {
			mino.moveRight(ojamaMino);
		}

		mino.show(canvas);

		// P押下
		if (GameLib.isKeyOn("P")) {
			this.gameStatus = 5;
		}


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

