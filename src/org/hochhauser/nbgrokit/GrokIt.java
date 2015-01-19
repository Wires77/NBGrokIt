package org.hochhauser.nbgrokit;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JEditorPane;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
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
    @ActionReference(path = "Shortcuts", name = "SM-G")
})

@Messages("CTL_GrokIt=Open Grok")
public final class GrokIt implements ActionListener {

    private final EditorCookie context;

    public GrokIt(EditorCookie context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        String selectedText = getSelectedText();
        if (selectedText != null) {
            openWebpage("https://www.google.com/#q=" + selectedText);
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

    public static void openWebpage(String urlStr) {
        try {
            openWebpage(new URI(urlStr));
        } catch (URISyntaxException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public static void openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            } catch (Exception ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }
}
