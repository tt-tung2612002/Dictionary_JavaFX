package controller.autocomplete;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;

public class AutoCompleteDocument extends PlainDocument {

	private static final long serialVersionUID = 1L;

	private CompletionService<?> completionService;

	private JTextComponent documentOwner;

	public AutoCompleteDocument(CompletionService<?> service, JTextComponent documentOwner) {
		this.completionService = service;
		this.documentOwner = documentOwner;
	}

	protected String complete(String str) {
		Object o = completionService.autoComplete(str);
		return o == null ? null : o.toString();
	}

	@Override
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		if (str == null || str.length() == 0) {
			return;
		}
		String text = getText(0, offs); // Current text.
		String completion = complete(text + str);
		int length = offs + str.length();
		if (completion != null && text.length() > 0) {
			str = completion.substring(length - 1);
			super.insertString(offs, str, a);
			documentOwner.select(length, getLength());
		} else {
			super.insertString(offs, str, a);
		}
	}
}