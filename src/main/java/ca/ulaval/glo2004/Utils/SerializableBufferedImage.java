package ca.ulaval.glo2004.Utils;

import java.io.*;
import java.awt.image.BufferedImage;

//Cette classe est adaptée de: http://chiproject.googlecode.com/svn/trunk/src/org/stratesync/model/SerializedImage.java

public class SerializableBufferedImage implements Serializable {

    private BufferedImage bimage;

    public SerializableBufferedImage(BufferedImage bufferedImage){
        bimage = bufferedImage;
    }

    public BufferedImage getBImage(){
        return bimage;
    }

    public int getWidth(){
        return bimage.getWidth();
    }

    public int getHeight(){
        return bimage.getHeight();
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.writeInt(bimage.getWidth());
        s.writeInt(bimage.getHeight());
        s.writeInt(bimage.getType());

        for (int i = 0; i < bimage.getWidth(); i++){
            for (int j = 0; j < bimage.getHeight(); j++){
                s.writeInt(bimage.getRGB(i, j));
            }
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        bimage = new BufferedImage(s.readInt(), s.readInt(), s.readInt());
        for (int i = 0; i < bimage.getWidth(); i++){
            for (int j = 0; j < bimage.getHeight(); j++){
                bimage.setRGB(i, j, s.readInt());
            }
        }
    }
}
