/**
 * 
 */
package ui.util;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * @author 1
 *
 */
public class PlainButton extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	// TODO Auto-generated constructor stub
	public PlainButton(ImageIcon icon, ImageIcon refresh) {
		// TODO Auto-generated constructor stub
		super();
		setOpaque(true);
		setMargin(new Insets(0, 0, 0, 0));
		setContentAreaFilled(false);
		setBorderPainted(false);
		super.setIcon(icon);
		setRolloverIcon(refresh);
		setSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
	}

	protected void paintIcon(Graphics g, JComponent c, Rectangle iconRect) {
		AbstractButton b = (AbstractButton) c;
		ButtonModel model = b.getModel();
		Icon icon = b.getIcon();
		Icon tmpIcon = null;
		if (icon == null) {
			return;
		}
		Icon selectedIcon = null;
		if (model.isSelected()) {
			selectedIcon = (Icon) b.getSelectedIcon();
			if (selectedIcon != null) {
				icon = selectedIcon;
			}
		}
		if (model.isPressed() && model.isArmed()) {
			tmpIcon = (Icon) b.getPressedIcon();
			if (tmpIcon != null) {
				// clearTextShiftOffset();
				System.out.println("tmpIcon != null");
			}
		} else if (b.isRolloverEnabled() && model.isRollover()) {
			if (model.isSelected()) {
				tmpIcon = (Icon) b.getRolloverSelectedIcon();
				if (tmpIcon == null) {
					tmpIcon = selectedIcon;
				}
			}
			if (tmpIcon == null) {
				tmpIcon = (Icon) b.getRolloverIcon();
			}
		}
		if (tmpIcon != null) {
			icon = tmpIcon;
		}
		if (model.isPressed() && model.isArmed()) {
			// icon.paintIcon(c, g, iconRect.x + getTextShiftOffset(),
			// iconRect.y + getTextShiftOffset());
			System.out.println("model.isPressed() && model.isArmed()");
		} else {
			icon.paintIcon(c, g, iconRect.x, iconRect.y);
		}
	}
}
