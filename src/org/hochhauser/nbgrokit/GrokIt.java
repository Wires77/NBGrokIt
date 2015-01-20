package org.hochhauser.nbgrokit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import javax.swing.JEditorPane;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.awt.HtmlBrowser.URLDisplayer;
import org.openide.cookies.EditorCookie;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;

@ActionID(
		category = "Edit",
		id = "org.hochhauser.nbgrokit.GrokIt"
)
@ActionRegistration(
		displayName = "#CTL_GrokIt"
)

@ActionReferences({
	@ActionReference(path = "Menu/GoTo", position = 750),
	@ActionReference(path = "Shortcuts", name = "SC-G")
})

@Messages("CTL_GrokIt=Grok It")
public final class GrokIt implements ActionListener {

	private final EditorCookie context;

	public GrokIt(EditorCookie context) {
		this.context = context;
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		grokText(getSelectedText(context));
	}

	public static void grokText(String selectedText) {
		try {
			if (selectedText != null)
				URLDisplayer.getDefault().showURL(new URL("http://opengrok/grokcwan/search?q=" + URLEncoder.encode(selectedText, "UTF-8")));
		} catch (MalformedURLException | UnsupportedEncodingException ex) {
			Exceptions.printStackTrace(ex);
		}
	}

	public static String getSelectedText(EditorCookie context) {
		if (context != null) {
			for (JEditorPane pane : context.getOpenedPanes()) {
				if (pane != null && pane.isShowing()) {
					return pane.getSelectedText();
				}
			}
		}
		return null;
	}
}
