package org.hochhauser.nbgrokit;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
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

@Messages("CTL_GrokIt=GrokIt")
public final class GrokIt implements ActionListener {

	private final EditorCookie context;

	public GrokIt(EditorCookie context) {
		this.context = context;
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		grokText(getSelectedText());
//		grokInPane(getSelectedText());
	}

	public static void grokText(String selectedText) {
		try {
			if (selectedText != null)
				openWebpage("http://opengrok/grokcwan/search?q=" + URLEncoder.encode(selectedText, "UTF-8"));
		} catch (IOException | URISyntaxException ex) {
			Exceptions.printStackTrace(ex);
		}
	}

	// This is an attempt to open the pane right in NetBeans
	public static void grokInPane(String selectedText) {
		try {
			URLDisplayer.getDefault().showURL(new URI("https://www.google.com/#q=" + URLEncoder.encode(selectedText, "UTF-8")).toURL());
		} catch (UnsupportedEncodingException | MalformedURLException | URISyntaxException ex) {
			Exceptions.printStackTrace(ex);
		}
	}

	public String getSelectedText() {
		if (context != null) {
			for (JEditorPane pane : context.getOpenedPanes()) {
				if (pane != null && pane.isShowing()) {
					return pane.getSelectedText();
				}
			}
		}
		return null;
	}

	public static void openWebpage(String urlStr) throws IOException, URISyntaxException {
		openWebpage(new URI(urlStr));
	}

	public static void openWebpage(URI uri) throws IOException {
		if (Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();
			if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
				desktop.browse(uri);
			}
		}
	}
}
