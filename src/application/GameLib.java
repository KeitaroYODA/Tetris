package application;

import java.util.HashSet;
import java.util.Set;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class GameLib extends Application {

	// 画面の幅
	private static final int WIDTH = 660;
	// private static final int WIDTH = 440;
	private static final int HEIGHT = 380;

	// テトリスクラス
	private Tetris_Obj game = new Tetris_Obj();

	// Canvas
	private static Canvas canvas;

	// キー
	private static Set<String> curKeyCode = new HashSet<>();
	private static Set<String> preKeyCode = new HashSet<>();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Group root = new Group();
		Scene theScene = new Scene(root);
		primaryStage.setScene(theScene);

		canvas = new Canvas(WIDTH, HEIGHT);

		root.getChildren().add(canvas);

		// ループ処理
		AnimationTimer at = new AnimationTimer() {

			@Override
			public void handle(long now) {

				game.update();

				preKeyCode = new HashSet<>(curKeyCode);

			}
		};

		// キーイベント処理
		theScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				String code = event.getCode().toString();
				curKeyCode.add(code);
			}
		});

		theScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				String code = event.getCode().toString();
				curKeyCode.remove(code);
			}
		});

		at.start();

		primaryStage.show();
	}

	/**
	 * 画面の縦の長さを取得します。
	 */
	public static int height() {
		return HEIGHT;
	}

	/**
	 * 画面の横の長さを取得します。
	 */
	public static int width() {
		return WIDTH;
	}

	/**
	 * GraphicsContextを取得します。
	 */
	public static GraphicsContext getGC() {
		return canvas.getGraphicsContext2D();
	}

	/**
	 * 指定されたキーが押されているかを返します。
	 */
	public static boolean isKeyOn(String code) {
		return curKeyCode.contains(code);
	}

	/**
	 * 指定されたキーが本フレームで押されだしたかを返します。
	 */
	public static boolean isKeyTrigger(String code) {
		return curKeyCode.contains(code) && !preKeyCode.contains(code);
	}

}