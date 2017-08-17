package org.lobobrowser.html.js;

import javax.swing.Timer;

/**
 * The Class TaskWrapper.
 */
public class TaskWrapper {

	/** The timer. */
	public final Timer timer;

	/**
	 * Instantiates a new task wrapper.
	 *
	 * @param timer
	 *            the timer
	 * @param retained
	 *            the retained
	 */
	public TaskWrapper(Timer timer, Object retained) {
		super();
		this.timer = timer;
	}
}
