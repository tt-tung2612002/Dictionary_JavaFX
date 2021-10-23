package controller;

public class ControllerManager {
	private ViewController viewController;
	private MenuController menuController;

	public void addMenuController(MenuController controller) {
		this.menuController = controller;
	}

	public void addViewController(ViewController controller) {
		this.viewController = controller;
	}

	public MenuController getMenuController() {
		return menuController;
	}

	public ViewController getViewController() {
		return viewController;
	}

}
