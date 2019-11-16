package model;

import java.io.*;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class SerializePhoto implements Serializable {
	
	private int width;
	private int height;
	private int[][] pixels;

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int[][] getPixels() {
		return pixels;
	}
	
	public void setPhoto(Image image) {
		width = ((int) image.getWidth());
		height = ((int) image.getHeight());
		pixels = new int[width][height];
		PixelReader reader = image.getPixelReader();
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				pixels[i][j] = reader.getArgb(i, j);
			}
		}
	}
	
	public Image getPhoto(Image image) {
		WritableImage photo = new WritableImage(width, height);
		PixelWriter writer = photo.getPixelWriter();
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				writer.setArgb(i, j, pixels[i][j]);
			}
		}
		return photo;
	}
	
	public boolean samePhoto (SerializePhoto photo) {
		if (width != photo.getWidth() || height != photo.getHeight()) {
			return false;
		}
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (pixels[i][j] != photo.getPixels()[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
}

