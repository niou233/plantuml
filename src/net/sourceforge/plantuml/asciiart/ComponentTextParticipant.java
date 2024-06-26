/* ========================================================================
 * PlantUML : a free UML diagram generator
 * ========================================================================
 *
 * (C) Copyright 2009-2024, Arnaud Roques
 *
 * Project Info:  https://plantuml.com
 * 
 * If you like this project or if you find it useful, you can support us at:
 * 
 * https://plantuml.com/patreon (only 1$ per month!)
 * https://plantuml.com/paypal
 * 
 * This file is part of PlantUML.
 *
 * PlantUML is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PlantUML distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public
 * License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 *
 * Original Author:  Arnaud Roques
 *
 *
 */
package net.sourceforge.plantuml.asciiart;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.StringUtils;
import net.sourceforge.plantuml.klimt.creole.Display;
import net.sourceforge.plantuml.klimt.drawing.UGraphic;
import net.sourceforge.plantuml.klimt.drawing.txt.UGraphicTxt;
import net.sourceforge.plantuml.klimt.font.StringBounder;
import net.sourceforge.plantuml.klimt.geom.XDimension2D;
import net.sourceforge.plantuml.skin.Area;
import net.sourceforge.plantuml.skin.ComponentType;
import net.sourceforge.plantuml.skin.Context2D;

public class ComponentTextParticipant extends AbstractComponentText {

	private final ComponentType type;
	private final Display stringsToDisplay;
	private final FileFormat fileFormat;

	public ComponentTextParticipant(ComponentType type, Display stringsToDisplay, FileFormat fileFormat) {
		this.type = type;
		this.stringsToDisplay = stringsToDisplay;
		this.fileFormat = fileFormat;
	}

	public void drawU(UGraphic ug, Area area, Context2D context) {
		final XDimension2D dimensionToUse = area.getDimensionToUse();
		final UmlCharArea charArea = ((UGraphicTxt) ug).getCharArea();
		final int width = (int) dimensionToUse.getWidth();
		final int height = (int) dimensionToUse.getHeight();
		int textWidth = StringUtils.getWcWidth(stringsToDisplay);
		int boxWidth = textWidth+2;

		charArea.fillRect(' ', 0, 0, boxWidth, height);
		if (fileFormat == FileFormat.UTXT) {
			charArea.drawBoxSimpleUnicode(0, 0, boxWidth, height);
			if (type == ComponentType.PARTICIPANT_TAIL) {
				charArea.drawChar('\u2534', (width - 1) / 2, 0);
			}
			if (type == ComponentType.PARTICIPANT_HEAD) {
				charArea.drawChar('\u252c', (width - 1) / 2, height - 1);
			}
		} else {
			charArea.drawBoxSimple(0, 0, boxWidth, height);
			if (type == ComponentType.PARTICIPANT_TAIL) {
				charArea.drawChar('+', (width - 1) / 2, 0);
			}
			if (type == ComponentType.PARTICIPANT_HEAD) {
				charArea.drawChar('+', (width - 1) / 2, height - 1);
			}
		}
		if (fileFormat == FileFormat.UTXT) {
			charArea.drawStringsLRUnicode(stringsToDisplay.asList(), 1, 1);
		} else {
			charArea.drawStringsLRSimple(stringsToDisplay.asList(), 1, 1);
		}
	}

	public double getPreferredHeight(StringBounder stringBounder) {
		return StringUtils.getHeight(stringsToDisplay) + 2;
	}

	public double getPreferredWidth(StringBounder stringBounder) {
		// make all widths odd sized by converting the string widths to even,
		// adding 3 to make it both odd and padded by 1 on each side, then return the result
		// as a double.
		return (double) ((StringUtils.getWcWidth(stringsToDisplay) / 2 * 2) + 3);
	}

}
