package controller;

public class ControllerManager {
	private ViewController viewController;
	private IntroController introController;

	public void addIntroController(IntroController controller) {
		this.introController = controller;
	}

	public void addViewController(ViewController controller) {
		this.viewController = controller;
	}

	public IntroController getMenuController() {
		return introController;
	}

	public ViewController getViewController() {
		return viewController;
	}

}
