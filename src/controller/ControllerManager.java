package controller;

public class ControllerManager {
	private ViewController viewController;
	private IntroController introController;
	private EditController editController;
	private HelpController helpController;

	public void addHelpController(HelpController helpController) {
		this.helpController = helpController;
	}

	public void addIntroController(IntroController introController) {
		this.introController = introController;
	}

	public void addViewController(ViewController viewController) {
		this.viewController = viewController;
	}

	public void addEditController(EditController editController) {
		this.editController = editController;
	}

	public HelpController getHelpController() {
		return helpController;
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
