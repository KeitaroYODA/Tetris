package application;

import java.io.File;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
public class Tetris_Obj {

	// フレーム間引き
	private long execTime = System.nanoTime();
	private long nanoTime = 50000000;

	// サウンド
	private final AudioClip mediaTurn = new AudioClip(new File("turn.mp3").toURI().toString()); // ブロック回転
	private final AudioClip mediaColision = new AudioClip(new File("colision.mp3").toURI().toString()); // ブロック衝突
	private final AudioClip mediaRemove = new AudioClip(new File("remove.mp3").toURI().toString()); // ブロック消滅

	// 0:初期表示、1:ブロック初期化、2:ブロック移動中、3:ポーズ
	private Integer gameStatus = 0;

	// ゲーム情報
	private int score = 0; // スコア
	private int gameLevel = 1; // レベル
	private String message = "";

	private Mino mino; // 落下中のミノ
	private Mino nextMino; // 次に表示されるミノ
	private Field field; // パネル表示領域

	// 画面レイアウト
	// 得点表示
	private static final double scoreX = Panel.panelW() * 18;
	private static final double scoreY = Panel.panelW() * 1;
	private static final double scoreW = Panel.panelW() * 13;
	private static final double scoreH = Panel.panelH() * 3;
	// 次のミノ表示
	private final double nextX = Panel.panelW() * 18;
	private final double nextY = Panel.panelW() * 5;
	private final double nextW = Panel.panelW() * 6;
	private final double nextH = Panel.panelH() * 6;
	// レベル表示
	private final double levelX = Panel.panelW() * 25;
	private final double levelY = Panel.panelW() * 5;
	private final double levelW = Panel.panelW() * 6;
	private final double levelH = Panel.panelH() * 2;
	// 魔法ストック表示
	private final double magicX = Panel.panelW() * 25;
	private final double magicY = Panel.panelW() * 8;
	private final double magicW = Panel.panelW() * 6;
	private final double magicH = Panel.panelH() * 3;

	// このメソッドが1秒間に60回ぐらい呼ばれるので
	// テトリスの内部処理をここに書く
	public void update() {

		// 不要なフレームを間引く
		long now = System.nanoTime();
		if (now - execTime < nanoTime) {return;}
		this.execTime = now;

		GraphicsContext canvas = GameLib.getGC();

		switch(this.gameStatus) {
		case 0: // 画面の初期化
			this.message = "TETRIS SMILE";
			this.field = new Field();
			this.mino = Mino.getMino();
			this.showBackground();
			this.showNextMino();
			this.gameStatus = 1;
			break;

		case 1: // ミノ落下中。。。
			this.message = "";
			this.mino.clear(canvas);
//this.gameLevel = 5;
			// キー操作
			if (GameLib.isKeyOn("P")) {
				// ポーズ
				this.gameStatus = 5;
				return;
			}
			boolean isKeyOn = false;
			if (GameLib.isKeyOn("A")) {
				// 左へ移動
				isKeyOn = true;
				this.mino.moveLeft(field);
			}
			if (GameLib.isKeyOn("D")) {
				// 右へ移動
				isKeyOn = true;
				this.mino.moveRight(field);
			}
			if (GameLib.isKeyOn("L")) {
				// 左へ回転
				isKeyOn = true;
				this.mino.turnLeft(field);
				mediaTurn.play();
			}

			// 左右への移動中または回転中は落下が止まる
			if (!isKeyOn) {
				this.mino.moveDown(this.field, this.gameLevel);
			}

			// ミノが着地した
			if (this.mino.colision(this.field)) {
				// ミノをフィールドに積み上げる
				mino.setPanel2Field(field);

				// ミノを削除する行があるかチェック
				int removeRow = field.checkRemoveRow();
				if (removeRow > 0) {
					// 削除した行数に応じてスコアを加算
					this.score = this.score + (removeRow * 100);

					// レベルの上限が5しかない。。。
					this.gameLevel++;
					if (this.gameLevel > 5) {
						this.gameLevel--;
					}

					field.removeRow();
					mediaRemove.play();
				}

				mediaColision.play();
				this.gameStatus = 3;
			}

			break;

		case 3: // 新しいミノを表示
			this.nextMino.init();
			this.mino = this.nextMino;

			// ゲームオーバの判定
			if (this.mino.colision(field)) {
				this.gameStatus = 4;
				return;
			}

			this.nextMino = Mino.getMino();
			this.showNextMino();
			this.gameStatus = 1;
			break;

		case 4: // ゲームオーバー画面
			this.message = "ゲームオーバーしました。。。PRESS ENTER ";

			if (GameLib.isKeyOn("ENTER")) {
				this.gameStatus = 0;
			}

			break;

		case 5: // ポーズ中画面
			if (GameLib.isKeyOn("E")) {
				this.gameStatus = 1;
			}
			this.message = "PAUSE。。。PRESS E ";
			break;
		}

		this.field.show(canvas);
		this.mino.show(canvas);
		this.showScore();
		this.showLevel();
		this.showMagic();
		this.showMessage();
	}

	// メッセージの表示
	private void showMessage() {
		GraphicsContext canvas = GameLib.getGC();
		canvas.setFill(Color.WHITE);
		canvas.fillText(this.message, 60, 50 );
	}

	// 背景の表示
	private void showBackground() {

		GraphicsContext canvas = GameLib.getGC();

		Image img = new Image(new File("tile.png").toURI().toString());
		WritableImage resizedImage = new WritableImage(img.getPixelReader(),64, 192, (int) (img.getWidth() / 8), (int) (img.getHeight() / 8));

		int col = GameLib.width() / (int) Panel.panelW();
		int row = GameLib.height() / (int) Panel.panelH();

		int x,y;
		for (int i = 0; i < col; i++) {
			for (int l = 0; l < row; l++) {
				x = i * (int) Panel.panelW();
				y = l * (int) Panel.panelH();
				canvas.drawImage(resizedImage,x, y, Panel.panelW(), Panel.panelH());
			}
		}
	}

	// 得点表示
	private void showScore() {
		GraphicsContext canvas = GameLib.getGC();
		canvas.setFill(Color.BLACK);
		canvas.fillRect(scoreX, scoreY, scoreW, scoreH);
		canvas.setFill(Color.WHITE);
		canvas.fillText("スコア：" + this.score, scoreX + Panel.panelW(), scoreY + Panel.panelW());
	}

	// 次のミノ表示
	private void showNextMino() {
		GraphicsContext canvas = GameLib.getGC();
		canvas.setFill(Color.BLACK);
		canvas.fillRect(nextX, nextY, nextW, nextH);
		nextMino = Mino.getMino();
		nextMino.setMinoX(nextX);
		nextMino.setMinoY(nextY);
		nextMino.show(canvas);
	}

	// レベル表示
	private void showLevel() {
		GraphicsContext canvas = GameLib.getGC();
		canvas.setFill(Color.BLACK);
		canvas.fillRect(levelX, levelY, levelW, levelH);
		canvas.setFill(Color.WHITE);
		canvas.fillText("LV：" + this.gameLevel, levelX + Panel.panelW(), levelY + Panel.panelW());
	}

	// 魔法のストック表示
	private void showMagic() {
		GraphicsContext canvas = GameLib.getGC();
		canvas.setFill(Color.BLACK);
		canvas.fillRect(magicX, magicY, magicW, magicH);
	}
}