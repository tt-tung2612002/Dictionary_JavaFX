package controller.autocomplete;

public interface CompletionService<T> {

	T autoComplete(String startsWith);
}
