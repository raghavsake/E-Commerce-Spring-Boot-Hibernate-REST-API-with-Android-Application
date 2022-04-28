package com.ecommerce.model;
public class Image {

    private boolean error;
    private String message;
    private String image;

    public String getMessage() {
        return message;
    }

	public Image(boolean error, String message, String image) {
		super();
		this.error = error;
		this.message = message;
		this.image = image;
	}

	@Override
	public String toString() {
		return "Image [error=" + error + ", message=" + message + ", image=" + image + "]";
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
