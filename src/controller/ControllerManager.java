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

	public IntroController getIntroController() {
		return introController;
	}

	public ViewController getViewController() {
		return viewController;
	}

	public EditController getEditController() {
		return editController;
	}

	/**
	 * @param viewController the viewController to set
	 */
	public void setViewController(ViewController viewController) {
		this.viewController = viewController;
	}

	/**
	 * @param introController the introController to set
	 */
	public void setIntroController(IntroController introController) {
		this.introController = introController;
	}

	/**
	 * @param editController the editController to set
	 */
	public void setEditController(EditController editController) {
		this.editController = editController;
	}

	/**
	 * @param helpController the helpController to set
	 */
	public void setHelpController(HelpController helpController) {
		this.helpController = helpController;
	}

}
