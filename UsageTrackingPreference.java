/*
 * Scilab ( http://www.scilab.org/ ) - This file is part of Scilab
 * Copyright (C) 2010 - Calixte DENIZET
 *
 * Copyright (C) 2012 - 2016 - Scilab Enterprises
 *
 * This file is hereby licensed under the terms of the GNU GPL v2.0,
 * pursuant to article 5.3.4 of the CeCILL v.2.1.
 * This file was originally licensed under the terms of the CeCILL v2.1,
 * and continues to be available under such terms.
 * For more information, see the COPYING file which you should have received
 * along with this program.
 *
 */

//CHECKSTYLE:OFF

// /scilab_master/scilab/modules/scinotes/src/java/org/scilab/modules/scinotes

package org.scilab.modules.scinotes;

import java.awt.*;
import java.awt.event.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.Timer;
import javax.swing.JFileChooser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Iterator;
import java.util.prefs.Preferences;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import org.scilab.modules.commons.ScilabCommonsUtils;


public class UsageTrackingPreference extends JFrame {

	private static final long serialVersionUID = 1L;
	private JFrame mainFrame;
	private JButton okButton;
	private JButton cancelButton;
	private JLabel headerLabel;
   	private JLabel statusLabel;
   	private JPanel controlPanel;
   	private Preferences prefs;


	// This method sets the Usage Tracking Preference 
	public void setUsageTrackingPreference() {
		
		// Sets the node which will store the preferences
		prefs = Preferences.userRoot().node(this.getClass().getName());
		String ID1 = "Usage Tracking";

		// The prepareGUI() method is called to prepare the options window
		prepareGUI();

		// Buttons defined for the usage tracking
		okButton = new JButton("Track SCILAB Usage");
		cancelButton = new JButton("Do not Track");

		// Function performed when the OK button is pressed
		okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e){
					// Preferences are set according to the OK button and message is displayed 
					prefs.putBoolean(ID1, true);
					statusLabel.setText("SCILAB Usage Tracking ON");
					mainFrame.dispose();
					// From here, we will call the Google Analytics API to be implemented by HashMap to start the tracking process which will be the next step. 
				}
			});	

		
		cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e){
					// Prefernces are set according to the Cancel Button and message is displayed and therefore Tracking functionality is camcelled.
					prefs.putBoolean(ID1, false);
					statusLabel.setText("SCILAB Usage Tracking OFF");
					mainFrame.dispose();
				}
			});

		// Adds the buttons to the panel
		controlPanel.add(okButton);
		controlPanel.add(cancelButton);

	}

	// Method implemented for creating the window
	public void prepareGUI(){
      
      	mainFrame = new JFrame("SCILAB Usage Tracking");
      	mainFrame.setSize(400,400);
      	mainFrame.setLayout(new GridLayout(3, 1));
      
      	mainFrame.addWindowListener(new WindowAdapter() {
         		public void windowClosing(WindowEvent windowEvent){
            	System.exit(0);
         	}        
      	});    
      
     	headerLabel = new JLabel("", JLabel.CENTER);        
     	statusLabel = new JLabel("",JLabel.CENTER);    
     	statusLabel.setSize(350,100);

      	controlPanel = new JPanel();
      	controlPanel.setLayout(new FlowLayout());

    	mainFrame.add(headerLabel);
      	mainFrame.add(controlPanel);
      	mainFrame.add(statusLabel);
      	mainFrame.setVisible(true);  
	}

}
