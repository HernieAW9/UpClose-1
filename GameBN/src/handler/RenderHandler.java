package handler;

import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import entity.Rectangle;
import entity.Sprite;
import game.Game;

public class RenderHandler {

	private BufferedImage view;
	private int[] pixels;
	private Rectangle camera;
	private int maxScreenWidth, maxScreenHeight;

	public RenderHandler(int width, int height) {
		//get all devices
		GraphicsDevice[] graphicsDevices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();

		for(int i = 0; i < graphicsDevices.length; i++) {
			if(maxScreenWidth < graphicsDevices[i].getDisplayMode().getWidth()) {
				maxScreenWidth = graphicsDevices[i].getDisplayMode().getWidth();
			}
			
			if(maxScreenHeight < graphicsDevices[i].getDisplayMode().getHeight()) {
				maxScreenHeight = graphicsDevices[i].getDisplayMode().getHeight();
			}
		}
		
		//bufferedimage that will represent our view
		view = new BufferedImage(maxScreenWidth, maxScreenHeight, BufferedImage.TYPE_INT_RGB);

		camera = new Rectangle(0, 0, width, height);

		// array for pixels
		pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();
	}


	//render array of pixels to screen
	public void render(Graphics graphics) {
		graphics.drawImage(view.getSubimage(0, 0, camera.w, camera.h), 0, 0, camera.w, camera.h, null);

	}

	//render an img to array of pixels
	public void renderImage(BufferedImage image, int xPos, int yPos, int xZoom, int yZoom, boolean fixed) {
		int[] imagePixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		renderArray(imagePixels, image.getWidth(), image.getHeight(), xPos, yPos, xZoom, yZoom, fixed);

	}
	
	public void renderSprite(Sprite sprite, int xPos, int yPos, int xZoom, int yZoom, boolean fixed) {
		renderArray(sprite.getPixels(), sprite.getWidth(), sprite.getHeight(), xPos, yPos, xZoom, yZoom, fixed);
		
	}
	
	public void renderRectangle(Rectangle rectangle, int xZoom, int yZoom, boolean fixed) {
		int[] rectanglePixels = rectangle.getPixels();
		if(rectanglePixels != null) {
			renderArray(rectanglePixels, rectangle.w, rectangle.h, rectangle.x, rectangle.y, xZoom, yZoom, fixed);
		}
	}
	
	public void renderRectangle(Rectangle rectangle, Rectangle offset, int xZoom, int yZoom, boolean fixed) {
		int[] rectanglePixels = rectangle.getPixels();
		if(rectanglePixels != null) {
			renderArray(rectanglePixels, rectangle.w, rectangle.h, rectangle.x + offset.x, rectangle.y + offset.y, xZoom, yZoom, fixed);
		}
	}

	public void renderArray(int[] renderPixels, int renderWidth, int renderHeight, int xPos, int yPos, int xZoom, int yZoom, boolean fixed) {
		for(int y=0; y< renderHeight; y++) {
			for(int x=0; x< renderWidth; x++) {
				for(int yZoomPos = 0; yZoomPos < yZoom; yZoomPos++) {
					for(int xZoomPos = 0; xZoomPos < xZoom; xZoomPos++) {
						setPixel(renderPixels[x + y * renderWidth], ((x * xZoom) + xPos + xZoomPos), ((y*yZoom) + yPos + yZoomPos), fixed);

					}
				}
			}
		}
	}

	private void setPixel(int pixel, int x, int y, boolean fixed) {
		int pixelIndex = 0;
		
		if(!fixed) {
		if(x >= camera.x && y >= camera.y && x <= camera.x + camera.w && y <= camera.y + camera.h) {
			pixelIndex = (x - camera.x) + (y-camera.y) * view.getWidth();
			
		}
		}
		
		else {
			if(x >= 0 && y >= 0 && x <=  camera.w && y <= camera.h) {
				pixelIndex = x + y * view.getWidth();
			}
		}
		
		if(pixels.length > pixelIndex && pixel != Game.alpha) {
			pixels[pixelIndex] = pixel;
		}

	}
	
	public Rectangle getCamera() {
		return camera;
	}
	
	public int getMaxWidth() {
		return maxScreenWidth;
	}

	public int getMaxHeight() {
		return maxScreenHeight;
	}
	
	
	public void clear() {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}
}
