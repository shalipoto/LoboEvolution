/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Sep 18, 2005
 */
package org.lobobrowser.primary.clientlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lobobrowser.clientlet.Clientlet;
import org.lobobrowser.clientlet.ClientletRequest;
import org.lobobrowser.clientlet.ClientletResponse;
import org.lobobrowser.clientlet.ClientletSelector;
import org.lobobrowser.primary.clientlets.download.DownloadClientlet;
import org.lobobrowser.primary.clientlets.html.HtmlClientlet;
import org.lobobrowser.primary.clientlets.img.ImageClientlet;
import org.lobobrowser.primary.clientlets.pdf.PdfClientlet;

/**
 * The Class PrimaryClientletSelector.
 */
public class PrimaryClientletSelector implements ClientletSelector {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(PrimaryClientletSelector.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.clientlet.ClientletSelector#select(org.lobobrowser.
	 * clientlet .ClientletRequest, org.lobobrowser.clientlet.ClientletResponse)
	 */
	@Override
	public Clientlet select(ClientletRequest request, ClientletResponse response) {
		// Don't try to catch too much here.
		// Clientlets here are not overriddable.

		String mimeType = response.getMimeType();

		if (logger.isInfoEnabled()) {
			logger.info("select(): mimeType=" + mimeType);
		}
		String mimeTypeTL = mimeType == null ? null : mimeType.toLowerCase();
		if ("text/html".equals(mimeTypeTL) || "image/svg+xml".equals(mimeTypeTL)) {
			// TODO: XHTML needs its own clientlet.
			return new HtmlClientlet();
		} else if ("image/jpeg".equals(mimeTypeTL) || "image/jpg".equals(mimeTypeTL) || "image/gif".equals(mimeTypeTL)
				|| "image/png".equals(mimeTypeTL)) {
			return new ImageClientlet();
		} else if (mimeType == null || "application/octet-stream".equals(mimeTypeTL)
				|| "content/unknown".equals(mimeTypeTL)) {

			String path = response.getResponseURL().getPath();
			int lastDotIdx = path.lastIndexOf('.');
			String extension = lastDotIdx == -1 ? "" : path.substring(lastDotIdx + 1);
			String extensionTL = extension.toLowerCase();
			if ("html".equals(extensionTL) || "htm".equals(extensionTL) || "svg".equals(extensionTL)
					|| extensionTL.length() == 0) {
				return new HtmlClientlet();
			} else if ("gif".equals(extensionTL) || "jpg".equals(extensionTL) || "png".equals(extensionTL)) {
				return new ImageClientlet();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.clientlet.ClientletSelector#lastResortSelect(org.
	 * lobobrowser .clientlet.ClientletRequest,
	 * org.lobobrowser.clientlet.ClientletResponse)
	 */
	@Override
	public Clientlet lastResortSelect(ClientletRequest request, ClientletResponse response) {
		String mimeType = response.getMimeType();
		String mimeTypeTL = mimeType == null ? null : mimeType.toLowerCase();

		if ("application/xml".equals(mimeTypeTL)) {
			// TODO: XHTML needs its own clientlet.
			return new HtmlClientlet();
		} else {
			String path = response.getResponseURL().getPath();
			int lastDotIdx = path.lastIndexOf('.');
			String extension = lastDotIdx == -1 ? "" : path.substring(lastDotIdx + 1);
			String extensionTL = extension.toLowerCase();
			if ("xhtml".equals(extensionTL)) {
				return new HtmlClientlet();
			} else if ("txt".equals(extensionTL) || "xml".equals(extensionTL) || "js".equals(extensionTL)
					|| "rss".equals(extensionTL) || "xaml".equals(extensionTL) || "css".equals(extensionTL)) {
				return new TextClientlet(extensionTL);
			} else if ("pdf".equals(extensionTL)) {
				return new PdfClientlet();
			} else if (mimeType == null) {
				return new HtmlClientlet();
			} else {
				return new DownloadClientlet();
			}
		}
	}
}
