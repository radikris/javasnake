package elements;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import enumtype.STATE;
import gameplay.Table;
import jaco.mp3.player.MP3Player;

public class MyMenuBar extends JMenuBar {

	public static ImageIcon darkmode = new ImageIcon(
			new ImageIcon("src/assets/darkmode.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
	public static ImageIcon lightmode = new ImageIcon(
			new ImageIcon("src/assets/lightmode.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
	public static ImageIcon mute = new ImageIcon(
			new ImageIcon("src/assets/mute.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
	public static ImageIcon nomute = new ImageIcon(
			new ImageIcon("src/assets/nomute.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));

	public JCheckBoxMenuItem modeMenuItem;
	JCheckBoxMenuItem songMenuItem;
	Table mytable;

	public MyMenuBar(MP3Player mp3player, Table mytable) {

		JMenu songMenu = new JMenu("Song");
		JMenu modeMenu = new JMenu("Mode");
		this.mytable=mytable;

		this.add(songMenu);
		this.add(modeMenu);

		modeMenuItem = new JCheckBoxMenuItem("Light", lightmode);
		modeMenuItem.setSelected(true);
		modeMenuItem.setMnemonic(KeyEvent.VK_V);
		modeMenu.add(modeMenuItem);

		songMenuItem = new JCheckBoxMenuItem("Music:ON", nomute);
		songMenuItem.setSelected(true);
		songMenuItem.setMnemonic(KeyEvent.VK_C);
		songMenu.add(songMenuItem);

		ActionListener songListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				AbstractButton aButton = (AbstractButton) event.getSource();
				boolean selected = aButton.getModel().isSelected();
				String newLabel;
				Icon newIcon;
				if (selected) {

					newLabel = "MUSIC:ON";
					aButton.setIcon(nomute);
					mp3player.play();
				} else {
					newLabel = "MUSIC:OFF";
					aButton.setIcon(mute);

					mp3player.pause();
				}
				aButton.setText(newLabel);
			}

		};

		ActionListener modeListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				/*
				 * AbstractButton aButton = (AbstractButton) event.getSource(); boolean selected
				 * = aButton.getModel().isSelected(); String newLabel; Icon newIcon; if
				 * (selected) {
				 * 
				 * newLabel = "Light"; aButton.setIcon(lightmode);
				 * mytable.setColorMode(Color.white); } else { newLabel = "Dark";
				 * mytable.setColorMode(Color.black); aButton.setIcon(darkmode); }
				 * aButton.setText(newLabel);
				 */
				modeListen(event);
			}

		};

		songMenuItem.addActionListener(songListener);
		modeMenuItem.addActionListener(modeListener);
	}
	
	public void modeListen(ActionEvent event) {
		AbstractButton aButton = (AbstractButton) event.getSource();
		boolean selected = aButton.getModel().isSelected();
		String newLabel;
		Icon newIcon;
		if (selected) {

			newLabel = "Light";
			aButton.setIcon(lightmode);
			mytable.setColorMode(Color.white);
		} else {
			newLabel = "Dark";
			mytable.setColorMode(Color.black);
			aButton.setIcon(darkmode);
		}
		aButton.setText(newLabel);
	}

}
