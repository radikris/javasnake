/*
 * package elements;
 * 
 * @Geeky Gamer package com.harry.fortress.window;
 * 
 * import java.applet.Applet; import java.applet.AudioClip;
 * 
 * public class Jukebox {
 * 
 * public static final Jukebox sound1 = new Jukebox("/ld30tunes.wav"); public
 * static final Jukebox intruder = new Jukebox("/intruder.wav"); public static
 * final Jukebox intruder2 = new Jukebox("/humanoidescape.wav"); public static
 * final Jukebox explosion = new Jukebox("/Explosion.wav"); public static final
 * Jukebox hit = new Jukebox("/hit.wav"); public static final Jukebox jump = new
 * Jukebox("/Jump.wav"); public static final Jukebox checkpoint = new
 * Jukebox("/Pickup_Coin.wav");
 * 
 * private AudioClip clip;
 * 
 * public Jukebox(String filename) { try { clip =
 * Applet.newAudioClip(Jukebox.class.getResource(filename)); } catch (Exception
 * e) { e.printStackTrace(); } }
 * 
 * public void play() { try { new Thread() { public void run() { clip.play(); }
 * }.start(); } catch (Exception ex) { ex.printStackTrace(); } }
 * 
 * public void stop() { try { new Thread() { public void run() { clip.stop(); }
 * }.start(); } catch (Exception exc) { exc.printStackTrace(); } }
 * 
 * public void loop() { try { new Thread() { public void run() { clip.loop(); }
 * }.start(); } catch (Exception exce) { exce.printStackTrace(); } }
 * 
 * }
 * 
 * This is my audio class. I have implemented a play,stop, and loop method.
 * 
 * You play the sounds by writing:Jukebox.sound1.loop(); to loop the audio
 * file.Hope this helps .
 */