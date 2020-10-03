/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.JFrame;

/**
 *
 * @author Er Vaibhav Karnwal
 */
public class MainClass {
	public static void main(String[] args) {
		JFrame obj=new JFrame();
		GamePlay gamePlay = new GamePlay();
		
		obj.setBounds(10, 10, 700, 600);
		obj.setTitle("Brick Breaker");		
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(gamePlay);
		
	}

}
