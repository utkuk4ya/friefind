package main;

import java.io.FileNotFoundException;

import GUI.LoginFrame;
import service.FriefindSystem;

import java.io.FileNotFoundException;

public class FriefindMain {

	public static void main(String[] args) throws FileNotFoundException {

		 FriefindSystem.readTxt();
		 LoginFrame frame = new LoginFrame();
		 frame.setVisible(true);

	}

}
