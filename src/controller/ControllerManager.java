package controller;

public class ControllerManager {
	private ViewController viewController;
	private IntroController introController;
	private EditController editController;

	public void addIntroController(IntroController controller) {
		this.introController = controller;
	}

	public void addViewController(ViewController controller) {
		this.viewController = controller;
	}

	public void addEditController(EditController controller) {
		this.editController = controller;
	}

	public IntroController getMenuController() {
		return introController;
	}

	public ViewController getViewController() {
		return viewController;
	}

	public EditController getEditController() {
		return editController;
	}

}
