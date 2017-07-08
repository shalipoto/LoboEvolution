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

package org.lobobrowser.html.svgimpl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

import javax.swing.JComponent;
import javax.swing.Timer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lobobrowser.html.control.RUIControl;
import org.lobobrowser.html.info.SVGInfo;
import org.lobobrowser.w3c.smil.ElementTargetAttributes;
import org.lobobrowser.w3c.svg.SVGTransform;
import org.lobobrowser.w3c.svg.SVGTransformList;

public class SVGAnimateImpl extends JComponent implements ActionListener {

	private static final long serialVersionUID = 1L;
	protected static final Logger logger = LogManager.getLogger(SVGAnimateImpl.class.getName());
	private Timer timer;
	private SVGInfo info;
	private RUIControl ruicontrol;
	private float from_xml;
	private float to_xml;
	private String from_trans;
	private String to_trans;

	public SVGAnimateImpl(SVGInfo info, RUIControl ruicontrol) {
		this.info = info;
		this.ruicontrol = ruicontrol;
		timer = new Timer(1, this);
		timer.setInitialDelay(0);
		timer.setCoalesce(true);
		startAnimation();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		SVGAnimationImpl animate = info.getAnimate();
		switch (animate.getAttributeName().toLowerCase()) {
		case "width":
			animateWidth();
			break;
		case "heigth":
			animateHeight();
			break;
		case "y":
		case "cy":
			animateY();
			break;
		case "x":
		case "cx":
			animateX();
			break;
		case "x1":
			animateX1();
		case "x2":
			animateX2();
		case "y1":
			animateY1();
		case "y2":
			animateY2();
		case "r":
			animateR();
			break;
		case "transform":
			animateTransform();
		default:
			break;
		}
	}

	private void animateWidth() {
		if (info.getWidth() >= to_xml) {
			stopAnimation();
		} else {
			from_xml++;
			info.setWidth(from_xml);
			ruicontrol.relayout();
		}
	}

	private void animateHeight() {
		if (info.getHeight() >= to_xml) {
			stopAnimation();
		} else {
			from_xml++;
			info.setHeight(from_xml);
			ruicontrol.relayout();
		}
	}

	private void animateX() {
		if (info.getX() >= to_xml) {
			stopAnimation();
		} else {
			from_xml++;
			info.setX(from_xml);
			ruicontrol.relayout();
		}
	}

	private void animateY() {
		if (info.getY() >= to_xml) {
			stopAnimation();
		} else {
			from_xml++;
			info.setY(from_xml);
			ruicontrol.relayout();
		}
	}

	private void animateR() {
		if (info.getR() >= to_xml) {
			stopAnimation();
		} else {
			from_xml++;
			info.setR(from_xml);
			ruicontrol.relayout();
		}
	}

	private void animateX1() {
		if (info.getX1() >= to_xml) {
			stopAnimation();
		} else {
			from_xml++;
			info.setX1(from_xml);
			ruicontrol.relayout();
		}
	}

	private void animateX2() {
		if (info.getX2() >= to_xml) {
			stopAnimation();
		} else {
			from_xml++;
			info.setX2(from_xml);
			ruicontrol.relayout();
		}
	}

	private void animateY1() {
		if (info.getY1() >= to_xml) {
			stopAnimation();
		} else {
			from_xml++;
			info.setY1(from_xml);
			ruicontrol.relayout();
		}
	}

	private void animateY2() {
		if (info.getY2() >= to_xml) {
			stopAnimation();
		} else {
			from_xml++;
			info.setY2(from_xml);
			ruicontrol.relayout();
		}
	}

	private void animateTransform() {
		
		if (from_trans.equals(to_trans)) {
			stopAnimation();
		} else {
			String transformString = "";
			SVGAnimationImpl animate = info.getAnimate();
			StringTokenizer stFrom = new StringTokenizer(from_trans, " ,");
			
			if (animate.getType() == SVGTransform.SVG_TRANSFORM_TRANSLATE) {

				float txFrom = 0;
				float tyFrom = 0;
				if (stFrom.countTokens() == 1) {
					txFrom = Float.parseFloat(stFrom.nextToken());
				} else if (stFrom.countTokens() == 2) {
					txFrom = Float.parseFloat(stFrom.nextToken());
					tyFrom = Float.parseFloat(stFrom.nextToken());
				}

				StringTokenizer stTo = new StringTokenizer(to_trans, " ,");
				float txTo = 0;
				float tyTo = 0;
				if (stTo.countTokens() == 1) {
					txTo = Float.parseFloat(stTo.nextToken());
				} else if (stTo.countTokens() == 2) {
					txTo = Float.parseFloat(stTo.nextToken());
					tyTo = Float.parseFloat(stTo.nextToken());
				}

				if (txFrom > txTo)
					txFrom--;

				if (txFrom < txTo)
					txFrom++;

				if (tyFrom > tyTo)
					tyFrom--;

				if (tyFrom < tyTo)
					tyFrom++;

				from_trans = txFrom + ", " + tyFrom;
				to_trans = tyFrom + ", " + tyTo;
				transformString = "translate(" + from_trans + ")";

			} else if (animate.getType() == SVGTransform.SVG_TRANSFORM_SCALE) {

				float sxFrom = 0;
				float syFrom = 0;
				if (stFrom.countTokens() == 1) {
					sxFrom = Float.parseFloat(stFrom.nextToken());
				} else if (stFrom.countTokens() == 2) {
					sxFrom = Float.parseFloat(stFrom.nextToken());
					syFrom = Float.parseFloat(stFrom.nextToken());
				}

				StringTokenizer stTo = new StringTokenizer(to_trans, " ,");
				float sxTo = 0;
				float syTo = 0;
				if (stTo.countTokens() == 1) {
					sxTo = Float.parseFloat(stTo.nextToken());
				} else if (stTo.countTokens() == 2) {
					sxTo = Float.parseFloat(stTo.nextToken());
					syTo = Float.parseFloat(stTo.nextToken());
				}

				if (sxFrom > sxTo)
					sxFrom--;

				if (sxFrom < sxTo)
					sxFrom++;

				if (syFrom > syTo)
					syFrom--;

				if (syFrom < syTo)
					syFrom++;
				
				if(syFrom == 0){
					from_trans = String.valueOf(sxFrom);
					to_trans = String.valueOf(Float.parseFloat(to_trans));
				} else{
					from_trans = sxFrom + ", " + syFrom;
					to_trans = syFrom + ", " + syTo;
				}

				transformString = "scale(" + from_trans + ")";
				
			} else if (animate.getType() == SVGTransform.SVG_TRANSFORM_ROTATE) {

				float angleFrom = 0;
				float cxFrom = 0;
				float cyFrom = 0;
				
				if (stFrom.countTokens() == 1) {
					angleFrom = Float.parseFloat(stFrom.nextToken());
				} else if (stFrom.countTokens() == 3) {
					angleFrom = Float.parseFloat(stFrom.nextToken());
					cxFrom = Float.parseFloat(stFrom.nextToken());
					cyFrom = Float.parseFloat(stFrom.nextToken());
				}

				StringTokenizer stTo = new StringTokenizer(to_trans, " ,");
				float angleTo = 0;
				float cxTo = 0;
				float cyTo = 0;
				if (stTo.countTokens() == 1) {
					angleTo = Float.parseFloat(stTo.nextToken());
				} else if (stTo.countTokens() == 3) {
					angleTo = Float.parseFloat(stTo.nextToken());
					cxTo = Float.parseFloat(stTo.nextToken());
					cyTo = Float.parseFloat(stTo.nextToken());
				}

				if (angleFrom > angleTo)
					angleFrom--;

				if (angleFrom < angleTo)
					angleFrom++;

				if (cxFrom > cxTo)
					cxFrom--;

				if (cxFrom < cxTo)
					cxFrom++;

				if (cyFrom > cyTo)
					cyFrom--;

				if (cyFrom < cyTo)
					cyFrom++;

				from_trans = angleFrom + ", " + cxFrom + ", " + cyFrom;
				to_trans = angleTo + ", " + cxTo + ", " + cyTo;
				transformString = "rotate(" + from_trans + ")";

			} else if (animate.getType() == SVGTransform.SVG_TRANSFORM_SKEWX) {

				float sxFrom = Float.parseFloat(from_trans);
				float sxTo = Float.parseFloat(to_trans);

				if (sxFrom > sxTo)
					sxFrom--;

				if (sxFrom < sxTo)
					sxFrom++;
				
				from_trans = String.valueOf(sxFrom);
				to_trans = String.valueOf(sxTo);
				transformString = "skewX(" + sxFrom + ")";

			} else if (animate.getType() == SVGTransform.SVG_TRANSFORM_SKEWY) {

				float sxFrom = Float.parseFloat(from_trans);
				float sxTo = Float.parseFloat(to_trans);

				if (sxFrom > sxTo)
					sxFrom--;

				if (sxFrom < sxTo)
					sxFrom++;
				
				from_trans = String.valueOf(sxFrom);
				to_trans = String.valueOf(sxTo);
				transformString = "skewY(" + sxFrom + ")";
			}
			
			SVGTransformList transformList = SVGTransformListImpl.createTransformList(transformString);
			info.setTransformList(transformList);
			ruicontrol.relayout();
		}
	}

	private void startAnimation() {
		if (!timer.isRunning()) {
			SVGAnimationImpl animate = info.getAnimate();

			if ("transform".equalsIgnoreCase(animate.getAttributeName())) {
				from_trans = animate.getFrom();
				to_trans = animate.getTo();
			} else if (ElementTargetAttributes.ATTRIBUTE_TYPE_XML == animate.getAttributeType()) {
				from_xml = Float.parseFloat(animate.getFrom());
				to_xml = Float.parseFloat(animate.getTo());
			}
			timer.start();
		}
	}

	private void stopAnimation() {
		if (timer.isRunning()) {
			timer.stop();
		}
	}
}